package co.edu.uniandes.dse.thespa.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.entities.ServicioEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.repositories.SedeRepository;
import co.edu.uniandes.dse.thespa.repositories.ServicioRepository;
import lombok.extern.slf4j.Slf4j;

//Author -> @Juan Coronel

@Slf4j
@Service
public class SedeAndServicioService {

    // Inyeccion de dependencias -> Repositorio Sede
    @Autowired
    SedeRepository sedeRepo;

    // Inyeccion de dependencias -> Repositorio Servicio
    @Autowired
    ServicioRepository servicioRepo;

    // Eliminar una Sede
    @Transactional
    public void deleteSede(Long SedeId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de borrar la sede con id = {0}", SedeId);
        Optional<SedeEntity> sedeEntity = sedeRepo.findById(SedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException("SEDE_NOT_FOUND");
        }

        sedeRepo.deleteById(SedeId);
        log.info("Termina proceso de borrar la sede con id = {0}", SedeId);
    }

    // Añadir un servicio a la sede
    @Transactional
    public ServicioEntity addSedeServicio(Long SedeId, Long ServicioId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de añadir a la sede un servicio con con id = {0}", ServicioId);
        Optional<ServicioEntity> servEntity = servicioRepo.findById(ServicioId);
        if (servEntity.isEmpty()) {
            throw new EntityNotFoundException("SERVICE_NOT_FOUND");
        }

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(SedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException("SEDE_NOT_FOUND");
        }

        // revisa si el servicio ya esta en la sede, si esta lanza una
        // IllegalOperationException
        if (sedeEntity.get().getServicios().contains(servEntity.get())) {
            throw new IllegalOperationException("SERVICE_ALREADY_EXISTS");
        }

        List<ServicioEntity> servicios = sedeEntity.get().getServicios();
        servicios.add(servEntity.get());

        sedeEntity.get().setServicios(servicios);

        log.info("Termina proceso de añadir a la sede un servicio con con id = {0}", SedeId);

        return servEntity.get();
    }

    // Eliminar un servicio a la sede
    @Transactional
    public ServicioEntity deleteSedeServicio(Long SedeId, Long ServicioId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de eliminar de la sede un servicio con con id = {0}", ServicioId);
        Optional<ServicioEntity> servEntity = servicioRepo.findById(ServicioId);
        if (servEntity.isEmpty()) {
            throw new EntityNotFoundException("SERVICE_NOT_FOUND");
        }

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(SedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException("SEDE_NOT_FOUND");
        }

        List<ServicioEntity> servicios = sedeEntity.get().getServicios();

        // revisa si el servicio no esta en la sede, si no esta lanza una
        // IllegalOperationException
        if (servicios.contains(servEntity.get()) == false) {
            throw new IllegalOperationException("SERVICIO_NOT_FOUND_IN_CURRENT_SEDE");
        }
        servicios.remove(servEntity.get());

        sedeEntity.get().setServicios(servicios);

        log.info("Termina proceso de elimnar de la sede un servicio con con id = {0}", SedeId);

        return servEntity.get();
    }

}
