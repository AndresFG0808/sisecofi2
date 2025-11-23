package com.sisecofi.admingeneral.dto.plantillador;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class HtmlExcelListDto {
	
	private Long idSubPlantillador;
	private Map<String, String> datosGenerales;
	private List<Map<String, String>> datos;
}

