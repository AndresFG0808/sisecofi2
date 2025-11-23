package com.sisecofi.reportedocumental.repository.financiero.sql;

@SuppressWarnings("all")
public interface ReporteSeguimientoLineaServicioSQL {
    String[] ETIQUETAS_DETALLE_GENERAL = new String[]{
            "Nombre corto del proyecto",
            "Nombre corto del contrato",
            "Número del contrato",
            "Estatus de volumetría",
            "Número consecutivo del concepto de servicio",
            "Grupo de servicio",
            "Concepto de servicio",
            "Tipo de consumo",
            "Tipo de unidad",
            "Precio unitario",
            "Aplica IVA",
            "Aplica IEPS",
            "Cantidad de servicios mínima",
            "Cantidad de servicios máxima",
            "Cantidad de servicios máxima último CM"
    };

    String ETIQUETA_CANTIDAD_SERVICIOS = "Cantidad de servicios de %mes% %anio%";

    String ETIQUETA_MONTO = "Monto de %mes% %anio%";

    String ETIQUETA_CANTIDAD_SERVICIOS_SAT_CC = "Cantidad de servicios %sat_cc% de %mes% %anio%";

    String ETIQUETA_MONTO_SAT_CC = "Monto %sat_cc% de %mes% %anio%";

    String REGEX_MES = "%mes%";

    String REGEX_ANIO = "%anio%";

    String REGEX_SAT_CC = "%sat_cc%";

    String REGEX_VALOR_PERIODO = "%valor_periodo%";

    String SELECT = "select ";

    String FROM = "\nfrom\n";

    String JOINS = "\tsisecofi.sscft_proyecto sp\n" +
            "left join sisecofi.sscft_contrato sc on\n" +
            "\tsc.id_proyecto = sp.id_proyecto\n" +
            "left join sisecofi.sscft_datos_generales_contrato sdgc on\n" +
            "\tsdgc.id_contrato = sc.id_contrato\n" +
            "left join sisecofi.sscft_vigencia_montos svm on\n" +
            "\tsvm.id_contrato = sc.id_contrato\n" +
            "left join sisecofi.sscfc_iva si on\n" +
            "\tsi.id_iva = svm.id_iva \n" +
            "left join sisecofi.sscft_servicio_contrato ssc on\n" +
            "\tssc.id_contrato = sc.id_contrato\n" +
            "join sisecofi.sscfc_tipo_unidad stu on\n" +
            "\tstu.id_tipo_unidad = ssc.id_tipo_unidad \n" +
            "left join sisecofi.sscft_grupo_servicio_contrato sgsc on\n" +
            "\tsgsc.id_grupo_servicio = ssc.id_grupo_servicio\n" +
            "join sisecofi.sscfc_tipo_consumo stc on\n" +
            "\tstc.id_tipo_consumo = sgsc.id_tipo_consumo";

    String WHERE = "\nwhere \n\t1 = 1 ";

    String PAGINACION = "\n\toffset :pagina rows fetch first :registros rows only";

    String CAMPOS_DETALLE_GENERAL = "  \n" +
            "\tsp.nombre_corto nombre_corto_proyecto,\n" +
            "\tsc.nombre_corto nombre_corto_contrato,\n" +
            "\tsdgc.numero_contrato numero_contrato,\n" +
            "\t(\n" +
            "\t\tcase \n" +
            "\t\t\twhen exists (\n" +
            "\t\t\t\tselect 1 \n" +
            "\t\t\t\tfrom sisecofi.sscft_servicios_dictaminados ssd \n" +
            "\t\t\t\tleft join sisecofi.sscft_dictamen sd_01 on ssd.id_dictamen = sd_01.id_dictamen\n" +
            "\t\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed_01 on sd_01.id_estatus_dictamen = sed_01.id_estatus_dictamen\n" +
            "\t\t\t\twhere ssd.id_servicio_contrato = ssc.id_servicio_contrato \n" +
            "\t\t\t\t\tand ssd.cantidad_total_servicios > 0\n" +
            "\t\t\t\t\tand ssd.seleccionado = true and lower(sed_01.nombre) not in ('inicial','cancelado')\n" +
            "\t\t\t) then \n" +
            "\t\t\t\t'D'\n" +
            "\t\t\telse \n" +
            "\t\t\t\tcase \n" +
            "\t\t\t\t\twhen exists (\n" +
            "\t\t\t\t\t\tselect 1\n" +
            "\t\t\t\t\t\tfrom sisecofi.sscft_servicio_estimado sse\n" +
            "\t\t\t\t\t\tjoin sisecofi.sscft_estimacion se ON se.id_estimacion = sse.id_estimacion\n" +
            "\t\t\t\t\t\tjoin sisecofi.sscfc_estatus_estimacion see ON see.id_estatus_estimacion = se.id_estatus_estimacion\n" +
            "\t\t\t\t\t\twhere LOWER(see.nombre) = 'estimado' \n" +
            "\t\t\t\t\t\t\tand sse.id_servicio_contrato = ssc.id_servicio_contrato \n" +
            "\t\t\t\t\t\t\tand sse.cantidad_servicio_estimados > 0\n" +
            "\t\t\t\t\t) then \n" +
            "\t\t\t\t\t\t'E'\n" +
            "\t\t\t\t\telse \n" +
            "\t\t\t\t\t\t''\n" +
            "\t\t\t\tend\n" +
            "\t\tend\n" +
            "\t) as estatus_volumetria,\n" +
            "\tssc.orden as numero_consecutivo_concepto_servicio,\n" +
            "\tsgsc.grupo grupo_servicio,\n" +
            "\tssc.concepto concepto_servicio,\n" +
            "\tstc.nombre tipo_consumo,\n" +
            "\tstu.nombre tipo_unidad,\n" +
            "\tssc.precio_unitario precio_unitario,\n" +
            "\t(case when lower(si.porcentaje) = 'no aplica' then 'No' else 'Sí' end) aplica_iva,\n" +
            "\t(case when ssc.ieps then 'Sí' else 'No' end) aplica_ieps,\n" +
            "\tssc.cantidad_minima cantidad_servicio_minima,\n" +
            "\tssc.cantidad_maxima cantidad_servicio_maxima,\n" +
            "\tcoalesce (\n" +
            "\t\t(\n" +
            "\t\t\tselect\n" +
            "\t\t\t\tssc2.numero_total_servicios \n" +
            "\t\t\tfrom sisecofi.sscft_servicio_convenio ssc2 \n" +
            "\t\t\tleft join sisecofi.sscft_servicio_contrato ssc3 on ssc2.id_servicio_contrato = ssc3.id_servicio_contrato\n" +
            "\t\t\twhere\n" +
            "\t\t\t\tssc2.id_convenio_modificatorio = (\n" +
            "\t\t\t\t\tselect\n" +
            "\t\t\t\t\t\tmax(scm.id_convenio_modificatorio)\n" +
            "\t\t\t\t\tfrom\n" +
            "\t\t\t\t\t\tsisecofi.sscft_convenio_modificatorio scm \n" +
            "\t\t\t\t\twhere\n" +
            "\t\t\t\t\t\tscm.id_contrato = sc.id_contrato\n" +
            "\t\t\t\t) and ssc3.id_servicio_contrato = ssc.id_servicio_contrato\n" +
            "\t\t)\n" +
            "\t, 0) cantidad_servicio_maxima_ultimo_cm";

    String CAMPOS_PLANEADO_CANTIDAD_SERVICIOS = ",\n" +
            "\tcoalesce (\n" +
            "\t\tcase \n" +
            "\t\t\twhen (select count(ssc2.id_servicio_convenio) from sisecofi.sscft_servicio_convenio ssc2 where ssc2.id_servicio_contrato = ssc.id_servicio_contrato) > 0 then \n" +
            "\t\t\t\t(\n" +
            "\t\t\t\t\tselect sum(v.valor_volumetria) as suma_volumetria\n" +
            "\t\t\t\t\tfrom sisecofi.sscft_caso_negocio_servicio_convenio scnsc\n" +
            "\t\t\t\t\tleft join sisecofi.sscft_servicio_convenio ssc2 on ssc2.id_servicio_convenio = scnsc.id_servicio_convenio,\n" +
            "\t\t\t\t\tlateral unnest(scnsc.periodos) with ordinality as p(valor_periodo, indice),\n" +
            "\t\t\t\t\tlateral unnest(scnsc.volumetria) with ordinality as v(valor_volumetria, indice)\n" +
            "\t\t\t\t\twhere lower(p.valor_periodo) = '%valor_periodo%'\n" +
            "\t\t\t\t\tand p.indice = v.indice and ssc2.id_servicio_convenio = (select max(ssc2.id_servicio_convenio) from sisecofi.sscft_servicio_convenio ssc2 where ssc2.id_servicio_contrato = ssc.id_servicio_contrato)\n" +
            "\t\t\t\t)\n" +
            "\t\t\telse (\n" +
            "\t\t\t\tselect sum(v.valor_volumetria) as suma_volumetria\n" +
            "\t\t\t\tfrom sisecofi.sscft_caso_negocio_servicio_contrato scnsc,\n" +
            "\t\t\t\tlateral unnest(scnsc.periodos) with ordinality as p(valor_periodo, indice),\n" +
            "\t\t\t\tlateral unnest(scnsc.volumetria) with ordinality as v(valor_volumetria, indice)\n" +
            "\t\t\t\twhere lower(p.valor_periodo) = '%valor_periodo%'\n" +
            "\t\t\t\tand p.indice = v.indice and scnsc.id_servicio_contrato = ssc.id_servicio_contrato\n" +
            "\t\t\t)\n" +
            "\t\tend\n" +
            "\t, 0) planeado_cantidad_servicios_%mes%_%anio%";

    String CAMPOS_PLANEADA_MONTO = ",\n" +
            "\tcoalesce (\n" +
            "\t\tcase \n" +
            "\t\t\twhen (select count(ssc2.id_servicio_convenio) from sisecofi.sscft_servicio_convenio ssc2 where ssc2.id_servicio_contrato = ssc.id_servicio_contrato) > 0 then \n" +
            "\t\t\t\t(\n" +
            "\t\t\t\t\tselect sum(v.valor_volumetria * ssc2.precio_unitario) as suma_volumetria\n" +
            "\t\t\t\t\tfrom sisecofi.sscft_caso_negocio_servicio_convenio scnsc\n" +
            "\t\t\t\t\tleft join sisecofi.sscft_servicio_convenio ssc2 on ssc2.id_servicio_convenio = scnsc.id_servicio_convenio,\n" +
            "\t\t\t\t\tlateral unnest(scnsc.periodos) with ordinality as p(valor_periodo, indice),\n" +
            "\t\t\t\t\tlateral unnest(scnsc.volumetria) with ordinality as v(valor_volumetria, indice)\n" +
            "\t\t\t\t\twhere lower(p.valor_periodo) = '%valor_periodo%'\n" +
            "\t\t\t\t\tand p.indice = v.indice and ssc2.id_servicio_convenio = (select max(ssc3.id_servicio_convenio) from sisecofi.sscft_servicio_convenio ssc3 where ssc3.id_servicio_contrato = ssc.id_servicio_contrato)\n" +
            "\t\t\t\t)\n" +
            "\t\t\telse (\n" +
            "\t\t\t\tselect sum(v.valor_volumetria * ssc.precio_unitario) as suma_volumetria\n" +
            "\t\t\t\tfrom sisecofi.sscft_caso_negocio_servicio_contrato scnsc,\n" +
            "\t\t\t\tlateral unnest(scnsc.periodos) with ordinality as p(valor_periodo, indice),\n" +
            "\t\t\t\tlateral unnest(scnsc.volumetria) with ordinality as v(valor_volumetria, indice)\n" +
            "\t\t\t\twhere lower(p.valor_periodo) = '%valor_periodo%'\n" +
            "\t\t\t\tand p.indice = v.indice and scnsc.id_servicio_contrato = ssc.id_servicio_contrato\n" +
            "\t\t\t)\n" +
            "\t\tend\n" +
            "\t, 0) planeado_monto_%mes%_%anio%";

    String CAMPOS_ESTIMADO_CANTIDAD_SERVICIOS = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(select sum(sse.cantidad_servicio_estimados) \n" +
            "\t\tfrom sisecofi.sscft_servicio_estimado sse \n" +
            "\t\tleft join sisecofi.sscft_estimacion se on se.id_estimacion = sse.id_estimacion \n" +
            "\t\tleft join sisecofi.sscfc_estatus_estimacion see on se.id_estatus_estimacion = see.id_estatus_estimacion\n" +
            "\t\tleft join sisecofi.sscfc_periodo_control_anio spca on se.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "\t\tleft join sisecofi.sscfc_periodo_control_mes spcm on se.id_periodo_control_mes = spcm.id_periodo_control_mes\n" +
            "\t\twhere sse.id_servicio_contrato = ssc.id_servicio_contrato and lower(see.nombre) not in ('inicial', 'cancelado')\n" +
            "\t\t\tand spca.nombre = '%anio%' and lower(spcm.nombre) = '%mes%')\n" +
            "\t, 0) estimado_cantidad_servicios_%mes%_%anio%";

    String CAMPOS_ESTIMADO_MONTO = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(select sum(sse.monto_estimado) \n" +
            "\t\tfrom sisecofi.sscft_servicio_estimado sse \n" +
            "\t\tleft join sisecofi.sscft_estimacion se on se.id_estimacion = sse.id_estimacion \n" +
            "\t\tleft join sisecofi.sscfc_estatus_estimacion see on se.id_estatus_estimacion = see.id_estatus_estimacion\n" +
            "\t\tleft join sisecofi.sscfc_periodo_control_anio spca on se.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "\t\tleft join sisecofi.sscfc_periodo_control_mes spcm on se.id_periodo_control_mes = spcm.id_periodo_control_mes\n" +
            "\t\twhere sse.id_servicio_contrato = ssc.id_servicio_contrato and lower(see.nombre) not in ('inicial', 'cancelado')\n" +
            "\t\t\tand spca.nombre = '%anio%' and lower(spcm.nombre) = '%mes%')\n" +
            "\t, 0) estimado_monto_%mes%_%anio%";

    String CAMPOS_DICTAMINADA_CANTIDAD_SERVICIOS_SAT = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(select sum(ssd.cantidad_servicios_sat) \n" +
            "\t\tfrom sisecofi.sscft_servicios_dictaminados ssd \n" +
            "\t\tleft join sisecofi.sscft_dictamen sd on sd.id_dictamen = ssd.id_dictamen \n" +
            "\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sed.id_estatus_dictamen = sd.id_estatus_dictamen\n" +
            "\t\tleft join sisecofi.sscfc_periodo_control_anio spca on sd.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "\t\tleft join sisecofi.sscfc_periodo_control_mes spcm on sd.id_periodo_control_mes = spcm.id_periodo_control_mes\n" +
            "\t\twhere ssd.id_servicio_contrato = ssc.id_servicio_contrato and lower(sed.nombre) in ('dictaminado')\n" +
            "\t\t\tand spca.nombre = '%anio%' and lower(spcm.nombre) = '%mes%')\n" +
            "\t, 0) dictaminada_cantidad_servicios_sat_%mes%_%anio%";

    String CAMPOS_DICTAMINADA_CANTIDAD_SERVICIOS_CC = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(select sum(ssd.cantidad_servicios_cc) \n" +
            "\t\tfrom sisecofi.sscft_servicios_dictaminados ssd \n" +
            "\t\tleft join sisecofi.sscft_dictamen sd on sd.id_dictamen = ssd.id_dictamen \n" +
            "\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sed.id_estatus_dictamen = sd.id_estatus_dictamen\n" +
            "\t\tleft join sisecofi.sscfc_periodo_control_anio spca on sd.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "\t\tleft join sisecofi.sscfc_periodo_control_mes spcm on sd.id_periodo_control_mes = spcm.id_periodo_control_mes\n" +
            "\t\twhere ssd.id_servicio_contrato = ssc.id_servicio_contrato and lower(sed.nombre) in ('dictaminado')\n" +
            "\t\t\tand spca.nombre = '%anio%' and lower(spcm.nombre) = '%mes%')\n" +
            "\t, 0) dictaminada_cantidad_servicios_cc_%mes%_%anio%";

    String CAMPOS_DICTAMINADA_MONTO_SAT = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(select sum(ssd.cantidad_servicios_sat * ssc.precio_unitario) \n" +
            "\t\tfrom sisecofi.sscft_servicios_dictaminados ssd \n" +
            "\t\tleft join sisecofi.sscft_dictamen sd on sd.id_dictamen = ssd.id_dictamen \n" +
            "\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sed.id_estatus_dictamen = sd.id_estatus_dictamen\n" +
            "\t\tleft join sisecofi.sscfc_periodo_control_anio spca on sd.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "\t\tleft join sisecofi.sscfc_periodo_control_mes spcm on sd.id_periodo_control_mes = spcm.id_periodo_control_mes\n" +
            "\t\twhere ssd.id_servicio_contrato = ssc.id_servicio_contrato and lower(sed.nombre) in ('dictaminado')\n" +
            "\t\t\tand spca.nombre = '%anio%' and lower(spcm.nombre) = '%mes%')\n" +
            "\t, 0) dictaminada_monto_sat_%mes%_%anio%";

    String CAMPOS_DICTAMINADA_MONTO_CC = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(select sum(ssd.cantidad_servicios_cc * ssc.precio_unitario) \n" +
            "\t\tfrom sisecofi.sscft_servicios_dictaminados ssd \n" +
            "\t\tleft join sisecofi.sscft_dictamen sd on sd.id_dictamen = ssd.id_dictamen \n" +
            "\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sed.id_estatus_dictamen = sd.id_estatus_dictamen\n" +
            "\t\tleft join sisecofi.sscfc_periodo_control_anio spca on sd.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "\t\tleft join sisecofi.sscfc_periodo_control_mes spcm on sd.id_periodo_control_mes = spcm.id_periodo_control_mes\n" +
            "\t\twhere ssd.id_servicio_contrato = ssc.id_servicio_contrato and lower(sed.nombre) in ('dictaminado')\n" +
            "\t\t\tand spca.nombre = '%anio%' and lower(spcm.nombre) = '%mes%')\n" +
            "\t, 0) dictaminada_monto_cc_%mes%_%anio%";

    String CAMPOS_PAGADA_CANTIDAD_SERVICIOS_SAT = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(select sum(ssd.cantidad_servicios_sat) \n" +
            "\t\tfrom sisecofi.sscft_servicios_dictaminados ssd \n" +
            "\t\tleft join sisecofi.sscft_dictamen sd on sd.id_dictamen = ssd.id_dictamen \n" +
            "\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sed.id_estatus_dictamen = sd.id_estatus_dictamen\n" +
            "\t\tleft join sisecofi.sscfc_periodo_control_anio spca on sd.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "\t\tleft join sisecofi.sscfc_periodo_control_mes spcm on sd.id_periodo_control_mes = spcm.id_periodo_control_mes\n" +
            "\t\twhere ssd.id_servicio_contrato = ssc.id_servicio_contrato and lower(sed.nombre) in ('pagado')\n" +
            "\t\t\tand spca.nombre = '%anio%' and lower(spcm.nombre) = '%mes%')\n" +
            "\t, 0) pagada_cantidad_servicios_sat_%mes%_%anio%";

    String CAMPOS_PAGADA_CANTIDAD_SERVICIOS_CC = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(select sum(ssd.cantidad_servicios_cc) \n" +
            "\t\tfrom sisecofi.sscft_servicios_dictaminados ssd \n" +
            "\t\tleft join sisecofi.sscft_dictamen sd on sd.id_dictamen = ssd.id_dictamen \n" +
            "\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sed.id_estatus_dictamen = sd.id_estatus_dictamen\n" +
            "\t\tleft join sisecofi.sscfc_periodo_control_anio spca on sd.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "\t\tleft join sisecofi.sscfc_periodo_control_mes spcm on sd.id_periodo_control_mes = spcm.id_periodo_control_mes\n" +
            "\t\twhere ssd.id_servicio_contrato = ssc.id_servicio_contrato and lower(sed.nombre) in ('pagado')\n" +
            "\t\t\tand spca.nombre = '%anio%' and lower(spcm.nombre) = '%mes%')\n" +
            "\t, 0) pagada_cantidad_servicios_cc_%mes%_%anio%";

    String CAMPOS_PAGADA_MONTO_SAT = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(select sum(ssd.cantidad_servicios_sat * ssc.precio_unitario) \n" +
            "\t\tfrom sisecofi.sscft_servicios_dictaminados ssd \n" +
            "\t\tleft join sisecofi.sscft_dictamen sd on sd.id_dictamen = ssd.id_dictamen \n" +
            "\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sed.id_estatus_dictamen = sd.id_estatus_dictamen\n" +
            "\t\tleft join sisecofi.sscfc_periodo_control_anio spca on sd.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "\t\tleft join sisecofi.sscfc_periodo_control_mes spcm on sd.id_periodo_control_mes = spcm.id_periodo_control_mes\n" +
            "\t\twhere ssd.id_servicio_contrato = ssc.id_servicio_contrato and lower(sed.nombre) in ('pagado')\n" +
            "\t\t\tand spca.nombre = '%anio%' and lower(spcm.nombre) = '%mes%')\n" +
            "\t, 0) pagada_monto_sat_%mes%_%anio%";

    String CAMPOS_PAGADA_MONTO_CC = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(select sum(ssd.cantidad_servicios_cc * ssc.precio_unitario) \n" +
            "\t\tfrom sisecofi.sscft_servicios_dictaminados ssd \n" +
            "\t\tleft join sisecofi.sscft_dictamen sd on sd.id_dictamen = ssd.id_dictamen \n" +
            "\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sed.id_estatus_dictamen = sd.id_estatus_dictamen\n" +
            "\t\tleft join sisecofi.sscfc_periodo_control_anio spca on sd.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "\t\tleft join sisecofi.sscfc_periodo_control_mes spcm on sd.id_periodo_control_mes = spcm.id_periodo_control_mes\n" +
            "\t\twhere ssd.id_servicio_contrato = ssc.id_servicio_contrato and lower(sed.nombre) in ('pagado')\n" +
            "\t\t\tand spca.nombre = '%anio%' and lower(spcm.nombre) = '%mes%')\n" +
            "\t, 0) pagada_monto_cc_%mes%_%anio%";
}
