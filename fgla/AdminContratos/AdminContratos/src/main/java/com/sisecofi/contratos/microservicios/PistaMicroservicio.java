package com.sisecofi.contratos.microservicios;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sisecofi.libreria.comunes.model.pista.PistaModel;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@FeignClient(value = "pistaholder", url = "${url.pistas.auditoria}")
public interface PistaMicroservicio {

	@PutMapping(value = "/guardar", produces = "application/json")
	String guardarPista(@RequestBody PistaModel model);

}
