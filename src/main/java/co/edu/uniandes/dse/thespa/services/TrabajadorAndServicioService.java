package co.edu.uniandes.dse.thespa.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import co.edu.uniandes.dse.thespa.entities.ServicioEntity;
import co.edu.uniandes.dse.thespa.entities.TrabajadorEntity;
import co.edu.uniandes.dse.thespa.repositories.ServicioRepository;
import co.edu.uniandes.dse.thespa.repositories.TrabajadorRepository;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TrabajadorAndServicioService {
    // String estático para eliminar el code smell en el mensaje de excepción y reporte
    private static final String SERVICE_NOT_FOUND = "SERVICE_NOT_FOUND";
    private static final String TRABAJADOR_NOT_FOUND = "TRABAJADOR_NOT_FOUND";
    private static final String SERVICE_ALREADY_EXISTS = "SERVICE_ALREADY_EXISTS";
    private static final String SERVICE_NOT_FOUND_IN_CURRENT_TRABAJADOR = "SERVICE_NOT_FOUND_IN_CURRENT_TRABAJADOR";

    // Inyeccion de dependencias -> Repositorio Trabajador
    @Autowired
    private TrabajadorRepository trabajadorRepository;

    // Inyeccion de dependencias -> Repositorio Servicio
    @Autowired
    private ServicioRepository servicioRepository;

    // Añadir un servicio al trabajador
    @Transactional
    public ServicioEntity addServicioToTrabajador(Long trabajadorId, Long servicioId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de añadir al trabajador con id = {0} un servicio con id = {1}", trabajadorId, servicioId);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException(SERVICE_NOT_FOUND);
        }

        Optional<TrabajadorEntity> trabajadorEntity = trabajadorRepository.findById(trabajadorId);
        if (trabajadorEntity.isEmpty()) {
            throw new EntityNotFoundException(TRABAJADOR_NOT_FOUND);
        }

        // revisa si el servicio ya esta en la sede
        if (trabajadorEntity.get().getServicios().contains(servicioEntity.get())) {
            throw new IllegalOperationException(SERVICE_ALREADY_EXISTS);
        }

        List<ServicioEntity> servicios = trabajadorEntity.get().getServicios();
        servicios.add(servicioEntity.get());

        trabajadorEntity.get().setServicios(servicios);

        log.info("Termina proceso de añadir al trabajador con id = {0} el servicio con id = {1}", trabajadorId, servicioId);

        return servicioEntity.get();
    }

    // Eliminar un servicio del trabajador
    @Transactional
    public ServicioEntity deleteServicioTrabajador(Long trabajadorId, Long servicioId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de remover al trabajador con id = {0} un servicio con id = {1}", trabajadorId, servicioId);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException(SERVICE_NOT_FOUND);
        }

        Optional<TrabajadorEntity> trabajadorEntity = trabajadorRepository.findById(trabajadorId);
        if (trabajadorEntity.isEmpty()) {
            throw new EntityNotFoundException(TRABAJADOR_NOT_FOUND);
        }

        // revisa si el servicio no esta en el trabajador
        if (!trabajadorEntity.get().getServicios().contains(servicioEntity.get())) {
            throw new IllegalOperationException(SERVICE_NOT_FOUND_IN_CURRENT_TRABAJADOR);
        }

        List<ServicioEntity> servicios = trabajadorEntity.get().getServicios();
        servicios.remove(servicioEntity.get());

        trabajadorEntity.get().setServicios(servicios);

        log.info("Termina proceso de eliminar del trabajador con id = {0} un servicio con id = {1}", trabajadorId, servicioId);

        return servicioEntity.get();
    }
}
