package com.sisecofi.reportedocumental.controller.financiero;


import com.sisecofi.reportedocumental.dto.financiero.ConsultaSeguimientoLineaServicioDTO;
import com.sisecofi.reportedocumental.dto.financiero.pages.PageSeguimientoLineaServicio;
import com.sisecofi.reportedocumental.service.financiero.ReporteSeguimientoLineaServicioService;
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
@RequestMapping("/financiero/seguimiento-linea-servicio")
@RequiredArgsConstructor
public class ReporteSeguimientoLineaServicioCtrl {
    @Autowired
    private ReporteSeguimientoLineaServicioService service;

    @PostMapping("/reporte")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
    public ResponseEntity<PageSeguimientoLineaServicio> reporteTipos(@RequestBody ConsultaSeguimientoLineaServicioDTO dto) {
        return new ResponseEntity<>(service.obtenerReporte(dto), HttpStatus.OK);
    }

    @PostMapping("/reporte-export")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
    public ResponseEntity<byte[]> reporteTiposExport(@RequestBody ConsultaSeguimientoLineaServicioDTO dto) {
        return new ResponseEntity<>(service.obtenerReporteExport(dto), HttpStatus.OK);
    }
}
