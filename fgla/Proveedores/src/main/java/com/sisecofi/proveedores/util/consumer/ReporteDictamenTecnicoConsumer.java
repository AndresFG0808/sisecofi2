package com.sisecofi.proveedores.util.consumer;

import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;
import com.sisecofi.proveedores.dto.DictamenTecnicoResponseDto;

@Component
public class ReporteDictamenTecnicoConsumer extends BaseExcel implements Consumer<DictamenTecnicoResponseDto> {

    @Override
    public void accept(DictamenTecnicoResponseDto t){
    	// FortifyIssueSuppression: Este parametro es nesesario
        Row row = sheet.createRow(numeroRenglon++);
        crearCelda(row, 0, t.getOrdenDictamenProveedor());
        crearCelda(row, 1, t.getNombreTituloServicio());
        crearCelda(row, 2, t.getAnio());
        crearCelda(row, 3, t.getResponsable());
        crearCelda(row, 4, t.getResultado());
        crearCelda(row, 5, t.getObservacion());

    }



     public void agregarCabeceras(String cadenaTitulos) {
    	 BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
    }



}
