package com.sisecofi.contratos.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.sisecofi.contratos.util.enums.ErroresEnum;
import com.sisecofi.contratos.util.exception.ContratoException;
import com.sisecofi.contratos.util.validator.ContratoValidator;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.sisecofi.contratos.dto.CargaLayoutInformesDto;
import com.sisecofi.libreria.comunes.model.contratos.AtrasoPrestacionModel;
import com.sisecofi.contratos.service.ServicioCargaLayoutInformes;
import com.sisecofi.contratos.microservicios.CatalogoMicroservicio;
import com.sisecofi.contratos.microservicios.DevengadosMicroservicio;
import com.sisecofi.contratos.repository.contrato.AtrasoPresentacionRepository;
import com.sisecofi.contratos.repository.contrato.InformesDocumentalesPeriodicosRepository;
import com.sisecofi.contratos.repository.contrato.InformesDocumentalesServiciosRepository;
import com.sisecofi.contratos.repository.contrato.InformesDocumentalesUnicaVezRepository;
import com.sisecofi.contratos.repository.contrato.NivelesServicioSlaRepository;
import com.sisecofi.contratos.repository.contrato.PenaContractualContratoRepository;
import com.sisecofi.contratos.service.ServicioInformesDocumentalesPeriodicos;
import com.sisecofi.contratos.service.ServicioInformesDocumentalesServicios;
import com.sisecofi.contratos.service.ServicioInformesDocumentalesUnicaVez;
import com.sisecofi.contratos.service.ServicioNivelesServicioSLA;
import com.sisecofi.contratos.service.ServicioPenasContractuales;
import com.sisecofi.contratos.service.ServicioSeccionAtrasoPresentacion;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesPeriodicosModel;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesUnicaVezModel;
import com.sisecofi.libreria.comunes.model.contratos.NivelesServicioSLAModel;
import com.sisecofi.libreria.comunes.model.contratos.PenaContractualContratoModel;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesServiciosModel;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ServicioCargaLayoutInformesImpl implements ServicioCargaLayoutInformes {

	private static final String LOG_ERROR_EXCEL = "Ocurrio un error al leer el archivo Excel";

	private final ServicioInformesDocumentalesUnicaVez servicioDoc;
	private final ServicioInformesDocumentalesPeriodicos servicioPeriodicos;
	private final ServicioInformesDocumentalesServicios servicioServicios;
	private final ServicioNivelesServicioSLA servicioNiveles;
	private final ServicioSeccionAtrasoPresentacion seccionAtraso;
	private final ServicioPenasContractuales servicioPenas;
	private final CatalogoMicroservicio catalogoMicroservicio;
	private final ContratoValidator contratoValidator;
	private final InformesDocumentalesUnicaVezRepository informesDao;
	private final InformesDocumentalesPeriodicosRepository informesPeriodicosDao;
	private final InformesDocumentalesServiciosRepository informesServiciosDao;
	private final NivelesServicioSlaRepository nivelesServicioDao;
	private final AtrasoPresentacionRepository atrasoPresentacionRepository;
	private final PenaContractualContratoRepository penaContractualContratoRepository;
	private final DevengadosMicroservicio devengadosMicroservicio;

	@Override
	public String cargaLayoutInformes(CargaLayoutInformesDto cargaLayout) {
		StringBuilder resultado = new StringBuilder();

		if (cargaLayout.getTipoLayout().equals("informesDocUni")) {
			resultado.append(tranformacionInformesDocUniVez(cargaLayout));

		} else if (cargaLayout.getTipoLayout().equals("informesDocPer")) {
			resultado.append(tranformacionInformesDocumentalesPeriodicos(cargaLayout));

		} else if (cargaLayout.getTipoLayout().equals("informesDocSer")) {
			resultado.append(tranformacionInformesDocumentalesServicios(cargaLayout));

		} else if (cargaLayout.getTipoLayout().equals("nivelesSla")) {
			resultado.append(tranformacionNivelesServicioSLA(cargaLayout));

		} else if (cargaLayout.getTipoLayout().equals("atrasoPrestacion")) {
			resultado.append(tranformacionInformesAtrasoPrestacion(cargaLayout));

		} else if (cargaLayout.getTipoLayout().equals("penasContractuales")) {
			resultado.append(tranformacionInformesPenasContractuales(cargaLayout));
		}
		return resultado.toString();

	}

	private int obtenerNumeroDeColumnas(Sheet sheet) {
		Row headerRow = sheet.getRow(0);
		if (headerRow == null) {
			throw new ContratoException(ErroresEnum.ERROR_ESTRUCTURA_LAYOUT);
		}

		return headerRow.getLastCellNum();
	}

	@SuppressWarnings("resource")
	private String tranformacionInformesDocUniVez(CargaLayoutInformesDto cargaLayout) {
		List<InformesDocumentalesUnicaVezModel> listaInformes = new ArrayList<>();
		Long idContrato = cargaLayout.getIdContrato();
		List<String> nombreInformeDoc = new ArrayList<>();

		Long penas = devengadosMicroservicio.validarPenas(idContrato, 1);
		validarPenas(penas);

		informesDao.updateEstatusToFalseByIdContrato(idContrato);

		try (XSSFWorkbook workbook = new XSSFWorkbook(cargaLayout.getArchivo().getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0);
			int totalColumnas = obtenerNumeroDeColumnas(sheet);

			if (totalColumnas != 6) {
				throw new ContratoException(ErroresEnum.ERROR_ESTRUCTURA_LAYOUT);
			}
			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				if (row != null && isCellValid(row.getCell(1))) {
					InformesDocumentalesUnicaVezModel informe = new InformesDocumentalesUnicaVezModel();
					informe.setFase(getCellValueAsString(row.getCell(1))); // Usa un método auxiliar
					log.info("fase: {}", getCellValueAsString(row.getCell(1)));
					informe.setInformeDocumental(getCellValueAsString(row.getCell(2)));
					informe.setFechaEntrega(getCellValueAsString(row.getCell(3)));
					informe.setPenasDeduccionesAplicables(getCellValueAsString(row.getCell(4)));
					informe.setDescripcion(getCellValueAsString(row.getCell(5)));
					informe.setIdContrato(idContrato);
					listaInformes.add(informe);

					contratoValidator.validarModeloInformeDocUnicaVez(informe);

					if (isCellValid(row.getCell(2))) {
						nombreInformeDoc.add(getCellValueAsString(row.getCell(2))); // Validación adicional
					}
				}
			}
		} catch (ContratoException e) {
			throw e;
		} catch (Exception e) {
			log.error(LOG_ERROR_EXCEL);
			throw new ContratoException(ErroresEnum.ERROR_ESTRUCTURA_LAYOUT);
		}

		contratoValidator.validarInformesDocumentalesUnicaVez(nombreInformeDoc, idContrato);
		servicioDoc.guardarInformeDocumentalUnicaVez(listaInformes);

		return "OK";
	}

	private boolean isCellValid(Cell cell) {
		if (cell == null) {
			throw new ContratoException(ErroresEnum.DATOS_OBLIGATORIOS);
		}

		switch (cell.getCellType()) {
			case STRING:
				return !cell.getStringCellValue().isBlank();

			case NUMERIC:
				return true;
			case BOOLEAN:
				return true;
			case FORMULA:
				try {
					CellType formulaResultType = cell.getCachedFormulaResultType();
					return formulaResultType == CellType.STRING ? !cell.getStringCellValue().isBlank()
							: formulaResultType == CellType.NUMERIC;
				} catch (Exception e) {
					return false;
				}

			case BLANK:
				return false;
			case ERROR:
				return false;
			default:
				return false;
		}
	}

	private String getCellValueAsString(Cell cell) {
		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {
			case STRING:
				return cell.getStringCellValue().trim();

			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					// Si la celda es una fecha, la formateamos correctamente
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					return dateFormat.format(cell.getDateCellValue());
				} else {
					double numericValue = cell.getNumericCellValue();
					// Si el número es entero, devolverlo sin decimales
					if (numericValue == Math.floor(numericValue)) {
						return String.valueOf((long) numericValue);
					} else {
						return String.valueOf(numericValue);
					}
				}

			case BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());

			case BLANK:
			default:
				return "";
		}
	}

	@SuppressWarnings("resource")
	private String tranformacionInformesDocumentalesPeriodicos(CargaLayoutInformesDto cargaLayout) {
		List<InformesDocumentalesPeriodicosModel> listaInformes = new ArrayList<>();
		List<String> informes = new ArrayList<>();
		Long idcontrato = cargaLayout.getIdContrato();

		Long penas = devengadosMicroservicio.validarPenas(idcontrato, 2);
		validarPenas(penas);

		informesPeriodicosDao.updateEstatusToFalseByIdContrato(idcontrato);

		try (XSSFWorkbook workbook = new XSSFWorkbook(cargaLayout.getArchivo().getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0);
			int totalColumnas = obtenerNumeroDeColumnas(sheet);

			if (totalColumnas != 5) {
				throw new ContratoException(ErroresEnum.ERROR_ESTRUCTURA_LAYOUT);
			}

			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				if (row != null) {
					InformesDocumentalesPeriodicosModel informe = new InformesDocumentalesPeriodicosModel();
					informe.setInformeDocumental(getCellValueAsString(row.getCell(1)));
					informe.setIdPeriodicidad(obtenerIdPeriodicidad(getCellValueAsString(row.getCell(2))));
					informe.setPenaConvencionalDeductiva(getCellValueAsString(row.getCell(3)));
					informe.setDescripcion(getCellValueAsString(row.getCell(4)));
					informe.setIdContrato(idcontrato);

					contratoValidator.validarModeloInfoDocPeriodico(informe);
					listaInformes.add(informe);

					if (isCellValid(row.getCell(1))) {
						informes.add(getCellValueAsString(row.getCell(1)));
					}
				}
			}
		} catch (ContratoException e) {
			throw e;
		} catch (Exception e) {
			log.error(LOG_ERROR_EXCEL);
			throw new ContratoException(ErroresEnum.ERROR_AL_GUARDAR);
		}

		contratoValidator.validarInformesDocumentalesPeriodicos(informes, idcontrato);
		servicioPeriodicos.guardarInformeDocumentalPeridicos(listaInformes);
		return "OK";
	}

	@SuppressWarnings("resource")
	private String tranformacionInformesDocumentalesServicios(CargaLayoutInformesDto cargaLayout) {
		List<InformesDocumentalesServiciosModel> listaInformes = new ArrayList<>();
		Long idContrato = cargaLayout.getIdContrato();

		Long penas = devengadosMicroservicio.validarPenas(idContrato, 3);
		validarPenas(penas);

		informesServiciosDao.updateEstatusToFalseByIdContrato(idContrato);
		List<String> informes = new ArrayList<>();

		try (XSSFWorkbook workbook = new XSSFWorkbook(cargaLayout.getArchivo().getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0);
			int totalColumnas = obtenerNumeroDeColumnas(sheet);

			if (totalColumnas != 6) {
				throw new ContratoException(ErroresEnum.ERROR_ESTRUCTURA_LAYOUT);
			}
			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				if (row != null && isCellValid(row.getCell(1))) {
					InformesDocumentalesServiciosModel informe = new InformesDocumentalesServiciosModel();
					informe.setInformeDocumental(getCellValueAsString(row.getCell(1)));
					informe.setIdPeriodicidad(obtenerIdPeriodicidad(getCellValueAsString(row.getCell(2))));
					informe.setFechaEntrega(getCellValueAsString(row.getCell(3)));
					informe.setPenasDeduccionesAplicables(getCellValueAsString(row.getCell(4)));
					informe.setDescripcion(getCellValueAsString(row.getCell(5)));
					informe.setIdContrato(idContrato);

					contratoValidator.validarModeloInfoDocServicios(informe);
					listaInformes.add(informe);

					if (isCellValid(row.getCell(1))) {
						informes.add(getCellValueAsString(row.getCell(1)));
					}
				}
			}
		} catch (IOException | IllegalArgumentException | NullPointerException e) {
			log.error(LOG_ERROR_EXCEL);
		} catch (ContratoException e) {
			log.error(LOG_ERROR_EXCEL);
			throw e;
		}

		contratoValidator.validarInfoDocServicios(informes, idContrato);
		servicioServicios.guardarInformeDocumentalServicios(listaInformes);
		return "OK";
	}

	@SuppressWarnings("resource")
	private String tranformacionNivelesServicioSLA(CargaLayoutInformesDto cargaLayout) {
		List<NivelesServicioSLAModel> listaInformes = new ArrayList<>();
		Long idContrato = cargaLayout.getIdContrato();

		nivelesServicioDao.updateEstatusToFalseByIdContrato(idContrato);

		List<String> informes = new ArrayList<>();
		try (XSSFWorkbook workbook = new XSSFWorkbook(cargaLayout.getArchivo().getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0);
			int totalColumnas = obtenerNumeroDeColumnas(sheet);

			if (totalColumnas != 5) {
				throw new ContratoException(ErroresEnum.ERROR_ESTRUCTURA_LAYOUT);
			}
			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				if (row != null) {
					NivelesServicioSLAModel informe = new NivelesServicioSLAModel();
					informe.setSla(getCellValueAsString(row.getCell(1)));
					informe.setDeduccionesAplicables(getCellValueAsString(row.getCell(2)));
					informe.setObjectivoMinimo(getCellValueAsString(row.getCell(3)));
					informe.setDescripcion(getCellValueAsString(row.getCell(4)));
					informe.setIdContrato(cargaLayout.getIdContrato());
					listaInformes.add(informe);

					contratoValidator.validarModeloServiciosSla(informe);

					if (isCellValid(row.getCell(1))) {
						informes.add(getCellValueAsString(row.getCell(1)));
					}
				}
			}

		} catch (IOException | IllegalArgumentException | NullPointerException e) {
			log.error(LOG_ERROR_EXCEL);
		} catch (ContratoException e) {
			log.error(LOG_ERROR_EXCEL);
			throw e;
		}

		contratoValidator.validarServiciosSla(informes, idContrato);
		servicioNiveles.guardarNivelesServicioSLA(listaInformes);
		return "OK";
	}

	@SuppressWarnings("resource")
	private String tranformacionInformesAtrasoPrestacion(CargaLayoutInformesDto cargaLayout) {
		List<AtrasoPrestacionModel> listaInformes = new ArrayList<>();

		Long penas = devengadosMicroservicio.validarPenas(cargaLayout.getIdContrato(), 4);
		validarPenas(penas);

		atrasoPresentacionRepository.updateEstatusToFalseByIdContrato(cargaLayout.getIdContrato());

		List<String> informes = new ArrayList<>();
		try (XSSFWorkbook workbook = new XSSFWorkbook(cargaLayout.getArchivo().getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0);
			int totalColumnas = obtenerNumeroDeColumnas(sheet);

			if (totalColumnas != 4) {
				throw new ContratoException(ErroresEnum.ERROR_ESTRUCTURA_LAYOUT);
			}
			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				if (row != null) {
					AtrasoPrestacionModel informe = new AtrasoPrestacionModel();
					informe.setDescripcion(getCellValueAsString(row.getCell(1)));
					informe.setFechaPrestacion(getCellValueAsString(row.getCell(2)));
					informe.setPenasDeducciones(getCellValueAsString(row.getCell(3)));

					informe.setIdContrato(cargaLayout.getIdContrato());

					listaInformes.add(informe);

					if (isCellValid(row.getCell(3))) {
						informes.add(getCellValueAsString(row.getCell(3)));
					}

					contratoValidator.validarModeloAtrasoPrestacion(informe);
				}
			}

		} catch (IOException | IllegalArgumentException | NullPointerException e) {
			log.error(LOG_ERROR_EXCEL);
		} catch (ContratoException e) {
			log.error(LOG_ERROR_EXCEL);
			throw e;
		}

		// contratoValidator.validarAtrasoPrestacion(informes, idContrato); tabla de
		// dictámenes aún no cuenta con el id de atraso
		seccionAtraso.guardarAtrasoPresentacion(listaInformes);
		return "OK";
	}

	@SuppressWarnings("resource")
	private String tranformacionInformesPenasContractuales(CargaLayoutInformesDto cargaLayout) {
		List<PenaContractualContratoModel> listaInformes = new ArrayList<>();
		Long idContrato = cargaLayout.getIdContrato();

		Long penas = devengadosMicroservicio.validarPenas(idContrato, 5);
		validarPenas(penas);

		penaContractualContratoRepository.updateEstatusToFalseByIdContrato(idContrato);

		List<String> informes = new ArrayList<>();

		try (XSSFWorkbook workbook = new XSSFWorkbook(cargaLayout.getArchivo().getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0);
			int totalColumnas = obtenerNumeroDeColumnas(sheet);

			if (totalColumnas != 5) {
				throw new ContratoException(ErroresEnum.ERROR_ESTRUCTURA_LAYOUT);
			}
			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				if (row != null && isCellValid(row.getCell(1))) {
					PenaContractualContratoModel informe = new PenaContractualContratoModel();
					informe.setInformeDocumentoConcepto(getCellValueAsString(row.getCell(1)));
					informe.setDescripcion(getCellValueAsString(row.getCell(2)));
					informe.setPlazoEntrega(getCellValueAsString(row.getCell(3)));
					informe.setPenaAplicable(getCellValueAsString(row.getCell(4)));
					listaInformes.add(informe);

					if (isCellValid(row.getCell(1))) {
						informes.add(getCellValueAsString(row.getCell(1)));
					}
					contratoValidator.validarModeloPenasContractuales(informe);
				}
			}

		} catch (IOException | IllegalArgumentException | NullPointerException e) {
			log.error(LOG_ERROR_EXCEL);
		} catch (ContratoException e) {
			log.error(LOG_ERROR_EXCEL);
			throw e;
		}

		contratoValidator.validarPenasContractuales(informes, idContrato);
		servicioPenas.crearPena(listaInformes, cargaLayout.getIdContrato());
		return "OK";
	}

	private Integer obtenerIdPeriodicidad(String value) {
		if (value == null || value.isBlank()) {
			throw new ContratoException(ErroresEnum.DATOS_OBLIGATORIOS);
		}
		String json = String.format(Constantes.PERIODICIDAD_BUSQUEDA, value);
		List<BaseCatalogoModel> lista = catalogoMicroservicio
				.obtenerInformacionCatalogoCampoEspecifico(CatalogosComunes.PERIODICIDAD.getIdCatalogo(), json);
		if (!lista.isEmpty()) {
			return lista.get(0).getPrimaryKey();
		} else {
			throw new ContratoException(ErroresEnum.ERROR_ESTRUCTURA_LAYOUT);
		}
	}

	private void validarPenas(Long cont) {
		if (cont > 0) {
			throw new ContratoException(ErroresEnum.ERROR_DICTAMENES_ASOCIADOS);
		}
	}
}
