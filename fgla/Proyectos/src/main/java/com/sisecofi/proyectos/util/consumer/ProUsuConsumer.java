package com.sisecofi.proyectos.util.consumer;

import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusProyecto;
import com.sisecofi.libreria.comunes.model.proyectos.UsuarioProyectoModel;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.proyectos.microservicio.CatalogoMicroservicio;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Component
@RequiredArgsConstructor
public class ProUsuConsumer extends BaseExcel implements Consumer<UsuarioProyectoModel> {

	private final CatalogoMicroservicio catalogoMicroservicio;

	public void agregarCabeceras(String cadenaTitulos) {
		BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
	}

	@Override
	public void accept(UsuarioProyectoModel t) {
		// Falso positivo fortify, la variable si está en uso
		Row row = sheet.createRow(numeroRenglon++);
		CatEstatusProyecto estatus = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
				CatalogosComunes.ESTATUS_PROYECTO.getIdCatalogo(), t.getProyectoModel().getIdEstatusProyecto(),
				new CatEstatusProyecto());
		crearCelda(row, 0, estatus.getNombre());
		crearCelda(row, 1, t.getProyectoModel().getNombreCorto());
		crearCelda(row, 2, t.getProyectoModel().getNombreProyecto());
		crearCelda(row, 3, t.getProyectoModel().getIdFormateado());
		crearCelda(row, 4, t.getUsuario().getNombre());
	}

}
