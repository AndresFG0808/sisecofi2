package com.sisecofi.proyectos.service;

import java.util.Set;

import com.sisecofi.libreria.comunes.model.proyectos.HistoricoModel;

public interface ServicioHistorico {

	HistoricoModel eliminarHistorico(Long id);
	
	boolean agregarHistoricos(Set<HistoricoModel> historicos, Long idFicha);
	
	String generarReporteHistorico(Long idProyecto);
}
