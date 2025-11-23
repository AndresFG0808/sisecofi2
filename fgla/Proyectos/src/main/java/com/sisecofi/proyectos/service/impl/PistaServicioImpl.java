package com.sisecofi.proyectos.service.impl;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
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
import com.sisecofi.proyectos.microservicio.PistaMicroservicio;
import com.sisecofi.proyectos.service.PistaService;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.exception.PistaException;

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
	    try {
	        PistaModel pista = new PistaModel();
	        StringBuilder s = new StringBuilder();

	        obj.ifPresent(modelo -> processModelo(s, modelo));

	        String resultadoFinal = s.toString().replace("log: Logger| ", "");
	        pista.setMovimiento(movimiento + (s.length() > 0 ? "|" + resultadoFinal : ""));

	        setPistaMetadata(pista, idModuloPista, idTipoMovPista, idSeccion);
	        pista.setIp(ObtenerIpUtil.obtenerIp(httpRequest));

	        pistaMicroservicio.guardarPista(pista);

	        return true;
	    } catch (Exception e) {
	        throw new PistaException(ErroresEnum.ERROR_AL_PISTA_DESCONOCIDO, e);
	    }
	}


	private void processModelo(StringBuilder s, Object modelo) {
	    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	    for (Field field : modelo.getClass().getDeclaredFields()) {
	        if (field.isAnnotationPresent(JsonIgnore.class)) {
	            continue;
	        }
	        // Solución al uso inseguro de setAccessible
	        if (field.trySetAccessible()) {
	            try {
	                Object valor = field.get(modelo);
	                appendFieldValue(s, field, valor, dateFormatter);
	            } catch (IllegalAccessException e) {
	                s.append(field.getName()).append(": [ACCESS DENIED]| ");
	            } catch (Exception e) {
	                throw new PistaException(ErroresEnum.ERROR_AL_PISTA_DESCONOCIDO, e);
	            }
	        } else {
	            s.append(field.getName()).append(": [ACCESS DENIED]| ");
	        }
	    }

	    if (s.length() > 2) {
	        s.setLength(s.length() - 2);
	    }
	}

	private void appendFieldValue(StringBuilder s, Field field, Object valor, DateTimeFormatter dateFormatter) throws NoSuchFieldException, IllegalAccessException {
	    if (valor == null) {
	        s.append(field.getName()).append(": null| ");
	    } else if (valor instanceof String || valor instanceof Number || valor instanceof Boolean || valor instanceof Enum) {
	        s.append(field.getName()).append(": ").append(valor).append("| ");
	    } else if (valor instanceof TemporalAccessor temporalValue) {
	        appendTemporalValue(s, field, temporalValue, dateFormatter);
	    } else {
	        agregarObjetoComplejo(field, valor, s);
	    }
	}

	private void appendTemporalValue(StringBuilder s, Field field, TemporalAccessor temporalValue, DateTimeFormatter dateFormatter) {
	    if (temporalValue instanceof LocalDate) {
	        DateTimeFormatter dateOnlyFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        s.append(field.getName()).append(": ").append(dateOnlyFormatter.format(temporalValue)).append("| ");
	    } else if (temporalValue instanceof LocalDateTime || temporalValue instanceof OffsetDateTime || temporalValue instanceof ZonedDateTime) {
	        s.append(field.getName()).append(": ").append(dateFormatter.format(temporalValue)).append("| ");
	    } else {
	        s.append(field.getName()).append(": Unsupported temporal type| ");
	    }
	}

	private void setPistaMetadata(PistaModel pista, Integer idModuloPista, Integer idTipoMovPista, Integer idSeccion) {
	    pista.setModuloPistaModel(new CatModuloPistaModel(idModuloPista));
	    pista.setTipoMovPistaModel(new CatTipoMovPistaModel(idTipoMovPista));
	    pista.setSeccionPistaModel(new CatSeccionPistaModel(idSeccion));
	}

	@SuppressWarnings("unused")
	private void handleException(Integer idTipoMovPista, Exception e) {
	    if (idTipoMovPista == TipoMovPista.INSERTA_REGISTRO.getId() || idTipoMovPista == TipoMovPista.ACTUALIZA_REGISTRO.getId()) {
	        throw new PistaException(ErroresEnum.ERROR_AL_GUARDAR_LA_PISTA, e);
	    } else if (idTipoMovPista == TipoMovPista.CONSULTA_REGISTRO.getId()) {
	        throw new PistaException(ErroresEnum.ERROR_AL_CONSULTAR_LA_PISTA, e);
	    } else if (idTipoMovPista == TipoMovPista.IMPRIME_REGISTRO.getId()) {
	        throw new PistaException(ErroresEnum.ERROR_AL_IMPRIMIR_LA_PISTA, e);
	    }
	    throw new PistaException(ErroresEnum.ERROR_AL_PISTA_DESCONOCIDO, e);
	}


	private void agregarObjetoComplejo(Field field, Object valor, StringBuilder s) throws NoSuchFieldException, IllegalAccessException {
	    if (valor instanceof DictamenId) {
	        Field idField = valor.getClass().getDeclaredField("idDictamen");
	        s.append("idDictamen: ").append(idField.get(valor)).append("| ");
	    } else {
	        s.append(field.getName()).append(": ").append(valor.getClass().getSimpleName());
	        Optional<Field> idField = Arrays.stream(valor.getClass().getDeclaredFields())
	                .filter(f -> f.getName().equalsIgnoreCase("id"))
	                .findFirst();

	        if (idField.isPresent()) {
	            Field id = idField.get();
	            s.append(" (ID: ").append(id.get(valor)).append(")| ");
	        } else {
	            s.append("| ");
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
			} else if (idTipoMovPista == TipoMovPista.BORRA_REGISTRO.getId()) {
				throw new PistaException(ErroresEnum.ERROR_AL_BORRAR_LA_PISTA, e);
			}
			throw new PistaException(ErroresEnum.ERROR_AL_PISTA_DESCONOCIDO, e);
		}
	}





}
