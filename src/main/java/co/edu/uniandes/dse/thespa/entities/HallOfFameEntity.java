package co.edu.uniandes.dse.thespa.entities;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Getter
@Setter
@Entity

public class HallOfFameEntity extends BaseEntity {
    private Integer ratingPromedio;

    @PodamExclude
    @OneToOne
    private SedeEntity sede;

    @PodamExclude
    @ManyToMany
    private List<TrabajadorEntity> trabajadores;
}