package com.sisecofi.libreria.comunes.util.consumer;

import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.sisecofi.libreria.comunes.dto.dictamen.DevengadoBusquedaResponse;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;

@Component
public class ReporteDevengadoDictamenConsumer extends BaseExcel implements Consumer<DevengadoBusquedaResponse> {

	 public void agregarCabeceras( String cadenaTitulos){
	       BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
	    }

	    @Override
	    public void accept(DevengadoBusquedaResponse dictamen) {
	        Row row = sheet.createRow(numeroRenglon++);
	        crearCelda(row, 0, dictamen.getId());
	        crearCelda(row, 1, dictamen.getMontoDictaminadoPesos());
	        crearCelda(row, 2, dictamen.getEstatus());
	        crearCelda(row, 3, dictamen.getComprobanteFiscal());
	        crearCelda(row, 4, dictamen.getPendientePago());
	    }

}
