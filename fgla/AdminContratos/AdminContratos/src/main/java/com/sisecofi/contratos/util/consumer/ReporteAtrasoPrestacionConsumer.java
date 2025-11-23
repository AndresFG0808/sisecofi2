package com.sisecofi.contratos.util.consumer;

import com.sisecofi.libreria.comunes.model.contratos.AtrasoPrestacionModel;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class ReporteAtrasoPrestacionConsumer extends BaseExcel implements Consumer<AtrasoPrestacionModel> {
	
	
    @Override
    public void accept(AtrasoPrestacionModel atrasoPresentacionModel) {
        Row row = sheet.createRow(numeroRenglon++);
        crearCelda(row, 0 , numeroRenglon - 1);
        crearCelda(row, 1, atrasoPresentacionModel.getDescripcion());
        crearCelda(row, 2, atrasoPresentacionModel.getFechaPrestacion());
        crearCelda(row, 3, atrasoPresentacionModel.getPenasDeducciones());
    }

    public void agregarCabeceras(String cadenaTitulos) {
		BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
	}

}
