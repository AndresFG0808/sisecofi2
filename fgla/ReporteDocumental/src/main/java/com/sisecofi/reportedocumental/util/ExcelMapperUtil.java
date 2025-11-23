package com.sisecofi.reportedocumental.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;

import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;

public class ExcelMapperUtil {	

	private ExcelMapperUtil() {
	}

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private static final Map<String, Integer> MESES = Map.ofEntries(
	    Map.entry("enero", 1), Map.entry("febrero", 2), Map.entry("marzo", 3),
	    Map.entry("abril", 4), Map.entry("mayo", 5), Map.entry("junio", 6),
	    Map.entry("julio", 7), Map.entry("agosto", 8), Map.entry("septiembre", 9),
	    Map.entry("octubre", 10), Map.entry("noviembre", 11), Map.entry("diciembre", 12)
	);

	public static void map(Object obj, BaseExcel baseExcel, Row row, int contador, boolean convertirFechas) {
	    if (obj != null) {
	        if (obj instanceof LocalDateTime objC) {
	            baseExcel.crearCelda(row, contador, (objC).format(FORMATTER));
	        } else if (obj instanceof Boolean) {
	            baseExcel.crearCelda(row, contador, Boolean.TRUE.equals(obj) ? "Activo" : "Inactivo");
	        } else {
	            String value = convertirFechas ? formatOtherObject(obj) : String.valueOf(obj);
	            baseExcel.crearCelda(row, contador, value);
	        }
	    }
	}

	private static String formatOtherObject(Object obj) {
	    String regex = "^(enero|febrero|marzo|abril|mayo|junio|julio|agosto|septiembre|octubre|noviembre|diciembre)/\\d{4}$";
	    String value = String.valueOf(obj).toLowerCase();

	    if (value.matches(regex)) {
	        return convertMonthYearToDate(value);
	    }
	    return value;
	}

	private static String convertMonthYearToDate(String value) {
	    String[] parts = value.split("/");
	    Integer monthNumber = MESES.get(parts[0]);
	    int year = Integer.parseInt(parts[1]);

	    if (monthNumber != null) {
	        return String.format("01/%02d/%d", monthNumber, year);
	    }
	    return value;
	}
}