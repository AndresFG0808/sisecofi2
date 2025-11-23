package com.sisecofi.proyectos.service;

import java.util.List;

import com.sisecofi.libreria.comunes.dto.RequestMultiple;
import com.sisecofi.proyectos.dto.ProyectoProveedorRequestDto;
import com.sisecofi.proyectos.dto.ProyectoProveedorResponseDto;

public interface ProyectoProveedorService {

	List<ProyectoProveedorResponseDto> getProveedoresAsignados(Long idProyecto);

	List<ProyectoProveedorResponseDto> guardarProveedoresAsignados(
			RequestMultiple<ProyectoProveedorRequestDto, Long> lista);

	boolean eliminarProveedorAsignado(Long id);

}
