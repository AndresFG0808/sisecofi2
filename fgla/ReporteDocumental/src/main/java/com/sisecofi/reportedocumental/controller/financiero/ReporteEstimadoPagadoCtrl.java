package com.sisecofi.reportedocumental.controller.financiero;


import com.sisecofi.reportedocumental.dto.financiero.ConsultaEstimadoPagadoDTO;
import com.sisecofi.reportedocumental.dto.financiero.pages.PageReporteEstimadoPagado;
import com.sisecofi.reportedocumental.service.financiero.ReporteEstimadoPagadoService;
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
@RequestMapping("/financiero/estimado-pagado")
@RequiredArgsConstructor
public class ReporteEstimadoPagadoCtrl {
    @Autowired
    private ReporteEstimadoPagadoService reporteEstimadoPagadoService;

    @PostMapping("/reporte")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
    public ResponseEntity<PageReporteEstimadoPagado> reporteTipos(@Validated @RequestBody ConsultaEstimadoPagadoDTO dto) {
        return new ResponseEntity<>(reporteEstimadoPagadoService.obtenerReporte(dto), HttpStatus.OK);
    }

    @PostMapping("/reporte-export")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
    public ResponseEntity<byte[]> reporteTiposExport(@Validated @RequestBody ConsultaEstimadoPagadoDTO dto) {
        return new ResponseEntity<>(reporteEstimadoPagadoService.obtenerReporteExport(dto), HttpStatus.OK);
    }
}
