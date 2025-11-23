package com.sisecofi.admingeneral.service.adminplantillas.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.sisecofi.libreria.comunes.dto.CustomMultipartFile;
import com.sisecofi.libreria.comunes.dto.plantilla.PlantillaCarpetasDto;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sisecofi.admingeneral.util.Constantes;
import com.sisecofi.admingeneral.dto.adminplantillas.CarpetaDtoResponse;
import com.sisecofi.admingeneral.dto.adminplantillas.PlantillaResponseDto;
import com.sisecofi.admingeneral.microservicio.CatalogoMicroservicio;
import com.sisecofi.admingeneral.microservicio.ProyectoMicroservicio;
import com.sisecofi.admingeneral.repository.adminplantillas.CarpetaPlantillaRepository;
import com.sisecofi.admingeneral.repository.adminplantillas.PlantillaVigenteRopository;
import com.sisecofi.admingeneral.service.adminplantillas.AdminPlantillaService;
import com.sisecofi.admingeneral.service.adminplantillas.ReporteService;
import com.sisecofi.admingeneral.util.ConstantesAdminPlantilla;
import com.sisecofi.admingeneral.util.enums.ErroresAdminPlantillaEnum;
import com.sisecofi.admingeneral.util.exception.PlantillaException;
import com.sisecofi.libreria.comunes.dto.plantilla.PlantillaDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatFaseProyecto;
import com.sisecofi.libreria.comunes.model.plantilla.ArchivoPlantillaModel;
import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Iterator;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.JsonNode;
import com.sisecofi.admingeneral.service.adminpistas.PistaService;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Service("AdminPlantillaServiceLectura")
@RequiredArgsConstructor
@Slf4j
public class AdminPlantillaServiceLecturaImpl implements AdminPlantillaService {

	private static final String CARPETA = "Carpeta";
	private static final String ARCHIVO = "Archivo";
	private static final String PATRON_ALFANUMERICO = "^\\w+$";
	private final PlantillaVigenteRopository plantillaVigenteRopository;
	private final CarpetaPlantillaRepository carpetaPlantillaRepository;

	private final CatalogoMicroservicio catalogoMicroservicio;
	private final ProyectoMicroservicio proyectoMicroservicio;
	private final ReporteService reporteService;
	private final PistaService pistaService;

	@SuppressWarnings({ "rawtypes" })
	@Override
	public PlantillaDto<CarpetaDtoResponse> lecturaPlantilla(MultipartFile file, int idFase) {
		try {
			BaseCatalogoModel c = validarCatalogo(idFase);
			validarNombreFase(file, idFase);
			validarCabeceras(file);
			PlantillaVigenteModel plan = new PlantillaVigenteModel();
			plan.setEstado(true);
			plan.setIdFaseProyecto(c.getPrimaryKey());
			plan.setNombre(c.getNombre());
			List<CarpetaPlantillaModel> carpeta = procesarArchivoExcel(file, plan);

			validarDuplicidad(carpeta);

			PlantillaDto<CarpetaDtoResponse> obj = new PlantillaDto<>();
			obj.setPlantillaVigenteModel(plan);
			obj.setIdFase(c.getPrimaryKey());

			CatFaseProyecto fase = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
					CatalogosComunes.FASES_PROYECTOS.getIdCatalogo(),
					obj.getPlantillaVigenteModel().getIdFaseProyecto(), new CatFaseProyecto());
			obj.setCatFaseProyecto(fase);

			List<CarpetaDtoResponse> carpetaDtoResponses = new ArrayList<>();
			for (CarpetaPlantillaModel carpetaItem : carpeta) {
				carpetaDtoResponses.add(convertirACarpetaDto(carpetaItem));
			}
			obj.setCarpetas(carpetaDtoResponses);

			pistaService
					.guardarPista(ModuloPista.MATRIZ_DOCUMENTAL.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),
							TipoSeccionPista.MATRIZ_DOCUMENTAL.getIdSeccionPista(),
							Constantes.getDescargarPlantillaFase()[0] + plan.getIdPlantillaVigente() + "|"
									+ Constantes.getDescargarPlantillaFase()[1] + plan.getNombre() + "|",
							Optional.of(plan));

			return obj;
		} catch (PlantillaException e) {
			throw e;
		} catch (Exception e) {
			throw new PlantillaException(ErroresAdminPlantillaEnum.ERROR_NO_CUMPLE_CON_LA_ESTRUCTURA, e);
		}
	}

	private void validarDuplicidad(List<CarpetaPlantillaModel> carpetaList) {
		Set<String> uniqueCombinations = new HashSet<>();
		for (CarpetaPlantillaModel carpeta : carpetaList) {
			String combination = carpeta.getNombre() + "-" + carpeta.getNivel();

			if (!uniqueCombinations.add(combination)) {
				throw new PlantillaException(ErroresAdminPlantillaEnum.ERROR_NO_CUMPLE_CON_LA_ESTRUCTURA_DUPLICIDAD);
			}
		}
	}

	private void validarNombresArchivos(String nombreArchivo, int nivel) {
		String patron = PATRON_ALFANUMERICO;
		if (nombreArchivo == null || nombreArchivo.length() > 50 || !nombreArchivo.matches(patron)) {
			throw new PlantillaException(ErroresAdminPlantillaEnum.ERROR_NO_CUMPLE_CON_LA_ESTRUCTURA);
		}
		if (nivel > 10) {
			throw new PlantillaException(ErroresAdminPlantillaEnum.ERROR_NO_CUMPLE_CON_LA_ESTRUCTURA_NIVEL_ARCHIVO);
		}
	}

	private void validarNombreFase(MultipartFile file, Integer id) throws IOException {
		CatFaseProyecto catFaseProyecto = catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
				CatalogosComunes.FASES_PROYECTOS.getIdCatalogo(), id, CatFaseProyecto.class);
		try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
			String secondSheetName = workbook.getSheetName(1);
			if (workbook.getNumberOfSheets() < 2 || !secondSheetName.equals(catFaseProyecto.getNombre())) {
				throw new PlantillaException(ErroresAdminPlantillaEnum.ERROR_NO_CUMPLE_CON_LA_ESTRUCTURA_NOMBRE_FASE);
			}

		}
	}

	private void validarCabeceras(MultipartFile file) {
		byte[] decodedBytes = reporteService.generarReporteBase();
		MultipartFile base = new CustomMultipartFile("nombre.xlsx", decodedBytes, "application/octet-stream");
		if (!compararPrimeraFilaConInformacion(file, base)) {
			throw new PlantillaException(
					ErroresAdminPlantillaEnum.ERROR_NO_CUMPLE_CON_LA_ESTRUCTURA_COLUMNAS_MODIFICADAS);
		}
	}

	public boolean compararPrimeraFilaConInformacion(MultipartFile file1, MultipartFile file2) {
		try (Workbook workbook1 = new XSSFWorkbook(file1.getInputStream());
				Workbook workbook2 = new XSSFWorkbook(file2.getInputStream())) {

			Sheet sheet1 = workbook1.getSheetAt(1);
			Sheet sheet2 = workbook2.getSheetAt(1);

			return compareFirstDataRow(sheet1, sheet2);

		} catch (IOException e) {
			log.error("Error: {}");
			return false;
		}
	}

	private boolean compareFirstDataRow(Sheet sheet1, Sheet sheet2) {
		Row firstDataRow1 = getFirstDataRow(sheet1);
		Row firstDataRow2 = getFirstDataRow(sheet2);

		return compareRows(firstDataRow1, firstDataRow2);
	}

	private Row getFirstDataRow(Sheet sheet) {
		Iterator<Row> rowIterator = sheet.iterator();

		if (rowIterator.hasNext())
			rowIterator.next();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (row.cellIterator().hasNext()) {
				return row;
			}
		}
		return null;
	}

	private boolean compareRows(Row row1, Row row2) {
		if (row1 == null || row2 == null) {
			return false;
		}

		Iterator<Cell> cellIterator1 = row1.cellIterator();
		Iterator<Cell> cellIterator2 = row2.cellIterator();

		if (cellIterator1.hasNext()) {
			cellIterator1.next();
		}
		if (cellIterator2.hasNext()) {
			cellIterator2.next();
		}

		while (cellIterator1.hasNext() && cellIterator2.hasNext()) {
			Cell cell1 = cellIterator1.next();
			Cell cell2 = cellIterator2.next();

			if (!compareCells(cell1, cell2)) {
				return false;
			}
		}

		return !(cellIterator1.hasNext() || cellIterator2.hasNext());
	}

	private boolean compareCells(Cell cell1, Cell cell2) {
		if (cell1.getCellType() != cell2.getCellType()) {
			return false;
		}

		switch (cell1.getCellType()) {
		case STRING:
			return cell1.getStringCellValue().equals(cell2.getStringCellValue());
		case NUMERIC:
			return cell1.getNumericCellValue() == cell2.getNumericCellValue();
		case BOOLEAN:
			return cell1.getBooleanCellValue() == cell2.getBooleanCellValue();
		case FORMULA:
			return cell1.getCellFormula().equals(cell2.getCellFormula());
		default:
			return false;
		}
	}

	private BaseCatalogoModel validarCatalogo(int idFase) {
		BaseCatalogoModel c = catalogoMicroservicio
				.obtenerInformacionCatalogoId(CatalogosComunes.FASES_PROYECTOS.getIdCatalogo(), idFase);
		if (c == null) {
			throw new PlantillaException(ErroresAdminPlantillaEnum.ERROR_AL_CONSULTAR_FASE_PROYECTO);
		}
		return c;
	}

	private void validarNombrePlantilla(String nombre) {
		List<PlantillaVigenteModel> opcional = plantillaVigenteRopository.findByNombre(nombre);
		if (!opcional.isEmpty()) {
			throw new PlantillaException(ErroresAdminPlantillaEnum.NOMBRE_REPETIDO);
		}
	}

// ***********************************
	@SuppressWarnings({ "rawtypes" })
	@Override
	@Transactional
	public PlantillaResponseDto guardarPlantilla(PlantillaDto<CarpetaDtoResponse> plantilla)
			throws JsonProcessingException {
		PlantillaVigenteModel plan = new PlantillaVigenteModel();
		plan.setDescripcion(plantilla.getPlantillaVigenteModel().getNombre());
		plan.setEstado(true);
		plan.setFecha(LocalDateTime.now());
		plan.setNombre(plantilla.getPlantillaVigenteModel().getNombre());
		plan.setIdFaseProyecto(plantilla.getIdFase());
		validarNombrePlantilla(plan.getNombre());
		plan = plantillaVigenteRopository.save(plan);
		PlantillaResponseDto dto = new PlantillaResponseDto();
		dto.setIdPlantillaVigente(plan.getIdPlantillaVigente());
		dto.setIdFaseProyecto(plan.getIdFaseProyecto());
		dto.setNombre(plan.getNombre());
		dto.setFecha(plan.getFecha());
		dto.setFechaModificacion(plan.getFechaModificacion());
		dto.setDescripcion(plan.getDescripcion());
		dto.setEstado(plan.isEstado());
		dto.setAsignado(plan.isAsignado());
		guardarCarpetas(plantilla, plan);
		CatFaseProyecto catFaseProyecto = catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
				CatalogosComunes.FASES_PROYECTOS.getIdCatalogo(), plan.getIdFaseProyecto(), CatFaseProyecto.class);
		dto.setCatFaseProyecto(catFaseProyecto);

		pistaService.guardarPista(ModuloPista.MATRIZ_DOCUMENTAL.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),
				TipoSeccionPista.MATRIZ_DOCUMENTAL.getIdSeccionPista(),
				Constantes.getMatrizDocumental()[0] + plan.getIdPlantillaVigente() + "|"
						+ Constantes.getMatrizDocumental()[1] + plan.getNombre() + "|",
				Optional.of(plan));
		return dto;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private CarpetaPlantillaModel obtenerCarpeta(CarpetaDtoResponse car, PlantillaVigenteModel plan)
			throws JsonProcessingException {
		CarpetaPlantillaModel carpeta = new CarpetaPlantillaModel();
		carpeta.setIdCarpetaPlantilla(car.getIdCarpetaPlantilla());
		carpeta.setNombre(car.getNombre());
		carpeta.setOrden(car.getOrden());
		carpeta.setNivel(car.getNivel());
		carpeta.setDato(car.getDato());
		carpeta.setDescripcion(car.getDescripcion());
		carpeta.setTipo(car.getTipo());
		carpeta.setTipo(car.getTipo());
		carpeta.setEstatus(car.isEstatus());
		carpeta.setPlantillaVigenteModel(plan);
		carpeta.setObligatorio(car.isObligatorio());

		List<Object> lista = car.getSubCarpetas();

		for (Object elemento : lista) {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(elemento);
			JsonNode jsonNode = objectMapper.readTree(json);
			JsonNode nombreNode = jsonNode.get("tipo");
			String tipo = nombreNode.asText();

			if (tipo.equals(ARCHIVO)) {
				ArchivoPlantillaModel archivo = objectMapper.readValue(json, ArchivoPlantillaModel.class);
				carpeta.addArchivo(archivo);
			} else if (tipo.equals(CARPETA)) {
				CarpetaDtoResponse subCarpeta = objectMapper.readValue(json, CarpetaDtoResponse.class);
				carpeta.addSubCarpeta(obtenerCarpeta(subCarpeta, plan));
			}
		}

		return carpeta;
	}

	@SuppressWarnings("rawtypes")
	private void guardarCarpetas(PlantillaDto<CarpetaDtoResponse> plantilla, PlantillaVigenteModel plan)
			throws JsonProcessingException {
		List<CarpetaDtoResponse> lista = plantilla.getCarpetas();
		for (CarpetaDtoResponse car : lista) {
			carpetaPlantillaRepository.save(obtenerCarpeta(car, plan));
		}
	}

// ***********************************
	private LocalDateTime horaActual() {
		ZoneId zoneId = ZoneId.of("America/Mexico_City");
		ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
		return zonedDateTime.toLocalDateTime();
	}

	public List<CarpetaPlantillaModel> procesarArchivoExcel(MultipartFile file, PlantillaVigenteModel plan)
			throws IOException {
		List<CarpetaPlantillaModel> carpetasNivel1 = new ArrayList<>();
		Map<Integer, CarpetaPlantillaModel> niveles = new HashMap<>();

		try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
			Sheet sheet = workbook.getSheetAt(1);

			actualizarNombrePlan(sheet, plan);

			for (Row row : sheet) {
				if (row.getRowNum() <= 1)
					continue;

				Cell descripcionCell = row.getCell(22);
				procesarNiveles(row, descripcionCell, plan, carpetasNivel1, niveles);
			}
		}

		return carpetasNivel1;
	}

	private void actualizarNombrePlan(Sheet sheet, PlantillaVigenteModel plan) {
		if (plan.getNombre() != null) {
			Row rowB3 = sheet.getRow(2);
			if (rowB3 != null) {
				Cell cellB3 = rowB3.getCell(1);
				if (cellB3 != null && cellB3.getCellType() != CellType.BLANK) {
					plan.setNombre(cellB3.getStringCellValue());
				}
			}
		}
	}

	private void procesarNiveles(Row row, Cell descripcionCell, PlantillaVigenteModel plan,
			List<CarpetaPlantillaModel> carpetasNivel1, Map<Integer, CarpetaPlantillaModel> niveles) {
		for (int nivel = 1; nivel <= 10; nivel++) {
			Cell carpetaCell = row.getCell(nivel * 2);
			Cell archivoCell = row.getCell(nivel * 2 + 1);

			if (carpetaCell != null && carpetaCell.getCellType() != CellType.BLANK) {
				CarpetaPlantillaModel carpeta = crearCarpeta(carpetaCell, descripcionCell, nivel, row.getRowNum(),
						plan);
				agregarCarpetaAColeccion(nivel, carpeta, carpetasNivel1, niveles);
				validarNombresArchivos(carpeta.getNombre(), nivel);
				niveles.put(nivel, carpeta);
			}

			if (archivoCell != null && archivoCell.getCellType() != CellType.BLANK) {
				ArchivoPlantillaModel archivo = crearArchivo(archivoCell, descripcionCell, nivel, row.getRowNum());
				agregarArchivoACarpeta(archivo, niveles.get(nivel));
				validarNombresArchivos(archivo.getNombre(), nivel);
			}
		}
	}

	private CarpetaPlantillaModel crearCarpeta(Cell carpetaCell, Cell descripcionCell, int nivel, int orden,
			PlantillaVigenteModel plan) {
		CarpetaPlantillaModel carpeta = new CarpetaPlantillaModel();
		carpeta.setNombre(carpetaCell.getStringCellValue());
		carpeta.setNivel(nivel);
		carpeta.setOrden(orden);
		carpeta.setDescripcion(descripcionCell != null ? descripcionCell.getStringCellValue() : "");
		carpeta.setSubCarpetas(new ArrayList<>());
		carpeta.setArchivos(new ArrayList<>());
		carpeta.setTipo(ConstantesAdminPlantilla.TIPO_CARPETA);
		carpeta.setObligatorio(true);
		carpeta.setEstatus(true);
		carpeta.setPlantillaVigenteModel(plan);
		if (carpeta.getDescripcion() == null || carpeta.getDescripcion().isBlank()) {
		    throw new PlantillaException(ErroresAdminPlantillaEnum.ERROR_DESCRIPCION_VACIA);
		}
		return carpeta;
	}

	private ArchivoPlantillaModel crearArchivo(Cell archivoCell, Cell descripcionCell, int nivel, int orden) {
		ArchivoPlantillaModel archivo = new ArchivoPlantillaModel();
		archivo.setNombre(archivoCell.getStringCellValue());
		archivo.setNivel(nivel);
		archivo.setTipo(ConstantesAdminPlantilla.TIPO_ARCHIVO);
		archivo.setOrden(orden);
		archivo.setDescripcion(descripcionCell != null ? descripcionCell.getStringCellValue() : "");
		archivo.setObligatorio(true);
		archivo.setEstatus(true);
		if (archivo.getDescripcion()==null ||archivo.getDescripcion().isBlank()) {
			throw new PlantillaException(ErroresAdminPlantillaEnum.ERROR_DESCRIPCION_VACIA);
		}
		return archivo;
	}

	private void agregarCarpetaAColeccion(int nivel, CarpetaPlantillaModel carpeta,
			List<CarpetaPlantillaModel> carpetasNivel1, Map<Integer, CarpetaPlantillaModel> niveles) {
		if (nivel == 1) {
			carpetasNivel1.add(carpeta);
		} else {
			CarpetaPlantillaModel carpetaPadre = niveles.get(nivel - 1);
			if (carpetaPadre != null)
				carpetaPadre.getSubCarpetas().add(carpeta);
		}
	}

	private void agregarArchivoACarpeta(ArchivoPlantillaModel archivo, CarpetaPlantillaModel carpetaActual) {
		if (carpetaActual == null)
			return;

		boolean repetido = carpetaActual.getArchivos().stream()
				.anyMatch(archivoCom -> archivoCom.getNivel() == archivo.getNivel()
						&& archivoCom.getNombre().equals(archivo.getNombre()));

		if (repetido) {
			throw new PlantillaException(ErroresAdminPlantillaEnum.ERROR_NO_CUMPLE_CON_LA_ESTRUCTURA_DUPLICIDAD);
		}

		carpetaActual.addArchivo(archivo);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PlantillaDto<CarpetaDtoResponse> obtenerPlantilla(Integer idPlantilla) {
		Optional<PlantillaVigenteModel> plan = plantillaVigenteRopository.findById(idPlantilla);
		log.info("Plantilla: {}", plan);

		if (plan.isPresent()) {
			List<CarpetaPlantillaModel> carpetas = carpetaPlantillaRepository
					.findByNivelAndPlantillaVigenteModelIdPlantillaVigenteOrderByIdCarpetaPlantillaAsc(1, plan.get().getIdPlantillaVigente());
			log.info("Carpetas nivel 1: {}", carpetas.size());

			PlantillaDto<CarpetaDtoResponse> obj = new PlantillaDto<>();
			obj.setPlantillaVigenteModel(plan.get());

			CatFaseProyecto fase = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
					CatalogosComunes.FASES_PROYECTOS.getIdCatalogo(),
					obj.getPlantillaVigenteModel().getIdFaseProyecto(), new CatFaseProyecto());
			obj.setCatFaseProyecto(fase);
			obj.setIdFase(plan.get().getIdFaseProyecto());

			// Convertir las carpetas a DTOs
			List<CarpetaDtoResponse> carpetaDtos = new ArrayList<>();
			for (CarpetaPlantillaModel carpeta : carpetas) {
				CarpetaDtoResponse carpetaDto = convertirACarpetaDto(carpeta);
				carpetaDtos.add(carpetaDto);
			}
			obj.setCarpetas(carpetaDtos);

			pistaService.guardarPista(ModuloPista.MATRIZ_DOCUMENTAL.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),
					TipoSeccionPista.MATRIZ_DOCUMENTAL.getIdSeccionPista(), Constantes.getDescargarPlantillaFase()[0]
							+ idPlantilla + "|" + Constantes.getDescargarPlantillaFase()[1] + plan.get().getNombre(),
					Optional.empty());

			return obj;
		}
		throw new PlantillaException(ErroresAdminPlantillaEnum.ERROR_PLANTILLA_NO_ENCONTRADA);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private CarpetaDtoResponse convertirACarpetaDto(CarpetaPlantillaModel carpeta) {
		CarpetaDtoResponse carpetaDto = new CarpetaDtoResponse();
		carpetaDto.setIdCarpetaPlantilla(carpeta.getIdCarpetaPlantilla());
		carpetaDto.setNombre(carpeta.getNombre());
		carpetaDto.setOrden(carpeta.getOrden());
		carpetaDto.setNivel(carpeta.getNivel());
		carpetaDto.setDato(carpeta.getDato());
		carpetaDto.setDescripcion(carpeta.getDescripcion());
		carpetaDto.setTipo(carpeta.getTipo());
		carpetaDto.setObligatorio(carpeta.isObligatorio());
		carpetaDto.setEstatus(carpeta.isEstatus());

		List<CarpetaPlantillaModel> subCarpetas = carpeta.getSubCarpetas();
		List<Object> subCarpetaDtos = new ArrayList<>();

		if (subCarpetas != null) {
			subCarpetaDtos = subCarpetas.stream().map(this::convertirACarpetaDto).collect(Collectors.toList());
		}

		List<ArchivoPlantillaModel> archivos = carpeta.getArchivos();
		if (archivos != null) {
			for (ArchivoPlantillaModel archivo : archivos) {
				subCarpetaDtos.add(archivo);
			}
		}
		carpetaDto.setSubCarpetas(subCarpetaDtos);
		return carpetaDto;
	}

	@Override
	public List<PlantillaVigenteModel> obtenerPlantillas() {
		List<Integer> listaIds = proyectoMicroservicio.plantillasOcupadas();
		
		List<PlantillaVigenteModel> lista = plantillaVigenteRopository.findAll();
		
		for (PlantillaVigenteModel plan: lista) {
			if (plan != null) {
			    plan.setAsignado(
			        listaIds != null &&
			        plan.getIdPlantillaVigente() != null &&
			        listaIds.contains(plan.getIdPlantillaVigente())
			    );
			}

		}
		
		plantillaVigenteRopository.saveAll(lista);

		return lista;
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public PlantillaVigenteModel actualizarPlantilla(PlantillaDto<CarpetaDtoResponse> plantilla,
			Integer idPlantillaVigente) throws JsonProcessingException {
		PlantillaVigenteModel plantillaMod = plantillaVigenteRopository.findByIdPlantillaVigente(idPlantillaVigente);
		PlantillaVigenteModel plantillaV = plantilla.getPlantillaVigenteModel();
		if (!plantillaMod.getNombre().equals(plantillaV.getNombre())) {
			validarNombrePlantilla(plantillaV.getNombre());
		}
		plantillaV.setFechaModificacion(horaActual());
		PlantillaVigenteModel plan = plantillaVigenteRopository.save(plantillaV);
		guardarCarpetas(plantilla, plan);

		pistaService.guardarPista(ModuloPista.MATRIZ_DOCUMENTAL.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),
				TipoSeccionPista.MATRIZ_DOCUMENTAL.getIdSeccionPista(),
				Constantes.getDescargarPlantillaFase()[0] + plantillaV.getIdPlantillaVigente() + "|"
						+ Constantes.getDescargarPlantillaFase()[1] + plantillaV.getNombre(),
				Optional.empty());
		return plantillaVigenteRopository.findByIdPlantillaVigente(idPlantillaVigente);
	}

	@Override
	public List<PlantillaVigenteModel> obtenerPlantillasFase(Integer idFaseProyecto) {
		return plantillaVigenteRopository.findByIdFaseProyectoAndEstado(idFaseProyecto, true);
	}

	@Override
	public CarpetaPlantillaModel obtenerArchivosVerificacion() {
		List<CarpetaPlantillaModel> lista = carpetaPlantillaRepository
				.findByPlantillaVigenteModelCatFaseProyectoNombreAndNivel("Verificación", 1);
		if (!lista.isEmpty()) {
			return lista.get(lista.size() - 1);
		} else {
			return null;
		}
	}

	@Override
	public CarpetaPlantillaModel obtenerArchivosReintegros() {
		List<CarpetaPlantillaModel> lista = carpetaPlantillaRepository
				.findByPlantillaVigenteModelCatFaseProyectoNombreAndNivel("Reintegros", 1);
		if (!lista.isEmpty()) {
			return lista.get(lista.size() - 1);
		} else {
			return null;
		}
	}

	@Override
	public PlantillaVigenteModel obtenterPlantillaVigente(Integer idPlantilla) {
		return plantillaVigenteRopository.findById(idPlantilla)
				.orElseThrow(() -> new PlantillaException(ErroresAdminPlantillaEnum.ERROR_PLANTILLA_NO_ENCONTRADA));
	}

	@Override
	public PlantillaVigenteModel guardarEstatusPlantilla(boolean estatus, Integer idPlantilla) {
		PlantillaVigenteModel plantilla = plantillaVigenteRopository.findById(idPlantilla)
				.orElseThrow(() -> new PlantillaException(ErroresAdminPlantillaEnum.ERROR_PLANTILLA_NO_ENCONTRADA));
		plantilla.setEstado(estatus);
		plantilla.setFechaModificacion(horaActual());
		plantillaVigenteRopository.save(plantilla);

		pistaService.guardarPista(ModuloPista.MATRIZ_DOCUMENTAL.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),
				TipoSeccionPista.MATRIZ_DOCUMENTAL.getIdSeccionPista(),
				Constantes.getMatrizDocumental()[0] + plantilla.getIdPlantillaVigente() + "|"
						+ Constantes.getMatrizDocumental()[1] + plantilla.getNombre() + "|"
						+ Constantes.getMatrizDocumental()[2] + plantilla.isEstado() + "|",
				Optional.of(plantilla));
		return plantilla;
	}

	@Override
	public PlantillaCarpetasDto<CarpetaPlantillaModel> obtenerPlantillaGeneral(Integer idPlantilla) {
		Optional<PlantillaVigenteModel> plan = plantillaVigenteRopository.findById(idPlantilla);
		log.info("Plantilla: {}", plan);
		if (plan.isPresent()) {
			List<CarpetaPlantillaModel> carpetas = carpetaPlantillaRepository
					.findByNivelAndPlantillaVigenteModelIdPlantillaVigente(1, plan.get().getIdPlantillaVigente());
			log.info("Capertas: {}", carpetas.size());
			PlantillaCarpetasDto<CarpetaPlantillaModel> obj = new PlantillaCarpetasDto<>();
			obj.setPlantillaVigenteModel(plan.get());
			obj.setCarpetas(carpetas);
			obj.setIdFase(plan.get().getIdFaseProyecto());
			CatFaseProyecto fase = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
					CatalogosComunes.FASES_PROYECTOS.getIdCatalogo(),
					obj.getPlantillaVigenteModel().getIdFaseProyecto(), new CatFaseProyecto());
			obj.setCatFaseProyecto(fase);
			return obj;
		}
		throw new PlantillaException(ErroresAdminPlantillaEnum.ERROR_PLANTILLA_NO_ENCONTRADA);
	}

}
