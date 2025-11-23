package com.sisecofi.reportedocumental.dto.financiero.mappers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReporteFinancieroMapper {
    private String nombreCortoProyecto;
    private String nombreCortoContrato;
    private DetalleGeneralResumenMapper detalleGeneral;
    private DetalleFinancieroMapper detalleFinanciero;
}
