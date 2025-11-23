package com.sisecofi.libreria.comunes.util.enums;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public enum ErroresSistema implements DefinitionMessage {
	ERROR_SE_NECESITA_ANOTACION_PERMISO("1000", "Se requiere anotación de permiso"),
	ERROR_SIN_PERMISO_PARA_EL_RECURSO("1001", "No tiene permisos para este recurso: "),
	ERROR_SE_PRE_AUTORIZACION("1002,", "Se requiere anotación preautorización"),
	ERROR_NEXUS_CONECTAR("1003", "Error conectar con SatCloud"), 
	ERROR_FORMATO_EXCEL("1004", "Error en formato excel"),
	PETICION_CORRECTA("1005", "Correctamente"),
	ERROR_NEXUS_CARGAR_ARCHIVO("1006", "Error al cargar archivo en SatCloud"),
	ERROR_NEXUS_CREAR_FOLDER("1007", "Error al crear folder en SatCloud"),
	ERROR_NEXUS_BORRAR_FOLDER("1008", "Error al borrar folder en SatCloud"),
	ERROR_NEXUS_COMPARTIR_ARCHIVO("1009", "Error al compartir archivo en SatCloud"),
	ERROR_NEXUS_DESCARGAR_ARCHIVO("1010", "Error al descargar archivo en SatCloud"),
	ERROR_NEXUS_ELIMINAR_ARCHIVO("1011", "Error al eliminar archivo en SatCloud"),
	ERROR_AL_LEER_ARCHIVO_EXCEL("1012","Error al leer archivo excel"),
	ERROR_AL_GENERAR_TOKEN_DEV("1013","Error al generar token para local");

	private String clave;
	private String message;

	ErroresSistema(String clave, String message) {
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
