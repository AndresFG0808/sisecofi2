package com.sisecofi.reportedocumental.dto.financiero.mappers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReporteEstimadoPagadoMapper {
    private String nombreCortoProyecto;
    private String nombreCortoContrato;
    private String proveedor;
    private String numeroContrato;
    private String vigente;
    private String periodoControl;
    private String estimado;
    private String dictaminadoSat;
    private String dictaminadoCc;
    private String deduccionSat;
    private String deduccionCc;
    private String notaCreditoSat;
    private String notaCreditoCc;
    private String subtotalSat;
    private String subtotalCc;
    private String ivaSat;
    private String ivaCc;
    private String iepsSat;
    private String iepsCc;
    private String otrosImpuestosSat;
    private String otrosImpuestosCc;
    private String totalPagadoSat;
    private String totalPagadoCc;
}
