package co.edu.uniandes.dse.thespa.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ArticuloDeRopaEntity extends BeneficioEntity {

    private Integer talla;
    private String color;
    private Integer numDisponible;

    @ManyToOne
    private SedeEntity sede;

}
