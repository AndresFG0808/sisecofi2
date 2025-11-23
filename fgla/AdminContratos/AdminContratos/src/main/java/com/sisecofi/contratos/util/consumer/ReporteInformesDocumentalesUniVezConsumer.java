package com.sisecofi.contratos.util.consumer;

import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesUnicaVezModel;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;
@Component
public class ReporteInformesDocumentalesUniVezConsumer extends BaseExcel implements Consumer<InformesDocumentalesUnicaVezModel> {
	@Override
	public void accept(InformesDocumentalesUnicaVezModel t) {
		 Row row = sheet.createRow(numeroRenglon++);
		 crearCelda(row, 0 , numeroRenglon - 1);
		 crearCelda(row, 1, t.getFase());
		 crearCelda(row, 2, t.getInformeDocumental());
		 crearCelda(row, 3, t.getFechaEntrega());
		 crearCelda(row, 4, t.getPenasDeduccionesAplicables());
		 crearCelda(row, 5, t.getDescripcion());
	}
	
	public void agregarCabeceras(String cadenaTitulos) {
		BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
	}

}
