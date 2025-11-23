package com.sisecofi.reportedocumental.repository.financiero;

import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.reportedocumental.dto.financiero.ConsultaEstimadoPagadoDTO;
import com.sisecofi.reportedocumental.repository.financiero.sql.ReporteEstimadoPagadoSQL;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ReporteEstimadoPagadoRepository extends RFUtil implements ReporteEstimadoPagadoSQL {
    @PersistenceContext
    private EntityManager entityManager;

    public PageGeneric obtenerReporte(ConsultaEstimadoPagadoDTO dto, boolean paginacion) {
        if (dto.getIdConvenioColaboracion() == null) {
            dto.setIdConvenioColaboracion(2);
        }

        PageGeneric page = new PageGeneric();
        initPageGeneric(page);
        setEtiquetas(dto, page);

        Query countQuery = entityManager.createNativeQuery(getContadorSQL(dto));
        setParametrosSQL(getParametrosSQL(dto), countQuery);
        Long total = (Long) countQuery.getSingleResult();

        Query query = entityManager.createNativeQuery(getConsultaSQL(dto, paginacion));
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

        formatearMontosReporte(dto, page);

        return page;
    }

    protected String getContadorSQL(ConsultaEstimadoPagadoDTO dto) {
        StringBuilder sql = new StringBuilder();
        sql.append("with dictaminado as (\n");
        sql.append(SELECT);
        sql.append(CAMPOS_BASE);
        sql.append(getCamposDictamen(dto));
        sql.append(FROM);
        sql.append(JOINS_BASE);
        sql.append(JOINS_DICTAMEN);
        sql.append(WHERE);
        sql.append(getCondicionesSQL(dto));
        sql.append(CONDICION_DICTAMENES);
        sql.append("\n),\n");

        sql.append("estimado as (\n");

        sql.append(SELECT);
        sql.append(CAMPOS_BASE);
        sql.append(CAMPO_ESTIMADO);
        sql.append(FROM);
        sql.append(JOINS_BASE);
        sql.append(JOINS_ESTIMACION);
        sql.append(WHERE);
        sql.append(getCondicionesSQL(dto));
        sql.append(CONDICION_ESTIMACION);
        sql.append(")\n");
        sql.append(FULL_JOIN_CONTADOR);

        log.info("SQL GENERADO: {}", sql);

        return sql.toString();
    }

    protected String getConsultaSQL(ConsultaEstimadoPagadoDTO dto, boolean paginacion) {
        StringBuilder sql = new StringBuilder();
        sql.append("with dictaminado as (\n");
        sql.append(SELECT);
        sql.append(CAMPOS_BASE);
        sql.append(getCamposDictamen(dto));
        sql.append(FROM);
        sql.append(JOINS_BASE);
        sql.append(JOINS_DICTAMEN);
        sql.append(WHERE);
        sql.append(getCondicionesSQL(dto));
        sql.append(CONDICION_DICTAMENES);
        sql.append("\n),\n");

        sql.append("estimado as (\n");

        sql.append(SELECT);
        sql.append(CAMPOS_BASE);
        sql.append(CAMPO_ESTIMADO);
        sql.append(FROM);
        sql.append(JOINS_BASE);
        sql.append(JOINS_ESTIMACION);
        sql.append(WHERE);
        sql.append(getCondicionesSQL(dto));
        sql.append(CONDICION_ESTIMACION);
        sql.append(")\n");
        sql.append(SELECT_FULL_JOIN);
        sql.append(CAMPO_FULL_JOIN_ESTIMADO);
        sql.append(getCamposDictamenFullJoin(dto));
        sql.append(FROM_FULL_JOIN);

        if (paginacion) {
            sql.append(PAGINACION);
        }

        log.info("SQL GENERADO: {}", sql);

        return sql.toString();
    }

    protected String getCondicionesSQL(ConsultaEstimadoPagadoDTO dto) {
        StringBuilder sql = new StringBuilder();

        if (dto.getNombreCortoProyecto() != null && !dto.getNombreCortoProyecto().isEmpty() && !dto.getNombreCortoProyecto().equalsIgnoreCase("Todos")) {
            sql.append("\n\tand sp.nombre_corto = :nombreCortoProyecto");
        }

        if (dto.getNombreCortoContrato() != null && !dto.getNombreCortoContrato().isEmpty()) {
            sql.append("\n\tand sc.nombre_corto = :nombreCortoContrato");
        }

		if (dto.getIdContratoVigente() != null && dto.getIdContratoVigente() > 0) {
			if (dto.getIdContratoVigente() == 1) { // Contratos vigentes
				sql.append("\n\tand date(coalesce(cm.fecha_fin_max, ")
						.append("\n\tsvm.fecha_fin_vigencia_contrato)) >= date(:fechaFinVigenciaContrato)");

			} else if (dto.getIdContratoVigente() == 2) { // Contratos no vigentes
				sql.append("\n\tand date(coalesce(cm.fecha_fin_max, ")
				.append("\n\tsvm.fecha_fin_vigencia_contrato)) < date(:fechaFinVigenciaContrato)");
			}
		}

        return sql.toString();
    }

    protected Map<String, Object> getParametrosSQL(ConsultaEstimadoPagadoDTO dto) {
        Map<String, Object> params = new HashMap<>();

        if (dto.getNombreCortoProyecto() != null && !dto.getNombreCortoProyecto().isEmpty() && !dto.getNombreCortoProyecto().equalsIgnoreCase("Todos")) {
            params.put("nombreCortoProyecto", dto.getNombreCortoProyecto());
        }

        if (dto.getNombreCortoContrato() != null && !dto.getNombreCortoContrato().isEmpty()) {

            params.put("nombreCortoContrato", dto.getNombreCortoContrato());
        }

        if (dto.getIdContratoVigente() != null && (dto.getIdContratoVigente() == 1 || dto.getIdContratoVigente() == 2)) {
            params.put("fechaFinVigenciaContrato", new Date());
        }

        return params;
    }

    protected void setEtiquetas(ConsultaEstimadoPagadoDTO dto, PageGeneric page) {
        page.getEtiquetas().addAll(new ArrayList<>(Arrays.asList(ETIQUETAS)));

        if (dto.getIdConvenioColaboracion() == 2) {
            page.getEtiquetas().removeIf(etiqueta -> etiqueta.endsWith("CC"));
        }
    }

    protected String getCamposDictamenFullJoin(ConsultaEstimadoPagadoDTO dto) {
        StringBuilder camposFullJoin = new StringBuilder();

        camposFullJoin.append(CAMPO_FULL_JOIN_DICTAMAINADO_SAT);
        if (dto.getIdConvenioColaboracion() == 1) {
            camposFullJoin.append(CAMPO_FULL_JOIN_DICTAMAINADO_CC);
        }

        camposFullJoin.append(CAMPO_FULL_JOIN_DEDUCCIONES_SAT);
        if (dto.getIdConvenioColaboracion() == 1) {
            camposFullJoin.append(CAMPO_FULL_JOIN_DEDUCCIONES_CC);
        }

        camposFullJoin.append(CAMPO_FULL_JOIN_NOTA_CREDITO_SAT);
        if (dto.getIdConvenioColaboracion() == 1) {
            camposFullJoin.append(CAMPO_FULL_JOIN_NOTA_CREDITO_CC);
        }

        camposFullJoin.append(CAMPO_FULL_JOIN_SUBTOTAL_SAT);
        if (dto.getIdConvenioColaboracion() == 1) {
            camposFullJoin.append(CAMPO_FULL_JOIN_SUBTOTAL_CC);
        }

        camposFullJoin.append(CAMPO_FULL_JOIN_IVA_SAT);
        if (dto.getIdConvenioColaboracion() == 1) {
            camposFullJoin.append(CAMPO_FULL_JOIN_IVA_CC);
        }

        camposFullJoin.append(CAMPO_FULL_JOIN_IEPS_SAT);
        if (dto.getIdConvenioColaboracion() == 1) {
            camposFullJoin.append(CAMPO_FULL_JOIN_IEPS_CC);
        }

        camposFullJoin.append(CAMPO_FULL_JOIN_OTROS_IMP_SAT);
        if (dto.getIdConvenioColaboracion() == 1) {
            camposFullJoin.append(CAMPO_FULL_JOIN_OTROS_IMP_CC);
        }

        camposFullJoin.append(CAMPO_FULL_JOIN_PAGADO_SAT);
        if (dto.getIdConvenioColaboracion() == 1) {
            camposFullJoin.append(CAMPO_FULL_JOIN_PAGADO_CC);
        }

        return camposFullJoin.toString();
    }

    protected String getCamposDictamen(ConsultaEstimadoPagadoDTO dto) {
        StringBuilder campos = new StringBuilder();

        campos.append(CAMPO_DICTAMINADO_SAT);
        if (dto.getIdConvenioColaboracion() == 1) {
            campos.append(CAMPO_DICTAMINADO_CC);
        }

        campos.append(CAMPO_DEDUCCIONES_SAT);
        if (dto.getIdConvenioColaboracion() == 1) {
            campos.append(CAMPO_DEDUCCIONES_CC);
        }

        campos.append(CAMPO_NOTA_CREDITO_SAT);
        if (dto.getIdConvenioColaboracion() == 1) {
            campos.append(CAMPO_NOTA_CREDITO_CC);
        }

        campos.append(CAMPO_SUB_TOTAL_SAT);
        if (dto.getIdConvenioColaboracion() == 1) {
            campos.append(CAMPO_SUB_TOTAL_CC);
        }

        campos.append(CAMPO_IVA_SAT);
        if (dto.getIdConvenioColaboracion() == 1) {
            campos.append(CAMPO_IVA_CC);
        }

        campos.append(CAMPO_IEPS_SAT);
        if (dto.getIdConvenioColaboracion() == 1) {
            campos.append(CAMPO_IEPS_CC);
        }

        campos.append(CAMPO_OTROS_IMPUESTOS_SAT);
        if (dto.getIdConvenioColaboracion() == 1) {
            campos.append(CAMPO_OTROS_IMPUESTOS_CC);
        }

        campos.append(CAMPO_TOTAL_PAGADO_SAT);
        if (dto.getIdConvenioColaboracion() == 1) {
            campos.append(CAMPO_TOTAL_PAGADO_CC);
        }

        return campos.toString();
    }

    protected void formatearMontosReporte(ConsultaEstimadoPagadoDTO dto, PageGeneric page) {
        page.setContent(page.getContent().stream().map(item -> {
            int ultimoIndice = 0;

            ultimoIndice = incrementarIndice(ultimoIndice, 6);

            item[ultimoIndice] = getMontoConFormato(item[ultimoIndice++]); // Estimado

            ultimoIndice = formatMonto(dto, item, ultimoIndice); // Dictaminado
            ultimoIndice = formatMonto(dto, item, ultimoIndice); // Deduccion
            ultimoIndice = formatMonto(dto, item, ultimoIndice); // NotaCredito
            ultimoIndice = formatMonto(dto, item, ultimoIndice); // Subtotal
            ultimoIndice = formatMonto(dto, item, ultimoIndice); // Iva
            ultimoIndice = formatMonto(dto, item, ultimoIndice); // Ieps
            ultimoIndice = formatMonto(dto, item, ultimoIndice); // OtrosImpuestos
            formatMonto(dto, item, ultimoIndice); // TotalPagado

            return item;
        }).toList());
    }

    private int formatMonto(ConsultaEstimadoPagadoDTO dto, Object[] item, int ultimoIndice) {
        item[ultimoIndice] = getMontoConFormato(item[ultimoIndice++]);
        if (dto.getIdConvenioColaboracion() == 1) {
            item[ultimoIndice] = getMontoConFormato(item[ultimoIndice++]);
        }
        return ultimoIndice;
    }

}
