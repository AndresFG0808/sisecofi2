package com.sisecofi.reportedocumental.repository.financiero.sql;

@SuppressWarnings("all")
public interface ReporteSeguimientoDictamenSQL {
    String[] ETIQUETAS = new String[]{
            "Nombre corto del proyecto",
            "Nombre corto del contrato",
            "Verificador",
            "Periodo de control",
    };

    String[] ETIQUETAS_ESTATUS = new String[]{
            "Importe de factura sin impuesto",
            "Neto a pagar en dólares",
            "Neto a pagar en pesos"
    };

    String SELECT = "select ";
    
    String SELECT_ALL_FROM = "select * \n" +
    		"from ( \n";

    String SELECT_COUNT = "select count(*) ";

    String FROM = "\nfrom\n";

    String WHERE = "\nwhere \n\t1 = 1 ";

    String ORDER_BY = "\n" +
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

    String PAGINACION = "\n\toffset :pagina rows fetch first :registros rows only";

    String CAMPOS_BASE = "\n" +
            "\tdistinct \n" +
            "    sp.nombre_corto as nombre_proyecto,\n" +
            "    sc.nombre_corto as nombre_contrato,\n" +
            "    verificador.verificador,\n" +
            "    (spcm1.nombre || ' ' || spca1.nombre) as periodo_control";

    String CAMPOS_INICIAL = ",\n" +
            "\tcoalesce (\n" +
            "        (\n" +
            "        \tselect sum(src.sub_total)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sd2.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_resumen_consolidado src on src.id_dictamen = sd2.id_dictamen \n" +
            "\t\t\tleft join sisecofi.sscfc_fase_dictamen sfd on sfd.id_fase_dictamen = src.id_fase_dictamen \n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato  \n" +
            "\t\t\tand lower(sfd.nombre) = 'dictaminado' \n" +
            "\t\t\tand lower(sed.nombre) = 'inicial'\n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "    ,0) as inicial_importe_factura_sin_impuestos,\n" +
            "\tcoalesce (\n" +
            "    \tcase \n" +
            "    \t\twhen lower(stm.nombre) = 'mxn' then 0\n" +
            "    \t\telse (\n" +
            "    \t\t\tselect sum(src.total) \n" +
            "\t\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sd2.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\t\tleft join sisecofi.sscft_resumen_consolidado src on src.id_dictamen = sd2.id_dictamen \n" +
            "\t\t\t\tleft join sisecofi.sscfc_fase_dictamen sfd on sfd.id_fase_dictamen = src.id_fase_dictamen \n" +
            "\t\t\t\twhere sd2.id_contrato = sc.id_contrato  \n" +
            "\t\t\t\tand lower(sfd.nombre) = 'dictaminado' \n" +
            "\t\t\t\tand lower(sed.nombre) = 'inicial'\n" +
            "\t\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t\t)\n" +
            "\t\tend\n" +
            "    ,0) as inicial_neto_pagar_usd,\n" +
            "    coalesce (\n" +
            "    \t(\n" +
            "        \tselect sum(src.total_pesos)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sd2.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_resumen_consolidado src on src.id_dictamen = sd2.id_dictamen \n" +
            "\t\t\tleft join sisecofi.sscfc_fase_dictamen sfd on sfd.id_fase_dictamen = src.id_fase_dictamen \n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato  \n" +
            "\t\t\tand lower(sfd.nombre) = 'dictaminado' \n" +
            "\t\t\tand lower(sed.nombre) = 'inicial'\n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "\t,0) as inicial_neto_pagar_pesos";

    String CAMPOS_DICTAMINADO = ",\n" +
            "\tcoalesce (\n" +
            "        (\n" +
            "        \tselect sum(src.sub_total)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sd2.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_resumen_consolidado src on src.id_dictamen = sd2.id_dictamen \n" +
            "\t\t\tleft join sisecofi.sscfc_fase_dictamen sfd on sfd.id_fase_dictamen = src.id_fase_dictamen \n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato  \n" +
            "\t\t\tand lower(sfd.nombre) = 'dictaminado' \n" +
            "\t\t\tand lower(sed.nombre) = 'dictaminado'\n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "    ,0) as dictaminado_importe_factura_sin_impuestos,\n" +
            "\tcoalesce (\n" +
            "    \tcase \n" +
            "    \t\twhen lower(stm.nombre) = 'mxn' then 0\n" +
            "    \t\telse (\n" +
            "    \t\t\tselect sum(src.total) \n" +
            "\t\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sd2.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\t\tleft join sisecofi.sscft_resumen_consolidado src on src.id_dictamen = sd2.id_dictamen \n" +
            "\t\t\t\tleft join sisecofi.sscfc_fase_dictamen sfd on sfd.id_fase_dictamen = src.id_fase_dictamen \n" +
            "\t\t\t\twhere sd2.id_contrato = sc.id_contrato  \n" +
            "\t\t\t\tand lower(sfd.nombre) = 'dictaminado' \n" +
            "\t\t\t\tand lower(sed.nombre) = 'dictaminado'\n" +
            "\t\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t\t)\n" +
            "\t\tend\n" +
            "    ,0) as dictaminado_neto_pagar_usd,\n" +
            "    coalesce (\n" +
            "    \t(\n" +
            "        \tselect sum(src.total_pesos)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sd2.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_resumen_consolidado src on src.id_dictamen = sd2.id_dictamen \n" +
            "\t\t\tleft join sisecofi.sscfc_fase_dictamen sfd on sfd.id_fase_dictamen = src.id_fase_dictamen \n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato  \n" +
            "\t\t\tand lower(sfd.nombre) = 'dictaminado' \n" +
            "\t\t\tand lower(sed.nombre) = 'dictaminado'\n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "\t,0) as dictaminado_neto_pagar_pesos";

    String CAMPOS_PROFORMA = ",\n" +
            "\tcoalesce (\n" +
            "        (\n" +
            "        \tselect sum(src.sub_total)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sd2.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_resumen_consolidado src on src.id_dictamen = sd2.id_dictamen \n" +
            "\t\t\tleft join sisecofi.sscfc_fase_dictamen sfd on sfd.id_fase_dictamen = src.id_fase_dictamen \n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato  \n" +
            "\t\t\tand lower(sfd.nombre) = 'dictaminado' \n" +
            "\t\t\tand lower(sed.nombre) = 'proforma'\n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "    ,0) as proforma_importe_factura_sin_impuestos,\n" +
            "\tcoalesce (\n" +
            "    \tcase \n" +
            "    \t\twhen lower(stm.nombre) = 'mxn' then 0\n" +
            "    \t\telse (\n" +
            "    \t\t\tselect sum(src.total) \n" +
            "\t\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sd2.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\t\tleft join sisecofi.sscft_resumen_consolidado src on src.id_dictamen = sd2.id_dictamen \n" +
            "\t\t\t\tleft join sisecofi.sscfc_fase_dictamen sfd on sfd.id_fase_dictamen = src.id_fase_dictamen \n" +
            "\t\t\t\twhere sd2.id_contrato = sc.id_contrato  \n" +
            "\t\t\t\tand lower(sfd.nombre) = 'dictaminado' \n" +
            "\t\t\t\tand lower(sed.nombre) = 'proforma'\n" +
            "\t\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t\t)\n" +
            "\t\tend\n" +
            "    ,0) as proforma_neto_pagar_usd,\n" +
            "    coalesce (\n" +
            "    \t(\n" +
            "        \tselect sum(src.total_pesos)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sd2.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_resumen_consolidado src on src.id_dictamen = sd2.id_dictamen \n" +
            "\t\t\tleft join sisecofi.sscfc_fase_dictamen sfd on sfd.id_fase_dictamen = src.id_fase_dictamen \n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato  \n" +
            "\t\t\tand lower(sfd.nombre) = 'dictaminado' \n" +
            "\t\t\tand lower(sed.nombre) = 'proforma'\n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "\t,0) as proforma_neto_pagar_pesos";

    String CAMPOS_FACTURADO = ",\n" +
            "\tcoalesce (\n" +
            "        (\n" +
            "        \tselect sum(src.sub_total)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sd2.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_resumen_consolidado src on src.id_dictamen = sd2.id_dictamen \n" +
            "\t\t\tleft join sisecofi.sscfc_fase_dictamen sfd on sfd.id_fase_dictamen = src.id_fase_dictamen \n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato  \n" +
            "\t\t\tand lower(sfd.nombre) = 'facturado' \n" +
            "\t\t\tand lower(sed.nombre) = 'facturado'\n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "    ,0) as facturado_importe_factura_sin_impuestos,\n" +
            "\tcoalesce (\n" +
            "    \tcase \n" +
            "    \t\twhen lower(stm.nombre) = 'mxn' then 0\n" +
            "    \t\telse (\t\t\t\t\t\n" +
            "    \t\t\tselect sum(src.total)\n" +
            "\t\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sd2.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\t\tleft join sisecofi.sscft_resumen_consolidado src on src.id_dictamen = sd2.id_dictamen \n" +
            "\t\t\t\tleft join sisecofi.sscfc_fase_dictamen sfd on sfd.id_fase_dictamen = src.id_fase_dictamen \n" +
            "\t\t\t\twhere sd2.id_contrato = sc.id_contrato \n" +
            "\t\t\t\tand lower(sfd.nombre) = 'facturado' \n" +
            "\t\t\t\tand lower(sed.nombre) = 'facturado'\n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t\t)\n" +
            "\t\tend\n" +
            "    ,0) as facturado_neto_pagar_usd,\n" +
            "    coalesce (\n" +
            "    \t(\n" +
            "        \tselect sum(src.total_pesos)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sd2.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_resumen_consolidado src on src.id_dictamen = sd2.id_dictamen \n" +
            "\t\t\tleft join sisecofi.sscfc_fase_dictamen sfd on sfd.id_fase_dictamen = src.id_fase_dictamen \n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato \n" +
            "\t\t\tand lower(sfd.nombre) = 'facturado' \n" +
            "\t\t\tand lower(sed.nombre) = 'facturado'\n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "\t,0) as facturado_neto_pagar_pesos";

    String CAMPOS_SOLICITUD_PAGO = ",\n" +
            "\tcoalesce (\n" +
            "        (\n" +
            "        \tselect sum(src.sub_total)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sd2.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_resumen_consolidado src on src.id_dictamen = sd2.id_dictamen \n" +
            "\t\t\tleft join sisecofi.sscfc_fase_dictamen sfd on sfd.id_fase_dictamen = src.id_fase_dictamen \n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato\n" +
            "\t\t\tand lower(sfd.nombre) = 'facturado' \n" +
            "\t\t\tand lower(sed.nombre) = 'solicitud de pago'\n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "    ,0) as solicitud_pago_importe_factura_sin_impuestos,\n" +
            "\tcoalesce (\n" +
            "    \tcase \n" +
            "    \t\twhen lower(stm.nombre) = 'mxn' then 0\n" +
            "    \t\telse (\n" +
            "    \t\t\tselect sum(src.total) \n" +
            "\t\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sd2.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\t\tleft join sisecofi.sscft_resumen_consolidado src on src.id_dictamen = sd2.id_dictamen \n" +
            "\t\t\t\tleft join sisecofi.sscfc_fase_dictamen sfd on sfd.id_fase_dictamen = src.id_fase_dictamen \n" +
            "\t\t\t\twhere sd2.id_contrato = sc.id_contrato \n" +
            "\t\t\t\tand lower(sfd.nombre) = 'facturado' \n" +
            "\t\t\t\tand lower(sed.nombre) = 'solicitud de pago'\n" +
            "\t\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t\t)\n" +
            "\t\tend\n" +
            "    ,0) as solicitud_pago_neto_pagar_usd,\n" +
            "    coalesce (\n" +
            "    \t(\n" +
            "        \tselect sum(src.total_pesos)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sd2.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_resumen_consolidado src on src.id_dictamen = sd2.id_dictamen \n" +
            "\t\t\tleft join sisecofi.sscfc_fase_dictamen sfd on sfd.id_fase_dictamen = src.id_fase_dictamen \n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato \n" +
            "\t\t\tand lower(sfd.nombre) = 'facturado' \n" +
            "\t\t\tand lower(sed.nombre) = 'solicitud de pago' \n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "\t,0) as solicitud_pago_neto_pagar_pesos";

    String CAMPOS_PAGADO = ",\n" +
            "\tcoalesce (\n" +
            "        (\n" +
            "        \tselect sum(sf.sub_total)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sd2.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_facturas sf on sd2.id_dictamen = sf.id_dictamen \n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_factura sef on sf.id_estatus_factura = sef.id_estatus_factura \n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato\n" +
            "\t\t\tand lower(sef.nombre) not in ('cancelado')\n" +
            "\t\t\tand lower(sed.nombre) = 'pagado' \n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "    ,0) as pagado_importe_factura_sin_impuestos,\n" +
            "\tcoalesce (\n" +
            "    \tcase \n" +
            "    \t\twhen lower(stm.nombre) = 'mxn' then 0\n" +
            "    \t\telse (\n" +
            "    \t\t\tselect sum(srp.pagado_nafin) \n" +
            "\t\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sd2.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\t\tleft join sisecofi.sscft_solicitud_pago ssp on ssp.id_dictamen = sd2.id_dictamen \n" +
            "\t\t\t\tleft join sisecofi.sscft_referencia_pago srp on srp.id_solicitud_pago = ssp.id_solicitud_pago \n" +
            "\t\t\t\tleft join sisecofi.sscfc_desgloce sd3 on srp.id_desgloce = sd3.id_desgloce \n" +
            "\t\t\t\twhere sd2.id_contrato = sc.id_contrato \n" +
            "\t\t\t\tand lower(sed.nombre) = 'pagado' \n" +
            "\t\t\t\tand lower(sd3.nombre) in ('sat', 'cc')\n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t\t)\n" +
            "\t\tend\n" +
            "    ,0) as pagado_neto_pagar_usd,\n" +
            "    coalesce (\n" +
            "    \t(\n" +
            "        \tselect sum(\n" +
            "\t\t\t\t\tcase \n" +
            "\t\t\t\t\t\twhen lower(stm.nombre) = 'usd' then (srp.pagado_nafin * svm.tipo_cambio_maximo)\n" +
            "\t\t\t\t\t\telse srp.pagado_nafin\n" +
            "\t\t\t\t\tend\n" +
            "\t\t\t\t)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sd2.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_solicitud_pago ssp on ssp.id_dictamen = sd2.id_dictamen \n" +
            "\t\t\tleft join sisecofi.sscft_referencia_pago srp on srp.id_solicitud_pago = ssp.id_solicitud_pago \n" +
            "\t\t\tleft join sisecofi.sscfc_desgloce sd3 on srp.id_desgloce = sd3.id_desgloce \n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato \n" +
            "\t\t\tand lower(sed.nombre) = 'pagado' \n" +
            "\t\t\tand lower(sd3.nombre) in ('sat', 'cc')\n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "\t,0) as pagado_neto_pagar_pesos";

    String CAMPOS_CANCELADO = ",\n" +
            "\tcoalesce (\n" +
            "        (\n" +
            "        \tselect sum(src.sub_total)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sd2.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_resumen_consolidado src on src.id_dictamen = sd2.id_dictamen \n" +
            "\t\t\tleft join sisecofi.sscfc_fase_dictamen sfd on sfd.id_fase_dictamen = src.id_fase_dictamen \n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato  \n" +
            "\t\t\tand lower(sfd.nombre) = 'dictaminado' \n" +
            "\t\t\tand lower(sed.nombre) = 'cancelado'\n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "    ,0) as cancelado_importe_factura_sin_impuestos,\n" +
            "\tcoalesce (\n" +
            "    \tcase \n" +
            "    \t\twhen lower(stm.nombre) = 'mxn' then 0\n" +
            "    \t\telse (\n" +
            "    \t\t\tselect sum(src.total) \n" +
            "\t\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sd2.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\t\tleft join sisecofi.sscft_resumen_consolidado src on src.id_dictamen = sd2.id_dictamen \n" +
            "\t\t\t\tleft join sisecofi.sscfc_fase_dictamen sfd on sfd.id_fase_dictamen = src.id_fase_dictamen \n" +
            "\t\t\t\twhere sd2.id_contrato = sc.id_contrato  \n" +
            "\t\t\t\tand lower(sfd.nombre) = 'dictaminado' \n" +
            "\t\t\t\tand lower(sed.nombre) = 'cancelado'\n" +
            "\t\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t\t)\n" +
            "\t\tend\n" +
            "    ,0) as cancelado_neto_pagar_usd,\n" +
            "    coalesce (\n" +
            "    \t(\n" +
            "        \tselect sum(src.total_pesos)\n" +
            "\t\t\tfrom sisecofi.sscft_dictamen sd2\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on sd2.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscft_resumen_consolidado src on src.id_dictamen = sd2.id_dictamen \n" +
            "\t\t\tleft join sisecofi.sscfc_fase_dictamen sfd on sfd.id_fase_dictamen = src.id_fase_dictamen \n" +
            "\t\t\twhere sd2.id_contrato = sc.id_contrato  \n" +
            "\t\t\tand lower(sfd.nombre) = 'dictaminado' \n" +
            "\t\t\tand lower(sed.nombre) = 'cancelado'\n" +
            "\t\t\tand sd2.id_periodo_control_mes = spcm1.id_periodo_control_mes and sd2.id_periodo_control_anio = spca1.id_periodo_control_anio\n" +
            "\t\t)\n" +
            "\t,0) as cancelado_neto_pagar_pesos";

    String JOINS = "\t sisecofi.sscft_contrato sc\n" +
            "left join sisecofi.sscft_proyecto sp ON sc.id_proyecto = sp.id_proyecto\n" +
            "left join (\n" +
            "    select spac.id_contrato, su.nombre AS verificador, spac.id_referencia   \n" +
            "    from sisecofi.sscft_participantes_administracion_contrato spac\n" +
            "    join sisecofi.sscft_usuario su ON spac.id_user = su.id_user\n" +
            "    join sisecofi.sscfc_responsabilidad sr ON spac.id_responsabilidad = sr.id_responsabilidad\n" +
            "    where lower(sr.nombre) = 'verificador del contrato'\n" +
            ") AS verificador on verificador.id_contrato = sc.id_contrato\n" +
            "left join sisecofi.sscft_vigencia_montos svm ON svm.id_contrato = sc.id_contrato\n" +
            "left join sisecofi.sscfc_tipo_moneda stm ON stm.id_tipo_moneda = svm.id_tipo_moneda\n" +
            "left join (\n" +
            "\tselect id_contrato, max(fecha_fin) as fecha_fin\n" +
            "\tfrom sisecofi.sscft_convenio_modificatorio\n" +
            "\tgroup by id_contrato\n" +
            ") scm on scm.id_contrato = sc.id_contrato\n" +
            "left join sisecofi.sscft_dictamen sd ON sd.id_contrato = sc.id_contrato\n" +
            "left join sisecofi.sscfc_periodo_control_mes spcm1 ON sd.id_periodo_control_mes = spcm1.id_periodo_control_mes\n" +
            "left join sisecofi.sscfc_periodo_control_anio spca1 ON sd.id_periodo_control_anio = spca1.id_periodo_control_anio";
            
}
