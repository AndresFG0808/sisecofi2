package com.sisecofi.admingeneral.dto.plantillador;

import java.util.Map;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
public class HtmlExcelDto {

	private Long idSubPlantillador;
	private Map<String, String> datos;

}
