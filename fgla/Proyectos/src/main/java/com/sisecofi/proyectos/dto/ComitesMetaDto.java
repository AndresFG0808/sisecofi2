package com.sisecofi.proyectos.dto;

import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;

@Getter
@Setter
public class ComitesMetaDto {

    private Long idComite;

    private String descripcion;

    private boolean estatus;

    private LocalDate fechaCreacion;

    private LocalDate fechaModificacion;

    private String nombre;

    private boolean activo;
}
