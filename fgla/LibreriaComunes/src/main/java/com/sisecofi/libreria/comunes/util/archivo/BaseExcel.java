package com.sisecofi.libreria.comunes.util.archivo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lombok.extern.slf4j.Slf4j;

/**
 * Clase para extender mediante un Consumer y poder generar un reporte en excel
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Slf4j
public class BaseExcel {

	protected Sheet sheet;
	protected XSSFWorkbook workbook;
	protected FileOutputStream out;
	protected File file;
	protected int numeroRenglon;
	protected byte[] bytes;

	/**
	 * Crear una celda en la posicion indicada con el valor seteado
	 * 
	 * @param row
	 * @param i
	 * @param valor
	 */

	public void crearCelda(Row row, int i, Object valor) {
		if (valor != null && !valor.equals("")) {
			Cell cell = row.createCell(i);

			try {
				if (valor instanceof Integer) {
					cell.setCellValue(Double.parseDouble(String.valueOf(valor)));
				} else if (valor instanceof LocalDate localDate) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					String formattedDate = localDate.format(formatter);
					cell.setCellValue(formattedDate);
				} else if (valor instanceof LocalDateTime localDateTime) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					String formattedDateTime = localDateTime.toLocalDate().format(formatter);
					cell.setCellValue(formattedDateTime);
				} else {
					cell.setCellValue(String.valueOf(valor));
				}
			} catch (NumberFormatException e) {
				log.error("Error al convertir valor a número en columna {}: {}", i);
			} catch (IllegalArgumentException e) {
				log.error("Formato inválido para el valor en columna {}: {}", i);
			} catch (Exception e) {
				log.error("Error inesperado al crear celda en columna {}: {}", i);
			}
		}
	}

	/**
	 * Inicializa la pestaña del excel con el nombre
	 * 
	 * @param nombreArchivo
	 */

	public void inializar(String nombre) {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet(nombre);
		numeroRenglon = 1;
	}

	/**
	 * Te regresa un arreglo de bytes para poder exponerlo hacia un API
	 * 
	 * @return
	 */
	public byte[] cerrarBytes() {
		try {
			autoAjustarColumnas();
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			workbook.write(arrayOutputStream);
			bytes = arrayOutputStream.toByteArray();
			arrayOutputStream.close();
			workbook.close();
		} catch (IOException e) {
			log.error("Error al escribir el archivo:");
		}
		return bytes;
	}

	public void autoAjustarColumnas() {
		int lastColNum = sheet.getRow(0).getLastCellNum(); // Obtener el número de columnas en la primera fila
		for (int colIndex = 0; colIndex < lastColNum; colIndex++) {
			sheet.autoSizeColumn(colIndex);
		}
	}

	public byte[] cerrarArchivoPruebaLocal(String rutaLocal) {
		try {
			file = new File(rutaLocal);
			out = new FileOutputStream(file);
			workbook.write(out);
			bytes = FileUtils.readFileToByteArray(file);
			out.close();
			workbook.close();
		} catch (IOException e) {
			log.error("Error al escribir el archivo");
			e.printStackTrace();
		}
		return bytes;
	}
}
