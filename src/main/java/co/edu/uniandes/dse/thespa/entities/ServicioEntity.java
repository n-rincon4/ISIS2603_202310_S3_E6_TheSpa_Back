package co.edu.uniandes.dse.thespa.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ServicioEntity extends BeneficioEntity {
    private int duracion;
    private String restricciones;
    private Boolean disponible;
    private int horaInicio;
    private int horaFin;

    @ManyToMany
    private List<PackDeServiciosEntity> packsDeServicios;
}