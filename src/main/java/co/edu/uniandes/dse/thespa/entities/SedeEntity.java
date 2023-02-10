package co.edu.uniandes.dse.thespa.entities;

import java.io.File;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa la entidad de sede
 * 
 * @author ISIS2603
 */

@Getter
@Setter
@Entity
public class SedeEntity extends BaseEntity {
    private String nombre;
    private File imagen;
}
