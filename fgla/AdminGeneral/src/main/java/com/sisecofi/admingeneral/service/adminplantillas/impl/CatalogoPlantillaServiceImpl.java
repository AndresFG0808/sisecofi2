package com.sisecofi.admingeneral.service.adminplantillas.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sisecofi.admingeneral.microservicio.CatalogoMicroservicio;
import com.sisecofi.admingeneral.service.adminplantillas.CatalogoPlantillaService;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.admingeneral.util.Constantes;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Service
@RequiredArgsConstructor
public class CatalogoPlantillaServiceImpl implements CatalogoPlantillaService {

	private final CatalogoMicroservicio catalogoMicroservicio;

	@Override
	public List<BaseCatalogoModel> obtenerCatalogoFasesProyecto() {
		return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.FASES_PROYECTOS.getIdCatalogo(),Constantes.VALIDACION_ESTATUS);
	}

}
