SELECT ncm."NÚMERO DE CONTRATO" AS numero_contrato,
    MAX(arg."Nombre corto del contrato") AS nombre_corto_contrato,
    MAX(arg."máximo resultante s/ impuestos MXN") AS monto_max_contrato_simp, 
    MAX(arg."mínimo contratdo s/ impuestos MXN") AS monto_min_contrato_simp,
    MAX(afn."monto_anded") AS monto_anded_ref,

    SUM(coalesce(ncm.monto_dictaminado_ad * GREATEST(ncm."TIPO DE CAMBIO DICTAMINADO", 1), ncm.monto_estimado_ad * GREATEST(ncm."TIPO DE CAMBIO ESTIMADO", 1), 0)) AS monto_dev_antes_ded, 
    SUM(ncm."PAGADO NAFIN" + ncm.otros_impuestos) AS monto_pagado_total, 
    SUM(ncm.penalizaciones) AS monto_penalizacion,
    SUM(ncm.reintegros) AS monto_reintegro,
    SUM(ncm.otros_impuestos) AS monto_otros_impuestos,
    SUM(ncm.deducciones) AS monto_deductivas,
    SUM(ncm."PAGADO NAFIN" + ncm.otros_impuestos - ncm.reintegros) AS monto_pesos_sn_rein

FROM sisecofi.nmac_consumo_mensual ncm
    LEFT JOIN sisecofi.acppi_reporte_gral arg
        ON ncm."NÚMERO DE CONTRATO" = arg."Número de contrato"
    LEFT JOIN sisecofi.acppi_facturas_nueva afn
        ON afn.numero_contrato = ncm."NÚMERO DE CONTRATO"
WHERE afn."monto_anded" = 671.89
GROUP BY ncm."NÚMERO DE CONTRATO"
ORDER BY ncm."NÚMERO DE CONTRATO";