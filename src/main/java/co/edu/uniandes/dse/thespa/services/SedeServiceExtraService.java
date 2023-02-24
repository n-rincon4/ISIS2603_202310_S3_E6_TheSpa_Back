package co.edu.uniandes.dse.thespa.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.thespa.entities.ServicioExtraEntity;
import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.repositories.SedeRepository;
import lombok.extern.slf4j.Slf4j;
import co.edu.uniandes.dse.thespa.repositories.ServicioExtraRepository;

//Author -> @Juan Coronel

@Slf4j
@Service
public class SedeServiceExtraService {

    // Inyeccion de dependencias -> Repositorio Sede
    @Autowired
    SedeRepository sedeRepo;

    // Inyeccion de dependencias -> Repositorio Servicio Extra
    @Autowired
    ServicioExtraRepository servicioExtraRepo;

    // añadir un servicio extra a la sede
    @Transactional
    ServicioExtraEntity addSedeExtraService(Long SedeId, Long SedeExtraServiceId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de añadir a la sede un servicio extra con con id = {0}", SedeExtraServiceId);
        Optional<ServicioExtraEntity> servEntity = servicioExtraRepo.findById(SedeExtraServiceId);
        if (servEntity.isEmpty()) {
            throw new EntityNotFoundException("EXTRA_SERVICE_NOT_FOUND");
        }

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(SedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException("SEDE_NOT_FOUND");
        }

        // revisa si el servicio extra ya esta en la sede, si esta lanza una
        // IllegalOperationException
        if (sedeEntity.get().getServiciosExtra().contains(servEntity.get())) {
            throw new IllegalOperationException("EXTRA_SERVICE_ALREADY_EXISTS");
        }

        List<ServicioExtraEntity> servs = sedeEntity.get().getServiciosExtra();
        servs.add(servEntity.get());

        sedeEntity.get().setServiciosExtra(servs);

        log.info("Termina proceso de añadir a la sede un servicio extra con con id = {0}", SedeId);

        return servEntity.get();
    }

    // Eliminar un servicio extra de la sede
    @Transactional
    ServicioExtraEntity deleteSedeExtraService(Long SedeId, Long SedeExtraServiceId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de remover a la sede un servicio extra con con id = {0}", SedeExtraServiceId);
        Optional<ServicioExtraEntity> servEntity = servicioExtraRepo.findById(SedeExtraServiceId);
        if (servEntity.isEmpty()) {
            throw new EntityNotFoundException("EXTRA_SERVICE_NOT_FOUND");
        }

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(SedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException("SEDE_NOT_FOUND");
        }

        // revisa si el servicio extra no esta en la sede, si no esta lanza una
        // IllegalOperationException
        if (sedeEntity.get().getServiciosExtra().contains(servEntity.get()) == false) {
            throw new IllegalOperationException("EXTRA_SERVICE_NOT_FOUND_IN_CURRENT_SEDE");
        }

        List<ServicioExtraEntity> servs = sedeEntity.get().getServiciosExtra();
        servs.remove(servEntity.get());

        sedeEntity.get().setServiciosExtra(servs);

        log.info("Termina proceso de eliminar de la sede un servicio extra con con id = {0}", SedeId);

        return servEntity.get();

    }
    
}
