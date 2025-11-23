package com.sisecofi.proveedores.util.consumer;

import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;
import com.sisecofi.proveedores.dto.ProveedoGeneralDto;

@Component
public class ReporteGeneralProveedoresConsumer extends BaseExcel implements Consumer<ProveedoGeneralDto> {

    @Override
    public void accept(ProveedoGeneralDto t) {
    	// FortifyIssueSuppression: Este parametro es nesesario
        Row row = sheet.createRow(numeroRenglon++);
        crearCelda(row, 0, t.getIdProveedor());
        crearCelda(row, 1, t.getNombreProveedor());
        crearCelda(row, 2, t.getNombreComercial());
        crearCelda(row, 3, t.getGiroDeLaEmpresa());
        crearCelda(row, 4, t.getRfc());
        crearCelda(row, 5, t.getRepresentanteLegal());
        crearCelda(row, 6, t.getTituloDeServicio());
        crearCelda(row, 7, t.getVigencia());
        crearCelda(row, 8, t.getVencimientoTitulo());
        crearCelda(row, 9, t.getCumpleDictamen());

        String estatus =(t.getEstatus() != null && t.getEstatus()) ? "Activo" : "Inactivo";
        crearCelda(row, 10, estatus);

    }

    public void agregarCabeceras(String cadenaTitulos) {
    	BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
    }

}
