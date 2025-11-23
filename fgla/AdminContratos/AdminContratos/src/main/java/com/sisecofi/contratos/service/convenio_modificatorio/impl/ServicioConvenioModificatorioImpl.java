package com.sisecofi.contratos.service.convenio_modificatorio.impl;

import com.sisecofi.contratos.dto.ConvenioDto;
import com.sisecofi.contratos.dto.ConvenioModificatorioRequest;
import com.sisecofi.contratos.dto.ReporteConvenioModificatorioDto;
import com.sisecofi.contratos.dto.ServiciosConvenioDto;
import com.sisecofi.contratos.microservicios.CatalogoMicroservicio;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ServicioConvenioModel;
import com.sisecofi.contratos.repository.contrato.ContratoRepository;
import com.sisecofi.contratos.repository.contrato.ConvenioModificatorioRepository;
import com.sisecofi.contratos.repository.contrato.ServicioContratoRepository;
import com.sisecofi.contratos.repository.contrato.VigenciaMontosRepository;
import com.sisecofi.contratos.repository.convenio_modificatorio.ServicioConvenioRepository;
import com.sisecofi.contratos.service.PistaService;
import com.sisecofi.contratos.service.convenio_modificatorio.ServicioConvenioModificatorio;
import com.sisecofi.contratos.util.enums.ErroresEnum;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.contratos.util.consumer.ReporteServicioConvenioConsumer;
import com.sisecofi.contratos.util.exception.ContratoException;
import com.sisecofi.libreria.comunes.model.catalogo.CatIva;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoMoneda;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.contratos.ServicioContratoModel;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.libreria.comunes.util.interfaces.ValidacionCompleta;
import com.sisecofi.libreria.comunes.util.interfaces.validaciones.ValidacionMonto;
import com.sisecofi.libreria.comunes.util.interfaces.validaciones.ValidacionTiempo;
import com.sisecofi.libreria.comunes.util.sesion.Session;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicioConvenioModificatorioImpl implements ServicioConvenioModificatorio {

	private final Validator validator;
	private final ContratoRepository contratoRepository;
	private final CatalogoMicroservicio catalogoMicroservicio;
	private final ConvenioModificatorioRepository convenioRepository;
	private final ServicioContratoRepository servicioContratoRepository;
	private final ServicioConvenioRepository servicioConvenioRepository;
	private final Session session;
	private final ReporteServicioConvenioConsumer consumer;
	private final PistaService pistaService;
	private static final String VOLUMETRIA = "Volumetría";
	private static final String BOLSA = "Bolsa";
	private final VigenciaMontosRepository vigenciaMontosRepository;

	@Override
	public List<ConvenioModificatorioModel> obtenerConvenio(Long idContrato) {
		try {
			return convenioRepository.findByIdContratoAndEstatusTrue(idContrato);
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public Page<List<ConvenioModificatorioModel>> obtenerConvenioPage(ConvenioModificatorioRequest request) {
		Pageable pagination = PageRequest.of(request.getPage(), request.getSize());
		return convenioRepository.findByIdContratoAndEstatusTrue(request.getIdContrato(), pagination);
	}

	@Override
	public ConvenioModificatorioModel crearConvenio(ConvenioModificatorioModel convenio, Long idContrato) {
		ContratoModel contrato = contratoRepository.findByIdContrato(idContrato)
				.orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));

		validarConvenio(convenio);

		if(convenio.getIdConvenioModificatorio()==null) {
			Long consecutivo = convenioRepository.countByIdContrato(idContrato);
			String numeroConsecutivo = String.format("%02d", consecutivo + 1);
			convenio.setNumeroConvenio(convenio.getNumeroConvenio() + "C" + numeroConsecutivo);
		}
		
		LocalDateTime fechaFinC = contrato.getVigencia().getFechaFinVigenciaContrato();
		LocalDateTime fechaFinS = contrato.getVigencia().getFechaFinVigenciaServicios();
		if (contrato.getUltimoConvenioModificatorio() != null) {
			fechaFinC = contrato.getUltimoConvenioModificatorio().getFechaFin();
			fechaFinS = contrato.getUltimoConvenioModificatorio().getFechaFinServicio();
		}


		agregarFechas(convenio, fechaFinC, fechaFinS);
		
		if (convenio.getIncremento() == null) {
			convenio.setIncremento(BigDecimal.ZERO);
		}
		if (convenio.getIeps() == null) {
			convenio.setIeps(BigDecimal.ZERO);
		}
		if (convenio.getTipoCambio() == null) {
			convenio.setTipoCambio(BigDecimal.ZERO);
		}

		if (convenio.getSubtotal() == null) {
			convenio.setSubtotal(datosIniciales(idContrato).getSubtotal());
		}
		if (convenio.getIdIva() == null) {
			convenio.setIdIva(datosIniciales(idContrato).getCatIva().getIdIva());
		}
		if (convenio.getCalculoDias() == null || convenio.getCalculoDias() < 1) {
			convenio.setCalculoDias(
					calcularDias(contrato.getVigencia().getFechaInicioVigenciaContrato(), convenio.getFechaFin()));
		}

		CatIva catIva = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
				CatalogosComunes.IVA.getIdCatalogo(), convenio.getIdIva(), new CatIva());
		BigDecimal iva;
		try {
			iva = new BigDecimal(catIva.getPorcentaje()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
		} catch (NumberFormatException e) {
			iva = BigDecimal.ZERO;
		}

		// Calculo de montos
		
		BigDecimal incremento = convenio.getIncremento();
		BigDecimal ieps = convenio.getIeps();
		BigDecimal subtotal = convenio.getSubtotal() != null ? convenio.getSubtotal() : BigDecimal.ZERO;


		BigDecimal impuestos = incremento.add(ieps).add(subtotal).multiply(iva);

		convenio.setEstatus(true);
		convenio.setImpuesto(impuestos);

		boolean esCreacion = convenio.getIdConvenioModificatorio() == null;
		
		convenio.setUltimaModificacion(ultimaModificacion());

		convenioRepository.save(convenio);
		contrato.setUltimoCm(convenio.getNumeroConvenio());
		contratoRepository.save(contrato);

		String movimiento = Constantes.getAtributosConvenioModificatorio()[0] + convenio.getIdContrato() + " | "
				+ Constantes.getAtributosConvenioModificatorio()[1] + convenio.getIdConvenioModificatorio();

		pistaService.guardarPista(ModuloPista.CONVENIO_MODIFICATORIO.getId(),
				esCreacion ? TipoMovPista.INSERTA_REGISTRO.getId() : TipoMovPista.ACTUALIZA_REGISTRO.getId(),
				TipoSeccionPista.REGISTRO_CONVENIO_MODIFICATORIO.getIdSeccionPista(), movimiento + convenio.toString(),
				Optional.empty());

		return convenio;
	}
	
	private void agregarFechas(ConvenioModificatorioModel convenio, LocalDateTime fechaFinC, LocalDateTime fechaFinS) {
		if (convenio.getFechaFinServicio() == null) {
			convenio.setFechaFinServicio(fechaFinS);
		}
		if (convenio.getFechaFin() == null) {
			convenio.setFechaFin(fechaFinC);
		}
	}

	private Long calcularDias(LocalDateTime inicio, LocalDateTime fin) {
		long dias = ChronoUnit.DAYS.between(inicio.toLocalDate(), fin.toLocalDate());
		return dias + 1;
	}

	@Override
	public ConvenioModificatorioModel modificarConvenio(ConvenioModificatorioModel convenio) {
		convenio.setUltimaModificacion(ultimaModificacion());
		return crearConvenio(convenio, convenio.getIdContrato());
	}

	private Usuario obtenerUsuario() {
		return session.retornarUsuario().orElseThrow(() -> new ContratoException(ErroresEnum.USUARIO_NO_ENCONTRADO));
	}

	private String horaActual() {
		ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("America/Mexico_City"));
		return zonedDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}

	private String ultimaModificacion() {
		Usuario usuario = obtenerUsuario();
		return usuario.getNombre() + " " + horaActual();
	}

	private void validarConvenio(ConvenioModificatorioModel convenio) {
		Set<ConstraintViolation<ConvenioModificatorioModel>> violations = new HashSet<>();
		if (convenio.getTipoConvenio().contains(Constantes.getTipoConvenio()[1])) {
			violations.addAll(validator.validate(convenio, ValidacionMonto.class));
		}
		if (convenio.getTipoConvenio().contains(Constantes.getTipoConvenio()[2])) {
			violations.addAll(validator.validate(convenio, ValidacionTiempo.class));
		}
		violations.addAll(validator.validate(convenio, ValidacionCompleta.class));

		if (!violations.isEmpty()) {
			throw new ValidationException(
					violations.stream().map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
							.collect(Collectors.joining("; ")));
		}

	}

	@Override
	public List<ReporteConvenioModificatorioDto> obtenerConvenioReporte(Long idContrato) {
		try {
			return convenioRepository.findByIdContratoAndEstatusTrue(idContrato).stream().map(convenio -> {
				ReporteConvenioModificatorioDto dto = new ReporteConvenioModificatorioDto();
				dto.setFechaFin(convenio.getFechaFin());
				dto.setFechaFirma(convenio.getFechaFirma());
				dto.setNumeroConvenio(convenio.getNumeroConvenio());
				dto.setMontoMaximo(convenio.getMontoMaximoSinImpuestos());
				dto.setTipo(String.join(",", convenio.getTipoConvenio()));
				return dto;
			}).toList();
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}
	}

	@Override
	public ServiciosConvenioDto obtenerServiciosDto(Long idConvenio) {
		ServiciosConvenioDto dto = new ServiciosConvenioDto();
		ConvenioModificatorioModel convenio = convenioRepository.findByIdConvenioModificatorioAndEstatusTrue(idConvenio)
				.orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));
		Set<ServicioConvenioModel> set = new HashSet<>(obtenerServicioConvenio(idConvenio));
		dto.setServicios(set);
		dto.setTipoConvenio(convenio.getTipoConvenio());
		return dto;
	}

	@Override
	public List<ServicioConvenioModel> obtenerServicioConvenio(Long idConvenio) {
		ConvenioModificatorioModel convenio = convenioRepository.findByIdConvenioModificatorioAndEstatusTrue(idConvenio)
				.orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));
		ContratoModel contrato = convenio.getContratoModel();

		List<ServicioContratoModel> listaContrato = servicioContratoRepository
				.findByIdContratoAndEstatusTrue(convenio.getIdContrato());
		List<ServicioConvenioModel> listaEstimados = servicioConvenioRepository
				.findByIdConvenioModificatorioAndServicioBaseEstatusTrue(idConvenio);

		List<ServicioConvenioModel> listaConvenioAnterior = new ArrayList<>();

		 Optional<Long> convenioReciente= convenioRepository.findUltimoIdConvenioByIdContratoAnterior(contrato.getIdContrato(), idConvenio);
		boolean ultimoCM = convenioReciente.isPresent();
		

		if (ultimoCM) {
			listaConvenioAnterior = servicioConvenioRepository.findByIdConvenioModificatorioAndServicioBaseEstatusTrue(
	     	convenioReciente.get());
			
		}
		
		procesarNuevosServicios(listaContrato, listaEstimados, listaConvenioAnterior, idConvenio, ultimoCM);

		if (ultimoCM) {
			aplicarDatosPenultimoConvenio(listaEstimados, listaConvenioAnterior);
		}
		listaEstimados.forEach(servicio -> {
			recalcularTotales(servicio);
			servicioConvenioRepository.save(servicio);
		});
		List<ServicioConvenioModel> servicios = servicioConvenioRepository
				.findByIdConvenioModificatorioAndServicioBaseEstatusTrue(idConvenio);
		servicios.sort(Comparator.comparing(servicio -> servicio.getServicioBase().getIdServicioContrato()));

		registrarAuditoria(servicios, contrato, convenio);

		return servicios;
	}

	private void procesarNuevosServicios(List<ServicioContratoModel> listaContrato,
			List<ServicioConvenioModel> listaEstimados, List<ServicioConvenioModel> listaConvenioAnterior,
			Long idConvenio, boolean ultimoCM) {
		for (ServicioContratoModel servicioContrato : listaContrato) {
			if (listaEstimados.stream().noneMatch(servicio -> servicio.getServicioBase().equals(servicioContrato))) {
				ServicioConvenioModel servicioNuevo = crearServicioConvenio(servicioContrato, listaConvenioAnterior,
						idConvenio, ultimoCM);
				recalcularTotales(servicioNuevo);
				listaEstimados.add(servicioNuevo);
			}
		}
	}

	private ServicioConvenioModel crearServicioConvenio(ServicioContratoModel servicioContrato,
			List<ServicioConvenioModel> listaConvenioAnterior, Long idConvenio, boolean ultimoCM) {
		ServicioConvenioModel servicioNuevo = new ServicioConvenioModel();
		servicioNuevo.setServicioBase(servicioContrato);
		servicioNuevo.setIdConvenioModificatorio(idConvenio);
		servicioNuevo.setPrimeraVez(true);

		if (ultimoCM && !listaConvenioAnterior.isEmpty()) {
			ServicioConvenioModel servAnteriorEncontrado = listaConvenioAnterior.stream()
				    .filter(servAnterior -> 
				        Objects.equals(servAnterior.getServicioBase().getIdServicioContrato(), 
				                       servicioContrato.getIdServicioContrato()))
				    .findFirst()
				    .orElse(null);

				copiarDatosServicio(servicioNuevo, servAnteriorEncontrado, servicioContrato);
			
		} else {
			servicioNuevo.setPrecioUnitario(servicioContrato.getPrecioUnitario());
			servicioNuevo.setNumeroMaximoServicios(servicioContrato.getCantidadMaxima());
			servicioNuevo.setMontoMaximo(servicioContrato.getMontoMaximo());
			servicioNuevo.setIeps(servicioContrato.getIeps());
			servicioNuevo.setCompensacionMonto(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
			servicioNuevo.setIncrementoMonto(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
		}

		
		return servicioNuevo;
	}


	@SuppressWarnings("null")
	private void copiarDatosServicio(ServicioConvenioModel destino, ServicioConvenioModel fuente, ServicioContratoModel servicioContrato) {
	    boolean tieneFuente = fuente != null;

	    BigDecimal montoMaximoTotal = tieneFuente ? fuente.getMontoMaximoTotal() : null;
	    boolean sinMontoMaximo = montoMaximoTotal == null || BigDecimal.ZERO.compareTo(montoMaximoTotal) == 0;

	    if (tieneFuente && !sinMontoMaximo) {
	        destino.setMontoMaximo(fuente.getMontoMaximoTotal());
	        destino.setNumeroMaximoServicios(fuente.getNumeroTotalServicios());
	        
	        if (Boolean.TRUE.equals(destino.getPrimeraVez())) {
		    	destino.setPrecioUnitario(fuente.getPrecioUnitario());
		    }
	    } else {
	        destino.setMontoMaximo(servicioContrato.getMontoMaximo());
	        destino.setNumeroMaximoServicios(servicioContrato.getCantidadMaxima());
	        destino.setPrecioUnitario(servicioContrato.getPrecioUnitario());
	    }
	    

	    destino.setIeps(servicioContrato.getIeps());
	    destino.setCompensacionMonto(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
	    destino.setIncrementoMonto(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
	}

	private void recalcularTotales(ServicioConvenioModel serv) {

		BigDecimal incrementoServicios = Optional.ofNullable(serv.getIncrementoServicios()).orElse(BigDecimal.ZERO);
		BigDecimal numeroMaximoServicios = Optional.ofNullable(serv.getNumeroMaximoServicios()).orElse(BigDecimal.ZERO);
		BigDecimal compensacionServicios = Optional.ofNullable(serv.getCompensacionServicios()).orElse(BigDecimal.ZERO);

		BigDecimal incrementoMonto = Optional.ofNullable(serv.getIncrementoMonto()).orElse(BigDecimal.ZERO);
		BigDecimal compensacionMonto = Optional.ofNullable(serv.getCompensacionMonto()).orElse(BigDecimal.ZERO);
		BigDecimal montoMaximo = Optional.ofNullable(serv.getMontoMaximo()).orElse(BigDecimal.ZERO);

		if (serv.getTipoConsumo().equals(VOLUMETRIA)) {

			incrementoMonto = serv.getPrecioUnitario().multiply(incrementoServicios).setScale(2, RoundingMode.DOWN)
					.stripTrailingZeros();

			compensacionMonto = serv.getPrecioUnitario().multiply(compensacionServicios).setScale(2, RoundingMode.DOWN)
					.stripTrailingZeros();

		} else {
			BigDecimal precioUnitario = serv.getPrecioUnitario();

			if (precioUnitario != null && precioUnitario.compareTo(BigDecimal.ZERO) != 0) {
				incrementoServicios = incrementoMonto.divide(precioUnitario, 6, RoundingMode.DOWN).stripTrailingZeros();

				compensacionServicios = compensacionMonto.divide(precioUnitario, 6, RoundingMode.DOWN)
						.stripTrailingZeros();
			} else {
				incrementoServicios = BigDecimal.ZERO;
				compensacionServicios = BigDecimal.ZERO;
			}
		}

		serv.setCompensacionMonto(compensacionMonto);
		serv.setCompensacionServicios(compensacionServicios);
		serv.setIncrementoMonto(incrementoMonto);
		serv.setIncrementoServicios(incrementoServicios);
		serv.setNumeroTotalServicios(incrementoServicios.add(numeroMaximoServicios).add(compensacionServicios));

		if (serv.getServicioBase().getGrupoServiciosModel().getCatTipoConsumo().getNombre().equals(VOLUMETRIA)) {
			serv.setMontoMaximoTotal(
					serv.getNumeroTotalServicios().multiply(serv.getPrecioUnitario()).setScale(2, RoundingMode.DOWN));

		}

		serv.setMontoMaximoTotal(incrementoMonto.add(compensacionMonto).add(montoMaximo));
	}

	// FALSO POSITIVO DE FORTIFY CON LA VARIABLE SERV
	private void aplicarDatosPenultimoConvenio(List<ServicioConvenioModel> listaEstimados,
			List<ServicioConvenioModel> listaConvenioAnterior) {
		for (ServicioConvenioModel serv : listaEstimados) {
			listaConvenioAnterior.stream()
					.filter(servConvenioAnterior -> servConvenioAnterior.getServicioBase().getIdServicioContrato()
							.equals(serv.getServicioBase().getIdServicioContrato()))
					.findFirst().ifPresent(servConvenioAnterior -> {
							copiarDatosServicio(serv, servConvenioAnterior, servConvenioAnterior.getServicioBase());
					     	recalcularTotales(serv);
					});
		}
	}

	private void registrarAuditoria(List<ServicioConvenioModel> servicios, ContratoModel contrato,
			ConvenioModificatorioModel convenio) {
		StringBuilder resultado = new StringBuilder("|id registro servicio: ");
		servicios.forEach(srv -> resultado.append("|").append(srv.getIdServicioConvenio()));

		String resultadoFinal = resultado.toString();

		pistaService.guardarPista(ModuloPista.CONVENIO_MODIFICATORIO.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),
				TipoSeccionPista.REGISTRO_SERVICIOS_CONVENIO_MODIFICATORIO.getIdSeccionPista(),
				Constantes.getAtributosConvenioModificatorio()[0] + contrato.getIdContrato()
						+ Constantes.getAtributosConvenioModificatorio()[1] + convenio.getIdConvenioModificatorio()
						+ resultadoFinal,
				Optional.of(convenio));
	}


	@Override
	public ServicioConvenioModel calcularServicio(ServicioConvenioModel convenio) {
		actualizarDatosServicio(convenio);
		return convenio;
	}

	@Override
	public ConvenioModificatorioModel obtenerConvenioId(Long idConvenio) {
		return convenioRepository.findByIdConvenioModificatorioAndEstatusTrue(idConvenio)
				.orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));
	}

	@Override
	public ConvenioDto datosIniciales(Long idContrato) {
		ContratoModel contrato = contratoRepository.findByIdContrato(idContrato)
				.orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));
		ConvenioDto convenioDto = new ConvenioDto();
		
		Optional<String> vigenciaOptional = vigenciaMontosRepository
				.findPorcentajeIepsByIdContratoAndEstatusTrue(contrato.getIdContrato());
		
		
		BigDecimal ieps = servicioContratoRepository.calcularMontoIeps(obtenerIeps(vigenciaOptional) , idContrato);
		ieps = Optional.ofNullable(ieps)
	              .map(BigDecimal::stripTrailingZeros)
	              .orElse(BigDecimal.ZERO);
		
		BigDecimal montoSinImpuestos;
		BigDecimal montoConImpuestos;
		BigDecimal montoPesos;
		
		convenioDto.setIeps(ieps);

		convenioDto.setFechaFinVigenciaContrato(contrato.getVigencia().getFechaFinVigenciaContrato());
		convenioDto.setFechaFinVigenciaServicios(contrato.getVigencia().getFechaFinVigenciaServicios());

		CatIva catIva = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
				CatalogosComunes.IVA.getIdCatalogo(), contrato.getVigencia().getId_iva(), new CatIva());

		if (contrato.getDatosGenerales() != null) {
			convenioDto.setNumeroContrato(contrato.getDatosGenerales().getNumeroContrato());
		}
		if (contrato.getUltimoConvenioModificatorio() != null) {
			convenioDto.setSubtotal(contrato.getUltimoConvenioModificatorio().getMontoMaximoSinImpuestos());
			montoSinImpuestos = contrato.getUltimoConvenioModificatorio().getMontoMaximoSinImpuestos();
			convenioDto.setNumeroUltimoConvenio(contrato.getUltimoConvenioModificatorio().getNumeroConvenio());
			if (montoSinImpuestos == null) {
				montoSinImpuestos = contrato.getVigencia().getMontoMaximoSinImpuestos();
			}
			if (contrato.getUltimoConvenioModificatorio().getIeps() != null) {
				convenioDto.setIeps(contrato.getUltimoConvenioModificatorio().getIeps());
			}

			catIva = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(CatalogosComunes.IVA.getIdCatalogo(),
					contrato.getUltimoConvenioModificatorio().getIdIva(), new CatIva());

			convenioDto.setFechaFinVigenciaContrato(contrato.getUltimoConvenioModificatorio().getFechaFin());
			convenioDto.setFechaFinVigenciaServicios(contrato.getUltimoConvenioModificatorio().getFechaFinServicio());
			convenioDto.setIncremento(contrato.getUltimoConvenioModificatorio().getIncremento());
			montoConImpuestos = contrato.getUltimoConvenioModificatorio().getMontoMaximoConImpuestos();
			montoPesos = contrato.getUltimoConvenioModificatorio().getMontoPesos();
			} else { 
			convenioDto.setSubtotal(contrato.getSubtotal());
			montoSinImpuestos = contrato.getVigencia().getMontoMaximoSinImpuestos();
			montoConImpuestos = contrato.getVigencia().getMontoMaximoConImpuestos();
			montoPesos = contrato.getVigencia().getMontoPesosConImpuestos();
		}

		if (contrato.getVigencia().getId_iva() != null) {

			CatTipoMoneda catMoneda = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
					CatalogosComunes.TIPO_MONEDA.getIdCatalogo(), contrato.getVigencia().getIdTipoMoneda(),
					new CatTipoMoneda());

			BigDecimal iva = BigDecimal.ZERO;
			try {
				iva = new BigDecimal(catIva.getPorcentaje()).divide(BigDecimal.valueOf(100));
				convenioDto.setImpuestos(iva.multiply(contrato.getSubtotal()));
			} catch (NumberFormatException e) {
				iva = BigDecimal.ZERO;
				convenioDto.setImpuestos(BigDecimal.ZERO);
			}
			convenioDto.setCatIva(catIva);
			convenioDto.setIvaCantidad(iva);
			convenioDto.setCatTipoMoneda(catMoneda);
		}

		convenioDto.setMontoMaximoSinImpuestos(montoSinImpuestos);
		convenioDto.setMontoMaximoConImpuestos(montoConImpuestos);
		convenioDto.setMontoPesos(montoPesos);

		return convenioDto;
	}
	
	private BigDecimal obtenerIeps(Optional<String> porcentaje) {
		if (porcentaje.isPresent()) {
			try {
				return new BigDecimal(porcentaje.get())
						.divide(BigDecimal.valueOf(100));
			} catch (NumberFormatException | ArithmeticException | NullPointerException e) {
				return BigDecimal.ZERO;
			}
		}
		return BigDecimal.ZERO;
	}

	@Override
	@Transactional
	public ServiciosConvenioDto guardarServicios(Set<ServicioConvenioModel> lista) {
		if (lista != null && !lista.isEmpty()) {
			Long idConvenio = new ArrayList<>(lista).get(0).getIdConvenioModificatorio();

			lista.forEach(serv -> serv.setPrimeraVez(false));

			guardarServiciosComplemento(lista);

			servicioConvenioRepository.saveAll(lista);

			ServiciosConvenioDto dto = new ServiciosConvenioDto();
			ConvenioModificatorioModel convenio = convenioRepository
					.findByIdConvenioModificatorioAndEstatusTrue(idConvenio)
					.orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));

			convenio.setUltimaModificacion(ultimaModificacion());
			convenioRepository.save(convenio);

			List<ServicioConvenioModel> listaTemporal = new ArrayList<>(lista);
			listaTemporal.sort(Comparator.comparing(servicio -> servicio.getServicioBase().getIdServicioContrato()));

			Set<ServicioConvenioModel> listaOrdenada = new LinkedHashSet<>(listaTemporal);

			for (ServicioConvenioModel sv : listaOrdenada) {
				pistaService.guardarPista(ModuloPista.CONVENIO_MODIFICATORIO.getId(),
						TipoMovPista.ACTUALIZA_REGISTRO.getId(),
						TipoSeccionPista.REGISTRO_SERVICIOS_CONVENIO_MODIFICATORIO.getIdSeccionPista(),
						Constantes.getAtributosRegistroConvenioModificatorio()[0] + convenio.getIdContrato() + "|"
								+ Constantes.getAtributosRegistroConvenioModificatorio()[1] + idConvenio + "|"
								+ Constantes.getAtributosRegistroConvenioModificatorio()[2]
								+ sv.getIdServicioConvenio(),
						Optional.of(sv));
			}

			dto.setServicios(listaOrdenada);
			dto.setTipoConvenio(convenio.getTipoConvenio());

			servicioConvenioRepository.actualizarPrimeraVez();
			return dto;
		} else {
			servicioConvenioRepository.actualizarPrimeraVez();
			return null;
		}
	}
	
	private void guardarServiciosComplemento(Set<ServicioConvenioModel> lista) {
		for (ServicioConvenioModel servicio : lista) {
			if (BOLSA.equals(servicio.getTipoConsumo())) {
				
					BigDecimal montoMaximoTotal = Optional.ofNullable(servicio.getMontoMaximo())
							.orElse(BigDecimal.ZERO)
							.add(Optional.ofNullable(servicio.getCompensacionMonto()).orElse(BigDecimal.ZERO))
							.add(Optional.ofNullable(servicio.getIncrementoMonto()).orElse(BigDecimal.ZERO));

					servicio.setMontoMaximoTotal(montoMaximoTotal);

					BigDecimal precioUnitario = Optional.ofNullable(servicio.getPrecioUnitario()).orElse(BigDecimal.ZERO);

					BigDecimal compensacionServicio = BigDecimal.ZERO;
					if (precioUnitario.compareTo(BigDecimal.ZERO) > 0) {
					    compensacionServicio = Optional.ofNullable(servicio.getCompensacionMonto())
					        .orElse(BigDecimal.ZERO)
					        .divide(precioUnitario, 6, RoundingMode.HALF_UP);
					}

					BigDecimal incrementoServicio = BigDecimal.ZERO;
					if (precioUnitario.compareTo(BigDecimal.ZERO) > 0) {
					    incrementoServicio = Optional.ofNullable(servicio.getIncrementoMonto())
					        .orElse(BigDecimal.ZERO)
					        .divide(precioUnitario, 6, RoundingMode.HALF_UP);
					}


					servicio.setCompensacionServicios(compensacionServicio);
					servicio.setIncrementoServicios(incrementoServicio);
					
					servicio.setNumeroTotalServicios(servicio.getNumeroMaximoServicios().add(compensacionServicio.add(incrementoServicio)));

			}
		}
	}

	@Override
	public String validar(List<ServicioConvenioModel> lista) {
		List<ServicioConvenioModel> listaVolumetria = lista.stream().filter(obj -> obj.getServicioBase()
				.getGrupoServiciosModel().getCatTipoConsumo().getNombre().equals(VOLUMETRIA))
				.toList();
		BigDecimal montoTotal = BigDecimal.ZERO;

		List<ServicioConvenioModel> listaBolsa = lista.stream().filter(
				obj -> obj.getServicioBase().getGrupoServiciosModel().getCatTipoConsumo().getNombre().equals(BOLSA))
				.toList();

		Map<Long, List<ServicioConvenioModel>> grupos = listaBolsa.stream()
				.collect(Collectors.groupingBy(obj -> obj.getServicioBase().getIdGrupoServicio()));

		for (List<ServicioConvenioModel> grupo : grupos.values()) {
			ServicioConvenioModel primerElementoDelGrupo = grupo.get(0);
			montoTotal = montoTotal.add(primerElementoDelGrupo.getMontoMaximoTotal());
		}

		for (ServicioConvenioModel servicioVol : listaVolumetria) {
			montoTotal = montoTotal.add(servicioVol.getMontoMaximoTotal());
		}

		ConvenioModificatorioModel convenio = obtenerConvenioId(lista.get(0).getIdConvenioModificatorio());
		BigDecimal montoConvenioMaximo = convenio.getMontoMaximoSinImpuestos();
		return montoTotal.compareTo(montoConvenioMaximo) > 0 ? ErroresEnum.ERROR_MONTO_MAXIMO.getMessage()
				: ErroresEnum.COINCIDEN_MONTO_MAXIMO.getMessage();
	}

	@Override
	public String reporteServicios(Long idConvenio) {
		List<ServicioConvenioModel> lista = obtenerServicioConvenio(idConvenio);
		try {
			consumer.inializar("Servicios de convenio");
			consumer.agregarCabeceras(Constantes.TITULOS_REPORTE_SERVICIOS_CONVENIO);
			lista.forEach(consumer);
			byte[] reporte = consumer.cerrarBytes();

			ConvenioModificatorioModel convenio = lista.get(0).getConvenio();
			ContratoModel contrato = convenio.getContratoModel();
			StringBuilder resultado = new StringBuilder("|id registro servicio: ");

			for (ServicioConvenioModel srv : lista) {
				resultado.append("|").append(srv.getIdServicioConvenio());
			}

			String resultadoFinal = resultado.toString();

			pistaService.guardarPista(ModuloPista.CONVENIO_MODIFICATORIO.getId(),
					TipoMovPista.CONSULTA_REGISTRO.getId(),
					TipoSeccionPista.REGISTRO_SERVICIOS_CONVENIO_MODIFICATORIO.getIdSeccionPista(),
					Constantes.getAtributosConvenioModificatorio()[0] + contrato.getIdContrato()
							+ Constantes.getAtributosConvenioModificatorio()[1] + convenio.getIdConvenioModificatorio()
							+ resultadoFinal,
					Optional.of(convenio));

			return Base64.getEncoder().encodeToString(reporte);
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}
	}

	private void actualizarDatosServicio(ServicioConvenioModel servicioConvenio) {
		String tipoConsumo = Optional.ofNullable(servicioConvenio.getServicioBase())
				.map(servicio -> servicio.getGrupoServiciosModel().getCatTipoConsumo().getNombre()).orElse("");

		BigDecimal compensacionServicios = Optional.ofNullable(servicioConvenio.getCompensacionServicios())
				.orElse(BigDecimal.ZERO);

		if (VOLUMETRIA.equals(tipoConsumo)) {
			servicioConvenio.setCompensacionServicios(compensacionServicios);
			servicioConvenio.setCompensacionMonto(compensacionServicios
					.multiply(Optional.ofNullable(servicioConvenio.getPrecioUnitario()).orElse(BigDecimal.ZERO)));
		} else if (BOLSA.equals(tipoConsumo) && servicioConvenio.getCompensacionMonto() != null) {
			BigDecimal precioUnitario = Optional.ofNullable(servicioConvenio.getPrecioUnitario())
					.orElse(BigDecimal.ONE); // Evitar división por cero
			BigDecimal compensacionMonto = servicioConvenio.getCompensacionMonto().divide(precioUnitario, 2,
					RoundingMode.HALF_UP);
			servicioConvenio.setCompensacionServicios(compensacionMonto);
		}

		BigDecimal incrementoServicios = Optional.ofNullable(servicioConvenio.getIncrementoServicios())
				.orElse(BigDecimal.ZERO);

		if (VOLUMETRIA.equals(tipoConsumo)) {
			servicioConvenio.setIncrementoServicios(incrementoServicios);
			servicioConvenio.setIncrementoMonto(incrementoServicios
					.multiply(Optional.ofNullable(servicioConvenio.getPrecioUnitario()).orElse(BigDecimal.ZERO)));
		} else if (BOLSA.equals(tipoConsumo)) {
			servicioConvenio.setIncrementoMonto(
					Optional.ofNullable(servicioConvenio.getIncrementoMonto()).orElse(BigDecimal.ZERO));
		}

		BigDecimal montoMaximo = Optional.ofNullable(servicioConvenio.getMontoMaximo()).orElse(BigDecimal.ZERO);
		BigDecimal compensacionMonto = Optional.ofNullable(servicioConvenio.getCompensacionMonto())
				.orElse(BigDecimal.ZERO);
		BigDecimal incrementoMonto = Optional.ofNullable(servicioConvenio.getIncrementoMonto()).orElse(BigDecimal.ZERO);
		BigDecimal montoMaximoTotal = montoMaximo.add(compensacionMonto).add(incrementoMonto);
		servicioConvenio.setMontoMaximoTotal(montoMaximoTotal);

		BigDecimal numeroMaximoServicios = Optional.ofNullable(servicioConvenio.getNumeroMaximoServicios())
				.orElse(BigDecimal.ZERO);
		BigDecimal numeroTotalServicios = numeroMaximoServicios.add(compensacionServicios).add(incrementoServicios);
		servicioConvenio.setNumeroTotalServicios(numeroTotalServicios);
	}


	@Override
	public String obtenerUltimaMod(Long idConvenio) {
		ConvenioModificatorioModel conv = convenioRepository.findByIdConvenioModificatorioAndEstatusTrue(idConvenio)
				.orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));

		return conv.getUltimaModificacion();
	}

}
