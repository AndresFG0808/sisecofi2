package com.sisecofi.reportedocumental.repository.financiero.sql;

@SuppressWarnings("all")
public interface ReporteTipoSQL {
    String SELECT = "select ";
	
    String SELECT_ALL_FROM = "select * \n" +
    		"from ( \n";

    String FROM = "\nfrom\n";

    String WHERE = "\nwhere \n\t1 = 1 ";

    String PAGINACION = "\n\toffset :pagina rows fetch first :registros rows only";

    String CAMPOS_DETALLE_GENERAL = " \n" +
            "\tdistinct \n" +
            "\tsp.nombre_corto as nombre_corto_proyecto,\n" +
            "\tsc.nombre_corto as nombre_corto_contrato,\n" +
            "\tsdgc.numero_contrato,\n" +
            "\t(\n" +
            "\t\tselect string_agg(p2_0.nombre_proveedor,'; ')\n" +
            "\t\tfrom sisecofi.sscfr_asociacion_contrato_proveedor a1_0\n" +
            "\t\tjoin sisecofi.sscft_proveedor p2_0 on p2_0.id_proveedor = a1_0.id_proveedor\n" +
            "\t\twhere a1_0.id_contrato = sdgc.id_contrato\n" +
            "\t) proveedor";

    String CAMPOS_DETALLE_GENERAL_OPCIONAL = ",\n" +
            "\tto_char(sd.periodo_inicio, 'dd/MM/YYYY') periodo_inicio,\n" +
            "\tto_char(sd.periodo_fin, 'dd/MM/YYYY') periodo_fin,\n" +
            "\t(spcm.nombre || ' ' || spca.nombre) periodo_control,\n" +
            "\tsd.descripcion";


    String CAMPOS_DETALLE_FACTURAS = ",\n" +
            "\tsf.folio folio_factura,\n" +
            "\tsf.comprobante_fiscal comprobante_fiscal,\n" +
            "\tto_char(sf.fecha_facturacion, 'dd/MM/YYYY') fecha_facturacion,\n" +
            "\t(\n" +
            "\t\tselect distinct to_char(srp1.fecha_pago, 'dd/MM/YYYY') \n" +
            "\t\tfrom sisecofi.sscft_referencia_pago srp1 \n" +
            "\t\tjoin sisecofi.sscfc_desgloce sd2 on sd2.id_desgloce = srp1.id_desgloce\n" +
            "\t\twhere srp1.id_factura = sf.id_factura and lower(sd2.nombre) = 'sat'\n" +
            "\t) fecha_pago,\n" +
            "\tstm_factura.nombre moneda,\n" +
            "\tcoalesce(\n" +
            "\t\tcase\n" +
            "\t\t\twhen lower(stm_contrato.nombre) <> 'mxn' then (\n" +
            "\t\t\t\tcase\n" +
            "\t\t\t\t\twhen lower(sed.nombre) = 'pagado' then (\n" +
            "\t\t\t\t\t\tselect distinct srp1.tipo_cambio_pagado\n" +
            "\t\t\t\t\t\tfrom sisecofi.sscft_referencia_pago srp1\n" +
            "\t\t\t\t\t\twhere srp1.id_factura = sf.id_factura\n" +
            "\t\t\t\t\t)\n" +
            "\t\t\t\t\telse sd.tipo_cambio_referencial\n" +
            "\t\t\t\tend\n" +
            "\t\t\t)\n" +
            "\t\t\telse null\n" +
            "\t\tend\n" +
            "\t,0) tipo_cambio";

    String CAMPOS_DETALLE_FACTURAS_SAT_01 = ",\n" +
            "\tcoalesce(case when lower(sef.nombre) in ('pagado') then sf.monto_sat else null end, 0) monto_dolares_sat";

    String CAMPOS_DETALLE_FACTURAS_SAT_02 = ",\n" +
            "\tcoalesce(case when lower(sef.nombre) in ('pagado') then sf.monto_pesos_sat else null end, 0) monto_pesos_sat";

    String CAMPOS_DETALLE_FACTURAS_SAT_03 = ",\n" +
            "\tcoalesce(case when lower(sef.nombre) in ('pagado') then ((sf.otros_impuestos * cast(sf.porcentaje_sat as decimal(5, 2))) / 100) else null end, 0) otros_impuestos_sat";

    String CAMPOS_DETALLE_FACTURAS_SAT_04 = ",\n" +
            "\t(\n" +
            "\t\tselect distinct srp2.folio_ficha_pago\n" +
            "\t\tfrom sisecofi.sscft_referencia_pago srp2\n" +
            "\t\tjoin sisecofi.sscfc_desgloce sd2 on sd2.id_desgloce = srp2.id_desgloce\n" +
            "\t\twhere lower(sd2.nombre) = 'sat' and srp2.id_factura = sf.id_factura\n" +
            "\t) folio_ficha_pago_sat";

    String CAMPOS_DETALLE_FACTURAS_CC_01 = ",\n" +
            "\tcoalesce(case when lower(sef.nombre) in ('pagado') then sf.montocc else null end, 0) monto_dolares_cc";

    String CAMPOS_DETALLE_FACTURAS_CC_02 = ",\n" +
            "\tcoalesce(case when lower(sef.nombre) in ('pagado') then sf.monto_pesoscc else null end, 0) monto_pesos_cc";

    String CAMPOS_DETALLE_FACTURAS_CC_03 = ",\n" +
            "\tcoalesce(case when lower(sef.nombre) in ('pagado') then ((sf.otros_impuestos * cast(sf.porcentajecc as decimal(5, 2))) / 100) else null end, 0) otros_impuestos_cc";

    String CAMPOS_DETALLE_FACTURAS_CC_04 = ",\n" +
            "\t(\n" +
            "\t\tselect distinct srp3.folio_ficha_pago \n" +
            "\t\tfrom sisecofi.sscft_referencia_pago srp3 \n" +
            "\t\tjoin sisecofi.sscfc_desgloce sd2 on sd2.id_desgloce = srp3.id_desgloce \n" +
            "\t\twhere lower(sd2.nombre) = 'cc' and srp3.id_factura = sf.id_factura\n" +
            "\t) folio_ficha_pago_cc";

    String CAMPOS_DETALLE_FACTURA_COMENTARIOS = ",\n" +
            "\tsf.comentarios comentarios";

    String CAMPOS_DETALLE_DEDUCCIONES_SAT_01 = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(\n" +
            "\t\t\tselect sum(sdd.monto)\n" +
            "\t\t\tfrom sisecofi.sscft_deducciones_dictamenes sdd\n" +
            "\t\t\tleft join sisecofi.sscfc_desgloce sd2 on sd2.id_desgloce = sdd.id_desgloce\n" +
            "\t\t\twhere lower(sd2.nombre) = 'sat' and sdd.id_dictamen = sd.id_dictamen\n" +
            "\t\t)\n" +
            "\t, 0) deduccion_monto_dolares_sat";

    String CAMPOS_DETALLE_DEDUCCIONES_SAT_02 = ",\n" +
            "\tcoalesce (\n" +
            "\t\tcase \n" +
            "\t\t\twhen sd.tipo_cambio_referencial = 0 or sd.tipo_cambio_referencial is null then  (\n" +
            "\t\t\t\tselect sum(sdd.monto)\n" +
            "\t\t\t\tfrom sisecofi.sscft_deducciones_dictamenes sdd\n" +
            "\t\t\t\tjoin sisecofi.sscfc_desgloce sd2 on sd2.id_desgloce = sdd.id_desgloce\n" +
            "\t\t\t\twhere lower(sd2.nombre) = 'sat' and sdd.id_dictamen = sd.id_dictamen \n" +
            "\t\t\t)\n" +
            "\t\t\telse (\n" +
            "\t\t\t\tselect sum(sdd.monto * sd.tipo_cambio_referencial)\n" +
            "\t\t\t\tfrom sisecofi.sscft_deducciones_dictamenes sdd\n" +
            "\t\t\t\tjoin sisecofi.sscfc_desgloce sd2 on sd2.id_desgloce = sdd.id_desgloce\n" +
            "\t\t\t\twhere lower(sd2.nombre) = 'sat' and sdd.id_dictamen = sd.id_dictamen\n" +
            "\t\t\t)\n" +
            "\t\tend\n" +
            "\t, 0) deduccion_monto_pesos_sat";

    String CAMPOS_DETALLE_DEDUCCIONES_CC_01 = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(\n" +
            "\t\t\tselect sum(sdd.monto)\n" +
            "\t\t\tfrom sisecofi.sscft_deducciones_dictamenes sdd\n" +
            "\t\t\tleft join sisecofi.sscfc_desgloce sd2 on sd2.id_desgloce = sdd.id_desgloce\n" +
            "\t\t\twhere lower(sd2.nombre) = 'cc' and sdd.id_dictamen = sd.id_dictamen\n" +
            "\t\t)\n" +
            "\t, 0) deduccion_monto_dolares_cc";

    String CAMPOS_DETALLE_DEDUCCIONES_CC_02 = ",\n" +
            "\tcoalesce (\n" +
            "\t\tcase \n" +
            "\t\t\twhen sd.tipo_cambio_referencial = 0 or sd.tipo_cambio_referencial is null then  (\n" +
            "\t\t\t\tselect sum(sdd.monto)\n" +
            "\t\t\t\tfrom sisecofi.sscft_deducciones_dictamenes sdd\n" +
            "\t\t\t\tjoin sisecofi.sscfc_desgloce sd2 on sd2.id_desgloce = sdd.id_desgloce\n" +
            "\t\t\t\twhere lower(sd2.nombre) = 'cc' and sdd.id_dictamen = sd.id_dictamen \n" +
            "\t\t\t)\n" +
            "\t\t\telse (\n" +
            "\t\t\t\tselect sum(sdd.monto * sd.tipo_cambio_referencial)\n" +
            "\t\t\t\tfrom sisecofi.sscft_deducciones_dictamenes sdd\n" +
            "\t\t\t\tjoin sisecofi.sscfc_desgloce sd2 on sd2.id_desgloce = sdd.id_desgloce\n" +
            "\t\t\t\twhere lower(sd2.nombre) = 'cc' and sdd.id_dictamen = sd.id_dictamen\n" +
            "\t\t\t)\n" +
            "\t\tend\n" +
            "\t, 0) deduccion_monto_pesos_cc";

    String CAMPOS_DETALLE_PENALIZACIONES_SAT_01 = ",\n" +
            "\tcoalesce(\n" +
            "\t\t(\n" +
            "\t\t\tselect \n" +
            "\t\t\t(\n" +
            "\t\t\t\tcoalesce (\n" +
            "\t\t\t\t\t(\n" +
            "\t\t\t\t\t\tselect\n" +
            "\t\t\t\t\t\t\tsum(spcd.monto)\n" +
            "\t\t\t\t\t\tfrom\n" +
            "\t\t\t\t\t\t\tsisecofi.sscft_penas_contractuales_dictamenes spcd\n" +
            "\t\t\t\t\t\tjoin sisecofi.sscfc_desgloce sd2 on\n" +
            "\t\t\t\t\t\t\tsd2.id_desgloce = spcd.id_desgloce \n" +
            "\t\t\t\t\t\twhere lower(sd2.nombre) = 'sat' and spcd.id_dictamen = sd.id_dictamen\n" +
            "\t\t\t\t\t), 0\n" +
            "\t\t\t\t)\n" +
            "\t\t\t)\n" +
            "\t\t\t+\n" +
            "\t\t\t(\n" +
            "\t\t\t\tcoalesce (\n" +
            "\t\t\t\t\t(\n" +
            "\t\t\t\t\t\tselect\n" +
            "\t\t\t\t\t\t\tsum(spcd2.monto)\n" +
            "\t\t\t\t\t\tfrom\n" +
            "\t\t\t\t\t\t\tsisecofi.sscft_penas_convencionales_dictamenes spcd2 \n" +
            "\t\t\t\t\t\tjoin sisecofi.sscfc_desgloce sd2 on\n" +
            "\t\t\t\t\t\t\tsd2.id_desgloce = spcd2.id_desgloce \n" +
            "\t\t\t\t\t\twhere lower(sd2.nombre) = 'sat' and spcd2.id_dictamen = sd.id_dictamen\n" +
            "\t\t\t\t\t), 0\n" +
            "\t\t\t\t)\n" +
            "\t\t\t)\n" +
            "\t\t)\n" +
            "\t, 0) penalizacion_monto_dolares_sat";

    String CAMPOS_DETALLE_PENALIZACIONES_SAT_02 = ",\n" +
            "\tcoalesce(\n" +
            "\t\t\tcase \n" +
            "\t\t\t\twhen sd.tipo_cambio_referencial = 0 or sd.tipo_cambio_referencial is null then \n" +
            "\t\t\t\t\t(\n" +
            "\t\t\t\t\t\tselect \n" +
            "\t\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\t\tcoalesce (\n" +
            "\t\t\t\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\t\t\t\tselect sum(spcd.monto)\n" +
            "\t\t\t\t\t\t\t\t\t\tfrom sisecofi.sscft_penas_contractuales_dictamenes spcd\n" +
            "\t\t\t\t\t\t\t\t\t\tjoin sisecofi.sscfc_desgloce sd2 on sd2.id_desgloce = spcd.id_desgloce \n" +
            "\t\t\t\t\t\t\t\t\t\twhere lower(sd2.nombre) = 'sat' and spcd.id_dictamen = sd.id_dictamen\n" +
            "\t\t\t\t\t\t\t\t\t), 0\n" +
            "\t\t\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t\t+\n" +
            "\t\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\t\tcoalesce (\n" +
            "\t\t\t\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\t\t\t\tselect sum(spcd2.monto)\n" +
            "\t\t\t\t\t\t\t\t\t\tfrom sisecofi.sscft_penas_convencionales_dictamenes spcd2 \n" +
            "\t\t\t\t\t\t\t\t\t\tjoin sisecofi.sscfc_desgloce sd2 on sd2.id_desgloce = spcd2.id_desgloce \n" +
            "\t\t\t\t\t\t\t\t\t\twhere lower(sd2.nombre) = 'sat' and spcd2.id_dictamen = sd.id_dictamen\n" +
            "\t\t\t\t\t\t\t\t\t), 0\n" +
            "\t\t\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t)\n" +
            "\t\t\telse \n" +
            "\t\t\t\t(\n" +
            "\t\t\t\t\tselect \n" +
            "\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\tcoalesce (\n" +
            "\t\t\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\t\t\tselect sum(spcd.monto  * sd.tipo_cambio_referencial)\n" +
            "\t\t\t\t\t\t\t\t\tfrom sisecofi.sscft_penas_contractuales_dictamenes spcd\n" +
            "\t\t\t\t\t\t\t\t\tjoin sisecofi.sscfc_desgloce sd2 on sd2.id_desgloce = spcd.id_desgloce \n" +
            "\t\t\t\t\t\t\t\t\twhere lower(sd2.nombre) = 'sat' and spcd.id_dictamen = sd.id_dictamen\n" +
            "\t\t\t\t\t\t\t\t), 0\n" +
            "\t\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t+\n" +
            "\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\tcoalesce (\n" +
            "\t\t\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\t\t\tselect sum(spcd2.monto  * sd.tipo_cambio_referencial)\n" +
            "\t\t\t\t\t\t\t\t\tfrom sisecofi.sscft_penas_convencionales_dictamenes spcd2 \n" +
            "\t\t\t\t\t\t\t\t\tjoin sisecofi.sscfc_desgloce sd2 on sd2.id_desgloce = spcd2.id_desgloce \n" +
            "\t\t\t\t\t\t\t\t\twhere lower(sd2.nombre) = 'sat' and spcd2.id_dictamen = sd.id_dictamen\n" +
            "\t\t\t\t\t\t\t\t), 0\n" +
            "\t\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t)\n" +
            "\t\t\t\t)\n" +
            "\t\t\tend\n" +
            "\t, 0) penalizacion_monto_pesos_sat";

    String CAMPOS_DETALLE_PENALIZACIONES_CC_01 = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(\n" +
            "\t\t\tselect \n" +
            "\t\t\t\t(\n" +
            "\t\t\t\t\tcoalesce (\n" +
            "\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\tselect\n" +
            "\t\t\t\t\t\t\t\tsum(spcd.monto)\n" +
            "\t\t\t\t\t\t\tfrom\n" +
            "\t\t\t\t\t\t\t\tsisecofi.sscft_penas_contractuales_dictamenes spcd\n" +
            "\t\t\t\t\t\t\tjoin sisecofi.sscfc_desgloce sd2 on\n" +
            "\t\t\t\t\t\t\t\tsd2.id_desgloce = spcd.id_desgloce \n" +
            "\t\t\t\t\t\t\twhere lower(sd2.nombre) = 'cc' and spcd.id_dictamen = sd.id_dictamen\n" +
            "\t\t\t\t\t\t), 0\n" +
            "\t\t\t\t\t)\n" +
            "\t\t\t\t)\n" +
            "\t\t\t\t+\n" +
            "\t\t\t\t(\n" +
            "\t\t\t\t\tcoalesce (\n" +
            "\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\tselect\n" +
            "\t\t\t\t\t\t\t\tsum(spcd2.monto)\n" +
            "\t\t\t\t\t\t\tfrom\n" +
            "\t\t\t\t\t\t\t\tsisecofi.sscft_penas_convencionales_dictamenes spcd2 \n" +
            "\t\t\t\t\t\t\tjoin sisecofi.sscfc_desgloce sd2 on\n" +
            "\t\t\t\t\t\t\t\tsd2.id_desgloce = spcd2.id_desgloce \n" +
            "\t\t\t\t\t\t\twhere lower(sd2.nombre) = 'cc' and spcd2.id_dictamen = sd.id_dictamen\n" +
            "\t\t\t\t\t\t), 0\n" +
            "\t\t\t\t\t)\n" +
            "\t\t\t\t)\n" +
            "\t\t)\n" +
            "\t, 0) penalizacion_monto_dolares_cc";

    String CAMPOS_DETALLE_PENALIZACIONES_CC_02 = ",\n" +
            "\tcoalesce(\n" +
            "\t\t\tcase \n" +
            "\t\t\t\twhen sd.tipo_cambio_referencial = 0 or sd.tipo_cambio_referencial is null then \n" +
            "\t\t\t\t\t(\n" +
            "\t\t\t\t\t\tselect \n" +
            "\t\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\t\tcoalesce (\n" +
            "\t\t\t\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\t\t\t\tselect sum(spcd.monto)\n" +
            "\t\t\t\t\t\t\t\t\t\tfrom sisecofi.sscft_penas_contractuales_dictamenes spcd\n" +
            "\t\t\t\t\t\t\t\t\t\tjoin sisecofi.sscfc_desgloce sd2 on sd2.id_desgloce = spcd.id_desgloce \n" +
            "\t\t\t\t\t\t\t\t\t\twhere lower(sd2.nombre) = 'cc' and spcd.id_dictamen = sd.id_dictamen\n" +
            "\t\t\t\t\t\t\t\t\t), 0\n" +
            "\t\t\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t\t+\n" +
            "\t\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\t\tcoalesce (\n" +
            "\t\t\t\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\t\t\t\tselect sum(spcd2.monto)\n" +
            "\t\t\t\t\t\t\t\t\t\tfrom sisecofi.sscft_penas_convencionales_dictamenes spcd2 \n" +
            "\t\t\t\t\t\t\t\t\t\tjoin sisecofi.sscfc_desgloce sd2 on sd2.id_desgloce = spcd2.id_desgloce \n" +
            "\t\t\t\t\t\t\t\t\t\twhere lower(sd2.nombre) = 'cc' and spcd2.id_dictamen = sd.id_dictamen\n" +
            "\t\t\t\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t\t\t\t, 0\n" +
            "\t\t\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t)\n" +
            "\t\t\telse \n" +
            "\t\t\t\t(\n" +
            "\t\t\t\t\tselect \n" +
            "\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\tcoalesce (\n" +
            "\t\t\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\t\t\tselect sum(spcd.monto  * sd.tipo_cambio_referencial)\n" +
            "\t\t\t\t\t\t\t\t\tfrom sisecofi.sscft_penas_contractuales_dictamenes spcd\n" +
            "\t\t\t\t\t\t\t\t\tjoin sisecofi.sscfc_desgloce sd2 on sd2.id_desgloce = spcd.id_desgloce \n" +
            "\t\t\t\t\t\t\t\t\twhere lower(sd2.nombre) = 'cc' and spcd.id_dictamen = sd.id_dictamen\n" +
            "\t\t\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t\t\t,0\n" +
            "\t\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t+\n" +
            "\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\tcoalesce (\n" +
            "\t\t\t\t\t\t\t\t(\n" +
            "\t\t\t\t\t\t\t\t\tselect sum(spcd2.monto  * sd.tipo_cambio_referencial)\n" +
            "\t\t\t\t\t\t\t\t\tfrom sisecofi.sscft_penas_convencionales_dictamenes spcd2 \n" +
            "\t\t\t\t\t\t\t\t\tjoin sisecofi.sscfc_desgloce sd2 on sd2.id_desgloce = spcd2.id_desgloce \n" +
            "\t\t\t\t\t\t\t\t\twhere lower(sd2.nombre) = 'cc' and spcd2.id_dictamen = sd.id_dictamen\n" +
            "\t\t\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t\t\t,0\n" +
            "\t\t\t\t\t\t\t)\n" +
            "\t\t\t\t\t\t)\n" +
            "\t\t\t\t)\n" +
            "\t\t\tend\n" +
            "\t, 0) penalizacion_monto_pesos_cc";

    String CAMPOS_DETALLE_REINTEGROS = ",\n" +
            "\tstr.nombre as tipo_reintegro,\n" +
            "\tcoalesce(sra.importe, 0) importe_reintegro,\n" +
            "\tcoalesce(sra.interes, 0) interes_reintegro,\n" +
            "\tcoalesce((case when lower(str.nombre) = 'servicio sat' then sra.total else 0 end), 0) total_sat,\n" +
            "\tcoalesce((case when lower(str.nombre) = 'servicio cc' then sra.total else 0 end), 0) total_cc,\n" +
            "\tto_char(sra.fecha_reintegro, 'dd/MM/YYYY') fecha_reintegro";

    String JOINS_BASE = "\tsisecofi.sscft_proyecto sp\n" +
            "left join sisecofi.sscft_contrato sc on sc.id_proyecto = sp.id_proyecto\n" +
            "left join sisecofi.sscft_datos_generales_contrato sdgc on sdgc.id_contrato = sc.id_contrato\n" +
            "left join sisecofi.sscft_vigencia_montos svm on svm.id_contrato = sc.id_contrato\n" +
            "left join sisecofi.sscfc_tipo_moneda stm_contrato on stm_contrato.id_tipo_moneda = svm.id_tipo_moneda\n" +
            "left join (\n" +
            "    select id_contrato, max(fecha_fin) as fecha_fin_max\n" +
            "    from sisecofi.sscft_convenio_modificatorio\n" +
            "    where estatus = true\n" +
            "    group by id_contrato\n" +
            ") cm on cm.id_contrato = svm.id_contrato\n";

    String JOINS_DICTAMENES = "\n" +
            "left join sisecofi.sscft_dictamen sd on sd.id_contrato = sc.id_contrato\n" +
            "left join sisecofi.sscfc_estatus_dictamen sed on sed.id_estatus_dictamen = sd.id_estatus_dictamen\n" +
            "left join sisecofi.sscfc_periodo_control_mes spcm on sd.id_periodo_control_mes = spcm.id_periodo_control_mes\n" +
            "left join sisecofi.sscfc_periodo_control_anio spca on sd.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "left join sisecofi.sscft_facturas sf on sf.id_dictamen = sd.id_dictamen\n" +
            "left join sisecofi.sscfc_estatus_factura sef on sef.id_estatus_factura = sf.id_estatus_factura\n" +
            "left join sisecofi.sscfc_tipo_moneda stm_factura on sf.id_tipo_moneda = stm_factura.id_tipo_moneda";

    String JOINS_REINTEGROS = "\n" +
            "left join sisecofi.sscft_reintegros_asociados sra on sra.id_contrato = sc.id_contrato\n" +
            "left join sisecofi.sscfc_tipo_reintegro str on str.id_tipo_reintegro = sra.id_tipo_reintegro";

    String CONDICION_DICTAMENES = "\n" +
            "\tand lower(sed.nombre) not in('inicial', 'cancelado') and sd.id_dictamen is not null";

    String CONDICION_REINTEGROS = "\n" +
            "\tand lower(str.nombre) in ('servicio sat', 'servicio cc')";
    
    String ORDER_BY_PERIODO_CONTROL = "\n" +
    		"\n) as sub" +
    	    "\norder by" +
    	    "\n\tcast(right(periodo_control, 4) as int)," +
    	    "\n\tcase" +
    	    "\n\t\twhen left(periodo_control, position(' ' in periodo_control)-1) = 'Enero' then 1" +
    	    "\n\t\twhen left(periodo_control, position(' ' in periodo_control)-1) = 'Febrero' then 2" +
    	    "\n\t\twhen left(periodo_control, position(' ' in periodo_control)-1) = 'Marzo' then 3" +
    	    "\n\t\twhen left(periodo_control, position(' ' in periodo_control)-1) = 'Abril' then 4" +
    	    "\n\t\twhen left(periodo_control, position(' ' in periodo_control)-1) = 'Mayo' then 5" +
    	    "\n\t\twhen left(periodo_control, position(' ' in periodo_control)-1) = 'Junio' then 6" +
    	    "\n\t\twhen left(periodo_control, position(' ' in periodo_control)-1) = 'Julio' then 7" +
    	    "\n\t\twhen left(periodo_control, position(' ' in periodo_control)-1) = 'Agosto' then 8" +
    	    "\n\t\twhen left(periodo_control, position(' ' in periodo_control)-1) = 'Septiembre' then 9" +
    	    "\n\t\twhen left(periodo_control, position(' ' in periodo_control)-1) = 'Octubre' then 10" +
    	    "\n\t\twhen left(periodo_control, position(' ' in periodo_control)-1) = 'Noviembre' then 11" +
    	    "\n\t\twhen left(periodo_control, position(' ' in periodo_control)-1) = 'Diciembre' then 12" +
    	    "\n\tend";

    String[] ETIQUETAS_DETALLE_GENERAL = new String[]{
            "Nombre corto del proyecto",
            "Nombre corto del contrato",
            "Número del contrato",
            "Proveedor"
    };

    String[] ETIQUETAS_DETALLE_OPCIONAL = new String[]{
            "Periodo de inicio",
            "Periodo de fin",
            "Periodo de control",
            "Descripcion"
    };

    String[] ETIQUETAS_DETALLE_FACTURAS = new String[]{
            "Folio factura",
            "Comprobante fiscal",
            "Fecha de facturación",
            "Fecha de pago",
            "Moneda",
            "Tipo de cambio",
            "Monto en dólares SAT",
            "Monto en dólares CC",
            "Monto en pesos SAT",
            "Monto en pesos CC",
            "Otros impuestos SAT",
            "Otros impuestos CC",
            "Folio de ficha de pago SAT",
            "Folio de ficha de pago CC",
            "Comentarios"
    };

    String[] ETIQUETAS_DETALLE_DEDUCCIONES = new String[]{
            "Monto en dólares SAT",
            "Monto en dólares CC",
            "Monto en pesos SAT",
            "Monto en pesos CC"
    };

    String[] ETIQUETAS_DETALLE_PENALIZACIONES = new String[]{
            "Monto en dólares SAT",
            "Monto en dólares CC",
            "Monto en pesos SAT",
            "Monto en pesos CC"
    };

    String[] ETIQUETAS_DETALLE_REINTEGROS = new String[]{
            "Tipo",
            "Importe",
            "Interés",
            "Total SAT",
            "Total CC",
            "Fecha de reintegro"
    };

}
