package com.sisecofi.reportedocumental.service.controldocumental;

import java.util.List;

import com.sisecofi.libreria.comunes.dto.reportecontroldoc.PlantillaVigenteDto;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface PlantillaVigenteService {

	List<PlantillaVigenteDto> findByFaseProyecto(Integer idFaseProyecto);
}
