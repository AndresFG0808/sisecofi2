package com.sisecofi.libreria.comunes.util.anotaciones;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface ClaseTemporal {

	String descripcion();
}
