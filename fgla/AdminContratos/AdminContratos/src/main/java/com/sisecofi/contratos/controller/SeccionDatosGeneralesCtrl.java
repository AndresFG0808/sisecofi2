package com.sisecofi.contratos.controller;

import com.sisecofi.contratos.dto.EliminarParticipantesDto;
import com.sisecofi.contratos.dto.ProveedoresDto;
import com.sisecofi.contratos.service.ServicioDatosGeneralesContrato;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.libreria.comunes.dto.contrato.DatosGeneralesContratoDto;
import com.sisecofi.libreria.comunes.dto.contrato.DatosGeneralesResponseDto;
import com.sisecofi.libreria.comunes.dto.contrato.ParticipantesAdminContratoDto;
import com.sisecofi.libreria.comunes.model.contratos.DatosGeneralesContratoModel;
import com.sisecofi.libreria.comunes.model.contratos.ParticipantesAdministracionModel;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
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
public class SeccionDatosGeneralesCtrl {

    private final ServicioDatosGeneralesContrato servicioDatosGeneralesContrato;

    @PostMapping("/datos-generales")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<DatosGeneralesContratoModel> guardarDatosGenerales(@Valid @RequestBody DatosGeneralesContratoDto datosGeneralesContratoDto) {
        return new ResponseEntity<>(servicioDatosGeneralesContrato.guardarDatosGenerales(datosGeneralesContratoDto), HttpStatus.OK);
    }

    @GetMapping("/datos-generales/{idContrato}")
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
    public ResponseEntity<DatosGeneralesResponseDto> obtenerDatosGenerales(@PathVariable("idContrato") Long idContrato){
        return new ResponseEntity<>(servicioDatosGeneralesContrato.obtenerDatosGnerales(idContrato), HttpStatus.OK);
    }

    @PutMapping("/datos-generales")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<DatosGeneralesContratoModel> actualizarDatosGenerales(@Valid @RequestBody DatosGeneralesContratoDto datosGeneralesContratoDto) {
        return new ResponseEntity<>(servicioDatosGeneralesContrato.actualizarDatosGenerales(datosGeneralesContratoDto), HttpStatus.OK);
    }

    @PostMapping("/datos-generales/agregar-proveedores")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity< List<ProveedorModel>> obtenerDatosGenerales(@RequestBody ProveedoresDto proveedoresDto){
        return new ResponseEntity<>(servicioDatosGeneralesContrato.agregarProveedor(proveedoresDto), HttpStatus.OK);
    }

    @DeleteMapping("/datos-generales/eliminar-proveedores")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<String> eliminarDatosGenerales(@RequestBody ProveedoresDto proveedoresDto){
        return new ResponseEntity<>(servicioDatosGeneralesContrato.eliminarProveedor(proveedoresDto), HttpStatus.OK);
    }

    @PostMapping("/participantes-contrato")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<String> guardadAdministradoresContrato(@Valid @RequestBody List<ParticipantesAdministracionModel> datosGeneralesContratoDto) {
        return new ResponseEntity<>(servicioDatosGeneralesContrato.guardarParticipantesAdminContrato(datosGeneralesContratoDto), HttpStatus.OK);
    }

    @PutMapping("/participantes-contrato")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<String> actualizarAdministradoresContrato(@Valid @RequestBody List<ParticipantesAdministracionModel> datosGeneralesContratoDto) {
        return new ResponseEntity<>(servicioDatosGeneralesContrato.editarParticipantesAdminContrato(datosGeneralesContratoDto), HttpStatus.OK);
    }

    @GetMapping("/datos-generales-paricipantes/{idContrato}")
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
    public ResponseEntity<  List<ParticipantesAdminContratoDto>> obtenerParticipantesAdminContrato(@PathVariable("idContrato") Long idContrato){
        return new ResponseEntity<>(servicioDatosGeneralesContrato.obtenerParticipantesAdminContrato(idContrato), HttpStatus.OK);
    }

    @DeleteMapping("/datos-generales-participantes")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<String> eliminarParticipantesAdminContrato(@RequestBody EliminarParticipantesDto eliminarParticipantesDto){
        return new ResponseEntity<>(servicioDatosGeneralesContrato.eliminarParticipantesAdminContrato(eliminarParticipantesDto), HttpStatus.OK);
    }
}
