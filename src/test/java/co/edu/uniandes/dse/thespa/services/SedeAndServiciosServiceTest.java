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
import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.entities.ServicioEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
//@Autor -> Juan Coronel

@DataJpaTest
@Transactional
@Import({ SedeService.class, ServicioService.class })
public class SedeAndServiciosServiceTest {

    // Servicio que se va a probar
    @Autowired
    private SedeService SedeService;

    // TestEntityManager
    @Autowired
    private TestEntityManager entityManager;

    // PodamFactory para generar datos aleatorios
    private PodamFactory factory = new PodamFactoryImpl();

    // Lista de sedes
    private List<SedeEntity> sedes = new ArrayList<>();

    // Lista de servicios
    private List<ServicioEntity> servicios = new ArrayList<>();

    // Configuracion inicial de la prueba
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    // Limpia las tablas que están implicadas en la prueba
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from SedeEntity");
        entityManager.getEntityManager().createQuery("delete from ServicioEntity");

    }

    // Inserta los datos de prueba en la lista de Sedes
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
            List<ServicioEntity> serviciosFicticios = new ArrayList<>();

            for (int n = 0; n < 3; n++) {
                ServicioEntity serEntity = factory.manufacturePojo(ServicioEntity.class);
                entityManager.persist(serEntity);
                serviciosFicticios.add(serEntity);
            }

            entity.setServicios(serviciosFicticios);

            entityManager.persist(entity);
            sedes.add(entity);

        }
        for (int i = 0; i < 3; i++) {
            ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
            entityManager.persist(entity);
            servicios.add(entity);
        }
    }

    // Prueba 1: Agregar un servicio a una sede
    @Test
    void testAddServiceToSede() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        ServicioEntity service = servicios.get(0);

        ServicioEntity answer = SedeService.addSedeServicio(sede.getId(), service.getId());
        assertNotNull(answer);
        assertEquals(service.getId(), answer.getId());

    }

    // Prueba 2: Agregar un servicio ya existente en una sede
    @Test
    void testAddServiceToSedeAlreadyExist() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        ServicioEntity service = sede.getServicios().get(0);

        assertThrows(IllegalOperationException.class, () -> {
            SedeService.addSedeServicio(sede.getId(), service.getId());
        });
    }

    // Prueba 3: eliminar un servicio de una sede
    @Test
    void testDeleteServiceToSede() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        ServicioEntity serv = sede.getServicios().get(0);

        ServicioEntity answer = SedeService.deleteSedeServicio(sede.getId(), serv.getId());

        assertNotNull(answer);
        assertEquals(serv.getId(), answer.getId());
    }

    // Prueba 4: eliminar un servicio que no existe en una sede
    @Test
    void testDeleteServiceToSedeNotExist() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        ServicioEntity serv = servicios.get(0);

        assertThrows(IllegalOperationException.class, () -> {
            SedeService.deleteSedeServicio(sede.getId(), serv.getId());
        });
    }
}
