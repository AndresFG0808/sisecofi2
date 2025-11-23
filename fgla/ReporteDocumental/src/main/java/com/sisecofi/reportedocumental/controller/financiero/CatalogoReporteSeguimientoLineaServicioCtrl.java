package com.sisecofi.reportedocumental.controller.financiero;

import com.sisecofi.libreria.comunes.dto.reportedinamico.ProyectoNombreDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.reportedocumental.dto.financiero.ConceptoServicioDTO;
import com.sisecofi.reportedocumental.dto.financiero.FiltroNombreContratoBaseDTO;
import com.sisecofi.reportedocumental.dto.financiero.NombreContratoDTO;
import com.sisecofi.reportedocumental.model.CatEstatusVolumetria;
import com.sisecofi.reportedocumental.service.financiero.CatalogoReporteFinancieroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/financiero/catalogo/seguimiento-linea-servicio")
@RequiredArgsConstructor
public class CatalogoReporteSeguimientoLineaServicioCtrl {

    private final CatalogoReporteFinancieroService catalogoReporteFinancieroService;

    @GetMapping("/nombre-corto-proyecto")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
    public ResponseEntity<List<ProyectoNombreDto>> buscarTodosProyectos() {
        return new ResponseEntity<>(catalogoReporteFinancieroService.buscarTodosProyectos(), HttpStatus.OK);
    }

    @GetMapping("/contrato-vigente")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
    public ResponseEntity<List<BaseCatalogoModel>> contratoVigente() {
        return new ResponseEntity<>(catalogoReporteFinancieroService.contratoVigente(), HttpStatus.OK);
    }

    @GetMapping("/convenio-colaboracion")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
    public ResponseEntity<List<BaseCatalogoModel>> convenioColaboracion() {
        return new ResponseEntity<>(catalogoReporteFinancieroService.convenioColaboracion(), HttpStatus.OK);
    }

    @PostMapping("/nombre-corto-contrato")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
    public ResponseEntity<List<NombreContratoDTO>> contratos(@RequestBody FiltroNombreContratoBaseDTO dto) {
        return new ResponseEntity<>(catalogoReporteFinancieroService.filtrarContratos(dto), HttpStatus.OK);
    }

    @GetMapping("/estatus-volumetria")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
    public ResponseEntity<List<CatEstatusVolumetria>> estatusVolumetria() {
        return new ResponseEntity<>(catalogoReporteFinancieroService.estatusVolumetria(), HttpStatus.OK);
    }

    @GetMapping("/concepto-servicio/{idContrato}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
    public ResponseEntity<List<ConceptoServicioDTO>> conceptoServicio(@PathVariable Long idContrato) {
        return new ResponseEntity<>(catalogoReporteFinancieroService.conceptoServicio(idContrato), HttpStatus.OK);
    }
}
