package com.sisecofi.admindevengados.service;

import java.util.List;
import java.util.Map;

import com.sisecofi.admindevengados.dto.ObtenerPenaContractualDto;
import com.sisecofi.admindevengados.dto.ObtenerPenaContractualRequestDto;



public interface DeduccionesService {
	
	List<ObtenerPenaContractualDto> guardarDeduccion(List<ObtenerPenaContractualRequestDto> penas);
	
	List<ObtenerPenaContractualDto> modificarDeduccion(List<ObtenerPenaContractualDto> penasDto);
	
	List<ObtenerPenaContractualDto> obtenerDeducciones(Long dictamenId);
	
	List<Long> eliminarRegistro(Map<String, List<Long>> request);

	List<String> obtenerConceptosServicio(String tipo, Long dictamenId);

}
