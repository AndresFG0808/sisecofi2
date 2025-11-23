package com.sisecofi.libreria.comunes.dto.carpeta;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.ArchivoPlantillaProyectoModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarpetaDtoResponse <T>{
    private Integer idCarpetaPlantillaProyecto;
    private Long idReintegro;
    private String nombre;
    private String ruta;
    private String descripcion;
    private String type;
    private boolean descarga;
    
    @JsonInclude(Include.NON_NULL)
    private List<T> subRows;  

    public boolean isDescarga() {
        if (subRows == null) {
            return descarga;
        }

        for (T row : subRows) {
            if (esDescargable(row)) {
                return true;
            }
        }
        return descarga;
    }

    private boolean esDescargable(Object row) {
        return (row instanceof CarpetaDtoResponse<?> carpeta && carpeta.isDescarga()) ||
               (row instanceof ArchivoPlantillaProyectoModel archivoPlantilla && archivoPlantilla.isCargado()) ||
               (row instanceof Archivo archivo && archivo.isCargado());
    }

}