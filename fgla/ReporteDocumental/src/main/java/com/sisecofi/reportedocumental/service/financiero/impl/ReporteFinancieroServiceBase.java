package com.sisecofi.reportedocumental.service.financiero.impl;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ReporteFinancieroServiceBase {
    private static final String DATE_FORMAT = "dd/MM/yyyy";

    protected String getMovimiento(Map<String, Object> datos) {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, Object> entry : datos.entrySet()) {
            result.append(entry.getKey())
                    .append(": ")
                    .append(getValue(entry.getValue()))
                    .append("|");
        }

        if (!result.isEmpty()) {
            result.setLength(result.length() - 1);
        }

        return result.toString();
    }

    private String getValue(Object object) {
        if (object == null) {
            return StringUtils.EMPTY;
        } else if (object instanceof String str) {
            return str;
        } else if (object instanceof Number number) {
            return String.valueOf(number);
        } else if (object instanceof Boolean bool) {
            return String.valueOf(bool);
        } else if (object instanceof Date date) {
            return new SimpleDateFormat(DATE_FORMAT).format(date);
        } else if (object instanceof LocalDate localDate) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            return localDate.format(dateFormatter);
        } else if (object instanceof List<?> list) {
            return list.toString();
        } else {
            return object.toString();
        }
    }
}
