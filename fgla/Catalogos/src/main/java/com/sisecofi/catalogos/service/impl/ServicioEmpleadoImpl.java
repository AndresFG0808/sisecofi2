package com.sisecofi.catalogos.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sisecofi.catalogos.dto.EmpleadoDto;
import com.sisecofi.catalogos.repository.CatAdministracionRepository;
import com.sisecofi.catalogos.repository.CatEmpleadoAdministracionRepository;
import com.sisecofi.catalogos.repository.CatTipoEmpleadoRepository;
import com.sisecofi.catalogos.service.ServicioEmpleado;
import com.sisecofi.catalogos.util.exception.CatalogoException;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatEmpleadoAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoEmpleado;
import com.sisecofi.catalogos.util.consumer.ReporteEmpleadosConsumer;
import com.sisecofi.catalogos.util.enums.ErroresEnum;
import com.sisecofi.catalogos.util.Constantes;

import org.modelmapper.ModelMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ServicioEmpleadoImpl implements ServicioEmpleado {

	private final CatTipoEmpleadoRepository catTipoEmpleadoRepository;
	private final CatEmpleadoAdministracionRepository catEmpleadoAdministracionRepository;
	private final ModelMapper modelMapper;
	private final CatAdministracionRepository catAdministracionRepository;
	private final ReporteEmpleadosConsumer consumer;

	@Override
	public List<CatTipoEmpleado> obtenerTipoEmpleado() {
		return catTipoEmpleadoRepository.findByEstatusTrue();
	}

	@Override
	public List<EmpleadoDto> obtenerEmpleadosAdministracion(Integer idAdministracion) {
		return catEmpleadoAdministracionRepository
				.findByCatAdministracionIdAdministracionOrderByIdEmpleadoAdministracionAsc(idAdministracion).stream()
				.map(empleado -> modelMapper.map(empleado, EmpleadoDto.class)).toList();
	}

	@Override
	public List<EmpleadoDto> guardarEmpleadosAdministracion(List<EmpleadoDto> lista, Integer idAdministracion) {

		if (lista == null || lista.isEmpty()) {
			return Collections.emptyList();
		}

		CatAdministracion administracion = catAdministracionRepository.findById(idAdministracion)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_CONSULTA_INVALIDA));

		Map<Integer, CatTipoEmpleado> tipoEmpleadoCache = new HashMap<>();

		for (EmpleadoDto empleado : lista) {
			if (empleado.isModificado() || empleado.getIdEmpleadoAdministracion() == null) {
				agregarFechas(empleado);
				CatEmpleadoAdministracion empleadoBd = modelMapper.map(empleado, CatEmpleadoAdministracion.class);
				CatTipoEmpleado tipoEmpleado = tipoEmpleadoCache.computeIfAbsent(empleado.getIdTipoEmpleado(),
						id -> catTipoEmpleadoRepository.findById(id)
								.orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_CONSULTA_INVALIDA)));

				empleadoBd.setCatTipoEmpleado(tipoEmpleado);

				empleadoBd.setCatAdministracion(administracion);
				
				catEmpleadoAdministracionRepository.save(empleadoBd);
			}
		}

		return obtenerEmpleadosAdministracion(idAdministracion);
	}
	
	private void agregarFechas(EmpleadoDto empleado) {
	    LocalDateTime ahora = LocalDateTime.now();

	    if (empleado.getIdEmpleadoAdministracion() == null) {
	        empleado.setFechaCreacion(ahora);
	    } else {
	        empleado.setFechaModificacion(ahora);
	        if (empleado.getFechaCreacion() == null) {
	            empleado.setFechaCreacion(ahora);
	        }
	    }
	}

	@Override
	public byte[] exportarEmpleadosAdministracion(Integer idAdministracion) {
		List<EmpleadoDto> lista= catEmpleadoAdministracionRepository
				.findByCatAdministracionIdAdministracionOrderByIdEmpleadoAdministracionAsc(idAdministracion).stream()
				.map(empleado -> modelMapper.map(empleado, EmpleadoDto.class)).toList();
		
		consumer.inializar("Empleados administración");
		consumer.agregarCabeceras(Constantes.TITULOS_EMPLEADOS);
		lista.stream().forEach(consumer);
		return consumer.cerrarBytes();
	}


}
