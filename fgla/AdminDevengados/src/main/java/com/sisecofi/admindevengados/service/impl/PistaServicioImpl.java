package com.sisecofi.admindevengados.service.impl;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.admindevengados.microservicio.PistaMicroservicio;
import com.sisecofi.admindevengados.service.PistaService;
import com.sisecofi.admindevengados.util.exception.PistaException;
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

	public boolean guardarPista(Integer idModuloPista, Integer idTipoMovPista, Integer idSeccion, String movimiento, Optional<Object> obj) {
	    try {
	        PistaModel pista = new PistaModel();
	        StringBuilder detalleMovimiento = new StringBuilder();

	        if (obj.isPresent()) {
	            procesarObjetoParaMovimiento(obj.get(), detalleMovimiento);
	        } else {
	            pista.setMovimiento(movimiento);
	        }

	        if (detalleMovimiento.length() > 0) {
	        	String detalleLimpio = detalleMovimiento.toString().replaceAll("log: (Slf4jLogger|Logger)\\| ", "");
	            pista.setMovimiento(movimiento + detalleLimpio);

	        }

	        pista.setModuloPistaModel(new CatModuloPistaModel(idModuloPista));
	        pista.setTipoMovPistaModel(new CatTipoMovPistaModel(idTipoMovPista));
	        pista.setSeccionPistaModel(new CatSeccionPistaModel(idSeccion));
	        pista.setIp(ObtenerIpUtil.obtenerIp(httpRequest));

	        pistaMicroservicio.guardarPista(pista);
	        return true;

	    } catch (Exception e) {
	        throw manejarExcepcionPista(idTipoMovPista, e);
	    }
	}

	private void procesarObjetoParaMovimiento(Object modelo, StringBuilder detalleMovimiento) {
	    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    Class<?> currentClass = modelo.getClass();

	    while (currentClass != null) {
	        for (Field field : currentClass.getDeclaredFields()) {
	            if (field.isAnnotationPresent(JsonIgnore.class)) {
	                continue;
	            }
	            procesarCampo(field, modelo, detalleMovimiento, dateFormatter);
	        }
	        currentClass = currentClass.getSuperclass();
	    }
	}

	private void procesarCampo(Field field, Object modelo, StringBuilder detalleMovimiento, DateTimeFormatter dateFormatter) {
	    try {
	        if (field.trySetAccessible()) { 
	            Object valor = field.get(modelo);
	            if (valor == null) {
	                detalleMovimiento.append(field.getName()).append(": null| ");
	            } else if (valor instanceof String || valor instanceof Number || valor instanceof Boolean || valor instanceof Enum) {
	                detalleMovimiento.append(field.getName()).append(": ").append(valor).append("| ");
	            } else if (valor instanceof TemporalAccessor temporalValue) {
	                detalleMovimiento.append(field.getName()).append(": ").append(dateFormatter.format(temporalValue)).append("| ");
	            } else {
	                agregarObjetoComplejo(field, valor, detalleMovimiento);
	            }
	        } else {
	            detalleMovimiento.append(field.getName()).append(": [ACCESS DENIED]| ");
	        }
	    } catch (Exception e) {
	        throw new PistaException(ErroresEnum.ERROR_AL_PISTA_DESCONOCIDO, e);
	    }
	}

	private PistaException manejarExcepcionPista(Integer idTipoMovPista, Exception e) {
	    if (idTipoMovPista == TipoMovPista.INSERTA_REGISTRO.getId()
	            || idTipoMovPista == TipoMovPista.ACTUALIZA_REGISTRO.getId()) {
	        return new PistaException(ErroresEnum.ERROR_AL_GUARDAR_LA_PISTA, e);
	    } else if (idTipoMovPista == TipoMovPista.CONSULTA_REGISTRO.getId()) {
	        return new PistaException(ErroresEnum.ERROR_AL_CONSULTAR_LA_PISTA, e);
	    } else if (idTipoMovPista == TipoMovPista.IMPRIME_REGISTRO.getId()) {
	        return new PistaException(ErroresEnum.ERROR_AL_IMPRIMIR_LA_PISTA, e);
	    }
	    return new PistaException(ErroresEnum.ERROR_AL_PISTA_DESCONOCIDO, e);
	}

	private void agregarObjetoComplejo(Field field, Object valor, StringBuilder detalleMovimiento)
	        throws NoSuchFieldException, IllegalAccessException {
	    if (valor instanceof DictamenId) {
	        Field idField = valor.getClass().getDeclaredField("idDictamen");
	        if (idField.trySetAccessible()) {
	            detalleMovimiento.append("idDictamen: ").append(idField.get(valor)).append("| ");
	        } else {
	            detalleMovimiento.append("idDictamen: [ACCESS DENIED]| ");
	        }
	    } else {
	        detalleMovimiento.append(field.getName()).append(": ").append(valor.getClass().getSimpleName());
	        Optional<Field> idField = Arrays.stream(valor.getClass().getDeclaredFields())
	                .filter(f -> f.getName().equalsIgnoreCase("id"))
	                .findFirst();

	        if (idField.isPresent()) {
	            Field id = idField.get();
	            if (id.trySetAccessible()) {
	                detalleMovimiento.append(" (ID: ").append(id.get(valor)).append(")| ");
	            } else {
	                detalleMovimiento.append(" (ID: [ACCESS DENIED])| ");
	            }
	        } else {
	            detalleMovimiento.append("| ");
	        }
	    }
	}



}
