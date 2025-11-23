package com.sisecofi.admindevengados.util.consumer;

import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Row;

import com.sisecofi.admindevengados.dto.PenaContractualExcelDto;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;

public abstract class PenasDeduccionesBaseConsumer extends BaseExcel implements Consumer<PenaContractualExcelDto> {

    public void agregarCabeceras(String cadenaTitulos) {
        BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
    }

    @Override
    public void accept(PenaContractualExcelDto penaContractualModel) {
    	// Incrementamos numeroRenglon para cada nueva fila
        Row row = sheet.createRow(numeroRenglon++);
        crearCeldas(row, penaContractualModel);
    }

    protected abstract void crearCeldas(Row row, PenaContractualExcelDto model);
}