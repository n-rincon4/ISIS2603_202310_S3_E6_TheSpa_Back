package co.edu.uniandes.dse.thespa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.uniandes.dse.thespa.entities.HallOfFameEntity;

public interface HallOfFameRepository extends JpaRepository<HallOfFameEntity, Long> {
}
