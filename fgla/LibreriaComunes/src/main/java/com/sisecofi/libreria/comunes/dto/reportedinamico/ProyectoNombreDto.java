package com.sisecofi.libreria.comunes.dto.reportedinamico;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoNombreDto {

	private Long idProyecto;
	private String nombreProyecto;
	private String nombreCorto;

	public ProyectoNombreDto(Long idProyecto, String nombreCorto) {
		super();
		this.idProyecto = idProyecto;
		this.nombreCorto = nombreCorto;
	}

}
