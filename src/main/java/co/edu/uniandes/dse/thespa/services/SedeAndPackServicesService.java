package co.edu.uniandes.dse.thespa.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.thespa.entities.PackDeServiciosEntity;
import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.repositories.PackDeServiciosRepository;
import co.edu.uniandes.dse.thespa.repositories.SedeRepository;
import lombok.extern.slf4j.Slf4j;

//Author -> @Juan Coronel

@Slf4j
@Service
public class SedeAndPackServicesService {

    // Inyeccion de dependencias -> Repositorio Sede
    @Autowired
    SedeRepository sedeRepo;

    // Inyeccion de dependencias -> Repositorio Pack de Servicios
    @Autowired
    PackDeServiciosRepository packDeServiciosRepo;

    //Obtener todos los packs servicios de una sede
    @Transactional
    public List<PackDeServiciosEntity> obtenerAllPacks(Long sedeId) {
        return packDeServiciosRepo.findAll();
    }

    // Añadir un Pack de servicios a la sede
    @Transactional
    public PackDeServiciosEntity addSedePackDeServicios(Long sedeId, Long packDeServiciosId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de añadir a la sede un PackDeServicios con con id = {0}", packDeServiciosId);
        Optional<PackDeServiciosEntity> packEntity = packDeServiciosRepo.findById(packDeServiciosId);
        if (packEntity.isEmpty()) {
            throw new EntityNotFoundException("PACK_NOT_FOUND");
        }

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(sedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException("SEDE_NOT_FOUND");
        }

        // revisa si el pack ya esta en la sede, si esta lanza una
        // IllegalOperationException
        if (sedeEntity.get().getPacksDeServicios().contains(packEntity.get())) {
            throw new IllegalOperationException("PACK_ALREADY_EXISTS");
        }

        List<PackDeServiciosEntity> packDeServicios = sedeEntity.get().getPacksDeServicios();
        packDeServicios.add(packEntity.get());

        sedeEntity.get().setPacksDeServicios(packDeServicios);

        log.info("Termina proceso de añadir a la sede un Trabajador con con id = {0}", sedeId);

        return packEntity.get();
    }

    // Eliminar un pack de servicios de la sede
    @Transactional
    public PackDeServiciosEntity deleteSedePackDeServicios(Long sedeId, Long packDeServiciosId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de remover a la sede un PackDeServicios con con id = {0}", packDeServiciosId);
        Optional<PackDeServiciosEntity> packEntity = packDeServiciosRepo.findById(packDeServiciosId);
        if (packEntity.isEmpty()) {
            throw new EntityNotFoundException("PACK_NOT_FOUND");
        }

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(sedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException("SEDE_NOT_FOUND");
        }

        // revisa si el pack no esta en la sede, si no esta lanza una
        // IllegalOperationException
        if (!sedeEntity.get().getPacksDeServicios().contains(packEntity.get())) {
            throw new IllegalOperationException("PACK_NOT_FOUND_IN_CURRENT_SEDE");
        }

        List<PackDeServiciosEntity> packDeServicios = sedeEntity.get().getPacksDeServicios();
        packDeServicios.remove(packEntity.get());

        sedeEntity.get().setPacksDeServicios(packDeServicios);

        log.info("Termina proceso de eliminar de la sede un PackDeServicios con con id = {0}", sedeId);

        return packEntity.get();
    }
}
