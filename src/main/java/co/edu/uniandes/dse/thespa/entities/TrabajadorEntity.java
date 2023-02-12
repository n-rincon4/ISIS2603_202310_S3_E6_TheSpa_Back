package co.edu.uniandes.dse.thespa.entities;

import java.io.File;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa un trabajador en la persistencia
 *
 * @author Nicolás Rincón Sánchez
 */

@Getter
@Setter
@Entity
public class TrabajadorEntity extends BaseEntity
{
    private String nombre;
    private File foto;
    private Integer calificacion; 
}
