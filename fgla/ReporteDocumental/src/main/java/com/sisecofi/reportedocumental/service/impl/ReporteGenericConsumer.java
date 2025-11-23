package com.sisecofi.reportedocumental.service.impl;

import java.util.List;
import java.util.function.Consumer;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sisecofi.libreria.comunes.dto.dinamico.Agrupacion;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.reportedocumental.service.dinamico.Mapper;

/**
 * @author ayuso2104@gmail.com
 */

@Component
@Scope("prototype")
public class ReporteGenericConsumer extends BaseExcel implements Consumer<Object[]> {
	private Mapper mapper;

	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}

	public void agregarCabeceras(List<String> etiquetas) {
		int i = 0;
		Row row = sheet.createRow(0);
		row.setHeightInPoints((3 * sheet.getDefaultRowHeightInPoints()));
		for (String titulo : etiquetas) {
			Cell cell = row.createCell(i);
			CellStyle style = crearEstilo();
			cell.setCellStyle(style);
			cell.setCellValue(titulo);
			i++;
		}
	}

	public void agregarCabeceras(List<String> etiquetas, List<Agrupacion> grupos) {
		Row agrupacionRow = sheet.createRow(0);
		int inicioGrupo = 0;

		if (grupos != null && !grupos.isEmpty()) {
			for (Agrupacion grupo : grupos) {
				String etiqueta = grupo.getEtiqueta();
				Integer cantidadPorGrupo = grupo.getTamanio();

				Cell cell = agrupacionRow.createCell(inicioGrupo);
				cell.setCellValue(etiqueta);
				CellStyle estiloGrupo = crearEstilo();
				cell.setCellStyle(estiloGrupo);

				sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, inicioGrupo,
						inicioGrupo + cantidadPorGrupo - 1));

				inicioGrupo += cantidadPorGrupo;
			}
		}

		Row row = sheet.createRow(1);
		row.setHeightInPoints((3 * sheet.getDefaultRowHeightInPoints()));

		int i = 0;
		for (String titulo : etiquetas) {
			Cell cell = row.createCell(i);
			CellStyle style = crearEstilo();
			cell.setCellStyle(style);
			cell.setCellValue(titulo);
			i++;
		}

		if (grupos != null && !grupos.isEmpty()) {
			this.numeroRenglon++;
		}

	}

	private CellStyle crearEstilo() {
		CellStyle style = workbook.createCellStyle();
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setWrapText(true);
		style.setFillPattern(FillPatternType.LEAST_DOTS);
		style.setFillBackgroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		Font font = workbook.createFont();
		font.setFontName("Calibri");
		font.setBold(true);
		font.setColor(HSSFColorPredefined.BLACK.getIndex());
		style.setFont(font);
		return style;
	}

	@Override
	public void accept(Object[] t) {
		Row row = sheet.createRow(numeroRenglon++);
		int contador = 0;
		for (Object ti : t) {
			mapper.map(ti, this, row, contador, t);
			contador++;
		}
	}

}
