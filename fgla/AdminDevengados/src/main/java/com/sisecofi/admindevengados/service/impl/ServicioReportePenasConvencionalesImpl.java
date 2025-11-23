package com.sisecofi.admindevengados.service.impl;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sisecofi.admindevengados.dto.PenaContractualExcelDto;
import com.sisecofi.admindevengados.service.PistaService;
import com.sisecofi.admindevengados.service.ServicioReportePenasConvencionales;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.admindevengados.util.consumer.ReportePenaConvencionalConsumer;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ServicioReportePenasConvencionalesImpl implements ServicioReportePenasConvencionales {

	private final ReportePenaConvencionalConsumer reportePenaConvencionalConsumer;
	private final PistaService pistaService;

	@Override
	public String obtenerInformacion(List<PenaContractualExcelDto> penasSeleccionadas) {
		StringBuilder idsPenas = new StringBuilder();

		for (PenaContractualExcelDto penaContractualExcelDto : penasSeleccionadas) {
			if (idsPenas.length() > 0) {
				idsPenas.append(",");
			}
			idsPenas.append(penaContractualExcelDto.getIdPenaPrimary());
		}

		reportePenaConvencionalConsumer.inializar("Penas convencionales registradas");
		reportePenaConvencionalConsumer
				.agregarCabeceras(com.sisecofi.admindevengados.util.Constantes.TITULOS_REPORTE_PENA);
		penasSeleccionadas.stream().forEach(reportePenaConvencionalConsumer);
		byte[] reporte = reportePenaConvencionalConsumer.cerrarBytes();
		
		String resultado = penasSeleccionadas.stream()
			    .map(PenaContractualExcelDto::toString)
			    .collect(Collectors.joining("|"));
		
			pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),
					TipoSeccionPista.DICTAMEN_PENAS_CONVENCIONALES.getIdSeccionPista(),
					Constantes.getAtributosPenasConvencionalesExportar()[0] + penasSeleccionadas.get(0).getDictamenId() + "|"
							+ Constantes.getAtributosPenasConvencionalesExportar()[1] + "|" + resultado, Optional.empty());

		return Base64.getEncoder().encodeToString(reporte);
	}

}
