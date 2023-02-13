package co.edu.uniandes.dse.thespa.entities;

import java.io.File;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

// import javax.persistence.OneToMany;
// Falta la entidad articulo de ropa

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa la entidad de sede
 * 
 * @author ISIS2603 - Juan Coronel
 */

@Getter
@Setter
@Entity
public class SedeEntity extends BaseEntity {
    private String nombre;
    private File imagen;

    @PodamExclude
    @ManyToMany(mappedBy = "sedes")
    private List<TrabajadorEntity> trabajadores;

    @PodamExclude
    @OneToMany(mappedBy = "sede", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ArticuloDeRopaEntity> articulosDeRopa;

    @PodamExclude
    @OneToOne
    private HallOfFameEntity hof;

    @PodamExclude
    @OneToOne
    private UbicacionEntity ubicacion;

    @PodamExclude
    @OneToMany(mappedBy = "sede", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PackDeServiciosEntity> packsDeServicios;

    @PodamExclude
    @OneToMany(mappedBy = "sede", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ServicioEntity> servicios;

    @PodamExclude
    @OneToMany(mappedBy = "sede", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ServicioExtraEntity> serviciosExtra;

}
