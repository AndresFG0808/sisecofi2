package com.sisecofi.proyectos.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CarpetaPlantillaComiteDto {
    private Integer idCarpetaPlantilla;
    private String nombre;
    private int orden;
    private int nivel;
    private int dato;
    private String tipo;
    private boolean obligatorio;
    private boolean estatus;
    private String justificacion;
    private String ruta;
    private boolean otrosDocumentos;


    private List<ArchivoPlantillaComiteDto> archivos;
    private List<CarpetaPlantillaComiteDto> subCarpetas;
}
