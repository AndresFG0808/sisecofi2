package com.sisecofi.admingeneral.util.consumers;

import java.util.List;
import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.sisecofi.libreria.comunes.model.plantilla.ArchivoPlantillaModel;
import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;


/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Component
public class CarpetaPlantillaConsumer extends BaseExcel implements Consumer<List<CarpetaPlantillaModel>> {
    
    public void agregarCabeceras(String cadenaTitulos) {
        BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
    }

    @Override
    public void accept(List<CarpetaPlantillaModel> carpetas) {
        for (CarpetaPlantillaModel carpeta : carpetas) {
            procesarCarpeta(carpeta, -2);
        }
    }

    private void procesarCarpeta(CarpetaPlantillaModel carpeta, int cont) {
        Row row = sheet.createRow(numeroRenglon++);

        cont+=2;
     
        crearCelda(row, cont, carpeta.getNombre());
        
        crearCelda(row, sheet.getRow(0).getLastCellNum() - 1, carpeta.getDescripcion());

        for (ArchivoPlantillaModel archivo : carpeta.getArchivos()) {
            procesarArchivo(archivo, cont+1);
        }

        for (CarpetaPlantillaModel subCarpeta : carpeta.getSubCarpetas()) {
            procesarCarpeta(subCarpeta, cont);  
        }
    }

    private void procesarArchivo(ArchivoPlantillaModel archivo, int nivel) {
        Row row = sheet.createRow(numeroRenglon++);

        crearCelda(row, nivel, archivo.getNombre());

        crearCelda(row, sheet.getRow(0).getLastCellNum() - 1, archivo.getDescripcion());
    }

}
