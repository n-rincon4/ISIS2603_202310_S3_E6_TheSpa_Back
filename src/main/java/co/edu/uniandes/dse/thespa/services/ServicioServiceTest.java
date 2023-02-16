package co.edu.uniandes.dse.thespa.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import co.edu.uniandes.dse.thespa.entities.ArticuloDeRopaEntity;
import co.edu.uniandes.dse.thespa.repositories.ArticuloDeRopaRepository;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import uk.co.jemos.podam.api.PodamFactory;

@ExtendWith(SpringExtension.class)
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
    }

    @Test 
    void testCreateBook(){}
}
