package com.sisecofi.admindevengados.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.contratos.ServicioContratoModel;
import com.sisecofi.libreria.comunes.model.estimacion.EstimacionModel;
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
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;



@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "servicio_estimado")
@Getter
@Setter
public class ServicioEstimadoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idServicioEstimado;

	@JoinColumn(name = "id_estimacion", insertable = false, updatable = false)
	@ManyToOne(targetEntity = EstimacionModel.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private EstimacionModel estimacionModel;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "id_estimacion")
	private Long idEstimacion;
	
	@Transient
	private boolean dictaminado;
	
	@ManyToOne
	@JoinColumn(name = "id_servicio_contrato", foreignKey = @ForeignKey(name = "FK_id_servicio_contrato"))
	private ServicioContratoModel servicioBase;
	
	@Column(name = "monto_estimado")
	private BigDecimal montoEstimado;
	
	@Column(name = "cantidad_servicio_estimados", precision = 44, scale = 6)
	private BigDecimal cantidadServiciosEstimados;
	
	@Column(name = "servicios_acumulados", precision = 44, scale = 6)
	private BigDecimal serviciosAcumulados;
	
	@Column(name = "monto_estimado_acumulado")
	private BigDecimal montoEstimadoAcumulado;
	
	@Column(name = "porcentaje_servicios_estimados_acumulados")
	private BigDecimal porcentajeServiciosEstimadosAcumulados;
	
	@Column(name = "porcentaje_monto_estimado_acumulado")
	private BigDecimal porcentajeMontoEstimadoAcumulado;
	
	@Column(name = "cantidadMaximaServiciosVigente")
	private BigDecimal cantidadMaximaServiciosVigente;
	
	@Column(name = "precioUnitarioActual")
	private BigDecimal precioUnitarioActual;
	
	@Column(name = "montoMaximoVigente")
	private BigDecimal montoMaximoVigente;
	
	@Column(name = "ieps")
	private Boolean ieps;
	
	public String getTipoConsumo() {
		return servicioBase.getGrupoServiciosModel().getCatTipoConsumo().getNombre();
	}
	
	public Long getIdGrupoServicio() {
		return servicioBase.getIdGrupoServicio();
	}
	
	public BigDecimal getCantidadServiciosEstimadosEnt() {
		if (cantidadServiciosEstimados==null || !estimacionModel.getIdEstatusEstimacion().equals(2) || dictaminado)  {
			return BigDecimal.ZERO;
		}else {return cantidadServiciosEstimados;
		}
	}
	
	public BigDecimal getCantidadServiciosEstimadosLimp() {
		if (cantidadServiciosEstimados==null)  {
			return BigDecimal.ZERO;
		}else {return cantidadServiciosEstimados;
		}
	}
	
	
	public BigDecimal getMontoEstimadoEnt() {
		if (montoEstimado==null || !estimacionModel.getIdEstatusEstimacion().equals(2) || dictaminado) {
			return BigDecimal.ZERO;
		}else {
			return montoEstimado;
		}
	}
	
	public BigDecimal getMontoEstimado() {
		if (montoEstimado==null || montoEstimado.compareTo(BigDecimal.ZERO) == 0) {
			return Optional.ofNullable(cantidadServiciosEstimados)
			        .orElse(BigDecimal.ZERO)
			        .multiply(precioUnitarioActual);

		}else {
			return montoEstimado;
		}
	}
	
	public BigDecimal getCantidadServiciosEstimados() {
		
		return cantidadServiciosEstimados;
	}
	

	
	public BigDecimal getCantidadServiciosAcumulados() {
		if (serviciosAcumulados==null) {
			return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
		}else {return serviciosAcumulados.setScale(6, RoundingMode.HALF_UP);
		}
	}
	
	public BigDecimal getServiciosAcumulados() {
		if (serviciosAcumulados==null) {
			return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
		}else {return serviciosAcumulados.setScale(2, RoundingMode.HALF_UP);
		}
	}
	
	public BigDecimal getPorcentajeServiciosEstimadosAcumulados() {
		if (porcentajeServiciosEstimadosAcumulados==null) {
			return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
		}else {return porcentajeServiciosEstimadosAcumulados.setScale(2, RoundingMode.HALF_UP);
		}
	}
	
	public BigDecimal getPorcentajeMontoEstimadoAcumulado() {
		if (porcentajeMontoEstimadoAcumulado==null) {
			return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
		}else {return porcentajeMontoEstimadoAcumulado.setScale(2, RoundingMode.HALF_UP);
		}
	}
	
	public ServicioEstimadoModel(ServicioEstimadoModel servicioEstimado, Long idEstimacionNueva) {
	    this.idServicioEstimado = null;
	    this.idEstimacion = idEstimacionNueva;
	    this.initialize(servicioEstimado);
	}

	private void initialize(ServicioEstimadoModel servicioEstimado) {
	    this.servicioBase = servicioEstimado.getServicioBase();
	    this.montoEstimado = servicioEstimado.getMontoEstimado();
	    this.cantidadServiciosEstimados = servicioEstimado.getCantidadServiciosEstimados();
	    this.serviciosAcumulados = servicioEstimado.getServiciosAcumulados();
	    this.montoEstimadoAcumulado = servicioEstimado.getMontoEstimadoAcumulado();
	    this.porcentajeServiciosEstimadosAcumulados = servicioEstimado.getPorcentajeServiciosEstimadosAcumulados();
	    this.porcentajeMontoEstimadoAcumulado = servicioEstimado.getPorcentajeMontoEstimadoAcumulado();
	    this.cantidadMaximaServiciosVigente = servicioEstimado.getCantidadMaximaServiciosVigente();
	    this.precioUnitarioActual = servicioEstimado.getPrecioUnitarioActual();
	    this.montoMaximoVigente = servicioEstimado.getMontoMaximoVigente();
	}
	
	

	
	public ServicioEstimadoModel() {
		
	}

	@Override
	public String toString() {
		return "|id servicio estimado: " + idServicioEstimado + "| Grupo: "+ servicioBase.getGrupoServiciosModel().getGrupo()  
				+"| Concepto de servicio: "+servicioBase.getConcepto()+ "| Tipo de unidad: "+ servicioBase.getCatTipoUnidad().getNombre()
				+ "| Tipo de consumo: "+ getTipoConsumo()
				+"| Precio unitario: " + precioUnitarioActual+ "| Cantidad de servicios máxima vigente: "
				+ cantidadMaximaServiciosVigente + "| Cantidad de servicios estimados: " + cantidadServiciosEstimados+ "| Monto estimado: " + montoEstimado
				+ "| Cantidad de servicios estimados acumulados: " + serviciosAcumulados + "| Monto estimado acumulado: " + montoEstimadoAcumulado
				+ "| % de servicios estimados acumulados: "+ porcentajeServiciosEstimadosAcumulados
				+ "| % de monto estimado acumulado: " + porcentajeMontoEstimadoAcumulado 
				+ "| Monto maximo vigente: " + montoMaximoVigente + "| Ieps: " + ieps;
	}
	
	
	
}
