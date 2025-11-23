package com.sisecofi.reportedocumental.dto.dinamico;

import com.sisecofi.libreria.comunes.util.anotaciones.reportes.Joiny;
import com.sisecofi.libreria.comunes.util.anotaciones.reportes.TableJoin;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Data
@Builder
@ToString
public class DataReporteDto {
	
	/*Proyecto*/
	@TableJoin(priority = 1, tableJoin = "sisecofi.sscft_proyecto", fieldJoin = "id_proyecto", alias = "s1",joinCondition = "s1.estatus=true",orderyBy = "s1.id_proyecto")
	@Joiny(tableJoin = "sisecofi.sscfc_estatus_proyecto", fieldJoin = "id_estatus_proyecto", alias = "s10",campos = {"s10.nombre" },specificJoin = "s1")	
	private DatosGenerales datosGenerales; //COMPLETO

	@TableJoin(priority = 2, tableJoin = "sisecofi.sscft_ficha_tecnica", fieldJoin = "id_proyecto", alias = "s11",orderyBy = "s11.id_proyecto")
	@Joiny(tableJoin = "sisecofi.sscfc_admon_general", fieldJoin = "id_admon_patrocinadora", fieldJoinReverse ="id_admon_general", alias = "s12",campos = {"s12.acronimo","s12.administracion","s29.administrador" },specificJoin = "s11")				
	@Joiny(tableJoin = "sisecofi.sscfc_administrador_general", fieldJoin = "id_admon_general",  alias = "s29",campos = {"s29.administrador"},specificJoin = "s12",joinCondition = " and s29.estatus=true ")	
	@Joiny(tableJoin = "sisecofi.sscfc_clasificacion_proyecto", fieldJoin = "id_clasificacion_proyecto", alias = "s13",campos = {"s13.nombre" },specificJoin = "s11")	
	@Joiny(tableJoin = "sisecofi.sscfc_tipo_procedimiento", fieldJoin = "id_tipo_procedimiento", alias = "s14",campos = {"s14.nombre" },specificJoin = "s11")	
	@Joiny(tableJoin = "sisecofi.sscfc_admon_general", fieldJoin = "id_admon_participante", fieldJoinReverse ="id_admon_general", alias = "s19",campos = {"s19.acronimo","s19.administracion","s31.administrador"},specificJoin = "s11")	
	@Joiny(tableJoin = "sisecofi.sscfc_administrador_general", fieldJoin = "id_admon_general",  alias = "s31",campos = {"s31.administrador"},specificJoin = "s19",joinCondition = " and s31.estatus=true ")
	@Joiny(tableJoin = "sisecofi.sscfr_ficha_tecnica_admon_central", fieldJoin = "id_ficha_tecnica", alias = "s20",campos = {"s21.acronimo","s21.administracion"},specificJoin = "s11")	
	@Joiny(tableJoin = "sisecofi.sscfc_admon_central", fieldJoin = "id_admon_central", alias = "s21",campos = {"s21.acronimo","s21.administracion","s30.administrador" },specificJoin = "s20")	
	@Joiny(tableJoin = "sisecofi.sscfc_administrador_central", fieldJoin = "id_admon_central",  alias = "s30",campos = {"s30.administrador"},specificJoin = "s21",joinCondition = " and s30.estatus=true ")	
	@Joiny(tableJoin = "sisecofi.sscfc_clasificacion_proyecto", fieldJoin = "id_clasificacion_proyecto", alias = "s22",campos = {"s22.nombre" },specificJoin = "s11")		
	@Joiny(tableJoin = "sisecofi.sscft_ficha_alineacion", fieldJoin = "id_ficha_tecnica", alias = "s23",campos = {"s24.nombre","s25.objetivo","s26.nombre"},specificJoin = "s11")	
	@Joiny(tableJoin = "sisecofi.sscfc_aliniacion", fieldJoin = "id_mapa", fieldJoinReverse ="id_aliniacion", alias = "s24",campos = {"s24.nombre" },specificJoin = "s23")		
	@Joiny(tableJoin = "sisecofi.sscfc_mapa_objetivo", fieldJoin = "id_mapa_objetivo",  alias = "s25",campos = {"s25.objetivo" },specificJoin = "s23")		
	@Joiny(tableJoin = "sisecofi.sscfc_periodo", fieldJoin = "id_periodo",  alias = "s26",campos = {"s26.nombre" },specificJoin = "s23")	
	@Joiny(tableJoin = "sisecofi.sscfc_administracion", fieldJoin = "id_area_planeacion",fieldJoinReverse ="id_administracion", alias = "s27",campos = {"s27.administracion" },specificJoin = "s11")	
	@Joiny(tableJoin = "sisecofi.sscfc_tipo_moneda", fieldJoin = "id_tipo_moneda", alias = "s28",campos = {"s28.nombre" },specificJoin = "s11")	
	@Joiny(tableJoin = "sisecofi.sscft_historico", fieldJoin = "id_ficha_tecnica", alias = "s32",campos = {"s32.nombre","s32.puesto","s32.correo","s32.fecha_inicio","s32.fecha_fin","s32.estatus" },specificJoin = "s11")	
	private FichaTecnica fichaTecnica; //COMPLETO
	/*Proyecto*/
	
	/*Proveedor*/
	@TableJoin(priority = 2, tableJoin = "sisecofi.sscft_proveedor", fieldJoin = "id_proyecto", alias = "s6", joinCondition = "and s6.estatus=true", extraTableJoin = "sisecofi.sscft_proyecto_proveedor", extraFieldJoin = "id_proveedor", extraAlias = "s7", extraJoinCondition = "and s7.estatus=true",orderyBy = "s6.id_proveedor")	
	@Joiny(tableJoin = "sisecofi.sscfc_giro_empresarial", fieldJoin = "id_giro_empresarial", alias = "s33",campos = {"s33.nombre" })	
	@Joiny(tableJoin = "sisecofi.sscft_directorio_proveedor", fieldJoin = "id_proveedor", alias = "s34",campos = {"s34.nombre_contacto" ,"s34.representante_legal"},specificJoin = "s6")	
	@Joiny(tableJoin = "sisecofi.sscfc_titulo_servicio_proveedor", fieldJoin = "id_proveedor", alias = "s35",campos = {"s35.numero_titulo","s35.vencimiento_titulo","s35.vigencia" },specificJoin = "s6")		
	@Joiny(tableJoin = "sisecofi.sscfc_dictamen_tecnico_proveedor", fieldJoin = "id_proveedor", alias = "s64",campos = {"s65.resultado" },specificJoin = "s6")	
	@Joiny(tableJoin = "sisecofi.sscfc_resultado_dictamen_tecnico", fieldJoin = "id_resultado_dictamen_tecnico", alias = "s65",campos = {"s65.resultado" },specificJoin = "s64")	
	private Proveedor proveedor; //COMPLETO
	/*Proveedor*/
	
	/*Contrato*/
	@TableJoin(priority = 3, tableJoin = "sisecofi.sscft_contrato", fieldJoin = "id_proyecto", alias = "s2",orderyBy = "s2.id_proyecto")		
	@Joiny(tableJoin = "sisecofi.sscfc_estatus_contrato", fieldJoin = "id_estatus_contrato", alias = "s36",campos = {"s36.nombre" })
	@Joiny(tableJoin = "sisecofi.sscft_datos_generales_contrato", fieldJoin = "id_contrato", alias = "s37",campos = {"s9.nombre","s37.numero_contrato","s38.fecha_inicio_vigencia_contrato" },specificJoin = "s2")
	@Joiny(tableJoin = "sisecofi.sscfr_asociacion_contrato_proveedor", fieldJoin = "id_contrato", alias = "s3", extraTableJoin = "sisecofi.sscft_proveedor", extraFieldJoin = "id_proveedor", campos = {"s4.nombre_proveedor" }, extraAlias = "s4")	
	@Joiny(tableJoin = "sisecofi.sscfc_tipo_procedimiento", fieldJoin = "id_tipo_procedimiento", alias = "s9", campos = {"s9.nombre" },specificJoin = "s37")		
	@Joiny(tableJoin = "sisecofi.sscft_vigencia_montos", fieldJoin = "id_contrato", alias = "s38",campos = {"s38.fecha_inicio_vigencia_contrato","s38.fecha_fin_vigencia_contrato","s38.monto_maximo_sin_impuestos","s38.monto_pesos_sin_impuestos"},specificJoin = "s2")			
	@Joiny(tableJoin = "sisecofi.sscft_participantes_administracion_contrato", fieldJoin = "id_contrato", alias = "s66",campos = {"s67.administracion","s68.nombre"},specificJoin = "s2")		
	@Joiny(tableJoin = "sisecofi.sscfc_admon_central", fieldJoin = "id_admon_central", alias = "s67",campos = {"s67.administracion"},specificJoin = "s66")
	@Joiny(tableJoin = "sisecofi.sscft_usuario", fieldJoin = "id_user", alias = "s68",campos = {"s68.nombre"},specificJoin = "s66")	
	private Contrato contrato; // COMPLETO
		
	@TableJoin(priority = 4, tableJoin = "sisecofi.sscft_convenio_modificatorio", fieldJoin = "id_contrato", alias = "s16",specificJoin = "s2",specificFieldJoin = "id_contrato",orderyBy = "s16.id_contrato")	
	@Joiny(tableJoin = "sisecofi.sscfc_iva", fieldJoin = "id_iva", alias = "s39",campos = {"s39.porcentaje" },specificJoin = "s16")	
	private ConvenioModificatorio convenioModificatorio; //COMPLETO
	/*Contrato*/
	
	/*Concepto de servicio*/
	@TableJoin(priority = 5, tableJoin = "sisecofi.sscft_servicio_contrato", fieldJoin = "id_contrato",  alias = "s17",specificJoin = "s2",specificFieldJoin = "id_contrato",orderyBy = "s17.id_contrato")	
	@Joiny(tableJoin = "sisecofi.sscft_grupo_servicio_contrato", fieldJoin = "id_contrato", alias = "s40",campos = {"s40.grupo","s41.nombre"},specificJoin = "s17")	
	@Joiny(tableJoin = "sisecofi.sscfc_tipo_consumo", fieldJoin = "id_tipo_consumo", alias = "s41",campos = {"s41.nombre" },specificJoin = "s40")		
	@Joiny(tableJoin = "sisecofi.sscft_servicio_convenio", fieldJoin = "id_servicio_contrato", alias = "s42",campos = {"s42.numero_total_servicios","s42.monto_maximo_total" },specificJoin = "s17")
	@Joiny(tableJoin = "sisecofi.sscfc_tipo_unidad", fieldJoin = "id_tipo_unidad", alias = "s74",campos = {"s74.nombre" },specificJoin = "s17")	
	private ConceptoServicio conceptoServicio; // COMPLETO
		
	@TableJoin(priority = 7, tableJoin = "sisecofi.sscft_servicio_estimado", fieldJoin = "id_servicio_contrato",  alias = "s43",specificJoin = "s17",specificFieldJoin = "id_servicio_contrato",orderyBy = "s43.id_servicio_estimado")
	private Estimado estimado; //COMPLETO
		
	@TableJoin(priority = 8, tableJoin = "sisecofi.sscft_servicios_dictaminados", fieldJoin = "id_servicio_contrato",  alias = "s44",specificJoin = "s17",specificFieldJoin = "id_servicio_contrato",orderyBy = "s44.id_contrato")
	private Dictaminado dictaminado; // COMPLETO
		
	/*Estimaciones*/
	@TableJoin(priority = 10, tableJoin = "sisecofi.sscft_estimacion", fieldJoin = "id_contrato", alias = "s18",specificJoin = "s2",specificFieldJoin = "id_contrato",orderyBy = "s18.id_contrato")
	@Joiny(tableJoin = "sisecofi.sscft_proveedor", fieldJoin = "id_proveedor", alias = "s46",campos = {"s46.nombre_proveedor"},specificJoin = "s18")	
	@Joiny(tableJoin = "sisecofi.sscfc_periodo_control_mes", fieldJoin = "id_periodo_control_mes", alias = "s47",campos = {"s47.nombre"},specificJoin = "s18")	
	@Joiny(tableJoin = "sisecofi.sscfc_iva", fieldJoin = "id_iva", alias = "s48",campos = {"s48.porcentaje"},specificJoin = "s18")		
	private Estimaciones estimaciones; //COMPLETO
	/*Estimaciones*/
	
	/*Dictamen*/
	@TableJoin(priority = 11, tableJoin = "sisecofi.sscft_dictamen", fieldJoin = "id_contrato", alias = "s49",specificJoin = "s2",specificFieldJoin = "id_contrato",orderyBy = "s49.id_contrato")
	@Joiny(tableJoin = "sisecofi.sscfc_periodo_control_mes", fieldJoin = "id_periodo_control_mes", alias = "s50",campos = {"s50.nombre"},specificJoin = "s49")
	@Joiny(tableJoin = "sisecofi.sscfc_iva", fieldJoin = "id_iva", alias = "s51",campos = {"s51.porcentaje"},specificJoin = "s49")
	@Joiny(tableJoin = "sisecofi.sscft_resumen_consolidado", fieldJoin = "id_dictamen", alias = "s53",campos = {"s53.sub_total","s53.deducciones","s53.ieps","s53.otros_impuestos","s53.total","s53.total_pesos","s71.nombre"},specificJoin = "s49")
	@Joiny(tableJoin = "sisecofi.sscft_proveedor", fieldJoin = "id_proveedor", alias = "s54",campos = {"s54.nombre_proveedor"},specificJoin = "s49")	
	@Joiny(tableJoin = "sisecofi.sscfc_fase_dictamen", fieldJoin = "id_fase_dictamen", alias = "s71",campos = {"s71.nombre"},specificJoin = "s53")
	private Dictamen  dictamen; //COMPLETO
	/*Dictamen*/
	
	/*Penalizacion deducciones*/
	@TableJoin(priority = 12, tableJoin = "sisecofi.sscft_deducciones_dictamenes", fieldJoin = "id_dictamen", alias = "s55",specificJoin = "s49",specificFieldJoin = "id_dictamen",orderyBy = "s55.id_dictamen")	
	@Joiny(tableJoin = "sisecofi.sscfc_tipo_deduccion", fieldJoin = "id_tipo_deduccion", alias = "s69",campos = {"s69.nombre"},specificJoin = "s55")	
	@Joiny(tableJoin = "sisecofi.sscfc_desgloce", fieldJoin = "id_desgloce", alias = "s70",campos = {"s70.nombre"},specificJoin = "s55")
	@Joiny(tableJoin = "sisecofi.sscfc_tipo_deduccion", fieldJoin = "id_tipo_deduccion", alias = "s72",campos = {"s72.nombre"},specificJoin = "s55")
	@Joiny(tableJoin = "sisecofi.sscfc_periodo_control_mes", fieldJoin = "id_periodo_control_mes", alias = "s73",campos = {"s73.nombre"},specificJoin = "s49")
	private PenalizacionDeduccion penalizacionDeduccion;//Falta  documento 
	/*Penalizacion deducciones*/ 
	
	/*Facturas*/
	@TableJoin(priority = 13, tableJoin = "sisecofi.sscft_facturas", fieldJoin = "id_dictamen", alias = "s56",specificJoin = "s49",specificFieldJoin = "id_dictamen",orderyBy = "s56.id_dictamen")
	@Joiny(tableJoin = "sisecofi.sscfc_tipo_moneda", fieldJoin = "id_tipo_moneda", alias = "s57",campos = {"s57.nombre" },specificJoin = "s56")	
	@Joiny(tableJoin = "sisecofi.sscfc_iva", fieldJoin = "id_iva", alias = "s58",campos = {"s58.porcentaje"},specificJoin = "s56")
	@Joiny(tableJoin = "sisecofi.sscft_referencia_pago", fieldJoin = "id_factura", alias = "s63",campos = {"s63.folio_ficha_pago","s63.fecha_notificacion","s63.tipo_cambio_pagado"},specificJoin = "s56")
	private Facturas facturas; //COMPLETO
		
	/*Notas de credito*/
	@TableJoin(priority = 15, tableJoin = "sisecofi.sscft_notas_credito", fieldJoin = "id_dictamen", alias = "s60",specificJoin = "s49",specificFieldJoin = "id_dictamen",orderyBy = "s60.id_dictamen")
	@Joiny(tableJoin = "sisecofi.sscfc_tipo_moneda", fieldJoin = "id_tipo_moneda", alias = "s61",campos = {"s61.nombre" },specificJoin = "s60")	
	@Joiny(tableJoin = "sisecofi.sscfc_iva", fieldJoin = "id_iva", alias = "s62",campos = {"s62.porcentaje"},specificJoin = "s60")	
	private NotaCredito notaCredito; //COMPLETO
		
}
