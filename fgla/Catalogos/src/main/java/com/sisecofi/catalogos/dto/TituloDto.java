package com.sisecofi.catalogos.dto;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Data
public class TituloDto implements Comparable<TituloDto> {

	private int orden;
	private String nombre;
	private String nombreReporte;

	@Override
	public int compareTo(TituloDto o) {
		return this.getOrden() - o.getOrden();
	}

	public TituloDto(String nombre, int orden, String nombreReporte) {
		this.nombre = nombre;
		this.orden = orden;
		this.nombreReporte = nombreReporte;
	}

}
