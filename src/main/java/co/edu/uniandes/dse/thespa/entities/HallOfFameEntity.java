package co.edu.uniandes.dse.thespa.entities;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

public class HallOfFameEntity extends BaseEntity{
    private int ratingPromedio;
}