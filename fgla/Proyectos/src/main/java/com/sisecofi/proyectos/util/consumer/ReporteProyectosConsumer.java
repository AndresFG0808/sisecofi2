package com.sisecofi.proyectos.util.consumer;

import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;
import com.sisecofi.proyectos.dto.ProyectoDtoLigero;

import java.text.DecimalFormat;
import java.util.function.Consumer;

@Component
public class ReporteProyectosConsumer extends BaseExcel implements Consumer<ProyectoDtoLigero> {



    public void agregarCabeceras( String cadenaTitulos){
       BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
    }

    @Override
    public void accept(ProyectoDtoLigero proyecto) {
    	// Falso positivo fortify, la variable si está en uso
    	Row row = sheet.createRow(numeroRenglon++);
        crearCelda(row, 0, proyecto.getNombreCorto());
        	
        	crearCelda(row, 1, proyecto.getFechaInicio());
            crearCelda(row, 2, proyecto.getFechaFin());
            
            DecimalFormat formatter = new DecimalFormat("#,##0.00");
            String montoFormateado = formatter.format(proyecto.getMonto());
            crearCelda(row, 6, "$"+montoFormateado);
            
            crearCelda(row, 5, proyecto.getAreaResponsableSt());
            crearCelda(row, 4, proyecto.getAreaSolicitante());
            crearCelda(row, 3, proyecto.getLiderProyecto());
           
        crearCelda(row, 7, proyecto.getEstatusProyecto());
    }
    	
}
