package com.sisecofi.proyectos.controller;

import com.sisecofi.libreria.comunes.dto.PapeleraDto;
import com.sisecofi.libreria.comunes.util.anotaciones.ConsumoInterno;
import com.sisecofi.proyectos.service.ServicioPapelera;
import com.sisecofi.proyectos.util.Constantes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class PapeleraCtrl {

    private final ServicioPapelera servicioPapelera;


    @GetMapping("/papelera/archivos-papelera")
    @ConsumoInterno
    public ResponseEntity<List<PapeleraDto>>  obtenerPlantillasVigentes() {
        return new ResponseEntity<>(servicioPapelera.obtenerPapelera(), HttpStatus.OK);
    }

    @GetMapping("/papelera/archivo/{idPapelera}")
    @ConsumoInterno
    public ResponseEntity<PapeleraDto> obtenerPlantillaPorId(@PathVariable("idPapelera") Long idPapelera) {
        return new ResponseEntity<>(servicioPapelera.obtenerArchivoPapelera(idPapelera), HttpStatus.OK);
    }

    @GetMapping("/papelera/restaurar-archivo/{idPapelera}")
    @ConsumoInterno
    public ResponseEntity<PapeleraDto> restaurarArchivo(@PathVariable("idPapelera") Long idPapelera){
        return new ResponseEntity<>(servicioPapelera.restaurarArchivo(idPapelera), HttpStatus.OK);
    }
}
