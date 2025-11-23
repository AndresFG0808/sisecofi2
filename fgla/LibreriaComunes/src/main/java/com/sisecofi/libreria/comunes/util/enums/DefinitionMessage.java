package com.sisecofi.libreria.comunes.util.enums;

import java.io.Serializable;

/**
 * Clase para extender y tener todos los mensajes en un solo lugar para despues
 * mandarlo a una exception
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface DefinitionMessage extends Serializable {

	/**
	 * Regresa el valor del mensaje
	 * 
	 * @return
	 */
	String getMessage();

	/**
	 * Regresa la clave del mensaje
	 * 
	 * @return
	 */
	String getClave();

}
