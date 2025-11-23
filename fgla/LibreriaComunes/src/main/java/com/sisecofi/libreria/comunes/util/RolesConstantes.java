package com.sisecofi.libreria.comunes.util;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class RolesConstantes {

	private RolesConstantes() {
	}

	/**
	 * Es el encargado de gestionar, mantener y apoyar en la operación en todos los
	 * módulos del sistema, asegurando la integridad del mismo. Ingresar a todos los
	 * módulos del sistema
	 */
	public static final String ROL_ADMINISTRADOR_SISTEMA = "cn=SAT_SISECOFI_ADMIN_SIS";

	/**
	 * Es el encargado de gestionar, mantener y apoyar en la operación en algunos
	 * módulos del sistema, asegurando la integridad del mismo. Ingresar a la
	 * mayoría de los módulos del sistema, a excepción del módulo catálogos
	 * generales.
	 */
	public static final String ROL_ADMINISTRADOR_SISTEMA_SECUNDARIO = "cn=SAT_SISECOFI_ADMIN_SIS_SEC";

	/**
	 * Es el responsable de la administración y definición de la estructura
	 * documental que se tendrá en cada una de las fases de los proyectos y/o
	 * contratos. Definir la estructura, carga y asignación de plantillas.
	 */
	public static final String ROL_ADMINISTRADOR_MATRIZ_DOCUMENTAL = "cn=SAT_SISECOFI_ADMIN_MAT_DOC";

	/**
	 * Es el personal ACPPI que brinda apoyo a los líderes de proyecto y/o
	 * administradores de contrato en la planeación, ejecución y procesos de cierre
	 * de los proyectos y/o contratos. Carga de archivos para los proyectos, planes
	 * de trabajos, proyecciones de caso de negocio, verificación de documentos del
	 * RCP y consulta de reportes.
	 */
	public static final String ROL_APOYO_ACPPIL = "cn=SAT_SISECOFI_ADMIN_APO_ACP";

	/**
	 * Es el personal que brinda el apoyo al líder del proyecto en la carga de
	 * archivos del proyecto, modificación del plan de trabajo, registro de informes
	 * documentales y generación del RCP. Carga de archivos de los proyectos
	 * asignados, planes de trabajo, informes documentales, generar RCP y consulta
	 * de reportes documentales.
	 */
	public static final String ROL_APOYO_AL_LIDER_DE_PROYECTO = "cn=SAT_SISECOFI_APO_LID_PROY";

	/**
	 * Es el personal que gestiona los títulos de autorización, dictamen técnico,
	 * registro y actualización del directorio de contacto. Títulos de autorización,
	 * dictamen técnico y directorio de contacto de proveedores.
	 */
	public static final String ROL_GESTOR_TITULOS_DE_AUTORIZACION = "cn=SAT_SISECOFI_GES_TIT_AUT";

	/**
	 * Es el personal que brinda el apoyo en la carga de archivos del contrato.
	 * Carga de archivos de los contratos asignados, consulta de reportes
	 * documentales.
	 */
	public static final String ROL_GESTOR_DOCUMENTAL_CONTRATO = "cn=SAT_SISECOFI_GES_DOC_CON";

	/**
	 * Es el personal que tiene permisos de consulta de información en todos los
	 * módulos del sistema, excepto en los referentes a configuración del
	 * sistema.Solo consultar información.
	 */
	public static final String ROL_USUARIO_CONSULTA = "cn=SAT_SISECOFI_USU_CONS";

	/**
	 * Es el responsable de dirigir, administrar la correcta ejecución y llevar el
	 * control físico y financiero de un proyecto a través de la planeación,
	 * preparación y aprobación, ejecución y cierre del mismo. Administración de
	 * proyectos asignados, proveedores, gestión documental, consulta de información
	 * de contratos, consumo de servicios y reportes.
	 */
	public static final String ROL_LIDER_DE_PROYECTO = "cn=SAT_SISECOFI_LID_PRO";

	/**
	 * Es el responsable del seguimiento, control y cumplimiento del (los)
	 * contrato(s) vinculado(s) a un proyecto específico de acuerdo con las
	 * facultades establecidas en el RISAT. Administración de contratos asignados,
	 * gestión documental, consulta de información de proyectos, estimaciones del
	 * consumo de servicios, penas y deductivas y consulta de reportes.
	 */
	public static final String ROL_ADMINISTRADOR_DEL_CONTRATO = "cn=SAT_SISECOFI_ADMIN_CON";

	/**
	 * Es el personal que apoya al administrador del contrato en la gestión de las
	 * estimaciones del consumo de los conceptos de servicio del contrato (Definido
	 * en el módulo de contratos como participante).Consulta de contratos y sus
	 * convenios modificatorios, gestión de estimaciones y consulta del reporte de
	 * estimaciones y seguimiento por concepto de servicio.
	 */
	public static final String ROL_PARTICIPANTES_EN_LA_ADMINISTRACION_DE_ESTIMACIONES = "cn=SAT_SISECOFI_PART_ADMIN_ESTIM";

	/**
	 * Es el personal que apoya al administrador del contrato en la gestión de los
	 * dictámenes de servicio (Definido en el módulo de contratos como
	 * participante). Consulta de contratos y sus convenios modificatorios, gestión
	 * del dictamen y consulta del reporte financiero, construir reportes y tablero
	 * de control.
	 */
	public static final String ROL_PARTICIPANTES_EN_LA_ADMINISTRACION_DE_DICTAMEN = "cn=SAT_SISECOFI_PART_ADMIN_DICT";

	/**
	 * Es el personal que apoya al verificador del contrato en la gestión del
	 * dictamen, facturas, notas de crédito y documentación relacionada con estos
	 * (Definido en el módulo de contratos como participante).Consulta de proyectos,
	 * contratos y sus convenios modificatorios, gestión del dictamen y consulta de
	 * reportes.
	 */
	public static final String ROL_PARTICIPANTES_EN_LA_ADMINISTRACION_DE_LA_VERIFICACION = "cn=SAT_SISECOFI_PART_ADMIN_VERIF";

	/**
	 * Es el personal responsable de revisar el Dictamen emitido por la
	 * administración de “EL CONTRATO”, así como de la recepción, revisión y
	 * aceptación de “EL SERVICIO”, cumpla con lo establecido en "EL CONTRATO" y la
	 * documentación referida en la Declaración 2.5 de "EL CONTRATO". (No es
	 * necesaria su definición en el módulo de contratos) Consulta de proyectos,
	 * contratos y sus convenios modificatorios, creación y gestión del dictamen,
	 * facturas/notas de crédito y consulta de reportes.
	 */
	public static final String ROL_VERIFICADOR_GENERAL = "cn=SAT_SISECOFI_VERI_GEN";

	/**
	 * Es el personal responsable de revisar el Dictamen emitido por la
	 * administración de “EL CONTRATO”, así como de la recepción, revisión y
	 * aceptación de “EL SERVICIO”, cumpla con lo establecido en "EL CONTRATO" y la
	 * documentación referida en la Declaración 2.5 de "EL CONTRATO". (Definido en
	 * el módulo de contratos) Consulta de proyectos, contratos y sus convenios
	 * modificatorios, creación y gestión del dictamen, facturas/notas de crédito y
	 * consulta de reportes.
	 */
	public static final String ROL_VERIFICADOR_ESPECIFICO_DEL_CONTRATO = "cn=SAT_SISECOFI_VERI_ESP_CON";

	/**
	 * Es el personal que tiene los permisos de poder ver todos los proyectos y
	 * contratos. Consultar información de Todos los contratos y todos los proyectos
	 * existentes.
	 */
	public static final String ROL_TODOS_LOS_PROYECTOS = "cn=SAT_SISECOFI_TDS_PROY";

}
