package com.sisecofi.admindevengados.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ResumenProformaDto {
    private Integer no;
    private Long idTipo;
    private Integer tipo;
    private String moneda;
    private BigDecimal monto;

}
