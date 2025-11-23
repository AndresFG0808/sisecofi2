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

@Repeatable(Joinys.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Joiny {

	String tableJoin();

	String fieldJoin();

	String fieldJoinReverse() default "";

	String alias() default "";

	String extraTableJoin() default "";

	String extraFieldJoin() default "";

	String[] campos() default {};

	String extraAlias() default "";

	String specificJoin() default "";

	String joinCondition() default "";

}
