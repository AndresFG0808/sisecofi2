package com.sisecofi.admindevengados.util.consumer;

import com.sisecofi.admindevengados.dto.estimacion.EstimacionBusquedaDTO;
import com.sisecofi.libreria.comunes.dto.dictamen.DevengadoBusquedaResponse;

import java.math.BigDecimal;
import java.util.Map;

public class EstimacionMap {
	
    private static final String CERO = "$0.00";
    private static final String FORMATO = "$%,.2f";
    
    private EstimacionMap() {
    }

    public static DevengadoBusquedaResponse apply(EstimacionBusquedaDTO t, Map<String, Object[]> resumenConsolidadoMap) {
        DevengadoBusquedaResponse response = new DevengadoBusquedaResponse();

        response.setEstatus(t.getEstatus());
        response.setId(t.getIdEstimacion());

        response.setMontoEstimado(t.getMontoEstimado() != null ? String.format(FORMATO, t.getMontoEstimado()) : CERO);
        response.setMontoEstimadoPesos(t.getMontoEstimadoPesos() != null ? String.format(FORMATO, t.getMontoEstimadoPesos()) : CERO);

        // 🔥 Obtener la clave correcta
        String claveResumen = t.getIdContrato() + "|" + t.getIdProveedor() + "|" + t.getIdPeriodoControlAnio() + "|" + t.getIdPeriodoControlMes();
        Object[] resumen = resumenConsolidadoMap.get(claveResumen);

        if (resumen != null) {
            response.setMontoDictaminado(String.format(FORMATO, (BigDecimal) resumen[1])); 
            response.setMontoDictaminadoPesos(String.format(FORMATO, (BigDecimal) resumen[2])); 
        } else {
            response.setMontoDictaminado(CERO);
            response.setMontoDictaminadoPesos(CERO);
        }
        response.setIdBd(t.getIdBd());
        response.setPeriodoControl(t.getPeriodoControlMes() + " " + t.getPeriodoControlAnio());
        response.setPeriodoFin(t.getPeriodoFin());
        response.setPeriodoInicio(t.getPeriodoInicio());
        response.setProveedor(t.getNombreProveedor());
        response.setTipo("Estimacion");
        response.setTipoCambioReferencial(t.getTipoCambioReferencial());
        response.setIdProveedor(t.getIdProveedor());

        return response;
    }
}



