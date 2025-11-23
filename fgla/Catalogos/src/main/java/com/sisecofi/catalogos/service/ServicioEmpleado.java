package com.sisecofi.catalogos.service;

import java.util.List;

import com.sisecofi.catalogos.dto.EmpleadoDto;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoEmpleado;


public interface ServicioEmpleado {

	List<CatTipoEmpleado> obtenerTipoEmpleado();
	
	List<EmpleadoDto> obtenerEmpleadosAdministracion(Integer idAdministracion);
	
	List<EmpleadoDto> guardarEmpleadosAdministracion(List<EmpleadoDto> lista, Integer idAdministracion);
	
	byte[] exportarEmpleadosAdministracion(Integer idAdministracion);
	
}
