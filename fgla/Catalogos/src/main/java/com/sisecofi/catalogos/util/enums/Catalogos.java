package com.sisecofi.catalogos.util.enums;

import java.util.Optional;

import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatAcuerdoPago;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonGeneral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAfectacion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAliniacion;
import com.sisecofi.libreria.comunes.model.catalogo.CatClasificacionProyecto;
import com.sisecofi.libreria.comunes.model.catalogo.CatClasificacionSesion;
import com.sisecofi.libreria.comunes.model.catalogo.CatClaveProducto;
import com.sisecofi.libreria.comunes.model.catalogo.CatComite;
import com.sisecofi.libreria.comunes.model.catalogo.CatContratoConvenio;
import com.sisecofi.libreria.comunes.model.catalogo.CatContratoVigente;
import com.sisecofi.libreria.comunes.model.catalogo.CatConvenioColaboracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatConvenioColaboracionReporte;
import com.sisecofi.libreria.comunes.model.catalogo.CatCorreo;
import com.sisecofi.libreria.comunes.model.catalogo.CatDesgloce;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusContrato;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusDictamen;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusEstimacion;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusFactura;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusNotaCredito;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusProyecto;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusRcp;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusTituloServicio;
import com.sisecofi.libreria.comunes.model.catalogo.CatFaseDictamen;
import com.sisecofi.libreria.comunes.model.catalogo.CatFaseProyecto;
import com.sisecofi.libreria.comunes.model.catalogo.CatFinanciamiento;
import com.sisecofi.libreria.comunes.model.catalogo.CatFondeoContrato;
import com.sisecofi.libreria.comunes.model.catalogo.CatGiroEmpresarial;
import com.sisecofi.libreria.comunes.model.catalogo.CatDominiosTecnologicos;
import com.sisecofi.libreria.comunes.model.catalogo.CatIeps;
import com.sisecofi.libreria.comunes.model.catalogo.CatIva;
import com.sisecofi.libreria.comunes.model.catalogo.CatMapaObjetivo;
import com.sisecofi.libreria.comunes.model.catalogo.CatPeriodicidad;
import com.sisecofi.libreria.comunes.model.catalogo.CatPeriodo;
import com.sisecofi.libreria.comunes.model.catalogo.CatPeriodoControlAnio;
import com.sisecofi.libreria.comunes.model.catalogo.CatPeriodoControlMes;
import com.sisecofi.libreria.comunes.model.catalogo.CatResponsabilidad;
import com.sisecofi.libreria.comunes.model.catalogo.CatRespuestaProveedor;
import com.sisecofi.libreria.comunes.model.catalogo.CatResultadoDictamenTecnicoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatSeccion;
import com.sisecofi.libreria.comunes.model.catalogo.CatSesion;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoConsumo;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoConsumoDevengados;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoDeduccion;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoDescuento;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoEmpleado;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoMoneda;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoNotificacionPago;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoPenaContractual;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoPenaConvencional;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoProcedimiento;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoReintegro;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoUnidad;
import com.sisecofi.libreria.comunes.model.catalogo.CatTituloServicio;
import com.sisecofi.libreria.comunes.model.catalogo.CatUrlPowerBi;
import com.sisecofi.libreria.comunes.model.catalogo.CatsubFaseProyecto;
import com.sisecofi.libreria.comunes.model.plantillador.CatTipoPlantillador;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;

import lombok.Getter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Getter
public enum Catalogos {
	ESTATUS_PROYECTO(CatalogosComunes.ESTATUS_PROYECTO, true, new CatEstatusProyecto(), false, false),
	FASES_PROYECTOS(CatalogosComunes.FASES_PROYECTOS, false, new CatFaseProyecto(), false, true),
	SUB_FASE_PROYECTO(CatalogosComunes.SUB_FASE_PROYECTO, true, new CatsubFaseProyecto(), false, false),
	TIPO_MONEDA(CatalogosComunes.TIPO_MONEDA, false, new CatTipoMoneda(), false, true),
	CONTRATO_CONVENIO(CatalogosComunes.CONTRATO_CONVENIO, false, new CatContratoConvenio(), false, true),
	AFECTACION(CatalogosComunes.AFECTACION, false, new CatAfectacion(), false, true),
	CLASIFICACION_PROYECTO(CatalogosComunes.CLASIFICACION_PROYECTO, false, new CatClasificacionProyecto(), false, true),
	FINANCIAMIENTO(CatalogosComunes.FINANCIAMIENTO, false, new CatFinanciamiento(), false, true),
	COMITE(CatalogosComunes.COMITE, false, new CatComite(), false, true),
	NUMERO_SESION(CatalogosComunes.NUMERO_SESION, false, new CatSesion(), false, true),
	ALINIACION(CatalogosComunes.ALINIACION, false, new CatAliniacion(), true, true),
	MAPA_OBJETIVO(CatalogosComunes.MAPA_OBJETIVO, false, new CatMapaObjetivo(), true, false),
	PERIDO(CatalogosComunes.PERIDO, false, new CatPeriodo(), false, true),
	TIPO_PROCEDIMIENTO(CatalogosComunes.TIPO_PROCEDIMIENTO, false, new CatTipoProcedimiento(), false, true),
	CLASIFICACION_SESION(CatalogosComunes.CLASIFICACION_SESION, false, new CatClasificacionSesion(), false, true),
	RESPUESTA_PROVEEDOR(CatalogosComunes.RESPUESTA_PROVEEDOR, false, new CatRespuestaProveedor(), false, true),
	ADMINISTRACION_GENERAL(CatalogosComunes.ADMINISTRACION_GENERAL, false, new CatAdmonGeneral(), true, true),
	ADMINISTRACION_CENTRAL(CatalogosComunes.ADMINISTRACION_CENTRAL, false, new CatAdmonCentral(), true, false),
	ADMINISTRACIONES(CatalogosComunes.ADMINISTRACIONES, false, new CatAdministracion(), true, false),
	FASE_DICTAMEN(CatalogosComunes.FASE_DICTAMEN, true, new CatFaseDictamen(), false, false),
	ESTATUS_DICTAMEN(CatalogosComunes.ESTATUS_DICTAMEN, true, new CatEstatusDictamen(), false, false),
	ESTATUS_CONTRATO(CatalogosComunes.ESTATUS_CONTRATO, true, new CatEstatusContrato(), false, false),
	TIPO_CONSUMO(CatalogosComunes.TIPO_CONSUMO, true, new CatTipoConsumo(), false, false),
	TIPO_PENA_CONTRACTUAL(CatalogosComunes.TIPO_PENA_CONTRACTUAL, false, new CatTipoPenaContractual(), false, true),
	DESGLOCE(CatalogosComunes.DESGLOCE, true, new CatDesgloce(), false, false),
	PERIODO_CONTROL_MES(CatalogosComunes.PERIODO_CONTROL_MES, false, new CatPeriodoControlMes(), false, true),
	CONTRATO_VIGENTE(CatalogosComunes.CONTRATO_VIGENTE, true, new CatContratoVigente(), false, false),
	ESTATUS_ESTIMACION(CatalogosComunes.ESTATUS_ESTIMACION, true, new CatEstatusEstimacion(), false, false),
	TIPO_CONSUMO_DEVENGADOS(CatalogosComunes.TIPO_CONSUMO_DEVENGADOS, true, new CatTipoConsumoDevengados(), false, false),
	DOMINIOS(CatalogosComunes.DOMINIOS, false, new CatDominiosTecnologicos(), false, true),
	TIPO_UNIDAD(CatalogosComunes.TIPO_UNIDAD, false, new CatTipoUnidad(), false, true),
	TIPO_PENA_CONVENCIONAL(CatalogosComunes.TIPO_PENA_CONVENCIONAL, false, new CatTipoPenaConvencional(), false, true),
	TIPO_DEDUCCION(CatalogosComunes.TIPO_DEDUCCION, false, new CatTipoDeduccion(), false, true),
	PERIODO_CONTROL_ANIO(CatalogosComunes.PERIODO_CONTROL_ANIO, false, new CatPeriodoControlAnio(), false, true),
	IVA(CatalogosComunes.IVA, false, new CatIva(), false, true),
	IEPS(CatalogosComunes.IEPS, false, new CatIeps(), false, true),
	SECCION(CatalogosComunes.SECCION, true, new CatSeccion(), false, false),
	PERIODICIDAD(CatalogosComunes.PERIODICIDAD, false, new CatPeriodicidad(), false, true),
	FONDEO(CatalogosComunes.FONDEO, false, new CatFondeoContrato(), false, true),
	RESPONSABILIDAD(CatalogosComunes.RESPONSABILIDAD, false, new CatResponsabilidad(), false, true),
	ESTATUS_FACTURA(CatalogosComunes.ESTATUS_FACTURA, true, new CatEstatusFactura(), false, false),
	ACUERDO_PAGO(CatalogosComunes.ACUERDO_PAGO, false, new CatAcuerdoPago(), false, true),
	ESTATUS_NOTA_CREDITO(CatalogosComunes.ESTATUS_NOTA_CREDITO, true, new CatEstatusNotaCredito(), false, false),
	TIPO_NOTIFICACION_PAGO(CatalogosComunes.TIPO_NOTIFICACION_PAGO, false, new CatTipoNotificacionPago(), false, true),
	TIPO_REINTEGRO(CatalogosComunes.TIPO_REINTEGRO, false, new CatTipoReintegro(), false, true),
	ESTATUS_RCP(CatalogosComunes.ESTATUS_RCP, true, new CatEstatusRcp(), false, false),
	TIPO_PLANTILLADOR(CatalogosComunes.TIPO_PLANTILLADOR, true, new CatTipoPlantillador(), false, false),
	TIPO_DESCUENTO(CatalogosComunes.TIPO_DESCUENTO, false, new CatTipoDescuento(), false, true),
	PROVEEDOR_GIRO_EMPRESARIAL(CatalogosComunes.PROVEEDOR_GIRO_EMPRESARIAL, false, new CatGiroEmpresarial(), false, true),
	PROVEEDOR_TITULO_SERVICIO(CatalogosComunes.PROVEEDOR_TITULO_SERVICIO, false, new CatTituloServicio(), false, true),
	RESULTADO_DICTAMEN_TECNICO(CatalogosComunes.RESULTADO_DICTAMEN_TECNICO, false, new CatResultadoDictamenTecnicoModel(), false, true),
	ESTATUS_TITULO_SERVICIO(CatalogosComunes.ESTATUS_TITULO_SERVICIO, false, new CatEstatusTituloServicio(), false, true),
	URL_PORWER_BI(CatalogosComunes.URL_PORWER_BI, false, new CatUrlPowerBi(), false, true),
	CONVENIO_COLABORACION_REPORTE(CatalogosComunes.CONVENIO_COLABORACION_REPORTE, true, new CatConvenioColaboracionReporte(), false, false),
	CONVENIO_COLABORACION(CatalogosComunes.CONVENIO_COLABORACION, false, new CatConvenioColaboracion(), false, false),
	CORREO(CatalogosComunes.CORREO, false, new CatCorreo(), false, true),
	PUESTOS_ORGANIGRAMA(CatalogosComunes.PUESTOS_ORGANIGRAMA, false, new CatTipoEmpleado(), false, true),
	CLAVE_PRODUCTO(CatalogosComunes.CLAVE_PRODUCTO, false, new CatClaveProducto(), false, true);
	
	 
	
	
	private CatalogosComunes catalogosComunes;
	private Object type;
	private boolean bloqueado;
	private boolean complementario;
	private boolean visible;

	<T extends BaseCatalogoModel> Catalogos(CatalogosComunes catalogosComunes, boolean bloqueado, T type,
			boolean complementario, boolean visible) {
		this.catalogosComunes = catalogosComunes;
		this.type = type;
		this.bloqueado = bloqueado;
		this.complementario = complementario;
		this.visible = visible;
	}

	public static Optional<Catalogos> obtenerCatalogo(int id) {
		for (Catalogos cat : values()) {
			if (cat.getCatalogosComunes().getIdCatalogo() == id) {
				return Optional.of(cat);
			}
		}
		return Optional.empty();
	}

}
