package com.sisecofi.admingeneral.util.docx4j;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.media.MediaType;
import com.sisecofi.admingeneral.dto.plantillador.RequestPlantilla;
import com.sisecofi.admingeneral.util.EndPosition;
import com.sisecofi.admingeneral.util.HtmlUtil;
import com.sisecofi.admingeneral.util.PdfUtils;

import jakarta.xml.bind.JAXBException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.FooterPart;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.wml.*;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
@Slf4j
@AllArgsConstructor
public class FileGenerator {

	private HtmlUtil htmlUtil;
	private final ObjectFactory objectFactory = new ObjectFactory();

	public byte[] generatePdfFromHtml(RequestPlantilla contenidoBase) {
		ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
		var htmlContent = htmlUtil.htmlFrom(contenidoBase.getHeader(), contenidoBase.getContenido(),
				contenidoBase.getFooter());

		try {
			PdfWriter writer = new PdfWriter(pdfOutputStream);
			PdfDocument pdfDoc = new PdfDocument(writer);

			ConverterProperties properties = new ConverterProperties();
			pdfDoc.setTagged();
			PageSize pageSize = new PageSize(800.0F, 14400F);
			pdfDoc.setDefaultPageSize(pageSize);
			MediaDeviceDescription mediaDeviceDescription = new MediaDeviceDescription(MediaType.SCREEN);
			mediaDeviceDescription.setWidth(pageSize.getWidth());
			properties.setMediaDeviceDescription(mediaDeviceDescription);

			Document document = HtmlConverter.convertToDocument(new ByteArrayInputStream(htmlContent.getBytes()),
					pdfDoc, properties);

			EndPosition endPosition = new EndPosition();
			LineSeparator separator = new LineSeparator(endPosition);

			document.add(separator);
			document.getRenderer().close();

			PdfPage page = pdfDoc.getPage(1);
			float y = endPosition.getY() - 36;
			page.setMediaBox(new Rectangle(0, y, pageSize.getWidth(), 14400 - y));

			HtmlConverter.convertToPdf(new ByteArrayInputStream(htmlContent.getBytes()), pdfDoc, properties);
			pdfDoc.close();
		} catch (Exception e) {
			log.error("error al momento de generar el pdf {}");
			return new byte[0];
		}

		return pdfOutputStream.toByteArray();
	}

	public byte[] generateWordHtml(RequestPlantilla contenidoBase) {

		try {

			var wordMLPackage = WordprocessingMLPackage.createPackage();
			XHTMLImporterImpl xhtmlImporter = new XHTMLImporterImpl(wordMLPackage);

			createHeaderPart(wordMLPackage, htmlUtil.wellFormedHtml(contenidoBase.getHeader()));

			createFooterPart(wordMLPackage, htmlUtil.wellFormedHtml(contenidoBase.getFooter()));

			wordMLPackage.getMainDocumentPart().getContent()
					.addAll(xhtmlImporter.convert(htmlUtil.wellFormedHtml(contenidoBase.getContenido()), null));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			wordMLPackage.save(baos);
			return baos.toByteArray();

		} catch (Exception e) {
			var sb = new StringBuilder();
			for (Throwable cause = e.getCause(); cause != null; cause = cause.getCause()) {
				sb.append(cause.getMessage());
				sb.append("; ");
			}
			log.error("error al generar el archivo docx {}");
			return new byte[0];
		}
	}

	private void createHeaderPart(WordprocessingMLPackage wordprocessingMLPackage, String header) throws Docx4JException{
		header = wrapHtmlToBody(header);
		HeaderPart headerPart = new HeaderPart();
	    HeaderReference headerReference = objectFactory.createHeaderReference();
	    headerReference.setId(wordprocessingMLPackage.getMainDocumentPart().addTargetPart(headerPart).getId());
	    PdfUtils.createSectionReference(wordprocessingMLPackage, headerReference);
		Hdr hdr = objectFactory.createHdr();
		XHTMLImporterImpl xhtmlImporter = new XHTMLImporterImpl(wordprocessingMLPackage);
		var imageHanlder = new XHTMLImageHandlerSat(headerPart);
		xhtmlImporter.setXHTMLImageHandler(imageHanlder);
		hdr.getContent().addAll(xhtmlImporter.convert(header, null));
		headerPart.setJaxbElement(hdr);
	}

	@SuppressWarnings("deprecation")
	private void createFooterPart(WordprocessingMLPackage wordprocessingMLPackage, String footer) throws Docx4JException  {
		footer = wrapHtmlToBody(footer);
		FooterPart footerPart = new FooterPart();
		FooterReference footerReference = objectFactory.createFooterReference();
		footerReference.setId(wordprocessingMLPackage.getMainDocumentPart().addTargetPart(footerPart).getId());
	    PdfUtils.createSectionReference(wordprocessingMLPackage, footerReference);

		CTSimpleField ctSimple = objectFactory.createCTSimpleField();
		ctSimple.setInstr(" PAGE \\* MERGEFORMAT ");

		RPr rpr = objectFactory.createRPr();
		rpr.setNoProof(new BooleanDefaultTrue());

		Text t = objectFactory.createText();
		t.setValue("17");

		R run = objectFactory.createR();
		run.getRunContent().add(rpr);
		run.getRunContent().add(t);

		ctSimple.getParagraphContent().add(run);

		var fldSimple = objectFactory.createPFldSimple(ctSimple);

		// P
		P para = objectFactory.createP();
		para.getParagraphContent().add(fldSimple);

		// Now add our paragraph to the footer
		var ftr = objectFactory.createFtr();

		XHTMLImporterImpl xhtmlImporter = new XHTMLImporterImpl(wordprocessingMLPackage);
		var imageHanlder = new XHTMLImageHandlerSat(footerPart);
		xhtmlImporter.setXHTMLImageHandler(imageHanlder);
		ftr.getContent().addAll(xhtmlImporter.convert(footer, null));
		ftr.getEGBlockLevelElts().add(para);
		footerPart.setJaxbElement(ftr);

	}

	private String wrapHtmlToBody(String htmlContent) {
		return """
				<html>
				<body>
				%%CONTENT%%
				</body>
				</html>
				""".replace("%%CONTENT%%", htmlContent);
	}
}