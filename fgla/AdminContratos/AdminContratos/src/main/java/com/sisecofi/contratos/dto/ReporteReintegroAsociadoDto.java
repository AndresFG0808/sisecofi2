package com.sisecofi.contratos.dto;

import com.sisecofi.libreria.comunes.model.reintegros.ReintegrosAsociadosModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ReporteReintegroAsociadoDto {
    private List<BigDecimal> listaTotales;
    private List<ReintegrosAsociadosModel> reintegrosAsociados;
}
