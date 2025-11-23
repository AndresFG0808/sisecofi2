package com.sisecofi.proyectos.util;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class Constantes {

	private Constantes() {
	}

	public static final String ROL_CARGA = "Carga";
	public static final String ID_PROYECTO = "Id proyecto: ";
	public static final String PATH_BASE = "";
	public static final String NOMBRE_CORTO = "Nombre corto (Proyecto): ";
	public static final String PATH_BASE_INTERNO = "proyectos-internos";
	public static final String TITULOS_REPORTE_COMITE = "Contrato/Convenio, Contratos, Fecha de sesión, Comité, Afectación, Acuerdo,Vigencia, Sesión número, Sesion clasificacion, Monto autorizado [c/IVA], Tipo de moneda, Tipo de cambio, Monto en MXN, Comentarios";
	public static final String TITULOS_REPORTE_PROYECTOS = "Nombre corto del proyecto, Fecha inicio, Fecha fin, Líder de proyecto, Área solicitante, Área responsable, Monto solicitado, Estatus";
	private static final String [] ESTATUS_PROYECTO = {"Planeación","Inicial","Ejecución","Proceso de cierre","Cerrado","Cancelado"};
	private static final String [] ATRIBUTOS_PROYECTO = {ID_PROYECTO,"Nombre corto del proyecto: ", "Estatus anterior: ", "Estatus actual: ", "Id del documento", "Descripción"};
	private static final String [] ATRIBUTOS_ASOCIACION = {"Id de registro","Id de proyecto: ", "Nombre de plantilla: "};
	private static final String [] ATRIBUTOS_CIERRE = {"Nombre corto: ","Documento seleccionado: ", "Fase: "};
	private static final String [] ATRIBUTOS_CIERRE_GUARDADO = {NOMBRE_CORTO,"Área de planeación: ", "Fecha de entrega: "};
	private static final String [] ATRIBUTOS_CIERRE_CANCELAR = {NOMBRE_CORTO,"Estatus RCP: "};
	private static final String [] ATRIBUTOS_SAT_CLOUD = {ID_PROYECTO,"Nombre: "};
	private static final String [] ATRIBUTOS_VER_PDF = {NOMBRE_CORTO,"Documento seleccionado: "};
	private static final String [] PISTA_GENERAR_PLANTILLA = {NOMBRE_CORTO,"Tipo de plantilla: ", "Generación de archivo RCP"};
	public static final String ERROR_AL_CONSULTAR_EXEPCION = "Error al consultar: {}";
	public static final String LOG_CUERPO_DE_PETICION = "Cuerpo de la peticion: {}";
	public static final String VALIDACION_ESTATUS = "{\"estatus\": true}";
	public static final String ESTATUS_INICIAL = "{\"nombre\": \"Inicial\"}";
	public static final String ESTATUS_INICIAL_PK = "{\"idEstatusProyecto\": \"1\"}";
	public static final String ESTATUS_CANCELAR = "{\"nombre\": \"Cancelado\"}";
	public static final String ESTATUS_EN_PROCESO = "{\"nombre\": \"En proceso\"}";
	public static final String ESTATUS_AP = "{\"nombre\": \"Revisado por Área de Planeación\"}";
	public static final String ESTATUS_LP = "{\"nombre\": \"Validado por Líder de Proyecto\"}";
	public static final String TIPO_RCP = "{\"nombre\": \"RCP\"}";
	public static final String ESTATUS_ENTREGADO = "{\"nombre\": \"RCP entregado\"}";
	public static final String TITULO_REPORTE_PROYECTO_USUARIO="Estatus del proyecto,Nombre corto del proyecto,Nombre completo del proyecto,Id proyecto,Usuarios asignados";
	public static final String TITULOS_REPORTE_HISTORICO = "Lider del proyecto, Puesto, Correo, Fecha inicio, Fecha fin, Estatus";
	public static final String TITULOS_REPORTE_ALINEACION = "Mapa, Periodo, Objetivo";
	public static final String TITULOS_REPORTE_PROVEEDORES_PARTICIPANTES = "Id, Proveedor, Investigación mercado, Fecha IM, Respuesta IM, Fecha respuesta IM, Junta de aclaraciones, Fecha JA, Licitación pública, Fecha proposición LP, Comentario";
	public static final String COMITE_ELIMINADO = "Ok";
	public static final String ERROR_AL_ELIMINAR_COMITE = "El comite que desea eliminar contiene archivos asociados, eliminelos e intente de nuevo";
	public static final String BASE_JSON_ALINEACION = "{\"catAliniacion\":{\"idAliniacion\":%d}}";
	public static final String BASE_JSON_ADMON_CENTRAL = "{\"catAdmonGeneral\":{\"idAdmonGeneral\":%d}}";
	public static final String BASE_JSON_AREA_PLANEACION = "{\"catAdmonCentral\":{\"idAdmonCentral\":%d}}";
	public static final String BASE_JSON_MAPAS = "{\"nombre\":\"%s\",\"idMapaEstrategico\":%d}";
	public static final String TITULOS_REPORTE_ASOCIACION = "Fase, Plantilla, Fecha de asignación";
	public static final String FASE_NOMBRE = "{\"nombre\": \"%s\"}";
	public static final String PLANTILLA_COMITE = "plantilla-comite";
	public static final String PLANTILLA_COMITE2 = "Informacion de comite";
	public static final String VALIDACION_COMITE = "{\"nombre\":\"Información de comité\"}";
	public static final String BASE_FOLDER= "baseFolder";
	public static final String FASE_FOLDER= "faseFilesFolder";
	public static final String COMITE_FOLDER= "comiteFilesFolder";
	public static final String COMITE_PROYECTO_FOLDER = "comiteFilesFolderArchivos";
	public static final String ERROR_ACTUALIZAR_C = "Error al actualizar comite-proyecto: {}";
	public static final String ERROR_DETALLE_C = "Error al obtener detalle comite: {}";
	public static final String TIPO_PLANTILLA = "tipoPlantilla";
	public static final String TIPO_FASE = "tipoFase";
	public static final String TIPO_PROYECTO = "tipoProyecto";
	public static final String DECIMALES_INCORRECTOS= " , decimales incorrectos: {}";
	public static final String VALIDACION_PROYECTO_EJECUCION = "{\"nombre\":\"Ejecución\"}";
	public static final String FASES = "/FASES/";
	public static final String ADMON_CENTRAL = "{\"acronimo\": \"ACPPI\"}";
	public static final String ID_COMITE_PROYECTO = "Id comite proyecto: ";
	private static final String [] ESTATUS_DE_PROYECTO = {"Inicial","Planeación", "Ejecución", "Proceso de cierre", "Cerrado", "Cancelado"};
	private static final String [] DATOS_PROVEEDORES = {"Investigación mercado: ", "Fecha IM: ", "Respuesta IM: ", "Fecha respuesta IM: ", "Junta de aclaraciones: ", "Fecha JA: ", "Licitación pública: ", "Fecha proposición: ", "Proveedor: ", "Comentario: "};
	private static final String [] RESPUESTA = {"Sí", "No", "N/A", "Declinó","null"};

	public static final String ERROR = "ERROR";
	
	public static String[] getEstatus() {
		return ESTATUS_PROYECTO.clone();
	}
	
	public static String[] getDatosProveedor() {
		return DATOS_PROVEEDORES.clone();
	}
	
	public static String[] getRespuesta() {
		return RESPUESTA.clone();
	}
	
	public static String[] getEstatusProyecto() {
		return ESTATUS_DE_PROYECTO.clone();
	}
	
	public static String[] getAtributosProyecto() {
		return ATRIBUTOS_PROYECTO.clone();
	}
	
	public static String[] getAtributosAsociacion() {
		return ATRIBUTOS_ASOCIACION.clone();
	}
	
	public static String[] getAtributosCierre() {
		return ATRIBUTOS_CIERRE.clone();
	}
	
	public static String[] getAtributosCierreGuardado() {
		return ATRIBUTOS_CIERRE_GUARDADO.clone();
	}
	
	public static String[] getAtributosCierreCancelar() {
		return ATRIBUTOS_CIERRE_CANCELAR.clone();
	}
	
	public static String[] getAtributosCierreSatCloud() {
		return ATRIBUTOS_SAT_CLOUD.clone();
	}
	
	public static String[] getAtributosVerPdf() {
		return ATRIBUTOS_VER_PDF.clone();
	}
	
	public static String[] getPistaGenerarPlantilla() {
		return PISTA_GENERAR_PLANTILLA.clone();
	}
}