package com.sisecofi.libreria.comunes.util.anotaciones;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CampoFront {

	String nombre();

	String tipoDato();

	int orden();

	boolean id() default false;
	
	String tamanio() default "";

}
