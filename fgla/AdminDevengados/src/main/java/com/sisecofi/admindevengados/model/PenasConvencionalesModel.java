package com.sisecofi.admindevengados.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoPenaConvencional;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.model.penasContractuales.BasePenas;
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
@Table(name = ConstantesComunes.PREFIX_TRAB + "penas_Convencionales_Dictamenes")
@Getter
@Setter
public class PenasConvencionalesModel extends BasePenas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPenaConvencional;

	@JoinColumn(name = "idDictamen", insertable = false, updatable = false)
	@ManyToOne(targetEntity = Dictamen.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private Dictamen dictamen;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "idDictamen")
	private Long idDictamen;

	@JoinColumn(name = "idTipoPenaConvencional", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatTipoPenaConvencional.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatTipoPenaConvencional catTipoPenaConvencional;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "idTipoPenaConvencional")
	private Integer idTipoPenaConvencional; // ESTATUS

	@Column(name = "monto")
	@Digits(integer = 20, fraction = 2)
	private BigDecimal monto;
}
