package co.edu.uniandes.dse.thespa.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import co.edu.uniandes.dse.thespa.entities.PackDeServiciosEntity;
import co.edu.uniandes.dse.thespa.entities.ServicioEntity;

import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.repositories.PackDeServiciosRepository;
import co.edu.uniandes.dse.thespa.repositories.ServicioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PackDeServiciosService {

    // Inyeccion de dependencias -> Repositorio PackDeServicios
    @Autowired
    private PackDeServiciosRepository packDeServiciosRepository;

    // Inyeccion de dependencias -> Servicio Servicio
    @Autowired
    private ServicioRepository servicioRepository;

    // creación de packs de servicios
    @Transactional
    public PackDeServiciosEntity createPackDeServicios(PackDeServiciosEntity packDeServicios)
            throws IllegalOperationException {
        log.info("Creando un pack de servicios nuevo");
        // revisa que el nombre del pack de servicios no sea null
        if (packDeServicios.getNombre() == null) {
            throw new IllegalOperationException("El nombre del pack de servicios no puede ser null");
        }
        // revisa que la sede del pack de servicios no sea null
        if (packDeServicios.getSede() == null) {
            throw new IllegalOperationException("La sede del pack de servicios no puede ser null");
        }
        // revia que los servicios del pack de servicios no sean null
        if (packDeServicios.getServicios() == null) {
            throw new IllegalOperationException("Los servicios del pack de servicios no pueden ser null");
        }
        return packDeServiciosRepository.save(packDeServicios);
    }

    // obtener todos los packs de servicios
    @Transactional
    public List<PackDeServiciosEntity> getPacksDeServicios() {
        log.info("Consultando todos los packs de servicios");
        return packDeServiciosRepository.findAll();
    }

    // obtener un pack de servicios
    @Transactional
    public PackDeServiciosEntity getPackDeServicios(Long id) throws EntityNotFoundException {
        log.info("Consultando el pack de servicios con id = {}", id);
        Optional<PackDeServiciosEntity> PacksBuscados = packDeServiciosRepository.findById(id);
        if (PacksBuscados.isEmpty()) {
            throw new EntityNotFoundException("El pack de servicios con el id = " + id + " no existe");
        }
        log.info("Pack de servicios encontrado");
        return PacksBuscados.get();

    }

    // actualizar un pack de servicios
    @Transactional
    public PackDeServiciosEntity updatePackDeServicios(Long id, PackDeServiciosEntity packDeServicios)
            throws EntityNotFoundException {

        log.info("Actualizando el pack de servicios con id = {}", id);
        Optional<PackDeServiciosEntity> PacksBuscados = packDeServiciosRepository.findById(id);
        if (PacksBuscados.isEmpty()) {
            throw new EntityNotFoundException("El pack de servicios con el id = " + id + " no existe");
        }

        packDeServicios.setId(id);
        log.info("Pack de servicios actualizado");
        return packDeServiciosRepository.save(packDeServicios);

    }

    // borrar un pack de servicios
    @Transactional
    public void deletePackDeServicios(Long id) throws EntityNotFoundException {
        log.info("Borrando el pack de servicios con id = {}", id);
        Optional<PackDeServiciosEntity> PacksBuscados = packDeServiciosRepository.findById(id);
        if (PacksBuscados.isEmpty()) {
            throw new EntityNotFoundException("El pack de servicios con el id = " + id + " no existe");
        }
        packDeServiciosRepository.deleteById(id);
        log.info("Pack de servicios borrado");
    }

    // Obtiene todos los servicios de un pack de servicios
    @Transactional
    public List<ServicioEntity> getServicios(Long id) throws EntityNotFoundException {
        log.info("Consultando los servicios del pack de servicios con id = {}", id);
        Optional<PackDeServiciosEntity> PacksBuscados = packDeServiciosRepository.findById(id);
        if (PacksBuscados.isEmpty()) {
            throw new EntityNotFoundException("El pack de servicios con el id = " + id + " no existe");
        }
        log.info("Servicios del pack de servicios encontrados");
        return PacksBuscados.get().getServicios();
    }

    // Agrega un servicio a un pack de servicios
    @Transactional
    public ServicioEntity addServicio(Long packid, Long servicioID)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Agregando el servicio con id = {} al pack de servicios con id = {}", servicioID, packid);

        // Busca el pack de servicios
        Optional<PackDeServiciosEntity> PacksBuscados = packDeServiciosRepository.findById(packid);
        if (PacksBuscados.isEmpty()) {
            throw new EntityNotFoundException("El pack de servicios con el id = " + packid + " no existe");
        }

        // Busca el servicio
        Optional<ServicioEntity> ServiciosBuscados = servicioRepository.findById(servicioID);
        if (ServiciosBuscados.isEmpty()) {
            throw new EntityNotFoundException("El servicio con el id = " + servicioID + " no existe");
        }

        // Verifica que el servicio no este ya en el pack de servicios
        if (PacksBuscados.get().getServicios().contains(ServiciosBuscados.get())) {
            throw new IllegalOperationException("El servicio con el id = " + servicioID
                    + " ya se encuentra en el pack de servicios con el id = " + packid);
        }

        // Agrega el servicio al pack de servicios
        PacksBuscados.get().getServicios().add(ServiciosBuscados.get());

        log.info("Servicio agregado al pack de servicios");

        return ServiciosBuscados.get();

    }

    // Elimina un servicio de un pack de servicios
    @Transactional
    public ServicioEntity removeServicio(Long packid, Long servicioID)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("eliminando el servicio con id = {} del pack de servicios con id = {}", servicioID, packid);

        // Busca el pack de servicios
        Optional<PackDeServiciosEntity> PacksBuscados = packDeServiciosRepository.findById(packid);
        if (PacksBuscados.isEmpty()) {
            throw new EntityNotFoundException("El pack de servicios con el id = " + packid + " no existe");
        }

        // Busca el servicio
        Optional<ServicioEntity> ServiciosBuscados = servicioRepository.findById(servicioID);
        if (ServiciosBuscados.isEmpty()) {
            throw new EntityNotFoundException("El servicio con el id = " + servicioID + " no existe");
        }

        // Verifica que el servicio este en el pack de servicios
        if (!PacksBuscados.get().getServicios().contains(ServiciosBuscados.get())) {
            throw new IllegalOperationException("El servicio con el id = " + servicioID
                    + " no esta en el pack de servicios con el id = " + packid);
        }

        // Elimina el servicio del pack de servicios
        PacksBuscados.get().getServicios().remove(ServiciosBuscados.get());

        log.info("Servicio eliminado del pack de servicios");

        return ServiciosBuscados.get();
    }

}
