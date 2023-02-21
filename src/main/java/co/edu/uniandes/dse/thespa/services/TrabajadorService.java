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
public class TrabajadorService {

    // Inyeccion de dependencias -> Repositorio Trabajador
    @Autowired
    private TrabajadorRepository trabajadorRepository;

    // Inyeccion de dependencias -> Repositorio Servicio
    @Autowired
    private ServicioRepository servicioRepository;

    // Método para la Creación de un trabajador
    @Transactional
    public TrabajadorEntity createTrabajador(TrabajadorEntity trabajadorEntity)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia el proceso de creación del trabajador");

        if ((trabajadorEntity.getNombre() == null) || (trabajadorEntity.getNombre().equals("")))
            throw new IllegalOperationException("El nombre del trabajador no puede estar vacío");

        if (trabajadorEntity.getSedes() == null)
            throw new IllegalOperationException("El trabajador debe estar en al menos una sede");

        if (trabajadorEntity.getServicios() == null)
            throw new IllegalOperationException("El trabajador debe tener relacionado al menos un servicio");

        log.info("Termina proceso de creación del trabajador");
        return trabajadorRepository.save(trabajadorEntity);
    }

    // Método para obtener todos los trabajadores
    @Transactional
    public List<TrabajadorEntity> getTrabajadores() {
        log.info("Inicia proceso de consultar todos los trabajadores");
        return trabajadorRepository.findAll();
    }

    // Método para obtener un trabajador por ID
    @Transactional
    public TrabajadorEntity getTrabajador(Long trabajadorId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar el trabajador con id = {0}", trabajadorId);
        Optional<TrabajadorEntity> trabajadorEntity = trabajadorRepository.findById(trabajadorId);
        if (trabajadorEntity.isEmpty())
            throw new EntityNotFoundException("El trabajador con el id = " + trabajadorId + " no existe");
        log.info("Termina proceso de consultar el trabajador con id = {0}", trabajadorId);
        return trabajadorEntity.get();
    }

    // Método para actualizar un trabajador
    @Transactional
    public TrabajadorEntity updateTrabajador(Long trabajadorId, TrabajadorEntity trabajador)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de actualizar el trabajador con id = {0}", trabajadorId);
        Optional<TrabajadorEntity> trabajadorEntity = trabajadorRepository.findById(trabajadorId);
        if (trabajadorEntity.isEmpty())
            throw new EntityNotFoundException("El trabajador con el id = " + trabajadorId + " no existe");

        if ((trabajador.getNombre() == null) || (trabajador.getNombre().equals("")))
            throw new IllegalOperationException("El nombre del trabajador no puede estar vacío");

        if (trabajador.getSedes() == null)
            throw new IllegalOperationException("El trabajador debe estar en al menos una sede");

        if (trabajador.getServicios() == null)
            throw new IllegalOperationException("El trabajador debe tener relacionado al menos un servicio");

        trabajador.setId(trabajadorId);
        log.info("Termina proceso de actualizar el trabajador con id = {0}", trabajadorId);
        return trabajadorRepository.save(trabajador);
    }

    // Método para borrar un trabajador
    @Transactional
    public void deleteTrabajador(Long trabajadorId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de borrar el trabajador con id = {0}", trabajadorId);
        Optional<TrabajadorEntity> trabajadorEntity = trabajadorRepository.findById(trabajadorId);
        if (trabajadorEntity.isEmpty())
            throw new EntityNotFoundException("No se encontró el trabajador con id = " + trabajadorId + " no existe");

        trabajadorRepository.deleteById(trabajadorId);
        log.info("Termina proceso de borrar el trabajador con id = {0}", trabajadorId);
    }

    // Añadir un servicio al trabajador
    @Transactional
    public ServicioEntity addServicioToTrabajador(Long trabajadorId, Long servicioId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de añadir al trabajador un servicio con con id = {0}", servicioId);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException("SERVICE_NOT_FOUND");
        }

        Optional<TrabajadorEntity> trabajadorEntity = trabajadorRepository.findById(trabajadorId);
        if (trabajadorEntity.isEmpty()) {
            throw new EntityNotFoundException("TRABAJADOR_NOT_FOUND");
        }

        // revisa si el servicio ya esta en la sede
        if (trabajadorEntity.get().getServicios().contains(servicioEntity.get())) {
            throw new IllegalOperationException("SERVICE_ALREADY_EXISTS");
        }

        List<ServicioEntity> servicios = trabajadorEntity.get().getServicios();
        servicios.add(servicioEntity.get());

        trabajadorEntity.get().setServicios(servicios);

        log.info("Termina proceso de añadir al trabajador el servicio con con id = {0}", servicioId);

        return servicioEntity.get();
    }

    // Eliminar un servicio del trabajador
    @Transactional
    public ServicioEntity deleteServicioTrabajador(Long trabajadorId, Long servicioId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de remover al trabajador un servicio con con id = {0}", servicioId);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException("SERVICE_NOT_FOUND");
        }

        Optional<TrabajadorEntity> trabajadorEntity = trabajadorRepository.findById(trabajadorId);
        if (trabajadorEntity.isEmpty()) {
            throw new EntityNotFoundException("TRABAJADOR_NOT_FOUND");
        }

        // revisa si el servicio no esta en el trabajador
        if (trabajadorEntity.get().getServicios().contains(servicioEntity.get()) == false) {
            throw new IllegalOperationException("SERVICE_NOT_FOUND_IN_CURRENT_TRABAJADOR");
        }

        List<ServicioEntity> servicios = trabajadorEntity.get().getServicios();
        servicios.remove(servicioEntity.get());

        trabajadorEntity.get().setServicios(servicios);

        log.info("Termina proceso de eliminar del trabajador un servicio con con id = {0}", servicioId);

        return servicioEntity.get();
    }
}
