package com.sisecofi.contratos.microservicios;

import com.sisecofi.libreria.comunes.dto.PapeleraDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "papeleraholder", url = "${url.papelera.internos}")
public interface PapeleraServico {
    @PutMapping("")
    PapeleraDto generarId(@RequestBody PapeleraDto model);

    @GetMapping("/papelera/obtener-papelera")
    List<PapeleraDto> obtenerPapelera();

    @GetMapping("/papelera/obtener-archivo/{idPapelera}")
    PapeleraDto obtenerArchivo(@PathVariable Long idPapelera);

    @GetMapping("/papelera/restaurar-archivo/{idPapelera}")
    PapeleraDto restaurarArchivo(@PathVariable Long idPapelera);
}
