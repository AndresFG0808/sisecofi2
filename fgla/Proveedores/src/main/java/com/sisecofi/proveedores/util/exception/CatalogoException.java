package com.sisecofi.proveedores.util.exception;



import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class CatalogoException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;

	private final DefinitionMessage definitionMessage;
	
	private final String mensaje;

	public CatalogoException(DefinitionMessage definitionMessage) {
		super(definitionMessage.getMessage());
		this.mensaje = "";
		this.definitionMessage = definitionMessage;
	}

	public CatalogoException(DefinitionMessage definitionMessage, String msj) {
		super(definitionMessage.getMessage() + msj);
		this.mensaje = msj;
		this.definitionMessage = definitionMessage;
	}

	public CatalogoException(DefinitionMessage definitionMessage, Throwable throwable) {
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
