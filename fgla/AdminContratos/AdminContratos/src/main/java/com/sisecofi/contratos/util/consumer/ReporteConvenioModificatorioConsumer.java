package com.sisecofi.contratos.util.consumer;

import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class ReporteConvenioModificatorioConsumer extends BaseExcel implements Consumer<ConvenioModificatorioModel> {


	public void agregarCabeceras( String cadenaTitulos){
	       BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
	    }

    @Override
    public void accept(ConvenioModificatorioModel convenioModificatorioModel) {
        Row row = sheet.createRow(numeroRenglon++);
        crearCelda(row, 0 , convenioModificatorioModel.getNumeroConvenio());
        crearCelda(row,1,convenioModificatorioModel.getTipoConvenioFormateado());
        crearCelda(row,2,convenioModificatorioModel.getFechaFirma());
        crearCelda(row,3,convenioModificatorioModel.getFechaFin());
        crearCelda(row,4,convenioModificatorioModel.getMontoMaximoSinImpuestos());
    }


}
