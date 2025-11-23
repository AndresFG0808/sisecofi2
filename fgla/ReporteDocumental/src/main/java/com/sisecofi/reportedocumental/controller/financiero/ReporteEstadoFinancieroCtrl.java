package com.sisecofi.reportedocumental.controller.financiero;


import com.sisecofi.reportedocumental.dto.financiero.ConsultaEstadoFinancieroDTO;
import com.sisecofi.reportedocumental.dto.financiero.pages.PageEstadoFinanciero;
import com.sisecofi.reportedocumental.service.financiero.ReporteEstadoFinancieroService;
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
@RequestMapping("/financiero/estado-financiero")
@RequiredArgsConstructor
public class ReporteEstadoFinancieroCtrl {
    @Autowired
    private ReporteEstadoFinancieroService reporteEstadoFinancieroService;

    @PostMapping("/reporte")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
    public ResponseEntity<PageEstadoFinanciero> reporteTipos(@RequestBody ConsultaEstadoFinancieroDTO dto) {
        return new ResponseEntity<>(reporteEstadoFinancieroService.obtenerReporte(dto), HttpStatus.OK);
    }

    @PostMapping("/reporte-export")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
    public ResponseEntity<byte[]> reporteTiposExport(@RequestBody ConsultaEstadoFinancieroDTO dto) {
        return new ResponseEntity<>(reporteEstadoFinancieroService.obtenerReporteExport(dto), HttpStatus.OK);
    }
}
