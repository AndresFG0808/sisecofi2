package com.sisecofi.admindevengados.service.impl;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sisecofi.admindevengados.dto.PenaContractualExcelDto;
import com.sisecofi.admindevengados.service.PistaService;
import com.sisecofi.admindevengados.service.ServicioReporteDeducciones;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.admindevengados.util.consumer.ReporteDeduccionConsumer;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ServicioReporteDeduccionesImpl implements ServicioReporteDeducciones {

	private final ReporteDeduccionConsumer reporteDeduccionConsumer;
	private final PistaService pistaService;

	@Override
	public String obtenerInformacion(List<PenaContractualExcelDto> penasSeleccionadas) {
		StringBuilder idsDeducciones = new StringBuilder();

		for (PenaContractualExcelDto penaContractualExcelDto : penasSeleccionadas) {
			if (idsDeducciones.length() > 0) {
				idsDeducciones.append(",");
			}
			idsDeducciones.append(penaContractualExcelDto.getIdPenaPrimary());
		}

		reporteDeduccionConsumer.inializar("Deducciones registradas");
		reporteDeduccionConsumer
				.agregarCabeceras(com.sisecofi.admindevengados.util.Constantes.TITULOS_REPORTE_DEDUCCION);
		penasSeleccionadas.stream().forEach(reporteDeduccionConsumer);
		byte[] reporte = reporteDeduccionConsumer.cerrarBytes();
		
		String resultado = penasSeleccionadas.stream()
			    .map(PenaContractualExcelDto::toString)
			    .collect(Collectors.joining("|"));
		
			pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),
					TipoSeccionPista.DICTAMEN_DEDUCCIONES.getIdSeccionPista(),
					Constantes.getAtributosDeduccionesExportar()[0] + penasSeleccionadas.get(0).getDictamenId() + "|"
							+ Constantes.getAtributosDeduccionesExportar()[1] + idsDeducciones.toString() + "|" + resultado, Optional.empty());

		return Base64.getEncoder().encodeToString(reporte);
	}

}
