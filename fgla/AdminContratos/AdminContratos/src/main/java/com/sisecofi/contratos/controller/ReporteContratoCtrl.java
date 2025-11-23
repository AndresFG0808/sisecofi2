package com.sisecofi.contratos.controller;

import com.sisecofi.contratos.dto.CriteriosDeBusquedaContratoDto;
import com.sisecofi.contratos.service.ServicioReporteContratos;
import com.sisecofi.contratos.util.Constantes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/"+ Constantes.PATH_BASE)
@RequiredArgsConstructor
@RestController
public class ReporteContratoCtrl {

    private final ServicioReporteContratos contratos;

    @PostMapping("/reporte-contrato")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
            "@seguridad.validarRolAdminSistemaSecundario() || " +
            "@seguridad.validarRolVerificadorEspecificoContrato() || " +
            "@seguridad.validarRolAdminMatrizDocumental() || " +
            "@seguridad.validarRolApoyoAcppi() ||" +
            "@seguridad.validarRolApoyoAlLiderTecnicoProyeto() ||"+
            "@seguridad.validarRolGestorDocumentalContrato() ||" +
            "@seguridad.validarRolUsuarioConsulta() ||" +
            "@seguridad.validarRolLiderProyecto() ||" +
            "@seguridad.validarRolAdministradorContrato()  ||" +
            "@seguridad.validarRolParticipantesAdmonEstimaciones() ||"+
            "@seguridad.validarRolParticipantesAdmonDictamen() ||"+
            "@seguridad.validarRolParticipantesAdmonVerificacion() ||"+
            "@seguridad.validarRolVerificadorGeneral() ||" +
            "@seguridad.validarRolVerificadorEspecificoContratol()"
    )
    public ResponseEntity<String> obtenerReporteComite(@RequestBody CriteriosDeBusquedaContratoDto criterios){
        return new ResponseEntity<>(contratos.obtenerReporteContratosRegistrados(criterios), HttpStatus.OK);
    }

    @PostMapping("/reporte-registro-servicio/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
            "@seguridad.validarRolAdminSistemaSecundario() || " +
            "@seguridad.validarRolVerificadorEspecificoContrato() || " +
            "@seguridad.validarRolAdminMatrizDocumental() || " +
            "@seguridad.validarRolApoyoAcppi() ||" +
            "@seguridad.validarRolApoyoAlLiderTecnicoProyeto() ||"+
            "@seguridad.validarRolGestorDocumentalContrato() ||" +
            "@seguridad.validarRolUsuarioConsulta() ||" +
            "@seguridad.validarRolLiderProyecto() ||" +
            "@seguridad.validarRolAdministradorContrato()  ||" +
            "@seguridad.validarRolParticipantesAdmonEstimaciones() ||"+
            "@seguridad.validarRolParticipantesAdmonDictamen() ||"+
            "@seguridad.validarRolParticipantesAdmonVerificacion() ||"+
            "@seguridad.validarRolVerificadorGeneral() ||" +
            "@seguridad.validarRolVerificadorEspecificoContratol()"
    )
    public ResponseEntity<String> obtenerReporteServicioContrato(@PathVariable("idContrato")  Long idContrato){
        return new ResponseEntity<>(contratos.obtenerReporteServicioConttrato(idContrato), HttpStatus.OK);
    }

    @PostMapping("/reporte-atraso-prestacion/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
            "@seguridad.validarRolAdminSistemaSecundario() || " +
            "@seguridad.validarRolVerificadorEspecificoContrato() || " +
            "@seguridad.validarRolAdminMatrizDocumental() || " +
            "@seguridad.validarRolApoyoAcppi() ||" +
            "@seguridad.validarRolApoyoAlLiderTecnicoProyeto() ||"+
            "@seguridad.validarRolGestorDocumentalContrato() ||" +
            "@seguridad.validarRolUsuarioConsulta() ||" +
            "@seguridad.validarRolLiderProyecto() ||" +
            "@seguridad.validarRolAdministradorContrato()  ||" +
            "@seguridad.validarRolParticipantesAdmonEstimaciones() ||"+
            "@seguridad.validarRolParticipantesAdmonDictamen() ||"+
            "@seguridad.validarRolParticipantesAdmonVerificacion() ||"+
            "@seguridad.validarRolVerificadorGeneral() ||" +
            "@seguridad.validarRolVerificadorEspecificoContratol()"
    )
    public ResponseEntity<String> obtenerReporteAtrasoPrestacion(@PathVariable("idContrato")  Long idContrato){
        return new ResponseEntity<>(contratos.obtenerReporteAtrasoPrestacion(idContrato), HttpStatus.OK);
    }

    @PostMapping("/reporte-participantes-contrato/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
            "@seguridad.validarRolAdminSistemaSecundario() || " +
            "@seguridad.validarRolVerificadorEspecificoContrato() || " +
            "@seguridad.validarRolAdminMatrizDocumental() || " +
            "@seguridad.validarRolApoyoAcppi() ||" +
            "@seguridad.validarRolApoyoAlLiderTecnicoProyeto() ||"+
            "@seguridad.validarRolGestorDocumentalContrato() ||" +
            "@seguridad.validarRolUsuarioConsulta() ||" +
            "@seguridad.validarRolLiderProyecto() ||" +
            "@seguridad.validarRolAdministradorContrato()  ||" +
            "@seguridad.validarRolParticipantesAdmonEstimaciones() ||"+
            "@seguridad.validarRolParticipantesAdmonDictamen() ||"+
            "@seguridad.validarRolParticipantesAdmonVerificacion() ||"+
            "@seguridad.validarRolVerificadorGeneral() ||" +
            "@seguridad.validarRolVerificadorEspecificoContratol()"
    )
    public ResponseEntity<String> obtenerReporteParticipantesContrato(@PathVariable("idContrato")  Long idContrato){
        return new ResponseEntity<>(contratos.obtenerReporteParticipantesContrato(idContrato), HttpStatus.OK);
    }

    @PostMapping("/reporte-reintegro-participantes/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
            "@seguridad.validarRolAdminSistemaSecundario() || " +
            "@seguridad.validarRolVerificadorEspecificoContrato() || " +
            "@seguridad.validarRolAdminMatrizDocumental() || " +
            "@seguridad.validarRolApoyoAcppi() ||" +
            "@seguridad.validarRolApoyoAlLiderTecnicoProyeto() ||"+
            "@seguridad.validarRolGestorDocumentalContrato() ||" +
            "@seguridad.validarRolUsuarioConsulta() ||" +
            "@seguridad.validarRolLiderProyecto() ||" +
            "@seguridad.validarRolAdministradorContrato()  ||" +
            "@seguridad.validarRolParticipantesAdmonEstimaciones() ||"+
            "@seguridad.validarRolParticipantesAdmonDictamen() ||"+
            "@seguridad.validarRolParticipantesAdmonVerificacion() ||"+
            "@seguridad.validarRolVerificadorGeneral() ||" +
            "@seguridad.validarRolVerificadorEspecificoContratol()"
    )
    public ResponseEntity<String> obtenerReporteReintegrosAsociados(@PathVariable("idContrato")  Long idContrato){
        return new ResponseEntity<>(contratos.obtenerReporteReintegroAsociado(idContrato), HttpStatus.OK);
    }

    @PostMapping("/reporte-convenio-modificatorio/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
            "@seguridad.validarRolAdminSistemaSecundario() || " +
            "@seguridad.validarRolVerificadorEspecificoContrato() || " +
            "@seguridad.validarRolAdminMatrizDocumental() || " +
            "@seguridad.validarRolApoyoAcppi() ||" +
            "@seguridad.validarRolApoyoAlLiderTecnicoProyeto() ||"+
            "@seguridad.validarRolGestorDocumentalContrato() ||" +
            "@seguridad.validarRolUsuarioConsulta() ||" +
            "@seguridad.validarRolLiderProyecto() ||" +
            "@seguridad.validarRolAdministradorContrato()  ||" +
            "@seguridad.validarRolParticipantesAdmonEstimaciones() ||"+
            "@seguridad.validarRolParticipantesAdmonDictamen() ||"+
            "@seguridad.validarRolParticipantesAdmonVerificacion() ||"+
            "@seguridad.validarRolVerificadorGeneral() ||" +
            "@seguridad.validarRolVerificadorEspecificoContratol()"
    )
    public ResponseEntity<String> obtenerReporteConvenioModificatorio(@PathVariable("idContrato")  Long idContrato){
        return new ResponseEntity<>(contratos.obtenerReporteConvenioModificatorio(idContrato), HttpStatus.OK);
    }
}
