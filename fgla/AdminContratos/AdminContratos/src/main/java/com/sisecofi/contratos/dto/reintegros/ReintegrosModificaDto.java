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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReintegrosModificaDto {

   
    private Integer idTipoReintegro;
    private String nombreTipoReintegro;
    private Long idReintegrosAsociados;
    private BigDecimal importe;
    private BigDecimal interes;
    private BigDecimal total;
    private LocalDateTime fechaReintegro;

}
