package co.edu.uniandes.dse.thespa.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.thespa.entities.PackDeServiciosEntity;
import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.entities.ServicioEntity;
import co.edu.uniandes.dse.thespa.entities.TrabajadorEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.repositories.PackDeServiciosRepository;
import co.edu.uniandes.dse.thespa.repositories.SedeRepository;
import co.edu.uniandes.dse.thespa.repositories.ServicioRepository;
import co.edu.uniandes.dse.thespa.repositories.TrabajadorRepository;
import lombok.extern.slf4j.Slf4j;
//Author -> @Juan Coronel

@Slf4j
@Service
public class SedeService {

    // Inyeccion de dependencias -> Repositorio Sede
    @Autowired
    SedeRepository sedeRepo;

    // Inyeccion de dependencias -> Repositorio Servicio
    @Autowired
    ServicioRepository servicioRepo;

    // Inyeccion de dependencias -> Repositorio Trabajadores
    @Autowired
    TrabajadorRepository trabajadoresRepo;

    // Inyeccion de dependencias -> Repositorio Pack de Servicios
    @Autowired
    PackDeServiciosRepository PackDeServiciosRepo;

    // Crear Sede
    @Transactional
    public SedeEntity createSede(SedeEntity sede) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de creacion de Sede.");

        // Assert 1: el nombre no debe ser null
        String nombreSede = sede.getNombre();
        if (nombreSede == null) {
            throw new IllegalOperationException("La sede tiene que tener un nombre.");
        }

        // Assert 2: el nombre debe ser unico
        // Assert 3: la sede no debe de existir en la base de datos
        List<SedeEntity> allSedes = getSedes();
        for (SedeEntity sed : allSedes) {
            if (sed.getNombre().equals(sede.getNombre())) {
                throw new IllegalOperationException("El nombre de la sede debe ser unico.");
            } else if (sed.getId().equals(sede.getId())) {
                throw new IllegalOperationException("La sede ya existe en la base de datos.");
            }

        }
        // Assert 4: el nombre no debe ser un string vacio
        if (nombreSede.equals("")) {
            throw new IllegalOperationException("La sede tiene que tener un nombre no vacio.");
        }

        // Assert 5: la lista de servicios no debe ser vacia
        if (sede.getServicios().isEmpty() == true) {
            throw new IllegalOperationException("La sede tiene que tener al menos un servicio.");
        }

        // Assert 6: Debe haber al menos un trabajador
        if (sede.getTrabajadores().isEmpty() == true) {
            throw new IllegalOperationException("La sede tiene que tener al menos un trabajador.");
        }

        // Assert 7: debe tener una ubicacion la sede
        if (sede.getUbicacion().equals(null)) {
            throw new IllegalOperationException("La sede tiene que tener una ubicacion.");
        }

        // Assert 8: debe tener un Pack de servicios
        if (sede.getPacksDeServicios().isEmpty() == true) {
            throw new IllegalOperationException("La sede tiene que tener al menos unpack de servicios");
        }

        log.info("Finaliza proceso de creacion de Sede.");
        return sedeRepo.save(sede);

    }

    // Obtener todas las sedes
    @Transactional
    public List<SedeEntity> getSedes() {
        log.info("Inicia proceso de encontrar todas las sedes");
        return sedeRepo.findAll();
    }

    // Obtener una sede segun su Id
    @Transactional
    public SedeEntity getSede(Long sedeId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar la Sede con id = {0}", sedeId);
        Optional<SedeEntity> sedeEntity = sedeRepo.findById(sedeId);
        if (sedeEntity.isEmpty())
            throw new EntityNotFoundException("SEDE_NOT_FOUND");
        log.info("Termina proceso de consultar la Sede con id = {0}", sedeId);
        return sedeEntity.get();
    }

    // Actualizar una sede
    @Transactional
    public SedeEntity updateSede(Long SedeId, SedeEntity sede)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de actualizar la Sede con id = {0}", SedeId);
        Optional<SedeEntity> SedeEntity = sedeRepo.findById(SedeId);
        if (SedeEntity.isEmpty())
            throw new EntityNotFoundException("SEDE_NOT_FOUND");

        sede.setId(SedeId);
        log.info("Termina proceso de actualizar la Sede con id = {0}", SedeId);
        return sedeRepo.save(sede);
    }

    // Eliminar una Sede
    @Transactional
    public void deleteSede(Long SedeId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de borrar la sede con id = {0}", SedeId);
        Optional<SedeEntity> sedeEntity = sedeRepo.findById(SedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException("SEDE_NOT_FOUND");
        }

        sedeRepo.deleteById(SedeId);
        log.info("Termina proceso de borrar la sede con id = {0}", SedeId);
    }

    // Añadir un servicio a la sede
    @Transactional
    public ServicioEntity addSedeServicio(Long SedeId, Long ServicioId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de añadir a la sede un servicio con con id = {0}", ServicioId);
        Optional<ServicioEntity> servEntity = servicioRepo.findById(ServicioId);
        if (servEntity.isEmpty()) {
            throw new EntityNotFoundException("SERVICE_NOT_FOUND");
        }

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(SedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException("SEDE_NOT_FOUND");
        }

        // revisa si el servicio ya esta en la sede, si esta lanza una
        // IllegalOperationException
        if (sedeEntity.get().getServicios().contains(servEntity.get())) {
            throw new IllegalOperationException("SERVICE_ALREADY_EXISTS");
        }

        List<ServicioEntity> servicios = sedeEntity.get().getServicios();
        servicios.add(servEntity.get());

        sedeEntity.get().setServicios(servicios);

        log.info("Termina proceso de añadir a la sede un servicio con con id = {0}", SedeId);

        return servEntity.get();
    }

    // Eliminar un servicio a la sede
    @Transactional
    public ServicioEntity deleteSedeServicio(Long SedeId, Long ServicioId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de eliminar de la sede un servicio con con id = {0}", ServicioId);
        Optional<ServicioEntity> servEntity = servicioRepo.findById(ServicioId);
        if (servEntity.isEmpty()) {
            throw new EntityNotFoundException("SERVICE_NOT_FOUND");
        }

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(SedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException("SEDE_NOT_FOUND");
        }

        List<ServicioEntity> servicios = sedeEntity.get().getServicios();

        // revisa si el servicio no esta en la sede, si no esta lanza una
        // IllegalOperationException
        if (servicios.contains(servEntity.get()) == false) {
            throw new IllegalOperationException("SERVICIO_NOT_FOUND_IN_CURRENT_SEDE");
        }
        servicios.remove(servEntity.get());

        sedeEntity.get().setServicios(servicios);

        log.info("Termina proceso de elimnar de la sede un servicio con con id = {0}", SedeId);

        return servEntity.get();
    }

    // Añadir un trabajador a la sede
    @Transactional
    public TrabajadorEntity addSedeTrabajador(Long SedeId, Long TrabajadorId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de añadir a la sede un Trabajador con con id = {0}", TrabajadorId);
        Optional<TrabajadorEntity> trabEntity = trabajadoresRepo.findById(TrabajadorId);
        if (trabEntity.isEmpty()) {
            throw new EntityNotFoundException("TRABAJADOR_NOT_FOUND");
        }

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(SedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException("SEDE_NOT_FOUND");
        }

        // revisa si el trabajador ya esta en la sede, si esta lanza una
        // IllegalOperationException
        if (sedeEntity.get().getTrabajadores().contains(trabEntity.get())) {
            throw new IllegalOperationException("TRABAJADOR_ALREADY_EXISTS");
        }

        List<TrabajadorEntity> trabajs = sedeEntity.get().getTrabajadores();
        trabajs.add(trabEntity.get());

        sedeEntity.get().setTrabajadores(trabajs);

        log.info("Termina proceso de añadir a la sede un Trabajador con con id = {0}", SedeId);

        return trabEntity.get();
    }

    // Eliminar un trabajador de la sede
    @Transactional
    public TrabajadorEntity deleteSedeTrabajador(Long SedeId, Long TrabajadorId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de eliminar de la sede un Trabajador con con id = {0}", TrabajadorId);
        Optional<TrabajadorEntity> trabEntity = trabajadoresRepo.findById(TrabajadorId);
        if (trabEntity.isEmpty()) {
            throw new EntityNotFoundException("TRABAJADOR_NOT_FOUND");
        }

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(SedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException("SEDE_NOT_FOUND");
        }

        // revisa si el trabajador no esta en la sede, si no esta lanza una
        // IllegalOperationException
        if (sedeEntity.get().getTrabajadores().contains(trabEntity.get()) == false) {
            throw new IllegalOperationException("TRABAJADOR_NOT_FOUND_IN_CURRENT_SEDE");
        }

        List<TrabajadorEntity> trabajs = sedeEntity.get().getTrabajadores();
        trabajs.remove(trabEntity.get());

        sedeEntity.get().setTrabajadores(trabajs);

        log.info("Termina proceso de añadir a la sede un Trabajador con con id = {0}", SedeId);

        return trabEntity.get();
    }

    // Añadir un Pack de servicios a la sede
    @Transactional
    public PackDeServiciosEntity addSedePackDeServicios(Long SedeId, Long PackDeServiciosId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de añadir a la sede un PackDeServicios con con id = {0}", PackDeServiciosId);
        Optional<PackDeServiciosEntity> packEntity = PackDeServiciosRepo.findById(PackDeServiciosId);
        if (packEntity.isEmpty()) {
            throw new EntityNotFoundException("PACK_NOT_FOUND");
        }

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(SedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException("SEDE_NOT_FOUND");
        }

        // revisa si el pack ya esta en la sede, si esta lanza una
        // IllegalOperationException
        if (sedeEntity.get().getPacksDeServicios().contains(packEntity.get())) {
            throw new IllegalOperationException("PACK_ALREADY_EXISTS");
        }

        List<PackDeServiciosEntity> PackDeServicios = sedeEntity.get().getPacksDeServicios();
        PackDeServicios.add(packEntity.get());

        sedeEntity.get().setPacksDeServicios(PackDeServicios);

        log.info("Termina proceso de añadir a la sede un Trabajador con con id = {0}", SedeId);

        return packEntity.get();
    }

    // Eliminar un pack de servicios de la sede
    @Transactional
    public PackDeServiciosEntity deleteSedePackDeServicios(Long SedeId, Long PackDeServiciosId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de añadir a la sede un PackDeServicios con con id = {0}", PackDeServiciosId);
        Optional<PackDeServiciosEntity> packEntity = PackDeServiciosRepo.findById(PackDeServiciosId);
        if (packEntity.isEmpty()) {
            throw new EntityNotFoundException("PACK_NOT_FOUND");
        }

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(SedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException("SEDE_NOT_FOUND");
        }

        // revisa si el pack no esta en la sede, si no esta lanza una
        // IllegalOperationException
        if (sedeEntity.get().getPacksDeServicios().contains(packEntity.get()) == false) {
            throw new IllegalOperationException("PACK_NOT_FOUND_IN_CURRENT_SEDE");
        }

        List<PackDeServiciosEntity> PackDeServicios = sedeEntity.get().getPacksDeServicios();
        PackDeServicios.remove(packEntity.get());

        sedeEntity.get().setPacksDeServicios(PackDeServicios);

        log.info("Termina proceso de eliminar de la sede un PackDeServicios con con id = {0}", SedeId);

        return packEntity.get();
    }

}
