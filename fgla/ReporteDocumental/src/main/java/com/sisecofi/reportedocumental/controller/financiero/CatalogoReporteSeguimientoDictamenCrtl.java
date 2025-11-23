package com.sisecofi.reportedocumental.controller.financiero;

import com.sisecofi.libreria.comunes.dto.reportedinamico.ProyectoNombreDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.reportedocumental.dto.financiero.FiltroNombreContratoBaseDTO;
import com.sisecofi.reportedocumental.dto.financiero.NombreContratoDTO;
import com.sisecofi.reportedocumental.dto.financiero.VerificadorContratoDto;
import com.sisecofi.reportedocumental.service.financiero.CatalogoReporteFinancieroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/financiero/catalogo/seguimiento-dictamen")
@RequiredArgsConstructor
public class CatalogoReporteSeguimientoDictamenCrtl {
    private final CatalogoReporteFinancieroService catalogoReporteFinancieroService;

    @GetMapping("/contrato-vigente")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
    public ResponseEntity<List<BaseCatalogoModel>> contratoVigente() {
        return new ResponseEntity<>(catalogoReporteFinancieroService.contratoVigente(), HttpStatus.OK);
    }

    @GetMapping("/estatus-dictamen")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
    public ResponseEntity<List<BaseCatalogoModel>> estatusDictamen() {
        return new ResponseEntity<>(catalogoReporteFinancieroService.estatusDictamen(), HttpStatus.OK);
    }

    @GetMapping("/nombre-corto-proyecto")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
    public ResponseEntity<List<ProyectoNombreDto>> buscarTodosProyectos() {
        return new ResponseEntity<>(catalogoReporteFinancieroService.buscarTodosProyectos(), HttpStatus.OK);
    }

    @PostMapping("/nombre-corto-contrato")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
    public ResponseEntity<List<NombreContratoDTO>> contratos(@RequestBody FiltroNombreContratoBaseDTO dto) {
        return new ResponseEntity<>(catalogoReporteFinancieroService.filtrarContratos(dto), HttpStatus.OK);
    }

    @GetMapping("/verificador-contrato/{nombreCortoContrato}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdministradorContrato()")
    public ResponseEntity<List<VerificadorContratoDto>> listarVerificadoresPorContrato(@PathVariable("nombreCortoContrato") String nombreCortoContrato) {
        return new ResponseEntity<>(catalogoReporteFinancieroService.listarVerificadoresPorContrato(nombreCortoContrato), HttpStatus.OK);
    }
}
