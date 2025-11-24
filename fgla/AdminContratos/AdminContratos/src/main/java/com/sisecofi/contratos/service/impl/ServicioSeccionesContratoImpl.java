package com.sisecofi.contratos.service.impl;

import com.sisecofi.contratos.dto.*;
import com.sisecofi.contratos.microservicios.*;
import com.sisecofi.contratos.repository.contrato.*;
import com.sisecofi.contratos.service.ContratoService;
import com.sisecofi.contratos.service.PistaService;
import com.sisecofi.contratos.service.ServicioInformesDocumentalesUnicaVez;
import com.sisecofi.contratos.service.ServicioSeccionesContrato;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.contratos.util.consumer.ContratosPistasConsumer;
import com.sisecofi.contratos.util.enums.ErroresEnum;
import com.sisecofi.contratos.util.exception.ContratoException;
import com.sisecofi.contratos.util.validator.ContratoValidator;
import com.sisecofi.libreria.comunes.dto.contrato.VigenciaMontoDto;
import com.sisecofi.libreria.comunes.model.catalogo.*;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.contratos.GrupoServiciosModel;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesUnicaVezModel;
import com.sisecofi.libreria.comunes.model.contratos.ServicioContratoModel;
import com.sisecofi.libreria.comunes.model.contratos.VigenciaMontosModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.libreria.comunes.util.sesion.Session;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class ServicioSeccionesContratoImpl implements ServicioSeccionesContrato {
	private static final Logger log = LoggerFactory.getLogger(ServicioSeccionesContratoImpl.class);
	private final PistaService pistaService;
	private final ContratoRepository contratoRepository;
	private final ContratoValidator contratoValidator;
	private final CatalogoMicroservicio catalogoMicroservicio;
	private final VigenciaMontosRepository vigenciaMontosRepository;
	private final ServicioContratoRepository servicioContratoRepository;
	private final GrupoServicioContratoRepository grupoServicioContratoRepository;
	private final Session session;
	private final ServicioInformesDocumentalesUnicaVez servicioInformesDoc;
	private final ContratosPistasConsumer contratosPistasConsumer;
	private final ContratoService contratoService;
	private static final String VOLUMETRIA = "Volumetría";
	private static final String BOLSA = "Bolsa";

	@Override
	public List<VigenciaMontoDto> obtenerVigenciaMontos() {
		try {
			List<VigenciaMontosModel> vigenciaMontosModelList = vigenciaMontosRepository.findByEstatusTrue();



			// pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


			// TipoSeccionPista.CONTRATOS_VIGENCIA_MONTOS.getIdSeccionPista(), Constantes.CONSULTA_VIGENCIA_MONTOS,


			// Optional.empty());

			return vigenciaMontosModelList.stream().map(vigenciaMontosModel -> {
				CatIva iva = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
						CatalogosComunes.IVA.getIdCatalogo(), vigenciaMontosModel.getId_iva(), new CatIva());

				CatIeps ieps = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
						CatalogosComunes.IEPS.getIdCatalogo(), vigenciaMontosModel.getIdIeps(), new CatIeps());

				VigenciaMontoDto dto = new VigenciaMontoDto();
				dto.setVigenciaMontoConteo(vigenciaMontosModelList.indexOf(vigenciaMontosModel) + 1L);
				dto.setVigenciaMontosModel(vigenciaMontosModel);
				dto.setIva(iva);
				dto.setIeps(ieps);

				return dto;
			}).toList();
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public VigenciaMontoDto obtenerVigenciaMonto(Long idContrato) {
		VigenciaMontosModel vigenciaMontosModel = vigenciaMontosRepository.findByIdContratoAndEstatusTrue(idContrato)
				.orElseThrow(() -> new ContratoException(ErroresEnum.ERROR_VIGENCIA_MONTOS));

		try {
			Integer idIva = vigenciaMontosModel.getId_iva();
			Integer idIeps = vigenciaMontosModel.getIdIeps();

			VigenciaMontoDto vigenciaMontoDto = new VigenciaMontoDto();
			vigenciaMontoDto.setVigenciaMontosModel(vigenciaMontosModel);
			if (idIva != null) {
				CatIva iva = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
						CatalogosComunes.IVA.getIdCatalogo(), vigenciaMontosModel.getId_iva(), new CatIva());
				vigenciaMontoDto.setIva(iva);
			} else if (idIeps != null) {
				CatIeps ieps = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
						CatalogosComunes.IEPS.getIdCatalogo(), vigenciaMontosModel.getIdIeps(), new CatIeps());
				vigenciaMontoDto.setIeps(ieps);
			}

			return vigenciaMontoDto;
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public VigenciaMontosModel vigenciaMontos(VigenciaMontosModel vigenciaMontosModel) {
		Long idContrato = vigenciaMontosModel.getIdContrato();

		if (vigenciaMontosModel.getTipoCambioMaximo() != null) {
			BigDecimal montoMaximoSinI = vigenciaMontosModel.getMontoMaximoSinImpuestos();
			BigDecimal tipoCambioMax = vigenciaMontosModel.getTipoCambioMaximo();
			BigDecimal montoPesosSinI = montoMaximoSinI.multiply(tipoCambioMax);
			vigenciaMontosModel.setMontoPesosSinImpuestos(montoPesosSinI);

			BigDecimal montoMaximoConI = vigenciaMontosModel.getMontoMaximoConImpuestos();
			BigDecimal montoPesosConI = montoMaximoConI.multiply(tipoCambioMax);
			vigenciaMontosModel.setMontoPesosConImpuestos(montoPesosConI);
		} else {
			BigDecimal montoPesosSinI = vigenciaMontosModel.getMontoMaximoSinImpuestos();
			vigenciaMontosModel.setMontoPesosSinImpuestos(montoPesosSinI);
			vigenciaMontosModel.setMontoMinimoConImpuestos(montoPesosSinI);
		}

		ContratoModel contratoModel = contratoRepository.findByIdContrato(idContrato)
				.orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));
		contratoModel.setFechaUltimaModificacion(LocalDateTime.now());

		VigenciaMontosModel vigenciaMontos = vigenciaMontosRepository.findByIdContratoAndEstatusTrue(idContrato)
				.orElse(null);
		if (vigenciaMontos != null) {
			throw new ContratoException(ErroresEnum.CONTRATO_CON_VIGENCIA_MONTOS);
		}
		actualizarUltimaModificacionContrato(idContrato);

		try {
			vigenciaMontosModel.setEstatus(true);
			vigenciaMontosRepository.save(vigenciaMontosModel);

			String movimiento = contratosPistasConsumer.movimientoVigenciaMontos(vigenciaMontosModel);
			this.contratoService.actualizarUltimaMod(idContrato);

			// pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),

			// TipoSeccionPista.CONTRATOS_VIGENCIA_MONTOS.getIdSeccionPista(), movimiento, Optional.empty());

			return vigenciaMontosModel;

		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public String actualizarVigenciaMontos(VigenciaMontosModel vigenciaMontosModel) {
		try {
			vigenciaMontosModel.setEstatus(true);
			vigenciaMontosRepository.save(vigenciaMontosModel);

			String movimiento = contratosPistasConsumer.movimientoVigenciaMontos(vigenciaMontosModel);
			this.contratoService.actualizarUltimaMod(vigenciaMontosModel.getIdContrato());

			// pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),

			// TipoSeccionPista.CONTRATOS_VIGENCIA_MONTOS.getIdSeccionPista(), movimiento, Optional.empty());
			return "OK";
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public String eliminarVigenciaMonto(EliminarVigenciaMontosDto eliminarVigenciaMontosDto) {

		contratoValidator.eliminarRefistroValidacion(eliminarVigenciaMontosDto.getIdContrato());

		VigenciaMontosModel vigenciaMontosModel = vigenciaMontosRepository
				.findByIdVigenciaMontoAndEstatusTrue(eliminarVigenciaMontosDto.getIdVigenciaMonto())
				.orElseThrow(() -> new ContratoException(ErroresEnum.ERROR_VIGENCIA_MONTOS));

		try {
			vigenciaMontosModel.setEstatus(false);
			vigenciaMontosRepository.save(vigenciaMontosModel);

			String movimiento = contratosPistasConsumer.movimientoVigenciaMontos(vigenciaMontosModel);
			this.contratoService.actualizarUltimaMod(eliminarVigenciaMontosDto.getIdContrato());

			// pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.BORRA_REGISTRO.getId(),

			// TipoSeccionPista.CONTRATOS_VIGENCIA_MONTOS.getIdSeccionPista(), movimiento, Optional.empty());

			return "OK";
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_ELIMINAR);
		}
	}

	@Override
	public String eliminarGrupoiServicio(List<Long> idsGrupoServicio) {
		try {
			if (idsGrupoServicio == null || idsGrupoServicio.isEmpty()) {
				return "OK";
			}

			Set<Long> gruposConServicios = new HashSet<>(
					servicioContratoRepository.findIdGrupoServicioByIds(idsGrupoServicio));

			List<Long> gruposSinServicios = idsGrupoServicio.stream().filter(id -> !gruposConServicios.contains(id))
					.toList();

			if (!gruposSinServicios.isEmpty()) {
				grupoServicioContratoRepository.updateEstatusFalseByIds(gruposSinServicios);

				Long idContrato = grupoServicioContratoRepository.findIdContratoByGrupo(gruposSinServicios.get(0));

				actualizarUltimaModificacionContrato(idContrato);



				// pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.BORRA_REGISTRO.getId(),


				// TipoSeccionPista.CONTRATOS_GRUPOS.getIdSeccionPista(), "Id contrato: " + idContrato + "|",


				// Optional.empty());
			}

			if (!gruposConServicios.isEmpty()) {
				return "El registro no se puede eliminar porque se encuentra relacionado en otro módulo.";
			}

			return "OK";
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_ELIMINAR);
		}
	}

	@Override
	public String actualizarGrupoSetvicio(List<ActualizarGrupoServicioDto> grupoServicioDtoList) {

		validarDuplicidadModificacion(grupoServicioDtoList);
		try {
			for (ActualizarGrupoServicioDto grupoServicioDto : grupoServicioDtoList) {

				GrupoServiciosModel grupoServiciosModel = grupoServicioContratoRepository
						.findByIdGrupoServicioAndEstatusTrue(grupoServicioDto.getIdGrupoServicio());
				if (grupoServiciosModel == null) {
					throw new ContratoException(ErroresEnum.GRUPO_SERVICIO_NO_ENCONTRADO);
				}
				Long idContrato = grupoServiciosModel.getIdContrato();

				actualizarUltimaModificacionContrato(idContrato);

				grupoServiciosModel.setIdTipoConsumo(grupoServicioDto.getIdTipoConsumo());
				grupoServiciosModel.setGrupo(grupoServicioDto.getGrupo());
				grupoServiciosModel.setEstatus(true);
				grupoServicioContratoRepository.save(grupoServiciosModel);

				String movimiento = contratosPistasConsumer.movimientoGruposServicio(grupoServiciosModel);
				this.contratoService.actualizarUltimaMod(idContrato);

				// pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),

				// TipoSeccionPista.CONTRATOS_GRUPOS.getIdSeccionPista(), movimiento, Optional.empty());
			}
			return "OK";

		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_GUARDAR);
		}
	}

	private void validarDuplicidadModificacion(List<ActualizarGrupoServicioDto> grupos) {
		Set<String> nombresVistos = new HashSet<>();

		for (ActualizarGrupoServicioDto grupoModel : grupos) {
			String nombreGrupo = grupoModel.getGrupo();

			if (!nombresVistos.add(nombreGrupo)) {
				throw new ContratoException(ErroresEnum.ERROR_DUPLICIDAD_GRUPO);
			}

			Optional<GrupoServiciosModel> existente = grupoServicioContratoRepository.findByGrupoAndEstatusTrue(nombreGrupo);

			if (existente.isPresent()) {
				GrupoServiciosModel grupoEnBd = existente.get();

				if (grupoModel.getIdGrupoServicio() == null
						|| !grupoEnBd.getIdGrupoServicio().equals(grupoModel.getIdGrupoServicio())) {
					throw new ContratoException(ErroresEnum.ERROR_DUPLICIDAD_GRUPO);
				}
			}
		}
	}

	@Override
	public String guardarServicioContrato(List<ServicioContratoModel> servicioContratoModelList) {

		contratoValidator.validarMontosBolsa(servicioContratoModelList);

		Long idContratoUm = 0L;

		for (ServicioContratoModel servicioContratoModel : servicioContratoModelList) {
			Long idGrupo = servicioContratoModel.getIdGrupoServicio();

			GrupoServiciosModel grupoServiciosModel = grupoServicioContratoRepository
					.findByIdGrupoServicioAndEstatusTrue(idGrupo);
			Integer idTipoConsumo = grupoServiciosModel.getIdTipoConsumo();
			CatTipoConsumo catTipoConsumo = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
					CatalogosComunes.TIPO_CONSUMO.getIdCatalogo(), idTipoConsumo, new CatTipoConsumo());

			BigDecimal precioUnitario = servicioContratoModel.getPrecioUnitario();
			log.info("nombre volumetria {}", catTipoConsumo.getNombre());
			log.info("constante volumetria {}", Constantes.TIPO_VOLUMETRIA);
			log.info("nombre bolsa {}", catTipoConsumo.getNombre());
			log.info("constante volumetria {}", Constantes.TIPO_BOLSA);
			if (Objects.equals(catTipoConsumo.getNombre(), Constantes.TIPO_VOLUMETRIA)
					&& servicioContratoModel.getCantidadMinima() != null
					&& servicioContratoModel.getCantidadMaxima() != null) {
				BigDecimal montoMinimo = precioUnitario.multiply(servicioContratoModel.getCantidadMinima());
				BigDecimal montoMaximo = precioUnitario.multiply(servicioContratoModel.getCantidadMaxima());
				servicioContratoModel.setMontoMaximo(montoMaximo);
				servicioContratoModel.setMontoMinimo(montoMinimo);

				servicioContratoModel.setEstatus(true);
				servicioContratoRepository.save(servicioContratoModel);

			} else if (Objects.equals(catTipoConsumo.getNombre(), Constantes.TIPO_BOLSA)
					&& servicioContratoModel.getMontoMaximo() != null
					&& servicioContratoModel.getMontoMinimo() != null) {

				BigDecimal montoMaximo = servicioContratoModel.getMontoMaximo();

				BigDecimal montoMinimoIndividual = servicioContratoModel.getMontoMinimo();
				BigDecimal cantidadMinima = montoMinimoIndividual.divide(servicioContratoModel.getPrecioUnitario(), 6,
						RoundingMode.HALF_UP);
				BigDecimal cantidadMaxima = montoMaximo.divide(servicioContratoModel.getPrecioUnitario(), 6,
						RoundingMode.HALF_UP);

				servicioContratoModel.setCantidadMinima(cantidadMinima);
				servicioContratoModel.setCantidadMaxima(cantidadMaxima);
			}

			servicioContratoModel.setEstatus(true);
			servicioContratoRepository.saveAll(servicioContratoModelList);
			this.contratoService.actualizarUltimaMod(servicioContratoModel.getIdContrato());
			idContratoUm = servicioContratoModel.getIdContrato();
		}
		actualizarUltimaModificacionContrato(idContratoUm);

		try {



			// pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),


			// TipoSeccionPista.CONTRATOS_REGISTRO_SERVICIOS.getIdSeccionPista(),


			// "Id contrato: " + idContratoUm.toString() + "|", Optional.empty());

			return "OK";

		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_GUARDAR);
		}
	}

	@Override
	public List<ServicioContratoDto> obtenerServicioContrato(Long idContrato) {
		List<ServicioContratoModel> servicioContratoModels = servicioContratoRepository
				.obtenerServiciosOrdenados(idContrato);
		
		Integer primerId= 0;
		if (!servicioContratoModels.isEmpty()) {
			primerId = Integer.parseInt(""+servicioContratoRepository.obtenerPrimerIdServicioContrato(idContrato))-1;
		}

		List<ServicioContratoDto> servicioContratoDtos = new ArrayList<>();

		for (ServicioContratoModel servicioContrato : servicioContratoModels) {
			ServicioContratoDto servicioContratoDto = new ServicioContratoDto();

			Long idGrupoServicio = servicioContrato.getIdGrupoServicio();

			GrupoServiciosModel grupoServiciosModel = grupoServicioContratoRepository
					.findByIdGrupoServicioAndEstatusTrue(idGrupoServicio);
			Integer idTipoConsumo = grupoServiciosModel.getIdTipoConsumo();
			Integer idTipoUnidad = servicioContrato.getIdTipoUnidad();

			BaseCatalogoModel tipoConsumo = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
					CatalogosComunes.TIPO_CONSUMO.getIdCatalogo(), idTipoConsumo, new CatTipoConsumo());
			BaseCatalogoModel tipoUnidad = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
					CatalogosComunes.TIPO_UNIDAD.getIdCatalogo(), idTipoUnidad, new CatTipoUnidad());

			servicioContratoDto.setIdServicioContrato(servicioContrato.getIdServicioContrato());
			servicioContratoDto.setGrupo(grupoServiciosModel.getGrupo());
			servicioContratoDto.setIdGrupoServicio(grupoServiciosModel.getIdGrupoServicio());
			servicioContratoDto.setTipoConsumo(tipoConsumo.getNombre());
			servicioContratoDto.setClaveProductosServicios(servicioContrato.getClave());
			servicioContratoDto.setConseptosServicio(servicioContrato.getConcepto());
			servicioContratoDto.setTipoUnidad(tipoUnidad.getNombre());
			servicioContratoDto.setIdTipoUnidad(idTipoUnidad);
			servicioContratoDto.setPrecioUnitario(servicioContrato.getPrecioUnitario());
			servicioContratoDto.setCantidadServiciosMinima(servicioContrato.getCantidadMinima());
			servicioContratoDto.setCantidadServicios(servicioContrato.getCantidadMaxima());
			servicioContratoDto.setMontoMinimo(servicioContrato.getMontoMinimo());
			servicioContratoDto.setMontoMaximo(servicioContrato.getMontoMaximo());
			servicioContratoDto.setAplicaDns(servicioContrato.getIeps());
			servicioContratoDto.setEstatus(servicioContrato.getEstatus());
			if (servicioContrato.getIdServicioContrato().intValue() == servicioContrato.getOrden()) {
				servicioContratoDto.setOrden(servicioContrato.getIdServicioContrato().intValue() - primerId);
			}else {
				servicioContratoDto.setOrden(servicioContrato.getOrden());
			}
			

			servicioContratoDtos.add(servicioContratoDto);
		}

		return servicioContratoDtos;
	}

	@Override
	public String eliminarRegistroServicioContrato(List<Long> idServicioContrato) {

		try {
			Long idContrato = 0L;
			for (Long idServicio : idServicioContrato) {
				ServicioContratoModel servicioContratoModel = servicioContratoRepository
						.findByIdServicioContrato(idServicio)
						.orElseThrow(() -> new ContratoException(ErroresEnum.SERVICIO_CONTRATO_NO_ENCONTRADO));
				servicioContratoModel.setEstatus(false);
				servicioContratoRepository.save(servicioContratoModel);

				idContrato = servicioContratoModel.getIdContrato();
			}

			String movimiento = contratosPistasConsumer.movimientoGruposServicioIds(idContrato, idServicioContrato);
			this.contratoService.actualizarUltimaMod(idContrato);

			// pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.BORRA_REGISTRO.getId(),

			// TipoSeccionPista.CONTRATOS_REGISTRO_SERVICIOS.getIdSeccionPista(), movimiento, Optional.empty());

			return "OK";
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_ELIMINAR);
		}
	}

	@Override
	public String actualizarServicioContrato(List<ServicioContratoModel> servicioContratoModelList) {
		try {
			Long idContratoMovimiento = 0L;
			List<Long> idsServicioContrato = new ArrayList<>();

			for (ServicioContratoModel servicioContratoModel : servicioContratoModelList) {
				Long idGrupo = servicioContratoModel.getIdGrupoServicio();
				idContratoMovimiento = servicioContratoModel.getIdContrato();

				GrupoServiciosModel grupoServiciosModel = grupoServicioContratoRepository
						.findByIdGrupoServicioAndEstatusTrue(idGrupo);
				Integer idTipoConsumo = grupoServiciosModel.getIdTipoConsumo();
				CatTipoConsumo catTipoConsumo = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
						CatalogosComunes.TIPO_CONSUMO.getIdCatalogo(), idTipoConsumo, new CatTipoConsumo());

				BigDecimal precioUnitario = servicioContratoModel.getPrecioUnitario();

				if (Objects.equals(catTipoConsumo.getNombre(), VOLUMETRIA)
						&& servicioContratoModel.getCantidadMinima() != null
						&& servicioContratoModel.getCantidadMaxima() != null) {
					BigDecimal montoMinimo = precioUnitario.multiply(servicioContratoModel.getCantidadMinima());
					BigDecimal montoMaximo = precioUnitario.multiply(servicioContratoModel.getCantidadMaxima());
					servicioContratoModel.setMontoMaximo(montoMaximo);
					servicioContratoModel.setMontoMinimo(montoMinimo);

					servicioContratoModel.setEstatus(true);
					servicioContratoRepository.save(servicioContratoModel);
					idsServicioContrato.add(servicioContratoModel.getIdServicioContrato());

				} else if (Objects.equals(catTipoConsumo.getNombre(), BOLSA)
						&& servicioContratoModel.getMontoMaximo() != null
						&& servicioContratoModel.getMontoMinimo() != null) {

					BigDecimal montoMaximo = servicioContratoModel.getMontoMaximo();

					BigDecimal montoMinimoIndividual = servicioContratoModel.getMontoMinimo();
					BigDecimal cantidadMinima = montoMinimoIndividual.divide(servicioContratoModel.getPrecioUnitario(),
							6, RoundingMode.HALF_UP);
					BigDecimal cantidadMaxima = montoMaximo.divide(servicioContratoModel.getPrecioUnitario(), 6,
							RoundingMode.HALF_UP);

					servicioContratoModel.setCantidadMinima(cantidadMinima);
					servicioContratoModel.setCantidadMaxima(cantidadMaxima);
				} else {
					throw new ContratoException(ErroresEnum.ERROR_ESTATUS_CAT_TIPO_CONSUMO);
				}
				servicioContratoModel.setEstatus(true);
			}

			servicioContratoRepository.saveAll(servicioContratoModelList);

			String movimiento = contratosPistasConsumer.movimientoServicioContrato(idContratoMovimiento,
					idsServicioContrato);
			this.contratoService.actualizarUltimaMod(idContratoMovimiento);

			// pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),

			// TipoSeccionPista.CONTRATOS_REGISTRO_SERVICIOS.getIdSeccionPista(), movimiento, Optional.empty());

			return "OK";
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_GUARDAR);
		}
	}

	@Override
	public String validarServicioContrato(Long idContrato) {
		List<ServicioContratoModel> lista = servicioContratoRepository.findByIdContratoAndEstatusTrue(idContrato);

		VigenciaMontosModel vigenciaMontosModel = vigenciaMontosRepository.findByIdContratoAndEstatusTrue(idContrato)
				.orElseThrow(() -> new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR));

		BigDecimal montoMaximo = vigenciaMontosModel.getMontoMaximoSinImpuestos();

		List<ServicioContratoModel> listaVolumetria = lista.stream()
				.filter(obj -> obj.getGrupoServiciosModel().getCatTipoConsumo().getNombre().equals(VOLUMETRIA))
				.toList();

		BigDecimal montoTotal = BigDecimal.ZERO;

		List<ServicioContratoModel> listaBolsa = lista.stream()
				.filter(obj -> obj.getGrupoServiciosModel().getCatTipoConsumo().getNombre().equals(BOLSA))
				.toList();

		Map<Long, List<ServicioContratoModel>> grupos = listaBolsa.stream()
				.collect(Collectors.groupingBy(ServicioContratoModel::getIdGrupoServicio));

		for (List<ServicioContratoModel> grupo : grupos.values()) {
			ServicioContratoModel primerElementoDelGrupo = grupo.get(0);
			montoTotal = montoTotal.add(primerElementoDelGrupo.getMontoMaximo());
		}

		for (ServicioContratoModel servicioVol : listaVolumetria) {
			montoTotal = montoTotal.add(servicioVol.getMontoMaximo());
		}

		if (!montoTotal.equals(montoMaximo)) {
			return Constantes.SUMA_INVALIDA;
		}

		// pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),

		// TipoSeccionPista.CONTRATOS_REGISTRO_SERVICIOS.getIdSeccionPista(),

		// TipoMovPista.CONSULTA_REGISTRO.getClave(), Optional.empty());

		return Constantes.SUMA_VALIDA;
	}

	@Override
	public List<GrupoServicioDto> obtenerGrupoServicio(Long idContrato) {
		List<GrupoServiciosModel> grupoServiciosModel = grupoServicioContratoRepository
				.findByIdContratoAndEstatusTrue(idContrato);

		if (grupoServiciosModel.isEmpty()) {
			throw new ContratoException(ErroresEnum.ERROR_REGISTROS_NO_ENCONTRADO);
		}

		try {

			List<GrupoServicioDto> grupoServicioDtos = new ArrayList<>();
			for (GrupoServiciosModel grupoServicio : grupoServiciosModel) {
				GrupoServicioDto grupoServicioDto = new GrupoServicioDto();

				BaseCatalogoModel baseCatalogoModel = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
						CatalogosComunes.TIPO_CONSUMO.getIdCatalogo(), grupoServicio.getIdTipoConsumo(),
						new CatTipoConsumo());

				grupoServicioDto.setTipoConsumo(baseCatalogoModel);
				grupoServicioDto.setGrupoServiciosModel(grupoServicio);

				grupoServicioDtos.add(grupoServicioDto);
			}
			return grupoServicioDtos;

		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public String guardarGrupoServicio(List<GrupoServiciosModel> grupoServiciosModel) {
		validarDuplicidad(grupoServiciosModel);
		try {
			List<Long> idsGrupoServicio = new ArrayList<>();
			Long idContratoMov = 0L;
			
			for (GrupoServiciosModel grupoServicios : grupoServiciosModel) {
				grupoServicios.setEstatus(true);
				grupoServicioContratoRepository.save(grupoServicios);
				idsGrupoServicio.add(grupoServicios.getIdGrupoServicio());
				idContratoMov = grupoServicios.getIdContrato();
			}
			actualizarUltimaModificacionContrato(idContratoMov);
			String movimiento = contratosPistasConsumer.movimientoGruposServicioIds(idContratoMov, idsGrupoServicio);
			this.contratoService.actualizarUltimaMod(idContratoMov);

			// pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),

			// TipoSeccionPista.CONTRATOS_GRUPOS.getIdSeccionPista(), movimiento, Optional.empty());

			return "OK";
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_GUARDAR);
		}
	}

	private void validarDuplicidad(List<GrupoServiciosModel> grupos) {
		Set<String> nombresVistos = new HashSet<>();

		for (GrupoServiciosModel grupoModel : grupos) {
			String nombreGrupo = grupoModel.getGrupo();
			Long idContrato = grupoModel.getIdContrato();

			if (!nombresVistos.add(nombreGrupo)) {
				throw new ContratoException(ErroresEnum.ERROR_DUPLICIDAD_GRUPO);
			}

			Optional<GrupoServiciosModel> existente = grupoServicioContratoRepository.findByGrupoAndIdContratoAndEstatusTrue(nombreGrupo, idContrato);

			if (existente.isPresent()) {
				GrupoServiciosModel grupoEnBd = existente.get();

				if (grupoModel.getIdGrupoServicio() == null
						|| !grupoEnBd.getIdGrupoServicio().equals(grupoModel.getIdGrupoServicio())) {
					throw new ContratoException(ErroresEnum.ERROR_DUPLICIDAD_GRUPO);
				}
			}
		}
	}

	@Override
	public List<InformesDocumentalesUnicaVezModel> obtenerInformesDocumentalesUnicaVez(Long idContrato) {
		return servicioInformesDoc.obtenerInformesDocumentalesUnicaVez(idContrato);
	}

	private Usuario obtenerUsuario() {
		Optional<Usuario> usuario = session.retornarUsuario();
		if (usuario.isPresent()) {
			return usuario.get();
		} else {
			throw new ContratoException(ErroresEnum.ERROR_USUARIO_NO_ENCONTRADO);
		}
	}

	private void actualizarUltimaModificacionContrato(Long idContrato) {
		try {
			Usuario usuario = obtenerUsuario();
			contratoRepository.updateFechaUltimaModificacion(idContrato, LocalDateTime.now(), usuario.getNombre());
		} catch (Exception e) {
			log.info("error usuario ------------>");
			throw new ContratoException(ErroresEnum.ERROR_AL_ACTUALIZAR_MODIFICADOR_CONTRATO);
		}
	}
}
