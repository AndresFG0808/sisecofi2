package com.sisecofi.admindevengados.service.descuentos_deducciones_penalizaciones.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sisecofi.admindevengados.microservicio.ContratoMicoservicio;
import com.sisecofi.admindevengados.microservicio.PlantilladorMicriservicio;
import com.sisecofi.admindevengados.microservicio.ProyectoMicroservicio;
import com.sisecofi.admindevengados.model.DictaminadoModel;
import com.sisecofi.admindevengados.repository.*;
import com.sisecofi.admindevengados.repository.contrato.ParticipantesContratoRepository;
import com.sisecofi.admindevengados.repository.descuentos_deduccione_penalizaciones.CatTipoDescuentoRepository;
import com.sisecofi.admindevengados.repository.descuentos_deduccione_penalizaciones.DeduccionesDescuentosPenalizacionesRespository;
import com.sisecofi.admindevengados.service.FileService;
import com.sisecofi.admindevengados.service.PistaService;
import com.sisecofi.admindevengados.service.descuentos_deducciones_penalizaciones.DeduccionesDescuentosPenalizacionesService;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoDto;
import com.sisecofi.libreria.comunes.dto.plantillador.HtmlExcelListDto;
import com.sisecofi.libreria.comunes.dto.proyecto.FichaTecnicaResponse;
import com.sisecofi.libreria.comunes.model.plantillador.SubPlantilladorModel;
import com.sisecofi.libreria.comunes.model.proveedores.DirectorioProveedorModel;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sisecofi.admindevengados.dto.deducciones_descuentos_penalizaciones_dto.*;
import com.sisecofi.admindevengados.microservicio.CatalogoMicroservicio;
import com.sisecofi.admindevengados.model.DeduccionesModel;
import com.sisecofi.admindevengados.model.PenasConvencionalesModel;
import com.sisecofi.admindevengados.model.deducciones_descuentos_penalizaciones.DeduccionesDescuentosPenalizacionesModel;
import com.sisecofi.admindevengados.service.DictamenService;
import com.sisecofi.admindevengados.service.impl.NexusImpl;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.admindevengados.util.enums.ErroresEnum;
import com.sisecofi.admindevengados.util.exception.CatalogoException;
import com.sisecofi.admindevengados.util.exception.DevengadosException;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoDescuento;
import com.sisecofi.libreria.comunes.model.contratos.ParticipantesAdministracionModel;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoPlantillaDictamenModel;
import com.sisecofi.libreria.comunes.model.penasContractuales.PenasContractualesModel;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoPlantillaDictamenRepository;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class DeduccionesDescuentosPenalizacionesServiceImpl implements DeduccionesDescuentosPenalizacionesService {

	private final CatTipoDescuentoRepository catTipoDescuentoRepository;
	private final DeduccionesDescuentosPenalizacionesRespository ddpRepository;
	private final CatDictamenRespository catDictamenRepo;
	private final PenasContractualesRepository penasContractualesRepository;
	private final PenasConvencionalesRepository penasConvencionalesRepository;
	private final DeduccionRepository deduccionRepository;
	private final NexusImpl nexusImpl;
	private final DictamenRepository dictamenRepository;
	private final DictamenService dictamenService;
	private final ArchivoPlantillaDictamenRepository archivoRepo;
	private final CatalogoMicroservicio catalogoMicroservicio;
	private final ContratoMicoservicio contratoMicoservicio;
	private final FileService fileService;
	private final DictaminadoRepository dictaminadoRepository;
	private final PlantilladorMicriservicio plantilladorMicriservicio;
	private static final String PISTA_GEN = "Pista Generada: {}";
	private final PistaService pistaService;
	private final ProyectoMicroservicio proyectoMicroservicio;
	private final ParticipantesContratoRepository participantesContratoRepository;
	private static final String ID_DICTAMEN = "Id dictamen:  ";
	private static final String DED_DES_PEN = "Id deducciones, descuentos y penalizaciones: ";
	private static final String MONTO = "Monto: ";
	private static final String MONEDA = "Moneda: ";
	private static final String TIPO_CAMBIO = "Tipo descuento: ";
	private static final String DICTAMEN_ID = "Id dictamen: ";
	private static final String VERIFICADOR = "Verificador";

	@Transactional
	@Override
	public List<DeduccionesDPDto> crearDdp(List<DeduccionesDPDto> ddpList) {
		List<DeduccionesDPDto> responseList = new ArrayList<>();

		for (DeduccionesDPDto ddp : ddpList) {

			// buscar tipo deduccion
			Long dictamenId = ddp.getDictamenId();

			Optional<CatTipoDescuento> tipoDeduccionOpt = catTipoDescuentoRepository.findById(ddp.getIdTipoDescuento());
			if (tipoDeduccionOpt.isEmpty()) {
				throw new DevengadosException(ErroresEnum.ERROR_BUSCAR_CAT_TIPODESCUENTO);
			}

			Optional<Dictamen> dictamenOpt = catDictamenRepo.findById(dictamenId);
			if (dictamenOpt.isEmpty()) {
				throw new DevengadosException(ErroresEnum.ERROR_CONTRATO_VACIO);
			}

			// crear instancia de la entidad
			DeduccionesDescuentosPenalizacionesModel nuevoDdp = new DeduccionesDescuentosPenalizacionesModel();
			nuevoDdp.setMonto(ddp.getMonto());
			nuevoDdp.setMoneda(dictamenOpt.get().getContratoModel().getVigencia().getCatTipoMoneda().getNombre());
			nuevoDdp.setEstatusDeduccion(true);
			nuevoDdp.setIdDictamen(ddp.getDictamenId());

			// Asignar objeto completo de tipoDeduccion
			nuevoDdp.setTipoDescuento(tipoDeduccionOpt.get());
			nuevoDdp.setIdTipoDescuento(tipoDeduccionOpt.get().getIdTipoDescuento());

			DeduccionesDescuentosPenalizacionesModel ddpGuardado = ddpRepository.save(nuevoDdp);

			// Crear DTO de respuesta
			DeduccionesDPDto responseDto = new DeduccionesDPDto();
			responseDto.setDictamenId(ddpGuardado.getIdDictamen());
			responseDto.setOrdenDescuento(ddpGuardado.getOrdenDescuento());
			responseDto.setIdDdp(ddpGuardado.getIdDdp());
			responseDto.setIdTipoDescuento(ddpGuardado.getIdTipoDescuento());

			responseDto.setNombreDescuento(
					ddpGuardado.getTipoDescuento() != null ? ddpGuardado.getTipoDescuento().getNombre() : null);

			responseDto.setMoneda(ddpGuardado.getMoneda());
			responseDto.setMonto(ddpGuardado.getMonto());

			responseList.add(responseDto);

			// pistas auditoria -Insertar
			StringBuilder builder = new StringBuilder();

			builder.append(ID_DICTAMEN).append(dictamenId).append(" | ").append(DED_DES_PEN).append(nuevoDdp.getIdDdp())
					.append(" | ").append(MONTO).append(nuevoDdp.getMonto()).append(" | ").append(MONEDA)
					.append(nuevoDdp.getMoneda()).append(" | ").append(TIPO_CAMBIO)
					.append(ddpGuardado.getTipoDescuento().getNombre()).append(" | ");
			log.info(PISTA_GEN, builder.toString());



			// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),


			// TipoSeccionPista.DICTAMEN_DEDUCCIONES_DESCUENTOS.getIdSeccionPista(), builder.toString(),


			// Optional.empty());

		}

		return responseList;
	}

	@Transactional
	@Override
	public List<DeduccionesDPModiDto> modificarDdp(List<DeduccionesDPModiDto> ddpList) {
		List<DeduccionesDPModiDto> responseList = new ArrayList<>();

		for (DeduccionesDPModiDto ddpModi : ddpList) {

			DeduccionesDescuentosPenalizacionesModel deduccionExiste = ddpRepository
					.findByIdDdpAndEstatusDeduccionTrue(ddpModi.getIdDdp())
					.orElseThrow(() -> new DevengadosException(ErroresEnum.ERROR_BUSCAR_DDP));

			CatTipoDescuento catTipoDeduccion = catTipoDescuentoRepository.findById(ddpModi.getIdTipoDescuento())
					.orElseThrow(() -> new DevengadosException(ErroresEnum.ERROR_BUSCAR_CAT_TIPODESCUENTO));

			deduccionExiste.setTipoDescuento(catTipoDeduccion);
			deduccionExiste.setMonto(ddpModi.getMonto());

			DeduccionesDescuentosPenalizacionesModel deduModificacion = ddpRepository.save(deduccionExiste);

			DeduccionesDPModiDto responseDto = mapearDeduccionDto(deduModificacion);
			responseList.add(responseDto);

			// pistas auditoria -Insertar
			StringBuilder builder = new StringBuilder();

			builder.append(ID_DICTAMEN).append(deduccionExiste.getDictamen().getIdDictamen()).append(" | ")
					.append(DED_DES_PEN).append(deduccionExiste.getIdDdp()).append(" | ").append(MONTO)
					.append(deduccionExiste.getMonto()).append(" | ").append(MONEDA).append(deduccionExiste.getMoneda())
					.append(" | ").append(TIPO_CAMBIO).append(deduccionExiste.getTipoDescuento().getNombre())
					.append(" | ");
			log.info(PISTA_GEN, builder.toString());



			// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),


			// TipoSeccionPista.DICTAMEN_DEDUCCIONES_DESCUENTOS.getIdSeccionPista(), builder.toString(),


			// Optional.empty());

		}

		return responseList;

	}

	// metodo aux. para mapeo de entidad a dto
	private DeduccionesDPModiDto mapearDeduccionDto(DeduccionesDescuentosPenalizacionesModel dModel) {
		DeduccionesDPModiDto dto = new DeduccionesDPModiDto();

		dto.setIdTipoDescuento(dModel.getTipoDescuento().getIdTipoDescuento());
		dto.setNombreDescuento(dModel.getTipoDescuento().getNombre());
		dto.setIdDdp(dModel.getIdDdp());
		dto.setMoneda(dModel.getDictamen().getContratoModel().getVigencia().getCatTipoMoneda().getNombre());
		dto.setMonto(dModel.getMonto());

		return dto;

	}

	// soft delete

	@Transactional
	@Override
	public void eliminacionLogicaDdp(List<Long> idDdpList) {

		for (Long idDdp : idDdpList) {

			/// buscar deducc, penaliza, descuento activo (estatus = true)
			DeduccionesDescuentosPenalizacionesModel dModel = ddpRepository.findByIdDdpAndEstatusDeduccionTrue(idDdp)
					.orElseThrow(() -> new DevengadosException(ErroresEnum.ERROR_BUSCAR_DDP));

			dModel.setEstatusDeduccion(false);
			ddpRepository.save(dModel);

			// pistas auditoria -Insertar
			StringBuilder builder = new StringBuilder();

			builder.append(ID_DICTAMEN).append(dModel.getIdDictamen()).append(" | ").append(DED_DES_PEN)
					.append(dModel.getIdDdp()).append(" | ").append(MONTO).append(dModel.getMonto()).append(" | ")
					.append(MONEDA).append(dModel.getMoneda()).append(" | ").append(TIPO_CAMBIO)
					.append(dModel.getTipoDescuento().getNombre()).append(" | ");
			log.info(PISTA_GEN, builder.toString());



			// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.BORRA_REGISTRO.getId(),


			// TipoSeccionPista.DICTAMEN_DEDUCCIONES_DESCUENTOS.getIdSeccionPista(), builder.toString(),


			// Optional.empty());

		}

	}

	@Override
	public List<DeduccionesDpConsultaDto> buscarPorIdDictamen(Long dictamenId) {

		// 1. Obntener DDP dede tabla local
		List<DeduccionesDescuentosPenalizacionesModel> deduccionesList = ddpRepository
				.findByIdDictamenAndEstatusDeduccionTrueOrderByIdDdpAsc(dictamenId);

		// 2. obtener penas contractuales
		List<PenasContractualesModel> penasContractuales = penasContractualesRepository
				.findByIdDictamenAndEstatusTrue(dictamenId);

		// 3. obtener penas convencionales
		List<PenasConvencionalesModel> penasConvencionales = penasConvencionalesRepository
				.findByIdDictamenAndEstatusTrue(dictamenId);

		// 4.obtener deducciones
		List<DeduccionesModel> deduccionModel = deduccionRepository.findByIdDictamenAndEstatusTrue(dictamenId);

		// Mapear lista de DeduccionesDescuentosPenalizaciones Model a
		// DeduccionesDpConsultaDto
		List<DeduccionesDpConsultaDto> resultado = deduccionesList.stream().map(deduccion -> {
			DeduccionesDpConsultaDto dto = new DeduccionesDpConsultaDto();
			dto.setIdDdp(deduccion.getIdDdp());
			dto.setMoneda(deduccion.getDictamen().getContratoModel().getVigencia().getCatTipoMoneda().getNombre());
			dto.setIdTipoDescuento(deduccion.getTipoDescuento().getIdTipoDescuento());
			dto.setNombreDescuento(deduccion.getTipoDescuento().getNombre());
			dto.setMonto(deduccion.getMonto());
			dto.setIdOrigen("DeduccionesDescuentosPenalizaciones");
			return dto;
		}).collect(Collectors.toList());

		// penas contractuales
		resultado.addAll(penasContractuales.stream().map(penaContractual -> {
			DeduccionesDpConsultaDto dto = new DeduccionesDpConsultaDto();
			dto.setIdDdp(null);
			dto.setMoneda(
					penaContractual.getDictamen().getContratoModel().getVigencia().getCatTipoMoneda().getNombre());
			dto.setIdTipoDescuento(penaContractual.getCatDesgloce().getIdDesgloce());
			dto.setNombreDescuento(penaContractual.getCatDesgloce().getNombre());
			dto.setMonto(penaContractual.getMonto());
			dto.setIdOrigen("Penas Contractuales");
			return dto;
		}).toList()); // Conversión explícita a una lista

		// Penas convencionales
		resultado.addAll(penasConvencionales.stream().map(penaConvencional -> {
			DeduccionesDpConsultaDto dto = new DeduccionesDpConsultaDto();
			dto.setIdDdp(null);
			dto.setMoneda(
					penaConvencional.getDictamen().getContratoModel().getVigencia().getCatTipoMoneda().getNombre());
			dto.setIdTipoDescuento(penaConvencional.getCatDesgloce().getIdDesgloce());
			dto.setNombreDescuento(penaConvencional.getCatDesgloce().getNombre());
			dto.setMonto(penaConvencional.getMonto());
			dto.setIdOrigen("Penas Convencionales");
			return dto;
		}).toList());

		// Deducciones
		resultado.addAll(deduccionModel.stream().map(deduccion -> {
			DeduccionesDpConsultaDto dto = new DeduccionesDpConsultaDto();
			dto.setIdDdp(null);
			dto.setMoneda(deduccion.getDictamen().getContratoModel().getVigencia().getCatTipoMoneda().getNombre());
			dto.setIdTipoDescuento(deduccion.getCatDesgloce().getIdDesgloce());
			dto.setNombreDescuento(deduccion.getCatDesgloce().getNombre());
			dto.setMonto(deduccion.getMonto());
			dto.setIdOrigen("Deducción");
			return dto;
		}).toList());

		return resultado;
	}

	public Dictamen obtenerDictamen(Long dictamenId) {
		return dictamenRepository.findByIdDictamen(dictamenId)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_CONTRATO_VACIO));
	}

	// examinar-ddp
	@Transactional
	@Override
	public ArchivoPlantillaDictamenModel procesarArchivoPenasDeducciones(MultipartFile detallePenasDeducciones,
			Long idDictamen) {

		// Obtener dictamen

		// Obtener archivo existente del dictamen
		ArchivoPlantillaDictamenModel archivo = archivoRepo
				.findByDescripcionContainingAndCarpetaPlantillaModelIdDictamen(VERIFICADOR, idDictamen)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_CONTRATO_VACIO));
		
		comprobarObligatorio(archivo, (detallePenasDeducciones!=null && !detallePenasDeducciones.isEmpty()));

		// Validar si las deducciones o penalizaciones tienen montos > 0
		List<DeduccionesDpConsultaDto> deduccionesDtos = buscarPorIdDictamen(idDictamen);

		// Sumar montos
		BigDecimal montoTotal = deduccionesDtos.stream().map(DeduccionesDpConsultaDto::getMonto).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		// Determinar la extensión del archivo dependiendo del monto
		String extensionArchivo;
		if (montoTotal.compareTo(BigDecimal.ZERO) > 0) {
			extensionArchivo = ".xlsx"; // Cargar un archivo Excel
		} else {
			extensionArchivo = ".pdf"; // Cargar un archivo PDF
		}

		String rutaBase = archivo.getCarpeta();
		String rutaModificada = rutaBase + "/" + archivo.getNombre() + extensionArchivo;

		// Determinar si es una actualización o una inserción
		boolean esPrimeraCarga = !archivo.isCargado();
		
		
		// Crear el StringBuilder para la pista de auditoría
		StringBuilder builder = new StringBuilder();
		// Actualizar la información del archivo
		if (detallePenasDeducciones!=null && !detallePenasDeducciones.isEmpty()) {
			archivo.setRuta(rutaModificada);
			archivo.setCargado(true);
			archivo.setTamanoMb((double) detallePenasDeducciones.getSize() / (1024 * 1024));
			archivo.setFechaModificacion(horaActual());

			// Guardar en la BD
			archivoRepo.save(archivo);
			
			// Cargar el archivo físico con el nombre y extensión
			try {
				cargarArchivoConInformacionRenombrado(detallePenasDeducciones, rutaBase,
						archivo.getNombre() + extensionArchivo);
			} catch (Exception e) {
				if (e.getMessage() != null && e.getMessage().contains("Connection is closed")) {
					throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
				}
				throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
			}
			
			builder.append(DICTAMEN_ID).append(idDictamen).append(" | ").append("Nombre archivo: ")
			.append(detallePenasDeducciones.getOriginalFilename()).append(" ");
		}
		

		// Registrar la pista de auditoría de acuerdo al estado de carga
		if (esPrimeraCarga) {

			// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),

			// TipoSeccionPista.DICTAMEN_DEDUCCIONES_DESCUENTOS.getIdSeccionPista(), builder.toString(),

			// Optional.empty());
		} else {

			// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),

			// TipoSeccionPista.DICTAMEN_DEDUCCIONES_DESCUENTOS.getIdSeccionPista(), builder.toString(),

			// Optional.empty());
		}

		return archivo;
	}

	private void comprobarObligatorio(Archivo archivo, boolean carga) {

		if (archivo != null && archivo.isObligatorio() && !carga && !archivo.isCargado()) {
			throw new CatalogoException(ErroresEnum.DATOS_OBLIGATORIOS);
		}
	}

	private LocalDateTime horaActual() {
		ZoneId zoneId = ZoneId.of("America/Mexico_City");
		ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
		return zonedDateTime.toLocalDateTime();
	}

	// descarga archivo - descargar-ddp
	@Override
	public String descargarArchivo(String path, Long dictamenId) {

		verDetallePenasDeduccionesProforma(dictamenId);

		return this.fileService.descargarArchivo(path);
	}

	public void verDetallePenasDeduccionesProforma(Long dictamenId) {
		try {

			StringBuilder builder = new StringBuilder();

			builder.append(DICTAMEN_ID).append(dictamenId).append(" | ");

			// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),

			// TipoSeccionPista.DICTAMEN_DEDUCCIONES_DESCUENTOS.getIdSeccionPista(), builder.toString(),

			// Optional.empty());
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_GUARDAR_PISTA, e);
		}
	}

	// busqueda dictamen id --obtener-archivo-ddp

	@Override
	public ArchivoDto obtenerArchivoPorIdDictamen(Long idDictamen) {
		ArchivoPlantillaDictamenModel archivo = archivoRepo
				.findByDescripcionContainingAndCarpetaPlantillaModelIdDictamen(VERIFICADOR, idDictamen)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_CONTRATO_VACIO));

		String ruta = archivo.getRuta();
		String nombre = archivo.getNombre();

		StringBuilder builder = new StringBuilder();
		builder.append(DICTAMEN_ID).append(idDictamen).append(" | ").append("Nombre archivo: ")
				.append(archivo.getNombre()).append(" ");
		log.info(PISTA_GEN, builder.toString());



		// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


		// TipoSeccionPista.DICTAMEN_DEDUCCIONES_DESCUENTOS.getIdSeccionPista(), builder.toString(),


		// Optional.empty()); 

		return new ArchivoDto(ruta, nombre, archivo.getArchivoBase().isObligatorio() );
	}

	public void cargarArchivoConInformacionRenombrado(MultipartFile file, String path, String nombre) {
		try {
			boolean archivo = nexusImpl.cargarArchivo(file.getInputStream(), path, nombre);
			log.info("Archivo cargado: {},", archivo);

		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_GUARDAR_ARCHIVO, e);
		}
	}

	@Override
	public List<MonedaResponseDto> buscarMonedasPorIdDictamen(Long dictamenId) {

		// 1. Obtener DDP desde la tabla local
		List<DeduccionesDescuentosPenalizacionesModel> deduccionesList = ddpRepository
				.findByIdDictamenAndEstatusDeduccionTrueOrderByIdDdpAsc(dictamenId);

		// 2. Obtener penas contractuales
		List<PenasContractualesModel> penasContractuales = penasContractualesRepository
				.findByIdDictamenAndEstatusTrue(dictamenId);

		// 3. Obtener penas convencionales
		List<PenasConvencionalesModel> penasConvencionales = penasConvencionalesRepository
				.findByIdDictamenAndEstatusTrue(dictamenId);

		// 4. Obtener deducciones
		List<DeduccionesModel> deduccionModel = deduccionRepository.findByIdDictamenAndEstatusTrue(dictamenId);

		// Lista para almacenar las monedas con origen
		List<MonedaResponseDto> monedas = new ArrayList<>();

		monedas.addAll(deduccionesList.stream()
				.map(deduccion -> new MonedaResponseDto(
						deduccion.getDictamen().getContratoModel().getVigencia().getCatTipoMoneda().getNombre(),
						"DeduccionesDescuentosPenalizaciones"))
				.toList());

		monedas.addAll(penasContractuales.stream()
				.map(penaContractual -> new MonedaResponseDto(
						penaContractual.getDictamen().getContratoModel().getVigencia().getCatTipoMoneda().getNombre(),
						"Penas Contractuales"))
				.toList());

		monedas.addAll(penasConvencionales.stream()
				.map(penaConvencional -> new MonedaResponseDto(
						penaConvencional.getDictamen().getContratoModel().getVigencia().getCatTipoMoneda().getNombre(),
						"Penas Convencionales"))
				.toList());

		monedas.addAll(deduccionModel.stream()
				.map(deduccion -> new MonedaResponseDto(
						deduccion.getDictamen().getContratoModel().getVigencia().getCatTipoMoneda().getNombre(),
						"Deducción"))
				.toList());

		// Si no se encontraron registros en ninguno de los módulos, devuelve la moneda
		// por defecto del Dictamen
		if (monedas.isEmpty()) {
			List<Dictamen> dictamenes = dictamenRepository.findByIdDictamenAndEstatusTrue(dictamenId);

			if (!dictamenes.isEmpty()) {
				Dictamen dictamen = dictamenes.get(0);
				String monedaPorDefecto = dictamen.getContratoModel().getVigencia().getCatTipoMoneda().getNombre();
				monedas.add(new MonedaResponseDto(monedaPorDefecto, "Moneda por defecto del dictamen"));
			} else {
				throw new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND);
			}
		}

		return monedas;
	}

	@Override
	public Boolean rechazarProforma(Long idDictamen, String justificacion) {

		ObjectMapper objectMapper = new ObjectMapper();
		String nombreEstatus;

		try {
			JsonNode estatusNode = objectMapper.readTree(Constantes.ESTATUS_INICIAL);
			nombreEstatus = estatusNode.get("nombre").asText();
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS, e);
		}

		Dictamen dictamen = dictamenRepository.findByIdDictamen(idDictamen)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));

		List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.ESTATUS_DICTAMEN.getIdCatalogo(), Constantes.ESTATUS_INICIAL);

		dictamen.setIdEstatusDictamen(lista.get(0).getPrimaryKey());
		dictamen.setDescripcion("Motivo del rechazo: " + justificacion + "|" + dictamen.getDescripcion());
		dictamen.setUltimaModificacion(dictamenService.ultimaModificacionGeneral());

		// pista de auditoría- Actualizar
		StringBuilder builder = new StringBuilder();
		builder.append(DICTAMEN_ID).append(dictamen.getIdDictamen()).append(" | ").append("Estatus dictamen: ")
				.append(nombreEstatus);



		// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),


		// TipoSeccionPista.DICTAMEN_DEDUCCIONES_DESCUENTOS.getIdSeccionPista(), builder.toString(),


		// Optional.empty());

		return true;
	}

	@Override
	public Boolean validarDictamen(Long idDictamen) {
		ObjectMapper objectMapper = new ObjectMapper();
		String nombreEstatus;

		try {
			JsonNode estatusNode = objectMapper.readTree(Constantes.ESTATUS_PROFORMA);
			nombreEstatus = estatusNode.get("nombre").asText();
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS, e);
		}

		// Buscar id dictamen
		Dictamen dictamen = dictamenRepository.findByIdDictamen(idDictamen)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));

		// Verificar si existe un archivo asociado al dictamen
		ArchivoDto archivo;
		try {
			archivo = obtenerArchivoPorIdDictamen(idDictamen);

			// Validar si la ruta del archivo es valida
			if ((archivo.getPath() == null || archivo.getPath().isEmpty()) && archivo.isObligatorio()) {
				throw new CatalogoException(ErroresEnum.ERROR_ARCHIVO_NO_ENCONTRADO);
			}
		} catch (CatalogoException e) {
			// Si no hay archivo o la validación, evita cambiar el estatus
			throw new CatalogoException(ErroresEnum.NO_HAY_ARCHIVO);
		}

		// Obtener el estatus "dictaminado" a proforma
		List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.ESTATUS_DICTAMEN.getIdCatalogo(), Constantes.ESTATUS_PROFORMA);

		// Cambiar el estatus
		dictamen.setIdEstatusDictamen(lista.get(0).getPrimaryKey());

		// Actualizar la fecha de última modificación
		dictamen.setUltimaModificacion(dictamenService.ultimaModificacionGeneral());

		// Guardar los cambios
		dictamenRepository.save(dictamen);

		// Pista auditoria- Actualizar

		StringBuilder builder = new StringBuilder();

		builder.append(ID_DICTAMEN).append(dictamen.getIdDictamen()).append(" | ").append("Estatus dictamen: ")
				.append(nombreEstatus);



		// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),


		// TipoSeccionPista.DICTAMEN_DATOS_GENERALES.getIdSeccionPista(), builder.toString(), Optional.empty());

		return true;
	}

	@Override
	public List<SubPlantilladorModel> obtenerPlantillasProforma() {

		return plantilladorMicriservicio.obtenerContenidoSubPlantillador();
	}

	@Override
	public String obtenerArchivoProforma(ProformaArchivoRequestDto request) {

		Map<String, String> datosGenerales = obtenerDatosGenerales(request.getIdDictamen());
		HtmlExcelListDto htmlExcelListDto = new HtmlExcelListDto();

		List<Map<String, String>> datos = this.getInformacionServiciosContrato(request.getIdDictamen());

		if (Boolean.TRUE.equals(request.getPlantilla())) {
			htmlExcelListDto.setIdSubPlantillador(request.getIdSubPlantillador().longValue());
			htmlExcelListDto.setDatosGenerales(new HashMap<>());
			htmlExcelListDto.setDatos(new ArrayList<>());
		} else {
			htmlExcelListDto.setIdSubPlantillador(request.getIdSubPlantillador().longValue());
			htmlExcelListDto.setDatosGenerales(datosGenerales);
			htmlExcelListDto.setDatos(datos);
		}

		// pista
		registrarPistaArchivoProforma(request.getIdDictamen(), request.getIdSubPlantillador().longValue());

		if (request.getTipoArchivo().equalsIgnoreCase("excel")) {
			try {
				byte[] archivoExcel = plantilladorMicriservicio.obtenerArchivoProformaExcel(htmlExcelListDto);
				return Base64.getEncoder().encodeToString(archivoExcel);

			} catch (Exception e) {
				throw new DevengadosException(ErroresEnum.NO_HAY_ARCHIVO_PROFORMA, e);
			}
		} else {
			try {
				byte[] archivo = plantilladorMicriservicio.obtenerArchivoProforma(htmlExcelListDto);
				return Base64.getEncoder().encodeToString(archivo);
			} catch (Exception e) {
				throw new DevengadosException(ErroresEnum.NO_HAY_ARCHIVO_PROFORMA, e);
			}

		}

	}

	// metodo para pistas:

	private void registrarPistaArchivoProforma(Long idDictamen, Long idTipoSubPlantillador) {
		StringBuilder builder = new StringBuilder();

		builder.append("Id proforma: ").append(idTipoSubPlantillador).append(" | ").append(DICTAMEN_ID)
				.append(idDictamen);



		// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),


		// TipoSeccionPista.DICTAMEN_SOLICITUD_FACTURA.getIdSeccionPista(), builder.toString(), Optional.empty());

	}

	private Map<String, String> obtenerDatosGenerales(Long dictamenId) {

		Dictamen dictamen = dictamenRepository.findByIdDictamen(dictamenId).orElse(null);
		Long idContrato = dictamen.getIdContrato();
		Long idProyecto = dictamen.getContratoModel().getProyecto().getIdProyecto();
		String idDictamen = dictamen.getIdDictamenVisual();

		FichaTecnicaResponse fichaTecnicaResponse = proyectoMicroservicio.obtenerFichaTecnica(idProyecto);
		String adminCentral = fichaTecnicaResponse.getAreaPlaneacion().getCatAdmonCentral().getAdministracion();

		ContratoDto contratoDto = contratoMicoservicio.obtenerContratoId(idContrato);
		log.info("Contrato nombre ", contratoDto.getNombreContrato());

		ProveedorModel proveedor = dictamen.getProveedorModel();

		List<DictaminadoModel> dictaminadoModels = dictaminadoRepository
				.findByIdDictamenAndSeleccionadoTrue(dictamenId);
		List<BigDecimal> totales = new ArrayList<>();

		for (DictaminadoModel dictaminado : dictaminadoModels) {
			BigDecimal precioUnitario = dictaminado.getServicioContratoModel().getPrecioUnitario() != null
					? dictaminado.getServicioContratoModel().getPrecioUnitario()
					: BigDecimal.valueOf(1);

			BigDecimal cantidadTotal = dictaminado.getCantidadTotalServicios();
			BigDecimal total = cantidadTotal.multiply(precioUnitario);
			totales.add(total);
		}
		BigDecimal totalSuma = totales.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
		String porcentajeStr = dictamen.getCatIva().getPorcentaje().toLowerCase();

		BigDecimal porcentajeIva = "no aplica".equals(porcentajeStr) ? BigDecimal.ZERO
				: new BigDecimal(porcentajeStr).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

		BigDecimal iva = totalSuma.multiply(porcentajeIva);
		BigDecimal importeFactura = totalSuma.add(iva);

		BigDecimal totalDedducciones = deduccionRepository.sumarMontosPorIdDictamen(dictamenId.toString());
		BigDecimal totalPenasContractuales = penasContractualesRepository
				.sumarMontosPorIdDictamen(dictamenId.toString());
		BigDecimal totalPenasConvencionales = penasConvencionalesRepository
				.sumarMontosPorIdDictamen(dictamenId.toString());

		BigDecimal totalPenalizaciones = (totalPenasContractuales != null ? totalPenasContractuales : BigDecimal.ZERO)
				.add(totalPenasConvencionales != null ? totalPenasConvencionales : BigDecimal.ZERO);

		BigDecimal ivaDeducciones = (totalDedducciones != null ? totalDedducciones : BigDecimal.ZERO)
				.multiply(porcentajeIva);
		BigDecimal importeDeducciones = (totalDedducciones != null ? totalDedducciones : BigDecimal.ZERO)
				.add(ivaDeducciones);

		String dictamenIdFiveDigits = idDictamen.substring(idDictamen.length() - 5);

		LocalDateTime periodoI = dictamen.getPeriodoInicio();
		LocalDateTime periodoF = dictamen.getPeriodoFin();
		String periodoInicio = periodoI.getDayOfMonth() + " de " + formatMonthSpanish(periodoI.getMonth()) + " de "
				+ periodoI.getYear();
		String periodoFin = periodoF.getDayOfMonth() + " de " + formatMonthSpanish(periodoF.getMonth()) + " de "
				+ periodoF.getYear();

		LocalDateTime contratoI = contratoDto.getFecha_inicio();
		LocalDateTime contratoF = contratoDto.getFecha_termino();

		String fechaInicioContrato = contratoI.getDayOfMonth() + " de " + formatMonthSpanish(contratoI.getMonth())
				+ " de " + contratoI.getYear();
		String fechaFinContrato = contratoF.getDayOfMonth() + " de " + formatMonthSpanish(contratoF.getMonth()) + " de "
				+ contratoF.getYear();

		LocalDateTime fechaE = LocalDateTime.now();
		String fechaElbaroacion = fechaE.getDayOfMonth() + " de " + formatMonthSpanish(fechaE.getMonth()) + " de "
				+ fechaE.getYear();
		String vigenciaContrato = fechaInicioContrato + "<br>" + fechaFinContrato;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String fechaInicioDictamen = dictamen.getPeriodoInicio().format(formatter);
		String fechaFinDictamen = dictamen.getPeriodoFin().format(formatter);
		String periodoDictamenFormat = fechaInicioDictamen + "-" + fechaFinDictamen;

		Map<String, String> datosGenerales = new HashMap<>();
		if (proveedor.getDirectorio() != null && !proveedor.getDirectorio().isEmpty()) {
			DirectorioProveedorModel directorioProveedor = proveedor.getDirectorio().get(0);
			datosGenerales.put("email_proveedor",
					Optional.ofNullable(directorioProveedor.getCorreoElectronico()).orElse("N/A"));
			datosGenerales.put("telefono_proveedor",
					Optional.ofNullable(directorioProveedor.getTelefonoCelular()).orElse("N/A"));
		}
		datosGenerales.put("Administracion_Central", adminCentral);
		datosGenerales.put("Nombre_largo_contrato", contratoDto.getNombreContrato());
		datosGenerales.put("Número_contrato", contratoDto.getNumeroContrato());
		datosGenerales.put("nombre_proveedor", proveedor.getNombreProveedor());
		datosGenerales.put("Id_dictamen", Optional.ofNullable(dictamenId.toString()).orElse("N/A"));
		datosGenerales.put("fecha_elaboracion", fechaElbaroacion);
		datosGenerales.put("periodo_de_inicio", Optional.of(periodoInicio).orElse("N/A"));
		datosGenerales.put("periodo_fin", Optional.of(periodoFin).orElse("N/A"));
		datosGenerales.put("domicilio_proveedor", Optional.ofNullable(proveedor.getDireccion()).orElse("N/A"));
		datosGenerales.put("vigencia_de_contrato", vigenciaContrato);
		datosGenerales.put("acuerdo_pago_contrato", Optional.ofNullable(contratoDto.getAcuerdo()).orElse("N/A"));
		datosGenerales.put("id_del_proyecto", contratoDto.getIdProyecto().toString());
		datosGenerales.put("rfc_proveedor", Optional.ofNullable(proveedor.getRfc()).orElse("N/A"));
		datosGenerales.put("id_5_digitos_dictamen", dictamenIdFiveDigits);
		datosGenerales.put("mes del periodo de inicio y termino del dictamen", periodoDictamenFormat);
		datosGenerales.put("total deducciones",
				formatNumber(totalDedducciones != null ? totalDedducciones : BigDecimal.ZERO));
		datosGenerales.put("Subtotal_deducciones",
				formatNumber(totalDedducciones != null ? totalDedducciones : BigDecimal.ZERO));
		datosGenerales.put("Iva_deducciones", formatNumber(ivaDeducciones));
		datosGenerales.put("Importe_deducciones", formatNumber(importeDeducciones));
		datosGenerales.put("total de las penalizaciones", Optional.of(formatNumber(totalPenalizaciones)).orElse("N/A"));
		datosGenerales.put("mes en curso", periodoDictamenFormat);
		datosGenerales.put("Subtotal", formatNumber(totalSuma));
		datosGenerales.put("IVA", formatNumber(iva));
		datosGenerales.put("Importe_factura", formatNumber(importeFactura));

		List<ParticipantesAdministracionModel> listaParticipantes = participantesContratoRepository
				.findByIdContratoAndEstatusTrueAndVigenteTrue(idContrato);
		String nombreAdminCentralContrato = "";
		String puesto = "";
		for (ParticipantesAdministracionModel participante : listaParticipantes) {
			String responsabilidad = participante.getCatResponsabilidad().getNombre();
			if (responsabilidad.equals("Verificador del contrato")) {
				nombreAdminCentralContrato = participante.getCatResponsabilidad().getNombre(); // VERIFICAR QUE SI SEA
																								// ESTE PUESTO O DEL
																								// PUESTO DEL USUARIO
				puesto = participante.getUsuario().getNombre();
			}
		}

		datosGenerales.put(VERIFICADOR, puesto);
		datosGenerales.put("Cargo", nombreAdminCentralContrato);

		return datosGenerales;
	}

	private List<Map<String, String>> getInformacionServiciosContrato(Long dictamenId) {

		List<DictaminadoModel> dictaminadoModels = dictaminadoRepository
				.findByIdDictamenAndSeleccionadoTrueOrderByIdDictaminadoAsc(dictamenId);
		List<Map<String, String>> datos = new ArrayList<>();

		for (DictaminadoModel dictaminado : dictaminadoModels) {
			BigDecimal precioUnitario = dictaminado.getServicioContratoModel().getPrecioUnitario() != null
					? dictaminado.getServicioContratoModel().getPrecioUnitario()
					: null;

			Map<String, String> item = new HashMap<>();

			BigDecimal cantidadTotal = dictaminado.getCantidadTotalServicios();
			BigDecimal total = cantidadTotal.multiply(precioUnitario);

			String unidadMedida = dictaminado.getServicioContratoModel().getCatTipoUnidad().getNombre();

			item.put("Cantidad_total_servicio", cantidadTotal.toString());
			item.put("Servicio", dictaminado.getServicioContratoModel().getConcepto());
			item.put("cantidad_de_medida", unidadMedida);
			item.put("Precio_unitario", formatNumber(precioUnitario));
			item.put("Total", formatNumber(total));

			datos.add(item);
		}
		return datos;
	}

	private String formatNumber(BigDecimal number) {
		if (number != null) {
			DecimalFormat decimalFormat = new DecimalFormat("$#,##0.00");
			return decimalFormat.format(number);
		}
		return "";
	}

	private String formatMonthSpanish(Month month) {
		if (month != null) {
			return month.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
		}
		return "";
	}

}
