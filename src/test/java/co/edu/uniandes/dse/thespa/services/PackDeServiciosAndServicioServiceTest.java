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
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import({ PackDeServiciosService.class, ServicioService.class })
public class PackDeServiciosAndServicioServiceTest {

    // Servicio que se va a probar
    @Autowired
    private PackDeServiciosService packDeServiciosService;

    // TestEntityManager
    @Autowired
    private TestEntityManager entityManager;

    // PodamFactory para generar datos aleatorios
    private PodamFactory factory = new PodamFactoryImpl();

    // Lista de Packs de servicios para la prueba
    private List<PackDeServiciosEntity> packs = new ArrayList<>();

    // Lista de servicios para la prueba
    private List<ServicioEntity> servicios = new ArrayList<>();

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
    }

    // Prueba para agregar un servicio a un pack de servicios
    @Test
    void addServicioTest() throws EntityNotFoundException, IllegalOperationException {
        // Se obtiene el primer pack de servicios de la lista de packs de servicios
        PackDeServiciosEntity pack = packs.get(0);
        // Se obtiene el primer servicio de la lista de servicios
        ServicioEntity servicio = servicios.get(0);

        // Se agrega el servicio al pack de servicios
        ServicioEntity result = packDeServiciosService.addServicio(pack.getId(), servicio.getId());

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
            packDeServiciosService.addServicio(pack.getId(), servicio.getId());
        });
    }

    // Prueba para obtener la lista de servicios de un pack de servicios
    @Test
    void getServiciosTest() throws EntityNotFoundException {
        // Se obtiene el primer pack de servicios de la lista de packs de servicios
        PackDeServiciosEntity pack = packs.get(0);
        // Se obtiene la lista de servicios del pack de servicios
        List<ServicioEntity> serviciosPack = packDeServiciosService.getServicios(pack.getId());

        // Se verifica que la lista de servicios del pack de servicios sea la misma que
        // la
        // lista de servicios del pack de servicios
        assertNotNull(serviciosPack);
        assertEquals(pack.getServicios(), serviciosPack);
    }

    // Prueba para eliminar un servicio de un pack de servicios
    @Test
    void removeServicioTest() throws EntityNotFoundException, IllegalOperationException {
        // Se obtiene el primer pack de servicios de la lista de packs de servicios
        PackDeServiciosEntity pack = packs.get(0);
        // Se obtiene el primer servicio de la lista de servicios del pack de servicios
        ServicioEntity servicio = pack.getServicios().get(0);

        // Se elimina el servicio del pack de servicios
        packDeServiciosService.removeServicio(pack.getId(), servicio.getId());

        // Se verifica que el servicio no se encuentre en la lista de servicios del pack
        // de servicios
        List<ServicioEntity> serviciosPack = packDeServiciosService.getServicios(pack.getId());
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
            packDeServiciosService.removeServicio(pack.getId(), servicio.getId());
        });

    }

}