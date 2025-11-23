package com.sisecofi.admingeneral.util.enums;

import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public enum ErroresAdminPlantillaEnum implements DefinitionMessage {

	ERROR_MSJ_SIN_USUARIO("001", "Sin usuario"),
	ERROR_AL_GENERAR_VISTA_PREVIA("002", "No se pudo generar la vista previa"),
	ERROR_AL_CONSULTAR_FASE_PROYECTO("003", "Fase de proyecto no encontrada"),
	ERROR_PLANTILLA("004", "La plantilla no tiene la estructura correcta: carpeta-archivos"),
	ERROR_PLANTILLA_NO_ENCONTRADA("005", "Plantilla no encontrada"),	
	ERROR_AL_GENERAR_REPORTE("006", "Error al descargar el reporte base"),
	ERROR_NO_CUMPLE_CON_LA_ESTRUCTURA_NOMBRE_ARCHIVO("007", "El nombre de algun archivo no cumple con el patron"),
	ERROR_NO_CUMPLE_CON_LA_ESTRUCTURA("008", "El archivo no cumple con la estructura, por favor consulte el archivo de ayuda."),
	ERROR_NO_CUMPLE_CON_LA_ESTRUCTURA_NOMBRE_FASE("009", "El nombre de la pestaña “Nombre_Fase” en el archivo cargado debe coincidir exactamente con el nombre de una fase registrada en el catálogo de Fases. Consulta la “Plantilla tipo”."),
	ERROR_NO_CUMPLE_CON_LA_ESTRUCTURA_NIVEL_ARCHIVO("010", "El límite de niveles creados será de 10. Consulta la “Plantilla tipo”."),
	ERROR_NO_CUMPLE_CON_LA_ESTRUCTURA_COLUMNAS_MODIFICADAS("011", "No se pueden realizar modificaciones en los encabezados, añadir o eliminar columnas. Consulta la “Plantilla tipo”."),
	ERROR_NO_CUMPLE_CON_LA_ESTRUCTURA_DUPLICIDAD("012", "No puede haber duplicidad de archivos o carpetas en el mismo nivel. Consulta la “Plantilla tipo”."),
	NOMBRE_REPETIDO("013", "Ya existe una plantilla con el mismo nombre. Intente de nuevo por favor."),
	ERROR_DESCRIPCION_VACIA("014", "La descripción de la carpeta o archivo no debe estar vacía.");


	private String clave;
	private String message;

	ErroresAdminPlantillaEnum(String clave, String message) {
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
