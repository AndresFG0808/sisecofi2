package com.directorio.activo.service;

import java.util.List;

import com.directorio.activo.dto.BusquedaDirectorioDto;
import com.directorio.activo.dto.UsuarioDirectorioDto;
import com.directorio.activo.util.exception.DirectorioActivoException;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface DirectorioActivoService {

	List<UsuarioDirectorioDto> buscar(BusquedaDirectorioDto dto) throws DirectorioActivoException;

}
