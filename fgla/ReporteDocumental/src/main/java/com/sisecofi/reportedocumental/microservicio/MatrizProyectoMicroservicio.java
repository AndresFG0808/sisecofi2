package com.sisecofi.reportedocumental.microservicio;

import com.sisecofi.libreria.comunes.dto.carpeta.CarpetaDtoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(value = "proyectoholder", url = "${url.proyecto}")
public interface MatrizProyectoMicroservicio {

    @SuppressWarnings("rawtypes")
	@GetMapping("/gestion-documental-proyectos/{idProyecto}")
    List<CarpetaDtoResponse> obtenerMatrizDoc(@PathVariable("idProyecto") Long idProyecto);


}
