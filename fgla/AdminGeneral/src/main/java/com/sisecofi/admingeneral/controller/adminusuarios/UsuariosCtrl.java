package com.sisecofi.admingeneral.controller.adminusuarios;

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

import com.sisecofi.admingeneral.dto.adminusuarios.BusquedaUsuarioDto;
import com.sisecofi.admingeneral.service.adminusuarios.UsuariosService;
import com.sisecofi.admingeneral.util.Constantes;
import com.sisecofi.admingeneral.util.ConstantesAdminUsuario;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class UsuariosCtrl {

	private final UsuariosService service;

	@PostMapping(ConstantesAdminUsuario.PATH_BASE + "/buscar-usuarios")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<List<Usuario>> buscarUsuarios(@RequestBody @Valid BusquedaUsuarioDto dto) {
		return new ResponseEntity<>(service.obtenerUsuario(dto), HttpStatus.OK);
	}

	@PutMapping(ConstantesAdminUsuario.PATH_BASE + "/guardar-usuario")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<Usuario> guardarUsuario(@RequestBody @Valid Usuario model) {
		return new ResponseEntity<>(service.guardarUsuario(model), HttpStatus.OK);
	}

	@PutMapping(ConstantesAdminUsuario.PATH_BASE + "/guardar-usuarios")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<Boolean> guardarUsuario(@RequestBody @Valid List<Usuario> model) {
		return new ResponseEntity<>(service.guardarUsuarios(model), HttpStatus.OK);
	}

	@PostMapping("/" + ConstantesAdminUsuario.PATH_BASE + "/buscar-usuario-directorio")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<List<Usuario>> buscarUsuarioDirectorio(@RequestBody @Valid BusquedaUsuarioDto dto) {
		return new ResponseEntity<>(service.buscarUsuarioDirectorioActivo(dto), HttpStatus.OK);
	}
	
	@GetMapping(ConstantesAdminUsuario.PATH_BASE + "/pista-acceso-sistema")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorTitutulos() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolTodosProyectos()")
	public ResponseEntity<Boolean> obtenerPlantilla() {
		return new ResponseEntity<>(service.guardarPista(), HttpStatus.OK);
	}
	
}
