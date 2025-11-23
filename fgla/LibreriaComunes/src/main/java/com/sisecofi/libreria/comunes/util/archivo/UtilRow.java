package com.sisecofi.libreria.comunes.util.archivo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Slf4j
public final class UtilRow {

	private UtilRow() {

	}
	
	public static BigDecimal validarSoloBigDecimal(int campoNumero, Row row) {
		try {
			if (row.getCell(campoNumero).getCellType() == CellType.NUMERIC) {
				return BigDecimal.valueOf((row.getCell(campoNumero)).getNumericCellValue());
			}
			return null;
		} catch (IllegalStateException e) {
	        log.debug("Tipo de celda inválido para validarSoloBigDecimal en columna {}: {}", campoNumero);
	    } catch (NullPointerException e) {
	        log.debug("Celda nula para validarSoloBigDecimal en columna {}: {}", campoNumero);
	    } catch (Exception e) {
	        log.error("Error inesperado en validarSoloBigDecimal en columna {}: {}", campoNumero);
	    }
	    return null;
	}

	public static String validarNumber(int campoNumero, Row row) {
		try {
			if ((row.getCell(campoNumero)) != null) {
				return String.valueOf((row.getCell(campoNumero)).getNumericCellValue());
			}
		} catch (IllegalStateException e) {
	        log.debug("Tipo de celda inválido para validarNumber en columna {}: {}", campoNumero );
	    } catch (NullPointerException e) {
	        log.debug("Celda nula para validarNumber en columna {}: {}", campoNumero );
	    } catch (Exception e) {
	        log.error("Error inesperado en validarNumber en columna {}: {}", campoNumero );
	    }
	    return null;
	}

	public static Integer validarSoloNumero(int campoNumero, Row row) {
		try {
			if (row.getCell(campoNumero).getCellType() == CellType.NUMERIC) {
				return (int) (row.getCell(campoNumero)).getNumericCellValue();
			}
			return null;
		} catch (IllegalStateException e) {
	        log.debug("Tipo de celda inválido para validarSoloNumero en columna {}: {}", campoNumero );
	    } catch (NullPointerException e) {
	        log.debug("Celda nula para validarSoloNumero en columna {}: {}", campoNumero );
	    } catch (Exception e) {
	        log.error("Error inesperado en validarSoloNumero en columna {}: {}", campoNumero );
	    }
	    return null;
	}

	public static Integer validarRowNumber(int campoNumero, Row row) {
		try {
			if ((row.getCell(campoNumero)) != null) {
				return (int) (row.getCell(campoNumero)).getNumericCellValue();
			}
		} catch (IllegalStateException e) {
	        log.debug("Tipo de celda inválido en validarRowNumber para columna {}: {}", campoNumero );
	        return textNumero(campoNumero, row); // Si ocurre un error, intenta usar `textNumero`.
	    } catch (NullPointerException e) {
	        log.debug("Celda nula en validarRowNumber para columna {}: {}", campoNumero );
	    }
	    return null;
	}

	public static Integer textNumero(int campoNumero, Row row) {
		try {
			// Obtener la celda en la posición campoNumero
			Cell cell = row.getCell(campoNumero, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
	
			// Verificar si la celda es nula
			if (cell == null) {
				return null;
			}
	
			// Verificar el tipo de celda y obtener el valor numérico
			switch (cell.getCellType()) {
				case STRING:
					String cellValue = cell.getStringCellValue().trim();
					return cellValue.isEmpty() ? null : Integer.parseInt(cellValue);
				case NUMERIC:
					// Si es numérico, devolver el valor como entero
					return (int) cell.getNumericCellValue();
				default:
					return null;
			}
		} catch (NumberFormatException e) {
	        log.debug("Error al convertir valor STRING a número en columna {}: {}", campoNumero );
	    } catch (IllegalStateException e) {
	        log.debug("Tipo de celda inválido en textNumero para columna {}: {}", campoNumero );
	    } catch (Exception e) {
	        log.error("Error inesperado en textNumero para columna {}: {}", campoNumero );
	    }
	    return null;
	}

	

	public static String validarRow(int campoNumero, Row row) {
		try {
			if (row.getCell(campoNumero) != null) {
				Cell cell = row.getCell(campoNumero);
				switch (cell.getCellType()) {
					case STRING:
						return cell.getStringCellValue().trim();
					case NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
							return dateFormat.format(cell.getDateCellValue());
						} else {
							 return String.valueOf((int) cell.getNumericCellValue()).trim();
						}
					case BOOLEAN:
						return cell.getBooleanCellValue() ? "si" : "no";
					default:
						return "";
				}
			}
			return "";
		} catch (IllegalStateException e) {
	        log.debug("Tipo de celda inválido en validarRow para columna {}: {}", campoNumero );
	    } catch (NullPointerException e) {
	        log.debug("Celda nula en validarRow para columna {}: {}", campoNumero );
	    } catch (Exception e) {
	        log.error("Error inesperado en validarRow para columna {}: {}", campoNumero );
	    }
	    return "";
	}


	public static String validarRowStringNumber(int campoNumero, Row row) {
		try {
			if ((row.getCell(campoNumero)) != null) {
				return (row.getCell(campoNumero)).getStringCellValue().trim();
			}
			return "";
		} catch (IllegalStateException e) {
	        log.debug("Tipo de celda inválido en validarRowStringNumber para columna {}: {}", campoNumero );
	        return String.valueOf(validarRowNumber(campoNumero, row)); 
	    } catch (NullPointerException e) {
	        log.debug("Celda nula en validarRowStringNumber para columna {}: {}", campoNumero );
	    }
	    return "";
	}

	public static LocalDateTime obtenerFecha(Row row, int rownum) {
		try {
			return (row.getCell(rownum)).getLocalDateTimeCellValue();
		} catch (IllegalStateException e) {
	        log.debug("Error al obtener la fecha en obtenerFecha para fila {} y columna {}: {}", row.getRowNum(), rownum );
	    } catch (NullPointerException e) {
	        log.debug("Celda nula en obtenerFecha para fila {} y columna {}: {}", row.getRowNum(), rownum );
	    }
	    String fecha = validarRow(rownum, row);
	    return validarFecha(fecha);
	}

	public static LocalDateTime validarFecha(String fecha) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a");
			String fechaNueva = fecha.replace("a.m", "AM").replace("p.m", "PM");
			return LocalDateTime.parse(fechaNueva, formatter);
		} catch (DateTimeParseException e) {
	        log.debug("Error al parsear la fecha '{}': {}", fecha );
	    } catch (NullPointerException e) {
	        log.debug("Fecha nula al intentar parsear: {}" );
	    }
	    return null;
	}

	public static Boolean validarBoolean(int campoNumero, Row row) {
		try {
			if ((row.getCell(campoNumero)) != null) {
				return (row.getCell(campoNumero)).getBooleanCellValue();
			}
		} catch (IllegalStateException e) {
	        log.debug("Tipo de celda inválido en validarBoolean para columna {}: {}", campoNumero );
	    } catch (NullPointerException e) {
	        log.debug("Celda nula en validarBoolean para columna {}: {}", campoNumero );
	    }
	    return null;
	}
}
