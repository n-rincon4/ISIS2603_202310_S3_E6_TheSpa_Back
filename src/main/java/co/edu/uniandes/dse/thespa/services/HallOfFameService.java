package co.edu.uniandes.dse.thespa.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import co.edu.uniandes.dse.thespa.entities.HallOfFameEntity;
import co.edu.uniandes.dse.thespa.repositories.HallOfFameRepository;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service

public class HallOfFameService {
    @Autowired
    HallOfFameRepository hallOfFameRepository;

    @Transactional
    public HallOfFameEntity createHallOfFame(HallOfFameEntity hallOfFame) {
        log.info("Creando un hall of fame nuevo");
        return hallOfFameRepository.save(hallOfFame);
    }

    @Transactional
    public HallOfFameEntity getHallOfFame(Long id) throws EntityNotFoundException {
        log.info("Consultando el hall of fame con id = {}", id);
        Optional<HallOfFameEntity> hallOfFameEntity = hallOfFameRepository.findById(id);
        if (hallOfFameEntity.isEmpty()) {
            throw new EntityNotFoundException("El hall of fame con el id = " + id + " no existe");
        }
        log.info("Hall of fame encontrado");
        return hallOfFameEntity.get();
    }

    @Transactional
    public HallOfFameEntity updateHallOfFame(Long id, HallOfFameEntity hallOfFame) throws EntityNotFoundException {
        log.info("Actualizando el hall of fame con id = {}", id);
        Optional<HallOfFameEntity> hallOfFameEntity = hallOfFameRepository.findById(id);
        if (hallOfFameEntity.isEmpty()) {
            throw new EntityNotFoundException("El hall of fame con el id = " + id + " no existe");
        }
        hallOfFame.setId(id);
        log.info("Termina la actualizacion del hall of fame con id = {}", id);
        return hallOfFameRepository.save(hallOfFame);
    }

    @Transactional
    public void deleteHallOfFame(Long hofId) throws EntityNotFoundException {
        log.info("Borrando el hall of fame con id = {}", hofId);
        Optional<HallOfFameEntity> hallOfFameEntity = hallOfFameRepository.findById(hofId);
        if (hallOfFameEntity.isEmpty()) {
            throw new EntityNotFoundException("El hall of fame con el id = " + hofId + " no existe");
        }
        hallOfFameRepository.deleteById(hofId);
        log.info("Termina la eliminacion del hall of fame con id = {}", hofId);
    }
}