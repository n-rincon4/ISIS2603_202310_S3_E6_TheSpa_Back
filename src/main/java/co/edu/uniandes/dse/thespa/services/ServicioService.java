package co.edu.uniandes.dse.thespa.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.thespa.entities.ServicioEntity;
import co.edu.uniandes.dse.thespa.entities.TrabajadorEntity;
import co.edu.uniandes.dse.thespa.entities.PackDeServiciosEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.repositories.TrabajadorRepository;
import co.edu.uniandes.dse.thespa.repositories.SedeRepository;
import co.edu.uniandes.dse.thespa.repositories.ServicioRepository;
import co.edu.uniandes.dse.thespa.repositories.PackDeServiciosRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service

public class ServicioService {

    @Autowired
    ServicioRepository servicioRepository;

    @Autowired
    PackDeServiciosRepository packDeServiciosRepository;

    @Autowired
    SedeRepository sedeRepository;

    @Autowired
    TrabajadorRepository trabajadorRepository;

    @Transactional
    public ServicioEntity createServicio(ServicioEntity servicioEntity)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de creación del servicio");

        if (servicioEntity.getNombre() == null || servicioEntity.getNombre().equals("")) {
            throw new IllegalOperationException("El nombre del servicio no es válido");
        }

        if (servicioEntity.getDescripcion() == null || servicioEntity.getDescripcion().equals("")) {
            throw new IllegalOperationException("La descripción del servicio no es válida");
        }

        if (servicioEntity.getPrecio() == null) {
            throw new IllegalOperationException("El precio del servicio no es válido");
        }

        if (servicioEntity.getDuracion() == null) {
            throw new IllegalOperationException("La duración del servicio no es válida");
        }

        if (servicioEntity.getRestricciones() == null || servicioEntity.getRestricciones().equals("")) {
            throw new IllegalOperationException("Las restricciones no son válidas");
        }

        if (servicioEntity.getDisponible() == null) {
            throw new IllegalOperationException("El servicio no tiene un estado de disponibilidad válido");
        }

        if (servicioEntity.getHoraInicio() == null) {
            throw new IllegalOperationException("La hora de inicio del servicio no es válida");
        }

        if (servicioEntity.getHoraFin() == null) {
            throw new IllegalOperationException("La hora de fin del servicio no es válida");
        }

        if (servicioEntity.getSede() == null) {
            throw new IllegalOperationException("La sede del servicio no es válida");
        }
        
        if (servicioEntity.getTrabajadores() == null) {
            throw new IllegalOperationException("Los trabajadores del servicio no son válidos");
        }

        if (servicioEntity.getPacksDeServicios() == null) {
            throw new IllegalOperationException("Los de servicios del servicio no son válidos");
        }

        log.info("Termina proceso de creación del Servicio");

        return servicioRepository.save(servicioEntity);
    }

    @Transactional
    public List<ServicioEntity> getServicios() {
        log.info("Inicia proceso de consultar todos los servicios");
        return servicioRepository.findAll();
    }

    @Transactional
    public ServicioEntity getServicio(Long servicioId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar el servicio con id = {0}", servicioId);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException("Servicio not found");
        }
        log.info("Termina proceso de consultar el servicio con id = {0}", servicioId);
        return servicioEntity.get();
    }

    @Transactional
    public ServicioEntity updateServicio(Long servicioId, ServicioEntity servicio) throws EntityNotFoundException {
        log.info("Inicia proceso de actualizar el servicio con id = {0}", servicioId);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException("Servicio not found");
        }
        servicio.setId(servicioEntity.get().getId());
        log.info("Termina proceso de actualizar el servicio con id = {0}", servicioId);
        return servicioRepository.save(servicio);
    }

    @Transactional
    public void deleteServicio(Long servicioId) throws EntityNotFoundException {
        log.info("Inicia proceso de borrar el servicio con id = {0}", servicioId);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException("Servicio not found");
        }
        servicioRepository.deleteById(servicioId);
        log.info("Termina proceso de borrar el servicio con id = {0}", servicioId);
    }

    @Transactional
    public TrabajadorEntity addTrabajador(Long servicioID, long trabajadorID) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de agregar un trabajador al servicio con id = {0}", servicioID);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioID);
        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException("El servicio con el id = " + servicioID + " no existe");
        }
        Optional<TrabajadorEntity> trabajadorEntity = trabajadorRepository.findById(trabajadorID);
        if (trabajadorEntity.isEmpty()) {
            throw new EntityNotFoundException("El trabajador con el id = " + trabajadorID + " no existe");
        }

        if (servicioEntity.get().getTrabajadores().contains(trabajadorEntity.get())) {
            throw new IllegalOperationException("El trabajador con el id = " + trabajadorID + " ya está asociado al servicio con el id = " + servicioID);
        }
        servicioEntity.get().getTrabajadores().add(trabajadorEntity.get());
        log.info("Termina proceso de agregar un trabajador al servicio con id = {0}", servicioID);

        return trabajadorEntity.get();
    }

    @Transactional
    public List<TrabajadorEntity> getTrabajadores(Long servicioID) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar los trabajadores del servicio con id = {0}", servicioID);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioID);
        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException("El servicio con el id = " + servicioID + " no existe");
        }
        log.info("Termina proceso de consultar los trabajadores del servicio con id = {0}", servicioID);
        return servicioEntity.get().getTrabajadores();
    }

    @Transactional
    public TrabajadorEntity removeTrabajador(Long servicioID, long trabajadorID) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de remover un trabajador del servicio con id = {0}", servicioID);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioID);
        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException("El servicio con el id = " + servicioID + " no existe");
        }
        Optional<TrabajadorEntity> trabajadorEntity = trabajadorRepository.findById(trabajadorID);
        if (trabajadorEntity.isEmpty()) {
            throw new EntityNotFoundException("El trabajador con el id = " + trabajadorID + " no existe");
        }

        if (!servicioEntity.get().getTrabajadores().contains(trabajadorEntity.get())) {
            throw new IllegalOperationException("El trabajador con el id = " + trabajadorID + " no está asociado al servicio con el id = " + servicioID);
        }
        servicioEntity.get().getTrabajadores().remove(trabajadorEntity.get());
        log.info("Termina proceso de remover un trabajador del servicio con id = {0}", servicioID);

        return trabajadorEntity.get();
    }

    @Transactional
    public PackDeServiciosEntity addPackDeServicios(Long servicioID, long packDeServiciosID) throws EntityNotFoundException, IllegalOperationException {
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
            throw new IllegalOperationException("El pack de servicios con el id = " + packDeServiciosID + " ya está asociado al servicio con el id = " + servicioID);
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
    public PackDeServiciosEntity removePackDeServicios(Long servicioID, long packDeServiciosID) throws EntityNotFoundException, IllegalOperationException {
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
            throw new IllegalOperationException("El pack de servicios con el id = " + packDeServiciosID + " no está asociado al servicio con el id = " + servicioID);
        }
        servicioEntity.get().getPacksDeServicios().remove(packDeServiciosEntity.get());
        log.info("Termina proceso de remover un pack de servicios del servicio con id = {0}", servicioID);

        return packDeServiciosEntity.get();
    }
}
