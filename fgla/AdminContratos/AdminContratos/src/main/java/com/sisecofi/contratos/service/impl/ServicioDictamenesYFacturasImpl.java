package com.sisecofi.contratos.service.impl;

import com.sisecofi.libreria.comunes.util.consumer.ReporteDictamenesAsociadosConsumer;
import com.sisecofi.libreria.comunes.util.consumer.ReporteFacturasAsociadasConsumer;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.contratos.dto.FacturaContratoResponse;
import com.sisecofi.contratos.microservicios.DevengadosMicroservicio;
import com.sisecofi.contratos.service.ServicioDictamenesYFacturas;
import com.sisecofi.contratos.util.enums.ErroresEnum;
import com.sisecofi.contratos.util.exception.ContratoException;
import com.sisecofi.libreria.comunes.dto.dictamen.DevengadoBusquedaResponse;
import com.sisecofi.libreria.comunes.dto.dictamen.FacturaContratoDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ServicioDictamenesYFacturasImpl implements ServicioDictamenesYFacturas {

    private final DevengadosMicroservicio devengadosMicroservicio;
    private final ReporteDictamenesAsociadosConsumer consumer;
    private final ReporteFacturasAsociadasConsumer consumerF;
    
    
    @Override
	public List<FacturaContratoDto> obtenerFacturasContrato(Long idContrato) {
		try {
		FacturaContratoResponse facturas = devengadosMicroservicio.obtenerFacturasContrato(idContrato);
		return facturas.getData();
		}catch (Exception e) {
            throw new ContratoException(ErroresEnum.ERROR_USUARIO_NO_ENCONTRADO, e);
		}
	}
    
    
	@Override
	public String exportarFacturasAsociadas(Long idContrato) {
		try {
		List<FacturaContratoDto> lista = obtenerFacturasContrato(idContrato);
		consumerF.inializar("Facturas asociados");
		consumerF.agregarCabeceras(Constantes.TITULOS_REPORTE_FACTURAS_ASOCIADAS);
		lista.stream().forEach(consumerF);
		byte[] reporte = consumerF.cerrarBytes();
		return Base64.getEncoder().encodeToString(reporte);
		}catch (Exception e) {
			 throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}
	}
	
	@Override
	public String exportarDictamenesAsociados(Long idContrato) {
		try {
		List<DevengadoBusquedaResponse> lista = devengadosMicroservicio.obtenerDictamenesPorIdContrato(idContrato);
		consumer.inializar("Dictamenes asociadas");
		consumer.agregarCabeceras(Constantes.TITULOS_REPORTE_DICTAMENES_ASOCIADOS);
		lista.stream().forEach(consumer);
		byte[] reporte = consumer.cerrarBytes();
		return Base64.getEncoder().encodeToString(reporte);
		}catch (Exception e) {
			 throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}
	}

   
}