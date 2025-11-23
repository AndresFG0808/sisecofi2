package com.sisecofi.admingeneral.dto.adminplantillas;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArchivoDto {
    private String nombre;
    private int orden;
    private String tipo;
    private boolean obligatorio;
    private boolean estatus;
    private String descripcion;

    @Override
    public String toString() {
        return "ArchivoDto [nombre=" + nombre + ", orden=" + orden + "]";
    }
}