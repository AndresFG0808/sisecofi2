package com.sisecofi.catalogos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sisecofi.catalogos.service.ServicioCatalogo;
import com.sisecofi.catalogos.util.Constantes;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.util.anotaciones.ConsumoInterno;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@Slf4j
public class CatalogoInternoCtrl {

	private final ServicioCatalogo catalogo;

	public CatalogoInternoCtrl(ServicioCatalogo catalogo) {
		super();
		this.catalogo = catalogo;
	}

	@GetMapping(Constantes.PATH_BASE_INTERNO + "/catalogos/consumo-interno/informacion/{idCatalogo}")
	@ConsumoInterno
	public <T extends BaseCatalogoModel> ResponseEntity<List<T>> obtenerInformacionCatalogo(
			@PathVariable("idCatalogo") int idCatalogo) {
		return new ResponseEntity<>(catalogo.obtenerInformacion(idCatalogo,""), HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@GetMapping(Constantes.PATH_BASE_INTERNO + "/catalogos/consumo-interno/informacion/{idCatalogo}/{id}")
	@ConsumoInterno
	public <T extends BaseCatalogoModel> ResponseEntity<T> obtenerInformacionCatalogoId(
	        @PathVariable("idCatalogo") int idCatalogo, @PathVariable("id") Integer id) {
	    
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.registerModule(new JavaTimeModule());
	    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	    T t = catalogo.obtenerInformacionIdInterno(idCatalogo, id);

	    log.info("Infor: {}", t);
	    log.info("Infor: {}", t.getClass());

	    return new ResponseEntity<>(t, HttpStatus.OK);
	}

	@PostMapping(Constantes.PATH_BASE_INTERNO + "/catalogos/consumo-interno/informacion/{idCatalogo}")
	@ConsumoInterno
	public <T extends BaseCatalogoModel> ResponseEntity<List<T>> obtenerInformacionCatalogoCampo(
			@PathVariable("idCatalogo") int idCatalogo, @RequestBody String json) {
		return new ResponseEntity<>(catalogo.obtenerInformacionCampo(idCatalogo, json), HttpStatus.OK);
	}

}
