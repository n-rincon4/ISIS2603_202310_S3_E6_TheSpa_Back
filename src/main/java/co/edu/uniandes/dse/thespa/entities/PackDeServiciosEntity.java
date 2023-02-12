package co.edu.uniandes.dse.thespa.entities;

import java.io.File;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

public class PackDeServiciosEntity extends BaseEntity {
    private Integer descuento;
    private String nombre;
    private File image;

}
