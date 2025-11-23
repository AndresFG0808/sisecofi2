package com.sisecofi.reportedocumental.service.controldocumental.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.reportedocumental.service.dinamico.Mapper;
import com.sisecofi.reportedocumental.util.Constantes;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Component("mapperControlDocumental")
public class MapperControlDocumental implements Mapper {

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Override
	public void map(Object obj, BaseExcel baseExcel, Row row, int contador, Object[] t) {
		if (obj != null && contador > 0 && contador < 11) {
			if (obj instanceof LocalDateTime fecha) {
				baseExcel.crearCelda(row, contador - 1, fecha.format(formatter));
			} else if (obj instanceof Boolean) {
				validarBoolean(obj, baseExcel, row, contador, t);
			} else {
				baseExcel.crearCelda(row, contador - 1, String.valueOf(obj));
			}
		}
	}

	private void validarBoolean(Object obj, BaseExcel baseExcel, Row row, int contador, Object[] t) {
		boolean valor = (Boolean) obj;
		if (contador == 6) {
			baseExcel.crearCelda(row, contador - 1, valor ? "Si" : "No");
		} else if (contador == 8) {
			boolean cargado = Boolean.parseBoolean(String.valueOf(t[11]));
			boolean noAplica = Boolean.parseBoolean(String.valueOf(t[7]));
			String status = "";
			if (cargado && !noAplica) {
				status = Constantes.CARGADO;
			} else if (!cargado && !noAplica) {
				status = Constantes.PENDIENTE;
			} else {
				status = Constantes.NO_APLICA;
			}
			baseExcel.crearCelda(row, contador - 1, status);
		} else {
			baseExcel.crearCelda(row, contador - 1, valor ? "True" : "False");
		}
	}

}
