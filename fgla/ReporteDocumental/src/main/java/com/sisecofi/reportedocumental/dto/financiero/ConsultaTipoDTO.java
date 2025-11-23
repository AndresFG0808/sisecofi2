package com.sisecofi.reportedocumental.dto.financiero;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConsultaTipoDTO extends PaginadorDTO {
    private Integer idContratoVigente;
    private Integer idConvenioColaboracion;

    private String nombreCortoProyecto;
    private String nombreCortoContrato;

    private List<String> tipos;
}
