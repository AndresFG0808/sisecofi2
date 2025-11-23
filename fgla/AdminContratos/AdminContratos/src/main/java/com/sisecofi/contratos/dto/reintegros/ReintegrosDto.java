package com.sisecofi.contratos.dto.reintegros;

import java.math.BigDecimal;
import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ReintegrosDto {

    private Long idContrato;
    private Long idReintegrosAsociados;
    private String nombreTipoReintegro;
    private Integer idTipoReintegro;
    private BigDecimal importe;
    private BigDecimal interes;
    private BigDecimal total;
    private LocalDateTime fechaReintegro;
    private Integer ordenContrato;






}
