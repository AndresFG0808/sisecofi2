package com.sisecofi.reportedocumental.dto.financiero;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ConsultaSeguimientoLineaServicioDTO extends PaginadorDTO {
    private Integer idContratoVigente; // 1 (Sí), 2 (No), 3 (Todos)
    private Integer idConvenioColaboracion;  // 1 (Con desgloce), 2 (Sin desgloce)
    private List<Integer> listaIdEstatusVolumetria;

    private List<Integer> listaIdServicioContrato;
    private String nombreCortoProyecto;
    private String nombreCortoContrato;

    private boolean volumetria;
    private boolean monto;

    private LocalDate periodoInicio;
    private LocalDate periodoFin;
}
