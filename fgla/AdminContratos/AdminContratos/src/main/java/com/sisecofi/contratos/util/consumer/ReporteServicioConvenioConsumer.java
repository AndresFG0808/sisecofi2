package com.sisecofi.contratos.util.consumer;

import com.sisecofi.libreria.comunes.model.convenioModificatorio.ServicioConvenioModel;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.function.Consumer;

@Component
public class ReporteServicioConvenioConsumer extends BaseExcel implements Consumer<ServicioConvenioModel> {

	public void agregarCabeceras(String cadenaTitulos) {
		BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
	}

	@Override
	public void accept(ServicioConvenioModel t) { 
	    Row row = sheet.createRow(numeroRenglon++);
	    crearCelda(row, 0, numeroRenglon - 1);
	    crearCelda(row, 1, t.getTipoConsumo());
	    crearCelda(row, 2, t.getConcepto());

	    crearCeldaFormateada(row, 3, t.getNumeroMaximoServicios(), false);
	    crearCeldaConFormatoMoneda(row, 4 , t.getMontoMaximo());
	    crearCeldaConFormatoMoneda(row, 5, t.getPrecioUnitario());
	    crearCeldaFormateada(row, 6, t.getCompensacionServicios(), false);
	    crearCeldaConFormatoMoneda(row, 7, t.getCompensacionMonto());
	    crearCeldaFormateada(row, 8, t.getIncrementoServicios(), false);
	    crearCeldaConFormatoMoneda(row, 9, t.getIncrementoMonto());
	    crearCeldaFormateada(row, 10, t.getNumeroTotalServicios(), false);
	    crearCeldaConFormatoMoneda(row, 11, t.getMontoMaximoTotal());
	    if (Boolean.TRUE.equals(t.getIeps())) {
	    	crearCelda(row, 12, "Sí");
	    }else {
	    	crearCelda(row, 12, "No");
	    }
	    
	}
	
	
	private void crearCeldaConFormatoMoneda(Row row, int column, BigDecimal value) {
	    if (value != null) {
	        DecimalFormat decimalFormat = new DecimalFormat("$#,##0.00");
	        decimalFormat.setRoundingMode(RoundingMode.DOWN);
	        String valorFormateado = decimalFormat.format(value); 
	        crearCelda(row, column, valorFormateado); 
	    } else {
	        crearCelda(row, column, ""); 
	    }
	}

	private void crearCeldaFormateada(Row row, int columnIndex, BigDecimal value, boolean includeCurrency) {
	    if (value != null) {
	        BigDecimal cantidad = value.stripTrailingZeros();
	        DecimalFormat decimalFormat = new DecimalFormat(includeCurrency ? "$#,##0.######" : "#,##0.######");
	        decimalFormat.setRoundingMode(RoundingMode.DOWN);
	        
	        String valorFormateado = cantidad.scale() > 0 
	            ? decimalFormat.format(cantidad.setScale(Math.min(6, cantidad.scale()), RoundingMode.DOWN))
	            : decimalFormat.format(cantidad);

	        crearCelda(row, columnIndex, valorFormateado);
	    } else {
	        crearCelda(row, columnIndex, value);
	    }
	}
}
