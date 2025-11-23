package com.sisecofi.admindevengados.util.consumer;

import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.sisecofi.admindevengados.dto.ResumenConsolidadoDto;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;

@Component
public class ReporteDictamenesConsumer extends BaseExcel implements Consumer<ResumenConsolidadoDto> {

	 public void agregarCabeceras( String cadenaTitulos){
	       BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
	    }

	    @Override
	    public void accept(ResumenConsolidadoDto dictamen) {
	        Row row = sheet.createRow(numeroRenglon++);
	        crearCelda(row, 0, dictamen.getNombreFase());
	        crearCelda(row, 1, dictamen.getSubTotal());
	        crearCelda(row, 2, dictamen.getDeducciones());
	        crearCelda(row, 3, dictamen.getIeps());
	        crearCelda(row, 4, dictamen.getIva());
	        crearCelda(row, 5, dictamen.getOtrosImpuestos());
	        crearCelda(row, 6, dictamen.getTotal());
	        crearCelda(row, 7, dictamen.getTotalPesos());
	    }

}
