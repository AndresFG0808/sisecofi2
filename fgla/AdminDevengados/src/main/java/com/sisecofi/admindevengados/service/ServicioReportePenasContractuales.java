package com.sisecofi.admindevengados.service;

import java.util.List;
import com.sisecofi.admindevengados.dto.PenaContractualExcelDto;

public interface ServicioReportePenasContractuales {
	
	String obtenerInformacion(List<PenaContractualExcelDto> listaPenasContractuales);


}
