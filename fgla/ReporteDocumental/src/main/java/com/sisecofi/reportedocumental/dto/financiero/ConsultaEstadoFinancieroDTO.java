package com.sisecofi.reportedocumental.dto.financiero;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ConsultaEstadoFinancieroDTO extends PaginadorDTO {
    private Integer idEstatusProyecto;
    private Integer idContratoVigente; // 1 (Sí), 2 (No), 3 (Todos)
    private Integer idDominiosTecnologicos;
    private Integer idConvenioColaboracion;  // 1 (Con desgloce), 2 (Sin desgloce)

    private String nombreCortoProyecto;
    private String nombreCortoContrato;

    private Date periodoInicio;
    private Date periodoFin;
}
