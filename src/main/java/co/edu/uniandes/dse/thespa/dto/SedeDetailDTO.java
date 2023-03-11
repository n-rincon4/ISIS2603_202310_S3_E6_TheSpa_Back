package co.edu.uniandes.dse.thespa.dto;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SedeDetailDTO extends SedeDTO{
    private List<ServicioExtraDTO> ServiciosExtra = new ArrayList<>();
    private List<PackDeServiciosDTO> PackServicios = new ArrayList<>();
    private List<ArticuloDeRopaDTO> Articulos = new ArrayList<>();
    private List<TrabajadorDTO> Trabajadores = new ArrayList<>();
    private List<ServicioDTO> Servicios = new ArrayList<>();

}
