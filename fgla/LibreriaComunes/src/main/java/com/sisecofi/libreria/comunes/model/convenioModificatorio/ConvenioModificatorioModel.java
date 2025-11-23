package com.sisecofi.libreria.comunes.model.convenioModificatorio;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.CatIva;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.consumer.StringListConverter;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
import com.sisecofi.libreria.comunes.util.interfaces.ValidacionCompleta;
import com.sisecofi.libreria.comunes.util.interfaces.validaciones.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "convenio_modificatorio")
@Getter
@Setter
public class ConvenioModificatorioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConvenioModificatorio;

    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups = ValidacionCompleta.class)
    @Column(name = "numero_convenio", unique = true)
    private String numeroConvenio;

    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups = ValidacionCompleta.class)
    @Column(name = "fecha_firma")
    private LocalDateTime fechaFirma;
    
    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups = ValidacionMonto.class)
    @Column(name = "incremento")
    private BigDecimal incremento;
    
    @Column(name = "tipo_cambio", precision = 25, scale = 4)
    private BigDecimal tipoCambio;
    
    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups = ValidacionTiempo.class)
    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;
    
    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups = ValidacionTiempo.class)
    @Column(name = "fecha_fin_servicio")
    private LocalDateTime fechaFinServicio;
    
    @Column(name = "calculo_dias")
    private Long calculoDias;
    
    @Column(name = "subtotal")
    private BigDecimal subtotal;

    @Column(name = "estatus")
    private Boolean estatus;
    
    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups = ValidacionCompleta.class)
    @Column(name = "ieps")
    private BigDecimal ieps;
    
    @JoinColumn(name = "id_iva", insertable = false, updatable = false)
    @ManyToOne(targetEntity = CatIva.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private CatIva catIva;

    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups = ValidacionMonto.class)
    @Column(name = "id_iva")
    private Integer idIva;
    
    @Column(name = "impuesto")
    private BigDecimal impuesto;
    
    @Column(name = "monto_maximo_sin_impuestos")
    private BigDecimal montoMaximoSinImpuestos;
    
    @Column(name = "monto_maximo_con_impuestos")
    private BigDecimal montoMaximoConImpuestos;
    
    @Column(name = "monto_pesos")
    private BigDecimal montoPesos;
    
    @Column(name = "comentarios")
    private String comentarios;
    
    @Column(name = "tipo_convenio")
    @NotEmpty(message = ErroresEnum.MensajeValidation.MENSAJE, groups = ValidacionCompleta.class)
    @Convert(converter = StringListConverter.class)  
    private List<String> tipoConvenio;
    
    @JoinColumn(name = "id_contrato", insertable = false, updatable = false)
	@ManyToOne(targetEntity = ContratoModel.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private ContratoModel contratoModel;

	@Column(name = "id_contrato")
	private Long idContrato;
	
	@Column(name = "ultima_modificacion")
	private String ultimaModificacion;
	
	public String getUltimaModificacion() {
		if (ultimaModificacion==null) {
			return "";
		}else {
			return ultimaModificacion;
		}
	}
	
	@JsonIgnore
	public BigDecimal getPorcentajeIva() {
		BigDecimal porcentaje = BigDecimal.ZERO;
		if (catIva!=null) {
			porcentaje = new BigDecimal(catIva.getPorcentaje()).divide(BigDecimal.valueOf(100));
		}
		return porcentaje;
	}
	
	@JsonIgnore
	@OneToMany(mappedBy = "convenio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ServicioConvenioModel> serviciosConvenio;
	
	public List<ServicioConvenioModel> getServiciosConvenio() {
	    return serviciosConvenio;
	}
	
	public boolean getTieneIeps() {
	    if (serviciosConvenio == null || serviciosConvenio.isEmpty()) {
	        return false;
	    }
	    return serviciosConvenio.stream().anyMatch(servicio -> Boolean.TRUE.equals(servicio.getIeps()));
	}
	
	public String getTipoConvenioFormateado() {
	    if (tipoConvenio == null || tipoConvenio.isEmpty()) {
	        return "";
	    }
	    return tipoConvenio.stream()
	            .filter(tipo -> tipo != null && !tipo.isBlank()) 
	            .map(tipo -> tipo.substring(0, 1).toUpperCase() + tipo.substring(1).toLowerCase())
	            .collect(Collectors.joining(", ")); 
	}
	
	private String formatoFecha(LocalDateTime fecha) {
	    if (fecha == null) {
	        return "";
	    }
	    
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	    return fecha.format(formatter);
	}
	
	private BigDecimal formatoNumero(BigDecimal tipoCambio) {
		if (tipoCambio==null) {
			return BigDecimal.ZERO;
		} return tipoCambio;
	}
	
	private Long formatoNumero(Long tipoCambio) {
		if (tipoCambio==null) {
			return 0L;
		} return tipoCambio;
	}
	
	

	@Override
	public String toString() {
		return "| idConvenioModificatorio: " + idConvenioModificatorio + "| Numero de convenio: "
				+ numeroConvenio + "| fechaFirma: " + formatoFecha(fechaFirma) + "| incremento: " + incremento + "| Tipo de cambio: "
				+ formatoNumero(tipoCambio) + "| Fecha fin: " + formatoFecha(fechaFin) + "| Fecha fin de servicios: " + formatoFecha(fechaFinServicio) + "| Cálculo de días naturales: "
				+ formatoNumero(calculoDias) + "| Subtotal: " + subtotal + "| ieps: " + ieps + "| idIva: " + idIva + "| impuesto: "
				+ impuesto + "| Monto maximo sin impuestos: " + montoMaximoSinImpuestos + "| Monto maximo con impuestos: "
				+ montoMaximoConImpuestos + "| Monto en pesos: " + montoPesos + "| Comentarios: " + comentarios
				+ "| tipoConvenio: " + getTipoConvenioFormateado() + "| IdContrato: " + idContrato + "| Ultima modificación: "
				+ ultimaModificacion ;
	}
	
	
	
}
