package com.sisecofi.admingeneral.controller.adminusuarios;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.admingeneral.dto.adminusuarios.BusquedaUsuarioDto;
import com.sisecofi.admingeneral.service.adminusuarios.UsuariosService;
import com.sisecofi.admingeneral.util.Constantes;
import com.sisecofi.admingeneral.util.ConstantesAdminUsuario;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class ReporteUsuCtrl {

	private final UsuariosService service;

	@PostMapping(ConstantesAdminUsuario.PATH_BASE + "/buscar-usuarios-reporte")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<byte[]> buscarUsuariosReporte(@RequestBody @Valid BusquedaUsuarioDto dto) {
		return new ResponseEntity<>(service.obtenerUsuarioReporte(dto), HttpStatus.OK);
	}

}
