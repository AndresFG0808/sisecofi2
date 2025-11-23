package com.sisecofi.catalogos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.catalogos.service.AdministradorService;
import com.sisecofi.catalogos.util.Constantes;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorGeneral;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class AdministradorCtrl {

	private final AdministradorService service;

	@GetMapping("/administrador-central")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<List<CatAdministradorCentral>> buscarAdministradorCentral() {
		return new ResponseEntity<>(service.buscarAdministradorCentral(), HttpStatus.OK);
	}

	@PutMapping("/administrador-central")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<CatAdministradorCentral> guardarAdminsitradorCentral(
			@RequestBody CatAdministradorCentral administradorCentral) {
		return new ResponseEntity<>(service.guardarAdminsitradorCentral(administradorCentral), HttpStatus.OK);
	}

	@DeleteMapping("/administrador-central/{id}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<Boolean> eliminarAdminsitradorCentral(@PathVariable("id") Integer id) {
		return new ResponseEntity<>(service.eliminarAdminsitradorCentral(id), HttpStatus.OK);
	}

	@GetMapping("/administrador-general")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<List<CatAdministradorGeneral>> buscarAdministradorGeneral() {
		return new ResponseEntity<>(service.buscarAdministradorGeneral(), HttpStatus.OK);
	}

	@PutMapping("/administrador-general")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<CatAdministradorGeneral> guardarAdminsitradorGeneral(
			@RequestBody CatAdministradorGeneral administradorCentral) {
		return new ResponseEntity<>(service.guardarAdminsitradorGeneral(administradorCentral), HttpStatus.OK);
	}

	@DeleteMapping("/administrador-general/{id}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<Boolean> eliminarAdminsitradorGeneral(@PathVariable("id") Integer id) {
		return new ResponseEntity<>(service.eliminarAdminsitradorGeneral(id), HttpStatus.OK);
	}

	@GetMapping("/administrador-administracion")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<List<CatAdministradorAdministracion>> buscarAdministradorAdministracion() {
		return new ResponseEntity<>(service.buscarAdministradorAdministracion(), HttpStatus.OK);
	}

	@PutMapping("/administrador-administracion")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<CatAdministradorAdministracion> guardarAdminsitradorAdministracion(
			@RequestBody CatAdministradorAdministracion administradorCentral) {
		return new ResponseEntity<>(service.guardarAdminsitradorAdministracion(administradorCentral), HttpStatus.OK);
	}

	@DeleteMapping("/administrador-administracion/{id}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<Boolean> eliminarAdminsitradorAdministracion(@PathVariable("id") Integer id) {
		return new ResponseEntity<>(service.eliminarAdminsitradorAdministracion(id), HttpStatus.OK);
	}
}
