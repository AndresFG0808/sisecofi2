package com.sisecofi.contratos.util.consumer;

import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import com.sisecofi.libreria.comunes.model.contratos.PenaContractualContratoModel;
import java.util.function.Consumer;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;

@Component
public class ReportePenasConsumer extends BaseExcel implements Consumer<PenaContractualContratoModel> {



    public void agregarCabeceras( String cadenaTitulos){
       BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
    }

    @Override
    public void accept(PenaContractualContratoModel pena) {
        Row row = sheet.createRow(numeroRenglon++);
        crearCelda(row, 0, numeroRenglon - 1); //cambiar si debe ser el id de la base de datos
        crearCelda(row, 1, pena.getInformeDocumentoConcepto());
        crearCelda(row, 2, pena.getDescripcion());
        crearCelda(row, 3, pena.getPlazoEntrega());
        crearCelda(row, 4, pena.getPenaAplicable());
    }
}
