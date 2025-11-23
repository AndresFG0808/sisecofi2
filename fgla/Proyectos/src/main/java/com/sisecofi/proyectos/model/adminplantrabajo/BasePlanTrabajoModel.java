package com.sisecofi.proyectos.model.adminplantrabajo;

import java.time.LocalDateTime;

import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;

import jakarta.persistence.Column;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BasePlanTrabajoModel {

    @ManyToOne
    @JoinColumn(name = "idProyecto", foreignKey = @ForeignKey(name = "FK_id_proyecto"), nullable = false)
    private ProyectoModel proyectoModel;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "comentarios", nullable = false)
    private String comentarios;
}
