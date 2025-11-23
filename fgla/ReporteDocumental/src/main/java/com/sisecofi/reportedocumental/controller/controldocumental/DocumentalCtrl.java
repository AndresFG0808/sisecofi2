package com.sisecofi.reportedocumental.controller.controldocumental;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.libreria.comunes.dto.carpeta.CarpetaDtoResponse;
import com.sisecofi.libreria.comunes.dto.reportecontroldoc.PlantillaVigenteDto;
import com.sisecofi.libreria.comunes.dto.reportedinamico.ProyectoNombreDto;
import com.sisecofi.reportedocumental.dto.controldocumental.BusquedaDocumentalDto;
import com.sisecofi.reportedocumental.dto.controldocumental.PageDocumental;
import com.sisecofi.reportedocumental.dto.dinamico.Ids;
import com.sisecofi.reportedocumental.service.controldocumental.DocumentalService;
import com.sisecofi.reportedocumental.service.controldocumental.PlantillaVigenteService;
import com.sisecofi.reportedocumental.service.dinamico.CatalogoDinamicoService;
import com.sisecofi.reportedocumental.util.Constantes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class DocumentalCtrl {

	private final DocumentalService documentalService;
	private final CatalogoDinamicoService catalogoDinamicoService;
	private final PlantillaVigenteService plantillaVigenteService;

	@PostMapping("/controldoc/reporte-control-documental")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<PageDocumental> obtenerReporte(@Valid @RequestBody BusquedaDocumentalDto busquedaDto) {
		return new ResponseEntity<>(documentalService.buscarControlDocumental(busquedaDto), HttpStatus.OK);
	}
	
	@GetMapping("/controldoc/comprobar-proyectos")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<Boolean> comprobarProyectos() {
		return new ResponseEntity<>(documentalService.comprobarProyectos(), HttpStatus.OK);
	}

	@GetMapping("/controldoc/nombre-plantilla/{idFaseProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<PlantillaVigenteDto>> plantillas(
			@PathVariable("idFaseProyecto") Integer idFaseProyecto) {
		return new ResponseEntity<>(plantillaVigenteService.findByFaseProyecto(idFaseProyecto), HttpStatus.OK);
	}

	@PostMapping("/controldoc/nombre-corto-proyecto")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<ProyectoNombreDto>> proyectos(@RequestBody Ids<Integer> estatusProyecto) {
		return new ResponseEntity<>(catalogoDinamicoService.buscarProyecto(estatusProyecto), HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/controldoc/matriz-doc/{identificador}/{idReferencia}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<CarpetaDtoResponse>> matrizDocumental(
			@PathVariable("identificador") Integer identificador, @PathVariable("idReferencia") Long idReferencia) {
		return new ResponseEntity<>(documentalService.obtenerMatriz(identificador, idReferencia), HttpStatus.OK);
	}

}
