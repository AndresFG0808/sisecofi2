package com.sisecofi.proyectos.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArchivoOtrosDocumentosDto {
    private Integer idArchivoPlantillaComite;
    private String nombre;
    private String justificacion;
    private String descripcionCarpeta;
    private boolean noAplica;
    private boolean estatus;
    private String ruta;
    private Double tamanoMb;
    private LocalDateTime fechaModificacion;
    private boolean otrosDocumentosInterno;
    private String archivoCargadoOtrosDocumentos;
    private Integer idCarpetaPlantilla;
}
