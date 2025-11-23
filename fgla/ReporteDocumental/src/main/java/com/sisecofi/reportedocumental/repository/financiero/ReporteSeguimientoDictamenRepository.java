package com.sisecofi.reportedocumental.repository.financiero;

import com.sisecofi.libreria.comunes.dto.dinamico.Agrupacion;
import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.reportedocumental.dto.financiero.ConsultaSeguimientoDictamenDTO;
import com.sisecofi.reportedocumental.repository.financiero.sql.ReporteSeguimientoDictamenSQL;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;


@Slf4j
@Repository
public class ReporteSeguimientoDictamenRepository extends RFUtil implements ReporteSeguimientoDictamenSQL {
    @PersistenceContext
    private EntityManager entityManager;

    public PageGeneric obtenerReporte(ConsultaSeguimientoDictamenDTO dto, boolean paginacion) {
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


    protected String getContadorSQL(ConsultaSeguimientoDictamenDTO dto) {
        return SELECT_COUNT +
                FROM +
                JOINS +
                WHERE +
                getCondicionesSQL(dto);
    }


    protected String getConsultaSQL(ConsultaSeguimientoDictamenDTO dto, boolean paginacion) {
        String sql = SELECT_ALL_FROM + SELECT + getCamposSQL(dto) +
                FROM +
                JOINS +
                WHERE +
                getCondicionesSQL(dto) +
                ORDER_BY +
                (paginacion ? PAGINACION : "");

        log.info("SQL GENERADO: {}", sql);

        return sql;
    }


    protected String getCamposSQL(ConsultaSeguimientoDictamenDTO dto) {
        StringBuilder campos = new StringBuilder(CAMPOS_BASE);

        if (dto.getListaIdEstatusDictamen() != null && !dto.getListaIdEstatusDictamen().isEmpty()) {
            // Mapeo de estatus a sus correspondientes campos
            Map<Integer, String> estatusCamposMap = Map.of(
                    1, CAMPOS_INICIAL,
                    2, CAMPOS_DICTAMINADO,
                    3, CAMPOS_PROFORMA,
                    4, CAMPOS_FACTURADO,
                    5, CAMPOS_SOLICITUD_PAGO,
                    6, CAMPOS_PAGADO,
                    7, CAMPOS_CANCELADO
            );

            for (Map.Entry<Integer, String> entry : estatusCamposMap.entrySet()) {
                if (dto.getListaIdEstatusDictamen().contains(0) ||
                        dto.getListaIdEstatusDictamen().contains(entry.getKey())) {
                    campos.append(entry.getValue());
                }
            }
        }

        return campos.toString();
    }


    protected String getCondicionesSQL(ConsultaSeguimientoDictamenDTO dto) {
        StringBuilder condiciones = new StringBuilder();

        if (dto.getIdContratoVigente() != null && dto.getIdContratoVigente() > 0) {
        if (dto.getIdContratoVigente() == 1) { // Contratos vigentes
            condiciones.append("\n\tand date(coalesce(scm.fecha_fin, svm.fecha_fin_vigencia_contrato)) >= date(:fechaFinVigenciaContrato)");
        } else if (dto.getIdContratoVigente() == 2) { // Contratos no vigentes
            condiciones.append("\n\tand date(coalesce(scm.fecha_fin, svm.fecha_fin_vigencia_contrato)) < date(:fechaFinVigenciaContrato)");
        }
    }

    //referencia a verficadores
        //  if (dto.getIdVerificadorContrato() != null && dto.getIdVerificadorContrato() > 0) {             
        //      condiciones.append("\n\tand verificador.id_referencia = :idVerificadorContrato");
        //  }

        if (dto.getNombreCortoProyecto() != null && !dto.getNombreCortoProyecto().isEmpty() && !dto.getNombreCortoProyecto().equalsIgnoreCase("Todos")) {
            condiciones.append("\n\tand sp.nombre_corto = :nombreCortoProyecto");
        }

        if (dto.getNombreCortoContrato() != null && !dto.getNombreCortoContrato().isEmpty()) {
            condiciones.append("\n\tand sc.nombre_corto = :nombreCortoContrato");

        }

        return condiciones.toString();
    }


    protected Map<String, Object> getParametrosSQL(ConsultaSeguimientoDictamenDTO dto) {
        Map<String, Object> params = new HashMap<>();

        if (dto.getIdContratoVigente() != null && (dto.getIdContratoVigente() == 1 || dto.getIdContratoVigente() == 2)) {
            params.put("fechaFinVigenciaContrato", new Date());
        }

        // if (dto.getIdVerificadorContrato() != null && dto.getIdVerificadorContrato() > 0) {
        //     params.put("idVerificadorContrato", dto.getIdVerificadorContrato());
        // }

        if (dto.getNombreCortoProyecto() != null && !dto.getNombreCortoProyecto().isEmpty() && !dto.getNombreCortoProyecto().equalsIgnoreCase("Todos")) {
            params.put("nombreCortoProyecto", dto.getNombreCortoProyecto());
        }

        if (dto.getNombreCortoContrato() != null && !dto.getNombreCortoContrato().isEmpty()) {
            params.put("nombreCortoContrato", dto.getNombreCortoContrato());
        }

        return params;
    }

    protected void setEtiquetas(ConsultaSeguimientoDictamenDTO dto, PageGeneric page) {
        page.getGrupoEtiquetas().add(new Agrupacion("", 4));
        page.getEtiquetas().addAll(Arrays.asList(ETIQUETAS));

        if (dto.getListaIdEstatusDictamen() != null && !dto.getListaIdEstatusDictamen().isEmpty()) {
            // Mapeo de estatus a sus correspondientes etiquetas y agrupaciones
            Map<Integer, String> estatusAgrupacionMap = Map.of(
                    1, "Inicial",
                    2, "Dictaminado",
                    3, "Proforma",
                    4, "Facturado",
                    5, "Solicitud de pago",
                    6, "Pagado",
                    7, "Cancelado"
            );

            for (Map.Entry<Integer, String> entry : estatusAgrupacionMap.entrySet()) {
                if (dto.getListaIdEstatusDictamen().contains(0) ||
                        dto.getListaIdEstatusDictamen().contains(entry.getKey())) {
                    page.getGrupoEtiquetas().add(new Agrupacion(entry.getValue(), 3));
                    page.getEtiquetas().addAll(Arrays.asList(ETIQUETAS_ESTATUS));
                }
            }
        }
    }


    protected void formatearMontosReporte(ConsultaSeguimientoDictamenDTO dto, PageGeneric page) {
        page.setContent(page.getContent().stream().map(item -> {
            int indice = 0;

            indice = incrementarIndice(indice, 4);

            if (dto.getListaIdEstatusDictamen() != null && !dto.getListaIdEstatusDictamen().isEmpty()) {
                indice = formatMontosPorEstatusDictamen(dto, item, 1, indice); // inicial
                indice = formatMontosPorEstatusDictamen(dto, item, 2, indice); // dictaminado
                indice = formatMontosPorEstatusDictamen(dto, item, 3, indice); // proforma
                indice = formatMontosPorEstatusDictamen(dto, item, 4, indice); // facturado
                indice = formatMontosPorEstatusDictamen(dto, item, 5, indice); // solicitud de pago
                indice = formatMontosPorEstatusDictamen(dto, item, 6, indice); // pagado
                formatMontosPorEstatusDictamen(dto, item, 7, indice); // cancelado
            }

            return item;
        }).toList());
    }

    private int formatMontosPorEstatusDictamen(ConsultaSeguimientoDictamenDTO dto, Object[] item, Integer idEstusDictamen, int indice) {
        if (dto.getListaIdEstatusDictamen().contains(0) || dto.getListaIdEstatusDictamen().contains(idEstusDictamen)) {
            item[indice] = getMontoConFormato(item[indice++]);
            item[indice] = getMontoConFormato(item[indice++]);
            item[indice] = getMontoConFormato(item[indice++]);
        }
        return indice;
    }
}
