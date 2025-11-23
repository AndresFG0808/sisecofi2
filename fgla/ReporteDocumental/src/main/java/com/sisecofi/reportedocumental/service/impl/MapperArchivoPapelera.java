package com.sisecofi.reportedocumental.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.reportedocumental.service.dinamico.Mapper;

/**
 * 
 * @author omartinezj
 *
 */
@Component("mapperArchivoPapelera")
public class MapperArchivoPapelera implements Mapper {

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy");

	@Override
	public void map(Object obj, BaseExcel baseExcel, Row row, int contador, Object[] t) {
		if (obj != null && contador >= 0 && contador < 9) {
			if (obj instanceof LocalDateTime fecha) {
				baseExcel.crearCelda(row, contador, fecha.format(formatter));
			} else if (obj instanceof Boolean) {
				validarBoolean(obj, baseExcel, row, contador);
			} else {
				baseExcel.crearCelda(row, contador, String.valueOf(obj));
			}
		}
	}
	
	private void validarBoolean(Object obj, BaseExcel baseExcel, Row row, int contador) {
		boolean valor = (Boolean) obj;
		if (contador == 6) {
			baseExcel.crearCelda(row, contador - 1, valor ? "Si" : "No");
		} else {
			baseExcel.crearCelda(row, contador, valor ? "Activo" : "Inactivo");
		}
	}

}
