package com.sisecofi.proyectos.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusProyecto;
import com.sisecofi.proyectos.dto.EstructuraProyectoMetaDto;
import com.sisecofi.proyectos.dto.ProyectoDtoLigero;
import com.sisecofi.proyectos.dto.ProyectoNombreDto;
import com.sisecofi.proyectos.dto.ResponseComiteInfo;
import com.sisecofi.libreria.comunes.dto.ResponseGeneric;
import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoResponse;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import com.sisecofi.proyectos.service.ServicioComite;
import com.sisecofi.proyectos.service.ServicioProyecto;
import com.sisecofi.proyectos.util.Constantes;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.interfaces.ValidacionCompleta;
import com.sisecofi.proyectos.util.interfaces.ValidacionIncompleta;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class ProyectoCtrl {

	private final ServicioProyecto proyectoServicio;
	private final ServicioComite comite;

	@PostMapping("/buscar")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolAdminMatrizDocumental() " + "|| @seguridad.validarRolApoyoAcppi() "
			+ "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " + "|| @seguridad.validarRolUsuarioConsulta() "
			+ "|| @seguridad.validarRolLiderProyecto() " + "|| @seguridad.validarRolAdministradorContrato() "
			+ "|| @seguridad.validarRolParticipantesAdmonDictamen() "
			+ "|| @seguridad.validarRolParticipantesAdmonVerificacion() "
			+ "|| @seguridad.validarRolVerificadorGeneral() "
			+ "|| @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolTodosProyectos()")
	public ResponseEntity<Page<ProyectoDtoLigero>> buscarProyectos(@RequestBody EstructuraProyectoMetaDto proyecto) {
		return new ResponseEntity<>(proyectoServicio.buscarProyectosModel(proyecto),
				org.springframework.http.HttpStatus.OK);
	}

	@PostMapping("/agregar-proyecto")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<ProyectoResponse> crearProyecto(
			@Validated({ ValidacionCompleta.class, ValidacionIncompleta.class }) @RequestBody ProyectoModel proyecto) {
		return new ResponseEntity<>(proyectoServicio.crearProyecto(proyecto), HttpStatus.CREATED);
	}

	@PutMapping("/modificar-proyecto/{idProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<ProyectoResponse> modificarProyecto(
			@Validated({ ValidacionCompleta.class, ValidacionIncompleta.class }) @RequestBody ProyectoModel proyecto,
			@PathVariable Long idProyecto) {
		return new ResponseEntity<>(proyectoServicio.actualizarProyecto(proyecto, idProyecto),
				org.springframework.http.HttpStatus.OK);
	}

	@PutMapping("/modificar-estatus/{idProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi()")
	public ResponseEntity<ProyectoResponse> modificarEstatus(@Valid @RequestBody CatEstatusProyecto proyecto,
			@PathVariable Long idProyecto) {

		return new ResponseEntity<>(proyectoServicio.actualizarEstatus(proyecto, idProyecto), HttpStatus.OK);

	}

	@GetMapping("/verificar-cancelado/{idProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi()")
	public ResponseEntity<Boolean> verificarCancelado(@PathVariable Long idProyecto) {

		return new ResponseEntity<>(proyectoServicio.verificarCancelado(idProyecto), HttpStatus.OK);

	}

	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ProyectoResponse modificarEstatusNormal(CatEstatusProyecto proyecto, Long idProyecto) {
		return proyectoServicio.actualizarEstatus(proyecto, idProyecto);
	}

	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi()")
	public ProyectoResponse modificarEstatusCierre(CatEstatusProyecto proyecto, Long idProyecto) {
		return proyectoServicio.actualizarEstatus(proyecto, idProyecto);
	}

	@GetMapping("/agregar-proyecto")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolAdminMatrizDocumental() " + "|| @seguridad.validarRolApoyoAcppi() "
			+ "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " + "|| @seguridad.validarRolUsuarioConsulta() "
			+ "|| @seguridad.validarRolLiderProyecto() " + "|| @seguridad.validarRolAdministradorContrato() "
			+ "|| @seguridad.validarRolParticipantesAdmonDictamen() "
			+ "|| @seguridad.validarRolParticipantesAdmonVerificacion() "
			+ "|| @seguridad.validarRolVerificadorGeneral() "
			+ "|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<String> ultimoId() {
		return new ResponseEntity<>(proyectoServicio.obtnerUltimoId(), org.springframework.http.HttpStatus.OK);
	}

	@GetMapping("/ver-detalle/{idProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolAdminMatrizDocumental() " + "|| @seguridad.validarRolApoyoAcppi() "
			+ "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " + "|| @seguridad.validarRolUsuarioConsulta() "
			+ "|| @seguridad.validarRolLiderProyecto() " + "|| @seguridad.validarRolAdministradorContrato() "
			+ "|| @seguridad.validarRolParticipantesAdmonDictamen() "
			+ "|| @seguridad.validarRolParticipantesAdmonVerificacion() "
			+ "|| @seguridad.validarRolVerificadorGeneral() "
			+ "|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<ProyectoResponse> obtenerProyecto(@PathVariable Long idProyecto) {
		return new ResponseEntity<>(proyectoServicio.obtenerProyecto(idProyecto),
				org.springframework.http.HttpStatus.OK);
	}

	@GetMapping("/ultima-mod/{idProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolAdminMatrizDocumental() " + "|| @seguridad.validarRolApoyoAcppi() "
			+ "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " + "|| @seguridad.validarRolUsuarioConsulta() "
			+ "|| @seguridad.validarRolLiderProyecto() " + "|| @seguridad.validarRolAdministradorContrato() "
			+ "|| @seguridad.validarRolParticipantesAdmonDictamen() "
			+ "|| @seguridad.validarRolParticipantesAdmonVerificacion() "
			+ "|| @seguridad.validarRolVerificadorGeneral() "
			+ "|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<String> obtenerUltimaMod(@PathVariable Long idProyecto) {
		String ultimaMod= proyectoServicio.obtenerUltimaMod(idProyecto);
		String sanitzedData = HtmlUtils.htmlEscape(ultimaMod); 
		return new ResponseEntity<>(sanitzedData, HttpStatus.OK);
	}

	@GetMapping("/nombres-cortos/{idEstatus}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolAdminMatrizDocumental() " + "|| @seguridad.validarRolApoyoAcppi() "
			+ "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " + "|| @seguridad.validarRolUsuarioConsulta() "
			+ "|| @seguridad.validarRolLiderProyecto() " + "|| @seguridad.validarRolAdministradorContrato() "
			+ "|| @seguridad.validarRolParticipantesAdmonDictamen() "
			+ "|| @seguridad.validarRolParticipantesAdmonVerificacion() "
			+ "|| @seguridad.validarRolVerificadorGeneral() "
			+ "|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<ProyectoNombreDto>> obtenerProyectoNombres(@PathVariable Integer idEstatus) {
		return new ResponseEntity<>(proyectoServicio.obtenerNombresCortos(idEstatus),
				org.springframework.http.HttpStatus.OK);
	}

	@GetMapping("/nombres-cortos-tablero/{idEstatus}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolGestorTitutulos() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<ProyectoModel>> obtenerProyectoTablero(@PathVariable Integer idEstatus) {
		return new ResponseEntity<>(proyectoServicio.obtenerProyectosEstatus(idEstatus),
				org.springframework.http.HttpStatus.OK);
	}

	@GetMapping("/tablero-control-comites/{idProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolGestorTitutulos() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<ResponseGeneric<List<ResponseComiteInfo>>> obtenerInformacionComiteProyectos(
			@PathVariable("idProyecto") Integer idProyecto) {

		List<ResponseComiteInfo> comiteProyectoModelRes = comite.obtenerComiteInformacion(idProyecto);
		ResponseGeneric<List<ResponseComiteInfo>> data = ResponseGeneric.<List<ResponseComiteInfo>>builder().build();
		data.setData(comiteProyectoModelRes);
		data.setMsj(ErroresEnum.COMITE_PROYECTO_CREADO);
		return new ResponseEntity<>(data, HttpStatus.CREATED);
	}

	@GetMapping("/prueba-version-integracion")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolGestorTitutulos() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<String> obtenerVersion() {
		return new ResponseEntity<>("Version 18/06/2025 v1", HttpStatus.OK);
	}
}
