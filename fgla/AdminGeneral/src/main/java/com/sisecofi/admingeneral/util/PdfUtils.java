package com.sisecofi.admingeneral.util;

import java.util.List;

import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.FooterReference;
import org.docx4j.wml.HdrFtrRef;
import org.docx4j.wml.HeaderReference;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.SectPr;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.media.MediaType;

public class PdfUtils {

	private PdfUtils() {
	}
	
	public static void configurePdfDocument(PdfDocument pdfDoc, ConverterProperties properties) {
        pdfDoc.setTagged();
        PageSize pageSize = new PageSize(800f, 14400f);
        pdfDoc.setDefaultPageSize(pageSize);
        
        MediaDeviceDescription mediaDeviceDescription = new MediaDeviceDescription(MediaType.SCREEN);
        mediaDeviceDescription.setWidth(pageSize.getWidth());
        properties.setMediaDeviceDescription(mediaDeviceDescription);
    }

    public static void finalizePdfDocument(Document document, PdfDocument pdfDoc, EndPosition endPosition) {
        LineSeparator separator = new LineSeparator(endPosition);
        document.add(separator);
        document.getRenderer().close();

        PdfPage page = pdfDoc.getPage(1);
        float y = endPosition.getY() - 36;
        page.setMediaBox(new Rectangle(0, y, pdfDoc.getDefaultPageSize().getWidth(), 14400 - y));
    }
    
    private static final ObjectFactory objectFactory = new ObjectFactory();

    public static void createSectionReference(WordprocessingMLPackage wordprocessingMLPackage, Object reference) {
        List<SectionWrapper> sections = wordprocessingMLPackage.getDocumentModel().getSections();
        SectPr sectPr = sections.get(sections.size() - 1).getSectPr();
        
        if (sectPr == null) {
            sectPr = objectFactory.createSectPr();
            wordprocessingMLPackage.getMainDocumentPart().addObject(sectPr);
            sections.get(sections.size() - 1).setSectPr(sectPr);
        }

        if (reference instanceof HeaderReference headerReference) {
            headerReference.setType(HdrFtrRef.DEFAULT);
            sectPr.getEGHdrFtrReferences().add(headerReference);
        } else if (reference instanceof FooterReference footerReference) {
            footerReference.setType(HdrFtrRef.DEFAULT);
            sectPr.getEGHdrFtrReferences().add(footerReference);
        }
    }


}
