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

import com.sisecofi.catalogos.dto.EmpleadoDto;
import com.sisecofi.catalogos.service.ServicioEmpleado;
import com.sisecofi.catalogos.util.Constantes;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoEmpleado;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class EmpleadosCtrl {

	private final ServicioEmpleado empleado;


	@GetMapping("/tipo-empleado")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario")
	public ResponseEntity<List<CatTipoEmpleado>> obtenerTipoEmpleado() {
		return new ResponseEntity<>(empleado.obtenerTipoEmpleado(), HttpStatus.OK);
	}
	
	@GetMapping("/empleados-administracion/{idAdministracion}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario")
	public ResponseEntity<List<EmpleadoDto>> obtenerEmpleadosAdministracion(@PathVariable("idAdministracion") Integer idAdministracion) {
		return new ResponseEntity<>(empleado.obtenerEmpleadosAdministracion(idAdministracion), HttpStatus.OK);
	}
	
	@PutMapping("/empleados-administracion-guardar/{idAdministracion}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario")
	public ResponseEntity<List<EmpleadoDto>> guardarEmpleadosAdministracion(@RequestBody List<EmpleadoDto> lista, @PathVariable("idAdministracion") Integer idAdministracion) {
		return new ResponseEntity<>(empleado.guardarEmpleadosAdministracion(lista, idAdministracion), HttpStatus.OK);
	}
	
	
	

}
