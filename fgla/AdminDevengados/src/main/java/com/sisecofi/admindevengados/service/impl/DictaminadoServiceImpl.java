package com.sisecofi.admindevengados.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sisecofi.admindevengados.dto.AtributosId;
import com.sisecofi.admindevengados.dto.DictaminadoDto;
import com.sisecofi.admindevengados.dto.EstimacionDto;
import com.sisecofi.admindevengados.model.DictaminadoModel;
import com.sisecofi.admindevengados.model.ServicioEstimadoModel;
import com.sisecofi.admindevengados.repository.ContratoRepository;
import com.sisecofi.admindevengados.repository.ConvenioModificatorioRepository;
import com.sisecofi.admindevengados.repository.DictamenRepository;
import com.sisecofi.admindevengados.repository.DictaminadoRepository;
import com.sisecofi.admindevengados.repository.ServicioContratoRepository;
import com.sisecofi.admindevengados.repository.ServicioConvenioRepository;
import com.sisecofi.admindevengados.repository.ServicioEstimadoRepository;
import com.sisecofi.admindevengados.repository.estimacion.EstimacionRepository;
import com.sisecofi.admindevengados.service.CatalogoService;
import com.sisecofi.admindevengados.service.DictamenService;
import com.sisecofi.admindevengados.service.DictaminadoService;
import com.sisecofi.admindevengados.service.PistaService;
import com.sisecofi.admindevengados.service.estimacion.EstimacionService;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.admindevengados.util.enums.ErroresEnum;
import com.sisecofi.admindevengados.util.exception.CatalogoException;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.contratos.ServicioContratoModel;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ServicioConvenioModel;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.model.estimacion.EstimacionModel;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class DictaminadoServiceImpl implements DictaminadoService {

	private final ServicioContratoRepository servicioContratoRepository;
	private final DictaminadoRepository dictaminadoRepository;
	private final EstimacionRepository estimacionRepository;
	private final DictamenRepository dictamenRepository;
	private final ConvenioModificatorioRepository convenioModificatorioRepository;
	private final PistaService pistaService;
	private final ServicioEstimadoRepository servicioEstimadoRepository;
	LocalDateTime hoy = LocalDateTime.now();
	private final CatalogoService catalogoService;
	private final ContratoRepository contratoRepository;
	private final ServicioConvenioRepository servicioConvenioRepository;
	private final DictamenService dictamenService;
	private static final String BOLSA = "Bolsa";
	private final EstimacionService estimacionService;

	@Override
	public List<DictaminadoDto> obtenerDictaminados(Long idContrato, Long dictamenId) {
		try {
			ContratoModel contratoConvenio = obtenerContrato(idContrato);
			ConvenioModificatorioModel ultimoConvenioModificatorio = obtenerUltimoConvenio(contratoConvenio);
			log.info("idContrato {}", idContrato);

			List<DictaminadoModel> dictaminadoModelMonto = dictaminadoRepository
					.findByIdDictamenAndDictamenEstatusTrue(dictamenId);
			Dictamen dictamen = obtenerDictamen(dictamenId);
			Boolean estatusCM = determinarEstatusCM(dictaminadoModelMonto, ultimoConvenioModificatorio);

			List<ServicioContratoModel> contratoServicios = servicioContratoRepository
					.obtenerServiciosOrdenados(idContrato);
			
			Integer primerId= 0;
			if (!contratoServicios.isEmpty()) {
				primerId = Integer.parseInt(""+servicioContratoRepository.obtenerPrimerIdServicioContrato(idContrato))-1;
			}
			
		    for (ServicioContratoModel serv: contratoServicios)	{
		    	if (serv.getIdServicioContrato().intValue() == serv.getOrden()) {
		    		serv.setOrden(serv.getIdServicioContrato().intValue() - primerId);
				}
		    }
		    
		    contratoServicios.sort(Comparator.comparingInt(ServicioContratoModel::getOrden));
			
			
			List<ServicioConvenioModel> serviciosConvenio = obtenerServiciosConvenio(ultimoConvenioModificatorio);
			List<ServicioConvenioModel> serviciosConvenioEspecifico = new ArrayList<>();
			if (dictamen.getIdConvenioCodificatorio() != null) {
				ConvenioModificatorioModel convenioModificatorioEspecifico = obtenerConvenioEspecifico(
						dictamen.getIdConvenioCodificatorio());
				serviciosConvenioEspecifico = obtenerServiciosConvenio(convenioModificatorioEspecifico);
			}

			List<DictaminadoDto> listaDictaminados = primeraVez(dictaminadoModelMonto)
					? cargarServiciosPrimeraVez(contratoServicios, serviciosConvenio, dictamenId, estatusCM)
					: cargarServiciosValidandoEstatusCM(contratoServicios, serviciosConvenioEspecifico, serviciosConvenio, dictamenId,
							estatusCM, dictaminadoModelMonto);
			actualizarDictaminados(listaDictaminados, null);
			listaDictaminados.sort(Comparator.comparing(DictaminadoDto::getOrden));
			
			return listaDictaminados;

		} catch (Exception e) {
			log.error("Error al obtener dictaminados para el contrato con id {} y dictamen id {}", idContrato,
					dictamenId, e);
			throw new CatalogoException(ErroresEnum.MSJ_NOT_FOUND_DICTAMEN, e);
		}
	}

	private ContratoModel obtenerContrato(Long idContrato) {
		return contratoRepository.findByIdContrato(idContrato)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.MSJ_NOT_FOUND_DICTAMEN));
	}

	private ConvenioModificatorioModel obtenerUltimoConvenio(ContratoModel contratoConvenio) {
		return contratoConvenio.getUltimoConvenioModificatorio() != null
				? contratoConvenio.getUltimoConvenioModificatorio()
				: null;
	}

	private ConvenioModificatorioModel obtenerConvenioEspecifico(Long idConvenioModificatorio) {
		return convenioModificatorioRepository.findByIdConvenioModificatorioAndEstatusTrue(idConvenioModificatorio)
				.orElse(null);
	}

	private Boolean determinarEstatusCM(List<DictaminadoModel> dictaminadoModelMonto,
			ConvenioModificatorioModel ultimoConvenioModificatorio) {
		return dictaminadoModelMonto.isEmpty() ? ultimoConvenioModificatorio != null
				: dictaminadoModelMonto.get(0).getEstatusCM();
	}

	private List<ServicioConvenioModel> obtenerServiciosConvenio(
			ConvenioModificatorioModel ultimoConvenioModificatorio) {
		return ultimoConvenioModificatorio != null
				? servicioConvenioRepository.findByConvenioNumeroConvenioAndServicioBaseEstatusTrue(
						ultimoConvenioModificatorio.getNumeroConvenio())
				: null;
	}

	private boolean primeraVez(List<DictaminadoModel> dictaminadoModelMonto) {
		return dictaminadoModelMonto.isEmpty();
	}

	private List<DictaminadoDto> cargarServiciosPrimeraVez(List<ServicioContratoModel> contratoServicios,
			List<ServicioConvenioModel> serviciosConvenio, Long dictamenId, Boolean estatusCM) {
		List<DictaminadoDto> listaDictaminados = new ArrayList<>();
		log.info("Primera vez cargando servicios. Usando servicios del convenio.");
		contratoServicios.forEach(servicioContrato -> {
			DictaminadoDto dictaminado = inicializarDictaminado(servicioContrato, dictamenId, estatusCM);
			cargarDatosDesdeConvenioPrimeraVez(servicioContrato, serviciosConvenio, dictaminado);
			listaDictaminados.add(dictaminado);
		});
		return listaDictaminados;
	}

	private List<DictaminadoDto> cargarServiciosValidandoEstatusCM(List<ServicioContratoModel> contratoServicios,
			List<ServicioConvenioModel> serviciosConvenio, List<ServicioConvenioModel> serviciosConvenioUltimoVigente, Long dictamenId, Boolean estatusCM,
			List<DictaminadoModel> dictaminadoModelMonto) {
		List<DictaminadoDto> listaDictaminados = new ArrayList<>();
		log.info("Cargando servicios, validando estatusCM.");

		contratoServicios.forEach(servicioContrato -> {
			DictaminadoDto dictaminado = inicializarDictaminado(servicioContrato, dictamenId, estatusCM);

			if (Boolean.TRUE.equals(estatusCM)) {
				cargarDatosDesdeConvenio(servicioContrato, serviciosConvenio, serviciosConvenioUltimoVigente, dictaminado);
			} else {
				log.info("EstatusCM es false o no existe. Usando servicios del contrato.");
				cargarDatosDeContratoAConvenio(servicioContrato, serviciosConvenioUltimoVigente, dictaminado);
			}

			procesarDictaminado(dictaminado, dictaminadoModelMonto, servicioContrato.getIdServicioContrato());
			listaDictaminados.add(dictaminado);
		});
		return listaDictaminados;
	}
	
	private void cargarDatosDeContratoAConvenio(ServicioContratoModel servicioContrato,	         
	        List<ServicioConvenioModel> serviciosConvenioUltimoVigente, 
	        DictaminadoDto dictaminado) {

		if (serviciosConvenioUltimoVigente != null) {
			serviciosConvenioUltimoVigente.stream()
					.filter(servicioConvenio -> servicioConvenio.getServicioBase().getIdServicioContrato()
							.equals(servicioContrato.getIdServicioContrato()))
					.findFirst().ifPresent(servicioConvenio -> {
						dictaminado.setCantidadServiciosVigente(servicioConvenio.getNumeroTotalServicios());
						dictaminado.setMontoMaximoServicio(servicioConvenio.getMontoMaximoTotal());
						log.info("id del convenio mas actual: {}" + dictaminado.getIdConvenioModificatorio());
					});
		}
	}

	private void cargarDatosDesdeConvenioPrimeraVez(ServicioContratoModel servicioContrato,
			List<ServicioConvenioModel> serviciosConvenio, DictaminadoDto dictaminado) {
		if (serviciosConvenio != null) {
			serviciosConvenio.stream()
					.filter(servicioConvenio -> servicioConvenio.getServicioBase().getIdServicioContrato()
							.equals(servicioContrato.getIdServicioContrato()))
					.findFirst().ifPresent(servicioConvenio -> {
						dictaminado.setCantidadServiciosVigente(servicioConvenio.getNumeroTotalServicios());
						dictaminado.setMontoMaximoServicio(servicioConvenio.getMontoMaximoTotal());
						dictaminado.setIdConvenioModificatorio(serviciosConvenio.get(0).getIdConvenioModificatorio());
						dictaminado.setPrecioUnitario(servicioConvenio.getPrecioUnitario());
						dictaminado.setOrden(servicioConvenio.getServicioBase().getOrden());
						log.info("id del convenio: {}" + dictaminado.getIdConvenioModificatorio());
					});
		}
	}
	
	private void cargarDatosDesdeConvenio(ServicioContratoModel servicioContrato,
	        List<ServicioConvenioModel> serviciosConvenio, 
	        List<ServicioConvenioModel> serviciosConvenioUltimoVigente, 
	        DictaminadoDto dictaminado) {

	    if (serviciosConvenio != null) {
	        serviciosConvenio.stream()
	                .filter(servicioConvenio -> servicioConvenio.getServicioBase().getIdServicioContrato()
	                        .equals(servicioContrato.getIdServicioContrato()))
	                .findFirst().ifPresent(servicioConvenio -> {

	                    serviciosConvenioUltimoVigente.stream()
	                            .filter(ultimoVigente -> ultimoVigente.getServicioBase().getIdServicioContrato()
	                                    .equals(servicioContrato.getIdServicioContrato()))
	                            .findFirst().ifPresent(ultimoVigente -> {
	                                dictaminado.setCantidadServiciosVigente(ultimoVigente.getNumeroTotalServicios());
	                                dictaminado.setMontoMaximoServicio(ultimoVigente.getMontoMaximoTotal());
	                                dictaminado.setOrden(servicioConvenio.getServicioBase().getOrden());
	                            });

	                    dictaminado.setIdConvenioModificatorio(servicioConvenio.getIdConvenioModificatorio());
	                    dictaminado.setPrecioUnitario(servicioConvenio.getPrecioUnitario());
	                    dictaminado.setOrden(servicioConvenio.getServicioBase().getOrden());

	                    log.info("id del convenio: {}", dictaminado.getIdConvenioModificatorio());
	                });
	    }
	}

	private DictaminadoDto inicializarDictaminado(ServicioContratoModel servicioContrato, Long dictamenId,
			Boolean estatusCM) {
		DictaminadoDto dictaminado = new DictaminadoDto();
		dictaminado.setGrupoServicio(servicioContrato.getGrupoServiciosModel().getGrupo());
		dictaminado.setConseptosServico(servicioContrato.getConcepto());
		dictaminado.setIdContrato(servicioContrato.getIdContrato());
		dictaminado.setIdServicioContrato(servicioContrato.getIdServicioContrato());
		dictaminado.setUnidadMedida(servicioContrato.getCatTipoUnidad().getNombre());
		dictaminado.setTipoConsumo(servicioContrato.getGrupoServiciosModel().getCatTipoConsumo().getDescripcion());
		dictaminado.setPrecioUnitario(servicioContrato.getPrecioUnitario());
		dictaminado.setCantidadServiciosVigente(servicioContrato.getCantidadMaxima());
		dictaminado.setMontoMaximoServicio(servicioContrato.getMontoMaximo());
		dictaminado.setIdDictamen(dictamenId);
		dictaminado.setEstatusCM(estatusCM);
		BigDecimal cero = BigDecimal.ZERO;
		dictaminado.setCantidadServiciosSat(cero);
		dictaminado.setCantidadServiciosCc(cero);
		dictaminado.setCantidadTotalServicios(cero);
		dictaminado.setMontoDictaminado(cero);
		dictaminado.setCantidadServicioDictaminadoAcumulado(cero);
		dictaminado.setMontonDictaminadoAcumulado(cero);
		dictaminado.setPorcentajeServiciosDictaminadosAcumulados(cero);
		dictaminado.setPorcentajeMontoDictaminadosAcumulados(cero);
		dictaminado.setChecked(false);
		dictaminado.setOrden(servicioContrato.getOrden());

		return dictaminado;
	}

	private void procesarDictaminado(DictaminadoDto dictaminado, List<DictaminadoModel> dictaminadoModelMonto,
			Long idServicioContrato) {
		dictaminadoModelMonto.stream()
				.filter(dictaminadoExistente -> dictaminadoExistente.getIdServicioContrato().equals(idServicioContrato))
				.findFirst()
				.ifPresent(dictaminadoExistente -> asignarValoresDictaminado(dictaminado, dictaminadoExistente));

		Double porcentajeServicios = dictaminado.getPorcentajeServiciosDictaminadosAcumulados().doubleValue();
		Double porcentajeMonto = dictaminado.getPorcentajeMontoDictaminadosAcumulados().doubleValue();

		dictaminado.setPorcentajeServiciosDictaminadosAcumulados(BigDecimal.valueOf(porcentajeServicios));
		dictaminado.setPorcentajeMontoDictaminadosAcumulados(BigDecimal.valueOf(porcentajeMonto));

		dictaminado.setColorPorcentajetServicios(asignarColorPorcentaje(porcentajeServicios));
		dictaminado.setColorPorcentajeMonto(asignarColorPorcentaje(porcentajeMonto));
	}

	private void asignarValoresDictaminado(DictaminadoDto dictaminado, DictaminadoModel dictaminadoExistente) {
		log.info("ids dictaminados: {}", dictaminadoExistente.getIdDictaminado());
		log.info("ids dictaminados: {}", dictaminadoExistente.getIdServicioContrato());
		dictaminado.setIdDictaminado(dictaminadoExistente.getIdDictaminado());
		dictaminado.setCantidadServiciosSat(dictaminadoExistente.getCantidadServiciosSat());
		dictaminado.setCantidadServiciosCc(dictaminadoExistente.getCantidadServiciosCc());
		dictaminado.setCantidadTotalServicios(dictaminadoExistente.getCantidadTotalServicios());
		dictaminado.setMontoDictaminado(dictaminadoExistente.getMontoDictaminado());
		dictaminado.setCantidadServicioDictaminadoAcumulado(
				dictaminadoExistente.getCantidadServicioDictaminadoAcumulado());
		dictaminado.setMontonDictaminadoAcumulado(dictaminadoExistente.getMontonDictaminadoAcumulado());
		dictaminado.setChecked(dictaminadoExistente.getSeleccionado());
	}

	private String asignarColorPorcentaje(Double porcentaje) {
		if (porcentaje >= 95) {
			return "Rojo";
		} else if (porcentaje >= 70 && porcentaje < 95) {
			return "Naranja";
		} else if (porcentaje < 40) {
			return "Amarillo";
		} else {
			return "Verde";
		}
	}

	// --------------------------------------------

	@Override
	public List<DictaminadoDto> actualizarDictaminados(List<DictaminadoDto> listaDictaminados, String idEstimacion) {
		if (listaDictaminados.isEmpty()) {
			return Collections.emptyList();
		}

		Dictamen dictamenActual = obtenerDictamen(listaDictaminados.get(0).getIdDictamen());
		LocalDateTime periodoControlActual = dictamenActual.getPeriodoControl();

		List<DictaminadoModel> dictamenesValidos = obtenerDictamenesValidos(listaDictaminados, dictamenActual,
				periodoControlActual);

		Map<Long, BigDecimal> acumuladoCantidadPorServicio = new HashMap<>();
		Map<Long, BigDecimal> acumuladoMontoPorServicio = new HashMap<>();
		Map<String, BigDecimal> acumuladoMontoPorClave = new HashMap<>();

		acumularDatos(dictamenesValidos, acumuladoCantidadPorServicio, acumuladoMontoPorServicio,
				acumuladoMontoPorClave);

		procesarDictaminados(listaDictaminados, idEstimacion, dictamenActual, acumuladoCantidadPorServicio,
				acumuladoMontoPorServicio, acumuladoMontoPorClave);

		for (DictaminadoDto dictaminadoModel : listaDictaminados) {
			calcularPorcentajes(dictaminadoModel);
			asignarColor(dictaminadoModel);
		}
		return listaDictaminados;
	}

	private List<DictaminadoModel> obtenerDictamenesValidos(List<DictaminadoDto> listaDictaminados,
			Dictamen dictamenActual, LocalDateTime periodoControlActual) {
		List<DictaminadoModel> bolsaListaMonto = dictaminadoRepository.findByContratoExcludingDictamen(
				listaDictaminados.get(0).getIdContrato(), dictamenActual.getIdDictamen());

		return bolsaListaMonto.stream()
				.filter(model -> model.getDictamen().getPeriodoControl().minusDays(1).isBefore(periodoControlActual))
				.toList();
	}

	private void acumularDatos(List<DictaminadoModel> dictamenesValidos,
			Map<Long, BigDecimal> acumuladoCantidadPorServicio, Map<Long, BigDecimal> acumuladoMontoPorServicio,
			Map<String, BigDecimal> acumuladoMontoPorClave) {
		for (DictaminadoModel registro : dictamenesValidos) {
			String claveGrupo = registro.getServicioContratoModel().getGrupoServiciosModel().getGrupo() + "-"
					+ registro.getServicioContratoModel().getGrupoServiciosModel().getCatTipoConsumo().getNombre();
			Long idServicio = registro.getIdServicioContrato();
			String tipoConsumo = registro.getServicioContratoModel().getGrupoServiciosModel().getCatTipoConsumo()
					.getNombre();

			if (BOLSA.equalsIgnoreCase(tipoConsumo)) {
				acumuladoMontoPorClave.merge(claveGrupo, registro.getMontoDictaminado(), BigDecimal::add);
			}
			acumuladoCantidadPorServicio.merge(idServicio, registro.getCantidadTotalServicios(), BigDecimal::add);
			acumuladoMontoPorServicio.merge(idServicio, registro.getMontoDictaminado(), BigDecimal::add);
		}
	}

	private void procesarDictaminados(List<DictaminadoDto> listaDictaminados, String idEstimacion,
			Dictamen dictamenActual, Map<Long, BigDecimal> acumuladoCantidadPorServicio,
			Map<Long, BigDecimal> acumuladoMontoPorServicio, Map<String, BigDecimal> acumuladoMontoPorClave) {
		for (DictaminadoDto dictaminado : listaDictaminados) {
			actualizarCantidadServiciosSat(dictaminado, idEstimacion);
			calcularServiciosTotales(dictaminado);
			log.info("id del convenio2: {}" + dictaminado.getIdConvenioModificatorio());

			String claveGrupo = dictaminado.getGrupoServicio() + "-" + dictaminado.getTipoConsumo();
			Long idServicioContrato = dictaminado.getIdServicioContrato();
			BigDecimal cantidadAcumulada = acumuladoCantidadPorServicio.getOrDefault(idServicioContrato,
					BigDecimal.ZERO);
			BigDecimal montoAcumulado;

			if (BOLSA.equalsIgnoreCase(dictaminado.getTipoConsumo())) {
				montoAcumulado = acumuladoMontoPorClave.getOrDefault(claveGrupo, BigDecimal.ZERO);
				if (!esCancelado(dictamenActual)) {
					montoAcumulado = montoAcumulado.add(dictaminado.getMontoDictaminado());
					cantidadAcumulada = cantidadAcumulada.add(dictaminado.getCantidadTotalServicios());
				}
				acumuladoMontoPorClave.put(claveGrupo, montoAcumulado);
			} else {
				montoAcumulado = acumuladoMontoPorServicio.getOrDefault(idServicioContrato, BigDecimal.ZERO);
				if (!esCancelado(dictamenActual)) {
					montoAcumulado = montoAcumulado.add(dictaminado.getMontoDictaminado());
					cantidadAcumulada = cantidadAcumulada.add(dictaminado.getCantidadTotalServicios());
				}
				acumuladoMontoPorServicio.put(idServicioContrato, montoAcumulado);
			}

			dictaminado.setMontonDictaminadoAcumulado(montoAcumulado);
			dictaminado.setCantidadServicioDictaminadoAcumulado(cantidadAcumulada);

			actualizarMontoDictaminadoAcumulado(BOLSA, claveGrupo, dictaminado, acumuladoMontoPorClave,
					listaDictaminados);
		}
	}

	private void actualizarMontoDictaminadoAcumulado(String tipoConsumo, String claveGrupo, DictaminadoDto dictaminado,
			Map<String, BigDecimal> acumuladoMontoPorClave, List<DictaminadoDto> listaDictaminados) {
		if (tipoConsumo.equalsIgnoreCase(dictaminado.getTipoConsumo())) {
			BigDecimal montoFinal = acumuladoMontoPorClave.getOrDefault(claveGrupo, BigDecimal.ZERO);
			for (DictaminadoDto dto : listaDictaminados) {
				if (tipoConsumo.equalsIgnoreCase(dto.getTipoConsumo())
						&& dto.getGrupoServicio().equals(dictaminado.getGrupoServicio())) {
					dto.setMontonDictaminadoAcumulado(montoFinal);
				}
			}
		}
	}

	private boolean esCancelado(Dictamen dictamen) {
		return "CANCELADO".equalsIgnoreCase(dictamen.getCatEstatusDictamen().getNombre()) || "Inicial".equalsIgnoreCase(dictamen.getCatEstatusDictamen().getNombre());
	}

	private void actualizarCantidadServiciosSat(DictaminadoDto dictaminado, String idEstimacion) {
		BigDecimal cantidad = dictaminado.getCantidadServiciosSat();
		if (idEstimacion != null && !idEstimacion.isEmpty()) {
			AtributosId atributos = estimacionService.generarAtributosId(idEstimacion);
			
			Long idEstimacionBd = estimacionRepository.findIdEstimacionByContratoAndProveedorAndConsecutivoAndEstatusTrue(atributos.getNombreCorto(), atributos.getIdProveedor(), atributos.getConsecutivo()).orElse(0L);
			ServicioEstimadoModel servicioEstimado = servicioEstimadoRepository
					.findByIdEstimacionAndServicioBaseIdServicioContrato(idEstimacionBd,
							dictaminado.getIdServicioContrato())
					.orElse(null);

			if (servicioEstimado == null || servicioEstimado.getCantidadServiciosEstimados() == null
					|| BigDecimal.ZERO.equals(servicioEstimado.getCantidadServiciosEstimados())) {
				dictaminado.setCantidadServiciosSat(BigDecimal.ZERO);
			} else {
				dictaminado.setCantidadServiciosSat(servicioEstimado.getCantidadServiciosEstimados());
			}
		} else {
			dictaminado.setCantidadServiciosSat(cantidad);
		}
	}

	private void calcularServiciosTotales(DictaminadoDto dictaminado) {
		BigDecimal cantidadServiciosSat = Optional.ofNullable(dictaminado.getCantidadServiciosSat())
				.orElse(BigDecimal.ZERO);
		BigDecimal cantidadServiciosCc = Optional.ofNullable(dictaminado.getCantidadServiciosCc())
				.orElse(BigDecimal.ZERO);

		BigDecimal cantidadTotalServicios = cantidadServiciosSat.add(cantidadServiciosCc).setScale(6,
				RoundingMode.HALF_UP);
		dictaminado.setCantidadTotalServicios(cantidadTotalServicios.setScale(6, RoundingMode.HALF_UP));//<-

		BigDecimal montoDictaminado = dictaminado.getPrecioUnitario().multiply(cantidadTotalServicios).setScale(2,
				RoundingMode.HALF_UP);
		dictaminado.setMontoDictaminado(montoDictaminado);
	}

	private void calcularPorcentajes(DictaminadoDto dictaminado) {
		BigDecimal cantidadMaximaVigente = dictaminado.getCantidadServiciosVigente();

		BigDecimal porcentajeServicios = calcularPorcentaje(dictaminado.getCantidadServicioDictaminadoAcumulado(),
				cantidadMaximaVigente);
		dictaminado.setPorcentajeServiciosDictaminadosAcumulados(porcentajeServicios);

		BigDecimal montoMaximoVigente = dictaminado.getMontoMaximoServicio();

		if (montoMaximoVigente.compareTo(BigDecimal.ZERO) != 0) {

			BigDecimal division = dictaminado.getMontonDictaminadoAcumulado().divide(montoMaximoVigente, 6,
					RoundingMode.HALF_UP);

			BigDecimal multiplicacion = division.multiply(new BigDecimal("100"));

			BigDecimal porcentajeMonto = multiplicacion.setScale(2, RoundingMode.HALF_UP);

			dictaminado.setPorcentajeMontoDictaminadosAcumulados(porcentajeMonto);
		} else {
			dictaminado.setPorcentajeMontoDictaminadosAcumulados(BigDecimal.ZERO);
		}
	}

	private Dictamen obtenerDictamen(Long idDictamen) {
		return dictamenRepository.findByIdDictamen(idDictamen)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.MSJ_NOT_FOUND_DICTAMEN));
	}

	private BigDecimal calcularPorcentaje(BigDecimal cantidad, BigDecimal total) {

		if (total.compareTo(BigDecimal.ZERO) == 0) {//<-
			return BigDecimal.ZERO;
		}

		BigDecimal division = cantidad.divide(total, 6, RoundingMode.HALF_UP);

		BigDecimal multiplicacion = division.multiply(new BigDecimal("100"));

		return multiplicacion.setScale(2, RoundingMode.HALF_UP);

	}

	private void asignarColor(DictaminadoDto dictaminado) {
		Double porcentajeServicios = dictaminado.getPorcentajeServiciosDictaminadosAcumulados().doubleValue();
		Double porcentajeMonto = dictaminado.getPorcentajeMontoDictaminadosAcumulados().doubleValue();

		String colorServicios = asignarColorPorcentaje(porcentajeServicios);
		String colorMonto = asignarColorPorcentaje(porcentajeMonto);

		dictaminado.setColorPorcentajetServicios(colorServicios);
		dictaminado.setColorPorcentajeMonto(colorMonto);
	}

	// --------------------------------------------

	@Override
	public List<DictaminadoDto> guardarServicioDictaminado(List<DictaminadoDto> serviciosSeleccionados) {
		actualizarDictaminados(serviciosSeleccionados, null);
		Boolean aplicaCC = catalogoService.aplicaCC(serviciosSeleccionados.get(0).getIdContrato());
		StringBuilder idsDictaminados = new StringBuilder();
		List<DictaminadoModel> dictaminadoGuardar = new ArrayList<>();

		validarServiciosSeleccionados(serviciosSeleccionados, aplicaCC);

		for (DictaminadoDto dto : serviciosSeleccionados) {
			DictaminadoModel dictaminado = obtenerDictaminado(dto);
			Optional<Dictamen> dictamen = obtenerDictamen(dictaminado, serviciosSeleccionados);
			actualizarDictaminado(dto, dictaminado, dictamen);

			dictaminadoGuardar.add(dictaminadoRepository.save(dictaminado));
			idsDictaminados.append(dto.getIdDictaminado()).append(",");
			dto.setIdDictaminado(dictaminado.getIdDictaminado());
			guardarPista(dictaminado);
		}
		dictamenService.actualizarResumenConsolidado(dictaminadoGuardar.get(0).getIdDictamen());
		return serviciosSeleccionados;
	}

	private void validarServiciosSeleccionados(List<DictaminadoDto> serviciosSeleccionados, Boolean aplicaCC) {
		for (DictaminadoDto dto : serviciosSeleccionados) {
			if (Boolean.TRUE.equals(!dto.getChecked()) && (dto.getCantidadServiciosSat().doubleValue() > 0
					|| (aplicaCC && dto.getCantidadServiciosCc().doubleValue() > 0))) {
				throw new CatalogoException(ErroresEnum.SERVICIOS_NO_SELECCIONADOS_CON_VALORES);
			}
		}
	}

	private DictaminadoModel obtenerDictaminado(DictaminadoDto dto) {
		Optional<DictaminadoModel> dictaminadoExistente = dto.getIdDictaminado() != null
				? dictaminadoRepository.findById(dto.getIdDictaminado())
				: Optional.empty();
		return dictaminadoExistente.orElseGet(() -> {
			DictaminadoModel nuevoDictaminado = new DictaminadoModel();
			nuevoDictaminado.setFechaRegistro(hoy);
			return nuevoDictaminado;
		});
	}

	private Optional<Dictamen> obtenerDictamen(DictaminadoModel dictaminado,
			List<DictaminadoDto> serviciosSeleccionados) {
		if (dictaminado.getDictamen() != null) {
			return Optional.ofNullable(dictaminado.getDictamen());
		} else {
			return dictamenRepository.findByIdDictamen(serviciosSeleccionados.get(0).getIdDictamen());
		}
	}

	private void actualizarDictaminado(DictaminadoDto dto, DictaminadoModel dictaminado, Optional<Dictamen> dictamen) {
		dictaminado.setIdDictaminado(dto.getIdDictaminado());
		dictaminado.setIdDictamen(dto.getIdDictamen());
		dictaminado.setCantidadServiciosSat(dto.getCantidadServiciosSat());
		dictaminado.setCantidadServiciosCc(dto.getCantidadServiciosCc());
		dictaminado.setCantidadTotalServicios(dto.getCantidadTotalServicios());
		dictaminado.setMontoDictaminado(dto.getMontoDictaminado());
		dictaminado.setIdContrato(dto.getIdContrato());
		dictaminado.setIdServicioContrato(dto.getIdServicioContrato());
		dictaminado.setCantidadServicioDictaminadoAcumulado(dto.getCantidadServicioDictaminadoAcumulado());
		dictaminado.setMontonDictaminadoAcumulado(dto.getMontonDictaminadoAcumulado());
		dictaminado.setPorcentajeServiciosDictaminadosAcumulados(dto.getPorcentajeServiciosDictaminadosAcumulados());
		dictaminado.setPorcentajeMontoDictaminadosAcumulados(dto.getPorcentajeMontoDictaminadosAcumulados());
		dictaminado.setUltimaModificacion("" + hoy);
		dictaminado.setSeleccionado(dto.getChecked());
		dictaminado.setEstatusCM(dto.getEstatusCM());

		dictamen.ifPresent(d -> {
			d.setUltimaModificacion(dictamenService.ultimaModificacionGeneral());
			d.setIdConvenioCodificatorio(dto.getIdConvenioModificatorio());
		});
		if (!dictamen.isPresent()) {
			throw new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND);
		}
	}

	private void guardarPista(DictaminadoModel dictaminado) {
		boolean esCreacion = dictaminado.getIdDictaminado() == null;

		// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(),

		// esCreacion ? TipoMovPista.INSERTA_REGISTRO.getId() : TipoMovPista.ACTUALIZA_REGISTRO.getId(),

		// TipoSeccionPista.DICTAMEN_REGISTRO_SERVICIOS.getIdSeccionPista(),

		// Constantes.getAtributosDictaminados()[0] + dictaminado.getIdDictamen()

		// + Constantes.getAtributosDictaminados()[1] + dictaminado.getIdDictaminado() + "|",

		// Optional.of(dictaminado));
	}

	@Override
	public List<EstimacionDto> obtenerEstimaciones(Long idContrato, Long idProveedor) {
		log.info("Consultando estimaciones con idContrato: {} y idProveedor: {}", idContrato, idProveedor);

		List<EstimacionModel> estimaciones = estimacionRepository
				.findByEstatusTrueAndContratoModelIdContratoAndIdProveedorAndCatEstatusEstimacionNombreOrderByCatPeriodoControlAnioNombreDescCatPeriodoControlMesNombreDesc(
						idContrato, idProveedor);

		List<EstimacionDto> lista = new ArrayList<>();

		for (EstimacionModel estimacionDto2 : estimaciones) {
			EstimacionDto estimacionDto = new EstimacionDto();
			estimacionDto.setIdEstimacion(estimacionDto2.getIdEstimacionVisual());
			estimacionDto.setFechaInicio(estimacionDto2.getPeriodoInicio());
			estimacionDto.setFechaTermino(estimacionDto2.getPeriodoFin());
			estimacionDto.setPeriodoControl(estimacionDto2.getCatPeriodoControlMes().getNombre() + " "
					+ estimacionDto2.getCatPeriodoControlAnio().getNombre());
			estimacionDto.setMontoEstimado(estimacionDto2.getMontoEstimado());
			log.info("monto dictaminado {}", estimacionDto2.getMontoEstimado());
			log.info("id estimacion {}", estimacionDto2.getIdEstimacion());
			estimacionDto.setMontoEstimadoPesos(estimacionDto2.getMontoEstimadoPesos());
			lista.add(estimacionDto);
		}

		return lista;
	}
}
