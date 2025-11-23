package com.sisecofi.admindevengados.dto.deducciones_descuentos_penalizaciones_dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeduccionesDPModiDto {

    private Integer idTipoDescuento;
    private String nombreDescuento;    
    private Long idDdp;
    private String moneda;
    private BigDecimal monto;
    private String ordenDescuento;




}
