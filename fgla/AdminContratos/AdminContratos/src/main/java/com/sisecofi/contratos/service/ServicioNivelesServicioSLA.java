package com.sisecofi.contratos.service;

import java.util.List;

import com.sisecofi.libreria.comunes.model.contratos.NivelesServicioSLAModel;

public interface ServicioNivelesServicioSLA {
	public List<NivelesServicioSLAModel> obtenerNivelesServicioSLA(Long idContrato);

	public String guardarNivelesServicioSLA(List<NivelesServicioSLAModel> informe);

	public String eliminarNivelesServicioSLA(List<Long> ids);

	public String actualizarNivelesServicioSLA(List<NivelesServicioSLAModel> ids);

	public String exportarExcel(Long idContrato);
}
