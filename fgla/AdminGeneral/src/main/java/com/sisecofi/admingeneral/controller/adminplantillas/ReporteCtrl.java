package com.sisecofi.admingeneral.controller.adminplantillas;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.admingeneral.service.adminplantillas.ReporteService;
import com.sisecofi.admingeneral.util.Constantes;
import com.sisecofi.admingeneral.util.ConstantesAdminPlantilla;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class ReporteCtrl {

	private final ReporteService reporteService;

	@GetMapping(ConstantesAdminPlantilla.PATH_BASE + "/generar-reporte/{idPlantilla}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() ||  @seguridad.validarRolAdminMatrizDocumental()")
	public ResponseEntity<byte[]> generarReporte(@PathVariable("idPlantilla") Integer idPlantilla) {
		return new ResponseEntity<>(reporteService.generarReporte(idPlantilla), HttpStatus.OK);
	}

	@GetMapping(ConstantesAdminPlantilla.PATH_BASE + "/generar-plantilla-base")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() ||  @seguridad.validarRolAdminMatrizDocumental()")
	public ResponseEntity<byte[]> generarPlantillaBase() {
		return new ResponseEntity<>(reporteService.generarReporteBase(), HttpStatus.OK);
	}
}
