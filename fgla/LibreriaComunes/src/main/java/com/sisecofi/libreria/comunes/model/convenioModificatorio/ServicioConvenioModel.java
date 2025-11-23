package com.sisecofi.libreria.comunes.model.convenioModificatorio;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.contratos.ServicioContratoModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "servicio_convenio")
@Getter
@Setter
public class ServicioConvenioModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idServicioConvenio;

	@JoinColumn(name = "id_convenio_modificatorio", insertable = false, updatable = false)
	@ManyToOne(targetEntity = ConvenioModificatorioModel.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private ConvenioModificatorioModel convenio;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "id_convenio_modificatorio")
	private Long idConvenioModificatorio;

	@ManyToOne
	@JoinColumn(name = "id_servicio_contrato", foreignKey = @ForeignKey(name = "FK_id_servicio_contrato"))
	private ServicioContratoModel servicioBase;

	@Column(name = "numeroMaximoServicios")
	private BigDecimal numeroMaximoServicios;
	
	@Column(name = "numeroTotalServicios")
	private BigDecimal numeroTotalServicios;

	@Column(name = "montoMaximo")
	private BigDecimal montoMaximo;
	
	@Column(name = "montoMaximoTotal")
	private BigDecimal montoMaximoTotal;

	@Digits(integer = 12, fraction = 2)
	@Column(name = "precioUnitario")
	private BigDecimal precioUnitario;

	@Digits(integer = 12, fraction = 6)
	@Column(name = "compensacionServicios")
	private BigDecimal compensacionServicios;

	@Digits(integer = 12, fraction = 2)
	@Column(name = "compensacionMonto")
	private BigDecimal compensacionMonto;
	
	@Digits(integer = 12, fraction = 6)
	@Column(name = "incrementoServicios")
	private BigDecimal incrementoServicios;

	@Digits(integer = 12, fraction = 2)
	@Column(name = "incrementoMonto")
	private BigDecimal incrementoMonto;
	
	@Column(name = "ieps")
	private Boolean ieps;
	
	@Column(name = "primeraVez")
	private Boolean primeraVez;
	
	public BigDecimal getCompensacionServicios() {
		if (compensacionServicios==null) {
			return BigDecimal.ZERO;
		}else {
			return compensacionServicios;
		}
	}
	
	public BigDecimal getIncrementoServicios() {
		if (incrementoServicios==null) {
			return BigDecimal.ZERO;
		}else {
			return incrementoServicios;
		}
	}
	
	public Boolean getPrimeraVez() {
		if(primeraVez==null) {
			return false;
		}else {return primeraVez;}
	}	
	
	public Long getIdContrato() {
		if (servicioBase!=null) {
			return servicioBase.getIdContrato();
		}else {
			return null;
		}
	}
	
	public String getTipoConsumo() {
		if (servicioBase != null) {
			return servicioBase.getGrupoServiciosModel().getCatTipoConsumo().getNombre();
		} else {
			return "";
		}
	}
	
	public String getConcepto() {
		if (servicioBase != null) {
			return servicioBase.getConcepto();
		} else {
			return "";
		}
	}
	
	public BigDecimal getMontoMaximo() {
	    return montoMaximo != null ? montoMaximo.setScale(2, RoundingMode.HALF_UP) : null;
	}

	@Override
	@JsonIgnore
	public String toString() {
	    return "ServicioConvenioModel | idServicioConvenio=" + idServicioConvenio +
	            " | convenio=" + convenio +
	            " | idConvenioModificatorio=" + idConvenioModificatorio +
	            " | servicioBase=" + servicioBase +
	            " | numeroMaximoServicios=" + numeroMaximoServicios +
	            " | numeroTotalServicios=" + numeroTotalServicios +
	            " | montoMaximo=" + montoMaximo +
	            " | montoMaximoTotal=" + montoMaximoTotal +
	            " | precioUnitario=" + precioUnitario +
	            " | compensacionServicios=" + compensacionServicios +
	            " | compensacionMonto=" + compensacionMonto +
	            " | incrementoServicios=" + incrementoServicios +
	            " | incrementoMonto=" + incrementoMonto +
	            " | ieps=" + ieps +
	            " | primeraVez=" + primeraVez +
	            " | getIdContrato()=" + getIdContrato() +
	            " | getTipoConsumo()=" + getTipoConsumo() +
	            " | getConcepto()=" + getConcepto() +
	            " | getIdServicioConvenio()=" + getIdServicioConvenio() +
	            " | getConvenio()=" + getConvenio() +
	            " | getIdConvenioModificatorio()=" + getIdConvenioModificatorio() +
	            " | getServicioBase()=" + getServicioBase() +
	            " | getNumeroMaximoServicios()=" + getNumeroMaximoServicios() +
	            " | getNumeroTotalServicios()=" + getNumeroTotalServicios() +
	            " | getMontoMaximo()=" + getMontoMaximo() +
	            " | getMontoMaximoTotal()=" + getMontoMaximoTotal() +
	            " | getPrecioUnitario()=" + getPrecioUnitario() +
	            " | getCompensacionServicios()=" + getCompensacionServicios() +
	            " | getCompensacionMonto()=" + getCompensacionMonto() +
	            " | getIncrementoServicios()=" + getIncrementoServicios() +
	            " | getIncrementoMonto()=" + getIncrementoMonto() +
	            " | getIeps()=" + getIeps() +
	            " | getPrimeraVez()=" + getPrimeraVez();
	}
	
	

}
