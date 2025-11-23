package com.sisecofi.contratos.service;

import com.sisecofi.contratos.dto.ReintegrosAsociadosDto;
import com.sisecofi.contratos.dto.ReporteReintegroAsociadoDto;
import com.sisecofi.libreria.comunes.model.reintegros.ReintegrosAsociadosModel;



public interface ServicioReintegrosAsociados {

    ReintegrosAsociadosModel guardarReintegrosAsociados(ReintegrosAsociadosModel reintegrosAsociadosModel);

    ReintegrosAsociadosDto obtenerReintegrosAsociados(Long idContrato);

    ReporteReintegroAsociadoDto obtenerReintegrosAsociadosReporte(Long idContrato);
}
