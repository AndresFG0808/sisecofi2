package com.sisecofi.reportedocumental.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.libreria.comunes.util.anotaciones.ConsumoInterno;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@RestController
@RequestMapping
public class TestController {

	@GetMapping
	@ConsumoInterno
	public String servicioOk() {
		return "Servicios desplegados correctamente";
	}
}
