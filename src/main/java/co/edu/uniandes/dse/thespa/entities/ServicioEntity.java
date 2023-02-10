package co.edu.uniandes.dse.thespa.entities;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ServicioEntity extends BaseEntity{
    private int duracion;
    private String restricciones;
    private Boolean disponible;
    private int horaInicio;
    private int horaFin;
}