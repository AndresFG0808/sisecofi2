package com.sisecofi.admindevengados.controller.solicitudpago;


import com.sisecofi.admindevengados.dto.solicitudpago.*;
import com.sisecofi.admindevengados.service.solicitudpago.SolicitudPagoService;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.libreria.comunes.dto.plantillador.ContenidoPlantilladorPdfReponseDto;
import com.sisecofi.libreria.comunes.model.plantillador.PlantilladorModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE)
public class SolicitudPagoCtrl {

    private final SolicitudPagoService solicitudPagoService;

    @PostMapping("/solicitud-pago")
	@PreAuthorize("@seguridad.validarRolAdminSistema() ||  @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen()")
    public ResponseEntity<SolicitudPagoResponseDto> guardarSolicutudPago(@RequestBody SolicitudPagoDto request) {
        return new ResponseEntity<>(solicitudPagoService.guardarSolicitudPago(request), HttpStatus.OK);
    }

    @PutMapping("/editar-solicitud-pago")
	@PreAuthorize("@seguridad.validarRolAdminSistema() ||  @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen()")
    public ResponseEntity<String> editarSolicutudPago(@RequestBody EditarSolicitudPagoDto request) {
        return new ResponseEntity<>(solicitudPagoService.editarSolicitudPago(request), HttpStatus.OK);
    }

    @PutMapping("/editar-referencia-pago")
	@PreAuthorize("@seguridad.validarRolAdminSistema() ||  @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen()")
    public ResponseEntity<String> editarReferenciaSolicutudPago(@RequestBody ReferenciaPagoDto request) {
        return new ResponseEntity<>(solicitudPagoService.editarReferenciaPago(request), HttpStatus.OK);
    }

    @PutMapping("/estatus-solicitud-pago")
	@PreAuthorize("@seguridad.validarRolAdminSistema() ||  @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen()")
    public ResponseEntity<String> estatusSolicitudPago(@RequestBody EstatusSolicitudPagoDto request) {
        return new ResponseEntity<>(solicitudPagoService.editarEstatusSolicitudPago(request), HttpStatus.OK);
    }

    @PostMapping("/referencia-pago")
	@PreAuthorize("@seguridad.validarRolAdminSistema() ||  @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen()")
    public ResponseEntity<String> guardarReferenciaPago(@Valid @RequestBody ReferenciaPagoDto request) {
        return new ResponseEntity<>(solicitudPagoService.guardarReferenciaPago(request), HttpStatus.OK);
    }


    @GetMapping("/solicitud-pago/plantillas")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<List<PlantilladorModel>> obtenerPlantilladores() {
        return new ResponseEntity<>(solicitudPagoService.obtenerPlantillas(), HttpStatus.OK);
    }

    @PostMapping("/solicitud-pago/plantilla-base")
    @PreAuthorize("@seguridad.validarRolAdminSistema() " +
            "|| @seguridad.validarRolAdminSistemaSecundario() " +
            "|| @seguridad.validarRolApoyoAcppi() " +
            "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
            "|| @seguridad.validarRolUsuarioConsulta() " +
            "|| @seguridad.validarRolLiderProyecto() " +
            "|| @seguridad.validarRolAdministradorContrato() " +
            "|| @seguridad.validarRolParticipantesAdmonDictamen()")
    public ResponseEntity<ContenidoPlantilladorPdfReponseDto> obtenerPlantillaBase(@RequestBody RequestPlantillaBaseDto request) {
        return new ResponseEntity<>(solicitudPagoService.obtenerPlantillaBase(request), HttpStatus.OK);
    }

    @GetMapping("/referencia-pago/validar-archivo-nafin/{idReferenciaPago}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() " +
            "|| @seguridad.validarRolAdminSistemaSecundario() " +
            "|| @seguridad.validarRolApoyoAcppi() " +
            "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
            "|| @seguridad.validarRolUsuarioConsulta() " +
            "|| @seguridad.validarRolLiderProyecto() " +
            "|| @seguridad.validarRolAdministradorContrato() " +
            "|| @seguridad.validarRolParticipantesAdmonDictamen()")
    public ResponseEntity<Boolean> validarArchivosExistentes(@PathVariable("idReferenciaPago") Long idReferenciaPago) {
        return new ResponseEntity<>(solicitudPagoService.validarArchivoReferenciaPago(idReferenciaPago), HttpStatus.OK);
    }

    @GetMapping("/referencia-pago/{idSolicitudPago}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() " +
            "|| @seguridad.validarRolAdminSistemaSecundario() " +
            "|| @seguridad.validarRolApoyoAcppi() " +
            "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
            "|| @seguridad.validarRolUsuarioConsulta() " +
            "|| @seguridad.validarRolLiderProyecto() " +
            "|| @seguridad.validarRolAdministradorContrato() " +
            "|| @seguridad.validarRolParticipantesAdmonDictamen()")
    public ResponseEntity<SolicitudPagoResponseDto> obtenerReferenciaPago(@PathVariable("idSolicitudPago") Long idSolicitudPago) {
        return new ResponseEntity<>(solicitudPagoService.obtenerReferenciaPago(idSolicitudPago), HttpStatus.OK);
    }

    @PostMapping("/obtener-solicitud-pago")
    @PreAuthorize("@seguridad.validarRolAdminSistema() " +
            "|| @seguridad.validarRolAdminSistemaSecundario() " +
            "|| @seguridad.validarRolApoyoAcppi() " +
            "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
            "|| @seguridad.validarRolUsuarioConsulta() " +
            "|| @seguridad.validarRolLiderProyecto() " +
            "|| @seguridad.validarRolAdministradorContrato() " +
            "|| @seguridad.validarRolParticipantesAdmonDictamen()")
    public ResponseEntity<List<SolicitudPagoReponseDto>> obtenerSolicitudPago(@RequestBody ObtenerSolicitudPagoRequest request) {
        return new ResponseEntity<>(solicitudPagoService.obtenerSolicitudPago(request), HttpStatus.OK);
    }

}
