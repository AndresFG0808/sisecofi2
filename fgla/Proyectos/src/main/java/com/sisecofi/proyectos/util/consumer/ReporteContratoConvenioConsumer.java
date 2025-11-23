package com.sisecofi.proyectos.util.consumer;

import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;
import com.sisecofi.proyectos.dto.ResponseComiteInfoReporte;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.Consumer;

@Component
public class ReporteContratoConvenioConsumer extends BaseExcel implements Consumer<ResponseComiteInfoReporte> {

	public void agregarCabeceras(String cadenaTitulos) {
        BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
    }

    @Override
    public void accept(ResponseComiteInfoReporte responseComiteInfo) {

        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(Locale.US);

        BigDecimal montoVal = responseComiteInfo.getInformacionComite().getMonto();
        BigDecimal montoAuth = responseComiteInfo.getInformacionComite().getMontoAutorizado();

        String monto = formatoMoneda.format( montoVal != null ? montoVal: BigDecimal.ZERO);
        String montoAutorizado = formatoMoneda.format(montoAuth != null ? montoAuth:BigDecimal.ZERO);

     // Falso positivo fortify, la variable si está en uso
        Row row = sheet.createRow(numeroRenglon++);
        crearCelda(row, 0, responseComiteInfo.getContratoConvenio());
        crearCelda(row, 1, responseComiteInfo.getContrato());
        crearCelda(row, 2, responseComiteInfo.getInformacionComite().getFechaSesion());
        crearCelda(row, 3, responseComiteInfo.getComite().getNombre());
        crearCelda(row, 4, responseComiteInfo.getAfectacion());
        crearCelda(row, 5, responseComiteInfo.getInformacionComite().getAcuerdo());
        crearCelda(row, 6, responseComiteInfo.getInformacionComite().getVigencia());
        crearCelda(row, 7, responseComiteInfo.getSesionNumero());
        crearCelda(row, 8, responseComiteInfo.getSesionClasificacion());        
        crearCelda(row, 9, montoAutorizado);
        crearCelda(row, 10, responseComiteInfo.getTipoMoneda());
        crearCelda(row, 11, responseComiteInfo.getInformacionComite().getTipoCambio());
        crearCelda(row, 12, monto);
        crearCelda(row, 13, responseComiteInfo.getInformacionComite().getComentarios());
    }
}
