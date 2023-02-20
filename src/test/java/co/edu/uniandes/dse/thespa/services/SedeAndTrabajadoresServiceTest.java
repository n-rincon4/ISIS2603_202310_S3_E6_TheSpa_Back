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
import co.edu.uniandes.dse.thespa.entities.TrabajadorEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
//@Autor -> Juan Coronel

@DataJpaTest
@Transactional
@Import({ SedeService.class, TrabajadorService.class })
public class SedeAndTrabajadoresServiceTest {

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

    // Lista de trabajadores
    private List<TrabajadorEntity> trabajadores = new ArrayList<>();

    // Configuracion inicial de la prueba
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    // Limpia las tablas que están implicadas en la prueba
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from SedeEntity");
        entityManager.getEntityManager().createQuery("delete from TrabajadorEntity");

    }

    // Inserta los datos de prueba en la lista de Sedes
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
            TrabajadorEntity tEntity = factory.manufacturePojo(TrabajadorEntity.class);
            entityManager.persist(tEntity);

            List<TrabajadorEntity> t = new ArrayList<>();
            t.add(tEntity);
            entity.setTrabajadores(t);
            entityManager.persist(entity);
            sedes.add(entity);

        }
        for (int i = 0; i < 3; i++) {
            TrabajadorEntity entity = factory.manufacturePojo(TrabajadorEntity.class);
            entityManager.persist(entity);
            trabajadores.add(entity);
        }
    }

    // Prueba 1: Agregar un Trabajador a una sede
    @Test
    void testAddTrabajadorToSede() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        TrabajadorEntity trabaj = trabajadores.get(0);

        TrabajadorEntity answer = SedeService.addSedeTrabajador(sede.getId(), trabaj.getId());
        assertNotNull(answer);
        assertEquals(trabaj.getId(), answer.getId());

    }

    // Prueba 2: Agregar un Trabajador que ya existe en una sede, esperando una
    // excepcion de tipo IllegalOperationException
    @Test
    void testAddTrabajadorToSedeAlreadyExists() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        TrabajadorEntity trabaj = sede.getTrabajadores().get(0);

        assertThrows(IllegalOperationException.class, () -> {
            SedeService.addSedeTrabajador(sede.getId(), trabaj.getId());
        });

    }

    // Prueba 3: Eliminar un Trabajador a una sede
    @Test
    void testdeleteTrabajadorToSede() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        TrabajadorEntity trabaj = sede.getTrabajadores().get(0);

        TrabajadorEntity answer = SedeService.deleteSedeTrabajador(sede.getId(), trabaj.getId());
        assertNotNull(answer);
        assertEquals(trabaj.getId(), answer.getId());

    }

    // Prueba 4: Eliminar un Trabajador que no existe en una sede, esperando una
    // excepcion de tipo IllegalOperationException
    @Test
    void testdeleteTrabajadorToSedeNotExists() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        TrabajadorEntity trabaj = trabajadores.get(0);

        assertThrows(IllegalOperationException.class, () -> {
            SedeService.deleteSedeTrabajador(sede.getId(), trabaj.getId());
        });

    }

}
