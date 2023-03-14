package co.edu.uniandes.dse.thespa.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;


import java.util.List;

import co.edu.uniandes.dse.thespa.services.ServicioAndTrabajadorService;
import co.edu.uniandes.dse.thespa.entities.TrabajadorEntity;
import co.edu.uniandes.dse.thespa.dto.TrabajadorDTO;

import org.modelmapper.TypeToken;

import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;  
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;

@RestController
@RequestMapping("/servicios")
public class ServicioAndTrabajadorController {


    @Autowired
    private ServicioAndTrabajadorService service;

    @Autowired
    private ModelMapper modelMapper;


    //Encuentra todos los trabajadores de un servicio
    @GetMapping("/{id}/trabajadores")
    @ResponseStatus(code = HttpStatus.OK)
    public List<TrabajadorEntity> findAll(@PathVariable("id") Long id) throws EntityNotFoundException{

        List<TrabajadorEntity> trabajadores = service.getTrabajadores(id);
        return modelMapper.map(trabajadores, new TypeToken<List<TrabajadorEntity>>() {
        }.getType());
    }

    //Agrega un trabajador a un servicio
    @PostMapping("/{id}/trabajadores/{idTrabajador}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public TrabajadorDTO create(@PathVariable("id") Long id, @PathVariable("idTrabajador") Long idTrabajador) throws EntityNotFoundException, IllegalOperationException {
        TrabajadorEntity trabajador = service.addTrabajador(id, idTrabajador);
        return modelMapper.map(trabajador, TrabajadorDTO.class);
    }

    //Elimina un traajador de un servicio
    @DeleteMapping(value = "/{id}/trabajadores/{idTrabajador}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public TrabajadorDTO delete(@PathVariable("id") Long id, @PathVariable("idTrabajador") Long idTrabajador) throws EntityNotFoundException, IllegalOperationException {
        TrabajadorEntity trabajadorEliminado = service.removeTrabajador(id, idTrabajador);
        return modelMapper.map(trabajadorEliminado,TrabajadorDTO.class);
    }
}