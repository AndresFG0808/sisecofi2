package com.sisecofi.admindevengados.controller;

import com.sisecofi.admindevengados.dto.*;
import com.sisecofi.admindevengados.model.SolicitudFacturaModel;
import com.sisecofi.admindevengados.service.ProformaService;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE)
public class ProformaController {
    private final ProformaService proformaService;

    @PostMapping("/proforma/guardar")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
                        "@seguridad.validarRolAdminSistemaSecundario() || " +
                        "@seguridad.validarRolParticipantesAdmonVerificacion() || " +
                        "@seguridad.validarRolVerificadorGeneral() || " +
                        "@seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<List<ResumenProformaDto>> guardar(@RequestBody List<DescuentoDto> listDescuentoDto) {
        return new ResponseEntity<>(proformaService.guardarProforma(listDescuentoDto), HttpStatus.OK);
    }

    @PostMapping("/resumen-deducciones-descuentos-penalizaciones")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
                        "@seguridad.validarRolAdminSistemaSecundario() || " +
                        "@seguridad.validarRolParticipantesAdmonVerificacion() || " +
                        "@seguridad.validarRolVerificadorGeneral() || " +
                        "@seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<List<ResumenProformaDto>> resumenProforma(@RequestBody DictamenId idDictamen){
        return new ResponseEntity<>(proformaService.obtenerResumenProforma(idDictamen.getIdDictamen()), HttpStatus.OK);
    }

    @PostMapping("/obtener-plantilla-proforma")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
    "@seguridad.validarRolAdminSistemaSecundario() || " +
    "@seguridad.validarRolParticipantesAdmonVerificacion() || " +
    "@seguridad.validarRolVerificadorGeneral() || " +
    "@seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<PlantillaProformaDto> obtenerPlantillaProforma(@RequestBody DictamenId dictamenId){
        return new ResponseEntity<>(proformaService.obtenerPlantillaProforma(dictamenId.getIdDictamen()), HttpStatus.OK);
    }
 
    @PostMapping("/solicitud-factura/guardar")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
                        "@seguridad.validarRolAdminSistemaSecundario() || " +
                        "@seguridad.validarRolParticipantesAdmonVerificacion() || " +
                        "@seguridad.validarRolVerificadorGeneral() || " +
                        "@seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<SolicitudFacturaResponseDto> guardarSolicitudFactura(@Valid @ModelAttribute SolicitudFacturaCreateDto request) {
        return new ResponseEntity<>(this.proformaService.solicitudFacturaProformaGuardar(request), HttpStatus.CREATED);
    }

    @PostMapping("/solicitud-factura/actualizar")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
                        "@seguridad.validarRolAdminSistemaSecundario() || " +
                        "@seguridad.validarRolParticipantesAdmonVerificacion() || " +
                        "@seguridad.validarRolVerificadorGeneral() || " +
                        "@seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<SolicitudFacturaResponseDto> actualizarSolicitudFactura(@ModelAttribute SolicitudFacturaUpdateDto request) {
        return new ResponseEntity<>(this.proformaService.solicitudFacturaProformaActualizar(request), HttpStatus.CREATED);
    }

    @GetMapping("/catalogo-desgloce")
    @PreAuthorize("@seguridad.validarRolAdminSistema() " +
            "||  @seguridad.validarRolAdminSistemaSecundario() " +
            "|| @seguridad.validarRolAdministradorContrato() " +
            "|| @seguridad.validarRolParticipantesAdmonDictamen() " +
            "|| @seguridad.validarRolApoyoAcppi() " +
            "|| @seguridad.validarRolVerificadorGeneral() " +
            "|| @seguridad.validarRolLiderProyecto() " +
            "|| @seguridad.validarRolUsuarioConsulta() " +
            "|| @seguridad.validarRolVerificadorEspecificoContrato() " +
            "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto()")
    public ResponseEntity<List<DesgloceProformaDto>> obtenerCatalogoDesglose() {
        return new ResponseEntity<>(proformaService.obtenerCatalogoDesglose(), HttpStatus.OK);
    }


    @PostMapping("/buscar-solicitud-factura")
    @PreAuthorize("@seguridad.validarRolAdminSistema() " +
            "|| @seguridad.validarRolAdminSistemaSecundario() " +
            "|| @seguridad.validarRolApoyoAcppi() " +
            "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
            "|| @seguridad.validarRolUsuarioConsulta() " +
            "|| @seguridad.validarRolVerificadorGeneral() " +
            "|| @seguridad.validarRolLiderProyecto() " +
            "|| @seguridad.validarRolAdministradorContrato() " +
            "|| @seguridad.validarRolVerificadorEspecificoContrato() " +
            "|| @seguridad.validarRolParticipantesAdmonDictamen()")
    public ResponseEntity<SolicitudFacturaBanderaDto> buscarPorIdDictamen(@RequestBody Map<String, String> requestBody) {
        String dictamenIdStr = requestBody.get("idDictamen");
        Long idDictamen = Long.parseLong(dictamenIdStr);
        SolicitudFacturaBanderaDto facturas = proformaService.buscarPorIdDictamen(idDictamen);

        if (facturas != null) return ResponseEntity.ok(facturas);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/solicitud-factura/descarga/oficio")
    @PreAuthorize("@seguridad.validarRolAdminSistema() " +
            "|| @seguridad.validarRolAdminSistemaSecundario() " +
            "|| @seguridad.validarRolApoyoAcppi() " +
            "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
            "|| @seguridad.validarRolUsuarioConsulta() " +
            "|| @seguridad.validarRolLiderProyecto() " +
            "|| @seguridad.validarRolVerificadorGeneral() " +
            "|| @seguridad.validarRolAdministradorContrato() " +
            "|| @seguridad.validarRolVerificadorEspecificoContrato() " +
            "|| @seguridad.validarRolParticipantesAdmonDictamen()")
    public ResponseEntity<String> solicitudFacturaDescargarOficio(@RequestParam("path") String path) {
        return new ResponseEntity<>(this.proformaService.solicitudFaturaDescargarOficio(path), HttpStatus.OK);
    }
    
    @GetMapping("/validar-factura-recibida/{idDictamen}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() " +
            "|| @seguridad.validarRolAdminSistemaSecundario() " +
            "|| @seguridad.validarRolApoyoAcppi() " +
            "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
            "|| @seguridad.validarRolUsuarioConsulta() " +
            "|| @seguridad.validarRolVerificadorGeneral() " +
            "|| @seguridad.validarRolLiderProyecto() " +
            "|| @seguridad.validarRolAdministradorContrato() " +
            "|| @seguridad.validarRolVerificadorEspecificoContrato() " +
            "|| @seguridad.validarRolParticipantesAdmonDictamen()")
    public ResponseEntity<SolicitudFacturaModel> solicitudFacturaValidar(@PathVariable Long idDictamen) {
        SolicitudFacturaModel response = this.proformaService.validarSolicitudFacrura(idDictamen);

        if (response != null) return ResponseEntity.ok(response);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
