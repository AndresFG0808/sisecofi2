package com.sisecofi.catalogos.util.enums;

import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public enum ErroresEnum implements DefinitionMessage {

	ERROR_MSJ_SIN_USUARIO("001", "No se encontro usuario"),
	ERROR_AL_GUARDAR("002", "No se pudo guardar en el catalogo"),
	RRROR_AL_ACTUALIZAR("003", "No se pudo actualizar en el catalogo"),
	ERRO_CAMPO_NO_ENCONTRAFO("004", "El campo en la tabla no existe"),
	ERROR_AL_CONSUMIR_SERVICIO_PISTA("005","Error al consumir servicio pistas de audutoria"),
	ERROR_AL_OBTENER_ID_DE_CATALOGO("006","Error al obtener ID de catalogo"),
	ERROR_REGISTRO_NO_ENCONTRADO("007","Registro no encontrado"),
	ERROR_AL_GUARDAR_LA_PISTA("008","Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01)."),
	ERROR_AL_CONSULTAR_LA_PISTA("009","Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01)."),
	ERROR_AL_IMPRIMIR_LA_PISTA("010","Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01)."),
	ERROR_AL_BORRAR_LA_PISTA("011","Ocurrió un error al borrar la información, favor de intentar nuevamente (PA01)."),
	ERROR_AL_PISTA_DESCONOCIDO("012","Ocurrió un error, favor de intentar nuevamente (PA01)."),
	ERROR_MSJ_BUSQUEDA("013","No se encontró información relacionada con el catálogo "),
	ERRO_MSJ_REGISTRO_DUPLICADO("014","Registro duplicado. Intente nuevamente."),
	ERROR_MSJ_REPORTE("015","No se encotró información para el reporte"),
	ERROR_MSJ_ADMINISTRADORES("016","Solo puede haber un administrador activo"),
	ERROR_PK("017","Error en PK"),
	ERROR_FECHA_VIGENCIA("018", "La fecha fin de vigencia debe ser superior a la fecha de inicio de vigencia"),
	ERROR_CONSULTA_INVALIDA("019","La consulta es invalida.");
 
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

}
