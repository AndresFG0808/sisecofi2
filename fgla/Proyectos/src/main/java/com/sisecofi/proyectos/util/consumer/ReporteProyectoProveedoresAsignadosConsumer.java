package com.sisecofi.proyectos.util.consumer;

import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;
import com.sisecofi.proyectos.model.ProyectoProveedorModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ReporteProyectoProveedoresAsignadosConsumer extends BaseExcel implements Consumer<ProyectoProveedorModel> {


	@Override
	public void accept(ProyectoProveedorModel d) {
		// Falso positivo fortify, la variable si está en uso
		Row row = sheet.createRow(numeroRenglon++);
	    crearCelda(row, 0, numeroRenglon - 1);

	    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	    crearCeldaConValor(row, 1, d.getProveedorModel() != null ? d.getProveedorModel().getNombreProveedor() : "");
	    crearCeldaConValor(row, 2, d.getCatInvestigacionMercado() != null ? d.getCatInvestigacionMercado().getNombre() : "");
	    crearCeldaConFecha(row, 3, d.getFechaIm(), outputFormatter);
	    crearCeldaConValor(row, 4, d.getCatRespuestaIm() != null ? d.getCatRespuestaIm().getNombre() : "");
	    crearCeldaConFecha(row, 5, d.getFechaRespuestaIm(), outputFormatter);
	    crearCeldaConValor(row, 6, d.getCatJuntaAclaracion() != null ? d.getCatJuntaAclaracion().getNombre() : "");
	    crearCeldaConFecha(row, 7, d.getFechaJa(), outputFormatter);
	    crearCeldaConValor(row, 8, d.getCatLicitacionPublica() != null ? d.getCatLicitacionPublica().getNombre() : "");
	    crearCeldaConFecha(row, 9, d.getFechaLp(), outputFormatter);
	    crearCeldaConValor(row, 10, d.getComentario() != null ? d.getComentario() : "");
	}

	private void crearCeldaConValor(Row row, int index, String value) {
	    crearCelda(row, index, value);
	}

	private void crearCeldaConFecha(Row row, int index, LocalDate date, DateTimeFormatter formatter) {
	    if (date != null) {
	        crearCelda(row, index, date.format(formatter));
	    } else {
	        crearCelda(row, index, "");
	    }
	}

    public void agregarCabeceras(String cadenaTitulos) {
        BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
    }
}
