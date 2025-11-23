package com.sisecofi.contratos.controller;

import com.sisecofi.contratos.dto.DescargaSatCloudRequest;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.contratos.ContratoPlantilla;
import com.sisecofi.contratos.service.ServicioContratoPlantilla;
import com.sisecofi.contratos.service.carpetas.ServicioArchivo;
import com.sisecofi.contratos.service.carpetas.ServicioGestionDocumental;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.libreria.comunes.dto.CarpetaCompartidaDto;
import com.sisecofi.libreria.comunes.dto.carpeta.ArchivoCargadoFaseDto;
import com.sisecofi.libreria.comunes.dto.carpeta.CarpetaDtoResponse;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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

@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
@RestController
public class ContratoPlantillaCtrl {

	private final ServicioContratoPlantilla servicioContratoPlantilla;
	private final ServicioGestionDocumental servicioGestionDocumental;
	private final ServicioArchivo servicioArchivo;

	@GetMapping("/obtener-plantillas")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolGestorDocumentalContrato()")
	public ResponseEntity<List<PlantillaVigenteModel>> obtenerPlantillas() {
		return new ResponseEntity<>(servicioContratoPlantilla.obtenerPlantillas(), HttpStatus.OK);
	}

	@PostMapping("/asociar-plantilla/{idContrato}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolGestorDocumentalContrato()")
	public ResponseEntity<Boolean> crearPena(@Valid @RequestBody ContratoPlantilla request,
			@PathVariable Long idContrato) {
		return new ResponseEntity<>(servicioContratoPlantilla.asociarPlantillas(request, idContrato), HttpStatus.OK);
	}

	@GetMapping("/obtener-asociaciones/{idContrato}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<ContratoPlantilla>> obtenerAsociaciones(@PathVariable Long idContrato) {
		return new ResponseEntity<>(servicioContratoPlantilla.obtenerAsociaciones(idContrato), HttpStatus.OK);
	}

	@PutMapping("/eliminar-asociaciones")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<Boolean> eliminarAsociacionPlantillas(@RequestBody List<Long> ids) {
		return new ResponseEntity<>(servicioContratoPlantilla.eliminarAsociacionPlantillas(ids), HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/obtener-estructura-documental/{idContrato}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<CarpetaDtoResponse>> obtenerEstructuraDocumental(@PathVariable Long idContrato) {
		return new ResponseEntity<>(servicioGestionDocumental.obtenerEstructuraDocumental(idContrato), HttpStatus.OK);
	}

	@PutMapping("/cargar-archivo-contrato")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<Boolean> cargarArchivoFaseIndividual(@ModelAttribute ArchivoCargadoFaseDto dto) {
		return new ResponseEntity<>(servicioGestionDocumental.cargarArchivoFaseIndividual(dto), HttpStatus.OK);
	}

	@PutMapping("eliminar-archivo")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<Boolean> eliminarArchivo(@RequestBody List<Archivo> archivos) {
		return new ResponseEntity<>(servicioGestionDocumental.eliminarArchivos(archivos),
				org.springframework.http.HttpStatus.OK);
	}

	@PostMapping("/descarga-sat-cloud")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolAdminMatrizDocumental() " + "|| @seguridad.validarRolApoyoAcppi() "
			+ "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() "
			+ "|| @seguridad.validarRolLiderProyecto() ||  @seguridad.validarRolGestorDocumentalContrato() ||  @seguridad.validarRolAdministradorContrato() ||  @seguridad.validarRolParticipantesAdmonEstimaciones() ||  @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<CarpetaCompartidaDto> descargaSatCloud(
			@RequestBody DescargaSatCloudRequest descargaSatCloudRequest) {
		return new ResponseEntity<>(servicioGestionDocumental.descargaSatCloud(descargaSatCloudRequest),
				org.springframework.http.HttpStatus.OK);
	}

	@PostMapping("/descarga-masiva")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolAdminMatrizDocumental() " + "|| @seguridad.validarRolApoyoAcppi() "
			+ "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() "
			+ "|| @seguridad.validarRolLiderProyecto() ||  @seguridad.validarRolGestorDocumentalContrato() ||  @seguridad.validarRolAdministradorContrato() ||  @seguridad.validarRolParticipantesAdmonEstimaciones() ||  @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<String> descargaMasiva(@RequestBody DescargaSatCloudRequest descargaSatCloudRequest) {
		return new ResponseEntity<>(servicioGestionDocumental.descargaMasiva(descargaSatCloudRequest),
				org.springframework.http.HttpStatus.OK);
	}

	@PutMapping("/descargar-archivo")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolAdminMatrizDocumental() " + "|| @seguridad.validarRolApoyoAcppi() "
			+ "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() "
			+ "|| @seguridad.validarRolLiderProyecto() ||  @seguridad.validarRolGestorDocumentalContrato() ||  @seguridad.validarRolAdministradorContrato() ||  @seguridad.validarRolParticipantesAdmonEstimaciones() ||  @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<String> descargar(@RequestParam("path") String path) {
		return new ResponseEntity<>(servicioArchivo.descargarArchivo(path, null), HttpStatus.OK);
	}
}
