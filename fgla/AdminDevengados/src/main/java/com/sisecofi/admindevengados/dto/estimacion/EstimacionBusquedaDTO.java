package com.sisecofi.admindevengados.dto.estimacion;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Getter
@Setter
public class EstimacionBusquedaDTO {
    
    private String idEstimacion;
    private String estatus;
    private Long idContrato;
    private BigDecimal montoEstimado;
    private BigDecimal montoEstimadoPesos;
    private Long idProveedor;
    private Integer idPeriodoControlAnio;
    private Integer idPeriodoControlMes;
    private String periodoControlMes;
    private String periodoControlAnio;
    private String nombreProveedor;
    private LocalDateTime periodoInicio;
    private LocalDateTime periodoFin;
    private BigDecimal tipoCambioReferencial;
    private String nombreContrato;
    private Integer consecutivo;
    private Long idBd;
   
    public EstimacionBusquedaDTO(Object[] row) {
        this.nombreContrato = getString(row[0]);
        this.consecutivo = getInteger (row[1]);
        this.estatus = getString(row[2]);
        this.idContrato = getLong(row[3]);
        this.montoEstimado = getBigDecimal(row[4]);
        this.montoEstimadoPesos = getBigDecimal(row[5]);
        this.idProveedor = getLong(row[6]);
        this.idPeriodoControlAnio = getInteger(row[7]);
        this.idPeriodoControlMes = getInteger(row[8]);
        this.periodoControlMes = getString(row[9]);
        this.periodoControlAnio = getString(row[10]);
        this.nombreProveedor = getString(row[11]);
        this.periodoInicio = getDateTime(row[12]);
        this.periodoFin = getDateTime(row[13]);
        this.tipoCambioReferencial = getBigDecimal(row[14]);
        this.idBd = getLong(row[15]);
    }
    
    
    private String getString(Object value) {
        return value != null ? value.toString() : "";
    }


    private Long getLong(Object value) {
        return value != null ? ((Number) value).longValue() : null;
    }

    private Integer getInteger(Object value) {
        return value != null ? ((Number) value).intValue() : null;
    }

    private LocalDateTime getDateTime(Object value) {
        return value != null ? (LocalDateTime) value : null;
    }

    private BigDecimal getBigDecimal(Object value) {
        return value != null
            ? BigDecimal.valueOf(((Number) value).doubleValue()).setScale(2, RoundingMode.HALF_UP)
            : BigDecimal.ZERO;
    }
    
    public String getIdEstimacion() {
    	this.idEstimacion= nombreContrato + "|" + String.format("%05d", idProveedor) + "|" + String.format("%05d", consecutivo) + "-E";
    	return idEstimacion;
    }
}

