package com.sisecofi.contratos.util.consumer;

import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesServiciosModel;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;

@Component
public class ReporteInformeDocumentalesServiciosConsumer extends BaseExcel implements Consumer<InformesDocumentalesServiciosModel>{

	@Override
	public void accept(InformesDocumentalesServiciosModel t) {
		Row row = sheet.createRow(numeroRenglon++);
		crearCelda(row, 0 , numeroRenglon - 1);
		 crearCelda(row, 1, t.getInformeDocumental());
		 if(t.getCatPeriodicidad()!=null) {
			 crearCelda(row, 2, t.getCatPeriodicidad().getNombre()); 
		 }else {
			 crearCelda(row, 2, "");
		 }
		 crearCelda(row, 3, t.getFechaEntrega());
		 crearCelda(row, 4, t.getPenasDeduccionesAplicables());
		 crearCelda(row, 5, t.getDescripcion());
	}

	public void agregarCabeceras( String cadenaTitulos){
	       BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
	    }
	
}
