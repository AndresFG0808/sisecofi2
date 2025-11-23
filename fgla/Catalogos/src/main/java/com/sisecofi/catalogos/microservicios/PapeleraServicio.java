package com.sisecofi.catalogos.microservicios;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sisecofi.libreria.comunes.dto.PapeleraDto;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@FeignClient(value = "papeleraholder", url = "${url.papelera.id}")
public interface PapeleraServicio {

	@PutMapping
	PapeleraDto generarId(@RequestBody PapeleraDto model);

}
