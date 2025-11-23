package com.sisecofi.proyectos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArchivosActualizadosDto {
    private Integer idAsociacion;
    private Integer idComiteProyecto;
    private Integer idPlantillaVigente;
    private Integer idArchivoPlantillaComite;
    private ArchivoPlantillaComiteDto archivoPlantillaComiteDto;
    private String archivoCargado;
    private String descripcionCarpeta;
}
