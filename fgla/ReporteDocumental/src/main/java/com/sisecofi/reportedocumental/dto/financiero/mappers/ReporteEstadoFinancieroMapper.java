package com.sisecofi.reportedocumental.dto.financiero.mappers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReporteEstadoFinancieroMapper {
    private String nombreCortoProyecto;
    private String nombreCortoContrato;
    private String dominio;
    private String proveedor;
    private String vigente;
    private String numeroContrato;
    private String periodoControl;
    private String moneda;
    private String tipoCambioReferencialEstimado;
    private String montoDolaresEstimado;
    private String montoPesosEstimado;
    private String tipoCambioReferencialDictaminado;
    private String montoDolaresDictaminado;
    private String montoPesosDictaminado;
    private String tipoCambioReal;
    private String montoDolares;
    private String montoPesos;
    private String dictamenSat;
    private String dictamenCc;
    private String pagadoSat;
    private String pagadoCc;
}
