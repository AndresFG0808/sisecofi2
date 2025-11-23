package com.sisecofi.admingeneral.util;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.media.MediaType;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.nio.charset.StandardCharsets;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.model.structure.PageDimensions;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.SectPr;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Transactional
@Slf4j
public class CreatePDF {
	
	private static final String ERROR = "Se ha producido un error";

	private CreatePDF() {
	}

	public static void doSomething() {
		// COMENTADO
	}

	private static final String SEPARADOR = "//";

	public static byte[] convertHtmlToPdf(String htmlContent) {
		ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

		try {
			PdfWriter writer = new PdfWriter(pdfOutputStream);
			PdfDocument pdfDoc = new PdfDocument(writer);
			ConverterProperties properties = new ConverterProperties();

			PdfUtils.configurePdfDocument(pdfDoc, properties);

			Document document = HtmlConverter.convertToDocument(new ByteArrayInputStream(htmlContent.getBytes()),
					pdfDoc, properties);

			EndPosition endPosition = new EndPosition();

			PdfUtils.finalizePdfDocument(document, pdfDoc, endPosition);

			HtmlConverter.convertToPdf(new ByteArrayInputStream(htmlContent.getBytes()), pdfDoc, properties);
			pdfDoc.close();
		} catch (Exception e) {
			log.error(ERROR);
		}

		return pdfOutputStream.toByteArray();
	}

	public static byte[] convertHtmlToPdfProforma(String htmlContent) {
		ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

		try {
			PdfWriter writer = new PdfWriter(pdfOutputStream);
			PdfDocument pdfDoc = new PdfDocument(writer);

			ConverterProperties properties = new ConverterProperties();
			pdfDoc.setTagged();
			PageSize pageSize = new PageSize(PageSize.LEGAL);

			pageSize.applyMargins(0, 0, 140, 100, true);

			pdfDoc.setDefaultPageSize(pageSize);

			MediaDeviceDescription mediaDeviceDescription = new MediaDeviceDescription(MediaType.SCREEN);
			mediaDeviceDescription.setWidth(pageSize.getWidth());
			properties.setMediaDeviceDescription(mediaDeviceDescription);

			Document document = HtmlConverter.convertToDocument(new ByteArrayInputStream(htmlContent.getBytes(StandardCharsets.UTF_8)),
					pdfDoc, properties);

			EndPosition endPosition = new EndPosition();
			LineSeparator separator = new LineSeparator(endPosition);

			document.add(separator);
			document.getRenderer().close();

			HtmlConverter.convertToPdf(new ByteArrayInputStream(htmlContent.getBytes(StandardCharsets.UTF_8)), pdfDoc, properties);
			pdfDoc.close();
		} catch (Exception e) {
			log.error(ERROR);
		}

		return pdfOutputStream.toByteArray();
	}

	public static String[] convertHtmlToPdfMultiPage(List<String> htmlContent, String path) {
	    int i = 0;
	    String[] listContent = new String[htmlContent.size()];
	    byte[] content;
	    String name;
	    for (String temp : htmlContent) {
	        content = convertHtmlToPdfProforma(temp);
	        name = "pdf-" + i + "exporter.pdf";
	        Path tempFile = Paths.get(path, name);

	        try (OutputStream fos = Files.newOutputStream(tempFile, StandardOpenOption.CREATE_NEW)) {
	            fos.write(content);
	        } catch (IOException e) {
	        	log.error(ERROR);
	        }
	        listContent[i] = tempFile.toAbsolutePath().toString();
	        i++;
	    }
	    return listContent;
	}


	public static byte[] convertHtmlToPdfRCP(String htmlContent) {
		ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

		try {
			PdfWriter writer = new PdfWriter(pdfOutputStream);
			PdfDocument pdfDoc = new PdfDocument(writer);

			ConverterProperties properties = new ConverterProperties();
			pdfDoc.setTagged();
			PageSize pageSize = new PageSize(PageSize.LETTER);
			pdfDoc.setDefaultPageSize(pageSize);
			MediaDeviceDescription mediaDeviceDescription = new MediaDeviceDescription(MediaType.SCREEN);
			mediaDeviceDescription.setWidth(pageSize.getWidth());
			properties.setMediaDeviceDescription(mediaDeviceDescription);

			HtmlConverter.convertToPdf(new ByteArrayInputStream(htmlContent.getBytes()), pdfDoc, properties);
			pdfDoc.close();
		} catch (Exception e) {
			log.error(ERROR);
		}

		return pdfOutputStream.toByteArray();
	}

	public static byte[] generateWordHtml(String htmlContent) {
		WordprocessingMLPackage wordMLPackage;
		try {
			wordMLPackage = WordprocessingMLPackage.createPackage();

			XHTMLImporterImpl xhtmlImporter = new XHTMLImporterImpl(wordMLPackage);
			MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();
			setPageSizeToLetter(wordMLPackage);

			mdp.getContent().addAll(xhtmlImporter.convert(htmlContent, null));

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			wordMLPackage.save(baos);
			wordMLPackage.save(new java.io.File("C:" + SEPARADOR + "sisecofi" + SEPARADOR + "test2.docx"));

			return baos.toByteArray();

		} catch (Exception e) {
			log.error(ERROR);
			return new byte[0];
		}
	}

	private static void setPageSizeToLetter(WordprocessingMLPackage wordMLPackage) {
		try {
			ObjectFactory factory = new ObjectFactory();

			SectPr sectPr = wordMLPackage.getMainDocumentPart().getJaxbElement().getBody().getSectPr();
			if (sectPr == null) {
				sectPr = factory.createSectPr();
				wordMLPackage.getMainDocumentPart().getJaxbElement().getBody().setSectPr(sectPr);
			}

			PageDimensions pageDimensions = new PageDimensions();
			pageDimensions.setPgSize();
			sectPr.setPgSz(pageDimensions.getPgSz());

			SectPr.PgMar pgMar = factory.createSectPrPgMar();
			pgMar.setTop(BigInteger.valueOf(1440L));
			pgMar.setBottom(BigInteger.valueOf(1440L));
			pgMar.setLeft(BigInteger.valueOf(1440L));
			pgMar.setRight(BigInteger.valueOf(1440L));
			sectPr.setPgMar(pgMar);

		} catch (Exception e) {
			log.error(ERROR);
		}
	}
}
