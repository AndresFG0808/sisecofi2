package com.sisecofi.reportedocumental.repository.financiero;

import com.sisecofi.libreria.comunes.dto.dinamico.Agrupacion;
import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.reportedocumental.dto.financiero.ConsultaTipoDTO;
import com.sisecofi.reportedocumental.repository.financiero.sql.ReporteTipoSQL;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ReporteTipoRepository extends RFUtil implements ReporteTipoSQL {
    @PersistenceContext
    private EntityManager entityManager;

    private static final String FACTURAS = "Facturas"; // Facturas
    private static final String DEDUCCIONES = "Deducciones"; // Deducciones
    private static final String PENALIZACIONES = "Penalizaciones"; // Penalizaciones
    private static final String REINTEGROS = "Reintegros"; // Reintegros

    public PageGeneric obtenerReporte(ConsultaTipoDTO dto, boolean paginacion) {
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

    protected String getContadorSQL(ConsultaTipoDTO dto) {
        StringBuilder sql = new StringBuilder();
        sql.append(SELECT);
        sql.append("count(*)");
        sql.append(FROM);
        sql.append(JOINS_BASE);

        if (dto.getTipos().contains(FACTURAS) || dto.getTipos().contains(DEDUCCIONES) || dto.getTipos().contains(PENALIZACIONES)) {
            sql.append(JOINS_DICTAMENES);
        }

        if (dto.getTipos().contains(REINTEGROS)) {
            sql.append(JOINS_REINTEGROS);
        }

        sql.append(WHERE);
        sql.append(getCondicionesSQL(dto));

        return sql.toString();
    }

    protected String getConsultaSQL(ConsultaTipoDTO dto, boolean paginacion) {
        StringBuilder sql = new StringBuilder();
        sql.append(SELECT_ALL_FROM);
        sql.append(SELECT);
        sql.append(getCamposSQL(dto));
        sql.append(FROM);
        sql.append(JOINS_BASE);

        if (dto.getTipos().contains(FACTURAS) || dto.getTipos().contains(DEDUCCIONES) || dto.getTipos().contains(PENALIZACIONES)) {
            sql.append(JOINS_DICTAMENES);
        }

        if (dto.getTipos().contains(REINTEGROS)) {
            sql.append(JOINS_REINTEGROS);
        }

        sql.append(WHERE);
        sql.append(getCondicionesSQL(dto));
        sql.append(ORDER_BY_PERIODO_CONTROL);
        sql.append(paginacion ? PAGINACION : "");

        log.info("SQL GENERADO: {}", sql);

        return sql.toString();
    }

    protected String getCamposSQL(ConsultaTipoDTO dto) {
        StringBuilder campos = new StringBuilder(CAMPOS_DETALLE_GENERAL);

        if (dto.getTipos().contains(FACTURAS) || dto.getTipos().contains(DEDUCCIONES) || dto.getTipos().contains(PENALIZACIONES)) {
            campos.append(CAMPOS_DETALLE_GENERAL_OPCIONAL);

            setCamposFacturas(dto, campos);

            setCamposDeducciones(dto, campos);

            setCamposPenalizaciones(dto, campos);
        }

        if (dto.getTipos().contains(REINTEGROS)) {
            campos.append(CAMPOS_DETALLE_REINTEGROS);
        }

        return campos.toString();
    }

    private static void setCamposPenalizaciones(ConsultaTipoDTO dto, StringBuilder campos) {
        if (dto.getTipos().contains(PENALIZACIONES)) {
            campos.append(CAMPOS_DETALLE_PENALIZACIONES_SAT_01);

            if (dto.getIdConvenioColaboracion() == 1) {
                campos.append(CAMPOS_DETALLE_PENALIZACIONES_CC_01);
            }

            campos.append(CAMPOS_DETALLE_PENALIZACIONES_SAT_02);

            if (dto.getIdConvenioColaboracion() == 1) {
                campos.append(CAMPOS_DETALLE_PENALIZACIONES_CC_02);
            }

        }
    }

    private static void setCamposDeducciones(ConsultaTipoDTO dto, StringBuilder campos) {
        if (dto.getTipos().contains(DEDUCCIONES)) {
            campos.append(CAMPOS_DETALLE_DEDUCCIONES_SAT_01);

            if (dto.getIdConvenioColaboracion() == 1) {
                campos.append(CAMPOS_DETALLE_DEDUCCIONES_CC_01);
            }

            campos.append(CAMPOS_DETALLE_DEDUCCIONES_SAT_02);

            if (dto.getIdConvenioColaboracion() == 1) {
                campos.append(CAMPOS_DETALLE_DEDUCCIONES_CC_02);
            }
        }
    }

    private static void setCamposFacturas(ConsultaTipoDTO dto, StringBuilder campos) {
        if (dto.getTipos().contains(FACTURAS)) {
            campos.append(CAMPOS_DETALLE_FACTURAS);

            campos.append(CAMPOS_DETALLE_FACTURAS_SAT_01);

            if (dto.getIdConvenioColaboracion() == 1) {
                campos.append(CAMPOS_DETALLE_FACTURAS_CC_01);
            }

            campos.append(CAMPOS_DETALLE_FACTURAS_SAT_02);

            if (dto.getIdConvenioColaboracion() == 1) {
                campos.append(CAMPOS_DETALLE_FACTURAS_CC_02);
            }

            campos.append(CAMPOS_DETALLE_FACTURAS_SAT_03);

            if (dto.getIdConvenioColaboracion() == 1) {
                campos.append(CAMPOS_DETALLE_FACTURAS_CC_03);
            }

            campos.append(CAMPOS_DETALLE_FACTURAS_SAT_04);

            if (dto.getIdConvenioColaboracion() == 1) {
                campos.append(CAMPOS_DETALLE_FACTURAS_CC_04);
            }

            campos.append(CAMPOS_DETALLE_FACTURA_COMENTARIOS);
        }
    }

    protected String getCondicionesSQL(ConsultaTipoDTO dto) {
        StringBuilder condiciones = new StringBuilder();

        if (dto.getTipos().contains(FACTURAS) || dto.getTipos().contains(DEDUCCIONES) || dto.getTipos().contains(PENALIZACIONES)) {
            condiciones.append(CONDICION_DICTAMENES);
        }

        if (dto.getTipos().contains(REINTEGROS)) {
            condiciones.append(CONDICION_REINTEGROS);
        }

        if (dto.getNombreCortoProyecto() != null && !dto.getNombreCortoProyecto().isEmpty() && !dto.getNombreCortoProyecto().equalsIgnoreCase("Todos")) {
            condiciones.append("\n\tand sp.nombre_corto = :nombreCortoProyecto");

        }

        if (dto.getNombreCortoContrato() != null && !dto.getNombreCortoContrato().isEmpty()) {
            condiciones.append("\n\tand sc.nombre_corto = :nombreCortoContrato");

        }

		if (dto.getIdContratoVigente() != null && dto.getIdContratoVigente() > 0) {
			if (dto.getIdContratoVigente() == 1) { // Contratos vigentes
				condiciones.append("\n\tand date(coalesce(cm.fecha_fin_max, ")
						.append("\n\tsvm.fecha_fin_vigencia_contrato)) >= date(:fechaFinVigenciaContrato)");

			} else if (dto.getIdContratoVigente() == 2) { // Contratos no vigentes
				condiciones.append("\n\tand date(coalesce(cm.fecha_fin_max, ")
				.append("\n\tsvm.fecha_fin_vigencia_contrato)) < date(:fechaFinVigenciaContrato)");
			}
		}

        return condiciones.toString();
    }

    protected Map<String, Object> getParametrosSQL(ConsultaTipoDTO dto) {
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

    protected void setEtiquetas(ConsultaTipoDTO dto, PageGeneric page) {
        page.getGrupoEtiquetas().add(new Agrupacion("", 4));
        page.getEtiquetas().addAll(new ArrayList<>(Arrays.asList(ETIQUETAS_DETALLE_GENERAL)));

        if (dto.getTipos().contains(FACTURAS) || dto.getTipos().contains(DEDUCCIONES) || dto.getTipos().contains(PENALIZACIONES)) {
            page.getEtiquetas().addAll(new ArrayList<>(Arrays.asList(ETIQUETAS_DETALLE_OPCIONAL)));
            page.getGrupoEtiquetas().add(new Agrupacion("", 4));

            setEtiquetasFacturas(dto, page);

            setEtiquetasDeducciones(dto, page);

            setEtiquetasPenalizaciones(dto, page);
        }

        if (dto.getTipos().contains(REINTEGROS)) {
            page.getEtiquetas().addAll(new ArrayList<>(Arrays.asList(ETIQUETAS_DETALLE_REINTEGROS)));
            page.getGrupoEtiquetas().add(new Agrupacion("Detalle reintegros", 6));
        }

    }

    private static void setEtiquetasPenalizaciones(ConsultaTipoDTO dto, PageGeneric page) {
        if (dto.getTipos().contains(PENALIZACIONES)) {
            List<String> etiquetas = new ArrayList<>(Arrays.asList(ETIQUETAS_DETALLE_PENALIZACIONES));

            if (dto.getIdConvenioColaboracion() == 2) {
                etiquetas.removeIf(etiqueta -> etiqueta.endsWith("CC"));
                page.getGrupoEtiquetas().add(new Agrupacion("Detalle penalizaciones", 2));
            } else {
                page.getGrupoEtiquetas().add(new Agrupacion("Detalle penalizaciones", 4));
            }

            page.getEtiquetas().addAll(etiquetas);
        }
    }

    private static void setEtiquetasDeducciones(ConsultaTipoDTO dto, PageGeneric page) {
        if (dto.getTipos().contains(DEDUCCIONES)) {
            List<String> etiquetas = new ArrayList<>(Arrays.asList(ETIQUETAS_DETALLE_DEDUCCIONES));

            if (dto.getIdConvenioColaboracion() == 2) {
                etiquetas.removeIf(etiqueta -> etiqueta.endsWith("CC"));
                page.getGrupoEtiquetas().add(new Agrupacion("Detalle deducciones", 2));
            } else {
                page.getGrupoEtiquetas().add(new Agrupacion("Detalle deducciones", 4));
            }

            page.getEtiquetas().addAll(etiquetas);
        }
    }

    private static void setEtiquetasFacturas(ConsultaTipoDTO dto, PageGeneric page) {
        if (dto.getTipos().contains(FACTURAS)) {

            List<String> etiquetas = new ArrayList<>(Arrays.asList(ETIQUETAS_DETALLE_FACTURAS));

            if (dto.getIdConvenioColaboracion() == 2) {
                etiquetas.removeIf(etiqueta -> etiqueta.endsWith("CC"));
                page.getGrupoEtiquetas().add(new Agrupacion("Detalle facturas", 11));
            } else {
                page.getGrupoEtiquetas().add(new Agrupacion("Detalle facturas", 15));
            }

            page.getEtiquetas().addAll(etiquetas);
        }
    }

    protected void formatearMontosReporte(ConsultaTipoDTO dto, PageGeneric page) {
        page.setContent(page.getContent().stream().map(item -> {
            int indice = 0;

            indice = incrementarIndice(indice, 4);

            if (dto.getTipos().contains(FACTURAS) || dto.getTipos().contains(DEDUCCIONES) || dto.getTipos().contains(PENALIZACIONES)) {
                indice = incrementarIndice(indice, 4);
            }

            indice = formatMontosFacturas(dto, item, indice);

            indice = formatMontosDeducciones(dto, item, indice);

            indice = formatMontosPenalizaciones(dto, item, indice);

            formatMontosReintegros(dto, item, indice);

            return item;
        }).toList());
    }

    private void formatMontosReintegros(ConsultaTipoDTO dto, Object[] item, int indice) {
        if (dto.getTipos().contains(REINTEGROS)) {
            indice = incrementarIndice(indice, 1);
            item[indice] = getMontoConFormato(item[indice++]);
            item[indice] = getMontoConFormato(item[indice++]);
            item[indice] = getMontoConFormato(item[indice++]);
            item[indice] = getMontoConFormato(item[indice]);
        }
    }

    private int formatMontosPenalizaciones(ConsultaTipoDTO dto, Object[] item, int indice) {
        if (dto.getTipos().contains(PENALIZACIONES)) {
            item[indice] = getMontoConFormato(item[indice++]);

            if (dto.getIdConvenioColaboracion() == 1) {
                item[indice] = getMontoConFormato(item[indice++]);
            }

            item[indice] = getMontoConFormato(item[indice++]);

            if (dto.getIdConvenioColaboracion() == 1) {
                item[indice] = getMontoConFormato(item[indice++]);
            }

        }
        return indice;
    }

    private int formatMontosDeducciones(ConsultaTipoDTO dto, Object[] item, int indice) {
        if (dto.getTipos().contains(DEDUCCIONES)) {
            item[indice] = getMontoConFormato(item[indice++]);

            if (dto.getIdConvenioColaboracion() == 1) {
                item[indice] = getMontoConFormato(item[indice++]);
            }

            item[indice] = getMontoConFormato(item[indice++]);

            if (dto.getIdConvenioColaboracion() == 1) {
                item[indice] = getMontoConFormato(item[indice++]);
            }
        }
        return indice;
    }

    private int formatMontosFacturas(ConsultaTipoDTO dto, Object[] item, int indice) {
        if (dto.getTipos().contains(FACTURAS)) {
            indice = incrementarIndice(indice, 5);

            item[indice] = getMontoConFormato(item[indice++]);

            item[indice] = getMontoConFormato(item[indice++]);

            if (dto.getIdConvenioColaboracion() == 1) {
                item[indice] = getMontoConFormato(item[indice++]);
            }

            item[indice] = getMontoConFormato(item[indice++]);

            if (dto.getIdConvenioColaboracion() == 1) {
                item[indice] = getMontoConFormato(item[indice++]);
            }

            item[indice] = getMontoConFormato(item[indice++]);

            if (dto.getIdConvenioColaboracion() == 1) {
                item[indice] = getMontoConFormato(item[indice++]);
            }

            indice = incrementarIndice(indice, 1);

            if (dto.getIdConvenioColaboracion() == 1) {
                indice++;
            }

            indice = incrementarIndice(indice, 1);
        }
        return indice;
    }
}
