package com.sisecofi.reportedocumental.controller.financiero;

import com.sisecofi.libreria.comunes.dto.reportedinamico.ProyectoNombreDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.reportedocumental.dto.financiero.FiltroNombreContratoResumenDTO;
import com.sisecofi.reportedocumental.dto.financiero.NombreContratoDTO;
import com.sisecofi.reportedocumental.service.financiero.CatalogoReporteFinancieroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/financiero/catalogo/estado-financiero")
@RequiredArgsConstructor
public class CatalogoReporteEstadoFinancieroCtrl {

    private final CatalogoReporteFinancieroService catalogoReporteFinancieroService;

    @GetMapping("/estatus-proyecto")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
    public ResponseEntity<List<BaseCatalogoModel>> estatusProyecto() {
        return new ResponseEntity<>(catalogoReporteFinancieroService.estatusProyecto(), HttpStatus.OK);
    }

    @GetMapping("/contrato-vigente")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
    public ResponseEntity<List<BaseCatalogoModel>> contratoVigente() {
        return new ResponseEntity<>(catalogoReporteFinancieroService.contratoVigente(), HttpStatus.OK);
    }

    @GetMapping("/dominios-tecnologicos")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
    public ResponseEntity<List<BaseCatalogoModel>> dominiosTecnologicos() {
        return new ResponseEntity<>(catalogoReporteFinancieroService.dominiosTecnologicos(), HttpStatus.OK);
    }

    @GetMapping("/convenio-colaboracion")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
    public ResponseEntity<List<BaseCatalogoModel>> convenioColaboracion() {
        return new ResponseEntity<>(catalogoReporteFinancieroService.convenioColaboracion(), HttpStatus.OK);
    }

    @GetMapping("/nombre-corto-proyecto")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
    public ResponseEntity<List<ProyectoNombreDto>> buscarTodosProyectos() {
        return new ResponseEntity<>(catalogoReporteFinancieroService.buscarTodosProyectos(), HttpStatus.OK);
    }

    @GetMapping("/nombre-corto-proyecto/{idEstatusProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
    public ResponseEntity<List<ProyectoNombreDto>> buscarProyectosPorEstatus(@PathVariable("idEstatusProyecto") Integer idEstatusProyecto) {
        return new ResponseEntity<>(catalogoReporteFinancieroService.buscarProyectosPorEstatus(idEstatusProyecto), HttpStatus.OK);
    }

    @PostMapping("/nombre-corto-contrato")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
    public ResponseEntity<List<NombreContratoDTO>> contratos(@RequestBody FiltroNombreContratoResumenDTO dto) {
        return new ResponseEntity<>(catalogoReporteFinancieroService.filtrarContratos(dto), HttpStatus.OK);
    }

}
