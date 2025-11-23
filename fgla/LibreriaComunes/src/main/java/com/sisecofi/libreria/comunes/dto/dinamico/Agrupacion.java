package com.sisecofi.libreria.comunes.dto.dinamico;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Agrupacion {
    private String etiqueta;
    private int tamanio;

    public Agrupacion(String etiqueta, int tamanio) {
        this.etiqueta = etiqueta;
        this.tamanio = tamanio;
    }
}