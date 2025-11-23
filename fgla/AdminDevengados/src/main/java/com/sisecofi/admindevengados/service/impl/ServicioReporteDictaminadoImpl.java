package com.sisecofi.admindevengados.service.impl;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sisecofi.admindevengados.dto.DictaminadoDto;
import com.sisecofi.admindevengados.service.PistaService;
import com.sisecofi.admindevengados.service.ServicioReporteDictaminado;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.admindevengados.util.consumer.ReporteDictaminadoConsumer;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ServicioReporteDictaminadoImpl implements ServicioReporteDictaminado {

	private final PistaService pistaService;
	private final ReporteDictaminadoConsumer reporteDictaminadoConsumer;

	@Override
	public String obtenerInformacion(List<DictaminadoDto> listaDictaminados) {
		StringBuilder idsDictaminados = new StringBuilder();
		for (DictaminadoDto dictaminadoDto : listaDictaminados) {
			if (idsDictaminados.length() > 0) {
				idsDictaminados.append(",");
			}
			idsDictaminados.append(dictaminadoDto.getIdDictaminado());
		}
		reporteDictaminadoConsumer.inializar("Servicios dictaminados");
		reporteDictaminadoConsumer
				.agregarCabeceras(com.sisecofi.admindevengados.util.Constantes.TITULOS_REPORTE_DICTAMINADO);
		listaDictaminados.stream().forEach(reporteDictaminadoConsumer);
		byte[] reporte = reporteDictaminadoConsumer.cerrarBytes();

		String resultado = listaDictaminados.stream()
			    .map(DictaminadoDto::toString)
			    .collect(Collectors.joining("|"));
		
		pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),
				TipoSeccionPista.DICTAMEN_REGISTRO_SERVICIOS.getIdSeccionPista(),
				Constantes.getAtributosDictaminados()[0] + listaDictaminados.get(0).getIdDictamen() + "|"
						+ Constantes.getAtributosDictaminados()[1] + idsDictaminados + "|" + resultado,
				Optional.empty());

		return Base64.getEncoder().encodeToString(reporte);

	}

}
