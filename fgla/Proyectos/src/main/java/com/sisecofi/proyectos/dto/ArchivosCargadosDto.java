package com.sisecofi.proyectos.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArchivosCargadosDto {

   private Integer idCarpetaPlantillaComite;
   private Integer idAsociacion;
   private Integer idComiteProyecto;
   private Integer idPlantillaVigente;
   private List<ArchivoPlantillaComiteDto> archivoPlantillaComiteDto;
   private List<ArchivoOtrosDocumentosDto> archivoOtrosDocumentosDto;
}
