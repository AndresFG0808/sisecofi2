package com.sisecofi.contratos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.contratos.dto.CargaLayoutInformesDto;
import com.sisecofi.contratos.service.ServicioCargaLayoutInformes;
import com.sisecofi.contratos.util.Constantes;

import lombok.RequiredArgsConstructor;

@RequestMapping("/"+ Constantes.PATH_BASE)
@RequiredArgsConstructor
@RestController
public class SeccionCargaLayoutInformes {
	private final ServicioCargaLayoutInformes servicioLayout;

	@PostMapping("/carga-layout-informes")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi()")
	public ResponseEntity<String> cargarLayoutInformes(@ModelAttribute CargaLayoutInformesDto carga){
		return new ResponseEntity<>(servicioLayout.cargaLayoutInformes(carga),HttpStatus.OK);
	}
}
