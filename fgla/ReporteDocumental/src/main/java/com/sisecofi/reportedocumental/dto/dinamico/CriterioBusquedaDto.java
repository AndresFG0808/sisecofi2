package com.sisecofi.reportedocumental.dto.dinamico;

import java.time.LocalDate;
import java.util.List;

import com.sisecofi.libreria.comunes.dto.dinamico.GenericReport;
import com.sisecofi.libreria.comunes.util.anotaciones.reportes.FilterField;
import com.sisecofi.libreria.comunes.util.enums.TypeObject;
import com.sisecofi.reportedocumental.util.annotations.TargetDate;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Data
@Builder
@ToString
public class CriterioBusquedaDto implements GenericReport {

	@FilterField(filter = "s1.id_estatus_proyecto", type = TypeObject.TYPE_LIST, nameTarget = "datosGenerales:id,nombreCorto,estatus,nombreProyecto,idAgp", objectTarget = "datosGenerales,fichaTecnica")
	private List<Integer> idEstatusProyecto;

	@FilterField(filter = "s1.id_proyecto", type = TypeObject.TYPE_LIST, nameTarget = "datosGenerales:id,nombreCorto,estatus,nombreProyecto,idAgp", objectTarget = "datosGenerales,fichaTecnica")
	private List<Long> idProyectos;

	@FilterField(filter = "s2.id_estatus_contrato", type = TypeObject.TYPE_LIST, nameTarget = "contrato:estatusContrato,id,nombreContrato,nombreProyecto,numeroContrato,provedor,tipoProcedimiento,fechaInicioContrato,fechaTerminoContrato,ultimoCm,montoMaximo,montoMaximoMc,montoPesos,administracionCentral,administradorContrato", objectTarget = "contrato")
	private List<Integer> idEstatusContratoProyecto;

	@FilterField(filter = "s2.id_contrato", type = TypeObject.TYPE_LIST, nameTarget = "contrato:estatusContrato,id,nombreContrato,nombreProyecto,numeroContrato,provedor,tipoProcedimiento,fechaInicioContrato,fechaTerminoContrato,ultimoCm,montoMaximo,montoMaximoMc,montoPesos,administracionCentral,administradorContrato", objectTarget = "contrato")
	private List<Long> idContratos;

	@FilterField(filter = "s37.id_dominios_tecnologicos", type = TypeObject.TYPE_LIST, nameTarget = "contrato:estatusContrato,id,nombreContrato,nombreProyecto,numeroContrato,provedor,tipoProcedimiento,fechaInicioContrato,fechaTerminoContrato,ultimoCm,montoMaximo,montoMaximoMc,montoPesos,administracionCentral,administradorContrato", objectTarget = "contrato")
	private List<Integer> idDominioTecnologicos;

	@FilterField(filter = "s37.id_convenio_colaboracion", type = TypeObject.TYPE_LIST, nameTarget = "contrato:estatusContrato,id,nombreContrato,nombreProyecto,numeroContrato,provedor,tipoProcedimiento,fechaInicioContrato,fechaTerminoContrato,ultimoCm,montoMaximo,montoMaximoMc,montoPesos,administracionCentral,administradorContrato", objectTarget = "contrato")
	private List<Integer> idConveniosColaboracion;

	@FilterField(filter = "s6.id_proveedor", type = TypeObject.TYPE_LIST, nameTarget = "proveedor:nombreProveedor,nombreComercial,giroEmpresa,directorioContacto,rfc,representanteLegal,tituloServicio,vigencia,fechaVencimiento,cumpleDictamen", objectTarget = "proveedor")
	private List<Integer> idRazonSocial;

	@FilterField(filter = "s35.id_titulo_servicio_proveedor", type = TypeObject.TYPE_LIST, nameTarget = "proveedor:nombreProveedor,nombreComercial,giroEmpresa,directorioContacto,rfc,representanteLegal,tituloServicio,vigencia,fechaVencimiento,cumpleDictamen", objectTarget = "proveedor")
	private List<Integer> idTituloServicio;

	@TargetDate(field = { "fichaTecnica:fechaInicio:s32.fecha_inicio:1",
			"fichaTecnica:fechaInicioProyecto:s11.fecha_inicio:2",
			"contrato:fechaInicioContrato:s38.fecha_inicio_vigencia_contrato:3",
			"estimaciones:periodoInicio:s18.periodo_inicio:4", "dictamen:periodoInicio:s49.periodo_inicio:5",
			"facturas:fechaFacturacion:s56.fecha_facturacion:6:unica",
			"notaCredito:fechaGeneracion:s60.fecha_generacion:7:unica" }, function = "TO_DATE(TO_CHAR(%valor, 'YYYY-MM-DD'), 'YYYY-MM-DD')")
	private LocalDate fechaInicio;

	@TargetDate(field = { "fichaTecnica:fechaFin:s32.fecha_fin:1", "fichaTecnica:fechaFinProyecto:s11.fecha_termino:2",
			"contrato:fechaTerminoContrato:s38.fecha_fin_vigencia_contrato:3",
			"estimaciones:periodoFin:s18.periodo_fin:4", "dictamen:periodoFin:s49.periodo_fin:5",
			"facturas:fechaFacturacion:s56.fecha_facturacion:6:unica",
			"notaCredito:fechaGeneracion:s60.fecha_generacion:7:unica" }, function = "TO_DATE(TO_CHAR(%valor, 'YYYY-MM-DD'), 'YYYY-MM-DD')")
	private LocalDate fechaTermino;

	private boolean acumulada;
	private boolean mensual;

	private int aplicacionPeriodo;

	private DataReporteDto dataReporteDto;

	private int page;
	private int size;
}
