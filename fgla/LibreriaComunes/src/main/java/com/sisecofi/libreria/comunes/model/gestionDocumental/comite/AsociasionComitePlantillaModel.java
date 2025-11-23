package com.sisecofi.libreria.comunes.model.gestionDocumental.comite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
import com.sisecofi.libreria.comunes.util.interfaces.ValidacionIncompleta;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "asociacion_comite_plantilla")
@Getter
@Setter
public class AsociasionComitePlantillaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAsociacionComitePlantilla;

    @JoinColumn(name = "id_comite_proyecto", insertable = false, updatable = false)
    @OneToOne(targetEntity = ComiteProyectoModel.class, fetch = FetchType.LAZY)
    @JsonIgnore
    private ComiteProyectoModel comiteProyectoModel;

    @Column(name = "id_comite_proyecto")
    private Integer idComiteProyecto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plantilla_vigente", insertable = false, updatable = false)
    @JsonIgnore
    private PlantillaVigenteModel plantillaVigenteModel;

    @Column(name = "id_plantilla_vigente")
    private Integer idPlantillaVigente;

    @NotNull(message = ErroresEnum.MensajeValidation.MSJ, groups= ValidacionIncompleta.class)
    @Column(name = "fecha_asignacion")
    private LocalDateTime fechaAsignacion;

    @NotNull(message = ErroresEnum.MensajeValidation.MSJ, groups= ValidacionIncompleta.class)
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @Column(name = "estatus")
    private boolean estatus;

    @Column(name = "otros_documentos")
    private boolean otrosDocumentos;

}
