package com.sisecofi.admingeneral.util;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class Constantes {

	private Constantes() {
	}

	public static final String PATH_BASE = "";
	public static final String PATH_BASE_INTERNO = "administracion-interno";
	
	private static final String [] ATRIBUTOS_MATRIZ = {"ID de registro: " , "Nombre de la plantilla: ", "Estatus de plantilla: "};
	private static final String [] PLANTILLA_TIPO = {"Plantilla tipo: "};
	private static final String [] DESCARGAR_PLANTILLA_FASE = {"ID de registro: ", "Nombre de plantilla: ", "Plantilla tipo (“Estructura fase”)", "Fase: ", " | Estatus: Activo", " | Estatus: "};
	public static final String VALIDACION_ESTATUS = "{\"estatus\": true}";
	
	public static String [] getMatrizDocumental() {
		return ATRIBUTOS_MATRIZ.clone();
	}
	
	public static String [] getPlantillaTipo() {
		return PLANTILLA_TIPO.clone();
	}
	
	public static String [] getDescargarPlantillaFase() {
		return DESCARGAR_PLANTILLA_FASE.clone();
	}

}
