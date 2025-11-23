package com.sisecofi.libreria.comunes.model.dictamenes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusDictamen;
import com.sisecofi.libreria.comunes.model.catalogo.CatIva;
import com.sisecofi.libreria.comunes.model.catalogo.CatPeriodoControlAnio;
import com.sisecofi.libreria.comunes.model.catalogo.CatPeriodoControlMes;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
import com.sisecofi.libreria.comunes.model.dictamenes.factura.FacturaModel;
import com.sisecofi.libreria.comunes.model.dictamenes.factura.NotaCreditoModel;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.consumer.MonthMapper;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "dictamen")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dictamen {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDictamen;

	@JoinColumn(name = "id_contrato", insertable = false, updatable = false)
	@ManyToOne(targetEntity = ContratoModel.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private ContratoModel contratoModel;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "id_contrato")
	private Long idContrato;

	@JoinColumn(name = "id_proveedor", insertable = false, updatable = false)
	@ManyToOne(targetEntity = ProveedorModel.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private ProveedorModel proveedorModel;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "id_proveedor")
	private Long idProveedor;

	@JoinColumn(name = "idEstatusDictamen", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatEstatusDictamen.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatEstatusDictamen catEstatusDictamen;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "idEstatusDictamen")
	private Integer idEstatusDictamen; // ESTATUS
	
	@JoinColumn(name = "id_convenio_modificatorio", insertable = false, updatable = false)
	@ManyToOne(targetEntity = ConvenioModificatorioModel.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private ConvenioModificatorioModel convenio;

	@Column(name = "id_convenio_modificatorio")
	private Long idConvenioCodificatorio; // ESTATUS

	@Column(name = "estatus", length = 15)
	private Boolean estatus;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "periodoInicio")
	private LocalDateTime periodoInicio;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "periodoFin")
	private LocalDateTime periodoFin;

	@JoinColumn(name = "idPeriodoControlMes", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatPeriodoControlMes.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatPeriodoControlMes catPeriodoControlMes;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "idPeriodoControlMes")
	private Integer idPeriodoControlMes;

	@JoinColumn(name = "idPeriodoControlAnio", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatPeriodoControlAnio.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatPeriodoControlAnio catPeriodoControlAnio;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "idPeriodoControlAnio")
	private Integer idPeriodoControlAnio;

	@JoinColumn(name = "idIva", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatIva.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatIva catIva;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "idIva")
	private Long idIva;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "tipoCambioReferencial")
	@Digits(integer = 2, fraction = 4)
	private BigDecimal tipoCambioReferencial;

	@Column(name = "descripcion", length = 1250)
	private String descripcion;

	@Column(name = "ultimaModificacion")
	private String ultimaModificacion;

	@OneToMany(mappedBy = "dictamen", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<FacturaModel> facturaModel;

	@OneToMany(mappedBy = "dictamen", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<NotaCreditoModel> notaCreditoModel;


	@Column(name = "plantillaAsignada")
	private Boolean plantillaAsignada;

	@Column(name = "fechaCreacion")
	private LocalDateTime fechaCreacion;

	@JsonIgnore
	public LocalDateTime getPeriodoControl() {
		int mes = Integer.parseInt("" + idPeriodoControlMes);
		int anio = Integer.parseInt(catPeriodoControlAnio.getNombre());
		return LocalDateTime.of(anio, mes, 1, 0, 0).with(TemporalAdjusters.lastDayOfMonth());

	}

	@JsonIgnore
	public LocalDate getPeriodoFecha() {
		Month mesEnum = MonthMapper.getMonth(this.catPeriodoControlMes.getNombre());
		int anioInt = Integer.parseInt(this.catPeriodoControlAnio.getNombre());
		return LocalDate.of(anioInt, mesEnum, 1);
	}
	
	@Column(name = "checkContractual")
	private Boolean checkContractual;
	
	@Column(name = "checkConvencional")
	private Boolean checkConvencional;
	
	@Column(name = "checkDeducciones")
	private Boolean checkDeducciones;
	
	@Column(name = "consecutivo")
    private Integer consecutivo;
	
	public Boolean getCheckContractual() {
		if(checkContractual == null) {
			return true;
		}		
		return checkContractual;
	}
	
	public Boolean getCheckConvencional() {
		if(checkConvencional == null) {
			return true;
		}		
		return checkConvencional;
	}
	
	public Boolean getCheckDeducciones() {
		if(checkDeducciones == null) {
			return true;
		}		
		return checkDeducciones;
	}
	
	@Transient
	String idDictamenVisual;
	
	public String getIdDictamenVisual() {
    	if (contratoModel!=null) {
    		return contratoModel.getNombreCorto()+"|"+  String.format("%05d", idProveedor) + "|" + String.format("%05d", consecutivo);
    	} return ""+idDictamenVisual;
    }
	
}