package com.sisecofi.proyectos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.proyectos.service.ServicioReporteProveedoresAsignados;
import com.sisecofi.proyectos.util.Constantes;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class ReporteProveedoresParticipantesCtrl {
	
	private final ServicioReporteProveedoresAsignados servicioReporteProveedoresAsignados;
	
	@GetMapping("proveedores-asignados/reporte/proyecto/{idProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi()")
	public ResponseEntity<byte[]> obtenerReporteProyectos(@PathVariable Long idProyecto) {
		return new ResponseEntity<>(servicioReporteProveedoresAsignados.obtenerReporteProveedoresAsignados(idProyecto),
				HttpStatus.OK);
	}

}
