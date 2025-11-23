package com.sisecofi.proyectos.util.enums;

import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public enum ErroresPlanTrabajoEnum implements DefinitionMessage {

	ERROR_MSJ_SIN_USUARIO("001", "Usuario no encontrado"),
	PETICION_CORRECTA("002","Correctamente"),
	ERROR_CARGAR_PLAN("003","Error al cargar el plan"),
	FORMATO_INVALIDO("004", "Se debe adjuntar un archivo de Excel con extensión (.xlsx). "),
	CAMPOS_OBLIGATORIOS("005", "Por cada tarea se tendrán que ingregar valores en los siguientes campos obligatoriamente: 'Id tarea', 'Nivel de esquema', 'Nombre de la tarea', 'Activo', 'Duración planeada', 'Fecha de inicio planeada' y 'Fecha fin planeada'. Consulte el 'Plan tipo'."),
	IDTAREA_UNICO("006", "El " + "Id tarea" +" debe ser único e irrepetible. Consulte el 'Plan tipo'. "),
	NIVELESQUEMA_UNICO("007", "Solo puede haber un nivel de esquema con el valor 'Cero'. Consulte el 'Plan tipo'. "),
	TAREAHIJA_VALIDACION("008", "Por cada tarea (hijo) debe haber una tarea (padre). Consulte el 'Plan tipo'. "),
    VALOR_ACTIVO_INVALIDO("009", "Los valores para 'Activo' deben de ser 'Sí' o 'No'. Consulte el 'Plan tipo'. "),
	PORCENTAJES_ENTEROS("010", "Los porcentajes deben ser enteros (Aplicar reglas de redondeo). Consulte el 'Plan tipo'. "),
	FECHA_PADRE_MENOR_QUE_HIJO("011", "Ninguna tarea (Padre) puede tener la fecha de inicio planeada menor que su conjunto de tareas (hijos). Consulte el 'Plan tipo'. "),
	ERROR_TAREA_PADRE_FECHA_FIN("012", "Ninguna tarea (padre) puede tener la fecha fin planeada mayor que su conjunto de tareas (hijos). Consulte el 'Plan tipo'. "),
	VALIDAR_DURACION_REAL("013", "Para calcular los valores de los campos 'Duración real', obligatoriamente se necesita los valores de los campos 'Fecha inicio real' y 'Fecha fin real'. "),
	VALIDACION_FECHA_INICIO_REAL("014", "Para calcular los valores de los campos 'Fecha inicio real' obligatoriamente se necesita los valores de los campos 'Duracion real' y 'Fecha fin real'. "),
	VALIDACION_FECHA_FIN_REAL("015", "Para calcular los valores de los campos 'Fecha fin real', obligatoriamente se necesita los valores de los campos 'Fecha inicio real' y 'Duracion real'. "),
	PREDECESORA_INVALIDA("016", "El valor para el campo 'Predecesora' debe tener un id menor que la tarea que la invoca. Consulta el 'Plan tipo'. "),
	ERROR_COLUMNA_EXCEL("017", "No se pueden realizar modificaciones en los encabezados, añadir o eliminar columnas. Consulte el 'Plan tipo'. "),
	ERROR_NO_SE_ECONTRO("018", "No se encontró un plan de trabajo asignado al proyecto."),
	FECHA_PADRE_NO_ENCONTRADA("019", "La fecha de inicio planeada de la tarea padre no se encontro"),
	DURACION_REAL_NUMERICO("020", "El valor en la celda de predecesora no es numérico."),
	ERROR_VALIDAR_PREDECESORA("021", "Error al validar la predecesora."),
	ARCHIVO_VACIO("022", "El archivo está vacío o no contiene encabezados."),
	ERROR_PROYECTO("023", "No se encontro el Id Proyecto."),
	PLAN_EXISTENTE("024", "Se está adjuntado un nuevo plan de trabajo y se sobrecribirá el existente."),
	PLAN_NO_ENCONTRADO("025", "Plan de trabajo no encontrado."),
	PLAN_TRABAJO_GUARDADO("026", "Plan de trabajo guardado correctamente."),
	CALCULO_CORRECTO("027", "Calculo duración real exitoso"),
	ERROR_AL_GENERAR_EXCEL("028", "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01)."),
	ERROR_GENERICO("029", "La tarea padre tiene fechas nulas"),
	FORMATO_FECHA_INVALIDO("030", "El formato de la fecha es invalido.");
	
	

	private String clave;
	private String message;

	ErroresPlanTrabajoEnum(String clave, String message) {
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
