package com.sisecofi.catalogos.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.catalogos.service.ReporteService;
import com.sisecofi.catalogos.service.ServicioEmpleado;
import com.sisecofi.catalogos.util.Constantes;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class ReporteCtrl {

	private final ReporteService reporteService;
	private final ServicioEmpleado empleado;

	@GetMapping("/reporte/{idCatalogo}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<byte[]> obtenerReporteCatalogo(@PathVariable("idCatalogo") int idCatalogo) {
		return new ResponseEntity<>(reporteService.obtenerReporte(idCatalogo), HttpStatus.OK);
	}
	
	@GetMapping("/reporte/administracion/{id}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<byte[]> obtenerAdministracion(@PathVariable("id") int id) {
		return new ResponseEntity<>(reporteService.obtenerAdministracion(id), HttpStatus.OK);
	}

	@GetMapping("/reporte/central/{id}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<byte[]> obtenerCentral(@PathVariable("id") int id) {
		return new ResponseEntity<>(reporteService.obtenerCentral(id), HttpStatus.OK);
	}
	
	@GetMapping("/reporte/mapas/{id}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<byte[]> obtenerMapas(@PathVariable("id") int id) {
		return new ResponseEntity<>(reporteService.obtenerMapas(id), HttpStatus.OK);
	}
	
	@GetMapping("/reporte/general/administrador/{id}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<byte[]> administradorGeneral(@PathVariable("id") int id) {
		return new ResponseEntity<>(reporteService.obtenerAdministradoresGeneral(id), HttpStatus.OK);
	}
	
	@GetMapping("/reporte/central/administrador/{id}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<byte[]> administradorCentral(@PathVariable("id") int id) {
		return new ResponseEntity<>(reporteService.obtenerAdministradoresCentral(id), HttpStatus.OK);
	}
	
	@GetMapping("/reporte/administracion/administrador/{id}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<byte[]> administradores(@PathVariable("id") int id) {
		return new ResponseEntity<>(reporteService.obtenerAdministradoresAdministracion(id), HttpStatus.OK);
	}
	
	
	@GetMapping("/reporte/empleados-administracion/{idAdministracion}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario")
	public ResponseEntity<byte[]> exportarEmpleadosAdministracion(@PathVariable("idAdministracion") Integer idAdministracion) {
		return new ResponseEntity<>(empleado.exportarEmpleadosAdministracion(idAdministracion), HttpStatus.OK);
	}
}
