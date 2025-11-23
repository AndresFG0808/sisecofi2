package com.sisecofi.admindevengados.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DescuentoDto {
    private Long  idDictamen;
    private Long idDesgloce;
    private BigDecimal monto;
    private String moneda;
    private String nombreDocumento;
}
