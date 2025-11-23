package com.sisecofi.proyectos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.proyectos.dto.ProyectoNombreDto;
import com.sisecofi.proyectos.dto.ProyectoUsuarioDto;
import com.sisecofi.proyectos.dto.UsuarioProyectoDto;
import com.sisecofi.proyectos.service.AsignarProService;
import com.sisecofi.proyectos.util.Constantes;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class AsignarProyectoCtl {

	private final AsignarProService asignarProService;

	@PostMapping("/proyecto-nombre-corto")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<ProyectoNombreDto>> buscarProyetosPorNombreCorto(
			@RequestBody BaseCatalogoModel catalogoModel) {
		return new ResponseEntity<>(asignarProService.obtenerProyectosNombreCorto(catalogoModel), HttpStatus.OK);
	}

	@PostMapping("/buscar-proyecto")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<ProyectoUsuarioDto> buscarProyectoUsuario(@RequestBody ProyectoNombreDto dto) {
		return new ResponseEntity<>(asignarProService.buscarProyectoUsuario(dto), HttpStatus.OK);
	}

	@PutMapping("/guardar-proyectos-usuario")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<Boolean> guardarUsuariosAsignados(@RequestBody ProyectoUsuarioDto dto) {
		return new ResponseEntity<>(asignarProService.guardarUsuariosAsignados(dto), HttpStatus.CREATED);
	}

	@GetMapping("/usuarios-proyecto")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<Usuario>> buscarUsuariosProyecto() {
		return new ResponseEntity<>(asignarProService.buscarUsuarios(), HttpStatus.OK);
	}

	@PostMapping("/buscar-usuario-proyecto")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<UsuarioProyectoDto> buscarUsuarioProyecto(@RequestBody Usuario dto) {
		return new ResponseEntity<>(asignarProService.buscarUsuarioProyecto(dto), HttpStatus.OK);
	}

	@PutMapping("/guardar-usuario-proyecto")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<Boolean> guardarUsuarioProyecto(@RequestBody UsuarioProyectoDto dto) {
		return new ResponseEntity<>(asignarProService.guardarProyectoUsuario(dto), HttpStatus.CREATED);
	}
}
