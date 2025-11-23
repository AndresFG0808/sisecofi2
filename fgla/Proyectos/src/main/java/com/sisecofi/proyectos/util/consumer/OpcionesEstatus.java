package com.sisecofi.proyectos.util.consumer;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusProyecto;
import com.sisecofi.libreria.comunes.service.security.SeguridadService;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.proyectos.microservicio.CatalogoMicroservicio;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OpcionesEstatus implements Function<CatEstatusProyecto,Set<BaseCatalogoModel>>{

	private final CatalogoMicroservicio catalogoMicroservicio;
	private final SeguridadService seguridadService;
	
	@Override
	public Set<BaseCatalogoModel> apply(CatEstatusProyecto t) {

		Set<BaseCatalogoModel> opciones = new HashSet<>();
		
		boolean acceso = seguridadService.validarRolAdminSistema() ||
				seguridadService.validarRolAdminSistemaSecundario();
		
		Integer idEstatus= t.getIdEstatusProyecto();
		
		if (acceso && idEstatus<6) {
		if (idEstatus==5) {
			opciones.add(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(CatalogosComunes.ESTATUS_PROYECTO.getIdCatalogo(), 4, CatEstatusProyecto.class));
		}else if(idEstatus==1) {
			opciones.add(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(CatalogosComunes.ESTATUS_PROYECTO.getIdCatalogo(), idEstatus+1, CatEstatusProyecto.class));
		}else {
			opciones.add(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(CatalogosComunes.ESTATUS_PROYECTO.getIdCatalogo(), idEstatus-1, CatEstatusProyecto.class));
			opciones.add(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(CatalogosComunes.ESTATUS_PROYECTO.getIdCatalogo(), idEstatus+1, CatEstatusProyecto.class));
		}
		
		}
		if (acceso && idEstatus.equals(6)) {
			opciones.add(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(CatalogosComunes.ESTATUS_PROYECTO.getIdCatalogo(), 1, CatEstatusProyecto.class));
		}
		
		boolean accesoAcppi = seguridadService.validarRolApoyoAcppi();
		
		if (!acceso && accesoAcppi && idEstatus==3) {
			opciones.add(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(CatalogosComunes.ESTATUS_PROYECTO.getIdCatalogo(), 4, CatEstatusProyecto.class));
		}
				
		return opciones;
	}
}
