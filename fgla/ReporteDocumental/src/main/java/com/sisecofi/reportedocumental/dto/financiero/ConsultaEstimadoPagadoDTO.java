package com.sisecofi.reportedocumental.dto.financiero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultaEstimadoPagadoDTO extends PaginadorDTO {
    private Integer idContratoVigente;
    private Integer idConvenioColaboracion;

    private String nombreCortoProyecto;
    private String nombreCortoContrato;
}
