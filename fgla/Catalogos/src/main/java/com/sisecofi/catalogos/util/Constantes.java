package com.sisecofi.catalogos.util;

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
	public static final String PATH_BASE_INTERNO = "catalogos-interno";

	public static final String TIPO = "tipo";
	public static final String CATALOGO = "Catalogo";
	public static final String NOMBRE_FRONT = "nombreFront";
	public static final String ID_CATALOGO = "idCagalogo";
	public static final String ORDEN = "orden";	
	public static final String CAMPO_MANDAR = "campoMandar";
	public static final String CAMPO_MOSTRAR = "campoMostrar";
	public static final String VISTA = "vista";
	public static final String TAMANIO = "maxLength";

	public static final String META_NEW = "meta-new";
	public static final String ALL_DATA = "all-data";
	public static final String METADATA = "metadata";
	public static final String SAVE = "save";
	public static final String UPDATE = "update";
	public static final String HIJO = "hijos";
	public static final String ID = "ID-";
	private static final String [] ATRIBUTOS_COMPLEMENTARIOS = {"Nombre del catálogo: ", "| id: " , "| id administrador: "};
	
	public static final String TITULOS = "ID,Nombre,Descripción,Fecha creación,Fecha modificación,Estatus";
	public static final String TITULOS_EMPLEADOS = "Id,Nombre,Puesto,Correo electrónico,Teléfono,Inicio Vigencia,Fin Vigencia,Fecha de creación,Última modificación,Estatus";
	
	public static String[] getAtributosComplementarios() {
		return ATRIBUTOS_COMPLEMENTARIOS.clone();
	}
}
