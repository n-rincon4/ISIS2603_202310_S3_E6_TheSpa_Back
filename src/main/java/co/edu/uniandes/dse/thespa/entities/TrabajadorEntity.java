package co.edu.uniandes.dse.thespa.entities;

import java.io.File;
import javax.persistence.ManyToMany;

import java.util.List;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa un trabajador en la persistencia
 *
 * @author Nicolás Rincón Sánchez
 */

@Getter
@Setter
@Entity
public class TrabajadorEntity extends BaseEntity {
    private String nombre;
    private File foto;
    private Integer calificacion;
    private Boolean enHallOfFame;

    @PodamExclude
    @ManyToMany
    private List<SedeEntity> sedes;

    @PodamExclude
    @ManyToMany
    private List<ServicioEntity> servicios;

}
