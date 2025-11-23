package com.sisecofi.proyectos.controller;

import com.sisecofi.libreria.comunes.dto.ResponseGeneric;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ComiteProyectoModel;
import com.sisecofi.proyectos.dto.*;
import com.sisecofi.proyectos.service.ServicioComite;
import com.sisecofi.proyectos.util.Constantes;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/"+ Constantes.PATH_BASE)
@RequiredArgsConstructor
public class ComitesCtrl {

    private final ServicioComite comite;


    @GetMapping("/informacion-comites/{idProyecto}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()" +
            " || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi()" +
            " || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() " +
            "|| @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato()" +
            " || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion()" +
            " || @seguridad.validarRolVerificadorGeneral() " + "|| @seguridad.validarRolLiderProyecto()" +
            "|| @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<ResponseGeneric<List<ResponseComiteInfo>>> obtenerInformacionComiteProyectos(@PathVariable("idProyecto") Integer idProyecto){

        List<ResponseComiteInfo> comiteProyectoModelRes = comite.obtenerComiteInformacion(idProyecto);
        ResponseGeneric< List<ResponseComiteInfo>> data = ResponseGeneric.<List<ResponseComiteInfo>>builder().build();
        data.setData(comiteProyectoModelRes);
        data.setMsj(ErroresEnum.COMITE_PROYECTO_CREADO);
        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }

    @PostMapping("/comite-proyecto")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta()|| @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<ResponseGeneric<ComiteProyectoModel>> guardarComiteProyecto(@Valid @RequestBody ComiteProyectoDto comiteProyectoModel){

        ComiteProyectoModel comiteProyectoModelRes = comite.guardarComiteProyecto(comiteProyectoModel);
        ResponseGeneric<ComiteProyectoModel> data = ResponseGeneric.<ComiteProyectoModel>builder().build();
        data.setData(comiteProyectoModelRes);
        data.setMsj(ErroresEnum.COMITE_PROYECTO_CREADO);
        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }

    @PutMapping("/comite-proyecto")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta()|| @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<ResponseGeneric<InformacionComiteDto>> comiteProyecto(@Valid @RequestBody InformacionComiteDto comiteProyectoModel){

        InformacionComiteDto comiteProyectoDesdeDb = comite.actualizarComiteProyecto(comiteProyectoModel);
        ResponseGeneric<InformacionComiteDto> data = ResponseGeneric.<InformacionComiteDto>builder().build();
        data.setData(comiteProyectoDesdeDb);
        data.setMsj(ErroresEnum.COMITE_PROYECTO_ACTUALIZADO);

        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }

    @GetMapping("/detalle-comite/{idComiteProyecto}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta()|| @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<ResponseGeneric<ResponseComite>> comiteDetalle(@PathVariable("idComiteProyecto") Integer idComiteProyecto) {

        ResponseComite responseComite = comite.obtenerDetalleComite(idComiteProyecto);
        ResponseGeneric<ResponseComite> data = ResponseGeneric.<ResponseComite>builder().build();
        data.setData(responseComite);
        data.setMsj(ErroresEnum.COMITE_PROYECTO_OBTENIDO);

        return new ResponseEntity<>(data , HttpStatus.OK);
    }

    @DeleteMapping("informacion-comite/{idComiteProyecto}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta()|| @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<ResponseGeneric<Object>> eliminarInformacionComite(@PathVariable("idComiteProyecto") Integer idComiteProyecto){
        String response = comite.eliminarInformacionComite(idComiteProyecto);
        ResponseGeneric<Object> data = ResponseGeneric.builder().build();
        data.setData(response);
        data.setMsj(ErroresEnum.ESTATUS_DE_CONSULTA_OK);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
