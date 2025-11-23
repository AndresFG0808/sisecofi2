package com.sisecofi.contratos.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.sisecofi.contratos.dto.ArchivoCasoNegocioDto;
import com.sisecofi.contratos.dto.CasoNegocioResponseDto;

public interface ServicioCasoNegocio {
   String obtenerLayout(Long idContrato);
   
   Map<Integer, List<String>> procesarProyeccion (ArchivoCasoNegocioDto archivo, Long idContrato) throws IOException;
   
   String exportarExcel(Long idContrato)throws IOException;
   
   CasoNegocioResponseDto verCasoNegocio(Long idContrato)throws IOException;

String obtenerLayoutConvenio(Long idConvenioModificatorio);

Map<Integer, List<String>> procesarProyeccionConvenio(ArchivoCasoNegocioDto archivo, Long idConvenioModificatorio)
		throws IOException;

String exportarExcelConvenio(Long idConvenioModificatorio) throws IOException;

CasoNegocioResponseDto verCasoNegocioConvenio(Long idConvenioModificatorio) throws IOException;
}
