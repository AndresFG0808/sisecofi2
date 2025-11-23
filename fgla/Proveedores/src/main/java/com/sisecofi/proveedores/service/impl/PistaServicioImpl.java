package com.sisecofi.proveedores.service.impl;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;
import com.sisecofi.libreria.comunes.model.pista.CatModuloPistaModel;
import com.sisecofi.libreria.comunes.model.pista.CatSeccionPistaModel;
import com.sisecofi.libreria.comunes.model.pista.CatTipoMovPistaModel;
import com.sisecofi.libreria.comunes.model.pista.PistaModel;
import com.sisecofi.libreria.comunes.util.ObtenerIpUtil;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.proveedores.microservicio.PistaMicroservicio;
import com.sisecofi.proveedores.service.PistaService;
import com.sisecofi.proveedores.util.enums.ErroresEnum;
import com.sisecofi.proveedores.util.exception.PistaException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author adtolentino
 * 
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class PistaServicioImpl implements PistaService {

	private final PistaMicroservicio pistaMicroservicio;
	private final HttpServletRequest httpRequest;

	@Override
	public boolean guardarPista(Integer idModuloPista, Integer idTipoMovPista, Integer idSeccion, String movimiento, Optional<Object> obj) {
	    try {
	        PistaModel pista = new PistaModel();

	        String movimientoFinal = obj.map(this::procesarObjetoParaPista).orElse(movimiento);
	        pista.setMovimiento(movimientoFinal);

	        configurarPista(pista, idModuloPista, idTipoMovPista, idSeccion);
	        pistaMicroservicio.guardarPista(pista);
	        return true;

	    } catch (Exception e) {
	        manejarErroresPista(idTipoMovPista, e);
	        throw new PistaException(ErroresEnum.ERROR_AL_PISTA_DESCONOCIDO, e);
	    }
	}

	private String procesarObjetoParaPista(Object modelo) {
	    StringBuilder s = new StringBuilder();
	    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	    for (Field field : modelo.getClass().getDeclaredFields()) {
	        if (field.isAnnotationPresent(JsonIgnore.class)) {
	            continue;
	        }
	        if (field.trySetAccessible()) {
	            try {
	                Object valor = field.get(modelo);
	                agregarValorCampo(field, valor, s, dateFormatter);
	            } catch (Exception e) {
	                throw new PistaException(ErroresEnum.ERROR_AL_PISTA_DESCONOCIDO, e);
	            }
	        }
	    }

	    if (s.length() > 2) {
	        s.setLength(s.length() - 2);
	    }
	    return s.toString().replace("log: Logger| ", "");
	}

	private void agregarValorCampo(Field field, Object valor, StringBuilder s, DateTimeFormatter dateFormatter) {
	    if (valor == null) {
	        s.append(field.getName()).append(": null| ");
	    } else if (valor instanceof String || valor instanceof Number || valor instanceof Boolean || valor instanceof Enum) {
	        s.append(field.getName()).append(": ").append(valor).append("| ");
	    } else if (valor instanceof TemporalAccessor temporalValue) {
	        s.append(field.getName()).append(": ").append(dateFormatter.format(temporalValue)).append("| ");
	    } else {
	        try {
	            agregarObjetoComplejo(field, valor, s);
	        } catch (NoSuchFieldException | IllegalAccessException e) {
	            log.error("Error al agregar objeto complejo:");
	        }
	    }
	}

	private void configurarPista(PistaModel pista, Integer idModuloPista, Integer idTipoMovPista, Integer idSeccion) {
	    pista.setModuloPistaModel(new CatModuloPistaModel(idModuloPista));
	    pista.setTipoMovPistaModel(new CatTipoMovPistaModel(idTipoMovPista));
	    pista.setSeccionPistaModel(new CatSeccionPistaModel(idSeccion));
	    pista.setIp(ObtenerIpUtil.obtenerIp(httpRequest));
	}

	private void manejarErroresPista(Integer idTipoMovPista, Exception e) {
	    if (idTipoMovPista == TipoMovPista.INSERTA_REGISTRO.getId()
	            || idTipoMovPista == TipoMovPista.ACTUALIZA_REGISTRO.getId()) {
	        throw new PistaException(ErroresEnum.ERROR_AL_GUARDAR_LA_PISTA, e);
	    } else if (idTipoMovPista == TipoMovPista.CONSULTA_REGISTRO.getId()) {
	        throw new PistaException(ErroresEnum.ERROR_AL_CONSULTAR_LA_PISTA, e);
	    } else if (idTipoMovPista == TipoMovPista.IMPRIME_REGISTRO.getId()) {
	        throw new PistaException(ErroresEnum.ERROR_AL_IMPRIMIR_LA_PISTA, e);
	    }
	}

	private void agregarObjetoComplejo(Field field, Object valor, StringBuilder s) throws NoSuchFieldException, IllegalAccessException {
	    if (valor instanceof DictamenId) {
	        Field idField = valor.getClass().getDeclaredField("idDictamen");
	        if (idField.trySetAccessible()) {
	            s.append("idDictamen: ").append(idField.get(valor)).append("| ");
	        }
	    } else {
	        s.append(field.getName()).append(": ").append(valor.getClass().getSimpleName());
	        Optional<Field> idField = Arrays.stream(valor.getClass().getDeclaredFields())
	                .filter(f -> f.getName().equalsIgnoreCase("id"))
	                .findFirst();

	        if (idField.isPresent() && idField.get().trySetAccessible()) {
	            s.append(" (ID: ").append(idField.get().get(valor)).append(")| ");
	        }
	    }
	}

	@Override
	public boolean guardarPistaSimple(Integer idModuloPista, Integer idTipoMovPista, Integer idSeccion, String movimiento,
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
			if (idTipoMovPista == TipoMovPista.INSERTA_REGISTRO.getId()
					|| idTipoMovPista == TipoMovPista.ACTUALIZA_REGISTRO.getId()) {
				throw new PistaException(ErroresEnum.ERROR_AL_GUARDAR_LA_PISTA, e);
			} else if (idTipoMovPista == TipoMovPista.CONSULTA_REGISTRO.getId()) {
				throw new PistaException(ErroresEnum.ERROR_AL_CONSULTAR_LA_PISTA, e);
			} else if (idTipoMovPista == TipoMovPista.IMPRIME_REGISTRO.getId()) {
				throw new PistaException(ErroresEnum.ERROR_AL_IMPRIMIR_LA_PISTA, e);
			}
			throw new PistaException(ErroresEnum.ERROR_AL_PISTA_DESCONOCIDO, e);
		}
	}
}