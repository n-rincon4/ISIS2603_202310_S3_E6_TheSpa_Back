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

import co.edu.uniandes.dse.thespa.entities.TrabajadorEntity;
import co.edu.uniandes.dse.thespa.entities.ServicioEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import({ ServicioAndTrabajadorService.class })

public class ServicioAndTrabajadorServiceTest {

    @Autowired
    private ServicioAndTrabajadorService servicioService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<ServicioEntity> servicios = new ArrayList<>();

    private List<TrabajadorEntity> trabajadores = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.clear();
        entityManager.getEntityManager().createQuery("delete from ServicioEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from TrabajadorEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ServicioEntity servicio = factory.manufacturePojo(ServicioEntity.class);
            entityManager.persist(servicio);
            servicios.add(servicio);
            servicio.setTrabajadores(new ArrayList<>());
            for (int j = 0; j < 3; j++) {
                TrabajadorEntity trabajador = factory.manufacturePojo(TrabajadorEntity.class);
                entityManager.persist(trabajador);
                trabajadores.add(trabajador);
                servicio.getTrabajadores().add(trabajador);
            }
        }

    }

    @Test
    void addTrabajadorTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        TrabajadorEntity trabajador = factory.manufacturePojo(TrabajadorEntity.class);
        entityManager.persist(trabajador);
        trabajadores.add(trabajador);

        TrabajadorEntity result = servicioService.addTrabajador(servicio.getId(), trabajador.getId());

        assertNotNull(result);
        assertEquals(trabajador, result);
    }

    @Test
    void addTrabajadorEmptyServicioTest() throws EntityNotFoundException, IllegalOperationException {
        Long id = 0L;
        TrabajadorEntity trabajador = factory.manufacturePojo(TrabajadorEntity.class);

        entityManager.persist(trabajador);
        trabajadores.add(trabajador);

        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.addTrabajador(id, trabajador.getId());
        });
    }

    @Test
    void addTrabajadorEmptyTrabajadorTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        Long id = 0L;

        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.addTrabajador(servicio.getId(), id);
        });
    }

    @Test
    void addTrabajadorAlreadyInServicioTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        TrabajadorEntity trabajador = trabajadores.get(0);

        assertThrows(IllegalOperationException.class, () -> {
            servicioService.addTrabajador(servicio.getId(), trabajador.getId());
        });
    }

    @Test
    void getTrabajadoresTest() throws EntityNotFoundException {
        ServicioEntity servicio = servicios.get(0);

        List<TrabajadorEntity> result = servicioService.getTrabajadores(servicio.getId());

        assertNotNull(result);
        assertEquals(servicio.getTrabajadores(), result);
    }

    @Test
    void getTrabajadoresEmptyServicioTest() throws EntityNotFoundException {
        Long id = 0L;

        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.getTrabajadores(id);
        });
    }

    @Test
    void getTrabajadorEmptyTrabajadorTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        Long id = 0L;

        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.getTrabajador(servicio.getId(), id);
        });
    }

    @Test
    void getTrabajadorEmptyServicioTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        Long id = 0L;

        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.getTrabajador(id, servicio.getId());
        });
    }

    @Test
    void getTrabajadorNotInServicioTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        TrabajadorEntity trabajador = factory.manufacturePojo(TrabajadorEntity.class);
        entityManager.persist(trabajador);
        trabajadores.add(trabajador);

        assertThrows(IllegalOperationException.class, () -> {
            servicioService.getTrabajador(servicio.getId(), trabajador.getId());
        });
    }

    @Test
    void getTrabajadorTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        TrabajadorEntity trabajador = trabajadores.get(0);

        TrabajadorEntity result = servicioService.getTrabajador(servicio.getId(), trabajador.getId());

        assertNotNull(result);
        assertEquals(trabajador, result);
    }

    @Test
    void removeTrabajadorTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        TrabajadorEntity trabajador = trabajadores.get(0);

        servicioService.removeTrabajador(servicio.getId(), trabajador.getId());

        List<TrabajadorEntity> result = servicioService.getTrabajadores(servicio.getId());
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void removeTrabajadorEmptyServicioTest() throws EntityNotFoundException, IllegalOperationException {
        Long id = 0L;
        TrabajadorEntity trabajador = factory.manufacturePojo(TrabajadorEntity.class);

        entityManager.persist(trabajador);
        trabajadores.add(trabajador);

        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.removeTrabajador(id, trabajador.getId());
        });
    }

    @Test
    void removeTrabajadorEmptyTrabajadorTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        Long id = 0L;

        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.removeTrabajador(servicio.getId(), id);
        });
    }

    @Test
    void removeTrabajadorNotInServicioTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        TrabajadorEntity trabajador = factory.manufacturePojo(TrabajadorEntity.class);
        entityManager.persist(trabajador);
        trabajadores.add(trabajador);

        assertThrows(IllegalOperationException.class, () -> {
            servicioService.removeTrabajador(servicio.getId(), trabajador.getId());
        });
    }

    @Test
    void updateTrabajadores() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);

        servicioService.updateTrabajadores(servicio.getId(), trabajadores);

        List<TrabajadorEntity> trabajadores2 = servicioService.getTrabajadores(servicio.getId());
        assertNotNull(trabajadores2);
        assertEquals(trabajadores.size(), trabajadores2.size());
    }

    @Test
    void updateTrabajadoresEmptyServicio() throws EntityNotFoundException, IllegalOperationException {
        Long id = 0L;

        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.updateTrabajadores(id, trabajadores);
        });
    }
}
