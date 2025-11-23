package com.sisecofi.admindevengados.util.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PdfFileValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface PdfFile {

    String message() default "El archivo seleccionado no contiene la extensión .pdf. Favor seleccione un archivo con la extensión correcta.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
