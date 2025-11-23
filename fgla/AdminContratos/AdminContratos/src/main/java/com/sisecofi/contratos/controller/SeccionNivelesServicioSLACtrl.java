package com.sisecofi.contratos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.contratos.service.ServicioNivelesServicioSLA;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.libreria.comunes.model.contratos.NivelesServicioSLAModel;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/"+ Constantes.PATH_BASE)
@RequiredArgsConstructor
@RestController
public class SeccionNivelesServicioSLACtrl {
private final ServicioNivelesServicioSLA informesServicio;
	
	@PostMapping("/guardar-niveles-servicio-sla")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto()")
	public ResponseEntity<String> guardarNivelesServicioSLA(@Valid @RequestBody List<NivelesServicioSLAModel> request) {
		return new ResponseEntity<>(informesServicio.guardarNivelesServicioSLA(request), HttpStatus.CREATED);
	}

	@GetMapping("/consultar-niveles-servicio-sla/{id_contrato}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<NivelesServicioSLAModel>> consultarNivelesServicioSLA(@PathVariable("id_contrato") Long id){
		return new ResponseEntity<>(informesServicio.obtenerNivelesServicioSLA(id), HttpStatus.OK);
	}

	@PutMapping("/actualizar-niveles-servicio-sla")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto()")
	public ResponseEntity<String> actualizarNivelesServicioSLA(@Valid @RequestBody List<NivelesServicioSLAModel> request) {
		return new ResponseEntity<>(informesServicio.actualizarNivelesServicioSLA(request), HttpStatus.OK);
	}

	@PostMapping("/eliminar-niveles-servicio-sla")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto()")
	public ResponseEntity<String> eliminarNivelesServicioSLA(@RequestBody List<Long> ids){
		return new ResponseEntity<>(informesServicio.eliminarNivelesServicioSLA(ids), HttpStatus.ACCEPTED);
	}

	@GetMapping("/reporte-niveles-servicio-sla/{id_contrato}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<String> exportarExcel(@PathVariable("id_contrato") Long idContrato){
		return new ResponseEntity<>(informesServicio.exportarExcel(idContrato), HttpStatus.OK);
	}
}
