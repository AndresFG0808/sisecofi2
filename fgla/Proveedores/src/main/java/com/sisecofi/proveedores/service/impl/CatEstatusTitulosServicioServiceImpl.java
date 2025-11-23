package com.sisecofi.proveedores.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusTituloServicio;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.proveedores.microservicio.CatalogoMicroservicio;
import com.sisecofi.proveedores.service.CatEstatusTitulosServicioService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CatEstatusTitulosServicioServiceImpl implements CatEstatusTitulosServicioService {

	private final CatalogoMicroservicio catalogoMicroservicio;

	@Override
	public CatEstatusTituloServicio obtenerEstatusPorId(Integer idCatEstatusTituloServicio) {
		return catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
				CatalogosComunes.ESTATUS_TITULO_SERVICIO.getIdCatalogo(), idCatEstatusTituloServicio,
				new CatEstatusTituloServicio());
	}

	@Override
	public List<CatEstatusTituloServicio> obtenerTodosEstatus() {
		return catalogoMicroservicio.obtenerInformacionCatalogo(CatalogosComunes.ESTATUS_TITULO_SERVICIO.getIdCatalogo());
	}


}
