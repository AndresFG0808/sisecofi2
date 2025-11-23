package com.sisecofi.reportedocumental.util.exception;

import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;

/**
 * 
 * @author omartinezj
 *
 */
public class ControlDocumentalException extends RuntimeException {

	private static final long serialVersionUID = 8020910716291843590L;
	
	private final DefinitionMessage definitionMessage;
	private final String msj;

	public ControlDocumentalException(DefinitionMessage definitionMessage) {
		super(definitionMessage.getMessage());
		this.definitionMessage = definitionMessage;
		this.msj = definitionMessage.getMessage();
	}

	public ControlDocumentalException(DefinitionMessage definitionMessage, String msj) {
		super(definitionMessage.getMessage() + msj);
		this.msj = msj;
		this.definitionMessage = definitionMessage;
	}

	public ControlDocumentalException(DefinitionMessage definitionMessage, Throwable throwable) {
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
