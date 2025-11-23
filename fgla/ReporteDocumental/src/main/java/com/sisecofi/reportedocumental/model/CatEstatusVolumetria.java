package com.sisecofi.reportedocumental.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = ConstantesComunes.PREFIX_CAT + "estatusVolumetria", schema = "sisecofi")
public class CatEstatusVolumetria {
    @Id
    @Column(name = "id_estatus_volumetria")
    @JsonProperty("primaryKey")
    private Integer idEstatusVolumetria;

    @Column(name = "estatus")
    @JsonProperty("estatus")
    private boolean estatus;

    @Column(name = "fecha_creacion")
    @JsonProperty("fechaCreacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion")
    @JsonProperty("fechaModificacion")
    private LocalDateTime fechaModificacion;

    @Column(name = "descripcion", length = 300)
    @JsonProperty("descripcion")
    String descripcion;

    @Column(name = "nombre", length = 150)
    @JsonProperty("nombre")
    String nombre;
}
