package com.sisecofi.admingeneral.util.enums;

import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public enum ErroresAdminUsuarioEnum implements DefinitionMessage {

	ERROR_DIRECTORIO_ACTIVO("001", "Sin usuario"),
	ERROR_NO_ENCONTRADO("002", "Usuario no encontrado en directorio activo");

	private String clave;
	private String message;

	ErroresAdminUsuarioEnum(String clave, String message) {
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

	public class Msj {
		private Msj() {}
		public static final String MSJ001 = "Favor de ingresar como mínimo un criterio de búsqueda";
	}
}
