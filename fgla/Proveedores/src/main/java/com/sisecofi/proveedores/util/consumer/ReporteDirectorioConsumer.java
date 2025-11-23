package com.sisecofi.proveedores.util.consumer;

import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.sisecofi.libreria.comunes.model.proveedores.DirectorioProveedorModel;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;

@Component
public class ReporteDirectorioConsumer extends BaseExcel implements Consumer<DirectorioProveedorModel> {

    @Override
    public void accept(DirectorioProveedorModel d) {
    	// Este parametro es nesesario
        Row row = sheet.createRow(numeroRenglon++);
        crearCelda(row, 0, d.getOrdenDirectorio());
        crearCelda(row, 1, d.getNombreContacto());
        crearCelda(row, 2, d.getTelefonoOficina());
        crearCelda(row, 3, d.getTelefonoCelular());
        crearCelda(row, 4, d.getCorreoElectronico());

        String representanteLegal = (d.getRepresentanteLegal() != null && d.getRepresentanteLegal()) ? "Sí" : "No";
        crearCelda(row, 5, representanteLegal);

        crearCelda(row, 6, d.getComentarios());

    }

    public void agregarCabeceras(String cadenaTitulos) {
    	BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
    }

}
