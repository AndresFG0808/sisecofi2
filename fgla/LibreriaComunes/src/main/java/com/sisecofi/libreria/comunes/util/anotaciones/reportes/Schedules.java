package com.sisecofi.libreria.comunes.util.anotaciones.reportes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Schedules {

	Schedule[] value();
}
