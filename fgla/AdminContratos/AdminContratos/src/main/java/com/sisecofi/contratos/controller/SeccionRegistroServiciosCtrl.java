package com.sisecofi.contratos.controller;

import com.sisecofi.contratos.dto.ServicioContratoDto;
import com.sisecofi.contratos.service.ServicioSeccionesContrato;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.libreria.comunes.model.contratos.ServicioContratoModel;
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
public class SeccionRegistroServiciosCtrl {
    private final ServicioSeccionesContrato seccionesContrato;

    @PostMapping("/registro-servicios")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<String> guardarServicioContrato(@Valid @RequestBody List<ServicioContratoModel> grupoServiciosModel) {
        return new ResponseEntity<>(seccionesContrato.guardarServicioContrato(grupoServiciosModel), HttpStatus.OK);
    }

    @GetMapping("/registro-servicios/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<List<ServicioContratoDto>> obtenerServicioContrato(@PathVariable("idContrato") Long idContrato) {
        return new ResponseEntity<>(seccionesContrato.obtenerServicioContrato(idContrato), HttpStatus.OK);
    }

    @PutMapping("/registro-servicios")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<String> actualizarRegistroServicios(@RequestBody List<ServicioContratoModel> grupoServiciosModel) {
        return new ResponseEntity<>(seccionesContrato.actualizarServicioContrato(grupoServiciosModel), HttpStatus.OK);
    }

    @GetMapping("/validar-registro-servicios/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<String> validarRegistroServicios(@PathVariable("idContrato") Long idContrato) {
        return new ResponseEntity<>(seccionesContrato.validarServicioContrato(idContrato), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar-registro-servicio")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<String> eliminarRegistroServicioD(@RequestBody List<Long> idsRegistroServicio ) {
        return new ResponseEntity<>(seccionesContrato.eliminarRegistroServicioContrato(idsRegistroServicio), HttpStatus.OK);
    }
}
