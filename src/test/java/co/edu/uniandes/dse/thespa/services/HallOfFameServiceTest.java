package co.edu.uniandes.dse.thespa.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import co.edu.uniandes.dse.thespa.entities.HallOfFameEntity;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;

@DataJpaTest
@Transactional
@Import(HallOfFameService.class)

public class HallOfFameServiceTest {

    @Autowired
    private HallOfFameService hallOfFameService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<HallOfFameEntity> hofList = new ArrayList<>();
    
    @BeforeEach
    void setUp()
    {
        clearData();
        insertData();
    }

    private void clearData()
    {
        entityManager.getEntityManager().createQuery("delete from HallOfFameEntity");
    }

    private void insertData()
    {
        for (int i = 0; i < 3; i++)
        {
            HallOfFameEntity hofEntity = factory.manufacturePojo(HallOfFameEntity.class);
            entityManager.persist(hofEntity);
            hofList.add(hofEntity);
        }
    }

    @Test
    void testCreateHallOfFame() throws EntityNotFoundException, IllegalOperationException {
        HallOfFameEntity newEntity = factory.manufacturePojo(HallOfFameEntity.class);
        newEntity.setRatingPromedio(100);
        HallOfFameEntity result = hallOfFameService.createHallOfFame(newEntity);
        assertNotNull(result);
        HallOfFameEntity entity = entityManager.find(HallOfFameEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getRatingPromedio(), entity.getRatingPromedio());
    }

    @Test
    void testGetHallOfFame() throws EntityNotFoundException {
        HallOfFameEntity entity = hofList.get(0);
        HallOfFameEntity resultEntity = hallOfFameService.getHallOfFame(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getRatingPromedio(), resultEntity.getRatingPromedio());
    }

    @Test
    void testGetInvalidHallOfFame() {
        assertThrows(EntityNotFoundException.class, () -> {
            hallOfFameService.getHallOfFame(0L);
        });
    }

    @Test
    void testUpdateHallOfFame() throws EntityNotFoundException, IllegalOperationException {
        HallOfFameEntity entity = hofList.get(0);
        HallOfFameEntity pojoEntity = factory.manufacturePojo(HallOfFameEntity.class);
        pojoEntity.setId(entity.getId());
        pojoEntity.setRatingPromedio(100);
        hallOfFameService.updateHallOfFame(entity.getId(), pojoEntity);
        HallOfFameEntity resp = entityManager.find(HallOfFameEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getRatingPromedio(), resp.getRatingPromedio());
    }

    @Test
    void testDeleteHallOfFame() throws EntityNotFoundException {
        HallOfFameEntity entity = hofList.get(0);
        hallOfFameService.deleteHallOfFame(entity.getId());
        HallOfFameEntity deleted = entityManager.find(HallOfFameEntity.class, entity.getId());
        assertNull(deleted);
    }
}
