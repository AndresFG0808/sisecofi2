package com.sisecofi.admindevengados.util.consumer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.sisecofi.admindevengados.model.ServicioEstimadoModel;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;

@Component
public class ReporteServiciosEstimadosConsumer extends BaseExcel implements Consumer<ServicioEstimadoModel> {
	
	private static final String CERO= "$0.00";

	 public void agregarCabeceras( String cadenaTitulos){
	       BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
	    }
	 
	 String formato= "#,##0.######";

		@Override
		public void accept(ServicioEstimadoModel t) {
		    Row row = sheet.createRow(numeroRenglon++);
		    DecimalFormat formatter = new DecimalFormat("#,##0.00");
		    crearCelda(row, 0, numeroRenglon - 1);
		    crearCelda(row, 1, t.getServicioBase().getGrupoServiciosModel().getGrupo());
		    crearCelda(row, 2, t.getServicioBase().getConcepto());
		    crearCelda(row, 3, t.getServicioBase().getCatTipoUnidad().getNombre());
		    crearCelda(row, 4, t.getServicioBase().getGrupoServiciosModel().getCatTipoConsumo().getNombre());
		    crearCeldaConMonto(row, 5, t.getPrecioUnitarioActual(), CERO, formatter);
		    crearCeldaConFormatoDigitos(row, 6, t.getCantidadMaximaServiciosVigente());
		    crearCeldaConMonto(row, 7, t.getMontoMaximoVigente(), CERO, formatter);
		    
		    if (t.getServiciosAcumulados() != null) {
		        BigDecimal cantidad = t.getCantidadServiciosEstimadosLimp().stripTrailingZeros();
		        
		        DecimalFormat formatter2 = new DecimalFormat(formato);
		        String valorFormateado = formatter2.format(cantidad);

		        crearCelda(row, 8, valorFormateado);
		    } else {
		        crearCelda(row, 8, "0");
		    }

		    
		    crearCeldaConMonto(row, 9, t.getMontoEstimado(), CERO, formatter);

		    if (t.getServiciosAcumulados() != null) {
		        BigDecimal cantidad = t.getServiciosAcumulados().stripTrailingZeros();
		        
		        DecimalFormat formatter2 = new DecimalFormat(formato);
		        String valorFormateado = formatter2.format(cantidad);

		        crearCelda(row, 10, valorFormateado);
		    } else {
		        crearCelda(row, 10, t.getServiciosAcumulados());
		    }
		    
		    crearCelda(row, 11, "$" + formatter.format(t.getMontoEstimadoAcumulado()));

		    crearCeldaConPorcentaje(row, 12, t.getPorcentajeServiciosEstimadosAcumulados(), "0%");
		    crearCeldaConPorcentaje(row, 13, t.getPorcentajeMontoEstimadoAcumulado(), "0%");
		}
		
		private void crearCeldaConFormatoDigitos(Row row, int column, BigDecimal value) {
		    if (value != null) {
		        DecimalFormat formatter = new DecimalFormat(formato); 
		        formatter.setRoundingMode(RoundingMode.HALF_UP);
		        String valorFormateado = formatter.format(value);
		        crearCelda(row, column, valorFormateado);
		    } else {
		        crearCelda(row, column, ""); 
		    }
		}

		private void crearCeldaConMonto(Row row, int column, BigDecimal value, String defaultValue, DecimalFormat formatter) {
		    if (value != null) {
		        crearCelda(row, column, "$" + formatter.format(value));
		    } else {
		        crearCelda(row, column, defaultValue);
		    }
		}

		private void crearCeldaConPorcentaje(Row row, int column, BigDecimal porcentaje, String defaultValue) {
		    if (porcentaje != null) {
		        crearCelda(row, column, porcentaje+"%");
		    } else {
		        crearCelda(row, column, defaultValue);
		    }
		}


}
