package com.sisecofi.proveedores.util.exception;

import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;

public class PistaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	private final DefinitionMessage definitionMessage;
	private final String mensaje;

	public PistaException(DefinitionMessage definitionMessage) {
		super(definitionMessage.getMessage());
		this.mensaje = "";
		this.definitionMessage = definitionMessage;
	}

	public PistaException(DefinitionMessage definitionMessage, String mensaje) {
		super(definitionMessage.getMessage() + mensaje);
		this.mensaje = mensaje;
		this.definitionMessage = definitionMessage;
	}

	public PistaException(DefinitionMessage definitionMessage, Throwable throwable) {
		super(definitionMessage.getMessage(), throwable);
		this.mensaje = "";
		this.definitionMessage = definitionMessage;
	}

	public DefinitionMessage getDefinitionMessage() {
		return definitionMessage;
	}

	public String getMsj() {
		return mensaje;
	}


}
