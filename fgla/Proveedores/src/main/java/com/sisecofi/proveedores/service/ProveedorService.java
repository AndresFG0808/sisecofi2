package com.sisecofi.proveedores.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.sisecofi.proveedores.dto.CatalogoProveedorDto;
import com.sisecofi.proveedores.dto.ConsultaProveedorDto;
import com.sisecofi.proveedores.dto.DictamenTecnicoResponseDto;
import com.sisecofi.proveedores.dto.ProveedoGeneralDto;
import com.sisecofi.proveedores.dto.ProveedorDto;
import com.sisecofi.proveedores.dto.ProveedorRequestDto;
import com.sisecofi.proveedores.dto.TituloServicioResponseDto;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;

/**
 *
 * @author adtolentino
 *
 */

@Service
public interface ProveedorService {

	ProveedorModel crearProveedor(ProveedorDto proveedorDto);

	Page<ConsultaProveedorDto> obtenerTodosLosProveedores(ProveedorRequestDto proveedorRequestDto);

	ConsultaProveedorDto obtenerProveedorPorId(Long id);

	ProveedorDto actualizarProveedor(Long id, ProveedorDto proveedorDto);

	List<CatalogoProveedorDto> listarProveedores();

	List<CatalogoProveedorDto> listarProveedoresActivos();

	Page<ProveedoGeneralDto> filtrarProveedores(ProveedorRequestDto filtro);

	List<DictamenTecnicoResponseDto> obtenerProveedorPorIdCumple(ProveedorRequestDto filtro);

	List<TituloServicioResponseDto> obtenerProveedorTitulosGeneral(ProveedorRequestDto filtro);

}
