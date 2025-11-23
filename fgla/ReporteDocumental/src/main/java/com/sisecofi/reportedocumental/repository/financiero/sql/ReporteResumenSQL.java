package com.sisecofi.reportedocumental.repository.financiero.sql;

@SuppressWarnings("all")
public interface ReporteResumenSQL {
    String SELECT = "select";

    String FROM = "\nfrom\n";

    String WHERE = "\nwhere \n\t1 = 1";

    String PAGINACION = "\n\toffset :pagina rows fetch first :registros rows only";

    String ORDER_BY = "\torder by id_contrato desc ";

    String CAMAPOS_BASE = "\n" +
            "\tt_proyecto.nombre_corto nombre_corto_proyecto,\n" +
            "\tt_contrato.nombre_corto nombre_corto_contrato";

    String CAMPOS_DATOS_GENERALES = ",\n" +
            "\tt_proyecto.id_agp id_proyecto_agp,\n" +
            "\tc_ep.nombre estatus_proyecto,\n" +
            "\tt_dgp.id_contrato id_contrato,\n" +
            "\tt_dgp.numero_contrato numero_contrato,\n" +
            "\tt_contrato.nombre_contrato nombre_contrato,\n" +
            "\tt_dgp.objetivo_servicio objetivo_servicio,\n" +
            "\tt_dgp.alcance_servicio alcance_servicio,\n" +
            "\t(\n" +
            "\tcase\n" +
	"\t\twhen date(\n" +
	"\t\t\tcoalesce(\n" +
	"\t\t\t\t(select cm.fecha_fin\n" +
	"\t\t\t\t from sisecofi.sscft_convenio_modificatorio cm\n" +
	"\t\t\t\t where cm.id_contrato = t_contrato.id_contrato\n" +
	"\t\t\t\t   and cm.id_convenio_modificatorio = (\n" +
	"\t\t\t\t\t\tselect max(cm2.id_convenio_modificatorio)\n" +
	"\t\t\t\t\t\tfrom sisecofi.sscft_convenio_modificatorio cm2\n" +
	"\t\t\t\t\t\twhere cm2.id_contrato = t_contrato.id_contrato\n" +
	"\t\t\t\t   )\n" +
	"\t\t\t\t),\n" +
	"\t\t\t\tsvm.fecha_fin_vigencia_contrato\n" +
	"\t\t\t)\n" +
	"\t\t) >= date(now()) then 'Sí'\n" +
	"\t\telse 'No'\n" +
	"\tend\n" +
            "\t) contrato_vigente,\n" +
            "\tc_dt.nombre dominio,\n" +
            "\tc_tc.nombre tipo_contratacion,\n" +
            "\t(\n" +
            "\t\tselect\n" +
            "\t\t\tstring_agg(p2_0.nombre_proveedor, '; ')\n" +
            "\t\tfrom\n" +
            "\t\t\tsisecofi.sscfr_asociacion_contrato_proveedor a1_0\n" +
            "\t\tjoin sisecofi.sscft_proveedor p2_0 on\n" +
            "\t\t\tp2_0.id_proveedor = a1_0.id_proveedor,\n" +
            "\t\t\tsisecofi.sscft_contrato c5_0\n" +
            "\t\twhere\n" +
            "\t\t\tc5_0.id_contrato = t_dgp.id_contrato\n" +
            "\t\t\tand a1_0.id_contrato = t_dgp.id_contrato\n" +
            "\t) proveedor,\n" +
            "\tto_char(\n" +
            "\t\tsvm.fecha_inicio_vigencia_contrato, \n" +
            "\t\t'dd/MM/YYYY'\n" +
            "\t) fecha_inicio_contrato,\n" +
            "\tto_char(\n" +
            "\t\tsvm.fecha_fin_vigencia_contrato, \n" +
            "\t\t'dd/MM/YYYY'\n" +
            "\t) fecha_fin_contrato,\n" +
            "\tto_char(\n" +
            "\t\t(\n" +
            "\t\t\tselect\n" +
            "\t\t\t\tc6_0.fecha_fin\n" +
            "\t\t\tfrom\n" +
            "\t\t\t\tsisecofi.sscft_convenio_modificatorio c6_0\n" +
            "\t\t\twhere\n" +
            "\t\t\t\tc6_0.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\t\tand c6_0.id_convenio_modificatorio = (\n" +
            "\t\t\t\t\tselect\n" +
            "\t\t\t\t\t\tmax(c8_0.id_convenio_modificatorio)\n" +
            "\t\t\t\t\tfrom\n" +
            "\t\t\t\t\t\tsisecofi.sscft_convenio_modificatorio c8_0\n" +
            "\t\t\t\t\twhere\n" +
            "\t\t\t\t\t\tc8_0.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\t\t)\n" +
            "\t\t),\n" +
            "\t\t'dd/MM/YYYY'\n" +
            "\t) fecha_fin_ultimo_cm,\n" +
            "\tcoalesce (\n" +
            "\t\tcase\n" +
            "\t\t\twhen lower(stm.nombre) = 'mxn' then svm.monto_minimo_con_impuestos\n" +
            "\t\t\telse (svm.monto_minimo_con_impuestos * svm.tipo_cambio_maximo)\n" +
            "\t\tend \n" +
            "\t, 0) monto_min_con_impuestos_mxn,\n" +
            "\tcoalesce(\n" +
            "\t\tcase\n" +
            "\t\t\twhen lower(stm.nombre) = 'mxn' then svm.monto_maximo_con_impuestos\n" +
            "\t\t\telse (svm.monto_maximo_con_impuestos * svm.tipo_cambio_maximo)\n" +
            "\t\tend \n" +
            "\t, 0) monto_max_con_impuestos_mxn,\n" +
            "\tcoalesce (\n" +
            "\t\tcase\n" +
            "\t\t\twhen lower(stm.nombre) = 'mxn' then (\n" +
            "\t\t\tselect\n" +
            "\t\t\t\tcm.monto_maximo_con_impuestos\n" +
            "\t\t\tfrom\n" +
            "\t\t\t\tsisecofi.sscft_convenio_modificatorio cm\n" +
            "\t\t\twhere\n" +
            "\t\t\t\tcm.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\t\tand cm.id_convenio_modificatorio =(\n" +
            "\t\t\t\tselect\n" +
            "\t\t\t\t\tmax(cm.id_convenio_modificatorio)\n" +
            "\t\t\t\tfrom\n" +
            "\t\t\t\t\tsisecofi.sscft_convenio_modificatorio cm\n" +
            "\t\t\t\twhere\n" +
            "\t\t\t\t\tcm.id_contrato = t_contrato.id_contrato))\n" +
            "\t\t\telse ((\n" +
            "\t\t\tselect\n" +
            "\t\t\t\tcm.monto_maximo_con_impuestos\n" +
            "\t\t\tfrom\n" +
            "\t\t\t\tsisecofi.sscft_convenio_modificatorio cm\n" +
            "\t\t\twhere\n" +
            "\t\t\t\tcm.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\t\tand cm.id_convenio_modificatorio =(\n" +
            "\t\t\t\tselect\n" +
            "\t\t\t\t\tmax(cm.id_convenio_modificatorio)\n" +
            "\t\t\t\tfrom\n" +
            "\t\t\t\t\tsisecofi.sscft_convenio_modificatorio cm\n" +
            "\t\t\t\twhere\n" +
            "\t\t\t\t\tcm.id_contrato = t_contrato.id_contrato))*(\n" +
            "\t\t\tselect\n" +
            "\t\t\t\tcm.tipo_cambio\n" +
            "\t\t\tfrom\n" +
            "\t\t\t\tsisecofi.sscft_convenio_modificatorio cm\n" +
            "\t\t\twhere\n" +
            "\t\t\t\tcm.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\t\tand cm.id_convenio_modificatorio =(\n" +
            "\t\t\t\tselect\n" +
            "\t\t\t\t\tmax(cm.id_convenio_modificatorio)\n" +
            "\t\t\t\tfrom\n" +
            "\t\t\t\t\tsisecofi.sscft_convenio_modificatorio cm\n" +
            "\t\t\t\twhere\n" +
            "\t\t\t\t\tcm.id_contrato = t_contrato.id_contrato)))\n" +
            "\t\tend \n" +
            "\t, 0) monto_max_cm_con_impuestos_mxn,\n" +
            "\tcoalesce (\n" +
            "\t\tcase\n" +
            "\t\t\twhen lower(stm.nombre) = 'mxn' then svm.monto_minimo_sin_impuestos\n" +
            "\t\t\telse (svm.monto_minimo_sin_impuestos * svm.tipo_cambio_maximo)\n" +
            "\t\tend \n" +
            "\t, 0) monto_min_sin_impuestos_mxn,\n" +
            "\tcoalesce (\n" +
            "\t\tcase\n" +
            "\t\t\twhen lower(stm.nombre) = 'mxn' then svm.monto_maximo_sin_impuestos\n" +
            "\t\t\telse (svm.monto_maximo_sin_impuestos * svm.tipo_cambio_maximo)\n" +
            "\t\tend \n" +
            "\t, 0) monto_max_sin_impuestos_mxn,\n" +
            "\tcoalesce (\n" +
            "\t\tcase\n" +
            "\t\t\twhen lower(stm.nombre) = 'mxn' then (\n" +
            "\t\t\tselect\n" +
            "\t\t\t\tcm.monto_maximo_sin_impuestos\n" +
            "\t\t\tfrom\n" +
            "\t\t\t\tsisecofi.sscft_convenio_modificatorio cm\n" +
            "\t\t\twhere\n" +
            "\t\t\t\tcm.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\t\tand cm.id_convenio_modificatorio =(\n" +
            "\t\t\t\tselect\n" +
            "\t\t\t\t\tmax(cm.id_convenio_modificatorio)\n" +
            "\t\t\t\tfrom\n" +
            "\t\t\t\t\tsisecofi.sscft_convenio_modificatorio cm\n" +
            "\t\t\t\twhere\n" +
            "\t\t\t\t\tcm.id_contrato = t_contrato.id_contrato))\n" +
            "\t\t\telse ((\n" +
            "\t\t\tselect\n" +
            "\t\t\t\tcm.monto_maximo_sin_impuestos\n" +
            "\t\t\tfrom\n" +
            "\t\t\t\tsisecofi.sscft_convenio_modificatorio cm\n" +
            "\t\t\twhere\n" +
            "\t\t\t\tcm.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\t\tand cm.id_convenio_modificatorio =(\n" +
            "\t\t\t\tselect\n" +
            "\t\t\t\t\tmax(cm.id_convenio_modificatorio)\n" +
            "\t\t\t\tfrom\n" +
            "\t\t\t\t\tsisecofi.sscft_convenio_modificatorio cm\n" +
            "\t\t\t\twhere\n" +
            "\t\t\t\t\tcm.id_contrato = t_contrato.id_contrato))*(\n" +
            "\t\t\tselect\n" +
            "\t\t\t\tcm.tipo_cambio\n" +
            "\t\t\tfrom\n" +
            "\t\t\t\tsisecofi.sscft_convenio_modificatorio cm\n" +
            "\t\t\twhere\n" +
            "\t\t\t\tcm.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\t\tand cm.id_convenio_modificatorio =(\n" +
            "\t\t\t\tselect\n" +
            "\t\t\t\t\tmax(cm.id_convenio_modificatorio)\n" +
            "\t\t\t\tfrom\n" +
            "\t\t\t\t\tsisecofi.sscft_convenio_modificatorio cm\n" +
            "\t\t\t\twhere\n" +
            "\t\t\t\t\tcm.id_contrato = t_contrato.id_contrato)))\n" +
            "\t\tend \n" +
            "\t, 0) monto_max_cm_sin_impuestos_mxn,\n" +
            "\tstm.nombre tipo_moneda,\n" +
            "\tcoalesce (svm.tipo_cambio_maximo, 0) tipo_cambio,\n" +
            "\tcoalesce (svm.monto_minimo_con_impuestos, 0) monto_min_con_impuestos,\n" +
            "\tcoalesce (svm.monto_maximo_con_impuestos, 0) monto_max_con_impuestos,\n" +
            "\tcoalesce (\n" +
            "\t\t(select\n" +
            "\t\t\tcm.monto_maximo_con_impuestos\n" +
            "\t\tfrom\n" +
            "\t\t\tsisecofi.sscft_convenio_modificatorio cm\n" +
            "\t\twhere\n" +
            "\t\t\tcm.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\tand cm.id_convenio_modificatorio =(\n" +
            "\t\t\tselect\n" +
            "\t\t\t\tmax(cm.id_convenio_modificatorio)\n" +
            "\t\t\tfrom\n" +
            "\t\t\t\tsisecofi.sscft_convenio_modificatorio cm\n" +
            "\t\t\twhere\n" +
            "\t\t\t\tcm.id_contrato = t_contrato.id_contrato))\n" +
            "\t, 0) monto_max_cm_con_impuestos,\n" +
            "\t(\n" +
            "\t\tselect\n" +
            "\t\t\tstring_agg(su.nombre, ', ')\n" +
            "\t\tfrom\n" +
            "\t\t\tsisecofi.sscft_participantes_administracion_contrato spac\n" +
            "\t\tleft join sisecofi.sscft_usuario su on spac.id_user = su.id_user\n" +
            "\t\tleft join sisecofi.sscfc_responsabilidad sr on sr.id_responsabilidad = spac.id_responsabilidad\n" +
            "\t\twhere spac.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\tand su.enabled = true \n" +
            "\t\t\tand lower(sr.nombre) = lower('Administrador del contrato')\n" +
            "\t) administrador_contrato,\n" +
            "\t(\n" +
            "\t\tselect\n" +
            "\t\t\tstring_agg(((cm.numero_convenio ||('-' || cm.tipo_convenio))||('-' || to_char(cm.fecha_firma,\n" +
            "\t\t\t'dd/MM/YYYY'))),\n" +
            "\t\t\t', ')\n" +
            "\t\tfrom\n" +
            "\t\t\tsisecofi.sscft_convenio_modificatorio cm\n" +
            "\t\twhere\n" +
            "\t\t\tcm.id_contrato = t_contrato.id_contrato\n" +
            "\t) convenios_modificatorios,\n" +
            "\t(\n" +
	"\tcase\n" +
	"\t\twhen date(\n" +
	"\t\t\tcoalesce(\n" +
	"\t\t\t\t(select cm.fecha_fin\n" +
	"\t\t\t\t from sisecofi.sscft_convenio_modificatorio cm\n" +
	"\t\t\t\t where cm.id_contrato = t_contrato.id_contrato\n" +
	"\t\t\t\t   and cm.id_convenio_modificatorio = (\n" +
	"\t\t\t\t\t\tselect max(cm2.id_convenio_modificatorio)\n" +
	"\t\t\t\t\t\tfrom sisecofi.sscft_convenio_modificatorio cm2\n" +
	"\t\t\t\t\t\twhere cm2.id_contrato = t_contrato.id_contrato\n" +
	"\t\t\t\t   )\n" +
	"\t\t\t\t),\n" +
	"\t\t\t\tsvm.fecha_fin_vigencia_contrato\n" +
	"\t\t\t)\n" +
	"\t\t) < date(current_date) then 0\n" +
	"\t\telse round(extract(day from\n" +
	"\t\t\tcoalesce(\n" +
	"\t\t\t\t(select cm.fecha_fin\n" +
	"\t\t\t\t from sisecofi.sscft_convenio_modificatorio cm\n" +
	"\t\t\t\t where cm.id_contrato = t_contrato.id_contrato\n" +
	"\t\t\t\t   and cm.id_convenio_modificatorio = (\n" +
	"\t\t\t\t\t\tselect max(cm2.id_convenio_modificatorio)\n" +
	"\t\t\t\t\t\tfrom sisecofi.sscft_convenio_modificatorio cm2\n" +
	"\t\t\t\t\t\twhere cm2.id_contrato = t_contrato.id_contrato\n" +
	"\t\t\t\t   )\n" +
	"\t\t\t\t),\n" +
	"\t\t\t\tsvm.fecha_fin_vigencia_contrato\n" +
	"\t\t\t) - current_date\n" +
	"\t\t) / 30, 0)\n" +
	"\tend\n" +
	") meses_restantes_contrato,\n" +  
            "\t(\n" +
                    "\t\tselect\n" +
                    "\t\t\t'01/' || spcm.nombre || '/' || spca.nombre\n" +
                    "\t\tfrom\n" +
                    "\t\t\tsisecofi.sscft_estimacion se\n" +
                    "\t\tleft join sisecofi.sscfc_estatus_estimacion see on\n" +
                    "\t\t\tse.id_estatus_estimacion = see.id_estatus_estimacion\n" +
                    "\t\tleft join sisecofi.sscfc_periodo_control_mes spcm on\n" +
                    "\t\t\tse.id_periodo_control_mes = spcm.id_periodo_control_mes\n" +
                    "\t\tleft join sisecofi.sscfc_periodo_control_anio spca on\n" +
                    "\t\t\tse.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
                    "\t\twhere\n" +
                    "\t\t\tlower(see.nombre) <> 'cancelado'\n" +
                    "\t\t\tand se.id_contrato = t_contrato.id_contrato\n" +
                    "\t\torder by\n" +
                    "\t\t\tCAST(spca.nombre AS INTEGER) DESC, spcm.id_periodo_control_mes DESC\n" +
                    "\t\tfetch first 1 rows only\n" +
                    "\t) ultima_estimacion,\n" +
            "\t(\n" +
            "\t\tselect\n" +
            "\t\t\t'01/' || spcm.nombre || '/' || spca.nombre\n" +
            "\t\tfrom\n" +
            "\t\t\tsisecofi.sscft_dictamen sd\n" +
            "\t\tleft join sisecofi.sscfc_estatus_dictamen sed on\n" +
            "\t\t\tsd.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\tleft join sisecofi.sscfc_periodo_control_mes spcm on\n" +
            "\t\t\tsd.id_periodo_control_mes = spcm.id_periodo_control_mes\n" +
            "\t\tleft join sisecofi.sscfc_periodo_control_anio spca on\n" +
            "\t\t\tsd.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "\t\twhere\n" +
            "\t\t\tlower(sed.nombre) not in('inicial', 'cancelado')\n" +
            "\t\t\t\tand sd.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\torder by\n" +
            "\t\t\t\tsd.fecha_creacion desc fetch first 1 rows only\n" +
            "\t) ultimo_dictamen,\n" +
            "\t(\n" +
	    "\t\tselect\n" +
            "\t\t\t'01/' || spcm.nombre || '/' || spca.nombre\n" +
            "\t\tfrom\n" +
            "\t\t\tsisecofi.sscft_dictamen sd\n" +
            "\t\tleft join sisecofi.sscfc_estatus_dictamen sed on\n" +
            "\t\t\tsd.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\tleft join sisecofi.sscfc_periodo_control_mes spcm on\n" +
            "\t\t\tsd.id_periodo_control_mes = spcm.id_periodo_control_mes\n" +
            "\t\tleft join sisecofi.sscfc_periodo_control_anio spca on\n" +
            "\t\t\tsd.id_periodo_control_anio = spca.id_periodo_control_anio\n" +
            "\t\twhere\n" +
            "\t\t\tlower(sed.nombre) in('pagado')\n" +
            "\t\t\t\tand sd.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\torder by\n" +
             "\t\t\tCAST(spca.nombre AS INTEGER) DESC, spcm.id_periodo_control_mes DESC\n" +
            "\t\t\t\tfetch first 1 rows only\n" +
            "\t) ultimo_pago";

    String DEVENGADO_ANTES_DEDUCCIONES_SAT = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(select\n" +
            "\t\t\tsum(ssd.cantidad_servicios_sat * ssc.precio_unitario)\n" +
            "\t\tfrom\n" +
            "\t\t\tsisecofi.sscft_dictamen sd\n" +
            "\t\tleft join sisecofi.sscfc_estatus_dictamen sed on\n" +
            "\t\t\tsd.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\tleft join sisecofi.sscft_servicios_dictaminados ssd on\n" +
            "\t\t\tsd.id_dictamen = ssd.id_dictamen\n" +
            "\t\tleft join sisecofi.sscft_servicio_contrato ssc on\n" +
            "\t\t\tssd.id_servicio_contrato = ssc.id_servicio_contrato\n" +
            "\t\twhere\n" +
            "\t\t\tssd.seleccionado = true\n" +
            "\t\t\tand lower(sed.nombre) not in('inicial', 'cancelado')\n" +
            "\t\t\tand sd.id_contrato = t_contrato.id_contrato)\n" +
            "\t, 0) devengado_antes_deducciones_sat";

    String PAGADO_ANTES_DEDUCCIONES_SAT = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(select\n" +
            "\t\t\tsum(ssd.cantidad_servicios_sat * ssc.precio_unitario)\n" +
            "\t\tfrom\n" +
            "\t\t\tsisecofi.sscft_dictamen sd\n" +
            "\t\tleft join sisecofi.sscfc_estatus_dictamen sed on\n" +
            "\t\t\tsd.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\tleft join sisecofi.sscft_servicios_dictaminados ssd on\n" +
            "\t\t\tsd.id_dictamen = ssd.id_dictamen\n" +
            "\t\tleft join sisecofi.sscft_servicio_contrato ssc on\n" +
            "\t\t\tssd.id_servicio_contrato = ssc.id_servicio_contrato\n" +
            "\t\twhere\n" +
            "\t\t\tlower(sed.nombre) = 'pagado'\n" +
            "\t\t\tand sd.id_contrato = t_contrato.id_contrato)\n" +
            "\t, 0) pagado_antes_deducciones_sat";

    String DEDUCCIONES_SAT = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(select\n" +
            "\t\t\tsum(sdd.monto)\n" +
            "\t\tfrom\n" +
            "\t\t\tsisecofi.sscft_deducciones_dictamenes sdd\n" +
            "\t\tleft join sisecofi.sscft_dictamen sd on\n" +
            "\t\t\tsd.id_dictamen = sdd.id_dictamen\n" +
            "\t\tleft join sisecofi.sscfc_estatus_dictamen sed on\n" +
            "\t\t\tsd.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\tleft join sisecofi.sscfc_desgloce sd2 on\n" +
            "\t\t\tsd2.id_desgloce = sdd.id_desgloce\n" +
            "\t\twhere\n" +
            "\t\t\tsd.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\tand lower(sed.nombre) not in('inicial', 'cancelado')\n" +
            "\t\t\tand lower(sd2.nombre) = 'sat')\n" +
            "\t, 0) deducciones_sat";

    String IVA_SAT = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(select\n" +
            "\t\t\tsum((sf.iva * cast(sf.porcentaje_sat as decimal(5, 2))) / 100)\n" +
            "\t\tfrom\n" +
            "\t\t\tsisecofi.sscft_facturas sf\n" +
            "\t\tleft join sisecofi.sscft_dictamen sd on\n" +
            "\t\t\tsd.id_dictamen = sf.id_dictamen\n" +
            "\t\tleft join sisecofi.sscfc_estatus_dictamen sed on\n" +
            "\t\t\tsd.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\twhere\n" +
            "\t\t\tsd.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\tand lower(sed.nombre) not in('inicial', 'cancelado'))\n" +
            "\t, 0) iva_sat";

    String IEPS_SAT = ",\n" +
            "\tcoalesce((\n" +
            "\t\t(select\n" +
            "\t\t\tsum((sf.ieps * cast(sf.porcentaje_sat as decimal(5, 2))) / 100)\n" +
            "\t\tfrom\n" +
            "\t\t\tsisecofi.sscft_facturas sf\n" +
            "\t\tleft join sisecofi.sscft_dictamen sd on\n" +
            "\t\t\tsd.id_dictamen = sf.id_dictamen\n" +
            "\t\tleft join sisecofi.sscfc_estatus_dictamen sed on\n" +
            "\t\t\tsd.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\twhere\n" +
            "\t\t\tsd.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\tand lower(sed.nombre) not in('inicial', 'cancelado'))\n" +
            "\t), 0) ieps_sat";

    String OTROS_IMPUESTOS_SAT = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(select\n" +
            "\t\t\tsum((sf.otros_impuestos * cast(sf.porcentaje_sat as decimal(5, 2))) / 100)\n" +
            "\t\tfrom\n" +
            "\t\t\tsisecofi.sscft_facturas sf\n" +
            "\t\tleft join sisecofi.sscft_dictamen sd on\n" +
            "\t\t\tsd.id_dictamen = sf.id_dictamen\n" +
            "\t\tleft join sisecofi.sscfc_estatus_dictamen sed on\n" +
            "\t\t\tsd.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\twhere\n" +
            "\t\t\tsd.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\tand lower(sed.nombre) not in('inicial', 'cancelado'))\n" +
            "\t, 0) otros_impuestos_sat";

    String PENALIZACIONES_SAT = ",\n" +
            "\tcoalesce (\n" +
            "\t\tcoalesce(\n" +
            "\t\t\t(select\n" +
            "\t\t\t\tsum(spcd.monto)\n" +
            "\t\t\tfrom\n" +
            "\t\t\t\tsisecofi.sscft_penas_contractuales_dictamenes spcd\n" +
            "\t\t\tleft join sisecofi.sscft_dictamen sd on\n" +
            "\t\t\t\tsd.id_dictamen = spcd.id_dictamen\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on\n" +
            "\t\t\t\tsd.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscfc_desgloce sd2 on\n" +
            "\t\t\t\tspcd.id_desgloce = sd2.id_desgloce\n" +
            "\t\t\twhere\n" +
            "\t\t\t\tsd.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\t\tand lower(sd2.nombre) = 'sat'\n" +
            "\t\t\t\tand lower(sed.nombre) not in('inicial', 'cancelado')),0\n" +
            "\t\t)\n" +
            "\t\t+\n" +
            "\t\tcoalesce(\n" +
            "\t\t\t(select\n" +
            "\t\t\t\tsum(spcd.monto)\n" +
            "\t\t\tfrom\n" +
            "\t\t\t\tsisecofi.sscft_penas_convencionales_dictamenes spcd\n" +
            "\t\t\tleft join sisecofi.sscft_dictamen sd on\n" +
            "\t\t\t\tsd.id_dictamen = spcd.id_dictamen\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on\n" +
            "\t\t\t\tsd.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscfc_desgloce sd2 on\n" +
            "\t\t\t\tspcd.id_desgloce = sd2.id_desgloce\n" +
            "\t\t\twhere\n" +
            "\t\t\t\tsd.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\t\tand lower(sd2.nombre) = 'sat'\n" +
            "\t\t\t\tand lower(sed.nombre) not in('inicial', 'cancelado')),0\n" +
            "\t\t)\n" +
            "\t, 0) penalizaciones_sat";

    String REINTEGRO_SAT = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(select\n" +
            "\t\t\tsum(sra.total)\n" +
            "\t\tfrom\n" +
            "\t\t\tsisecofi.sscft_reintegros_asociados sra\n" +
            "\t\tleft join sisecofi.sscfc_tipo_reintegro str on\n" +
            "\t\t\tstr.id_tipo_reintegro = sra.id_tipo_reintegro\n" +
            "\t\twhere\n" +
            "\t\t\tsra.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\tand lower(str.nombre) = 'servicio sat')\n" +
            "\t, 0) reintegro_sat";

    String PAGADO_SAT = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(select\n" +
            "\t\t\tsum(srp.pagado_nafin)\n" +
            "\t\tfrom\n" +
            "\t\t\tsisecofi.sscft_referencia_pago srp\n" +
            "\t\tleft join sisecofi.sscfc_desgloce sd2 on\n" +
            "\t\t\tsd2.id_desgloce = srp.id_desgloce\n" +
            "\t\tleft join sisecofi.sscft_solicitud_pago ssp on\n" +
            "\t\t\tssp.id_solicitud_pago = srp.id_solicitud_pago\n" +
            "\t\tleft join sisecofi.sscft_dictamen sd on\n" +
            "\t\t\tssp.id_dictamen = sd.id_dictamen\n" +
            "\t\tleft join sisecofi.sscfc_estatus_dictamen sed on\n" +
            "\t\t\tsd.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\twhere\n" +
            "\t\t\tsd.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\tand lower(sed.nombre) in ('pagado')\n" +
            "\t\t\tand lower(sd2.nombre) = 'sat')\n" +
            "\t, 0) pagado_sat";

    String DEVENGADO_ANTES_DEDUCCIONES_CC = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(select\n" +
            "\t\t\tsum(ssd.cantidad_servicios_cc * ssc.precio_unitario)\n" +
            "\t\tfrom\n" +
            "\t\t\tsisecofi.sscft_dictamen sd\n" +
            "\t\tleft join sisecofi.sscfc_estatus_dictamen sed on\n" +
            "\t\t\tsd.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\tleft join sisecofi.sscft_servicios_dictaminados ssd on\n" +
            "\t\t\tsd.id_dictamen = ssd.id_dictamen\n" +
            "\t\tleft join sisecofi.sscft_servicio_contrato ssc on\n" +
            "\t\t\tssd.id_servicio_contrato = ssc.id_servicio_contrato\n" +
            "\t\twhere\n" +
            "\t\t\tssd.seleccionado = true\n" +
            "\t\t\tand lower(sed.nombre) not in('inicial', 'cancelado')\n" +
            "\t\t\tand sd.id_contrato = t_contrato.id_contrato)\n" +
            "\t, 0) devengado_antes_deducciones_cc";


    String PAGADO_ANTES_DEDUCCIONES_CC = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(select\n" +
            "\t\t\tsum(ssd.cantidad_servicios_cc * ssc.precio_unitario)\n" +
            "\t\tfrom\n" +
            "\t\t\tsisecofi.sscft_dictamen sd\n" +
            "\t\tleft join sisecofi.sscfc_estatus_dictamen sed on\n" +
            "\t\t\tsd.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\tleft join sisecofi.sscft_servicios_dictaminados ssd on\n" +
            "\t\t\tsd.id_dictamen = ssd.id_dictamen\n" +
            "\t\tleft join sisecofi.sscft_servicio_contrato ssc on\n" +
            "\t\t\tssd.id_servicio_contrato = ssc.id_servicio_contrato\n" +
            "\t\twhere\n" +
            "\t\t\tlower(sed.nombre) = 'pagado'\n" +
            "\t\t\tand sd.id_contrato = t_contrato.id_contrato)\n" +
            "\t, 0) pagado_antes_deducciones_cc";

    String DEDUCCIONES_CC = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(select\n" +
            "\t\t\tsum(sdd.monto)\n" +
            "\t\tfrom\n" +
            "\t\t\tsisecofi.sscft_deducciones_dictamenes sdd\n" +
            "\t\tleft join sisecofi.sscft_dictamen sd on\n" +
            "\t\t\tsd.id_dictamen = sdd.id_dictamen\n" +
            "\t\tleft join sisecofi.sscfc_estatus_dictamen sed on\n" +
            "\t\t\tsd.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\tleft join sisecofi.sscfc_desgloce sd2 on\n" +
            "\t\t\tsd2.id_desgloce = sdd.id_desgloce\n" +
            "\t\twhere\n" +
            "\t\t\tsd.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\tand lower(sed.nombre) not in('inicial', 'cancelado')\n" +
            "\t\t\tand lower(sd2.nombre) = 'cc')\n" +
            "\t, 0) deducciones_cc";

    String IVA_CC = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(select\n" +
            "\t\t\tsum((sf.iva * cast(sf.porcentajecc as decimal(5, 2))) / 100)\n" +
            "\t\tfrom\n" +
            "\t\t\tsisecofi.sscft_facturas sf\n" +
            "\t\tleft join sisecofi.sscft_dictamen sd on\n" +
            "\t\t\tsd.id_dictamen = sf.id_dictamen\n" +
            "\t\tleft join sisecofi.sscfc_estatus_dictamen sed on\n" +
            "\t\t\tsd.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\twhere\n" +
            "\t\t\tsd.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\tand lower(sed.nombre) not in('inicial', 'cancelado'))\n" +
            "\t, 0) iva_cc";

    String IEPS_CC = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(select\n" +
            "\t\t\tsum((sf.ieps * cast(sf.porcentajecc as decimal(5, 2))) / 100)\n" +
            "\t\tfrom\n" +
            "\t\t\tsisecofi.sscft_facturas sf\n" +
            "\t\tleft join sisecofi.sscft_dictamen sd on\n" +
            "\t\t\tsd.id_dictamen = sf.id_dictamen\n" +
            "\t\tleft join sisecofi.sscfc_estatus_dictamen sed on\n" +
            "\t\t\tsd.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\twhere\n" +
            "\t\t\tsd.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\tand lower(sed.nombre) not in('inicial', 'cancelado'))\n" +
            "\t, 0) ieps_cc";

    String OTROS_IMPUESTOS_CC = ",\n" +
            "\tcoalesce (\n" +
            "\t\t(select\n" +
            "\t\t\tsum((sf.otros_impuestos * cast(sf.porcentajecc as decimal(5, 2))) / 100)\n" +
            "\t\tfrom\n" +
            "\t\t\tsisecofi.sscft_facturas sf\n" +
            "\t\tleft join sisecofi.sscft_dictamen sd on\n" +
            "\t\t\tsd.id_dictamen = sf.id_dictamen\n" +
            "\t\tleft join sisecofi.sscfc_estatus_dictamen sed on\n" +
            "\t\t\tsd.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\twhere\n" +
            "\t\t\tsd.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\tand lower(sed.nombre) not in('inicial', 'cancelado'))\n" +
            "\t, 0) otros_impuestos_cc";

    String PENALIZACIONES_CC = ",\n" +
            "\tcoalesce (\n" +
            "\t\tcoalesce(\n" +
            "\t\t\t(select\n" +
            "\t\t\t\tsum(spcd.monto)\n" +
            "\t\t\tfrom\n" +
            "\t\t\t\tsisecofi.sscft_penas_contractuales_dictamenes spcd\n" +
            "\t\t\tleft join sisecofi.sscft_dictamen sd on\n" +
            "\t\t\t\tsd.id_dictamen = spcd.id_dictamen\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on\n" +
            "\t\t\t\tsd.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscfc_desgloce sd2 on\n" +
            "\t\t\t\tspcd.id_desgloce = sd2.id_desgloce\n" +
            "\t\t\twhere\n" +
            "\t\t\t\tsd.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\t\tand lower(sd2.nombre) = 'cc'\n" +
            "\t\t\t\tand lower(sed.nombre) not in('inicial', 'cancelado')),0\n" +
            "\t\t)\n" +
            "\t\t+\n" +
            "\t\tcoalesce(\n" +
            "\t\t\t(select\n" +
            "\t\t\t\tsum(spcd.monto)\n" +
            "\t\t\tfrom\n" +
            "\t\t\t\tsisecofi.sscft_penas_convencionales_dictamenes spcd\n" +
            "\t\t\tleft join sisecofi.sscft_dictamen sd on\n" +
            "\t\t\t\tsd.id_dictamen = spcd.id_dictamen\n" +
            "\t\t\tleft join sisecofi.sscfc_estatus_dictamen sed on\n" +
            "\t\t\t\tsd.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\t\tleft join sisecofi.sscfc_desgloce sd2 on\n" +
            "\t\t\t\tspcd.id_desgloce = sd2.id_desgloce\n" +
            "\t\t\twhere\n" +
            "\t\t\t\tsd.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\t\tand lower(sd2.nombre) = 'cc'\n" +
            "\t\t\t\tand lower(sed.nombre) not in('inicial', 'cancelado')),0\n" +
            "\t\t)\n" +
            "\t, 0) penalizaciones_cc";

    String REINTEGRO_CC = ",\n" +
            "\tcoalesce(\n" +
            "\t\t(select\n" +
            "\t\t\tsum(sra.total)\n" +
            "\t\tfrom\n" +
            "\t\t\tsisecofi.sscft_reintegros_asociados sra\n" +
            "\t\tleft join sisecofi.sscfc_tipo_reintegro str on\n" +
            "\t\t\tstr.id_tipo_reintegro = sra.id_tipo_reintegro\n" +
            "\t\twhere\n" +
            "\t\t\tsra.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\tand lower(str.nombre) = 'servicio cc')\n" +
            "\t, 0) reintegro_cc";

    String PAGADO_CC = ",\n" +
            "\tcoalesce(\n" +
            "\t\t(select\n" +
            "\t\t\tsum(srp.pagado_nafin)\n" +
            "\t\tfrom\n" +
            "\t\t\tsisecofi.sscft_referencia_pago srp\n" +
            "\t\tleft join sisecofi.sscfc_desgloce sd2 on\n" +
            "\t\t\tsd2.id_desgloce = srp.id_desgloce\n" +
            "\t\tleft join sisecofi.sscft_solicitud_pago ssp on\n" +
            "\t\t\tssp.id_solicitud_pago = srp.id_solicitud_pago\n" +
            "\t\tleft join sisecofi.sscft_dictamen sd on\n" +
            "\t\t\tssp.id_dictamen = sd.id_dictamen\n" +
            "\t\tleft join sisecofi.sscfc_estatus_dictamen sed on\n" +
            "\t\t\tsd.id_estatus_dictamen = sed.id_estatus_dictamen\n" +
            "\t\twhere\n" +
            "\t\t\tsd.id_contrato = t_contrato.id_contrato\n" +
            "\t\t\tand lower(sed.nombre) in ('pagado')\n" +
            "\t\t\tand lower(sd2.nombre) = 'cc')\n" +
            "\t, 0) pagado_cc";

    String SQL_DETALLE_CM = "select\n" +
            "\tscm.numero_convenio,\n" +
            "\tscm.tipo_convenio,\n" +
            "\tto_char(scm.fecha_firma, 'dd/MM/YYYY') fecha_firma,\n" +
            "\tto_char(scm.fecha_fin_servicio, 'dd/MM/YYYY') fecha_fin_servicio,\n" +
            "\tto_char(scm.fecha_fin, 'dd/MM/YYYY') fecha_fin_contrato,\n" +
            "\tcoalesce(scm.monto_maximo_con_impuestos,0) as monto_maximo_con_impuestos,\n" +
            "\tcoalesce(scm.monto_maximo_sin_impuestos,0) as monto_maximo_sin_impuestos,\n" +
            "\tcoalesce(scm.monto_pesos,0) as monto_pesos,\n" +
            "\tscm.comentarios\n" +
            "from\n" +
            "\tsisecofi.sscft_convenio_modificatorio scm\n" +
            "left join sisecofi.sscft_contrato sc on\n" +
            "\tsc.id_contrato = scm.id_contrato\n" +
            "where\n" +
            "\t1 = 1\n" +
            "\tand sc.id_contrato = :id_contrato";

    String JOINS = "\tsisecofi.sscft_datos_generales_contrato t_dgp\n" +
            "left join sisecofi.sscfc_dominios_tecnologicos c_dt on\n" +
            "\tc_dt.id_dominios_tecnologicos = t_dgp.id_dominios_tecnologicos\n" +
            "left join sisecofi.sscft_contrato t_contrato on\n" +
            "\tt_contrato.id_contrato = t_dgp.id_contrato\n" +
            "left join sisecofi.sscft_proyecto t_proyecto on\n" +
            "\tt_proyecto.id_proyecto = t_contrato.id_proyecto\n" +
            "join sisecofi.sscfc_estatus_proyecto c_ep on\n" +
            "\tc_ep.id_estatus_proyecto = t_proyecto.id_estatus_proyecto\n" +
            "join sisecofi.sscft_ficha_tecnica sft on\n" +
            "\tt_proyecto.id_proyecto = sft.id_proyecto\n" +
            "join sisecofi.sscfc_tipo_procedimiento c_tc on\n" +
            "\tc_tc.id_tipo_procedimiento = sft.id_tipo_procedimiento\n" +
            "join sisecofi.sscft_vigencia_montos svm on\n" +
            "\tt_contrato.id_contrato = svm.id_contrato\n" +
            "join sisecofi.sscfc_tipo_moneda stm on\n" +
            "\tstm.id_tipo_moneda = svm.id_tipo_moneda\n" +
            "left join (\n" +
            "\tselect id_contrato, max(fecha_fin) as fecha_fin\n" +
            "\tfrom sisecofi.sscft_convenio_modificatorio\n" +
            "\tgroup by id_contrato\n" +
            ") scm on scm.id_contrato = t_contrato.id_contrato";
            

    String[] ETIQUETAS_BASE = new String[]{"Nombre corto del proyecto", "Nombre corto del contrato"};

    String[] ETIQUETAS_DATOS_GENERALES = new String[]{
            "Id proyecto AGP", "Estatus del proyecto", "Id contrato", "Número del contrato",
            "Nombre del contrato", "Objetivo del servicio", "Alcance del servicio", "Contrato vigente",
            "Dominio", "Tipo de contratación", "Proveedor", "Fecha inicio del contrato", "Fecha fin del contrato", "Fecha fin del último CM",
            "Mínimo contratado c/ impuestos MXN", "Máximo contratado c/ impuestos MXN", "Máximo CM c/ impuestos MXN",
            "Mínimo contratado s/ impuestos MXN", "Máximo contratado s/ impuestos MXN", "Máximo CM s/ impuestos MXN",
            "Moneda", "Tipo de cambio", "Mínimo contratado c/ impuestos", "Máximo contratado c/ impuestos",
            "Máximo CM c/ impuestos", "Administrador del contrato", "Convenios modificatorios", "Meses restantes del contrato",
            "Última estimación", "Último dictamen", "Último pago"
    };

    String[] ETIQUETAS_DETALLE_FINANCIERO = new String[]{
            "Devengado antes de deducciones SAT",
            "Devengado antes de deducciones CC",
            "Pagado antes de deducciones SAT",
            "Pagado antes de deducciones CC",
            "Deducciones SAT",
            "Deducciones CC",
            "IVA SAT",
            "IVA CC",
            "IEPS SAT",
            "IEPS CC",
            "Otros Impuestos SAT",
            "Otros Impuestos CC",
            "Penalizaciones SAT",
            "Penalizaciones CC",
            "Reintegro SAT",
            "Reintegro CC",
            "Pagado SAT",
            "Pagado CC"
    };

    String[] ETIQUETAS_DETALLE_CM = new String[]{
            "Número del convenio",
            "Tipo de convenio",
            "Fecha de firma",
            "Fecha fin de servicio",
            "Fecha fin del contrato con CM",
            "Monto máximo del contrato c/ CM con impuestos",
            "Monto máximo del contrato c/ CM sin impuestos",
            "Monto en pesos",
            "Comentarios"
    };

}
