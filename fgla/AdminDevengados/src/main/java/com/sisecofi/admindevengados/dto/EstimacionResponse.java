package com.sisecofi.admindevengados.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusEstimacion;
import com.sisecofi.libreria.comunes.model.catalogo.CatIva;
import com.sisecofi.libreria.comunes.model.catalogo.CatPeriodoControlAnio;
import com.sisecofi.libreria.comunes.model.catalogo.CatPeriodoControlMes;
import com.sisecofi.libreria.comunes.model.estimacion.EstimacionModel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@EqualsAndHashCode( callSuper = false)
public class EstimacionResponse {

	    private String idEstimacion;
	    
	    private Long idEstimacionBd;

	    private BigDecimal tipoCambioReferencial;

	    private LocalDateTime periodoInicio;

	    private LocalDateTime periodoFin;

	    private BigDecimal montoEstimado; 

	    private BigDecimal montoEstimadoPesos;
	    
	    private Boolean estatus = true;

	    private Long idProveedor;
	    
	    private Long idContrato;
	    
	    private String nombreProveedor;
	    
	    private CatEstatusEstimacion catEstatusEstimacion;

		private Integer idEstatusEstimacion;
	    
	    private String nombreCortoContrato;

	    private CatPeriodoControlMes catPeriodoControlMes;
	    
	    private Integer idPeriodoControlMes;
	 
	    private CatPeriodoControlAnio catPeriodoControlAnio;
	
	    private Integer idPeriodoControlAnio;
	    
	    private CatIva catIva;

	    private Integer idIva;

	    private String ultimaModificacion;
	    
	    private String numeroContrato;
	    
	    private String justificacion;
	    
	    private LocalDateTime fechaInicio;

	    private LocalDateTime fechaTermino;
	    
	    private boolean duplicado;
	    
	    private String anterior;
	    
	    private Integer siguienteEstimacion;
	    
	    private Integer anteriorEstimacion;
	    
	    private Integer consecutivo;
	    
	    @JsonIgnore
	    private EstimacionModel estimacionModel;

		public EstimacionResponse(EstimacionModel estimacionModel) {
			super();
			this.estimacionModel = estimacionModel;
			this.tipoCambioReferencial = estimacionModel.getTipoCambioReferencial();
			this.periodoInicio = estimacionModel.getPeriodoInicio();
			this.periodoFin= estimacionModel.getPeriodoFin();
			this.idProveedor= estimacionModel.getIdProveedor();
			this.idEstatusEstimacion= estimacionModel.getIdEstatusEstimacion();
			this.idPeriodoControlMes =estimacionModel.getIdPeriodoControlMes();
			this.idPeriodoControlAnio= estimacionModel.getIdPeriodoControlAnio();
			this.idIva=estimacionModel.getIdIva();
			this.ultimaModificacion =estimacionModel.getUltimaModificacion();
			this.idContrato= estimacionModel.getIdContrato();
			this.justificacion= estimacionModel.getJustificacion();
			this.montoEstimado= estimacionModel.getMontoEstimado();
			this.montoEstimadoPesos= estimacionModel.getMontoEstimadoPesos();
			this.idEstimacionBd = estimacionModel.getIdEstimacion();
			this.consecutivo= estimacionModel.getConsecutivo();
		}
		
		public String getIdEstimacion() {
			return nombreCortoContrato + "|" + String.format("%05d", idProveedor) + "|" + String.format("%05d", consecutivo) + "-E";
		}
		
		private String formatoFecha(LocalDateTime fecha) {
		    if (fecha == null) {
		        return "";
		    }
		    
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		    return fecha.format(formatter);
		}
		
		private String obtenerJustificacion() {
			if (justificacion != null && !justificacion.isEmpty()) {
				return "| Justificacion: " + justificacion;
			} return "";
		}

		@Override
		public String toString() {
			return "idEstimacion: " + idEstimacion + "| Nombre corto del contrato: " + nombreCortoContrato 
					+ "| Número de contrato: " + numeroContrato + "| Proveedor: " + nombreProveedor 
					+ "| Estatus: " + catEstatusEstimacion.getNombre()
					+ "| Periodo de inicio: " + formatoFecha(periodoInicio) + "| Periodo fin: " + formatoFecha(periodoFin)
					+ "| Periodo de control mes: " + catPeriodoControlMes.getNombre() + "| Periodo de control Año: " + catPeriodoControlAnio.getNombre()
					+ "| Iva: " + catIva.getPorcentaje() + "| Tipo de cambio referencial: "+ tipoCambioReferencial 
					+ "| Monto estimado total : " + montoEstimado + "| Monto estimado total en pesos: " + montoEstimadoPesos
					 + "| ultimaModificacion: " + ultimaModificacion  + obtenerJustificacion();
		}
	    
	
}
