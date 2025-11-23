package com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.contratos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
import com.sisecofi.libreria.comunes.util.interfaces.ValidacionCompleta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = ConstantesComunes.PREFIX_REL + "contratoPlantilla")
@Getter
@Setter
public class ContratoPlantilla {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idContratoPlantilla;

	@JoinColumn(name = "id_contrato", insertable = false, updatable = false)
	@ManyToOne(targetEntity = ContratoModel.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private ContratoModel contratoModel;

	@Column(name = "id_contrato")
	private Long idContrato;
	
	@Column(name = "estatus")
	private Boolean estatus;
	
	@JoinColumn(name = "id_plantilla_vigente", insertable = false, updatable = false)
	@ManyToOne(targetEntity = PlantillaVigenteModel.class, fetch = FetchType.EAGER)
	private PlantillaVigenteModel plantillaVigenteModel;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MSJ, groups= ValidacionCompleta.class)
	@Column(name = "id_plantilla_vigente")
	private Integer idPlantillaVigente;
}
