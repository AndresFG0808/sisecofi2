package com.sisecofi.reportedocumental.controller.dinamico;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.reportedocumental.dto.dinamico.CriterioBusquedaDto;
import com.sisecofi.reportedocumental.service.dinamico.ReporteDinamicoService;
import com.sisecofi.reportedocumental.util.Constantes;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class ReporteDinamicoCtrl {

	private final ReporteDinamicoService dinamicoService;

	@PostMapping("/dinamico/reporte-dinamico")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
	public ResponseEntity<PageGeneric> obtenerReporte(@RequestBody CriterioBusquedaDto busquedaDto) {
		return new ResponseEntity<>(dinamicoService.obtenerReporte(busquedaDto), HttpStatus.OK);
	}

	@PostMapping("/dinamico/reporte-dinamico-export")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
	public ResponseEntity<byte[]> obtenerReporteExport(@RequestBody CriterioBusquedaDto busquedaDto) {
		return new ResponseEntity<>(dinamicoService.obtenerReporteExport(busquedaDto), HttpStatus.OK);
	}

}
