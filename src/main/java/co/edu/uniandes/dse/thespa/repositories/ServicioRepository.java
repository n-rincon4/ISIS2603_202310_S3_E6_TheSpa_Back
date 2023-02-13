package co.edu.uniandes.dse.thespa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.thespa.entities.ServicioEntity;

@Repository
public interface ServicioRepository extends JpaRepository<ServicioEntity, Long> {
}
