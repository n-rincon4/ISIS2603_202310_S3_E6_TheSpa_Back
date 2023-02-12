package co.edu.uniandes.dse.thespa.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ServicioEntity extends BaseEntity{
    private Integer duracion;
    private String restricciones;
    private Boolean disponible;
    private Integer horaInicio;
    private Integer horaFin;
    
    @ManyToMany
    private List<PackDeServiciosEntity> packsDeServicios;

}