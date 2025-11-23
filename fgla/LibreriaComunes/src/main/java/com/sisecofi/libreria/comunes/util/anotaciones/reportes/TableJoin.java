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

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TableJoin {

	int priority();

	String tableJoin();

	String fieldJoin();

	String alias() default "";

	String joinCondition() default "";

	String extraTableJoin() default "";

	String extraFieldJoin() default "";

	String extraAlias() default "";

	String specificJoin() default "";

	String extraJoinCondition() default "";

	String specificFieldJoin() default "";

	String orderyBy() default "";
	
}
