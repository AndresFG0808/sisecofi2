package com.sisecofi.libreria.comunes.util.consumer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.sisecofi.libreria.comunes.dto.dictamen.DevengadoBusquedaResponse;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;

@Component
public class ReporteDictamenesAsociadosConsumer extends BaseExcel implements Consumer<DevengadoBusquedaResponse> {

	 public void agregarCabeceras( String cadenaTitulos){
	       BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
	    }

	    @Override
	    public void accept(DevengadoBusquedaResponse dictamen) {
	        Row row = sheet.createRow(numeroRenglon++);
	        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        crearCelda(row, 0, dictamen.getId());
	        crearCelda(row, 1, dictamen.getPeriodoControl());
	        if (dictamen.getPeriodoInicio() != null) {
	            String fechaFormateada = dictamen.getPeriodoInicio().format(outputFormatter);
	            crearCelda(row, 2, fechaFormateada);
	        } else {
	            crearCelda(row, 2, "");
	        }
	        if (dictamen.getPeriodoFin() != null) {
	            String fechaFormateada = dictamen.getPeriodoFin().format(outputFormatter);
	            crearCelda(row, 3, fechaFormateada);
	        } else {
	            crearCelda(row, 3, "");
	        }
	        crearCelda(row, 4, dictamen.getEstatus());
	        crearCelda(row, 5, dictamen.getMontoDictaminado());
	        crearCelda(row, 6, dictamen.getMontoDictaminadoPesos());
	        
	        
	        BigDecimal tipoCambio = dictamen.getTipoCambioReferencial();

	        if (tipoCambio == null) {
	            crearCelda(row, 7, tipoCambio);
	        } else {
	            tipoCambio = tipoCambio.stripTrailingZeros();
	            String valorFormateado;

	            if (tipoCambio.scale() <= 0) {
	                valorFormateado = tipoCambio.toPlainString();
	            } else {
	                valorFormateado = tipoCambio.setScale(Math.min(4, tipoCambio.scale()), RoundingMode.DOWN).stripTrailingZeros().toPlainString();
	            }

	            crearCelda(row, 7, valorFormateado);
	        }
	        
	        
	        
	    }

}
