package com.sisecofi.proyectos.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InformacionArchivosComiteDto {

    private Long idComitePlantilla;

    private Integer idArchivoPlantilla;

    private  Boolean cargado;

    private String ruta;

    private Integer idArchivoPlantillaComite;

    private Integer cantitadArchivos;

    private boolean requerido ;

    private boolean estatus;

    private String descripcion;

    private Integer tamano;
}
