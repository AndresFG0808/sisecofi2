package com.sisecofi.proyectos.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Getter
@Setter
public class ProyectoNombreDto {

	private Long idProyecto;
	private String nombreCorto;
	private Integer idEstatusProyecto;

	public ProyectoNombreDto() {
	}

	public ProyectoNombreDto(Long idProyecto, String nombreCorto, Integer idEstatusProyecto) {
		this.idProyecto = idProyecto;
		this.nombreCorto = nombreCorto;
		this.idEstatusProyecto = idEstatusProyecto;
	}
}
