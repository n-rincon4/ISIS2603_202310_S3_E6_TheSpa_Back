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
import co.edu.uniandes.dse.thespa.entities.ServicioExtraEntity;
import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
//@Autor -> Juan Coronel

@DataJpaTest
@Transactional
@Import({ SedeService.class, ServicioExtraService.class })
public class SedeAndServicioExtraServiceTest {

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

    // Lista de servicios extra
    private List<ServicioExtraEntity> serviciosExtra = new ArrayList<>();

    // Configuracion inicial de la prueba
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    // Limpia las tablas que están implicadas en la prueba
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from SedeEntity");
        entityManager.getEntityManager().createQuery("delete from ServicioExtraEntity");

    }

    // Inserta los datos de prueba en la lista de Sedes

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            SedeEntity sede = factory.manufacturePojo(SedeEntity.class);
            entityManager.persist(sede);
            // define la lista de servicios extra
            List<ServicioExtraEntity> serviciosExtraTemp = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                ServicioExtraEntity servicioExtra = factory.manufacturePojo(ServicioExtraEntity.class);
                entityManager.persist(servicioExtra);
                serviciosExtraTemp.add(servicioExtra);
            }
            sede.setServiciosExtra(serviciosExtraTemp);

            sedes.add(sede);
        }
        for (int i = 0; i < 3; i++) {
            ServicioExtraEntity servicioExtra = factory.manufacturePojo(ServicioExtraEntity.class);
            entityManager.persist(servicioExtra);
            serviciosExtra.add(servicioExtra);
        }

    }

    // Prueba para agregar un servicio extra a una sede
    @Test
    void addServicioExtraTest() throws EntityNotFoundException, IllegalOperationException {
        // Se obtiene una sede aleatoria
        SedeEntity sede = sedes.get(0);
        // Se obtiene un servicio extra aleatorio
        ServicioExtraEntity servicioExtra = serviciosExtra.get(0);
        // Se agrega el servicio extra a la sede
        ServicioExtraEntity answer = SedeService.addSedeExtraService(sede.getId(), servicioExtra.getId());
        // Se verifica que el servicio extra se haya agregado a la sede
        assertNotNull(answer);
        assertEquals(answer.getId(), servicioExtra.getId());
    }

    // Prueba para agregar un servicio ya existente a una sede
    // Se espera que se lance una excepción de tipo IllegalOperationException
    @Test
    void addServicioExtraAlreadyExistsTest() throws EntityNotFoundException, IllegalOperationException {
        // Se obtiene una sede aleatoria
        SedeEntity sede = sedes.get(0);
        // Se obtiene un servicio extra aleatorio
        ServicioExtraEntity servicioExtra = sede.getServiciosExtra().get(0);
        // Se agrega el servicio extra a la sede
        assertThrows(IllegalOperationException.class, () -> {
            SedeService.addSedeExtraService(sede.getId(), servicioExtra.getId());
        });
    }

    // Prueba para eliminar un servicio extra de una sede
    @Test
    void deleteServicioExtraTest() throws EntityNotFoundException, IllegalOperationException {
        // Se obtiene una sede aleatoria
        SedeEntity sede = sedes.get(0);
        // Se obtiene un servicio extra aleatorio
        ServicioExtraEntity servicioExtra = sede.getServiciosExtra().get(0);
        // Se elimina el servicio extra de la sede
        SedeService.deleteSedeExtraService(sede.getId(), servicioExtra.getId());
        // Se verifica que el servicio extra se haya eliminado de la sede
        assertEquals(sede.getServiciosExtra().size(), 2);
    }

    // Prueba para eliminar un servicio extra que no existe de una sede
    // Se espera que se lance una excepción de tipo IllegalOperationException
    @Test
    void deleteServicioExtraNotExistsTest() throws EntityNotFoundException, IllegalOperationException {
        // Se obtiene una sede aleatoria
        SedeEntity sede = sedes.get(0);
        // Se obtiene un servicio extra aleatorio
        ServicioExtraEntity servicioExtra = factory.manufacturePojo(ServicioExtraEntity.class);
        entityManager.persist(servicioExtra);
        // Se elimina el servicio extra de la sede
        assertThrows(IllegalOperationException.class, () -> {
            SedeService.deleteSedeExtraService(sede.getId(), servicioExtra.getId());
        });
    }

}
