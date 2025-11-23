package com.sisecofi.admindevengados.service;

import java.util.List;

import com.sisecofi.libreria.comunes.dto.contrato.ContratoSimpleDto;
import com.sisecofi.libreria.comunes.dto.contrato.ProveedorDto;
import com.sisecofi.libreria.comunes.dto.dictamen.DevengadoBusquedaResponse;
import com.sisecofi.admindevengados.dto.DevengadoRequest;
import com.sisecofi.admindevengados.dto.EstimacionNuevaDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;

public interface DevengadoService {
	
	List<BaseCatalogoModel> obtenerEstatus(String tipoConsumo);
	
	List<ContratoSimpleDto> obtenerContratosVigentes(String vigente);
	
	List<ProveedorDto> obtenerProveedoresContrato(Long idContrato);
	
	List<DevengadoBusquedaResponse> obtenerDictamenesEstimaciones(DevengadoRequest request);

	String exportarExcel(DevengadoRequest request);
	
	EstimacionNuevaDto nuevaEstimacion(Long idContrato, Long idProveedor, String tipo);
	
	boolean comprobarDependencias (Long idContrato);
}
