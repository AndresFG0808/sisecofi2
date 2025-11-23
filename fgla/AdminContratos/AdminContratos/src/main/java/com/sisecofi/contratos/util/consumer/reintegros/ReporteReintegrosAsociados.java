package com.sisecofi.contratos.util.consumer.reintegros;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.sisecofi.contratos.dto.reintegros.ReintegrosConsultaDto;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;

@Component
public class ReporteReintegrosAsociados extends BaseExcel implements Consumer<ReintegrosConsultaDto> {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final String NUMERO = "$0.00";
	@Override
	public void accept(ReintegrosConsultaDto dto) {
		Row row = sheet.createRow(numeroRenglon++);
		DecimalFormat formatterMonto = new DecimalFormat("#,##0.00");
		crearCelda(row, 0, dto.getNombreCorto());
		crearCelda(row, 1, numeroRenglon - 1);
		crearCelda(row, 2, dto.getNombreTipoReintegro());
		crearCeldaConMonto(row, 3, dto.getImporte(), NUMERO, formatterMonto);
		crearCeldaConMonto(row, 4, dto.getInteres(), NUMERO, formatterMonto);
		crearCeldaConMonto(row, 5, dto.getTotal(), NUMERO, formatterMonto);

		String fechaFormato = dto.getFechaReintegro().format(formatter);
		crearCelda(row, 6, fechaFormato);

	}

	private void crearCeldaConMonto(Row row, int column, BigDecimal value, String defaultValue,
			DecimalFormat formatter) {
		if (value != null) {
			crearCelda(row, column, "$" + formatter.format(value));
		} else {
			crearCelda(row, column, defaultValue);
		}
	}

	public void agregarCabeceras(String cadenaTitulos) {
		BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
	}

}
