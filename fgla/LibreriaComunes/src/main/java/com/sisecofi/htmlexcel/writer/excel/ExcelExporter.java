package com.sisecofi.htmlexcel.writer.excel;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Clase para exportar HTML a Excel
 * Convierte tablas HTML en hojas de Excel
 */
public class ExcelExporter {
    
    private static final Logger logger = Logger.getLogger(ExcelExporter.class.getName());

    /**
     * Exporta HTML a Excel y retorna el contenido como byte[]
     * @param html String con contenido HTML
     * @return byte[] con el contenido del archivo Excel
     */
    public byte[] exportarHtmlRcp(String html) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            XSSFWorkbook workbook = convertHtmlToWorkbook(html);
            workbook.write(byteArrayOutputStream);
            workbook.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            logger.severe("Error al exportar HTML a Excel: " + e.getMessage());
            return new byte[]{};
        }
    }

    /**
     * Exporta HTML a Excel y guarda en archivo
     * @param html String con contenido HTML
     * @param file Archivo donde guardar el Excel
     */
    public void exportHtml(String html, File file) {
        try {
            XSSFWorkbook workbook = convertHtmlToWorkbook(html);
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                workbook.write(fileOutputStream);
            }
            workbook.close();
            logger.info("Archivo Excel generado exitosamente: " + file.getAbsolutePath());
        } catch (IOException e) {
            logger.severe("Error al exportar HTML a archivo Excel: " + e.getMessage());
        }
    }

    /**
     * Convierte HTML a XSSFWorkbook
     * @param html String con contenido HTML
     * @return XSSFWorkbook con el contenido procesado
     */
    private XSSFWorkbook convertHtmlToWorkbook(String html) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Document doc = Jsoup.parse(html);
        
        // Buscar todas las tablas en el HTML
        Elements tables = doc.select("table");
        
        if (tables.isEmpty()) {
            // Si no hay tablas, crear una hoja vacía
            workbook.createSheet("Sheet1");
        } else {
            // Crear una hoja por cada tabla
            int sheetIndex = 0;
            for (Element table : tables) {
                String sheetName = getSheetName(table, sheetIndex);
                Sheet sheet = workbook.createSheet(sheetName);
                processTable(sheet, table);
                sheetIndex++;
            }
        }
        
        return workbook;
    }

    /**
     * Obtiene el nombre de la hoja desde el atributo data-sheet-name o genera uno por defecto
     */
    private String getSheetName(Element table, int index) {
        String name = table.attr("data-sheet-name");
        if (name == null || name.isEmpty()) {
            name = "Sheet" + (index + 1);
        }
        // Excel limita nombres de hoja a 31 caracteres
        if (name.length() > 31) {
            name = name.substring(0, 31);
        }
        return name;
    }

    /**
     * Procesa una tabla HTML y la convierte en una hoja de Excel
     */
    private void processTable(Sheet sheet, Element table) {
        int rowIndex = 0;
        
        // Procesar tbody o directamente tr
        Elements tbody = table.select("tbody");
        Elements rows;
        
        if (!tbody.isEmpty()) {
            rows = tbody.select("tr");
        } else {
            rows = table.select("tr");
        }
        
        for (Element row : rows) {
            Row excelRow = sheet.createRow(rowIndex);
            Elements cells = row.select("td, th");
            
            int cellIndex = 0;
            for (Element cell : cells) {
                Cell excelCell = excelRow.createCell(cellIndex);
                
                // Obtener el contenido de la celda
                String cellContent = cell.text();
                
                // Intentar parsear como número si es posible
                try {
                    double numValue = Double.parseDouble(cellContent);
                    excelCell.setCellValue(numValue);
                } catch (NumberFormatException e) {
                    // Si no es número, guardar como texto
                    excelCell.setCellValue(cellContent);
                }
                
                // Aplicar estilos si existen
                applyCellStyles(excelCell, cell, sheet.getWorkbook());
                
                cellIndex++;
            }
            
            rowIndex++;
        }
        
        // Auto-ajustar ancho de columnas
        for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
        }
    }

    /**
     * Aplica estilos a las celdas basado en los estilos CSS del HTML
     */
    private void applyCellStyles(Cell cell, Element htmlCell, Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        
        String styleAttr = htmlCell.attr("style");
        if (styleAttr != null && !styleAttr.isEmpty()) {
            // Procesar estilos CSS simples
            if (styleAttr.contains("text-align:center") || styleAttr.contains("text-align: center")) {
                style.setAlignment(HorizontalAlignment.CENTER);
            }
            if (styleAttr.contains("border")) {
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderTop(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
            }
        }
        
        // Aplicar color de fondo si la celda tiene atributo bgcolor
        String bgColor = htmlCell.attr("bgcolor");
        if (bgColor != null && !bgColor.isEmpty()) {
            try {
                // Convertir hex color a IndexedColor
                short colorIndex = convertHexColorToIndexed(bgColor);
                style.setFillForegroundColor(colorIndex);
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            } catch (Exception e) {
                logger.warning("No se pudo aplicar color de fondo: " + bgColor);
            }
        }
        
        cell.setCellStyle(style);
    }

    /**
     * Convierte color HEX a IndexedColor de POI
     */
    private short convertHexColorToIndexed(String hexColor) {
        // Colores básicos predefinidos
        hexColor = hexColor.toLowerCase().replace("#", "");
        
        switch (hexColor) {
            case "000000": return IndexedColors.BLACK.getIndex();
            case "ffffff": return IndexedColors.WHITE.getIndex();
            case "ff0000": return IndexedColors.RED.getIndex();
            case "00ff00": return IndexedColors.GREEN.getIndex();
            case "0000ff": return IndexedColors.BLUE.getIndex();
            case "ffff00": return IndexedColors.YELLOW.getIndex();
            case "ff00ff": return IndexedColors.PINK.getIndex();
            case "00ffff": return IndexedColors.LIGHT_BLUE.getIndex();
            default: return IndexedColors.WHITE.getIndex();
        }
    }
}
