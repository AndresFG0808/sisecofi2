package com.sisecofi.libreria.comunes.util.exception;

import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class ErrorSistemaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final DefinitionMessage definitionMessage;
	private String msj;

	public ErrorSistemaException(DefinitionMessage definitionMessage) {
		super(definitionMessage.getMessage());
		this.definitionMessage = definitionMessage;
	}

	public ErrorSistemaException(DefinitionMessage definitionMessage, String msj) {
		super(definitionMessage.getMessage() + msj);
		this.msj = msj;
		this.definitionMessage = definitionMessage;
	}

	public ErrorSistemaException(DefinitionMessage definitionMessage, Throwable throwable) {
		super(definitionMessage.getMessage(), throwable);
		this.definitionMessage = definitionMessage;
	}

	public DefinitionMessage getDefinitionMessage() {
		return definitionMessage;
	}

	public String getMsj() {
		return msj;
	}

}
