package com.sisecofi.libreria.comunes.util.enums;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public enum TipoMovPista {
	
	INSERTA_REGISTRO(1,"INSR"),
	CONSULTA_REGISTRO(2,"CNST"),
	ACTUALIZA_REGISTRO(3,"UPDT"),
	BORRA_REGISTRO(4,"DLT"),
	IMPRIME_REGISTRO(5,"PRNT");
	
	private int id;
	private String clave;

	TipoMovPista(int id,String clave){
		this.id = id;
		this.clave = clave;
	}

	public int getId() {
		return id;
	}

	public String getClave() {
		return clave;
	}

}
