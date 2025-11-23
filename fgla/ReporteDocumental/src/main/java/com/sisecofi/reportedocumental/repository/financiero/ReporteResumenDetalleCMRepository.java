package com.sisecofi.reportedocumental.repository.financiero;

import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.reportedocumental.dto.financiero.ConsultaDetalleCMDTO;
import com.sisecofi.reportedocumental.repository.financiero.sql.ReporteResumenSQL;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ReporteResumenDetalleCMRepository extends RFUtil implements ReporteResumenSQL {
    @PersistenceContext
    private EntityManager entityManager;

    public PageGeneric obtenerReporte(ConsultaDetalleCMDTO dto, boolean paginacion) {
        PageGeneric page = new PageGeneric();
        initPageGeneric(page);
        setEtiquetas(page);

        Query countQuery = entityManager.createNativeQuery("select count(*) from sisecofi.sscft_convenio_modificatorio where id_contrato = :id_contrato");
        setParametrosSQL(getParametrosSQL(dto), countQuery);
        Long total = (Long) countQuery.getSingleResult();

        Query query = entityManager.createNativeQuery(getConsultaSQL(paginacion));
        setParametrosSQL(getParametrosSQL(dto), query);

        if (paginacion) {
            query.setParameter("registros", dto.getPageSize());
            query.setParameter("pagina", getOffset(dto));
        }

        if ((long) dto.getPageNumber() * dto.getPageSize() >= total) {
            page.setContent(new ArrayList<>());
        } else {
            page.setContent(query.getResultList());
        }

        setPaginador(page, dto, total);

        formatearMontosReporte(page);

        return page;
    }

    protected String getConsultaSQL(boolean paginacion) {
        return SQL_DETALLE_CM.concat(paginacion ? PAGINACION : "");
    }

    protected Map<String, Object> getParametrosSQL(ConsultaDetalleCMDTO dto) {
        Map<String, Object> params = new HashMap<>();
        params.put("id_contrato", dto.getIdContrato());
        return params;
    }

    protected void setEtiquetas(PageGeneric page) {
        page.getEtiquetas().addAll(Arrays.asList(ETIQUETAS_DETALLE_CM));
    }

    protected void formatearMontosReporte(PageGeneric page) {
        page.setContent(page.getContent().stream().map(item -> {
            item[5] = getMontoConFormato(item[5]);
            item[6] = getMontoConFormato(item[6]);
            item[7] = getMontoConFormato(item[7]);
            return item;
        }).toList());
    }
}
