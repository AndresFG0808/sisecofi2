package com.sisecofi.libreria.comunes.model.contratos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoConsumo;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "GrupoServicioContrato")
@Getter
@Setter
public class GrupoServiciosModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idGrupoServicio;

	@JoinColumn(name = "id_contrato", insertable = false, updatable = false)
	@ManyToOne(targetEntity = ContratoModel.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private ContratoModel contratoModel;

	@Column(name = "id_contrato")
	private Long idContrato;

	@Column(name = "grupo")
	private String grupo;

	@JoinColumn(name = "id_tipo_consumo", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatTipoConsumo.class, fetch = FetchType.EAGER)
	private CatTipoConsumo catTipoConsumo;

	@NotNull(message = "tipo de consumo es un campo obligatorio")
	@Column(name = "id_tipo_consumo")
	private Integer idTipoConsumo;

	@Column(name = "estatus")
	private Boolean estatus;
}
