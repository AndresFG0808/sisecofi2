package com.sisecofi.reportedocumental.dto.financiero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultaResumenDTO extends PaginadorDTO {
    private Integer idEstatusProyecto;
    private Integer idDominiosTecnologicos;
    private Integer idContratoVigente; // 1 (Sí), 2 (No), 3 (Todos)
    private Integer idConvenioColaboracion;  // 1 (Con desgloce), 2 (Sin desgloce)

    private String nombreCortoProyecto;
    private String nombreCortoContrato;

    private boolean incluirDatosGenerales;
    private boolean incluirDetalleFinanciero;
    private boolean incluirDetalleCM;
}
