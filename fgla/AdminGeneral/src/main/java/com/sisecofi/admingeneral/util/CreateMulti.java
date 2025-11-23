package com.sisecofi.admingeneral.util;


import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Transactional
@Slf4j
public class CreateMulti {
	
	private CreateMulti() {
    }

    public static void doSomething() {
      //COMENTADO
    }

    public static byte[] convertHtmlToPdfProf(String htmlProfo) {
        byte[] pdf = null;
        Path pdfPath = Paths.get("/sisecofi/multipage.pdf");
        try {
            Files.createDirectories(pdfPath.getParent()); 

            Document documento = new Document(PageSize.A4);
            try (OutputStream fos = Files.newOutputStream(pdfPath)) {
                PdfWriter.getInstance(documento, fos);
                String css = null;
                ElementList elements = XMLWorkerHelper.parseToElementList(htmlProfo, css);
                documento.open();

                for (Element element : elements) {
                    documento.add(element);
                }
                documento.close();
            }

            pdf = Files.readAllBytes(pdfPath);

        } catch (Exception e) {
            log.error("Se ha producido un error");
        }
        return pdf;
    }


}
