package com.sisecofi.proyectos.model.adminplantrabajo;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseTareaPlanTrabajo {

    @Column(name = "idTarea", nullable = false)
    private Integer idTarea;

    @Column(name = "nivelEsquema", nullable = false)
    private Integer nivelEsquema;

    @Column(name = "nombreTarea", nullable = false)
    private String nombreTarea;

    @Column(name = "activo", nullable = false)
    private boolean activo;

    @Column(name = "duracionPlaneada", nullable = false)
    private Double duracionPlaneada;

    @Column(name = "fechaInicioPlaneado", nullable = false)
    private LocalDate fechaInicioPlaneada;

    @Column(name = "fechaFinPlaneado", nullable = false)
    private LocalDate fechaFinPlaneada;

    @Column(name = "duracionReal")
    private Double duracionReal;

    @Column(name = "fechaInicioReal")
    private LocalDate fechaInicioReal;

    @Column(name = "fechaFinReal")
    private LocalDate fechaFinReal;

    @Column(name = "predecesora")
    private Integer predecesora;

    @Column(name = "porcentajePlaneado")
    private Integer porcentajePlaneado;

    @Column(name = "porcentajeCompletado")
    private Integer porcentajeCompletado;
   
}
