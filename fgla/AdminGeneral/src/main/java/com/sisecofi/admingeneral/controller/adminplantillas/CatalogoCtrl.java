package com.sisecofi.admingeneral.controller.adminplantillas;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.admingeneral.service.adminplantillas.CatalogoPlantillaService;
import com.sisecofi.admingeneral.util.Constantes;
import com.sisecofi.admingeneral.util.ConstantesAdminPlantilla;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class CatalogoCtrl {

	private final CatalogoPlantillaService catalogoService;

	@GetMapping(ConstantesAdminPlantilla.PATH_BASE + "/fase-proyecto")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() ||  @seguridad.validarRolAdminMatrizDocumental()")
	public ResponseEntity<List<BaseCatalogoModel>> catalogoEstatusProyecto() {
		return new ResponseEntity<>(catalogoService.obtenerCatalogoFasesProyecto(), HttpStatus.OK);
	}

}
