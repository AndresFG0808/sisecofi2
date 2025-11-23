package com.sisecofi.proyectos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonCentral;
import com.sisecofi.proyectos.microservicio.CatalogoMicroservicio;
import com.sisecofi.proyectos.util.Constantes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
@Slf4j
public class PruebaCatalogoCtrl {

	private final CatalogoMicroservicio catalogoMicroservicio;

	@GetMapping("/validar-catalogo2/{idCatalogo}")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<List<CatAdmonCentral>> pruebaCatalogo2(@PathVariable("idCatalogo") int idCatalogo) {
		List<CatAdmonCentral> b = catalogoMicroservicio.obtenerInformacionCatalogo(idCatalogo);
		log.info("Catalogo: {}", b);
		return new ResponseEntity<>(b, HttpStatus.OK);
	}
	
}
