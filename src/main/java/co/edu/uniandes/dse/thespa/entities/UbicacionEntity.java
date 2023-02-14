package co.edu.uniandes.dse.thespa.entities;

import javax.persistence.Entity;

import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que contiene las coordenadas geográficas
 *
 * @author Luisa Hernández
 */

@Getter
@Setter
@Entity
public class UbicacionEntity extends BaseEntity {

    private Integer latitud;
    private Integer longitud;
    private String Ciudad;
    private String Direccion;

    @PodamExclude
    @OneToOne
    private SedeEntity sede;

}