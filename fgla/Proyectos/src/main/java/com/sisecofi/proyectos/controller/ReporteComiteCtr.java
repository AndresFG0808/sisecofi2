package com.sisecofi.proyectos.controller;

import com.sisecofi.proyectos.service.ServicioReporteComite;
import com.sisecofi.proyectos.util.Constantes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/"+ Constantes.PATH_BASE)
@RequiredArgsConstructor
public class ReporteComiteCtr {

    private final ServicioReporteComite servicioReporteComite;


    @GetMapping("/reporte/{idProyecto}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<String> obtenerReporteComite(@PathVariable Integer idProyecto){
        return new ResponseEntity<>(servicioReporteComite.obtenerReporteContratoConvenio(idProyecto), HttpStatus.OK);
    }
}
