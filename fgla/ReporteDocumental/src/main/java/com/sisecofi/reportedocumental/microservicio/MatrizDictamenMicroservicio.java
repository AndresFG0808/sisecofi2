package com.sisecofi.reportedocumental.microservicio;

import com.sisecofi.libreria.comunes.dto.carpeta.CarpetaDtoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(value = "dictamenholder", url = "${url.devengados}")
public interface MatrizDictamenMicroservicio {

    @SuppressWarnings("rawtypes")
	@GetMapping("/gestion-documental-dictamen/{idDitamen}")
    List<CarpetaDtoResponse> obtenerMatrizDoc(@PathVariable("idDitamen") Long idDitamen);


}
