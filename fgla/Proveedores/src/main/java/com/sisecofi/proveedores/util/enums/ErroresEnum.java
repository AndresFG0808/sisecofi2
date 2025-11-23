package com.sisecofi.proveedores.util.enums;

import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public enum ErroresEnum implements DefinitionMessage {

	ERROR_MSJ_SIN_USUARIO("001", "No se encontraron el proveedor"),
	ERROR_AL_GUARDAR("002", "Ocurrió un error al guardar el registro de proveedor, favor de intentar nuevamente"),
	ERROR_DATOS_OBLIGATORIOS("003", ErroresEnum.MensajeValidation.MENSAJE),
	ERROR_NOMBRE_DUPLICADO("004", "El Nombre del proveedor ya se encuentra almacenado en la BD"),
	ERROR_RFC_DUPLICADO("005", "El RFC capturado ya lo tiene registrado el(los) proveedor(es):"),
	ERROR_AL_CONSUMIR_SERVICIO_PISTA("006", "Error al consumir el servicio pistas de auditoria"),
	ERROR_CARACTERES_RFC("007", ErroresEnum.MensajeValidation.RFC_VALIDACION),
	ERROR_GIROEMPRESARIAL_DUPLICADO("008", "El giro empresarial ya se encuentra almacenado en la BD"),
	ERROR_AL_GUARDAR_GIRO_EMPRESARIAL("009", "Ocurrió un error al guardar el giro empresarial, favor de intentar nuevamente"),
	ERROR_AL_BUSCAR_ID_GIRO_EMPRESARIAL("010","No se encontro el ID del giro empresarial"),
	ERROR_AL_CONSULTAR_CATALOGO_GIRO_EMPRESARIAL("011", "Ocurrió un error al consultar la información del giro empresarial, favor de intentar nuevamente "),
	ERROR_AL_ELIMINAR_GIRO_EMPRESARIAL("012", "Ocurrió un error al eliminar el giro empresarial, favor de intentar nuevamente"),
	ERROR_ACTIVO("013", ErroresEnum.MensajeValidation.ACTIVO_VALIDACION),
	ERROR_ACTUALIZAR_GIRO("014", "Ocurrió un error al actualizar el giro empresarial, favor de intentar nuevamente"),
	ERROR_FORMATO("015", ErroresEnum.MensajeValidation.CORREO_FORMATO),
	ERROR_NOMBRE_CONTACTO_DUPLICADO("016", "El nombre de contacto ya se encuentra almacenado en la BD"),
	ERROR_TELEFONO_DUPLICADO("017","El teléfono particular ya se encuentra almacenado en la BD"),
	ERROR_AL_GUARDAR_DIRECTORIO("018","Ocurrió un error al guardar el directorio de contacto, favor de intentar nuevamente"),
	ERROR_MSJ_SIN_DIRECTORIO("019", "No se encontraron registros del directorio de contacto"),
	ERROR_IDAGS_INCOREECTO("020", ErroresEnum.MensajeValidation.PATRON),
	ERROR_IDAGS_DUPLICADO("021", "El ID AGS ya se encuentra almacenado en la BD"),
	ERROR_ACTUALIZAR_PROVEEDOR("022", "Ocurrió un error al actualizar el proveedor, favor de intentar nuevamente"),
	ERROR_ACTUALIZAR_DIRECTORIO("023", "Ocurrió un error al actualizar el directorio de contacto, favor de intentar nuevamente"),
	ERROR_AL_CONSULTAR_DIRECTORIO("024","Ocurrió un error consultar la información del directorio de contacto, favor de intentar nuevamente"),
	ERROR_PAGINADOR("025", "Error en el tamanio del paginador"),
	ERROR_TITULOSERVICIO_DUPLICADO("026", "El título de servicio ya se encuentra almacenado en la BD"),
	ERROR_AL_GUARDAR_TITULOSERVICIO("027", "Ocurrió un error al guardar el título de servicio, favor de intentar nuevamente"),
	ERROR_AL_BUSCAR_ID_TITULOSERVICIO("028","No se encontro el ID título de servicio"),
	ERROR_ACTUALIZAR_TITULOSERVICIO("029", "Ocurrió un error al actualizar el título de servicio, favor de intentar nuevamente"),
	ERROR_SEMAFOROESTATUS_DUPLICADO("030", "El estatus ya se encuntra almacenado en la BD"),
	ERROR_AL_GUARDAR_SEMAFOROESTATUS("031", "Ocurrió un error al guardar el estatus, favor de intentar nuevamente"),
	ERROR_AL_BUSCAR_ID_SEMAFOROESTATUS("032","No se encontro el ID del estatus"),
	ERROR_AL_ACTUALIZAR_SEMAFOROESTATUS("033", "Ocurrió un error al actualizar el estatus, favor de intentar nuevamente"),
	ERROR_NUMEROTITULO_DUPLICADO("034", "El numero de título ya se encuentra almacenado en la BD"),
	ERROR_ALGUADAR_TITULO("035", "Ocurrió un error al guardar el título de servicio del proveedor, favor de intentar nuevamente"),
	ERROR_AL_BUSCAR_TITULO("036","No se encontraron registros de titulos de servicio de proveedores"),
	ERROR_COLORSEMAFORO_DUPLICADO("037", "El color del estatus ya se encuntra almacenado en la BD"),
	ERROR_AL_ACTUALIZAR_TITULOPROVEEDOR("038", "Ocurrió un error al actualizar el titulo de servicio del proveedor, favor de intentar nuevamente"),
	ERROR_AL_CONSULTAR_TITULO("039","Ocurrió un error consultar la información de los titulos de servicio de proveedor, favor de intentar nuevamente"),
	ERROR_RESULTADO_DUPLICADO("040", "El resultado dictamen técnico ya se encuentra almacenado en la BD"),
	ERROR_AL_GUARDAR_RESULTADO("041", "Ocurrió un error al guardar resultado dictamen técnico, favor de insertar nuevamente"),
	ERROR_AL_BUSCAR_RESULTADO("042", "No se encontraron catalogos resultado de dictamen técnico"),
	ERROR_AL_GUARDAR_DICTAMEN("043", "Ocurrió un error al guardar el dictamen técnico, favor de intentar nuevamente"),
	ERROR_NOMBRECORTO_TITULODUPLICADO("044", "El nombre corto del título de servicio ya se encuentra almacenado en la BD"),
	ERROR_AL_GUARDAR_LA_PISTA("045","Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01)."),
	ERROR_AL_CONSULTAR_LA_PISTA("046","Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01)."),
	ERROR_AL_IMPRIMIR_LA_PISTA("047","Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01)."),
	ERROR_AL_PISTA_DESCONOCIDO("048","Ocurrió un error, favor de intentar nuevamente (PA01)."),
	ERROR_AL_ACTUALIZAR_DICTAMEN("049", "Ocurrió un error al actualizar el dictamen técnico, favor de intentar nuevamente"),
	ERROR_AL_BUSCAR_DICTAMEN("050","No se encontraron registros de dictamen técnico "),
	ERROR_SIN_USUARIO("051", "No se encontro el usuario"),
	ERROR_AL_EXPORTAR_EXCEL_DICTAMEN("052","Error al exportar dictamen tecnico, favor de intentar nuevamente"),
	ERROR_AL_EXPORTAR_EXCEL_PROVEEDORES("053", "Error al exportar reporte excel proveedores general"),
	ERROR_FILTRADO_PROVEEDORES("054", "No se encontraron registros de proveedores "),
	ERROR_AL_CONSULTAR_CATALOGOS("055", "Ocurrio un error al consultar los catalogos"),
	ERROR_AL_EXPORTAR_DIRECTORIO("056", "Error al exportar el directorio contacto"),
	ERROR_MSJ_CATALOGO_NO_ENCONTRADO("057","Catalogo no encontrado");


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
	
	
	public static class MensajeValidation{
		
		private MensajeValidation() {
			throw new IllegalStateException("Utility class");
		}
		
		public static final String MENSAJE = "Favor de ingresar los datos obligatorios marcados con un asterisco (*).";
		public static final String RFC_VALIDACION = "El RFC no tiene el formato correcto";
		public static final String ACTIVO_VALIDACION = "El campo debe estar activo o desactivado";
		public static final String CORREO_FORMATO = "El correo eletrónico debe tener un formato válido";
		public static final String PATRON = "El dato ingresado en “Id AGS” es incorrecto, solo valores numericos";
		
		
	}
	
	

}
