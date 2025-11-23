package com.sisecofi.admindevengados.util.enums;

import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public enum ErroresEnum implements DefinitionMessage {

	ERROR_MSJ_SIN_USUARIO("001", "No se encontro usuario"),
	ERROR_AL_GUARDAR("002", "No se pudo guardar en el catalogo"),
	ERROR_GUARDAR_PISTA("003", "Ocurrió un error al guardar la información, favor de intentar nuevamente (PA01)."),	
	ERROR_PERIODO_INICIO("004", "El periodo de inicio es incorrecto. Por favor verifique la información."),
	ERROR_PERIODO_FIN("005", "El periodo de inicio es incorrecto. Por favor verifique la información."),
	MSJ_NOT_FOUND_DICTAMEN("006", "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01). "),
	ERROR_GUARDAR_PISTA_PRNT("007","Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01)."),
	DICTAMEN_EXPORTAR_EXCEL("08","Resumen dictaminado exportado exitosamente."),
	DICTAMEN_CREADO("09","Dictamen creado con exito"),
	DICTAMEN_OBTENIDO("10","Dictamen obtenido exitosamente."),
	DICTAMEN_ACTUALIZADO("11","Dictamen actualizado con exito"),
	DICTAMEN_CONTRATO_OBTENIDO("12","Dictamenes del contrato obtenidos exitosamente."),
	DICTAMEN_SIGUIENTE_OBTENIDO("13","Dictamen siguiente obtenido exitosamente."),
	CONTRATO_OBTENIDO("14","Contrato obtenido exitosamente."),
	DICTAMINADO_REGISTRO("15","Se registraron los servicios correctamente."),
	DICTAMINADO_ESTRUCTURA_INCORRECTA("16","Estructura Incorrecta"),
	DICTAMEN_RESUMEN_NOT_FOUND("17","No se encontraron registros"),
	ESRIMACION_OBTENIDA("18","Estimación obtenida exitosamente."),
	ESTIMACION_ACTUALIZADA("19","Se actualizó el registro de los servicios de acuerdo con la estimación seleccionada."),
	PENA_CONTRACTUAL_REGISTRO("20","Se guardaron los datos de forma correcta."),
	ERROR_AL_CONSULTAR_CATALOGOS("021", "Ocurrio un error al consultar los catalogos"),
	PENA_CONTRACTUAL_OBTENIDO("022","Pena contractual obtenida"),
	PENA_DOCUMENTO_NOT_FOUND("023","No se encontraron registros en la sección"),
	DICTAMEN_ANTERIOR_OBTENIDO("024","Dictamen anterior obtenido exitosamente."),
	DICTAMEN_RESUMEN_CONSOLIDADO("025","Resumen generado exitosamente."),
	ERROR_AL_GUARDAR_ARCHIVO("025", "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01)."),
	ERROR_AL_CONSULTAR("026", "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01)."),
	REGISTRO_NO_ENCONTRADO("027","Registro no encontrado"),
	ERROR_EXTENSION_EXCEL("028","El archivo seleccionado no contiene la extensión .xlsx. Favor de seleccionar un archivo con la extensión correcta."),
	ERROR_EXTENSION_ZIP("029","El archivo seleccionado no contiene la extensión .zip. Favor de seleccionar un archivo con la extensión correcta."),
	ERROR_EXTENSION_PDF("030","El archivo seleccionado no contiene la extensión .pdf. Favor de seleccionar un archivo con la extensión correcta."),
	ERROR_GUARDAR_ARCHIVO("031","Error al guardar archivo"),
	PENA_CONVENCIONAL_OBTENIDA("032","Pena convencional obtenida"),
	SOPORTE_DOCUMENTAL_REGISTRO("033","Se guardaron los datos de forma correcta."),
	ERROR_FECHA_RECEPCION_MENOR_FECHA_SOLICITUD("034","La estructura de la información ingresada es incorrecta. Intente nuevamente."),
	FALTA_INFORMACION_SOPORTE_DOCUEMNTAL("035","Falta información en soporte documental."),
	ERROR_AL_DESCARGAR_ARCHIVO("036","Ocurrió un error al descargar el archivo, favor de intentar nuevamente."),
	TIPO_CAMBIO_VALIDADO("036","Se valido el tipo de cambio referencial"),
	FACTURA_GUARDADA("037","Los datos de la factura se guardaron satisfactoriamente. "),
	VALIDAR_FACTURAS_RECIBIDAS("038","Se valido correctamente las facturas recibidas"),
	EXTENSION_XML("039","La extensión del archivo no es correcta. Favor de verificar y cargar el XML de la factura."),
	XML_CAMPO_OBLIGATORIO("040","Campo obligatorio *."),
	CLAVE_E48("041","La ClaveUnidad debe ser 'E48'"),
	CLAVE_RPDO_SERV_INVALIDAD("042","La ClaveProdServ es invalida."),
	FOLIO_COMPROBANTE_REGISTRADO("043","El comprobante fiscal y/o folio ya se encuentra registrado para este proveedor."),
	PERIODO_ESTIMACION_INCORRECTO("038","El periodo seleccionado es incorrecto."),
	ESTIMACION_REPETIDA("039","Ya existe una estimación, favor de verificar los datos."),
	ERROR_USUARIO_NO_ENCONTRADO("040", "Ocurrió un error al tratar de obtener la sesion."),
	PROVEEDOR_NO_COINCIDE("041","La factura no corresponde a los datos del proveedor del contrato."),
	ESTIMACION_NO_ENCONTRADA("041","La estimacion que deseas consultar no existe."),
	ID_INCORRECTO("042","El id no tiene el formato correcto."),
	SELECCIONA_CANTIDAD_MONTO("043","Por favor selecciona una estimacion valida."),
	MONTO_MAYOR_TOTAL_PESOS("044","El monto ingresado es mayor al total de la factura, favor de verificar. "),
	EXTENSION_PDF("045","La extensión del archivo no es correcta. Favor de verificar y cargar archivo con extensión PDF."),
	SERVICIO_NO_ENCONTRADO("046","El servicio no existe."),
	SERVICIOS_NO_SELECCIONADOS_CON_VALORES("047","Existe(n) {número de servicios} servicio(s) con valores. ¿Desea conservarlos? "),
	DATOS_OBLIGATORIOS("048",ErroresEnum.MensajeValidation.MSJ),
	CONTRATO_SIN_FECHAS("047","El contrato no tiene fechas registradas."),
	FACTURA_NOT_FOUND("048","Factura no encontrada."),
	CONTRATO_NO_ENCONTRADO("049","El contrato no existe."),
	FALTAN_FECHAS_OBLIGATORIAS("050","Faltan fechas obligatorias"),
	ERROR_NO_ARCHIVOS_PDF("051","Los archivos contenidos no estan en formato pdf."),
	EXISTE_SOPORTE_DOCUMENTAL_DICTAMEN("052","Existe soporte documental en el dictamen"),
	APLICA_CC("053","Se valido correcto el convenio de colaboracion del contrato"),
	ESTRUCTUTA_CFDI_INCORRECTA("054","La estructura de los datos no es correcta de acuerdo al anexo 20, Favor de validar."),
	FOLIO_COMP_UTILIZADO_OTRO_CONTRATO("055","El comprobante fiscal del archivo seleccionado ya fue utilizado previamente, favor de verificarlo."),
	PROVEEDOR_MO_COINCIDE_CONTRATO_DICTAMEN("056","El comprobante fiscal no corresponde al RFC y/o razón social del proveedor del contrato-dictamen, favor de verificarlo."),
	NOTA_CANCELADA("057","La nota de crédito ha sido cancelada exitosamente."),
	ERROR_REGISTRO_FACTURA("058","No se pueden asociar más de dos registros con el mismo id_factura."),
	FACTURA_NOTA_EXISTE("059","Debe cancelar las facturas y/o notas de crédito."),
	EXISTEN_FACTURAS_NOTAS("060","Para regresar el estatus a proforma debe cancelar las Facturas y/o Notas de crédito."),
	FACTURA_NO_ENCONTATA("061", "Factura no encontrada"),
	PENA_CONTRACTUAL_NOT_FOUND("062","Pena contractual no en encontrada"),
	PENA_CONVENCIONAL_NOT_FOUND("063","Pena convencional no en encontrada"),
	DEDUCCIONES_NOT_FOUND("064","Deduccion no en encontrada"),
	ERROR_ASIGNAR_PLANTILLA("065", "Ocurrió un error al asignar la plantilla"),
	ERROR_FACTURAS_NO_ENCONTRADAS("066", "No se encontraron facturas"),
	ERROR_ARCHIVO_NO_ENCONTRADO("067", "El archivo correspondiente a este registro no se encuentra en la plantilla."),
	ERROR_ESTATUS_SOLICITUD_NO_ENCONTRADO("068", "El estatus 'Solicitud de pago' no se encontro."),
	ERROR_FORMATO_PDF("069", "Debe ingresar un archivo PDF, favor de verificar."),
	MONEDA_DISTINTA("070","La moneda definida en el archivo XML, no corresponde con la sección “vigencia y montos” del contrato"),
	ERROR_SOLICITUD_FACTURA_NO_ENCONTRADO("071", "No se encontró la solicitud de factura"),
	ERROR_SOLICITUD_FACTURA_FECHA_RECEP_MENOR("072", "La fecha de recepción ingresada debe ser mayor o igual a la fecha de solicitud. Por favor verifique el dato y vuelva a intentarlo."),
	ERROR_SOLICITUD_FACTURA_EXIST("073", "La solicitud ya existe"),
	ERROR_BUSCAR_CAT_TIPODESCUENTO("070", "No existe el Tipo de descuento en el catalogo"),
	ERROR_CONTRATO_VACIO("45", "el Id dictamen no existe"),
	ERROR_BUSCAR_DDP("046", "No se encontro ninguna deduccion, descuento o penalizacion"),
	ERROR_PERIODO_CONTROL("047","El periodo de control debe estar dentro del periodo de inicio y periodo fin del contrato."),
	DOCUMENTO_REGISTRADO("048","Se guardaron los datos de forma correcta"),
	CARGA_EXITOSA("049", "Archivo cargado con exito"),
	ARCHIVO_NOCARGADO("050", "No se cargo ningún archivo"),
	SECCION_ERRONEA_NOTA("051", "El documento ingresado no corresponde a una nota de crédito válida, favor de verificar"),
	SECCION_ERRONEA_FACTURA("052", "El documento ingresado no corresponde a una factura válida, favor de verificar"),
	VALIDAR_WEB_SERVICE("053","Mensaje de rechazo"),
	NO_HAY_ARCHIVO("054","No se puede regresar el estatus a proforma, este no contiene un archivo asociado"),
	NO_HAY_ARCHIVO_ESTATUS_PAGADO("055","No se puede actualizar el estatus sin un archivo"),
	CONEXION_PERDIDA("056", "Se perdió la conexión con Sat Cloud, favor de intentar nuevamente"),
	ERROR_GENERAL("057","Error general"),
	NO_HAY_ARCHIVO_PROFORMA("058","No se encontro el archivo"),
	ERROR_SERVICIO_INTERNO("059", "Error al consultar servicio interno"),
	ERROR_SERVICIO_INEXISTENTE("060", "Error al guardar servicio estimado"),
	ERROR_LEER_ZIP("061", "Error al leer el archivo ZIP"),
	ZIP_CORRUPTO("062", "El zip esta corrupto"),
	ARCHIVOS_NO_PDF("063", "Los archivos adjuntos deben contener la extensión .pdf, favor de verificar"),
	PROVEEDOR_NO_ENCONTRADO("064", "No se encontro al proveedor seleccionado"),
	NO_EXISTE_INFORMACION("065","No existe información del dictamen."), 
	PROYECTO_NO_ENCONTRADO("066","El proyecto no existe."),
	UUID_PDF_NO_CORRESPONDE("067","El comprobante fiscal del PDF adjuntado no corresponde al comprobante fiscal del XML, favor de ajuntar el correcto."),
	ERROR_LEER_PDF("068","Error al leer el archivo PDF"),
	NO_FOUND_ESTATUS("069","No se encontró el estatus proforma en el catálogo."),
	ESTATUS_PROFORMA("070", "El dictamen debe estar en estatus PROFORMA para poder marcarlo como PAGADO."),
	ESTATUS_PAGDO_NO_FOUND("071","No se encontró el estatus pagado en el catálogo."),
	ERROR_FLUJO_WEB_SERVICE("072","Ocurrio un error al consultar el CFDI.");


	
	private String clave;
	private String message;

	ErroresEnum(String clave, String message) {
		this.clave = clave;
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getClave() {
		return clave;
	}
	
	public static class MensajeValidation {

		private MensajeValidation() {
			throw new IllegalStateException("Utility class");
		}

		public static final String MSJ = "Favor de ingresar los datos obligatorios marcados con un asterisco (*).";
		public static final String MSJ_NOT_FOUND_DICTAMEN = "No se encontro un dictamen.";

	}

}
