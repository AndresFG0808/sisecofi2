package com.sisecofi.admindevengados.util.consumer;

import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.sisecofi.libreria.comunes.dto.dictamen.DevengadoBusquedaResponse;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;

@Component
public class ReporteDevengadoEstimacionConsumer extends BaseExcel implements Consumer<DevengadoBusquedaResponse> {

	 public void agregarCabeceras( String cadenaTitulos){
	       BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
	    }
	 
	    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	    @Override
	    public void accept(DevengadoBusquedaResponse dictamen) {
	    	// Incrementamos numeroRenglon para cada nueva fila
	        Row row = sheet.createRow(numeroRenglon++);
	        crearCelda(row, 0, dictamen.getId());
	        crearCelda(row, 1, dictamen.getPeriodoControl());
	        if (dictamen.getPeriodoInicio()!=null) {
	        	String fechaFormateada = dictamen.getPeriodoInicio().format(outputFormatter);
		        crearCelda(row, 2, fechaFormateada);
	        }else {
	        	crearCelda(row, 2, "");
	        }
	        if (dictamen.getPeriodoFin()!=null) {
	        	String fechaFormateada = dictamen.getPeriodoFin().format(outputFormatter);
		        crearCelda(row, 3, fechaFormateada);
	        }else {
	        	crearCelda(row, 3, "");
	        }
	        crearCelda(row, 4, dictamen.getProveedor());
	        crearCelda(row, 5, dictamen.getEstatus());
	        crearCelda(row, 6, dictamen.getMontoEstimado());
	        crearCelda(row, 7, dictamen.getMontoEstimadoPesos());
	        crearCelda(row, 8, dictamen.getMontoDictaminado());
	        crearCelda(row, 9, dictamen.getMontoDictaminadoPesos()); 
	    }

}
