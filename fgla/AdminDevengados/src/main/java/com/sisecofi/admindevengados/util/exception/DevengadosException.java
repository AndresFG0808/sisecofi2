package com.sisecofi.admindevengados.util.exception;

import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;

public class DevengadosException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    private final DefinitionMessage definitionMessage;
    private final String msj;

    public DevengadosException(DefinitionMessage definitionMessage) {
        super(definitionMessage.getMessage());
        this.definitionMessage = definitionMessage;
        this.msj = definitionMessage.getMessage();
    }

    public DevengadosException(DefinitionMessage definitionMessage, Throwable throwable) {
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

