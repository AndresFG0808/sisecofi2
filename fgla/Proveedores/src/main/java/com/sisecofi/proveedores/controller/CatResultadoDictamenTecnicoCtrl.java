package com.sisecofi.proveedores.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.libreria.comunes.model.catalogo.CatResultadoDictamenTecnicoModel;
import com.sisecofi.proveedores.service.CatResultadoDictamenTecnicoService;
import com.sisecofi.proveedores.util.Constantes;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE)
public class CatResultadoDictamenTecnicoCtrl {

	private final CatResultadoDictamenTecnicoService catResultadoDictamenTecnicoService;

	@GetMapping("/obtener-resultado-dictamen")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || "
			+ "@seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()"
			+ " ||@seguridad.validarRolGestorTitutulos()")
	public ResponseEntity<List<CatResultadoDictamenTecnicoModel>> obtenerTodoResultado() {
		return new ResponseEntity<>(catResultadoDictamenTecnicoService.obtenerTodosResultados(), HttpStatus.OK);
	}

}
