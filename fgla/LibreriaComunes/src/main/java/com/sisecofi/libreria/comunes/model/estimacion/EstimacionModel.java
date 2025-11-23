package com.sisecofi.libreria.comunes.model.estimacion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoLigeroDto;
import com.sisecofi.libreria.comunes.dto.proveedor.ProveedorLigeroDto;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusEstimacion;
import com.sisecofi.libreria.comunes.model.catalogo.CatIva;
import com.sisecofi.libreria.comunes.model.catalogo.CatPeriodoControlAnio;
import com.sisecofi.libreria.comunes.model.catalogo.CatPeriodoControlMes;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
import com.sisecofi.libreria.comunes.util.consumer.MonthMapper;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "estimacion")
@Getter
@Setter
public class EstimacionModel {
  
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEstimacion;
	
    @Column(name = "consecutivo")
    private Integer consecutivo;

    @Column(name = "tipo_cambio_referencial", precision = 25, scale = 4) 
    private BigDecimal tipoCambioReferencial;

    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    @Column(name = "periodo_inicio")
    private LocalDateTime periodoInicio;

    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    @Column(name = "periodo_fin")
    private LocalDateTime periodoFin;

    @Column(name = "monto_estimado")
    private BigDecimal montoEstimado; 

    @Column(name = "monto_estimado_pesos")
    private BigDecimal montoEstimadoPesos;

    @Column(name = "estatus")
    private Boolean estatus;
    
    @JoinColumn(name = "idProveedor", insertable = false, updatable = false)
    @ManyToOne(targetEntity = ProveedorLigeroDto.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private ProveedorLigeroDto proveedorModel;

    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    @Column(name = "idProveedor")
    private Long idProveedor;
    
    public String getNombreProveedor() {
    	return proveedorModel !=null ? proveedorModel.getNombreProveedor() : null;
    }
    
    @JoinColumn(name = "id_estatus_estimacion", insertable = false, updatable = false)
    @ManyToOne(targetEntity = CatEstatusEstimacion.class, fetch = FetchType.LAZY)
    private CatEstatusEstimacion catEstatusEstimacion;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "id_estatus_estimacion")
	private Integer idEstatusEstimacion; // ESTATUS
    
    @JoinColumn(name = "idPeriodoControlMes", insertable = false, updatable = false)
    @ManyToOne(targetEntity = CatPeriodoControlMes.class, fetch = FetchType.LAZY)
    private CatPeriodoControlMes catPeriodoControlMes;

    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    @Column(name = "idPeriodoControlMes")
    private Integer idPeriodoControlMes;
    
    @JoinColumn(name = "idPeriodoControlAnio", insertable = false, updatable = false)
    @ManyToOne(targetEntity = CatPeriodoControlAnio.class, fetch = FetchType.LAZY)
    private CatPeriodoControlAnio catPeriodoControlAnio;
    
    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    @Column(name = "idPeriodoControlAnio")
    private Integer idPeriodoControlAnio;
    
    @JoinColumn(name = "idIva", insertable = false, updatable = false)
    @ManyToOne(targetEntity = CatIva.class, fetch = FetchType.LAZY)
    private CatIva catIva;

    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    @Column(name = "idIva")
    private Integer idIva;

    @Column(name = "ultima_moficicacion")
    private String ultimaModificacion;
    
    public String getNumeroContrato() {
    	return numeroContrato;
    }
    
    @Column(name = "justificacion")
    private String justificacion;
    
    @Transient
    private String idReferencia;
    
    @Transient
    private String numeroContrato;
    
    @Transient
    private boolean duplicado;
    
    @Transient
    private String anterior;
    
    
    @ManyToOne
	@JoinColumn(name = "id_convenio_modificatorio", foreignKey = @ForeignKey(name = "FK_id_convenio_modificatorio"), nullable = true)
	@JsonIgnore
	private ConvenioModificatorioModel convenio;
    

    @JoinColumn(name = "id_contrato", insertable = false, updatable = false)
	@ManyToOne(targetEntity = ContratoLigeroDto.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private ContratoLigeroDto contratoModel;
    
    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    @Column(name = "id_contrato")
    private Long idContrato;

    @JsonIgnore
    public LocalDate getPeriodoFecha() {
    	if (catPeriodoControlMes==null || catPeriodoControlAnio==null) {
    		return null;
    	}else {
    		Month mesEnum = MonthMapper.getMonth(this.catPeriodoControlMes.getNombre());  
            int anioInt = Integer.parseInt(this.catPeriodoControlAnio.getNombre());  
            return LocalDate.of(anioInt, mesEnum, 1);
    	}
    }
    
    public String getIdEstimacionVisual() {
    	if (contratoModel!=null) {
    		return contratoModel.getNombreCorto()+"|"+  String.format("%05d", idProveedor) + "|" + String.format("%05d", consecutivo) + "-E";
    	} return ""+idEstimacion;
    }
    
    public BigDecimal getTipoCambioReferencialEnt() {
    	if (tipoCambioReferencial==null) {
    		return BigDecimal.ONE;
    	}else {
    		return tipoCambioReferencial;
    	}
    }
    
    @Column(name = "fechaCreacion")
	private LocalDateTime fechaCreacion;
}