package com.sisecofi.proyectos.controller;

import com.sisecofi.libreria.comunes.dto.proyecto.FichaTecnicaResponse;
import com.sisecofi.libreria.comunes.util.anotaciones.ConsumoInterno;
import com.sisecofi.proyectos.service.ServicioFichaTecnica;
import com.sisecofi.proyectos.util.Constantes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/" +Constantes.PATH_BASE)
public class FichaTecnicaCtrInterno {
    private final ServicioFichaTecnica fichaServicio;

    @GetMapping("/"+Constantes.PATH_BASE_INTERNO +"/ficha/{idProyecto}")
    @ConsumoInterno
    public ResponseEntity<FichaTecnicaResponse> obtenerFicha(@PathVariable Long idProyecto) {
        return new ResponseEntity<>(fichaServicio.obtenerFicha(idProyecto), org.springframework.http.HttpStatus.OK);
    }
}
