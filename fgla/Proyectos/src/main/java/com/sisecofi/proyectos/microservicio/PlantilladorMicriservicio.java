package com.sisecofi.proyectos.microservicio;

import com.sisecofi.libreria.comunes.model.plantillador.PlantilladorModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "administracionUrl", url = "${url.administracion}")
public interface PlantilladorMicriservicio {

    @GetMapping("/obtener-plantillador/{idTipoPlantillador}")
    List<PlantilladorModel> obtenerPlantillador(@PathVariable("idTipoPlantillador") Integer idTipoPlantillador);
    
    @GetMapping("/plantillador/{idPlantillador}")
    PlantilladorModel obtenerPlantilladorPorId(@PathVariable("idPlantillador") Long idPlantillador);
    
}
