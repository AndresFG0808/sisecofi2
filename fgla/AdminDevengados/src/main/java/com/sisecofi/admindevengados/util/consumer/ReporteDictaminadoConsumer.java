package com.sisecofi.admindevengados.util.consumer;

import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.sisecofi.admindevengados.dto.DictaminadoDto;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;

@Component
public class ReporteDictaminadoConsumer extends BaseExcel implements Consumer<DictaminadoDto> {

	public void agregarCabeceras(String cadenaTitulos) {
		BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
	}

	@Override
	public void accept(DictaminadoDto dictaminadoDto) {
		Row row = sheet.createRow(numeroRenglon++);
		crearCelda(row, 0, dictaminadoDto.getIdDictamen());
		if (Boolean.TRUE.equals(dictaminadoDto.getChecked())) {
			crearCelda(row, 1, "Si");
		} else {
			crearCelda(row, 1, "No");
		}
		crearCelda(row, 2, dictaminadoDto.getIdDictaminado());
		crearCelda(row, 3, dictaminadoDto.getConseptosServico());
		crearCelda(row, 4, dictaminadoDto.getUnidadMedida());
		crearCelda(row, 5, dictaminadoDto.getTipoConsumo());
		crearCelda(row, 6, dictaminadoDto.getPrecioUnitario());
		crearCelda(row, 7, dictaminadoDto.getCantidadServiciosVigente());
		crearCelda(row, 8, dictaminadoDto.getMontoMaximoServicio());
		crearCelda(row, 9, dictaminadoDto.getCantidadServiciosSat());
		crearCelda(row, 10, dictaminadoDto.getCantidadServiciosCc());
		crearCelda(row, 11, dictaminadoDto.getCantidadTotalServicios());
		crearCelda(row, 12, dictaminadoDto.getMontoDictaminado());
		crearCelda(row, 13, dictaminadoDto.getCantidadServicioDictaminadoAcumulado());
		crearCelda(row, 14, dictaminadoDto.getMontonDictaminadoAcumulado());
		crearCelda(row, 15, dictaminadoDto.getPorcentajeServiciosDictaminadosAcumulados());
		crearCelda(row, 16, dictaminadoDto.getPorcentajeMontoDictaminadosAcumulados());
	}

}
