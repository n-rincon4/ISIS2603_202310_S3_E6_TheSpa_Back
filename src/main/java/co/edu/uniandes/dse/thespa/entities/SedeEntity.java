package co.edu.uniandes.dse.thespa.entities;

import java.io.File;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

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

    @OneToMany(mappedBy = "sede", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PackDeServiciosEntity> packsDeServicios;
}
