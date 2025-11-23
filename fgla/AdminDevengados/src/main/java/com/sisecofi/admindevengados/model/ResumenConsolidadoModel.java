package com.sisecofi.admindevengados.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.CatFaseDictamen;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "resumen_consolidado")
@Getter
@Setter
public class ResumenConsolidadoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idResumenConsolidado;

	@JoinColumn(name = "idFaseDictamen", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatFaseDictamen.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private CatFaseDictamen catFaseDictamen;
	
	private Integer idFaseDictamen;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Digits(integer = 20, fraction = 2)
	private BigDecimal subTotal;

	private BigDecimal deducciones;
	private BigDecimal ieps;
	private BigDecimal iva;
	private BigDecimal otrosImpuestos;
	private BigDecimal total;
	private BigDecimal totalPesos;
	
	@JoinColumn(name = "idDictamen", insertable = false, updatable = false)
	@ManyToOne(targetEntity = Dictamen.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private Dictamen dictamen;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "idDictamen")
	private Long idDictamen;

}
