package com.sisecofi.reportedocumental.util.enums;

import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;

/**
 * 
 * @author omartinezj
 *
 */

public enum ErroresPapeleraEnum implements DefinitionMessage {

	ERROR_MSJ_SIN_USUARIO("001", "No se encontro usuario"),
	ERROR_AL_GUARDAR("002", "No se pudo guardar en el catalogo"),
	ERROR_AL_CONSULTAR("003","No se pudo consultar la información"),
	ERROR_AL_ELIMINAR("004","No se pudo eliminar la información"),
	ERROR_AL_RESTAURAR("005","No se pudo restaurar la información");

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
