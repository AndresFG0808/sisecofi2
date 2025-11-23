package com.sisecofi.proyectos.dto;

import jakarta.validation.constraints.Digits;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class InformacionComiteDto {

    private Integer idComiteProyecto;

    private Integer idContratoConvenio;
    
    private Integer idContrato;

    private String contratoConvenio;

    private LocalDateTime fechaSesion;

    private Integer idSesionNumero;

    private Integer idSesionClasificacion;

    private String acuerdo;

    private String vigencia;

    @Digits(integer = 12, fraction = 2, message = "El monto debe tener hasta 12 dígitos enteros y 2 decimales")
    private BigDecimal montoAutorizado;

    @Digits(integer = 24, fraction = 2, message = "El monto debe tener hasta 24 dígitos enteros y 2 decimales")
    private BigDecimal monto;

    @Digits(integer = 12, fraction = 4, message = "El tipo de cambio debe tener hasta 12 dígitos enteros y 4 decimales")
    private BigDecimal tipoCambio;

    private Integer idComite;

    private Boolean estatus;

    private Boolean ultimoValor;

    private List<Integer> idsAfectacion;

    private String comentarios;

    private Integer idTipoMoneda;

    private Integer idPlantilla;
    
    private Long idProyecto;
    
    private String nombreCortoProyecto;
    
    public String getMontoString() {
        return monto != null ? monto.toPlainString() : "0.00";
    }
}
