package com.sisecofi.libreria.comunes.dto;

import java.util.List;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Data
public class AccionesDto<T> {

	private List<T> agregar;
	private List<T> editar;
	private List<Long> eliminar;

}
