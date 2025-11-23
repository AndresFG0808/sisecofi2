package com.sisecofi.catalogos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sisecofi.libreria.comunes.controller.AdviceController;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@RestControllerAdvice
public class AdviceCtrlPersonalizado extends AdviceController {

	@Value("${DEBUG}")
	private boolean debug;

}
