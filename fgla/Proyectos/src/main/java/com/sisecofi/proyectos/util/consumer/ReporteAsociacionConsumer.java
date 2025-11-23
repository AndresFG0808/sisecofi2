package com.sisecofi.proyectos.util.consumer;

import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;
import com.sisecofi.proyectos.dto.AsociacionResponse;
import java.util.function.Consumer;

@Component
public class ReporteAsociacionConsumer extends BaseExcel implements Consumer<AsociacionResponse> {



    public void agregarCabeceras( String cadenaTitulos){
       BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
    }

    @Override
    public void accept(AsociacionResponse asociacion) {
    	// Falso positivo fortify, la variable si está en uso
        Row row = sheet.createRow(numeroRenglon++);
        crearCelda(row, 0, asociacion.getFase().getNombre());
        crearCelda(row, 1, asociacion.getPlantilla().getNombre());
        crearCelda(row, 2, asociacion.getFechaAsignacion());
    }
}
