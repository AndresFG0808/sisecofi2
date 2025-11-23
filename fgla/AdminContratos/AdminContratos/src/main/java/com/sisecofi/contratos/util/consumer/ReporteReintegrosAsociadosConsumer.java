package com.sisecofi.contratos.util.consumer;

import com.sisecofi.libreria.comunes.model.reintegros.ReintegrosAsociadosModel;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.Consumer;
@Component
public class ReporteReintegrosAsociadosConsumer extends BaseExcel implements Consumer<ReintegrosAsociadosModel> {
    private int conteo = 0;
    @Override
    public void accept(ReintegrosAsociadosModel t) {
        Row row = sheet.createRow(numeroRenglon++);

        crearCelda(row, 0, conteo++);
        crearCelda(row, 2, t.getImporte());
        crearCelda(row, 3, t.getInteres());
        crearCelda(row, 4, t.getTotal());
        crearCelda(row, 5, t.getFechaReintegro());

    }

    public void agregarCabeceras( String cadenaTitulos){
	       BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
	    }

    public void agregarValoresTotales(BigDecimal[] valoresFijos) {
        int totalColumnas = sheet.getRow(0).getLastCellNum();

        if (totalColumnas < 3) {
            throw new IllegalArgumentException("No hay suficientes columnas para agregar valores fijos.");
        }
        Row nuevaFila = sheet.getRow(1);

        int startColumn = totalColumnas - 3;
        for (int i = 0; i < valoresFijos.length; i++) {
            crearCelda(nuevaFila, startColumn + i, valoresFijos[i]);
        }
    }
}

