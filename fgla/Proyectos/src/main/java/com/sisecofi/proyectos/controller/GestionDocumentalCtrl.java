package com.sisecofi.proyectos.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sisecofi.libreria.comunes.dto.CarpetaCompartidaDto;
import com.sisecofi.libreria.comunes.dto.carpeta.ArchivoCargadoFaseDto;
import com.sisecofi.libreria.comunes.dto.carpeta.CarpetaDtoResponse;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.proyectos.dto.DescargaSatCloudRequest;
import com.sisecofi.proyectos.dto.GestionDocumentalRequest;
import com.sisecofi.proyectos.service.ServicioGestionDocumental;
import com.sisecofi.proyectos.util.Constantes;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class GestionDocumentalCtrl {

	private final ServicioGestionDocumental servicioGestionDocumental;

	@SuppressWarnings("rawtypes")
	@GetMapping("/matriz-documental/{idProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolParticipantesAdmonDictamen()" +
			"|| @seguridad.validarRolLiderProyecto()  || @seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<List<CarpetaDtoResponse>> obtenerEstructuraDocumental(@PathVariable Long idProyecto) {
		return new ResponseEntity<>(servicioGestionDocumental.obtenerEstructuraDocumental(idProyecto),
				org.springframework.http.HttpStatus.OK);
	}

	@PutMapping("/cargar-archivo-fase")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolLiderProyecto() ")
	public ResponseEntity<Boolean> cargarArchivoFase(@RequestBody GestionDocumentalRequest archivos) {
		return new ResponseEntity<>(servicioGestionDocumental.guardarTabla(archivos),
				org.springframework.http.HttpStatus.OK);
	}

	@PutMapping("/cargar-archivo-fase/individual")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolLiderProyecto() ")
	public ResponseEntity<Boolean> cargarArchivoFaseIndividual(@ModelAttribute ArchivoCargadoFaseDto dto) {
		return new ResponseEntity<>(servicioGestionDocumental.cargarArchivoFaseIndividual(dto),
				org.springframework.http.HttpStatus.OK);
	}

	@DeleteMapping("eliminar-archivo")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolAdminMatrizDocumental() " + "|| @seguridad.validarRolApoyoAcppi() "
			+ "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() "
			+ "|| @seguridad.validarRolLiderProyecto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<Boolean> eliminarArchivo(@RequestBody List<Archivo> archivos) {
		return new ResponseEntity<>(servicioGestionDocumental.eliminarArchivos(archivos),
				org.springframework.http.HttpStatus.OK);
	}

	@PostMapping("/descarga-sat-cloud")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolAdminMatrizDocumental() " + "|| @seguridad.validarRolApoyoAcppi() "
			+ "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() "
			+ "|| @seguridad.validarRolLiderProyecto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<CarpetaCompartidaDto> descargaSatCloud(
			@RequestBody DescargaSatCloudRequest descargaSatCloudRequest) {
		return new ResponseEntity<>(servicioGestionDocumental.descargaSatCloud(descargaSatCloudRequest),
				org.springframework.http.HttpStatus.OK);
	}

	@PostMapping("/descarga-masiva")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolAdminMatrizDocumental() " + "|| @seguridad.validarRolApoyoAcppi() "
			+ "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() "
			+ "|| @seguridad.validarRolLiderProyecto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<String> descargaMasiva(@RequestBody DescargaSatCloudRequest descargaSatCloudRequest) {
		return new ResponseEntity<>(servicioGestionDocumental.descargaMasiva(descargaSatCloudRequest),
				org.springframework.http.HttpStatus.OK);
	}
	
	@PostMapping("/eliminar-archivos-gestion")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolAdminMatrizDocumental() " + "|| @seguridad.validarRolApoyoAcppi() "
			+ "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() "
			+ "|| @seguridad.validarRolLiderProyecto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen()")
	public ResponseEntity<Boolean> eliminar(@RequestBody List<Archivo> lista) {
		return new ResponseEntity<>(servicioGestionDocumental.eliminarArchivos(lista),
				org.springframework.http.HttpStatus.OK);
	}

}
