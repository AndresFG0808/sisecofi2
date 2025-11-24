package com.sisecofi.admindevengados.service.impl;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sisecofi.admindevengados.dto.PenaContractualExcelDto;
import com.sisecofi.admindevengados.service.PistaService;
import com.sisecofi.admindevengados.service.ServicioReportePenasContractuales;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.admindevengados.util.consumer.ReportePenaContractualConsumer;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ServicioReportePenasContractualesImpl implements ServicioReportePenasContractuales {

	private final ReportePenaContractualConsumer reportePenaContractualConsumer;
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
		reportePenaContractualConsumer.inializar("Penas contractuales registradas");
		reportePenaContractualConsumer
				.agregarCabeceras(com.sisecofi.admindevengados.util.Constantes.TITULOS_REPORTE_PENA);
		penasSeleccionadas.stream().forEach(reportePenaContractualConsumer);
		byte[] reporte = reportePenaContractualConsumer.cerrarBytes();
		
		String resultado = penasSeleccionadas.stream()
			    .map(PenaContractualExcelDto::toString)
			    .collect(Collectors.joining("|"));
		

		
			// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),

		
			// TipoSeccionPista.DICTAMEN_PENAS_CONTRACTUALES.getIdSeccionPista(),

		
			// Constantes.getAtributosPenasContractualesExportar()[0] + penasSeleccionadas.get(0).getDictamenId() + "|"

		
			// + Constantes.getAtributosPenasContractualesExportar()[1] + idsPenas.toString()+ "|" + resultado, Optional.empty());

		return Base64.getEncoder().encodeToString(reporte);
	}

}
