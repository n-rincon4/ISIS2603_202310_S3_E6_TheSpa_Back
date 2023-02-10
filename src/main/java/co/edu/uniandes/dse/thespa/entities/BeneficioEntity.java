package co.edu.uniandes.dse.thespa.entities;

import java.io.File;

import javax.persistence.Entity;

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
@Entity
public abstract class BeneficioEntity extends BaseEntity {
    protected String nombre;
    protected String description;
    protected int precio;
    protected File imagen;

}
