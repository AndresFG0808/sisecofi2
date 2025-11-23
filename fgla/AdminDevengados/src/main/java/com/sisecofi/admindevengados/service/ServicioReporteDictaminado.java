package com.sisecofi.admindevengados.service;

import java.util.List;
import com.sisecofi.admindevengados.dto.DictaminadoDto;



public interface ServicioReporteDictaminado {
	
	String obtenerInformacion(List<DictaminadoDto> listaDictaminados);

}
