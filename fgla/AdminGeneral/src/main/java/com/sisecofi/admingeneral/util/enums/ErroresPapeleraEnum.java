package com.sisecofi.admingeneral.util.enums;

import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public enum ErroresPapeleraEnum implements DefinitionMessage {

	ERROR_MSJ_SIN_USUARIO("001", "No se encontro usuario"),
	ERROR_AL_GUARDAR("002", "No se pudo guardar en el catalogo"),
	ERROR_AL_CONSULTAR("003","No se pudo consultar la información"),
	ERROR_AL_ELIMINAR("004","No se pudo eliminar la información");

	private String clave;
	private String message;

	ErroresPapeleraEnum(String clave, String message) {
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
