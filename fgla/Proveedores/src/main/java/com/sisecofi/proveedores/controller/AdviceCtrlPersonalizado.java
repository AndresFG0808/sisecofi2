package com.sisecofi.proveedores.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.sisecofi.libreria.comunes.controller.AdviceController;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@ControllerAdvice
public class AdviceCtrlPersonalizado extends AdviceController {

	@Value("${DEBUG}")
	private boolean debug;

}
