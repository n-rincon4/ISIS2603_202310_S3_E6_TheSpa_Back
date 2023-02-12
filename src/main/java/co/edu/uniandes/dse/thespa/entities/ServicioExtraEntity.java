package co.edu.uniandes.dse.thespa.entities;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ManyToOne;

/**
 * Clase que representa un trabajador en la persistencia
 *
 * @author Nicolás Rincón Sánchez
 */

@Getter
@Setter
@Entity
public class ServicioExtraEntity extends BeneficioEntity {
    private Boolean disponible;

    @ManyToOne
    private SedeEntity sede;

}
