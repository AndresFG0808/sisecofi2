package com.sisecofi.proveedores.util.consumer;

import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;
import com.sisecofi.proveedores.dto.TituloServicioResponseDto;



@Component
public class ReporteTituloServicioProveedorConsumer extends BaseExcel implements Consumer<TituloServicioResponseDto> {

    @Override
    public void accept(TituloServicioResponseDto t){
    	// FortifyIssueSuppression: Este parametro es nesesario
        Row row = sheet.createRow(numeroRenglon++);
        crearCelda(row, 0, t.getOrdenTitulo());
        crearCelda(row, 1, t.getNumeroTitulo());
        crearCelda(row, 2, t.getNombreTituloServicio());
        crearCelda(row, 3, t.getSemaforoEstatus());
        crearCelda(row, 4, t.getVencimientoTitulo());
        crearCelda(row, 5, t.getVigencia());
        crearCelda(row, 6, t.getComentarios());

    }

    public void agregarCabeceras(String cadenaTitulos) {
    	BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
    }
}
