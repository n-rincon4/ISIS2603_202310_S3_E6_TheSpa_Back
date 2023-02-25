package co.edu.uniandes.dse.thespa.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.entities.TrabajadorEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.repositories.SedeRepository;
import co.edu.uniandes.dse.thespa.repositories.TrabajadorRepository;
import lombok.extern.slf4j.Slf4j;

//Author -> @Juan Coronel

@Slf4j
@Service
public class SedeAndTrabajadorService {

    // Inyeccion de dependencias -> Repositorio Sede
    @Autowired
    SedeRepository sedeRepo;

    // Inyeccion de dependencias -> Repositorio Trabajadores
    @Autowired
    TrabajadorRepository trabajadoresRepo;

    // Añadir un trabajador a la sede
    @Transactional
    public TrabajadorEntity addSedeTrabajador(Long SedeId, Long TrabajadorId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de añadir a la sede un Trabajador con con id = {0}", TrabajadorId);
        Optional<TrabajadorEntity> trabEntity = trabajadoresRepo.findById(TrabajadorId);
        if (trabEntity.isEmpty()) {
            throw new EntityNotFoundException("TRABAJADOR_NOT_FOUND");
        }

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(SedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException("SEDE_NOT_FOUND");
        }

        // revisa si el trabajador ya esta en la sede, si esta lanza una
        // IllegalOperationException
        if (sedeEntity.get().getTrabajadores().contains(trabEntity.get())) {
            throw new IllegalOperationException("TRABAJADOR_ALREADY_EXISTS");
        }

        List<TrabajadorEntity> trabajs = sedeEntity.get().getTrabajadores();
        trabajs.add(trabEntity.get());

        sedeEntity.get().setTrabajadores(trabajs);

        log.info("Termina proceso de añadir a la sede un Trabajador con con id = {0}", SedeId);

        return trabEntity.get();
    }

    // Eliminar un trabajador de la sede
    @Transactional
    public TrabajadorEntity deleteSedeTrabajador(Long SedeId, Long TrabajadorId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de eliminar de la sede un Trabajador con con id = {0}", TrabajadorId);
        Optional<TrabajadorEntity> trabEntity = trabajadoresRepo.findById(TrabajadorId);
        if (trabEntity.isEmpty()) {
            throw new EntityNotFoundException("TRABAJADOR_NOT_FOUND");
        }

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(SedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException("SEDE_NOT_FOUND");
        }

        // revisa si el trabajador no esta en la sede, si no esta lanza una
        // IllegalOperationException
        if (sedeEntity.get().getTrabajadores().contains(trabEntity.get()) == false) {
            throw new IllegalOperationException("TRABAJADOR_NOT_FOUND_IN_CURRENT_SEDE");
        }

        List<TrabajadorEntity> trabajs = sedeEntity.get().getTrabajadores();
        trabajs.remove(trabEntity.get());

        sedeEntity.get().setTrabajadores(trabajs);

        log.info("Termina proceso de añadir a la sede un Trabajador con con id = {0}", SedeId);

        return trabEntity.get();
    }
}
