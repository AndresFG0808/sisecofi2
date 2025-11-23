package com.sisecofi.admingeneral.service.plantillador.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.model.plantillador.ContenidoSubPlantilladorModel;
import com.sisecofi.libreria.comunes.model.plantillador.SubPlantilladorModel;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
import com.sisecofi.admingeneral.model.plantillador.OutRenderDocument;
import com.sisecofi.admingeneral.repository.plantillador.ContenidoPlantillaRepository;
import com.sisecofi.admingeneral.repository.plantillador.ContenidoSubPlantilladorRespository;
import com.sisecofi.admingeneral.repository.plantillador.PlantillaRespository;
import com.sisecofi.admingeneral.repository.plantillador.SubPlantilladorRespository;
import com.sisecofi.admingeneral.service.plantillador.HtmlToExcelService;
import com.sisecofi.admingeneral.util.Base64Util;
import com.sisecofi.admingeneral.util.CreatePDF;
import com.sisecofi.admingeneral.util.PlantillaUtil;
import com.sisecofi.admingeneral.util.exception.PlantilladorException;
import com.sisecofi.libreria.comunes.model.plantillador.ContenidoPlantilladorModel;
import com.sisecofi.libreria.comunes.model.plantillador.EtiquetaPlantilladorModel;
import com.sisecofi.libreria.comunes.model.plantillador.PlantilladorModel;
import com.sisecofi.htmlexcel.writer.excel.ExcelExporter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class HtmlToExcelServiceImpl implements HtmlToExcelService {

	private final SubPlantilladorRespository subPlantilladorRespository;
	private final PlantillaRespository plantillaRespository;
	private final ContenidoSubPlantilladorRespository contenidoSubPlantilladorRespository;
	private final ContenidoPlantillaRepository contenidoPlantillaRepository;
	private static final String DIAGONAL = "/";
	private static final String PATH = DIAGONAL + "sisecofi" + DIAGONAL;
	private static final String PATHEADER = PATH + "header/";
	private static final String NAMEFILE = "html-exporter-";
	private static final String EXCELEXTENSION = ".xlsx";
	private static final String INICIOCONTENEDOREXCEL = "<html><head><style>td {border:thin solid #dddddd;}</style></head><body>";
	private static final String FINCONTENEDOREXCEL = "</body></html>";
	private static final String HEADER_PNG = "header1.png";
	private static final String PATTERN_ERROR_IMAGE = "Error guardando la imagen en la ruta: {}. Detalle del error: {}";
	private static final String PATTERN_SUCCESS_IMAGE = "Imagen guardada exitosamente en la ruta: {}";
	private static final String PATTERN_BASE64_IMAGE = "src=\"data:image/png;base64,";
	private static final String PATTERN_TOTAL_IMAGE = "Total de imagenes: {}";
	private static final String NO_ENCONTRO_SUBPL = "No se encontró el subplantillador con ID: {}";
	private static final String TD = "</td>";
	private static final String TR = "</tr>";
	private static final String ETIQUETAS_CONTENIDO = "</tbody></table></figure>";
	private static final String ETIQUETA_LARGA = "<html><head><meta charset=\"UTF-8\"><style>table{border-collapse:collapse;}</style></head><body><div style=\"width:800px;\">";
	private static final String ETIQUETA = "lista: {}";
	private static final String STRONG = "<strong>";
	private static final String STRONG_CIERRE = "</strong>";
	private static final String CANTIDAD = "${Cantidad";
	private static final String TR_CIERRE = "</tr>";
	private static final String CANTIDAD_TOTAL_SERVICIO = "Cantidad_total_servicio";
	private static final String SERVICIO = "Servicio";
	private static final String CANTIDAD_MEDIDA = "cantidad_de_medida";
	private static final String PRECIO_UNITARIO = "Precio_unitario";
	private static final String TOTAL = "Total";
	private static final String NBSP = "&nbsp;";
	private static final String HEADER_PROFORMA = "headerProforma";
	private static final String CONTENIDO_PROFORMA = "contenidoProforma";
	private static final String HEADER_NOTAS = "headerNotas";
	private static final String HEADER_PENALIZACION = "headerPenalizacion";
	private static final String CONTENIDO_NOTAS = "contenidoNotas";
	private static final String CONTENIDO_PENALIZACION = "contenidoPenalizacion";

	@Override
	public byte[] htmlToExcel(Long idSubPlantillador, Map<String, String> datos) {
		Optional<SubPlantilladorModel> plantilladorOpt = subPlantilladorRespository.findById(idSubPlantillador);
		if (!plantilladorOpt.isPresent()) {
			log.warn(NO_ENCONTRO_SUBPL, idSubPlantillador);
			return new byte[] {};
		}

		try {
			List<ContenidoSubPlantilladorModel> lista = obtenerContenidoSubPlantillador(idSubPlantillador);
			String headerProforma = reemplazarEtiquetas(lista.get(0).getHeader(), datos);
			String headerNotas = reemplazarEtiquetas(lista.get(1).getHeader(), datos);
			String headerPenalizacion = reemplazarEtiquetas(lista.get(2).getHeader(), datos);

			String contenidoProforma = reemplazarEtiquetas(lista.get(0).getContenido(), datos);
			String contenidoNotas = reemplazarEtiquetas(lista.get(1).getContenido(), datos);
			String contenidoPenalizacion = reemplazarEtiquetas(lista.get(2).getContenido(), datos);

			String htmlCompleto = generarHtmlCompleto(headerProforma + contenidoProforma, headerNotas + contenidoNotas,
					headerPenalizacion + contenidoPenalizacion,
					guardarImagenes(headerProforma, headerNotas, headerPenalizacion));

			return generarExcel(htmlCompleto);
		} catch (Exception e) {
			log.error("Error al generar Excel en htmlToExcel");
			return new byte[] {};
		}
	}

	@Override
	public byte[] htmlToExcelRCP(Long idPlantillador, Map<String, String> datosGenerales,
			List<Map<String, String>> listaDeMapas) {
		Optional<PlantilladorModel> plantilladorOpt = plantillaRespository.findById(idPlantillador);
		if (!plantilladorOpt.isPresent()) {
			log.warn("No se encontró el plantillador con ID: {}", idPlantillador);
			return new byte[] {};
		}

		try {
			ContenidoPlantilladorModel contenidoPlantilla = obtenerContenidoPlantilla(idPlantillador);
			String header = reemplazarEtiquetas(contenidoPlantilla.getHeader(), datosGenerales);
			String contenido = procesarContenidoConFilas(contenidoPlantilla.getContenido(), listaDeMapas);
			String footer = "";

			String htmlCompleto = generarHtmlCompleto(header, contenido, footer, null);
			return generarExcel(htmlCompleto);
		} catch (Exception e) {
			log.error("Error al generar Excel en htmlToExcelRCP");
			return new byte[] {};
		}
	}

	private String procesarContenidoConFilas(String contenido, List<Map<String, String>> listaDeMapas) {
		StringBuilder filasGeneradas = new StringBuilder();
		int contador = 1;

		for (Map<String, String> datos : listaDeMapas) {
			filasGeneradas.append("<tr><td style='border:1px solid;'>").append(contador).append(TD);
			datos.forEach(
					(key, value) -> filasGeneradas.append("<td style='border:1px solid;'>").append(value).append(TD));
			filasGeneradas.append(TR);
			contador++;
		}

		return contenido + filasGeneradas;
	}

	@Override
	public byte[] cierreProyectoExcel(Long idSubPlantillador, Map<String, String> datosGenerales,
			List<Map<String, String>> listaDeMapas) {
		Optional<PlantilladorModel> plantillador = plantillaRespository.findById(idSubPlantillador);
		if (plantillador.isEmpty()) {
			return new byte[] {};
		}

		try {
			Optional<ContenidoPlantilladorModel> lista = contenidoPlantillaRepository
					.findByplantillaModelPlantilladorModelIdPlantillador(idSubPlantillador);
			String header = "";
			String contenido = "";
			if (lista.isPresent()) {
				header = lista.get().getHeader().replace("rowspan=\"5\"", "colspan=\"3\" rowspan=\"5\"").replace(
						"style=\"text-align:center;width:50%;\"",
						"style=\"text-align: center; width: 50%\" colspan=\"5\"");
				contenido = lista.get().getContenido().replace(ETIQUETAS_CONTENIDO, "")
						.replace("style=\"border-collapse:collapse;\"", "")
						.replace("<tbody>", "<tbody><tr><td>&nbsp;</td></tr>").replace(STRONG, "")
						.replace(STRONG_CIERRE, "");
			}

			StringBuilder filasGeneradas = new StringBuilder();
			int contador = 1;

			for (Map<String, String> datos : listaDeMapas) {
				if (contador == 1) {
					contenido = reemplazarEtiquetasContenido(contenido, datos);
				} else {
					filasGeneradas.append("<tr><td style=\"border:1px solid black; text-align: center\">")
							.append(contador).append(TD);
					for (Map.Entry<String, String> entry : datos.entrySet()) {
						filasGeneradas.append("<td style=\"border:1px solid black; text-align: center\">")
								.append(entry.getValue()).append(TD);
					}
					filasGeneradas.append(TR);
				}
				contador++;
			}

			String contenidoFinal = contenido + filasGeneradas.append(ETIQUETAS_CONTENIDO);

			for (Map.Entry<String, String> entry : datosGenerales.entrySet()) {
				String etiqueta = "${" + entry.getKey() + "}";
				header = header.replace(etiqueta, entry.getValue());
				contenidoFinal = contenidoFinal.replace(etiqueta, entry.getValue());
			}

			StringBuilder htmlCompleto = new StringBuilder();
			htmlCompleto.append(INICIOCONTENEDOREXCEL);
			sanitizadorExcel(htmlCompleto, header, contenidoFinal, "", guardarImagenes(header, contenidoFinal, ""));
			htmlCompleto.append(FINCONTENEDOREXCEL);

			ExcelExporter exporter = new ExcelExporter();
			String name = NAMEFILE.concat(String.valueOf(System.currentTimeMillis())).concat(EXCELEXTENSION);
			File tempFile = new File(PATH + name);
			log.info("Path cierreProyectoExcel: {}", tempFile.getAbsolutePath());
			log.info("codigo final: {}", htmlCompleto);

			return exporter.exportarHtmlRcp(htmlCompleto.toString());

		} catch (Exception e) {
			log.error("Error al generar excel cierreProyectoExcel");
			return new byte[] {};
		}
	}

	private String reemplazarEtiquetasContenido(String contenido, Map<String, String> datos) {
		for (Map.Entry<String, String> entry : datos.entrySet()) {
			String etiqueta = "${" + entry.getKey() + "}";
			contenido = contenido.replace(etiqueta, entry.getValue());
		}
		return contenido;
	}

	private ContenidoPlantilladorModel obtenerContenidoPlantilla(Long idSubPlantillador) throws Exception {
		return contenidoPlantillaRepository.findByplantillaModelPlantilladorModelIdPlantillador(idSubPlantillador)
				.orElseThrow(() -> new Exception("No se encontró el contenido del subplantillador"));
	}

	private String generarHtmlCompleto(String header, String contenido, String footer, List<String> images) {
		StringBuilder htmlCompleto = new StringBuilder(INICIOCONTENEDOREXCEL);
		sanitizadorExcel(htmlCompleto, header, contenido, footer, images);
		htmlCompleto.append(FINCONTENEDOREXCEL);
		return htmlCompleto.toString();
	}

	public String generarEstiloTabla(String cssBorderStyle) {
		cssBorderStyle = (cssBorderStyle != null) ? cssBorderStyle.toUpperCase() : "SOLID";
		return "border-style: " + cssBorderStyle + ";";
	}

	public String generarContenidoConFilas(List<Map<String, String>> listaDeMapas,
			List<EtiquetaPlantilladorModel> etiquetas, String cssBorderStyle) {
		StringBuilder contenidoHtml = new StringBuilder();

		for (Map<String, String> mapa : listaDeMapas) {
			contenidoHtml.append("<tr>");
			for (EtiquetaPlantilladorModel etiqueta : etiquetas) {
				String valor = mapa.get(etiqueta.getNombreEtiqueta());
				contenidoHtml.append("<td style='border:1px ").append(cssBorderStyle).append(";'>")
						.append(valor != null ? valor : " ").append(TD);
			}
			contenidoHtml.append(TR);
		}

		return contenidoHtml.toString();
	}

	@Override
	public byte[] htmlToPdfRCP(Long idSubPlantillador, List<Map<String, String>> listaDeMapas) {
		Optional<ContenidoPlantilladorModel> plantilladorOpt = contenidoPlantillaRepository.findById(idSubPlantillador);
		if (!plantilladorOpt.isPresent()) {
			log.warn(NO_ENCONTRO_SUBPL, idSubPlantillador);
			return new byte[] {};
		}

		try {
			ContenidoPlantilladorModel contenidoPlantilla = plantilladorOpt.get();
			String header = contenidoPlantilla.getHeader();
			String contenido = contenidoPlantilla.getContenido();
			String footer = "";

			List<EtiquetaPlantilladorModel> etiquetas = contenidoPlantilla.getPlantillaModelPlantilladorModel()
					.getCatTipoPlantillador().getEtiquetas();

			header = reemplazarEtiquetasEnTexto(header, etiquetas, listaDeMapas);
			contenido = reemplazarEtiquetasEnTexto(contenido, etiquetas, listaDeMapas);

			String contenidoConFilas = generarContenidoConFilas(listaDeMapas, etiquetas, "SOLID");
			String contenidoFinal = contenido + contenidoConFilas;

			return generarPDF(header, contenidoFinal, footer);
		} catch (Exception e) {
			log.error("Error al generar PDF en htmlToPdfRCP");
			return new byte[] {};
		}
	}

	private String reemplazarEtiquetasEnTexto(String texto, List<EtiquetaPlantilladorModel> etiquetas,
			List<Map<String, String>> listaDeMapas) {
		for (Map<String, String> mapa : listaDeMapas) {
			for (EtiquetaPlantilladorModel etiqueta : etiquetas) {
				String placeholder = "${" + etiqueta.getNombreEtiqueta() + "}";
				String valor = mapa.getOrDefault(etiqueta.getNombreEtiqueta(), " ");
				texto = texto.replace(placeholder, valor);
			}
		}
		return texto;
	}

	private byte[] generarPDF(String header, String contenidoFinal, String footer) {
		GeneraPDF generaPDF = new GeneraPDF();
		byte[] pdfBytes = generaPDF.generaPdf(header, contenidoFinal, footer);
		creacionPDF(pdfBytes);
		return pdfBytes;
	}

	private void creacionPDF(byte[] pdfBytes) {
	    File tempFile = null;
	    try {
	        tempFile = File.createTempFile("subplantillador_", ".pdf");
	        
	        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
	            fos.write(pdfBytes);
	        }
	        
	        log.info("PDF guardado en: {}", tempFile.getAbsolutePath());

	    } catch (IOException e) {
	        log.error("Error al guardar el PDF");
	        throw new PlantilladorException(ErroresEnum.ERROR_PDF);
	    } finally {
	        if (tempFile != null && tempFile.exists()) {
	            try {
	                Files.delete(tempFile.toPath());
	                log.info("Archivo temporal eliminado: {}", tempFile.getAbsolutePath());
	            } catch (IOException e) {
	                log.warn("No se pudo eliminar el archivo temporal: {}", tempFile.getAbsolutePath(), e);
	            }
	        }
	    }

	}


	private void sanitizadorExcel(StringBuilder htmlCompleto, String headerConHls, String contenidoConHls,
			String footerConHsl, List<String> images) {
		Optional<List<String>> imagenbase641 = Base64Util.guardarImagen(headerConHls);
		log.info("Validando imagen sanitizador excel: {}", imagenbase641.isPresent());
		String header = "";
		if (imagenbase641.isPresent()) {
			List<String> imagenes = imagenbase641.get();
			if (!imagenes.isEmpty()) {
				String img = imagenes.get(0);
				header = headerConHls
						.replace("<table>", "<table data-sheet-name='RCP' data-image-name='" + images.get(0) + "'>")
						.replace(img, "src=\"" + images.get(0) + "\"").replace("<b>", "").replace("</b>", "")
						.replace(STRONG, "").replace(STRONG_CIERRE, "");
			}
		}
		String headerSinHsl = PlantillaUtil.reemplazarHsl(header);
		String contenidoSinHls = PlantillaUtil.reemplazarHsl(contenidoConHls);
		String footerSinHls = PlantillaUtil.reemplazarHsl(footerConHsl);
		htmlCompleto.append(headerSinHsl.replace("#000000", "black"));
		htmlCompleto.append(contenidoSinHls.replace("1px", "thin").replace("#000000", "black"));
		htmlCompleto.append(footerSinHls);
	}

	private List<String> guardarImagenes(String headerProforma, String headerNotas, String headerPenalizacion)
			throws IOException {
		List<String> lista = new ArrayList<>();
		lista.addAll(procesarImagen(headerProforma, "header1-", ".jpg"));
		lista.addAll(procesarImagen(headerNotas, "header2-", ".png"));
		lista.addAll(procesarImagen(headerPenalizacion, "header3-", ".png"));
		return lista;
	}

	private List<String> procesarImagen(String header, String prefix, String extension) throws IOException {
		List<String> lista = new ArrayList<>();
		Optional<List<String>> imagenesOpt = Base64Util.guardarImagen(header);
		log.info("Validando imagen: {}", imagenesOpt.isPresent());

		if (imagenesOpt.isPresent() && !imagenesOpt.get().isEmpty()) {
			List<String> imagenes = imagenesOpt.get();
			log.info(PATTERN_TOTAL_IMAGE, imagenes.size());
			String img = imagenes.get(0).replace(PATTERN_BASE64_IMAGE, "");
			img = img.substring(0, img.length() - 2);
			log.info("Imagen procesada: {}", img.substring(0, 100));

			try {
				byte[] decodedImg = Base64.getDecoder().decode(img.getBytes(StandardCharsets.UTF_8));
				String name = prefix + System.currentTimeMillis() + extension;
				Path destinationFile = Paths.get(PATHEADER, name);
				Files.write(destinationFile, decodedImg);
				lista.add(PATHEADER + name);
				log.info(PATTERN_SUCCESS_IMAGE, destinationFile.toAbsolutePath());
			} catch (IOException e) {
				log.error(PATTERN_ERROR_IMAGE, Paths.get(PATHEADER, HEADER_PNG).toAbsolutePath());
				throw e;
			}
		}
		return lista;
	}

	private String procesarContenidoProforma(String contenidoProforma, int inicio, int lastIndex, String placeholder,
			List<Map<String, String>> listaDeMapas, boolean usarExpresionRegular) {
		String substr = contenidoProforma.substring(inicio, lastIndex + 5);
		StringBuilder filasGeneradas = new StringBuilder();
		int contador = 1;

		List<String> etiquetas = Arrays.asList(CANTIDAD_TOTAL_SERVICIO, SERVICIO, CANTIDAD_MEDIDA, PRECIO_UNITARIO,
				TOTAL);

		for (Map<String, String> datos : listaDeMapas) {
			String item = substr;

			if (usarExpresionRegular) {
				item = item.replaceFirst("(<td[^>]*>)(.*?)(</td>)", "$1" + contador + "$3");
			} else {
				item = item.replace(placeholder, contador + "");
			}

			for (String str : etiquetas) {
				item = item.replace("${" + str + "}", datos.get(str) != null ? datos.get(str) : " ");
			}
			filasGeneradas.append(item);
			contador++;
		}

		return contenidoProforma.replace(substr, filasGeneradas);
	}

	@Override
	public byte[] proformaExcel(Long idSubPlantillador, Map<String, String> datosGenerales,
			List<Map<String, String>> listaDeMapas) {
		OutRenderDocument out = new OutRenderDocument();

		Optional<SubPlantilladorModel> plantillador = subPlantilladorRespository.findById(idSubPlantillador);
		if (plantillador.isPresent()) {
			File tempFile = null;
			try {
				log.info("CONSULTANDO PLANTILLA", 1);
				log.info("SubPlantillador: {}", plantillador.get().getCatTipoPlantillador().getEtiquetas());
				List<ContenidoSubPlantilladorModel> lista = contenidoSubPlantilladorRespository
						.findBySubPlantilladorDatosModelSubPlantilladorModelIdSubPlantillador(idSubPlantillador);
				log.info("PLANTILLA ECNONTRADA", 1);
				log.info(ETIQUETA, lista);
				Collections.sort(lista);
				log.info("GENRANDO HEADER", 1);
				String headerProforma = lista.get(0).getHeader();
				String headerNotas = lista.get(1).getHeader();
				String headerPenalizacion = lista.get(2).getHeader();
				log.info("GENRANDO FOOTER", 1);
				String footerProforma = lista.get(0).getFooter();
				String footerNotas = lista.get(1).getFooter();
				String footerPenalizacion = lista.get(2).getFooter();
				log.info("GENRANDO CONTENIDO", 1);
				String contenidoProforma = lista.get(0).getContenido();
				String contenidoNotas = lista.get(1).getContenido();
				String contenidoPenalizacion = lista.get(2).getContenido();
				log.info("ELIMINANDO ETIQUETAS", 1);
				contenidoProforma = eliminacionEtiquetas(contenidoProforma);
				log.info("ELIMINACION ETIQUETAS PROFORMA", 1);
				contenidoNotas = eliminacionEtiquetas(contenidoNotas);
				log.info("ELIMINACION ETIQUETAS NOTAS", 1);
				contenidoPenalizacion = eliminacionEtiquetas(contenidoPenalizacion);
				log.info("ELIMINACION ETIQUETAS PENALIZACION", 1);
				headerProforma = eliminacionEtiquetas(headerProforma);
				log.info("ELIMINACION ETIQUETAS HEADER PROFORMA", 1);
				headerNotas = eliminacionEtiquetas(headerNotas);
				log.info("ELIMINACION ETIQUETAS HEADER NOTAS", 1);
				headerPenalizacion = eliminacionEtiquetas(headerPenalizacion);
				log.info("ELIMINACION ETIQUETAS HEADER PENALIZACION", 1);
				log.info("ELIMINACION ETIQUETAS EXITOSO", 1);
				String cantidad = CANTIDAD;
				int indice = contenidoProforma.indexOf(cantidad);
				int inicio = contenidoProforma.lastIndexOf("<tr>", indice);
				int lastIndex = contenidoProforma.indexOf(TR_CIERRE, indice);
				log.info("GENERACION DE INDICES", 1);
				log.info("PROCESAR CONTENIDO PROFORMA", 1);
				contenidoProforma = procesarContenidoProforma(contenidoProforma, inicio, lastIndex, NBSP, listaDeMapas,
						true);
				log.info("PROCESAR CONTENIDO PROFORMA EXITOSO", 1);
				log.info("SETEANDO HEADERS", 1);
				Map<String, String> headers = new HashMap<>();
				headers.put(HEADER_PROFORMA, headerProforma);
				headers.put(HEADER_NOTAS, headerNotas);
				headers.put(HEADER_PENALIZACION, headerPenalizacion);
				log.info("SETEANDO HEADERS EXITOSO", 1);
				log.info("SETEANDO CONTENIDOS", 1);
				Map<String, String> contenidos = new HashMap<>();
				contenidos.put(CONTENIDO_PROFORMA, contenidoProforma);
				contenidos.put(CONTENIDO_NOTAS, contenidoNotas);
				contenidos.put(CONTENIDO_PENALIZACION, contenidoPenalizacion);
				log.info("SETEANDO CONTENIDOS EXITOSO", 1);
				log.info("PROCESANDO ETIQUETAS EXCEL", 1);
				procesarEtiquetasExcel(plantillador.get(), datosGenerales, headers, contenidos);
				log.info("PROCESANDO ETIQUETAS EXCEL EXITOSO", 1);
				List<String> imagesP = guardarImagenes(headers.get(HEADER_PROFORMA), contenidos.get(CONTENIDO_PROFORMA),
						footerProforma);
				log.info("GUARDAR IMAGENES 1 EXITOSO", 1);
				List<String> imagesN = guardarImagenes(headers.get(HEADER_NOTAS), contenidos.get(CONTENIDO_NOTAS),
						footerNotas);
				log.info("GUARDAR IMAGENES 2 EXITOSO", 1);
				List<String> imagesPe = guardarImagenes(headers.get(HEADER_PENALIZACION),
						contenidos.get(CONTENIDO_PENALIZACION), footerPenalizacion);
				log.info("GUARDAR IMAGENES 3 EXITOSO", 1);
				StringBuilder htmlCompleto = new StringBuilder();
				htmlCompleto.append(INICIOCONTENEDOREXCEL);
				
				log.info("INICIANDO AGREGAR HOJA", 1);
				agregarHoja(htmlCompleto, headers.get(HEADER_PROFORMA), contenidos.get(CONTENIDO_PROFORMA),
						footerProforma, imagesP, "Factura proforma", "false");
				log.info("AGREGAR HOJA 1 EXITOSO", 1);
				agregarHoja(htmlCompleto, headers.get(HEADER_NOTAS), contenidos.get(CONTENIDO_NOTAS), footerNotas,
						imagesN, "Notas de credito", "true");
				log.info("AGREGAR HOJA 2 EXITOSO", 1);
				agregarHoja(htmlCompleto, headers.get(HEADER_PENALIZACION), contenidos.get(CONTENIDO_PENALIZACION),
						footerPenalizacion, imagesPe, "Penalización", "true");
				log.info("AGREGAR HOJA 3 EXITOSO", 1);
				htmlCompleto.append(FINCONTENEDOREXCEL);
				log.info("HTML COMPLETO EXITOSO", 1);
				log.info("inicia proceso de exportacion a excel");

				log.info("INICIANDO BORRA ARCHIVO", 1);
				/* Borrar despues dejar solo para pruebas para poder ver el archivo */
				ExcelExporter exporter = new ExcelExporter();
				String name = NAMEFILE.concat(String.valueOf(System.currentTimeMillis())).concat(EXCELEXTENSION);
				log.info("OBTENIENDO NOMBRE ARCHIVO", 1);
				log.info("GENERANDO FILE", 1);
			    tempFile = new File(PATH + name);
				log.info("FILES EXIOSO", 1);
				log.info("INICIANDO EXPORTACION HTML A EXCEL", 1);
				exporter.exportHtml(htmlCompleto.toString(), tempFile);
				log.info("FIN EXPORTAR HTML A EXCEL", 1);
				log.info("INICIANDO A CARAGR BYTE DEL ARCHIVO", 1);
				byte[] array = Files.readAllBytes(tempFile.toPath());
				out.setContenttype("vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				out.setFilename(tempFile.getName());
				out.setStatus(200);
				out.setFile(array);
				log.info("ARRAY EXITOSO", 1);
				log.info("se creo el array");

				return array;
			} catch (Exception e) {
				log.error("Error al generar excel proformaExcel");
			}
			finally {
	            // Asegurar eliminación del archivo temporal
				if (tempFile != null && tempFile.exists()) {
				    try {
				        Files.delete(tempFile.toPath());
				        log.info("Archivo temporal eliminado: {}", tempFile.getAbsolutePath());
				    } catch (IOException e) {
				        log.warn("No se pudo eliminar el archivo temporal: {}", tempFile.getAbsolutePath(), e);
				    }
				}
	        }
			

		}
		return new byte[] {};
	}

	private void procesarEtiquetasExcel(SubPlantilladorModel plantillador, Map<String, String> datosGenerales,
			Map<String, String> headers, Map<String, String> contenidos) {
		for (EtiquetaPlantilladorModel e : plantillador.getCatTipoPlantillador().getEtiquetas()) {
			headers.replaceAll((key, value) -> value.replace("${" + e.getNombreEtiqueta() + "}",
					datosGenerales.getOrDefault(e.getNombreEtiqueta(), " ")));
			contenidos.replaceAll((key, value) -> value.replace("${" + e.getNombreEtiqueta() + "}",
					datosGenerales.getOrDefault(e.getNombreEtiqueta(), " ")));
		}
	}

	public String eliminacionEtiquetas(String cadena) {
		String parrafo = "<p";
		String substri = "";
		int index = -1;
		int lastIndex = 0;
		while ((index = cadena.indexOf(parrafo, index + 1)) != -1) {
			lastIndex = cadena.indexOf(">", index);
			substri = cadena.substring(index, lastIndex + 1);
			cadena = cadena.replace(substri, "");
		}

		parrafo = "</p";
		index = -1;
		while ((index = cadena.indexOf(parrafo, index + 1)) != -1) {
			lastIndex = cadena.indexOf(">", index);
			substri = cadena.substring(index, lastIndex + 1);
			cadena = cadena.replace(substri, "");
		}

		cadena = cadena.replace(STRONG, "");
		cadena = cadena.replace(STRONG_CIERRE, "");
		cadena = cadena.replace("<i>", "");
		cadena = cadena.replace("</i>", "");
		cadena = cadena.replace(NBSP, "");

		return cadena;
	}

	private byte[] generarExcel(String htmlCompleto) throws IOException {
		String name = NAMEFILE.concat(String.valueOf(System.currentTimeMillis())).concat(EXCELEXTENSION);
		File tempFile = new File(PATH + name);
		ExcelExporter exporter = new ExcelExporter();
		exporter.exportHtml(htmlCompleto, tempFile);
		return Files.readAllBytes(tempFile.toPath());
	}

	private void agregarHoja(StringBuilder htmlCompleto, String headeConHsl, String contenidoConHsl,
			String footerConHsl, List<String> images, String name, String nuevo) {
		Optional<List<String>> imagenbase641 = Base64Util.guardarImagen(headeConHsl);
		log.info("Validando imagen agregar Penalizacion: {}", imagenbase641.isPresent());

		String contenidoSinHls = PlantillaUtil.reemplazarHsl(contenidoConHsl);
		String footerSinHls = PlantillaUtil.reemplazarHsl(footerConHsl);

		htmlCompleto.append("<table data-new-sheet='" + nuevo + "' data-image-name='");
		htmlCompleto.append(images.get(0));
		htmlCompleto.append("'  src=\"");
		htmlCompleto.append(images.get(0));
		htmlCompleto.append("\"  data-sheet-name='" + name + "'><tr><td></td></tr></table>");

		htmlCompleto.append(headeConHsl);
		htmlCompleto.append(contenidoSinHls);
		htmlCompleto.append(footerSinHls);
	}

	@Override
	public byte[] proformaPDF(Long idSubPlantillador, Map<String, String> datosGenerales,
			List<Map<String, String>> listaDeMapas) {
		Optional<SubPlantilladorModel> plantillador = subPlantilladorRespository.findById(idSubPlantillador);
		byte[] pdfBytes = null;
		if (plantillador.isPresent()) {
			try {
				log.info("SubPlantillador: {}", plantillador.get().getCatTipoPlantillador().getEtiquetas());
				List<ContenidoSubPlantilladorModel> lista = contenidoSubPlantilladorRespository
						.findBySubPlantilladorDatosModelSubPlantilladorModelIdSubPlantillador(idSubPlantillador);
				Collections.sort(lista);

				log.info(ETIQUETA, lista);

				Map<String, String> headers = new HashMap<>();
				headers.put(HEADER_PROFORMA, lista.get(0).getHeader());
				headers.put(HEADER_NOTAS, lista.get(1).getHeader());
				headers.put(HEADER_PENALIZACION, lista.get(2).getHeader());

				Map<String, String> footers = new HashMap<>();
				footers.put("footerProforma", lista.get(0).getFooter());
				footers.put("footerNotas", lista.get(1).getFooter());
				footers.put("footerPenalizacion", lista.get(2).getFooter());

				Map<String, String> contenidos = new HashMap<>();
				contenidos.put(CONTENIDO_PROFORMA, lista.get(0).getContenido());
				contenidos.put(CONTENIDO_NOTAS, lista.get(1).getContenido());
				contenidos.put(CONTENIDO_PENALIZACION, lista.get(2).getContenido());

				procesarFooters(footers);

				String cantidad = CANTIDAD;
				int indice = contenidos.get(CONTENIDO_PROFORMA).indexOf(cantidad);
				int inicio = contenidos.get(CONTENIDO_PROFORMA).lastIndexOf("<tr>", indice);
				int lastIndex = contenidos.get(CONTENIDO_PROFORMA).indexOf(TR, indice);

				contenidos.put(CONTENIDO_PROFORMA, procesarContenidoProforma(contenidos.get(CONTENIDO_PROFORMA), inicio,
						lastIndex, NBSP, listaDeMapas, false));

				procesarEtiquetasPDF(plantillador, datosGenerales, headers, contenidos);

				StringBuilder htmlCompleto = new StringBuilder();
				htmlCompleto.append(
						"<html><head><style>table{border-collapse:collapse;} </style></head><body><div style=\"width:800px;\">");

				StringBuilder htmlCompletoProforma = new StringBuilder();
				htmlCompletoProforma.append(ETIQUETA_LARGA);

				StringBuilder htmlCompletoNotas = new StringBuilder();
				htmlCompletoNotas.append(ETIQUETA_LARGA);

				StringBuilder htmlCompletoPenalizacion = new StringBuilder();
				htmlCompletoPenalizacion.append(
					    "<html><head><meta charset=\"UTF-8\"><style>table{border-collapse:collapse;width:100%;}</style></head><body><div style=\"width:800px;\">");

				htmlCompletoProforma.append(headers.get(HEADER_PROFORMA));
				htmlCompletoProforma.append(PlantillaUtil.reemplazarHsl(contenidos.get(CONTENIDO_PROFORMA)));
				htmlCompletoProforma
						.append("<div style=\"position: absolute;bottom: 0px;width:100%;margin-right: -20px;\">");
				htmlCompletoProforma.append(footers.get("footerProforma"));
				htmlCompletoProforma.append("</div>");

				htmlCompletoNotas.append(headers.get(HEADER_PROFORMA));
				htmlCompletoNotas.append(PlantillaUtil.reemplazarHsl(contenidos.get(CONTENIDO_NOTAS)));
				htmlCompletoNotas
						.append("<div style=\"position: absolute;bottom: 0px;width:100%;margin-right: -20px;\">");
				htmlCompletoNotas.append(footers.get("footerNotas"));
				htmlCompletoNotas.append("</div>");

				htmlCompletoPenalizacion.append(headers.get(HEADER_PROFORMA));
				htmlCompletoPenalizacion.append(PlantillaUtil.reemplazarHsl(contenidos.get(CONTENIDO_PENALIZACION)));
				htmlCompletoPenalizacion
						.append("<div style=\"position: absolute;bottom: 0px;width:100%;margin-right: -100 px;\">");
				htmlCompletoPenalizacion.append(footers.get("footerPenalizacion"));
				htmlCompletoPenalizacion.append("</div></div></body></html>");

				List<String> htmlContent = new ArrayList<>();

				htmlContent.add(htmlCompletoProforma.toString());
				htmlContent.add(htmlCompletoNotas.toString());
				htmlContent.add(htmlCompletoPenalizacion.toString());

				String[] srcFiles = CreatePDF.convertHtmlToPdfMultiPage(htmlContent, PATH);

				String name = "pdf-exporter-".concat(String.valueOf(System.currentTimeMillis())).concat(".pdf");
				String destFile = PATH + name;

				creaconPDFMultipage(destFile, srcFiles);

				pdfBytes = Files.readAllBytes(Paths.get(destFile));
				return pdfBytes;

			} catch (Exception e) {
				log.error("Error al generar PDF");
			}
		}
		return pdfBytes;
	}

	private void procesarFooters(Map<String, String> footers) {
		String colgrouop = "<colgroup>";
		String colgrouopFin = "</colgroup>";

		for (Map.Entry<String, String> entry : footers.entrySet()) {
			int ind = entry.getValue().indexOf(colgrouop);
			if (ind != -1) {
				int indUlt = entry.getValue().indexOf(colgrouopFin, ind);
				if (indUlt != -1) {
					String substri = entry.getValue().substring(ind, indUlt + colgrouopFin.length());
					footers.put(entry.getKey(), entry.getValue().replace(substri, ""));
				}
			}
		}
	}

	private void procesarEtiquetasPDF(Optional<SubPlantilladorModel> plantillador, Map<String, String> datosGenerales,
			Map<String, String> headers, Map<String, String> contenidos) {
		if (plantillador.isPresent()) {
			for (EtiquetaPlantilladorModel e : plantillador.get().getCatTipoPlantillador().getEtiquetas()) {
				log.info("Nombre etiqueta:{}", "${" + e.getNombreEtiqueta() + "}");
				log.info("Nombre etiqueta datos:{}", "${" + datosGenerales.get(e.getNombreEtiqueta()) + "}");

				headers.replaceAll((key, value) -> value.replace("${" + e.getNombreEtiqueta() + "}",
						datosGenerales.getOrDefault(e.getNombreEtiqueta(), " ")));
				contenidos.replaceAll((key, value) -> value.replace("${" + e.getNombreEtiqueta() + "}",
						datosGenerales.getOrDefault(e.getNombreEtiqueta(), " ")));
			}
		}
	}

	private List<ContenidoSubPlantilladorModel> obtenerContenidoSubPlantillador(Long idSubPlantillador) {
		List<ContenidoSubPlantilladorModel> lista = contenidoSubPlantilladorRespository
				.findBySubPlantilladorDatosModelSubPlantilladorModelIdSubPlantillador(idSubPlantillador);
		Collections.sort(lista);
		return lista;
	}

	private String reemplazarEtiquetas(String contenido, Map<String, String> datos) {
		if (contenido == null || datos == null)
			return contenido;
		for (Map.Entry<String, String> entry : datos.entrySet()) {
			String placeholder = "${" + entry.getKey() + "}";
			contenido = contenido.replace(placeholder, entry.getValue() != null ? entry.getValue() : " ");
		}
		return contenido;
	}

	private void creaconPDFMultipage(String destFile, String[] srcFiles) {
		try {
			PdfDocument pdf = new PdfDocument(new PdfWriter(destFile));
			PdfMerger merger = new PdfMerger(pdf);

			for (String src : srcFiles) {
				PdfDocument srcDoc = new PdfDocument(new PdfReader(src));
				merger.merge(srcDoc, 1, srcDoc.getNumberOfPages());
				srcDoc.close();
				File temp = new File(src);
				if (temp.exists())
					Files.delete(Paths.get(src));
			}

			pdf.close();

		} catch (IOException e) {
			log.error("Se ha producido un error");
		}
	}

}
