package com.sisecofi.proyectos.service;

import java.util.List;
import java.util.Set;

import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.AsociacionesModel;
import com.sisecofi.proyectos.dto.AsociacionGuardarRequest;
import com.sisecofi.proyectos.dto.AsociacionResponse;

public interface ServicioAsociacion {

	boolean crearAsociacion(AsociacionesModel asociacion, Long idProyecto);
	
	boolean modificarAsociacion(AsociacionesModel asociacion);
	
	List<AsociacionResponse> obtenerAsociaciones(Long idProyecto);
	
	boolean eliminarAsociacion(Long idAsociacion);
	
	List<AsociacionResponse> guardarAsociaciones(AsociacionGuardarRequest request, Long idProyecto);
	
	String generarReporteAsociacion(Long idProyecto);
	
	Set <Integer> plantillasOcupadas();
}
