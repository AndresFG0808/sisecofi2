package com.sisecofi.admingeneral.dto.adminplantillas;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarpetaDtoResponse <T>{
    private Integer idCarpetaPlantilla;
    private String nombre;
    private int orden;
    private int nivel;
    private int dato;
    private String descripcion;
    private String tipo;
    private boolean obligatorio;
    private boolean estatus;
    
    @JsonInclude(Include.NON_NULL)
    private List<T> subCarpetas;  
    
    @JsonInclude(Include.NON_NULL)
    private List<ArchivoDto> archivos;

    @Override
    public String toString() {
        return "CarpetaDto [nombre=" + nombre + ", orden=" + orden + ", nivel=" + nivel + "]";
    }
}