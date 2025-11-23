package com.sisecofi.proyectos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.proyectos.dto.EstructuraProyectoMetaDto;
import com.sisecofi.proyectos.dto.ProyectoNombreDto;
import com.sisecofi.proyectos.service.AsignarProService;
import com.sisecofi.proyectos.service.ServicioReporteProyectos;
import com.sisecofi.proyectos.util.Constantes;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class ReporteProyectoCtr {

	private final ServicioReporteProyectos servicioReporteProyectos;
	private final AsignarProService asignarProService;

	@PostMapping("/reporte-proyectos")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<byte[]> obtenerReporteProyectos(@RequestBody EstructuraProyectoMetaDto proyecto) {
		return new ResponseEntity<>(servicioReporteProyectos.obtenerReporteProyectosRegistrados(proyecto),
				HttpStatus.OK);
	}

	@PostMapping("/reporte-proyectos-usuarios")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<byte[]> obtenerReporteProyectosUsuarios(@RequestBody ProyectoNombreDto dto) {
		return new ResponseEntity<>(asignarProService.exportarProyectosUsuarios(dto), HttpStatus.OK);
	}
}
