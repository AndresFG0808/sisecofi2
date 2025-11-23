select ncm."NÚMERO DE CONTRATO" as numero_contrato,
arg."Nombre corto del contrato" as nombre_corto_contrato,
arg."máximo resultante s/ impuestos MXN" as monto_max_contrato_simp, 
arg."mínimo contratdo s/ impuestos MXN" as monto_min_contrato_simp,
afn."monto_anded" as monto_anded_ref,

SUM(coalesce(ncm.monto_dictaminado_ad*greatest(ncm."TIPO DE CAMBIO DICTAMINADO",1), ncm.monto_estimado_ad*greatest(ncm."TIPO DE CAMBIO ESTIMADO",1), 0)) as monto_dev_antes_ded, 
SUM(ncm."PAGADO NAFIN" + ncm.otros_impuestos) as monto_pagado_total, 
SUM(ncm.penalizaciones) as monto_penalizacion,
sum(ncm.reintegros) as monto_reintegro,
sum(ncm.otros_impuestos) as monto_otros_impuestos,
sum(ncm.deducciones) as monto_deductivas,
sum(ncm."PAGADO NAFIN" + ncm.otros_impuestos - ncm.reintegros) as monto_pesos_sn_rein

from sisecofi.nmac_consumo_mensual ncm
	LEFT JOIN sisecofi.acppi_reporte_gral arg
         ON arg."Nombre corto del contrato" = arg."Nombre corto del contrato"
	LEFT JOIN sisecofi.acppi_facturas_nueva afn
		ON afn."proyecto" = afn."proyecto"
WHERE afn."monto_anded" = 671.89
group by ncm."NÚMERO DE CONTRATO", arg."Nombre corto del contrato", afn."monto_anded",
arg."máximo resultante s/ impuestos MXN", arg."mínimo contratdo s/ impuestos MXN"
ORDER BY ncm."NÚMERO DE CONTRATO";