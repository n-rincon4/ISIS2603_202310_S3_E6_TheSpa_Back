package co.edu.uniandes.dse.thespa.services;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.repositories.SedeRepository;
import lombok.extern.slf4j.Slf4j;
//Author -> @Juan Coronel


@Slf4j
@Service
public class SedeService {
    
    //Inyeccion de dependencias -> Repositorio Sede
    @Autowired
    SedeRepository sedeRepo;

    //Crear Sede
    @Transactional
    public SedeEntity createSede(SedeEntity sede) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de creacion de Sede.");

        //Assert 1: el nombre no debe ser null
        String nombreSede = sede.getNombre();
        if (nombreSede==null){
            throw new IllegalOperationException("La sede tiene que tener un nombre.");
        }

        //Assert 2: el nombre debe ser unico
        //Assert 3: la sede no debe de existir en la base de datos
        List<SedeEntity> allSedes =  getSedes();
        for (SedeEntity sed:allSedes){
            if (sed.getNombre()==sede.getNombre()){
                throw new IllegalOperationException("El nombre de la sede debe ser unico.");
            }
            else if (sed.getId()==sede.getId()){
                throw new IllegalOperationException("La sede ya existe en la base de datos.");
            }

        }
        //Assert 4: el nombre no debe ser un string vacio
        if (nombreSede==""){
            throw new IllegalOperationException("La sede tiene que tener un nombre no vacio.");
        }

        //Assert 5: la lista de servicios no debe ser vacia
        if (sede.getServicios().isEmpty() == true){
            throw new IllegalOperationException("La sede tiene que tener al menos un servicio."); 
        }

        //Assert 6: Debe haber al menos un trabajador
        if (sede.getTrabajadores().isEmpty() == true){
            throw new IllegalOperationException("La sede tiene que tener al menos un trabajador."); 
        }

        //Assert 7: debe tener una ubicacion la sede
        if (sede.getUbicacion().equals(null)){
            throw new IllegalOperationException("La sede tiene que tener una ubicacion."); 
        }

        //Assert 8: debe tener un hall of fame
        if (sede.getHof().equals(null)){
            throw new IllegalOperationException("La sede tiene que tener un hall of fame."); 
        }

        //Assert 9: debe tener un Pack de servicios
        if (sede.getPacksDeServicios().isEmpty()==true){
            throw new IllegalOperationException("La sede tiene que tener al menos unpack de servicios"); 
        }

        log.info("Finaliza proceso de creacion de Sede.");
        return sedeRepo.save(sede);


    }

    //Obtener todas las sedes
    @Transactional
    public List<SedeEntity> getSedes(){
        log.info("Inicia proceso de encontrar todas las sedes");
        return sedeRepo.findAll();
    }

    //Obtener una sede segun su Id
    @Transactional
    public SedeEntity getSede(Long sedeId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar la Sede con id = {0}", sedeId);
        Optional<SedeEntity> sedeEntity = sedeRepo.findById(sedeId);
        if (sedeEntity.isEmpty())
                throw new EntityNotFoundException("SEDE_NOT_FOUND");
        log.info("Termina proceso de consultar la Sede con id = {0}", sedeId);
        return sedeEntity.get();
    }

    //Actualizar una sede
    @Transactional
    public SedeEntity updateSede(Long SedeId, SedeEntity sede)
                        throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de actualizar la Sede con id = {0}", SedeId);
        Optional<SedeEntity> SedeEntity = sedeRepo.findById(SedeId);
        if (SedeEntity.isEmpty())
                throw new EntityNotFoundException("SEDE_NOT_FOUND");

        sede.setId(SedeId);
        log.info("Termina proceso de actualizar la Sede con id = {0}", SedeId);
        return sedeRepo.save(sede);
    }

    //Eliminar una Sede
    @Transactional
    public void deleteSede(Long SedeId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de borrar la sede con id = {0}", SedeId);
        Optional<SedeEntity> sedeEntity = sedeRepo.findById(SedeId);
        if (sedeEntity.isEmpty()){
                throw new EntityNotFoundException("SEDE_NOT_FOUND");
                }

        sedeRepo.deleteById(SedeId);
        log.info("Termina proceso de borrar la sede con id = {0}", SedeId);
    }      
}
