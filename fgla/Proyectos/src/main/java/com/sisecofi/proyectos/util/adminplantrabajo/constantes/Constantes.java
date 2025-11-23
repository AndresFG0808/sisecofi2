package com.sisecofi.proyectos.util.adminplantrabajo.constantes;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class Constantes {

	private Constantes() {
	}

	public static final String PATH_BASE = "administracion";

	public static final String TITULOS_PLAN_TRABAJO = "Id Tarea, Nivel de esquema, Nombre de la tarea, Activo, Duración planeada, Fecha inicio planeada, Fecha fin planeada, Duración real, Fecha inicio real, Fecha fin real, Predecesora, Planeado %, Completado %" ;

	private static final String [] ATRIBUTOS_INFORMACION_COMITE = {"Id comite proyecto: " , "Id proyecto: ", "Id tipo moneda: " , "Id contrato convenio: ", "Id comite: ","Id sesion numero: ", "Id sesion clasificacion: ", "Acuerdo:" , "Vigencia: ", "Monto autorizado: ", "Tipo cambio:", "Monto: ", "Comentarios: " };


	public static String [] getAtributosComiteProyecto() {
		return ATRIBUTOS_INFORMACION_COMITE.clone();
	}

}
