package co.edu.uniandes.dse.thespa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.thespa.entities.PackDeServiciosEntity;
import co.edu.uniandes.dse.thespa.entities.ServicioEntity;
import co.edu.uniandes.dse.thespa.entities.SedeEntity;

import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import({ ServicioService.class, PackDeServiciosAndServicioService.class })
public class PackDeServiciosAndServicioServiceTest {

    // Servicio que se va a probar
    @Autowired
    private PackDeServiciosAndServicioService packDeServiciosAndServicioService;

    // TestEntityManager
    @Autowired
    private TestEntityManager entityManager;

    // PodamFactory para generar datos aleatorios
    private PodamFactory factory = new PodamFactoryImpl();

    // Lista de Packs de servicios para la prueba
    private List<PackDeServiciosEntity> packs = new ArrayList<>();

    // Lista de servicios para la prueba
    private List<ServicioEntity> servicios = new ArrayList<>();

    // Lista de sedes para la prueba
    private List<SedeEntity> sedes = new ArrayList<>();

    // configuracion inicial de la prueba
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    // Limpia las tablas que están implicadas en la prueba
    private void clearData() {
        entityManager.clear();
        entityManager.getEntityManager().createQuery("delete from PackDeServiciosEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from ServicioEntity").executeUpdate();
    }

    // Inserta datos en las tablas para la prueba
    private void insertData() {
        // Se crea una lista de servicios
        for (int i = 0; i < 3; i++) {
            ServicioEntity servicio = factory.manufacturePojo(ServicioEntity.class);
            entityManager.persist(servicio);
            servicios.add(servicio);
        }
        // Se crea una lista de packs de servicios
        for (int i = 0; i < 3; i++) {
            PackDeServiciosEntity pack = factory.manufacturePojo(PackDeServiciosEntity.class);
            entityManager.persist(pack);
            packs.add(pack);
            pack.setServicios(new ArrayList<>());
            // por cada pack de servicios se le asigna una lista de nuevos servicios
            for (int j = 0; j < 3; j++) {
                ServicioEntity servicio = factory.manufacturePojo(ServicioEntity.class);
                entityManager.persist(servicio);
                pack.getServicios().add(servicio);
            }

        }
        // Se crea una lista se sedes
        for (int i = 0; i < 3; i++) {
            SedeEntity sede = factory.manufacturePojo(SedeEntity.class);
            entityManager.persist(sede);
            sedes.add(sede);
        }

        // se le agrega una sede al servicio y al pack de servicios
        for (int i = 0; i < 3; i++) {
            ServicioEntity servicio = servicios.get(i);
            servicio.setSede(sedes.get(i));
            entityManager.merge(servicio);
            PackDeServiciosEntity pack = packs.get(i);
            pack.setSede(sedes.get(i));
            entityManager.merge(pack);
        }

    }

    // Prueba para agregar un servicio a un pack de servicios
    @Test
    void addServicioTest() throws EntityNotFoundException, IllegalOperationException {
        // Se obtiene el primer pack de servicios de la lista de packs de servicios
        PackDeServiciosEntity pack = packs.get(0);
        // Se obtiene el primer servicio de la lista de servicios
        ServicioEntity servicio = servicios.get(0);

        // Se agrega el servicio al pack de servicios
        ServicioEntity result = packDeServiciosAndServicioService.addServicio(pack.getId(), servicio.getId());

        // Se verifica que el servicio agregado sea el mismo que se obtuvo
        assertNotNull(result);
        assertEquals(servicio, result);

    }

    // Prueba para agregar un servicio que ya pertenece a un pack de servicios, se
    // espera un error de tipo IllegalOperationException
    @Test
    void addServicioAlreadyInPackTest() throws EntityNotFoundException, IllegalOperationException {
        // Se obtiene el primer pack de servicios de la lista de packs de servicios
        PackDeServiciosEntity pack = packs.get(0);
        // Se obtiene el primer servicio de la lista de servicios del pack de servicios
        ServicioEntity servicio = pack.getServicios().get(0);

        // Se agrega el servicio al pack de servicios, se espera un error de tipo
        // IllegalOperationException
        assertThrows(IllegalOperationException.class, () -> {
            packDeServiciosAndServicioService.addServicio(pack.getId(), servicio.getId());
        });
    }

    // Prueba para obtener la lista de servicios de un pack de servicios
    @Test
    void getServiciosTest() throws EntityNotFoundException {
        // Se obtiene el primer pack de servicios de la lista de packs de servicios
        PackDeServiciosEntity pack = packs.get(0);
        // Se obtiene la lista de servicios del pack de servicios
        List<ServicioEntity> serviciosPack = packDeServiciosAndServicioService.getServicios(pack.getId());

        // Se verifica que la lista de servicios del pack de servicios sea la misma que
        // la
        // lista de servicios del pack de servicios
        assertNotNull(serviciosPack);
        assertEquals(pack.getServicios(), serviciosPack);
    }

    // Prueba para obtener la lista de servicios de un pack de servicios que no
    // existe, se espera un error de tipo EntityNotFoundException
    @Test
    void getServiciosPackNotFoundTest() {
        // Se obtiene el id de un pack de servicios que no existe
        Long id = Long.MAX_VALUE;

        // Se obtiene la lista de servicios del pack de servicios, se espera un error de
        // tipo EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> {
            packDeServiciosAndServicioService.getServicios(id);
        });
    }

    // Prueba para eliminar un servicio de un pack de servicios
    @Test
    void removeServicioTest() throws EntityNotFoundException, IllegalOperationException {
        // Se obtiene el primer pack de servicios de la lista de packs de servicios
        PackDeServiciosEntity pack = packs.get(0);
        // Se obtiene el primer servicio de la lista de servicios del pack de servicios
        ServicioEntity servicio = pack.getServicios().get(0);

        // Se elimina el servicio del pack de servicios
        packDeServiciosAndServicioService.removeServicio(pack.getId(), servicio.getId());

        // Se verifica que el servicio no se encuentre en la lista de servicios del pack
        // de servicios
        List<ServicioEntity> serviciosPack = packDeServiciosAndServicioService.getServicios(pack.getId());
        assertNotNull(serviciosPack);
        assertEquals(2, serviciosPack.size());
    }

    // Prueba para eliminar un servicio que no pertenece a un pack de servicios, se
    // espera un error de tipo IllegalOperationException
    @Test
    void removeServicioNotInPackTest() throws EntityNotFoundException, IllegalOperationException {
        // Se obtiene el primer pack de servicios de la lista de packs de servicios
        PackDeServiciosEntity pack = packs.get(0);
        // Se obtiene el primer servicio de la lista de servicios
        ServicioEntity servicio = servicios.get(0);

        // Se elimina el servicio del pack de servicios
        assertThrows(IllegalOperationException.class, () -> {
            packDeServiciosAndServicioService.removeServicio(pack.getId(), servicio.getId());
        });

    }

    // Prueba para agregar un servicio a un pack de servicios que no existe
    @Test
    void addServicioPackNotFoundTest() {
        // Se obtiene el id de un pack de servicios que no existe
        Long id = Long.MAX_VALUE;
        // Se obtiene el primer servicio de la lista de servicios
        ServicioEntity servicio = servicios.get(0);

        // Se agrega el servicio al pack de servicios, se espera un error de tipo
        // EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> {
            packDeServiciosAndServicioService.addServicio(id, servicio.getId());
        });
    }

    // Prueba para agregar un servicio que no existe a un pack de servicios
    @Test
    void addServicioNotFoundTest() throws EntityNotFoundException {
        // Se obtiene el primer pack de servicios de la lista de packs de servicios
        PackDeServiciosEntity pack = packs.get(0);
        // Se obtiene el id de un servicio que no existe
        Long id = Long.MAX_VALUE;

        // Se agrega el servicio al pack de servicios, se espera un error de tipo
        // EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> {
            packDeServiciosAndServicioService.addServicio(pack.getId(), id);
        });
    }

    // prueba para agregar un servicio que no existe a un pack de servicios que no
    // existe
    @Test
    void addServicioPackAndServicioNotFoundTest() {
        // Se obtiene el id de un pack de servicios que no existe
        Long idPack = Long.MAX_VALUE;
        // Se obtiene el id de un servicio que no existe
        Long idServicio = Long.MAX_VALUE;

        // Se agrega el servicio al pack de servicios, se espera un error de tipo
        // EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> {
            packDeServiciosAndServicioService.addServicio(idPack, idServicio);
        });
    }

    // Prueba para eliminar un servicio de un pack de servicios que no existe
    @Test
    void removeServicioPackNotFoundTest() {
        // Se obtiene el id de un pack de servicios que no existe
        Long id = Long.MAX_VALUE;
        // Se obtiene el primer servicio de la lista de servicios
        ServicioEntity servicio = servicios.get(0);

        // Se elimina el servicio del pack de servicios, se espera un error de tipo
        // EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> {
            packDeServiciosAndServicioService.removeServicio(id, servicio.getId());
        });
    }

    // Prueba para eliminar un servicio que no existe de un pack de servicios
    @Test
    void removeServicioNotFoundTest() throws EntityNotFoundException {
        // Se obtiene el primer pack de servicios de la lista de packs de servicios
        PackDeServiciosEntity pack = packs.get(0);
        // Se obtiene el id de un servicio que no existe
        Long id = Long.MAX_VALUE;

        // Se elimina el servicio del pack de servicios, se espera un error de tipo
        // EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> {
            packDeServiciosAndServicioService.removeServicio(pack.getId(), id);
        });
    }

    // Prueba para eliminar un servicio que no existe de un pack de servicios que no
    // existe
    @Test
    void removeServicioPackAndServicioNotFoundTest() {
        // Se obtienen los ids de un pack de servicios y un servicio que no existen
        Long idPack = Long.MAX_VALUE;
        Long idServicio = Long.MAX_VALUE;

        // Se elimina el servicio del pack de servicios, se espera un error de tipo
        // EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> {
            packDeServiciosAndServicioService.removeServicio(idPack, idServicio);
        });
    }

}