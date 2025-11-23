package com.sisecofi.admingeneral.dto.plantillador;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Data
public class RequestPlantilla {

	private String nombre;
	private String comentarios;
	private Long idPlantillador;
	private Long idSubPlantillador;
	private String header;
	private String footer;
	private String contenido;
	
	private Long idSubTipoPlantillador;
}
