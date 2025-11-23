package com.sisecofi.reportedocumental.util;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.BiConsumer;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.reportedocumental.dto.financiero.PaginadorDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
public class ReporteUtil {
	
	 private final EntityManager entityManager;

	    public ReporteUtil(EntityManager entityManager) {
	        this.entityManager = entityManager;
	    }

	    @SuppressWarnings("hiding")
		public <T extends PaginadorDTO> PageGeneric ejecutarConsultaReporte(
	            T dto,
	            String contadorSQL,
	            String consultaSQL,
	            Map<String, Object> parametrosSQL,
	            boolean paginacion,
	            BiConsumer<T, PageGeneric> postProcessor
	    ) {
	        PageGeneric page = new PageGeneric();
	        page.setEtiquetas(new ArrayList<>());
	        page.setGrupoEtiquetas(new ArrayList<>());

	        Query countQuery = entityManager.createNativeQuery(contadorSQL);
	        setParametrosSQL(parametrosSQL, countQuery);
	        Long total = (Long) countQuery.getSingleResult();

	        Query query = entityManager.createNativeQuery(consultaSQL);
	        setParametrosSQL(parametrosSQL, query);

	        if (paginacion) {
	            query.setParameter("registros", dto.getPageSize());
	            query.setParameter("pagina", dto.getPageNumber() * dto.getPageSize());
	        }

	        if ((long) dto.getPageNumber() * dto.getPageSize() >= total) {
	            page.setContent(new ArrayList<>());
	        } else {
	            page.setContent(query.getResultList());
	        }

	        setPaginador(page, dto, total);
	        
	        if (postProcessor != null) {
	            postProcessor.accept(dto, page);
	        }

	        return page;
	    }

	    private void setPaginador(PageGeneric page, PaginadorDTO dto, Long total) {
	        page.setSize(page.getContent().isEmpty() ? 1 : page.getContent().size());
	        page.setTotalPages(total.intValue() / dto.getPageSize() + ((total.intValue() % dto.getPageSize()) == 0 ? 0 : 1));
	        page.setTotalElements(total.intValue());
	        page.setFirst(dto.getPageNumber() == 0);
	        page.setNumber(dto.getPageNumber());
	        page.setNumberOfElements(page.getContent().size());
	        page.setLast(dto.getPageNumber() >= page.getTotalPages() - 1);
	    }

	    private void setParametrosSQL(Map<String, Object> params, Query query) {
	        for (Map.Entry<String, Object> entry : params.entrySet()) {
	            query.setParameter(entry.getKey(), entry.getValue());
	        }
	    }

}
