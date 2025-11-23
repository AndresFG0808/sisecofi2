package com.sisecofi.libreria.comunes.model.contratos;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoUnidad;
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
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;



@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "servicio_Contrato")
@Getter
@Setter
public class ServicioContratoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idServicioContrato;

	@JoinColumn(name = "id_contrato", insertable = false, updatable = false)
	@ManyToOne(targetEntity = ContratoModel.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private ContratoModel contratoModel;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "id_contrato")
	private Long idContrato;
	
	@JoinColumn(name = "id_grupo_servicio", insertable = false, updatable = false)
	@ManyToOne(targetEntity = GrupoServiciosModel.class, fetch = FetchType.EAGER)
	private GrupoServiciosModel grupoServiciosModel;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "id_grupo_servicio")
	private Long idGrupoServicio;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "concepto")
    private String concepto;
	
	@Column(name = "clave")
    private String clave;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "cantidadMaxima")
    private BigDecimal cantidadMaxima;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "cantidadMinima")
    private BigDecimal cantidadMinima;

	@JoinColumn(name = "id_tipo_unidad", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatTipoUnidad.class, fetch = FetchType.EAGER)
	private CatTipoUnidad catTipoUnidad;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "id_tipo_unidad")
	private Integer idTipoUnidad;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "precioUnitario")
	private BigDecimal precioUnitario;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "montoMinimo")
	private BigDecimal montoMinimo;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "montoMaximo")
	private BigDecimal montoMaximo;

	@Column(name = "ieps")
	private Boolean ieps;

	@Column(name = "estatus")
	private Boolean estatus;
	
	@Column(name = "orden")
	private Integer orden;
	
	public Integer getOrden() {
		if (orden==null) {
			return Integer.parseInt(""+idServicioContrato);
		}
		return orden;
	}

}
