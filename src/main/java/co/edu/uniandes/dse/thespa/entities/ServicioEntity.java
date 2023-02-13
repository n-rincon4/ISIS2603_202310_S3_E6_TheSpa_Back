package co.edu.uniandes.dse.thespa.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import javax.persistence.CascadeType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ServicioEntity extends BeneficioEntity {
    private Integer duracion;
    private String restricciones;
    private Boolean disponible;
    private Integer horaInicio;
    private Integer horaFin;

    @ManyToMany
    private List<PackDeServiciosEntity> packsDeServicios;

    @ManyToOne
    private SedeEntity sede;

    @ManyToMany(mappedBy = "servicios", cascade = CascadeType.PERSIST)
    private List<TrabajadorEntity> trabajadores;
}
