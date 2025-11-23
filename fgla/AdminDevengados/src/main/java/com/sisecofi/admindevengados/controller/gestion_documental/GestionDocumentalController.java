package com.sisecofi.admindevengados.controller.gestion_documental;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.admindevengados.dto.DescargaSatCloudRequest;
import com.sisecofi.admindevengados.service.gestion_documental.ServicioGestionDocumental;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.libreria.comunes.dto.CarpetaCompartidaDto;
import com.sisecofi.libreria.comunes.dto.carpeta.ArchivoCargadoFaseDto;
import com.sisecofi.libreria.comunes.dto.carpeta.CarpetaDtoResponse;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE)
public class GestionDocumentalController {

	private final ServicioGestionDocumental servicioGestionDocumental;
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/" +Constantes.PATH_BASE_DICTAMEN+"/gestion-documental/{idDitamen}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() "
			+ "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta()"
			+ "|| @seguridad.validarRolLiderProyecto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<CarpetaDtoResponse>> obtenerContratoNombreCorto(@PathVariable("idDitamen") Long idDitamen) {
		return new ResponseEntity<>(servicioGestionDocumental.obtenerEstructuraDocumental(idDitamen), HttpStatus.OK);
	}
	
	@PutMapping("/" +Constantes.PATH_BASE_DICTAMEN+"/cargar-archivo-dictamen")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<Boolean> cargarArchivoFaseIndividual(@ModelAttribute ArchivoCargadoFaseDto dto) {
		return new ResponseEntity<>(servicioGestionDocumental.cargarArchivoFaseIndividual(dto),
				org.springframework.http.HttpStatus.OK);
	}
	
	@PostMapping("/" +Constantes.PATH_BASE_DICTAMEN+"/eliminar-archivos-gestion")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<Boolean> eliminar(@RequestBody List<Archivo> lista) {
		return new ResponseEntity<>(servicioGestionDocumental.eliminarArchivos(lista),
				org.springframework.http.HttpStatus.OK);
	}
	
	@PostMapping("/" +Constantes.PATH_BASE_DICTAMEN+"/descarga-sat-cloud")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolAdminMatrizDocumental() " + "|| @seguridad.validarRolApoyoAcppi() "
			+ "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() "
			+ "|| @seguridad.validarRolLiderProyecto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<CarpetaCompartidaDto> descargaSatCloud(
			@RequestBody DescargaSatCloudRequest descargaSatCloudRequest) {
		return new ResponseEntity<>(servicioGestionDocumental.descargaSatCloud(descargaSatCloudRequest),
				org.springframework.http.HttpStatus.OK);
	}

	@PostMapping("/" +Constantes.PATH_BASE_DICTAMEN+"/descarga-masiva")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolAdminMatrizDocumental() " + "|| @seguridad.validarRolApoyoAcppi() "
			+ "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() "
			+ "|| @seguridad.validarRolLiderProyecto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<String> descargaMasiva(@RequestBody DescargaSatCloudRequest descargaSatCloudRequest) {
		return new ResponseEntity<>(servicioGestionDocumental.descargaMasiva(descargaSatCloudRequest),
				org.springframework.http.HttpStatus.OK);
	}
	
	@PutMapping("/" +Constantes.PATH_BASE_DICTAMEN+"/descargar-archivo")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolAdminMatrizDocumental() " + "|| @seguridad.validarRolApoyoAcppi() "
			+ "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() "
			+ "|| @seguridad.validarRolLiderProyecto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<String> descargar(@RequestParam("path") String path){
        return new ResponseEntity<>(servicioGestionDocumental.descargarArchivo(path), HttpStatus.OK);
    }
	
	
}
