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
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.services.ArticuloDeRopaService;
import co.edu.uniandes.dse.thespa.dto.ArticuloDeRopaDTO;
import co.edu.uniandes.dse.thespa.entities.ArticuloDeRopaEntity;

@RestController
@RequestMapping("/articulosDeRopa")
public class ArticuloDeRopaController {

    // inyectar el servicio de articulos de ropa
    private ArticuloDeRopaService articuloDeRopaService;

    // inyecta el model mapper
    private ModelMapper modelMapper;

    // metodo para encontrar todos los articulos de ropa
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ArticuloDeRopaDTO> findAll() {
        List<ArticuloDeRopaEntity> articulos = articuloDeRopaService.getArticulosDeRopa();
        return modelMapper.map(articulos, new TypeToken<List<ArticuloDeRopaDTO>>() {
        }.getType());
    }

    // metodo para encontrar un articulo de ropa dado su id
    @GetMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ArticuloDeRopaDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        ArticuloDeRopaEntity articulo = articuloDeRopaService.getArticuloDeRopa(id);
        return modelMapper.map(articulo, ArticuloDeRopaDTO.class);
    }

    // metodo para crear una entidad de un articulo de ropa dado un DTO, retorna el
    // DTO de la entidad creada
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ArticuloDeRopaDTO create(@RequestBody ArticuloDeRopaDTO articuloDTO)
            throws IllegalOperationException, EntityNotFoundException {

        ArticuloDeRopaEntity articulo = articuloDeRopaService
                .createArticuloDeRopa(modelMapper.map(articuloDTO, ArticuloDeRopaEntity.class));
        return modelMapper.map(articulo, ArticuloDeRopaDTO.class);
    }

    // metodo para eliminar un articulo de ropa dado su id
    @DeleteMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException {
        articuloDeRopaService.deleteArticuloDeRopa(id);
    }

}
