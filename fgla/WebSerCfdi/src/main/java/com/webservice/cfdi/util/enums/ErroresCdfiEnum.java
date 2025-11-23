package com.webservice.cfdi.util.enums;

import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public enum ErroresCdfiEnum implements DefinitionMessage {
	ERROR_DIRECTORIO_ACTIVO("001", "Error en directorio activo"),
	ERRO_SIN_USUARIO("002","Se necesita el usaurio");

	private String clave;
	private String message;

	ErroresCdfiEnum(String clave, String message) {
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
