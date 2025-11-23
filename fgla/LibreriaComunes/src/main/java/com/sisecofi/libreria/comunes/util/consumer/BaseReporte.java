package com.sisecofi.libreria.comunes.util.consumer;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

public class BaseReporte {
	
	private BaseReporte () {
		
	}
	
	public static void agregarCabeceras(Sheet sheet, Workbook workbook, String cadenaTitulos) {
		Row row = sheet.createRow(0);
        row.setHeightInPoints((3 * sheet.getDefaultRowHeightInPoints()));
        int i = 0;
        String[] titulos = cadenaTitulos.split(",");
        for (String titulo : titulos) {
            Cell cell = row.createCell(i);
            CellStyle style = workbook.createCellStyle();
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setWrapText(true);
            style.setFillPattern(FillPatternType.LEAST_DOTS);
            style.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setBorderBottom(BorderStyle.THIN);
            Font font = sheet.getWorkbook().createFont();
            font.setFontName("Calibri");
            font.setBold(true);
            font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            style.setFont(font);
            cell.setCellStyle(style);
            cell.setCellValue(titulo);
            i++;
        }
	}

}
