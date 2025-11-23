package com.sisecofi.catalogos.util.consumer;

import com.sisecofi.catalogos.dto.EmpleadoDto;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;

import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

@Component
public class ReporteEmpleadosConsumer extends BaseExcel implements Consumer<EmpleadoDto> {



    public void agregarCabeceras( String cadenaTitulos){
       BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
    }

    @Override
    public void accept(EmpleadoDto empleado) {
    	Row row = sheet.createRow(numeroRenglon++);
    	crearCelda(row, 0, numeroRenglon - 1); 
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        	crearCelda(row, 1, empleado.getNombre());
            crearCelda(row, 2, empleado.getCatTipoEmpleado().getNombre());
            crearCelda(row, 3, empleado.getCorreo());
            crearCelda(row, 4, empleado.getTelefono());
            crearCelda(row, 5, empleado.getFechaInicioVigencia().format(formatter));
            crearCelda(row, 6, empleado.getFechaFinVigencia().format(formatter));
            crearCelda(row, 7, empleado.getFechaCreacion().format(formatter));
            crearCelda(row, 8, empleado.getFechaModificacion().format(formatter));
            if (empleado.isEstatus()) {
            	crearCelda(row, 9, "Activo");
            }else {
            	crearCelda(row, 9, "Inactivo");
            }
            
    }
    	
}
