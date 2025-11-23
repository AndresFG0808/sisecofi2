package com.sisecofi.contratos.controller;

import com.sisecofi.libreria.comunes.model.contratos.PenaContractualContratoModel;
import com.sisecofi.contratos.service.ServicioPenasContractuales;
import com.sisecofi.contratos.util.Constantes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RequestMapping("/"+ Constantes.PATH_BASE)
@RequiredArgsConstructor
@RestController
public class PenaContractualCtrl {

    private final ServicioPenasContractuales servicioPenasContractuales;


    @PostMapping("/crear-pena-contractual/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto()")
    public ResponseEntity<Boolean> crearPena(@Valid @RequestBody List<PenaContractualContratoModel> request, @PathVariable Long idContrato) {
        return new ResponseEntity<>(servicioPenasContractuales.crearPena(request, idContrato), HttpStatus.OK);
    }
    
    @PutMapping("/actualizar-pena-contractual")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto()")
    public ResponseEntity<Boolean> editarPena(@Valid @RequestBody List<PenaContractualContratoModel> request) {
        return new ResponseEntity<>(servicioPenasContractuales.editarPena(request), HttpStatus.OK);
    }
    
    //cambiar a delete si es necesario
    @PutMapping("/eliminar-pena-contractual")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto()")
    public ResponseEntity<Boolean> eliminarPena(@Valid @RequestParam("idPena") List<Long> idPena) {
        return new ResponseEntity<>(servicioPenasContractuales.eliminarPena(idPena), HttpStatus.OK);
    }
    

    @GetMapping("/obtener-penas-contractuales/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<List<PenaContractualContratoModel>> obtenerPenas(@PathVariable Long idContrato){
        return new ResponseEntity<>(servicioPenasContractuales.obtenerPenas(idContrato), HttpStatus.OK);
    }
    
    @GetMapping("/reporte-penas-contractuales/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<String> generarReporte(@PathVariable Long idContrato){
        return new ResponseEntity<>(servicioPenasContractuales.generarReporte(idContrato), HttpStatus.OK);
    }
}
