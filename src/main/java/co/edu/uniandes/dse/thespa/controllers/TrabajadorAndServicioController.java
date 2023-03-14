package co.edu.uniandes.dse.thespa.controllers;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;

import co.edu.uniandes.dse.thespa.services.TrabajadorAndServicioService;
import co.edu.uniandes.dse.thespa.dto.ServicioDTO;
import co.edu.uniandes.dse.thespa.dto.ServicioDetailDTO;
import co.edu.uniandes.dse.thespa.entities.ServicioEntity;

@RestController
@RequestMapping("/trabajadores")
public class TrabajadorAndServicioController {

	@Autowired
	private TrabajadorAndServicioService trabajadorServicioService;

	@Autowired
	private ModelMapper modelMapper;

    // Asocia un servicio existente a un trabajador existente
	@PostMapping(value = "/{trabajadorId}/servicios/{servicioId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ServicioDTO addServicioToTrabajador(@PathVariable("servicioId") Long servicioId, @PathVariable("trabajadorId") Long trabajadorId)
			throws EntityNotFoundException, IllegalOperationException {
		ServicioEntity servicioEntity = trabajadorServicioService.addServicioToTrabajador(trabajadorId, servicioId);
		return modelMapper.map(servicioEntity, ServicioDTO.class);
	}


	// Elimina la conexión entre un trabajador y un servicio
	@DeleteMapping(value = "/{trabajadorId}/servicios/{servicioId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteServicioFromTrabajador(@PathVariable("servicioId") Long servicioId, @PathVariable("trabajadorId") Long trabajadorId)
			throws EntityNotFoundException, IllegalOperationException {
		trabajadorServicioService.deleteServicioTrabajador(trabajadorId, servicioId);
	}
    
}
