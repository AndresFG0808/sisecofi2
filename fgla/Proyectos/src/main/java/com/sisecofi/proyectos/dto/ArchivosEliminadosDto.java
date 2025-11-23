package com.sisecofi.proyectos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArchivosEliminadosDto {

   private Integer idComiteProyecto;
   private Integer idArchivoPlantillaComite;
   private Integer idArchivoOtrosDocumentos;
   private String path;
}

