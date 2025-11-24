package com.sisecofi.admindevengados.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.sisecofi.libreria.comunes.dto.dictamen.dictamenDto;
import com.sisecofi.libreria.comunes.dto.dictamen.DevengadoBusquedaResponse;
import com.sisecofi.admindevengados.dto.DictamenBusquedaDTO;
import com.sisecofi.admindevengados.dto.ResumenConsolidadoDto;
import com.sisecofi.admindevengados.microservicio.CatalogoMicroservicio;
import com.sisecofi.admindevengados.microservicio.ContratoMicoservicio;
import com.sisecofi.admindevengados.model.DeduccionesModel;
import com.sisecofi.admindevengados.model.DictaminadoModel;
import com.sisecofi.libreria.comunes.model.penasContractuales.PenasContractualesModel;
import com.sisecofi.libreria.comunes.model.plantilla.ArchivoPlantillaModel;
import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;
import com.sisecofi.admindevengados.model.PenasConvencionalesModel;
import com.sisecofi.admindevengados.model.ResumenConsolidadoModel;
import com.sisecofi.admindevengados.model.SolicitudFacturaModel;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.model.dictamenes.ReferenciaPagoModel;
import com.sisecofi.libreria.comunes.model.dictamenes.SolicitudPagoModel;
import com.sisecofi.libreria.comunes.model.dictamenes.factura.FacturaModel;
import com.sisecofi.libreria.comunes.model.dictamenes.factura.NotaCreditoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoOtroDocumentoDictamenModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoOtroDocumentoFaseDictamenModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoPlantillaDictamenModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.CarpetaPlantillaDictamenModel;
import com.sisecofi.admindevengados.repository.CatEstatusDictamenRepository;
import com.sisecofi.admindevengados.repository.ContratoRepository;
import com.sisecofi.admindevengados.repository.DeduccionRepository;
import com.sisecofi.admindevengados.repository.DictaminadoRepository;
import com.sisecofi.admindevengados.repository.PenasContractualesRepository;
import com.sisecofi.admindevengados.repository.PenasConvencionalesRepository;
import com.sisecofi.admindevengados.repository.ResumenConsolidadoRepository;
import com.sisecofi.admindevengados.repository.ServicioContratoRepository;
import com.sisecofi.admindevengados.repository.ServicioConvenioRepository;
import com.sisecofi.admindevengados.repository.SolicitudFacturaRepository;
import com.sisecofi.admindevengados.repository.VigenciaMontosRepository;
import com.sisecofi.admindevengados.repository.contrato.ParticipantesContratoRepository;
import com.sisecofi.admindevengados.repository.contrato.ProyectoRepository;
import com.sisecofi.admindevengados.repository.facturas.FacturasRepository;
import com.sisecofi.admindevengados.repository.facturas.NotasCreditoRepository;
import com.sisecofi.admindevengados.repository.gestion_documental.CarpetaPlantillaDictamenRepository;
import com.sisecofi.admindevengados.repository.solicitudpago.ReferenciaPagoRepository;
import com.sisecofi.admindevengados.repository.solicitudpago.SolicitudPagoRepository;
import com.sisecofi.admindevengados.repository.DictamenRepository;
import com.sisecofi.admindevengados.service.CatalogoService;
import com.sisecofi.admindevengados.service.DictamenService;
import com.sisecofi.admindevengados.service.PistaService;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.admindevengados.util.consumer.DictamenMap;
import com.sisecofi.admindevengados.util.enums.ErroresEnum;
import com.sisecofi.admindevengados.util.exception.CatalogoException;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoDto;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoDtoLigeroComun;
import com.sisecofi.libreria.comunes.dto.contrato.VigenciaMontoDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusDictamen;
import com.sisecofi.libreria.comunes.model.catalogo.CatIeps;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.contratos.ServicioContratoModel;
import com.sisecofi.libreria.comunes.model.contratos.VigenciaMontosModel;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ServicioConvenioModel;
import com.sisecofi.libreria.comunes.model.usuario.RolModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.util.ConstantesParaRutasSATCloud;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.libreria.comunes.util.exception.NexusException;
import com.sisecofi.libreria.comunes.util.sesion.Session;
import com.sisecofi.libreria.comunes.repository.ArchivoOtroDocumentoDictamenRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoOtroDocumentoFaseDictamenRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoPlantillaDictamenRepository;
import com.sisecofi.admindevengados.microservicio.PlantillaMicroRest;
import com.sisecofi.libreria.comunes.service.impl.NexusServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class DictamenServiceImpl implements DictamenService {

	private final DictamenRepository dictamenRepositoy;
	private final DictaminadoRepository dictaminadoRepository;
	private final ResumenConsolidadoRepository resumenConsolidadoRepository;
	private final PenasContractualesRepository penasContractualesRepository;
	private final PenasConvencionalesRepository penasConvencionalesRepository;
	private final DeduccionRepository deduccionRepository;
	private final PistaService pistaService;
	private final Session session;
	private final CatEstatusDictamenRepository catEstatusDictamenRepository;
	private final ContratoMicoservicio contratoServicio;
	private final CatalogoService catalogoService;
	private final CatalogoMicroservicio catalogoMicroservicio;
	private final CarpetaPlantillaDictamenRepository carpetaPlantillaDictamenRepository;
	private final PlantillaMicroRest plantillaMicroRest;
	private final ContratoRepository contratoRepository;
	private final ArchivoPlantillaDictamenRepository archivoPlantillaDictamenRepository;
	private final ArchivoOtroDocumentoFaseDictamenRepository archivoOtroDocumentoFaseRepository;
	private final FacturasRepository facturasRepository;
	private final NotasCreditoRepository notasCreditoRepository;
	private final ParticipantesContratoRepository participantesContratoRepository;
	private final ServicioContratoRepository servicioContratoRepository;
	private final ServicioConvenioRepository servicioConvenioRepository;
	private final FacturasRepository facturaRepository;
	private final SolicitudPagoRepository solicitudPagoRepository;
	private final ReferenciaPagoRepository referenciaPagoRepository;
	private final SolicitudFacturaRepository solicitudFacturaRepository;
	private final VigenciaMontosRepository vigenciaMontosRepository;
	private final ProyectoRepository proyectoRepository;
	private final Optional<Usuario> usuario;
	private final NexusServiceImpl nexusImpl;
	private final ArchivoOtroDocumentoFaseDictamenRepository archivoOtroDocumentoFaseDictamenRepository;
	private final ArchivoOtroDocumentoDictamenRepository archivoOtroDocumentoDictamenRepository;
	private final ArchivoPlantillaDictamenRepository archivoRepo;
	private static final String PENAS_DEDUCCIONES = "02_PYD";
	private static final String RUTA_BASE = "/DICTAMENES/";
	private static final String RUTA_BASE_OTROS = "/OTROS";
	LocalDateTime hoy = LocalDateTime.now();

	public Optional<Usuario> obtenerUsuario() {
		return session.retornarUsuario();
	}

	private LocalDateTime obtenerFechaYHoraActual() {
		return ZonedDateTime.now(ZoneOffset.UTC).withZoneSameInstant(ZoneId.of("America/Mexico_City"))
				.toLocalDateTime();
	}

	private String obtenerFechaYHoraActualFormateado() {
		LocalDateTime localDateTime = obtenerFechaYHoraActual();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		return localDateTime.format(formatter);
	}

	@Transactional
	@Override
	public Integer buscarEstatusDictamen(String estatus) {
		CatEstatusDictamen estatusDictamen = catEstatusDictamenRepository.findByNombre(estatus);
		return estatusDictamen.getIdEstatusDictamen();
	}

	@Transactional
	@Override
	public String ultimaModificacionGeneral() {
		String ultimaModificacion = "";
		Optional<Usuario> usuarioOp = obtenerUsuario();

		if (usuarioOp.isPresent()) {
			ultimaModificacion = usuarioOp.get().getNombre() + " " + obtenerFechaYHoraActualFormateado();
		} else {
			log.info("Usuario no encontrado, se asigna null a ultimaModificación");
			ultimaModificacion = null + " " + obtenerFechaYHoraActualFormateado();
		}

		return ultimaModificacion;
	}

	@Transactional
	@Override
	public String generarFormatoIdDictamen(String nombreCorto, Long idProveedor) {
		int nuevoConsecutivo = obtenerUltimoConsecutivo(nombreCorto, idProveedor) + 1;

		return String.format("%s|%05d|%05d", nombreCorto, idProveedor, nuevoConsecutivo);
	}

	private int obtenerUltimoConsecutivo(String nombreCorto, Long idProveedor) {
		return dictamenRepositoy.obtenerSiguienteConsecutivoNombreCorto(nombreCorto, idProveedor);
	}

	public Integer generarConsecutivo(Long idContrato, Long idProveedor) {
		return dictamenRepositoy.obtenerSiguienteConsecutivo(idContrato, idProveedor);
	}

	@Transactional
	@Override
	public Integer validarTipoCambio(Long idContrato) {
		try {
			return vigenciaMontosRepository.findIdTipoMonedaByIdContratoAndEstatusTrue(idContrato)
					.orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR));
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.MSJ_NOT_FOUND_DICTAMEN, e);
		}
	}

	@Transactional
	@Override
	public Dictamen guardarDictamen(Long idContrato, dictamenDto dictamendto) {
		log.info("id contrato {}", idContrato);

		Dictamen dictamen = inicializarDictamen(dictamendto);
		ContratoDto contrato = contratoServicio.obtenerContratoId(idContrato);
		validarPeriodos(dictamendto, contrato);

		asignarValoresFinales(dictamendto, dictamen);

		dictamen.setPlantillaAsignada(false);
		String nombreCorto = contratoRepository.findNombreCortoByIdContratoAndEstatusTrue(idContrato)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));
		dictamen.setConsecutivo(generarConsecutivo(idContrato, dictamendto.getIdProovedor()));

		Dictamen dictamenGuardado = dictamenRepositoy.save(dictamen);

		Long idProyecto = proyectoRepository.findIdProyectoByIdContrato(idContrato)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.PROYECTO_NO_ENCONTRADO));

		crearArchivos(dictamenGuardado.getIdDictamen(), nombreCorto, idProyecto,
				dictamenGuardado.getPlantillaAsignada(),
				obtenerIdVisual(nombreCorto, dictamendto.getIdProovedor(), dictamen.getConsecutivo()));

		dictamenRepositoy.save(dictamenGuardado);

		guardarResumenConsolidado(dictamenGuardado);

		dictamenGuardado.setIdDictamenVisual(
				obtenerIdVisual(nombreCorto, dictamendto.getIdProovedor(), dictamen.getConsecutivo()));



		// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),


		// TipoSeccionPista.DICTAMEN_DATOS_GENERALES.getIdSeccionPista(),


		// Constantes.getAtributosDictamen()[0] + dictamenGuardado.getIdDictamen() + "|",


		// Optional.of(dictamenGuardado));

		return dictamenGuardado;
	}

	private Dictamen inicializarDictamen(dictamenDto dictamendto) {
		Dictamen dictamen = new Dictamen();

		dictamen.setIdProveedor(Long.parseLong("" + dictamendto.getIdProovedor()));
		dictamen.setIdContrato(dictamendto.getIdContrato());

		return dictamen;
	}

	private void validarPeriodoControl(dictamenDto dictamendto) {
		YearMonth mesPeriodoControl = YearMonth.from(dictamendto.getPeriodoControl());
		YearMonth mesPeriodoInicio = YearMonth.from(dictamendto.getPeriodoInicio());
		YearMonth mesPeriodoFin = YearMonth.from(dictamendto.getPeriodoFin());

		if (mesPeriodoControl.isBefore(mesPeriodoInicio) || mesPeriodoControl.isAfter(mesPeriodoFin)) {
			throw new CatalogoException(ErroresEnum.ERROR_PERIODO_CONTROL);
		}
	}

	private void asignarValoresFinales(dictamenDto dictamendto, Dictamen dictamen) {
		if (catEstatusDictamenRepository.existsById(dictamendto.getIdEstatusDictamen())) {
			dictamen.setIdEstatusDictamen(dictamendto.getIdEstatusDictamen());
		} else {
			throw new IllegalArgumentException("No existe el estatus");
		}

		dictamen.setEstatus(true);
		dictamen.setCheckContractual(true);
		dictamen.setCheckConvencional(true);
		dictamen.setCheckDeducciones(true);
		dictamen.setPeriodoInicio(dictamendto.getPeriodoInicio());
		dictamen.setIdPeriodoControlAnio(dictamendto.getPeriodoAnio());
		dictamen.setIdPeriodoControlMes(dictamendto.getPeriodoMes());
		dictamen.setPeriodoFin(dictamendto.getPeriodoFin());
		dictamen.setIdIva(dictamendto.getIdIva());
		dictamen.setTipoCambioReferencial(dictamendto.getTipoCambioReferencial());
		dictamen.setDescripcion(dictamendto.getDescripcion());
		dictamen.setUltimaModificacion(ultimaModificacionGeneral());
		dictamen.setFechaCreacion(obtenerFechaYHoraActual());
	}

	private void guardarResumenConsolidado(Dictamen dictamen) {
		List<ResumenConsolidadoModel> listaResumen = new ArrayList<>();
		for (String primaryKey : obtenerListaPrimaryKeys()) {
			ResumenConsolidadoModel resumen = new ResumenConsolidadoModel();
			resumen.setIdDictamen(dictamen.getIdDictamen());
			resumen.setSubTotal(BigDecimal.ZERO);
			resumen.setIdFaseDictamen(Integer.parseInt(primaryKey));
			resumen.setDeducciones(BigDecimal.ZERO);
			resumen.setIva(BigDecimal.ZERO);
			resumen.setIeps(BigDecimal.ZERO);
			resumen.setOtrosImpuestos(BigDecimal.ZERO);
			resumen.setTotal(BigDecimal.ZERO);
			resumen.setTotalPesos(BigDecimal.ZERO);
			listaResumen.add(resumen);
		}
		resumenConsolidadoRepository.saveAll(listaResumen);
	}

	private List<String> obtenerListaPrimaryKeys() {
		List<BaseCatalogoModel> listaFases = catalogoService.obtenerFasesDictamen();

		String faseDictamen = listaFases.toString();
		String[] arreglo = faseDictamen.substring(1, faseDictamen.length() - 1).split("}, ");

		List<String> listaPrimaryKeys = new ArrayList<>();

		for (String elemento : arreglo) {
			if (elemento.contains("primaryKey=")) {
				String nombre = elemento.replaceAll(".*primaryKey=(.*?),.*", "$1");
				listaPrimaryKeys.add(nombre);
				log.info("primaryKey id {}", nombre);
			} else {
				// La expresion regular debe contener |
				String primaryKey = elemento.replaceAll(".nombre=(.?)[,}].*", "$1");
				listaPrimaryKeys.add(primaryKey);
			}
		}
		return listaPrimaryKeys;
	}

	private String obtenerIdVisual(String nombreContrato, Long idProveedor, Integer consecutivo) {
		return nombreContrato + "|" + String.format("%05d", idProveedor) + "|" + String.format("%05d", consecutivo);
	}

	@Transactional
	@Override
	public void crearArchivos(Long dictamenId, String nombreCorto, Long idProyecto, Boolean plantillaAsignada,
			String idDictamenVisual) {
		try {
			CarpetaPlantillaModel car = plantillaMicroRest.obtenerArchivosVerificacion();
			String idFormato = idDictamenVisual.replace('|', '-');
			String rutaActual = generarRutaDictamen(idDictamenVisual, nombreCorto, idProyecto);

			if (Boolean.TRUE.equals(plantillaAsignada)) {

				crearArchivosComplemento(car, dictamenId, rutaActual, idFormato);

				log.info("La carpeta ya ha sido creada para el dictamen {}", dictamenId);
				return;
			}

			if (car != null) {

				dictamenRepositoy.actualizarPlantillaDictamen(dictamenId);
				log.info("entra");
				log.info("id dictamen: {}", dictamenId);
				CarpetaPlantillaDictamenModel carpeta = new CarpetaPlantillaDictamenModel();
				carpeta.setDescripcion(idDictamenVisual);
				carpeta.setNombre(idDictamenVisual);
				carpeta.setObligatorio(car.isObligatorio());
				carpeta.setEstatus(car.isEstatus());
				carpeta.setNivel(car.getNivel());
				carpeta.setOrden(car.getOrden());
				carpeta.setTipo(car.getTipo());
				carpeta.setCarpetaBase(car);

				carpeta.setRuta(rutaActual);
				carpeta.setIdDictamen(dictamenId);

				for (ArchivoPlantillaModel arc : car.getArchivos()) {
					carpeta.addArchivo(crearArchivo(arc, rutaActual, idFormato));
				}
				carpetaPlantillaDictamenRepository.save(carpeta);
			}
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_ASIGNAR_PLANTILLA, e);
		}
	}

	private void crearArchivosComplemento(CarpetaPlantillaModel car, Long dictamenId, String rutaActual,
			String idFormato) {
		Optional<ArchivoPlantillaDictamenModel> archivo = archivoRepo
				.findByNombreContainingAndCarpetaPlantillaModelIdDictamen(PENAS_DEDUCCIONES, dictamenId);

		if (!archivo.isPresent()) {
			List<CarpetaPlantillaDictamenModel> carpetas = carpetaPlantillaDictamenRepository
					.findByIdDictamen(dictamenId);
			CarpetaPlantillaDictamenModel carpeta = carpetas.get(0);

			if (carpetas.isEmpty()) {
				log.warn("No se encontró carpeta para el dictamen {}", dictamenId);
				return;
			}

			for (ArchivoPlantillaModel arc : car.getArchivos()) {
				if (arc.getNombre().contains(PENAS_DEDUCCIONES)) {
					carpeta.addArchivo(crearArchivo(arc, rutaActual, idFormato));
				}

			}

			carpetaPlantillaDictamenRepository.save(carpeta);
		}
	}

	@Transactional
	@Override
	public Boolean sincronizarArchivos(Long idDictamen) throws NexusException {
		String path = RUTA_BASE + idDictamen;
		String pathOtros = path + RUTA_BASE_OTROS;
		List<String> archivos = nexusImpl.obtenerContenidoCarpetaLista(path);
		List<String> otrosArchivos = nexusImpl.obtenerContenidoCarpetaLista(pathOtros);
		List<CarpetaPlantillaDictamenModel> listaCarpetas = carpetaPlantillaDictamenRepository
				.findByNivelAndIdDictamenAndCarpetaBaseEstatusTrue(1, idDictamen);

		List<ArchivoPlantillaDictamenModel> arch = new ArrayList<>(listaCarpetas.get(0).getArchivosActivos());

		List<ArchivoOtroDocumentoFaseDictamenModel> listaDocsFase = archivoOtroDocumentoFaseDictamenRepository
				.findByIdDictamenAndEstatusTrue(idDictamen);

		archivosPlantillaYFase(archivos, path, idDictamen, arch, listaDocsFase);

		List<ArchivoOtroDocumentoDictamenModel> listaOtros = archivoOtroDocumentoDictamenRepository
				.findByIdDictamenAndEstatusTrueOrderById(idDictamen);

		otrosArchivos(otrosArchivos, pathOtros, idDictamen, listaOtros);

		return true;

	}

	private void archivosPlantillaYFase(List<String> archivos, String path, Long idDictamen,
			List<ArchivoPlantillaDictamenModel> arch, List<ArchivoOtroDocumentoFaseDictamenModel> listaDocsFase) {

		// Actualizar ArchivoPlantillaDictamenModel
		for (ArchivoPlantillaDictamenModel doc : arch) {
			String prefijoBaseModelo = obtenerPrefijoBase(doc.getNombre());

			Optional<String> archivoMatchOpt = archivos.stream().filter(nombreArchivo -> {
				String nombreSinExt = quitarExtension(nombreArchivo);
				String prefijoBaseArchivo = obtenerPrefijoBase(nombreSinExt);
				return prefijoBaseModelo.equalsIgnoreCase(prefijoBaseArchivo);
			}).findFirst();

			if (archivoMatchOpt.isPresent()) {
				String nombreArchivo = archivoMatchOpt.get();
				doc.setCargado(true);
				doc.setRuta(path + "/" + nombreArchivo);
			} else {
				doc.setCargado(false);
				doc.setRuta(null);
			}
			archivoPlantillaDictamenRepository.save(doc);
		}

		// Actualizar ArchivoOtroDocumentoFaseDictamenModel
		for (ArchivoOtroDocumentoFaseDictamenModel doc : listaDocsFase) {
		    String nombreSinExtModelo = quitarExtension(doc.getNombre());
		    String prefijoBaseModelo = obtenerPrefijoBase(nombreSinExtModelo);

		    Optional<String> archivoMatchOpt = archivos.stream()
		        .filter(nombreArchivo -> {
		            String nombreSinExt = quitarExtension(nombreArchivo);
		            String prefijoBaseArchivo = obtenerPrefijoBase(nombreSinExt);
		            return prefijoBaseModelo.equalsIgnoreCase(prefijoBaseArchivo);
		        })
		        .findFirst();

		    if (archivoMatchOpt.isPresent()) {
		        String nombreArchivo = archivoMatchOpt.get();
		        doc.setCargado(true);
		        doc.setRuta(path + "/" + nombreArchivo);
		    } else {
		        doc.setCargado(false);
		        doc.setRuta(null);
		    }
		    archivoOtroDocumentoFaseDictamenRepository.save(doc);
		}


		// Detectar archivos que no están en ninguna lista y crear nuevos en Fase
		for (String nombreArchivo : archivos) {
		    String nombreSinExt = quitarExtension(nombreArchivo);
		    String prefijoBaseArchivo = obtenerPrefijoBase(nombreSinExt);

		    boolean yaExisteEnPlantilla = arch.stream().anyMatch(doc -> {
		        String prefijoBaseModelo = obtenerPrefijoBase(doc.getNombre());
		        return prefijoBaseModelo.equalsIgnoreCase(prefijoBaseArchivo);
		    });

		    boolean yaExisteEnFase = listaDocsFase.stream().anyMatch(doc -> {
		        String nombreSinExtModelo = quitarExtension(doc.getNombre());
		        String prefijoBaseModelo = obtenerPrefijoBase(nombreSinExtModelo);
		        return prefijoBaseModelo.equalsIgnoreCase(prefijoBaseArchivo);
		    });

		    if (!yaExisteEnPlantilla && !yaExisteEnFase) {
		        ArchivoOtroDocumentoFaseDictamenModel nuevo = new ArchivoOtroDocumentoFaseDictamenModel();
		        nuevo.setIdDictamen(idDictamen);
		        nuevo.setNombre(nombreArchivo);
		        nuevo.setRuta(path + "/" + nombreArchivo);
		        nuevo.setCargado(true);
		        nuevo.setEstatus(true);
		        nuevo.setFechaModificacion(hoy);
		        nuevo.setCarpeta(path);
		        nuevo.setDescripcion(nombreArchivo);
		        nuevo.setNivel(1);
		        nuevo.setTipo("Archivo");
		        nuevo.setObligatorio(false);
		        nuevo.setNoAplica(false);

		        archivoOtroDocumentoFaseDictamenRepository.save(nuevo);
		    }
		}

	}

	
	private String obtenerPrefijoBase(String nombre) {
	    int lastUnderscore = nombre.lastIndexOf('_');
	    if (lastUnderscore != -1) {
	        return nombre.substring(0, lastUnderscore + 1); // incluye el "_"
	    } else {
	        return nombre;
	    }
	}

	private String quitarExtension(String nombreArchivo) {
	    return nombreArchivo.contains(".")
	        ? nombreArchivo.substring(0, nombreArchivo.lastIndexOf('.'))
	        : nombreArchivo;
	}

	
	private void otrosArchivos(List<String> otrosArchivos, String pathOtros, Long idDictamen,
			List<ArchivoOtroDocumentoDictamenModel> listaOtros) {
		for (String nombreArchivo : otrosArchivos) {
			Optional<ArchivoOtroDocumentoDictamenModel> existenteOpt = listaOtros.stream()
					.filter(doc -> doc.getNombre().equalsIgnoreCase(nombreArchivo)).findFirst();

			if (existenteOpt.isPresent()) {
				ArchivoOtroDocumentoDictamenModel existente = existenteOpt.get();
				existente.setRuta(pathOtros + "/" + nombreArchivo);
				existente.setCargado(true);
				archivoOtroDocumentoDictamenRepository.save(existente);
			} else {
				ArchivoOtroDocumentoDictamenModel nuevo = new ArchivoOtroDocumentoDictamenModel();
				nuevo.setIdDictamen(idDictamen);
				nuevo.setNombre(nombreArchivo);
				nuevo.setRuta(pathOtros + "/" + nombreArchivo);
				nuevo.setCargado(true);
				nuevo.setEstatus(true);
				nuevo.setFechaModificacion(hoy);
				nuevo.setCarpeta(pathOtros);
				nuevo.setDescripcion(nombreArchivo);
				nuevo.setNivel(1);
				nuevo.setTipo("Archivo");
				nuevo.setObligatorio(false);
				nuevo.setNoAplica(false);

				archivoOtroDocumentoDictamenRepository.save(nuevo);
			}
		}

		for (ArchivoOtroDocumentoDictamenModel doc : listaOtros) {
			boolean existeEnRepo = otrosArchivos.stream()
					.anyMatch(nombreArchivo -> doc.getNombre().equalsIgnoreCase(nombreArchivo));

			if (!existeEnRepo) {
				doc.setCargado(false);
				doc.setRuta(null);
				doc.setTamanoMb(null);
				archivoOtroDocumentoDictamenRepository.save(doc);
			}
		}
	}

	@Transactional
	@Override
	public String generarRuta(String idDictamen, ContratoModel contrato) {
		String idFormato = idDictamen.replace('|', '-');
		Long idProyecto = contrato.getProyecto().getIdProyecto();
		return ConstantesParaRutasSATCloud.PATH_BASE + "/" + idProyecto + "/"
				+ ConstantesParaRutasSATCloud.PATH_BASE_CONTRATOS + "/" + contrato.getNombreCorto() + "/"
				+ ConstantesParaRutasSATCloud.PATH_DICTAMEN_VERIFICACION + idFormato + "/verificacion/" + idFormato;
	}

	@Transactional
	@Override
	public String generarRutaDictamen(String idDictamen, String nombrecorto, Long idProyecto) {
		String idFormato = idDictamen.replace('|', '-');
		return ConstantesParaRutasSATCloud.PATH_BASE + "/" + idProyecto + "/"
				+ ConstantesParaRutasSATCloud.PATH_BASE_CONTRATOS + "/" + nombrecorto + "/"
				+ ConstantesParaRutasSATCloud.PATH_DICTAMEN_VERIFICACION + idFormato + "/verificacion/" + idFormato;
	}

	@Transactional
	@Override
	public String generarRutaProforma(String idDictamen, ContratoModel contrato) {
		String idFormato = idDictamen.replace('|', '-');
		Long idProyecto = contrato.getProyecto().getIdProyecto();
		return ConstantesParaRutasSATCloud.PATH_BASE + "/" + idProyecto + "/"
				+ ConstantesParaRutasSATCloud.PATH_BASE_CONTRATOS + "/" + contrato.getNombreCorto() + "/"
				+ ConstantesParaRutasSATCloud.PATH_DICTAMEN_VERIFICACION + idFormato + "/proforma/";
	}

	@Transactional
	@Override
	public String generarRutaFase(String idDictamen, ContratoModel contrato) {
		String idFormato = idDictamen.replace('|', '-');
		Long idProyecto = contrato.getProyecto().getIdProyecto();
		return ConstantesParaRutasSATCloud.PATH_BASE + "/" + idProyecto + "/"
				+ ConstantesParaRutasSATCloud.PATH_BASE_CONTRATOS + "/" + contrato.getNombreCorto() + "/"
				+ ConstantesParaRutasSATCloud.PATH_DICTAMEN_VERIFICACION + idFormato + "/verificacion/"
				+ ConstantesParaRutasSATCloud.PATH_OTROS_DOCUMENTOS;
	}

	@Transactional
	@Override
	public String generarRutaDictamen(String idDictamen, ContratoModel contrato) {
		String idFormato = idDictamen.replace('|', '-');
		Long idProyecto = contrato.getProyecto().getIdProyecto();
		return ConstantesParaRutasSATCloud.PATH_BASE + "/" + idProyecto + "/"
				+ ConstantesParaRutasSATCloud.PATH_BASE_CONTRATOS + "/" + contrato.getNombreCorto() + "/"
				+ ConstantesParaRutasSATCloud.PATH_DICTAMEN_VERIFICACION + idFormato + "/"
				+ ConstantesParaRutasSATCloud.PATH_OTROS_DOCUMENTOS;
	}

	private ArchivoPlantillaDictamenModel crearArchivo(ArchivoPlantillaModel arc, String carpeta, String idFormato) {
		ArchivoPlantillaDictamenModel archivo = new ArchivoPlantillaDictamenModel();
		archivo.setDescripcion(arc.getDescripcion());
		archivo.setEstatus(arc.isEstatus());
		archivo.setIdArchivoBase(arc.getIdArchivoPlantilla());
		if (arc.getNombre().contains("05_PYD")) {
			archivo.setNombre(arc.getNombre() + idFormato + "_Verificador");
		} else if (arc.getNombre().contains(PENAS_DEDUCCIONES)) {
			archivo.setNombre(arc.getNombre() + idFormato);
		} else {
			archivo.setNombre(arc.getNombre() + idFormato);
		}
		archivo.setNivel(arc.getNivel());
		archivo.setNoAplica(false);
		archivo.setTipo(arc.getTipo());
		archivo.setObligatorio(arc.isObligatorio());
		archivo.setCarpeta(carpeta);
		return archivo;
	}

	@Transactional
	@Override
	public List<Dictamen> obtenerDictamenes() {
		log.info("Dictamenes: {}", dictamenRepositoy.findAll());

		return dictamenRepositoy.findAll();

	}

	@Transactional
	@Override
	public ContratoDto obtenerContratoDto(Long idContrato) {
		ContratoDto contrato = contratoServicio.obtenerContratoId(idContrato);
		VigenciaMontoDto listaVigencia = contratoServicio.obtenerVigenciaMonto(idContrato);

		contrato.setIdIva(listaVigencia.getIva().getIdIva());
		return contrato;

	}

	@Transactional
	@Override
	public ContratoDtoLigeroComun obtenerContratoLigero(Long idContrato) {
		return contratoServicio.obtenerContratoIdLigero(idContrato);
	}

	@Transactional
	@Override
	public List<Dictamen> obtenerDictamenesByEstatus(String nombreEstatus, Long idContrato) {
		return dictamenRepositoy.findByCatEstatusDictamenNombreAndIdContrato(nombreEstatus, idContrato);
	}

	@Transactional
	@Override
	public boolean cancelarDictamen(Long dictamenId, String descripcion) {

		Dictamen dictamen = dictamenRepositoy.findByIdDictamen(dictamenId)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));

		if (dictamen.getCatEstatusDictamen().getNombre().equals("Inicial")
				|| dictamen.getCatEstatusDictamen().getNombre().equals("Dictaminado")
				|| dictamen.getCatEstatusDictamen().getNombre().equals("Proforma")) {

			List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
					CatalogosComunes.ESTATUS_DICTAMEN.getIdCatalogo(), Constantes.ESTATUS_CANCELADO);
			dictamen.setIdEstatusDictamen(lista.get(0).getPrimaryKey());
			dictamen.setDescripcion("Motivo de la cancelación: " + descripcion + "|" + dictamen.getDescripcion());
			dictamen.setUltimaModificacion(ultimaModificacionGeneral());



			// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),


			// TipoSeccionPista.DICTAMEN_DATOS_GENERALES.getIdSeccionPista(),


			// Constantes.getAtributosDictamen()[0] + dictamen.getIdDictamen() + "|", Optional.of(dictamen));

			dictamenRepositoy.save(dictamen);

			return true;

		} else {
			return false;
		}
	}

	@Transactional
	@Override
	public dictamenDto obtenerDictamenesId(Long dictamenId) {
		try {

			dictamenDto dictamenActual = dictamenRepositoy.findDictamenDtoById(dictamenId)
					.orElseThrow(() -> new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));

			Optional<String> vigenciaOptional = vigenciaMontosRepository
					.findPorcentajeIepsByIdContratoAndEstatusTrue(dictamenActual.getIdContrato());

			BigDecimal montoIeps = dictaminadoRepository.calcularMontoIeps(obtenerIeps(vigenciaOptional), dictamenId,
					dictamenActual.getIdContrato(), dictamenActual.getIdConvenioModificatorio());
			montoIeps = Optional.ofNullable(montoIeps).map(BigDecimal::stripTrailingZeros).orElse(BigDecimal.ZERO);
			dictamenActual.setIeps(montoIeps);

			crearPlantilla(dictamenId, dictamenActual.getNombreCortoContrato(), dictamenActual.getIdProyecto(),
					dictamenActual.getPlantillaAsignada(), obtenerIdVisual(dictamenActual.getNombreCortoContrato(),
							dictamenActual.getIdProovedor(), dictamenActual.getConsecutivo()));
			return dictamenActual;
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.MSJ_NOT_FOUND_DICTAMEN, e);
		}
	}

	private BigDecimal obtenerIeps(Optional<String> porcentaje) {
		if (porcentaje.isPresent()) {
			try {
				return new BigDecimal(porcentaje.get()).divide(BigDecimal.valueOf(100));
			} catch (NumberFormatException | ArithmeticException | NullPointerException e) {
				return BigDecimal.ZERO;
			}
		}
		return BigDecimal.ZERO;
	}

	private Boolean crearPlantilla(Long dictamenId, String nombreCorto, Long idProyecto, Boolean plantillaAsignada,
			String idDictamenVisual) {
		crearArchivos(dictamenId, nombreCorto, idProyecto, plantillaAsignada, idDictamenVisual);
		return true;
	}

	@Transactional
	@Override
	public List<Dictamen> anterior(Long dictamenId) {
		try {
			Dictamen dictamenBusqueda = dictamenRepositoy.findByIdDictamen(dictamenId)
					.orElseThrow(() -> new CatalogoException(ErroresEnum.MSJ_NOT_FOUND_DICTAMEN));

			List<Dictamen> dictamenes = dictamenRepositoy.findByIdContratoAndIdProveedorOrderByIdDictamenAsc(
					dictamenBusqueda.getIdContrato(), dictamenBusqueda.getIdProveedor());
			List<Dictamen> dictamenAnterior = new ArrayList<>();
			log.info("id contrato: {}", dictamenBusqueda.getIdContrato());

			int posicionActual = -1;

			for (int i = 0; i < dictamenes.size(); i++) {
				if (dictamenes.get(i).getIdDictamen().equals(dictamenId)) {
					posicionActual = i;
					break;
				}
			}

			if (posicionActual > 0) {
				dictamenAnterior.add(dictamenes.get(posicionActual - 1));
			} else if (posicionActual == 0) {
				throw new CatalogoException(ErroresEnum.NO_EXISTE_INFORMACION);
			}

			return dictamenAnterior;
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.NO_EXISTE_INFORMACION);
		}
	}

	@Transactional
	@Override
	public List<Dictamen> siguiente(Long dictamenId) {
		try {

			Dictamen dictamenBusqueda = dictamenRepositoy.findByIdDictamen(dictamenId)
					.orElseThrow(() -> new CatalogoException(ErroresEnum.MSJ_NOT_FOUND_DICTAMEN));

			List<Dictamen> dictamenes = dictamenRepositoy.findByIdContratoAndIdProveedorOrderByIdDictamenAsc(
					dictamenBusqueda.getIdContrato(), dictamenBusqueda.getIdProveedor());
			List<Dictamen> dictamenSiguiente = new ArrayList<>();
			int posicionActual = -1;

			for (int i = 0; i < dictamenes.size(); i++) {

				if (dictamenes.get(i).getIdDictamen().equals(dictamenId)) {
					posicionActual = i;
					break;
				}
			}
			if (posicionActual >= 0) {
				dictamenSiguiente.add(dictamenes.get(posicionActual + 1));
			} else if (posicionActual == dictamenes.size()) {
				throw new CatalogoException(ErroresEnum.NO_EXISTE_INFORMACION);
			}

			return dictamenSiguiente;
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.NO_EXISTE_INFORMACION);
		}
	}

	@Transactional
	@Override
	public Integer validarDictamenAnteriorYSiguiente(Long dictamenId) {
		try {
			Dictamen dictamenBusqueda = dictamenRepositoy.findByIdDictamen(dictamenId)
					.orElseThrow(() -> new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));

			List<Dictamen> dictamenes = dictamenRepositoy.findByIdContratoAndIdProveedorOrderByIdDictamenAsc(
					dictamenBusqueda.getIdContrato(), dictamenBusqueda.getIdProveedor());

			int posicionActual = -1;

			for (int i = 0; i < dictamenes.size(); i++) {
				if (dictamenes.get(i).getIdDictamen().equals(dictamenId)) {
					posicionActual = i;
					break;
				}
			}

			boolean existeAnterior = posicionActual > 0;
			boolean existeSiguiente = posicionActual < dictamenes.size() - 1;

			if (existeAnterior && existeSiguiente)
				return 2;
			if (existeAnterior)
				return 0;
			if (existeSiguiente)
				return 1;
			return -1;
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.MSJ_NOT_FOUND_DICTAMEN, e);
		}
	}

	@Transactional
	@Override
	public List<Dictamen> obtenerContratosDictamen(Long idContrato) {
		return dictamenRepositoy.findByIdContratoAndEstatusTrueOrderByIdDictamenAsc(idContrato);
	}

	@Transactional
	@Override
	public Dictamen editarDictamen(Long dictamenId, dictamenDto dictamendto) {

		Dictamen dictamenActual = obtenerDictamenExistente(dictamenId);
		ContratoDto contrato = contratoServicio.obtenerContratoId(dictamenActual.getIdContrato());

		validarPeriodos(dictamendto, contrato);
		validarPeriodoControl(dictamendto);

		asignarValoresDictamen(dictamendto, dictamenActual);



		// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),


		// TipoSeccionPista.DICTAMEN_DATOS_GENERALES.getIdSeccionPista(),


		// Constantes.getAtributosDictamen()[0] + dictamenActual.getIdDictamen() + "|",


		// Optional.of(dictamenActual));

		return dictamenRepositoy.save(dictamenActual);
	}

	private Dictamen obtenerDictamenExistente(Long dictamenId) {
		return dictamenRepositoy.findByIdDictamen(dictamenId)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.MSJ_NOT_FOUND_DICTAMEN));
	}

	private void validarPeriodos(dictamenDto dictamendto, ContratoDto contrato) {
		validarPeriodoInicio(dictamendto, contrato);
		validarPeriodoFin(dictamendto, contrato);
	}

	private void validarPeriodoInicio(dictamenDto dictamendto, ContratoDto contrato) {
		LocalDateTime fechaInicio = contrato.getFecha_inicio();
		fechaInicio = fechaInicio.withHour(0).withMinute(0).withSecond(0).withNano(0);
		contrato.setFecha_inicio(fechaInicio);

		LocalDateTime fechaTermino = contrato.getFecha_termino();
		fechaTermino = fechaTermino.withHour(0).withMinute(0).withSecond(0).withNano(0);
		contrato.setFecha_termino(fechaTermino);

		if (dictamendto.getPeriodoInicio().isAfter(dictamendto.getPeriodoFin())
				|| dictamendto.getPeriodoInicio().isBefore(contrato.getFecha_inicio())
				|| dictamendto.getPeriodoInicio().isAfter(contrato.getFecha_termino())) {
			throw new CatalogoException(ErroresEnum.ERROR_PERIODO_INICIO);
		}
	}

	private void validarPeriodoFin(dictamenDto dictamendto, ContratoDto contrato) {
		if (dictamendto.getPeriodoFin().isBefore(dictamendto.getPeriodoInicio())
				|| dictamendto.getPeriodoFin().isBefore(contrato.getFecha_inicio())
				|| dictamendto.getPeriodoFin().isAfter(contrato.getFecha_termino())) {
			throw new CatalogoException(ErroresEnum.ERROR_PERIODO_FIN);
		}
	}

	private void asignarValoresDictamen(dictamenDto dictamendto, Dictamen dictamenActual) {
		dictamenActual.setPeriodoInicio(dictamendto.getPeriodoInicio());
		dictamenActual.setPeriodoFin(dictamendto.getPeriodoFin());
		dictamenActual.setIdIva(dictamendto.getIdIva());
		dictamenActual.setIdPeriodoControlAnio(dictamendto.getPeriodoAnio());
		dictamenActual.setIdPeriodoControlMes(dictamendto.getPeriodoMes());
		dictamenActual.setTipoCambioReferencial(dictamendto.getTipoCambioReferencial());
		dictamenActual.setDescripcion(dictamendto.getDescripcion());

		Optional<Usuario> usuarioOpt = obtenerUsuario();
		if (usuarioOpt.isPresent()) {
			dictamenActual
					.setUltimaModificacion(usuarioOpt.get().getNombre() + " " + obtenerFechaYHoraActualFormateado());
		} else {
			throw new CatalogoException(ErroresEnum.ERROR_USUARIO_NO_ENCONTRADO);
		}
	}

	@Transactional
	@Override
	public List<ResumenConsolidadoDto> obtenerResumenConsolidado(Long dictamenId) {
		try {
			Locale locale = Locale.forLanguageTag("es-MX");
			NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

			numberFormat.setMinimumFractionDigits(2);
			numberFormat.setMaximumFractionDigits(2);

			List<ResumenConsolidadoDto> listaResumen = new ArrayList<>();
			List<ResumenConsolidadoModel> dictamenDictaminado = resumenConsolidadoRepository
					.findByIdDictamenOrderByIdResumenConsolidadoAsc(dictamenId);

			if (dictamenDictaminado == null || dictamenDictaminado.isEmpty()) {

				Dictamen dictamen = dictamenRepositoy.findByIdDictamen(dictamenId)
						.orElseThrow(() -> new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));
				guardarResumenConsolidado(dictamen);
			}

			actualizarResumenConsolidado(dictamenId);
			actualizarResumeFacturado(dictamenId);

			for (ResumenConsolidadoModel resumenConsolidadoModel : dictamenDictaminado) {
				ResumenConsolidadoDto resumen = new ResumenConsolidadoDto();
				resumen.setFase("" + resumenConsolidadoModel.getIdFaseDictamen());
				resumen.setNombreFase(resumenConsolidadoModel.getCatFaseDictamen().getNombre());
				resumen.setIdDictamen(resumenConsolidadoModel.getIdDictamen());

				// Formatear los números con comas y dos decimales para BigDecimal
				resumen.setSubTotal(numberFormat.format(resumenConsolidadoModel.getSubTotal()));
				resumen.setDeducciones(numberFormat.format(resumenConsolidadoModel.getDeducciones()));
				resumen.setIeps(numberFormat.format(resumenConsolidadoModel.getIeps()));
				resumen.setIva(numberFormat.format(resumenConsolidadoModel.getIva()));
				resumen.setOtrosImpuestos(numberFormat.format(resumenConsolidadoModel.getOtrosImpuestos()));
				resumen.setTotal(numberFormat.format(resumenConsolidadoModel.getTotal()));
				resumen.setTotalPesos(numberFormat.format(resumenConsolidadoModel.getTotalPesos()));

				listaResumen.add(resumen);
			}

			return listaResumen;
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND, e);
		}

	}

	@Transactional
	@Override
	public BigDecimal generarMonto(Long dictamenId) {
		try {

			List<ResumenConsolidadoModel> resumenConsolidado = resumenConsolidadoRepository
					.findByIdDictamenOrderByIdResumenConsolidadoAsc(dictamenId);

			return resumenConsolidado.stream().map(ResumenConsolidadoModel::getTotalPesos).reduce(BigDecimal.ZERO,
					BigDecimal::add);

		} catch (Exception e) {
			log.error("dictamen no encontrado {}", dictamenId);
			throw new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND, e);
		}

	}

	@Transactional
	@Override
	public List<DevengadoBusquedaResponse> obtenerDictamenesByEstatusAndProveedor(Long idContrato,
			Integer idEstatusDictamen, Long idProveedor) {
		List<DictamenBusquedaDTO> dictamenes = dictamenRepositoy.findDictamenesOptimizado(idContrato, idEstatusDictamen,
				idProveedor);

		if (dictamenes.isEmpty())
			return Collections.emptyList();

		List<Long> idDictamenes = dictamenes.stream().map(DictamenBusquedaDTO::getIdDictamen).toList();
		List<Long> idContratos = dictamenes.stream().map(DictamenBusquedaDTO::getIdContrato).toList();

		Map<Long, Object[]> resumenConsolidadoMap = resumenConsolidadoRepository.findResumenByIdDictamenIn(idDictamenes)
				.stream().collect(Collectors.toMap(r -> (Long) r[0], r -> r, (existing, replacement) -> existing));

		Map<Long, List<Object[]>> facturasMap = facturasRepository.findFacturasByIdDictamenIn(idDictamenes).stream()
				.collect(Collectors.groupingBy(f -> (Long) f[0]));
				
						

		Map<Long, List<Object[]>> participantesContratoMap = participantesContratoRepository
				.findParticipantesByIdContratoIn(idContratos).stream().collect(Collectors.groupingBy(p -> (Long) p[0]));

		return dictamenes.stream().map(t -> {
			DevengadoBusquedaResponse response = new DevengadoBusquedaResponse();
			response.setEstatus(t.getEstatus());
			response.setId(t.getIdDictamenVisual());
			response.setIdBd(t.getIdDictamen());
			response.setMontoDictaminadoPesos("$0.00");
			response.setPendientePago("0");
			response.setPeriodoControl(t.getPeriodoControl()); 
			response.setPeriodoInicio(t.getPeriodoInicio());
			response.setPeriodoFin(t.getPeriodoFin());
			response.setTipo("Dictamen");
			Object[] resumen = resumenConsolidadoMap.get(t.getIdDictamen());
			if (resumen != null) {
				response.setMontoDictaminado(String.format("$%,.2f", (BigDecimal) resumen[1]));
				response.setMontoDictaminadoPesos(String.format("$%,.2f", (BigDecimal) resumen[2]));
			}

			List<Object[]> facturas = facturasMap.getOrDefault(t.getIdDictamen(), Collections.emptyList());
			if (!facturas.isEmpty()) {
				List<String> comprobantesFiscales = facturas.stream()
						.map(f -> (String) f[1]) // Posición 1 = comprobante fiscal
						.filter(Objects::nonNull) // Filtrar nulls
						.toList();

				response.setComprobanteFiscal(String.join(", ", comprobantesFiscales));

				long pendientesPago = facturas.stream().filter(f -> {
					String estatus = (String) f[2]; // Posición 2 = estatus factura
					return !estatus.equalsIgnoreCase("Cancelado") && !estatus.equalsIgnoreCase("Pagado");
				}).count();

				response.setPendientePago(String.valueOf(pendientesPago));
			}

			List<Object[]> participantes = participantesContratoMap.getOrDefault(t.getIdContrato(),
					Collections.emptyList());
			boolean estatusResponsabilidad = determinarEstatusResponsabilidad(participantes);
			response.setEstatusResponsabilidad(estatusResponsabilidad);

			return response;
		}).toList();

	}

	private boolean determinarEstatusResponsabilidad(List<Object[]> participantes) {
		if (usuario.isPresent()) {
			List<RolModel> roles = usuario.get().getRolModels();
			boolean esAdminSistema = roles.stream().anyMatch(rol -> "cn=SAT_SISECOFI_ADMIN_SIS".equals(rol.getNombre())
					|| "cn=SAT_SISECOFI_ADMIN_SIS_SEC".equals(rol.getNombre()));

			if (esAdminSistema)
				return true;

			return participantes.stream()
					.anyMatch(participante -> esResponsabilidadValida((String) participante[1],
							"Participante en la administración de la verificación")
							|| esResponsabilidadValida((String) participante[1], "Verificador del contrato")
							|| esResponsabilidadValida((String) participante[1], "Verificador general"));
		}
		return false;
	}

	private boolean esResponsabilidadValida(String nombreResponsabilidad, String responsabilidadEsperada) {
		return nombreResponsabilidad.equals(responsabilidadEsperada);
	}

	@Transactional
	@Override
	public Dictamen actualizarEstatusInicial(Long dictamenId) {
		log.info("dictamenId {}", dictamenId);
		Dictamen dictamenActual = dictamenRepositoy.findByIdDictamen(dictamenId)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.MSJ_NOT_FOUND_DICTAMEN));

		List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.ESTATUS_DICTAMEN.getIdCatalogo(), Constantes.ESTATUS_INICIAL);

		dictamenActual.setIdEstatusDictamen(lista.get(0).getPrimaryKey());
		dictamenRepositoy.save(dictamenActual);

		SolicitudFacturaModel solicitud = solicitudFacturaRepository.findByDictamenIdAndDictamenEstatusTrue(dictamenId);

		if (solicitud != null) {
			// VALIDAR SI NO REQUIERE VALIDAR FACTURAS
			solicitud.setBanderaFactura(false);
			solicitudFacturaRepository.save(solicitud);
		}

		pistaService
				.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),
						TipoSeccionPista.DICTAMEN_DATOS_GENERALES.getIdSeccionPista(),
						Constantes.getAtributosDictaminado()[0] + dictamenActual.getIdDictamen() + "| "
								+ Constantes.getAtributosDictaminado()[1] + "Inicial" + "|",
						Optional.of(dictamenActual));

		return dictamenActual;
	}

	@Transactional
	@Override
	public List<ResumenConsolidadoModel> actualizarMontoResumenConsolidado(Long dictamenId, BigDecimal montoPesos) {
		log.info("dictamenId {}", dictamenId);
		List<ResumenConsolidadoModel> resumenActual = resumenConsolidadoRepository
				.findByIdDictamenOrderByIdResumenConsolidadoAsc(dictamenId);
		ResumenConsolidadoModel monto = resumenActual.get(0);
		monto.setTotal(montoPesos);

		if (resumenActual.get(0).getDictamen().getTipoCambioReferencial().compareTo(BigDecimal.ZERO) != 0) {
			monto.setTotalPesos(
					monto.getTotal().multiply(resumenActual.get(0).getDictamen().getTipoCambioReferencial()));
		} else {
			monto.setTotalPesos(montoPesos);
		}
		resumenActual.set(0, monto);
		return resumenConsolidadoRepository.saveAll(resumenActual);
	}

	@Transactional
	@Override
	public String validarIva(Long idContrato) {
		Optional<ContratoModel> contrato = contratoRepository.findByIdContrato(idContrato);

		if (contrato.isPresent()) {
			ConvenioModificatorioModel ultimoConvenioModificatorio = contrato.get().getUltimoConvenioModificatorio();
			if (ultimoConvenioModificatorio != null) {
				return String.valueOf(ultimoConvenioModificatorio.getIdIva());
			}
			return String.valueOf(contrato.get().getVigencia().getId_iva());
		}

		throw new CatalogoException(ErroresEnum.CONTRATO_NO_ENCONTRADO);
	}

	@Transactional
	@Override
	public Dictamen duplicarDictamen(Long idDictamen, Boolean registrosDictaminados, Boolean penasContractuales,
			Boolean penasConvencionales, Boolean deducciones, dictamenDto dictamenDto) {

		Dictamen dictamenDuplicar = dictamenRepositoy.findByIdDictamen(idDictamen)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));

		dictamenDuplicar.setUltimaModificacion(ultimaModificacionGeneral());
		dictamenRepositoy.save(dictamenDuplicar);
		log.info("periodo mes {}", dictamenDuplicar.getCatPeriodoControlMes().getDescripcion());
		log.info("periodo año {}", dictamenDuplicar.getCatPeriodoControlAnio().getDescripcion());
		log.info("id del contrato del dictamen {}", dictamenDuplicar.getIdContrato());
		Dictamen dictamenInformacionNuevo = guardarDictamen(dictamenDuplicar.getIdContrato(), dictamenDto);
		log.info("dictamen nuevo {}", dictamenInformacionNuevo.getIdDictamen());
		log.info("registrosDictaminados: {}", registrosDictaminados);
		log.info("penasContractuales: {}", penasContractuales);
		log.info("penasConvencionales: {}", penasConvencionales);
		log.info("deduccion: {}", deducciones);
		if (Boolean.TRUE.equals(registrosDictaminados)) {
			List<DictaminadoModel> dictaminadoDuplicar = dictaminadoRepository
					.findByIdDictamenAndDictamenEstatusTrue(idDictamen);
			for (DictaminadoModel dictaminadoModel : dictaminadoDuplicar) {
				DictaminadoModel dictaminadoNuevo = new DictaminadoModel();
				dictaminadoNuevo.setIdContrato(dictamenInformacionNuevo.getIdContrato());
				dictaminadoNuevo.setIdServicioContrato(dictaminadoModel.getIdServicioContrato());
				dictaminadoNuevo.setIdDictamen(dictamenInformacionNuevo.getIdDictamen());
				dictaminadoNuevo.setCantidadServiciosSat(dictaminadoModel.getCantidadServiciosSat());
				dictaminadoNuevo.setCantidadServiciosCc(dictaminadoModel.getCantidadServiciosCc());
				dictaminadoNuevo.setCantidadTotalServicios(dictaminadoModel.getCantidadTotalServicios());
				dictaminadoNuevo.setMontoDictaminado(dictaminadoModel.getMontoDictaminado());
				dictaminadoNuevo.setCantidadServicioDictaminadoAcumulado(
						dictaminadoModel.getCantidadServicioDictaminadoAcumulado());
				dictaminadoNuevo.setMontonDictaminadoAcumulado(dictaminadoModel.getMontonDictaminadoAcumulado());
				dictaminadoNuevo.setPorcentajeServiciosDictaminadosAcumulados(
						dictaminadoModel.getPorcentajeServiciosDictaminadosAcumulados());
				dictaminadoNuevo.setPorcentajeMontoDictaminadosAcumulados(
						dictaminadoModel.getPorcentajeMontoDictaminadosAcumulados());
				dictaminadoNuevo.setUltimaModificacion(dictaminadoModel.getUltimaModificacion());
				dictaminadoNuevo.setEstatusCM(dictaminadoModel.getEstatusCM());
				dictaminadoNuevo.setSeleccionado(dictaminadoModel.getSeleccionado());
				dictaminadoNuevo.setFechaRegistro(hoy);
				dictaminadoRepository.save(dictaminadoNuevo);

				// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),

				// TipoSeccionPista.DICTAMEN_REGISTRO_SERVICIOS.getIdSeccionPista(),

				// Constantes.getAtributosDictamen()[0] + dictaminadoNuevo.getIdDictamen() + "|",

				// Optional.of(dictaminadoNuevo));
			}
		}
		if (Boolean.TRUE.equals(penasContractuales)) {
			List<PenasContractualesModel> penasDuplicar = penasContractualesRepository
					.findByIdDictamenAndEstatusTrue(idDictamen);
			for (PenasContractualesModel penasContractualesModel : penasDuplicar) {
				PenasContractualesModel penaContractualNueva = new PenasContractualesModel();
				penaContractualNueva.setIdDesgloce(penasContractualesModel.getIdDesgloce());
				penaContractualNueva.setIdDictamen(dictamenInformacionNuevo.getIdDictamen());
				penaContractualNueva.setIdServicios(penasContractualesModel.getIdServicios());
				penaContractualNueva.setIdPeriodicos(penasContractualesModel.getIdPeriodicos());
				penaContractualNueva.setId(penasContractualesModel.getId());
				penaContractualNueva
						.setIdPenaContractualContrato(penasContractualesModel.getIdPenaContractualContrato());
				penaContractualNueva.setIdAtrasoPrestacion(penasContractualesModel.getIdAtrasoPrestacion());
				penaContractualNueva.setIdTipoPenaContractual(penasContractualesModel.getIdTipoPenaContractual());
				penaContractualNueva.setMonto(penasContractualesModel.getMonto());
				penaContractualNueva.setPenaAplicable(penasContractualesModel.getPenaAplicable());
				penaContractualNueva.setEstatus(true);
				penasContractualesRepository.save(penaContractualNueva);



				// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),


				// TipoSeccionPista.DICTAMEN_PENAS_CONTRACTUALES.getIdSeccionPista(),


				// Constantes.getAtributosDictamen()[0] + penaContractualNueva.getIdDictamen() + "|",


				// Optional.of(penaContractualNueva));
			}
		}

		if (Boolean.TRUE.equals(penasConvencionales)) {
			List<PenasConvencionalesModel> penasDuplicar = penasConvencionalesRepository
					.findByIdDictamenAndEstatusTrue(idDictamen);
			for (PenasConvencionalesModel penasConvencionalesModel : penasDuplicar) {
				PenasConvencionalesModel penaConvencionalNueva = new PenasConvencionalesModel();
				penaConvencionalNueva.setIdDesgloce(penasConvencionalesModel.getIdDesgloce());
				penaConvencionalNueva.setIdDictamen(dictamenInformacionNuevo.getIdDictamen());
				penaConvencionalNueva.setIdServicios(penasConvencionalesModel.getIdServicios());
				penaConvencionalNueva.setIdPeriodicos(penasConvencionalesModel.getIdPeriodicos());
				penaConvencionalNueva.setId(penasConvencionalesModel.getId());
				penaConvencionalNueva.setIdAtrasoPrestacion(penasConvencionalesModel.getIdAtrasoPrestacion());
				penaConvencionalNueva.setIdTipoPenaConvencional(penasConvencionalesModel.getIdTipoPenaConvencional());
				penaConvencionalNueva.setMonto(penasConvencionalesModel.getMonto());
				penaConvencionalNueva.setPenaAplicable(penasConvencionalesModel.getPenaAplicable());
				penaConvencionalNueva.setEstatus(true);
				penasConvencionalesRepository.save(penaConvencionalNueva);



				// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),


				// TipoSeccionPista.DICTAMEN_PENAS_CONVENCIONALES.getIdSeccionPista(),


				// Constantes.getAtributosDictamen()[0] + penaConvencionalNueva.getIdDictamen() + "|",


				// Optional.of(penaConvencionalNueva));

			}
		}

		if (Boolean.TRUE.equals(deducciones)) {
			List<DeduccionesModel> penasDuplicar = deduccionRepository
					.findByIdDictamenAndEstatusTrueAndDictamenEstatusTrue(idDictamen);
			for (DeduccionesModel deduccionesModel : penasDuplicar) {
				log.info("id deduccion: {}", deduccionesModel.getIdDeduccion());
				log.info("desglose: {}", deduccionesModel.getIdDesgloce());
				DeduccionesModel deduccionesNueva = new DeduccionesModel();
				deduccionesNueva.setIdDesgloce(deduccionesModel.getIdDesgloce());
				deduccionesNueva.setIdDictamen(dictamenInformacionNuevo.getIdDictamen());
				deduccionesNueva.setIdServicios(deduccionesModel.getIdServicios());
				deduccionesNueva.setIdPeriodicos(deduccionesModel.getIdPeriodicos());
				deduccionesNueva.setId(deduccionesModel.getId());
				deduccionesNueva.setIdServiciosSla(deduccionesModel.getIdServiciosSla());
				deduccionesNueva.setIdAtrasoPrestacion(deduccionesModel.getIdAtrasoPrestacion());
				deduccionesNueva.setIdTipoDeduccion(deduccionesModel.getIdTipoDeduccion());
				deduccionesNueva.setConceptoServicio(deduccionesModel.getConceptoServicio());
				deduccionesNueva.setMonto(deduccionesModel.getMonto());
				deduccionesNueva.setPenaAplicable(deduccionesModel.getPenaAplicable());
				deduccionesNueva.setEstatus(true);
				deduccionRepository.save(deduccionesNueva);
				log.info("deduccion duplicada: {}", deduccionesNueva.getIdDeduccion());

				// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),

				// TipoSeccionPista.DICTAMEN_DEDUCCIONES.getIdSeccionPista(),

				// Constantes.getAtributosDictamen()[0] + deduccionesNueva.getIdDictamen() + "|",

				// Optional.of(deduccionesNueva));
			}
		}

		return dictamenInformacionNuevo;
	}

	@Transactional
	@Override
	public List<DevengadoBusquedaResponse> obtenerDictamenesPorIdContrato(Long idContrato) {
		List<Dictamen> dictamenes = dictamenRepositoy.findByIdContratoAndEstatusTrueOrderByIdDictamenAsc(idContrato);
		List<DevengadoBusquedaResponse> resultado = new ArrayList<>();

		for (Dictamen dictamenModel : dictamenes) {
			resultado.add(new DictamenMap(resumenConsolidadoRepository).apply(dictamenModel));
		}

		return resultado;
	}

	@Transactional
	@Override
	public List<Archivo> obtenerArchivos(Long idProyecto) {
		List<Archivo> lista = new ArrayList<>();
		List<FacturaModel> listaFactura = facturasRepository
				.findByDictamenContratoModelProyectoIdProyectoAndCatEstatusFacturaNombreNot(idProyecto, "Cancelado");
		List<NotaCreditoModel> listaNota = notasCreditoRepository
				.findByDictamenContratoModelProyectoIdProyectoAndCatEstatusNotaCreditoNombreNot(idProyecto,
						"Cancelada");
		lista.addAll(archivoPlantillaDictamenRepository
				.findByCarpetaPlantillaModelDictamenContratoModelProyectoIdProyectoAndArchivoBaseEstatusTrue(
						idProyecto));
		for (FacturaModel factura : listaFactura) {
			lista.add(factura.getArchivoPdf());
			lista.add(factura.getArchivoXml());
		}
		for (NotaCreditoModel nota : listaNota) {
			lista.add(nota.getArchivoPdf());
			lista.add(nota.getArchivoXml());
		}

		return lista;

	}

	@Transactional
	@Override
	public List<Archivo> obtenerOtrosArchivosDictamen(Long idProyecto) {
		List<Archivo> lista = new ArrayList<>();
		lista.addAll(
				archivoOtroDocumentoFaseRepository.findByDictamen_ContratoModel_idProyectoAndEstatusTrue(idProyecto));
		return lista;

	}

	public static BigDecimal convertirPorcentaje(String porcentajeStr) {
		if (porcentajeStr == null || porcentajeStr.trim().isEmpty() || "No aplica".equalsIgnoreCase(porcentajeStr)) {
			return BigDecimal.ZERO;
		}

		String porcentajeLimpio = porcentajeStr.replaceAll("[^\\d.]", "");

		try {
			return new BigDecimal(porcentajeLimpio);
		} catch (NumberFormatException e) {
			return BigDecimal.ZERO;
		}
	}

	@Transactional
	@Override
	public List<ResumenConsolidadoModel> actualizarResumenConsolidado(Long dictamenId) {

		Dictamen dictamen = dictamenRepositoy.findByIdDictamen(dictamenId)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.MSJ_NOT_FOUND_DICTAMEN));

		List<ResumenConsolidadoModel> resumen = resumenConsolidadoRepository
				.findByIdDictamenOrderByIdResumenConsolidadoAsc(dictamenId);
		if (resumen.isEmpty()) {
			throw new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND);
		}

		List<DeduccionesModel> deducciones = deduccionRepository
				.findByIdDictamenAndEstatusTrueAndDictamenEstatusTrue(dictamenId);
		List<DictaminadoModel> dictamenDictaminado = dictaminadoRepository
				.findByIdDictamenAndDictamenEstatusTrue(dictamenId);

		String porcentajeIeps = Optional.ofNullable(dictamen).map(Dictamen::getContratoModel)
				.map(ContratoModel::getVigencia).map(VigenciaMontosModel::getCatIeps).map(CatIeps::getPorcentaje)
				.orElse("0");

		BigDecimal porcentajeIESP = convertirPorcentaje(porcentajeIeps);
		log.info("porcentaje ieps: {}", porcentajeIeps);
		BigDecimal montoDictaminado = dictamenDictaminado.stream().map(DictaminadoModel::getMontoDictaminado)
				.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);

		BigDecimal montoDeduccion = deducciones.stream().map(DeduccionesModel::getMonto)
				.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
		log.info("monto deducciones {}", montoDeduccion);

		// VALIDAR SOLO PARA LOS QUE APLIQUEN IEPS
		List<ServicioConvenioModel> convenio = new ArrayList<>();
		Long idContrato = Objects.requireNonNull(dictamen).getIdContrato();
		List<ServicioContratoModel> contrato = servicioContratoRepository.obtenerServiciosOrdenados(idContrato);
		
		if (!dictamenDictaminado.isEmpty() && Boolean.TRUE.equals(dictamenDictaminado.get(0).getEstatusCM())
				&& dictamen.getIdConvenioCodificatorio() != null) {
			convenio = servicioConvenioRepository
					.findByIdConvenioModificatorioAndServicioBaseEstatusTrue(dictamen.getIdConvenioCodificatorio());
		}

		BigDecimal ieps = calcularIeps(dictamenDictaminado, contrato, convenio, porcentajeIESP);

		BigDecimal iva = BigDecimal.ZERO;
		if (!dictamen.getCatIva().getPorcentaje().equals("No Aplica")) {
			log.info("iepssssss: {}", ieps);

			BigDecimal montoBase = montoDictaminado.subtract(montoDeduccion).add(ieps);
			log.info("monto base (subtotal - Deducción + IEPS): {}", montoBase);
			String porcentajeStr = dictamen.getCatIva().getPorcentaje();
			BigDecimal porcentajeIva = convertirPorcentaje(porcentajeStr).divide(new BigDecimal(100));
			BigDecimal ivaMonto = montoBase.multiply(porcentajeIva).setScale(2, RoundingMode.HALF_UP).setScale(2,
					RoundingMode.HALF_UP);

			log.info("iva calculado con porcentaje ({}%): {}", dictamen.getCatIva().getPorcentaje(), ivaMonto);

			iva = ivaMonto;
		}

		BigDecimal total = montoDictaminado.subtract(montoDeduccion).add(ieps).add(iva).setScale(2,
				RoundingMode.HALF_UP);

		ResumenConsolidadoModel resumenActualizar = resumen.get(0);
		resumenActualizar.setSubTotal(montoDictaminado);
		resumenActualizar.setDeducciones(montoDeduccion);
		resumenActualizar.setIeps(ieps);
		resumenActualizar.setIva(iva);
		resumenActualizar.setOtrosImpuestos(BigDecimal.ZERO);
		resumenActualizar.setTotal(total);
		log.info("moneda: {}", dictamen.getContratoModel().getVigencia().getCatTipoMoneda().getNombre());
		log.info("cambio referencial: {}", dictamen.getTipoCambioReferencial());
		if (dictamen.getContratoModel().getVigencia().getCatTipoMoneda().getIdTipoMoneda().equals(1)) {
			resumenActualizar.setTotalPesos(total);
		} else {
			log.info("calculo total en pesos: {}", resumenActualizar.getTotalPesos());
			resumenActualizar.setTotalPesos(
					total.multiply(dictamen.getTipoCambioReferencial()).setScale(2, RoundingMode.HALF_UP));
		}

		log.info("sub total {}", resumenActualizar.getSubTotal());
		log.info("deducciones {}", resumenActualizar.getDeducciones());
		log.info("ieps {}", resumenActualizar.getIeps());
		log.info("iva {}", resumenActualizar.getIva());
		log.info("total {}", resumenActualizar.getTotal());
		log.info("total en pesos {}", resumenActualizar.getTotalPesos());

		resumenConsolidadoRepository.save(resumenActualizar);

		return resumen;
	}

	public BigDecimal calcularIeps(List<DictaminadoModel> dictamenDictaminado, List<ServicioContratoModel> contrato,
			List<ServicioConvenioModel> convenio, BigDecimal porcentajeIESP) {

		if (contrato == null || contrato.isEmpty() || porcentajeIESP == null) {
			return BigDecimal.ZERO;
		}

		return dictamenDictaminado.stream()
				.map(dictaminado -> calcularIepsPorDictaminado(dictaminado, contrato, convenio, porcentajeIESP))
				.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
	}

	private BigDecimal calcularIepsPorDictaminado(DictaminadoModel dictaminado, List<ServicioContratoModel> contrato,
			List<ServicioConvenioModel> convenio, BigDecimal porcentajeIESP) {

		BigDecimal montoIeps;

		ServicioContratoModel servicioContrato = contrato.stream()
				.filter(c -> c.getIdServicioContrato().equals(dictaminado.getIdServicioContrato())).findFirst()
				.orElse(null);

		if (servicioContrato == null) {
			log.warn("ServicioContratoModel no encontrado en el contrato para el ID: {}",
					dictaminado.getIdServicioContrato());
			return BigDecimal.ZERO;
		}

		if (Boolean.TRUE.equals(dictaminado.getEstatusCM())) {
			ServicioConvenioModel servicioConvenio = convenio != null ? convenio.stream().filter(
					c -> c.getServicioBase().getIdServicioContrato().equals(dictaminado.getIdServicioContrato()))
					.findFirst().orElse(null) : null;

			if (servicioConvenio == null || Boolean.FALSE.equals(servicioConvenio.getIeps())) {
				log.info("El servicio en el convenio NO aplica IEPS o no se encontró.");
				return BigDecimal.ZERO;
			}

			BigDecimal cantidadTotalServicios = dictaminado.getCantidadTotalServicios();
			BigDecimal precioUnitario = servicioConvenio.getPrecioUnitario();

			log.info("cantidadTotalServicios (convenio): {}, precioUnitario (convenio): {}", cantidadTotalServicios,
					precioUnitario);

			montoIeps = cantidadTotalServicios.multiply(precioUnitario).multiply(porcentajeIESP)
					.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
		} else {
			if (Boolean.FALSE.equals(servicioContrato.getIeps())) {
				log.info("El servicio en el contrato NO aplica IEPS.");
				return BigDecimal.ZERO;
			}

			BigDecimal cantidadTotalServicios = dictaminado.getCantidadTotalServicios();
			BigDecimal precioUnitario = servicioContrato.getPrecioUnitario();

			log.info("cantidadTotalServicios (contrato): {}, precioUnitario (contrato): {}, porcentaje: {}",
					cantidadTotalServicios, precioUnitario, porcentajeIESP);
			log.info("porcentaje ieps: {}", porcentajeIESP);
			montoIeps = cantidadTotalServicios.multiply(precioUnitario).multiply(porcentajeIESP)
					.divide(BigDecimal.valueOf(100));
			log.info("monto ieps: {}", montoIeps);
		}
		log.info("Monto IEPS calculado para este dictaminado: {}", montoIeps);
		return montoIeps;
	}

	@Transactional
	@Override
	public List<ResumenConsolidadoModel> actualizarResumeFacturado(Long dictamenId) {
		log.info("dictamen id {}", dictamenId);

		List<ResumenConsolidadoModel> resumen = obtenerResumenConsolidadoOtro(dictamenId);

		List<FacturaModel> listaSubtotal = facturaRepository.findByDictamenIdAndDictamenEstatusTrue(dictamenId);
		List<NotaCreditoModel> listaDeducciones = notasCreditoRepository
				.findByDictamenIdAndDictamenEstatusTrue(dictamenId);

		BigDecimal subtotal = calcularSubtotal(listaSubtotal).setScale(2, RoundingMode.HALF_UP);
		BigDecimal deducciones = calcularDeducciones(listaDeducciones).setScale(2, RoundingMode.HALF_UP);
		BigDecimal ieps = calcularIeps(listaSubtotal, listaDeducciones).setScale(2, RoundingMode.HALF_UP);
		BigDecimal iva = calcularIva(listaSubtotal, listaDeducciones).setScale(2, RoundingMode.HALF_UP);
		BigDecimal otrosImpuestos = calcularOtrosImpuestos(listaSubtotal, listaDeducciones).setScale(2,
				RoundingMode.HALF_UP);

		ResumenConsolidadoModel resumenActualizar = resumen.get(1);
		actualizarResumenConsolidado(resumenActualizar, subtotal, deducciones, ieps, iva, otrosImpuestos);

		actualizarTotalPesos(resumenActualizar);

		resumenConsolidadoRepository.save(resumenActualizar);
		return resumen;
	}

	private List<ResumenConsolidadoModel> obtenerResumenConsolidadoOtro(Long dictamenId) {
		List<ResumenConsolidadoModel> resumen = resumenConsolidadoRepository
				.findByIdDictamenOrderByIdResumenConsolidadoAsc(dictamenId);
		if (resumen.isEmpty()) {
			throw new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND);
		}
		return resumen;
	}

	private BigDecimal calcularSubtotal(List<FacturaModel> listasubtotal) {
		return listasubtotal.stream()
				.map(factura -> factura.getSubTotal() != null ? factura.getSubTotal() : BigDecimal.ZERO)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private BigDecimal calcularDeducciones(List<NotaCreditoModel> listaDeducciones) {
		return listaDeducciones.stream().map(nota -> nota.getSubTotal() != null ? nota.getSubTotal() : BigDecimal.ZERO)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private BigDecimal calcularIeps(List<FacturaModel> listasubtotal, List<NotaCreditoModel> listaDeducciones) {
		BigDecimal sumaIeps = listasubtotal.stream()
				.map(factura -> factura.getIeps() != null ? factura.getIeps() : BigDecimal.ZERO)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal restaIeps = listaDeducciones.stream()
				.map(nota -> nota.getIeps() != null ? nota.getIeps() : BigDecimal.ZERO)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		return sumaIeps.add(restaIeps);
	}

	private BigDecimal calcularIva(List<FacturaModel> listasubtotal, List<NotaCreditoModel> listaDeducciones) {
		BigDecimal sumaIva = listasubtotal.stream()
				.map(factura -> factura.getIva() != null ? factura.getIva() : BigDecimal.ZERO)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal restaIva = listaDeducciones.stream()
				.map(nota -> nota.getIva() != null ? nota.getIva() : BigDecimal.ZERO)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		return sumaIva.subtract(restaIva);
	}

	private BigDecimal calcularOtrosImpuestos(List<FacturaModel> listasubtotal,
			List<NotaCreditoModel> listaDeducciones) {
		BigDecimal sumaOtrosImpu = listasubtotal.stream()
				.map(factura -> factura.getOtrosImpuestos() != null ? factura.getOtrosImpuestos() : BigDecimal.ZERO)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal restaOtrosImpu = listaDeducciones.stream()
				.map(nota -> nota.getOtrosImpuestos() != null ? nota.getOtrosImpuestos() : BigDecimal.ZERO)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		return sumaOtrosImpu.add(restaOtrosImpu);
	}

	private void actualizarResumenConsolidado(ResumenConsolidadoModel resumenActualizar, BigDecimal subtotal,
			BigDecimal deducciones, BigDecimal ieps, BigDecimal iva, BigDecimal otrosImpuestos) {
		resumenActualizar.setSubTotal(subtotal);
		resumenActualizar.setDeducciones(deducciones);
		resumenActualizar.setIeps(ieps);
		resumenActualizar.setIva(iva);
		resumenActualizar.setOtrosImpuestos(otrosImpuestos);
		resumenActualizar.setTotal((subtotal.subtract(deducciones).add(ieps)).add(iva));
	}

	private void actualizarTotalPesos(ResumenConsolidadoModel resumenActualizar) {

		Dictamen dictamen = obtenerDictamenExistente(resumenActualizar.getIdDictamen());
		Integer tipoMoneda = dictamen.getContratoModel().getVigencia().getCatTipoMoneda().getIdTipoMoneda();
		String estatusDictamen = dictamen.getCatEstatusDictamen().getNombre().trim();
		BigDecimal total = resumenActualizar.getTotal();
		BigDecimal tipoCambio = dictamen.getTipoCambioReferencial();
		log.info("estatus dictamen: {}", estatusDictamen);
		final Integer MONEDA_PESOS = 1;
		final String ESTATUS_PAGADO = "Pagado";

		if (MONEDA_PESOS.equals(tipoMoneda)) {
			log.info("1: {}", 1);
			resumenActualizar.setTotalPesos(total);
		} else if (ESTATUS_PAGADO.equals(estatusDictamen)) {
			log.info("2: {}", 2);
			SolicitudPagoModel solicitud = solicitudPagoRepository
					.findByDictamenIdAndDictamenEstatusTrue(resumenActualizar.getIdDictamen());

			if (solicitud != null) {
				List<ReferenciaPagoModel> listaReferencia = referenciaPagoRepository
						.findByIdSolicitudPagoAndFacturaModelCatEstatusFacturaNombreNotCancelado(
								solicitud.getIdSolicitudPago());
				log.info("total: {}", listaReferencia.size());
				BigDecimal tipoCambioReal = listaReferencia.stream()
						.map(referencia -> referencia.getPagadoNAFIN() != null ? referencia.getPagadoNAFIN()
								: BigDecimal.ZERO)
						.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
				resumenActualizar.setTotalPesos(tipoCambioReal);
			}

		} else {
			log.info("3: {}", 3);
			resumenActualizar.setTotalPesos(total.multiply(tipoCambio).setScale(2, RoundingMode.HALF_UP));
		}
	}

	@Transactional
	@Override
	public Long validarPenas(Long idContrato, Integer idTipo) {

		if (idTipo > 4) {
			return penasContractualesRepository
					.countByDictamenIdContratoAndIdTipoPenaContractualAndEstatusTrue(idContrato, idTipo);
		} else {
			Long contractuales = penasContractualesRepository
					.countByDictamenIdContratoAndIdTipoPenaContractualAndEstatusTrue(idContrato, idTipo);

			Long convencionales = penasConvencionalesRepository
					.countByDictamenIdContratoAndIdTipoPenaConvencionalAndEstatusTrue(idContrato, idTipo);

			return contractuales + convencionales;
		}

	}

	@Transactional
	@Override
	public String actualizarCheckPenasDeducciones(Long idDictamen, Boolean checkContractual, Boolean checkConvencional,
			Boolean checkDeducciones) {
		dictamenRepositoy.actualizarCheckPenasDeducciones(idDictamen, checkContractual, checkConvencional,
				checkDeducciones);

		return "ok";

	}

	@Transactional
	@Override
	public Boolean validarCancelacionProyecto(Long idProyecto) {
		List<Integer> estatus = Arrays.asList(7);
		Long cont = dictamenRepositoy
				.countByEstatusTrueAndCatEstatusDictamenIdEstatusDictamenNotInAndContratoModelProyectoIdProyecto(
						estatus, idProyecto);
		return cont <= 0;
	}

}