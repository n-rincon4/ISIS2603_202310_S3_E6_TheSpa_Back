package co.edu.uniandes.dse.thespa.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import co.edu.uniandes.dse.thespa.entities.TrabajadorEntity;
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

    // Método para la Creación de un trabajador
    @Transactional
    public TrabajadorEntity createTrabajador(TrabajadorEntity trabajadorEntity) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia el proceso de creación del trabajador");

        if (trabajadorEntity.getNombre() == null)
            throw new IllegalOperationException("El nombre del trabajador no puede estar vacío");
        
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

        if (trabajador.getNombre() == null)
                throw new IllegalOperationException("El nombre del trabajador no puede estar vacío");

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
}
