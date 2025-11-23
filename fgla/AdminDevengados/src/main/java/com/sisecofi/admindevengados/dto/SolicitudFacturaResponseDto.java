package com.sisecofi.admindevengados.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudFacturaResponseDto {

    private Long idSolicitudFactura;
    private Long dictamenId;
    private String noOficioSolicitud;
    private LocalDate fechaSolicitud;
    private LocalDate fechaRecepcionFactura;
    private Boolean banderaFactura;
    private String ruta;
}
