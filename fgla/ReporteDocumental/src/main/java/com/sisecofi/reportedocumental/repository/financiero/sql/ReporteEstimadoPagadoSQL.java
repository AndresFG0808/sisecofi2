package com.sisecofi.reportedocumental.repository.financiero.sql;

@SuppressWarnings("all")
public interface ReporteEstimadoPagadoSQL {
    String SELECT = "select ";

    String FROM = "\nfrom\n";

    String WHERE = "\nwhere \n\t1 = 1 ";

    String PAGINACION = "\n\toffset :pagina rows fetch first :registros rows only";

    String CAMPOS_BASE = "\tdistinct \n" +
            "\tsp.nombre_corto nombre_proyecto,\n" +
            "\tsc.nombre_corto nombre_contrato,\n" +
            "\t(\n" +
            "\t\tselect string_agg(sp2.nombre_proveedor, ', ')\n" +
            "\t\tfrom sisecofi.sscft_proveedor sp2\n" +
            "\t\tjoin sisecofi.sscfr_asociacion_contrato_proveedor sacp on sacp.id_proveedor = sp2.id_proveedor\n" +
            "\t\twhere sacp.id_contrato = sc.id_contrato \n" +
            "\t) proveedor,\n" +
            "\tsdgc.numero_contrato,\n" +
            "\t(\n" +
            "\t\tcase\n" +
            "\t\t\twhen date(coalesce(cm.fecha_fin_max, svm.fecha_fin_vigencia_contrato)) \n"+
            "\t\t\t>= date(now()) then 'Sí'\n" +
            "\t\t\telse 'No'\n" +
            "\t\tend\n" +
            "\t) vigente,\n" +
            "\tspca1.id_periodo_control_anio id_anio,\n" +
            "\tspca1.nombre anio,\n" +
            "\tspcm1.id_periodo_control_mes id_mes,\n" +
            "\tspcm1.nombre mes";

    String CAMPO_ESTIMADO = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(\n" +
            "\t\t\tselect sum(sse.monto_estimado)\n" +
            "\t\t\tfrom sisecofi.sscft_estimacion se\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_estimacion see on see.id_estatus_estimacion = se.id_estatus_estimacion\n" +
            "\t\t\tleft join sisecofi.sscft_servicio_estimado sse on sse.id_estimacion = se.id_estimacion\n" +
            "\t\t\twhere se.id_contrato = sc.id_contrato and se.id_periodo_control_anio = spca1.id_periodo_control_anio and se.id_periodo_control_mes = spcm1.id_periodo_control_mes\n" +
            "\t\t\tand lower(see.nombre) not in ('inicial', 'cancelado')\n" +
            "\t\t)\n" +
            "\t, 0) estimado";

    String CAMPO_DICTAMINADO_SAT = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(\n" +
            "\t\t\tselect \n" +
            "\t\t\t\tsum(\n" +
            "\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\tselect \n" +
            "\t\t\t\t\t\t\t\tsum((ssd.cantidad_servicios_sat * ssc.precio_unitario) * (1 + case when ssc.ieps is true then (cast(si.porcentaje as numeric) / 100) else 0 end))\n" +
            "\t\t\t\t\t\t\tfrom sisecofi.sscft_servicios_dictaminados ssd\n" +
            "\t\t\t\t\t\t\tleft join sisecofi.sscft_servicio_contrato ssc on ssc.id_servicio_contrato = ssd.id_servicio_contrato\n" +
            "\t\t\t\t\t\t\twhere ssd.id_dictamen = sd2.id_dictamen and ssd.seleccionado is true\n" +
            "\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t-\n" +
            "\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\tcoalesce (\n" +
            "\t\t\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\t\t\tselect sum(sdd.monto)\n" +
            "\t\t\t\t\t\t\t\t\tfrom sisecofi.sscft_deducciones_dictamenes sdd\n" +
            "\t\t\t\t\t\t\t\t\tleft join sisecofi.sscfc_desgloce sd3 on sd3.id_desgloce = sdd.id_desgloce \n" +
            "\t\t\t\t\t\t\t\t\twhere sdd.id_dictamen = sd2.id_dictamen and lower(sd3.nombre) = 'sat'\n" +
            "\t\t\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t\t, 0)\n" +
            "\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t)\n" +
            "\t\t\t\t\t*\n" +
            "\t\t\t\t\t(1 + case when lower(si2.porcentaje) = 'no aplica' then 0 else (cast(si2.porcentaje as numeric) / 100) end)\n" +
            "\t\t\t\t)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2 \n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscfc_iva si2 on si2.id_iva = sd2.id_iva\n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato\n" +
            "\t\t\t\tand lower(sed2.nombre) not in ('inicial', 'cancelado')\n" +
            "\t\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "\t, 0) dictaminado_sat";

    String CAMPO_DICTAMINADO_CC = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(\n" +
            "\t\t\tselect \n" +
            "\t\t\t\tsum(\n" +
            "\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\tselect \n" +
            "\t\t\t\t\t\t\t\tsum((ssd.cantidad_servicios_cc * ssc.precio_unitario) * (1 + case when ssc.ieps is true then (cast(si.porcentaje as numeric) / 100) else 0 end))\n" +
            "\t\t\t\t\t\t\tfrom sisecofi.sscft_servicios_dictaminados ssd\n" +
            "\t\t\t\t\t\t\tleft join sisecofi.sscft_servicio_contrato ssc on ssc.id_servicio_contrato = ssd.id_servicio_contrato\n" +
            "\t\t\t\t\t\t\twhere ssd.id_dictamen = sd2.id_dictamen and ssd.seleccionado is true\n" +
            "\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t-\n" +
            "\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\tcoalesce (\n" +
            "\t\t\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\t\t\tselect sum(sdd.monto)\n" +
            "\t\t\t\t\t\t\t\t\tfrom sisecofi.sscft_deducciones_dictamenes sdd\n" +
            "\t\t\t\t\t\t\t\t\tleft join sisecofi.sscfc_desgloce sd3 on sd3.id_desgloce = sdd.id_desgloce \n" +
            "\t\t\t\t\t\t\t\t\twhere sdd.id_dictamen = sd2.id_dictamen and lower(sd3.nombre) = 'cc'\n" +
            "\t\t\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t\t, 0)\n" +
            "\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t)\n" +
            "\t\t\t\t\t*\n" +
            "\t\t\t\t\t(1 + case when lower(si2.porcentaje) = 'no aplica' then 0 else (cast(si2.porcentaje as numeric) / 100) end)\n" +
            "\t\t\t\t)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2 \n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscfc_iva si2 on si2.id_iva = sd2.id_iva\n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato\n" +
            "\t\t\t\tand lower(sed2.nombre) not in ('inicial', 'cancelado')\n" +
            "\t\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "\t, 0) dictaminado_cc";

    String CAMPO_DEDUCCIONES_SAT = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(\n" +
            "\t\t\tselect sum(sdd.monto)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_deducciones_dictamenes sdd on sdd.id_dictamen = sd2.id_dictamen\n" +
            "\t\t\tleft join sisecofi.sscfc_desgloce sd3 on sd3.id_desgloce = sdd.id_desgloce\n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato and lower(sed2.nombre) not in ('inicial', 'cancelado') and lower(sd3.nombre) = 'sat' \n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "\t, 0) deducciones_sat";

    String CAMPO_DEDUCCIONES_CC = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(\n" +
            "\t\t\tselect sum(sdd.monto)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_deducciones_dictamenes sdd on sdd.id_dictamen = sd2.id_dictamen\n" +
            "\t\t\tleft join sisecofi.sscfc_desgloce sd3 on sd3.id_desgloce = sdd.id_desgloce\n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato and lower(sed2.nombre) not in ('inicial', 'cancelado') and lower(sd3.nombre) = 'cc' \n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "\t, 0) deducciones_cc";

    String CAMPO_NOTA_CREDITO_SAT = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(\n" +
            "\t\t\tselect sum(snc.total * (cast(snc.porcentaje_sat as numeric) / 100))\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_notas_credito snc on snc.id_dictamen = sd2.id_dictamen\n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato and lower(sed2.nombre) not in ('inicial', 'cancelado')\n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "\t, 0) nota_credito_sat";

    String CAMPO_NOTA_CREDITO_CC = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(\n" +
            "\t\t\tselect sum(snc.total * (cast(snc.porcentajecc as numeric) / 100))\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_notas_credito snc on snc.id_dictamen = sd2.id_dictamen\n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato and lower(sed2.nombre) not in ('inicial', 'cancelado')\n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "\t, 0) nota_credito_cc";

    String CAMPO_SUB_TOTAL_SAT = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(\n" +
            "\t\t\tselect sum(ssd.cantidad_servicios_sat * ssc.precio_unitario) \n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2 \n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_servicios_dictaminados ssd on ssd.id_dictamen = sd2.id_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_servicio_contrato ssc on ssc.id_servicio_contrato = ssd.id_servicio_contrato\n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato and lower(sed2.nombre) not in ('inicial', 'cancelado')\n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "\t, 0) subtotal_sat";

    String CAMPO_SUB_TOTAL_CC = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(\n" +
            "\t\t\tselect sum(ssd.cantidad_servicios_cc * ssc.precio_unitario) \n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_servicios_dictaminados ssd on ssd.id_dictamen = sd2.id_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_servicio_contrato ssc on ssc.id_servicio_contrato = ssd.id_servicio_contrato\n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato and lower(sed2.nombre) not in ('inicial', 'cancelado')\n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "\t, 0) subtotal_cc";

    String CAMPO_IVA_SAT = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(\n" +
            "\t\t\tselect\n" +
            "\t\t\t\t\tsum(\n" +
            "\t\t\t\t\t\t(sf.iva * cast(sf.porcentaje_sat as numeric)) / 100 - (\n" +
            "\t\t\t\t\t\t\tselect\n" +
            "\t\t\t\t\t\t\t\tsnc.iva * cast(snc.porcentaje_sat as numeric) / 100\n" +
            "\t\t\t\t\t\t\tfrom sisecofi.sscft_notas_credito snc \n" +
            "\t\t\t\t\t\t\tleft join sisecofi.sscfc_estatus_nota_credito senc on snc.id_estatus_nota_credito = senc.id_estatus_nota_credito\n" +
            "\t\t\t\t\t\t\twhere snc.id_dictamen = sd2.id_dictamen and lower(senc.nombre) = 'aprobada'\n" +
            "\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_facturas sf on sf.id_dictamen = sd2.id_dictamen\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_factura sef on sef.id_estatus_factura = sf.id_estatus_factura\n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato and lower(sed2.nombre) not in ('inicial', 'cancelado')\n" +
            "\t\t\tand lower(sef.nombre) not in ('inicial','cancelado')\n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "\t, 0) iva_sat";

    String CAMPO_IVA_CC = ",\n" +
            "\tcoalesce (\t\t\t\n" +
            "\t\t(\n" +
            "\t\t\tselect\n" +
            "\t\t\t\t\tsum(\n" +
            "\t\t\t\t\t\t(sf.iva * cast(sf.porcentajecc as numeric)) / 100 - (\n" +
            "\t\t\t\t\t\t\tselect\n" +
            "\t\t\t\t\t\t\t\tsnc.iva * cast(snc.porcentajecc as numeric) / 100\n" +
            "\t\t\t\t\t\t\tfrom sisecofi.sscft_notas_credito snc \n" +
            "\t\t\t\t\t\t\tleft join sisecofi.sscfc_estatus_nota_credito senc on snc.id_estatus_nota_credito = senc.id_estatus_nota_credito\n" +
            "\t\t\t\t\t\t\twhere snc.id_dictamen = sd2.id_dictamen and lower(senc.nombre) = 'aprobada'\n" +
            "\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_facturas sf on sf.id_dictamen = sd2.id_dictamen\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_factura sef on sef.id_estatus_factura = sf.id_estatus_factura\n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato and lower(sed2.nombre) not in ('inicial', 'cancelado')\n" +
            "\t\t\tand lower(sef.nombre) not in ('inicial','cancelado')\n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "\t, 0) iva_cc";

    String CAMPO_IEPS_SAT = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(\n" +
            "\t\t\tselect sum((sf.ieps * cast(sf.porcentaje_sat as decimal(5, 2))) / 100)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_facturas sf on sf.id_dictamen = sd2.id_dictamen\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_factura sef on sef.id_estatus_factura = sf.id_estatus_factura\n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato and lower(sed2.nombre) not in ('inicial', 'cancelado')\n" +
            "\t\t\tand lower(sef.nombre) not in ('cancelado')\n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "\t, 0) ieps_sat";

    String CAMPO_IEPS_CC = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(\n" +
            "\t\t\tselect sum((sf.ieps * cast(sf.porcentajecc as decimal(5, 2))) / 100)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_facturas sf on sf.id_dictamen = sd2.id_dictamen\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_factura sef on sef.id_estatus_factura = sf.id_estatus_factura\n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato and lower(sed2.nombre) not in ('inicial', 'cancelado')\n" +
            "\t\t\tand lower(sef.nombre) not in ('cancelado')\n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "\t, 0) ieps_cc";

    String CAMPO_OTROS_IMPUESTOS_SAT = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(\n" +
            "\t\t\tselect sum((sf.otros_impuestos * cast(sf.porcentaje_sat as decimal(5, 2))) / 100)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_facturas sf on sf.id_dictamen = sd2.id_dictamen\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_factura sef on sef.id_estatus_factura = sf.id_estatus_factura\n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato and lower(sed2.nombre) not in ('inicial', 'cancelado')\n" +
            "\t\t\tand lower(sef.nombre) not in ('cancelado')\n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "\t, 0) otros_impuestos_sat";

    String CAMPO_OTROS_IMPUESTOS_CC = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(\n" +
            "\t\t\tselect sum((sf.otros_impuestos * cast(sf.porcentajecc as decimal(5, 2))) / 100)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_facturas sf on sf.id_dictamen = sd2.id_dictamen\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_factura sef on sef.id_estatus_factura = sf.id_estatus_factura\n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato and lower(sed2.nombre) not in ('inicial', 'cancelado')\n" +
            "\t\t\tand lower(sef.nombre) not in ('cancelado')\n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "\t, 0) otros_impuestos_cc";

    String CAMPO_TOTAL_PAGADO_SAT = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(\n" +
            "\t\t\tselect sum(srp.pagado_nafin)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_facturas sf on sf.id_dictamen = sd2.id_dictamen\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_factura sef on sf.id_estatus_factura = sef.id_estatus_factura\n" +
            "\t\t\tleft join sisecofi.sscft_referencia_pago srp on srp.id_factura = sf.id_factura\n" +
            "\t\t\tleft join sisecofi.sscfc_desgloce sd3 on sd3.id_desgloce = srp.id_desgloce\n" +
            "\t\t\twhere\n" +
            "\t\t\t\tsd2.id_contrato = sc.id_contrato and lower(sef.nombre) not in ('cancelado')\n" +
            "\t\t\t\tand lower(sed2.nombre) not in ('inicial', 'cancelado')\n" +
            "\t\t\t\tand lower(sd3.nombre) = 'sat' and spcm1.nombre = (\n" +
            "\t\t\t\t\tselect \n" +
            "\t\t\t\t\t\tcase to_char(srp.fecha_pago, 'MM')\n" +
            "\t\t\t\t           when '01' then 'Enero'\n" +
            "\t\t\t\t           when '02' then 'Febrero'\n" +
            "\t\t\t\t           when '03' then 'Marzo'\n" +
            "\t\t\t\t           when '04' then 'Abril'\n" +
            "\t\t\t\t           when '05' then 'Mayo'\n" +
            "\t\t\t\t           when '06' then 'Junio'\n" +
            "\t\t\t\t           when '07' then 'Julio'\n" +
            "\t\t\t\t           when '08' then 'Agosto'\n" +
            "\t\t\t\t           when '09' then 'Septiembre'\n" +
            "\t\t\t\t           when '10' then 'Octubre'\n" +
            "\t\t\t\t           when '11' then 'Noviembre'\n" +
            "\t\t\t\t           when '12' then 'Diciembre'\n" +
            "\t\t\t       \t\tend\n" +
            "\t       \t\t) and spca1.nombre = to_char(srp.fecha_pago, 'YYYY')\n" +
            "       \t)\n" +
            "\t, 0) pagado_sat";

    String CAMPO_TOTAL_PAGADO_CC = ",\n" +
            "\tcoalesce(\n" +
            "\t\t(\n" +
            "\t\t\tselect sum(srp.pagado_nafin)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed2 on sed2.id_estatus_dictamen = sd2.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_facturas sf on sf.id_dictamen = sd2.id_dictamen\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_factura sef on sf.id_estatus_factura = sef.id_estatus_factura\n" +
            "\t\t\tleft join sisecofi.sscft_referencia_pago srp on srp.id_factura = sf.id_factura\n" +
            "\t\t\tleft join sisecofi.sscfc_desgloce sd3 on sd3.id_desgloce = srp.id_desgloce\n" +
            "\t\t\twhere\n" +
            "\t\t\t\tsd2.id_contrato = sc.id_contrato and lower(sef.nombre) not in ('cancelado')\n" +
            "\t\t\t\tand lower(sed2.nombre) not in ('inicial', 'cancelado')\n" +
            "\t\t\t\tand lower(sd3.nombre) = 'cc' and spcm1.nombre = (\n" +
            "\t\t\t\t\tselect \n" +
            "\t\t\t\t\t\tcase to_char(srp.fecha_pago, 'MM')\n" +
            "\t\t\t\t           when '01' then 'Enero'\n" +
            "\t\t\t\t           when '02' then 'Febrero'\n" +
            "\t\t\t\t           when '03' then 'Marzo'\n" +
            "\t\t\t\t           when '04' then 'Abril'\n" +
            "\t\t\t\t           when '05' then 'Mayo'\n" +
            "\t\t\t\t           when '06' then 'Junio'\n" +
            "\t\t\t\t           when '07' then 'Julio'\n" +
            "\t\t\t\t           when '08' then 'Agosto'\n" +
            "\t\t\t\t           when '09' then 'Septiembre'\n" +
            "\t\t\t\t           when '10' then 'Octubre'\n" +
            "\t\t\t\t           when '11' then 'Noviembre'\n" +
            "\t\t\t\t           when '12' then 'Diciembre'\n" +
            "\t\t\t       \t\tend\n" +
            "\t       \t\t) and spca1.nombre = to_char(srp.fecha_pago, 'YYYY')\n" +
            "       \t)\n" +
            "\t, 0) pagado_cc";

    String JOINS_BASE = "\tsisecofi.sscft_contrato sc\n" +
            "left join sisecofi.sscft_proyecto sp on sp.id_proyecto = sc.id_proyecto\n" +
            "left join sisecofi.sscft_datos_generales_contrato sdgc on sdgc.id_contrato = sc.id_contrato \n" +
            "left join sisecofi.sscft_vigencia_montos svm on svm.id_contrato = sc.id_contrato\n" +
            "left join sisecofi.sscfc_ieps si on si.id_ieps = svm.id_ieps\n"+
            "left join (\n" +
            "    select id_contrato, max(fecha_fin) as fecha_fin_max\n" +
            "    from sisecofi.sscft_convenio_modificatorio\n" +
            "    where estatus = true\n" +
            "    group by id_contrato\n" +
            ") cm on cm.id_contrato = svm.id_contrato\n";

    String JOINS_DICTAMEN = "\ninner join sisecofi.sscft_dictamen sd on sd.id_contrato = sc.id_contrato\n" +
            "left join sisecofi.sscfc_estatus_dictamen sed on sed.id_estatus_dictamen = sd.id_estatus_dictamen \n" +
            "left join sisecofi.sscfc_periodo_control_anio spca1 on spca1.id_periodo_control_anio = sd.id_periodo_control_anio\n" +
            "left join sisecofi.sscfc_periodo_control_mes spcm1 on spcm1.id_periodo_control_mes = sd.id_periodo_control_mes";

    String JOINS_ESTIMACION = "\ninner join sisecofi.sscft_estimacion se on se.id_contrato = sc.id_contrato\n" +
            "left join sisecofi.sscfc_estatus_estimacion see on se.id_estatus_estimacion = see.id_estatus_estimacion \n" +
            "left join sisecofi.sscfc_periodo_control_anio spca1 on spca1.id_periodo_control_anio = se.id_periodo_control_anio\n" +
            "left join sisecofi.sscfc_periodo_control_mes spcm1 on spcm1.id_periodo_control_mes = se.id_periodo_control_mes";

    String SELECT_FULL_JOIN = "select \n" +
            "    coalesce(d.nombre_proyecto, e.nombre_proyecto) as nombre_corto_proyecto,\n" +
            "    coalesce(d.nombre_contrato, e.nombre_contrato) as nombre_corto_contrato,\n" +
            "    coalesce(d.proveedor, e.proveedor) as proveedor,\n" +
            "    coalesce(d.numero_contrato, e.numero_contrato) as numero_contrato,\n" +
            "    coalesce(d.vigente, e.vigente) as vigente,\n" +
            "    coalesce(d.mes ||' '|| d.anio, e.mes ||' '|| e.anio) as periodo_control";

    String FROM_FULL_JOIN = "\nfrom \n" +
            "    dictaminado d\n" +
            "full join \n" +
            "    estimado e \n" +
            "on \n" +
            "    d.nombre_proyecto = e.nombre_proyecto \n" +
            "    and d.nombre_contrato = e.nombre_contrato\n" +
            "    and d.proveedor = e.proveedor\n" +
            "    and d.vigente =  e.vigente\n" +
            "    and d.numero_contrato = e.numero_contrato\n" +
            "    and d.id_mes = e.id_mes\n" +
            "    and d.id_anio = e.id_anio\n" +
            "order by \n" +
            "    d.nombre_proyecto, d.nombre_contrato, d.id_anio asc, d.id_mes asc";

    String CAMPO_FULL_JOIN_ESTIMADO = ",\n" +
            "    coalesce(e.estimado,0) as estimado";

    String CAMPO_FULL_JOIN_DICTAMAINADO_SAT = ",\n" +
            "    coalesce(d.dictaminado_sat,0) as dictaminado_sat";

    String CAMPO_FULL_JOIN_DICTAMAINADO_CC = ",\n" +
            "    coalesce(d.dictaminado_cc,0) as dictaminado_cc";

    String CAMPO_FULL_JOIN_DEDUCCIONES_SAT = ",\n" +
            "    coalesce(d.deducciones_sat,0) as deducciones_sat";

    String CAMPO_FULL_JOIN_DEDUCCIONES_CC = ",\n" +
            "    coalesce(d.deducciones_cc,0) as deducciones_cc";

    String CAMPO_FULL_JOIN_NOTA_CREDITO_SAT = ",\n" +
            "    coalesce(d.nota_credito_sat,0) as nota_credito_sat";

    String CAMPO_FULL_JOIN_NOTA_CREDITO_CC = ",\n" +
            "    coalesce(d.nota_credito_cc,0) as nota_credito_cc";

    String CAMPO_FULL_JOIN_SUBTOTAL_SAT = ",\n" +
            "    coalesce(d.subtotal_sat,0) as subtotal_sat";

    String CAMPO_FULL_JOIN_SUBTOTAL_CC = ",\n" +
            "    coalesce(d.subtotal_cc,0) as subtotal_cc";

    String CAMPO_FULL_JOIN_IVA_SAT = ",\n" +
            "    coalesce(d.iva_sat,0) as iva_sat";

    String CAMPO_FULL_JOIN_IVA_CC = ",\n" +
            "    coalesce(d.iva_cc,0) as iva_cc";

    String CAMPO_FULL_JOIN_IEPS_SAT = ",\n" +
            "    coalesce(d.ieps_sat,0) as ieps_sat";

    String CAMPO_FULL_JOIN_IEPS_CC = ",\n" +
            "    coalesce(d.ieps_cc,0) as ieps_cc";

    String CAMPO_FULL_JOIN_OTROS_IMP_SAT = ",\n" +
            "    coalesce(d.otros_impuestos_sat,0) as otros_impuestos_sat";

    String CAMPO_FULL_JOIN_OTROS_IMP_CC = ",\n" +
            "    coalesce(d.otros_impuestos_cc,0) as otros_impuestos_cc";

    String CAMPO_FULL_JOIN_PAGADO_SAT = ",\n" +
            "    coalesce(d.pagado_sat,0) as pagado_sat";

    String CAMPO_FULL_JOIN_PAGADO_CC = ",\n" +
            "    coalesce(d.pagado_cc,0) as pagado_cc";


    String FULL_JOIN_CONTADOR = "select \n" +
            "    count(*)\n" +
            "from \n" +
            "    dictaminado d\n" +
            "full join \n" +
            "    estimado e \n" +
            "on \n" +
            "    d.nombre_proyecto = e.nombre_proyecto \n" +
            "    and d.nombre_contrato = e.nombre_contrato\n" +
            "    and d.proveedor = e.proveedor\n" +
            "    and d.vigente =  e.vigente\n" +
            "    and d.numero_contrato = e.numero_contrato\n" +
            "    and d.id_mes = e.id_mes\n" +
            "    and d.id_anio = e.id_anio";

    String CONDICION_ESTIMACION = "\n\tand lower(see.nombre) not in ('inicial', 'cancelado')";

    String CONDICION_DICTAMENES = "\n\tand lower(sed.nombre) not in ('inicial', 'cancelado')";

    String[] ETIQUETAS = new String[]{
            "Nombre corto del proyecto",
            "Nombre corto del contrato",
            "Proveedor",
            "Número del contrato",
            "Vigente",
            "Periodo de control",
            "Estimado",
            "Dictaminado SAT",
            "Dictaminado CC",
            "Deducción SAT",
            "Deducción CC",
            "Nota de crédito SAT",
            "Nota de crédito CC",
            "Subtotal SAT",
            "Subtotal CC",
            "IVA SAT",
            "IVA CC",
            "IEPS SAT",
            "IEPS CC",
            "Otros Impuestos SAT",
            "Otros Impuestos CC",
            "Total pagado SAT",
            "Total pagado CC",
    };
}
