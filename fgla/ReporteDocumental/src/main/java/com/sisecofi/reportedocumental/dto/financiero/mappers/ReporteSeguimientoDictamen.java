package com.sisecofi.reportedocumental.dto.financiero.mappers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReporteSeguimientoDictamen {
    private String nombreCortoProyecto;
    private String nombreCortoContrato;
    private String verificador;
    private String periodoControl;
    private DetalleEstatusDictamenMapper inicial;
    private DetalleEstatusDictamenMapper dictaminado;
    private DetalleEstatusDictamenMapper proforma;
    private DetalleEstatusDictamenMapper facturado;
    private DetalleEstatusDictamenMapper solicitudPago;
    private DetalleEstatusDictamenMapper pagado;
    private DetalleEstatusDictamenMapper cancelado;
}
