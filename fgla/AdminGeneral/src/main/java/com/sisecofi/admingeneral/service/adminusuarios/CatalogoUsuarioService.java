package com.sisecofi.admingeneral.service.adminusuarios;

import java.util.List;

import com.sisecofi.admingeneral.dto.adminusuarios.EstatusDto;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface CatalogoUsuarioService {

	List<EstatusDto> obtenerEstatus();

}
