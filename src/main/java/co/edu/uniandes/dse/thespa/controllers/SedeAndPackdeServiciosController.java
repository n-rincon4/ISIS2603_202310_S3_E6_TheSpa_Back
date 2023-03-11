package co.edu.uniandes.dse.thespa.controllers;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.dto.PackDeServiciosDTO;
import co.edu.uniandes.dse.thespa.services.SedeAndPackServicesService;
import co.edu.uniandes.dse.thespa.entities.PackDeServiciosEntity;



@RestController
@RequestMapping("/sedes")
public class SedeAndPackdeServiciosController {

    // inyectar el servicio de sedes y packs de servicios
    private SedeAndPackServicesService SaP;

    // inyecta el model mapper
    private ModelMapper modelMapper;

    // metodo para encontrar todos los packs de servicios dentro de una sede dado su id
    @GetMapping(value = "{id}/packs")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PackDeServiciosEntity> findAll(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        List<PackDeServiciosEntity> servicios = SaP.obtenerAllPacks(id);
        return modelMapper.map(servicios, new TypeToken<List<PackDeServiciosDTO>>() {
        }.getType());
    }

    // metodo para agregar un pack de servicios a una sede dado su id
    @PostMapping(value = "{id}/packs/{idPack}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PackDeServiciosDTO create(@PathVariable("id") Long id, @PathVariable("idPack") Long idPack)
            throws IllegalOperationException, EntityNotFoundException {

        PackDeServiciosEntity servicio = SaP.addSedePackDeServicios(id, idPack);
        return modelMapper.map(servicio, PackDeServiciosDTO.class);
    }

    // metodo para eliminar un pack servicio de una sede dado su id
    @DeleteMapping(value = "{id}/packs/{idPack}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public PackDeServiciosDTO delete(@PathVariable("id") Long id, @PathVariable("idPack") Long idPack)
            throws IllegalOperationException, EntityNotFoundException {

        PackDeServiciosEntity servicioEliminado = SaP.deleteSedePackDeServicios(id, idPack);
        return modelMapper.map(servicioEliminado, PackDeServiciosDTO.class);
    }

}
