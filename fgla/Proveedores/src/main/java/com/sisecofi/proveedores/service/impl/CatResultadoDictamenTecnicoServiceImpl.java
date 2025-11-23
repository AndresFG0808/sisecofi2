package com.sisecofi.proveedores.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.model.catalogo.CatResultadoDictamenTecnicoModel;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.proveedores.microservicio.CatalogoMicroservicio;
import com.sisecofi.proveedores.service.CatResultadoDictamenTecnicoService;
import com.sisecofi.proveedores.util.Constantes;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CatResultadoDictamenTecnicoServiceImpl implements CatResultadoDictamenTecnicoService {

    private final CatalogoMicroservicio catalogoMicroservicio;

    @Override
    public List<CatResultadoDictamenTecnicoModel> obtenerTodosResultados(){
    	return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.RESULTADO_DICTAMEN_TECNICO.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);
    }


}
