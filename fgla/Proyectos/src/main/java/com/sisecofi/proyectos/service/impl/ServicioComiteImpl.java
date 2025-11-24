package com.sisecofi.proyectos.service.impl;

import com.sisecofi.libreria.comunes.dto.ResponseGeneric;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatAfectacion;
import com.sisecofi.libreria.comunes.model.catalogo.CatClasificacionSesion;
import com.sisecofi.libreria.comunes.model.catalogo.CatSesion;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoOtrosDocumentosComiteModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoPlantillaComiteModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.AsociasionComitePlantillaModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ComiteProyectoModel;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.proyectos.dto.*;
import com.sisecofi.proyectos.microservicio.CatalogoMicroservicio;
import com.sisecofi.proyectos.microservicio.ContratoMicroservicio;
import com.sisecofi.proyectos.model.*;
import com.sisecofi.proyectos.repository.*;
import com.sisecofi.proyectos.service.PistaService;
import com.sisecofi.proyectos.service.ServicioComite;
import com.sisecofi.proyectos.service.ServicioProyecto;
import com.sisecofi.proyectos.util.Constantes;
import com.sisecofi.proyectos.util.consumer.PistasInformacionComiteConsumer;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.exception.ProyectoException;
import com.sisecofi.proyectos.util.validator.ComiteProyectoValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import com.sisecofi.libreria.comunes.repository.ArchivoOtrosDocumentosComiteRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoPlantillaComiteRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ServicioComiteImpl implements ServicioComite {

	private final ComiteRepository comiteRepository;
	private final ProyectoRepository proyectoRepository;
	private final AfectacionComiteRepository afectacionComiteRepository;
	private final ComiteProyectoValidator comiteProyectoValidator;
	private final AsociacionComiteRepository asociacionComiteRepository;
	private final ArchivoPlantillaComiteRepository archivoPlantillaComiteRepository;
	private final ArchivoOtrosDocumentosComiteRepository archivoOtrosDocumentosComiteRepository;
	private final PistaService pistaService;
	private final CatalogoMicroservicio catalogoMicroservicio;
	private final ContratoMicroservicio contratoMicroservicio;
	private final PistasInformacionComiteConsumer pistasInformacionComiteConsumer;
	private final ServicioProyecto servicioProyecto;

	@Override
	public ResponseComite obtenerDetalleComite(Integer idComiteProyecto) {
		log.info(Constantes.LOG_CUERPO_DE_PETICION, idComiteProyecto);

		ComiteProyectoModel comiteProyecto = comiteRepository.findByIdComiteProyecto(idComiteProyecto)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.COMITE_PROYECTO_NO_ENCONTRADO));
		Integer contratoConvenioId = comiteProyecto.getIdContratoConvenio();
		Integer idContrato = comiteProyecto.getIdContrato();

		Integer comiteId = comiteProyecto.getIdComite();
		Integer tipoMondedaId = comiteProyecto.getIdTipoMoneda();

		BaseCatalogoModel comiteInformacion = catalogoMicroservicio
				.obtenerInformacionCatalogoId(CatalogosComunes.COMITE.getIdCatalogo(), comiteId);

		BaseCatalogoModel contratoConvenioInformacion = catalogoMicroservicio
				.obtenerInformacionCatalogoId(CatalogosComunes.CONTRATO_CONVENIO.getIdCatalogo(), contratoConvenioId);
		BaseCatalogoModel tipoMonedaInformacion = buscarCatalogoTipoMoneda(tipoMondedaId);

		if (comiteInformacion == null || contratoConvenioInformacion == null) {
			throw new ProyectoException(ErroresEnum.CATALOGO_NO_ENCONTRADO);
		}

		try {
			List<Integer> afectacionComiteModel = afectacionComiteRepository
					.findAllIdsByIdComiteProyecto(idComiteProyecto);

			AsociasionComitePlantillaModel asociasionComitePlantillaModel = asociacionComiteRepository
					.findByIdComiteProyectoAndEstatusTrue(idComiteProyecto).orElse(null);
			log.info("si hay asociaciones: {}",asociasionComitePlantillaModel != null);
			ResponseComite responseComite = new ResponseComite();
			responseComite.setInformacionComiteProyecto(comiteProyecto);
			responseComite.setComite(comiteInformacion);

			if (idContrato != null) {
				ContratoDto contratoDto = contratoMicroservicio.obtenerContatoPorId(idContrato.longValue());
				responseComite.setContrato(contratoDto);
			}

			responseComite.setContratoConvenio(contratoConvenioInformacion);
			if (tipoMonedaInformacion != null) {
				responseComite.setTipoMoneda(tipoMonedaInformacion);
			}
			responseComite.setIdsAfectacion(afectacionComiteModel);
			if (asociasionComitePlantillaModel != null) {
log.info("si hay asociaciones: {}",asociasionComitePlantillaModel != null);
				responseComite.setAsociasiones(asociasionComitePlantillaModel);
				Integer idAsociacion = asociasionComitePlantillaModel.getIdAsociacionComitePlantilla();
				List<ArchivoPlantillaComiteModel> archivoPlantillaComite = archivoPlantillaComiteRepository
						.findByIdAsociacionComiteProyectoAndEstatusTrue(idAsociacion);
				
				List<ArchivoOtrosDocumentosComiteModel> archivosOtrosDocumentos = archivoOtrosDocumentosComiteRepository
						.findByIdComiteProyectoAndEstatusTrue(idComiteProyecto);
				
				responseComite.setInformacionArchivos(archivoPlantillaComite);
				responseComite.setInformacionArchivosOtrosDocumentos(archivosOtrosDocumentos);
				
			}



			// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


			// TipoSeccionPista.PROYECTO_DATOS_INFORMACION_COMITE.getIdSeccionPista(),


			// TipoMovPista.CONSULTA_REGISTRO.getClave(), Optional.empty());

			return responseComite;

		} catch (Exception e) {
			log.info(Constantes.ERROR_DETALLE_C);
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public ComiteProyectoModel guardarComiteProyecto(ComiteProyectoDto comiteProyectoDto) {
		log.info(Constantes.LOG_CUERPO_DE_PETICION, comiteProyectoDto);

		comiteProyectoValidator.fechaValidacion(comiteProyectoDto.getFechaSesion());

		comiteProyectoValidator.decimalesValidacion(comiteProyectoDto.getMonto(), comiteProyectoDto.getTipoCambio(),
				comiteProyectoDto.getMontoAutorizado());

		ProyectoModel proyecto = proyectoRepository.findByIdProyectoAndEstatus(comiteProyectoDto.getIdProyecto(), true)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.PROYECTO_NO_ENCONTRADO));

		try {
			ComiteProyectoModel comiteProyectoModel = new ComiteProyectoModel();
			comiteProyectoModel.setIdComiteProyecto(comiteProyectoDto.getIdComiteProyecto());
			comiteProyectoModel.setProyectoModel(proyecto);
			comiteProyectoModel.setIdProyecto(comiteProyectoDto.getIdProyecto());
			comiteProyectoModel.setIdComite(comiteProyectoDto.getIdComite());
			comiteProyectoModel.setIdTipoMoneda(comiteProyectoDto.getIdTipoMoneda());

			comiteProyectoModel.setIdSesionClasificacion(comiteProyectoDto.getIdSesionClasificacion());
			comiteProyectoModel.setIdSesionNumero(comiteProyectoDto.getIdSesionNumero());

			if (comiteProyectoDto.getIdContratoConvenio() != null) {
				comiteProyectoModel.setIdContratoConvenio(comiteProyectoDto.getIdContratoConvenio());
			}

			if (comiteProyectoDto.getIdContrato() != null) {
				comiteProyectoModel.setIdContrato(comiteProyectoDto.getIdContrato());
			}

			if (comiteProyectoDto.getMonto() != null) {
				comiteProyectoModel.setMonto(comiteProyectoDto.getMonto());
			}
			if (comiteProyectoDto.getMontoAutorizado() != null) {
				comiteProyectoModel.setMontoAutorizado(comiteProyectoDto.getMontoAutorizado());
			}
			if (comiteProyectoDto.getTipoCambio() != null) {
				comiteProyectoModel.setTipoCambio(comiteProyectoDto.getTipoCambio());
			}

			if (comiteProyectoDto.getComentarios() != null) {
				comiteProyectoModel.setComentarios(comiteProyectoDto.getComentarios());
			}
			if (comiteProyectoDto.getAcuerdo() != null) {
				comiteProyectoModel.setAcuerdo(comiteProyectoDto.getAcuerdo());
			}
			if (comiteProyectoDto.getVigencia() != null) {
				comiteProyectoModel.setVigencia(comiteProyectoDto.getVigencia());
			}
			comiteProyectoModel.setFechaSesion(comiteProyectoDto.getFechaSesion());

			comiteProyectoModel.setEstatus(true);
			comiteRepository.save(comiteProyectoModel);

			Integer idComiteProyecto = comiteProyectoModel.getIdComiteProyecto();

			ComiteProyectoModel comiteProyectoGuardado = comiteRepository.save(comiteProyectoModel);

			guardarAfectacionComiteInfo(comiteProyectoDto.getIdsAfectacion(), idComiteProyecto);

			guardarAsociacion(idComiteProyecto, comiteProyectoDto.getIdPlantilla());

			ResponseGeneric<ComiteProyectoModel> data = ResponseGeneric.<ComiteProyectoModel>builder().build();

			data.setData(comiteProyectoGuardado);
			data.setMsj(ErroresEnum.COMITE_PROYECTO_CREADO);

			servicioProyecto.actualizarUltimaModificacion(comiteProyectoGuardado.getIdProyecto());

			String movimineto = pistasInformacionComiteConsumer.movimientoContratoModel(comiteProyectoModel);

			// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),

			// TipoSeccionPista.PROYECTO_DATOS_INFORMACION_COMITE.getIdSeccionPista(), movimineto,

			// Optional.empty());

			return comiteProyectoGuardado;
		} catch (Exception e) {
			log.info(Constantes.ERROR);
			throw new ProyectoException(ErroresEnum.ERROR_AL_GUARDAR);
		}
	}

	@Override
	public InformacionComiteDto actualizarComiteProyecto(InformacionComiteDto informacionComiteDto) {
		log.info(Constantes.LOG_CUERPO_DE_PETICION, informacionComiteDto);

		comiteProyectoValidator.fechaValidacion(informacionComiteDto.getFechaSesion());

		comiteProyectoValidator.decimalesValidacion(informacionComiteDto.getMonto(),
				informacionComiteDto.getTipoCambio(), informacionComiteDto.getMontoAutorizado());

		Integer idComiteProyecto = informacionComiteDto.getIdComiteProyecto();
		ComiteProyectoModel comiteProyectoDesdeDb = comiteRepository.findByIdComiteProyecto(idComiteProyecto)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.COMITE_PROYECTO_NO_ENCONTRADO));

		try {
			comiteProyectoDesdeDb.setAcuerdo(informacionComiteDto.getAcuerdo());
			comiteProyectoDesdeDb.setVigencia(informacionComiteDto.getVigencia());
			comiteProyectoDesdeDb.setMontoAutorizado(informacionComiteDto.getMontoAutorizado());
			comiteProyectoDesdeDb.setMonto(informacionComiteDto.getMonto());
			comiteProyectoDesdeDb.setIdSesionNumero(informacionComiteDto.getIdSesionNumero());
			comiteProyectoDesdeDb.setIdSesionClasificacion(informacionComiteDto.getIdSesionClasificacion());
			comiteProyectoDesdeDb.setTipoCambio(informacionComiteDto.getTipoCambio());
			comiteProyectoDesdeDb.setIdTipoMoneda(informacionComiteDto.getIdTipoMoneda());
			comiteProyectoDesdeDb.setIdComite(informacionComiteDto.getIdComite());
			comiteProyectoDesdeDb.setIdContratoConvenio(informacionComiteDto.getIdContratoConvenio());
			comiteProyectoDesdeDb.setIdContrato(informacionComiteDto.getIdContrato());
			comiteProyectoDesdeDb.setComentarios(informacionComiteDto.getComentarios());
			Integer idPlantillaVigente = informacionComiteDto.getIdPlantilla();
			actualizarAsociacion(idComiteProyecto, idPlantillaVigente);

			comiteRepository.save(comiteProyectoDesdeDb);

			List<AfectacionComiteModel> afectacionComiteList = afectacionComiteRepository
					.findAllByIdComiteProyecto(idComiteProyecto);

			for (AfectacionComiteModel afectacionComite : afectacionComiteList) {
				if (!informacionComiteDto.getIdsAfectacion().contains(afectacionComite.getIdAfectacionComite())) {
					afectacionComiteRepository.deleteById(afectacionComite.getIdAfectacionComite());
				}
			}
			guardarAfectacionComiteInfo(informacionComiteDto.getIdsAfectacion(),
					informacionComiteDto.getIdComiteProyecto());

			ComiteProyectoModel comiteProyectoActualizado = comiteRepository.findByIdComiteProyecto(idComiteProyecto)
					.orElseThrow(() -> new ProyectoException(ErroresEnum.COMITE_PROYECTO_NO_ENCONTRADO));

			List<Integer> afectacionComiteModel = afectacionComiteRepository
					.findAllIdsByIdComiteProyecto(idComiteProyecto);

			InformacionComiteDto informacionComiteResponse = new InformacionComiteDto();
			informacionComiteResponse.setIdComiteProyecto(comiteProyectoActualizado.getIdComiteProyecto());
			informacionComiteResponse.setIdContratoConvenio(comiteProyectoActualizado.getIdContratoConvenio());
			informacionComiteResponse.setFechaSesion(comiteProyectoActualizado.getFechaSesion());
			informacionComiteResponse.setIdComite(comiteProyectoActualizado.getIdComite());
			informacionComiteResponse.setMonto(comiteProyectoActualizado.getMonto());
			informacionComiteResponse.setAcuerdo(comiteProyectoDesdeDb.getAcuerdo());
			informacionComiteResponse.setVigencia(comiteProyectoActualizado.getVigencia());
			informacionComiteResponse.setMontoAutorizado(comiteProyectoActualizado.getMontoAutorizado());
			informacionComiteResponse.setIdsAfectacion(afectacionComiteModel);
			informacionComiteResponse.setEstatus(comiteProyectoActualizado.getEstatus());
			informacionComiteResponse.setIdPlantilla(idPlantillaVigente);
			informacionComiteResponse.setIdContrato(comiteProyectoActualizado.getIdContrato());
			servicioProyecto.actualizarUltimaModificacion(comiteProyectoDesdeDb.getIdProyecto());

			String movimineto = pistasInformacionComiteConsumer.movimientoContratoModel(comiteProyectoDesdeDb);

			// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),

			// TipoSeccionPista.PROYECTO_DATOS_INFORMACION_COMITE.getIdSeccionPista(), movimineto,

			// Optional.empty());

			return informacionComiteResponse;
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_ACTUALIZAR);
		}
	}

	@Override
	public String eliminarInformacionComite(Integer idComiteProyecto) {
		log.info(Constantes.LOG_CUERPO_DE_PETICION, idComiteProyecto);

		ComiteProyectoModel comiteProyecto = comiteRepository.findByIdComiteProyecto(idComiteProyecto)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.COMITE_PROYECTO_NO_ENCONTRADO));

		try {
			AsociasionComitePlantillaModel asociasionComitePlantillaModel = asociacionComiteRepository
					.findByIdComiteProyectoAndEstatusTrue(idComiteProyecto).orElse(null);

			List<ArchivoPlantillaComiteModel> archivos = new ArrayList<>();

			if (asociasionComitePlantillaModel != null) {
				archivos = archivoPlantillaComiteRepository.findByIdAsociacionComiteProyectoAndEstatusTrue(
						asociasionComitePlantillaModel.getIdAsociacionComitePlantilla());
			}

			servicioProyecto.actualizarUltimaModificacion(comiteProyecto.getIdProyecto());

			if (!archivos.isEmpty()) {
				log.info(Constantes.ERROR_DETALLE_C);
				return Constantes.ERROR_AL_ELIMINAR_COMITE;

			} else {
				comiteProyecto.setEstatus(false);
				comiteRepository.save(comiteProyecto);

				String movimiento = pistasInformacionComiteConsumer.movimientoContratoModel(comiteProyecto);

				// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.BORRA_REGISTRO.getId(),

				// TipoSeccionPista.PROYECTO_DATOS_INFORMACION_COMITE.getIdSeccionPista(), movimiento,

				// Optional.empty());

				return Constantes.COMITE_ELIMINADO;
			}

		} catch (Exception e) {
			log.info(Constantes.ERROR_DETALLE_C);
			throw new ProyectoException(ErroresEnum.ERROR_AL_ELIMINAR);
		}
	}

	public void imprimirDetallesResponseComite(List<ResponseComiteInfoReporte> responseComitesList) {
		if (responseComitesList == null || responseComitesList.isEmpty()) {
			log.info("La lista de ResponseComiteInfoReporte está vacía.");
			return;
		}

		for (ResponseComiteInfoReporte response : responseComitesList) {
			log.info("=== Detalles de ResponseComiteInfoReporte ===");

			// InformacionComiteDto
			if (response.getInformacionComite() != null) {
				log.info("Informacion Comite:");
				log.info("  Acuerdo: " + response.getInformacionComite().getAcuerdo());
				log.info("  Monto: " + response.getInformacionComite().getMonto());
				log.info("  Monto Autorizado: " + response.getInformacionComite().getMontoAutorizado());
				log.info("  Tipo Cambio: " + response.getInformacionComite().getTipoCambio());
				log.info("  Comentarios: " + response.getInformacionComite().getComentarios());
				log.info("  Fecha Sesión: " + response.getInformacionComite().getFechaSesion());
				log.info("  Vigencia: " + response.getInformacionComite().getVigencia());
			} else {
				log.info("Informacion Comite: null");
			}

			// Archivos asociados
			if (response.getInfomacionArchivos() != null && !response.getInfomacionArchivos().isEmpty()) {
				log.info("Archivos asociados:");
				for (ArchivoPlantillaComiteModel archivo : response.getInfomacionArchivos()) {
					log.info("  Archivo: " + archivo.getNombre() + ", Ruta: " + archivo.getRuta());
				}
			} else {
				log.info("Archivos asociados: vacíos o null.");
			}

		}
	}

	@Override
	public List<ResponseComiteInfoReporte> obtenerComitesReporteInfo(Integer idProyecto) {

		// Obtener los comités asociados al proyecto
		List<ComiteProyectoModel> comiteProyectoModels = comiteRepository.findByIdProyectoAndEstatusTrue(idProyecto)
				.orElse(null);

		log.info("ID Proyecto: " + idProyecto);
		log.info("Comité Proyecto Models encontrados: "
				+ (comiteProyectoModels != null ? comiteProyectoModels.size() : 0));

		List<ResponseComiteInfoReporte> responseComitesList = new ArrayList<>();

		if (comiteProyectoModels != null) {
			for (ComiteProyectoModel informacionComite : comiteProyectoModels) {
				Integer idComiteProyecto = informacionComite.getIdComiteProyecto();

				BaseCatalogoModel baseCatalogoSesionClasificacion = buscarCatalogoSesionClasificacion(
						informacionComite.getIdSesionClasificacion());

				BaseCatalogoModel baseCatalogoSesionNumero = buscarCatalogoSesionNumero(
						informacionComite.getIdSesionNumero());

				List<AfectacionComiteModel> afectacionComiteList = afectacionComiteRepository
						.findAllByIdComiteProyecto(idComiteProyecto);

				List<String> nombresLista = obtenerNombresLista(afectacionComiteList);

				ResponseComiteInfoReporte responseComiteInfo = obtenerReponseComiteInfo(informacionComite, nombresLista,
						baseCatalogoSesionClasificacion, baseCatalogoSesionNumero);

				responseComitesList.add(responseComiteInfo);
				imprimirDetallesResponseComite(responseComitesList);
			}
		} else {
			log.info("No se encontraron ComiteProyectoModels para el ID Proyecto proporcionado.");
		}

		log.info("Total de ResponseComiteInfoReporte generados: " + responseComitesList.size());
		return responseComitesList;
	}

	@Override
	public List<ResponseComiteInfo> obtenerComiteInformacion(Integer idProyecto) {
		log.info("Inicio de metodo obtener_comite_info");

		List<ComiteProyectoModel> comiteProyectoModels = comiteRepository.findByIdProyectoAndEstatusTrue(idProyecto)
				.orElse(null);

		try {
			List<ResponseComiteInfo> response = new ArrayList<>();
			if (comiteProyectoModels != null) {
				response = comiteProyectoModels.stream()
						.map(comiteProyectoModel -> crearResponseComiteInfo(comiteProyectoModel,
								comiteProyectoModels.get(comiteProyectoModels.size() - 1).equals(comiteProyectoModel)))
						.toList();
			}

			return response;

		} catch (Exception e) {
			log.info(Constantes.ERROR_DETALLE_C);
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	private ResponseComiteInfo crearResponseComiteInfo(ComiteProyectoModel informacionComite, boolean idUltimo) {
		Integer idComite = informacionComite.getIdComite();
		Integer idComiteProyecto = informacionComite.getIdComiteProyecto();
		Integer idContratoConvenio = informacionComite.getIdContratoConvenio();

		BaseCatalogoModel baseCatalogoComite = catalogoMicroservicio
				.obtenerInformacionCatalogoId(CatalogosComunes.COMITE.getIdCatalogo(), idComite);
		BaseCatalogoModel baseCatalogoContratoConvenio = catalogoMicroservicio
				.obtenerInformacionCatalogoId(CatalogosComunes.CONTRATO_CONVENIO.getIdCatalogo(), idContratoConvenio);

		AsociasionComitePlantillaModel asociasionComitePlantillaModel = asociacionComiteRepository
				.findByIdComiteProyectoAndEstatusTrue(idComiteProyecto).orElse(null);

		List<Integer> afectacionComiteModel = afectacionComiteRepository.findAllIdsByIdComiteProyecto(idComiteProyecto);
		InformacionComiteDto informacionComiteDto;
		if (asociasionComitePlantillaModel != null) {
			informacionComiteDto = crearInformacionComiteDto(informacionComite, baseCatalogoContratoConvenio,
					afectacionComiteModel, idUltimo, asociasionComitePlantillaModel.getIdPlantillaVigente());
		} else {
			informacionComiteDto = crearInformacionComiteDto(informacionComite, baseCatalogoContratoConvenio,
					afectacionComiteModel, idUltimo, null);
		}

		List<ArchivoOtrosDocumentosComiteModel> archivosOtrosDocumentos = archivoOtrosDocumentosComiteRepository
				.findByIdComiteProyectoAndEstatusTrue(idComiteProyecto);

		ResponseComiteInfo responseComiteInfo = new ResponseComiteInfo();
		responseComiteInfo.setComite(baseCatalogoComite);
		responseComiteInfo.setInformacionComite(informacionComiteDto);

		if (!archivosOtrosDocumentos.isEmpty()) {
			responseComiteInfo.setInformacionArchivosOtrosDocumentos(archivosOtrosDocumentos);
		}

		if (asociasionComitePlantillaModel != null) {
			Integer idAsociacion = asociasionComitePlantillaModel.getIdAsociacionComitePlantilla();
			List<ArchivoPlantillaComiteModel> archivoPlantillaComite = archivoPlantillaComiteRepository
					.findByIdAsociacionComiteProyectoAndEstatusTrue(idAsociacion);
			responseComiteInfo.setInfomacionArchivos(archivoPlantillaComite);
		}

		return responseComiteInfo;
	}

	private InformacionComiteDto crearInformacionComiteDto(ComiteProyectoModel informacionComite,
			BaseCatalogoModel baseCatalogoContratoConvenio, List<Integer> afectacionComiteModel, boolean isUltimo,
			Integer idVigente) {
		InformacionComiteDto informacionComiteDto = new InformacionComiteDto();
		if (informacionComite.getTipoCambio() == null
				|| informacionComite.getTipoCambio().compareTo(BigDecimal.ZERO) == 0) {
			informacionComiteDto.setMonto(informacionComite.getMontoAutorizado());
		} else {
			informacionComiteDto
					.setMonto(informacionComite.getMontoAutorizado().multiply(informacionComite.getTipoCambio()));
		}
		informacionComiteDto.setMontoAutorizado(informacionComite.getMontoAutorizado());
		informacionComiteDto.setIdComite(informacionComite.getIdComite());
		informacionComiteDto.setIdComiteProyecto(informacionComite.getIdComiteProyecto());
		informacionComiteDto.setIdContratoConvenio(informacionComite.getIdContratoConvenio());
		informacionComiteDto.setVigencia(informacionComite.getVigencia());
		informacionComiteDto.setAcuerdo(informacionComite.getAcuerdo());
		informacionComiteDto.setEstatus(informacionComite.getEstatus());
		informacionComiteDto.setFechaSesion(informacionComite.getFechaSesion());
		informacionComiteDto.setIdContratoConvenio(informacionComite.getIdContratoConvenio());
		informacionComiteDto.setContratoConvenio(baseCatalogoContratoConvenio.getNombre());
		informacionComiteDto.setTipoCambio(informacionComite.getTipoCambio());
		informacionComiteDto.setIdSesionNumero(informacionComite.getIdSesionNumero());
		informacionComiteDto.setIdSesionClasificacion(informacionComite.getIdSesionClasificacion());
		informacionComiteDto.setComentarios(informacionComite.getComentarios());
		informacionComiteDto.setUltimoValor(isUltimo);
		informacionComiteDto.setIdsAfectacion(afectacionComiteModel);
		informacionComiteDto.setIdPlantilla(idVigente);
		informacionComite.setMonto(informacionComiteDto.getMonto());
		comiteRepository.save(informacionComite);
		return informacionComiteDto;
	}

	private void guardarAfectacionComiteInfo(List<Integer> ids, Integer idComiteProyecto) {
		try {

			if (!ids.isEmpty()) {
				for (Integer id : ids) {
					AfectacionComiteModel afectacionComiteModel = new AfectacionComiteModel();
					afectacionComiteModel.setIdAfectacion(id);
					afectacionComiteModel.setIdComiteProyecto(idComiteProyecto);
					afectacionComiteRepository.save(afectacionComiteModel);
				}
			}

		} catch (Exception e) {
			log.info(Constantes.ERROR);
			throw new ProyectoException(ErroresEnum.ERROR_AL_ACTUALIZAR);
		}
	}

	private static String convertirListaAString(List<String> lista) {
		return String.join(",", lista);
	}

	private void guardarAsociacion(Integer idComiteProyecto, Integer idPlantillaVigente) {
		try {
			AsociasionComitePlantillaModel asociasionComitePlantillaModel = new AsociasionComitePlantillaModel();

			asociasionComitePlantillaModel.setIdComiteProyecto(idComiteProyecto);
			asociasionComitePlantillaModel.setIdPlantillaVigente(idPlantillaVigente);
			asociasionComitePlantillaModel.setFechaAsignacion(LocalDateTime.now());
			asociasionComitePlantillaModel.setFechaModificacion(LocalDateTime.now());
			asociasionComitePlantillaModel.setEstatus(true);

			asociacionComiteRepository.save(asociasionComitePlantillaModel);

		} catch (Exception e) {
			log.info(Constantes.ERROR_DETALLE_C);
			throw new ProyectoException(ErroresEnum.ERROR_AL_GUARDAR);
		}
	}

	private void actualizarAsociacion(Integer idComiteProyecto, Integer idPlantillaVigente) {
		try {
			AsociasionComitePlantillaModel asociasionComitePlantillaModel = asociacionComiteRepository
					.findByIdComiteProyecto(idComiteProyecto).orElse(new AsociasionComitePlantillaModel());
			if (asociasionComitePlantillaModel != null) {
				log.info("entra: {}", "entra");
				asociasionComitePlantillaModel.setIdComiteProyecto(idComiteProyecto);
				asociasionComitePlantillaModel.setIdPlantillaVigente(idPlantillaVigente);
				asociasionComitePlantillaModel.setFechaModificacion(LocalDateTime.now());
				asociasionComitePlantillaModel.setEstatus(true);
				asociacionComiteRepository.save(asociasionComitePlantillaModel);
			}else {
				log.info("entra2: {}", "entra2");
				guardarAsociacion(idComiteProyecto, idPlantillaVigente);
			}
		} catch (Exception e) {
			log.error(Constantes.ERROR);
			log.info(Constantes.ERROR_DETALLE_C);
			throw new ProyectoException(ErroresEnum.ERROR_AL_GUARDAR);
		}
	}

	private BaseCatalogoModel buscarCatalogoTipoMoneda(Integer idTipoMoneda) {

		if (idTipoMoneda != null) {
			return catalogoMicroservicio.obtenerInformacionCatalogoId(CatalogosComunes.TIPO_MONEDA.getIdCatalogo(),
					idTipoMoneda);
		} else {
			return null;
		}
	}

	private CatSesion buscarCatalogoSesionNumero(Integer idSesionNumero) {

		if (idSesionNumero != null) {
			return catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
					CatalogosComunes.NUMERO_SESION.getIdCatalogo(), idSesionNumero, new CatSesion());
		} else {
			return null;
		}
	}

	private CatClasificacionSesion buscarCatalogoSesionClasificacion(Integer idSesionClasificacion) {

		if (idSesionClasificacion != null) {
			return catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
					CatalogosComunes.CLASIFICACION_SESION.getIdCatalogo(), idSesionClasificacion,
					new CatClasificacionSesion());
		} else {
			return null;
		}
	}

	private List<String> obtenerNombresLista(List<AfectacionComiteModel> afectacionComiteList) {
		List<String> nombresLista = new ArrayList<>();

		for (AfectacionComiteModel afectacionComiteModel : afectacionComiteList) {
			Integer idAfectacion = afectacionComiteModel.getIdAfectacion();

			CatAfectacion baseCatalogoModel = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
					CatalogosComunes.AFECTACION.getIdCatalogo(), idAfectacion, new CatAfectacion());

			nombresLista.add(baseCatalogoModel.getNombre());
		}
		return nombresLista;
	}

	private ResponseComiteInfoReporte obtenerReponseComiteInfo(ComiteProyectoModel informacionComite,
			List<String> nombresLista, BaseCatalogoModel baseCatalogoSesionClasificacion,
			BaseCatalogoModel baseCatalogoSesionNumero) {
		InformacionComiteDto informacionComiteDto = new InformacionComiteDto();
		informacionComiteDto
				.setMonto(informacionComite.getMonto() != null ? informacionComiteDto.getMonto() : BigDecimal.ZERO);
		informacionComiteDto.setMontoAutorizado(
				informacionComite.getMontoAutorizado() != null ? informacionComite.getMontoAutorizado()
						: BigDecimal.ZERO);
		informacionComiteDto.setIdComiteProyecto(informacionComite.getIdComiteProyecto());
		informacionComiteDto.setIdContratoConvenio(informacionComite.getIdContratoConvenio());
		informacionComiteDto.setVigencia(informacionComite.getVigencia());
		informacionComiteDto.setAcuerdo(informacionComite.getAcuerdo());
		informacionComiteDto.setFechaSesion(informacionComite.getFechaSesion());
		informacionComiteDto.setComentarios(informacionComite.getComentarios());
		informacionComiteDto.setTipoCambio(
				informacionComite.getTipoCambio() != null ? informacionComite.getTipoCambio() : BigDecimal.valueOf(0));
		informacionComiteDto.setIdProyecto(informacionComite.getIdProyecto());
		informacionComiteDto.setNombreCortoProyecto(informacionComite.getProyectoModel().getNombreCorto());

		ResponseComiteInfoReporte responseComiteInfo = new ResponseComiteInfoReporte();

		responseComiteInfo.setComite(informacionComite.getCatComite());
		if (baseCatalogoSesionClasificacion != null) {
			responseComiteInfo.setSesionClasificacion(baseCatalogoSesionClasificacion.getNombre());
		}
		if (baseCatalogoSesionNumero != null) {
			responseComiteInfo.setSesionNumero(baseCatalogoSesionNumero.getNombre());
		}

		responseComiteInfo.setInformacionComite(informacionComiteDto);
		responseComiteInfo.setContratoConvenio("" + informacionComite.getCatContratoConvenio().getNombre());

		if (informacionComite.getIdContrato() != null) {
			ContratoDto contratoDto = contratoMicroservicio
					.obtenerContatoPorId(informacionComite.getIdContrato().longValue());
			responseComiteInfo.setContrato(contratoDto.getNombreContrato());
		}

		if (informacionComite.getIdTipoMoneda() != null) {
			BaseCatalogoModel baseCatalogoTipoMoneda = buscarCatalogoTipoMoneda(informacionComite.getIdTipoMoneda());
			assert baseCatalogoTipoMoneda != null;
			responseComiteInfo.setTipoMoneda(informacionComite.getCatTipoMoneda().getNombre() != null
					? informacionComite.getCatTipoMoneda().getNombre()
					: "");
		}

		String afectaciones = convertirListaAString(nombresLista);
		responseComiteInfo.setAfectacion(afectaciones);

		return responseComiteInfo;
	}

}
