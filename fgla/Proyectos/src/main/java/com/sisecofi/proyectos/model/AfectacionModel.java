package com.sisecofi.proyectos.model;

import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = ConstantesComunes.PREFIX_CAT + "afectacion")
@Getter
@Setter
public class AfectacionModel {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long idAfectacion;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estatus")
    private boolean estatus;

    @Column(name = "fechaCreacion")
    private LocalDate fechaCreacion;

    @Column(name = "fechaModificacion")
    private LocalDate fechaModificacion;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "activo")
    private boolean activo;

    @Column(name = "clave")
    private String clave;
}
