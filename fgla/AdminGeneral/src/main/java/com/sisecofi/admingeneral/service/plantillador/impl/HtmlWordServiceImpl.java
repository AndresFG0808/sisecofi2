package com.sisecofi.admingeneral.service.plantillador.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.sisecofi.admingeneral.dto.plantillador.HtmlExcelListDto;
import com.sisecofi.admingeneral.repository.plantillador.PlantillaRespository;
import com.sisecofi.admingeneral.util.CreatePDF;
import com.sisecofi.admingeneral.util.HtmlUtil;
import com.sisecofi.libreria.comunes.dto.plantillador.ContenidoPlantilladorPdfReponseDto;
import com.sisecofi.admingeneral.repository.plantillador.ContenidoPlantillaRepository;
import com.sisecofi.libreria.comunes.model.plantillador.ContenidoPlantilladorModel;
import com.sisecofi.libreria.comunes.model.plantillador.PlantilladorModel;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
import org.apache.commons.io.FileUtils;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.AltChunkType;
import org.docx4j.openpackaging.parts.WordprocessingML.FooterPart;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.FooterReference;
import org.docx4j.wml.HdrFtrRef;
import org.docx4j.wml.HeaderReference;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.SectPr;
import org.springframework.stereotype.Service;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import com.sisecofi.admingeneral.dto.plantillador.RequestPlantilla;
import com.sisecofi.admingeneral.service.plantillador.HtmlWordService;
import com.sisecofi.admingeneral.util.enums.ErroresPlantilladorEnum;
import com.sisecofi.admingeneral.util.exception.PlantilladorException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class HtmlWordServiceImpl implements HtmlWordService {

	private static final String DIAGONAL = "\\";
	private String filePathPdf = "C:" + DIAGONAL + "sisecofi" + DIAGONAL + "tes11.pdf";
	private String filePathWord = "C:" + DIAGONAL + "sisecofi" + DIAGONAL + "test1.docx";

	private ObjectFactory objectFactory = new ObjectFactory();
	private final GeneraPDF generaPdf;
	private final ContenidoPlantillaRepository contenidoPlantillaRepository;
	private final PlantillaRespository plantillaRespository;
	private final HtmlUtil htmlUtil;
	private static final String ERROR = "Error: {}";

	@Override
	public byte[] convertirHmltWord(RequestPlantilla plantilla) {
		WordprocessingMLPackage wordMLPackage;
		try {
			wordMLPackage = WordprocessingMLPackage.createPackage();
			XHTMLImporterImpl xhtmlImporter = new XHTMLImporterImpl(wordMLPackage);
			MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();
			Relationship styleRel = mdp.getStyleDefinitionsPart().getSourceRelationships().get(0);
			mdp.getRelationshipsPart().removeRelationship(styleRel);

			Relationship relationshipHeader = createHeaderPart(wordMLPackage, plantilla);
			createHeaderReference(wordMLPackage, relationshipHeader);

			Relationship relationshipFooter = createFooterPart(wordMLPackage, plantilla);
			createFooterReference(wordMLPackage, relationshipFooter);

			wordMLPackage.getMainDocumentPart().getContent()
					.addAll(xhtmlImporter.convert(plantilla.getContenido(), null));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			wordMLPackage.save(baos);
			this.saveWordMLPackage(wordMLPackage);
			return baos.toByteArray();
		} catch (Exception e) {
			log.error(ERROR);
			throw new PlantilladorException(ErroresPlantilladorEnum.ERROR_GENERAR_DOCX);
		}
	}

	private void saveWordMLPackage(WordprocessingMLPackage wordMLPackage) {
		try {
			wordMLPackage.save(new File(this.filePathWord));
		} catch (Docx4JException e) {
			log.error(ERROR);
		}
	}

	private Relationship createHeaderPart(WordprocessingMLPackage wordprocessingMLPackage, RequestPlantilla plantilla)
			throws Docx4JException {
		HeaderPart headerPart = new HeaderPart();
		Relationship rel = wordprocessingMLPackage.getMainDocumentPart().addTargetPart(headerPart);
		headerPart.addAltChunk(AltChunkType.Html, plantilla.getHeader().getBytes());
		return rel;
	}

	private void createHeaderReference(WordprocessingMLPackage wordprocessingMLPackage, Relationship relationship) {
		List<SectionWrapper> sections = wordprocessingMLPackage.getDocumentModel().getSections();
		SectPr sectPr = sections.get(sections.size() - 1).getSectPr();
		if (sectPr == null) {
			sectPr = objectFactory.createSectPr();
			wordprocessingMLPackage.getMainDocumentPart().addObject(sectPr);
			sections.get(sections.size() - 1).setSectPr(sectPr);
		}
		HeaderReference headerReference = objectFactory.createHeaderReference();
		headerReference.setId(relationship.getId());
		headerReference.setType(HdrFtrRef.DEFAULT);
		sectPr.getEGHdrFtrReferences().add(headerReference);
	}

	private Relationship createFooterPart(WordprocessingMLPackage wordprocessingMLPackage, RequestPlantilla plantilla)
			throws Docx4JException {
		FooterPart footerPart = new FooterPart();
		Relationship rel = wordprocessingMLPackage.getMainDocumentPart().addTargetPart(footerPart);
		footerPart.addAltChunk(AltChunkType.Html, plantilla.getFooter().getBytes());
		return rel;
	}

	private void createFooterReference(WordprocessingMLPackage wordprocessingMLPackage, Relationship relationship) {
		List<SectionWrapper> sections = wordprocessingMLPackage.getDocumentModel().getSections();
		SectPr sectPr = sections.get(sections.size() - 1).getSectPr();
		if (sectPr == null) {
			sectPr = objectFactory.createSectPr();
			wordprocessingMLPackage.getMainDocumentPart().addObject(sectPr);
			sections.get(sections.size() - 1).setSectPr(sectPr);
		}
		FooterReference footerReference = objectFactory.createFooterReference();
		footerReference.setId(relationship.getId());
		footerReference.setType(HdrFtrRef.DEFAULT);
		sectPr.getEGHdrFtrReferences().add(footerReference);
	}

	@Override
	public byte[] convertirHmltPdfSoloWindows(RequestPlantilla plantilla) {
		byte[] archivo = this.convertirHmltWord(plantilla);
		String path = File.separator + "opt" + File.separator + "jboss" + File.separator + "wildfly" + File.separator
				+ "standalone" + File.separator + "tmp";

		String nano = String.valueOf(System.nanoTime());
		String word = "word-" + nano + ".docx";
		String pdf = "pdf-" + nano + ".pdf";

		File wordFile = new File(path + File.separator + word);
		File target = new File(path + File.separator + pdf);

		try {
			// Guardar el archivo DOCX
			FileUtils.writeByteArrayToFile(wordFile, archivo);

			// Configurar el conversor
			IConverter converter = LocalConverter.builder().baseFolder(null).workerPool(20, 25, 10, TimeUnit.SECONDS)
					.processTimeout(60, TimeUnit.SECONDS).build();

			// Convertir a PDF
			Future<Boolean> conversion = converter.convert(wordFile).as(DocumentType.MS_WORD).to(target)
					.as(DocumentType.PDF).prioritizeWith(1000) // optional
					.schedule();

			if (Boolean.TRUE.equals(conversion.get())) {
				byte[] pdfBytes = Files.readAllBytes(target.toPath());

				// Eliminar los archivos temporales después de su uso
				eliminarArchivoTemporal(wordFile);
				eliminarArchivoTemporal(target);

				return pdfBytes;
			}
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
			log.warn("Hilo interrumpido");
		} catch (Exception e) {
			log.error(ERROR);
		} finally {
			// Asegurar eliminación de archivos temporales en caso de error
			eliminarArchivoTemporal(wordFile);
			eliminarArchivoTemporal(target);
		}

		throw new PlantilladorException(ErroresPlantilladorEnum.ERROR_GENERAR_DOCX);
	}

	private void eliminarArchivoTemporal(File file) {
		if (file != null && file.exists()) {
			try {
				Files.delete(file.toPath());
				log.info("Archivo temporal eliminado: {}", file.getAbsolutePath());
			} catch (IOException e) {
				log.warn("No se pudo eliminar el archivo temporal: {}", file.getAbsolutePath(), e);
			}

		}
	}

	@Override
	public byte[] convertirHmltPdf(RequestPlantilla plantilla) {
		byte[] bytes = generaPdf.generaPdf(plantilla.getHeader(), plantilla.getContenido(), plantilla.getFooter());
		File tempFile = null;

		try {
			tempFile = new File(this.filePathPdf);
			FileUtils.writeByteArrayToFile(tempFile, bytes);
			log.info("PDF guardado en: {}", tempFile.getAbsolutePath());

		} catch (Exception e) {
			log.error("Error no es local");
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

		return bytes;
	}

	@Override
	public byte[] cierreProyectoPdf(HtmlExcelListDto inputs) {
		Optional<PlantilladorModel> plantillador = plantillaRespository.findById(inputs.getIdSubPlantillador());
		byte[] hmltPdf = new byte[0];

		if (plantillador.isPresent()) {
			try {
				Optional<ContenidoPlantilladorModel> lista = contenidoPlantillaRepository
						.findByplantillaModelPlantilladorModelIdPlantillador(inputs.getIdSubPlantillador());

				if (lista.isPresent()) {
					String header = preprocessHeader(lista.get().getHeader());
					String contenido = preprocessContenido(lista.get().getContenido());
					String contenidoFinal = buildContenidoFinal(contenido, inputs);

					for (String keyGenerals : inputs.getDatosGenerales().keySet()) {
						String etiqueta = "${" + keyGenerals + "}";
						header = header.replace(etiqueta, inputs.getDatosGenerales().get(keyGenerals));
						contenidoFinal = contenidoFinal.replace(etiqueta, inputs.getDatosGenerales().get(keyGenerals));
					}

					StringBuilder headerBuilder = new StringBuilder();
					headerBuilder.append(
							"<figure class=\"table\" style=\"margin-top: 0%; width: 750px; border: medium solid black\">");
					headerBuilder.append(header);
					headerBuilder.append("</figure>");

					var html = htmlUtil.htmlFrom(headerBuilder.toString(), contenidoFinal.replace("&nbsp;", "<br>"),
							"");
					hmltPdf = CreatePDF.convertHtmlToPdfRCP("<div style=\"width: 750px;\">" + html + "</div>");
				}
			} catch (Exception e) {
				log.error("Error al generar pdf: {}");
			}
		}
		return hmltPdf;
	}

	private String preprocessHeader(String header) {
		return header.replace("style=\"width:100%;\"", "style=\"width: 100%; margin-left: -50px;\"")
				.replace("width=\"396\" height=\"52\"", "width=\"200\" height=\"52\"");
	}

	private String buildContenidoFinal(String contenido, HtmlExcelListDto inputs) {
		StringBuilder filasGeneradas = new StringBuilder();
		int contador = 1;

		for (Map<String, String> datos : inputs.getDatos()) {
			if (contador == 1) {
				for (Map.Entry<String, String> entry : datos.entrySet()) {
					String etiqueta = "${" + entry.getKey() + "}";
					contenido = contenido.replace(etiqueta, entry.getValue());
				}
			} else {
				filasGeneradas.append(
						"<tr><td style=\"border:1px solid black; text-align: center\"><span style=\"font-size: 12px\">")
						.append(contador).append("</span></td>");

				for (Map.Entry<String, String> entry : datos.entrySet()) {
					filasGeneradas.append(
							"<td style=\"border:1px solid black; text-align: center\"><span style=\"font-size: 12px\">")
							.append(entry.getValue()).append("</span></td>");
				}
				filasGeneradas.append("</tr>");
			}
			contador++;
		}
		return contenido + filasGeneradas.append("</tbody></table></figure>");
	}

	private String preprocessContenido(String contenido) {
		return contenido.replace("</tbody></table></figure>", "").replace("style=\"border-collapse: collapse\"", "");
	}

	@Override
	public ContenidoPlantilladorPdfReponseDto obtenerPdfContenidoPlantillador(Long idContenidoPlantillador) {

		ContenidoPlantilladorModel plantilladorModel = contenidoPlantillaRepository
				.findByIdContenidoPlantillador(idContenidoPlantillador)
				.orElseThrow(() -> new PlantilladorException(ErroresEnum.ERROR_PLANTILLA_NO_ENCONTRADA));

		ContenidoPlantilladorPdfReponseDto contenidoPlantillador = new ContenidoPlantilladorPdfReponseDto();
		contenidoPlantillador.setHeader(plantilladorModel.getHeader());
		contenidoPlantillador.setContenido(plantilladorModel.getContenido());
		contenidoPlantillador.setFooter(plantilladorModel.getFooter());

		return contenidoPlantillador;
	}

	@Override
	public ContenidoPlantilladorPdfReponseDto obtenerContenidoPlantilladorDoc(Long idPlantillador) {
		ContenidoPlantilladorModel plantilladorModel = contenidoPlantillaRepository
				.findByIdContenidoPlantillador(idPlantillador)
				.orElseThrow(() -> new PlantilladorException(ErroresEnum.ERROR_PLANTILLA_NO_ENCONTRADA));

		RequestPlantilla requestPlantilla = new RequestPlantilla();
		requestPlantilla.setContenido(plantilladorModel.getContenido());
		requestPlantilla.setHeader(plantilladorModel.getHeader());
		requestPlantilla.setFooter(plantilladorModel.getFooter());

		return new ContenidoPlantilladorPdfReponseDto();
	}
}
