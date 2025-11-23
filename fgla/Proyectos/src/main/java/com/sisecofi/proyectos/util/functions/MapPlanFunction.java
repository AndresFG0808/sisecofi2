package com.sisecofi.proyectos.util.functions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Set;
import java.util.function.Function;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import com.sisecofi.proyectos.dto.adminplantrabajo.DataDto;
import com.sisecofi.proyectos.util.enums.ErroresPlanTrabajoEnum;
import com.sisecofi.proyectos.util.exception.PlanTrabajoException;

import com.sisecofi.libreria.comunes.util.archivo.UtilRow;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Slf4j
public class MapPlanFunction implements Function<Row, DataDto> {

	private final Set<Integer> idTareaSet;
	public final boolean nivelEsquemaCeroUnico;
	private boolean nivelEsquemaCeroEncontrado = false;

	public MapPlanFunction(Set<Integer> idTareaSet, boolean nivelEsquemaCeroUnico) {
		this.idTareaSet = idTareaSet;
		this.nivelEsquemaCeroUnico = nivelEsquemaCeroUnico;

	}

	@Override
	public DataDto apply(Row t) {
		DataDto map = new DataDto();

		map.setIdTarea(validarIdTarea(1, t));
		validarIdTareaUnico(map.getIdTarea());
		map.setNivelEsquema(validarNivelEsquemaVacio(2, t));
		validarNivelEsquema(map.getNivelEsquema());
		map.setNombreTarea(validarCeldaTexto(3, t));
		map.setActivo(validarCeldaBoolean(t));
		map.setDuracionPlaneada(validarDuracion(obtenerValorCelda(t.getCell(5))));
		map.setFechaInicioPlaneada(validarCeldaFechaInicioPlaneada(6, t));
		map.setFechaFinPlaneada(validarCeldaFechaFinPlaneada(7, t));
		map.setDuracionReal(validarDuracionReal(obtenerValorCelda(t.getCell(8))));
		map.setFechaInicioReal(validarCeldaFechaInicioReal(9, t));
		map.setFechaFinReal((validarCeldaFechaFinReal(10, t)));
		map.setPredecesora(UtilRow.textNumero(11, t));
		map.setPlaneado(validarPorcentajePlaneado(t));
		map.setCompletado(validarPorcentajeCompletado(t));

		return map;
	}

	private Integer validarIdTarea(int cellIndex, Row row) {
		try {
			Cell cell = row.getCell(cellIndex);
			if (cell == null) {
				throw new PlanTrabajoException(ErroresPlanTrabajoEnum.CAMPOS_OBLIGATORIOS);
			}

			if (cell.getCellType() == CellType.NUMERIC) {
				return (int) cell.getNumericCellValue();
			} else {
				throw new PlanTrabajoException(ErroresPlanTrabajoEnum.CAMPOS_OBLIGATORIOS);
			}
		} catch (Exception e) {
			throw new PlanTrabajoException(ErroresPlanTrabajoEnum.CAMPOS_OBLIGATORIOS);
		}
	}

	private Double validarDuracionReal(String dur) {
		if (dur == null || dur.isEmpty()) {
			return null;
		}

		try {
			return Double.parseDouble(dur);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private void validarIdTareaUnico(Integer idTarea) {
		if (idTarea != null && idTareaSet.contains(idTarea)) {
			throw new PlanTrabajoException(ErroresPlanTrabajoEnum.IDTAREA_UNICO);
		}
		if (idTarea != null) {
			idTareaSet.add(idTarea);
		}
	}

	private Integer validarNivelEsquemaVacio(int cellIndex, Row t) {
		Integer valor = UtilRow.validarSoloNumero(cellIndex, t);
		if (valor == null) {
			throw new PlanTrabajoException(ErroresPlanTrabajoEnum.CAMPOS_OBLIGATORIOS);
		}
		return valor;
	}

	private String validarCeldaTexto(int cellIndex, Row t) {
		String valor = UtilRow.validarRow(cellIndex, t);
		if (valor == null || valor.isEmpty()) {
			throw new PlanTrabajoException(ErroresPlanTrabajoEnum.CAMPOS_OBLIGATORIOS);
		}
		return valor;
	}

	private Boolean validarCeldaBoolean(Row t) {
		return validarBoolean(t);
	}

	private Boolean validarBoolean(Row t) {
		try {
			String valor = UtilRow.validarRow(4, t);
			if ("si".equalsIgnoreCase(valor)) {
				return true;
			} else if ("no".equalsIgnoreCase(valor)) {
				return false;
			} else {
				throw new PlanTrabajoException(ErroresPlanTrabajoEnum.VALOR_ACTIVO_INVALIDO);
			}
		} catch (Exception e) {
			throw new PlanTrabajoException(ErroresPlanTrabajoEnum.VALOR_ACTIVO_INVALIDO);
		}
	}

	private LocalDate validarCeldaFechasPlaneadas(int cellIndex, Row t, ErroresPlanTrabajoEnum error) {
		String valor = UtilRow.validarRow(cellIndex, t);
		if (valor == null || valor.isEmpty()) {
			throw new PlanTrabajoException(error);
		}
		return traformarFechaFormato(valor);
	}

	private LocalDate validarCeldaFechasReales(int cellIndex, Row t) {
		String valor = UtilRow.validarRow(cellIndex, t);
		if (valor == null || valor.isEmpty()) {
			return null;
		}
		return traformarFechaFormato(valor);
	}

	private LocalDate validarCeldaFechaInicioPlaneada(int cellIndex, Row t) {
		return validarCeldaFechasPlaneadas(cellIndex, t, ErroresPlanTrabajoEnum.CAMPOS_OBLIGATORIOS);
	}

	private LocalDate validarCeldaFechaFinPlaneada(int cellIndex, Row t) {
		return validarCeldaFechasPlaneadas(cellIndex, t, ErroresPlanTrabajoEnum.CAMPOS_OBLIGATORIOS);
	}

	private LocalDate validarCeldaFechaInicioReal(int cellIndex, Row t) {
		return validarCeldaFechasReales(cellIndex, t);
	}

	private LocalDate validarCeldaFechaFinReal(int cellIndex, Row t) {
		return validarCeldaFechasReales(cellIndex, t);
	}

	private LocalDate traformarFechaFormato(String validarRow) {
		try {
			DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
			return LocalDate.parse(validarRow, formatter1);
		} catch (DateTimeParseException e) {
			throw new PlanTrabajoException(ErroresPlanTrabajoEnum.FORMATO_FECHA_INVALIDO);
		}
	}

	private String obtenerValorCelda(Cell cell) {
		if (cell == null) {
			return null;
		}

		switch (cell.getCellType()) {
			case STRING:
				return cell.getStringCellValue().trim(); // Si es texto
			case NUMERIC:
				return String.valueOf(cell.getNumericCellValue()).trim();
			case BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue()).trim();
			default:
				return "";
		}
	}

	private Double validarDuracion(String dur) {
		if (dur == null || dur.isEmpty()) {
			throw new PlanTrabajoException(ErroresPlanTrabajoEnum.CAMPOS_OBLIGATORIOS);
		}

		try {
			return Double.parseDouble(dur);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private Integer validarPorcentaje(Row t, int columnIndex) {
		try {
			Cell cell = t.getCell(columnIndex);
			if (cell == null || cell.getCellType() == CellType.BLANK) {
				return null;
			}

			String porcentajeStr;
			if (cell.getCellType() == CellType.NUMERIC) {
				porcentajeStr = String.valueOf(cell.getNumericCellValue());
			} else {
				porcentajeStr = cell.getStringCellValue().replace("%", "").trim();
			}

			Double porcentaje = Double.parseDouble(porcentajeStr);

			// Verificar si tiene decimales
			if (porcentaje % 1 != 0) {
				throw new PlanTrabajoException(ErroresPlanTrabajoEnum.PORCENTAJES_ENTEROS);
			}
			return porcentaje.intValue();
		} catch (NumberFormatException e) {
			throw new PlanTrabajoException(ErroresPlanTrabajoEnum.PORCENTAJES_ENTEROS);
		}
	}

	private Integer validarPorcentajePlaneado(Row t) {
		return validarPorcentaje(t, 12);
	}

	private Integer validarPorcentajeCompletado(Row t) {
		return validarPorcentaje(t, 13);
	}

	private void validarNivelEsquema(Integer nivelEsquema) {
		if (nivelEsquema != null && nivelEsquema == 0 && nivelEsquemaCeroEncontrado) {
			throw new PlanTrabajoException(ErroresPlanTrabajoEnum.NIVELESQUEMA_UNICO);
		}
		nivelEsquemaCeroEncontrado = true;
	}

}
