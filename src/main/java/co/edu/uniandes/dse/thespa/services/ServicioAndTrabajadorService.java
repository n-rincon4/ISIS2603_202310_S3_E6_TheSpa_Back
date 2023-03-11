package co.edu.uniandes.dse.thespa.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.thespa.entities.ServicioEntity;
import co.edu.uniandes.dse.thespa.entities.TrabajadorEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.repositories.TrabajadorRepository;
import co.edu.uniandes.dse.thespa.repositories.ServicioRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ServicioAndTrabajadorService {
    // String estático para eliminar el code smell en el mensaje de excepción y
    // reporte
    private static final String MENSAJE_TRABAJADOR_NO_EXISTE = "El trabajador con el id = {0} no existe";
    private static final String MENSAJE_SERVICIO_NO_EXISTE = "El servicio con el id = {0} no existe";

    @Autowired
    ServicioRepository servicioRepository;

    @Autowired
    TrabajadorRepository trabajadorRepository;

    @Transactional
    public TrabajadorEntity addTrabajador(Long servicioID, long trabajadorID)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de agregar un trabajador al servicio con id = {0}", servicioID);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioID);
        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_SERVICIO_NO_EXISTE, servicioID));
        }
        Optional<TrabajadorEntity> trabajadorEntity = trabajadorRepository.findById(trabajadorID);
        if (trabajadorEntity.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_TRABAJADOR_NO_EXISTE, trabajadorID));
        }

        if (servicioEntity.get().getTrabajadores().contains(trabajadorEntity.get())) {
            throw new IllegalOperationException("El trabajador con el id = " + trabajadorID
                    + " ya está asociado al servicio con el id = " + servicioID);
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
            throw new EntityNotFoundException(String.format(MENSAJE_SERVICIO_NO_EXISTE, servicioID));
        }
        log.info("Termina proceso de consultar los trabajadores del servicio con id = {0}", servicioID);
        return servicioEntity.get().getTrabajadores();
    }

    @Transactional
    public TrabajadorEntity removeTrabajador(Long servicioID, long trabajadorID)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de remover un trabajador del servicio con id = {0}", servicioID);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioID);
        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_SERVICIO_NO_EXISTE, servicioID));
        }
        Optional<TrabajadorEntity> trabajadorEntity = trabajadorRepository.findById(trabajadorID);
        if (trabajadorEntity.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_TRABAJADOR_NO_EXISTE, trabajadorID));
        }

        if (!servicioEntity.get().getTrabajadores().contains(trabajadorEntity.get())) {
            throw new IllegalOperationException("El trabajador con el id = " + trabajadorID
                    + " no está asociado al servicio con el id = " + servicioID);
        }
        servicioEntity.get().getTrabajadores().remove(trabajadorEntity.get());
        log.info("Termina proceso de remover un trabajador del servicio con id = {0}", servicioID);

        return trabajadorEntity.get();
    }

}
