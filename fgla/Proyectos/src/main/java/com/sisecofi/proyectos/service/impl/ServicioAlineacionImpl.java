package com.sisecofi.proyectos.service.impl;

import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.sisecofi.proyectos.dto.AlineacionRequest;
import com.sisecofi.proyectos.model.FichaAlineacion;
import com.sisecofi.libreria.comunes.model.proyectos.FichaTecnicaModel;
import com.sisecofi.proyectos.repository.FichaAlineacionRepository;
import com.sisecofi.proyectos.repository.FichaTecnicaRepository;
import com.sisecofi.proyectos.service.ServicioAlineacion;
import com.sisecofi.proyectos.util.Constantes;
import com.sisecofi.proyectos.util.consumer.ReporteAlineacionConsumer;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.exception.ProyectoException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioAlineacionImpl implements ServicioAlineacion {
	private final FichaAlineacionRepository fichaAlineacionRepository;
	private final FichaTecnicaRepository fichaTecnicaRepository;
	private final ReporteAlineacionConsumer consumer;

	@Override
	@Transactional
	public boolean agregarAlineaciones(List<AlineacionRequest> alineaciones, Long idFicha) {
		boolean exists = fichaTecnicaRepository.existsById(idFicha);
		if (!exists) {
		    throw new ProyectoException(ErroresEnum.FICHA_NO_ENCONTRADA);
		}
		if (alineaciones != null) {
			for (AlineacionRequest alineacionRequest : alineaciones) {
				FichaAlineacion alineacion = new FichaAlineacion();
				alineacion.setIdFichaAlineacion(alineacionRequest.getIdFichaAlineacion());
				alineacion.setIdObjetivo(alineacionRequest.getIdObjetivo());
				alineacion.setIdPeriodo(alineacionRequest.getIdPeriodo());
				alineacion.setIdFichaTecnica(idFicha);
				alineacion.setEstatusAlineacion(true);
				alineacion.setIdAliniacion(alineacionRequest.getIdMapa());
				fichaAlineacionRepository.save(alineacion);
			}
		}
		return true; 
	}


	@Override
	@Transactional
	public FichaAlineacion eliminarAlineacion(Long idAlineacion) {
		FichaAlineacion alineacion = fichaAlineacionRepository.findById(idAlineacion)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.ALINEACION_NO_ENCONTRADA));
		alineacion.setEstatusAlineacion(false);
		return fichaAlineacionRepository.save(alineacion);
	}

	@Override
	public String generarReporteAlineacion(Long idProyecto) {
		FichaTecnicaModel ficha = fichaTecnicaRepository.findByIdProyecto(idProyecto)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.ALINEACION_NO_DISPONIBLE));
		try {
			List<FichaAlineacion> lista = fichaAlineacionRepository
					.findByIdFichaTecnicaAndEstatusAlineacion(ficha.getIdFichaTecnica(), true);
			consumer.inializar("Alineaciones del proyecto con id " + idProyecto);
			consumer.agregarCabeceras(Constantes.TITULOS_REPORTE_ALINEACION);
			lista.stream().forEach(consumer);
			byte[] reporte = consumer.cerrarBytes();
			return Base64.getEncoder().encodeToString(reporte);
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}
	}

	@Override
	public boolean verificarMapas(Long idFicha) {
	    List<FichaAlineacion> lista = fichaAlineacionRepository
	            .findByIdFichaTecnicaAndEstatusAlineacion(idFicha, true);
	    
	    boolean contieneEstrategico = lista.stream()
	            .anyMatch(ficha -> ficha.getCatAliniacion().getNombre().toLowerCase()
	                    .replace("é", "e").contains("estrategico"));

	    boolean contieneEspecifico = lista.stream()
	            .anyMatch(ficha -> ficha.getCatAliniacion().getNombre().toLowerCase()
	                    .replace("í", "i").contains("especifico"));

	    return contieneEstrategico && contieneEspecifico;
	}

}
