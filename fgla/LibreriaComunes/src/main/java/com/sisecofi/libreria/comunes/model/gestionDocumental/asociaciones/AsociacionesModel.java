package com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
import com.sisecofi.libreria.comunes.util.interfaces.ValidacionCompleta;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "asociaciones")
@Getter
@Setter
public class AsociacionesModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idAsociacion;
	
	@JoinColumn(name = "id_proyecto", insertable = false, updatable = false)
	@OneToOne(targetEntity = ProyectoModel.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private ProyectoModel proyectoModel;

	@Column(name = "id_proyecto")
	private Long idProyecto;
	
	@JoinColumn(name = "id_plantilla_vigente", insertable = false, updatable = false)
	@ManyToOne(targetEntity = PlantillaVigenteModel.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private PlantillaVigenteModel plantillaVigenteModel;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MSJ, groups= ValidacionCompleta.class)
	@Column(name = "id_plantilla_vigente")
	private Integer idPlantillaVigente;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MSJ, groups= ValidacionCompleta.class)
	@Column(name = "fecha_asignacion")
	private LocalDate fechaAsignacion;
	
	@Column(name = "estatus_asociacion")
	private boolean estatusAsociacion;

    @Column(name = "estatus")
    private boolean estatus;
    
    @Column(name = "cargado")
    private boolean cargado;
    
    @Column(name = "orden")
	private Integer orden;
	
}
