package com.sisecofi.reportedocumental.controller.controldocumental;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
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
public class CatalogoDocumentalCtrl {

	private final CatalogoDinamicoService catalogoDinamicoService;

	@GetMapping("/controldoc/estatus-proyecto")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> estatusProyecto() {
		return new ResponseEntity<>(catalogoDinamicoService.estatusProyecto(), HttpStatus.OK);
	}

	@GetMapping("/controldoc/fase-proyecto")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> faseProyecto() {
		return new ResponseEntity<>(catalogoDinamicoService.faseProyecto(), HttpStatus.OK);
	}
}
