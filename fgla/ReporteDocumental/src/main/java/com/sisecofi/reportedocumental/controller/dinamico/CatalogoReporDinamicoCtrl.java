package com.sisecofi.reportedocumental.controller.dinamico;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.libreria.comunes.dto.reportedinamico.ContratoNombreDto;
import com.sisecofi.libreria.comunes.dto.reportedinamico.ProveedorRazonSocialDto;
import com.sisecofi.libreria.comunes.dto.reportedinamico.ProyectoNombreDto;
import com.sisecofi.libreria.comunes.dto.reportedinamico.TituloServicioPreveedorDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.reportedocumental.dto.dinamico.Ids;
import com.sisecofi.reportedocumental.service.dinamico.CatalogoDinamicoService;
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
public class CatalogoReporDinamicoCtrl {

	private final CatalogoDinamicoService catalogoDinamicoService;

	@GetMapping("/dinamico/convenio-colaboracion")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> convenioColaboracion() {
		return new ResponseEntity<>(catalogoDinamicoService.convenioColoboracion(), HttpStatus.OK);
	}

	@GetMapping("/dinamico/estatus-proyecto")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> estatusProyecto() {
		return new ResponseEntity<>(catalogoDinamicoService.estatusProyecto(), HttpStatus.OK);
	}

	@GetMapping("/dinamico/estatus-contrato")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> estatusContrato() {
		return new ResponseEntity<>(catalogoDinamicoService.estatusContrato(), HttpStatus.OK);
	}

	@PostMapping("/dinamico/nombre-corto-proyecto")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
	public ResponseEntity<List<ProyectoNombreDto>> proyectos(@RequestBody Ids<Integer> estatusProyecto) {
		return new ResponseEntity<>(catalogoDinamicoService.buscarProyecto(estatusProyecto), HttpStatus.OK);
	}

	@PostMapping("/dinamico/nombre-corto-contrato")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
	public ResponseEntity<List<ContratoNombreDto>> contratos(@RequestBody Ids<Integer> estatusContrato) {
		return new ResponseEntity<>(catalogoDinamicoService.buscarContrato(estatusContrato), HttpStatus.OK);
	}

	@GetMapping("/dinamico/dominio-tecnologico")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolApoyoAcppi()")
	public ResponseEntity<List<BaseCatalogoModel>> dominioTecnologico() {
		return new ResponseEntity<>(catalogoDinamicoService.dominioTecnologico(), HttpStatus.OK);
	}

	@GetMapping("/dinamico/razon-social")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolApoyoAcppi()")
	public ResponseEntity<List<ProveedorRazonSocialDto>> razonSocial() {
		return new ResponseEntity<>(catalogoDinamicoService.razonSocial(), HttpStatus.OK);
	}

	@PostMapping("/dinamico/titulo-servicio")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolApoyoAcppi()")
	public ResponseEntity<List<TituloServicioPreveedorDto>> tituloServicio(@RequestBody Ids<Long> idProveedor) {
		return new ResponseEntity<>(catalogoDinamicoService.buscarTituloServicio(idProveedor), HttpStatus.OK);
	}

}
