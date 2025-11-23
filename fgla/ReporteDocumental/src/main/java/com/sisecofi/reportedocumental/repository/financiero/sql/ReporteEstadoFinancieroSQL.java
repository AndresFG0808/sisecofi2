package com.sisecofi.reportedocumental.repository.financiero.sql;

@SuppressWarnings("all")
public interface ReporteEstadoFinancieroSQL {
    String SELECT = "select";

    String FROM = "\nfrom\n";

    String WHERE = "\nwhere \n\t1 = 1";

    String PAGINACION = "\n\toffset :pagina rows fetch first :registros rows only";

    String CAMPOS_BASE = "\n" +
            "\t\tdistinct \n" +
            "\t\tsp.nombre_corto nombre_corto_proyecto,\n" +
            "\t\tsc.nombre_corto nombre_corto_contrato,\n" +
            "\t\tsdt.nombre dominio,\n" +
            "\t\t(\n" +
            "\t\t\tselect string_agg(sp2.nombre_proveedor, ', ')\n" +
            "\t\t\tfrom sisecofi.sscft_proveedor sp2\n" +
            "\t\t\tjoin sisecofi.sscfr_asociacion_contrato_proveedor sacp on sacp.id_proveedor = sp2.id_proveedor\n" +
            "\t\t\twhere sacp.id_contrato = sc.id_contrato \n" +
            "\t\t) proveedor,\n" +
            "\t\tcase when coalesce(\n" +
            "\t\t\t\t(select cm.fecha_fin\\:\\:date\n" +
            "\t\t\t\t from sisecofi.sscft_convenio_modificatorio cm\n" +
            "\t\t\t\t where cm.id_contrato = sc.id_contrato\n" +
            "\t\t\t\t order by cm.id_convenio_modificatorio desc\n" +
            "\t\t\t\t limit 1),\n" +
            "\t\t\t\tsvm.fecha_fin_vigencia_contrato\\:\\:date\n" +
            "\t\t\t) >= current_date then 'Sí' else 'No' end as vigente,\n"+
            "\t\tsdgc.numero_contrato numero_contrato,\n" +
            "\t\tspca.id_periodo_control_anio id_anio,\n" +
            "\t\tspca.nombre anio,\n" +
            "\t\tspcm.id_periodo_control_mes id_mes,\n" +
            "\t\tspcm.nombre mes,\n" +
            "\t\tstm.nombre moneda";

    String CAMPOS_ESTIMACION = ",\n" +
            "\t\tcoalesce(\n" +
            "\t\t\tcase \n" +
            "\t\t\t\twhen lower(stm.nombre) = 'mxn' then 0 \n" +
            "\t\t\t\telse (\n" +
            "\t\t\t\t\tselect sum(se2.tipo_cambio_referencial) \n" +
            "\t\t\t\t\tfrom sisecofi.sscft_estimacion se2\n" +
            "\t\t\t\t\tleft join sisecofi.sscfc_estatus_estimacion see on see.id_estatus_estimacion = se2.id_estatus_estimacion\n" +
            "\t\t\t\t\twhere se2.id_contrato = sc.id_contrato and lower(see.nombre) not in('inicial', 'cancelado')\n" +
            "\t\t\t\t\tand se2.id_periodo_control_mes = spcm.id_periodo_control_mes and se2.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "\t\t\t\t)\n" +
            "\t\t\tend\n" +
            "\t\t, 0) tipo_cambio_referencial_estimado,\n" +
            "\t\tcoalesce(\n" +
            "\t\t\t(select sum(se2.monto_estimado) \n" +
            "\t\t\tfrom sisecofi.sscft_estimacion se2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_estimacion see on see.id_estatus_estimacion = se2.id_estatus_estimacion\n" +
            "\t\t\twhere se2.id_contrato = sc.id_contrato and lower(see.nombre) not in('inicial', 'cancelado')\n" +
            "\t\t\tand se2.id_periodo_control_mes = spcm.id_periodo_control_mes and se2.id_periodo_control_anio = spca.id_periodo_control_anio)\n" +
            "\t\t, 0) monto_dolares_estimado,\n" +
            "\t\tcoalesce(\n" +
            "\t\t\t(select sum(se2.monto_estimado_pesos) \n" +
            "\t\t\tfrom sisecofi.sscft_estimacion se2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_estimacion see on see.id_estatus_estimacion = se2.id_estatus_estimacion\n" +
            "\t\t\twhere se2.id_contrato = sc.id_contrato and lower(see.nombre) not in('inicial', 'cancelado')\n" +
            "\t\t\tand se2.id_periodo_control_mes = spcm.id_periodo_control_mes and se2.id_periodo_control_anio = spca.id_periodo_control_anio)\n" +
            "\t\t, 0) monto_pesos_estimado";

    String CAMPOS_DICTAMEN = ",\n" +
            "\t\tcoalesce(\n" +
            "\t\t\tcase \n" +
            "\t\t\t\twhen lower(stm.nombre) = 'mxn' then 0 \n" +
            "\t\t\t\telse (\n" +
            "\t\t\t\t\tselect sum(sd2.tipo_cambio_referencial)\n" +
            "\t\t\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\t\t\twhere sd2.id_contrato = sc.id_contrato and lower(sed2.nombre) not in('inicial', 'cancelado')\n" +
            "\t\t\t\t\tand sd2.id_periodo_control_mes = spcm.id_periodo_control_mes and sd2.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "\t\t\t\t)\n" +
            "\t\t\tend\n" +
            "\t\t, 0) tipo_cambio_referencial_dictaminado,\n" +
            "\t\tcoalesce\n" +
            "\t\t(\n" +
            "\t\t\t(\n" +
            "\t\t\t\tselect sum(src.total)\n" +
            "\t\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\t\tleft join sisecofi.sscft_resumen_consolidado src on src.id_dictamen = sd2.id_dictamen\n" +
            "\t\t\t\tleft join sisecofi.sscfc_fase_dictamen sfd on sfd.id_fase_dictamen = src.id_fase_dictamen\n" +
            "\t\t\t\twhere lower(sfd.nombre) = 'dictaminado' and sd2.id_contrato = sc.id_contrato and lower(sed2.nombre) not in('inicial', 'cancelado')\n" +
            "\t\t\t\tand sd2.id_periodo_control_mes = spcm.id_periodo_control_mes and sd2.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "\t\t\t)\n" +
            "\t\t, 0) monto_dolares_dictaminado,\n" +
            "\t\tcoalesce\n" +
            "\t\t(\n" +
            "\t\t\t(\n" +
            "\t\t\t\tselect sum(src.total_pesos)\n" +
            "\t\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\t\tleft join sisecofi.sscft_resumen_consolidado src on src.id_dictamen = sd2.id_dictamen\n" +
            "\t\t\t\tleft join sisecofi.sscfc_fase_dictamen sfd on sfd.id_fase_dictamen = src.id_fase_dictamen\n" +
            "\t\t\t\twhere lower(sfd.nombre) = 'dictaminado' and sd2.id_contrato = sc.id_contrato and lower(sed2.nombre) not in('inicial', 'cancelado')\n" +
            "\t\t\t\tand sd2.id_periodo_control_mes = spcm.id_periodo_control_mes and sd2.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "\t\t\t)\n" +
            "\t\t, 0) monto_pesos_dictaminado";

    String CAMPOS_DICTAMEN_PAGADO = ",\n" +
            "\t\tcoalesce (\n" +
            "\t\t\t(\n" +
            "\t\t\t\tselect sum(srp.tipo_cambio_pagado)\n" +
            "\t\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\t\tleft join sisecofi.sscft_solicitud_pago ssp on ssp.id_dictamen = sd2.id_dictamen\n" +
            "\t\t\t\tleft join sisecofi.sscft_referencia_pago srp on srp.id_solicitud_pago = ssp.id_solicitud_pago\n" +
            "\t\t\t\twhere sd2.id_contrato = sc.id_contrato and lower(sed2.nombre) in ('pagado')\n" +
            "\t\t\t\tand sd2.id_periodo_control_mes = spcm.id_periodo_control_mes and sd2.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "\t\t\t)\n" +
            "\t\t, 0) dictamen_pagado_tipo_cambio_real,\n" +
            "\t\tcoalesce (\t\t\n" +
            "\t\t\t\tcase \n" +
            "\t\t\t\t\twhen lower(stm.nombre) = 'mxn' then (\n" +
            "\t\t\t\t\t\tselect sum(srp.pagado_nafin)\n" +
            "\t\t\t\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\t\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\t\t\t\tleft join sisecofi.sscft_facturas sf on sd2.id_dictamen = sf.id_dictamen\n" +
            "\t\t\t\t\t\tleft join sisecofi.sscfc_estatus_factura sef on sef.id_estatus_factura = sf.id_estatus_factura\n" +
            "\t\t\t\t\t\tleft join sisecofi.sscft_referencia_pago srp on srp.id_factura = sf.id_factura\n" +
            "\t\t\t\t\t\twhere sd2.id_contrato = sc.id_contrato and lower(sed2.nombre) in ('pagado') and lower(sef.nombre) = 'pagado'\n" +
            "\t\t\t\t\t\tand sd2.id_periodo_control_mes = spcm.id_periodo_control_mes and sd2.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "\t\t\t\t\t)\n" +
            "\t\t\t\t\telse (\n" +
            "\t\t\t\t\t\tselect sum(src.total)\n" +
            "\t\t\t\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\t\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\t\t\t\tleft join sisecofi.sscft_resumen_consolidado src on src.id_dictamen = sd2.id_dictamen\n" +
            "\t\t\t\t\t\tleft join sisecofi.sscfc_fase_dictamen sfd on sfd.id_fase_dictamen = src.id_fase_dictamen\n" +
            "\t\t\t\t\t\twhere sd2.id_contrato = sc.id_contrato and lower(sed2.nombre) in ('pagado') and lower(sfd.nombre) = 'facturado'\n" +
            "\t\t\t\t\t\tand sd2.id_periodo_control_mes = spcm.id_periodo_control_mes and sd2.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "\t\t\t\t\t)\n" +
            "\t\t\t\tend\t\t\t\n" +
            "\t\t, 0) dictamen_pagado_monto_dolares,\n" +
            "\t\tcoalesce (\n" +
            "\t\t\t(\n" +
            "\t\t\t\tselect sum(srp.pagado_nafin)\n" +
            "\t\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\t\tleft join sisecofi.sscft_facturas sf on sf.id_dictamen = sd2.id_dictamen\n" +
            "\t\t\t\tleft join sisecofi.sscfc_estatus_factura sef on sef.id_estatus_factura = sf.id_estatus_factura\n" +
            "\t\t\t\tleft join sisecofi.sscft_referencia_pago srp on srp.id_factura = sf.id_factura\n" +
            "\t\t\t\twhere sd2.id_contrato = sc.id_contrato and lower(sed2.nombre) in ('pagado') and lower(sef.nombre) = 'pagado'\n" +
            "\t\t\t\tand sd2.id_periodo_control_mes = spcm.id_periodo_control_mes and sd2.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "\t\t\t)\n" +
            "\t\t, 0) dictamen_pagado_monto_pesos";

    String CAMPO_DICTAMEN_SAT = ",\n" +
            "\t\tcoalesce (\n" +
            "\t\t\t(\n" +
            "\t\t\t\tselect \n" +
            "\t\t\t\t\tsum(\n" +
            "\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\t\tselect \n" +
            "\t\t\t\t\t\t\t\t\tsum((ssd.cantidad_servicios_sat * ssc.precio_unitario) * (1 + case when ssc.ieps is true then (cast(si.porcentaje as numeric) / 100) else 0 end))\n" +
            "\t\t\t\t\t\t\t\tfrom sisecofi.sscft_servicios_dictaminados ssd\n" +
            "\t\t\t\t\t\t\t\tleft join sisecofi.sscft_servicio_contrato ssc on ssc.id_servicio_contrato = ssd.id_servicio_contrato\n" +
            "\t\t\t\t\t\t\t\twhere ssd.id_dictamen = sd2.id_dictamen and ssd.seleccionado is true\n" +
            "\t\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t\t-\n" +
            "\t\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\t\tcoalesce (\n" +
            "\t\t\t\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\t\t\t\tselect sum(sdd.monto)\n" +
            "\t\t\t\t\t\t\t\t\t\tfrom sisecofi.sscft_deducciones_dictamenes sdd\n" +
            "\t\t\t\t\t\t\t\t\t\tleft join sisecofi.sscfc_desgloce sd3 on sd3.id_desgloce = sdd.id_desgloce \n" +
            "\t\t\t\t\t\t\t\t\t\twhere sdd.id_dictamen = sd2.id_dictamen and lower(sd3.nombre) = 'sat'\n" +
            "\t\t\t\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t\t\t, 0)\n" +
            "\t\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t*\n" +
            "\t\t\t\t\t\t(1 + case when lower(si2.porcentaje) = 'no aplica' then 0 else (cast(si2.porcentaje as numeric) / 100) end)\n" +
            "\t\t\t\t\t)\n" +
            "\t\t\t\tfrom sisecofi.sscft_dictamen sd2 \n" +
            "\t\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\t\tleft join sisecofi.sscfc_iva si2 on si2.id_iva = sd2.id_iva\n" +
            "\t\t\t\twhere sd2.id_contrato = sc.id_contrato\n" +
            "\t\t\t\t\tand lower(sed2.nombre) not in ('inicial', 'cancelado')\n" +
            "\t\t\t\t\tand sd2.id_periodo_control_mes = spcm.id_periodo_control_mes and sd2.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "\t\t\t)\n" +
            "\t\t, 0) dictamen_sat";

    String CAMPO_DICTAMEN_CC = ",\n" +
            "\t\tcoalesce (\n" +
            "\t\t\t(\n" +
            "\t\t\t\tselect \n" +
            "\t\t\t\t\tsum(\n" +
            "\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\t\tselect \n" +
            "\t\t\t\t\t\t\t\t\tsum((ssd.cantidad_servicios_cc * ssc.precio_unitario) * (1 + case when ssc.ieps is true then (cast(si.porcentaje as numeric) / 100) else 0 end))\n" +
            "\t\t\t\t\t\t\t\tfrom sisecofi.sscft_servicios_dictaminados ssd\n" +
            "\t\t\t\t\t\t\t\tleft join sisecofi.sscft_servicio_contrato ssc on ssc.id_servicio_contrato = ssd.id_servicio_contrato\n" +
            "\t\t\t\t\t\t\t\twhere ssd.id_dictamen = sd2.id_dictamen and ssd.seleccionado is true\n" +
            "\t\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t\t-\n" +
            "\t\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\t\tcoalesce (\n" +
            "\t\t\t\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\t\t\t\tselect sum(sdd.monto)\n" +
            "\t\t\t\t\t\t\t\t\t\tfrom sisecofi.sscft_deducciones_dictamenes sdd\n" +
            "\t\t\t\t\t\t\t\t\t\tleft join sisecofi.sscfc_desgloce sd3 on sd3.id_desgloce = sdd.id_desgloce \n" +
            "\t\t\t\t\t\t\t\t\t\twhere sdd.id_dictamen = sd2.id_dictamen and lower(sd3.nombre) = 'cc'\n" +
            "\t\t\t\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t\t\t, 0)\n" +
            "\t\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t*\n" +
            "\t\t\t\t\t\t(1 + case when lower(si2.porcentaje) = 'no aplica' then 0 else (cast(si2.porcentaje as numeric) / 100) end)\n" +
            "\t\t\t\t\t)\n" +
            "\t\t\t\tfrom sisecofi.sscft_dictamen sd2 \n" +
            "\t\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\t\tleft join sisecofi.sscfc_iva si2 on si2.id_iva = sd2.id_iva\n" +
            "\t\t\t\twhere sd2.id_contrato = sc.id_contrato\n" +
            "\t\t\t\t\tand lower(sed2.nombre) not in ('inicial', 'cancelado')\n" +
            "\t\t\t\t\tand sd2.id_periodo_control_mes = spcm.id_periodo_control_mes and sd2.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "\t\t\t)\n" +
            "\t\t, 0) dictamen_cc";

    String CAMPO_PAGADO_SAT = ",\n" +
            "\t\tcoalesce (\n" +
            "\t\t\t(\n" +
            "\t\t\t\tselect sum(srp.pagado_nafin)\n" +
            "\t\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\t\tleft join sisecofi.sscft_facturas sf on sf.id_dictamen = sd2.id_dictamen\n" +
            "\t\t\t\tjoin sisecofi.sscfc_estatus_factura sef on sef.id_estatus_factura = sf.id_estatus_factura\n" +
            "\t\t\t\tjoin sisecofi.sscft_referencia_pago srp on sf.id_factura = srp.id_factura\n" +
            "\t\t\t\tjoin sisecofi.sscfc_desgloce sd3 on sd3.id_desgloce = srp.id_desgloce\n" +
            "\t\t\t\twhere sd2.id_contrato = sc.id_contrato and lower(sef.nombre) in ('pagado') and lower(sd3.nombre) = 'sat' and lower(sed2.nombre) in ('pagado')\n" +
            "\t\t\t\tand sd2.id_periodo_control_mes = spcm.id_periodo_control_mes and sd2.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "\t       \t)\n" +
            "\t\t, 0) pagado_sat";

    String CAMPO_PAGADO_CC = ",\n" +
            "\t\tcoalesce (\n" +
            "\t\t\t(\n" +
            "\t\t\t\tselect sum(srp.pagado_nafin)\n" +
            "\t\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\t\tleft join sisecofi.sscft_facturas sf on sf.id_dictamen = sd2.id_dictamen\n" +
            "\t\t\t\tjoin sisecofi.sscfc_estatus_factura sef on sef.id_estatus_factura = sf.id_estatus_factura\n" +
            "\t\t\t\tjoin sisecofi.sscft_referencia_pago srp on sf.id_factura = srp.id_factura\n" +
            "\t\t\t\tjoin sisecofi.sscfc_desgloce sd3 on sd3.id_desgloce = srp.id_desgloce\n" +
            "\t\t\t\twhere sd2.id_contrato = sc.id_contrato and lower(sef.nombre) in ('pagado') and lower(sd3.nombre) = 'cc' and lower(sed2.nombre) in ('pagado')\n" +
            "\t\t\t\tand sd2.id_periodo_control_mes = spcm.id_periodo_control_mes and sd2.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "\t       \t)\n" +
            "\t\t, 0) pagado_cc";

    String JOINS_BASE = "\tsisecofi.sscft_proyecto sp\n" +
            "\tleft join sisecofi.sscft_contrato sc on\n" +
            "\t\tsc.id_proyecto = sp.id_proyecto\n" +
            "\tleft join sisecofi.sscft_datos_generales_contrato sdgc on\n" +
            "\t\tsdgc.id_contrato = sc.id_contrato \n" +
            "\tleft join sisecofi.sscfc_dominios_tecnologicos sdt on\n" +
            "\t\tsdt.id_dominios_tecnologicos = sdgc.id_dominios_tecnologicos \n" +
            "\tleft join sisecofi.sscft_vigencia_montos svm on\n" +
            "\t\tsvm.id_contrato = sc.id_contrato\n" +
            "\tleft join sisecofi.sscfc_ieps si on\n" +
            "\t\tsi.id_ieps = svm.id_ieps\n" +
            "\tleft join sisecofi.sscfc_tipo_moneda stm on\n" +
            "\t\tstm.id_tipo_moneda = svm.id_tipo_moneda";

    String JOINS_DICTAMEN = "\tleft join sisecofi.sscft_dictamen sd on\n" +
            "\t\tsd.id_contrato = sc.id_contrato\n" +
            "\tleft join sisecofi.sscfc_estatus_dictamen sed on\n" +
            "\t\tsed.id_estatus_dictamen = sd.id_estatus_dictamen\n" +
            "\tleft join sisecofi.sscfc_periodo_control_mes spcm on\n" +
            "\t\tspcm.id_periodo_control_mes = sd.id_periodo_control_mes\n" +
            "\tleft join sisecofi.sscfc_periodo_control_anio spca on\n" +
            "\t\tspca.id_periodo_control_anio = sd.id_periodo_control_anio";

    String JOINS_ESTIMACION = "\tleft join sisecofi.sscft_estimacion se on\n" +
            "\t\tse.id_contrato = sc.id_contrato\n" +
            "\tleft join sisecofi.sscfc_estatus_estimacion see on\n" +
            "\t\tsee.id_estatus_estimacion = se.id_estatus_estimacion\n" +
            "\tleft join sisecofi.sscfc_periodo_control_mes spcm on\n" +
            "\t\tspcm.id_periodo_control_mes = se.id_periodo_control_mes\n" +
            "\tleft join sisecofi.sscfc_periodo_control_anio spca on\n" +
            "\t\tspca.id_periodo_control_anio = se.id_periodo_control_anio";

    String SELECT_FULL_JOIN = "select \n" +
            "    coalesce(d.nombre_corto_proyecto, e.nombre_corto_proyecto) as nombre_corto_proyecto,\n" +
            "    coalesce(d.nombre_corto_contrato, e.nombre_corto_contrato) as nombre_corto_contrato,\n" +
            "    coalesce(d.dominio, e.dominio) as dominio,\n" +
            "    coalesce(d.proveedor, e.proveedor) as proveedor,\n" +
            "    coalesce(d.vigente, e.vigente) as vigente,\n" +
            "    coalesce(d.numero_contrato, e.numero_contrato) as numero_contrato,\n" +
            "    coalesce(d.mes ||' '|| d.anio, e.mes ||' '|| e.anio) as periodo_control,\n" +
            "    coalesce(d.moneda, e.moneda) as moneda,\n" +
            "    coalesce(e.tipo_cambio_referencial_estimado,0) as tipo_cambio_referencial_estimado,\n" +
            "    coalesce(e.monto_dolares_estimado,0) as monto_dolares_estimado,\n" +
            "    coalesce(e.monto_pesos_estimado,0) as monto_pesos_estimado,\n" +
            "    coalesce(d.tipo_cambio_referencial_dictaminado,0) as tipo_cambio_referencial_dictaminado,\n" +
            "    coalesce(d.monto_dolares_dictaminado,0) as monto_dolares_dictaminado,\n" +
            "    coalesce(d.monto_pesos_dictaminado,0) as monto_pesos_dictaminado,\n" +
            "    coalesce(d.dictamen_pagado_tipo_cambio_real,0) as dictamen_pagado_tipo_cambio_real,\n" +
            "    coalesce(d.dictamen_pagado_monto_dolares,0) as dictamen_pagado_monto_dolares,\n" +
            "    coalesce(d.dictamen_pagado_monto_pesos,0) as dictamen_pagado_monto_pesos";


    String FROM_FULL_JOIN = "\nfrom \n" +
            "    dictaminado d\n" +
            "full join \n" +
            "    estimado e \n" +
            "on \n" +
            "    d.nombre_corto_proyecto = e.nombre_corto_proyecto \n" +
            "    and d.nombre_corto_contrato = e.nombre_corto_contrato\n" +
            "    and coalesce(d.dominio, 'true') = coalesce(e.dominio, 'true')\n" +
            "    and coalesce(d.proveedor, 'true') = coalesce(e.proveedor, 'true')\n" +
            "    and d.vigente =  e.vigente\n" +
            "    and coalesce(d.numero_contrato, 'true') = coalesce(e.numero_contrato, 'true')\n" +
            "    and coalesce(d.id_mes, 1) = coalesce(e.id_mes, 1)\n" +
            "    and coalesce(d.id_anio, 1) = coalesce(e.id_anio, 1)\n" +
            "    and coalesce(d.moneda, 'true') = coalesce(e.moneda, 'true')\n" +
            "    order by \n" +
            "    coalesce(d.nombre_corto_proyecto, e.nombre_corto_proyecto) asc,    coalesce(d.nombre_corto_contrato, e.nombre_corto_contrato) asc,\tcoalesce(d.id_anio, e.id_anio) asc,\tcoalesce (d.id_mes, e.id_mes) asc";

    String CAMPO_DICTAMAEN_SAT_FULL_JOIN = ",\n" +
            "    coalesce(d.dictamen_sat,0) as dictamen_sat";

    String CAMPO_DICTAMEN_CC_FULL_JOIN = ",\n" +
            "    coalesce(d.dictamen_cc,0) as dictamen_cc";

    String CAMPO_PAGADO_SAT_FULL_JOIN = ",\n" +
            "    coalesce(d.pagado_sat,0) as pagado_sat";

    String CAMPO_PAGADO_CC_FULL_JOIN = ",\n" +
            "    coalesce(d.pagado_cc,0) as pagado_cc";

    String FULL_JOIN_CONTADOR = "select \n" +
            "    count(*)\n" +
            "from \n" +
            "    dictaminado d\n" +
            "full join \n" +
            "    estimado e \n" +
            "on \n" +
            "    d.nombre_corto_proyecto = e.nombre_corto_proyecto \n" +
            "    and d.nombre_corto_contrato = e.nombre_corto_contrato\n" +
            "    and coalesce(d.dominio, 'true') = coalesce(e.dominio, 'true')\n" +
            "    and coalesce(d.proveedor, 'true') = coalesce(e.proveedor, 'true')\n" +
            "    and d.vigente =  e.vigente\n" +
            "    and coalesce(d.numero_contrato, 'true') = coalesce(e.numero_contrato, 'true')\n" +
            "    and coalesce(d.id_mes, 1) = coalesce(e.id_mes, 1)\n" +
            "    and coalesce(d.id_anio, 1) = coalesce(e.id_anio, 1)\n" +
            "    and coalesce(d.moneda, 'true') = coalesce(e.moneda, 'true')";


    String[] ETIQUETAS = new String[]{
            "Nombre corto del proyecto",
            "Nombre corto del contrato",
            "Dominio",
            "Proveedor",
            "Vigente",
            "Número del contrato",
            "Periodo de control",
            "Moneda",
            "Tipo de cambio referencial",
            "Monto en dólares",
            "Monto en pesos",
            "Tipo de cambio referencial",
            "Monto en dólares",
            "Monto en pesos",
            "Tipo de cambio real",
            "Monto en dólares",
            "Monto en pesos",
            "Dictamen SAT",
            "Dictamen CC",
            "Pagado SAT",
            "Pagado CC",
    };
}
