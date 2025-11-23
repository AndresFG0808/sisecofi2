package com.sisecofi.admindevengados.util.exception;

import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class PistaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final DefinitionMessage definitionMessage;

    public PistaException(DefinitionMessage definitionMessage) {
        super(definitionMessage.getMessage());
        this.definitionMessage = definitionMessage;
    }

    public PistaException(DefinitionMessage definitionMessage, String msj) {
        super(definitionMessage.getMessage() + msj);
        this.definitionMessage = definitionMessage;
    }

    public PistaException(DefinitionMessage definitionMessage, Throwable throwable) {
        super(definitionMessage.getMessage(), throwable);
        this.definitionMessage = definitionMessage;
    }

    public DefinitionMessage getDefinitionMessage() {
        return definitionMessage;
    }
}
