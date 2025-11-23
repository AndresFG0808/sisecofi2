package com.sisecofi.admindevengados.microservicio;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sisecofi.libreria.comunes.dto.plantilla.PlantillaDto;
import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@FeignClient(value = "plantillaDocholder", url = "${url.plantilla.documental}")
public interface PlantillaDocMicroservicio {

	@GetMapping("/obtener-plantilla/{idPlantilla}")
	<T> PlantillaDto<T> obtenerPlantilla(@PathVariable("idPlantilla") Integer idPlantilla);

	@GetMapping("/plantillas")
	List<PlantillaVigenteModel> plantillas();

	@GetMapping("/plantillas/{idFaseProyecto}")
	List<PlantillaVigenteModel> plantillasFase(@PathVariable Integer idFaseProyecto);

	@GetMapping("/plantilla/{idPlantillaVigente}")
	PlantillaVigenteModel plantillaVigente(@PathVariable Integer idPlantillaVigente);
	
	
	@GetMapping("/plantilla/obtener-plantilla-verificacion")
	CarpetaPlantillaModel obtenerArchivosVerificacion();
	
}
