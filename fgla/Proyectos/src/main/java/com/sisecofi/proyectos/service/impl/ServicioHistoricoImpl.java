package com.sisecofi.proyectos.service.impl;

import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.util.Base64;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.sisecofi.libreria.comunes.model.proyectos.FichaTecnicaModel;
import com.sisecofi.libreria.comunes.model.proyectos.HistoricoModel;
import com.sisecofi.proyectos.repository.HistoricoRepository;
import com.sisecofi.proyectos.repository.FichaTecnicaRepository;
import com.sisecofi.proyectos.service.ServicioHistorico;
import com.sisecofi.proyectos.util.Constantes;
import com.sisecofi.proyectos.util.consumer.ReporteHistoricoConsumer;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.exception.ProyectoException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioHistoricoImpl implements ServicioHistorico {
	private final HistoricoRepository historicoRepository;
	private final FichaTecnicaRepository fichaTecnicaRepository;
	private final ReporteHistoricoConsumer consumer;

	@Override
	public HistoricoModel eliminarHistorico(Long id) {
		HistoricoModel historicoModel = historicoRepository.findByIdHistoricoAndEstatusHistorico(id, true)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.HISTORICO_NO_ENCONTRADO));
		historicoModel.setEstatusHistorico(false);
		historicoRepository.save(historicoModel);
		return historicoModel;
	}

	@Override
	@Transactional
	public boolean agregarHistoricos(Set<HistoricoModel> historicos, Long idFicha) {
		boolean exists = fichaTecnicaRepository.existsById(idFicha);
		if (!exists) {
		    throw new ProyectoException(ErroresEnum.FICHA_NO_ENCONTRADA);
		}
	    if (historicos != null) {
	        validarHistoricos(historicos, idFicha);
	        historicos.addAll(historicoRepository.findByIdFichaTecnicaAndEstatusHistorico(idFicha, true));
	        validarUnicoLiderActivo(historicos);
	        validarFechasHistoricos(idFicha);
	    }
	    return true;
	}

	@Transactional
	private void validarHistoricos(Set<HistoricoModel> historicos, Long idFicha) {
	    for (HistoricoModel historico : historicos) {
	        historico.setIdFichaTecnica(idFicha);
	        historico.setEstatusHistorico(true);
	        if (!historico.isEstatus()
	                && (historico.getFechaFin() == null || historico.getFechaFin().isAfter(LocalDateTime.now())
	                || !historico.getFechaFin().isAfter(historico.getFechaInicio().minusDays(1)))) {
	            throw new ProyectoException(ErroresEnum.ERROR_FECHA_FIN);
	        }
	        historicoRepository.save(historico);
	    }
	}

	private void validarUnicoLiderActivo(Set<HistoricoModel> historicos) {
	    long count = historicos.stream().filter(HistoricoModel::isEstatus).count();
	    if (count > 2) {
	        throw new ProyectoException(ErroresEnum.LIDER_ACTUAL_ACTIVO);
	    }
	}

	private void validarFechasHistoricos(Long idFicha) {
		LocalDateTime fechaValidacion = LocalDateTime.now();
	    Optional<HistoricoModel> historicoAnterior = historicoRepository
	            .findTopByIdFichaTecnicaAndEstatusHistoricoAndEstatusOrderByIdHistoricoDesc(idFicha, true, false);
	    Optional<HistoricoModel> historicoActual = historicoRepository
	            .findTopByIdFichaTecnicaAndEstatusHistoricoAndEstatusOrderByIdHistoricoDesc(idFicha, true, true);

	    if (historicoAnterior.isPresent()) {
	    	
	        fechaValidacion = obtenerSiguienteDiaHabil(historicoAnterior.get().getFechaFin());
	        validarFechasConHistoricoActual(historicoActual, fechaValidacion);
	    } else {
	        validarFechasSinHistoricoAnterior(historicoActual, fechaValidacion);
	    }
	}

	private void validarFechasConHistoricoActual(Optional<HistoricoModel> historicoActual, LocalDateTime fechaValidacion) {
		if (historicoActual.isPresent() && 
			    !historicoActual.get().getFechaInicio().toLocalDate().equals(fechaValidacion.toLocalDate())) {
			    throw new ProyectoException(ErroresEnum.ERROR_FECHA_INICIO_CON_LIDER);
			}

	}

	private void validarFechasSinHistoricoAnterior(Optional<HistoricoModel> historicoActual, LocalDateTime fechaValidacion) {
	    if (historicoActual.isPresent()
	            && (historicoActual.get().getFechaInicio().isAfter(fechaValidacion)
	            || finDeSemana(historicoActual.get().getFechaInicio()))) {
	        throw new ProyectoException(ErroresEnum.ERROR_FECHA_INICIO);
	    }
	}

	private LocalDateTime obtenerSiguienteDiaHabil(LocalDateTime fecha) {
		LocalDateTime diaSiguiente = fecha.plusDays(1);
		while (finDeSemana(diaSiguiente)) {
			diaSiguiente = diaSiguiente.plusDays(1);
		}
		
		return diaSiguiente;
	}

	private boolean finDeSemana(LocalDateTime fecha) {
		DayOfWeek dayOfWeek = fecha.getDayOfWeek();
		return (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY);
	}

	@Override
	public String generarReporteHistorico(Long idProyecto) {
		FichaTecnicaModel ficha = fichaTecnicaRepository.findByIdProyecto(idProyecto)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.HISTORICO_NO_DISPONIBLE));
		try {
		Set<HistoricoModel> historico = historicoRepository.findByIdFichaTecnicaAndEstatusHistorico(ficha.getIdFichaTecnica(), true);
		consumer.inializar("Historico de lideres del proyecto" + idProyecto);
		consumer.agregarCabeceras(Constantes.TITULOS_REPORTE_HISTORICO);
		historico.stream().forEach(consumer);
		byte[] reporte = consumer.cerrarBytes();
		 return Base64.getEncoder().encodeToString(reporte);
		}catch (Exception e) {
	            throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
	        }
	}

}
