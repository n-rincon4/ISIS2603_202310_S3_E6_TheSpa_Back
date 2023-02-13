package co.edu.uniandes.dse.thespa.entities;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

public class HallOfFameEntity extends BaseEntity {
    private Integer ratingPromedio;

    @OneToOne
    private SedeEntity sede;

    @ManyToMany
    private List<TrabajadorEntity> trabajadores;
}