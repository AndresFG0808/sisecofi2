package com.sisecofi.contratos.controller;

import com.sisecofi.contratos.dto.ActualizarGrupoServicioDto;
import com.sisecofi.contratos.dto.GrupoServicioDto;
import com.sisecofi.contratos.service.ServicioSeccionesContrato;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.libreria.comunes.model.contratos.GrupoServiciosModel;
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
public class SeccionGruposServicioCtrl {

    private final ServicioSeccionesContrato seccionesContrato;

    @PostMapping("/guardar-grupo-servicio")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<String> grupoServicio(@Valid @RequestBody List<GrupoServiciosModel> grupo) {
        return new ResponseEntity<>(seccionesContrato.guardarGrupoServicio(grupo), HttpStatus.OK);
    }

    @GetMapping("/grupo-servicio/{idContrato}")
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
    public ResponseEntity<List<GrupoServicioDto>> obtenerGrupoServicio(@PathVariable("idContrato") Long idContrato) {
        return new ResponseEntity<>(seccionesContrato.obtenerGrupoServicio(idContrato), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar-grupo-servicio")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<String> eliminarGrupoServicioD(@RequestBody List<Long> idsGrupoServicio ) {
        return new ResponseEntity<>(seccionesContrato.eliminarGrupoiServicio(idsGrupoServicio), HttpStatus.OK);
    }

    @PutMapping("/actualizar-grupo-servicio")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<String> actualizarGrupoServicio(@RequestBody List<ActualizarGrupoServicioDto> grupoServicioDto ) {
        return new ResponseEntity<>(seccionesContrato.actualizarGrupoSetvicio(grupoServicioDto), HttpStatus.OK);
    }

}
