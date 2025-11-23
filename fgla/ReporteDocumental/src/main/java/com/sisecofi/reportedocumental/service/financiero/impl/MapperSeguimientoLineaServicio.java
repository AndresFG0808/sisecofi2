package com.sisecofi.reportedocumental.service.financiero.impl;

import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.reportedocumental.service.dinamico.Mapper;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component("mapperSeguimientoLineaServicio")
public class MapperSeguimientoLineaServicio implements Mapper {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy");

    @Override
    public void map(Object obj, BaseExcel baseExcel, Row row, int contador, Object[] t) {
        if (obj != null) {
            if (obj instanceof LocalDateTime fecha) {
                baseExcel.crearCelda(row, contador, fecha.format(formatter));
            } else if (obj instanceof Boolean valor) {
                baseExcel.crearCelda(row, contador, Boolean.TRUE.equals(valor) ? "Activo" : "Inactivo");
            } else {
                baseExcel.crearCelda(row, contador, String.valueOf(obj));
            }
        }
    }

}
