package co.edu.uniandes.dse.thespa.services;

import co.edu.uniandes.dse.thespa.entities.*;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.thespa.entities.ServicioEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.repositories.PackDeServiciosRepository;
import co.edu.uniandes.dse.thespa.repositories.SedeRepository;
import co.edu.uniandes.dse.thespa.repositories.ServicioRepository;
import co.edu.uniandes.dse.thespa.repositories.TrabajadorRepository;
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
    public ServicioEntity createServicio(ServicioEntity servicioEntity) throws EntityNotFoundException {
        log.info("Inicia proceso de creación del servicio");

        if (servicioEntity.getDuracion() == null) {
            throw new EntityNotFoundException("Duración is not valid");
        }

        if (servicioEntity.getRestricciones() == null) {
            throw new EntityNotFoundException("Restricciones is not valid");
        }

        if (servicioEntity.getDisponible() == null) {
            throw new EntityNotFoundException("Disponible is not valid");
        }

        if (servicioEntity.getHoraInicio() == null) {
            throw new EntityNotFoundException("HoraInicio is not valid");
        }

        if (servicioEntity.getHoraFin() == null) {
            throw new EntityNotFoundException("HoraFin is not valid");
        }

        if (servicioEntity.getNombre() == null) {
            throw new EntityNotFoundException("Nombre is not valid");
        }

        if (servicioEntity.getPacksDeServicios() == null){
            throw new EntityNotFoundException("PacksDeServicios is not valid");
        }

        if (servicioEntity.getSede() == null){
            throw new EntityNotFoundException("Sede is not valid");
        }

        if (servicioEntity.getTrabajadores() == null){
            throw new EntityNotFoundException("Trabajadores is not valid");
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
}
