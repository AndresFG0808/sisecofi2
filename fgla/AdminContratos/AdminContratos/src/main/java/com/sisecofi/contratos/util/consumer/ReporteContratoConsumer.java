package com.sisecofi.contratos.util.consumer;

import com.sisecofi.contratos.dto.ContratoDtoLigero;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

@Component
public class ReporteContratoConsumer extends BaseExcel implements Consumer<ContratoDtoLigero> {

    private final NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(Locale.US);
    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void agregarCabeceras(String cadenaTitulos) {
        BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
    }

    @Override
    public void accept(ContratoDtoLigero contratoDto) {
        Row row = sheet.createRow(numeroRenglon++);

        crearCelda(row, 0, contratoDto.getIdContrato());
        crearCelda(row, 1, contratoDto.getNombreContrato());
        crearCelda(row, 2, contratoDto.getNombreProyecto());
        crearCelda(row, 3, contratoDto.getNumeroContrato());
        crearCelda(row, 4, contratoDto.getProveedores());
        crearCelda(row, 5, contratoDto.getTipoProcedimiento());
        crearCelda(row, 6, formatearFecha(contratoDto.getFechaInicio()));
        crearCelda(row, 7, formatearFecha(contratoDto.getFechaTermino()));
        crearCelda(row, 8, formatearFecha(contratoDto.getFechaUltimoCm()));
        crearCelda(row, 9, formatearMonto(contratoDto.getMontoMaximo()));
        crearCelda(row, 10, formatearMonto(contratoDto.getMontoMaximoCm()));
        crearCelda(row, 11, formatearMonto(contratoDto.getMontoPesos()));
        crearCelda(row, 12, contratoDto.getAdministracionCentral());
        crearCelda(row, 13, contratoDto.getAdministradorContrato());
    }

    private String formatearMonto(BigDecimal monto) {
        return monto != null ? formatoMoneda.format(monto) : "";
    }

    private String formatearFecha(LocalDateTime fecha) {
        return fecha != null ? fecha.format(formatoFecha) : "";
    }
}

