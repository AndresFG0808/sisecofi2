package com.sisecofi.proyectos.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EstructuraComiteCargarArchivosDto {

    private List<ArchivosCargadosDto> archivosCargados;
}
