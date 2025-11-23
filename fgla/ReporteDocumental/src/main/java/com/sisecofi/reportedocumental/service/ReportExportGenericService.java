package com.sisecofi.reportedocumental.service;

import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.reportedocumental.service.dinamico.Mapper;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface ReportExportGenericService {

	byte[] exportarReporte(PageGeneric generic, String nombrePestanna, Mapper mapper);

}
