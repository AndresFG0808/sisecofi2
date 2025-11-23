package com.sisecofi.proyectos.util.consumer;

import com.sisecofi.libreria.comunes.model.catalogo.CatAliniacion;
import com.sisecofi.libreria.comunes.model.catalogo.CatMapaObjetivo;
import com.sisecofi.libreria.comunes.model.catalogo.CatPeriodo;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;
import com.sisecofi.proyectos.microservicio.CatalogoMicroservicio;
import com.sisecofi.proyectos.model.FichaAlineacion;
import lombok.RequiredArgsConstructor;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Component
public class ReporteAlineacionConsumer extends BaseExcel implements Consumer<FichaAlineacion> {
	
	private final CatalogoMicroservicio catalogoMicroservicio;


    public void agregarCabeceras( String cadenaTitulos){
       BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
    }

    @Override
    public void accept(FichaAlineacion alineacion) {
    	// Falso positivo fortify, la variable si está en uso
        Row row = sheet.createRow(numeroRenglon++);
        CatAliniacion alineacionCatalogo= catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
				CatalogosComunes.ALINIACION.getIdCatalogo(), alineacion.getIdAliniacion(), CatAliniacion.class);
        CatMapaObjetivo objetivo= catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
				CatalogosComunes.MAPA_OBJETIVO.getIdCatalogo(), alineacion.getIdObjetivo(), CatMapaObjetivo.class);
        CatPeriodo periodoCatalogo = catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
				CatalogosComunes.PERIDO.getIdCatalogo(), alineacion.getIdPeriodo(), CatPeriodo.class);
        crearCelda(row, 0, alineacionCatalogo.getNombre());
        crearCelda(row, 1, periodoCatalogo.getNombre());
        crearCelda(row, 2, objetivo.getObjetivo());
    }
}
