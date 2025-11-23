package com.sisecofi.admingeneral.service.plantillador.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.w3c.tidy.Tidy;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontProvider;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.ElementHandlerPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.sisecofi.admingeneral.util.Base64Util;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Slf4j
@Service
public class GeneraPDF extends AbstractImageProvider implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String CLOSE_TAG = "/>";
	private static final Pattern IMAGE_PATTERN = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
	private static final String UTF = StandardCharsets.UTF_8.name();
	public static final int SETENTAYDOS = 72;
	public static final int NOVENTAYSEIS = 96;
	private static final float CERO_PUNTO_CINCO = 0.5F;
	private static final String SISECOFI = "/sisecofi/";
	private static final String ESP = "mcrEsp";
	private static final String DIAGONALES = "\\";

	public byte[] generaPdf(String encabezado, String cuerpo, String piePagina) {
		try {
			Document documento = inicializarDocumento();
			ByteArrayOutputStream outPut = new ByteArrayOutputStream();
			configurarWriter(documento, outPut, encabezado, piePagina);

			documento.open();
			procesarContenido(documento, cuerpo);
			documento.close();

			return outPut.toByteArray();
		} catch (DocumentException | IOException e) {
			log.error("error al procesar");
		}
		return new byte[0];
	}

	private Document inicializarDocumento() {
		Rectangle layout = new Rectangle(PageSize.LETTER);
		Document documento = new Document(layout);
		documento.setMargins(convertirPixelesaPtos(70), convertirPixelesaPtos(70), 90, 90);
		return documento;
	}

	private PdfWriter configurarWriter(Document documento, ByteArrayOutputStream outPut, String encabezado,
			String piePagina) throws DocumentException, IOException {
		PdfWriter writer = PdfWriter.getInstance(documento, outPut);

		encabezado = guardarImagenHeader(validarHtml(fixHTML(encabezado)));
		Header partesDoc = configurarEncabezadoYPie(encabezado, piePagina);

		writer.setBoxSize("art", documento.getPageSize());
		writer.setPageEvent(partesDoc);

		return writer;
	}

	private Header configurarEncabezadoYPie(String encabezado, String piePagina) throws IOException {
		FontProvider fuentes = registrarFuente();
		Header partesDoc = new Header();
		partesDoc.setEncabezado(parseToElementList(validarHtml(fixHTML(encabezado)), null, fuentes));
		partesDoc.setPiePagina(parseToElementList(validarHtml(fixHTML(piePagina)), null, fuentes));
		partesDoc.setMargenIzquierdo(convertirPixelesaPtos(2));
		partesDoc.setMargenDerecho(convertirPixelesaPtos(2));
		partesDoc.setPathBanderaImg("true");
		partesDoc.setPathImg(SISECOFI);
		return partesDoc;
	}

	private void procesarContenido(Document documento, String cuerpo) throws IOException, DocumentException {
		FontProvider fuentes = registrarFuente();
		List<Element> elements = parseToElementList(
				fixHTML(cuerpo.replace("/*", "/&lowast;*")).replace("[[ESPACIOS]]", ESP), null, fuentes);

		for (Element elemento : elements) {
			if (elemento instanceof Paragraph ph)
				procesarParrafo(ph);
			documento.add(elemento);
		}

		validaEncabezados(elements, documento);
	}

	private Paragraph procesarParrafo(Paragraph parrafo) throws UnsupportedEncodingException {
		DottedLineSeparator separator = crearSeparador();
		Chunk leader = new Chunk(separator);

		if (parrafo.getContent().contains(ESP)) {
			int chCnt = -1;
			for (Iterator<Element> it = parrafo.iterator(); it.hasNext();) {
				Element el = it.next();
				chCnt++;

				if (el instanceof Chunk chunk) {
					reemplazarContenidoChunk(parrafo, chCnt, chunk);
				} else if (el instanceof PdfPTable tabla) {
					formatoTablas(tabla);
					parrafo.set(chCnt, tabla);
				}

			}
			parrafo.add(leader);
		}
		return parrafo;
	}

	private void reemplazarContenidoChunk(Paragraph parrafo, int chCnt, Chunk chunk)
			throws UnsupportedEncodingException {
		String content = chunk.getContent();
		if (content.contains(ESP)) {
			String newContent = content.replaceAll("(?i:mcrEsp)+", "");
			Chunk newChunk = new Chunk(new String(newContent.getBytes(UTF), Charset.defaultCharset().name()),
					chunk.getFont());
			parrafo.set(chCnt, newChunk);
		}
	}

	private DottedLineSeparator crearSeparador() {
		return new DottedLineSeparator() {
			@Override
			public void draw(PdfContentByte canvas, float llx, float lly, float urx, float ury, float y) {
				super.draw(canvas, llx, lly + CERO_PUNTO_CINCO, urx, ury, y + CERO_PUNTO_CINCO);
			}
		};
	}

	private String guardarImagenHeader(String encabezado) {
		Path destinationFile = null;
		try {
			Optional<List<String>> imagenbase64 = Base64Util.guardarImagen(encabezado);
			log.info("Validando imagen: {}", imagenbase64.isPresent());

			if (imagenbase64.isPresent()) {
				log.info("Validando imagen total2: {}", imagenbase64.get().size());
				String img = imagenbase64.get().get(0);
				img = img.replace("src=\"data:image/png;base64,", "");
				img = img.substring(0, img.length() - 2);
				log.info("Imagen header: {}", img.substring(0, 100));

				byte[] decodedImg = Base64.getDecoder().decode(img.getBytes(StandardCharsets.UTF_8));
				destinationFile = Paths.get(SISECOFI, "header.png");
				Files.write(destinationFile, decodedImg);

				return encabezado.replace(imagenbase64.get().get(0), "");
			}
		} catch (IOException e) {
			log.info("Error al guardar header");
		} finally {
			if (destinationFile != null && Files.exists(destinationFile)) {
				try {
					Files.delete(destinationFile);
					log.info("Archivo temporal eliminado: {}", destinationFile);
				} catch (IOException e) {
					log.warn("No se pudo eliminar el archivo temporal: {}", destinationFile);
				}
			}
		}
		return encabezado;
	}

	public void validaEncabezados(List<Element> elementos, Document documento) throws DocumentException {
		if (elementos.isEmpty()) {
			documento.add(new Paragraph("\n"));
		}
	}

	public void formatoTablas(PdfPTable tabla) {
		List<PdfPRow> rows = tabla.getRows();
		for (PdfPRow row : rows) {
			procesarFilas(row);
		}
	}

	private void procesarFilas(PdfPRow row) {
		PdfPCell[] celdas = row.getCells();
		for (PdfPCell celda : celdas) {
			if (esCeldaValida(celda)) {
				procesarElementosComposites(celda);
			}
		}
	}

	private boolean esCeldaValida(PdfPCell celda) {
		return celda != null && celda.getColumn() != null;
	}

	private void procesarElementosComposites(PdfPCell celda) {
		log.info("columnText {}", celda.getColumn());
		List<Element> elementos = celda.getColumn().getCompositeElements();
		if (elementos != null) {
			for (Element elemento : elementos) {
				log.info("elemento composite{}", elemento);
				if (elemento instanceof Paragraph paragraph) {
					try {
						procesarParrafoComponent(paragraph);
					} catch (Exception e) {
						log.error("error al procesar");
					}
				}
			}
		}
	}

	private void procesarParrafoComponent(Paragraph parrafo) {
		for (Chunk chunk : parrafo.getChunks()) {
			pruebaChunk(chunk, chunk.getFont());
		}
	}

	private void pruebaChunk(Chunk chk, Font fuente) {
		log.info("pueba de chunk {}, fuente {}", chk, fuente);
		if (fuente != null && chk != null && chk.getFont() != null) {
			chk.getFont().setSize(fuente.getSize());
		}
	}

	public float convertirPixelesaPtos(int pixeles) {
		return (pixeles * SETENTAYDOS) / (float) NOVENTAYSEIS;
	}

	private XMLWorkerFontProvider registrarFuente() {
		XMLWorkerFontProvider soberanaSans = null;
		String absoluteDiskPath = "C:" + DIAGONALES + "Users" + DIAGONALES + "Qualtop" + DIAGONALES + "Documents"
				+ DIAGONALES + "sdma6" + DIAGONALES + "APE5" + DIAGONALES + "plantillador" + DIAGONALES
				+ "ce_plantillador" + DIAGONALES + "ARCHIVOS_DE_CONFIGURACION" + DIAGONALES + "PRODUCCION" + DIAGONALES
				+ "PLANTILLADOR" + DIAGONALES;

		log.info("absoluteDiskPath {}", absoluteDiskPath);
		soberanaSans = new XMLWorkerFontProvider();
		soberanaSans.registerDirectory(absoluteDiskPath, true);
		log.debug("Fuente registrada correctamente");
		return soberanaSans;
	}

	private ElementList parseToElementList(String html, String css, FontProvider fuentes) throws IOException {

		CSSResolver cssResolver = new StyleAttrCSSResolver();
		if (css != null) {
			CssFile cssFile = XMLWorkerHelper.getCSS(new ByteArrayInputStream(css.getBytes(Charset.forName(UTF))));
			cssResolver.addCss(cssFile);
		}

		CssAppliers cssAppliers = new CssAppliersImpl(fuentes);
		HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);

		htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
		htmlContext.setImageProvider(this);

		ElementList elements = new ElementList();
		ElementHandlerPipeline end = new ElementHandlerPipeline(elements, null);
		HtmlPipeline htmlPipeline = new HtmlPipeline(htmlContext, end);
		CssResolverPipeline cssPipeline = new CssResolverPipeline(cssResolver, htmlPipeline);

		XMLWorker worker = new XMLWorker(cssPipeline, true);
		XMLParser p = new XMLParser(worker, Charset.forName(UTF));

		log.debug("Charset.defaultCharset() {}", Charset.defaultCharset());

		p.parse(new ByteArrayInputStream(html.getBytes(Charset.defaultCharset())));

		return elements;
	}

	private String validarHtml(String html) throws UnsupportedEncodingException {
		Tidy tidy = new Tidy();
		tidy.setInputEncoding(UTF);
		tidy.setOutputEncoding(UTF);
		tidy.setWraplen(Integer.MAX_VALUE);
		tidy.setDocType("transitional");
		tidy.setXHTML(true);
		tidy.setMakeClean(true);
		tidy.setSmartIndent(true);

		ByteArrayInputStream inputStream = new ByteArrayInputStream(
				html.replace("<div", "<span").replace("</div>", "</span>").getBytes(UTF));
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		tidy.parseDOM(inputStream, outputStream);
		log.debug("valor html{}", outputStream.toString(UTF));
		return outputStream.toString(UTF);
	}

	private String fixHTML(String html) {
		String str = StringUtils.replace(html, "<br>", "<br/>");
		String str2 = str;
		Matcher m = IMAGE_PATTERN.matcher(str);
		while (m.find()) {
			if (!m.group(0).endsWith(CLOSE_TAG)) {
				str2 = StringUtils.replace(str2, m.group(0),
						m.group(0).substring(0, m.group(0).length() - 1) + CLOSE_TAG);
			}
		}
		return str2;
	}

	@Override
	public String getImageRootPath() {
		return "somePath";
	}
}
