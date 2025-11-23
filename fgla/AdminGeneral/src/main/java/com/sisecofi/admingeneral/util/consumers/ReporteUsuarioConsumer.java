package com.sisecofi.admingeneral.util.consumers;

import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Component
public class ReporteUsuarioConsumer extends BaseExcel implements Consumer<Usuario> {

	public void agregarCabeceras(String cadenaTitulos) {
		BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
	}

	@Override
	public void accept(Usuario t) {
		Row row = sheet.createRow(numeroRenglon++);
		crearCelda(row, 0, t.getNombre());
		crearCelda(row, 1, t.getRfcCorto());
		crearCelda(row, 2, t.getAdministracion());
		crearCelda(row, 3, t.isEstatus() ? "Activo" : "Inactivo");
	}

}
