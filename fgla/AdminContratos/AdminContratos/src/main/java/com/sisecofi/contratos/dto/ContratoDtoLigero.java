package com.sisecofi.contratos.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusContrato;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContratoDtoLigero {
    private Long idContrato;
    private String nombreContrato;
    private String nombreCorto;
    private String numeroContrato;
    private LocalDateTime fechaUltimaModificacion;
    private Boolean estatus;
    private Integer idEstatusContrato;
    private String estatusContrato;
    private String ultimoModificador;
    private String actaCierreRuta;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaTermino;
    private BigDecimal montoMaximo;
    private BigDecimal montoMaximoCm;
    private BigDecimal montoPesos;
    private boolean tieneIeps;
    private String administradorContrato;
    private String administracionCentral;
    private String nombreProyecto;
    private CatEstatusContrato catEstatusContrato;
    private String proveedores;
    private String tipoProcedimiento;
    private LocalDateTime fechaUltimoCm;

    public ContratoDtoLigero(Object[] row) {
        this.idContrato = getLong(row[0]);
        this.nombreContrato = getString(row[1]);
        this.nombreCorto = getString(row[2]);
        this.numeroContrato = getString(row[3]);
        this.fechaUltimaModificacion = getDateTime(row[4]);
        this.estatus = getBoolean(row[5]);
        this.idEstatusContrato = getInteger(row[6]);
        this.estatusContrato = getString(row[7]);
        this.ultimoModificador = getString(row[8]);
        this.actaCierreRuta = getString(row[9]);
        this.fechaInicio = getDateTime(row[10]);
        this.fechaTermino = getDateTime(row[11]);
        this.montoMaximo = getBigDecimal(row[12]);
        this.montoMaximoCm = getBigDecimal(row[13]);
        this.montoPesos = getBigDecimal(row[14]);
        this.tieneIeps = getBoolean(row[15]);
        this.administradorContrato = getString(row[16]);
        this.administracionCentral = getString(row[17]);
        this.nombreProyecto = getString(row[18]);

        this.catEstatusContrato = new CatEstatusContrato();
        catEstatusContrato.setNombre(estatusContrato);
        catEstatusContrato.setEstatus(true);
        catEstatusContrato.setIdEstatusContrato(idEstatusContrato);

        this.proveedores = getString(row[19]);
        this.tipoProcedimiento = getString(row[20]);
        this.fechaUltimoCm = getDateTime(row[21]);
    }
    
    private String getString(Object value) {
        return value != null ? value.toString() : "";
    }

    private Boolean getBoolean(Object value) {
        return Boolean.TRUE.equals(value);
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

}
