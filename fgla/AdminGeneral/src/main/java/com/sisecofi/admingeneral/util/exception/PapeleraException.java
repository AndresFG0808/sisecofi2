package com.sisecofi.admingeneral.util.exception;

import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class PapeleraException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final DefinitionMessage definitionMessage;
	private final String msj;

	public PapeleraException(DefinitionMessage definitionMessage) {
		super(definitionMessage.getMessage());
		this.definitionMessage = definitionMessage;
		this.msj = definitionMessage.getMessage();
	}

	public PapeleraException(DefinitionMessage definitionMessage, String msj) {
		super(definitionMessage.getMessage() + msj);
		this.msj = msj;
		this.definitionMessage = definitionMessage;
	}

	public PapeleraException(DefinitionMessage definitionMessage, Throwable throwable) {
		super(definitionMessage.getMessage(), throwable);
		this.definitionMessage = definitionMessage;
		this.msj = definitionMessage.getMessage();
	}

	public DefinitionMessage getDefinitionMessage() {
		return definitionMessage;
	}

	public String getMsj() {
		return msj;
	}
}
