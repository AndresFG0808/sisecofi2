package com.sisecofi.admingeneral.util.enums;

import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public enum ErroresPlantilladorEnum implements DefinitionMessage {

	ERROR_GENERAR_DOCX("001", "Error al generar el docx"),
	ERROR_CREANDO_PDF("002","Error creando documento PDF");
	


	private String clave;
	private String message;

	ErroresPlantilladorEnum(String clave, String message) {
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
