package com.sisecofi.reportedocumental.service.dinamico;

import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.reportedocumental.dto.dinamico.CriterioBusquedaDto;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface ReporteDinamicoService {

	PageGeneric obtenerReporte(CriterioBusquedaDto busquedaDto);

	byte[] obtenerReporteExport(CriterioBusquedaDto busquedaDto);

}
