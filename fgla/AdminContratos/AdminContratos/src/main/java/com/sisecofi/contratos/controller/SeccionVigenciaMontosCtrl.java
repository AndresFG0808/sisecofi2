package com.sisecofi.contratos.controller;

import com.sisecofi.contratos.dto.EliminarVigenciaMontosDto;
import com.sisecofi.libreria.comunes.dto.contrato.VigenciaMontoDto;
import com.sisecofi.contratos.service.ServicioSeccionesContrato;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.libreria.comunes.model.contratos.VigenciaMontosModel;
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
public class SeccionVigenciaMontosCtrl {
    private final ServicioSeccionesContrato seccionesContrato;


    @PostMapping("/vigencia-montos")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<VigenciaMontosModel> vigenciaMontos(@Valid @RequestBody VigenciaMontosModel vigencia) {
        return new ResponseEntity<>(seccionesContrato.vigenciaMontos(vigencia), HttpStatus.OK);
    }

    @GetMapping("/vigencia-montos")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
            "@seguridad.validarRolAdminSistemaSecundario() || " +
            "@seguridad.validarRolVerificadorEspecificoContrato() || " +
            "@seguridad.validarRolAdminMatrizDocumental() || " +
            "@seguridad.validarRolApoyoAcppi() ||" +
            "@seguridad.validarRolApoyoAlLiderTecnicoProyeto() ||"+
            "@seguridad.validarRolGestorDocumentalContrato() ||" +
            "@seguridad.validarRolUsuarioConsulta() ||" +
            "@seguridad.validarRolLiderProyecto() ||" +
            "@seguridad.validarRolAdministradorContrato()  ||" +
            "@seguridad.validarRolParticipantesAdmonEstimaciones() ||"+
            "@seguridad.validarRolParticipantesAdmonDictamen() ||"+
            "@seguridad.validarRolParticipantesAdmonVerificacion() ||"+
            "@seguridad.validarRolVerificadorGeneral() ||" +
            "@seguridad.validarRolVerificadorEspecificoContratol()"

    )
    public ResponseEntity<List<VigenciaMontoDto>> obtenerVigenciaMontos() {
        return new ResponseEntity<>(seccionesContrato.obtenerVigenciaMontos(), HttpStatus.OK);
    }

    @PostMapping("/eliminar-vigencia-montos")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<String> eliminarVigenciaMontos(@RequestBody EliminarVigenciaMontosDto eliminar) {
        return new ResponseEntity<>(seccionesContrato.eliminarVigenciaMonto(eliminar), HttpStatus.OK);
    }

    @PutMapping("/vigencia-montos")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<String> actualizarvigenciaMontos(@Valid @RequestBody VigenciaMontosModel vigencia) {
        return new ResponseEntity<>(seccionesContrato.actualizarVigenciaMontos(vigencia), HttpStatus.OK);
    }

    @GetMapping("/vigencia-montos/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
            "@seguridad.validarRolAdminSistemaSecundario() || " +
            "@seguridad.validarRolVerificadorEspecificoContrato() || " +
            "@seguridad.validarRolAdminMatrizDocumental() || " +
            "@seguridad.validarRolApoyoAcppi() ||" +
            "@seguridad.validarRolApoyoAlLiderTecnicoProyeto() ||"+
            "@seguridad.validarRolGestorDocumentalContrato() ||" +
            "@seguridad.validarRolUsuarioConsulta() ||" +
            "@seguridad.validarRolLiderProyecto() ||" +
            "@seguridad.validarRolAdministradorContrato()  ||" +
            "@seguridad.validarRolParticipantesAdmonEstimaciones() ||"+
            "@seguridad.validarRolParticipantesAdmonDictamen() ||"+
            "@seguridad.validarRolParticipantesAdmonVerificacion() ||"+
            "@seguridad.validarRolVerificadorGeneral() ||" +
            "@seguridad.validarRolVerificadorEspecificoContratol()"

    )
    public ResponseEntity<VigenciaMontoDto> obtenerVigenciaMontos(@PathVariable("idContrato") Long idVigenciaMonto) {
        return new ResponseEntity<>(seccionesContrato.obtenerVigenciaMonto(idVigenciaMonto), HttpStatus.OK);
    }
}
