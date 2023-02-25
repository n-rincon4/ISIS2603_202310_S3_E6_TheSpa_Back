package co.edu.uniandes.dse.thespa.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.thespa.entities.ServicioEntity;
import co.edu.uniandes.dse.thespa.entities.PackDeServiciosEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.repositories.ServicioRepository;
import co.edu.uniandes.dse.thespa.repositories.PackDeServiciosRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ServicioAndPackDeServiciosService {

    @Autowired
    ServicioRepository servicioRepository;

    @Autowired
    PackDeServiciosRepository packDeServiciosRepository;

    @Transactional
    public PackDeServiciosEntity addPackDeServicios(Long servicioID, long packDeServiciosID)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de agregar un pack de servicios al servicio con id = {0}", servicioID);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioID);
        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException("El servicio con el id = " + servicioID + " no existe");
        }
        Optional<PackDeServiciosEntity> packDeServiciosEntity = packDeServiciosRepository.findById(packDeServiciosID);
        if (packDeServiciosEntity.isEmpty()) {
            throw new EntityNotFoundException("El pack de servicios con el id = " + packDeServiciosID + " no existe");
        }

        if (servicioEntity.get().getPacksDeServicios().contains(packDeServiciosEntity.get())) {
            throw new IllegalOperationException("El pack de servicios con el id = " + packDeServiciosID
                    + " ya está asociado al servicio con el id = " + servicioID);
        }
        servicioEntity.get().getPacksDeServicios().add(packDeServiciosEntity.get());
        log.info("Termina proceso de agregar un pack de servicios al servicio con id = {0}", servicioID);

        return packDeServiciosEntity.get();
    }

    @Transactional
    public List<PackDeServiciosEntity> getPacksDeServicios(Long servicioID) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar los packs de servicios del servicio con id = {0}", servicioID);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioID);
        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException("El servicio con el id = " + servicioID + " no existe");
        }
        log.info("Termina proceso de consultar los packs de servicios del servicio con id = {0}", servicioID);
        return servicioEntity.get().getPacksDeServicios();
    }

    @Transactional
    public PackDeServiciosEntity removePackDeServicios(Long servicioID, long packDeServiciosID)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de remover un pack de servicios del servicio con id = {0}", servicioID);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioID);
        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException("El servicio con el id = " + servicioID + " no existe");
        }
        Optional<PackDeServiciosEntity> packDeServiciosEntity = packDeServiciosRepository.findById(packDeServiciosID);
        if (packDeServiciosEntity.isEmpty()) {
            throw new EntityNotFoundException("El pack de servicios con el id = " + packDeServiciosID + " no existe");
        }

        if (!servicioEntity.get().getPacksDeServicios().contains(packDeServiciosEntity.get())) {
            throw new IllegalOperationException("El pack de servicios con el id = " + packDeServiciosID
                    + " no está asociado al servicio con el id = " + servicioID);
        }
        servicioEntity.get().getPacksDeServicios().remove(packDeServiciosEntity.get());
        log.info("Termina proceso de remover un pack de servicios del servicio con id = {0}", servicioID);

        return packDeServiciosEntity.get();
    }

}
