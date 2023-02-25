package co.edu.uniandes.dse.thespa.entities;

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
public abstract class ProductoEntity extends BaseEntity {

    protected String nombre;
    protected String descripcion;
    protected Double precio;
    protected String imagen;

}
