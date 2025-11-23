package com.sisecofi.proveedores.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.model.catalogo.CatTituloServicio;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.proveedores.microservicio.CatalogoMicroservicio;
import com.sisecofi.proveedores.service.CatTituloServicioService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CatTituloServicioServiceImpl implements CatTituloServicioService {
	
    private final CatalogoMicroservicio catalogoMicroservicio;

    @Override
    public CatTituloServicio obtenerTituloServicioPorId(Integer idServicioTitulo) {
        log.info("Consultar por Id Titulo Servicio {}", idServicioTitulo);

        return catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
				CatalogosComunes.PROVEEDOR_TITULO_SERVICIO.getIdCatalogo(), idServicioTitulo, new CatTituloServicio());


    }

    @Override
    public List<CatTituloServicio> obtenerTodosTitulosServicios() {
        log.info("Consultar Catalogo Titulos Servicio {}");
        log.info("Consultar Catalogo Titulos Servicio {}");

        return catalogoMicroservicio.obtenerInformacionCatalogo(
				CatalogosComunes.PROVEEDOR_TITULO_SERVICIO.getIdCatalogo());
        
    }


}
