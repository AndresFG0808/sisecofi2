package com.sisecofi.reportedocumental.repository.financiero;

import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.reportedocumental.dto.financiero.ConsultaResumenDTO;
import com.sisecofi.reportedocumental.repository.financiero.sql.ReporteResumenSQL;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;


@Slf4j
@Repository
public class ReporteResumenRepository extends RFUtil implements ReporteResumenSQL {
    @PersistenceContext
    private EntityManager entityManager;

    public PageGeneric obtenerReporte(ConsultaResumenDTO dto, boolean paginacion) {
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

    protected String getContadorSQL(ConsultaResumenDTO dto) {
        return "select count(*) " +
                FROM +
                JOINS +
                WHERE +
                getCondicionesSQL(dto);
    }

    protected String getConsultaSQL(ConsultaResumenDTO dto, boolean paginacion) {
        String sql = SELECT + getCamposSQL(dto) +
                FROM +
                JOINS +
                WHERE +
                getCondicionesSQL(dto) +
                ORDER_BY +
                (paginacion ? PAGINACION : "");

        log.info("SQL GENERADO: {}", sql);

        return sql;
    }

    protected String getCondicionesSQL(ConsultaResumenDTO dto) {
        StringBuilder condiciones = new StringBuilder();

        if (dto.getIdDominiosTecnologicos() != null) {
            condiciones.append("and t_dgp.id_dominios_tecnologicos = :idDominiosTecnologicos ");
        }

        if (dto.getIdEstatusProyecto() != null) {
            condiciones.append("and t_proyecto.id_estatus_proyecto = :idEstatusProyecto ");
        }

        if (dto.getNombreCortoProyecto() != null && !dto.getNombreCortoProyecto().isEmpty() && !dto.getNombreCortoProyecto().equalsIgnoreCase("Todos")) {
            condiciones.append("and t_proyecto.nombre_corto = :nombreCortoProyecto ");
        }

        if (dto.getNombreCortoContrato() != null && !dto.getNombreCortoContrato().isEmpty()) {
            condiciones.append("and t_contrato.nombre_corto = :nombreCortoContrato ");
        }

        // 1 (Sí), 2 (No), 3 (Todos)
        if (dto.getIdContratoVigente() != null) {
            if (dto.getIdContratoVigente() == 1) { // Contratos vigentes
                condiciones.append(
                        "and date(coalesce(scm.fecha_fin, svm.fecha_fin_vigencia_contrato)) >= date(:fechaFinVigenciaContrato) ");
            } else if (dto.getIdContratoVigente() == 2) { // Contratos no vigentes
                condiciones.append(
                        "and date(coalesce(scm.fecha_fin, svm.fecha_fin_vigencia_contrato)) < date(:fechaFinVigenciaContrato) ");
            }
        }





        return condiciones.toString();
    }

    protected Map<String, Object> getParametrosSQL(ConsultaResumenDTO dto) {
        Map<String, Object> params = new HashMap<>();

        if (dto.getIdDominiosTecnologicos() != null) {
            params.put("idDominiosTecnologicos", dto.getIdDominiosTecnologicos());
        }

        if (dto.getIdEstatusProyecto() != null) {
            params.put("idEstatusProyecto", dto.getIdEstatusProyecto());
        }

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

    protected String getCamposSQL(ConsultaResumenDTO dto) {
        StringBuilder campos = new StringBuilder(CAMAPOS_BASE);

        if (dto.isIncluirDatosGenerales()) {
            campos.append(CAMPOS_DATOS_GENERALES);
        }

        if (dto.isIncluirDetalleFinanciero()) {
            setSQLDevengado(dto, campos);
            setSQLPagadoAntesDeducciones(dto, campos);
            setSQLDeducciones(dto, campos);
            setSQLIva(dto, campos);
            setSQLIeps(dto, campos);
            setSQLOtrosImpuestos(dto, campos);
            setSQLPenalizaciones(dto, campos);
            setSQLReintegros(dto, campos);
            setSQLPagado(dto, campos);
        }

        return campos.toString();
    }

    private static void setSQLPagado(ConsultaResumenDTO dto, StringBuilder campos) {
        campos.append(PAGADO_SAT);

        if (dto.getIdConvenioColaboracion() == 1) {
            campos.append(PAGADO_CC);
        }
    }

    private static void setSQLReintegros(ConsultaResumenDTO dto, StringBuilder campos) {
        campos.append(REINTEGRO_SAT);

        if (dto.getIdConvenioColaboracion() == 1) {
            campos.append(REINTEGRO_CC);
        }
    }

    private static void setSQLPenalizaciones(ConsultaResumenDTO dto, StringBuilder campos) {
        campos.append(PENALIZACIONES_SAT);

        if (dto.getIdConvenioColaboracion() == 1) {
            campos.append(PENALIZACIONES_CC);
        }
    }

    private static void setSQLOtrosImpuestos(ConsultaResumenDTO dto, StringBuilder campos) {
        campos.append(OTROS_IMPUESTOS_SAT);

        if (dto.getIdConvenioColaboracion() == 1) {
            campos.append(OTROS_IMPUESTOS_CC);
        }
    }

    private static void setSQLIeps(ConsultaResumenDTO dto, StringBuilder campos) {
        campos.append(IEPS_SAT);

        if (dto.getIdConvenioColaboracion() == 1) {
            campos.append(IEPS_CC);
        }
    }

    private static void setSQLIva(ConsultaResumenDTO dto, StringBuilder campos) {
        campos.append(IVA_SAT);

        if (dto.getIdConvenioColaboracion() == 1) {
            campos.append(IVA_CC);
        }
    }

    private static void setSQLDeducciones(ConsultaResumenDTO dto, StringBuilder campos) {
        campos.append(DEDUCCIONES_SAT);

        if (dto.getIdConvenioColaboracion() == 1) {
            campos.append(DEDUCCIONES_CC);
        }
    }

    private static void setSQLPagadoAntesDeducciones(ConsultaResumenDTO dto, StringBuilder campos) {
        campos.append(PAGADO_ANTES_DEDUCCIONES_SAT);

        if (dto.getIdConvenioColaboracion() == 1) {
            campos.append(PAGADO_ANTES_DEDUCCIONES_CC);
        }
    }

    private static void setSQLDevengado(ConsultaResumenDTO dto, StringBuilder campos) {
        campos.append(DEVENGADO_ANTES_DEDUCCIONES_SAT);

        if (dto.getIdConvenioColaboracion() == 1) {
            campos.append(DEVENGADO_ANTES_DEDUCCIONES_CC);
        }
    }

    protected void setEtiquetas(ConsultaResumenDTO dto, PageGeneric page) {
        page.getEtiquetas().addAll(new ArrayList<>(Arrays.asList(ETIQUETAS_BASE)));

        if (dto.isIncluirDatosGenerales()) {
            page.getEtiquetas().addAll(new ArrayList<>(Arrays.asList(ETIQUETAS_DATOS_GENERALES)));
        }

        if (dto.isIncluirDetalleFinanciero()) {
            page.getEtiquetas().addAll(new ArrayList<>(Arrays.asList(ETIQUETAS_DETALLE_FINANCIERO)));

            if (dto.getIdConvenioColaboracion() == 2) {
                page.getEtiquetas().removeIf(etiqueta -> etiqueta.endsWith("CC"));
            }
        }
    }

    protected void formatearMontosReporte(ConsultaResumenDTO dto, PageGeneric page) {
        page.setContent(page.getContent().stream().map(item -> {
            int ultimoIndice = 0;

            ultimoIndice = incrementarIndice(ultimoIndice, 2);

            if (dto.isIncluirDatosGenerales()) {
                ultimoIndice = incrementarIndice(ultimoIndice, 14);

                item[ultimoIndice] = getMontoConFormato(item[ultimoIndice++]);
                item[ultimoIndice] = getMontoConFormato(item[ultimoIndice++]);
                item[ultimoIndice] = getMontoConFormato(item[ultimoIndice++]);
                item[ultimoIndice] = getMontoConFormato(item[ultimoIndice++]);
                item[ultimoIndice] = getMontoConFormato(item[ultimoIndice++]);
                item[ultimoIndice] = getMontoConFormato(item[ultimoIndice++]);

                ultimoIndice = incrementarIndice(ultimoIndice, 1);

                item[ultimoIndice] = getMontoConFormato(item[ultimoIndice++]);
                item[ultimoIndice] = getMontoConFormato(item[ultimoIndice++]);
                item[ultimoIndice] = getMontoConFormato(item[ultimoIndice++]);
                item[ultimoIndice] = getMontoConFormato(item[ultimoIndice++]);

                ultimoIndice = incrementarIndice(ultimoIndice, 6);
            }

            if (dto.isIncluirDetalleFinanciero()) {
                ultimoIndice = formatDetalleFinanciero(dto, item, ultimoIndice); // DevengadoAntesDeducciones
                ultimoIndice = formatDetalleFinanciero(dto, item, ultimoIndice); // PagadoAntesDeducciones
                ultimoIndice = formatDetalleFinanciero(dto, item, ultimoIndice); // Deducciones
                ultimoIndice = formatDetalleFinanciero(dto, item, ultimoIndice); // Iva
                ultimoIndice = formatDetalleFinanciero(dto, item, ultimoIndice); // Ieps
                ultimoIndice = formatDetalleFinanciero(dto, item, ultimoIndice); // OtrosImpuestos
                ultimoIndice = formatDetalleFinanciero(dto, item, ultimoIndice); // Penalizaciones
                ultimoIndice = formatDetalleFinanciero(dto, item, ultimoIndice); // Reintegros
                formatDetalleFinanciero(dto, item, ultimoIndice); // Pagado

            }

            return item;
        }).toList());
    }

    private int formatDetalleFinanciero(ConsultaResumenDTO dto, Object[] item, int ultimoIndice) {
        item[ultimoIndice] = getMontoConFormato(item[ultimoIndice++]);

        if (dto.getIdConvenioColaboracion() == 1) {
            item[ultimoIndice] = getMontoConFormato(item[ultimoIndice++]);
        }
        return ultimoIndice;
    }


}
