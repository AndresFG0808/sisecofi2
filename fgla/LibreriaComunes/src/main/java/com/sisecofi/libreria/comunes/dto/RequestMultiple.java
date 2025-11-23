package com.sisecofi.libreria.comunes.dto;

import java.util.List;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
public class RequestMultiple<T, M> {

	private List<T> agregarModificar;
	private List<M> eliminar;
}
