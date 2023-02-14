package co.edu.uniandes.dse.thespa.entities;

import java.io.File;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa un beneficio en la persistencia y permite su
 * serialización.
 * 
 * @author ISIS2603
 */

@Getter
@Setter
@MappedSuperclass
public abstract class BeneficioEntity extends BaseEntity {

    protected String nombre;
    protected String description;
    protected Integer precio;
    protected File imagen;

}
