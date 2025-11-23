package com.sisecofi.admindevengados.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.sisecofi.libreria.comunes.dto.dictamen.PenasContractualesByIdDto;
import org.springframework.stereotype.Service;

import com.sisecofi.admindevengados.dto.ObtenerPenaContractualDto;
import com.sisecofi.admindevengados.microservicio.ContratoMicoservicio;
import com.sisecofi.libreria.comunes.model.penasContractuales.PenasContractualesModel;
import com.sisecofi.admindevengados.model.PenasConvencionalesModel;
import com.sisecofi.admindevengados.repository.PenasContractualesRepository;
import com.sisecofi.admindevengados.repository.DictamenRepository;
import com.sisecofi.admindevengados.service.DictamenService;
import com.sisecofi.admindevengados.service.PenasContractualesService;
import com.sisecofi.admindevengados.service.PistaService;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.admindevengados.util.enums.ErroresEnum;
import com.sisecofi.admindevengados.util.exception.CatalogoException;
import com.sisecofi.libreria.comunes.model.contratos.AtrasoPrestacionModel;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesPeriodicosModel;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesServiciosModel;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesUnicaVezModel;
import com.sisecofi.libreria.comunes.model.contratos.NivelesServicioSLAModel;
import com.sisecofi.libreria.comunes.model.contratos.PenaContractualContratoModel;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
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
public class PenasContractualesServiceImpl implements PenasContractualesService {

	private final PenasContractualesRepository penaContractualRepository;
	private final PistaService pistaService;
	private final DictamenRepository dictamenRepository;
	private final ContratoMicoservicio contratoMicoservicio;
	private final DictamenService dictamenService;

	@Override
	public List<PenasContractualesModel> guardarPenaContractual(List<ObtenerPenaContractualDto> penas) {
		Dictamen dictamen = obtenerDictamen(penas);
		Integer estatus = dictamen.getIdEstatusDictamen();

		List<PenasContractualesModel> listaPenas = new ArrayList<>();
		for (ObtenerPenaContractualDto pena : penas) {
			PenasContractualesModel penaContractual = crearPenaContractual(pena, estatus, dictamen.getIdDictamen());
			if (penaContractual != null) {
				listaPenas.add(penaContractual);
				actualizarDictamen(dictamen);
			}
		}

		if (!listaPenas.isEmpty()) {
			listaPenas = penaContractualRepository.saveAll(listaPenas);
			registrarPista(listaPenas);
			dictamenService.actualizarResumenConsolidado(listaPenas.get(0).getIdDictamen());
			return listaPenas;
		} else {
			throw new CatalogoException(ErroresEnum.PENA_DOCUMENTO_NOT_FOUND, Constantes.PENA_CONTRACTUAL);
		}
	}

	private Dictamen obtenerDictamen(List<ObtenerPenaContractualDto> penas) {

		log.info("dictamen id: {}", penas.get(0).getDictamenId());
		return dictamenRepository.findByIdDictamen(penas.get(0).getDictamenId())
				.orElseThrow(() -> new CatalogoException(ErroresEnum.PENA_DOCUMENTO_NOT_FOUND, Constantes.PENA_CONTRACTUAL));
	}

	private PenasContractualesModel crearPenaContractual(ObtenerPenaContractualDto pena, Integer estatus,
			Long idDictamen) {
		if (pena.getIdDesglose() == null || pena.getPenaAplicable() == null || pena.getMonto() == null
				|| pena.getIdDocumento() == null) {
			return null;
		}

		boolean esEstatusValido = (estatus == 2 && pena.getMonto() != null)
				|| (estatus == 1 && BigDecimal.ZERO.compareTo(pena.getMonto()) == 0);
		if (!esEstatusValido) {
			return null;
		}

		PenasContractualesModel penasContractual = new PenasContractualesModel();
		asignarTipoPena(penasContractual, pena);
		penasContractual.setIdTipoPenaContractual(Integer.parseInt("" + pena.getIdTipoPena()));
		penasContractual.setPenaAplicable(pena.getPenaAplicable());
		penasContractual.setIdDesgloce(pena.getIdDesglose());
		penasContractual.setIdDictamen(idDictamen);
		penasContractual.setMonto(pena.getMonto());
		penasContractual.setEstatus(true);
		return penasContractual;
	}

	private void asignarTipoPena(PenasContractualesModel penasContractual, ObtenerPenaContractualDto pena) {
		switch (pena.getTipoPena()) {
		case "Informes documentales de los servicios":
			penasContractual.setIdServicios(pena.getIdDocumento());
			break;
		case "Informes documentales periódicos":
			penasContractual.setIdPeriodicos(pena.getIdDocumento());
			break;
		case "Informes documentales por única vez":
			penasContractual.setId(pena.getIdDocumento());
			break;
		case "Penas contractuales":
			penasContractual.setIdPenaContractualContrato(pena.getIdDocumento());
			break;
		case "Atraso en el inicio de la prestación":
			penasContractual.setIdAtrasoPrestacion(pena.getIdDocumento());
			break;
		default:
			log.warn("Tipo de pena desconocido: {}", pena.getTipoPena());
		}
	}

	private void actualizarDictamen(Dictamen dictamen) {
		dictamen.setUltimaModificacion(dictamenService.ultimaModificacionGeneral());
	}

	private void registrarPista(List<PenasContractualesModel> listaPenas) {
		String ids = listaPenas.stream().map(PenasContractualesModel::getIdPenaContractual).map(String::valueOf)
				.collect(Collectors.joining(","));

		for (PenasContractualesModel penasContractualesModel : listaPenas) {
			pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),
					TipoSeccionPista.DICTAMEN_PENAS_CONTRACTUALES.getIdSeccionPista(),
					Constantes.getAtributosContractuales()[0] + listaPenas.get(0).getIdDictamen() + "|"
							+ Constantes.getAtributosContractuales()[1] + ids + "|",
					Optional.of(penasContractualesModel));
		}
	}

	@Override
	public List<PenasContractualesModel> modificarPenaContractual(List<ObtenerPenaContractualDto> penas) {
		Dictamen dictamen = obtenerDictamen(penas);
		Integer estatus = dictamen.getIdEstatusDictamen();

		List<PenasContractualesModel> listaPenasModificadas = new ArrayList<>();

		for (ObtenerPenaContractualDto pena : penas) {
			PenasContractualesModel penaModificada = modificarPenaContractual(pena, estatus, dictamen);
			if (penaModificada != null) {
				listaPenasModificadas.add(penaModificada);
			}
		}

		if (listaPenasModificadas.isEmpty()) {
			throw new CatalogoException(ErroresEnum.PENA_DOCUMENTO_NOT_FOUND, Constantes.PENA_CONTRACTUAL);
		}

		actualizarDictamen(dictamen);
		List<PenasContractualesModel> penasGuardadas = penaContractualRepository.saveAll(listaPenasModificadas);
		String ids = penasGuardadas.stream().map(PenasContractualesModel::getIdPenaContractual).map(String::valueOf)
				.collect(Collectors.joining(","));
		for (PenasContractualesModel penasContractualesModel : penasGuardadas) {
			pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),
					TipoSeccionPista.DICTAMEN_PENAS_CONTRACTUALES.getIdSeccionPista(),
					Constantes.getAtributosContractuales()[0] + penasContractualesModel.getIdDictamen() + "|"
							+ Constantes.getAtributosContractuales()[1] + ids + "|",
					Optional.of(penasContractualesModel));
		}
		dictamenService.actualizarResumenConsolidado(dictamen.getIdDictamen());

		return penasGuardadas;
	}

	private PenasContractualesModel modificarPenaContractual(ObtenerPenaContractualDto pena, Integer estatus,
			Dictamen dictamen) {
		PenasContractualesModel penasContractual = penaContractualRepository.findById(pena.getIdPenaPrimary())
				.orElseThrow(() -> new CatalogoException(ErroresEnum.PENA_DOCUMENTO_NOT_FOUND,
						"Pena contractual no encontrada"));

		if (pena.getIdDesglose() == null || pena.getPenaAplicable() == null || pena.getMonto() == null
				|| pena.getIdDocumento() == null) {
			return null;
		}

		boolean esEstatusValido = (estatus == 2 && pena.getMonto() != null)
				|| (estatus == 1 && BigDecimal.ZERO.compareTo(pena.getMonto()) == 0);
		if (!esEstatusValido) {
			return null;
		}

		limpiarCamposPenaContractual(penasContractual);
		asignarTipoPena(penasContractual, pena);
		penasContractual.setIdTipoPenaContractual(Integer.parseInt("" + pena.getIdTipoPena()));
		penasContractual.setPenaAplicable(pena.getPenaAplicable());
		penasContractual.setIdDesgloce(pena.getIdDesglose());
		penasContractual.setIdDictamen(dictamen.getIdDictamen());
		log.info("monto {}", pena.getMonto());
		penasContractual.setMonto(pena.getMonto());

		return penasContractual;
	}

	private void limpiarCamposPenaContractual(PenasContractualesModel penasContractual) {
		penasContractual.setIdServicios(null);
		penasContractual.setIdPeriodicos(null);
		penasContractual.setId(null);
		penasContractual.setIdPenaContractualContrato(null);
		penasContractual.setIdAtrasoPrestacion(null);
	}

	@Override
	public <T> List<ObtenerPenaContractualDto> construirListaPenas(List<T> penas, Long dictamenId) {
	    List<ObtenerPenaContractualDto> lista = new ArrayList<>();

	    for (T pena : penas) {
	        ObtenerPenaContractualDto dto = new ObtenerPenaContractualDto();

	        if (pena instanceof PenasContractualesModel penaContractual) {
	            informacionPenaContractual(dto, penaContractual);
	            informacionDocumentos(dto, penaContractual);
	        } else if (pena instanceof PenasConvencionalesModel penaConvencional) {
	            informacionPenaConvencional(dto, penaConvencional);
	            informacionDocumentos(dto, penaConvencional);
	        }

	        dto.setDictamenId(dictamenId);
	        lista.add(dto);
	    }

	    return lista;
	}

	private void informacionPenaContractual(ObtenerPenaContractualDto dto, PenasContractualesModel pena) {
	    dto.setIdTipoPena(Long.parseLong("" + pena.getCatTipoPenaContractual().getIdTipoPenaContractual()));
	    dto.setTipoPena(pena.getCatTipoPenaContractual().getNombre());
	    dto.setIdPenaPrimary(pena.getIdPenaContractual());
	    dto.setPenaAplicable(pena.getPenaAplicable());
	    dto.setDesglose(pena.getCatDesgloce().getNombre());
	    dto.setIdDesglose(pena.getIdDesgloce());
	    dto.setMonto(pena.getMonto());
	}

	private void informacionPenaConvencional(ObtenerPenaContractualDto dto, PenasConvencionalesModel pena) {
	    dto.setIdTipoPena(Long.parseLong("" + pena.getCatTipoPenaConvencional().getIdTipoPenaConvencional()));
	    dto.setTipoPena(pena.getCatTipoPenaConvencional().getNombre());
	    dto.setIdPenaPrimary(pena.getIdPenaConvencional());
	    dto.setPenaAplicable(pena.getPenaAplicable());
	    dto.setDesglose(pena.getCatDesgloce().getNombre());
	    dto.setIdDesglose(pena.getIdDesgloce());
	    dto.setMonto(pena.getMonto());
	}

	private <P> void informacionDocumentos(ObtenerPenaContractualDto dto, P pena) {
	    if (pena instanceof PenasContractualesModel penasContractualesModel) {
	    	setPenasContractualesDocumentos(dto, penasContractualesModel);
	    } else if (pena instanceof PenasConvencionalesModel penasConvencionalesModel) {
	    	setPenasConvencionalesDocumentos(dto, penasConvencionalesModel);
	    } else {
	        dto.setIdDocumento(null);
	        dto.setDocumentoNombre(null);
	    }
	}

	private void setPenasContractualesDocumentos(ObtenerPenaContractualDto dto, PenasContractualesModel pena) {
	    if (pena.getInformesDocumentalesServiciosModel() != null) {
	        dto.setIdDocumento(pena.getIdServicios());
	        dto.setDocumentoNombre(pena.getInformesDocumentalesServiciosModel().getInformeDocumental());
	    } else if (pena.getInformesDocumentalesPeriodicosModel() != null) {
	        dto.setIdDocumento(pena.getIdPeriodicos());
	        dto.setDocumentoNombre(pena.getInformesDocumentalesPeriodicosModel().getInformeDocumental());
	    } else if (pena.getInformesDocumentalesUnicaVezModel() != null) {
	        dto.setIdDocumento(pena.getId());
	        dto.setDocumentoNombre(pena.getInformesDocumentalesUnicaVezModel().getInformeDocumental());
	    } else if (pena.getPenaContractualContratoModel() != null) {
	        dto.setIdDocumento(pena.getIdPenaContractualContrato());
	        dto.setDocumentoNombre(pena.getPenaContractualContratoModel().getInformeDocumentoConcepto());
	    } else if (pena.getAtrasoPrestacionModel() != null) {
	        dto.setIdDocumento(pena.getIdAtrasoPrestacion());
	        dto.setDocumentoNombre(pena.getAtrasoPrestacionModel().getDescripcion());
	    } else {
	        dto.setIdDocumento(null);
	        dto.setDocumentoNombre(null);
	    }
	}

	private void setPenasConvencionalesDocumentos(ObtenerPenaContractualDto dto, PenasConvencionalesModel pena) {
	    if (pena.getInformesDocumentalesServiciosModel() != null) {
	        dto.setIdDocumento(pena.getIdServicios());
	        dto.setDocumentoNombre(pena.getInformesDocumentalesServiciosModel().getInformeDocumental());
	    } else if (pena.getInformesDocumentalesPeriodicosModel() != null) {
	        dto.setIdDocumento(pena.getIdPeriodicos());
	        dto.setDocumentoNombre(pena.getInformesDocumentalesPeriodicosModel().getInformeDocumental());
	    } else if (pena.getInformesDocumentalesUnicaVezModel() != null) {
	        dto.setIdDocumento(pena.getId());
	        dto.setDocumentoNombre(pena.getInformesDocumentalesUnicaVezModel().getInformeDocumental());
	    } else if (pena.getAtrasoPrestacionModel() != null) {
	        dto.setIdDocumento(pena.getIdAtrasoPrestacion());
	        dto.setDocumentoNombre(pena.getAtrasoPrestacionModel().getDescripcion());
	    } else {
	        dto.setIdDocumento(null);
	        dto.setDocumentoNombre(null);
	    }
	}


	@Override
	public List<ObtenerPenaContractualDto> obtenerPenasContractuales(Long dictamenId) {
		log.info("dictamen id: {}", dictamenId);
		
		List<PenasContractualesModel> penas = penaContractualRepository.findByIdDictamenAndEstatusTrueOrderByIdPenaContractualAsc(dictamenId);

		if (penas == null || penas.isEmpty()) {
			return Collections.emptyList();
		}

		try {
			List<ObtenerPenaContractualDto> lista = construirListaPenas(penas, dictamenId);
			log.info("lista: {}", lista.toString());
			return lista;
		} catch (Exception e) {
			log.error("Error al obtener penas contractuales");
			throw new CatalogoException(ErroresEnum.ERROR_GUARDAR_PISTA, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> obtenerDocumentosContratoFuncional(Long idContrato, String tipo) {
		List<T> listaDocumentos = new ArrayList<>();

		if (tipo.equals("Informes documentales de los servicios")) {
			List<InformesDocumentalesServiciosModel> informesDocumentalesServiciosModel = contratoMicoservicio
					.obtenerTodosInformesDocumentalesServicios(idContrato);
			listaDocumentos.addAll((List<T>) informesDocumentalesServiciosModel);
		} else if (tipo.equals("Informes documentales periódicos")) {
			List<InformesDocumentalesPeriodicosModel> informesDocumentalesPeriodicosModel = contratoMicoservicio
					.obtenerTodosInformesDocumentalesPeriodicos(idContrato);
			listaDocumentos.addAll((List<T>) informesDocumentalesPeriodicosModel);
		} else if (tipo.equals("Informes documentales por única vez")) {
			List<InformesDocumentalesUnicaVezModel> informesDocumentalesUnicaVezModel = contratoMicoservicio
					.obtenerTodosInformesDocumentales(idContrato);
			listaDocumentos.addAll((List<T>) informesDocumentalesUnicaVezModel);
		} else if (tipo.equals(Constantes.PENA_CONTRACTUAL)) {
			List<PenaContractualContratoModel> penaContractualContratoModel = contratoMicoservicio
					.obtenerPenas(idContrato);
			listaDocumentos.addAll((List<T>) penaContractualContratoModel);
		} else if (tipo.equals("Niveles de servicio")) {
			List<NivelesServicioSLAModel> nivelesServicioSLAModel = contratoMicoservicio
					.obtenerTodosNivelesServicioSLA(idContrato);
			listaDocumentos.addAll((List<T>) nivelesServicioSLAModel);
		} else if (tipo.equals("Atraso en el inicio de la prestación")) {
			List<AtrasoPrestacionModel> atrasoPrestacionModel = contratoMicoservicio
					.obtenerAtrasoPrestacion(idContrato);
			listaDocumentos.addAll((List<T>) atrasoPrestacionModel);
		}

		return listaDocumentos;
	}

	@Override
	public List<PenasContractualesModel> obtenerPenasContractualesPorIds(
			PenasContractualesByIdDto penasContractualesByIdDto) {
		try {
			Long idPenaContractual = penasContractualesByIdDto.getIdPenaContractual();
			Long idServicio = penasContractualesByIdDto.getIdInfoDocServicios();
			Long idPeriodicos = penasContractualesByIdDto.getIdInfoDocPeriodicos();
			Long idInfoDocUnicaVez = penasContractualesByIdDto.getIdInfoDocUnicaVez();
			Long idAtrasoPrestacion = penasContractualesByIdDto.getIdAtraso();

			List<PenasContractualesModel> listaDefault = new ArrayList<>();

			if (idPenaContractual != null) {
				return penaContractualRepository.findByIdPenaContractualContratoAndEstatusTrue(idPenaContractual);
			}
			if (idServicio != null) {
				return penaContractualRepository.findByIdServiciosAndEstatusTrue(idServicio);
			}
			if (idPeriodicos != null) {
				return penaContractualRepository.findByIdPeriodicosAndEstatusTrue(idPeriodicos);
			}
			if (idInfoDocUnicaVez != null) {
				return penaContractualRepository.findByIdAndEstatusTrue(idInfoDocUnicaVez);
			}
			if (idAtrasoPrestacion != null) {
				return penaContractualRepository.findByIdAtrasoPrestacionAndEstatusTrue(idInfoDocUnicaVez);
			} else {
				return listaDefault;
			}
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR);
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
			if (penaContractualRepository.existsById(id)) {
				PenasContractualesModel pena = penaContractualRepository.findById(id)
						.orElseThrow(() -> new CatalogoException(ErroresEnum.PENA_CONTRACTUAL_NOT_FOUND));
				pena.setEstatus(false);
				penaContractualRepository.save(pena);
				pistaService
				.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.BORRA_REGISTRO.getId(),
						TipoSeccionPista.DICTAMEN_PENAS_CONTRACTUALES.getIdSeccionPista(),
						Constantes.getAtributosContractuales()[0] + pena.getIdDictamen() + "|"
								+ Constantes.getAtributosContractuales()[1] + ids,
						Optional.of(pena));
				Dictamen dictamen = pena.getDictamen();
				dictamen.setUltimaModificacion(dictamenService.ultimaModificacionGeneral());
				idsActualizados.add(id);
			} else {
				throw new CatalogoException(ErroresEnum.PENA_DOCUMENTO_NOT_FOUND);
			}
		}
		return idsActualizados;
	}

}
