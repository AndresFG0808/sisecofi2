package com.sisecofi.contratos.util.consumer;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;
import org.apache.poi.ss.usermodel.Row;

import com.sisecofi.libreria.comunes.model.contratos.NivelesServicioSLAModel;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;

@Component
public class ReporteNivelesServicioSLAConsumer extends BaseExcel implements Consumer<NivelesServicioSLAModel>{
	@Override
	public void accept(NivelesServicioSLAModel t) {
		Row row = sheet.createRow(numeroRenglon++);
		crearCelda(row, 0 , numeroRenglon - 1);
		 crearCelda(row, 1, t.getSla());
		 crearCelda(row, 2, t.getDeduccionesAplicables());
		 crearCelda(row, 3, t.getObjectivoMinimo());
		 crearCelda(row, 4, t.getDescripcion());
	}
	
	public void agregarCabeceras(String cadenaTitulos) {
		BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
	}

}
