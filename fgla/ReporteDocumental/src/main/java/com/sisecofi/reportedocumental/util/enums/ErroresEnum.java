package com.sisecofi.reportedocumental.util.enums;

import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public enum ErroresEnum implements DefinitionMessage {

	ERROR_MSJ_SIN_USUARIO("001", "No se encontro usuario"),
	ERROR_AL_GUARDAR("002", "No se pudo guardar en el catalogo"),
	ERRO_AL_CONSULTAR_CATALOGO("003", "No se pudo consultar el catalogo"),
	ERROR_AL_GENERAR_REPORTE_DINAMICO("004", "Error al generar el reporte dinamico"),
	ERROR_AL_GENERAR_EXCEL("005", "Error al generar el excel"),
	ERROR_GENERAL_PISTAS("006", "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01)."),
	ERROR_FECHAS("007","Las fechas ingresadas son incorrectas. Favor de verificar."),
	ERROR_AL_EXPORTAR("008","Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01)."),
	ERROR_NO_RESULT("009","No se encontraron resultados de la búsqueda."),
	ERROR_AL_GENERAR_CONTRASENNIA("010","Error al generar el enlace y contraseña. Intente nuevamente."),
	ERROR_OBTENER_USUARIOS_SAT("011","Error al obtener los usuarios SAT"),
	ERROR_OBTENER_ARCHIVO_PLANTILLA_PROYECTO("012","Error al obtener el registro Plantilla Proyecto"),
	ERROR_PK("013","Error en PK"),
	ERROR_SIN_PROYECTOS("014","No se cuenta con proyectos asignados."),
	ERROR_SIN_RESULTADOS("015","No se encontraron resultados de la búsqueda. Intente nuevamente."),
	ERROR_TIPO_ARCHIVO_NO_SOPORTADO("016", "El Archivo no pertenece a ningun tipo registrado.");

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

		public static final String MSJ = "Es necesario seleccionar la sección. ";

	}
}
