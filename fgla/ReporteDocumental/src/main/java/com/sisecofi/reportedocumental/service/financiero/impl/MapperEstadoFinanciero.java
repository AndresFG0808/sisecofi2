package com.sisecofi.reportedocumental.service.financiero.impl;

import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.reportedocumental.service.dinamico.Mapper;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component("mapperEstadoFinanciero")
public class MapperEstadoFinanciero implements Mapper {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy");

    @Override
    public void map(Object obj, BaseExcel baseExcel, Row row, int contador, Object[] t) {
        if (obj != null) {
            if (obj instanceof LocalDateTime fecha) {
                baseExcel.crearCelda(row, contador, fecha.format(formatter));
            } else if (obj instanceof Boolean valor) {
                baseExcel.crearCelda(row, contador, Boolean.TRUE.equals(valor) ? "Activo" : "Inactivo");
            } else {
                String regex = "^(enero|febrero|marzo|abril|mayo|junio|julio|agosto|septiembre|octubre|noviembre|diciembre)/\\d{4}$";
                String fecha = String.valueOf(obj);

                if (fecha.toLowerCase().matches(regex)) {
                    Map<String, Integer> meses = new HashMap<>();
                    meses.put("enero", 1);
                    meses.put("febrero", 2);
                    meses.put("marzo", 3);
                    meses.put("abril", 4);
                    meses.put("mayo", 5);
                    meses.put("junio", 6);
                    meses.put("julio", 7);
                    meses.put("agosto", 8);
                    meses.put("septiembre", 9);
                    meses.put("octubre", 10);
                    meses.put("noviembre", 11);
                    meses.put("diciembre", 12);

                    String[] partes = fecha.toLowerCase().split("/");
                    String mesTexto = partes[0];
                    int anio = Integer.parseInt(partes[1]);

                    Integer mesNumero = meses.get(mesTexto);
                    if (mesNumero != null) {
                        obj = String.format("01/%02d/%d", mesNumero, anio);
                    }
                }

                baseExcel.crearCelda(row, contador, String.valueOf(obj));
            }
        }
    }

}
