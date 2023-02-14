package co.edu.uniandes.dse.thespa.entities;

import java.io.File;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Getter
@Setter
@Entity

public class PackDeServiciosEntity extends BaseEntity {
    private Integer descuento;
    private String nombre;
    private File image;

    @PodamExclude
    @ManyToOne
    private SedeEntity sede;

    @PodamExclude
    @ManyToMany(mappedBy = "packsDeServicios")
    private List<ServicioEntity> servicios;

}
