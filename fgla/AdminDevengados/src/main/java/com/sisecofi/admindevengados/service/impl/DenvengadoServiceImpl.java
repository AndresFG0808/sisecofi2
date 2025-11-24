package com.sisecofi.admindevengados.service.impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.sisecofi.admindevengados.dto.DevengadoRequest;
import com.sisecofi.admindevengados.dto.EstimacionNuevaDto;
import com.sisecofi.admindevengados.dto.estimacion.EstimacionBusquedaDTO;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoSimpleDto;
import com.sisecofi.libreria.comunes.dto.contrato.ProveedorDto;
import com.sisecofi.libreria.comunes.dto.dictamen.DevengadoBusquedaResponse;
import com.sisecofi.admindevengados.microservicio.CatalogoMicroservicio;
import com.sisecofi.admindevengados.microservicio.ContratoMicoservicio;
import com.sisecofi.admindevengados.repository.ContratoRepository;
import com.sisecofi.admindevengados.repository.ConvenioModificatorioRepository;
import com.sisecofi.admindevengados.repository.DictamenRepository;
import com.sisecofi.admindevengados.repository.ProveedorRepository;
import com.sisecofi.admindevengados.repository.ResumenConsolidadoRepository;
import com.sisecofi.admindevengados.repository.VigenciaMontosRepository;
import com.sisecofi.admindevengados.repository.estimacion.EstimacionRepository;
import com.sisecofi.admindevengados.service.DevengadoService;
import com.sisecofi.admindevengados.service.DictamenService;
import com.sisecofi.admindevengados.service.PistaService;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.admindevengados.util.consumer.EstimacionMap;
import com.sisecofi.libreria.comunes.util.consumer.ReporteDevengadoDictamenConsumer;
import com.sisecofi.admindevengados.util.consumer.ReporteDevengadoEstimacionConsumer;
import com.sisecofi.admindevengados.util.enums.ErroresEnum;
import com.sisecofi.admindevengados.util.exception.CatalogoException;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusDictamen;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusEstimacion;
import com.sisecofi.libreria.comunes.model.catalogo.CatIva;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.contratos.VigenciaMontosModel;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
import com.sisecofi.libreria.comunes.service.security.SeguridadService;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DenvengadoServiceImpl implements DevengadoService {
	private final CatalogoMicroservicio catalogoMicroservicio;
	private final ContratoMicoservicio contratoMicoservicio;
	private final DictamenService dictamenService;
	private final EstimacionRepository estimacionRepository;
	private final ReporteDevengadoDictamenConsumer reporteDevengadoDictamenConsumer;
	private final ReporteDevengadoEstimacionConsumer reporteDevengadoEstimacionConsumer;
	private final VigenciaMontosRepository vigenciaMontosRepository;
	private final ContratoRepository contratoRepository;
	private final ProveedorRepository proveedorRepository;
	private final ResumenConsolidadoRepository resumenConsolidadoRepository;
	private final PistaService pistaService;
	private final SeguridadService seguridadService;
	private final DictamenRepository dictamenRepository;
	private final ConvenioModificatorioRepository convenioModificatorioRepository;

	@Override
	public List<BaseCatalogoModel> obtenerEstatus(String tipoConsumo) {
		if (tipoConsumo.equals(Constantes.OPCION_SELECT_DICTAMINADO)) {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.ESTATUS_DICTAMEN.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);
		} else if (tipoConsumo.equals(Constantes.OPCION_SELECT_ESTIMADO)) {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.ESTATUS_ESTIMACION.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);
		}
		return Collections.emptyList();
	}

	@Override
	public List<ContratoSimpleDto> obtenerContratosVigentes(String vigente) {
		List<ContratoSimpleDto> lista = new ArrayList<>();
		int rol = 0;
		boolean acceso = seguridadService.validarRolVerificadorGeneral() || seguridadService.validarRolTodosProyectos()
				|| seguridadService.validarRolAdminSistema();
		if (acceso) {
			rol = 1;
		}

		if (vigente.equals(Constantes.OPCION_SELECT_SI) || vigente.equals(Constantes.OPCION_SELECT_SI_A)) {
			lista = contratoMicoservicio.obtenerContratosVig(rol);
		} else if (vigente.equals("No")) {
			lista = contratoMicoservicio.obtenerContratosNoVig(rol);
		} else if (vigente.equals("Todos")) {
			lista = contratoMicoservicio.obtenerContratosModel(rol);
		}

		lista.forEach(elemento -> elemento.setEjecucion(elemento.getIdEstatusContrato() == 2));

		return lista;
	}

	@Override
	public List<ProveedorDto> obtenerProveedoresContrato(Long idContrato) {
		return contratoMicoservicio.obtenerProvedoresP(idContrato);
	}

	@Override
	public List<DevengadoBusquedaResponse> obtenerDictamenesEstimaciones(DevengadoRequest request) {
		int seccion = TipoSeccionPista.ADMINISTRAR_DEVENGADOS_DICTAMEN.getIdSeccionPista();
		if (request.getTipoConsumo().equals(Constantes.OPCION_SELECT_ESTIMADO)) {
			seccion = TipoSeccionPista.ADMINISTRAR_DEVENGADOS_ESTIMACION.getIdSeccionPista();
		}

		// pistaService.guardarPista(ModuloPista.ADMIN_DEVENGADOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(), seccion,

		// obtenerCriterios(request), Optional.empty());
		return buscar(request);
	}

	private List<DevengadoBusquedaResponse> buscar(DevengadoRequest request) {

		List<DevengadoBusquedaResponse> lista = new ArrayList<>();
		if (request.getTipoConsumo().equals(Constantes.OPCION_SELECT_DICTAMINADO)) {
			lista = dictamenService.obtenerDictamenesByEstatusAndProveedor(request.getIdContrato(),
					request.getIdEstatus(), request.getIdProveedor());
		} else if (request.getTipoConsumo().equals(Constantes.OPCION_SELECT_ESTIMADO)) {
			lista = obtenerEstimaciones(request.getIdContrato(), request.getIdProveedor(), request.getIdEstatus());
		}

		return lista;
	}

	private String obtenerCriterios(DevengadoRequest request) {
		StringBuilder criterios = new StringBuilder();

		criterios.append("Contrato vigente: ").append(request.getContratoVigente());

		String contrato = obtenerNombreCortoContrato(request.getIdContrato());
		criterios.append(" |Contrato: ").append(contrato);

		String proveedor = obtenerNombreProveedor(request.getIdProveedor());
		criterios.append(" |Proveedores: ").append(proveedor);

		criterios.append(" |Tipo de consumo: ").append(request.getTipoConsumo());

		String[] estatusArray = request.getTipoConsumo().equals(Constantes.OPCION_SELECT_DICTAMINADO)
				? Constantes.getEstatusDictamen()
				: Constantes.getEstatusEstimacion();
		if (request.getIdEstatus() != null) {
			criterios.append(" |Estatus: ").append(estatusArray[request.getIdEstatus() - 1]);
		}

		return criterios.toString();
	}

	private String obtenerNombreCortoContrato(Long idContrato) {
		return contratoRepository.findNombreCortoByIdContratoAndEstatusTrue(idContrato)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));
	}

	private String obtenerNombreProveedor(Long idProveedor) {
		return proveedorRepository.findNombreProveedorByIdProveedor(idProveedor)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.PROVEEDOR_NO_ENCONTRADO));
	}

	private List<DevengadoBusquedaResponse> obtenerEstimaciones(Long idContrato, Long idProveedor, Integer idEstatus) {
		List<Object[]> estimacionesObj = estimacionRepository.findEstimacionesOptimizado(idContrato,
				idEstatus, idProveedor);
		
		
		List<EstimacionBusquedaDTO> estimaciones = estimacionesObj.stream()
			    .map(EstimacionBusquedaDTO::new) 
			    .toList();

		if (estimaciones.isEmpty())
			return Collections.emptyList();

		Map<String, Object[]> resumenConsolidadoMap = new HashMap<>();

		for (EstimacionBusquedaDTO estimacion : estimaciones) {
			String claveResumen = estimacion.getIdContrato() + "|" + estimacion.getIdProveedor() + "|"
					+ estimacion.getIdPeriodoControlAnio() + "|" + estimacion.getIdPeriodoControlMes();
			if (!resumenConsolidadoMap.containsKey(claveResumen)) {
				List<Object[]> resumen = resumenConsolidadoRepository.findResumenByContratoProveedorPeriodo(
						estimacion.getIdContrato(), estimacion.getIdProveedor(), estimacion.getIdPeriodoControlAnio(),
						estimacion.getIdPeriodoControlMes());

				if (!resumen.isEmpty()) {
					Object[] primerRegistro = resumen.get(0);
					resumenConsolidadoMap.put(claveResumen, primerRegistro);
				}
			}

		}

		return estimaciones.stream().map(t -> EstimacionMap.apply(t, resumenConsolidadoMap))
				.toList();

	}
	
	

	@Override
	public String exportarExcel(DevengadoRequest request) {
		List<DevengadoBusquedaResponse> lista = buscar(request);
		String response = "";
		int seccion = TipoSeccionPista.ADMINISTRAR_DEVENGADOS_DICTAMEN.getIdSeccionPista();
		String idsConcatenados = lista.stream().map(DevengadoBusquedaResponse::getId)
				.collect(Collectors.joining(" | "));

		if (request.getTipoConsumo().equals("Dictaminado")) {
			reporteDevengadoDictamenConsumer.inializar("Dictámenes");
			reporteDevengadoDictamenConsumer.agregarCabeceras(Constantes.TITULOS_REPORTE_DEVENGADO_DICTAMEN);

			lista.stream().forEach(reporteDevengadoDictamenConsumer);
			byte[] reporte = reporteDevengadoDictamenConsumer.cerrarBytes();

			response = Base64.getEncoder().encodeToString(reporte);
		} else if (request.getTipoConsumo().equals("Estimado")) {
			reporteDevengadoEstimacionConsumer.inializar("Estimaciones");
			reporteDevengadoEstimacionConsumer.agregarCabeceras(Constantes.TITULOS_REPORTE_DEVENGADO_ESTIMACION);
			lista.stream().forEach(reporteDevengadoEstimacionConsumer);
			byte[] reporte = reporteDevengadoEstimacionConsumer.cerrarBytes();
			response = Base64.getEncoder().encodeToString(reporte);
			seccion = TipoSeccionPista.ADMINISTRAR_DEVENGADOS_ESTIMACION.getIdSeccionPista();
		}



		// pistaService.guardarPista(ModuloPista.ADMIN_DEVENGADOS.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(), seccion,


		// idsConcatenados, Optional.empty());
		return response;
	}

	@Override
	public EstimacionNuevaDto nuevaEstimacion(Long idContrato, Long idProveedor, String tipo) {
		ContratoModel contrato = contratoRepository.findByIdContratoAndEstatusTrue(idContrato)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));
		VigenciaMontosModel vigencia = vigenciaMontosRepository.findByIdContratoAndEstatusTrue(idContrato)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));
		ProveedorModel proveedor = proveedorRepository.findByIdProveedor(idProveedor)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));
		EstimacionNuevaDto nuevo = new EstimacionNuevaDto(contrato, vigencia, proveedor);
		CatIva catIva = catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
				CatalogosComunes.ESTATUS_ESTIMACION.getIdCatalogo(), vigencia.getId_iva(), CatIva.class);
		nuevo.setCatIva(catIva);
		if (tipo.equals("Estimado")) {
			List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
					CatalogosComunes.ESTATUS_ESTIMACION.getIdCatalogo(), Constantes.ESTATUS_INICIAL);

			if (!lista.isEmpty()) {
				CatEstatusEstimacion catEstatusEstimacion = catalogoMicroservicio
						.obtenerInformacionCatalogoIdEspecifico(CatalogosComunes.ESTATUS_ESTIMACION.getIdCatalogo(),
								lista.get(0).getPrimaryKey(), CatEstatusEstimacion.class);
				nuevo.setCatEstatusEstimacion(catEstatusEstimacion);
			}
		} else if (tipo.equals("Dictaminado")) {
			String idDictamen = dictamenService.generarFormatoIdDictamen(contrato.getNombreCorto(), idProveedor);
			nuevo.setIdDictamen(idDictamen);
			List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
					CatalogosComunes.ESTATUS_DICTAMEN.getIdCatalogo(), Constantes.ESTATUS_INICIAL);
			if (!lista.isEmpty()) {
				CatEstatusDictamen catEstatusDictamen = catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
						CatalogosComunes.ESTATUS_DICTAMEN.getIdCatalogo(), lista.get(0).getPrimaryKey(),
						CatEstatusDictamen.class);
				nuevo.setCatEstatusDictamen(catEstatusDictamen);
			}
		}

		return nuevo;
	}

	@Override
	public boolean comprobarDependencias(Long idContrato) {
	    return estimacionRepository.existsByIdContratoAndEstatusTrue(idContrato)
	        || dictamenRepository.existsByIdContratoAndEstatusTrue(idContrato) || convenioModificatorioRepository.existsByIdContratoAndEstatusTrue(idContrato);
	}


}
