package com.sisecofi.reportedocumental.dto.financiero.mappers;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReporteSeguimientoLineaServicio {
    private DetalleGeneralSeguimientoLineaServicioMapper detalleGeneral;
    private List<PlaneadaEstimadaDTO> planeada;
    private List<PlaneadaEstimadaDTO> estimada;
    private List<DictaminadaPagadaDTO> dictaminada;
    private List<DictaminadaPagadaDTO> pagada;
}
