package com.sisecofi.proyectos.util.consumer.adminplantrabajo;

import java.time.format.DateTimeFormatter;
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
import org.springframework.stereotype.Component;

import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.proyectos.dto.adminplantrabajo.ListaTareas;


@Component
public class ReportePlanTrabajoConsumer  extends BaseExcel implements Consumer<ListaTareas>{

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


 
@Override
public void accept(ListaTareas dto) {
    procesarTarea(dto, 0); // Inicia desde el nivel 0
}

private void procesarTarea(ListaTareas dto, int nivel) {
	// Falso positivo fortify, la variable si está en uso
	Row row = sheet.createRow(numeroRenglon++);
    // Estas columnas se llenan sin validación
    crearCelda(row, 0, dto.getIdTarea());
    crearCelda(row, 1, dto.getNivelEsquema());
    crearCelda(row, 2, dto.getNombreTarea());
    crearCelda(row, 3, convertirActivo(dto.isActivo()));
    crearCelda(row, 4, dto.getDuracionPlaneada());
    crearCelda(row, 5, (dto.getFechaInicioPlaneada() != null) ? dto.getFechaInicioPlaneada().format(formatter) : "");
    crearCelda(row, 6, (dto.getFechaFinPlaneada() != null) ? dto.getFechaFinPlaneada().format(formatter) : "");

    // Validaciones aplicadas solo a las columnas especificadas
    crearCelda(row, 7, dto.getDuracionReal());

    String fechaInicioReal = (dto.getFechaInicioReal() != null) 
        ? dto.getFechaInicioReal().format(formatter) 
        : "";
    crearCelda(row, 8, fechaInicioReal);

    String fechaFinReal = (dto.getFechaFinReal() != null) 
        ? dto.getFechaFinReal().format(formatter) 
        : "";
    crearCelda(row, 9, fechaFinReal);

    crearCelda(row, 10, (dto.getPredecesora() != null) ? dto.getPredecesora() : "");
    crearCelda(row, 11, (dto.getPlaneado() != null) ? dto.getPlaneado() : "");
    crearCelda(row, 12, (dto.getCompletado() != null) ? dto.getCompletado() : "");

    // proceso recursivo
    for (ListaTareas subTarea : dto.getSubRows()) {
        procesarTarea(subTarea, nivel + 1);
    }
}



private String convertirActivo(boolean activo)    {
    return activo ? "Si" : "No";
}

public void agregarCabeceras(String cadenaTitulos) {
    Row row = sheet.createRow(0);
    row.setHeightInPoints(3 * sheet.getDefaultRowHeightInPoints());
    int i = 0;
    String[] titulos = cadenaTitulos.split(",");
    for (String titulo : titulos) {
        Cell cell = row.createCell(i);
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
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Calibri");
        font.setBold(true);
        font.setColor(HSSFColorPredefined.BLACK.getIndex());
        style.setFont(font);
        cell.setCellStyle(style);
        cell.setCellValue(titulo);

        //establcer ancho, basandose en la longitud del titulo
        int columnWidth = Math.max(titulo.length() * 256, 20 * 256); 
        sheet.setColumnWidth(i, columnWidth);

        i++;

    }

}


}


