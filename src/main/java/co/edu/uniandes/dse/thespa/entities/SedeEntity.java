package co.edu.uniandes.dse.thespa.entities;

import java.io.File;
import java.util.ArrayList;

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
    @ManyToMany(mappedBy = "Sede")
    private List<TrabajadorEntity> trabajadores = new ArrayList<>();

    @PodamExclude
    @ManyToMany(mappedBy = "Sede")
    private List<TrabajadorEntity> articulosDeRopa = new ArrayList<>();

    @PodamExclude
    @OneToOne
    private HallOfFameEntity hof = new HallOfFameEntity();

    /**
     * Hace falta la entidad ubicacion para ser creada
     * 
     * @PodamExclude
     * @OneToOne
     *           UbicacionEntity ubicacion = new UbicacionEntity();
     */

    @PodamExclude
    @OneToMany(mappedBy = "Sede", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PackDeServiciosEntity> packServicios = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "Sede", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ServicioEntity> servicios = new ArrayList<>();

    // @PodamExclude
    // @OneToMany(mappedBy = "Sede", cascade = CascadeType.PERSIST, orphanRemoval =
    // true)
    // private List<ArticuloDeRopaEntity> ropa = new ArrayList<>();
    // Falta la entidad articulo de ropa

    @PodamExclude
    @OneToMany(mappedBy = "Sede", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ServicioExtraEntity> serviciosExtra = new ArrayList<>();

}
