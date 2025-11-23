package com.sisecofi.reportedocumental.service.dinamico.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.reportedocumental.service.dinamico.Mapper;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Component("mapperDinamico")
public class MapperDinamico implements Mapper {

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Override
	public void map(Object obj, BaseExcel baseExcel, Row row, int contador, Object[] t) {
		if (obj != null) {
			if (obj instanceof Timestamp timestamp) {
				// Formatear Timestamp
				LocalDateTime fecha = timestamp.toLocalDateTime();
				baseExcel.crearCelda(row, contador, fecha.format(formatter));
			} else if (obj instanceof Date date) {
				// Formatear Date
				LocalDate localDate = date.toLocalDate();
				baseExcel.crearCelda(row, contador, localDate.atStartOfDay().format(formatter));
			} else if (obj instanceof Number objC) {
				// Formatear números (Double, BigDecimal, Long)
				baseExcel.crearCelda(row, contador, formatNumber(objC));
			} else if (obj instanceof Boolean objC) {
				// Formatear Boolean
				baseExcel.crearCelda(row, contador, objC.booleanValue() ? "Activo" : "Inactivo");
			} else {
				// Formatear otros tipos de objetos
				baseExcel.crearCelda(row, contador, String.valueOf(obj));
			}
		}
	}

	private String formatNumber(Number valor) {
		DecimalFormat df;
		// Verificar si el valor tiene decimales significativos
		if (valor instanceof Double || valor instanceof BigDecimal || valor instanceof Long) {
			if (valor.doubleValue() == valor.longValue()) {
				// Si el valor es un número entero (sin decimales significativos)
				df = new DecimalFormat("#,###");
			} else {
				// Si tiene decimales significativos, mostrar con dos decimales
				df = new DecimalFormat("#,###.##");
			}
		} else {
			// Si no es un número válido, no formatear
			df = new DecimalFormat("#,###");
		}
		return df.format(valor);
	}

}
