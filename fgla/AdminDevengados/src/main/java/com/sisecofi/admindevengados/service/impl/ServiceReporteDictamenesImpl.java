package com.sisecofi.admindevengados.service.impl;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sisecofi.admindevengados.service.ServiceReporteDictamenes;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.admindevengados.util.consumer.ReporteDictamenesConsumer;
import com.sisecofi.admindevengados.util.enums.ErroresEnum;
import com.sisecofi.admindevengados.util.exception.CatalogoException;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.admindevengados.dto.ResumenConsolidadoDto;
import com.sisecofi.admindevengados.service.DictamenService;
import com.sisecofi.admindevengados.service.PistaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServiceReporteDictamenesImpl implements ServiceReporteDictamenes {

	private final PistaService pistaService;
	private final ReporteDictamenesConsumer reporteDictamenesConsumer;
	private final DictamenService dictamenService;

	@Override
	public String obtenerReporteResumenConsolidado(Long dictamenId) {
		log.info("dictamen {}", dictamenId);
		List<ResumenConsolidadoDto> resumenConsolidado = dictamenService.obtenerResumenConsolidado(dictamenId);

		if (resumenConsolidado.isEmpty()) {
			throw new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND);
		} else {

			reporteDictamenesConsumer.inializar("Dictamen registrado");
			reporteDictamenesConsumer
					.agregarCabeceras(com.sisecofi.admindevengados.util.Constantes.TITULOS_REPORTE_DICTAMENES);
			resumenConsolidado.stream().forEach(reporteDictamenesConsumer);
			byte[] reporte = reporteDictamenesConsumer.cerrarBytes();
			
			String resultado = resumenConsolidado.stream()
				    .map(ResumenConsolidadoDto::toString)
				    .collect(Collectors.joining("|"));
				
				pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),
						TipoSeccionPista.DICTAMEN_DATOS_GENERALES_RESUMEN.getIdSeccionPista(),
						Constantes.getAtributosDictamen()[0] + resumenConsolidado.get(0).getIdDictamen() + "|" + resultado,
						Optional.empty());

			return Base64.getEncoder().encodeToString(reporte);
		}

	}

}
