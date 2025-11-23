package com.sisecofi.admindevengados.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.contratos.ServicioContratoModel;
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
@Table(name = ConstantesComunes.PREFIX_TRAB + "servicios_dictaminados")
@Getter
@Setter
public class DictaminadoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDictaminado;

	@JoinColumn(name = "idDictamen", insertable = false, updatable = false)
	@ManyToOne(targetEntity = Dictamen.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private Dictamen dictamen;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "idDictamen")
	private Long idDictamen;

	@JoinColumn(name = "idContrato", insertable = false, updatable = false)
	@ManyToOne(targetEntity = ContratoModel.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private ContratoModel contratoModel;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "idContrato")
	private Long idContrato;

	@JoinColumn(name = "idServicioContrato", insertable = false, updatable = false)
	@ManyToOne(targetEntity = ServicioContratoModel.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private ServicioContratoModel servicioContratoModel;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "idServicioContrato")
	private Long idServicioContrato;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "seleccionado")
	private Boolean seleccionado;

	public Boolean getSeleccionado() {
	    return seleccionado != null ? seleccionado : (cantidadServiciosSat != null && cantidadServiciosSat.compareTo(BigDecimal.ZERO) > 0);
	}

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "fechaRegistro")
	private LocalDateTime fechaRegistro;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "cantidadServiciosSat")
	@Digits(integer = 20, fraction = 6)
	private BigDecimal cantidadServiciosSat;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "cantidadServiciosCc")
	@Digits(integer = 20, fraction = 6)
	private BigDecimal cantidadServiciosCc;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "cantidadTotalServicios")
	@Digits(integer = 20, fraction = 6) //<--
	private BigDecimal cantidadTotalServicios;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "montoDictaminado")
	@Digits(integer = 20, fraction = 2)
	private BigDecimal montoDictaminado;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "cantidadServicioDictaminadoAcumulado")
	@Digits(integer = 20, fraction = 2)
	private BigDecimal cantidadServicioDictaminadoAcumulado;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "montonDictaminadoAcumulado")
	@Digits(integer = 20, fraction = 2)
	private BigDecimal montonDictaminadoAcumulado;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "porcentajeServiciosDictaminadosAcumulados")
	@Digits(integer = 5, fraction = 2)
	private BigDecimal porcentajeServiciosDictaminadosAcumulados;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "porcentajeMontoDictaminadosAcumulados")
	@Digits(integer = 5, fraction = 2)
	private BigDecimal porcentajeMontoDictaminadosAcumulados;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "ultima_modificacion")
	private String ultimaModificacion;

	@Column(name = "estatusCM")
	private Boolean estatusCM;

	@JsonIgnore
	public Long getIdGrupoServicio() {
		return servicioContratoModel.getIdGrupoServicio();
	}

	@JsonIgnore
	public BigDecimal getCantidadAsInt() {
		return cantidadTotalServicios != null ? cantidadTotalServicios : BigDecimal.ZERO;
	}
}
