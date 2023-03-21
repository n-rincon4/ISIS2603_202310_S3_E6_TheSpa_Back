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
import co.edu.uniandes.dse.thespa.dto.SedeDTO;
import co.edu.uniandes.dse.thespa.dto.TrabajadorDTO;
import co.edu.uniandes.dse.thespa.services.SedeAndTrabajadorService;
import co.edu.uniandes.dse.thespa.entities.TrabajadorEntity;

@RestController
@RequestMapping("/sedes")
public class SedeAndTrabajadoresController {

    // inyectar el servicio de sedes y trabajadores
    private SedeAndTrabajadorService saTService;

    // inyecta el model mapper
    private ModelMapper modelMapper;

    // metodo para encontrar todos los trabajadores dentro de una sede dado su id
    @GetMapping(value = "{id}/trabajadores")
    @ResponseStatus(code = HttpStatus.OK)
    public List<TrabajadorEntity> findAll(@PathVariable("id") Long id) throws EntityNotFoundException {
        List<TrabajadorEntity> trabajadores = saTService.obtenerTrabajadroes(id);
        return modelMapper.map(trabajadores, new TypeToken<List<SedeDTO>>() {
        }.getType());
    }

    // metodo para agregar un trabajador a una sede dado su id
    @PostMapping(value = "{id}/trabajadores/{idTrabajador}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public TrabajadorDTO create(@PathVariable("id") Long id, @PathVariable("idTrabajador") Long idTrabajador)
            throws IllegalOperationException, EntityNotFoundException {

        TrabajadorEntity trabajador = saTService.addSedeTrabajador(id, idTrabajador);
        return modelMapper.map(trabajador, TrabajadorDTO.class);
    }

    // metodo para eliminar un trabajador de una sede dado su id
    @DeleteMapping(value = "{id}/trabajadores/{idTrabajador}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public TrabajadorDTO delete(@PathVariable("id") Long id, @PathVariable("idTrabajador") Long idTrabajador)
            throws IllegalOperationException, EntityNotFoundException {

        TrabajadorEntity trabajadorEliminado = saTService.deleteSedeTrabajador(id, idTrabajador);
        return modelMapper.map(trabajadorEliminado, TrabajadorDTO.class);
    }

}
