package com.sisecofi.contratos.controller.convenio_modificatorio;

import com.sisecofi.contratos.dto.ArchivoCasoNegocioDto;
import com.sisecofi.contratos.dto.CasoNegocioResponseDto;
import com.sisecofi.contratos.dto.ConvenioDto;
import com.sisecofi.contratos.dto.ConvenioModificatorioRequest;
import com.sisecofi.contratos.dto.ServiciosConvenioDto;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ServicioConvenioModel;
import com.sisecofi.contratos.service.ServicioCasoNegocio;
import com.sisecofi.contratos.service.convenio_modificatorio.ServicioConvenioModificatorio;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.text.StringEscapeUtils;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/"+ Constantes.PATH_BASE)
@RequiredArgsConstructor
@RestController
public class SeccionConveniosModificatorios {

    private final ServicioConvenioModificatorio servicioConvenioModificatorio;
    private final ServicioCasoNegocio servicioCasoNegocio;

    @PostMapping("/"+Constantes.PATH_BASE_CONVENIO)
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<Page<List<ConvenioModificatorioModel>>> obtenerContrato(@RequestBody ConvenioModificatorioRequest request) {

        return new ResponseEntity<>(servicioConvenioModificatorio.obtenerConvenioPage(request), HttpStatus.OK);
    }
    
    @GetMapping("/"+Constantes.PATH_BASE_CONVENIO+"/obtener-convenio/{idConvenioModificatorio}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<ConvenioModificatorioModel> obtenerConvenioId(@PathVariable("idConvenioModificatorio") Long idConvenioModificatorio) {
        return new ResponseEntity<>(servicioConvenioModificatorio.obtenerConvenioId(idConvenioModificatorio), HttpStatus.OK);
    }
    
    @GetMapping("/"+Constantes.PATH_BASE_CONVENIO+"/ultima-mod/{idConvenioModificatorio}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<String> obtenerUltimaMod(@PathVariable("idConvenioModificatorio") Long idConvenioModificatorio) {
    	String ultimaMod = servicioConvenioModificatorio.obtenerUltimaMod(idConvenioModificatorio);
    	String safeValue = StringEscapeUtils.escapeHtml4(ultimaMod);
    	return new ResponseEntity<>(safeValue, HttpStatus.OK);
    	
    }
    
    @PostMapping("/"+Constantes.PATH_BASE_CONVENIO+"/registrar/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<ConvenioModificatorioModel> crearConvenio(@RequestBody ConvenioModificatorioModel convenio, @PathVariable("idContrato") Long idContrato) {
    	return new ResponseEntity<>(servicioConvenioModificatorio.crearConvenio(convenio, idContrato), HttpStatus.OK);
    }
    
    @GetMapping("/"+Constantes.PATH_BASE_CONVENIO+"/layout/{idConvenioModificatorio}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<String> layoutBaseConvenio(@PathVariable Long idConvenioModificatorio) {
        return new ResponseEntity<>(servicioCasoNegocio.obtenerLayoutConvenio(idConvenioModificatorio), HttpStatus.OK);
    }
    
    @GetMapping("/"+Constantes.PATH_BASE_CONVENIO+"/caso-negocio/exportar-excel/{idConvenioModificatorio}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<String> exportarExcelConvenio(@PathVariable Long idConvenioModificatorio) throws IOException {
        return new ResponseEntity<>(servicioCasoNegocio.exportarExcelConvenio(idConvenioModificatorio), HttpStatus.OK);
    }
    
    @GetMapping("/"+Constantes.PATH_BASE_CONVENIO+"/ver-caso-negocio/{idConvenioModificatorio}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<CasoNegocioResponseDto> verCasoNegocio(@PathVariable Long idConvenioModificatorio) throws IOException {
        return new ResponseEntity<>(servicioCasoNegocio.verCasoNegocioConvenio(idConvenioModificatorio), HttpStatus.OK);
    }
    
    @PostMapping("/"+Constantes.PATH_BASE_CONVENIO+"/procesar-proyeccion/{idConvenioModificatorio}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi()")
	public ResponseEntity<Map<Integer, List<String>>> procesarProyeccionConvenio(
			@RequestBody ArchivoCasoNegocioDto archivo, @PathVariable Long idConvenioModificatorio) throws IOException {
        return new ResponseEntity<>(servicioCasoNegocio.procesarProyeccionConvenio(archivo, idConvenioModificatorio), HttpStatus.OK);
    }
     
    @GetMapping("/"+Constantes.PATH_BASE_CONVENIO+"/servicios-convenio/{idConvenioModificatorio}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<ServiciosConvenioDto> obtenerServicioConvenio(@PathVariable Long idConvenioModificatorio) {
        return new ResponseEntity<>(servicioConvenioModificatorio.obtenerServiciosDto(idConvenioModificatorio), HttpStatus.OK);
    }
    
    @PutMapping("/"+Constantes.PATH_BASE_CONVENIO+"/modificar")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<ConvenioModificatorioModel> modificarConvenio(@RequestBody ConvenioModificatorioModel convenio) {
    	return new ResponseEntity<>(servicioConvenioModificatorio.modificarConvenio(convenio), HttpStatus.OK);
    }
    
    @GetMapping("/"+Constantes.PATH_BASE_CONVENIO+"/datos-iniciales/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<ConvenioDto> datosIniciales(@PathVariable Long idContrato) {
        return new ResponseEntity<>(servicioConvenioModificatorio.datosIniciales(idContrato), HttpStatus.OK);
    }
    
    @PostMapping("/"+Constantes.PATH_BASE_CONVENIO+"/servicios-convenio/guardar")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<ServiciosConvenioDto> guardarServicios(
			@RequestBody Set<ServicioConvenioModel> lista){
        return new ResponseEntity<>(servicioConvenioModificatorio.guardarServicios(lista), HttpStatus.OK);
    }
    
    @PostMapping("/"+Constantes.PATH_BASE_CONVENIO+"/servicios-convenio/validar")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<String> validar(
			@RequestBody List<ServicioConvenioModel> lista){
        return new ResponseEntity<>(servicioConvenioModificatorio.validar(lista), HttpStatus.OK);
    }
   
    
    @GetMapping("/"+Constantes.PATH_BASE_CONVENIO+"/servicios-convenio/exportar/{idConvenio}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<String> reporteServicios(@PathVariable Long idConvenio){
        return new ResponseEntity<>(servicioConvenioModificatorio.reporteServicios(idConvenio), HttpStatus.OK);
    }
}
