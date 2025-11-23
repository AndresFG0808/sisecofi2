package com.sisecofi.catalogos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.catalogos.dto.AdminGeneric;
import com.sisecofi.catalogos.service.ServicioCatalogoComp;
import com.sisecofi.catalogos.util.Constantes;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorGeneral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonGeneral;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class CatalogoCompCtrl {

	private final ServicioCatalogoComp catalogo;

	@GetMapping("/catalogos/informacion/administraciones/{idAdmonCentral}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public <T extends BaseCatalogoModel> ResponseEntity<List<T>> obtenerAdministraciones(
			@PathVariable("idAdmonCentral") int idCentral) {
		return new ResponseEntity<>(catalogo.obtenerAdministracion(idCentral), HttpStatus.OK);
	}

	@GetMapping("/catalogos/informacion/centrales/{idAdmonGeneral}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public <T extends BaseCatalogoModel> ResponseEntity<List<T>> obtenerAdministracionesCentrales(
			@PathVariable("idAdmonGeneral") int idCentral) {
		return new ResponseEntity<>(catalogo.obtenerCentral(idCentral), HttpStatus.OK);
	}

	@GetMapping("/catalogos/informacion/mapas/{idAliniacion}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public <T extends BaseCatalogoModel> ResponseEntity<List<T>> obtenerMapa(
			@PathVariable("idAliniacion") int idCentral) {
		return new ResponseEntity<>(catalogo.obtenerMapas(idCentral), HttpStatus.OK);
	}

	@PutMapping("/catalogos/guardar-admon-general")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<AdminGeneric<CatAdmonGeneral, CatAdministradorGeneral>> guardarAdminsitradorGeneral(
			@RequestBody AdminGeneric<CatAdmonGeneral, CatAdministradorGeneral> adminGralDto) {
		return new ResponseEntity<>(catalogo.guardarAdministracionesGenerales(adminGralDto), HttpStatus.OK);
	}

	@PutMapping("/catalogos/guardar-admon-centrales")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<AdminGeneric<CatAdmonCentral, CatAdministradorCentral>> guardarAdminsitradorCentrales(
			@RequestBody AdminGeneric<CatAdmonCentral, CatAdministradorCentral> adminGralDto) {
		return new ResponseEntity<>(catalogo.guardarAdministracionesCentral(adminGralDto), HttpStatus.OK);
	}

	@PutMapping("/catalogos/guardar-admnistraciones")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<AdminGeneric<CatAdministracion, CatAdministradorAdministracion>> guardarAdminsitrador(
			@RequestBody AdminGeneric<CatAdministracion, CatAdministradorAdministracion> adminGralDto) {
		return new ResponseEntity<>(catalogo.guardarAdministraciones(adminGralDto), HttpStatus.OK);
	}
}
