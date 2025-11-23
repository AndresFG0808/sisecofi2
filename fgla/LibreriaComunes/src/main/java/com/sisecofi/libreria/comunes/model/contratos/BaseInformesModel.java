package com.sisecofi.libreria.comunes.model.contratos;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@MappedSuperclass
@EqualsAndHashCode(callSuper = false)
public class BaseInformesModel {
	@Column(name = "descripcion")
	@Size(min = 0)  
	private String descripcion;
	
	@Column(name = "estatus")
	private boolean estatus;
	
	@JoinColumn(name = "id_contrato", insertable = false, updatable = false)
	@ManyToOne(targetEntity = ContratoModel.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private ContratoModel contratoModel;

	@Column(name = "id_contrato")
	private Long idContrato;
	
	
}
