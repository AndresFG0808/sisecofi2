package com.sisecofi.libreria.comunes.util.archivo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.sisecofi.libreria.comunes.util.enums.ErroresSistema;
import com.sisecofi.libreria.comunes.util.exception.ErrorSistemaException;

import lombok.extern.slf4j.Slf4j;

/**
 * Clase para leer un excel retoranando una lista de row
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Component
@Slf4j
public class LectorExcel {

	public static final String ROWS = "rows";
	public static final String FIRST_ROW = "firstRow";
	private static final String XLSX = "xlsx";

	/**
	 * Metodo para leer un archivo excel, regresa un mapa, en rows regresa la lista
	 * de rows y en firstRow regresa el primero row para sacar encabezados o contar
	 * filas
	 * 
	 * @param in
	 * @param extension
	 * @param hoja
	 * @return
	 */
	public Map<String, Object> leerArchivoExcel(InputStream in, String extension, int hoja) {
		long tiempoInicial = System.currentTimeMillis();
		List<Row> list = new ArrayList<>();
		Map<String, Object> mapa = new HashMap<>();
		try {
			log.debug("Archivo creado:{}", in);
			Workbook workbook = obtenerWorkBook(in, extension);
			Sheet sheet = workbook.getSheetAt(hoja);
			mapa.put(FIRST_ROW, sheet.getRow(1));
			sheet.forEach(row -> {
				if (row.getRowNum() > 1) {
					list.add(row);
				}
			});
			log.info("Tiempo de lectura lambda: {} mls Lista de row: {}", (System.currentTimeMillis() - tiempoInicial),
					list.size());
		} catch (IOException ex) {
			throw new ErrorSistemaException(ErroresSistema.ERROR_AL_LEER_ARCHIVO_EXCEL, ex);
		}
		mapa.put(ROWS, list);
		return mapa;
	}

	private Workbook obtenerWorkBook(InputStream in, String extension) throws IOException {
		if (XLSX.equalsIgnoreCase(extension.trim())) {
			return new XSSFWorkbook(in);
		} else {
			throw new ErrorSistemaException(ErroresSistema.ERROR_FORMATO_EXCEL);
		}
	}

}