package com.sisecofi.libreria.comunes.dto.dinamico;

import lombok.Builder;
import lombok.ToString;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Builder
@ToString
public class CampoOrden implements Comparable<CampoOrden> {

	private static final String VALOR = "%valor";
	private static final String FUNCTION_AGREGATION = "SUM,COUNT,MAX,MIN,AVG";

	private int orden;
	private String campo;
	private String nombreFront;
	private boolean acumulada;
	private String function;

	@Override
	public int compareTo(CampoOrden o) {
		return orden - o.orden;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public String getCampoSolo() {
		return campo;
	}

	public String getCampo() {
		if (function != null && !function.isEmpty()) {
			if (acumulada) {
				if (FUNCTION_AGREGATION.contains(function.substring(0, 2))) {
					return function.replace(VALOR, campo);
				}
			} else {
				if (FUNCTION_AGREGATION.contains(function.substring(0, 2))) {
					return campo;
				}
				return function.replace(VALOR, campo);
			}
		}
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getNombreFront() {
		return nombreFront;
	}

	public void setNombreFront(String nombreFront) {
		this.nombreFront = nombreFront;
	}

	public boolean isAcumulada() {
		return acumulada;
	}

	public void setAcumulada(boolean acumulada) {
		this.acumulada = acumulada;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

}
