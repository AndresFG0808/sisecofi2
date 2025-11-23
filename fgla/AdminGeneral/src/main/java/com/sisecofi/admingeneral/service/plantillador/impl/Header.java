package com.sisecofi.admingeneral.service.plantillador.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author servidor12
 */
@Slf4j
public class Header extends PdfPageEventHelper {

	private static final int MARGEN_IZQUIERO = 35;
	private static final int MARGEN_TOP = 770;
	private static final int TAMANIO_HEADER = 510;

	private ElementList encabezado;
	private ElementList piePagina;
	private Phrase[] headerPhrase = new Phrase[2];
	private int pagenumber;
	private int totalPage;
	private float margenIzquierdo;
	private float margenDerecho;
	private String leyenda;
	private String pathImg;
	/** El template que almacena el total de paginas del documento. */
	private PdfTemplate total;

	private String pathBanderaImg;

	@Override
	public void onOpenDocument(PdfWriter writer, Document document) {
		total = writer.getDirectContent().createTemplate(CteNum.TREINTA, CteNum.DIESISEIS);
	}

	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		Rectangle rect = writer.getBoxSize("art");
		PdfPTable table = new PdfPTable(2);
		PdfPTable table2 = new PdfPTable(1);
		PdfPCell cell = new PdfPCell();
		PdfPCell cell1 = new PdfPCell();
		PdfPCell cell2 = new PdfPCell();

		try {
			log.info("rec with {}", rect.getWidth());
			log.info("total with {}", getMargenDerecho() <= CteNum.SESENTA ? rect.getWidth() + 2 : rect.getWidth());

			table.setTotalWidth(TAMANIO_HEADER);

			/**
			 * Table 2
			 *
			 */
			table2.setTotalWidth(rect.getWidth() - (getMargenDerecho() + MARGEN_IZQUIERO));

			cicloUno(cell, cell1);

			table.addCell(cell);
			table.addCell(cell1);

			log.info("Margen izquierdo {}", getMargenIzquierdo());
			table.writeSelectedRows(0, -40, MARGEN_IZQUIERO, MARGEN_TOP, writer.getDirectContent());

			cicloDos(cell2);

			table2.addCell(cell2);
			table2.writeSelectedRows(CteNum.CERO, -(CteNum.CINCUENTA), getMargenIzquierdo(), CteNum.SETENTAYTRES,
					writer.getDirectContent());

			File file = new File(pathImg + "footer1.png");
			
			if (file.exists()) {
	            byte[] footer1 = Files.readAllBytes(file.toPath());
	            Image background = Image.getInstance(footer1);
	            writer.getDirectContentUnder().addImage(background, 430, 0, CteNum.CERO, 40, 35, 15);

	           
	        }

			File file2 = new File(pathImg + "footer2.png");
			if (file2.exists()) {
	            byte[] footer2 = Files.readAllBytes(file2.toPath());
	            Image background2 = Image.getInstance(footer2);
	            writer.getDirectContentUnder().addImage(background2, 120, 0, CteNum.CERO, 70, 445, 15);

	            
	        }
		} catch (DocumentException ex) {
			log.error("Ops!");
		} catch (MalformedURLException e) {
			log.error("MalformedURLException!");
		} catch (IOException e) {
			log.error("IOException!");
		}
		Phrase watermark = new Phrase(getLeyenda(),
				new Font(FontFamily.HELVETICA, CteNum.SESENTA, Font.NORMAL, BaseColor.LIGHT_GRAY));

		PdfContentByte canvas = writer.getDirectContentUnder();
		ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, watermark,
				(CteNum.CIEN + CteNum.CIEN + CteNum.NOVENTA + CteNum.OCHO),
				(CteNum.CUATROCIENTOS + CteNum.VEINTE + CteNum.UNO), (CteNum.CINCUENTA + CteNum.CINCO));

	}

	public void cicloUno(PdfPCell cell, PdfPCell cell1) throws IOException, DocumentException {
		File file2 = new File(pathImg + "header.png");
		byte[] footer21 = Files.readAllBytes(file2.toPath());
		Image background21 = Image.getInstance(footer21);
		background21.scaleAbsolute(320, 30);
		cell.addElement(background21);
		cell.setBorder(CteNum.CERO);
		for (Element element : getEncabezado()) {
			try {
				log.info("Elemenst: {} ", element);
				cell1.addElement(element);
				cell1.setBorder(CteNum.CERO);
			} catch (Exception e) {
				log.error("Elemento no permitido: {}");
			}
		}
	}

	public void cicloDos(PdfPCell cell2) {
		for (Element element : getPiePagina()) {
			cell2.addElement(element);
			cell2.setBorder(CteNum.CERO);
		}
	}

	@Override
	public void onCloseDocument(PdfWriter writer, Document document) {
		Font fuenteNumero = new Font(FontFamily.HELVETICA, CteNum.ONCE, Font.NORMAL, BaseColor.BLACK);
		ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
				new Phrase(String.valueOf(writer.getPageNumber() - 1), fuenteNumero), CteNum.TRES, CteNum.TRES,
				CteNum.CERO);
	}

	public ElementList getEncabezado() {
		return encabezado;
	}

	public void setEncabezado(ElementList encabezado) {
		this.encabezado = encabezado;
	}

	public ElementList getPiePagina() {
		return piePagina;
	}

	public void setPiePagina(ElementList piePagina) {
		this.piePagina = piePagina;
	}

	public Phrase[] getHeader() {
		return (headerPhrase == null ? null : headerPhrase.clone());
	}

	public void setHeader(Phrase[] headerPhrase) {
		this.headerPhrase = (headerPhrase == null ? null : headerPhrase.clone());
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPagenumber() {
		return pagenumber;
	}

	public void setPagenumber(int pagenumber) {
		this.pagenumber = pagenumber;
	}

	public float getMargenIzquierdo() {
		return margenIzquierdo;
	}

	public void setMargenIzquierdo(float margenIzquierdo) {
		this.margenIzquierdo = margenIzquierdo;
	}

	public float getMargenDerecho() {
		return margenDerecho;
	}

	public void setMargenDerecho(float margenDerecho) {
		this.margenDerecho = margenDerecho;
	}

	public String getLeyenda() {
		return leyenda;
	}

	public void setLeyenda(String leyenda) {
		this.leyenda = leyenda;
	}

	public String getPathImg() {
		return pathImg;
	}

	public void setPathImg(String pathImg) {
		this.pathImg = pathImg;
	}

	public String getPathBanderaImg() {
		return pathBanderaImg;
	}

	public void setPathBanderaImg(String pathBanderaImg) {
		this.pathBanderaImg = pathBanderaImg;
	}


}
