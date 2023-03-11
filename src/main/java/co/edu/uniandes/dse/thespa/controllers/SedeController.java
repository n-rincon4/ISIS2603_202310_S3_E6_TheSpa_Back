package co.edu.uniandes.dse.thespa.controllers;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import co.edu.uniandes.dse.thespa.dto.SedeDTO;
import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.services.SedeService;

@RestController
@RequestMapping("/sedes")
public class SedeController {
    
    // inyectar el servicio de sede
    private SedeService SedeService;

    // inyecta el model mapper
    private ModelMapper modelMapper;

    // metodo para encontrar todas las sedes
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<SedeDTO> findAll() {
        List<SedeEntity> sedes = SedeService.getSedes();
        return modelMapper.map(sedes, new TypeToken<List<SedeDTO>>() {
        }.getType());
    }

    // metodo para encontrar una sede dado su id
    @GetMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public SedeDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        SedeEntity sede = SedeService.getSede(id);
        return modelMapper.map(sede, SedeDTO.class);
    }

    // metodo para crear una entidad de una sede dado un DTO, retorna el
    // DTO de la entidad creada
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public SedeDTO create(@RequestBody SedeDTO SedeDTO)
            throws IllegalOperationException, EntityNotFoundException {

        SedeEntity sede = SedeService
                .createSede(modelMapper.map(SedeDTO, SedeEntity.class));
        return modelMapper.map(sede, SedeDTO.class);
    }

    // metodo para eliminar una sede dado su id
    @DeleteMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        SedeService.deleteSede(id);
    }
}
