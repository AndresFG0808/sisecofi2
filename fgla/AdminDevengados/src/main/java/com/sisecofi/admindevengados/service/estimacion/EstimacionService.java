package com.sisecofi.admindevengados.service.estimacion;

import com.sisecofi.libreria.comunes.model.estimacion.EstimacionModel;

import java.util.List;

import com.sisecofi.admindevengados.dto.AtributosId;
import com.sisecofi.admindevengados.dto.EstimacionResponse;
import com.sisecofi.admindevengados.dto.ServicioEstimacionDtoMod;
import com.sisecofi.admindevengados.dto.estimacion.ConveniosResponse;
import com.sisecofi.admindevengados.model.ServicioEstimadoModel;
import com.sisecofi.libreria.comunes.dto.dictamen.DevengadoBusquedaResponse;
import com.sisecofi.libreria.comunes.dto.dictamen.FacturaContratoDto;

public interface EstimacionService {
	
	EstimacionResponse crearEstimacion(EstimacionModel estimacion);

	List<DevengadoBusquedaResponse> obtenerDictamenesAsociados(String idEstimacion);
	
	byte[] exportarExcelDictamenesAsociados(String idEstimacion);

	EstimacionResponse obtenerEstimacion(String idEstimacion);

	List<EstimacionModel> obtenerEstimacionPorIdContrato(Long idContrato);
	
	EstimacionResponse modificarEstimacion (EstimacionModel estimacion);
	
	EstimacionResponse duplicarEstimacion (String idEstimacion);
	
	List<FacturaContratoDto> obtenerFacturasAsociadas(String idEstimacion);

	byte[] exportarFacturasAsociadas(String idEstimacion);
	
	EstimacionResponse cambiarAInicial(String idEstimacion);
	
	EstimacionResponse cancelarEstimacion(String idEstimacion, String justificacion);

	ServicioEstimacionDtoMod volumetriaEstimada(List<ServicioEstimadoModel> servicios, String idEstimacion);
	
	List<ServicioEstimadoModel> obtenerServiciosEstimados(String idEstimacion, Long idEstimacionBd);

	ServicioEstimacionDtoMod guardarServicios(List<ServicioEstimadoModel> servicios, String idEstimacion, boolean volumetria);

	byte[] exportarExcelServicios(String idEstimacion);

	ConveniosResponse obtenerConvenios(String idEstimacion);

	List<ServicioEstimadoModel> cambiarPrecioUnitario(String idEstimacion, String numeroConvenio);

	List<ServicioEstimadoModel> calcularServicio(List<ServicioEstimadoModel> servicios, EstimacionModel estima);

	EstimacionResponse obtenerEstimacionPersist(List<ServicioEstimadoModel> servicios);

	void calcularMontoTotal(EstimacionModel estimacion);

	EstimacionModel obtenerMontoTotal(EstimacionModel estimacion);

	AtributosId generarAtributosId(String id);

	EstimacionModel generarEstimacion(String id);
	
}