package com.sisecofi.proyectos.service.impl;

import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.proyectos.dto.EstructuraProyectoMetaDto;
import com.sisecofi.proyectos.dto.ProyectoDtoLigero;
import com.sisecofi.proyectos.service.PistaService;
import com.sisecofi.proyectos.service.ServicioReporteProyectos;
import com.sisecofi.proyectos.util.Constantes;
import com.sisecofi.proyectos.util.consumer.ReporteProyectosConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ServicioReporteProyectosImpl implements ServicioReporteProyectos {
	
	private final PistaService pistaService;
	private final ReporteProyectosConsumer reporteProyectosConsumer;
	private final ServicioProyectoImpl servicioProyectoImpl;

	@Override
	public byte[] obtenerReporteProyectosRegistrados(EstructuraProyectoMetaDto proyecto) {
			List<ProyectoDtoLigero> lista = servicioProyectoImpl.buscarProyectosLista(proyecto);
	
			reporteProyectosConsumer.inializar("Proyectos registrados");
			reporteProyectosConsumer.agregarCabeceras(Constantes.TITULOS_REPORTE_PROYECTOS);
			lista.stream().forEach(reporteProyectosConsumer);

			// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),

			// TipoSeccionPista.PROYECTO_BUSQUEDA.getIdSeccionPista(), servicioProyectoImpl.obtenerCriterios(proyecto),Optional.empty());
			return reporteProyectosConsumer.cerrarBytes();
	}
}
