package com.sisecofi.reportedocumental.controller.financiero;


import com.sisecofi.reportedocumental.dto.financiero.ConsultaSeguimientoDictamenDTO;
import com.sisecofi.reportedocumental.dto.financiero.pages.PageReporteSeguimientoDictamen;
import com.sisecofi.reportedocumental.service.financiero.ReporteSeguimientoDictamenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/financiero/seguimiento-dictamen")
@RequiredArgsConstructor
public class ReporteSeguimientoDictamenCtrl {
    @Autowired
    private ReporteSeguimientoDictamenService reporteSeguimientoDictamenService;

    @PostMapping("/reporte")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
    public ResponseEntity<PageReporteSeguimientoDictamen> reporteSeguimientoDictamen(@Validated @RequestBody ConsultaSeguimientoDictamenDTO dto) {
        return new ResponseEntity<>(reporteSeguimientoDictamenService.obtenerReporte(dto), HttpStatus.OK);
    }

    @PostMapping("/reporte-export")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
    public ResponseEntity<byte[]> reporteSeguimientoDictamenExport(@Validated @RequestBody ConsultaSeguimientoDictamenDTO dto) {
        return new ResponseEntity<>(reporteSeguimientoDictamenService.obtenerReporteExport(dto), HttpStatus.OK);
    }
}
