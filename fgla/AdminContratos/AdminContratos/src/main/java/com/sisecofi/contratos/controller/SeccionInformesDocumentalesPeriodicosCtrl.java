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

import com.sisecofi.contratos.service.ServicioInformesDocumentalesPeriodicos;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesPeriodicosModel;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/"+ Constantes.PATH_BASE)
@RequiredArgsConstructor
@RestController
public class SeccionInformesDocumentalesPeriodicosCtrl {
private final ServicioInformesDocumentalesPeriodicos informesServicio;

@PostMapping("/guardar-informes-documentales-periodicos")
@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto()")
public ResponseEntity<String> guardarInformesDocumentalesPeriodicos(@Valid @RequestBody List<InformesDocumentalesPeriodicosModel> request) {
	return new ResponseEntity<>(informesServicio.guardarInformeDocumentalPeridicos(request), HttpStatus.CREATED);
}

@GetMapping("/consultar-informes-documentales-periodicos/{id_contrato}")
@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
public ResponseEntity<List<InformesDocumentalesPeriodicosModel>> consultarInformesDocumentalesPeriodicos(@PathVariable("id_contrato") Long id){
	return new ResponseEntity<>(informesServicio.obtenerInformesDocumentalesPeriodicos(id), HttpStatus.OK);
}

@PutMapping("/actualizar-informes-documentales-periodicos")
@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto()")
public ResponseEntity<String> actualizarInformesDocumentalesPeriodicos(@Valid @RequestBody List<InformesDocumentalesPeriodicosModel> request) {
	return new ResponseEntity<>(informesServicio.actualizarInformeDocumentalPeriodicos(request), HttpStatus.OK);
}

@PostMapping("/eliminar-informes-documentales-periodicos")
@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto()")
public ResponseEntity<String> eliminarInformesDocumentalesPeriodicos(@RequestBody List<Long> ids){
	return new ResponseEntity<>(informesServicio.eliminarInformeDocumentalPeriodicos(ids), HttpStatus.ACCEPTED);
}

@GetMapping("/reporte-informes-documentales-periodicos/{id_contrato}")
@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
public ResponseEntity<String> exportarExcel(@PathVariable("id_contrato") Long idContrato){
	return new ResponseEntity<>(informesServicio.exportarExcel(idContrato), HttpStatus.OK);
}
}
