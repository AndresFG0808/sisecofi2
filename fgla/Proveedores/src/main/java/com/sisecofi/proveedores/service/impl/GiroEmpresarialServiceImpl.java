package com.sisecofi.proveedores.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sisecofi.proveedores.microservicio.CatalogoMicroservicio;
import com.sisecofi.libreria.comunes.model.catalogo.CatGiroEmpresarial;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.proveedores.service.GiroEmpresarialService;
import com.sisecofi.proveedores.util.Constantes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@RequiredArgsConstructor
public class GiroEmpresarialServiceImpl implements GiroEmpresarialService {

    private final CatalogoMicroservicio catalogoMicroservicio;

    public CatGiroEmpresarial obtenerGiroPorId(Long id) {
        log.info("Consultar por Id giro empresarial: {}", id);
        
        Integer idGiro = Integer.parseInt(""+id);
        

        return catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
				CatalogosComunes.PROVEEDOR_GIRO_EMPRESARIAL.getIdCatalogo(), idGiro, new CatGiroEmpresarial());

    }

    public List<CatGiroEmpresarial> obtenerTodosLosGiros() {
        log.info("Consultar Catalogo Giros Empresariales");

        return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
				CatalogosComunes.PROVEEDOR_GIRO_EMPRESARIAL.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);

    }

}
