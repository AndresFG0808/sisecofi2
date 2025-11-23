package com.sisecofi.libreria.comunes.dto.proyecto;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProyectoSimpleDto {
	private String nombreProyecto;
    private String nombreCorto;
    private Long idProyecto;

    public ProyectoSimpleDto(String nombreProyecto, String nombreCorto, Long idProyecto) {
        this.nombreProyecto = nombreProyecto;
        this.nombreCorto = nombreCorto;
        this.idProyecto = idProyecto;
    }

    public ProyectoSimpleDto() {
    }
}