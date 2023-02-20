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
import co.edu.uniandes.dse.thespa.entities.*;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
@Import(ServicioService.class)

public class ServicioServiceTest {

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<ServicioEntity> servicioList = new ArrayList<>();
    private List<PackDeServiciosEntity> packList = new ArrayList<>();
    private List<SedeEntity> sedeList = new ArrayList<>();
    private List<TrabajadorEntity> trabajadorList = new ArrayList<>();

    /**
     * Configuración inicial de la prueba.
     */
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    /*
     * Limpia las tablas que están implicadas en la prueba.
     */

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ServicioEntity");
    }

    /*
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ServicioEntity servicio = factory.manufacturePojo(ServicioEntity.class);
            entityManager.persist(servicio);
            servicioList.add(servicio);
        }

        for (int i = 0; i < 3; i++) {
            PackDeServiciosEntity pack = factory.manufacturePojo(PackDeServiciosEntity.class);
            entityManager.persist(pack);
            packList.add(pack);
        }

        for (int i = 0; i < 3; i++) {
            SedeEntity sede = factory.manufacturePojo(SedeEntity.class);
            entityManager.persist(sede);
            sedeList.add(sede);
        }

        for (int i = 0; i < 3; i++) {
            TrabajadorEntity trabajador = factory.manufacturePojo(TrabajadorEntity.class);
            entityManager.persist(trabajador);
            trabajadorList.add(trabajador);
        }

    }

    @Test
    void testCreateServicio() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity newEntity = servicioList.get(0);
        ServicioEntity result = servicioService.createServicio(newEntity);
        assertNotNull(result);

        ServicioEntity entity = entityManager.find(ServicioEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getDuracion(), entity.getDuracion());
        assertEquals(newEntity.getRestricciones(), entity.getRestricciones());
        assertEquals(newEntity.getDisponible(), entity.getDisponible());
        assertEquals(newEntity.getHoraInicio(), entity.getHoraInicio());
        assertEquals(newEntity.getHoraFin(), entity.getHoraFin());
    }

    @Test
    void testCreateServicioSinNombre() {
        ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
        newEntity.setNombre(null);
        assertThrows(IllegalOperationException.class, () -> {
            servicioService.createServicio(newEntity);
        });
    }

    @Test
    void testCreateServicioSinSede() {
        ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
        newEntity.setSede(null);
        assertThrows(IllegalOperationException.class, () -> {
            servicioService.createServicio(newEntity);
        });
    }

    @Test
    void testCreateServicioSinTrabajadores() {
        ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
        newEntity.setSede(null);
        assertThrows(IllegalOperationException.class, () -> {
            servicioService.createServicio(newEntity);
        });
    }

    @Test
    void testGetServicios() {
        List<ServicioEntity> list = servicioService.getServicios();
        assertEquals(servicioList.size(), list.size());
        for (ServicioEntity entity : list) {
            boolean found = false;
            for (ServicioEntity storedEntity : servicioList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

    @Test
    void testGetServicio() throws EntityNotFoundException {
        ServicioEntity entity = servicioList.get(0);
        ServicioEntity resultEntity = servicioService.getServicio(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        assertEquals(entity.getPrecio(), resultEntity.getPrecio());
        assertEquals(entity.getSede(), resultEntity.getSede());
        assertEquals(entity.getPacksDeServicios(), resultEntity.getPacksDeServicios());
        assertEquals(entity.getTrabajadores(), resultEntity.getTrabajadores());
        assertEquals(entity.getDuracion(), resultEntity.getDuracion());
        assertEquals(entity.getRestricciones(), resultEntity.getRestricciones());
        assertEquals(entity.getDisponible(), resultEntity.getDisponible());
        assertEquals(entity.getHoraInicio(), resultEntity.getHoraInicio());
        assertEquals(entity.getHoraFin(), resultEntity.getHoraFin());
    }

    @Test
    void testGetInvalidSServicio() {
        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.getServicio(0L);
        });
    }

    @Test
    void testUpdateServicio() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity entity = servicioList.get(0);
        ServicioEntity pojoEntity = factory.manufacturePojo(ServicioEntity.class);
        pojoEntity.setId(entity.getId());
        servicioService.updateServicio(entity.getId(), pojoEntity);

        ServicioEntity resp = entityManager.find(ServicioEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        assertEquals(pojoEntity.getPrecio(), resp.getPrecio());
        assertEquals(pojoEntity.getSede(), resp.getSede());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getPacksDeServicios(), resp.getPacksDeServicios());
        assertEquals(pojoEntity.getTrabajadores(), resp.getTrabajadores());
        assertEquals(pojoEntity.getDuracion(), resp.getDuracion());
        assertEquals(pojoEntity.getRestricciones(), resp.getRestricciones());
        assertEquals(pojoEntity.getDisponible(), resp.getDisponible());
        assertEquals(pojoEntity.getHoraInicio(), resp.getHoraInicio());
        assertEquals(pojoEntity.getHoraFin(), resp.getHoraFin());
    }

    @Test
    void testDeleteBook() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity entity = servicioList.get(1);
        servicioService.deleteServicio(entity.getId());
        ServicioEntity deleted = entityManager.find(ServicioEntity.class, entity.getId());
        assertNull(deleted);
    }
}
