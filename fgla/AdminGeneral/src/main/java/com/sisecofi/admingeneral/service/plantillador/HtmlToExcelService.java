package com.sisecofi.admingeneral.service.plantillador;


import java.util.List;
import java.util.Map;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface HtmlToExcelService {

	byte[] htmlToExcel(Long idSubPlantillador, Map<String, String> datos);

	byte[] htmlToExcelRCP(Long idSubPlantillador, Map<String, String> datosGenerales, List<Map<String, String>> listaDeMapas);

	byte[] cierreProyectoExcel(Long idSubPlantillador, Map<String, String> datosGenerales, List<Map<String, String>> listaDeMapas);

	byte[] proformaExcel(Long idSubPlantillador, Map<String, String> datosGenerales, List<Map<String, String>> listaDeMapas) ;

	byte[] htmlToPdfRCP(Long idSubPlantillador, List<Map<String, String>> listaDeMapas);
	
	byte[] proformaPDF(Long idSubPlantillador, Map<String, String> datosGenerales,List<Map<String, String>> listaDeMapas);
	
}
