package com.sisecofi.admingeneral.dto.plantillador;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
@Getter
@Setter
public class ContenidoPlantillaDto {

	private String header;

	private String footer;

	private String contenido;
	
	private String nombre;
	
	private String comentarios;
	
	private String tipo;
	
	private Long id;

}
