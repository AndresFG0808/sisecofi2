package com.sisecofi.catalogos.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sisecofi.catalogos.microservicios.PistaMicroservicio;
import com.sisecofi.catalogos.service.PistaService;
import com.sisecofi.catalogos.util.enums.ErroresEnum;
import com.sisecofi.catalogos.util.exception.CatalogoException;
import com.sisecofi.libreria.comunes.model.pista.CatModuloPistaModel;
import com.sisecofi.libreria.comunes.model.pista.CatSeccionPistaModel;
import com.sisecofi.libreria.comunes.model.pista.CatTipoMovPistaModel;
import com.sisecofi.libreria.comunes.model.pista.PistaModel;
import com.sisecofi.libreria.comunes.util.ObtenerIpUtil;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Service
@RequiredArgsConstructor
public class PistaServicioImpl implements PistaService {

	private final PistaMicroservicio pistaMicroservicio;
	private final HttpServletRequest httpRequest;

	@Override
	public boolean guardarPista(Integer idModuloPista, Integer idTipoMovPista, Integer idSeccion, String movimiento,
			Optional<Object> obj) {
		try {
			PistaModel pista = new PistaModel();
			if (obj.isPresent()) {
				pista.setPista(obj.get());
			}
			pista.setModuloPistaModel(new CatModuloPistaModel(idModuloPista));
			pista.setTipoMovPistaModel(new CatTipoMovPistaModel(idTipoMovPista));
			pista.setSeccionPistaModel(new CatSeccionPistaModel(idSeccion));
			pista.setMovimiento(movimiento);
			pista.setIp(ObtenerIpUtil.obtenerIp(httpRequest));
			pistaMicroservicio.guardarPista(pista);
			return true;
		} catch (Exception e) {
			// En ambiente local, solo logear el error y continuar sin lanzar excepción
			// Esto permite que el sistema funcione aunque el servicio de auditoría no esté disponible
			System.err.println("ADVERTENCIA: No se pudo guardar pista de auditoría: " + e.getMessage());
			return false;
		}
	}

}
