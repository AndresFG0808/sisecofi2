package com.sisecofi.admindevengados.dto.solicitudpago;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class InfoReferenciaPagoDto {

    private Long idReferenciaPago;

    @NotNull
    private Long idFactura;

    @NotNull
    private Integer tipoNotificacionPago;

    @NotNull
    private String oficioNotificacionPago;

    @NotNull
    private LocalDateTime fechaNotificacion;

    private String folioFichaPago;

    @NotNull
    private LocalDateTime fechaPago;

    private BigDecimal tipoCambioPagado;

    @NotNull
    private BigDecimal pagadoNAFIN;

    private String archivoFicha;

    private String nombreArchivo;

    @NotNull
    private Integer idDesglose;

    private Boolean convenioColaboracion;
}
