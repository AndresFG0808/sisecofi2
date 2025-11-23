package com.sisecofi.libreria.comunes.util.exception;

import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final DefinitionMessage definitionMessage;
	private String msj;

	public UserNotFoundException(DefinitionMessage definitionMessage) {
		super(definitionMessage.getMessage());
		this.definitionMessage = definitionMessage;
	}

	public UserNotFoundException(DefinitionMessage definitionMessage, String msj) {
		super(definitionMessage.getMessage() + msj);
		this.msj = msj;
		this.definitionMessage = definitionMessage;
	}

	public UserNotFoundException(DefinitionMessage definitionMessage, Throwable throwable) {
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
