package com.sisecofi.contratos.util.consumer;

import com.sisecofi.contratos.dto.ServicioContratoDto;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.Consumer;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReporteServicioContratoConsumer extends BaseExcel implements Consumer<ServicioContratoDto> {

	public void agregarCabeceras( String cadenaTitulos){
        BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
     }

    @Override
    public void accept(ServicioContratoDto servicioContratoDto) {
        Boolean aplicaDns = servicioContratoDto.getAplicaDns();
        List<String> aplicDn = new ArrayList<>();

        if(Boolean.TRUE.equals(aplicaDns)){
            aplicDn.add("si");
        }else {
            aplicDn.add("no");
        }

        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(Locale.US);

        String montoMaximo = formatoMoneda.format(servicioContratoDto.getMontoMaximo());
        String montoMinimo = formatoMoneda.format(servicioContratoDto.getMontoMinimo());


        Row row = sheet.createRow(numeroRenglon++);
        crearCelda(row, 0 , numeroRenglon - 1);
        crearCelda(row,1,servicioContratoDto.getGrupo());
        crearCelda(row,2,servicioContratoDto.getTipoConsumo());
        crearCelda(row,3,servicioContratoDto.getClaveProductosServicios());
        crearCelda(row, 4, servicioContratoDto.getConseptosServicio());
        crearCelda(row,5, servicioContratoDto.getTipoUnidad());
        crearCelda(row,6, "$"+servicioContratoDto.getPrecioUnitario());
        NumberFormat numberFormat = new DecimalFormat("#,##0.###");
        crearCelda(row, 7, numberFormat.format(servicioContratoDto.getCantidadServiciosMinima()));
        crearCelda(row, 8, numberFormat.format(servicioContratoDto.getCantidadServicios()));
        crearCelda(row,9,montoMinimo);
        crearCelda(row,10,montoMaximo);
        crearCelda(row,11, aplicDn.get(0));
    }


}
