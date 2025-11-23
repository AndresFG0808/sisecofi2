package com.sisecofi.reportedocumental.dto.financiero.mappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DetalleEstatusDictamenMapper {
    private String importeFacturaSinImpuestos;
    private String netoPagarUsd;
    private String netoPagarPesos;
}
