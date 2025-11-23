package com.sisecofi.contratos.service.impl;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.contratos.microservicios.PistaMicroservicio;
import com.sisecofi.contratos.service.PistaService;
import com.sisecofi.contratos.util.exception.PistaException;
import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;
import com.sisecofi.libreria.comunes.model.pista.CatModuloPistaModel;
import com.sisecofi.libreria.comunes.model.pista.CatSeccionPistaModel;
import com.sisecofi.libreria.comunes.model.pista.CatTipoMovPistaModel;
import com.sisecofi.libreria.comunes.model.pista.PistaModel;
import com.sisecofi.libreria.comunes.util.ObtenerIpUtil;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
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
	public boolean guardarPista(Integer idModuloPista, Integer idTipoMovPista, Integer idSeccion, String movimiento, Optional<Object> obj) {
	    boolean resultado = false;
	    try {
	        PistaModel pista = crearPista(idModuloPista, idTipoMovPista, idSeccion, movimiento, obj);
	        pistaMicroservicio.guardarPista(pista);
	        resultado = true;
	    } catch (Exception e) {
	        manejarExcepcionPista(idTipoMovPista, e);
	    }
	    return resultado;
	}

	private String procesarObjeto(Optional<Object> obj) {
	    if (obj.isEmpty()) {
	        return "";
	    }

	    Object modelo = obj.get();
	    StringBuilder s = new StringBuilder();
	    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	    for (Field field : modelo.getClass().getDeclaredFields()) {
	        if (field.isAnnotationPresent(JsonIgnore.class)) {
	            continue;
	        }
	        if (field.trySetAccessible()) {
	            try {
	                Object valor = field.get(modelo);
	                procesarCampo(field, valor, s, dateFormatter);
	            } catch (Exception e) {
	                throw new PistaException(ErroresEnum.ERROR_AL_PISTA_DESCONOCIDO, e);
	            }
	        }
	    }

	    return s.length() > 2 ? s.substring(0, s.length() - 2).replace("log: Logger| ", "") : "";
	}

	private void procesarCampo(Field field, Object valor, StringBuilder s, DateTimeFormatter dateFormatter) throws NoSuchFieldException, IllegalAccessException {
	    if (valor == null) {
	        s.append(field.getName()).append(": null| ");
	    } else if (valor instanceof String || valor instanceof Number || valor instanceof Boolean || valor instanceof Enum) {
	        s.append(field.getName()).append(": ").append(valor).append("| ");
	    } else if (valor instanceof TemporalAccessor temporalValue) {
	        s.append(field.getName()).append(": ").append(dateFormatter.format(temporalValue)).append("| ");
	    } else {
	        agregarObjetoComplejo(field, valor, s);
	    }
	}

	private void manejarExcepcionPista(Integer idTipoMovPista, Exception e) {
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

	private PistaModel crearPista(Integer idModuloPista, Integer idTipoMovPista, Integer idSeccion, String movimiento, Optional<Object> obj) {
	    PistaModel pista = new PistaModel();

	    String log = obj.isPresent() ? procesarObjeto(obj) : movimiento;
	    pista.setMovimiento(log);

	    pista.setModuloPistaModel(new CatModuloPistaModel(idModuloPista));
	    pista.setTipoMovPistaModel(new CatTipoMovPistaModel(idTipoMovPista));
	    pista.setSeccionPistaModel(new CatSeccionPistaModel(idSeccion));
	    pista.setIp(ObtenerIpUtil.obtenerIp(httpRequest));

	    return pista;
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

	        if (idField.isPresent()) {
	            Field id = idField.get();
	            if (id.trySetAccessible()) {
	                s.append(" (ID: ").append(id.get(valor)).append(")| ");
	            }
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
