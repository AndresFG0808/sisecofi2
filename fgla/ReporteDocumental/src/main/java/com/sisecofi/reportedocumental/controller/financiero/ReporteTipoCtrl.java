package com.sisecofi.reportedocumental.controller.financiero;


import com.sisecofi.reportedocumental.dto.financiero.ConsultaTipoDTO;
import com.sisecofi.reportedocumental.dto.financiero.pages.PageReporteTipo;
import com.sisecofi.reportedocumental.service.financiero.ReporteTipoService;
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
@RequestMapping("/financiero/tipo")
@RequiredArgsConstructor
public class ReporteTipoCtrl {
    @Autowired
    private ReporteTipoService reporteTipoService;

    @PostMapping("/reporte")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
    public ResponseEntity<PageReporteTipo> reporteTipos(@Validated @RequestBody ConsultaTipoDTO dto) {
        return new ResponseEntity<>(reporteTipoService.obtenerReporte(dto), HttpStatus.OK);
    }

    @PostMapping("/reporte-export")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
    public ResponseEntity<byte[]> reporteTiposExport(@Validated @RequestBody ConsultaTipoDTO dto) {
        return new ResponseEntity<>(reporteTipoService.obtenerReporteExport(dto), HttpStatus.OK);
    }
}
