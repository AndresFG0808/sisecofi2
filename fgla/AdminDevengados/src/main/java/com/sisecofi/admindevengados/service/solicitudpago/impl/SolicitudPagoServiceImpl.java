package com.sisecofi.admindevengados.service.solicitudpago.impl;

import com.sisecofi.admindevengados.dto.BanderaReponseDto;
import com.sisecofi.admindevengados.dto.SolicitudFacturaUpdateDto;
import com.sisecofi.admindevengados.dto.solicitudpago.*;
import com.sisecofi.admindevengados.microservicio.*;
import com.sisecofi.admindevengados.model.SolicitudFacturaModel;
import com.sisecofi.admindevengados.repository.DictamenRepository;
import com.sisecofi.admindevengados.repository.facturas.FacturasRepository;
import com.sisecofi.admindevengados.repository.facturas.NotasCreditoRepository;
import com.sisecofi.admindevengados.repository.gestion_documental.CarpetaPlantillaDictamenRepository;
import com.sisecofi.admindevengados.repository.ProveedorRepository;
import com.sisecofi.admindevengados.repository.SolicitudFacturaRepository;
import com.sisecofi.admindevengados.repository.contrato.ParticipantesContratoRepository;
import com.sisecofi.admindevengados.repository.solicitudpago.ReferenciaPagoRepository;
import com.sisecofi.admindevengados.repository.solicitudpago.SolicitudPagoRepository;
import com.sisecofi.admindevengados.service.CatalogoService;
import com.sisecofi.admindevengados.service.DictamenService;
import com.sisecofi.admindevengados.service.PistaService;
import com.sisecofi.admindevengados.service.ProformaService;
import com.sisecofi.admindevengados.service.facturas.FacturaService;
import com.sisecofi.admindevengados.service.impl.NexusImpl;
import com.sisecofi.admindevengados.service.solicitudpago.SolicitudPagoService;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.admindevengados.util.consumer.*;
import com.sisecofi.admindevengados.util.enums.ErroresEnum;
import com.sisecofi.admindevengados.util.exception.CatalogoException;
import com.sisecofi.admindevengados.util.exception.DevengadosException;
import com.sisecofi.libreria.comunes.dto.PapeleraDto;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoDto;
import com.sisecofi.libreria.comunes.dto.contrato.DatosGeneralesResponseDto;
import com.sisecofi.libreria.comunes.dto.dictamen.FacturasInformacion;
import com.sisecofi.libreria.comunes.dto.dictamen.dictamenDto;
import com.sisecofi.libreria.comunes.dto.plantillador.ContenidoPlantilladorPdfReponseDto;
import com.sisecofi.libreria.comunes.dto.plantillador.NotificacionDTO;
import com.sisecofi.libreria.comunes.dto.proyecto.FichaTecnicaResponse;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatAcuerdoPago;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusDictamen;
import com.sisecofi.libreria.comunes.model.contratos.ParticipantesAdministracionModel;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.model.dictamenes.ReferenciaPagoModel;
import com.sisecofi.libreria.comunes.model.dictamenes.SolicitudPagoModel;
import com.sisecofi.libreria.comunes.model.dictamenes.factura.FacturaModel;
import com.sisecofi.libreria.comunes.model.dictamenes.factura.NotaCreditoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoPlantillaDictamenModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.CarpetaPlantillaDictamenModel;
import com.sisecofi.libreria.comunes.model.plantillador.PlantilladorModel;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoPlantillaDictamenRepository;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SolicitudPagoServiceImpl implements SolicitudPagoService {

	private final SolicitudPagoRepository solicitudPagoRepository;
	private final ReferenciaPagoRepository referenciaPagoRepository;
	private final CatalogoMicroservicio catalogoMicroservicio;
	private final FacturasRepository facturasRepository;
	private final NexusImpl nexusImpl;
	private final ContratoMicoservicio contratoMicoservicio;
	private final DictamenService dictamenService;
	private final ProveedorRepository proveedorRepository;
	private final PlantilladorMicriservicio plantilladorMicriservicio;
	private final DictamenRepository dictamenRepository;
	private final CatalogoService catalogoService;
	private final NotasCreditoRepository notasCreditoRepository;
	private final SolicitudFacturaRepository solicitudFacturaRepository;
	private final ProyectoMicroservicio proyectoMicroservicio;
	private final ProformaService proformaService;
	private final ArchivoPlantillaDictamenRepository archivoRepo;
	private final CarpetaPlantillaDictamenRepository carpetaPlantillaDictamenRepository;
	private final PistaService pistaService;
	private final SolicitudPagoPistasConsumer solicitudPagoPistasConsumer;
	private static final Logger logger = LoggerFactory.getLogger(SolicitudPagoServiceImpl.class);
	private final PapeleraServico papeleraServicio;
	private final ParticipantesContratoRepository participantesContratoRepository;
	private final FacturaService facturaService;

	private static final String CONEXION = "Connection is closed";
	private static final String ELIMINACION = "No se pudo eliminar el archivo temporal: {}";

	private LocalDateTime obtenerFechaYHoraActual() {
		ZonedDateTime utcTime = ZonedDateTime.now(ZoneOffset.UTC);
		ZoneId zoneId = ZoneId.of("America/Mexico_City");
		return utcTime.withZoneSameInstant(zoneId).toLocalDateTime();
	}

	public Dictamen actualizarUltimaModificacion(Long dictamenId) {
		Dictamen dictamen = dictamenRepository.findByIdDictamen(dictamenId)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));

		dictamen.setUltimaModificacion(dictamenService.ultimaModificacionGeneral());
		return dictamenRepository.save(dictamen);
	}

	@Override
	public SolicitudPagoResponseDto guardarSolicitudPago(SolicitudPagoDto solicitudPagoDto) {

		Dictamen dictamen = dictamenRepository.findByIdDictamen(solicitudPagoDto.getIdDictamen()).orElse(null);

		if (dictamen == null) {
			throw new DevengadosException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND);
		}

		Long idContrato = dictamen.getIdContrato();
		Boolean convenio = catalogoService.aplicaCC(idContrato);

		SolicitudPagoModel solicitudPagoModel = new SolicitudPagoModel();

		solicitudPagoModel.setOficioSoliciturPago(solicitudPagoDto.getOficioSolicitudPago());
		solicitudPagoModel.setFechaSolicitud(solicitudPagoDto.getFechaSolicitud());
		solicitudPagoModel.setDictamenId(dictamen.getIdDictamen());
		solicitudPagoModel.setRutaSolicitudPago("");
		solicitudPagoModel.setEstatus(true);
		solicitudPagoModel.setAplicaConvenioColaboracion(convenio);

		solicitudPagoModel = solicitudPagoRepository.save(solicitudPagoModel);
			guardarArchivoSolicitudPago(solicitudPagoModel.getIdSolicitudPago(), solicitudPagoDto);
		solicitudPagoModel.setDocumentoGenerado(solicitudPagoDto.getDocumentoGenerado());
		solicitudPagoRepository.save(solicitudPagoModel);

		List<FacturaModel> facturaModelList = facturasRepository.findByIdDictamen(dictamen.getIdDictamen());
		List<FacturaModel> facturasNoCanceladas = new ArrayList<>();
		if (!facturaModelList.isEmpty()) {
			for (FacturaModel facturaModel : facturaModelList) {
				if (!facturaModel.getCatEstatusFactura().getNombre().equals(Constantes.CANCELADO)) {
					facturasNoCanceladas.add(facturaModel);
				}
			}
		}

		if (facturasNoCanceladas.isEmpty()) {
			throw new DevengadosException(ErroresEnum.ERROR_FACTURAS_NO_ENCONTRADAS);
		}

		if (Boolean.TRUE.equals(solicitudPagoDto.getSolicitudPago())) {
			List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
					CatalogosComunes.ESTATUS_DICTAMEN.getIdCatalogo(), Constantes.ESTATUS_SOLICITUD_PAGO);
			if (lista.isEmpty()) {
				throw new DevengadosException(ErroresEnum.ERROR_ESTATUS_SOLICITUD_NO_ENCONTRADO);
			}
			Integer idEstatus = lista.get(0).getPrimaryKey();

			dictamen.setIdEstatusDictamen(idEstatus);
			dictamenRepository.save(dictamen);
		}
		SolicitudPagoResponseDto solicitudPagoResponseDto = new SolicitudPagoResponseDto();
		solicitudPagoResponseDto.setIdSolicitudPago(solicitudPagoModel.getIdSolicitudPago());
		solicitudPagoResponseDto.setOficioSoliciturPago(solicitudPagoModel.getOficioSoliciturPago());
		solicitudPagoResponseDto.setFechaSolicitud(solicitudPagoModel.getFechaSolicitud());

		solicitudPagoResponseDto.setRutaSolicitudPago(solicitudPagoModel.getRutaSolicitudPago());
		solicitudPagoResponseDto.setDocumentoGenerado(solicitudPagoModel.getDocumentoGenerado());

		solicitudPagoResponseDto.setAplicaConvenioColaboracion(convenio);
		actualizarUltimaModificacion(dictamen.getIdDictamen());
		String movimiento = solicitudPagoPistasConsumer.movimientoSolicitudPagol(solicitudPagoModel);

		// pistaService.guardarPista(ModuloPista.ADMIN_DEVENGADOS.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),

		// TipoSeccionPista.SOLICITUD_PAGO.getIdSeccionPista(), movimiento, Optional.empty());

		return solicitudPagoResponseDto;

	}

	@Override
	public String editarEstatusSolicitudPago(EstatusSolicitudPagoDto solicitudPagoDto) {

		if (Boolean.TRUE.equals(solicitudPagoDto.getEstatusSolicitud())) {

			List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
					CatalogosComunes.ESTATUS_DICTAMEN.getIdCatalogo(), Constantes.ESTATUS_SOLICITUD_PAGO);

			List<BaseCatalogoModel> listaFactura = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
					CatalogosComunes.ESTATUS_FACTURA.getIdCatalogo(), Constantes.ESTATUS_SOLICITUD_PAGO);

			if (lista.isEmpty()) {
				throw new DevengadosException(ErroresEnum.ERROR_ESTATUS_SOLICITUD_NO_ENCONTRADO);
			}

			Integer idEstatus = lista.get(0).getPrimaryKey();
			Dictamen disctamen = dictamenRepository.findByIdDictamen(solicitudPagoDto.getIdDictamen().getIdDictamen()).orElse(null);
			assert disctamen != null;
			List<FacturaModel> facturas = disctamen.getFacturaModel();
			List<FacturaModel> facturasNoCanceladas = new ArrayList<>();
			if (!facturas.isEmpty()) {
				for (FacturaModel facturaModel : facturas) {
					if (!facturaModel.getCatEstatusFactura().getNombre().equals("Cancelado")) {
						facturaModel.setIdEstatusFactura(Long.parseLong("" + listaFactura.get(0).getPrimaryKey()));
						facturasNoCanceladas.add(facturaModel);
					}
				}
			}
			disctamen.setIdEstatusDictamen(idEstatus);
			disctamen.setUltimaModificacion(dictamenService.ultimaModificacionGeneral());
			dictamenRepository.save(disctamen);
			facturasRepository.saveAll(facturasNoCanceladas);



			// pistaService.guardarPista(ModuloPista.ADMIN_DEVENGADOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),


			// TipoSeccionPista.SOLICITUD_PAGO.getIdSeccionPista(),


			// "Id dictamen:" + solicitudPagoDto.getIdDictamen(), Optional.empty());

		} else {
			return "estatus solicitud debe estar en true";
		}
		return "OK";
	}

	@Override
	public String guardarReferenciaPago(ReferenciaPagoDto referenciaPagoDto) {
		boolean exists = dictamenRepository.existsByIdDictamen(referenciaPagoDto.getDictamenId().getIdDictamen());
		if (!exists) {
			throw new DevengadosException(ErroresEnum.MSJ_NOT_FOUND_DICTAMEN);
		}
		Long idSolicitudPago = referenciaPagoDto.getIdSolicitudPago();

		for (InfoReferenciaPagoDto referenciaPago : referenciaPagoDto.getInfoReferenciaPagoDto()) {
			Long idFactua = referenciaPago.getIdFactura();
			validarRegistroFactura(idFactua, idSolicitudPago);
			String archivo = referenciaPago.getArchivoFicha();

			ReferenciaPagoModel referenciaPagoModel = guardarReferenciaPagoModel(referenciaPago, idSolicitudPago,
					referenciaPagoDto.getDictamenId().getIdDictamen());

			if (!referenciaPago.getNombreArchivo().endsWith(".pdf")) {
				throw new DevengadosException(ErroresEnum.ERROR_FORMATO_PDF);
			}

			if (archivo != null) {
				guardarArchivosReferenciaPago(referenciaPagoModel.getIdReferenciaPago(), archivo,
						referenciaPago.getNombreArchivo(), referenciaPagoModel);
			}
			actualizarUltimaModificacion(referenciaPagoDto.getDictamenId().getIdDictamen());
			String movimiento = solicitudPagoPistasConsumer.movimientoReferenciaPago(referenciaPagoModel);

			// pistaService.guardarPista(ModuloPista.ADMIN_DEVENGADOS.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),

			// TipoSeccionPista.SOLICITUD_PAGO.getIdSeccionPista(), movimiento, Optional.empty());
		}
		return "OK";
	}

	@Override
	public String editarSolicitudPago(EditarSolicitudPagoDto solicitudPagoDto) {
		SolicitudPagoModel solicitudPago = solicitudPagoRepository
				.findByIdSolicitudPago(solicitudPagoDto.getIdSolicitudPago());
		solicitudPago.setFechaSolicitud(solicitudPagoDto.getFechaSolicitud());
		solicitudPago.setOficioSoliciturPago(solicitudPagoDto.getOficioSolicitudPago());
		solicitudPago.setDocumentoGenerado(solicitudPagoDto.getDocumentoGenerado());

		String archivo = solicitudPagoDto.getArchivoPdf();
		if (archivo != null) {
			SolicitudPagoDto solicitudPagoDto1 = new SolicitudPagoDto();
			solicitudPagoDto1.setFechaSolicitud(solicitudPagoDto.getFechaSolicitud());
			solicitudPagoDto1.setOficioSolicitudPago(solicitudPagoDto.getOficioSolicitudPago());
			solicitudPagoDto1.setIdSolicitudPago(solicitudPagoDto.getIdSolicitudPago());
			solicitudPagoDto1.setIdDictamen(solicitudPagoDto.getIdDictamen().getIdDictamen());
			solicitudPagoDto1.setArchivoPdf(solicitudPagoDto.getArchivoPdf());
			solicitudPagoDto1.setNombreArchivo(solicitudPagoDto.getNombreArchivo());
			actualizarUltimaModificacion(solicitudPagoDto.getIdDictamen().getIdDictamen());
			guardarArchivoSolicitudPago(solicitudPagoDto.getIdSolicitudPago(), solicitudPagoDto1);
		}
		solicitudPagoRepository.save(solicitudPago);
		String movimiento = solicitudPagoPistasConsumer.movimientoSolicitudPagol(solicitudPago);

		// pistaService.guardarPista(ModuloPista.ADMIN_DEVENGADOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),

		// TipoSeccionPista.SOLICITUD_PAGO.getIdSeccionPista(), movimiento, Optional.empty());

		return "OK";
	}

	@Override
	public String editarReferenciaPago(ReferenciaPagoDto referenciaPagoDto) {
		log.info("referenciaPagoDto: {}", referenciaPagoDto.getInfoReferenciaPagoDto().get(0).getPagadoNAFIN());
		// Validar estatus factura
		List<BaseCatalogoModel> listaFactura = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.ESTATUS_FACTURA.getIdCatalogo(), Constantes.ESTATUS_PAGADO);

		if (listaFactura.isEmpty()) {
			throw new DevengadosException(ErroresEnum.ERROR_ESTATUS_SOLICITUD_NO_ENCONTRADO);
		}
		actualizarReferenciaPago(referenciaPagoDto.getInfoReferenciaPagoDto(), referenciaPagoDto);
		// Actualizar dictamen y facturas si está pagado
		actualizarFacturas(referenciaPagoDto, listaFactura);
		return "OK";
	}

	@Override
	public ContenidoPlantilladorPdfReponseDto obtenerPlantillaBase(RequestPlantillaBaseDto request) {

		ContenidoPlantilladorPdfReponseDto contenidoPlantillador = new ContenidoPlantilladorPdfReponseDto();
		try {

			NotificacionDTO notificacionDTO2 = generarNotificacionDTO(request,
					request.getTipoDocumento().toUpperCase());

			byte[] word = plantilladorMicriservicio.obtenerArchivoNotificacionWord(notificacionDTO2);

			if (word == null) {
				throw new DevengadosException(ErroresEnum.ERROR_SERVICIO_INTERNO);
			}

			log.info("-------------> Documento generado");
			String response = Base64.getEncoder().encodeToString(word);
			contenidoPlantillador.setDocumentoBase64(response);



			// pistaService.guardarPista(ModuloPista.ADMIN_DEVENGADOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


			// TipoSeccionPista.SOLICITUD_PAGO.getIdSeccionPista(), TipoMovPista.CONSULTA_REGISTRO.getClave(),


			// Optional.empty());

			return contenidoPlantillador;
		} catch (DevengadosException e) {
			throw e;
		} catch (Exception e) {
			throw new DevengadosException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}
	}

	@Override
	public List<PlantilladorModel> obtenerPlantillas() {
		try {
			Integer idTipoPlantillador = 1;

			List<PlantilladorModel> plantilladorModels = plantilladorMicriservicio
					.obtenerPlantillador(idTipoPlantillador);



			// pistaService.guardarPista(ModuloPista.ADMIN_DEVENGADOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


			// TipoSeccionPista.SOLICITUD_PAGO.getIdSeccionPista(), TipoMovPista.CONSULTA_REGISTRO.getClave(),


			// Optional.empty());

			if (plantilladorModels != null) {
				return plantilladorModels;
			}
			return List.of();

		} catch (Exception e) {
			throw new DevengadosException(ErroresEnum.ERROR_GUARDAR_PISTA);
		}
	}

	@Override
	public Boolean validarArchivoReferenciaPago(Long idReferenciaPago) {

		ReferenciaPagoModel referenciaPagoModel = referenciaPagoRepository
				.findByIdReferenciaPagoAndEstatusTrue(idReferenciaPago);

		if (referenciaPagoModel != null) {
			String ruta = referenciaPagoModel.getRutaFichaNAFIN();

			return !(ruta.isBlank() && ruta.isEmpty());
		}
		return false;
	}

	@Override
	public SolicitudPagoResponseDto obtenerReferenciaPago(Long idSolicitudPago) {

		try {
			SolicitudPagoInformacion solicitudPagoModel = solicitudPagoRepository.findIdSolicitudPago(idSolicitudPago);

			List<FacturasInformacion> facturaModelBusqueda = facturasRepository
					.findIdDictamenAndEstatusTrue(solicitudPagoModel.getIdDictamen());
			Boolean aplicaConvenioColaboracion = catalogoService.aplicaCC(solicitudPagoModel.getIdContrato());

			SolicitudPagoResponseDto solicitudPagoResponseDto = new SolicitudPagoResponseDto();
			solicitudPagoResponseDto.setIdSolicitudPago(idSolicitudPago);
			solicitudPagoResponseDto.setFechaSolicitud(solicitudPagoModel.getFechaSolicitud());
			solicitudPagoResponseDto.setAplicaConvenioColaboracion(aplicaConvenioColaboracion);
			solicitudPagoResponseDto.setOficioSoliciturPago(solicitudPagoModel.getOficioSoliciturPago());
			solicitudPagoResponseDto.setDocumentoGenerado(solicitudPagoModel.getDocumentoGenerado());
			solicitudPagoResponseDto.setMoneda(solicitudPagoModel.getMoneda());

			if (solicitudPagoModel.getRutaSolicitudPago() != null) {
				solicitudPagoResponseDto.setRutaSolicitudPago(solicitudPagoModel.getRutaSolicitudPago());
			}

			List<ReferenciaPagoResponseDto> referenciaPagoResponseDtos = obtenerFacturas(facturaModelBusqueda,
					aplicaConvenioColaboracion);

			solicitudPagoResponseDto.setFacturaModelList(referenciaPagoResponseDtos);

			return solicitudPagoResponseDto;
		} catch (Exception e) {
			throw new DevengadosException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}
	}

	@Override
	public List<SolicitudPagoReponseDto> obtenerSolicitudPago(ObtenerSolicitudPagoRequest request) {
		try {
			List<SolicitudPagoReponseDto> listResponse = new ArrayList<>();
			List<SolicitudPagoModel> solicitudPagoModelList = solicitudPagoRepository
					.findByDictamenId(request.getIdDictamen());

			for (SolicitudPagoModel solicitudPagoModel : solicitudPagoModelList) {

				Integer idEstatusDictame = solicitudPagoModel.getDictamen().getIdEstatusDictamen();

				CatEstatusDictamen estatusDictamen = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
						CatalogosComunes.ESTATUS_DICTAMEN.getIdCatalogo(), idEstatusDictame, new CatEstatusDictamen());

				boolean estatusSol = estatusDictamen.getNombre().equals("Solicitud de pago")
						|| Objects.requireNonNull(estatusDictamen).getNombre().equals("Pagado");

				boolean estatusPagado = "Pagado".equals(estatusDictamen.getNombre());

				SolicitudPagoReponseDto solicitudPago = new SolicitudPagoReponseDto();
				solicitudPago.setIdSolicitudPago(solicitudPagoModel.getIdSolicitudPago());
				solicitudPago.setDictamen(solicitudPago.getDictamen());
				solicitudPago.setOficioSoliciturPago(solicitudPagoModel.getOficioSoliciturPago());
				solicitudPago.setFechaSolicitud(solicitudPagoModel.getFechaSolicitud());
				solicitudPago.setRutaSolicitudPago(solicitudPagoModel.getRutaSolicitudPago());
				solicitudPago.setAplicaConvenioColaboracion(solicitudPagoModel.getAplicaConvenioColaboracion());
				solicitudPago.setEstatus(solicitudPagoModel.getEstatus());
				solicitudPago.setDocumentoGenerado(solicitudPagoModel.getDocumentoGenerado());
				solicitudPago.setEstatusSolicitud(estatusSol);
				solicitudPago.setEstatusPagado(estatusPagado);
				listResponse.add(solicitudPago);
				log.info("-----------------------------------------> OK");
			}

			return listResponse;
		} catch (Exception e) {
			throw new DevengadosException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	private String obtenerNombreArchivo(String ruta) {
		if (ruta == null || ruta.isEmpty()) {
			return "";
		}
		String nombreArchivo = ruta.substring(ruta.lastIndexOf('/') + 1);

		if (nombreArchivo.isEmpty()) {
			return "";
		}
		return nombreArchivo;
	}

	// **

	public NotificacionDTO generarNotificacionDTO(RequestPlantillaBaseDto request, String tipoDoc) {
		try {
			Long idContrato = request.getIdContrato();

			LocalDateTime fecha = request.getFechaSolicitud();
			ContratoDto contratoDto = contratoMicoservicio.obtenerContratoId(idContrato);
			Long idProyecto = contratoDto.getIdProyecto();
			DatosGeneralesResponseDto datosGeneralesResponseDto = contratoMicoservicio
					.obtenerDatosGenerales(idContrato);

			FichaTecnicaResponse fichaTecnicaResponse = proyectoMicroservicio.obtenerFichaTecnica(idProyecto);
			String adminCentral = fichaTecnicaResponse.getAreaPlaneacion().getCatAdmonCentral().getAdministracion();

			SolicitudFacturaModel solicitudFacturaModel = solicitudFacturaRepository
					.findByDictamenIdAndDictamenEstatusTrue(request.getDictamenId().getIdDictamen());

			LocalDateTime fechaRecepcion = solicitudFacturaModel.getFechaRecepcionFactura();

			dictamenDto dictamenDto = dictamenService.obtenerDictamenesId(request.getDictamenId().getIdDictamen());
			Long idProveedor = dictamenDto.getIdProovedor();

			SolicitudFacturaModel solicitudFactura = solicitudFacturaRepository
					.findByDictamenId(request.getDictamenId().getIdDictamen()).orElse(null);

			ProveedorModel proveedorModel = proveedorRepository.findByIdProveedor(idProveedor).orElse(null);

			List<FacturaModel> facturaModel = facturasRepository
					.findByIdDictamenAndEstatusTrueAndCatEstatusFacturaNombre(request.getDictamenId().getIdDictamen());

			List<NotaCreditoModel> notaCreditoModels = notasCreditoRepository
					.findByIdDictamenAndEstatusTrue(request.getDictamenId().getIdDictamen());

			List<String> folios = new ArrayList<>();
			List<String> notasCredito = new ArrayList<>();
			List<BigDecimal> importes = new ArrayList<>();
			List<BigDecimal> montosSat = new ArrayList<>();
			List<BigDecimal> montoCc = new ArrayList<>();
			if (facturaModel != null) {
				for (FacturaModel factura : facturaModel) {
					folios.add(factura.getFolio());
					notasCredito.add(factura.getComprobanteFiscal());
					importes.add(factura.getTotalPesos());
					montosSat.add(factura.getMontoPesosSat());
					montoCc.add(factura.getMontoPesosCC());
				}
			}

			BigDecimal montoTotalFacturas = obtenerMontoTotal(importes);

			BigDecimal montoTotalSat = obtenerMontoTotal(montosSat);

			BigDecimal totalAnam = obtenerMontoTotal(montoCc);

			List<String> noNotasCredito = agregarNotas(notaCreditoModels);

			String foliosStr = String.join(", ", folios);
			String notasCretridoStr = String.join(",", notasCredito);
			String numeroNotasCredito = Optional.of(String.join(",", noNotasCredito)).orElse("");

			NotificacionDTO notificacionDTO = new NotificacionDTO();
			notificacionDTO.setImporte(Double.valueOf(Optional.of(montoTotalFacturas.toString()).orElse("N/A")));
			notificacionDTO.setNumOficioSolicitud(request.getOficioSolicitudPago());
			notificacionDTO.setFecha(fecha.toLocalDate()); // ** reciben la decha sin formato
			notificacionDTO.setNombreLargoContrato(contratoDto.getNombreContrato());
			notificacionDTO.setProyectoId(contratoDto.getIdProyecto().toString());
			List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
					CatalogosComunes.ACUERDO_PAGO.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);

			if (lista == null || lista.isEmpty()) {
				notificacionDTO.setAcuerdoId("N/A");

			} else {
				BaseCatalogoModel registroSeleccionado = lista.stream()
						.max(Comparator.comparing((BaseCatalogoModel item) -> Optional
								.ofNullable(item.getFechaModificacion()).orElse(LocalDateTime.MIN))
								.thenComparing(BaseCatalogoModel::getPrimaryKey))
						.orElse(null);

				Integer primaryKey = registroSeleccionado != null ? registroSeleccionado.getPrimaryKey() : 0;

				CatAcuerdoPago acuerdo = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
						CatalogosComunes.ACUERDO_PAGO.getIdCatalogo(), primaryKey, new CatAcuerdoPago());
				notificacionDTO.setAcuerdoId(registroSeleccionado != null ? acuerdo.getNombre() : "N/A");

			}

			notificacionDTO.setNumeroFactura(Optional.of(foliosStr).orElse("N/A"));
			notificacionDTO.setNombreCortoContrato(contratoDto.getNombreCorto());
			notificacionDTO.setFondeoContrato(datosGeneralesResponseDto.getCatFondeoContrato().getNombre());

			if (proveedorModel != null) {
				notificacionDTO.setProveedorId(Optional.of(proveedorModel.getIdAgs()).orElse("N/A"));
				notificacionDTO.setProveedor(Optional.ofNullable(proveedorModel.getNombreProveedor()).orElse("N/A"));
			}
			notificacionDTO.setContratoId(idContrato.toString());
			notificacionDTO.setMoneda(contratoDto.getTipoMoneda());
			notificacionDTO.setNumeroContrato(contratoDto.getNumeroContrato());
			int numeroMes = dictamenDto.getMes();
			Locale espanol = Locale.forLanguageTag("es-ES");
			String nombreMes = Month.of(numeroMes).getDisplayName(TextStyle.FULL, espanol);
			notificacionDTO.setPeriodoServicio(Optional
					.of(nombreMes.charAt(0) + nombreMes.substring(1).toLowerCase() + " " + dictamenDto.getAnio())
					.orElse("N/A"));
			notificacionDTO.setAdmContrato(Optional.ofNullable(contratoDto.getAdministradorContrato()).orElse("N/A"));
			notificacionDTO.setVerificador(Optional.ofNullable(contratoDto.getVerificadorContrato()).orElse("N/A"));
			notificacionDTO.setAdmCentral(adminCentral);
			notificacionDTO.setAdministracionGeneral(
					Optional.of(contratoDto.getAdministracionGeneral().get(0).getAdministracion()).orElse("N/A")); // VALIDAR

			notificacionDTO.setIdPlantillador(request.getIdPlantillador());
			notificacionDTO.setTipoDocumento(tipoDoc);
			if (solicitudFactura != null) {
				notificacionDTO.setFechaRecepcion(
						Optional.of(solicitudFactura.getFechaRecepcionFactura().toLocalDate()).orElse(null));
			}
			notificacionDTO.setNotaCreditoId(notasCretridoStr);
			notificacionDTO.setNumeroNota(numeroNotasCredito);
			notificacionDTO.setFechaRecepcion(fechaRecepcion.toLocalDate());
			notificacionDTO.setImporteSat(montoTotalSat.doubleValue()); // SOLO SE TOMAN EN CUENTA FACTURAS ACTIVAS

			// **
			notificacionDTO.setFolioFiscalId("test");// ** // FOLIO DE FACTURA
			notificacionDTO.setPedidoId("test");// ** // MES AÑOS

			notificacionDTO.setImporteAnam(totalAnam.doubleValue()); // SUMA DE MONTO TOTAL EN PESOS DE CONVENIO DE
																		// COLABORACION EN FACTURAS
			notificacionDTO.setComprobantes(""); // ESTO YA NO SE USA
			notificacionDTO.setGasto("");// LO HACE EL USUARIO
			notificacionDTO.setFechaLimite("");// LO HACE EL USUARIO
			notificacionDTO.setUnidadAdministrativa(
					"102 Administración General de Comunicaciones y Tecnologías de la información y Comunicaciones.");
			// VERIFICADOR EN LA TABLA PART. DEL CONTRATO
			responsabilidad(notificacionDTO, idContrato);
			// ***
			notificacionDTO.setPagina(1);
			notificacionDTO.setPaginas(1);

			return notificacionDTO;

		} catch (Exception e) {
			throw new DevengadosException(ErroresEnum.ERROR_AL_GUARDAR, e);
		}

	}

	private BigDecimal obtenerMontoTotal(List<BigDecimal> importes) {
		return importes.stream().filter(importe -> importe != null && importe.compareTo(BigDecimal.ZERO) > 0)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private List<String> agregarNotas(List<NotaCreditoModel> notaCreditoModels) {
		List<String> noNotasCredito = new ArrayList<>();

		if (notaCreditoModels != null) {
			for (NotaCreditoModel notaCredito : notaCreditoModels) {
				noNotasCredito.add(notaCredito.getFolio());
			}
		}

		return noNotasCredito;
	}

	private void responsabilidad(NotificacionDTO notificacionDTO, Long idContrato) {
		List<ParticipantesAdministracionModel> listaParticipantes = participantesContratoRepository
				.findByIdContratoAndEstatusTrueAndVigenteTrue(idContrato);
		String nombreAdminCentralContrato = "";
		for (ParticipantesAdministracionModel participante : listaParticipantes) {
			String responsabilidad = participante.getCatResponsabilidad().getNombre();
			if (responsabilidad.equals("Verificador del contrato")) {
				nombreAdminCentralContrato = (participante.getCatAdmonCentral() != null
						&& participante.getCatAdmonCentral().getPuesto() != null)
								? participante.getCatAdmonCentral().getPuesto()
								: ""; // VERIFICAR QUE SI SEA ESTE
										// PUESTO O DEL PUESTO DEL
										// USUARIO
			}
		}
		notificacionDTO.setPuesto(nombreAdminCentralContrato);// **verficiar // ES EL NOMBRE DE LA PERSONA QUE APARESCA
																// COMO

	}

	public void guardarArchivoSolicitudPago(Long idSolicutudPago, SolicitudPagoDto solicitudPagoDto) {

		Long idDictamen = solicitudPagoDto.getIdDictamen();
		String archivo = solicitudPagoDto.getArchivoPdf();
		String nombre = solicitudPagoDto.getNombreArchivo();
		String oficioSolicitud = solicitudPagoDto.getOficioSolicitudPago();
		LocalDateTime fechaSolicitud = solicitudPagoDto.getFechaSolicitud();

		
		

		ArchivoPlantillaDictamenModel archivoPlantilla = archivoRepo
				.findByNombreContainingAndCarpetaPlantillaModelIdDictamen("11_Of", idDictamen)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_ARCHIVO_NO_ENCONTRADO));
		
		comprobarObligatorio(archivoPlantilla, (archivo!=null && !archivo.isEmpty()));

		PathGenerator pathGenerator = new PathGenerator();

		String path = pathGenerator.generarPathSolicitudPago(archivoPlantilla.getCarpeta(),
				archivoPlantilla.getNombre());

		if(archivo!=null && !archivo.isEmpty()){
			if (!nombre.endsWith(".pdf")) {
				throw new DevengadosException(ErroresEnum.ERROR_FORMATO_PDF);
			}
			guardarArchivoSolicitudPagoComplemento(archivoPlantilla,archivo,nombre,path);
		}
		
		SolicitudPagoModel solicitudPagoModel = solicitudPagoRepository.findByIdSolicitudPago(idSolicutudPago);
		solicitudPagoModel.setRutaSolicitudPago(path);
		solicitudPagoModel.setOficioSoliciturPago(oficioSolicitud);
		solicitudPagoModel.setFechaSolicitud(fechaSolicitud);
		solicitudPagoModel.setArchivoPdf(archivoPlantilla);
		solicitudPagoRepository.save(solicitudPagoModel);
	}
	
	private void guardarArchivoSolicitudPagoComplemento(ArchivoPlantillaDictamenModel archivoPlantilla, String archivo, String nombre, String path) {
		File file = null;
		try {
			file = base64ToFile(archivo, nombre);
			double sizeMb = file.length() / (1024.0 * 1024.0);

			cargarArchivoConInformacionRenombrado(file, archivoPlantilla.getCarpeta(),
					archivoPlantilla.getNombre() + ".pdf");

			archivoPlantilla.setRuta(path);
			archivoPlantilla.setTamanoMb(sizeMb);
			archivoPlantilla.setCargado(true);
			archivoPlantilla.setFechaModificacion(obtenerFechaYHoraActual());

			archivoRepo.save(archivoPlantilla);

		} catch (Exception e) {
			if (e.getMessage() != null && e.getMessage().contains(CONEXION)) {
				throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
			}
			throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
		} finally {
			if (file != null && file.exists()) {
				Path filePath = file.toPath();
				try {
					Files.delete(filePath);
				} catch (IOException e) {
					log.warn(ELIMINACION, filePath, e);
				}
			}
		}
	}

	private void comprobarObligatorio(Archivo archivo, boolean carga) {

		if (archivo != null && archivo.isObligatorio() && !carga && !archivo.isCargado()) {
			throw new CatalogoException(ErroresEnum.DATOS_OBLIGATORIOS);
		}
	}

	public void cargarArchivoConInformacionRenombrado(File file, String path, String nombre) {
		try {
			try (InputStream inputStream = new FileInputStream(file)) {
				boolean archivo = nexusImpl.cargarArchivo(inputStream, path, nombre);
				log.info("Archivo cargado: {}", archivo);
			}

		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_GUARDAR_ARCHIVO, e);
		}
	}

	public void actualizarSolicitudFactura(String archivo, String nombre, Long idDictamen, String oficioSolicitud,
			LocalDateTime fechaSolicitud) {
		byte[] archivoByte = Base64.getDecoder().decode(archivo);

		MultipartFile docum = new CustomMultipartFile(archivoByte, nombre, nombre, Constantes.CONTENT_TYPE_PDF);

		SolicitudFacturaUpdateDto request = new SolicitudFacturaUpdateDto(idDictamen, oficioSolicitud,
				fechaSolicitud.toLocalDate(), docum, null, null);
		try {
			proformaService.solicitudFacturaProformaActualizar(request);
		} catch (Exception e) {
			throw new DevengadosException(ErroresEnum.ERROR_SERVICIO_INTERNO);
		}
	}

	public void guardarArchivosReferenciaPago(Long idReferenciaPago, String archivo, String nombre,
			ReferenciaPagoModel referenciaPago) {
		logger.info("archivo: {}", archivo);
		logger.info("nombre: {}", nombre);

		File file = null;
		try {
			file = base64ToFile(archivo, nombre);
			double sizeMb = file.length() / (1024.0 * 1024.0);

			cargarArchivoConInformacionRenombrado(file, referenciaPago.getArchivoPdf().getCarpeta(),
					referenciaPago.getArchivoPdf().getNombre() + ".pdf");

			ArchivoPlantillaDictamenModel archivosizeMb = referenciaPago.getArchivoPdf();
			archivosizeMb.setFechaModificacion(obtenerFechaYHoraActual());
			archivosizeMb.setNombre(referenciaPago.getArchivoPdf().getNombre());
			archivosizeMb.setDescripcion(referenciaPago.getArchivoPdf().getNombre());
			archivosizeMb.setTamanoMb(sizeMb);
			archivosizeMb.setRuta(referenciaPago.getArchivoPdf().getCarpeta() + "/"
					+ referenciaPago.getArchivoPdf().getNombre() + ".pdf");
			archivoRepo.save(archivosizeMb);

			ReferenciaPagoModel referenciaPagoModel = referenciaPagoRepository
					.findByIdReferenciaPagoAndEstatusTrue(idReferenciaPago);

			referenciaPagoModel.setRutaFichaNAFIN(referenciaPago.getArchivoPdf().getRuta());
			referenciaPagoModel.setEstatusPagado(referenciaPago.getEstatusPagado());
			referenciaPagoRepository.save(referenciaPagoModel);

		} catch (Exception e) {
			if (e.getMessage() != null && e.getMessage().contains(CONEXION)) {
				throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
			}
			throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
		} finally {
			if (file != null && file.exists()) {
				Path filePath = file.toPath();
				try {
					Files.delete(filePath);
					logger.info("Archivo temporal eliminado: {}", filePath);
				} catch (IOException e) {
					logger.warn(ELIMINACION, filePath, e);
				}
			}
		}
	}

	private void eliminarArchivo(String path) {
		try {
			log.info("Cuerpo de la peticion: {}", path);
			String nombreArchivo = separarNombreArchivo(path);
			PapeleraDto dto = new PapeleraDto();
			dto.setFecha(LocalDateTime.now());

			dto.setPathOriginal(path);

			PathGenerator pathGenerator = new PathGenerator();

			dto = papeleraServicio.generarId(dto);

			String pathNuevo = pathGenerator.generarPathPapeleta(dto.getIdPapelera(), nombreArchivo);

			dto.setPathNuevo(pathNuevo);
			dto = papeleraServicio.generarId(dto);

			nexusImpl.eliminarArchivo(path, dto.getIdPapelera());

			log.info("Archivo eliminado");



			// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.BORRA_REGISTRO.getId(),


			// TipoSeccionPista.PROYECTO_DATOS_GENERALES.getIdSeccionPista(),


			// TipoMovPista.BORRA_REGISTRO.getClave(), Optional.empty());

		} catch (Exception e) {
			throw new DevengadosException(ErroresEnum.ERROR_AL_GUARDAR);
		}

	}

	private String separarNombreArchivo(String path) {
		String[] archivos = path.split("/");
		return archivos[archivos.length - 1];
	}

	private void validarRegistroFactura(Long idFactura, Long idSolicitudPago) {

		Integer count = referenciaPagoRepository.countByIdFacturaAndIdSolicitudPagoAndEstatusTrue(idFactura,
				idSolicitudPago);
		log.info("total: {}", count);
		if (count > 2) {
			throw new DevengadosException(ErroresEnum.ERROR_REGISTRO_FACTURA);
		}
	}

	private static File base64ToFile(String base64String, String fileName) {
		try {
			String sanitizedFileName = sanitizeFileName(fileName);

			byte[] decodedBytes = Base64.getDecoder().decode(base64String);

			Path tempDir = Files.createTempDirectory("secureTempDir");
			// El nombre del archivo a sido validado, se trata de un falso positivo
			Path desiredFilePath = tempDir.resolve(sanitizedFileName).normalize();

			if (!desiredFilePath.startsWith(tempDir)) {
				throw new SecurityException("Intento de Path Traversal detectado.");
			}

			if (Files.exists(desiredFilePath)) {
				throw new IllegalStateException("El archivo ya existe.");
			}

			// La ruta del archivo a sido validado, se trata de un falso positivo
			Files.write(desiredFilePath, decodedBytes, StandardOpenOption.CREATE_NEW);

			return desiredFilePath.toFile();
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			throw new DevengadosException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}
	}

	private static String sanitizeFileName(String fileName) {
		return fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
	}

	private ReferenciaPagoModel guardarReferenciaPagoModel(InfoReferenciaPagoDto referenciaPago, Long idSolicitudPago,
			Long idDictamen) {

		Long idFactua = referenciaPago.getIdFactura();

		ReferenciaPagoModel referenciaPagoModel = new ReferenciaPagoModel();
		referenciaPagoModel.setIdSolicitudPago(idSolicitudPago);
		referenciaPagoModel.setFechaPago(referenciaPago.getFechaPago());
		referenciaPagoModel.setTipoCambioPagado(referenciaPago.getTipoCambioPagado());
		referenciaPagoModel.setPagadoNAFIN(referenciaPago.getPagadoNAFIN());
		referenciaPagoModel.setIdFactura(idFactua);
		referenciaPagoModel.setIdTipoNotificacionPago(referenciaPago.getTipoNotificacionPago());
		referenciaPagoModel.setOficioNotificacionPago(referenciaPago.getOficioNotificacionPago());
		referenciaPagoModel.setFechaNotificacion(referenciaPago.getFechaNotificacion());
		referenciaPagoModel.setFolioFichaPago(referenciaPago.getFolioFichaPago());

		referenciaPagoModel.setEstatus(true);
		referenciaPagoModel.setIdDesglose(referenciaPago.getIdDesglose());
		referenciaPagoModel.setConvenioColaboracion(referenciaPago.getConvenioColaboracion());
		List<CarpetaPlantillaDictamenModel> listaCarpeta = carpetaPlantillaDictamenRepository
				.findByIdDictamen(idDictamen);// se buscar por id de dictamen
		String rutaCarpeta = "";
		if (!listaCarpeta.isEmpty()) {
			rutaCarpeta = listaCarpeta.get(0).getRuta();
		}
		// CAMBIO PENDIENTE
		ArchivoPlantillaDictamenModel archivo = new ArchivoPlantillaDictamenModel();
		archivo.setNombre("12_NAFIN_" + referenciaPago.getFolioFichaPago());
		archivo.setDescripcion(referenciaPago.getFolioFichaPago());
		archivo.setObligatorio(true);
		archivo.setCargado(true);
		archivo.setNoAplica(false);
		archivo.setTamanoMb(null);
		archivo.setCarpeta(rutaCarpeta);
		archivo.setRuta(rutaCarpeta + "/" + archivo.getNombre() + ".pdf");
		archivo.setFechaModificacion(obtenerFechaYHoraActual());
		archivoRepo.save(archivo);
		referenciaPagoModel.setRutaFichaNAFIN(archivo.getRuta());
		referenciaPagoModel.setArchivoPdf(archivo);
		referenciaPagoRepository.save(referenciaPagoModel);

		return referenciaPagoModel;
	}

	private void actualizarReferenciaPago(List<InfoReferenciaPagoDto> referenciaPagoDtos,
			ReferenciaPagoDto referenciaPagoDto) {
		referenciaPagoDtos.forEach(infoReferenciaPagoDto -> {
			Long idReferenciaPago = infoReferenciaPagoDto.getIdReferenciaPago();
			if (idReferenciaPago == null) {
				crearNuevaReferenciaPago(infoReferenciaPagoDto, referenciaPagoDto);
			} else {
				actualizarReferenciaExistente(infoReferenciaPagoDto, referenciaPagoDto, idReferenciaPago);
			}
		});
	}

	private void crearNuevaReferenciaPago(InfoReferenciaPagoDto infoReferenciaPagoDto,
			ReferenciaPagoDto referenciaPagoDto) {
		ReferenciaPagoDto nuevaReferenciaDto = new ReferenciaPagoDto();
		nuevaReferenciaDto.setDictamenId(referenciaPagoDto.getDictamenId());
		nuevaReferenciaDto.setIdSolicitudPago(referenciaPagoDto.getIdSolicitudPago());
		nuevaReferenciaDto.setEstatusPagado(referenciaPagoDto.getEstatusPagado());
		nuevaReferenciaDto.setInfoReferenciaPagoDto(Collections.singletonList(infoReferenciaPagoDto));
		guardarReferenciaPago(nuevaReferenciaDto);
	}

	private void actualizarReferenciaExistente(InfoReferenciaPagoDto infoReferenciaPagoDto,
			ReferenciaPagoDto referenciaPagoDto, Long idReferenciaPago) {
		ReferenciaPagoModel referenciaPagoModel = referenciaPagoRepository
				.findByIdReferenciaPagoAndEstatusTrue(idReferenciaPago);
		if (referenciaPagoModel == null) {
			throw new DevengadosException(ErroresEnum.REGISTRO_NO_ENCONTRADO);
		}

		referenciaPagoModel.setIdFactura(infoReferenciaPagoDto.getIdFactura());
		referenciaPagoModel.setIdTipoNotificacionPago(infoReferenciaPagoDto.getTipoNotificacionPago());
		referenciaPagoModel.setOficioNotificacionPago(infoReferenciaPagoDto.getOficioNotificacionPago());
		referenciaPagoModel.setFechaNotificacion(infoReferenciaPagoDto.getFechaNotificacion());
		referenciaPagoModel.setFolioFichaPago(infoReferenciaPagoDto.getFolioFichaPago());
		referenciaPagoModel.setIdDesglose(infoReferenciaPagoDto.getIdDesglose());
		referenciaPagoModel.setFechaPago(infoReferenciaPagoDto.getFechaPago());
		referenciaPagoModel.setTipoCambioPagado(infoReferenciaPagoDto.getTipoCambioPagado());
		referenciaPagoModel.setPagadoNAFIN(infoReferenciaPagoDto.getPagadoNAFIN());

		if (infoReferenciaPagoDto.getArchivoFicha() == null || infoReferenciaPagoDto.getArchivoFicha().isEmpty()) {
			return;
		}

		validarFormatoArchivo(infoReferenciaPagoDto.getNombreArchivo());

		File file = null;
		try {
			file = base64ToFile(infoReferenciaPagoDto.getArchivoFicha(), infoReferenciaPagoDto.getNombreArchivo());
			double sizeMb = file.length() / (1024.0 * 1024.0);

			ArchivoPlantillaDictamenModel archivoPlantilla = referenciaPagoModel.getArchivoPdf();

			if (archivoPlantilla != null) {
				actualizarArchivoExistente(file, sizeMb, infoReferenciaPagoDto, archivoPlantilla, referenciaPagoModel);
			} else {
				crearNuevoArchivoPlantilla(file, sizeMb, infoReferenciaPagoDto, referenciaPagoDto, referenciaPagoModel);
			}

			referenciaPagoModel.setEstatus(true);
			referenciaPagoRepository.save(referenciaPagoModel);
			registrarMovimientoYGuardarPista(referenciaPagoModel);

			String movimiento = solicitudPagoPistasConsumer.movimientoReferenciaPago(referenciaPagoModel);

			// pistaService.guardarPista(ModuloPista.ADMIN_DEVENGADOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),

			// TipoSeccionPista.SOLICITUD_PAGO.getIdSeccionPista(), movimiento, Optional.empty());

		} catch (Exception e) {
			log.error("Error al actualizar referencia de pago", e);
			throw new DevengadosException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		} finally {
			if (file != null && file.exists()) {
				Path filePath = file.toPath();
				try {
					Files.delete(filePath);
					log.info("Archivo temporal eliminado: {}", filePath);
				} catch (IOException e) {
					log.warn(ELIMINACION, filePath, e);
				}
			}
		}
	}

	private void validarFormatoArchivo(String nombreArchivo) {
		if (!nombreArchivo.endsWith(".pdf")) {
			throw new DevengadosException(ErroresEnum.ERROR_FORMATO_PDF);
		}
	}

	private void actualizarArchivoExistente(File file, double sizeMb, InfoReferenciaPagoDto infoReferenciaPagoDto,
			ArchivoPlantillaDictamenModel archivoPlantilla, ReferenciaPagoModel referenciaPagoModel) {
		String pathAnterior = archivoPlantilla.getRuta();
		if (pathAnterior != null) {
			eliminarArchivo(pathAnterior); // validar el caso en papelera cuando se va a remplazar un archivo
			archivoPlantilla.setRuta(null);
			archivoRepo.save(archivoPlantilla);
		}
		try {
			cargarArchivoConInformacionRenombrado(file, archivoPlantilla.getCarpeta(),
					Constantes.NAFIN_12 + infoReferenciaPagoDto.getFolioFichaPago() + ".pdf");
		} catch (Exception e) {

			if (e.getMessage() != null && e.getMessage().contains(CONEXION)) {
				throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
			}
			throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
		}

		archivoPlantilla.setTamanoMb(sizeMb);
		archivoPlantilla.setFechaModificacion(obtenerFechaYHoraActual());
		archivoPlantilla.setNombre(Constantes.NAFIN_12 + infoReferenciaPagoDto.getFolioFichaPago());
		archivoPlantilla.setDescripcion(Constantes.NAFIN_12 + infoReferenciaPagoDto.getFolioFichaPago());
		archivoPlantilla.setRuta(archivoPlantilla.getCarpeta() + "/" + Constantes.NAFIN_12
				+ infoReferenciaPagoDto.getFolioFichaPago() + ".pdf");
		archivoRepo.save(archivoPlantilla);

		referenciaPagoModel.setRutaFichaNAFIN(archivoPlantilla.getRuta());
	}

	private void crearNuevoArchivoPlantilla(File file, double sizeMb, InfoReferenciaPagoDto infoReferenciaPagoDto,
			ReferenciaPagoDto referenciaPagoDto, ReferenciaPagoModel referenciaPagoModel) {
		ArchivoPlantillaDictamenModel nuevaPlantilla = new ArchivoPlantillaDictamenModel();
		nuevaPlantilla.setNombre(Constantes.NAFIN_12 + infoReferenciaPagoDto.getFolioFichaPago());
		nuevaPlantilla.setDescripcion(Constantes.NAFIN_12 + infoReferenciaPagoDto.getFolioFichaPago());
		nuevaPlantilla.setObligatorio(true);
		nuevaPlantilla.setCargado(true);
		nuevaPlantilla.setNoAplica(false);
		nuevaPlantilla.setTamanoMb(sizeMb);

		List<CarpetaPlantillaDictamenModel> listaCarpeta = carpetaPlantillaDictamenRepository
				.findByIdDictamen(referenciaPagoDto.getDictamenId().getIdDictamen());
		if (!listaCarpeta.isEmpty()) {
			String rutaCarpeta = listaCarpeta.get(0).getRuta();
			nuevaPlantilla.setCarpeta(rutaCarpeta);
			nuevaPlantilla.setRuta(rutaCarpeta + "/" + nuevaPlantilla.getNombre() + ".pdf");
		}

		nuevaPlantilla.setFechaModificacion(obtenerFechaYHoraActual());
		archivoRepo.save(nuevaPlantilla);

		try {
			cargarArchivoConInformacionRenombrado(file, nuevaPlantilla.getCarpeta(),
					nuevaPlantilla.getNombre() + ".pdf");
		} catch (Exception e) {

			if (e.getMessage() != null && e.getMessage().contains(CONEXION)) {
				throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
			}
			throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
		}
		referenciaPagoModel.setRutaFichaNAFIN(nuevaPlantilla.getRuta());
		referenciaPagoModel.setArchivoPdf(nuevaPlantilla);
	}

	private void registrarMovimientoYGuardarPista(ReferenciaPagoModel referenciaPagoModel) {
		String movimiento = solicitudPagoPistasConsumer.movimientoReferenciaPago(referenciaPagoModel);

		// pistaService.guardarPista(ModuloPista.ADMIN_DEVENGADOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),

		// TipoSeccionPista.SOLICITUD_PAGO.getIdSeccionPista(), movimiento, Optional.empty());
	}

	// ***
	private void actualizarFacturas(ReferenciaPagoDto referenciaPagoDto, List<BaseCatalogoModel> listaFactura) {

		if (Boolean.TRUE.equals(referenciaPagoDto.getEstatusPagado())) {
			List<BaseCatalogoModel> listaDictamen = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
					CatalogosComunes.ESTATUS_DICTAMEN.getIdCatalogo(), Constantes.ESTATUS_SOLCITUDD_PAGO_PAGADO);

			if (listaDictamen.isEmpty()) {
				throw new DevengadosException(ErroresEnum.ERROR_ESTATUS_SOLICITUD_NO_ENCONTRADO);
			}

			Integer idEstatusDictamen = listaDictamen.get(0).getPrimaryKey();
			Dictamen dictamen = dictamenRepository.findByIdDictamen(referenciaPagoDto.getDictamenId().getIdDictamen())
					.orElseThrow(() -> new DevengadosException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));
			dictamen.setIdEstatusDictamen(idEstatusDictamen);
			dictamen.setUltimaModificacion(dictamenService.ultimaModificacionGeneral());

			dictamenRepository.save(dictamen);

			List<FacturaModel> facturas = dictamen.getFacturaModel();
			if (facturas != null && !facturas.isEmpty()) {
				for (FacturaModel facturaModel : facturas) {
					if (!Constantes.CANCELADO.equals(facturaModel.getCatEstatusFactura().getNombre())) {
						facturaModel.setIdEstatusFactura(Long.parseLong("" + listaFactura.get(0).getPrimaryKey()));
					}
				}
				facturasRepository.saveAll(facturas);
			}
		}
	}

	private List<ReferenciaPagoResponseDto> obtenerFacturas(List<FacturasInformacion> facturaModel,
			Boolean aplicaConvenioColaboracion) {

		List<ReferenciaPagoResponseDto> referenciaPagoResponseDtos = new ArrayList<>();
		for (FacturasInformacion factura : facturaModel) {

			Long idFactura = factura.getIdFactura();

			ReferenciaPagoResponseDto referenciaPagoResponse = new ReferenciaPagoResponseDto();

			List<ReferenciaPagoConvenioInformacion> referencias = referenciaPagoRepository
					.findConveniosByIdFacturaAndEstatus(idFactura, true);

			ReferenciaPagoConvenioInformacion referenciaPagoConvenio = referencias.stream()
					.filter(ReferenciaPagoConvenioInformacion::getConvenioColaboracion).findFirst().orElse(null);

			ReferenciaPagoConvenioInformacion referenciaPago = referencias.stream()
					.filter(referencia -> !referencia.getConvenioColaboracion()).findFirst().orElse(null);

			ReferenciaPagoConvenioDto referenciaPagoModel = new ReferenciaPagoConvenioDto();

			BanderaReponseDto bandera = facturaService.banderaPagadofactura(factura.getIdDictamen());

			referenciaPagoModel.setIdFactura(idFactura);
			referenciaPagoModel.setFolio(factura.getFolio());
			referenciaPagoModel.setTipoMoneda(factura.getTipoMoneda());
			referenciaPagoModel.setTieneSAT(bandera.isTieneSAT());
			referenciaPagoModel.setTieneCC(bandera.isTieneCC());

			if (referenciaPago != null) {
				String nombreArchivo = obtenerNombreArchivo(referenciaPago.getRutaFichaNAFIN());
				referenciaPagoModel.setIdReferenciaPago(referenciaPago.getIdReferenciaPago());
				referenciaPagoModel.setIdTipoNotificacionPago(referenciaPago.getIdTipoNotificacionPago());
				referenciaPagoModel.setIdSolicitudPago(referenciaPago.getIdSolicitudPago());
				referenciaPagoModel.setOficioNotificacionPago(referenciaPago.getOficioNotificacionPago());
				referenciaPagoModel.setFolioFichaPago(referenciaPago.getFolioFichaPago());
				referenciaPagoModel.setFechaPago(referenciaPago.getFechaPago());
				referenciaPagoModel.setTipoCambioPagado(referenciaPago.getTipoCambioPagado());
				referenciaPagoModel.setPagadoNAFIN(referenciaPago.getPagadoNAFIN());
				referenciaPagoModel.setRutaFichaNAFIN(referenciaPago.getRutaFichaNAFIN());
				referenciaPagoModel.setIdDesglose(referenciaPago.getIdDesglose());
				referenciaPagoModel.setEstatusPagado(referenciaPago.getEstatusPagado());
				referenciaPagoModel.setConvenioColaboracion(referenciaPago.getConvenioColaboracion());
				referenciaPagoModel.setFechaNotificacion(referenciaPago.getFechaNotificacion());
				referenciaPagoModel.setNombreArchivo(nombreArchivo);// ....
			}
			referenciaPagoResponse.setReferenciaPagoModel(referenciaPagoModel);

			if (Boolean.TRUE.equals(aplicaConvenioColaboracion)) {
				ReferenciaPagoConvenioDto referenciaPagoConvenioDto = new ReferenciaPagoConvenioDto();
				referenciaPagoConvenioDto.setIdFactura(idFactura);
				referenciaPagoConvenioDto.setFolio(factura.getFolio());
				referenciaPagoConvenioDto.setTipoMoneda(factura.getTipoMoneda());

				if (referenciaPagoConvenio != null) {
					String nombreArchivo = obtenerNombreArchivo(referenciaPagoConvenio.getRutaFichaNAFIN());
					referenciaPagoConvenioDto.setIdReferenciaPago(referenciaPagoConvenio.getIdReferenciaPago());
					referenciaPagoConvenioDto
							.setIdTipoNotificacionPago(referenciaPagoConvenio.getIdTipoNotificacionPago());
					referenciaPagoConvenioDto.setIdSolicitudPago(referenciaPagoConvenio.getIdSolicitudPago());
					referenciaPagoConvenioDto
							.setOficioNotificacionPago(referenciaPagoConvenio.getOficioNotificacionPago());
					referenciaPagoConvenioDto.setFolioFichaPago(referenciaPagoConvenio.getFolioFichaPago());
					referenciaPagoConvenioDto.setFechaPago(referenciaPagoConvenio.getFechaPago());
					referenciaPagoConvenioDto.setFechaNotificacion(referenciaPagoConvenio.getFechaNotificacion());
					referenciaPagoConvenioDto.setTipoCambioPagado(referenciaPagoConvenio.getTipoCambioPagado());
					referenciaPagoConvenioDto.setPagadoNAFIN(referenciaPagoConvenio.getPagadoNAFIN());
					referenciaPagoConvenioDto.setRutaFichaNAFIN(referenciaPagoConvenio.getRutaFichaNAFIN());
					referenciaPagoConvenioDto.setIdDesglose(referenciaPagoConvenio.getIdDesglose());
					referenciaPagoConvenioDto.setEstatusPagado(referenciaPagoConvenio.getEstatusPagado());
					referenciaPagoConvenioDto.setConvenioColaboracion(referenciaPagoConvenio.getConvenioColaboracion());
					referenciaPagoConvenioDto.setNombreArchivo(nombreArchivo);
				}
				referenciaPagoResponse.setReferenciaConvenioColaboracion(referenciaPagoConvenioDto);
			}
			referenciaPagoResponseDtos.add(referenciaPagoResponse);
		}
		return referenciaPagoResponseDtos;
	}

}
