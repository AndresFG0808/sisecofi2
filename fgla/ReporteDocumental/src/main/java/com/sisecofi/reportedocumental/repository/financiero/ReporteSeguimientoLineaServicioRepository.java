package com.sisecofi.reportedocumental.repository.financiero;

import com.sisecofi.libreria.comunes.dto.dinamico.Agrupacion;
import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.reportedocumental.dto.financiero.ConsultaSeguimientoLineaServicioDTO;
import com.sisecofi.reportedocumental.repository.financiero.sql.ReporteSeguimientoLineaServicioSQL;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ReporteSeguimientoLineaServicioRepository extends RFUtil implements ReporteSeguimientoLineaServicioSQL {
    @PersistenceContext
    private EntityManager entityManager;

    private static final String CAMPOS = "%campos%";

    private static final int TODOS = 1;
    private static final int PLANEADA = 2;
    private static final int ESTIMADA = 3;
    private static final int DICTAMINADA = 4;
    private static final int PAGADA = 5;

    public PageGeneric obtenerReporte(ConsultaSeguimientoLineaServicioDTO dto, boolean paginacion) {
        if (dto.getIdConvenioColaboracion() == null) {
            dto.setIdConvenioColaboracion(2);
        }

        if (dto.getListaIdServicioContrato() == null || dto.getListaIdServicioContrato().isEmpty()) {
            dto.setListaIdServicioContrato(new ArrayList<>());
            dto.getListaIdEstatusVolumetria().add(TODOS);
        }

        PageGeneric page = new PageGeneric();
        page.setEtiquetas(new ArrayList<>());
        page.setGrupoEtiquetas(new ArrayList<>());

        StringBuilder sqlBuilder = new StringBuilder(SELECT);
        sqlBuilder.append(CAMPOS);

        page.getGrupoEtiquetas().add(new Agrupacion("", 15));
        page.getEtiquetas().addAll(new ArrayList<>(Arrays.asList(ETIQUETAS_DETALLE_GENERAL)));

        StringBuilder camposPlaneada = new StringBuilder();
        StringBuilder camposEstimado = new StringBuilder();
        StringBuilder camposDictaminado = new StringBuilder();
        StringBuilder camposPagado = new StringBuilder();

        List<String> etiquetasPlaneada = new ArrayList<>();
        List<String> etiquetasEstimado = new ArrayList<>();
        List<String> etiquetasDictaminado = new ArrayList<>();
        List<String> etiquetasPagado = new ArrayList<>();

        Map<Integer, Integer> agrupacion = new HashMap<>();
        agrupacion.put(PLANEADA, 0);
        agrupacion.put(ESTIMADA, 0);
        agrupacion.put(DICTAMINADA, 0);
        agrupacion.put(PAGADA, 0);

        if (dto.getPeriodoInicio() != null && dto.getPeriodoFin() != null) {
            Calendar calInicio = getCaledario(dto.getPeriodoInicio());
            Calendar calFin = getCaledario(dto.getPeriodoFin());

            SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", new Locale("es", "MX"));
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

            do {
                var mesAnio = sdf.format(calInicio.getTime()).split(" ");
                String mes = mesAnio[0].toLowerCase();
                String anio = mesAnio[1];

                configurarPlaneada(dto, agrupacion, camposPlaneada, mes, anio, etiquetasPlaneada);

                configurarEstimada(dto, agrupacion, camposEstimado, mes, anio, etiquetasEstimado);

                configurarDictaminada(dto, agrupacion, camposDictaminado, mes, anio, etiquetasDictaminado);

                configurarPagada(dto, agrupacion, camposPagado, mes, anio, etiquetasPagado);

                calInicio.add(Calendar.MONTH, 1);
            } while (calInicio.before(calFin) || calInicio.equals(calFin));
        }

        setEtiquetas(etiquetasPlaneada, page, agrupacion, etiquetasEstimado, etiquetasDictaminado, etiquetasPagado);

        sqlBuilder.append(FROM);

        sqlBuilder.append(JOINS);

        sqlBuilder.append(WHERE);

        sqlBuilder.append(getCondicionesSQL(dto));

        Query countQuery = entityManager.createNativeQuery(sqlBuilder.toString().replace(CAMPOS, " distinct count(*) "));
        setParametrosSQL(getParametrosSQL(dto), countQuery);
        Long total = (Long) countQuery.getSingleResult();

        StringBuilder camposBuilder = new StringBuilder(CAMPOS_DETALLE_GENERAL)
                .append(camposPlaneada)
                .append(camposEstimado)
                .append(camposDictaminado)
                .append(camposPagado);

        String sql = sqlBuilder.toString().replace(CAMPOS, camposBuilder.toString()).concat(paginacion ? PAGINACION : "");
        log.info("SQL GENERADO: {}", sql);

        Query query = entityManager.createNativeQuery(sql);
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

    private static void setEtiquetas(List<String> etiquetasPlaneada, PageGeneric page, Map<Integer, Integer> agrupacion, List<String> etiquetasEstimado, List<String> etiquetasDictaminado, List<String> etiquetasPagado) {
        if (!etiquetasPlaneada.isEmpty()) {
            page.getGrupoEtiquetas().add(new Agrupacion("Planeada", agrupacion.get(PLANEADA)));
            page.getEtiquetas().addAll(etiquetasPlaneada);
        }

        if (!etiquetasEstimado.isEmpty()) {
            page.getGrupoEtiquetas().add(new Agrupacion("Estimada", agrupacion.get(ESTIMADA)));
            page.getEtiquetas().addAll(etiquetasEstimado);
        }

        if (!etiquetasDictaminado.isEmpty()) {
            page.getGrupoEtiquetas().add(new Agrupacion("Dictaminada", agrupacion.get(DICTAMINADA)));
            page.getEtiquetas().addAll(etiquetasDictaminado);
        }

        if (!etiquetasPagado.isEmpty()) {
            page.getGrupoEtiquetas().add(new Agrupacion("Pagada", agrupacion.get(PAGADA)));
            page.getEtiquetas().addAll(etiquetasPagado);
        }
    }

    private static void configurarPagada(ConsultaSeguimientoLineaServicioDTO dto, Map<Integer, Integer> agrupacion, StringBuilder camposPagado, String mes, String anio, List<String> etiquetasPagado) {
        if (dto.getListaIdEstatusVolumetria() != null && (dto.getListaIdEstatusVolumetria().contains(TODOS) || dto.getListaIdEstatusVolumetria().contains(PAGADA))) {
            if (dto.isVolumetria()) {
                agrupacion.put(PAGADA, agrupacion.get(PAGADA) + 1);
                camposPagado.append(CAMPOS_PAGADA_CANTIDAD_SERVICIOS_SAT.replace(REGEX_MES, mes).replace(REGEX_ANIO, anio));
                etiquetasPagado.add(ETIQUETA_CANTIDAD_SERVICIOS_SAT_CC.replace(REGEX_SAT_CC, "SAT").replace(REGEX_MES, mes).replace(REGEX_ANIO, anio));

                if (dto.getIdConvenioColaboracion() == 1) {
                    agrupacion.put(PAGADA, agrupacion.get(PAGADA) + 1);
                    camposPagado.append(CAMPOS_PAGADA_CANTIDAD_SERVICIOS_CC.replace(REGEX_MES, mes).replace(REGEX_ANIO, anio));
                    etiquetasPagado.add(ETIQUETA_CANTIDAD_SERVICIOS_SAT_CC.replace(REGEX_SAT_CC, "CC").replace(REGEX_MES, mes).replace(REGEX_ANIO, anio));
                }
            }

            if (dto.isMonto()) {
                agrupacion.put(PAGADA, agrupacion.get(PAGADA) + 1);
                camposPagado.append(CAMPOS_PAGADA_MONTO_SAT.replace(REGEX_MES, mes).replace(REGEX_ANIO, anio));
                etiquetasPagado.add(ETIQUETA_MONTO_SAT_CC.replace(REGEX_SAT_CC, "SAT").replace(REGEX_MES, mes).replace(REGEX_ANIO, anio));

                if (dto.getIdConvenioColaboracion() == 1) {
                    agrupacion.put(PAGADA, agrupacion.get(PAGADA) + 1);
                    camposPagado.append(CAMPOS_PAGADA_MONTO_CC.replace(REGEX_MES, mes).replace(REGEX_ANIO, anio));
                    etiquetasPagado.add(ETIQUETA_MONTO_SAT_CC.replace(REGEX_SAT_CC, "CC").replace(REGEX_MES, mes).replace(REGEX_ANIO, anio));
                }
            }
        }
    }

    private static void configurarDictaminada(ConsultaSeguimientoLineaServicioDTO dto, Map<Integer, Integer> agrupacion, StringBuilder camposDictaminado, String mes, String anio, List<String> etiquetasDictaminado) {
        if (dto.getListaIdEstatusVolumetria() != null && (dto.getListaIdEstatusVolumetria().contains(TODOS) || dto.getListaIdEstatusVolumetria().contains(DICTAMINADA))) {
            if (dto.isVolumetria()) {
                agrupacion.put(DICTAMINADA, agrupacion.get(DICTAMINADA) + 1);
                camposDictaminado.append(CAMPOS_DICTAMINADA_CANTIDAD_SERVICIOS_SAT.replace(REGEX_MES, mes).replace(REGEX_ANIO, anio));
                etiquetasDictaminado.add(ETIQUETA_CANTIDAD_SERVICIOS_SAT_CC.replace(REGEX_SAT_CC, "SAT").replace(REGEX_MES, mes).replace(REGEX_ANIO, anio));

                if (dto.getIdConvenioColaboracion() == 1) {
                    agrupacion.put(DICTAMINADA, agrupacion.get(DICTAMINADA) + 1);
                    camposDictaminado.append(CAMPOS_DICTAMINADA_CANTIDAD_SERVICIOS_CC.replace(REGEX_MES, mes).replace(REGEX_ANIO, anio));
                    etiquetasDictaminado.add(ETIQUETA_CANTIDAD_SERVICIOS_SAT_CC.replace(REGEX_SAT_CC, "CC").replace(REGEX_MES, mes).replace(REGEX_ANIO, anio));
                }
            }

            if (dto.isMonto()) {
                agrupacion.put(DICTAMINADA, agrupacion.get(DICTAMINADA) + 1);
                camposDictaminado.append(CAMPOS_DICTAMINADA_MONTO_SAT.replace(REGEX_MES, mes).replace(REGEX_ANIO, anio));
                etiquetasDictaminado.add(ETIQUETA_MONTO_SAT_CC.replace(REGEX_SAT_CC, "SAT").replace(REGEX_MES, mes).replace(REGEX_ANIO, anio));

                if (dto.getIdConvenioColaboracion() == 1) {
                    agrupacion.put(DICTAMINADA, agrupacion.get(DICTAMINADA) + 1);
                    camposDictaminado.append(CAMPOS_DICTAMINADA_MONTO_CC.replace(REGEX_MES, mes).replace(REGEX_ANIO, anio));
                    etiquetasDictaminado.add(ETIQUETA_MONTO_SAT_CC.replace(REGEX_SAT_CC, "CC").replace(REGEX_MES, mes).replace(REGEX_ANIO, anio));
                }
            }
        }
    }

    private static void configurarEstimada(ConsultaSeguimientoLineaServicioDTO dto, Map<Integer, Integer> agrupacion, StringBuilder camposEstimado, String mes, String anio, List<String> etiquetasEstimado) {
        if (dto.getListaIdEstatusVolumetria() != null && (dto.getListaIdEstatusVolumetria().contains(TODOS) || dto.getListaIdEstatusVolumetria().contains(ESTIMADA))) {
            if (dto.isVolumetria()) {
                agrupacion.put(ESTIMADA, agrupacion.get(ESTIMADA) + 1);
                camposEstimado.append(CAMPOS_ESTIMADO_CANTIDAD_SERVICIOS.replace(REGEX_MES, mes).replace(REGEX_ANIO, anio));
                etiquetasEstimado.add(ETIQUETA_CANTIDAD_SERVICIOS.replace(REGEX_MES, mes).replace(REGEX_ANIO, anio));
            }

            if (dto.isMonto()) {
                agrupacion.put(ESTIMADA, agrupacion.get(ESTIMADA) + 1);
                camposEstimado.append(CAMPOS_ESTIMADO_MONTO.replace(REGEX_MES, mes).replace(REGEX_ANIO, anio));
                etiquetasEstimado.add(ETIQUETA_MONTO.replace(REGEX_MES, mes).replace(REGEX_ANIO, anio));
            }
        }
    }

    private static void configurarPlaneada(ConsultaSeguimientoLineaServicioDTO dto, Map<Integer, Integer> agrupacion, StringBuilder camposPlaneada, String mes, String anio, List<String> etiquetasPlaneada) {
        if (dto.getListaIdEstatusVolumetria() != null && (dto.getListaIdEstatusVolumetria().contains(TODOS) || dto.getListaIdEstatusVolumetria().contains(PLANEADA))) {
            if (dto.isVolumetria()) {
                agrupacion.put(PLANEADA, agrupacion.get(PLANEADA) + 1);
                camposPlaneada.append(CAMPOS_PLANEADO_CANTIDAD_SERVICIOS.replace(REGEX_MES, mes).replace(REGEX_ANIO, anio).replace(REGEX_VALOR_PERIODO, mes.substring(0, 3).concat("-").concat(anio)));
                etiquetasPlaneada.add(ETIQUETA_CANTIDAD_SERVICIOS.replace(REGEX_MES, mes).replace(REGEX_ANIO, anio));
            }

            if (dto.isMonto()) {
                agrupacion.put(PLANEADA, agrupacion.get(PLANEADA) + 1);
                camposPlaneada.append(CAMPOS_PLANEADA_MONTO.replace(REGEX_MES, mes).replace(REGEX_ANIO, anio).replace(REGEX_VALOR_PERIODO, mes.substring(0, 3).concat("-").concat(anio)));
                etiquetasPlaneada.add(ETIQUETA_MONTO.replace(REGEX_MES, mes).replace(REGEX_ANIO, anio));
            }
        }
    }

    protected String getCondicionesSQL(ConsultaSeguimientoLineaServicioDTO dto) {
        StringBuilder condiciones = new StringBuilder();

        if (dto.getNombreCortoProyecto() != null && !dto.getNombreCortoProyecto().isEmpty() && !dto.getNombreCortoProyecto().equalsIgnoreCase("Todos")) {
            condiciones.append("\n\tand sp.nombre_corto = :nombreCortoProyecto");
        }

        if (dto.getNombreCortoContrato() != null && !dto.getNombreCortoContrato().isEmpty()) {
            condiciones.append("\n\tand sc.nombre_corto = :nombreCortoContrato");
        }

        if (dto.getIdContratoVigente() != null && dto.getIdContratoVigente() > 0) {
            if (dto.getIdContratoVigente() == 1) { // Contratos vigentes
                condiciones.append("\n\tand date(svm.fecha_fin_vigencia_contrato) >= date(:fechaFinVigenciaContrato)");
            } else if (dto.getIdContratoVigente() == 2) { // Contratos no vigentes
                condiciones.append("\n\tand date(svm.fecha_fin_vigencia_contrato) < date(:fechaFinVigenciaContrato)");
            }
        }

        if (dto.getListaIdServicioContrato() != null && !dto.getListaIdServicioContrato().isEmpty()) {
            condiciones.append("\n\tand ssc.id_servicio_contrato in (:listaIdServicioContrato)");
        }

        return condiciones.toString();
    }

    protected Map<String, Object> getParametrosSQL(ConsultaSeguimientoLineaServicioDTO dto) {
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

        if (dto.getListaIdServicioContrato() != null && !dto.getListaIdServicioContrato().isEmpty()) {
            params.put("listaIdServicioContrato", dto.getListaIdServicioContrato());
        }

        return params;
    }

    protected void formatearMontosReporte(ConsultaSeguimientoLineaServicioDTO dto, PageGeneric page) {
        page.setContent(page.getContent().stream().map(item -> {
            int indice = 0;

            indice = incrementarIndice(indice, 9);

            item[indice] = getMontoConFormato(item[indice++]);

            indice = incrementarIndice(indice, 5);

            if (dto.getPeriodoInicio() != null && dto.getPeriodoFin() != null) {
                indice = formatPlaneadaEstimada(dto, item, indice, getCaledario(dto.getPeriodoInicio()), getCaledario(dto.getPeriodoFin()), 2);
                indice = formatPlaneadaEstimada(dto, item, indice, getCaledario(dto.getPeriodoInicio()), getCaledario(dto.getPeriodoFin()), 3);

                indice = formatDictaaminadaPagada(dto, item, indice, getCaledario(dto.getPeriodoInicio()), getCaledario(dto.getPeriodoFin()), 4);
                formatDictaaminadaPagada(dto, item, indice, getCaledario(dto.getPeriodoInicio()), getCaledario(dto.getPeriodoFin()), 5);
            }

            return item;
        }).toList());
    }

    @SuppressWarnings("java:S3776")
    private int formatDictaaminadaPagada(ConsultaSeguimientoLineaServicioDTO dto, Object[] item, int indice, Calendar fechaInicio, Calendar fechaFin, int idEstusVolumetria) {
        do {
            if (dto.getListaIdEstatusVolumetria().contains(1) || dto.getListaIdEstatusVolumetria().contains(idEstusVolumetria)) {
                if (dto.isVolumetria()) {
                    indice++;

                    if (dto.getIdConvenioColaboracion() == 1) {
                        indice++;
                    }
                }

                if (dto.isMonto()) {
                    item[indice] = getMontoConFormato(item[indice++]);

                    if (dto.getIdConvenioColaboracion() == 1) {
                        item[indice] = getMontoConFormato(item[indice++]);
                    }
                }
            }

            fechaInicio.add(Calendar.MONTH, 1);
        } while (fechaInicio.before(fechaFin) || fechaInicio.equals(fechaFin));
        return indice;
    }

    private int formatPlaneadaEstimada(ConsultaSeguimientoLineaServicioDTO dto, Object[] item, int indice, Calendar fechaInicio, Calendar fechaFin, int idEstusVolumetria) {
        do {
            if (dto.getListaIdEstatusVolumetria().contains(1) || dto.getListaIdEstatusVolumetria().contains(idEstusVolumetria)) {
                if (dto.isVolumetria()) {
                    indice++;
                }

                if (dto.isMonto()) {
                    item[indice] = getMontoConFormato(item[indice++]);
                }
            }

            fechaInicio.add(Calendar.MONTH, 1);
        } while (fechaInicio.before(fechaFin) || fechaInicio.equals(fechaFin));
        return indice;
    }
}
