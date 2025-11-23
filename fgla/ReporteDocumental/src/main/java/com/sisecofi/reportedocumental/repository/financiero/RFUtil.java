package com.sisecofi.reportedocumental.repository.financiero;

import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.reportedocumental.dto.financiero.PaginadorDTO;
import jakarta.persistence.Query;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class RFUtil {
    protected void initPageGeneric(PageGeneric page) {
        page.setEtiquetas(new ArrayList<>());
        page.setGrupoEtiquetas(new ArrayList<>());
    }

    protected void setPaginador(PageGeneric page, PaginadorDTO dto, Long total) {
        page.setSize(page.getContent().isEmpty() ? 1 : page.getContent().size());
        page.setTotalPages(total.intValue() / dto.getPageSize() + ((total.intValue() % dto.getPageSize()) == 0 ? 0 : 1));
        page.setTotalElements(total.intValue());
        page.setFirst(dto.getPageNumber() == 0);
        page.setNumber(dto.getPageNumber());
        page.setNumberOfElements(page.getContent().size());
        page.setLast(dto.getPageNumber() >= page.getTotalPages() - 1);
    }

    protected int getOffset(PaginadorDTO dto) {
        return dto.getPageNumber() * dto.getPageSize();
    }

    protected String getMontoConFormato(Object obj) {
        if (obj instanceof Number objC) {
            BigDecimal valor = BigDecimal.valueOf((objC).doubleValue()).setScale(2, RoundingMode.HALF_UP);
            NumberFormat formatter = new DecimalFormat("#,##0.00");
            return formatter.format(valor.doubleValue());
        } else {
            return "El objeto no es un número";
        }
    }

    protected void setParametrosSQL(Map<String, Object> params, Query query) {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            query.setParameter(key, value);
        }
    }

    protected int incrementarIndice(int indice, int cantidad) {
        return indice + cantidad;
    }

    protected Calendar getCaledario(LocalDate periodoInicio) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, periodoInicio.getYear());
        calendar.set(Calendar.MONTH, periodoInicio.getMonthValue() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return calendar;
    }
}
