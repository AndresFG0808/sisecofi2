package com.sisecofi.proyectos.util.consumer;

import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import com.sisecofi.libreria.comunes.model.proyectos.HistoricoModel;

import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;

@Component
public class ReporteHistoricoConsumer extends BaseExcel implements Consumer<HistoricoModel> {

	public void agregarCabeceras(String cadenaTitulos) {
		BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
	}

	@Override
	public void accept(HistoricoModel lider) {
		// Falso positivo fortify, la variable si está en uso
		Row row = sheet.createRow(numeroRenglon++);
		crearCelda(row, 0, lider.getNombre());
		crearCelda(row, 1, lider.getPuesto());
		crearCelda(row, 2, lider.getCorreo());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		crearCelda(row, 3, lider.getFechaInicio().format(formatter));

		if (lider.getFechaFin() == null) {
			crearCelda(row, 4, ""); 
		} else {
			crearCelda(row, 4, lider.getFechaFin().format(formatter));
		}
		if (lider.isEstatus()) {
			crearCelda(row, 5, "Activo");
		} else {
			crearCelda(row, 5, "Inactivo");
		}
	}
}
