package com.sisecofi.proyectos.controller.cierre;

import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.libreria.comunes.dto.CarpetaCompartidaDto;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.plantillador.PlantilladorModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.proyectos.dto.DescargaSatCloudRequest;
import com.sisecofi.proyectos.dto.cierre.DatosPlantillaRcp;
import com.sisecofi.proyectos.dto.cierre.GuardarCierreDto;
import com.sisecofi.proyectos.dto.cierre.ObtenerProyectodto;
import com.sisecofi.proyectos.dto.cierre.PistaDto;
import com.sisecofi.proyectos.dto.cierre.PistaPlantillaDto;
import com.sisecofi.proyectos.model.cierre.CierreModel;
import com.sisecofi.proyectos.service.cierre.CierreProyectoService;
import com.sisecofi.proyectos.util.Constantes;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class CierreController {

	private final CierreProyectoService cierreProyectoService;

	@GetMapping("/validar-estatus-proyecto/{idProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<Boolean> validarEstatus(@PathVariable Long idProyecto) {
		return new ResponseEntity<>(cierreProyectoService.validarEstatus(idProyecto), HttpStatus.OK);
	}

	@GetMapping("/informacion-proyecto-cierre/{idProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<ObtenerProyectodto> obtenerDatosProyecto(@PathVariable Long idProyecto) {
		return new ResponseEntity<>(cierreProyectoService.obtenerDatosProyecto(idProyecto), HttpStatus.OK);
	}

	@PutMapping("/descargar-archivo-cierre")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato()")
	public ResponseEntity<String> descargarArchivo(@RequestParam("path") String path, @RequestParam String nombreCorto,
			@RequestParam String documentoSeleccionado, @RequestParam String fase) {
		return new ResponseEntity<>(cierreProyectoService.descargarArchivo(path, nombreCorto, documentoSeleccionado, fase), HttpStatus.OK);
	}

	@GetMapping("/area-planeacion")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<List<Usuario>> obtenerUsuarios() {
		return new ResponseEntity<>(cierreProyectoService.obtenerUsuarios(), HttpStatus.OK);
	}

	@GetMapping("/obtener-archivos-seccion/{idProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<List<Archivo>> obtenerArchivosSeccion(@PathVariable("idProyecto") Long idProyecto) {
		return new ResponseEntity<>(cierreProyectoService.obtenerArchivosSeccion(idProyecto), HttpStatus.OK);
	}

	@GetMapping("/obtener-archivos-comites/{idProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<List<Archivo>> obtenerOtrosComite(@PathVariable("idProyecto") Long idProyecto) {
		return new ResponseEntity<>(cierreProyectoService.obtenerOtrosComite(idProyecto), HttpStatus.OK);
	}

	@PostMapping("/guardar-proceso-cierre")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	@Transactional
	public ResponseEntity<CierreModel> guardarCierre(@RequestBody GuardarCierreDto guardarCierreDto) {
		return new ResponseEntity<>(cierreProyectoService.guardarCierre(guardarCierreDto.getCierreModel(),
				guardarCierreDto.getArchivos()), HttpStatus.CREATED);
	}

	@PostMapping("/modificar-proceso-cierre")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolLiderProyecto()")
	@Transactional
	public ResponseEntity<CierreModel> modificarCierre(@RequestBody GuardarCierreDto guardarCierreDto) {
		return new ResponseEntity<>(cierreProyectoService.modificarCierre(guardarCierreDto.getCierreModel(),
				guardarCierreDto.getArchivos()), HttpStatus.OK);
	}

	@PostMapping("/calcular-porcentajes")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<List<Double>> porcentajes(@RequestBody List<Archivo> archivos) {
		return new ResponseEntity<>(cierreProyectoService.porcentajes(archivos), HttpStatus.OK);
	}

	@GetMapping("/estatus-en-proceso/{idCierre}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi()")
	@Transactional
	public ResponseEntity<CierreModel> estatusEnProceso(@PathVariable("idCierre") Long idCierre) {
		return new ResponseEntity<>(cierreProyectoService.estatusEnProceso(idCierre), HttpStatus.OK);
	}

	@GetMapping("/cancelar-cierre/{idCierre}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi()")
	@Transactional
	public ResponseEntity<Boolean> cancelarCierre(@PathVariable("idCierre") Long idCierre) {
		return new ResponseEntity<>(cierreProyectoService.cancelarCierre(idCierre), HttpStatus.OK);
	}

	@GetMapping("/obtener-cierre/{idProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<CierreModel> obtenerCierre(@PathVariable("idProyecto") Long idProyecto) {
		return new ResponseEntity<>(cierreProyectoService.obtenerCierre(idProyecto), HttpStatus.OK);
	}

	@PostMapping("/revisado-AP/{idCierre}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi()")
	@Transactional
	public ResponseEntity<CierreModel> revisadoAP(@PathVariable("idCierre") Long idCierre,
			@RequestBody List<Archivo> archivos) {
		return new ResponseEntity<>(cierreProyectoService.revisadoAP(idCierre, archivos), HttpStatus.CREATED);
	}

	@PostMapping("/descarga-masiva-cierre")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato()")
	public ResponseEntity<String> descargaMasiva(@RequestBody DescargaSatCloudRequest descargaSatCloudRequest) {
		return new ResponseEntity<>(cierreProyectoService.descargaMasiva(descargaSatCloudRequest),
				org.springframework.http.HttpStatus.OK);
	}

	@PostMapping("/descarga-sat-cloud-cierre")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato()")
	public ResponseEntity<CarpetaCompartidaDto> descargaSatCloud(
			@RequestBody DescargaSatCloudRequest descargaSatCloudRequest) {
		return new ResponseEntity<>(cierreProyectoService.descargaSatCloud(descargaSatCloudRequest),
				org.springframework.http.HttpStatus.OK);
	}

	@GetMapping("/asignar-lista-documentos-plantilla/{idProyecto}/{idContenidoPlantillador}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<DatosPlantillaRcp> generarMapas(@PathVariable("idProyecto") Long idProyecto,
			@PathVariable("idContenidoPlantillador") Long idContenidoPlantillador) {
		return new ResponseEntity<>(cierreProyectoService.generarMapas(idProyecto, idContenidoPlantillador),
				org.springframework.http.HttpStatus.OK);
	}

	@GetMapping("/validado-LP/{idCierre}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() ||  @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolLiderProyecto()")
	@Transactional
	public ResponseEntity<Boolean> validadoLp(@PathVariable("idCierre") Long idCierre) {
		return new ResponseEntity<>(cierreProyectoService.validadoLp(idCierre), HttpStatus.OK);
	}

	@GetMapping("/generar-rcp/{idCierre}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolVerificadorGeneral()")
	@Transactional
	public ResponseEntity<Boolean> generarRcp(@PathVariable("idCierre") Long idCierre) {
		return new ResponseEntity<>(cierreProyectoService.generarRcp(idCierre), HttpStatus.OK);
	}

	@GetMapping("/plantilla-rcp")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<List<PlantilladorModel>> obtenerPlantillasRcp() {
		return new ResponseEntity<>(cierreProyectoService.obtenerPlantillasRcp(), HttpStatus.OK);
	}

	@GetMapping("/cierre/{proyecto}/{plantillador}/{plantilla}/descarga")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolVerificadorGeneral()")
	@Transactional
	public ResponseEntity<ByteArrayResource> descargarArchivo(@PathVariable(name = "proyecto") long idProyecto,
			@PathVariable(name = "plantillador") long idPlantillador, @RequestParam(name = "type_file") String typeFile,
			@PathVariable("plantilla") Boolean plantilla) {
		return new ResponseEntity<>(this.cierreProyectoService.cierreProyectoDescargaArchivo(idProyecto, idPlantillador,
				typeFile, plantilla), HttpStatus.OK);
	}

	@PostMapping("/pista-ver-pdf")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolLiderProyecto()")
	public ResponseEntity<Boolean> pistaVerPdf(@RequestBody PistaDto pista) {
		return new ResponseEntity<>(cierreProyectoService.pistaVerPdf(pista.getNombreCortoProyecto(),
				pista.getEntregable(), pista.getEstatus()), org.springframework.http.HttpStatus.OK);
	}

	@PostMapping("/pista-generar-plantilla")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolLiderProyecto()")
	public ResponseEntity<Boolean> crearPistaPlantilla(@RequestBody PistaPlantillaDto pista) {
		return new ResponseEntity<>(cierreProyectoService.crearPistaPlantilla(pista.getNombreCortoProyecto(),
				pista.getTipoPlantilla(), pista.getIdProyecto()), org.springframework.http.HttpStatus.OK);
	}

}
