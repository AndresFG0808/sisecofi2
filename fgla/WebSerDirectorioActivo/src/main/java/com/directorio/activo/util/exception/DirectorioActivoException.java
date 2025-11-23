package com.directorio.activo.util.exception;

import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class DirectorioActivoException extends Exception {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	private final DefinitionMessage definitionMessage;
	private final String msj;

	public DirectorioActivoException(DefinitionMessage definitionMessage) {
		super(definitionMessage.getMessage());
		this.msj = "";
		this.definitionMessage = definitionMessage;
	}

	public DirectorioActivoException(DefinitionMessage definitionMessage, String msj) {
		super(definitionMessage.getMessage() + msj);
		this.msj = msj;
		this.definitionMessage = definitionMessage;
	}

	public DirectorioActivoException(DefinitionMessage definitionMessage, Throwable throwable) {
		super(definitionMessage.getMessage(), throwable);
		this.msj = "";
		this.definitionMessage = definitionMessage;
	}

	public DefinitionMessage getDefinitionMessage() {
		return definitionMessage;
	}

	public String getMsj() {
		return msj;
	}

}
