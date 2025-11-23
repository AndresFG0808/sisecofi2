package com.sisecofi.admindevengados.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sisecofi.admindevengados.dto.ObtenerPenaContractualDto;
import com.sisecofi.admindevengados.model.PenasConvencionalesModel;
import com.sisecofi.admindevengados.repository.PenasConvencionalesRepository;
import com.sisecofi.admindevengados.repository.DictamenRepository;
import com.sisecofi.admindevengados.service.DictamenService;
import com.sisecofi.admindevengados.service.PenasContractualesService;
import com.sisecofi.admindevengados.service.PenasConvencionalesService;
import com.sisecofi.admindevengados.service.PistaService;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.admindevengados.util.enums.ErroresEnum;
import com.sisecofi.admindevengados.util.exception.CatalogoException;
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
public class PenasConvencionalesServiceImpl implements PenasConvencionalesService {

	private final PenasConvencionalesRepository penasConvencionalesRepository;
	private final PistaService pistaService;
	private final DictamenRepository dictamenRepository;
	private final DictamenService dictamenService;
	private final PenasContractualesService penasContractualesService;

	@Override
	public List<PenasConvencionalesModel> guardarPenaConvencional(List<ObtenerPenaContractualDto> penas) {
		Dictamen dictamen = obtenerDictamen(penas);
		Integer estatus = dictamen.getIdEstatusDictamen();

		List<PenasConvencionalesModel> listaPenas = new ArrayList<>();

		for (ObtenerPenaContractualDto pena : penas) {
			PenasConvencionalesModel penaConvencional = crearPenaConvencional(pena, estatus, dictamen.getIdDictamen());
			if (penaConvencional != null) {
				listaPenas.add(penaConvencional);
			}
		}

		if (listaPenas.isEmpty()) {
			throw new CatalogoException(ErroresEnum.PENA_DOCUMENTO_NOT_FOUND, Constantes.PENA_CONVENCIONAL);
		}

		actualizarDictamen(dictamen);
		List<PenasConvencionalesModel> penasGuardadas = penasConvencionalesRepository.saveAll(listaPenas);
		registrarPista(penasGuardadas);
		dictamenService.actualizarResumenConsolidado(dictamen.getIdDictamen());

		return penasGuardadas;
	}

	private Dictamen obtenerDictamen(List<ObtenerPenaContractualDto> penas) {

		log.info("dictamen id: {}", penas.get(0).getDictamenId());
		return dictamenRepository.findByIdDictamen(penas.get(0).getDictamenId()).orElseThrow(
				() -> new CatalogoException(ErroresEnum.PENA_DOCUMENTO_NOT_FOUND, " " + Constantes.PENA_CONVENCIONAL));
	}

	private PenasConvencionalesModel crearPenaConvencional(ObtenerPenaContractualDto pena, Integer estatus,
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

		PenasConvencionalesModel penaConvencional = new PenasConvencionalesModel();
		asignarTipoPena(penaConvencional, pena);
		penaConvencional.setIdTipoPenaConvencional(Integer.parseInt("" + pena.getIdTipoPena()));
		penaConvencional.setPenaAplicable(pena.getPenaAplicable());
		penaConvencional.setIdDesgloce(pena.getIdDesglose());
		penaConvencional.setIdDictamen(idDictamen);
		penaConvencional.setMonto(pena.getMonto());
		penaConvencional.setEstatus(true);
		return penaConvencional;
	}

	private void asignarTipoPena(PenasConvencionalesModel penaConvencional, ObtenerPenaContractualDto pena) {
		switch (pena.getTipoPena()) {
		case "Informes documentales de los servicios":
			penaConvencional.setIdServicios(pena.getIdDocumento());
			break;
		case "Informes documentales periódicos":
			penaConvencional.setIdPeriodicos(pena.getIdDocumento());
			break;
		case "Informes documentales por única vez":
			penaConvencional.setId(pena.getIdDocumento());
			break;
		case "Atraso en el inicio de la prestación":
			penaConvencional.setIdAtrasoPrestacion(pena.getIdDocumento());
			break;
		default:
			log.warn("Tipo de pena desconocido: {}", pena.getTipoPena());
		}
	}

	private void actualizarDictamen(Dictamen dictamen) {
		dictamen.setUltimaModificacion(dictamenService.ultimaModificacionGeneral());
	}

	private void registrarPista(List<PenasConvencionalesModel> listaPenas) {
		String ids = listaPenas.stream().map(PenasConvencionalesModel::getIdPenaConvencional).map(String::valueOf)
				.collect(Collectors.joining(","));
		for (PenasConvencionalesModel penasConvencionalesModel : listaPenas) {
			pistaService
					.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),
							TipoSeccionPista.DICTAMEN_PENAS_CONVENCIONALES.getIdSeccionPista(),
							Constantes.getAtributosConvencionales()[0] + penasConvencionalesModel.getIdDictamen() + "|"
									+ Constantes.getAtributosConvencionales()[1] + ids,
							Optional.of(penasConvencionalesModel));
		}
	}

	@Override
	public List<PenasConvencionalesModel> modificarPenaConvencional(List<ObtenerPenaContractualDto> penasDto) {
		validarPenasDto(penasDto);

		Dictamen dictamen = obtenerDictamen(penasDto);
		Integer estatus = dictamen.getIdEstatusDictamen();

		List<PenasConvencionalesModel> listaPenasModificadas = new ArrayList<>();

		for (ObtenerPenaContractualDto pena : penasDto) {
			PenasConvencionalesModel penaModificada = modificarPena(pena, estatus, dictamen.getIdDictamen());
			if (penaModificada != null) {
				listaPenasModificadas.add(penaModificada);
			}
		}

		if (listaPenasModificadas.isEmpty()) {
			throw new CatalogoException(ErroresEnum.PENA_DOCUMENTO_NOT_FOUND);
		}

		actualizarDictamen(dictamen);
		List<PenasConvencionalesModel> penasGuardadas = penasConvencionalesRepository.saveAll(listaPenasModificadas);
		String ids = penasGuardadas.stream().map(PenasConvencionalesModel::getIdPenaConvencional).map(String::valueOf)
				.collect(Collectors.joining(","));
		for (PenasConvencionalesModel penasConvencionalesModel : penasGuardadas) {
			pistaService
					.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),
							TipoSeccionPista.DICTAMEN_PENAS_CONVENCIONALES.getIdSeccionPista(),
							Constantes.getAtributosConvencionales()[0] + penasConvencionalesModel.getIdDictamen() + "|"
									+ Constantes.getAtributosConvencionales()[1] + ids,
							Optional.of(penasConvencionalesModel));
		}
		dictamenService.actualizarResumenConsolidado(dictamen.getIdDictamen());

		return penasGuardadas;
	}

	private void validarPenasDto(List<ObtenerPenaContractualDto> penasDto) {
		if (penasDto == null || penasDto.isEmpty()) {
			throw new CatalogoException(ErroresEnum.PENA_DOCUMENTO_NOT_FOUND, "Lista de penas vacía o nula");
		}
	}

	private PenasConvencionalesModel modificarPena(ObtenerPenaContractualDto pena, Integer estatus, Long idDictamen) {
		PenasConvencionalesModel penasConvencionales = penasConvencionalesRepository.findById(pena.getIdPenaPrimary())
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

		limpiarCamposPena(penasConvencionales);
		asignarTipoPena(penasConvencionales, pena);
		penasConvencionales.setIdTipoPenaConvencional(Integer.parseInt("" + pena.getIdTipoPena()));
		penasConvencionales.setPenaAplicable(pena.getPenaAplicable());
		penasConvencionales.setIdDesgloce(pena.getIdDesglose());
		penasConvencionales.setIdDictamen(idDictamen);
		penasConvencionales.setMonto(pena.getMonto());
		return penasConvencionales;
	}

	private void limpiarCamposPena(PenasConvencionalesModel penasConvencionales) {
		penasConvencionales.setIdServicios(null);
		penasConvencionales.setIdPeriodicos(null);
		penasConvencionales.setId(null);
		penasConvencionales.setIdAtrasoPrestacion(null);
	}

	@Override
	public List<ObtenerPenaContractualDto> obtenerPenasConvencional(Long dictamenId) {
	    log.info("dictamen id: {}", dictamenId);
	    List<PenasConvencionalesModel> penas = penasConvencionalesRepository.findByIdDictamenAndEstatusTrueOrderByIdPenaConvencionalAsc(dictamenId);

	    if (penas == null || penas.isEmpty()) {
	        return Collections.emptyList();
	    }

	    try {
	        List<ObtenerPenaContractualDto> lista = penasContractualesService.construirListaPenas(penas, dictamenId);
	        log.info("lista: {}", lista.toString());
	        return lista;
	    } catch (Exception e) {
	        log.error("Error al obtener penas contractuales");
	        throw new CatalogoException(ErroresEnum.ERROR_GUARDAR_PISTA, e);
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
			if (penasConvencionalesRepository.existsById(id)) {
				PenasConvencionalesModel pena = penasConvencionalesRepository.findById(id)
						.orElseThrow(() -> new CatalogoException(ErroresEnum.PENA_CONVENCIONAL_NOT_FOUND));
				pena.setEstatus(false);
				Dictamen dictamen = pena.getDictamen();
				dictamen.setUltimaModificacion(dictamenService.ultimaModificacionGeneral());
				penasConvencionalesRepository.save(pena);
				pistaService
				.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.BORRA_REGISTRO.getId(),
						TipoSeccionPista.DICTAMEN_PENAS_CONVENCIONALES.getIdSeccionPista(),
						Constantes.getAtributosConvencionales()[0] + pena.getIdDictamen() + "|"
								+ Constantes.getAtributosConvencionales()[1] + ids,
						Optional.of(pena));
				idsActualizados.add(id);
			} else {
				throw new CatalogoException(ErroresEnum.PENA_DOCUMENTO_NOT_FOUND);
			}
		}
		return idsActualizados;
	}

}
