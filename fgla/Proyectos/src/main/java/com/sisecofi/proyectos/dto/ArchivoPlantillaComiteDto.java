package com.sisecofi.proyectos.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArchivoPlantillaComiteDto {
    private Integer idArchivoPlantillaComite;
    private String nombre;
    private String justificacion;
    private String descripcion;
    private String descripcionCarpeta;
    private boolean noAplica;
    private boolean estatus;
    private String ruta;
    private Double tamanoMb;
    private LocalDateTime fechaModificacion;
    private String archivoCargado;
    private Integer idArchivoPlantilla;
}
