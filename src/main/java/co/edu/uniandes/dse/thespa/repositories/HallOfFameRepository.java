package co.edu.uniandes.dse.thespa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.thespa.entities.HallOfFameEntity;

@Repository
public interface HallOfFameRepository extends JpaRepository<HallOfFameEntity, Long> {
}
