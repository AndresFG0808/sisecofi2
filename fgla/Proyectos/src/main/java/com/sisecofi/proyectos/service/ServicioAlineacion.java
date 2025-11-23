package com.sisecofi.proyectos.service;

import java.util.List;
import com.sisecofi.proyectos.dto.AlineacionRequest;
import com.sisecofi.proyectos.model.FichaAlineacion;

public interface ServicioAlineacion {

	boolean agregarAlineaciones(List<AlineacionRequest> alineaciones, Long idFicha);
	
	FichaAlineacion eliminarAlineacion(Long idAlineacion);
	
	String generarReporteAlineacion(Long idProyecto);
	
	boolean verificarMapas(Long idFicha);
}
