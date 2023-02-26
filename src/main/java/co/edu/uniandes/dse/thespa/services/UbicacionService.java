package co.edu.uniandes.dse.thespa.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import co.edu.uniandes.dse.thespa.entities.UbicacionEntity;

import co.edu.uniandes.dse.thespa.repositories.UbicacionRepository;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UbicacionService {
    // String estático para eliminar el code smell en el mensaje de excepción y reporte
    private static final String MENSAJE_UBICACION_NO_EXISTE = "La ubicación con el id = {0} no existe";

    // Inyeccion de dependencias -> Repositorio Ubicacion
    @Autowired
    private UbicacionRepository ubicacionRepository;

    // creación de ubicaciones
    @Transactional
    public UbicacionEntity createUbicacion(UbicacionEntity ubicacion)
            throws IllegalOperationException {
        log.info("Creando una ubicacion nueva");
        // revisa que la latitud y longitud no esten vacias
        if (ubicacion.getLatitud() == null || ubicacion.getLongitud() == null) {
            throw new IllegalOperationException("La latitud y longitud no pueden ser nulas");
        }
        // revisa que la latitud y longitud sean validas
        if (ubicacion.getLatitud() < -90 || ubicacion.getLatitud() > 90) {
            throw new IllegalOperationException("La latitud debe estar entre -90 y 90");
        }
        // revisa que la latitud y longitud sean validas
        if (ubicacion.getLongitud() < -180 || ubicacion.getLongitud() > 180) {
            throw new IllegalOperationException("La longitud debe estar entre -180 y 180");
        }
        // revisa que la ciudad no este vacia
        if (ubicacion.getCiudad() == null) {
            throw new IllegalOperationException("La ciudad no puede ser nula");
        }
        // revisa que la cuidad no sea " "
        if (ubicacion.getCiudad().equals(" ")) {
            throw new IllegalOperationException("La ciudad no puede ser vacia");
        }
        // revisa que la direccion no este vacia
        if (ubicacion.getDireccion() == null) {
            throw new IllegalOperationException("La direccion no puede ser nula");
        }
        // revisa que la direccion no sea " "
        if (ubicacion.getDireccion().equals(" ")) {
            throw new IllegalOperationException("La direccion no puede ser vacia");
        }
        // revisa que la sede no este vacia
        if (ubicacion.getSede() == null) {
            throw new IllegalOperationException("La sede no puede ser nula");
        }
        return ubicacionRepository.save(ubicacion);
    }

    // obtener todas las ubicaciones
    @Transactional
    public List<UbicacionEntity> getUbicaciones() {
        log.info("Consultando todas las ubicaciones");
        return ubicacionRepository.findAll();
    }

    // obtener una ubicacion por id
    @Transactional
    public UbicacionEntity getUbicacion(Long id) throws EntityNotFoundException {
        log.info("Consultando la ubicacion con id = {}", id);
        Optional<UbicacionEntity> ubicacionesBuscadas = ubicacionRepository.findById(id);
        if (ubicacionesBuscadas.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_UBICACION_NO_EXISTE, id));
        }
        log.info("Ubicacion encontrada");
        return ubicacionesBuscadas.get();
    }

    // actualizar una ubicacion, dada un id y una ubicacion
    @Transactional
    public UbicacionEntity updateUbicacion(Long id, UbicacionEntity ubicacion)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Actualizando la ubicacion con id = {}", id);
        Optional<UbicacionEntity> ubicacionesBuscadas = ubicacionRepository.findById(id);
        if (ubicacionesBuscadas.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_UBICACION_NO_EXISTE, id));
        }
        log.info("Ubicacion encontrada");
        // revisa que la latitud y longitud no esten vacias
        if (ubicacion.getLatitud() == null || ubicacion.getLongitud() == null) {
            throw new IllegalOperationException("La latitud y longitud no pueden ser nulas");
        }
        // revisa que la latitud y longitud sean validas
        if (ubicacion.getLatitud() < -90 || ubicacion.getLatitud() > 90) {
            throw new IllegalOperationException("La latitud debe estar entre -90 y 90");
        }
        // revisa que la latitud y longitud sean validas
        if (ubicacion.getLongitud() < -180 || ubicacion.getLongitud() > 180) {
            throw new IllegalOperationException("La longitud debe estar entre -180 y 180");
        }
        // revisa que la ciudad no este vacia
        if (ubicacion.getCiudad() == null) {
            throw new IllegalOperationException("La ciudad no puede ser nula");
        }
        // revisa que la cuidad no sea " "
        if (ubicacion.getCiudad().equals(" ")) {
            throw new IllegalOperationException("La ciudad no puede ser vacia");
        }
        // revisa que la direccion no este vacia
        if (ubicacion.getDireccion() == null) {
            throw new IllegalOperationException("La direccion no puede ser nula");
        }
        // revisa que la direccion no sea " "
        if (ubicacion.getDireccion().equals(" ")) {
            throw new IllegalOperationException("La direccion no puede ser vacia");
        }
        // revisa que la sede no este vacia
        if (ubicacion.getSede() == null) {
            throw new IllegalOperationException("La sede no puede ser nula");
        }

        ubicacion.setId(id);
        log.info("Actualizando la ubicacion con id = {}", id);
        return ubicacionRepository.save(ubicacion);
    }

    // eliminar una ubicacion
    @Transactional
    public void deleteUbicacion(long id) throws EntityNotFoundException {
        log.info("Eliminando la ubicacion con id = {}", id);
        Optional<UbicacionEntity> ubicacionesBuscadas = ubicacionRepository.findById(id);
        if (ubicacionesBuscadas.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_UBICACION_NO_EXISTE, id));
        }
        log.info("Ubicacion encontrada");
        ubicacionRepository.deleteById(id);
    }

}
