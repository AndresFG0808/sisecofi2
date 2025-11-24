package com.sisecofi.contratos.service.impl;

import com.sisecofi.contratos.dto.ArchivoCasoNegocioDto;
import com.sisecofi.contratos.dto.CasoNegocioDto;
import com.sisecofi.contratos.dto.CasoNegocioResponseDto;
import com.sisecofi.contratos.model.caso_negocio.CasoNegocioConvenioModel;
import com.sisecofi.contratos.model.caso_negocio.CasoNegocioModel;
import com.sisecofi.contratos.model.caso_negocio.CasoNegocioServicioConvenioModel;
import com.sisecofi.contratos.model.caso_negocio.CasoNegocioServicioModel;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ServicioConvenioModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.libreria.comunes.util.sesion.Session;
import com.sisecofi.libreria.comunes.model.contratos.ServicioContratoModel;
import com.sisecofi.libreria.comunes.model.contratos.VigenciaMontosModel;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
import com.sisecofi.contratos.repository.contrato.CasoNegocioConvenioRepository;
import com.sisecofi.contratos.repository.contrato.CasoNegocioRepository;
import com.sisecofi.contratos.repository.contrato.ContratoRepository;
import com.sisecofi.contratos.repository.contrato.ConvenioModificatorioRepository;
import com.sisecofi.contratos.repository.contrato.ServicioContratoRepository;
import com.sisecofi.contratos.repository.convenio_modificatorio.ServicioCasoContratoRepository;
import com.sisecofi.contratos.repository.convenio_modificatorio.ServicioCasoConvenioRepository;
import com.sisecofi.contratos.service.ContratoService;
import com.sisecofi.contratos.service.PistaService;
import com.sisecofi.contratos.service.ServicioCasoNegocio;
import com.sisecofi.contratos.service.convenio_modificatorio.ServicioConvenioModificatorio;
import com.sisecofi.contratos.util.exception.ContratoException;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.contratos.util.consumer.LayoutConsumer;
import com.sisecofi.contratos.util.enums.ErroresEnum;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;

@Service
@RequiredArgsConstructor
@Transactional
public class ServicioCasoNegocioImpl implements ServicioCasoNegocio {

	private final ContratoRepository contratoRepository;
	private final ServicioContratoRepository servicioContratoRepository;
	private final CasoNegocioRepository casoNegocioRepository;
	private final LayoutConsumer consumer;
	private final ConvenioModificatorioRepository convenioRepository;
	private final CasoNegocioConvenioRepository casoNegocioConvenioRepository;
	private final ServicioConvenioModificatorio servicioConvenioModificatorio;
	private final ServicioCasoConvenioRepository servicioCasoConvenioRepository;
	private final PistaService pistaService;
	private final ServicioCasoContratoRepository servicioCasoContratoRepository;
	private static final String PATRON = ",\\s*";
	private static final String START_SERVICE_REGISTRY = "|id registro servicio: ";
	private final ContratoService contratoService;
	private final Session session;

	@Override
	public String obtenerLayout(Long idContrato) {
		ContratoModel contrato = contratoRepository.findByIdContrato(idContrato)
				.orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));
		VigenciaMontosModel vigencia = contrato.getVigencia();
		LocalDateTime fechaTermino = vigencia != null ? vigencia.getFechaFinVigenciaContrato() : null;
		LocalDateTime fechaInicio = vigencia != null ? vigencia.getFechaInicioVigenciaContrato() : null;
		StringBuilder resultado = new StringBuilder(START_SERVICE_REGISTRY);

		Optional<CasoNegocioModel> casoNegocio = casoNegocioRepository
				.findByContratoModelIdContratoAndEstatusTrue(idContrato);
		BigDecimal[][] volumeria = casoNegocio.map(CasoNegocioModel::getVolumeria).orElse(null);

		List<CasoNegocioServicioModel> lista = servicioCasoContratoRepository
				.findByCasoNegocioContratoModelIdContratoOrderByServicioContratoOrden(idContrato);

		for (CasoNegocioServicioModel srv : lista) {
			resultado.append("|").append(srv.getIdCasoNegocioServicio());
		}

		String resultadoFinal = resultado.toString();



		// pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),


		// TipoSeccionPista.CONTRATOS_PROYECCION_CASO_NEGOCIO.getIdSeccionPista(),


		// Constantes.getAtributosConvenioModificatorio()[0] + contrato.getIdContrato()


		// + Constantes.getAtributosConvenioModificatorio()[1] + resultadoFinal,


		// Optional.empty());

		return obtenerLayoutGenerico(idContrato, fechaInicio, fechaTermino, volumeria, false);
	}

	private String obtenerLayoutGenerico(Long idContrato, LocalDateTime fechaInicio, LocalDateTime fechaTermino,
			BigDecimal[][] volumeria, boolean exportar) {
		try {
			List<ServicioContratoModel> servicios = servicioContratoRepository
					.findByIdContratoAndEstatusTrueOrderByOrden(idContrato);

			servicios.sort((s1, s2) -> Integer.compare(s1.getOrden(), s2.getOrden()));

			CasoNegocioDto dto = new CasoNegocioDto();
			dto.setListaServicio(servicios);
			dto.setVolumeria(volumeria);
			dto.setExportar(exportar);

			String meses = (fechaInicio == null || fechaTermino == null) ? obtenerCabecerasSinMeses()
					: obtenerMeses(fechaInicio, fechaTermino);

			consumer.inializar("Layout base");
			consumer.agregarCabeceras(meses);
			consumer.accept(dto);

			byte[] layout = consumer.cerrarBytes();
			return Base64.getEncoder().encodeToString(layout);
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_GENERAR_LAYOUT);
		}
	}

	private String obtenerMeses(LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
		String meses = obtenerMesesEntreFechas(fechaInicio, fechaFinal);

		return obtenerCabecerasSinMeses() + "," + meses;
	}

	private String obtenerMesesEntreFechas(LocalDateTime fechaInicial, LocalDateTime fechaFinal) {
		Locale locale = Locale.forLanguageTag("es-ES");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-yyyy", locale);

		List<String> meses = new ArrayList<>();

		LocalDateTime current = fechaInicial.withDayOfMonth(1);
		LocalDateTime end = fechaFinal.withDayOfMonth(fechaFinal.toLocalDate().lengthOfMonth());

		while (!current.isAfter(end)) {
			meses.add(current.format(formatter));
			current = current.plusMonths(1);
		}

		return String.join(", ", meses);
	}

	private String obtenerCabecerasSinMeses() {
		return Constantes.INICIO_ENCABEZADOS_CASO_NEGOCIO;
	}

	public void compararExcel(String base64Excel1, String base64Excel2) throws IOException {
		byte[] decodedBytes1 = Base64.getDecoder().decode(base64Excel1);
		byte[] decodedBytes2 = Base64.getDecoder().decode(base64Excel2);

		// Ajuste para permitir ratios de compresión bajos ya que no se trata de un zip
	    ZipSecureFile.setMinInflateRatio(0.0001);
		
		try (Workbook workbook1 = new XSSFWorkbook(new ByteArrayInputStream(decodedBytes1));
				Workbook workbook2 = new XSSFWorkbook(new ByteArrayInputStream(decodedBytes2))) {

			Sheet sheet1 = workbook1.getSheetAt(0);
			Sheet sheet2 = workbook2.getSheetAt(0);

			List<String> primeraFila1 = leerPrimeraFila(sheet1);
			List<String> primeraFila2 = leerPrimeraFila(sheet2);
			List<String> primeraColumna1 = leerPrimeraColumna(sheet1);
			List<String> primeraColumna2 = leerPrimeraColumna(sheet2);
			List<String> segundaColumna1 = leerSegundaColumna(sheet1);
			List<String> segundaColumna2 = leerSegundaColumna(sheet2);

			if (!Objects.equals(primeraFila1, primeraFila2)) {
				throw new ContratoException(ErroresEnum.ERROR_ARCHIVO_NO_COMPATIBLE);
			}

			if (!Objects.equals(primeraColumna1, primeraColumna2)) {
				throw new ContratoException(ErroresEnum.ERROR_ARCHIVO_NO_COMPATIBLE);
			}

			if (!Objects.equals(segundaColumna1, segundaColumna2)) {
				throw new ContratoException(ErroresEnum.ERROR_ARCHIVO_NO_COMPATIBLE);
			}
		}
	}

	private List<String> leerPrimeraFila(Sheet sheet) {
		Row row = sheet.getRow(0);
		List<String> primeraFila = new ArrayList<>();
		if (row != null) {
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				String value = getCellValueAsString(cell).trim();
				primeraFila.add(value.toLowerCase());
			}
		}
		return primeraFila;
	}

	private List<String> leerPrimeraColumna(Sheet sheet) {
		List<String> primeraColumna = new ArrayList<>();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Cell cell = row.getCell(0);
			if (cell != null) {
				primeraColumna.add(getCellValueAsString(cell));
			} else {
				primeraColumna.add("0");
			}
		}
		return primeraColumna;
	}

	private List<String> leerSegundaColumna(Sheet sheet) {
		List<String> segundaColumna = new ArrayList<>();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Cell cell = row.getCell(1);
			if (cell != null) {
				segundaColumna.add(getCellValueAsString(cell));
			} else {
				segundaColumna.add("0");
			}
		}
		return segundaColumna;
	}

	@Override
	public Map<Integer, List<String>> procesarProyeccion(ArchivoCasoNegocioDto archivo, Long idContrato)
			throws IOException {
		List<String> validacionMaximos = new ArrayList<>();

		compararExcel(obtenerLayout(idContrato), archivo.getArchivo());

		StringBuilder resultado = new StringBuilder("");

		ContratoModel contrato = contratoRepository.findByIdContrato(idContrato)
				.orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));
		VigenciaMontosModel vigencia = contrato.getVigencia();

		LocalDateTime fechaInicio = vigencia != null ? vigencia.getFechaInicioVigenciaContrato() : null;
		LocalDateTime fechaTermino = vigencia != null ? vigencia.getFechaFinVigenciaContrato() : null;
		String meses = obtenerMeses(fechaInicio, fechaTermino);

		String[] periodos = meses.split(PATRON);
		periodos = Arrays.copyOfRange(periodos, 2, periodos.length);
		int cont = contarMeses(meses);

		BigDecimal[][] matriz = convertExcelToMatrix(archivo.getArchivo(), cont);

		CasoNegocioModel casoNegocio = casoNegocioRepository.findByContratoModelIdContratoAndEstatusTrue(idContrato)
				.orElseGet(() -> new CasoNegocioModel(true, contrato, matriz));

		if (casoNegocio.getCasoNegocioServicios() == null) {
			casoNegocio.setCasoNegocioServicios(new ArrayList<>());
		}

		casoNegocio.setVolumeria(matriz);

		Map<Long, CasoNegocioServicioModel> serviciosExistentesMap = casoNegocio.getCasoNegocioServicios().stream()
				.collect(Collectors.toMap(servicio -> servicio.getServicioContrato().getIdServicioContrato(),
						servicio -> servicio));

		List<ServicioContratoModel> servicios = servicioContratoRepository.findByIdContratoAndEstatusTrueOrderByOrden(idContrato);
		servicios.sort((s1, s2) -> Integer.compare(s1.getOrden(), s2.getOrden()));

		for (int i = 0; i < servicios.size(); i++) {
			ServicioContratoModel servicio = servicios.get(i);
			double[] volumetria = Arrays.stream(matriz[i]).map(value -> value != null ? value.doubleValue() : 0.0)
					.mapToDouble(Double::doubleValue).toArray();

			CasoNegocioServicioModel casoServicio = serviciosExistentesMap.get(servicio.getIdServicioContrato());

			if (casoServicio != null) {
				casoServicio.setVolumetria(volumetria);
				casoServicio.setFechaModificacion(horaActual());
				casoServicio.setPeriodos(periodos);
			} else {
				casoServicio = new CasoNegocioServicioModel(casoNegocio, servicio, volumetria, horaActual(), periodos);
				casoNegocio.getCasoNegocioServicios().add(casoServicio);
			}

			if (!casoServicio.isVolumetriaWithinMaximo()) {
				validacionMaximos.add(casoServicio.getServicioContrato().getConcepto());

			}
			resultado.append("|").append(casoServicio.toString());
		}

		validarMaximos(Constantes.BASE_ERROR_VOLUMETRIA, validacionMaximos);

		String resultadoFinal = resultado.toString();

		boolean esCreacion = casoNegocio.getIdCasoNegocio() == null;
		casoNegocioRepository.save(casoNegocio);

		contratoService.actualizarUltimaMod(idContrato);



		// pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(),


		// esCreacion ? TipoMovPista.INSERTA_REGISTRO.getId() : TipoMovPista.ACTUALIZA_REGISTRO.getId(),


		// TipoSeccionPista.CONTRATOS_PROYECCION_CASO_NEGOCIO.getIdSeccionPista(),


		// Constantes.getAtributosConvenioModificatorio()[0] + contrato.getIdContrato() + resultadoFinal,


		// Optional.empty());

		return leerExcel(archivo.getArchivo());
	}

	@Override
	public String obtenerLayoutConvenio(Long idConvenioModificatorio) {
		ConvenioModificatorioModel convenio = convenioRepository.findById(idConvenioModificatorio)
				.orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));
		ContratoModel contrato = convenio.getContratoModel();
		LocalDateTime fechaTermino = convenio.getFechaFin();
		LocalDateTime fechaInicio = contrato.getVigencia().getFechaInicioVigenciaContrato();

		Optional<CasoNegocioConvenioModel> casoNegocio = casoNegocioConvenioRepository
				.findByConvenioModificatorioModelIdConvenioModificatorioAndEstatusTrue(idConvenioModificatorio);

		BigDecimal[][] volumeria = casoNegocio.map(CasoNegocioConvenioModel::getVolumeria).orElse(null);

		StringBuilder resultado = new StringBuilder(START_SERVICE_REGISTRY);

		List<CasoNegocioServicioConvenioModel> lista = servicioCasoConvenioRepository
				.findByCasoNegocioConvenioModificatorioModelIdConvenioModificatorioOrderByServicioConvenioServicioBaseOrden(idConvenioModificatorio);

		for (CasoNegocioServicioConvenioModel srv : lista) {
			resultado.append("|").append(srv.getIdCasoNegocioServicio());
		}

		String resultadoFinal = resultado.toString();



		// pistaService.guardarPista(ModuloPista.CONVENIO_MODIFICATORIO.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


		// TipoSeccionPista.PROYECCION_CONVENIO_MODIFICATORIO.getIdSeccionPista(),


		// Constantes.getAtributosConvenioModificatorio()[0] + contrato.getIdContrato()


		// + Constantes.getAtributosConvenioModificatorio()[1] + convenio.getIdConvenioModificatorio()


		// + resultadoFinal,


		// Optional.empty());

		return obtenerLayoutGenerico(convenio.getIdContrato(), fechaInicio, fechaTermino, volumeria, false);
	}

	@Override
	public Map<Integer, List<String>> procesarProyeccionConvenio(ArchivoCasoNegocioDto archivo,
			Long idConvenioModificatorio) throws IOException {
		List<String> validacionMaximos = new ArrayList<>();

		compararExcel(obtenerLayoutConvenio(idConvenioModificatorio), archivo.getArchivo());

		ConvenioModificatorioModel convenio = convenioRepository.findById(idConvenioModificatorio)
				.orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));

		convenio.setUltimaModificacion(ultimaModificacion());
		ContratoModel contrato = convenio.getContratoModel();
		LocalDateTime fechaInicio = contrato.getVigencia().getFechaInicioVigenciaContrato();
		LocalDateTime fechaTermino = convenio.getFechaFin();
		String meses = obtenerMeses(fechaInicio, fechaTermino);

		String[] periodos = meses.split(PATRON);
		periodos = Arrays.copyOfRange(periodos, 2, periodos.length);
		int cont = contarMeses(meses);

		BigDecimal[][] matriz = convertExcelToMatrix(archivo.getArchivo(), cont);

		CasoNegocioConvenioModel casoConvenio = casoNegocioConvenioRepository
				.findByConvenioModificatorioModelIdConvenioModificatorioAndEstatusTrue(idConvenioModificatorio)
				.orElseGet(() -> new CasoNegocioConvenioModel(true, convenio, matriz));

		if (casoConvenio.getCasoNegocioServicios() == null) {
			casoConvenio.setCasoNegocioServicios(new ArrayList<>());
		}

		casoConvenio.setVolumeria(matriz);

		Map<Long, CasoNegocioServicioConvenioModel> serviciosExistentesMap = casoConvenio.getCasoNegocioServicios()
				.stream().collect(Collectors.toMap(servicio -> servicio.getServicioConvenio().getIdServicioConvenio(),
						servicio -> servicio));

		List<ServicioConvenioModel> serviciosConvenio = servicioConvenioModificatorio
				.obtenerServicioConvenio(idConvenioModificatorio);

		serviciosConvenio.sort(Comparator.comparing(servicio -> servicio.getServicioBase().getIdServicioContrato()));

		StringBuilder resultado = new StringBuilder("");

		for (int i = 0; i < serviciosConvenio.size(); i++) {
			ServicioConvenioModel servicio = serviciosConvenio.get(i);
			double[] volumetria = Arrays.stream(matriz[i]).map(value -> value != null ? value.doubleValue() : 0.0)
					.mapToDouble(Double::doubleValue).toArray();

			CasoNegocioServicioConvenioModel casoServicio = serviciosExistentesMap
					.get(servicio.getIdServicioConvenio());

			if (casoServicio != null) {
				casoServicio.setVolumetria(volumetria);
				casoServicio.setFechaModificacion(horaActual());
				casoServicio.setPeriodos(periodos);
			} else {
				casoServicio = new CasoNegocioServicioConvenioModel(casoConvenio, servicio, volumetria, horaActual());
				casoConvenio.getCasoNegocioServicios().add(casoServicio);
			}

			if (!casoServicio.isVolumetriaWithinMaximo()) {
				validacionMaximos.add(casoServicio.getServicioConvenio().getServicioBase().getConcepto());

			}

			resultado.append("|").append(casoServicio.toString());
		}
		
		validarMaximos(Constantes.BASE_ERROR_VOLUMETRIA_CONVENIO, validacionMaximos);

		String resultadoFinal = resultado.toString();

		boolean esCreacion = casoConvenio.getIdCasoNegocioConvenio() == null;
		casoNegocioConvenioRepository.save(casoConvenio);
		convenioRepository.save(convenio);



		// pistaService.guardarPista(ModuloPista.CONVENIO_MODIFICATORIO.getId(),


		// esCreacion ? TipoMovPista.INSERTA_REGISTRO.getId() : TipoMovPista.ACTUALIZA_REGISTRO.getId(),


		// TipoSeccionPista.PROYECCION_CONVENIO_MODIFICATORIO.getIdSeccionPista(),


		// Constantes.getAtributosConvenioModificatorio()[1] + convenio.getIdConvenioModificatorio()


		// + resultadoFinal,


		// Optional.empty());

		return leerExcel(archivo.getArchivo());
	}
	
	@SuppressWarnings("serial")
	private void validarMaximos(String base,  List<String> conceptos) {
		if (conceptos == null || conceptos.isEmpty()) {
	        return; 
	    }

	    String msj = String.join(", ", conceptos);
	    
		String validacionMaximosFinal = String.format(base, msj);
		
		if (!conceptos.isEmpty()) {
			throw new ContratoException(new DefinitionMessage() {
				@Override
				public String getMessage() {
					return validacionMaximosFinal;
				}

				@Override
				public String toString() {
					return ErroresEnum.CANTIDAD_DE_SERVICIOS_CONVENIO.name();
				}

				@Override
				public String getClave() {
					return "050";
				}
			});
		}
	}

	private Usuario obtenerUsuario() {
		return session.retornarUsuario().orElseThrow(() -> new ContratoException(ErroresEnum.USUARIO_NO_ENCONTRADO));
	}

	private String ultimaModificacion() {
		Usuario usuario = obtenerUsuario();
		return usuario.getNombre() + " " + formatearFecha(horaActual());
	}
	
	public String formatearFecha(LocalDateTime fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return fecha.format(formatter);
    }

	private LocalDateTime horaActual() {
		ZoneId zoneId = ZoneId.of("America/Mexico_City");
		ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
		return zonedDateTime.toLocalDateTime();
	}

	// Métodos auxiliares compartidos
	public int contarMeses(String meses) {
		return (meses == null || meses.trim().isEmpty()) ? 0 : meses.split(PATRON).length - 2;
	}

	private Map<Integer, List<String>> leerExcel(String base64Excel) throws IOException {
		byte[] decodedBytes = Base64.getDecoder().decode(base64Excel);
		Map<Integer, List<String>> excelData = new HashMap<>();

		// Ajuste para permitir ratios de compresión bajos ya que no se trata de un zip
	    ZipSecureFile.setMinInflateRatio(0.0001);
		
		try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(decodedBytes))) {
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			int rowNumber = 0;

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				List<String> cellValues = new ArrayList<>();
				int lastCellNum = row.getLastCellNum();

				for (int cn = 0; cn < lastCellNum; cn++) {
					Cell cell = row.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cellValues.add(getCellValueAsString(cell));
				}

				excelData.put(rowNumber, cellValues);
				rowNumber++;
			}
		}
		return excelData;
	}

	private String getCellValueAsString(Cell cell) {
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			return (cell.getNumericCellValue() == (int) cell.getNumericCellValue())
					? String.valueOf((int) cell.getNumericCellValue())
					: String.valueOf(cell.getNumericCellValue());
		case BLANK:
			return "0";
		default:
			return "";
		}
	}

	public BigDecimal[][] convertExcelToMatrix(String base64Excel, int cont) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Excel);

     // Ajuste para permitir ratios de compresión bajos ya que no se trata de un zip
	    ZipSecureFile.setMinInflateRatio(0.0001);
        
        try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(decodedBytes))) {
            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();
            BigDecimal[][] matrix = new BigDecimal[rowCount - 1][cont];

            for (int r = 1; r < rowCount; r++) {
                Row row = sheet.getRow(r);
                if (row != null) {
                    for (int c = 0; c < cont; c++) {
                        Cell cell = row.getCell(c + 2);
                        matrix[r - 1][c] = obtenerValorNumerico(cell);
                    }
                }
            }

            return matrix;
        }
    }

    private BigDecimal obtenerValorNumerico(Cell cell) {
        if (cell == null) {
            return BigDecimal.ZERO; 
        }

        switch (cell.getCellType()) {
            case NUMERIC:
                BigDecimal valor = BigDecimal.valueOf(cell.getNumericCellValue());
                return ajustarDecimales(valor);
            
            case STRING:
                try {
                    BigDecimal valorTexto = new BigDecimal(cell.getStringCellValue().trim());
                    return ajustarDecimales(valorTexto);
                } catch (NumberFormatException e) {
                    return BigDecimal.ZERO; 
                }
            
            default:
                return BigDecimal.ZERO; 
        }
    }

    private BigDecimal ajustarDecimales(BigDecimal valor) {
        if (valor.scale() > 6) {
            return valor.setScale(6, RoundingMode.DOWN);
        } else if (valor.stripTrailingZeros().scale() <= 0) {
            return valor.setScale(0, RoundingMode.HALF_UP); 
        } else {
            return valor.setScale(valor.scale(), RoundingMode.UNNECESSARY);
        }
    }


	@Override
	public String exportarExcel(Long idContrato) throws IOException {
		// **
		ContratoModel contrato = contratoRepository.findByIdContrato(idContrato)
				.orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));
		VigenciaMontosModel vigencia = contrato.getVigencia();
		LocalDateTime fechaTermino = null;
		LocalDateTime fechaInicio = null;
		if (vigencia != null) {
			fechaTermino = vigencia.getFechaFinVigenciaContrato();
			fechaInicio = vigencia.getFechaInicioVigenciaContrato();
		}
		Optional<CasoNegocioModel> casoNegocio = casoNegocioRepository
				.findByContratoModelIdContratoAndEstatusTrue(idContrato);

		StringBuilder resultado = new StringBuilder(START_SERVICE_REGISTRY);
		List<CasoNegocioServicioModel> lista = servicioCasoContratoRepository
				.findByCasoNegocioContratoModelIdContratoOrderByServicioContratoOrden(idContrato);

		for (CasoNegocioServicioModel srv : lista) {
			resultado.append("|").append(srv.getIdCasoNegocioServicio());
		}



		// pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


		// TipoSeccionPista.CONTRATOS_PROYECCION_CASO_NEGOCIO.getIdSeccionPista(),


		// Constantes.getAtributosConvenioModificatorio()[0] + contrato.getIdContrato() + resultado,


		// Optional.empty());

		if (casoNegocio.isPresent()) {
			return obtenerLayoutGenerico(idContrato, fechaInicio, fechaTermino, casoNegocio.get().getVolumeria(), true);
		} else {
			return exportarCabeceras(fechaInicio, fechaTermino);
		}

	}

	private String exportarCabeceras(LocalDateTime fechaInicio, LocalDateTime fechaTermino) {
		String meses = "";
		if (fechaInicio == null || fechaTermino == null) {
			meses = obtenerCabecerasSinMeses();
		} else {
			meses = obtenerMeses(fechaInicio, fechaTermino);
		}
		consumer.inializar("Layout base");
		consumer.agregarCabeceras(meses);
		byte[] layout = consumer.cerrarBytes();
		return Base64.getEncoder().encodeToString(layout);
	}

	@Override
	public CasoNegocioResponseDto verCasoNegocio(Long idContrato) throws IOException {
		Optional<CasoNegocioModel> casoNegocio = casoNegocioRepository
				.findByContratoModelIdContratoAndEstatusTrue(idContrato);
		CasoNegocioResponseDto caso = new CasoNegocioResponseDto();
		if (casoNegocio.isPresent()) {
			int col = casoNegocio.get().getVolumeria()[0].length + 2;
			int row = casoNegocio.get().getVolumeria().length + 1;
			String base = obtenerLayout(idContrato);
			String excel = procesarExcel(base, 0, row, 0, col);
			caso.setMapa(leerExcel(excel));
			caso.setCargado(true);
		} else {
			caso.setCargado(false);
		}
		return caso;
	}

	public String procesarExcel(String base64Excel, int startRow, int numRows, int startCol, int numCols)
			throws IOException {
		byte[] decodedBytes = Base64.getDecoder().decode(base64Excel);

		// Ajuste para permitir ratios de compresión bajos ya que no se trata de un zip
	    ZipSecureFile.setMinInflateRatio(0.0001);
		
		try (Workbook originalWorkbook = new XSSFWorkbook(new ByteArrayInputStream(decodedBytes));
				Workbook newWorkbook = new XSSFWorkbook()) {

			Sheet originalSheet = originalWorkbook.getSheetAt(0);
			Sheet newSheet = newWorkbook.createSheet("Extracted Data");

			for (int rowIndex = startRow; rowIndex < startRow + numRows; rowIndex++) {
				Row originalRow = originalSheet.getRow(rowIndex);
				if (originalRow == null)
					continue;

				Row newRow = newSheet.createRow(rowIndex - startRow);

				for (int colIndex = startCol; colIndex < startCol + numCols; colIndex++) {
					Cell originalCell = originalRow.getCell(colIndex);
					Cell newCell = newRow.createCell(colIndex - startCol);

					if (originalCell != null) {
						newCell.setCellValue(getCellValueAsString(originalCell));

						CellStyle newCellStyle = newWorkbook.createCellStyle();
						newCellStyle.cloneStyleFrom(originalCell.getCellStyle());
						newCell.setCellStyle(newCellStyle);

						if (originalCell.getCellComment() != null) {
							newCell.setCellComment(newCell.getSheet().createDrawingPatriarch()
									.createCellComment(originalCell.getCellComment().getClientAnchor()));
						}
					} else {
						newCell.setCellValue("");
					}
				}
			}

			try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
				newWorkbook.write(outputStream);
				byte[] newExcelBytes = outputStream.toByteArray();
				return Base64.getEncoder().encodeToString(newExcelBytes);
			}
		}
	}

	@Override
	public String exportarExcelConvenio(Long idConvenioModificatorio) throws IOException {
		ConvenioModificatorioModel convenio = convenioRepository.findById(idConvenioModificatorio)
				.orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));
		ContratoModel contrato = convenio.getContratoModel();
		LocalDateTime fechaTermino = convenio.getFechaFin();
		LocalDateTime fechaInicio = contrato.getVigencia().getFechaInicioVigenciaContrato();
		Optional<CasoNegocioConvenioModel> casoNegocio = casoNegocioConvenioRepository
				.findByConvenioModificatorioModelIdConvenioModificatorioAndEstatusTrue(idConvenioModificatorio);

		StringBuilder resultado = new StringBuilder(START_SERVICE_REGISTRY);
		List<CasoNegocioServicioConvenioModel> lista = servicioCasoConvenioRepository
				.findByCasoNegocioConvenioModificatorioModelIdConvenioModificatorioOrderByServicioConvenioServicioBaseOrden(idConvenioModificatorio);

		for (CasoNegocioServicioConvenioModel srv : lista) {
			resultado.append("|").append(srv.getIdCasoNegocioServicio());
		}



		// pistaService.guardarPista(ModuloPista.CONVENIO_MODIFICATORIO.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


		// TipoSeccionPista.PROYECCION_CONVENIO_MODIFICATORIO.getIdSeccionPista(),


		// Constantes.getAtributosConvenioModificatorio()[0] + contrato.getIdContrato()


		// + Constantes.getAtributosConvenioModificatorio()[1] + convenio.getIdConvenioModificatorio()


		// + resultado,


		// Optional.empty());

		if (casoNegocio.isPresent()) {
			return obtenerLayoutGenerico(convenio.getIdContrato(), fechaInicio, fechaTermino,
					casoNegocio.get().getVolumeria(), true);
		} else {
			return exportarCabeceras(fechaInicio, fechaTermino);
		}
	}

	@Override
	public CasoNegocioResponseDto verCasoNegocioConvenio(Long idConvenioModificatorio) throws IOException {
		Optional<CasoNegocioConvenioModel> casoNegocio = casoNegocioConvenioRepository
				.findByConvenioModificatorioModelIdConvenioModificatorioAndEstatusTrue(idConvenioModificatorio);
		CasoNegocioResponseDto caso = new CasoNegocioResponseDto();
		if (casoNegocio.isPresent()) {
			int col = casoNegocio.get().getVolumeria()[0].length + 2;
			int row = casoNegocio.get().getVolumeria().length + 1;
			String base = obtenerLayoutConvenio(idConvenioModificatorio);
			String excel = procesarExcel(base, 0, row, 0, col);
			caso.setMapa(leerExcel(excel));
			caso.setCargado(true);
		} else {
			caso.setCargado(false);
		}
		return caso;
	}

}
