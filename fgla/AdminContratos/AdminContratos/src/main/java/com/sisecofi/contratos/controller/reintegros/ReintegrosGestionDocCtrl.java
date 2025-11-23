package com.sisecofi.contratos.controller.reintegros;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.contratos.service.carpetas.ServicioGestionDocumental;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.libreria.comunes.dto.carpeta.CarpetaDtoResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE)
public class ReintegrosGestionDocCtrl {

    private final ServicioGestionDocumental servicioGestionDocumental;


    @SuppressWarnings("rawtypes")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    @GetMapping("/reintegros/gestion-documental/{idContrato}")
    public ResponseEntity<List<CarpetaDtoResponse>> obtenerEstructuraDocReintegros(@PathVariable Long idContrato) {
        return new ResponseEntity<>(servicioGestionDocumental.obtenerEstructuraDocReintegros(idContrato), HttpStatus.OK);
    }

  
}
