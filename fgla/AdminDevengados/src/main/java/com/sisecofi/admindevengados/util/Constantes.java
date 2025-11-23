package com.sisecofi.admindevengados.util;

import java.util.List;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class Constantes {

	private Constantes() {
	}

	public static final String ROL_CARGA = "Carga";
	
	private static final String ID_DICTAMEN = "Id del dictamen: ";
	private static final String ID_DE_DICTAMEN = "Id de dictamen: "; 
	private static final String COMPROVANTE_FISCAL = "Comprobante fiscal: ";
	public static final String CANCELADO = "Cancelado";
	public static final String INICIAL = "Inicial";
	public static final String PATH_BASE = "";
	public static final String PATH_BASE_INTERNO = "devengados-internos";
	private static final String [] ATRIBUTOS_CONTRATO = {"Id contrato: ","Nombre corto del contrato: ", "Estatus anterior: ", "Estatus actual: "};
	private static final String [] ATRIBUTOS_DICTAMEN = {"Id dictamen: "};
	private static final String [] ATRIBUTOS_SERVICIOS_DICTAMINADOS = {ID_DICTAMEN,"Id de registro de servicios dictaminados: "};
	private static final String [] ATRIBUTOS_SERVICIOS_DICTAMINADOS_EXPORTAR = {ID_DE_DICTAMEN,"Id Registro de servicios: "};
	private static final String [] ATRIBUTOS_DEDUCCIONES_EXPORTAR = {ID_DE_DICTAMEN,"Id Deducciones: "};
	private static final String [] ATRIBUTOS_PENAS_CONTRACTUAL_EXPORTAR = {ID_DE_DICTAMEN,"Id Penas contractuales: "};
	private static final String [] ATRIBUTOS_PENAS_CONVENCIONAL_EXPORTAR = {ID_DE_DICTAMEN,"Id Penas convencionales: "};
	public static final String TITULOS_REPORTE_DICTAMENES= "Fase, Subtotal, Deducciónes, IEPS, IVA, Otros impuestos, Total, Total en pesos";
	public static final String TITULOS_REPORTE_DICTAMINADO= "Id del dictamen, Seleccionado, Id,  Concepto de servicio, Unidad de medida, Tipo de consumo, Precio unitario, Cantidad de servicios maxima vigente, Monto maximo vigente, Cantidad de servicios SAT, Cantidad de servicios CC, Cantidad total de servicios, Monto dictmainado, Cantidad de servicios dictaminados acumulados, Monto dictaminado acumulado, % de servicios dictaminados acumulados, % de monto dictaminado acumulado";
	public static final String TITULOS_REPORTE_PENA= "Id de dictamen, Id, Tipo, Documentos, Pena aplicable, Desglose, Monto";
	public static final String TITULOS_REPORTE_DEDUCCION= "Id de dictamen, Id, Tipo, Documentos, Pena aplicable, Desglose, Conceptos de servicio, Monto";
	private static final String [] ATRIBUTOS_PENAS_CONTRACTUALES = {ID_DICTAMEN , "Id “Penas contractuales”: "};
	private static final String [] ATRIBUTOS_PENAS_CONVENCIOALES = {ID_DICTAMEN , "Id “Penas convencionales”: "};
	private static final String [] ATRIBUTOS_DEDUCCION = {ID_DICTAMEN , "Id de “Deducciones”: "};
	private static final String [] ATRIBUTOS_SOPORTE_DOCUMENTAL = {ID_DICTAMEN , "Nombre del archivo:"};
	private static final String [] ATRIBUTOS_SOPORTE_DOCUMENTAL_REGI_ACTUA = {ID_DICTAMEN};
	private static final String [] ATRIBUTOS_DICTAMINADO = {ID_DICTAMEN , "Estatus: "};
	private static final String [] ATRIBUTOS_FACTURA = {"Id Dictamen: " , "Folio: ", COMPROVANTE_FISCAL};
	private static final String [] ATRIBUTOS_NOTA_CREDITO = {"Id de Dictamen: " , "Folio de la Nota de crédito: ", COMPROVANTE_FISCAL};
	private static final String [] ATRIBUTOS_CANCELAR_FACTURA = {"Id dictamen: " , COMPROVANTE_FISCAL, "Folio: ", "Estatus= "+CANCELADO};
	private static final String [] ATRIBUTOS_CANCELAR_NOTA = {"Id de Dictamen: " , "Folio de la Nota de crédito: ", COMPROVANTE_FISCAL, "Estatus= "+CANCELADO};
	private static final String [] ATRIBUTOS_REGRESAR_PROFORMA = {"Id Dictamen: " , "Estatus=Proforma"};
	public static final String VALIDACION_ESTATUS = "{\"estatus\": true}";
	public static final String FILTRO_DICTAMINADO = "{\"nombre\": \"Dictaminado\"}";
	public static final String ESTATUS_INICIAL = "{\"nombre\": \"Inicial\"}";
	public static final String ESTATUS_ESTIMADO = "{\"nombre\": \"Estimado\"}";
	public static final String TIPO_PENA_CONTRACTUAL = "{\"nombre\": \"Pena contractual\"}";
	public static final String VALIDACION_ESTATUS_NOMBRE = "{\"estatus\": true, \"nombre\": \"SAT\"}";	
	public static final String NAFIN_12 = "12_NAFIN_";
	public static final String TITULOS_REPORTE_DEVENGADO_ESTIMACION= "Id estimación, Periodo de control, Periodo inicial, Periodo final, Proveedor, Estatus, Monto estimado, Monto estimado en pesos, Monto dictaminado, Monto dictaminado en pesos";
	public static final String TITULOS_REPORTE_DEVENGADO_DICTAMEN= "Id dictámen, Monto, Estatus, Comprobante fiscal, Pendientes de pago";
	public static final String TITULOS_REPORTE_DICTAMENES_ASOCIADOS= "Id del dictamen, Periodo de control, Periodo inicio, Periodo fin, Estatus, Monto, Monto en pesos, Tipo de cambio referencial";
	public static final String TITULOS_SERVICIOS_ESTIMADOS= "Id, Grupo, Conceptos de servicio, Tipo de unidad, Tipo de consumo, Precio unitario, Cantidad de servicios máximo vigente, Monto máximo vigente, Cantidad de servicios estimados, Monto estimado, Cantidad de servicios estimados acumulados, Monto estimado acumulado, % de servicios estimados acumulados, % de monto estimado acumulado";
	public static final String TITULOS_REPORTE_FACTURAS_ASOCIADAS= "Id del dictamen, Comprobante fiscal, Convenio de colaboración, Monto, Monto en pesos, Estatus, Tipo de cambio";
	public static final String ESTATUS_CANCELADO = "{\"nombre\": \"Cancelado\"}";
	public static final String ESTATUS_CANCELADA_NOTA = "{\"nombre\": \"Cancelada\"}";
	public static final String ESTATUS_APROBADA_NOTA = "{\"nombre\": \"Aprobada\"}";
	public static final String ESTATUS_ACCPI = "{\"nombre\": \"Factura en ACPPI\"}";
	public static final String ESTATUS_PROFORMA = "{\"nombre\": \"Proforma\"}";
	public static final String ESTATUS_FACTURADO = "{\"nombre\": \"Facturado\"}";
	public static final String ESTATUS_DICTAMINADO = "{\"nombre\": \"Dictaminado\"}";
	public static final String ESTATUS_PAGADO = "{\"nombre\": \"Pagado\"}";
	public static final String BASE_FOLDER = "baseFolder";
	public static final String SOLICITUD_FOLDER = "SolicitudPagoFilesFolder";
	public static final String PATH_BASE_DICTAMEN = "dictamenes";
	public static final String TIPO_PLANTILLA = "tipoPlantillaDictamen";
	public static final String TIPO_FASE = "tipoFaseDictamen";
	public static final String TIPO_DICTAMEN = "tipoDictamen";
	public static final String ESTATUS_SOLICITUD_PAGO = "{\"nombre\": \"Solicitud de pago\"}";
	public static final String ESTATUS_SOLCITUDD_PAGO_PAGADO = "{\"nombre\": \"Pagado\"}";
	public static final String OPCION_SELECT_SI = "Si";
	public static final String OPCION_SELECT_SI_A = "Sí";
	public static final String OPCION_SELECT_DICTAMINADO = "Dictaminado";
	public static final String OPCION_SELECT_ESTIMADO = "Estimado";
	public static final String CARPETA_OTROS = "Otros Documentos";
	public static final String PENA_CONTRACTUAL = "Penas contractuales";
	public static final String PENA_CONVENCIONAL = "Penas convencionales";
	public static final String ERROR = "ERROR";
	public static final String BOLSA = "Bolsa";
	public static final String FACTURA = "Factura";
	public static final String FACTURA_ARCHIVO = "_Factura_";
	public static final String NOTA_ARCHIVO = "_NotaCredito_";
	public static final String CON_DESGLOSE = "Con desglose";
	public static final String SIN_DESGLOSE = "Sin gesglose";
	public static final String CONTENT_TYPE_PDF = "application/pdf";
	public static final String CONTENT_TYPE_DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	private static final String [] ATRIBUTOS_SOLICITUD_PAGO = {"Id grupo servicio: " , "Id tipo notificacion pago: ", "Id solicitud pago: " , "Fecha solicitud: ", "Ruta solicitud pago", "Aplica convenio de colaboracion" };
	private static final String [] ATRIBUTOS_REFERENCIA_PAGO = {"Id referencia de pago: ","Id tipo notificacion pago: " , "Id solicitud pago: ", "Oficio notificacion pago: ", "Fecha notificacion pago: ", "Folio ficha pago: ", "Fecha pago: ", "Tipo cambio pagado: ", "Pagado nafin: ", "Ruta ficha nafin: ", "Id desglose: ", "Id factura: ", "Convenio colaboracion: "};
	private static final String [] ATRIBUTOS_GENERALES= {"Criterios: ", "Id estimación: ", "| Nombre corto del proyecto: ", "|Contrato: ", "|Id (concepto de servicio): ", "|Estatus: ", INICIAL, CANCELADO, OPCION_SELECT_ESTIMADO, "|proveedor: ", "|Id del dictamen:", "|Comprobante fiscal: "};
	private static final String [] ESTATUS_DICTAMEN= {INICIAL, OPCION_SELECT_DICTAMINADO,"Proforma","Facturado","Solicitud de pago","Pagado",CANCELADO};
	private static final String [] ESTATUS_ESTIMACION= {INICIAL, OPCION_SELECT_ESTIMADO,CANCELADO};
	public static final String FOTMATO_BUSQUEDA = "{\"estatus\": true, \"estatus\": \"%s\"}";
	public static final List<String> LISTA_CLAVE_UNIDAD = List.of("E48" , "ACT");
	public static final List<String> LISTA_UNIDAD = List.of("UNIDAD DE SERVICIO", "MON", "ACT");
	public static final List<String> LISTA_PAGOS = List.of("PUE", "PPD");
	public static final List<String> LISTA_FORMA_PAGO = List.of("03", "99");
	public static final List<String> LISTA_CONDICIONES_PAGO = List.of("");
	public static final List<String> LISTA_MONEDA = List.of("MXN", "USD");
	public static final List<String> LISTA_USO_CFDI = List.of("G02", "G03");
	

	
	public static String[] getAtributosDevengados() {
		return ATRIBUTOS_CONTRATO.clone();
	}
	
	public static String[] getEstatusDictamen() {
		return ESTATUS_DICTAMEN.clone();
	}
	
	public static String[] getEstatusEstimacion() {
		return ESTATUS_ESTIMACION.clone();
	}
	
	public static String[] getAtributosGenerales() {
		return ATRIBUTOS_GENERALES.clone();
	}
	
	public static String[] getAtributosDictamen() {
		return ATRIBUTOS_DICTAMEN.clone();
	}
	
	public static String[] getAtributosDictaminados() {
		return ATRIBUTOS_SERVICIOS_DICTAMINADOS.clone();
	}
	
	public static String[] getAtributosDictaminadosExportar() {
		return ATRIBUTOS_SERVICIOS_DICTAMINADOS_EXPORTAR.clone();
	}
	
	public static String[] getAtributosDeduccionesExportar() {
		return ATRIBUTOS_DEDUCCIONES_EXPORTAR.clone();
	}
	
	public static String[] getAtributosPenasContractualesExportar() {
		return ATRIBUTOS_PENAS_CONTRACTUAL_EXPORTAR.clone();
	}
	
	public static String[] getAtributosPenasConvencionalesExportar() {
		return ATRIBUTOS_PENAS_CONVENCIONAL_EXPORTAR.clone();
	}

	public static String[] getAtributosSolicitudPago() {
		return ATRIBUTOS_SOLICITUD_PAGO.clone();
	}

	public static String[] getAtributosReferenciaPago() {
		return ATRIBUTOS_REFERENCIA_PAGO.clone();
	}
	
	public static String[] getAtributosContractuales() {
		return ATRIBUTOS_PENAS_CONTRACTUALES.clone();
	}
	
	public static String[] getAtributosConvencionales() {
		return ATRIBUTOS_PENAS_CONVENCIOALES.clone();
	}
	
	public static String[] getAtributosDeducciones() {
		return ATRIBUTOS_DEDUCCION.clone();
	}
	
	public static String [] getAtributosSoporteDocumental() {
		return ATRIBUTOS_SOPORTE_DOCUMENTAL.clone();
	}
	
	public static String [] getAtributosSoporteDocumentalRegiActua() {
		return ATRIBUTOS_SOPORTE_DOCUMENTAL_REGI_ACTUA.clone();
	}
	
	public static String [] getAtributosDictaminado() {
		return ATRIBUTOS_DICTAMINADO.clone();
	}
	
	public static String [] getAtributosFactura() {
		return ATRIBUTOS_FACTURA.clone();
	}
	
	public static String [] getAtributosNotaCredito() {
		return ATRIBUTOS_NOTA_CREDITO.clone();
	}
	
	public static String [] getAtributosRegresarProforma() {
		return ATRIBUTOS_REGRESAR_PROFORMA.clone();
	}
	
	public static String [] getAtributosCancelarFactura() {
		return ATRIBUTOS_CANCELAR_FACTURA.clone();
	}
	
	public static String [] getAtributosCancelarNota() {
		return ATRIBUTOS_CANCELAR_NOTA.clone();
	}

}
