package com.sisecofi.contratos.dto;

import com.sisecofi.contratos.dto.reintegros.ReintegrosDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ReintegrosAsociadosDto {
    private List<ReintegrosDto> reintegrosAsociados;
    private BigDecimal totales;
    private BigDecimal intereses;
    private BigDecimal importes;
}
