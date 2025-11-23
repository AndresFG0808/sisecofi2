package com.sisecofi.reportedocumental.controller.financiero;

import com.sisecofi.reportedocumental.dto.financiero.ConsultaDetalleCMDTO;
import com.sisecofi.reportedocumental.dto.financiero.ConsultaResumenDTO;
import com.sisecofi.reportedocumental.dto.financiero.PageReporteDetalleCM;
import com.sisecofi.reportedocumental.dto.financiero.pages.PageReporteFinanciero;
import com.sisecofi.reportedocumental.service.financiero.ReporteResumenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/financiero/resumen")
@RequiredArgsConstructor
public class ReporteResumenCtrl {

    @Autowired
    private ReporteResumenService reporteResumenService;

    @PostMapping("/reporte")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<PageReporteFinanciero> reporteResumen(@RequestBody ConsultaResumenDTO dto) {
        return new ResponseEntity<>(reporteResumenService.obtenerReporte(dto), HttpStatus.OK);
    }

    @PostMapping("/reporte-detalle-cm")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<PageReporteDetalleCM> reporteResumenDetalleCM(@RequestBody ConsultaDetalleCMDTO dto) {
        return new ResponseEntity<>(reporteResumenService.obtenerReporteDetalleCM(dto), HttpStatus.OK);
    }

    @PostMapping("/reporte-export")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<byte[]> reporteResumenExport(@RequestBody ConsultaResumenDTO dto) {
        return new ResponseEntity<>(reporteResumenService.obtenerReporteExport(dto), HttpStatus.OK);
    }

    @PostMapping("/reporte-detalle-cm-export")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<byte[]> reporteResumenDetalleCMExport(@RequestBody ConsultaDetalleCMDTO dto) {
        return new ResponseEntity<>(reporteResumenService.obtenerReporteDetalleCMExport(dto), HttpStatus.OK);
    }
}
