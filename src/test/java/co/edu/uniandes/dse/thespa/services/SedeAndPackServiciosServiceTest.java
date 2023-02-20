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
import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
//@Autor -> Juan Coronel

@DataJpaTest
@Transactional
@Import({ SedeService.class, PackDeServiciosService.class })
public class SedeAndPackServiciosServiceTest {

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

    // Lista de pack de servicios
    private List<PackDeServiciosEntity> packs = new ArrayList<>();

    // Configuracion inicial de la prueba
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    // Limpia las tablas que están implicadas en la prueba
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from SedeEntity");
        entityManager.getEntityManager().createQuery("delete from PackDeServiciosEntity");

    }

    // Inserta los datos de prueba en la lista de Sedes
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
            PackDeServiciosEntity pEntity = factory.manufacturePojo(PackDeServiciosEntity.class);
            entityManager.persist(pEntity);

            List<PackDeServiciosEntity> p = new ArrayList<>();
            p.add(pEntity);
            entity.setPacksDeServicios(p);
            entityManager.persist(entity);
            sedes.add(entity);

        }
        for (int i = 0; i < 3; i++) {
            PackDeServiciosEntity entity = factory.manufacturePojo(PackDeServiciosEntity.class);
            entityManager.persist(entity);
            packs.add(entity);
        }
    }

    // Prueba 1: Agregar un Pack de servicios a una sede
    @Test
    void testAddPackDeServiciosToSede() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        PackDeServiciosEntity PackDeServicios = packs.get(0);

        PackDeServiciosEntity answer = SedeService.addSedePackDeServicios(sede.getId(), PackDeServicios.getId());
        assertNotNull(answer);
        assertEquals(PackDeServicios.getId(), answer.getId());

    }

    // Prueba 2: Intentar agregar un Pack de servicios ya existente en una sede.
    // Espera una IllegalOperationException
    @Test
    void testAddPackDeServiciosToSedeAlreadyExist() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        PackDeServiciosEntity PackDeServicios = sede.getPacksDeServicios().get(0);

        assertThrows(IllegalOperationException.class, () -> {
            SedeService.addSedePackDeServicios(sede.getId(), PackDeServicios.getId());
        });
    }

    // Prueba 3: Eliminar un Pack de servicios de una sede
    @Test
    void testdeletePackDeServiciosToSede() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        PackDeServiciosEntity PackDeServicios = sede.getPacksDeServicios().get(0);

        PackDeServiciosEntity answer = SedeService.deleteSedePackDeServicios(sede.getId(), PackDeServicios.getId());
        assertNotNull(answer);
        assertEquals(PackDeServicios.getId(), answer.getId());

    }

    // Prueba 4: Intentar eliminar un Pack de servicios que no existe en una sede.
    // Espera una IllegalOperationException
    @Test
    void testdeletePackDeServiciosToSedeNotExist() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        PackDeServiciosEntity PackDeServicios = packs.get(0);

        assertThrows(IllegalOperationException.class, () -> {
            SedeService.deleteSedePackDeServicios(sede.getId(), PackDeServicios.getId());
        });
    }

}