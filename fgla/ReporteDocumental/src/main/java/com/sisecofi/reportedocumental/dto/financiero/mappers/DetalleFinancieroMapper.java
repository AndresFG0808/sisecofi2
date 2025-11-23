package com.sisecofi.reportedocumental.dto.financiero.mappers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleFinancieroMapper {
    private String devengadoAntesDeduccionesSat;
    private String pagadoAntesDeduccionesSat;
    private String deduccionesSat;
    private String ivaSat;
    private String iepsSat;
    private String otrosImpuestosSat;
    private String penalizacionesSat;
    private String reintegroSat;
    private String pagadoSat;
    private DesgloceMapper desgloceMapper;
}
