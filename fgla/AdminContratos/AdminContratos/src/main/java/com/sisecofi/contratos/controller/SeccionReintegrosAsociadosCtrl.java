package com.sisecofi.contratos.controller;

import com.sisecofi.contratos.dto.ReintegrosAsociadosDto;
import com.sisecofi.contratos.service.ServicioReintegrosAsociados;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.libreria.comunes.model.reintegros.ReintegrosAsociadosModel;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/"+ Constantes.PATH_BASE)
@RequiredArgsConstructor
@RestController
public class SeccionReintegrosAsociadosCtrl {

    private final ServicioReintegrosAsociados servicioReintegrosAsociados;

    @PostMapping("/reintegros-asociados")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<ReintegrosAsociadosModel> guardarServicioContrato(@Valid @RequestBody ReintegrosAsociadosModel reintegrosAsociadosModel) {
        return new ResponseEntity<>(servicioReintegrosAsociados.guardarReintegrosAsociados(reintegrosAsociadosModel), HttpStatus.OK);
    }

    @GetMapping("/reintegros/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<ReintegrosAsociadosDto> obtenerServicioContrato(@PathVariable("idContrato") Long idContato) {
        return new ResponseEntity<>(servicioReintegrosAsociados.obtenerReintegrosAsociados(idContato), HttpStatus.OK);
    }

}
