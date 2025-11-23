package com.sisecofi.proveedores.util;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class Constantes {

	private Constantes() {
	}

	public static final String ROL_CARGA = "Carga";

	public static final String PATH_BASE = "";
	public static final String PATH_BASE_INTERNO = "proveedores-internos";
	public static final String TITULOS_REPORTE_DIRECTORIO_CONTACTO = "Id, Nombre del contacto, Teléfono oficina, Teléfono celular, Correo electrónico, Representante legal, Comentarios";
	public static final String TITULOS_SERVICIOS_PROVEEDOR ="Id, Número de título, Título de servicio, Estatus, Fecha de vencimiento, Vigencia, Comentarios";
	public static final String TITULOS_REPORTE_DICTAMEN_TECNICO ="Id, Servicio, Año, Responsable, Resultado, Observación ";
	public static final String TITULOS_REPORTE_PROVEEDOR_GENERAL = "Id, Nombre del proveedor, Nombre comercial, Giro de la empresa, RFC, Representante legal, Título de servicio, Vigencia, Fecha de vencimiento, Cumple dictamen, Estatus";
	public static final String VALIDACION_ESTATUS = "{\"estatus\": true}";
	public static final String ESTATUS_CUMPLE = "{\"nombre\": \"Cumple\"}";
	public static final String ESTATUS_NO_CUMPLE = "{\"nombre\": \"No Cumple\"}";
	
}
