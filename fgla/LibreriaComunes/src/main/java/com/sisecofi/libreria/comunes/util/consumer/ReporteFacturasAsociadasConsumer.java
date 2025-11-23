package com.sisecofi.libreria.comunes.util.consumer;

import java.text.NumberFormat;
import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.sisecofi.libreria.comunes.dto.dictamen.FacturaContratoDto;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;

@Component
public class ReporteFacturasAsociadasConsumer extends BaseExcel implements Consumer<FacturaContratoDto> {

	 public void agregarCabeceras( String cadenaTitulos){
	       BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
	    }

	    @Override
	    public void accept(FacturaContratoDto factura) {
	        Row row = sheet.createRow(numeroRenglon++);
	        crearCelda(row, 0, factura.getIdDictamen());
	        crearCelda(row, 1, factura.getComprobanteFiscal());
	        if(factura.isConvenioColaboracion()) {
	        	crearCelda(row, 2, "Sí");
	        }else {
	        	crearCelda(row, 2, "No");
	        }
	        NumberFormat formatter = NumberFormat.getNumberInstance(); 
	        String montoFormateado = formatter.format(factura.getMonto());
	        crearCelda(row, 3, "$"+montoFormateado); 
	        montoFormateado = formatter.format(factura.getMontoPesos());
	        crearCelda(row, 4, "$"+montoFormateado); 
	        crearCelda(row, 5, factura.getEstatus());
	        crearCelda(row, 6, factura.getTipoCambioReferencial());
	    }

}
