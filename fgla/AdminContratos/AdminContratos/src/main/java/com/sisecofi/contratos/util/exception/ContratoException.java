package com.sisecofi.contratos.util.exception;

import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;


public class ContratoException extends RuntimeException {

    public final DefinitionMessage definitionMessage;
    public final String msj;

    public ContratoException(DefinitionMessage definitionMessage){
        super(definitionMessage.getMessage());
        this.definitionMessage = definitionMessage;
        this.msj = definitionMessage.getMessage();
    }

    public ContratoException(DefinitionMessage definitionMessage, Throwable throwable) {
        super(definitionMessage.getMessage(), throwable);
        this.definitionMessage = definitionMessage;
        this.msj = definitionMessage.getMessage();
    }
}
