package com.sisecofi.contratos.util;


/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class Constantes {

	private Constantes() {
	}

	private static final String ATTR_CONTRATO_ID = "Id de contrato: ";
	private static final String ATTR_CONVENIO_MODIFICATORIO_ID = "Id convenio modificatorio: ";
	private static final String ATTR_ID_CONTRATO = "Id contrato: ";

	public static final String ROL_CARGA = "Carga";
	
	public static final String ACUERDO = "Acuerdo: ";
	public static final String INICIAL = "Inicial";
	public static final String EJECUCION = "Ejecución";

	public static final String PATH_BASE = "";
	public static final String PATH_BASE_CONVENIO = "convenios-modificatorios";
	public static final String PATH_BASE_INTERNO = "admin-contratos-internos";
	public static final String BASE_ERROR_VOLUMETRIA = "Verifique el layout de carga, ya que la línea(s) [%s] no cumple con la “Cantidad de servicios máxima”";
	public static final String BASE_ERROR_VOLUMETRIA_CONVENIO = "Verifique el layout de carga, ya que la línea(s) [%s] sobrepasa el “Número total de servicios”.";
	public static final String TITULOS_REPORTE_CONTRATO = "Id, Nombre del contrato, Nombre del proyecto,Número de contrato, Proveedor, Tipo de procedimiento," +
			" Inicio,  Término,Último CM, Monto máximo, Monto máximo último CM, Monto en pesos, Administración central, Administrador del contrato";
	public static final String ESTATUS_CONTRATO_CANCELADO = "{\"nombre\":\"Cancelado\"}";
	public static final String ESTATUS_CONTRATO_CERRADO= "{\"nombre\":\"Cerrado\"}";
	public static final String ESTATUS_CONTRATO_INICIAL = 	"{\"nombre\":\"Inicial\"}";

	public static final String ESTATUS_CONTRATO_EJECUCION = "{\"nombre\":\"Ejecución\"}";
	public static final String VALIDACION_ESTATUS = "{\"estatus\": true}";

	public static final String CONSULTA_VIGENCIA_MONTOS = "{consulta: vigencia y montos general}";
	public static final String TITULOS_REPORTE_SERVICIO_CONTRATO = "Id, Grupo, Tipo de consumo, Clave productos y servicios,Conceptos de servicio, Tipo de unidad," +
			"Precio unitario, Cantidad de servicios mínima, Cantidad de servicios máxima, Monto mínimo,Monto máximo, Aplica IEPS";
	public static final String SUMA_INVALIDA = "La sumatoria de los campos “Monto máximo” de la tabla “Registro de servicios” no es igual al campo “Monto máximo en pesos sin impuestos” de la sección “Vigencia y montos”. Favor de verificar los datos ingresados.";
	public static final String SUMA_VALIDA = "El Monto máximo de los servicios coincide con el Monto máximo sin impuestos del contrato.";
	public static final String BASE_FOLDER = "contratos";
	public static final String LAYOUT_FOLDER = "layout";
	public static final String TITULOS_REPORTE_ATRASO_PRESENTACION = "Id, Descripción, Fecha de prestación, Penas y/o deducciones aplicables";
	public static final String TITULOS_REPORTE_INFORMES_DOCUMENTALES_UNICA_VEZ = "Id, Fase, Informe documental, Fecha de entrega, Penas y/o deducciones aplicables, Descripción";
	public static final String TITULOS_REPORTE_PARTICIPANTES_CONTRATO = "Id, Responsabilidad, Administración general, Administración central, Nombre del servidor público, Teléfono, Correo, Fecha de inicio, Fecha de término, Vigente";
	public static final String SIN_INFORMACION_EXCEL = "No se puede generar el archivo Excel porque no hay informacion disponible";
	public static final String TITULOS_REPORTE_INFOMES_DOCUMENTALES_PERIODICOS = "Id, Informe documental, Periodicidad, Pena convencional deductiva, Descripción";
	public static final String TITULOS_REPORTE_INFORMES_DOCUMENTALES_SERVICIOS = "Id, Informe documental, Periodicidad, Fecha de entrega, Penas y/o deducciones aplicables, Descripción";
	public static final String TITULOS_REPORTE_PENAS_CONTRACTUALES = "Id, Informe/Documento/Concepto de servicio, Descripción, Plazo de entrega, Pena aplicable";
	public static final String TITULOS_REPORTE_DICTAMENES_ASOCIADOS= "Id dictamen, Periodo de control, Periodo inicio, Periodo fin, Estatus, Monto, Monto en pesos, Tipo de cambio referencial";
	public static final String TITULOS_REPORTE_FACTURAS_ASOCIADAS= "Id dictamen, Comprobante fiscal, Convenio de colaboración, Monto, Monto en pesos, Estatus, Tipo de cambio";
	public static final String TITULOS_REPORTE_NIVELES_SERVICIO_SLA = "Id, SLA, Deducciones aplicables, Objetivo mínimo, Descripción";
	public static final String TITULOS_REPORTE_REINTEGROS_ASOCIADOS = "Id, Tipo, Importe, Interes, Total, Fecha de reintegro, Importes, Intereses, Totales";
	public static final String TIPO_PLANTILLA = "tipoPlantilla";
	public static final String TIPO_FASE = "tipoFase";
	public static final String TIPO_CONTRATO = "tipoContrato";
	public static final String TIPO_CONVENIO_T = "tipoConvenio";
	public static final String TIPO_FASE_CONVENIO = "tipoFaseConvenio";
	public static final String TIPO_PLANTILLA_CONVENIO = "tipoPlantillaConvenio";
	public static final String TIPO_REINTEGRO = "tipoReintegro";
	public static final String TIPO_FASE_REINTEGRO = "tipoFaseReintegro";
	public static final String TIPO_PLANTILLA_REINTEGRO = "tipoPlantillaReintegro";
	public static final String FASE_NOMBRE = "{\"nombre\": \"%s\"}";
	public static final String BASE_FOLDER_COTRATOS = "baseFolder";
	public static final String FASE_FOLDER= "faseFilesFolder";
	public static final String TITULOS_REPORTE_CONVENIO_MODIFICATORIO ="Numero de convenio, Tipo, Fecha de firma, Fecha fin, Monto máximo";
	public static final String PERIODICIDAD_BUSQUEDA = "{\"nombre\":\"%s\"}";
	private static final String [] TIPO_CONVENIO = {"alcance","monto","tiempo","administrativo"};
	public static final String  TITULO_REINTEGROS = "Nombre corto del contrato, No, Tipo, Importe, Interés, Total, Fecha de reintegro";
	public static final String TITULOS_REPORTE_SERVICIOS_CONVENIO = "Id, Tipo de consumo, Conceptos de servicio, Número de servicios máximos, Monto máximo, Precio unitario, Compensación (Número de servicios), Compensación (Monto), Incremento (Número de servicios), Incremento (Monto), Número total de servicios, Monto máximo total, Aplica IEPS";
	public static final String ERROR = "ERROR";
	public static String[] getTipoConvenio() {
		return TIPO_CONVENIO.clone();
	}
	public static final String VALIDACION_RESPONSABILIDAD = "{\"nombre\":\"Administrador del contrato\"}";
	public static final String INICIO_ENCABEZADOS_CASO_NEGOCIO = "Id(registro de servicios) , Conceptos de servicio";
	private static final String [] ATRIBUTOS_CONTRATO = {ATTR_CONTRATO_ID , "Numero de contrato: ", "Nombre corto: " , "Id proyecto"};
	private static final String [] ATRIBUTOS_CONTRATO_DTO = {ATTR_CONTRATO_ID , "Numero de contrato: ", "Nombre: ", "Nombre corto: " , "Id proyecto: ", "Administrador: ", "Veridicador: ", "Tipo procedimiento:", "Fecha inicio:", "Fecha fin: ", "Monto  maximo: " , "Monto en pesos:", "Monto maximo ultmimo Cm:", "Ulitmo convenio modificatorio: ", "Iva: ","Tipo cambio: ", "Nombre proyecto: ", "Estatus contrato: ", "Grupo servicio: ", "Conseptos servicio: ", "Unidad medida: ", "Tipo consumo: ", "Precio unitario :", "Cantidad servicios vigente: ", "Monto maximo servicio: ", "Tipo moneda: ", ACUERDO};
	private static final String [] ATRIBUTOS_CONVENIO_MODIFICATORIO = {ATTR_ID_CONTRATO , ATTR_CONVENIO_MODIFICATORIO_ID};
	private static final String [] ATRIBUTOS_REGISTRO_CONVENIO_MODIFICATORIO = {ATTR_ID_CONTRATO , ATTR_CONVENIO_MODIFICATORIO_ID, "Id registro de servicio: "};
	private static final String [] ATRIBUTOS_PLANTILLA_CONVENIO_MODIFICATORIO = {ATTR_CONVENIO_MODIFICATORIO_ID, "Asignar plantilla: "};
	public static final String CONTRATO_FOLDER = "ContratosFilesFolder";
	private static final String [] ATRIBUTOS_GENERALES = {ATTR_ID_CONTRATO, "Id convenio: ", "Id reintegro"};
	public static final String VALIDACION_RESPONSABILIDAD_VERIFICADOR = "{\"nombre\":\"Verificador del contrato\"}";
	public static final String TIPO_CONSUMO_BOLSA = "{\"nombre\":\"Bolsa\"}";
	public static final String TIPO_VOLUMETRIA = "Volumetría";
	public static final String TIPO_BOLSA = "Bolsa";
	public static final String TIPO_CONTRATO_INICIAL = INICIAL;
	private static final String [] ATRIBUTOS_DATOS_GENERALES_CONTRATO = {"Id datos generales:",ATTR_CONTRATO_ID ,"Numero contrato: ", "Numero de contrato compra net: ", ACUERDO, "Id tipo procedimiento: ","Numero procedimiento: ", "Convenio colaboracion: ", "id dominios tecnologicos:" , "id fondeo contrato: ", "Objetivo servicio: ", "Alcance servicio: ", "Titulo servicio: "};
	private static final String [] ATRIBUTOS_PARTICIPANTES_CONTRATO = {"Id de participante contrato: ",ATTR_ID_CONTRATO  , "Id responsabilidad: ", ATTR_ID_CONTRATO , ACUERDO, "Id admon general: ","Id usuario: ", "Id servidor publico: ", "fecha inicio:" , "fecha termino: ", "vigente: "};
	private static final String [] ATRIBUTOS_VIGENCIA_MONTOS_CONTRATO = {"Id vigencia monto: " , ATTR_ID_CONTRATO, "Fecha inicio vigencia servicios: " , "Fecha fin vigencia servicios: ", "Fecha inicio vigencia contrato: ","Fecha din vigencia contrato: ", "Id tipo moneda: ", "Fecha inicio:" , "TIpo cambio maximo: ", "Monto minimo sin impuestos: ", "Monto maximo sin impuestos:", "Monto en pesos sin impuestos:", "Monto minimo con impuestos:", "Monto maximo con impuestos:", "Monto maximo con impuestos", "Monto en pesos con impuestos", " id iva:", " Id ieps:" };
	private static final String [] ATRIBUTOS_GRUPO_SERVICIO = {"Id grupo servicio: " , ATTR_ID_CONTRATO, "Grupo: " , "Id tipo consumo: " };
	private static final String [] ATRIBUTOS_BUSCAR_CONTRATO = {"Estatus del contrato: " , " |Fecha inicio: ", " |Fecha termino: " , " |Proveedor: ", " |Administracion central: " };
	private static final String [] ESTATUS_CONTRATO = {INICIAL , EJECUCION, "Cerrado" , "Cancelado"};
	public static final String CARPETA_EJECUCION = EJECUCION;
	public static final String BASE_JSON_ADMON_CENTRAL = "{\"catAdmonGeneral\":{\"idAdmonGeneral\":%d}}";
	public static final String BASE_JSON_ADMONISTRADORES_CENTRALESL = "{\"catAdmonCentral\":{\"idAdmonCentral\":%d}}";


	public static String[] getAtributosGenerales() {
		return ATRIBUTOS_GENERALES.clone();
	}
	
	public static String[] getEstatusContrato() {
		return ESTATUS_CONTRATO.clone();
	}

	public static String [] getAtributosContrato() {
		return ATRIBUTOS_CONTRATO.clone();
	}

	public static String [] getAtributosContratoDto() {
		return ATRIBUTOS_CONTRATO_DTO.clone();
	}

	public static String [] getAtributosDatosGeneralesContrato() {
		return ATRIBUTOS_DATOS_GENERALES_CONTRATO.clone();
	}

	public static String [] getAtributosParticipantesContrato() {
		return ATRIBUTOS_PARTICIPANTES_CONTRATO.clone();
	}

	public static String [] getAtributosVigenciaMontosContrato() {
		return ATRIBUTOS_VIGENCIA_MONTOS_CONTRATO.clone();
	}


	public static String [] getAtributosConvenioModificatorio() {
		return ATRIBUTOS_CONVENIO_MODIFICATORIO.clone();
	}
	
	public static String [] getAtributosRegistroConvenioModificatorio() {
		return ATRIBUTOS_REGISTRO_CONVENIO_MODIFICATORIO.clone();
	}
	
	public static String [] getAtributosPlantillaConvenioModificatorio() {
		return ATRIBUTOS_PLANTILLA_CONVENIO_MODIFICATORIO.clone();
	}
	public static String [] getAtributosGrupoServicioContrato() {
		return ATRIBUTOS_GRUPO_SERVICIO.clone();
	}

	public static String [] getAtributosBuscarContratos() {
		return ATRIBUTOS_BUSCAR_CONTRATO.clone();
	}

}
