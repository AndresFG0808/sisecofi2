package com.sisecofi.libreria.comunes.util.enums;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public enum CatalogosComunes {
	
	ESTATUS_PROYECTO(1, "Estatus del proyecto", "estatusProyecto"),	
	FASES_PROYECTOS(2,"Fases del proyecto","faseProyecto"),
	SUB_FASE_PROYECTO(3,"Subfase del proyecto","subFase"),
	TIPO_MONEDA(4,"Tipo moneda","tipoMoneda"),
	CONTRATO_CONVENIO(5,"Contrato convenio","contratoConvenio"),
	AFECTACION(6,"Afectación","afectacion"),	
	CLASIFICACION_PROYECTO(10,"Clasificación proyecto","clasificacionProyecto"),
	FINANCIAMIENTO(13,"Financiamiento","financiamiento"),
	COMITE(15,"Comité","comite"),
	NUMERO_SESION(16,"Número Sesión","sesion"),
	PLANTILLA(17,"Plantilla","plantilla"),
	ALINIACION(18,"Alineación","aliniacion"),
	MAPA_OBJETIVO(19,"Mapa estratégico","mapaObjetivo"),
	PERIDO(21,"Periodo (Alineación)","periodo"),
	TIPO_PROCEDIMIENTO(22,"Tipo procedimiento","tipoProcedimiento"),
	CLASIFICACION_SESION(23,"Clasificación sesión","clasificacionSesion"),
	RESPUESTA_PROVEEDOR(24,"Respuesta proveedor","respuestaProveedor"),
	ADMINISTRACION_GENERAL(28,"Administración general","admonGeneral"),
	ADMINISTRACION_CENTRAL(29,"Administración central","admonCentral"),
	ADMINISTRACIONES(30,"Administración","administracion"),
	PROVEEDOR_GIRO_EMPRESARIAL(31,"Giro Empresarial","giroEmpresarial"), 
	PROVEEDOR_TITULO_SERVICIO(32,"Titulos de servicio","tituloServicio"), 
	PROVEEDOR_CUMPLE_DICTAMEN(33,"Cumple dictamen","cumpleDictamen"),
	PROVEEDOR_SEMAFORO(34,"Proveedor semaforo","proveedorSemaforo"),
	FASE_DICTAMEN(35,"Fase Dictamen","faseDictamen"),
	ESTATUS_DICTAMEN(36,"Estatus Dictamen","estatusDictamen"),
	ESTATUS_CONTRATO(37,"Estatus Contrato","estatusContrato"),
	TIPO_CONSUMO(38,"Tipo Consumo","tipoConsumo"),
	TIPO_PENA_CONTRACTUAL(39,"Tipo pena contractual","tipoPenaContractual"),
	DESGLOCE(40,"Desgloce","desgloce"),
	PERIODO_CONTROL_MES(41,"Periodo Mes","periodoControlMes"),
	CONTRATO_VIGENTE(42,"Contrato Vigente","contratoVigente"),
	ESTATUS_ESTIMACION(43,"Estatus Estimación","estatusEstimacion"),
	TIPO_CONSUMO_DEVENGADOS(44,"Tipo Consumo Devengados","tipoConsumoDevengados"),
	DOMINIOS(45,"Dominios Tecnologicos","dominiosTecnologicos"),
	TIPO_UNIDAD(46,"Tipo Unidad","tipoUnidad"),
	TIPO_PENA_CONVENCIONAL(47,"Tipo Pena Convencional","tipoPenaConvencional"),
	TIPO_DEDUCCION(48,"Tipo Deducción","tipoDeduccion"),
	PERIODO_CONTROL_ANIO(49,"Periodo Control Año","periodoControlAnio"),
	IVA(50,"IVA","iva"),
	IEPS(51,"IEPS","ieps"),
	SECCION(52,"Sección a cargar","seccion"),
	PERIODICIDAD(53,"Periodicidad","periodicidad"),
	FONDEO(54,"Fondeo Contrato","fondeoContrato"),
	RESPONSABILIDAD(55,"Responsabilidad","responsabilidad"),
	ESTATUS_FACTURA(56,"Estatus Factura","estatusFactura"),
	ACUERDO_PAGO(57,"Acuerdo de pago","acuerdoPago"),
	ESTATUS_NOTA_CREDITO(58,"Estatus de notas de crédito","estatusNotaCredito"),
	TIPO_NOTIFICACION_PAGO(59,"Tipo de notificación de pago","tipoNotificacionPago"),
	TIPO_REINTEGRO(60,"Tipo de reintegro","tipoReintegro"),
	ESTATUS_RCP(61,"Estatus RCP","estatusRcp"),
	TIPO_PLANTILLADOR(62,"Tipo de plantillador","tipoPlantillador"),
	TIPO_DESCUENTO(63,"Tipo de descuento","tipoDescuento"),
	CONVENIO_COLABORACION(64,"Convenio de colaboración","convenioColaboracion"),
	RESULTADO_DICTAMEN_TECNICO(65,"Resultado Dictamen Técnico","resultadoDictamenTecnico"), 
	ESTATUS_TITULO_SERVICIO(66,"Estatus Título Servicio","estatusTituloServicio"),
	URL_PORWER_BI(67, "Url Power Bi", "urlPowerBi"),
	CONVENIO_COLABORACION_REPORTE(68,"Convenio de colaboración reporte","convenioColaboracionReporte"),
	CORREO(69,"Correo electrónico","correo"),
	ADMINISTRADORES_CENTRALES(70,"Administrador Central","administradorCentral"),
	PUESTOS_ORGANIGRAMA(71,"Puesto organigrama","tipoEmpleado"),
	CLAVE_PRODUCTO(72,"Clave de producto o servicio","claveProducto");
	
	
	
	private int idCatalogo;
	private String nombreCatalogo;
	private String tabla;

	CatalogosComunes(int idCatalogo, String nombreCatalogo, String tabla) {
		this.idCatalogo = idCatalogo;
		this.nombreCatalogo = nombreCatalogo;
		this.tabla = tabla;
	}

	public int getIdCatalogo() {
		return idCatalogo;
	}

	public String getNombreCatalogo() {
		return nombreCatalogo;
	}

	public String getTabla() {
		return tabla;
	}

}
