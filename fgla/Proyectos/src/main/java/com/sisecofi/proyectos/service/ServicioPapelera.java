package com.sisecofi.proyectos.service;

import com.sisecofi.libreria.comunes.dto.PapeleraDto;

import java.util.List;

public interface ServicioPapelera {

    PapeleraDto obtenerArchivoPapelera(Long idPapelera);

    List<PapeleraDto> obtenerPapelera();

    PapeleraDto restaurarArchivo(Long idPapelera);
}
