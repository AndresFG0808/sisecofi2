package com.sisecofi.libreria.comunes.util.enums;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public enum ModuloPista {
	
	ADMIN_DEVENGADOS(1, "Consumo de Servicios"),
	PLANTILLAS(2, "Plantillas"),
	ADMIN_CATALOGOS(3, "Catálogos"),
	PROVEEDORES(4, "Proveedores"),
	PROYECTOS(5, "Proyectos"),
	ASIGNAR_PROYECTO(6, "Asignar Proyectos"),
	DICTAMEN(7, "Dictamen"),
	ESTIMACION(8, "Estimación"),
	REINTEGRO(9, "Reintegro"),
	REPORTE_CONTROL_DOCUMENTAL(10, "Reporte de control documental"),
	ADMIN_CONTRATOS(11, "Contratos"),
	MATRIZ_DOCUMENTAL(12, "Matriz Documental"),
	CONVENIO_MODIFICATORIO(13, "Convenio Modificatorio"),
	USUARIOS_SISTEMA(14, "Usuarios del Sistema"),
	REPORTE_FINANCIERO(15, "Reporte Financiero"),
	TABLERO_CONTROL(16, "Tablero de control"),
	SISTEMA(17, "Sistema"),
	CONSTRUIR_REPORTES(18, "Construir Reportes"),
	INFORMACION_COMITES(19, "Información de comités"),
	ACCESO_SISTEMA(20, "Acceso sistema");


	
	private int id;
	private String clave;

	ModuloPista(int id,String clave){
		this.id = id;
		this.clave = clave;
	}

	public int getId() {
		return id;
	}

	public String getClave() {
		return clave;
	}
	
}
