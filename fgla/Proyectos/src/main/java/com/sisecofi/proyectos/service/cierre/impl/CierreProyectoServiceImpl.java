package com.sisecofi.proyectos.service.cierre.impl;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.dto.CarpetaCompartidaDto;
import com.sisecofi.libreria.comunes.dto.plantillador.HtmlExcelListDto;
import com.sisecofi.libreria.comunes.dto.proyecto.FichaTecnicaResponse;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonCentral;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.ArchivoOtroDocumentoFaseModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.ArchivoPlantillaProyectoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoOtroDocumentoFaseContratoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoOtroDocumentoFaseConvenioModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoPlantillaContratoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoPlantillaConvenioModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoOtroDocumentoFaseDictamenModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoPlantillaDictamenModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.ArchivoPlantillaReintegroModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoOtrosDocumentosComiteModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoPlantillaComiteModel;
import com.sisecofi.libreria.comunes.model.plantillador.PlantilladorModel;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.repository.ArchivoOtroDocumentoFaseRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoOtrosDocumentosComiteRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoPlantillaComiteRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoPlantillaContratoRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoPlantillaConvenioRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoPlantillaProyectoRepository;
import com.sisecofi.libreria.comunes.repository.UsuarioRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoOtroDocumentoFaseContratoRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoOtroDocumentoFaseConvenioRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoOtroDocumentoFaseDictamenRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoPlantillaDictamenRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoPlantillaReintegroRepository;
import com.sisecofi.libreria.comunes.util.ConstantesParaRutasSATCloud;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.libreria.comunes.util.exception.NexusException;
import com.sisecofi.proyectos.dto.DescargaSatCloudRequest;
import com.sisecofi.proyectos.dto.cierre.DatosPlantillaRcp;
import com.sisecofi.proyectos.dto.cierre.ObtenerProyectodto;
import com.sisecofi.proyectos.microservicio.AdministracionPlantilladorMicriservicio;
import com.sisecofi.proyectos.microservicio.CatalogoMicroservicio;
import com.sisecofi.proyectos.microservicio.PlantilladorMicriservicio;
import com.sisecofi.proyectos.microservicio.cierre.ContratoRestMicroservicio;
import com.sisecofi.proyectos.microservicio.cierre.DictamenRestMicroservicio;
import com.sisecofi.proyectos.model.cierre.CierreModel;
import com.sisecofi.proyectos.repository.ProyectoRepository;
import com.sisecofi.proyectos.repository.cierre.AsociacionesRepository;
import com.sisecofi.proyectos.repository.cierre.CierreRepository;
import com.sisecofi.proyectos.service.PistaService;
import com.sisecofi.proyectos.service.ServicioArchivo;
import com.sisecofi.proyectos.service.ServicioFichaTecnica;
import com.sisecofi.proyectos.service.ServicioProyecto;
import com.sisecofi.proyectos.service.cierre.CierreProyectoService;
import com.sisecofi.proyectos.service.impl.NexusImpl;
import com.sisecofi.proyectos.util.Constantes;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.exception.ProyectoException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CierreProyectoServiceImpl implements CierreProyectoService {

	private final ProyectoRepository proyectoRepository;
	private final CierreRepository cierreRepository;
	private final NexusImpl nexusImpl;
	private final PistaService pistaService;
	private final UsuarioRepository usuarioRepository;
	private final ContratoRestMicroservicio contratoRestMicroservicio;
	private final DictamenRestMicroservicio dictamenRestMicroservicio;
	private final AsociacionesRepository asociacionesRepository;
	private final ArchivoPlantillaProyectoRepository archivoPlantillaProyectoRepository;
	private final ArchivoPlantillaContratoRepository archivoPlantillaContratoRepository;
	private final ArchivoPlantillaConvenioRepository archivoPlantillaConvenioRepository;
	private final ArchivoPlantillaDictamenRepository archivoPlantillaDictamenRepository;
	private final ArchivoPlantillaReintegroRepository archivoPlantillaReintegroRepository;
	private final ArchivoOtroDocumentoFaseRepository archivoOtroDocumentoFaseRepository;
	private final ArchivoOtrosDocumentosComiteRepository archivoOtrosDocumentosComiteRepository;
	private final ArchivoOtroDocumentoFaseContratoRepository archivoOtroDocumentoFaseContratoRepository;
	private final ArchivoOtroDocumentoFaseConvenioRepository archivoOtroDocumentoFaseConvenioRepository;
	private final ArchivoOtroDocumentoFaseDictamenRepository archivoOtroDocumentoFaseDictamenRepository;
	private final CatalogoMicroservicio catalogoMicroservicio;
	private final ServicioArchivo servicioArchivo;
	private final ArchivoPlantillaComiteRepository archivoPlantillaComiteRepository;
	private final PlantilladorMicriservicio plantilladorMicriservicio;
	private final AdministracionPlantilladorMicriservicio administracionPlantilladorMicriservicio;
	private final ServicioFichaTecnica fichaServicio;
	private final ServicioProyecto servicioProyecto;
	LocalDateTime hoy = LocalDateTime.now();
	private static final String JUSTIFICACION = "justificacion";
	private static final String ID_PROYECTO = "id_proyecto_sistema";
	private static final String FECHA_DOCUMENTO = "fecha_del_documento";
	private static final String ADMIN_CENTRAL = "administracion_central";

	@Override
	public String descargarArchivo(String path, String nombreCorto, String documentoSeleccionado, String fase) {
		try {
			InputStream obj = nexusImpl.descargarArchivo(path);
			log.info("Descargado: {}", obj);
			byte[] bytes = obj.readAllBytes();
			obj.close();



			// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),


			// TipoSeccionPista.VERIFICACION_RCP.getIdSeccionPista(),


			// Constantes.getAtributosCierre()[0] + nombreCorto + "|" + Constantes.getAtributosCierre()[1]


			// + documentoSeleccionado + "|" + Constantes.getAtributosCierre()[2] + fase,


			// Optional.empty());

			return Base64.getEncoder().encodeToString(bytes);
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_DESCARGAR_ARCHIVO);
		}
	}

	@Override
	public boolean validarEstatus(Long idProyecto) {
		ProyectoModel proyecto = proyectoRepository.findByIdProyectoAndEstatus(idProyecto, true)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.NO_PROCESO_CIERRE));

		if (proyecto.getCatEstatusProyecto().getNombre().equals("Proceso de cierre")) {
			return true;
		}
		throw new ProyectoException(ErroresEnum.NO_PROCESO_CIERRE);
	}

	@Override
	public ObtenerProyectodto obtenerDatosProyecto(Long idProyecto) {
		boolean existePlatilla = asociacionesRepository.existsByidProyecto(idProyecto);

		if (existePlatilla) {
			ProyectoModel proyecto = proyectoRepository.findByIdProyectoAndEstatus(idProyecto, true)
					.orElseThrow(() -> new ProyectoException(ErroresEnum.NO_PROCESO_CIERRE));

			ObtenerProyectodto proyectoDto = new ObtenerProyectodto();
			proyectoDto.setIdProyecto(proyecto.getIdProyecto());
			proyectoDto.setIdProyectoAGP(proyecto.getIdAgp());
			proyectoDto.setNombreCompleto(proyecto.getNombreProyecto());
			proyectoDto.setNombreCorto(proyecto.getNombreCorto());
			if (proyecto.getFichaTecnicaModel() != null && proyecto.getFichaTecnicaModel().getHistoricoModel() != null
					&& proyecto.getFichaTecnicaModel().getHistoricoModel().getNombre() != null) {
				proyectoDto.setLider(proyecto.getFichaTecnicaModel().getHistoricoModel().getNombre());
			} else {
				proyectoDto.setLider(null);
			}

			proyectoDto.setPorcentajePlaneado(null);
			proyectoDto.setPorcentajeReal(null);
			return proyectoDto;
		} else {
			throw new ProyectoException(ErroresEnum.NO_PLANTILLA_DOCUMENTAL);
		}
	}

	@Override
	public Boolean cancelarCierre(Long idCierre) {

		List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.ESTATUS_RCP.getIdCatalogo(), Constantes.ESTATUS_CANCELAR);

		CierreModel cierre = cierreRepository.findByIdCierreAndEstatusTrue(idCierre)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.CIERRE_NOT_FOUND));

		cierre.setIdEstatusRcp(Long.parseLong("" + lista.get(0).getPrimaryKey()));
		servicioProyecto.actualizarUltimaModificacion(cierre.getIdProyecto());
		CierreModel cierreNuevo = cierreRepository.save(cierre);



		// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),


		// TipoSeccionPista.VERIFICACION_RCP.getIdSeccionPista(),


		// Constantes.getAtributosCierreCancelar()[0] + cierreNuevo.getProyectoModel().getNombreCorto() + "|"


		// + Constantes.getAtributosCierreCancelar()[1] + cierreNuevo.getCatEstatusRcp().getNombre() + "|",


		// Optional.of(cierreNuevo));
		return true;
	}

	@Override
	public List<Usuario> obtenerUsuarios() {
		return usuarioRepository.findAll();
	}

	@Override
	public List<Archivo> obtenerArchivosSeccion(Long idProyecto) {
		List<Archivo> lista = new ArrayList<>();
		List<Archivo> proyectos = archivoPlantillaProyectoRepository
				.findByCarpetaPlantillaModelAsociacionesModelIdProyectoAndArchivoBaseEstatusTrue(idProyecto);
		List<Archivo> proyectosOtrosDoc = archivoOtroDocumentoFaseRepository.findByIdProyectoAndEstatusTrue(idProyecto);
		List<Archivo> contrato = contratoRestMicroservicio.obtenerArchivosSeccion(idProyecto);
		List<Archivo> dictamenes = dictamenRestMicroservicio.obtenerArchivosSeccion(idProyecto);
		List<Archivo> otrosdictamenes = dictamenRestMicroservicio.obtenerOtrosArchivosDictamen(idProyecto);
		List<Archivo> comites = archivoPlantillaComiteRepository
				.findByAsociasionComitePlantillaModelComiteProyectoModelIdProyectoAndEstatusTrue(idProyecto);

		List<Archivo> otrosDocumentosComiteModels = obtenerOtrosComite(idProyecto);
		lista.addAll(proyectos);
		lista.addAll(proyectosOtrosDoc);
		lista.addAll(contrato);
		lista.addAll(comites);
		lista.addAll(otrosDocumentosComiteModels);
		lista.addAll(dictamenes);
		lista.addAll(otrosdictamenes);
		return lista;
	}

	@Override
	public List<Archivo> obtenerOtrosComite(Long idProyecto) {
		List<Archivo> otrosDocumentosComiteModels = archivoOtrosDocumentosComiteRepository
				.findByComiteProyectoModel_IdProyectoAndEstatusTrue(idProyecto);
		List<Archivo> otros = new ArrayList<>();

		for (Archivo archivo : otrosDocumentosComiteModels) {
			if (archivo instanceof ArchivoOtrosDocumentosComiteModel comiteArchivo && Boolean.TRUE.equals(comiteArchivo.getOtrosDocumentosInterno())) {

					otros.add(comiteArchivo);
			}
		}
		return otros;
	}

	public DatosPlantillaRcp generarPlantillaRcp(Long idSubPlantillador) {
		DatosPlantillaRcp datosPlantilla = new DatosPlantillaRcp();

		datosPlantilla.setIdContenidoPlantillador(idSubPlantillador);

		datosPlantilla.setDatosGenerales(new HashMap<>());

		datosPlantilla.setListaEncabezado(new ArrayList<>());

		datosPlantilla.setListaDocumentos(new ArrayList<>());

		return datosPlantilla;
	}

	@Override
	public DatosPlantillaRcp generarMapas(Long idProyecto, Long idContenidoPlantillador) {
		DatosPlantillaRcp datosRcp = new DatosPlantillaRcp();
		List<Map<String, String>> listaDeMapas = new ArrayList<>();
		List<Map<String, String>> listaAdministracionCentral = new ArrayList<>();
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		CierreModel cierre = obtenerDatosCierre(idProyecto, listaAdministracionCentral);

		procesarArchivos(obtenerArchivosSeccion(idProyecto), cierre, listaDeMapas, inputFormatter, outputFormatter);

		Map<String, String> datosGenerales = generarDatosGenerales(cierre, listaAdministracionCentral, inputFormatter,
				outputFormatter);

		datosRcp.setIdContenidoPlantillador(idContenidoPlantillador);
		datosRcp.setDatosGenerales(datosGenerales);
		datosRcp.setListaDocumentos(listaDeMapas);
		datosRcp.setListaEncabezado(listaAdministracionCentral);

		return datosRcp;
	}

	private CierreModel obtenerDatosCierre(Long idProyecto, List<Map<String, String>> listaAdministracionCentral) {
		CierreModel cierre = cierreRepository.findByIdProyecto(idProyecto)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.CIERRE_NOT_FOUND));

		FichaTecnicaResponse adminCentral = fichaServicio.obtenerFicha(idProyecto);
		Set<CatAdmonCentral> listAdmonCentral = adminCentral != null ? adminCentral.getAdmonCentrales()
				: new HashSet<>();

		for (CatAdmonCentral admonCentral : listAdmonCentral) {
			Map<String, String> mapaAdminCentral = new HashMap<>();
			mapaAdminCentral.put(ADMIN_CENTRAL, admonCentral.getAdministracion());
			listaAdministracionCentral.add(mapaAdminCentral);
		}
		return cierre;
	}

	private void procesarArchivos(List<?> lista, CierreModel cierre, List<Map<String, String>> listaDeMapas,
			DateTimeFormatter inputFormatter, DateTimeFormatter outputFormatter) {
		for (Object obj : lista) {
			Map<String, String> mapaArchivo = new LinkedHashMap<>();
			if (obj instanceof Archivo archivo) {
				procesarArchivo(archivo, cierre, mapaArchivo, inputFormatter, outputFormatter);
			} else if (obj instanceof Map<?, ?> mapa) {
				procesarMapaArchivo(mapa, cierre, mapaArchivo, inputFormatter, outputFormatter);
			} else {
				log.error("Tipo inesperado en la lista: {}", obj.getClass().getName());
			}
			listaDeMapas.add(mapaArchivo);
		}
	}

	private void procesarArchivo(Archivo archivo, CierreModel cierre, Map<String, String> mapaArchivo,
			DateTimeFormatter inputFormatter, DateTimeFormatter outputFormatter) {
		mapaArchivo.put("entregables", archivo.getDescripcion());
		mapaArchivo.put("fase", archivo.getFase());
		mapaArchivo.put("estatus", String.valueOf(archivo.getEstatusDocumento()));
		mapaArchivo.put(FECHA_DOCUMENTO, formatFecha(archivo.getFechaDocumento(), inputFormatter, outputFormatter));
		mapaArchivo.put("responsable",
				cierre.getProyectoModel().getFichaTecnicaModel().getHistoricoModel().getNombre());
		mapaArchivo.put(JUSTIFICACION, archivo.getJustificacion() != null ? archivo.getJustificacion() : "");
		mapaArchivo.put("#paginas", archivo.getNumeroPaginas() != null ? archivo.getNumeroPaginas().toString() : "");
	}

	private void procesarMapaArchivo(Map<?, ?> mapa, CierreModel cierre, Map<String, String> mapaArchivo,
			DateTimeFormatter inputFormatter, DateTimeFormatter outputFormatter) {

		mapaArchivo.put("entregables", mapa.get("descripcion") != null ? mapa.get("descripcion").toString() : "");
		mapaArchivo.put("fase", mapa.get("fase") != null ? mapa.get("fase").toString() : "");
		Object estatus = mapa.get("estatusDocumento");
		mapaArchivo.put("estatus", estatus != null ? estatus.toString() : "");
		Object fechaDocumento = mapa.get("fechaDocumento");
		mapaArchivo.put(FECHA_DOCUMENTO, formatFecha(fechaDocumento, inputFormatter, outputFormatter));
		mapaArchivo.put("responsable",
				cierre.getProyectoModel().getFichaTecnicaModel().getHistoricoModel().getNombre());
		mapaArchivo.put(JUSTIFICACION, mapa.get(JUSTIFICACION) != null ? mapa.get(JUSTIFICACION).toString() : "");
		Object numeroPaginas = mapa.get("numeroPaginas");
		mapaArchivo.put("#paginas", numeroPaginas != null ? numeroPaginas.toString() : "");
	}

	private Map<String, String> generarDatosGenerales(CierreModel cierre,
			List<Map<String, String>> listaAdministracionCentral, DateTimeFormatter inputFormatter,
			DateTimeFormatter outputFormatter) {
		Map<String, String> datosGenerales = new HashMap<>();
		datosGenerales.put("administracion_general",
				cierre.getProyectoModel().getFichaTecnicaModel().getCatAdmonPatrocinadora().getAdministracion());
		datosGenerales.put(ADMIN_CENTRAL,
				listaAdministracionCentral.isEmpty() ? "" : listaAdministracionCentral.get(0).get(ADMIN_CENTRAL));
		datosGenerales.put("nombre_proyecto", cierre.getProyectoModel().getNombreCorto());
		datosGenerales.put("id", cierre.getProyectoModel().getIdFormateado());
		datosGenerales.put("nombre_corto", cierre.getProyectoModel().getNombreCorto());
		datosGenerales.put(ID_PROYECTO, cierre.getProyectoModel().getIdFormateado());
		datosGenerales.put("estatus_RCP", cierre.getCatEstatusRcp().getNombre());
		datosGenerales.put("id_proyecto_AGP", String.valueOf(cierre.getProyectoModel().getIdAgp()));
		datosGenerales.put("nombre_completo_del_proyecto", cierre.getProyectoModel().getNombreProyecto());
		datosGenerales.put("lider_de_proyecto",
				cierre.getProyectoModel().getFichaTecnicaModel().getHistoricoModel().getNombre());
		datosGenerales.put("area_de_planeacion", cierre.getUsuario().getNombre());
		datosGenerales.put("%_planeado", String.valueOf(cierre.getPorcentajePlaneado()));
		datosGenerales.put("%_real", String.valueOf(cierre.getPorcentajeReal()));
		datosGenerales.put("fecha_de_entrega", formatFecha(cierre.getFechaEntrega(), inputFormatter, outputFormatter));
		return datosGenerales;
	}

	private String formatFecha(Object fecha, DateTimeFormatter inputFormatter, DateTimeFormatter outputFormatter) {
		if (fecha == null) {
			return "";
		}
		DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_DATE_TIME;
		try {
			LocalDateTime dateTime = LocalDateTime.parse(fecha.toString(), isoFormatter);
			return dateTime.format(outputFormatter);
		} catch (DateTimeParseException e) {
			return LocalDateTime.parse(fecha.toString(), inputFormatter).format(outputFormatter);
		}
	}

	public String obtenerTextoAntesDeSlash(String cadena) {
		String[] partes = cadena.split("/");
		return partes[0];
	}

	@Override
	public CierreModel guardarCierre(CierreModel cierreModel, List<Archivo> archivos) {
		ProyectoModel proyecto = proyectoRepository.findByIdProyectoAndEstatus(cierreModel.getIdProyecto(), true)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.NO_PROCESO_CIERRE));

		Usuario usuario = usuarioRepository.findById(Long.parseLong("" + cierreModel.getIdUser()))
				.orElseThrow(() -> new ProyectoException(ErroresEnum.NO_PROCESO_CIERRE));

		log.info("nombre corto: {}", proyecto.getNombreCorto());
		boolean esRegistro = cierreModel.getIdCierre() != null;

		if (archivos != null) {
			for (Archivo archivo : archivos) {
				log.info("carpeta: {}", archivo.getCarpeta());
				procesarArchivoSegunTipo(archivo);
			}
		}

		CierreModel cierre = cierreRepository.save(cierreModel);
		servicioProyecto.actualizarUltimaModificacion(cierre.getIdProyecto());



		// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(),


		// esRegistro ? TipoMovPista.INSERTA_REGISTRO.getId() : TipoMovPista.ACTUALIZA_REGISTRO.getId(),


		// TipoSeccionPista.VERIFICACION_RCP.getIdSeccionPista(),


		// Constantes.getAtributosCierreGuardado()[0] + proyecto.getNombreCorto() + "|"


		// + Constantes.getAtributosCierreGuardado()[1] + usuario.getNombre() + "|"


		// + Constantes.getAtributosCierreGuardado()[2] + cierreModel.getFechaEntrega() + "|",


		// Optional.of(cierre));

		return cierre;
	}

	@Override
	public CierreModel modificarCierre(CierreModel cierreModel, List<Archivo> archivos) {

		CierreModel cierreExistente = null;

		if (cierreModel.getIdCierre() != null) {
			cierreExistente = cierreRepository.findById(cierreModel.getIdCierre())
					.orElseThrow(() -> new ProyectoException(ErroresEnum.CIERRE_NOT_FOUND));
			cierreExistente.setPorcentajeReal(cierreModel.getPorcentajeReal());
			cierreExistente.setFechaEntrega(cierreModel.getFechaEntrega());
			cierreExistente.setIdUser(cierreModel.getIdUser());
		} else {
			throw new ProyectoException(ErroresEnum.CIERRE_NOT_FOUND);
		}

		if (archivos != null) {
			for (Archivo archivo : archivos) {
				log.info("carpeta: {}", archivo.getCarpeta());
				procesarArchivoSegunTipo(archivo);
			}
		}

		CierreModel cierre = cierreRepository.save(cierreExistente);
		servicioProyecto.actualizarUltimaModificacion(cierre.getIdProyecto());
		boolean esRegistro = cierreModel.getIdCierre() != null;

		// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(),

		// esRegistro ? TipoMovPista.INSERTA_REGISTRO.getId() : TipoMovPista.ACTUALIZA_REGISTRO.getId(),

		// TipoSeccionPista.VERIFICACION_RCP.getIdSeccionPista(),

		// Constantes.getAtributosCierreGuardado()[0] + cierreExistente.getProyectoModel().getNombreCorto() + "|"

		// + Constantes.getAtributosCierreGuardado()[1] + cierreExistente.getUsuario().getNombre() + "|"

		// + Constantes.getAtributosCierreGuardado()[2] + cierreExistente.getFechaEntrega() + "|",

		// Optional.of(cierre));

		return cierre;
	}

	private void procesarArchivoSegunTipo(Archivo archivo) {
		if (archivo instanceof ArchivoPlantillaProyectoModel) {
			ArchivoPlantillaProyectoModel archivoEspecifico = archivoPlantillaProyectoRepository
					.findById(archivo.getId())
					.orElseThrow(() -> new ProyectoException(ErroresEnum.ARCHIVO_NO_ENCONTRADO));

			archivoEspecifico.setJustificacion(archivo.getJustificacion());
			archivoEspecifico.setEstatusDocumento(archivo.getEstatusDocumento());
			archivoEspecifico.setFechaDocumento(archivo.getFechaDocumento());
			archivoEspecifico.setNumeroPaginas(archivo.getNumeroPaginas());
			archivoPlantillaProyectoRepository.save(archivoEspecifico);

		} else if (archivo instanceof ArchivoPlantillaContratoModel) {
			ArchivoPlantillaContratoModel archivoEspecifico = archivoPlantillaContratoRepository
					.findById(archivo.getId())
					.orElseThrow(() -> new ProyectoException(ErroresEnum.ARCHIVO_NO_ENCONTRADO));

			archivoEspecifico.setJustificacion(archivo.getJustificacion());
			archivoEspecifico.setEstatusDocumento(archivo.getEstatusDocumento());
			archivoEspecifico.setFechaDocumento(archivo.getFechaDocumento());
			archivoEspecifico.setNumeroPaginas(archivo.getNumeroPaginas());
			archivoPlantillaContratoRepository.save(archivoEspecifico);

		} else if (archivo instanceof ArchivoPlantillaConvenioModel) {
			ArchivoPlantillaConvenioModel archivoEspecifico = archivoPlantillaConvenioRepository
					.findById(archivo.getId())
					.orElseThrow(() -> new ProyectoException(ErroresEnum.ARCHIVO_NO_ENCONTRADO));

			archivoEspecifico.setJustificacion(archivo.getJustificacion());
			archivoEspecifico.setEstatusDocumento(archivo.getEstatusDocumento());
			archivoEspecifico.setFechaDocumento(archivo.getFechaDocumento());
			archivoEspecifico.setNumeroPaginas(archivo.getNumeroPaginas());
			archivoPlantillaConvenioRepository.save(archivoEspecifico);

		} else if (archivo instanceof ArchivoPlantillaDictamenModel) {
			ArchivoPlantillaDictamenModel archivoEspecifico = archivoPlantillaDictamenRepository
					.findById(archivo.getId())
					.orElseThrow(() -> new ProyectoException(ErroresEnum.ARCHIVO_NO_ENCONTRADO));

			archivoEspecifico.setJustificacion(archivo.getJustificacion());
			archivoEspecifico.setEstatusDocumento(archivo.getEstatusDocumento());
			archivoEspecifico.setFechaDocumento(archivo.getFechaDocumento());
			archivoEspecifico.setNumeroPaginas(archivo.getNumeroPaginas());
			archivoPlantillaDictamenRepository.save(archivoEspecifico);

		} else if (archivo instanceof ArchivoPlantillaReintegroModel) {
			ArchivoPlantillaReintegroModel archivoEspecifico = archivoPlantillaReintegroRepository
					.findById(archivo.getId())
					.orElseThrow(() -> new ProyectoException(ErroresEnum.ARCHIVO_NO_ENCONTRADO));

			archivoEspecifico.setJustificacion(archivo.getJustificacion());
			archivoEspecifico.setEstatusDocumento(archivo.getEstatusDocumento());
			archivoEspecifico.setFechaDocumento(archivo.getFechaDocumento());
			archivoEspecifico.setNumeroPaginas(archivo.getNumeroPaginas());
			archivoPlantillaReintegroRepository.save(archivoEspecifico);

		} else if (archivo instanceof ArchivoOtroDocumentoFaseModel) {
			ArchivoOtroDocumentoFaseModel archivoEspecifico = archivoOtroDocumentoFaseRepository
					.findById(archivo.getId())
					.orElseThrow(() -> new ProyectoException(ErroresEnum.ARCHIVO_NO_ENCONTRADO));

			archivoEspecifico.setJustificacion(archivo.getJustificacion());
			archivoEspecifico.setEstatusDocumento(archivo.getEstatusDocumento());
			archivoEspecifico.setFechaDocumento(archivo.getFechaDocumento());
			archivoEspecifico.setNumeroPaginas(archivo.getNumeroPaginas());
			archivoOtroDocumentoFaseRepository.save(archivoEspecifico);

		} else if (archivo instanceof ArchivoOtroDocumentoFaseContratoModel) {
			ArchivoOtroDocumentoFaseContratoModel archivoEspecifico = archivoOtroDocumentoFaseContratoRepository
					.findById(archivo.getId())
					.orElseThrow(() -> new ProyectoException(ErroresEnum.ARCHIVO_NO_ENCONTRADO));

			archivoEspecifico.setJustificacion(archivo.getJustificacion());
			archivoEspecifico.setEstatusDocumento(archivo.getEstatusDocumento());
			archivoEspecifico.setFechaDocumento(archivo.getFechaDocumento());
			archivoEspecifico.setNumeroPaginas(archivo.getNumeroPaginas());
			archivoOtroDocumentoFaseContratoRepository.save(archivoEspecifico);

		} else if (archivo instanceof ArchivoOtroDocumentoFaseConvenioModel) {
			ArchivoOtroDocumentoFaseConvenioModel archivoEspecifico = archivoOtroDocumentoFaseConvenioRepository
					.findById(archivo.getId())
					.orElseThrow(() -> new ProyectoException(ErroresEnum.ARCHIVO_NO_ENCONTRADO));

			archivoEspecifico.setJustificacion(archivo.getJustificacion());
			archivoEspecifico.setEstatusDocumento(archivo.getEstatusDocumento());
			archivoEspecifico.setFechaDocumento(archivo.getFechaDocumento());
			archivoEspecifico.setNumeroPaginas(archivo.getNumeroPaginas());
			archivoOtroDocumentoFaseConvenioRepository.save(archivoEspecifico);

		} else if (archivo instanceof ArchivoOtroDocumentoFaseDictamenModel) {
			ArchivoOtroDocumentoFaseDictamenModel archivoEspecifico = archivoOtroDocumentoFaseDictamenRepository
					.findById(archivo.getId())
					.orElseThrow(() -> new ProyectoException(ErroresEnum.ARCHIVO_NO_ENCONTRADO));

			archivoEspecifico.setJustificacion(archivo.getJustificacion());
			archivoEspecifico.setEstatusDocumento(archivo.getEstatusDocumento());
			archivoEspecifico.setFechaDocumento(archivo.getFechaDocumento());
			archivoEspecifico.setNumeroPaginas(archivo.getNumeroPaginas());
			archivoOtroDocumentoFaseDictamenRepository.save(archivoEspecifico);

		} else if (archivo instanceof ArchivoPlantillaComiteModel) {
			ArchivoPlantillaComiteModel archivoEspecifico = archivoPlantillaComiteRepository.findById(archivo.getId())
					.orElseThrow(() -> new ProyectoException(ErroresEnum.ARCHIVO_NO_ENCONTRADO));

			archivoEspecifico.setJustificacion(archivo.getJustificacion());
			archivoEspecifico.setEstatusDocumento(archivo.getEstatusDocumento());
			archivoEspecifico.setFechaDocumento(archivo.getFechaDocumento());
			archivoEspecifico.setNumeroPaginas(archivo.getNumeroPaginas());
			archivoPlantillaComiteRepository.save(archivoEspecifico);

		} else if (archivo instanceof ArchivoOtrosDocumentosComiteModel) {
			ArchivoOtrosDocumentosComiteModel archivoEspecifico = archivoOtrosDocumentosComiteRepository
					.findById(archivo.getId())
					.orElseThrow(() -> new ProyectoException(ErroresEnum.ARCHIVO_NO_ENCONTRADO));

			archivoEspecifico.setJustificacion(archivo.getJustificacion());
			archivoEspecifico.setEstatusDocumento(archivo.getEstatusDocumento());
			archivoEspecifico.setFechaDocumento(archivo.getFechaDocumento());
			archivoEspecifico.setNumeroPaginas(archivo.getNumeroPaginas());
			archivoOtrosDocumentosComiteRepository.save(archivoEspecifico);
		}
	}

	@Override
	public CierreModel obtenerCierre(Long idProyecto) {
		return cierreRepository.findByIdProyecto(idProyecto)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.CIERRE_NOT_FOUND));
	}

	@Override
	public List<Double> porcentajes(List<Archivo> archivos) {
	    double documentosCargados = 0;
	    double documentosNoAplica = 0;
	    double documentoSolicitados = 0;
	    double documentosEntregado = 0;
	    double porcentajePlaneado = 100;
	    double porcentajeReal = 0;

	    if (archivos == null || archivos.isEmpty()) {
	        log.info("Lista vacía o nula, retornando valores predeterminados.");
	        return Arrays.asList(porcentajePlaneado, porcentajeReal);
	    }

	    for (Archivo archivo : archivos) {
	        if ("No aplica".equals(archivo.getEstatusDocumento())) {
	            documentosNoAplica++;
	        }
	        if ("Pendiente".equals(archivo.getEstatusDocumento()) && archivo.isObligatorio()) {
	            documentoSolicitados++;
	        }
	        if ("Entregado".equals(archivo.getEstatusDocumento())) {
	            documentosEntregado++;
	        }
	    }

	    log.info("cargados {}", documentosCargados);
	    log.info("no aplica {}", documentosNoAplica);
	    log.info("Pendiente {}", documentoSolicitados);
	    log.info("Entregados {}", documentosEntregado);

	    porcentajeReal = ((documentosEntregado + documentosNoAplica) / archivos.size()) * 100;

	    log.info("porcentaje planeado {}", porcentajePlaneado);
	    log.info("porcentaje real {}", porcentajeReal);

	    return Arrays.asList(porcentajePlaneado, porcentajeReal);
	}


	@Override
	public CierreModel estatusEnProceso(Long idCierre) {
		List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.ESTATUS_RCP.getIdCatalogo(), Constantes.ESTATUS_EN_PROCESO);

		CierreModel cierre = cierreRepository.findById(idCierre)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.CIERRE_NOT_FOUND));

		cierre.setIdEstatusRcp(Long.parseLong("" + lista.get(0).getPrimaryKey()));
		CierreModel cierreNuevo = cierreRepository.save(cierre);
		servicioProyecto.actualizarUltimaModificacion(cierre.getIdProyecto());

		// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),

		// TipoSeccionPista.VERIFICACION_RCP.getIdSeccionPista(),

		// Constantes.getAtributosCierreCancelar()[0] + cierreNuevo.getProyectoModel().getNombreCorto()

		// + Constantes.getAtributosCierreCancelar()[1] + cierreNuevo.getCatEstatusRcp().getNombre() + "|",

		// Optional.of(cierre));

		return cierreNuevo;

	}

	@Override
	public CierreModel revisadoAP(Long idCierre, List<Archivo> archivos) {
		CierreModel cierre = cierreRepository.findByIdCierreAndEstatusTrue(idCierre)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.CIERRE_NOT_FOUND));

		LocalDate fechaInicioProyecto = cierre.getProyectoModel().getFichaTecnicaModel().getFechaInicio();
		LocalDate fechaFinProyecto = cierre.getProyectoModel().getFichaTecnicaModel().getFechaTermino();

		LocalDateTime inicioProyectoDateTime = fechaInicioProyecto.atTime(00, 00, 00);
		LocalDateTime finProyectoDateTime = fechaFinProyecto.atTime(00, 00, 00);

		for (Archivo archivo : archivos) {
			log.info("Estatus del documento: {}", archivo.getEstatusDocumento());

			if ("Entregado".equals(archivo.getEstatusDocumento())) {
				if (archivo.getFechaDocumento().isBefore(inicioProyectoDateTime)
						|| archivo.getFechaDocumento().isAfter(finProyectoDateTime)) {
					throw new ProyectoException(ErroresEnum.FECHAS_ERROR_PROYECTO);
				}

				procesarArchivoSegunTipo(archivo);
			}
		}

		List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.ESTATUS_RCP.getIdCatalogo(), Constantes.ESTATUS_AP);

		if (!lista.isEmpty()) {
			cierre.setIdEstatusRcp(Long.parseLong("" + lista.get(0).getPrimaryKey()));
			cierreRepository.save(cierre);
			servicioProyecto.actualizarUltimaModificacion(cierre.getIdProyecto());

			// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),

			// TipoSeccionPista.VERIFICACION_RCP.getIdSeccionPista(),

			// Constantes.getAtributosCierreCancelar()[0] + cierre.getProyectoModel().getNombreCorto()

			// + Constantes.getAtributosCierreCancelar()[1] + cierre.getCatEstatusRcp().getNombre() + "|",

			// Optional.of(cierre));
		} else {
			throw new ProyectoException(ErroresEnum.CATALOGO_NO_ENCONTRADO);
		}
		return cierre;
	}

	@Override
	public String descargaMasiva(DescargaSatCloudRequest descargaSatCloudRequest) {
		String ruta;
		String resultado;

		if (descargaSatCloudRequest.getPath() != null && !descargaSatCloudRequest.getPath().isEmpty()) {
			ruta = descargaSatCloudRequest.getPath().replaceFirst("^/PROYECTO/", "/" + ConstantesParaRutasSATCloud.PATH_BASE + "/");
		} else {
			ruta = ConstantesParaRutasSATCloud.PATH_BASE + "/" + descargaSatCloudRequest.getIdProyecto();
		}

		resultado = servicioArchivo.descargarFolder(ruta);

		String contenido = "";
		try {
			contenido = nexusImpl.obtenerContenidoCarpeta(ruta);
		} catch (NexusException e) {
			log.error("Error:");
		}



		// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),


		// TipoSeccionPista.VERIFICACION_RCP.getIdSeccionPista(),


		// Constantes.getAtributosCierreSatCloud()[0] + descargaSatCloudRequest.getIdProyecto()


		// + Constantes.getAtributosCierreSatCloud()[1] + contenido + "|",


		// Optional.empty());

		return resultado;
	}

	@Override
	public CarpetaCompartidaDto descargaSatCloud(DescargaSatCloudRequest descargaSatCloudRequest) {
		CarpetaCompartidaDto resultado;
		String ruta;

		if (descargaSatCloudRequest.getPath() != null && !descargaSatCloudRequest.getPath().isEmpty()) {
			ruta = descargaSatCloudRequest.getPath().replaceFirst("^/PROYECTO/", "/" + ConstantesParaRutasSATCloud.PATH_BASE + "/");
		} else {
			ruta = ConstantesParaRutasSATCloud.PATH_BASE + "/" + descargaSatCloudRequest.getIdProyecto();
		}
		String contenido = "";
		try {
			contenido = nexusImpl.obtenerContenidoCarpeta(ruta);
		} catch (NexusException e) {
			log.error("Error:");
		}
		resultado = servicioArchivo.descargarFolderSatCloud(ruta);

		// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),

		// TipoSeccionPista.VERIFICACION_RCP.getIdSeccionPista(),

		// Constantes.getAtributosCierreSatCloud()[0] + descargaSatCloudRequest.getIdProyecto()

		// + Constantes.getAtributosCierreSatCloud()[1] + contenido + "|",

		// Optional.empty());

		return resultado;
	}

	@Override
	public boolean generarRcp(Long idCierre) {
		CierreModel cierre = cierreRepository.findByIdCierreAndEstatusTrue(idCierre)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.CIERRE_NOT_FOUND));

		if (cierre.getFechaEntrega() == null) {
			throw new ProyectoException(ErroresEnum.DATOS_OBLIGATORIOS_CIERRE);
		}

		LocalDate fechaInicioProyecto = cierre.getProyectoModel().getFichaTecnicaModel().getFechaInicio();
		LocalDate fechaFinProyecto = cierre.getProyectoModel().getFichaTecnicaModel().getFechaTermino();

		LocalDateTime inicioProyectoDateTime = fechaInicioProyecto.atTime(00, 00, 00);
		LocalDateTime finProyectoDateTime = fechaFinProyecto.atTime(00, 00, 00);

		if (cierre.getFechaEntrega() != null && (cierre.getFechaEntrega().isBefore(inicioProyectoDateTime)
				|| cierre.getFechaEntrega().isAfter(finProyectoDateTime))) {
			throw new ProyectoException(ErroresEnum.FECHAS_ERROR_PROYECTO);
		}

		List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.ESTATUS_RCP.getIdCatalogo(), Constantes.ESTATUS_ENTREGADO);

		cierre.setIdEstatusRcp(Long.parseLong("" + lista.get(0).getPrimaryKey()));
		cierreRepository.save(cierre);
		servicioProyecto.actualizarUltimaModificacion(cierre.getIdProyecto());

		// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),

		// TipoSeccionPista.VERIFICACION_RCP.getIdSeccionPista(),

		// Constantes.getAtributosCierreCancelar()[0] + cierre.getProyectoModel().getNombreCorto()

		// + Constantes.getAtributosCierreCancelar()[1] + cierre.getCatEstatusRcp().getNombre() + "|",

		// Optional.of(cierre));
		return true;
	}

	@Override
	public boolean validadoLp(Long idCierre) {
		CierreModel cierre = cierreRepository.findByIdCierreAndEstatusTrue(idCierre)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.CIERRE_NOT_FOUND));

		List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.ESTATUS_RCP.getIdCatalogo(), Constantes.ESTATUS_LP);

		cierre.setIdEstatusRcp(Long.parseLong("" + lista.get(0).getPrimaryKey()));
		cierreRepository.save(cierre);
		servicioProyecto.actualizarUltimaModificacion(cierre.getIdProyecto());

		// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),

		// TipoSeccionPista.VERIFICACION_RCP.getIdSeccionPista(),

		// Constantes.getAtributosCierreCancelar()[0] + cierre.getProyectoModel().getNombreCorto()

		// + Constantes.getAtributosCierreCancelar()[1] + cierre.getCatEstatusRcp().getNombre() + "|",

		// Optional.of(cierre));
		return true;
	}

	@Override
	public List<PlantilladorModel> obtenerPlantillasRcp() {

		List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.TIPO_PLANTILLADOR.getIdCatalogo(), Constantes.TIPO_RCP);

		List<PlantilladorModel> plantillasRcp = plantilladorMicriservicio
				.obtenerPlantillador(lista.get(0).getPrimaryKey());

		if (plantillasRcp != null) {
			return plantillasRcp;
		}
		return List.of();
	}

	@Override
	public ByteArrayResource cierreProyectoDescargaArchivo(long idProyecto, long idPlantillador, String typeFile,
			Boolean plantilla) {
		DatosPlantillaRcp data;
		if (Boolean.TRUE.equals(plantilla)) {
			data = this.generarPlantillaRcp(idPlantillador);
		} else {
			data = this.generarMapas(idProyecto, idPlantillador);
		}

		if (typeFile != null && !typeFile.isBlank() && typeFile.equalsIgnoreCase("pdf"))
			return this.descargarPdf(data);
		else if (typeFile != null && !typeFile.isBlank() && typeFile.equalsIgnoreCase("excel")) {
			return this.descargarExcel(data);
		} else
			throw new ProyectoException((ErroresEnum.ERROR_TIPO_ARCHIVO));
	}

	private ByteArrayResource descargarPdf(DatosPlantillaRcp data) {
		HtmlExcelListDto htmlExcelListDto = this.buildHtmlExcelListDto(data);
		return this.administracionPlantilladorMicriservicio.descargarPdf(htmlExcelListDto);
	}

	private ByteArrayResource descargarExcel(DatosPlantillaRcp data) {
		HtmlExcelListDto htmlExcelListDto = this.buildHtmlExcelListDto(data);
		return this.administracionPlantilladorMicriservicio.descargarExcel(htmlExcelListDto);
	}

	private HtmlExcelListDto buildHtmlExcelListDto(DatosPlantillaRcp data) {
		HtmlExcelListDto htmlExcelListDto = new HtmlExcelListDto();
		htmlExcelListDto.setIdSubPlantillador(data.getIdContenidoPlantillador());
		htmlExcelListDto.setDatosGenerales(data.getDatosGenerales());
		htmlExcelListDto.setDatos(data.getListaDocumentos());

		Map<String, String> datosGenerales = data.getDatosGenerales();

		if (datosGenerales != null && datosGenerales.containsKey(ID_PROYECTO)) {
			Long idProyecto = Long.valueOf(datosGenerales.get(ID_PROYECTO));

			CierreModel cierre = cierreRepository.findByIdProyecto(idProyecto)
					.orElseThrow(() -> new ProyectoException(ErroresEnum.CIERRE_NOT_FOUND));

			PlantilladorModel plantillador = plantilladorMicriservicio
					.obtenerPlantilladorPorId(data.getIdContenidoPlantillador());



			// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),


			// TipoSeccionPista.VERIFICACION_RCP.getIdSeccionPista(),


			// Constantes.getPistaGenerarPlantilla()[0] + cierre.getProyectoModel().getNombreCorto() + "|"


			// + Constantes.getPistaGenerarPlantilla()[1] + plantillador.getNombre() + "|"


			// + Constantes.getPistaGenerarPlantilla()[2],


			// Optional.of(cierre));
		}

		return htmlExcelListDto;
	}

	@Override
	public Boolean pistaVerPdf(String nombreCortoProyecto, String entregable, Boolean estatus) {
		if (Boolean.TRUE.equals(estatus)) {

			// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),

			// TipoSeccionPista.VERIFICACION_RCP.getIdSeccionPista(), Constantes.getAtributosVerPdf()[0]

			// + nombreCortoProyecto  + "|" + Constantes.getAtributosVerPdf()[1] + entregable + "|",

			// Optional.empty());
			return true;
		}
		return false;
	}

	@Override
	public Boolean crearPistaPlantilla(String nombreCortoProyecto, String tipoPlantilla, Long idProyecto) {

		CierreModel cierre = cierreRepository.findByIdProyecto(idProyecto)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.CIERRE_NOT_FOUND));



		// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),


		// TipoSeccionPista.VERIFICACION_RCP.getIdSeccionPista(),


		// Constantes.getPistaGenerarPlantilla()[0] + nombreCortoProyecto + "|"


		// + Constantes.getPistaGenerarPlantilla()[1] + tipoPlantilla + "|"


		// + Constantes.getPistaGenerarPlantilla()[2],


		// Optional.of(cierre));

		return true;
	}

}
