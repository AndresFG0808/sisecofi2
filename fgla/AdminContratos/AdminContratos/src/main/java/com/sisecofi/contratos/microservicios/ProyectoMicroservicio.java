package com.sisecofi.contratos.microservicios;

import com.sisecofi.libreria.comunes.dto.proyecto.FichaTecnicaResponse;
import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


@FeignClient(value = "proyectoholder", url = "${url.proyecto}")
public interface ProyectoMicroservicio {

    @GetMapping("/ficha/{idProyecto}")
    Optional <FichaTecnicaResponse> obtenerFichaTecnica(@PathVariable("idProyecto") Long idProyecto);

    @GetMapping("/proyecto/{idProyecto}")
    ProyectoResponse obtenerProyecto(@PathVariable("idProyecto") Long idProyecto);

}
