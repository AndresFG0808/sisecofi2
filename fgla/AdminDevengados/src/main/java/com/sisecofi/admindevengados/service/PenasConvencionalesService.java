package com.sisecofi.admindevengados.service;

import java.util.List;
import java.util.Map;

import com.sisecofi.admindevengados.dto.ObtenerPenaContractualDto;
import com.sisecofi.admindevengados.model.PenasConvencionalesModel;

public interface PenasConvencionalesService {

	List<PenasConvencionalesModel> guardarPenaConvencional(List<ObtenerPenaContractualDto> penas);

	List<PenasConvencionalesModel> modificarPenaConvencional(List<ObtenerPenaContractualDto> penasDto);

	List<ObtenerPenaContractualDto> obtenerPenasConvencional(Long dictamenId);

	List<Long> eliminarRegistro(Map<String, List<Long>> request);

}
