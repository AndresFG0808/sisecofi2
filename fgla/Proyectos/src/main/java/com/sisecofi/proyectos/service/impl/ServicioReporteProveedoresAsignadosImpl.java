package com.sisecofi.proyectos.service.impl;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.proyectos.model.ProyectoProveedorModel;
import com.sisecofi.proyectos.repository.ProyectoProveedorRepository;
import com.sisecofi.proyectos.service.PistaService;
import com.sisecofi.proyectos.service.ServicioReporteProveedoresAsignados;
import com.sisecofi.proyectos.util.Constantes;
import com.sisecofi.proyectos.util.consumer.ReporteProyectoProveedoresAsignadosConsumer;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.exception.ProyectoException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioReporteProveedoresAsignadosImpl implements ServicioReporteProveedoresAsignados {

	private final ReporteProyectoProveedoresAsignadosConsumer asignadosConsumer;
	private final PistaService pistaService;
	private final ProyectoProveedorRepository proyectoProveedorRepository;

	@Override
	public byte[] obtenerReporteProveedoresAsignados(Long idProyecto) {
		try {
			List<ProyectoProveedorModel> lista = proyectoProveedorRepository
					.findByProyectoModelIdProyectoAndEstatusOrderByIdProyectoProveedorAsc(idProyecto, true);
			
			StringBuilder proveedores = new StringBuilder();

			for (ProyectoProveedorModel prov: lista) {
				proveedores.append(" |").append(prov.getProveedorModel().getNombreProveedor());
			}
			
			asignadosConsumer.inializar("Proveedores Participantes");
			asignadosConsumer.agregarCabeceras(Constantes.TITULOS_REPORTE_PROVEEDORES_PARTICIPANTES);
			lista.stream().forEach(asignadosConsumer);

			// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),TipoSeccionPista.PROYECTO_DATOS_PARTICIPACION_PROVEDORES.getIdSeccionPista(),

			// proveedores.toString(),Optional.empty());
			return asignadosConsumer.cerrarBytes();
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}

	}

}
