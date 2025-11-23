package com.sisecofi.proyectos.model;

import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = ConstantesComunes.PREFIX_CAT + "contratoConvenio")
@Getter
@Setter
public class ContratoConvenioModel {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long idContratoConvenio;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estatus")
    private boolean estatus;

    @Column(name = "fechaCreacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fechaModificacion")
    private LocalDateTime fechaModificacion;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "activo")
    private boolean activo;

    @Column(name = "clave")
    private String clave;
}
