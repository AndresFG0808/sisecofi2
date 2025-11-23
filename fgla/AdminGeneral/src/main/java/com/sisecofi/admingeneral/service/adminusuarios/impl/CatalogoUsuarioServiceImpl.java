package com.sisecofi.admingeneral.service.adminusuarios.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sisecofi.admingeneral.dto.adminusuarios.EstatusDto;
import com.sisecofi.admingeneral.service.adminusuarios.CatalogoUsuarioService;
import com.sisecofi.admingeneral.util.enums.EstatusUsuarioEnum;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Service
public class CatalogoUsuarioServiceImpl implements CatalogoUsuarioService {

	@Override
	public List<EstatusDto> obtenerEstatus() {
		List<EstatusDto> lista = new ArrayList<>();
		for (EstatusUsuarioEnum valor : EstatusUsuarioEnum.values()) {
			EstatusDto o = new EstatusDto();
			o.setDescripcion(valor.getDescripcion());
			o.setEstatus(valor.isEstatus());
			lista.add(o);
		}
		return lista;
	}

}
