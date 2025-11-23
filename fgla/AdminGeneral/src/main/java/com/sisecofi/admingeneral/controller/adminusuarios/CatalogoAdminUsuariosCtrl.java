package com.sisecofi.admingeneral.controller.adminusuarios;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.admingeneral.dto.adminusuarios.EstatusDto;
import com.sisecofi.admingeneral.service.adminusuarios.CatalogoUsuarioService;
import com.sisecofi.admingeneral.util.Constantes;
import com.sisecofi.admingeneral.util.ConstantesAdminUsuario;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class CatalogoAdminUsuariosCtrl {

	private final CatalogoUsuarioService catalogoService;

	@GetMapping(ConstantesAdminUsuario.PATH_BASE + "/catalogo-estatus-usuario")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<List<EstatusDto>> obtenerPlantilla() {
		return new ResponseEntity<>(catalogoService.obtenerEstatus(), HttpStatus.OK);
	}

}
