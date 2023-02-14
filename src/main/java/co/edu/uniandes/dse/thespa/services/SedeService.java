package co.edu.uniandes.dse.thespa.services;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.repositories.SedeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SedeService {
    
    //Inyeccion de dependencias -> Repositorio Sede
    @Autowired
    SedeRepository sedeRepo;

    //Crear Sede
    @Transactional
    public SedeEntity createSede(SedeEntity sede) throws EntityNotFoundException, IllegalOperationException {
        return sedeRepo.save(sede);
    }

    //Obtener todas las sedes
    @Transactional
    public List<SedeEntity> getSedes(){
        log.info("Inicia proceso de encontrar todas las sedes");
        return sedeRepo.findAll();
    }
}
