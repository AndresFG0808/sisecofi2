package com.sisecofi.libreria.comunes.util.anotaciones.reportes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Repeatable(Schedules.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Schedule {
	String dayOfMonth() default "first";

	String dayOfWeek() default "Mon";

	int hour() default 12;
}
