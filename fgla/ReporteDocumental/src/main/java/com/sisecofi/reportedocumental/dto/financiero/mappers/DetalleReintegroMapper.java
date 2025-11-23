package com.sisecofi.reportedocumental.dto.financiero.mappers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleReintegroMapper {
    private String tipo;
    private String importe;
    private String intereses;
    private String totalSat;
    private String totalCc;
    private String fechaReintegro;
}
