package com.sisecofi.proyectos.service;


import com.sisecofi.proyectos.dto.EstructuraProyectoMetaDto;

public interface ServicioReporteProyectos {
    byte[] obtenerReporteProyectosRegistrados(EstructuraProyectoMetaDto proyecto);
}
