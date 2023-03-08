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
import co.edu.uniandes.dse.thespa.dto.ServicioDTO;

import co.edu.uniandes.dse.thespa.services.PackDeServiciosAndServicioService;
import co.edu.uniandes.dse.thespa.entities.ServicioEntity;

@RestController
@RequestMapping("/packs")
public class PackDeServiciosAndServiciosController {

    // inyectar el servicio de packs de servicios
    private PackDeServiciosAndServicioService packDeServiciosAndServicioService;

    // inyecta el model mapper
    private ModelMapper modelMapper;

    // metodo para encontrar todos los servicios dentro de un pack de servicios dado
    // su id
    @GetMapping(value = "{id}/servicios")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ServicioEntity> findAll(@PathVariable("id") Long id) throws EntityNotFoundException {
        List<ServicioEntity> packs = packDeServiciosAndServicioService.getServicios(id);
        return modelMapper.map(packs, new TypeToken<List<PackDeServiciosDTO>>() {
        }.getType());
    }

    // metodo para agregar un servicio a un pack de servicios dado su id
    @PostMapping(value = "{id}/servicios/{idServicio}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ServicioDTO create(@PathVariable("id") Long id, @PathVariable("idServicio") Long idServicio)
            throws IllegalOperationException, EntityNotFoundException {

        ServicioEntity servicio = packDeServiciosAndServicioService.addServicio(id, idServicio);
        return modelMapper.map(servicio, ServicioDTO.class);
    }

    // metodo para eliminar un servicio de un pack de servicios dado su id
    @DeleteMapping(value = "{id}/servicios/{idServicio}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ServicioDTO delete(@PathVariable("id") Long id, @PathVariable("idServicio") Long idServicio)
            throws IllegalOperationException, EntityNotFoundException {

        ServicioEntity servicioEliminado = packDeServiciosAndServicioService.removeServicio(id, idServicio);
        return modelMapper.map(servicioEliminado, ServicioDTO.class);
    }

}
