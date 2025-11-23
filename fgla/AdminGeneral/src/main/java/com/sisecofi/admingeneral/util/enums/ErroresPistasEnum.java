package com.sisecofi.admingeneral.util.enums;

import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public enum ErroresPistasEnum implements DefinitionMessage {

	ERROR_GUARDAR_PISTA("001", "Error al guardar la pista de auditoria");

	private String clave;
	private String message;

	ErroresPistasEnum(String clave, String message) {
		this.clave = clave;
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getClave() {
		return clave;
	}

}
