package com.sisecofi.proyectos.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.AsociacionesModel;
import com.sisecofi.proyectos.dto.AsociacionGuardarRequest;
import com.sisecofi.proyectos.dto.AsociacionResponse;
import com.sisecofi.proyectos.service.ServicioAsociacion;
import com.sisecofi.proyectos.util.Constantes;
import com.sisecofi.proyectos.util.interfaces.ValidacionCompleta;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class AsociacionCtrl {

	private final ServicioAsociacion servicioAsociacion;

	@PostMapping("/asociaciones/guardar/{idProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental()")
	public ResponseEntity<List<AsociacionResponse>> guardarAsociaciones(@Validated({ValidacionCompleta.class})@RequestBody AsociacionGuardarRequest request, @PathVariable Long idProyecto) {
		return new ResponseEntity<>(servicioAsociacion.guardarAsociaciones(request, idProyecto), org.springframework.http.HttpStatus.OK);
	}
	
	@GetMapping("/asociaciones/{idProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolApoyoAcppi()")
	public ResponseEntity<List<AsociacionResponse>> obtenerAsociaciones(@PathVariable Long idProyecto){
		return new ResponseEntity<>(servicioAsociacion.obtenerAsociaciones(idProyecto), org.springframework.http.HttpStatus.OK);
	}
	
	@GetMapping("/reporte/asociaciones/{idProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolApoyoAcppi()")
	public ResponseEntity<String> generarReporteAsociacion(@PathVariable Long idProyecto){
		return new ResponseEntity<>(servicioAsociacion.generarReporteAsociacion(idProyecto), org.springframework.http.HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping("/crear-asociacion/{idProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental()")
	public ResponseEntity guardarAsociacion(@RequestBody AsociacionesModel request, @PathVariable Long idProyecto) {
		return new ResponseEntity<>(servicioAsociacion.crearAsociacion(request, idProyecto), org.springframework.http.HttpStatus.OK);
	}

}
