package com.sisecofi.admindevengados.service.estimacion.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sisecofi.admindevengados.dto.AtributosId;
import com.sisecofi.admindevengados.dto.EstimacionResponse;
import com.sisecofi.admindevengados.dto.ServicioEstimacionDtoMod;
import com.sisecofi.admindevengados.dto.estimacion.ConveniosResponse;
import com.sisecofi.admindevengados.dto.estimacion.EstimacionProyeccionDto;
import com.sisecofi.admindevengados.microservicio.CatalogoMicroservicio;
import com.sisecofi.admindevengados.model.DictaminadoModel;
import com.sisecofi.admindevengados.model.ServicioEstimadoModel;
import com.sisecofi.admindevengados.repository.ContratoRepository;
import com.sisecofi.admindevengados.repository.ConvenioModificatorioRepository;
import com.sisecofi.admindevengados.repository.DictamenRepository;
import com.sisecofi.admindevengados.repository.DictaminadoRepository;
import com.sisecofi.admindevengados.repository.ProveedorRepository;
import com.sisecofi.admindevengados.repository.ResumenConsolidadoRepository;
import com.sisecofi.admindevengados.repository.ServicioContratoRepository;
import com.sisecofi.admindevengados.repository.ServicioConvenioRepository;
import com.sisecofi.admindevengados.repository.ServicioEstimadoRepository;
import com.sisecofi.admindevengados.repository.estimacion.EstimacionRepository;
import com.sisecofi.admindevengados.repository.VigenciaMontosRepository;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoLigeroDto;
import com.sisecofi.libreria.comunes.dto.dictamen.DevengadoBusquedaResponse;
import com.sisecofi.libreria.comunes.dto.dictamen.FacturaContratoDto;
import com.sisecofi.admindevengados.service.PistaService;
import com.sisecofi.admindevengados.service.estimacion.EstimacionService;
import com.sisecofi.admindevengados.service.facturas.FacturaService;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.admindevengados.util.UrlDecoderUtil;
import com.sisecofi.admindevengados.util.consumer.DictamenMap;
import com.sisecofi.admindevengados.util.consumer.ReporteServiciosEstimadosConsumer;
import com.sisecofi.libreria.comunes.util.consumer.MonthMapper;
import com.sisecofi.libreria.comunes.util.consumer.ReporteDictamenesAsociadosConsumer;
import com.sisecofi.libreria.comunes.util.consumer.ReporteFacturasAsociadasConsumer;
import com.sisecofi.admindevengados.util.enums.ErroresEnum;
import com.sisecofi.admindevengados.util.exception.CatalogoException;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusEstimacion;
import com.sisecofi.libreria.comunes.model.catalogo.CatIva;
import com.sisecofi.libreria.comunes.model.catalogo.CatPeriodoControlAnio;
import com.sisecofi.libreria.comunes.model.catalogo.CatPeriodoControlMes;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.contratos.ServicioContratoModel;
import com.sisecofi.libreria.comunes.model.contratos.VigenciaMontosModel;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ServicioConvenioModel;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.model.estimacion.EstimacionModel;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.libreria.comunes.util.sesion.Session;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstimacionServiceImpl implements EstimacionService {

	private final EstimacionRepository estimacionRepository;
	private final CatalogoMicroservicio catalogoMicroservicio;
	private final Session session;
	private final DictamenRepository dictamenRepository;
	private final ResumenConsolidadoRepository resumenConsolidadoRepository;
	private final ReporteDictamenesAsociadosConsumer consumer;
	private final PistaService pistaService;
	private final VigenciaMontosRepository vigenciaMontosRepository;
	private final ContratoRepository contratoRepository;
	private final ProveedorRepository proveedorRepository;
	private final FacturaService facturaService;
	private final ReporteFacturasAsociadasConsumer consumerF;
	private final ServicioContratoRepository servicioContratoRepository;
	private final ServicioEstimadoRepository servicioEstimadoRepository;
	private final ReporteServiciosEstimadosConsumer consumerSer;
	private final DictaminadoRepository dictaminadoRepository;
	private final ConvenioModificatorioRepository convenioRepository;
	private final ServicioConvenioRepository servicioConvenioRepository;
	private static final String CANCELADO = "Cancelado";
	private static final String ESTIMADO = "Estimado";
	private static final String EXPRESION= "^0+(?!$)";

	@Override
	@Transactional
	public EstimacionResponse crearEstimacion(EstimacionModel estimacion) {
		validarFechas(estimacion);
		ContratoModel contrato = contratoRepository.findByIdContratoAndEstatusTrue(estimacion.getIdContrato())
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ESTIMACION_NO_ENCONTRADA));

		validarDuplicidad(estimacion.getIdContrato(), estimacion.getIdProveedor(), estimacion.getIdPeriodoControlAnio(),
				estimacion.getIdPeriodoControlMes(), null);
		estimacion.setFechaCreacion(horaActual());
		estimacion.setEstatus(true);


		estimacion.setUltimaModificacion(ultimaModificacion());
		estimacion.setConvenio(contrato.getUltimoConvenioModificatorio());
		estimacion.setConsecutivo(generarConsecutivo(estimacion.getIdContrato(), estimacion.getIdProveedor()));
		List<ServicioEstimadoModel> lista = new ArrayList<>();

		if (estimacion.isDuplicado()) {
			EstimacionModel est =generarEstimacion(estimacion.getAnterior());
			
			estimacion.setConvenio(est.getConvenio());
			lista = obtenerServiciosEstimados(estimacion.getAnterior(), null);
		}

		estimacionRepository.save(estimacion);

		if (estimacion.isDuplicado()) {
			for (ServicioEstimadoModel srv : lista) {
				ServicioEstimadoModel nuevo = new ServicioEstimadoModel(srv, estimacion.getIdEstimacion());
				servicioEstimadoRepository.save(nuevo);
			}
		}

		EstimacionResponse dto = agruparRespuesta(estimacion);



		// pistaService.guardarPista(ModuloPista.ESTIMACION.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),


		// TipoSeccionPista.ESTIMACION_DATOS_GENERALES.getIdSeccionPista(),


		// Constantes.getAtributosGenerales()[1] + estimacion.getIdEstimacion()


		// + Constantes.getAtributosGenerales()[2] + contrato.getProyecto().getNombreCorto() + "| "


		// + dto.toString(),


		// Optional.empty());

		return dto;
	}

	private EstimacionResponse agruparRespuesta(EstimacionModel estimacion) {
		ContratoLigeroDto contrato = contratoRepository.findByIdContratoDto(estimacion.getIdContrato())
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ESTIMACION_NO_ENCONTRADA));
		EstimacionResponse response = new EstimacionResponse(estimacion);
		  
		response.setNumeroContrato(contrato.getNumeroContrato());
		CatEstatusEstimacion catEstatusEstimacion = catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
				CatalogosComunes.ESTATUS_ESTIMACION.getIdCatalogo(), estimacion.getIdEstatusEstimacion(),
				CatEstatusEstimacion.class);
		CatIva catIva = catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
				CatalogosComunes.IVA.getIdCatalogo(), estimacion.getIdIva(), CatIva.class);
		CatPeriodoControlMes catPeriodoControlMes = catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
				CatalogosComunes.PERIODO_CONTROL_MES.getIdCatalogo(), estimacion.getIdPeriodoControlMes(),
				CatPeriodoControlMes.class);
		CatPeriodoControlAnio catPeriodoControlAnio = catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
				CatalogosComunes.PERIODO_CONTROL_ANIO.getIdCatalogo(), estimacion.getIdPeriodoControlAnio(),
				CatPeriodoControlAnio.class);
		ProveedorModel proveedor = proveedorRepository.findByIdProveedor(estimacion.getIdProveedor())
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ESTIMACION_NO_ENCONTRADA));

		response.setNombreProveedor(proveedor.getNombreProveedor());
		response.setCatEstatusEstimacion(catEstatusEstimacion);
		response.setCatPeriodoControlAnio(catPeriodoControlAnio);
		response.setCatPeriodoControlMes(catPeriodoControlMes);
		response.setCatIva(catIva);
		response.setNombreCortoContrato(contrato.getNombreCorto());
		obtenerAnteriorYSiguiente(response);
		Optional<VigenciaMontosModel> vigencia = vigenciaMontosRepository
				.findByIdContratoAndEstatusTrue(contrato.getIdContrato());
		if (vigencia.isPresent()) {
			response.setFechaInicio(vigencia.get().getFechaInicioVigenciaContrato());
			response.setFechaTermino(vigencia.get().getFechaFinVigenciaContrato());
		}
		return response;
	}
	
	private void obtenerAnteriorYSiguiente(EstimacionResponse response) {
        
        int numero = response.getConsecutivo();
        
        if (numero <= 1) {
        	response.setAnteriorEstimacion(null);
        }else {
        	int numeroAnterior = numero - 1;
        	
        	response.setAnteriorEstimacion(numeroAnterior);
        }
        
        int numeroSiguiente = numero+ 1;
       
        if(estimacionRepository.existsByIdContratoAndIdProveedorAndEstatusTrueAndConsecutivo(response.getIdContrato(), response.getIdProveedor(), numeroSiguiente)) {
        	response.setSiguienteEstimacion(numeroSiguiente);
        }
        
    }


	@Transactional
	private String ultimaModificacion() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String formattedDate = horaActual().format(formatter);

		String nombreUsuario = obtenerUsuario().map(Usuario::getNombre).orElse("");

		return nombreUsuario + " " + formattedDate;
	}

	private Optional<Usuario> obtenerUsuario() {
		return session.retornarUsuario();
	}

	private LocalDateTime horaActual() {
		ZoneId zoneId = ZoneId.of("America/Mexico_City");
		ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
		return zonedDateTime.toLocalDateTime();
	}

	public Integer generarConsecutivo(Long idContrato, Long idProveedor) {
		return estimacionRepository.obtenerSiguienteConsecutivo(idContrato, idProveedor);
	}


	private void validarFechas(EstimacionModel estimacion) {
		ContratoModel contrato = contratoRepository.findByIdContratoAndEstatusTrue(estimacion.getIdContrato())
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ESTIMACION_NO_ENCONTRADA));

		LocalDate periodoInicio = estimacion.getPeriodoInicio().toLocalDate();
		LocalDate periodoFin = estimacion.getPeriodoFin().toLocalDate();

		VigenciaMontosModel vigencia = contrato.getVigencia();
		LocalDateTime fechaTerminoContrato = null;
		LocalDateTime fechaInicioContrato = null;
		if (vigencia != null) {
			fechaTerminoContrato = vigencia.getFechaFinVigenciaContrato();
			fechaInicioContrato = vigencia.getFechaInicioVigenciaContrato();
			if (contrato.getUltimoConvenioModificatorio() != null) {
				fechaTerminoContrato = contrato.getUltimoConvenioModificatorio().getFechaFin();
			}

		}
		if (fechaInicioContrato == null || fechaTerminoContrato == null) {
			throw new CatalogoException(ErroresEnum.CONTRATO_SIN_FECHAS);
		} else {
			if (!validarRango(periodoInicio, fechaInicioContrato.toLocalDate(), fechaTerminoContrato.toLocalDate())
					|| !validarRango(periodoFin, periodoInicio, fechaTerminoContrato.toLocalDate())) {
				throw new CatalogoException(ErroresEnum.PERIODO_ESTIMACION_INCORRECTO);
			}

			String mes = catalogoMicroservicio
					.obtenerInformacionCatalogoIdEspecifico(CatalogosComunes.PERIODO_CONTROL_MES.getIdCatalogo(),
							estimacion.getIdPeriodoControlMes(), CatPeriodoControlMes.class)
					.getNombre();

			Month month = MonthMapper.getMonth(mes.toUpperCase());

			String anio = catalogoMicroservicio
					.obtenerInformacionCatalogoIdEspecifico(CatalogosComunes.PERIODO_CONTROL_ANIO.getIdCatalogo(),
							estimacion.getIdPeriodoControlAnio(), CatPeriodoControlAnio.class)
					.getNombre();
			YearMonth fechaVerificar = YearMonth.of(Integer.parseInt(anio), month);

			YearMonth inicio = YearMonth.from(periodoInicio);
			YearMonth fin = YearMonth.from(periodoFin);

			if (!validarRangoPeriodo(fechaVerificar, inicio, fin)) {
				throw new CatalogoException(ErroresEnum.PERIODO_ESTIMACION_INCORRECTO);
			}
		}

	}

	private boolean validarRango(LocalDate date, LocalDate start, LocalDate end) {
		return (date.isEqual(start) || date.isAfter(start)) && (date.isEqual(end) || date.isBefore(end));
	}

	private boolean validarRangoPeriodo(YearMonth date, YearMonth start, YearMonth end) {
		return (date.equals(start) || date.isAfter(start)) && (date.equals(end) || date.isBefore(end));
	}

	private void validarDuplicidad(Long idContrato, Long idProveedor, Integer idAnio, Integer idMes, Long idEstimacion) {
		Long lista;
		if (idEstimacion ==null) {
			lista = estimacionRepository
					.countByIdContratoAndIdProveedorAndIdPeriodoControlAnioAndIdPeriodoControlMesAndEstatusTrueAndIdEstatusEstimacionNot(idContrato,
							idProveedor, idAnio, idMes, 3);
		}else {
			lista = estimacionRepository
					.countByIdContratoAndIdProveedorAndIdPeriodoControlAnioAndIdPeriodoControlMesAndEstatusTrueAndIdEstatusEstimacionNotAndIdEstimacionNot(idContrato,
							idProveedor, idAnio, idMes, 3, idEstimacion);
		}
		
		if (lista>0) {
			throw new CatalogoException(ErroresEnum.ESTIMACION_REPETIDA);
		}
	}

	@Override
	public List<DevengadoBusquedaResponse> obtenerDictamenesAsociados(String idEstimacion) {
		EstimacionModel estimacion = obtenerEstimacionModel(idEstimacion);
		return buscarDictamenesAsociados(estimacion);
	}
	
	@Override
	public EstimacionModel generarEstimacion(String id) {
		String idDecodificado = UrlDecoderUtil.decodeId(id);
		String[] partes = idDecodificado.split("\\|");
        String nombreC = partes[0];
        Long idProv = Long.parseLong(partes[1].replaceFirst(EXPRESION, ""));
        String[] subPartes = partes[2].split("-");
        Integer consec = Integer.parseInt(subPartes[0].replaceFirst(EXPRESION, ""));
        
        return estimacionRepository.findByContratoModelNombreCortoAndIdProveedorAndConsecutivoAndEstatusTrue(nombreC, idProv, consec)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ESTIMACION_NO_ENCONTRADA));
	}

	@Override
	public EstimacionResponse obtenerEstimacion(String idEstimacion) {
		
		EstimacionModel estimacion = generarEstimacion(idEstimacion);
		calcularMontoTotal(estimacion);
		return agruparRespuesta(estimacion);
	}

	private EstimacionModel obtenerEstimacionModel(String idEstimacion) {
		EstimacionModel estimacion = generarEstimacion(idEstimacion);
		calcularMontoTotal(estimacion);
		return estimacion;
	}

	@Override
	@Transactional
	public void calcularMontoTotal(EstimacionModel estimacion) {
		procesarEstimacion(estimacion);
	}

	@Override
	@Transactional
	public EstimacionModel obtenerMontoTotal(EstimacionModel estimacion) {
		return procesarEstimacion(estimacion);
	}

	private EstimacionModel procesarEstimacion(EstimacionModel estimacion) {
		List<ServicioEstimadoModel> lista = obtenerServiciosEstimados("", estimacion.getIdEstimacion());
		calcularMontoTotalPersist(estimacion, lista);

		Optional<VigenciaMontosModel> vigenciaOptional = vigenciaMontosRepository
				.findByIdContratoAndEstatusTrue(estimacion.getIdContrato());

		BigDecimal montoEstimado = estimacion.getMontoEstimado();

		actualizarMontoEstimadoPesos(estimacion, montoEstimado, vigenciaOptional);
		estimacion.setMontoEstimado(montoEstimado.setScale(2, RoundingMode.HALF_UP));

		estimacionRepository.save(estimacion);
		return estimacion;
	}

	private BigDecimal obtenerIva(EstimacionModel estimacion) {
		try {
			return new BigDecimal(estimacion.getCatIva().getPorcentaje()).divide(BigDecimal.valueOf(100));
		} catch (NumberFormatException | ArithmeticException | NullPointerException e) {
			return BigDecimal.ZERO;
		}
	}

	private boolean esMontoValido(ServicioEstimadoModel servicio) {
		return servicio.getMontoEstimado() != null && servicio.getMontoEstimado().compareTo(BigDecimal.ZERO) > 0;
	}

	private BigDecimal calcularMontoConIva(BigDecimal monto, BigDecimal iva) {
		return monto.add(monto.multiply(iva));
	}

	private BigDecimal calcularMontoConIeps(ServicioEstimadoModel servicio, BigDecimal montoEstimadoServicio,
			Optional<VigenciaMontosModel> vigenciaOptional) {
		BigDecimal ieps = BigDecimal.ZERO;
		if (servicioTieneIeps(servicio, vigenciaOptional)) {
			ieps = obtenerIeps(vigenciaOptional);
		}

		return montoEstimadoServicio.multiply(ieps);
	}

	private boolean servicioTieneIeps(ServicioEstimadoModel servicio, Optional<VigenciaMontosModel> vigenciaOptional) {
		return Boolean.TRUE.equals(servicio.getIeps()) && vigenciaOptional.isPresent();
	}

	private BigDecimal obtenerIeps(Optional<VigenciaMontosModel> vigenciaOptional) {
		if (vigenciaOptional.isPresent()) {
			try {
				return new BigDecimal(vigenciaOptional.get().getCatIeps().getPorcentaje())
						.divide(BigDecimal.valueOf(100));
			} catch (NumberFormatException | ArithmeticException | NullPointerException e) {
				return BigDecimal.ZERO;
			}
		}
		return BigDecimal.ZERO;
	}

	private void actualizarMontoEstimadoPesos(EstimacionModel estimacion, BigDecimal montoEstimado,
			Optional<VigenciaMontosModel> vigenciaOptional) {
		if (vigenciaOptional.isPresent() && !vigenciaOptional.get().getCatTipoMoneda().getIdTipoMoneda().equals(1)) {
			BigDecimal montoEstimadoPesos = montoEstimado.multiply(estimacion.getTipoCambioReferencialEnt());
			estimacion.setMontoEstimadoPesos(montoEstimadoPesos.setScale(2, RoundingMode.HALF_UP));
		} else {
			estimacion.setMontoEstimadoPesos(montoEstimado.setScale(2, RoundingMode.HALF_UP));
		}
	}

	private List<DevengadoBusquedaResponse> buscarDictamenesAsociados(EstimacionModel estimacion) {
		List<Dictamen> lista = dictamenRepository
				.findByIdContratoAndIdProveedorAndIdPeriodoControlAnioAndIdPeriodoControlMesAndEstatusTrueOrderByIdDictamenAsc(
						estimacion.getIdContrato(), estimacion.getIdProveedor(),
						estimacion.getIdPeriodoControlAnio(), estimacion.getIdPeriodoControlMes());

		List<DevengadoBusquedaResponse> resultado = new ArrayList<>();
		DictamenMap dictamenMap = new DictamenMap(resumenConsolidadoRepository);
		for (Dictamen dictamen : lista) {
			resultado.add(dictamenMap.apply(dictamen));
		}
		return resultado;
	}

	@Override
	public byte[] exportarExcelDictamenesAsociados(String idEstimacion) {
		EstimacionModel estimacion = obtenerEstimacionModel(idEstimacion);
		List<DevengadoBusquedaResponse> lista = buscarDictamenesAsociados(estimacion);

		consumer.inializar("Dictámenes asociados");
		consumer.agregarCabeceras(Constantes.TITULOS_REPORTE_DICTAMENES_ASOCIADOS);
		lista.stream().forEach(consumer);

		String resultado = lista.stream()
				.map(dictamen -> Constantes.getAtributosGenerales()[3] + estimacion.getContratoModel().getNombreCorto()
						+ Constantes.getAtributosGenerales()[9] + estimacion.getProveedorModel().getNombreProveedor()
						+ Constantes.getAtributosGenerales()[10] + dictamen.getId())
				.collect(Collectors.joining("")); 

 

		// pistaService.guardarPista(ModuloPista.ESTIMACION.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),
 

		// TipoSeccionPista.ESTIMACION_DATOS_GENERALES.getIdSeccionPista(), resultado, Optional.empty());

		return consumer.cerrarBytes();

	}

	@Override
	public List<EstimacionModel> obtenerEstimacionPorIdContrato(Long idContrato) {
		try {
			return estimacionRepository.findByContratoModelIdContratoAndEstatusTrue(idContrato);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ESTIMACION_REPETIDA);
		}
	}

	@Override
	@Transactional
	public EstimacionResponse modificarEstimacion(EstimacionModel estimacion) {
		validarDuplicidad(estimacion.getIdContrato(), estimacion.getIdProveedor(),
					estimacion.getIdPeriodoControlAnio(), estimacion.getIdPeriodoControlMes(), estimacion.getIdEstimacion());
		
		EstimacionModel original = estimacionRepository.findByIdEstimacionAndEstatusTrue(estimacion.getIdEstimacion())
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ESTIMACION_NO_ENCONTRADA));

		validarFechas(estimacion);
		estimacion.setUltimaModificacion(ultimaModificacion());
		estimacion.setConvenio(original.getConvenio());
		estimacionRepository.save(estimacion);
		calcularMontoTotal(estimacion);

		ContratoModel contrato = contratoRepository.findByIdContratoAndEstatusTrue(estimacion.getIdContrato())
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ESTIMACION_NO_ENCONTRADA));

		EstimacionResponse dto = agruparRespuesta(estimacion);



		// pistaService.guardarPista(ModuloPista.ESTIMACION.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),


		// TipoSeccionPista.ESTIMACION_DATOS_GENERALES.getIdSeccionPista(),


		// Constantes.getAtributosGenerales()[1] + estimacion.getIdEstimacion()


		// + Constantes.getAtributosGenerales()[2] + contrato.getProyecto().getNombreCorto() + "| "


		// + dto.toString(),


		// Optional.empty());
		return dto;
	}

	@Override
	public EstimacionResponse duplicarEstimacion(String idEstimacion) {
		EstimacionModel estimacion = obtenerEstimacionModel(idEstimacion);

		estimacion.setUltimaModificacion(ultimaModificacion());
		EstimacionResponse response = new EstimacionResponse(estimacion);
		response.setConsecutivo(generarConsecutivo(estimacion.getIdContrato(), estimacion.getIdProveedor()));
		response.setDuplicado(true);
		response.setAnterior(idEstimacion);
		return response;
	}

	@Override
	public List<FacturaContratoDto> obtenerFacturasAsociadas(String idEstimacion) {
		AtributosId atributos= generarAtributosId(idEstimacion);
		
		Optional<EstimacionModel> estimacionOptional = estimacionRepository
				.findByContratoModelNombreCortoAndIdProveedorAndConsecutivoAndEstatusTrue(atributos.getNombreCorto(), atributos.getIdProveedor(), atributos.getConsecutivo());
		if (estimacionOptional.isPresent()) {
			EstimacionModel estimacion = estimacionOptional.get();
			List<Dictamen> lista = dictamenRepository
					.findByIdContratoAndIdProveedorAndIdPeriodoControlAnioAndIdPeriodoControlMesAndEstatusTrueOrderByIdDictamenAsc(
							estimacion.getIdContrato(), estimacion.getIdProveedor(),
							estimacion.getIdPeriodoControlAnio(), estimacion.getIdPeriodoControlMes());
			return facturaService.obtenerFacturasEstimacion(lista);
		} else {
			return Collections.emptyList();
		}
	}
	
	@Override
	public AtributosId generarAtributosId(String id) {
		String idDecodificado = UrlDecoderUtil.decodeId(id);
		String[] partes = idDecodificado.split("\\|");
        String nombreC = partes[0];
        Long idProv = Long.parseLong(partes[1].replaceFirst(EXPRESION, ""));
        String[] subPartes = partes[2].split("-");
        Integer consec = Integer.parseInt(subPartes[0].replaceFirst(EXPRESION, ""));
        return new AtributosId(nombreC, idProv, consec);
	}

	@Override
	public byte[] exportarFacturasAsociadas(String idEstimacion) {
		try {
			List<FacturaContratoDto> lista = obtenerFacturasAsociadas(idEstimacion);
			consumerF.inializar("Facturas asociadas");
			consumerF.agregarCabeceras(Constantes.TITULOS_REPORTE_FACTURAS_ASOCIADAS);
			lista.stream().forEach(consumerF);

			EstimacionModel estimacion = obtenerEstimacionModel(idEstimacion);

			String resultado = lista.stream()
					.map(factura -> Constantes.getAtributosGenerales()[3]
							+ estimacion.getContratoModel().getNombreCorto() + Constantes.getAtributosGenerales()[9]
							+ estimacion.getProveedorModel().getNombreProveedor()
							+ Constantes.getAtributosGenerales()[11] + factura.getComprobanteFiscal())
					.collect(Collectors.joining(""));



			// pistaService.guardarPista(ModuloPista.ESTIMACION.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),


			// TipoSeccionPista.ESTIMACION_DATOS_GENERALES.getIdSeccionPista(), resultado, Optional.empty());

			return consumerF.cerrarBytes();
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}
	}

	@Override
	@Transactional
	public EstimacionResponse cambiarAInicial(String idEstimacion) {
		EstimacionModel estimacion = generarEstimacion(idEstimacion);
		
		validarDuplicidad(estimacion.getIdContrato(), estimacion.getIdProveedor(), estimacion.getIdPeriodoControlAnio(),
				estimacion.getIdPeriodoControlMes(), estimacion.getIdEstimacion());
		
		estimacion.setIdEstatusEstimacion(1);
		estimacion.setUltimaModificacion(ultimaModificacion());
		estimacionRepository.save(estimacion);
		
		EstimacionResponse response= agruparRespuesta(estimacion);



		// pistaService.guardarPista(ModuloPista.ESTIMACION.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),


		// TipoSeccionPista.ESTIMACION_DATOS_GENERALES.getIdSeccionPista(),


		// Constantes.getAtributosGenerales()[1] + response.getIdEstimacion() + Constantes.getAtributosGenerales()[5]


		// + Constantes.getAtributosGenerales()[6] + "|",


		// Optional.of(estimacion));
		return response;
	}
	

	@Override
	@Transactional
	public EstimacionResponse cancelarEstimacion(String idEstimacion, String justificacion) {
		EstimacionModel estimacion = generarEstimacion(idEstimacion);
		
		estimacion.setIdEstatusEstimacion(3);
		estimacion.setJustificacion(justificacion);
		estimacion.setUltimaModificacion(ultimaModificacion());

		estimacionRepository.save(estimacion);
		
		EstimacionResponse response = agruparRespuesta(estimacion);



		// pistaService.guardarPista(ModuloPista.ESTIMACION.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),


		// TipoSeccionPista.ESTIMACION_DATOS_GENERALES.getIdSeccionPista(),


		// Constantes.getAtributosGenerales()[1] +  response.getIdEstimacion() + Constantes.getAtributosGenerales()[5]


		// + Constantes.getAtributosGenerales()[7] + "|",


		// Optional.of(estimacion));

		return response;
	}

	@Override
	@Transactional
	public ServicioEstimacionDtoMod volumetriaEstimada(List<ServicioEstimadoModel> servicios, String idEstimacion) {
		EstimacionModel estimacion = generarEstimacion(idEstimacion);
		for (ServicioEstimadoModel servicio : servicios) {
			servicio.setIdEstimacion(estimacion.getIdEstimacion());	
			if (servicio.getMontoEstimado() == null) {
				servicio.setMontoEstimado(BigDecimal.ZERO);
			}
			if (servicio.getCantidadServiciosEstimados() == null) {
				servicio.setCantidadServiciosEstimados(BigDecimal.ZERO);
			}
		}
		
		estimacion.setIdEstatusEstimacion(2);
		estimacionRepository.save(estimacion);
		obtenerEstimacion(idEstimacion);

		ContratoModel contrato = contratoRepository.findByIdContratoAndEstatusTrue(estimacion.getIdContrato())
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ESTIMACION_NO_ENCONTRADA));



		// pistaService.guardarPista(ModuloPista.ESTIMACION.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),


		// TipoSeccionPista.ESTIMACION_REGISTRO_SERVICIOS.getIdSeccionPista(),


		// Constantes.getAtributosGenerales()[1] + obtenerIdVisual(contrato.getNombreCorto(),estimacion.getIdProveedor(), estimacion.getConsecutivo())+ Constantes.getAtributosGenerales()[5]


		// + Constantes.getAtributosGenerales()[8] + Constantes.getAtributosGenerales()[3]


		// + contrato.getIdContrato() + Constantes.getAtributosGenerales()[9] + estimacion.getIdProveedor()


		// + "|",


		// Optional.of(estimacion));

		return guardarServicios(servicios, idEstimacion, true);
	}
	
	private String obtenerIdVisual(String nombreContrato, Long idProveedor, Integer consecutivo) {
		return nombreContrato + "|" + String.format("%05d", idProveedor) + "|" + String.format("%05d", consecutivo) + "-E";
	}

	// --------------------servicio de estimacion-----------------

	@Override
	@Transactional
	public List<ServicioEstimadoModel> obtenerServiciosEstimados(String idEstimacion, Long idEstimacionBd) {
		EstimacionModel estimacion;
		if (idEstimacionBd ==null) {
			estimacion = generarEstimacion(idEstimacion);
		}else {
			estimacion = estimacionRepository.findByIdEstimacionAndEstatusTrue(idEstimacionBd)
					.orElseThrow(() -> new CatalogoException(ErroresEnum.ESTIMACION_NO_ENCONTRADA));
		}
		
		ContratoModel contrato = contratoRepository.findByIdContratoAndEstatusTrue(estimacion.getIdContrato())
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ESTIMACION_NO_ENCONTRADA));

		List<ServicioContratoModel> lista = servicioContratoRepository
				.obtenerServiciosOrdenados(estimacion.getIdContrato());
		
		Integer primerId= 0;
		if (!lista.isEmpty()) {
			primerId = Integer.parseInt(""+servicioContratoRepository.obtenerPrimerIdServicioContrato(estimacion.getIdContrato()))-1;
		}
		
	    for (ServicioContratoModel serv: lista)	{
	    	if (serv.getIdServicioContrato().intValue() == serv.getOrden()) {
	    		serv.setOrden(serv.getIdServicioContrato().intValue() - primerId);
			}
	    }
	    
	    lista.sort(Comparator.comparingInt(ServicioContratoModel::getOrden));
		
		
		List<ServicioEstimadoModel> listaEstimados = servicioEstimadoRepository
				.findByIdEstimacionAndServicioBaseEstatusTrueOrderByServicioBaseIdServicioContrato(estimacion.getIdEstimacion());
		for (ServicioContratoModel servicioContrato : lista) {
			boolean found = listaEstimados.stream()
					.anyMatch(servicioEstimado -> servicioEstimado.getServicioBase().getIdServicioContrato().equals(servicioContrato.getIdServicioContrato()));
			if (!found) {
				ServicioEstimadoModel servicioNuevo = new ServicioEstimadoModel();
				servicioNuevo.setServicioBase(servicioContrato);
				servicioNuevo.setIdEstimacion(estimacion.getIdEstimacion());
				servicioNuevo.setPrecioUnitarioActual(servicioContrato.getPrecioUnitario());
				servicioNuevo.setIeps(servicioContrato.getIeps());
				servicioNuevo.setMontoMaximoVigente(servicioContrato.getMontoMaximo());
				servicioNuevo.setEstimacionModel(estimacion);
				servicioNuevo.setCantidadMaximaServiciosVigente(
						servicioContrato.getCantidadMaxima() != null ? servicioContrato.getCantidadMaxima()
								: BigDecimal.ZERO);

				servicioNuevo= servicioEstimadoRepository.save(servicioNuevo);

				// pistaService.guardarPista(ModuloPista.ESTIMACION.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),

				// TipoSeccionPista.ESTIMACION_REGISTRO_SERVICIOS.getIdSeccionPista(),

				// Constantes.getAtributosGenerales()[1] + estimacion.getIdEstimacion()

				// + Constantes.getAtributosGenerales()[3] + contrato.getIdContrato()

				// + Constantes.getAtributosGenerales()[4] + servicioContrato.getIdServicioContrato()

				// + "|",

				// Optional.of(servicioNuevo));

				listaEstimados.add(servicioNuevo);

			}
		}
		
		if (contrato.getUltimoCm()!=null) {
			cambioMaximos(listaEstimados, contrato.getUltimoCm());
		}

		if (estimacion.getConvenio() != null) {
			cambioPrecio(listaEstimados, estimacion.getConvenio().getNumeroConvenio());
		} else {
			actualizaPrecio(listaEstimados);
			servicioEstimadoRepository.saveAll(listaEstimados);
		}

		List<ServicioEstimadoModel> listaOrdenada = calcularServicio(listaEstimados, estimacion);

		servicioEstimadoRepository.saveAll(listaOrdenada);

		listaOrdenada.sort(Comparator.comparing(servicio -> servicio.getServicioBase().getOrden()));
		
		Map<Long, List<ServicioEstimadoModel>> agrupadosPorId = listaOrdenada.stream()
			    .collect(Collectors.groupingBy(servicio -> servicio.getServicioBase().getIdServicioContrato()));

		List<ServicioEstimadoModel> duplicados = agrupadosPorId.values().stream()
			    .filter(listaD -> listaD.size() > 1) 
			    .flatMap(Collection::stream) 
			    .toList();

		servicioEstimadoRepository.deleteAll(duplicados); 
		
		listaOrdenada.removeAll(duplicados);

		return listaOrdenada;

	}
	

	public void procesarEstimacionesYDictamenes(List<Dictamen> listaDictamenes,
			List<ServicioEstimadoModel> listaEstimadosActual, List<ServicioEstimadoModel> listaEstimadosAnterior) {

		Set<ServicioEstimadoModel> listaAProcesar = new HashSet<>();
		listaAProcesar.addAll(listaEstimadosActual);
		listaAProcesar.addAll(listaEstimadosAnterior);

		Set<Long> idsServiciosContrato = obtenerIdsServiciosContrato(listaAProcesar);

		procesarVolumetrias(listaDictamenes, listaAProcesar, idsServiciosContrato);
		procesarBolsa(listaDictamenes, listaAProcesar, idsServiciosContrato);

		for (ServicioEstimadoModel original : listaAProcesar)
			calcularPorcentajes(original);

	}

	private Set<Long> obtenerIdsServiciosContrato(Set<ServicioEstimadoModel> listaEstimadosActual) {
		return listaEstimadosActual.stream().map(servicio -> servicio.getServicioBase().getIdServicioContrato())
				.collect(Collectors.toSet());
	}

	private void procesarVolumetrias(List<Dictamen> listaDictamenes, Set<ServicioEstimadoModel> listaEstimadosActual,
			Set<Long> idsServiciosContrato) {

		List<ServicioEstimadoModel> estimadosVolumetria = new ArrayList<>(listaEstimadosActual);
		estimadosVolumetria.removeIf(servicio -> !servicio.getTipoConsumo().equals("Volumetría"));

		List<DictaminadoModel> dictaminadoVolumetria = dictaminadoRepository
				.findByDictamenInAndIdServicioContratoInAndServicioContratoModelGrupoServiciosModelCatTipoConsumoNombre(
						listaDictamenes, idsServiciosContrato, "Volumetría");

		Map<Long, BigDecimal> sumNumeroServiciosEstimaciones = sumarPorGrupo(estimadosVolumetria,
				servicio -> servicio.getServicioBase().getIdServicioContrato(),
				ServicioEstimadoModel::getCantidadServiciosEstimadosEnt);
		Map<Long, BigDecimal> sumMontosEstimaciones = sumarPorGrupo(estimadosVolumetria,
				servicio -> servicio.getServicioBase().getIdServicioContrato(),
				ServicioEstimadoModel::getMontoEstimadoEnt);

		Map<Long, BigDecimal> sumNumeroServiciosDictamenes = sumarPorGrupo(dictaminadoVolumetria,
				DictaminadoModel::getIdServicioContrato, DictaminadoModel::getCantidadAsInt);
		Map<Long, BigDecimal> sumMontosDictamenes = sumarPorGrupo(dictaminadoVolumetria,
				DictaminadoModel::getIdServicioContrato, DictaminadoModel::getMontoDictaminado);

		combinarSumas(sumNumeroServiciosEstimaciones, sumNumeroServiciosDictamenes);
		combinarSumas(sumMontosEstimaciones, sumMontosDictamenes);

		asignarValoresSumadosVol(estimadosVolumetria, sumNumeroServiciosEstimaciones, sumMontosEstimaciones, true);

	}

	private void procesarBolsa(List<Dictamen> listaDictamenes, Set<ServicioEstimadoModel> listaEstimadosActual,
			Set<Long> idsServiciosContrato) {

		List<ServicioEstimadoModel> estimadosBolsa = new ArrayList<>(listaEstimadosActual);
		estimadosBolsa.removeIf(servicio -> !servicio.getTipoConsumo().equals("Bolsa"));

		List<DictaminadoModel> dictaminadoBolsa = dictaminadoRepository
				.findByDictamenInAndIdServicioContratoInAndServicioContratoModelGrupoServiciosModelCatTipoConsumoNombre(
						listaDictamenes, idsServiciosContrato, "Bolsa");

		Map<Long, BigDecimal> sumNumeroServiciosEstimaciones = sumarPorGrupo(estimadosBolsa,
				servicio -> servicio.getServicioBase().getIdServicioContrato(),
				ServicioEstimadoModel::getCantidadServiciosEstimadosEnt);
		Map<Long, BigDecimal> sumMontosEstimaciones = sumarPorGrupo(estimadosBolsa,
				ServicioEstimadoModel::getIdGrupoServicio, ServicioEstimadoModel::getMontoEstimadoEnt);
		Map<Long, BigDecimal> sumNumeroServiciosDictamenes = sumarPorGrupo(dictaminadoBolsa,
				DictaminadoModel::getIdServicioContrato, DictaminadoModel::getCantidadAsInt);
		Map<Long, BigDecimal> sumMontosDictamenes = sumarPorGrupo(dictaminadoBolsa,
				DictaminadoModel::getIdGrupoServicio, DictaminadoModel::getMontoDictaminado);

		combinarSumas(sumNumeroServiciosEstimaciones, sumNumeroServiciosDictamenes);
		combinarSumas(sumMontosEstimaciones, sumMontosDictamenes);

		asignarValoresSumados(estimadosBolsa, sumMontosEstimaciones);

		asignarValoresSumadosVol(estimadosBolsa, sumNumeroServiciosEstimaciones, sumMontosEstimaciones, false);
	}

	private <T> Map<Long, BigDecimal> sumarPorGrupo(List<T> lista, Function<T, Long> grupoFunc,
			Function<T, BigDecimal> valorFunc) {
		return lista.stream().collect(
				Collectors.groupingBy(grupoFunc, Collectors.reducing(BigDecimal.ZERO, valorFunc, BigDecimal::add)));
	}

	private void combinarSumas(Map<Long, BigDecimal> sumasEstimaciones, Map<Long, BigDecimal> sumasDictamenes) {
		sumasDictamenes.forEach((key, value) -> sumasEstimaciones.merge(key, value, BigDecimal::add));
	}

	private void asignarValoresSumados(List<ServicioEstimadoModel> estimadosBolsa, Map<Long, BigDecimal> sumMontos) {
		estimadosBolsa.forEach(estimacion -> estimacion
				.setMontoEstimadoAcumulado(sumMontos.getOrDefault(estimacion.getIdGrupoServicio(), BigDecimal.ZERO)));
	}

	private void asignarValoresSumadosVol(List<ServicioEstimadoModel> estimadosBolsa,
			Map<Long, BigDecimal> sumNumeroServicios, Map<Long, BigDecimal> sumMontos, boolean todos) {
		asignarServiciosAcumulados(estimadosBolsa, sumNumeroServicios);
		if (todos) {
			asignarMontosAcumulados(estimadosBolsa, sumMontos);
		}
	}

	private void asignarServiciosAcumulados(List<ServicioEstimadoModel> estimadosBolsa,
			Map<Long, BigDecimal> sumNumeroServicios) {
		estimadosBolsa.forEach(estimacion -> estimacion.setServiciosAcumulados(sumNumeroServicios
				.getOrDefault(estimacion.getServicioBase().getIdServicioContrato(), BigDecimal.ZERO)));
	}

	private void asignarMontosAcumulados(List<ServicioEstimadoModel> estimadosBolsa, Map<Long, BigDecimal> sumMontos) {
		estimadosBolsa.forEach(estimacion -> estimacion.setMontoEstimadoAcumulado(
				sumMontos.getOrDefault(estimacion.getServicioBase().getIdServicioContrato(), BigDecimal.ZERO)));
	}

	private ServicioEstimadoModel calcularServicioModel(ServicioEstimadoModel original, BigDecimal cantidadServicios) {
		BigDecimal cantidadOriginal = Optional.ofNullable(original.getCantidadServiciosEstimadosLimp())
				.orElse(BigDecimal.ZERO);
		BigDecimal cantidadAcumulada = Optional.ofNullable(original.getServiciosAcumulados()).orElse(BigDecimal.ZERO);
		BigDecimal precioUnitario = Optional.ofNullable(original.getPrecioUnitarioActual()).orElse(BigDecimal.ZERO);

		BigDecimal montoEstimado = Optional.ofNullable(cantidadServicios).orElse(BigDecimal.ZERO)
				.multiply(precioUnitario).setScale(2, RoundingMode.HALF_UP);

		original.setMontoEstimado(montoEstimado);

		BigDecimal montoEstimadoAcumulado = Optional.ofNullable(original.getMontoEstimadoAcumulado())
				.orElse(BigDecimal.ZERO);
		BigDecimal montoEstimadoOriginal = Optional.ofNullable(original.getMontoEstimado()).orElse(BigDecimal.ZERO);
		BigDecimal montoAcumuladoOriginal = montoEstimadoAcumulado.subtract(montoEstimadoOriginal);
		BigDecimal montoAcumuladoActual = montoEstimado.add(montoAcumuladoOriginal);

		original.setMontoEstimadoAcumulado(montoAcumuladoActual);

		BigDecimal serviciosAcumulados = cantidadAcumulada.subtract(cantidadOriginal)
				.add(Optional.ofNullable(cantidadServicios).orElse(BigDecimal.ZERO));

		original.setServiciosAcumulados(
				serviciosAcumulados.compareTo(BigDecimal.ZERO) > 0 ? serviciosAcumulados : null);

		original.setCantidadServiciosEstimados(cantidadServicios);

		return original;
	}

	private void calcularPorcentajes(ServicioEstimadoModel servicio) {
		BigDecimal cantidadMaxima = servicio.getCantidadMaximaServiciosVigente();

		BigDecimal serviciosAcumulados = servicio.getServiciosAcumulados() != null ? servicio.getServiciosAcumulados()
				: BigDecimal.ZERO;

		BigDecimal porcentajeServicios;

		if (serviciosAcumulados.compareTo(BigDecimal.ZERO) != 0 && cantidadMaxima.compareTo(BigDecimal.ZERO) != 0) {
			porcentajeServicios = serviciosAcumulados.divide(cantidadMaxima, 4, RoundingMode.HALF_UP);
		} else {
			porcentajeServicios = BigDecimal.ZERO;
		}

		BigDecimal porcentajeServicioFinal = porcentajeServicios
				.multiply(BigDecimal.valueOf(100).setScale(1, RoundingMode.HALF_UP));

		servicio.setPorcentajeServiciosEstimadosAcumulados(porcentajeServicioFinal);
		BigDecimal montoEstimadoAcumulado = servicio.getMontoEstimadoAcumulado() != null
				? servicio.getMontoEstimadoAcumulado()
				: BigDecimal.ZERO;

		BigDecimal montoMaximo = servicio.getMontoMaximoVigente() != null ? servicio.getMontoMaximoVigente()
				: BigDecimal.ONE;

		BigDecimal porcentajeMonto = montoMaximo.compareTo(BigDecimal.ZERO) != 0
				? montoEstimadoAcumulado.divide(montoMaximo, 4, RoundingMode.HALF_UP)
				: BigDecimal.ZERO;

		BigDecimal porcentajeMontoFinal = porcentajeMonto.multiply(BigDecimal.valueOf(100));

		servicio.setPorcentajeMontoEstimadoAcumulado(
				porcentajeMontoFinal != null ? porcentajeMontoFinal : BigDecimal.ZERO);
	}

	@Override
	@Transactional
	public ServicioEstimacionDtoMod guardarServicios(List<ServicioEstimadoModel> servicios, String idEstimacion, boolean volumetria) {
		ServicioEstimacionDtoMod dto = new ServicioEstimacionDtoMod();
		
		for (ServicioEstimadoModel sv : servicios) {
				ServicioEstimadoModel sv2 = servicioEstimadoRepository.findById(sv.getIdServicioEstimado())
						.orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_SERVICIO_INEXISTENTE));
				sv.setServicioBase(sv2.getServicioBase()); 
				sv.setEstimacionModel(sv2.getEstimacionModel());
				sv.setCantidadMaximaServiciosVigente(sv2.getCantidadMaximaServiciosVigente());
				sv.setMontoMaximoVigente(sv2.getMontoMaximoVigente());
				sv.setIeps(sv2.getIeps());
				sv.setPorcentajeServiciosEstimadosAcumulados(sv2.getPorcentajeServiciosEstimadosAcumulados());
				sv.setPorcentajeMontoEstimadoAcumulado(sv2.getPorcentajeMontoEstimadoAcumulado());
				sv.setPrecioUnitarioActual(sv2.getPrecioUnitarioActual());		
				}
		
		EstimacionModel estimacion = generarEstimacion(idEstimacion);
		
		ContratoModel contrato = contratoRepository.findByIdContratoAndEstatusTrue(estimacion.getIdContrato())
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ESTIMACION_NO_ENCONTRADA));
		
		estimacion.setUltimaModificacion(ultimaModificacion());
		dto.setUltimaModificacion(ultimaModificacion());
		
		if (volumetria) {
			servicios= calcularServicio(servicios, estimacion);
			calcularMontoTotalPersist(estimacion, servicios);
			dto.setServicios(obtenerServiciosEstimados(idEstimacion, null));
		}else {
			calcularMontoEstimado(servicios);
			dto.setServicios(servicios);
			calcularMontoTotalPersist(estimacion, servicios);
			estimacionRepository.save(estimacion);
		}
		
		servicioEstimadoRepository.saveAll(servicios);

		// servicios.forEach(serv -> pistaService.guardarPista(ModuloPista.ESTIMACION.getId(),
		// 	TipoMovPista.ACTUALIZA_REGISTRO.getId(),
		// 	TipoSeccionPista.ESTIMACION_REGISTRO_SERVICIOS.getIdSeccionPista(),
		// 	Constantes.getAtributosGenerales()[1] + obtenerIdVisual(contrato.getNombreCorto(),estimacion.getIdProveedor(), estimacion.getConsecutivo())+ Constantes.getAtributosGenerales()[3]
		// 		+ contrato.getIdContrato() + Constantes.getAtributosGenerales()[4]
		// 		+ serv.getIdServicioEstimado() + "|",
		// 	Optional.of(serv)));

		dto.setMontoEstimado(estimacion.getMontoEstimado());
		dto.setMontoEstimadoPesos(estimacion.getMontoEstimadoPesos());
		return dto;
	}
	
	private void calcularMontoEstimado(List<ServicioEstimadoModel> servicios) {
		for (ServicioEstimadoModel svr: servicios) {
			BigDecimal montoEstimado = Optional.ofNullable(svr.getCantidadServiciosEstimadosEnt()).orElse(BigDecimal.ZERO)
					.multiply(svr.getPrecioUnitarioActual()).setScale(2, RoundingMode.HALF_UP);
			svr.setMontoEstimado(montoEstimado);		
			}
	}

	@Override
	public byte[] exportarExcelServicios(String idEstimacion) {
		List<ServicioEstimadoModel> lista = obtenerServiciosEstimados(idEstimacion, null);
		consumerSer.inializar("Servicios estimados");
		consumerSer.agregarCabeceras(Constantes.TITULOS_SERVICIOS_ESTIMADOS);
		lista.stream().forEach(consumerSer);

		String resultado = lista.stream()
				.map(servicioEstimado -> Constantes.getAtributosGenerales()[3]
						+ servicioEstimado.getServicioBase().getContratoModel().getNombreCorto()
						+ Constantes.getAtributosGenerales()[9]
						+ servicioEstimado.getEstimacionModel().getProveedorModel().getNombreProveedor()
						+ Constantes.getAtributosGenerales()[4]
						+ servicioEstimado.getServicioBase().getIdServicioContrato() + servicioEstimado.toString())
				.collect(Collectors.joining(""));



		// pistaService.guardarPista(ModuloPista.ESTIMACION.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),


		// TipoSeccionPista.ESTIMACION_REGISTRO_SERVICIOS.getIdSeccionPista(), resultado, Optional.empty());

		return consumerSer.cerrarBytes();
	}

	@Override
	public ConveniosResponse obtenerConvenios(String idEstimacion) {
		EstimacionModel estimacion = generarEstimacion(idEstimacion);
		ConveniosResponse response = new ConveniosResponse();
		if (estimacion.getConvenio() != null) {
			response.setSeleccionado(estimacion.getConvenio().getNumeroConvenio());
		}
		response.setNombreCorto(estimacion.getContratoModel().getNombreCorto());
		List<ConvenioModificatorioModel> convenios = convenioRepository
				.findByIdContratoAndEstatusTrue(estimacion.getIdContrato());
		List<String> numerosContratos = new ArrayList<>();
		convenios.stream().map(ConvenioModificatorioModel::getNumeroConvenio).forEach(numerosContratos::add);
		response.setConvenios(numerosContratos);
		return response;
	}

	@Override
	@Transactional
	public List<ServicioEstimadoModel> cambiarPrecioUnitario(String idEstimacion, String numeroConvenio) {
		EstimacionModel estimacion = generarEstimacion(idEstimacion);

		String sinComillas = numeroConvenio.replace("\"", "");
		String numero = sinComillas.trim();

		ConvenioModificatorioModel convenio = convenioRepository.findByNumeroConvenioAndEstatusTrue(numero)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ESTIMACION_NO_ENCONTRADA));

		log.error("numero convenio: {}", numero);

		estimacion.setConvenio(convenio);

		if (estimacion.getConvenio() == null) {
			throw new CatalogoException(ErroresEnum.ESTIMACION_NO_ENCONTRADA);
		}
		estimacionRepository.save(estimacion);
		return obtenerServiciosEstimados(idEstimacion, null);

	} 

	private void cambioPrecio(List<ServicioEstimadoModel> listaEstimados, String numeroConvenio) {
		List<ServicioConvenioModel> lista = servicioConvenioRepository
				.findByConvenioNumeroConvenioAndServicioBaseEstatusTrue(numeroConvenio);

		for (ServicioEstimadoModel estimado : listaEstimados) {
			for (ServicioConvenioModel convenio : lista) {
				if (estimado.getServicioBase().getIdServicioContrato()
						.equals(convenio.getServicioBase().getIdServicioContrato())) {
					estimado.setPrecioUnitarioActual(convenio.getPrecioUnitario());
					estimado.setIeps(convenio.getIeps());
					break;
				}
			}

		}
	}
	
	private void cambioMaximos(List<ServicioEstimadoModel> listaEstimados, String numeroConvenio) {
		List<ServicioConvenioModel> lista = servicioConvenioRepository
				.findByConvenioNumeroConvenioAndServicioBaseEstatusTrue(numeroConvenio);

		for (ServicioEstimadoModel estimado : listaEstimados) {
			for (ServicioConvenioModel convenio : lista) {
				if (estimado.getServicioBase().getIdServicioContrato()
						.equals(convenio.getServicioBase().getIdServicioContrato())) {
					estimado.setCantidadMaximaServiciosVigente(convenio.getNumeroTotalServicios());
					estimado.setMontoMaximoVigente(convenio.getMontoMaximoTotal());
					break;
				}
			}

		}
	}

	private void actualizaPrecio(List<ServicioEstimadoModel> listaEstimados) {

		for (ServicioEstimadoModel estimado : listaEstimados) {
			estimado.setPrecioUnitarioActual(estimado.getServicioBase().getPrecioUnitario());
			estimado.setIeps(estimado.getServicioBase().getIeps());
			calcularServicioModel(estimado, estimado.getCantidadServiciosEstimados());
		}
	}

	@Override
	@Transactional
	public List<ServicioEstimadoModel> calcularServicio(List<ServicioEstimadoModel> servicios, EstimacionModel estima) {
		EstimacionModel estimacion = Optional.ofNullable(estima).orElseGet(
				() -> estimacionRepository.findByIdEstimacionAndEstatusTrue(servicios.get(0).getIdEstimacion())
						.orElseThrow(() -> new CatalogoException(ErroresEnum.ESTIMACION_NO_ENCONTRADA)));

		List<String> listaExclusion = Arrays.asList(CANCELADO, "Inicial");

		servicios.forEach(servicio -> {
			EstimacionProyeccionDto original = Optional
					.ofNullable(
							servicioEstimadoRepository.findCantidadYAcumuladosById(servicio.getIdServicioEstimado()))
					.orElseThrow(() -> new CatalogoException(ErroresEnum.SERVICIO_NO_ENCONTRADO));

			BigDecimal cantidad = servicio.getCantidadServiciosEstimados();
			servicio.setCantidadServiciosEstimados(original.getCantidadServiciosEstimados());
			servicio.setServiciosAcumulados(original.getServiciosAcumulados());
			calcularServicioModel(servicio, cantidad);
		});

		CatPeriodoControlAnio anio = estimacion.getCatPeriodoControlAnio();
		CatPeriodoControlMes mes= estimacion.getCatPeriodoControlMes();
		if(anio==null) {
			anio= catalogoMicroservicio.obtenerInformacionCatalogoIdReference(CatalogosComunes.PERIODO_CONTROL_ANIO.getIdCatalogo(), estimacion.getIdPeriodoControlAnio(), new CatPeriodoControlAnio());
			mes= catalogoMicroservicio.obtenerInformacionCatalogoIdReference(CatalogosComunes.PERIODO_CONTROL_MES.getIdCatalogo(), estimacion.getIdPeriodoControlMes(), new CatPeriodoControlMes());
		}
		ContratoModel contrato = contratoRepository.findByIdContratoAndEstatusTrue(estimacion.getIdContrato())
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ESTIMACION_NO_ENCONTRADA));
		
		LocalDate fechaEstimacion = LocalDate.of(Integer.parseInt(anio.getNombre()),
				MonthMapper.getMonth(mes.getNombre()), 2);

		List<Dictamen> listaDictamenesFin = dictamenRepository
			    .findByContratoModelNombreCortoAndIdProveedorAndAndEstatusTrueAndCatEstatusDictamenNombreNotIn(
			        contrato.getNombreCorto(), estimacion.getIdProveedor(), listaExclusion)
			    .stream()
			    .filter(obj -> obj.getPeriodoFecha().isBefore(fechaEstimacion)) 
			    .toList();
		
		
		boolean dictaminado = listaDictamenesFin.stream()
			    .anyMatch(dictamen -> 
			        dictamen.getIdPeriodoControlAnio().equals(estimacion.getIdPeriodoControlAnio()) &&
			        dictamen.getIdPeriodoControlMes().equals(estimacion.getIdPeriodoControlMes())
			    );

		
		if (dictaminado) {
		    servicios.forEach(svrE -> svrE.setDictaminado(true));
		}
		

		List<EstimacionModel> estimacionesFiltradas = estimacionRepository
				.findByIdContratoAndIdProveedorAndEstatusTrueAndEstadoNotAndIdEstimacionNot(estimacion.getIdContrato(),
						estimacion.getIdProveedor(), ESTIMADO, estimacion.getIdEstimacion())
				.stream().filter(obj -> obj.getPeriodoFecha().isBefore(fechaEstimacion))
				.filter(estimacionFil -> listaDictamenesFin.stream().noneMatch(
						dictamen -> dictamen.getIdPeriodoControlMes().equals(estimacionFil.getIdPeriodoControlMes())
								&& dictamen.getIdPeriodoControlAnio().equals(estimacionFil.getIdPeriodoControlAnio())))
				.toList();

		List<ServicioEstimadoModel> listaOtrosEstimados = servicioEstimadoRepository
				.findByEstimacionModelInAndAndServicioBaseEstatusTrue(estimacionesFiltradas);

		procesarEstimacionesYDictamenes(listaDictamenesFin, servicios, listaOtrosEstimados);

		return servicios;
	}

	@Override
	@Transactional
	public EstimacionResponse obtenerEstimacionPersist(List<ServicioEstimadoModel> servicios) {
		Long idDecodificado = 0L;
		if (!servicios.isEmpty()) {
			idDecodificado = servicios.get(0).getIdEstimacion();
		}
		EstimacionModel estimacion = estimacionRepository.findByIdEstimacionAndEstatusTrue(idDecodificado)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ESTIMACION_NO_ENCONTRADA));
		calcularMontoTotalPersist(estimacion, servicios);
		return agruparRespuesta(estimacion);
	}

	private void calcularMontoTotalPersist(EstimacionModel estimacion, List<ServicioEstimadoModel> servicios) {
		List<ServicioEstimadoModel> lista = servicios;
		BigDecimal montoEstimado = BigDecimal.ZERO;
		BigDecimal iva = obtenerIva(estimacion);
		Optional<VigenciaMontosModel> vigenciaOptional = vigenciaMontosRepository
				.findByIdContratoAndEstatusTrue(estimacion.getIdContrato());

		for (ServicioEstimadoModel servicio : lista) {
			if (esMontoValido(servicio)) {
				BigDecimal montoEstimadoServicio = servicio.getMontoEstimado();

				BigDecimal subtotalIeps = calcularMontoConIeps(servicio, montoEstimadoServicio, vigenciaOptional);

				BigDecimal montoConIeps = montoEstimadoServicio.add(subtotalIeps);

				BigDecimal subtotalIva = calcularMontoConIva(montoConIeps, iva);

				montoEstimado = montoEstimado.add(subtotalIva);
			}
		}

		actualizarMontoEstimadoPesos(estimacion, montoEstimado, vigenciaOptional);
		estimacion.setMontoEstimado(montoEstimado.setScale(2, RoundingMode.HALF_UP));
		estimacionRepository.save(estimacion);
	}

}