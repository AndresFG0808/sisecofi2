package com.sisecofi.contratos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import com.sisecofi.contratos.service.ServicioInformesDocumentalesUnicaVez;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesUnicaVezModel;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/"+ Constantes.PATH_BASE)
@RequiredArgsConstructor
@RestController
public class SeccionInformesDocumentalesUniVezCtrl {

private final ServicioInformesDocumentalesUnicaVez informesDoc;

@PostMapping("/guardar-informes-documentales")
@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto()")
public ResponseEntity<String> crearInformeDocumentalUnicaVez(@Valid @RequestBody List<InformesDocumentalesUnicaVezModel> request) {
    return new ResponseEntity<>(informesDoc.guardarInformeDocumentalUnicaVez(request), HttpStatus.CREATED);
}


	
@GetMapping("/consultar-informes-documentales/{id_contrato}")
@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
public ResponseEntity<List<InformesDocumentalesUnicaVezModel>> obtenerTodosInformesDocumentales(@PathVariable("id_contrato") Long id) {
    return new ResponseEntity<>(informesDoc.obtenerInformesDocumentalesUnicaVez(id), HttpStatus.OK);
}


@PutMapping("/actualizar-informes-documentales")
@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto()")
public ResponseEntity<String> actualizarInformeDocumentalUnicaVez(@Valid @RequestBody List<InformesDocumentalesUnicaVezModel> request) {
      

    return new ResponseEntity<>(informesDoc.actualizarInformeDocumentalUnicaVez(request), HttpStatus.OK);
}

@PostMapping("/eliminar-informes-documentales")
@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto()")
public ResponseEntity<String> eliminarInformeDocumentalUnicaVez(@RequestBody List<Long> id){
	return new ResponseEntity<>(informesDoc.eliminarInformeDocumentalUnicaVez(id), HttpStatus.ACCEPTED);
}

@GetMapping("/reporte-informes-documentales/{idContrato}")
@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
public ResponseEntity<String> exportarExcel(@PathVariable Long idContrato) {
    return new ResponseEntity<>(informesDoc.exportarExcel(idContrato), HttpStatus.OK);
}
}
