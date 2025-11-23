package com.sisecofi.contratos.controller;

import com.sisecofi.libreria.comunes.model.contratos.AtrasoPrestacionModel;
import com.sisecofi.contratos.service.ServicioSeccionAtrasoPresentacion;
import com.sisecofi.contratos.util.Constantes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/"+ Constantes.PATH_BASE)
@RequiredArgsConstructor
@RestController
public class SeccionAtrasoPrestacionCtrl {

    private final ServicioSeccionAtrasoPresentacion servicioSeccionAtrasoPresentacion;

    @GetMapping("/atrasos-prestacion/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<List<AtrasoPrestacionModel>> obtenerAtrasosPrestaciones(@PathVariable("idContrato") Long idContrato) {
        return new ResponseEntity<>(servicioSeccionAtrasoPresentacion.obtenerAtrasosPrestaciones(idContrato), HttpStatus.OK);
    }

    @PostMapping("/atraso-prestacion")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto()")
    public ResponseEntity<String> filtrarRegistroServicios(@Valid @RequestBody List<AtrasoPrestacionModel> atrasoPresentacionModel) {
        return new ResponseEntity<>(servicioSeccionAtrasoPresentacion.guardarAtrasoPresentacion(atrasoPresentacionModel), HttpStatus.OK);
    }

    @GetMapping("/atraso-prestacion/{idAtrasoPresentacion}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<AtrasoPrestacionModel> obtenerGrupoServicio(@PathVariable("idAtrasoPresentacion") Long idAtrasoPresentacion) {
        return new ResponseEntity<>(servicioSeccionAtrasoPresentacion.obtenerAtrasoPresentacion(idAtrasoPresentacion), HttpStatus.OK);
    }

    @PutMapping("/atraso-prestacion")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto()")
    public ResponseEntity<String> actualizarAtrasoPresentacion(@Valid @RequestBody List<AtrasoPrestacionModel> atrasoPresentacionModel) {
        return new ResponseEntity<>(servicioSeccionAtrasoPresentacion.editarAtrasoPresnetacion(atrasoPresentacionModel), HttpStatus.OK);
    }

    @DeleteMapping("/atrasos-prestacion")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto()")
    public ResponseEntity<String> eliminarAtrasosPrestaciones(@RequestBody List<Long> idAtrasoPrestacion) {
        return new ResponseEntity<>(servicioSeccionAtrasoPresentacion.eliminarAtrasoPresentacion(idAtrasoPrestacion), HttpStatus.OK);
    }
}
