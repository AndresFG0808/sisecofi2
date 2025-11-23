package com.sisecofi.libreria.comunes.util.consumer;

import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class MonthMapper {

    private static final Map<String, Month> monthMap = new HashMap<>();

    private MonthMapper () {
    	
    }
    
    static {
        monthMap.put("ENERO", Month.JANUARY);
        monthMap.put("FEBRERO", Month.FEBRUARY);
        monthMap.put("MARZO", Month.MARCH);
        monthMap.put("ABRIL", Month.APRIL);
        monthMap.put("MAYO", Month.MAY);
        monthMap.put("JUNIO", Month.JUNE);
        monthMap.put("JULIO", Month.JULY);
        monthMap.put("AGOSTO", Month.AUGUST);
        monthMap.put("SEPTIEMBRE", Month.SEPTEMBER);
        monthMap.put("OCTUBRE", Month.OCTOBER);
        monthMap.put("NOVIEMBRE", Month.NOVEMBER);
        monthMap.put("DICIEMBRE", Month.DECEMBER);
    }

    public static Month getMonth(String mes) {
        Month month = monthMap.get(mes.toUpperCase());
        if (month == null) {
            throw new IllegalArgumentException("Mes no válido: " + mes);
        }
        return month;
    }
}