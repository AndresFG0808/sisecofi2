package com.sisecofi.libreria.comunes.util.enums;

import com.sisecofi.libreria.comunes.util.ConstantesEnums;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public enum TipoSeccionPista {
	PROYECTO_DATOS_GENERALES(1, ConstantesEnums.DATOS_GENERALES),
	PROYECTO_DATOS_FICHA_TECNICA(2, "Ficha técnica"),
	PROYECTO_DATOS_GESTION_DOCUMENTAL(3, ConstantesEnums.GESTION),
	PROYECTO_DATOS_INFORMACION_COMITE(4, "Información de comités"),
	PROYECTO_DATOS_PLAN_TRABAJO(5, "Plan de trabajo"),
	PROYECTO_DATOS_PARTICIPACION_PROVEDORES(6, "Participación de Proveedores"),
	ASIGNAR_PROYECTO_POR_PROYECTO(7, "Por proyecto"),
	ASIGNAR_PROYECTO_POR_USUARIO(8, "Por usuario"),
	CATALOGOS_GENERALES(9, "Generales"),
	CATALOGOS_COMPLEMENTARIOS(10, "Complementarios"),
	PROVEEDOR_DATOS_GENERALES(11, ConstantesEnums.DATOS_GENERALES),
	PROVEEDOR_DIRECTORIO_CONTACTO(12, "Directorio de contacto"),
	PROVEEDOR_TITULO_SERVICIO(13, "Títulos de servicio"),
	PROVEEDOR_DICTAMEN_TECNICO(14, "Dictamen técnico"),
	CONTRATO_DATOS_GENERALES(15, ConstantesEnums.DATOS_GENERALES),
	DICTAMEN_DATOS_GENERALES(16, ConstantesEnums.DATOS_GENERALES),
	DICTAMEN_DATOS_GENERALES_RESUMEN(17, "Datos generales- Resumen consolidado"),
	DICTAMEN_REGISTRO_SERVICIOS(18, "Registro de servicios"),
	DICTAMEN_PENAS_CONTRACTUALES(19, "Penas contractuales"),
	DICTAMEN_PENAS_CONVENCIONALES(20, "Penas convencionales"),
	DICTAMEN_DEDUCCIONES(21, "Deducciones"),
	DICTAMEN_SOPORTE_DOC(22, "Soporte documental del dictamen"),
	CONTRATOS_PROYECCION_CASO_NEGOCIO(23, "Proyección de caso de negocio"),
	CONTRATOS_IDENTIFICACION(24, "Identificación"),
	CONTRATOS_VIGENCIA_MONTOS(25, "Vigencia y montos"),
	CONTRATOS_GRUPOS(26, "Grupos de servicio y/o conceptos"),
	CONTRATOS_REGISTRO_SERVICIOS(27, "Registro de servicios"),
	CONTRATOS_ATRASO(28, "Atraso en el inicio de la prestación"),
	CONTRATOS_PENAS(29, "Penas contractuales"),
	CONTRATOS_INFORMES_UNICA(30, "Informes documentales por única vez"),
	CONTRATOS_INFORMES_PERIODICOS(31, "Informes documentales periódicos"),
	CONTRATOS_INFORMES_SERVICIOS(32, "Informes documentales de los servicios"),
	CONTRATOS_NIVELES(33, "Niveles de servicio (SLA)"),
	CONTRATOS_ASIGNACION(34, "Asignación de plantilla"),
	CONTRATOS_DATOS_GENERALES_PARTICIPANTES(35, "Datos generales-Participantes en la administración del contrato"),
	ESTIMACION_DATOS_GENERALES(36, "Datos generales estimación"),
	ESTIMACION_REGISTRO_SERVICIOS(37, "Registros de servicios"),
	DICTAMEN_DEDUCCIONES_DESCUENTOS(38, "Deducciones, descuentos y penalizaciones"),
	DICTAMEN_SOLICITUD_FACTURA(39, "Solicitud de Factura"),
	PISTAS(40, "Pistas de Auditoría"),
	FACTURAS(41, "Facturas"),
	NOTA_CREDITO(42, "NC"),
	PROYECTO_BUSQUEDA(43, "Búsqueda"),
	PROYECTOS_REGISTRADOS(44, "Proyectos registrados"),
	PROVEEDOR_CUMPLE_DICTAMEN(45, "Cumple dictamen"),
	PROVEEDOR_DETALLE(46, "Detalle"),
	PROVEEDORES(47, "Proveedores"),
	PROYECTO_ASOCIAR_FASE(48, "Asociar fases"),
	DICTAMEN_GESTION_DOCUMENTAL(49, ConstantesEnums.GESTION),
	CONTRATOS_GESTION_DOCUMENTAL(50, ConstantesEnums.GESTION),
	CONVENIO_MODIFICATORIO_GESTION_DOCUMENTAL(51, ConstantesEnums.GESTION),
	CONTRATOS_TABLA(52, "Tabla contratos"),
	CONTRATOS_DICTAMENES_ASOCIADOS(53, "Dictámenes asociados"),
	CONTRATOS_FACTURAS_ASOCIADAS(54, "Facturas asociadas"),
	CONTRATOS_REINTEGROS_ASOCIADOS(55, "Reintegros asociados"),
	ADMINISTRAR_DEVENGADOS_DICTAMEN(56, "Dictamen"),
	ADMINISTRAR_DEVENGADOS_ESTIMACION(57, "Estimación"),
	USUARIO_AGREGAR_SISTEMA(58, "Agregar usuario al sistema"),
	SOLICITUD_PAGO(59, "Solicitud de pago"),
	REFERENCIA_PAGO(60, "Referencia de pago"),
	REGISTRO_CONVENIO_MODIFICATORIO(61, "Registro de convenio modificatorio"),
	REGISTRO_SERVICIOS_CONVENIO_MODIFICATORIO(62, "Registro de servicios"),
	PROYECCION_CONVENIO_MODIFICATORIO(63, "Proyección de convenio modificatorio"),
	CONVENIO_MODIFICATORIO_ASIGNAR_PLANTILLA(64, "Asignación de plantilla"),
	VERIFICACION_RCP(65, "Verificación de RCP"),
	CRITERIOS_BUSQUEDA(66, "Criterios de búsqueda"),
	CAMPOS_REPORTE(67, "Campos para reporte"),
	CONVENIO_MODIFICATORIO_REPORTE(68, "Reporte"),
	PAPELERA_RECICLAJE(69, "Papelera de reciclaje"),
	MATRIZ_DOCUMENTAL(70, "Matriz Documental"),
	PLANTILLAS(71,"Plantillas"),
	REINTEGRO_GESTION_DOCUMENTAL(72, ConstantesEnums.GESTION),
	INFORMACION_COMITES_GESTION_DOCUMENTAL(73,ConstantesEnums.GESTION),
	USUARIO_DA(74,"Usuarios - DA"),
	USUARIOS_SISTEMA(75, "Usuarios - Sistema"),
	USUARIOS(76, "Usuarios"),
	REINTEGROS(77,"Reintegros"),
	ACCESO_SISTEMA(78, "Acceso sistema"),
	REPORTE_CONTROL_DOCUMENTAL(79, "Reporte de control documental"),
	REPORTE_FINANCIERO(80, "Reporte Financiero"),
	TABLERO_CONTROL(81, "Tablero de control");
	
	private Integer idSeccionPista;
	private String descripcion;

	TipoSeccionPista(Integer idSeccionPista,String descripcion){
		this.idSeccionPista=idSeccionPista;
		this.descripcion=descripcion;
	}

	public Integer getIdSeccionPista() {
		return idSeccionPista;
	}

	public void setIdSeccionPista(Integer idSeccionPista) {
		this.idSeccionPista = idSeccionPista;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
