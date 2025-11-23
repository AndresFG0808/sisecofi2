package com.sisecofi.contratos.controller;

import com.sisecofi.contratos.dto.ArchivoCasoNegocioDto;
import com.sisecofi.contratos.dto.CasoNegocioResponseDto;
import com.sisecofi.contratos.service.ServicioCasoNegocio;
import com.sisecofi.contratos.util.Constantes;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RequestMapping("/"+ Constantes.PATH_BASE)
@RequiredArgsConstructor
@RestController
public class CasoNegocioCtrl {

    private final ServicioCasoNegocio servicioCasoNegocio;

    @GetMapping("/layout/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<String> layoutBase(@PathVariable Long idContrato) {
        return new ResponseEntity<>(servicioCasoNegocio.obtenerLayout(idContrato), HttpStatus.OK);
    }
    
    @PostMapping("/procesar-proyeccion/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi()")
    public ResponseEntity<Map<Integer, List<String>>> procesarProyecion(@RequestBody ArchivoCasoNegocioDto archivo, @PathVariable Long idContrato) throws IOException {
        return new ResponseEntity<>(servicioCasoNegocio.procesarProyeccion(archivo, idContrato), HttpStatus.OK);
    }

    @GetMapping("/exportar-excel/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<String> exportarExcel(@PathVariable Long idContrato) throws IOException {
        return new ResponseEntity<>(servicioCasoNegocio.exportarExcel(idContrato), HttpStatus.OK);
    }
    
    @GetMapping("/caso-negocio/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<CasoNegocioResponseDto> verCasoNegocio(@PathVariable Long idContrato) throws IOException {
        return new ResponseEntity<>(servicioCasoNegocio.verCasoNegocio(idContrato), HttpStatus.OK);
    }
}
