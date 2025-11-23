package com.sisecofi.proyectos.service.impl;

import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.proyectos.dto.ResponseComiteInfoReporte;
import com.sisecofi.proyectos.service.PistaService;
import com.sisecofi.proyectos.service.ServicioComite;
import com.sisecofi.proyectos.service.ServicioReporteComite;
import com.sisecofi.proyectos.util.Constantes;
import com.sisecofi.proyectos.util.consumer.ReporteContratoConvenioConsumer;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.exception.ProyectoException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ServicioReporteComiteImpl implements ServicioReporteComite {

	private final PistaService pistaService;
	private final ReporteContratoConvenioConsumer reporteContratoConvenioConsumer;
	private final ServicioComite servicioComite;

	@Override
	public String obtenerReporteContratoConvenio(Integer idProyecto) {
		try {
			List<ResponseComiteInfoReporte> comiteProyectoModel = servicioComite.obtenerComitesReporteInfo(idProyecto);

			reporteContratoConvenioConsumer.inializar("Informacion del comite");
			reporteContratoConvenioConsumer.agregarCabeceras(Constantes.TITULOS_REPORTE_COMITE);

			comiteProyectoModel.forEach(reporteContratoConvenioConsumer);
			byte[] reporte = reporteContratoConvenioConsumer.cerrarBytes();

			pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),
					TipoSeccionPista.PROYECTO_DATOS_INFORMACION_COMITE.getIdSeccionPista(),
					"ID proyecto: " + comiteProyectoModel.get(0).getInformacionComite().getIdProyecto() + "| "
							+ "Nombre corto del proyecto: "
							+ comiteProyectoModel.get(0).getInformacionComite().getNombreCortoProyecto(),
					Optional.empty());

			pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),
					TipoSeccionPista.PROYECTO_DATOS_INFORMACION_COMITE.getIdSeccionPista(),
					"ID proyecto: " + comiteProyectoModel.get(0).getInformacionComite().getIdProyecto() + "| "
							+ "Nombre corto del proyecto: "
							+ comiteProyectoModel.get(0).getInformacionComite().getNombreCortoProyecto(),
					Optional.empty());

			return Base64.getEncoder().encodeToString(reporte);
		} catch (Exception e) {
			log.info(Constantes.ERROR);
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}
	}
}
