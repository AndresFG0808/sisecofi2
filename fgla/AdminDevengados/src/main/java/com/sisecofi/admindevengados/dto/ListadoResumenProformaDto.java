package com.sisecofi.admindevengados.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ListadoResumenProformaDto {
    private String pena;
    private List<ResumenProformaDto> listResumenProforma;
    private BigDecimal total;

}
