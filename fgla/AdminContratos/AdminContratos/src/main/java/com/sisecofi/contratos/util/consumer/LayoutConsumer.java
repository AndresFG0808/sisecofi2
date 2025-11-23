package com.sisecofi.contratos.util.consumer;

import com.sisecofi.contratos.dto.CasoNegocioDto;
import com.sisecofi.libreria.comunes.model.contratos.ServicioContratoModel;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;

import java.math.BigDecimal;
import java.util.function.Consumer;

@Component
public class LayoutConsumer extends BaseExcel implements Consumer<CasoNegocioDto> {



    public void agregarCabeceras( String cadenaTitulos){
       BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
    }

    @Override
    public void accept(CasoNegocioDto dto) {
        if (dto.getVolumeria() == null) {
            procesarServiciosSinVolumeria(dto);
        } else {
            procesarServiciosConVolumeria(dto);
        }
    }

    private void procesarServiciosSinVolumeria(CasoNegocioDto dto) {
        for (ServicioContratoModel servicio : dto.getListaServicio()) {
            Row row = crearNuevaFila();
            crearCelda(row, 0, numeroRenglon - 1);
            crearCelda(row, 1, servicio.getConcepto());
        }
    }

    private void procesarServiciosConVolumeria(CasoNegocioDto dto) {
        int indice = determinarIndice(dto);

        for (int i = 0; i < indice; i++) {
            ServicioContratoModel servicio = dto.getListaServicio().get(i);
            Row row = crearNuevaFila();
            crearCelda(row, 0, numeroRenglon - 1);
            crearCelda(row, 1, servicio.getConcepto());

            if (i < dto.getVolumeria().length) {
                procesarVolumeria(dto.getVolumeria()[i], row);
            }
        }
    }

    private int determinarIndice(CasoNegocioDto dto) {
        if (dto.isExportar()) {
            return dto.getVolumeria().length;
        }
        return dto.getListaServicio().size();
    }

    private void procesarVolumeria(BigDecimal[] fila, Row row) {
        for (int j = 0; j < fila.length; j++) {
            BigDecimal valor = (fila[j] != null) ? fila[j] : BigDecimal.ZERO;
            crearCelda(row, j + 2, valor);
        }
    }

    private Row crearNuevaFila() {
        return sheet.createRow(numeroRenglon++);
    }

}
