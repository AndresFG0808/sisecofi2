package com.sisecofi.libreria.comunes.aspecto.security;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.sisecofi.libreria.comunes.util.anotaciones.ConsumoInterno;
import com.sisecofi.libreria.comunes.util.enums.ErroresSistema;
import com.sisecofi.libreria.comunes.util.exception.ErrorSistemaException;
import com.sisecofi.libreria.comunes.util.sesion.Session;

import lombok.extern.slf4j.Slf4j;

/**
 * Clase para controlar el permiso a un recurso en un controller
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Aspect
@Component
@Slf4j
public class Permisos {

	@Autowired
	private Session session;

	@Before("execution(* com.sisecofi..controller..*.*(..))")
	public void validarPermisoLectura(JoinPoint jp) {
	    MethodSignature signature = (MethodSignature) jp.getSignature();
	    Method method = signature.getMethod();
	    ConsumoInterno consumoInterno = AnnotationUtils.findAnnotation(method, ConsumoInterno.class);
	    if (consumoInterno != null) {
	        log.info("Consumo interno: {}", session.retornarUsuario());
	        return;
	    }
	    PreAuthorize preAuthorize = AnnotationUtils.findAnnotation(method, PreAuthorize.class);
	    if (preAuthorize == null) {
	        throw new ErrorSistemaException(ErroresSistema.ERROR_SE_PRE_AUTORIZACION);
	    }
	}

	
}
