package com.sisecofi.proyectos.model;

import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = ConstantesComunes.PREFIX_TRAB + "afectacionComite")
@Getter
@Setter
public class AfectacionComiteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAfectacionComite;

    @Column(name = "id_afectacion")
    private Integer idAfectacion;

    @Column(name = "id_comite_proyecto")
    private Integer idComiteProyecto;
}
