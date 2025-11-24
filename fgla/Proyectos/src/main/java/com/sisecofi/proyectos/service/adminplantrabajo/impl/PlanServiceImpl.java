package com.sisecofi.proyectos.service.adminplantrabajo.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sisecofi.proyectos.dto.adminplantrabajo.DataDto;
import com.sisecofi.proyectos.dto.adminplantrabajo.ListaTareas;
import com.sisecofi.proyectos.model.adminplantrabajo.TareaPlanTrabajoModel;
import com.sisecofi.proyectos.repository.adminplantrabajo.PlanProyectoRepository;
import com.sisecofi.proyectos.repository.adminplantrabajo.PlanTrabajoRepository;
import com.sisecofi.proyectos.repository.adminplantrabajo.TareaPlanTrabajoRepository;
import com.sisecofi.proyectos.service.PistaService;
import com.sisecofi.proyectos.service.adminplantrabajo.PlanService;
import com.sisecofi.proyectos.util.enums.ErroresPlanTrabajoEnum;
import com.sisecofi.proyectos.util.exception.PlanTrabajoException;
import com.sisecofi.proyectos.util.functions.MapPlanFunction;

import jakarta.transaction.Transactional;

import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import com.sisecofi.libreria.comunes.model.proyectos.adminplantrabajo.PlanTrabajoModel;
import com.sisecofi.libreria.comunes.util.archivo.LectorExcel;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Service
@Slf4j
@AllArgsConstructor
public class PlanServiceImpl implements PlanService {

	private final LectorExcel excel;
	private final PlanProyectoRepository proyectoRepository;
	private final TareaPlanTrabajoRepository tareaPlanTrabajoRepository;
	private final PlanTrabajoRepository planTrabajoRepository;
	private final PistaService pistaService;

	// cargar plan con la jerarquía
	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public List<ListaTareas> cargarPlan(MultipartFile file, Long idProyecto) throws IOException {
		String filename = file.getOriginalFilename();

		// Validar extensión del archivo
		if (filename == null || !filename.endsWith(".xlsx")) {
			throw new PlanTrabajoException(ErroresPlanTrabajoEnum.FORMATO_INVALIDO);
		}

		// Leer archivo Excel y obtener datos
		Map<String, Object> map;
		List<Row> list;
		map = excel.leerArchivoExcel(file.getInputStream(), "xlsx", 1);
		list = (List<Row>) map.get(LectorExcel.ROWS);

		// Validar columnas del archivo Excel utilizando la primera fila
		if (list != null && !list.isEmpty()) {
			validarColumnasExcel(list.get(1).getSheet());
		} else {
			throw new PlanTrabajoException(ErroresPlanTrabajoEnum.ARCHIVO_VACIO);
		}

		Set<Integer> idTareaSet = new HashSet<>();
		List<DataDto> models = list.stream().map(new MapPlanFunction(idTareaSet, true))
				.filter(r -> r.getIdTarea() != null).toList();

		validarTareaHijas(models);

		List<DataDto> copiaTareas = new ArrayList<>(models);
		for (DataDto data : new ArrayList<>(models)) {
			if (data.getNivelEsquema() == 0) {
				validarFechasPorNivelEsquema(copiaTareas);
			}
		}

		Collections.sort(copiaTareas);

		// Construir la jerarquía de tareas
		List<ListaTareas> jerarquiaTareas = construirJerarquia(copiaTareas);

		// Guardar temporalmente las tareas
		guardarTareasOriginales(new ArrayList<>(copiaTareas), idProyecto);

		return jerarquiaTareas;

	}

	// Jerarquía para reponse de front
	private List<ListaTareas> construirJerarquia(List<DataDto> models) {
		List<ListaTareas> resultadoJerarquia = new ArrayList<>();
		Deque<ListaTareas> stack = new ArrayDeque<>();

		// Se Ordenan las tareas por idTarea
		models.sort(Comparator.comparing(DataDto::getIdTarea));

		for (DataDto data : models) {
			ListaTareas tareaActual = new ListaTareas();
			tareaActual.setIdTarea(data.getIdTarea());
			tareaActual.setNivelEsquema(data.getNivelEsquema());
			tareaActual.setNombreTarea(data.getNombreTarea());
			tareaActual.setActivo(data.isActivo());
			tareaActual.setDuracionPlaneada(data.getDuracionPlaneada());
			tareaActual.setFechaInicioPlaneada(data.getFechaInicioPlaneada());
			tareaActual.setFechaFinPlaneada(data.getFechaFinPlaneada());
			tareaActual.setDuracionReal(data.getDuracionReal());
			tareaActual.setFechaInicioReal(data.getFechaInicioReal());
			tareaActual.setFechaFinReal(data.getFechaFinReal());
			tareaActual.setPredecesora(data.getPredecesora());
			tareaActual.setPlaneado(data.getPlaneado());
			tareaActual.setCompletado(data.getCompletado());

			if (stack.isEmpty()) {
				resultadoJerarquia.add(tareaActual);
				stack.push(tareaActual);
			} else {

				while (!stack.isEmpty() && stack.peek().getNivelEsquema() >= tareaActual.getNivelEsquema()) {
					stack.pop();
				}

				if (!stack.isEmpty()) {
					stack.peek().getSubRows().add(tareaActual);
				} else {

					resultadoJerarquia.add(tareaActual);
				}

				stack.push(tareaActual);
			}
		}

		return resultadoJerarquia;
	}

	// validacion de tareas hijas
	private void validarTareaHijas(List<DataDto> models) {
		boolean padrePresente = false;

		for (int i = 0; i < models.size(); i++) {
			DataDto data = models.get(i);
			Integer nivelEsquema = data.getNivelEsquema();

			if (nivelEsquema == 1) {
				padrePresente = true;
			}

			if (nivelEsquema > 1 && !padrePresente) {
				throw new PlanTrabajoException(ErroresPlanTrabajoEnum.TAREAHIJA_VALIDACION);
			}

			if (nivelEsquema == 0) {
				padrePresente = false;
			}
		}
	}

	private void validarFechasPorNivelEsquema(List<DataDto> tareas) {
		// Ordenar las tareas por ID
		tareas.sort(Comparator.comparing(DataDto::getIdTarea));

		for (int i = 0; i < tareas.size(); i++) {
			DataDto tareaPadre = tareas.get(i);
			Integer nivelEsquemaPadre = tareaPadre.getNivelEsquema();

			if (nivelEsquemaPadre == 1) {
				ajustarFechasPadre(tareaPadre, tareas, i);
			}
		}
	}

	private void ajustarFechasPadre(DataDto tareaPadre, List<DataDto> tareas, int indexPadre) {
		Integer nivelEsquemaPadre = tareaPadre.getNivelEsquema();
		LocalDate fechaInicioPadre = tareaPadre.getFechaInicioPlaneada();
		LocalDate fechaFinPadre = tareaPadre.getFechaFinPlaneada();
		LocalDate fechaInicioMinHija = fechaInicioPadre;
		LocalDate fechaFinMaxHija = fechaFinPadre;

		for (int j = indexPadre + 1; j < tareas.size(); j++) {
			DataDto tareaHija = tareas.get(j);
			Integer nivelEsquemaHija = tareaHija.getNivelEsquema();

			if (nivelEsquemaHija > nivelEsquemaPadre) {
				fechaInicioMinHija = obtenerFechaInicioMinima(fechaInicioMinHija, tareaHija.getFechaInicioPlaneada());
				fechaFinMaxHija = obtenerFechaFinMaxima(fechaFinMaxHija, tareaHija.getFechaFinPlaneada());
			} else {
				break;
			}
		}

		validarFechas(fechaInicioPadre, fechaFinPadre, fechaInicioMinHija, fechaFinMaxHija);

		tareaPadre.setFechaInicioPlaneada(fechaInicioMinHija);
		tareaPadre.setFechaFinPlaneada(fechaFinMaxHija);

	}

	private void validarFechas(LocalDate fechaInicioPadre, LocalDate fechaFinPadre,
			LocalDate fechaInicioMinHija, LocalDate fechaFinMaxHija) {
		if (fechaInicioPadre == null || fechaInicioMinHija == null) {
			throw new PlanTrabajoException(ErroresPlanTrabajoEnum.ERROR_GENERICO);
		}

		if (fechaInicioPadre.isAfter(fechaInicioMinHija)) {
			throw new PlanTrabajoException(ErroresPlanTrabajoEnum.FECHA_PADRE_MENOR_QUE_HIJO);
		}

		if (fechaFinPadre == null || fechaFinMaxHija == null) {
			throw new PlanTrabajoException(ErroresPlanTrabajoEnum.ERROR_GENERICO);
		}

		if (fechaFinPadre.isBefore(fechaFinMaxHija)) {
			throw new PlanTrabajoException(ErroresPlanTrabajoEnum.ERROR_TAREA_PADRE_FECHA_FIN);
		}
	}

	private LocalDate obtenerFechaInicioMinima(LocalDate fechaInicioMin, LocalDate fechaInicioHija) {
		if (fechaInicioHija != null && (fechaInicioMin == null || fechaInicioHija.isBefore(fechaInicioMin))) {
			return fechaInicioHija;
		}
		return fechaInicioMin;
	}

	private LocalDate obtenerFechaFinMaxima(LocalDate fechaFinMax, LocalDate fechaFinHija) {
		if (fechaFinHija != null && (fechaFinMax == null || fechaFinHija.isAfter(fechaFinMax))) {
			return fechaFinHija;
		}
		return fechaFinMax;
	}

	/*
	 * Se modifica el plan y todas las tareas se almacenan en sus tablas originales,
	 * ya no existe temporales
	 * modificación propuesta por Rodolfo 08/04/2025
	 */

	@Transactional
	private void guardarTareasOriginales(List<DataDto> models, Long idProyecto) {

		PlanTrabajoModel planTrabajoOriginal = planTrabajoRepository
				.findByProyectoModelIdProyecto(idProyecto).orElseGet(() -> crearNuevoPlanTrabajo(idProyecto));

		// **Eliminar tareas existentes del proyecto**
		tareaPlanTrabajoRepository.deleteByPlanTrabajoModel(planTrabajoOriginal);

		List<TareaPlanTrabajoModel> tareasOriginales = models.stream()
				.map(dataDto -> conversionDtoAmodel(dataDto, planTrabajoOriginal)).toList();

		// Guardar todas las tareas temporales en la base de datos
		tareaPlanTrabajoRepository.saveAll(tareasOriginales);

		// pistas de Auditoria

		StringBuilder builder = new StringBuilder();
		builder.append("Id proyecto: ").append(idProyecto).append(" - Plan de trabajo - Cálculos masivos");

		// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(),

		// TipoMovPista.INSERTA_REGISTRO.getId(),

		// TipoSeccionPista.PROYECTO_DATOS_PLAN_TRABAJO.getIdSeccionPista(),

		// builder.toString(),

		// Optional.empty());

	}

	private TareaPlanTrabajoModel conversionDtoAmodel(DataDto dataDto, PlanTrabajoModel planTrabajoTemporal) {
		TareaPlanTrabajoModel tareaOriginal = new TareaPlanTrabajoModel();
		// No establecer idTareaPlanTrabajo, dejar que la base de datos lo genere
		tareaOriginal.setIdTarea(dataDto.getIdTarea());
		tareaOriginal.setNivelEsquema(dataDto.getNivelEsquema());
		tareaOriginal.setNombreTarea(dataDto.getNombreTarea());
		tareaOriginal.setActivo(dataDto.isActivo());
		tareaOriginal.setDuracionPlaneada(dataDto.getDuracionPlaneada());

		tareaOriginal.setFechaInicioPlaneada(dataDto.getFechaInicioPlaneada());
		tareaOriginal.setFechaFinPlaneada(dataDto.getFechaFinPlaneada());
		tareaOriginal.setDuracionReal(dataDto.getDuracionReal());
		tareaOriginal.setFechaInicioReal(dataDto.getFechaInicioReal());
		tareaOriginal.setFechaFinReal(dataDto.getFechaFinReal());
		tareaOriginal.setPredecesora(dataDto.getPredecesora());
		tareaOriginal.setPorcentajePlaneado(dataDto.getPlaneado());
		tareaOriginal.setPorcentajeCompletado(dataDto.getCompletado());
		tareaOriginal.setPlanTrabajoModel(planTrabajoTemporal);
		return tareaOriginal;
	}

	private PlanTrabajoModel crearNuevoPlanTrabajo(Long idProyecto) {
		ProyectoModel proyecto = proyectoRepository.findByIdProyectoAndEstatus(idProyecto, true)
				.orElseThrow(() -> new IllegalArgumentException("No se encontró el proyecto con id: " + idProyecto));

		PlanTrabajoModel nuevoPlan = new PlanTrabajoModel();
		nuevoPlan.setProyectoModel(proyecto);
		nuevoPlan.setFecha(horaActual());
		nuevoPlan.setComentarios("Plan Creado");

		// Guardar el nuevo plan de trabajo temporal en la base de datos
		return planTrabajoRepository.save(nuevoPlan);

	}

	private LocalDateTime horaActual() {
		ZoneId zoneId = ZoneId.of("America/Mexico_City");
		ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
		return zonedDateTime.toLocalDateTime();
	}

	// validacion de columnas Excel
	private void validarColumnasExcel(Sheet sheet) {
		String[] columnasEsperadas = { "Id tarea", "Nivel de esquema", "Nombre de la tarea", "Activo",
				"Duración planeada", "Fecha de inicio planeada", "Fecha fin planeada", "Duración real",
				"Fecha inicio real", "Fecha fin real", "Predecesora", "Planeado %", "Completado %" };

		int headerRowIndex = 1;
		Row headerRow = sheet.getRow(headerRowIndex);

		if (headerRow == null) {
			throw new PlanTrabajoException(ErroresPlanTrabajoEnum.ARCHIVO_VACIO,
					"No se encontró la fila de encabezados en la fila 2");
		}

		// Mapear las posiciones de los encabezados desde la columna B
		Map<String, Integer> headerMap = new HashMap<>();
		// se valida los encabezados del excel De B a N es 1 a 13
		for (int colIndex = 1; colIndex <= columnasEsperadas.length; colIndex++) {
			Cell cell = headerRow.getCell(colIndex);
			if (cell != null) {
				String cellValue = getCellValueAsString(cell).trim();
				headerMap.put(cellValue, colIndex);
			}
		}

		StringBuilder errorMessage = new StringBuilder("Problemas con los encabezados: ");
		boolean hayErrores = false;

		for (String expectedHeader : columnasEsperadas) {
			if (!headerMap.containsKey(expectedHeader)) {
				errorMessage.append("Falta el encabezado '").append(expectedHeader).append("'. ");
				hayErrores = true;
			}
		}

		if (headerRow.getLastCellNum() > columnasEsperadas.length + 1) {
			errorMessage.append("Se han agregado columnas adicionales no permitidas. ");
			hayErrores = true;
		}

		if (hayErrores) {
			throw new PlanTrabajoException(ErroresPlanTrabajoEnum.ERROR_COLUMNA_EXCEL);
		}
	}

	private String getCellValueAsString(Cell cell) {
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
			case STRING:
				return cell.getStringCellValue();
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					return cell.getLocalDateTimeCellValue().toString();
				} else {
					return String.valueOf(cell.getNumericCellValue());
				}
			case BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());
			case FORMULA:
				return cell.getCellFormula();
			case BLANK:
				return "";
			default:
				return "";
		}
	}

}
