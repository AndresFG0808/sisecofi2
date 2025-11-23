package com.sisecofi.admindevengados.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.sisecofi.admindevengados.dto.ObtenerPenaContractualDto;
import com.sisecofi.admindevengados.dto.ObtenerPenaContractualRequestDto;
import com.sisecofi.admindevengados.dto.ObtenerPenasDeduccionResponseDto;
import com.sisecofi.admindevengados.microservicio.ContratoMicoservicio;
import com.sisecofi.admindevengados.model.DeduccionesModel;
import com.sisecofi.admindevengados.repository.CatDesgloceRepository;
import com.sisecofi.admindevengados.repository.CatTipoDeduccionRepository;
import com.sisecofi.admindevengados.repository.DeduccionRepository;
import com.sisecofi.admindevengados.repository.DictamenRepository;
import com.sisecofi.admindevengados.repository.NivelesServiciosSLARepository;
import com.sisecofi.admindevengados.repository.ServicioContratoRepository;
import com.sisecofi.admindevengados.service.DeduccionesService;
import com.sisecofi.admindevengados.service.DictamenService;
import com.sisecofi.admindevengados.service.PistaService;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.admindevengados.util.enums.ErroresEnum;
import com.sisecofi.admindevengados.util.exception.CatalogoException;
import com.sisecofi.libreria.comunes.model.catalogo.CatDesgloce;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoDeduccion;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesPeriodicosModel;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesServiciosModel;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesUnicaVezModel;
import com.sisecofi.libreria.comunes.model.contratos.NivelesServicioSLAModel;
import com.sisecofi.libreria.comunes.model.contratos.ServicioContratoModel;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.sisecofi.libreria.comunes.model.contratos.AtrasoPrestacionModel;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class DeduccionesServiceImpl implements DeduccionesService {

	private final DeduccionRepository deduccionRepository;
	private final PistaService pistaService;
	private final DictamenRepository dictamenRepository;
	private final ServicioContratoRepository servicioContratoRepository;
	private final DictamenService dictamenService;
	private final CatTipoDeduccionRepository catDeduccionRepository;
	private final NivelesServiciosSLARepository nivelesServicioSLARepository;
	private final CatDesgloceRepository catDesgloceRepository;
	private final ContratoMicoservicio contratoMicoservicio;

	@Transactional
	@Override
	public List<ObtenerPenaContractualDto> guardarDeduccion(List<ObtenerPenaContractualRequestDto> penas) {
		try {
			Dictamen dictamen = obtenerDictamenGuardar(penas);
			Integer estatus = dictamen.getIdEstatusDictamen();

			List<DeduccionesModel> deducciones = penas.stream().filter(this::esPenaValidaGuardar)
					.map(pena -> crearDeduccion(pena, estatus, dictamen)).filter(Objects::nonNull).toList();

			if (deducciones.isEmpty()) {
				throw new CatalogoException(ErroresEnum.PENA_DOCUMENTO_NOT_FOUND, "Penas contractuales");
			}

			procesarDeducciones(deducciones);

			List<DeduccionesModel> penasActualizadas = deducciones.stream()
					.map(d -> deduccionRepository.findById(d.getIdDeduccion())).filter(Optional::isPresent)
					.map(Optional::get).toList();

			return mapearADtoAnterior(penasActualizadas);

		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.PENA_DOCUMENTO_NOT_FOUND, "Penas contractuales");
		}
	}

	private List<ObtenerPenaContractualDto> mapearADtoAnterior(List<DeduccionesModel> modelos) {
		return modelos.stream().map(model -> {
			ObtenerPenaContractualDto dto = new ObtenerPenaContractualDto();
			dto.setIdPenaPrimary(model.getIdDeduccion());
			dto.setIdTipoPena(Long.parseLong (""+model.getIdTipoDeduccion()));
			dto.setTipoPena(
					Optional.ofNullable(model.getCatDeduccion()).map(CatTipoDeduccion::getNombre).orElse(null));

			asignarInformesId(model, dto);
			dto.setDesglose(Optional.ofNullable(model.getCatDesgloce()).map(CatDesgloce::getNombre).orElse(null));
			dto.setDictamenId(model.getIdDictamen());
			dto.setConceptoServicio(model.getConceptoServicio());
			dto.setMonto(model.getMonto());
			dto.setPenaAplicable(model.getPenaAplicable());
			dto.setIdDesglose(model.getIdDesgloce());
			return dto;
		}).toList();
	}

	private void asignarInformes(DeduccionesModel model, ObtenerPenasDeduccionResponseDto dto) {
		dto.setIdServiciosSla(model.getIdServiciosSla());
		dto.setSlaNombre(Optional.ofNullable(model.getNivelesServicioSLAModel()).map(NivelesServicioSLAModel::getSla)
				.orElse(null));
		
		dto.setIdInformeSevicios(model.getIdServicios());
		dto.setInformeSeviciosNombre(Optional.ofNullable(model.getInformesDocumentalesServiciosModel()).map(InformesDocumentalesServiciosModel::getInformeDocumental)
				.orElse(null));
		
		dto.setIdPeriodicos(model.getIdPeriodicos());
		dto.setPeriodicosNombre(Optional.ofNullable(model.getInformesDocumentalesPeriodicosModel()).map(InformesDocumentalesPeriodicosModel::getInformeDocumental)
				.orElse(null));
		
		dto.setIdInformeUv(model.getId());
		dto.setInformeUvNombre(Optional.ofNullable(model.getInformesDocumentalesUnicaVezModel()).map(InformesDocumentalesUnicaVezModel::getInformeDocumental)
				.orElse(null));
		
		dto.setIdAtraso(model.getIdAtrasoPrestacion());
		dto.setAtrasoNombre(Optional.ofNullable(model.getAtrasoPrestacionModel()).map(AtrasoPrestacionModel::getDescripcion)
				.orElse(null));
		
	}
	
	private void asignarInformesId(DeduccionesModel pena, ObtenerPenaContractualDto dto) {
		if (pena.getInformesDocumentalesServiciosModel() != null) {
			dto.setIdDocumento(pena.getIdServicios());
			dto.setDocumentoNombre(pena.getInformesDocumentalesServiciosModel().getInformeDocumental());
		} else if (pena.getInformesDocumentalesPeriodicosModel() != null) {
			dto.setIdDocumento(pena.getIdPeriodicos());
			dto.setDocumentoNombre(pena.getInformesDocumentalesPeriodicosModel().getInformeDocumental());
		} else if (pena.getInformesDocumentalesUnicaVezModel() != null) {
			dto.setIdDocumento(pena.getId());
			dto.setDocumentoNombre(pena.getInformesDocumentalesUnicaVezModel().getInformeDocumental());
		} else if (pena.getNivelesServicioSLAModel() != null) {
			dto.setIdDocumento(pena.getIdServiciosSla());
			dto.setDocumentoNombre(pena.getNivelesServicioSLAModel().getSla());
		} else if (pena.getAtrasoPrestacionModel() != null) {
			dto.setIdDocumento(pena.getIdAtrasoPrestacion());
			dto.setDocumentoNombre(pena.getAtrasoPrestacionModel().getDescripcion());
		} else {
			dto.setIdDocumento(null);
			dto.setDocumentoNombre(null);
		}
		
	}

	private Dictamen obtenerDictamen(List<ObtenerPenaContractualDto> penas) {
		log.info("dictamen id: {}", penas.get(0).getDictamenId());
		return dictamenRepository.findByIdDictamen(penas.get(0).getDictamenId())
				.orElseThrow(() -> new CatalogoException(ErroresEnum.PENA_DOCUMENTO_NOT_FOUND, "deducciones"));
	}

	private Dictamen obtenerDictamenGuardar(List<ObtenerPenaContractualRequestDto> penas) {
		return dictamenRepository.findByIdDictamen(penas.get(0).getDictamenId())
				.orElseThrow(() -> new CatalogoException(ErroresEnum.PENA_DOCUMENTO_NOT_FOUND, "deducciones"));
	}

	private boolean esPenaValida(ObtenerPenaContractualDto pena) {
		return pena.getIdDesglose() != null && pena.getPenaAplicable() != null && pena.getMonto() != null
				&& pena.getIdDocumento() != null && pena.getConceptoServicio() != null;
	}

	private boolean esPenaValidaGuardar(ObtenerPenaContractualRequestDto pena) {
		return pena.getIdDesglose() != null && pena.getPenaAplicable() != null && pena.getMonto() != null
				&& pena.getIdDocumento() != null && pena.getConceptoServicio() != null;
	}

	private DeduccionesModel crearDeduccion(ObtenerPenaContractualRequestDto pena, Integer estatus,
			Dictamen estatusDictamen) {
		boolean esEstatusDos = (estatus == 2 && pena.getMonto() != null);
		boolean esOtroEstatus = (estatus == 1);

		if (esEstatusDos || esOtroEstatus) {
			DeduccionesModel deducciones = new DeduccionesModel();
			deducciones.setIdTipoDeduccion(pena.getIdTipoPena().intValue());
			deducciones.setIdDesgloce(pena.getIdDesglose());
			asignarTipoPena(deducciones, transformarDto(pena));
			deducciones.setIdPeriodicos(pena.getIdDocumento());
			deducciones.setConceptoServicio(pena.getConceptoServicio());
			deducciones.setMonto(pena.getMonto());
			deducciones.setPenaAplicable(pena.getPenaAplicable());
			deducciones.setIdDictamen(estatusDictamen.getIdDictamen());
			deducciones.setEstatus(true);

			CatTipoDeduccion catDeduccion = catDeduccionRepository.findById(pena.getIdTipoPena().intValue())
					.orElse(null);
			deducciones.setCatDeduccion(catDeduccion);

			CatDesgloce catDesgloce = catDesgloceRepository.findById(pena.getIdDesglose()).orElse(null);
			deducciones.setCatDesgloce(catDesgloce);

			return deducciones;
		}
		return null;
	}

	private ObtenerPenaContractualDto transformarDto(ObtenerPenaContractualRequestDto pena) {
		ObtenerPenaContractualDto nuevaPena = new ObtenerPenaContractualDto();
		nuevaPena.setIdDocumento(pena.getIdDocumento());
		nuevaPena.setTipoPena(pena.getTipoPena());
		return nuevaPena;
	}

	private void asignarTipoPena(DeduccionesModel deducciones, ObtenerPenaContractualDto pena) {
		switch (pena.getTipoPena()) {
		case "Informes documentales de los servicios":
			deducciones.setInformesDocumentalesServiciosModel(
					contratoMicoservicio.obtenerInformeDocumental(pena.getIdDocumento()));
			deducciones.setIdServicios(pena.getIdDocumento());
			break;
		case "Informes documentales periódicos":
			deducciones.setInformesDocumentalesPeriodicosModel(
					contratoMicoservicio.obtenerInformeDocumentalPeriodico(pena.getIdDocumento()));
			deducciones.setIdPeriodicos(pena.getIdDocumento());
			break;
		case "Informes documentales por única vez":
			deducciones.setInformesDocumentalesUnicaVezModel(
					contratoMicoservicio.obtenerInformeDocumentalUv(pena.getIdDocumento()));
			deducciones.setId(pena.getIdDocumento());
			break;
		case "Niveles de servicio":
			deducciones.setIdServiciosSla(pena.getIdDocumento());
			NivelesServicioSLAModel slaModel = nivelesServicioSLARepository.findById(pena.getIdDocumento())
					.orElse(null);
			deducciones.setNivelesServicioSLAModel(slaModel);
			break;
		case "Atraso en el inicio de la prestación":
			deducciones.setIdAtrasoPrestacion(pena.getIdDocumento());
			deducciones.setAtrasoPrestacionModel(
					contratoMicoservicio.obtenerAtrasoPrestacionIndividual(pena.getIdDocumento()));
			break;
		default:
			break;
		}
	}

	private void procesarDeducciones(List<DeduccionesModel> listaPenas) {
		Optional<Dictamen> dictamen = Optional.ofNullable(listaPenas.get(0).getDictamen());
		if (!dictamen.isPresent()) {

			dictamen = dictamenRepository.findByIdDictamen(listaPenas.get(0).getIdDictamen());
		}

		if (dictamen.isPresent()) {
			dictamen.get().setUltimaModificacion(dictamenService.ultimaModificacionGeneral());
		}

		listaPenas = deduccionRepository.saveAll(listaPenas);
		String ids = listaPenas.stream().map(DeduccionesModel::getIdDeduccion).map(String::valueOf)
				.collect(Collectors.joining(","));

		for (DeduccionesModel deduccionesModel : listaPenas) {
			pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),
					TipoSeccionPista.DICTAMEN_DEDUCCIONES.getIdSeccionPista(), Constantes.getAtributosDeducciones()[0]
							+ deduccionesModel.getIdDictamen() + "|" + Constantes.getAtributosDeducciones()[1] + ids,
					Optional.of(deduccionesModel));
		}
		dictamenService.actualizarResumenConsolidado(listaPenas.get(0).getIdDictamen());
	}

	@Override
	public List<ObtenerPenaContractualDto> modificarDeduccion(List<ObtenerPenaContractualDto> penasDto) {
		try {
			if (penasDto == null || penasDto.isEmpty()) {
				throw new CatalogoException(ErroresEnum.PENA_DOCUMENTO_NOT_FOUND, "Lista de deducciones vacía o nula");
			}

			Dictamen estatusDictamen = obtenerDictamen(penasDto);
			Integer estatus = estatusDictamen.getIdEstatusDictamen();
			List<DeduccionesModel> listaDeduccionesModificadas = new ArrayList<>();

			for (ObtenerPenaContractualDto contractualDto : penasDto) {
				DeduccionesModel deduccionModificar = obtenerDeduccion(contractualDto);
				if (esPenaValida(contractualDto)) {
					modificarDeduccionSiEstatusValido(deduccionModificar, contractualDto, estatus, estatusDictamen);
					listaDeduccionesModificadas.add(deduccionModificar);
				} else {
					log.warn("Pena contractual con id: {} no cumple las condiciones del estatus",
							contractualDto.getIdPenaPrimary());
				}
			}

			if (!listaDeduccionesModificadas.isEmpty()) {
				return mapearADtoAnterior (procesarDeduccionesModificadas(listaDeduccionesModificadas, estatusDictamen.getIdDictamen()));
			} else {
				throw new CatalogoException(ErroresEnum.PENA_DOCUMENTO_NOT_FOUND,
						"No se encontraron deducciones válidas para modificar.");
			}
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.PENA_DOCUMENTO_NOT_FOUND);
		}
	}

	private DeduccionesModel obtenerDeduccion(ObtenerPenaContractualDto contractualDto) {
		return deduccionRepository.findById(contractualDto.getIdPenaPrimary()).orElseThrow(
				() -> new CatalogoException(ErroresEnum.PENA_DOCUMENTO_NOT_FOUND, "Deducción no encontrada"));
	}

	private void modificarDeduccionSiEstatusValido(DeduccionesModel deduccionModificar,
			ObtenerPenaContractualDto contractualDto, Integer estatus, Dictamen estatusDictamen) {
		boolean esEstatusDos = (estatus == 2 || estatus == 1);

		if (esEstatusDos) {
			limpiarCamposDeduccion(deduccionModificar);
			asignarTipoPena(deduccionModificar, contractualDto);
			deduccionModificar.setIdTipoDeduccion(Integer.parseInt("" + contractualDto.getIdTipoPena()));
			deduccionModificar.setPenaAplicable(contractualDto.getPenaAplicable());
			deduccionModificar.setIdDesgloce(contractualDto.getIdDesglose());
			deduccionModificar.setIdDictamen(estatusDictamen.getIdDictamen());
			deduccionModificar.setMonto(contractualDto.getMonto());
			deduccionModificar.setConceptoServicio(contractualDto.getConceptoServicio());
		}
	}

	private void limpiarCamposDeduccion(DeduccionesModel deduccionModificar) {
		deduccionModificar.setIdServicios(null);
		deduccionModificar.setIdPeriodicos(null);
		deduccionModificar.setId(null);
		deduccionModificar.setIdServiciosSla(null);
	}

	private List<DeduccionesModel> procesarDeduccionesModificadas(List<DeduccionesModel> listaDeduccionesModificadas,
			Long dictamenId) {
		Optional<Dictamen> dictamen = Optional.ofNullable(listaDeduccionesModificadas.get(0).getDictamen());
		if (!dictamen.isPresent()) {

			dictamen = dictamenRepository.findByIdDictamen(listaDeduccionesModificadas.get(0).getIdDictamen());
		}

		if (dictamen.isPresent()) {
			dictamen.get().setUltimaModificacion(dictamenService.ultimaModificacionGeneral());
		}
		listaDeduccionesModificadas = deduccionRepository.saveAll(listaDeduccionesModificadas);

		String ids = listaDeduccionesModificadas.stream().map(DeduccionesModel::getIdTipoDeduccion).map(String::valueOf)
				.collect(Collectors.joining(","));

		dictamenService.actualizarResumenConsolidado(dictamenId);

		for (DeduccionesModel deduccionesModel : listaDeduccionesModificadas) {
			pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),
					TipoSeccionPista.DICTAMEN_DEDUCCIONES.getIdSeccionPista(),
					Constantes.getAtributosDeducciones()[0] + deduccionesModel.getIdTipoDeduccion() + "|"
							+ Constantes.getAtributosDeducciones()[1] + ids,
					Optional.of(deduccionesModel));
		}

		return listaDeduccionesModificadas;
	}

	@Override
	public List<ObtenerPenaContractualDto> obtenerDeducciones(Long dictamenId) {

		log.info("dictamen id: {}", dictamenId);
		List<DeduccionesModel> penas = deduccionRepository
				.findByIdDictamenAndEstatusTrueAndDictamenEstatusTrueOrderByIdDeduccionAsc(dictamenId);

		if (penas == null || penas.isEmpty()) {
			return Collections.emptyList();
		}

		try {

			List<ObtenerPenaContractualDto> lista = new ArrayList<>();
			for (DeduccionesModel pena : penas) {
				ObtenerPenaContractualDto dto = new ObtenerPenaContractualDto();
				dto.setIdTipoPena(Long.parseLong("" + pena.getIdTipoDeduccion()));
				dto.setTipoPena(pena.getCatDeduccion().getNombre());
				dto.setIdPenaPrimary(pena.getIdDeduccion());
				dto.setPenaAplicable(pena.getPenaAplicable());
				dto.setDesglose(pena.getCatDesgloce().getNombre());
				dto.setIdDesglose(pena.getIdDesgloce());
				dto.setMonto(pena.getMonto());
				dto.setDictamenId(dictamenId);
				dto.setConceptoServicio(pena.getConceptoServicio());

				if (pena.getInformesDocumentalesServiciosModel() != null) {
					dto.setIdDocumento(pena.getIdServicios());
					dto.setDocumentoNombre(pena.getInformesDocumentalesServiciosModel().getInformeDocumental());
				} else if (pena.getInformesDocumentalesPeriodicosModel() != null) {
					dto.setIdDocumento(pena.getIdPeriodicos());
					dto.setDocumentoNombre(pena.getInformesDocumentalesPeriodicosModel().getInformeDocumental());
				} else if (pena.getInformesDocumentalesUnicaVezModel() != null) {
					dto.setIdDocumento(pena.getId());
					dto.setDocumentoNombre(pena.getInformesDocumentalesUnicaVezModel().getInformeDocumental());
				} else if (pena.getNivelesServicioSLAModel() != null) {
					dto.setIdDocumento(pena.getIdServiciosSla());
					dto.setDocumentoNombre(pena.getNivelesServicioSLAModel().getSla());
				} else if (pena.getAtrasoPrestacionModel() != null) {
					dto.setIdDocumento(pena.getIdAtrasoPrestacion());
					dto.setDocumentoNombre(pena.getAtrasoPrestacionModel().getDescripcion());
				} else {
					dto.setIdDocumento(null);
					dto.setDocumentoNombre(null);
				}

				lista.add(dto);
			}

			return lista;
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_GUARDAR_PISTA);
		}
	}

	@Override
	public List<Long> eliminarRegistro(Map<String, List<Long>> request) {
		List<Long> ids = request.get("ids");
		if (ids == null || ids.isEmpty()) {
			throw new CatalogoException(ErroresEnum.PENA_DOCUMENTO_NOT_FOUND);
		}
		List<Long> idsActualizados = new ArrayList<>();
		for (Long id : ids) {
			if (deduccionRepository.existsById(id)) {
				DeduccionesModel pena = deduccionRepository.findById(id)
						.orElseThrow(() -> new CatalogoException(ErroresEnum.DEDUCCIONES_NOT_FOUND));
				pena.setEstatus(false);
				Dictamen dictamen = pena.getDictamen();
				dictamen.setUltimaModificacion(dictamenService.ultimaModificacionGeneral());
				deduccionRepository.save(pena);
				pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.BORRA_REGISTRO.getId(),
						TipoSeccionPista.DICTAMEN_DEDUCCIONES.getIdSeccionPista(),
						Constantes.getAtributosDeducciones()[0] + pena.getIdDictamen() + "|"
								+ Constantes.getAtributosDeducciones()[1] + ids,
						Optional.of(pena));
				idsActualizados.add(id);
			} else {
				throw new CatalogoException(ErroresEnum.PENA_DOCUMENTO_NOT_FOUND);
			}
		}
		return idsActualizados;
	}

	@Override
	public List<String> obtenerConceptosServicio(String tipo, Long dictamenId) {
		if (tipo.equals("Niveles de servicio")) {
			Dictamen dictamen = dictamenRepository.findByIdDictamen(dictamenId)
					.orElseThrow(() -> new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));
			return Stream.concat(Stream.of("Todos", "N/A"),
					servicioContratoRepository.obtenerServiciosOrdenados(dictamen.getIdContrato()).stream()
							.map(ServicioContratoModel::getConcepto))
					.toList();
		}

		return List.of("N/A");
	}

}
