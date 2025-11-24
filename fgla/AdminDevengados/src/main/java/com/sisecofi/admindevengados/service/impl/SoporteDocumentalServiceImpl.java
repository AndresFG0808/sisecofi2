package com.sisecofi.admindevengados.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sisecofi.admindevengados.dto.IdSoporteDocumentalDto;
import com.sisecofi.admindevengados.dto.SoporteDocumentalDto;
import com.sisecofi.admindevengados.microservicio.CatalogoMicroservicio;
import com.sisecofi.admindevengados.model.SoporteDocumentalModel;
import com.sisecofi.admindevengados.service.DictamenService;
import com.sisecofi.admindevengados.service.PistaService;
import com.sisecofi.admindevengados.service.SoporteDocumentalService;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.admindevengados.util.consumer.Configuration;
import com.sisecofi.admindevengados.util.consumer.PathGenerator;
import com.sisecofi.admindevengados.util.enums.ErroresEnum;
import com.sisecofi.admindevengados.util.exception.CatalogoException;
import com.sisecofi.libreria.comunes.dto.CarpetaCompartidaDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.contratos.ParticipantesAdministracionModel;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoOtroDocumentoFaseDictamenModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoPlantillaDictamenModel;
import com.sisecofi.libreria.comunes.model.usuario.RolModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.util.ConstantesParaRutasSATCloud;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.libreria.comunes.util.sesion.Session;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoOtroDocumentoFaseDictamenRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoPlantillaDictamenRepository;
import com.sisecofi.admindevengados.repository.DeduccionRepository;
import com.sisecofi.admindevengados.repository.DictamenRepository;
import com.sisecofi.admindevengados.repository.PenasContractualesRepository;
import com.sisecofi.admindevengados.repository.PenasConvencionalesRepository;
import com.sisecofi.admindevengados.repository.SoporteDocumentalRepository;
import com.sisecofi.admindevengados.repository.contrato.ParticipantesContratoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SoporteDocumentalServiceImpl implements SoporteDocumentalService {

	private final NexusImpl nexusImpl;
	private final SoporteDocumentalRepository soporteDocumentalRepository;
	private final PistaService pistaService;
	private final DictamenRepository dictamenRepository;
	private final DeduccionRepository deduccionRepository;
	private final DictamenRepository dictamenRepositoy;
	private final PenasContractualesRepository penasContractualesRepository;
	private final PenasConvencionalesRepository penasConvencionalesRepository;
	private final CatalogoMicroservicio catalogoMicroservicio;
	private final ArchivoPlantillaDictamenRepository archivoRepo;
	private final ArchivoOtroDocumentoFaseDictamenRepository archivoOtroFase;
	private final DictamenService dictamenService;
	private final ParticipantesContratoRepository participantesContratoRepository;
	private final Session session;
	private static final Logger logger = LoggerFactory.getLogger(SoporteDocumentalServiceImpl.class);
	private static final String ERROR_USER= "No se pudo obtener el ID del usuario logueado";
	private static final String VERIFICADOR= "Verificador del contrato";

	@Override
	public String cargarArchivo(Long dictamenId, MultipartFile files) {
		try {
			Dictamen dct= dictamenRepository.findByIdDictamen(dictamenId).orElseThrow(() -> new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));
			Configuration config = new Configuration();
			config.setProperty("baseFolder", ConstantesParaRutasSATCloud.PATH_DICTAMEN);
			config.setProperty("SoporteFilesFolder", ConstantesParaRutasSATCloud.PATH_SOPORTE);
			PathGenerator pathGenerator = new PathGenerator();
			String path = pathGenerator.generarPathSoporte(dct.getIdDictamenVisual() , config);
			cargarArchivoConInformacion(files, path);

			return path + "/" + files.getOriginalFilename();
		} catch (Exception e) {
			log.error("Error al cargar archivos: {}");
			throw new CatalogoException(ErroresEnum.ERROR_AL_GUARDAR_ARCHIVO, e);
		}
	}

	public void cargarArchivoConInformacion(MultipartFile file, String path) {
		try {

			boolean archivo = nexusImpl.cargarArchivo(file.getInputStream(), path, file.getOriginalFilename());
			log.info("Archivo cargado: {},", archivo);

		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_GUARDAR_ARCHIVO, e);
		}
	}

	// renombra el acrhivo PYD NOMBREL ARCH. EXTENSION--
	public void cargarArchivoConInformacionRenombrado(MultipartFile file, String path, String nombre) {
		try {

			boolean archivo = nexusImpl.cargarArchivo(file.getInputStream(), path, nombre);
			log.info("Archivo cargado: {},", archivo);

		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_GUARDAR_ARCHIVO, e);
		}
	}

	@Override
	public CarpetaCompartidaDto descargarFolderSatCloud(String path, Long dictamenId) {
		try {

			String nombreArchivo = path.substring(path.lastIndexOf("/") + 1);



			// pistaService.guardarPista(ModuloPista.ADMIN_DEVENGADOS.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),


			// TipoSeccionPista.DICTAMEN_SOPORTE_DOC.getIdSeccionPista(),


			// Constantes.getAtributosSoporteDocumental()[0] + "|" + dictamenId


			// + Constantes.getAtributosSoporteDocumental()[1] + nombreArchivo,


			// Optional.empty());

			return nexusImpl.crearCarpetaCompartida(path);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}
	}

	// ocupar para decarga (ver detalle) --base 64
	@Override
	public String descargarArchivo(String path, Long dictamenId) {
		try {

			InputStream obj = nexusImpl.descargarArchivo(path);
			log.info("Descargado: {}", obj);
			byte[] bytes = obj.readAllBytes();
			obj.close();

			String nombreArchivo = path.substring(path.lastIndexOf("/") + 1);



			// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),


			// TipoSeccionPista.DICTAMEN_SOPORTE_DOC.getIdSeccionPista(),


			// Constantes.getAtributosSoporteDocumental()[0] + dictamenId + "|"


			// + Constantes.getAtributosSoporteDocumental()[1] + nombreArchivo,


			// Optional.empty());

			return Base64.getEncoder().encodeToString(bytes);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_DESCARGAR_ARCHIVO);
		}
	}

	@Override
	public Boolean validarSiExistenPenasDeducciones(Long dictamenId) {
		return penasContractualesRepository.existsByIdDictamenAndEstatusTrue(dictamenId)
				|| penasConvencionalesRepository.existsByIdDictamenAndEstatusTrue(dictamenId)
				|| deduccionRepository.existsByIdDictamenAndEstatusTrue(dictamenId);
	}

	@Override
	public SoporteDocumentalModel guardarOActualizarSoporteDocumental(SoporteDocumentalDto soporteDocumental,
			MultipartFile detallePenasDeducciones) {

		log.info("dictamen: {}", soporteDocumental.getIdDictamen());
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

		SoporteDocumentalModel modelo;
		modelo = obtenerModeloSoporteDocumental(soporteDocumental);

		procesarArchivoPenasDeducciones(detallePenasDeducciones, soporteDocumental, modelo);
		Dictamen dictamen = obtenerDictamen(soporteDocumental.getIdDictamen());

		dictamen.getContratoModel().getVigencia().getCatTipoMoneda().getNombre();

		LocalDateTime fechaSolicitudDictamen = parsearFecha(soporteDocumental.getFechaSolicitudDictamen(),
				outputFormatter);
		LocalDateTime fechaRecepcionDictamen = parsearFecha(soporteDocumental.getFechaRecepcionDictamen(),
				outputFormatter);

		modelo.setIdDictamen(soporteDocumental.getIdDictamen());
		modelo.setNumeroOficio(
				(soporteDocumental.getNumeroOficio() == null || soporteDocumental.getNumeroOficio().isEmpty()) ? null
						: soporteDocumental.getNumeroOficio());

		if (dictamen.getIdEstatusDictamen() == 2) {
			if (fechaSolicitudDictamen == null) {
				fechaSolicitudDictamen = fechaRecepcionDictamen;
			} else if (fechaRecepcionDictamen != null && fechaRecepcionDictamen.isBefore(fechaSolicitudDictamen)) {
				throw new CatalogoException(ErroresEnum.ERROR_FECHA_RECEPCION_MENOR_FECHA_SOLICITUD);
			}
		}
		Optional<Dictamen> dictamenUltimo = Optional.ofNullable(modelo.getDictamen());

		if (!dictamenUltimo.isPresent()) {

			dictamenUltimo = dictamenRepository.findByIdDictamen(modelo.getIdDictamen());
		}

		if (dictamenUltimo.isPresent()) {
			Dictamen dictamenBuscado = dictamenUltimo.get();
			dictamenBuscado.setUltimaModificacion(dictamenService.ultimaModificacionGeneral());
			dictamenRepository.save(dictamenBuscado);
		}

		modelo.setFechaSolicitudDictamen(fechaSolicitudDictamen);
		modelo.setFechaRecepcionDictamen(fechaRecepcionDictamen);
		try {
			return soporteDocumentalRepository.save(modelo);
		} catch (DataAccessException e) {
			log.error("Error al guardar el modelo en el repositorio: {}", modelo);
			throw new CatalogoException(ErroresEnum.ERROR_GUARDAR_PISTA);
		}

	}

	private SoporteDocumentalModel obtenerModeloSoporteDocumental(SoporteDocumentalDto soporteDocumental) {
		if (soporteDocumental.getIdSoporteDocumento() != null) {
			return soporteDocumentalRepository.findById(soporteDocumental.getIdSoporteDocumento())
					.orElse(new SoporteDocumentalModel());
		} else {
			if (soporteDocumentalRepository.existsByIdDictamen(soporteDocumental.getIdDictamen())) {
				throw new CatalogoException(ErroresEnum.EXISTE_SOPORTE_DOCUMENTAL_DICTAMEN);
			}
			return new SoporteDocumentalModel();
		}
	}

	// VALIDA basarse de este metodo para proforma

	private void procesarArchivoPenasDeducciones(MultipartFile detallePenasDeducciones,
			SoporteDocumentalDto soporteDocumental, SoporteDocumentalModel modelo) {
		ArchivoPlantillaDictamenModel archivo = archivoRepo
				.findByNombreContainingAndCarpetaPlantillaModelIdDictamen("02_PYD",
						soporteDocumental.getIdDictamen())
				.orElse(null);
		
		boolean validar = validarSiExistenPenasDeducciones(modelo.getIdDictamen());

		if(validar) {
			comprobarObligatorio(archivo, (detallePenasDeducciones!=null && !detallePenasDeducciones.isEmpty()));
		}
		
		
		if (detallePenasDeducciones != null && !detallePenasDeducciones.isEmpty()) {
			
			
			String nombreOriginal = detallePenasDeducciones.getOriginalFilename();
			
			if (nombreOriginal != null && nombreOriginal.endsWith(".xlsx")) { // para solictud factura PDF
				procesarArchivoPenasDeduccionesComplemento( soporteDocumental, archivo, detallePenasDeducciones, modelo);
				
			} else {
				throw new CatalogoException(ErroresEnum.ERROR_EXTENSION_EXCEL);
			}
		} else if (modelo.getIdSoporteDocumento() == null) {
			modelo.setPathPenasDeducciones(null);
			modelo.setNombrePenasDeducciones(null);
		}
	}
	
	private void procesarArchivoPenasDeduccionesComplemento(SoporteDocumentalDto soporteDocumental, ArchivoPlantillaDictamenModel archivo, MultipartFile detallePenasDeducciones, SoporteDocumentalModel modelo) {
		Dictamen dictamen = obtenerDictamen(soporteDocumental.getIdDictamen());

		// usar generarRutaproforma
		String pathZip = dictamenService.generarRuta(dictamen.getIdDictamenVisual(),
				dictamen.getContratoModel());
		
        if (archivo!=null) {
        	String extensionExcel = ".xlsx";
			archivo.setRuta(pathZip + "/" + archivo.getNombre() + extensionExcel); //
			archivo.setCargado(true);
			archivo.setTamanoMb((double) detallePenasDeducciones.getSize() / (1024 * 1024));
			archivoRepo.save(archivo);
        
		

		try {
			cargarArchivoConInformacionRenombrado(detallePenasDeducciones, pathZip,
					archivo.getNombre() + extensionExcel);
		} catch (Exception e) {

			if (e.getMessage() != null && e.getMessage().contains("Connection is closed")) {
				throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
			}
			throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
		}
		modelo.setArchivoXlsx(archivo);
		if (archivo.isCargado()) {
			modelo.setPathPenasDeducciones(pathZip + "/" + archivo.getNombre() + extensionExcel);

		}
        }
		modelo.setNombrePenasDeducciones(soporteDocumental.getNombrePenasDeducciones());
	}

	private LocalDateTime parsearFecha(String fecha, DateTimeFormatter formatter) {
		return (fecha == null || fecha.isEmpty()) ? null : LocalDateTime.parse(fecha, formatter);
	}

	private Dictamen obtenerDictamen(Long idDictamen) {

		return dictamenRepository.findByIdDictamen(idDictamen)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));
	}

	public boolean validarContenidoZip(MultipartFile zipFile) {
		try (InputStream inputStream = zipFile.getInputStream();
				ZipArchiveInputStream zipInputStream = new ZipArchiveInputStream(inputStream, "UTF-8", true)) {

			ZipArchiveEntry entry = zipInputStream.getNextZipEntry();

			if (entry == null) {
				throw new CatalogoException(ErroresEnum.ERROR_LEER_ZIP);
			}

			while (entry != null) {
				String entryName = entry.getName();
				if (entryName == null || entryName.isEmpty()) {
					throw new CatalogoException(ErroresEnum.ERROR_LEER_ZIP);
				}

				String extension = FilenameUtils.getExtension(entryName);
				if (!"pdf".equalsIgnoreCase(extension)) {
					return false;
				}
				entry = zipInputStream.getNextZipEntry();
			}
		} catch (IOException e) {
			throw new CatalogoException(ErroresEnum.ERROR_LEER_ZIP);
		}
		return true;
	}

	@Override
	public SoporteDocumentalModel actualizarEntregablese(IdSoporteDocumentalDto idSoporteDocumental,
			MultipartFile entregables) {
		log.info("id soporte: {}", idSoporteDocumental);

		SoporteDocumentalModel modelo = obtenerSoporteDocumental(idSoporteDocumental);
		Dictamen dictamen = obtenerDictamen(modelo);
		String pathZip = dictamenService.generarRuta(dictamen.getIdDictamenVisual(), dictamen.getContratoModel());
		String nombreArchivo= "03_Entregables_"+dictamen.getIdDictamenVisual();
		nombreArchivo= nombreArchivo.replace('|', '-');		
		Archivo archivo = obtenerArchivo(modelo, nombreArchivo, dictamen.getIdDictamen());	
		boolean validar = validarSiExistenPenasDeducciones(modelo.getIdDictamen());

		if(validar) {
			comprobarObligatorio(archivo, (entregables!=null && !entregables.isEmpty()));
		}
		
		if (entregables != null && !entregables.isEmpty()) {
			String nombreOriginal = entregables.getOriginalFilename();
			String extension = obtenerExtension(nombreOriginal);
			validarExtension(extension);
			
			cargarArchivo(entregables, pathZip, nombreArchivo + ".zip");

			actualizarArchivo(archivo, pathZip, entregables);

			actualizarModeloConEntregables(modelo, archivo, pathZip, idSoporteDocumental);
		}
			
		
		actualizarUltimaModificacion(modelo);

		return soporteDocumentalRepository.save(modelo);
	}
	
	
	private void comprobarObligatorio(Archivo archivo, boolean carga) {
		
		if (archivo!=null && archivo.isObligatorio() && !carga && !archivo.isCargado()) {
			throw new CatalogoException(ErroresEnum.DATOS_OBLIGATORIOS);
		}
	}
	

	private SoporteDocumentalModel obtenerSoporteDocumental(IdSoporteDocumentalDto idSoporteDocumental) {
		return soporteDocumentalRepository.findById(idSoporteDocumental.getIdSoporteDocumental())
				.orElse(new SoporteDocumentalModel());
	}

	private Dictamen obtenerDictamen(SoporteDocumentalModel modelo) {
		return dictamenRepositoy.findByIdDictamen(modelo.getIdDictamen())
				.orElseThrow(() -> new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));
	}


	private String obtenerExtension(String nombreOriginal) {
		if (nombreOriginal == null || !nombreOriginal.contains(".")) {
			throw new CatalogoException(ErroresEnum.ERROR_EXTENSION_ZIP);
		}
		return nombreOriginal.substring(nombreOriginal.lastIndexOf(".") + 1);
	}

	private void validarExtension(String extension) {
		if (!"zip".equalsIgnoreCase(extension)) {
			throw new CatalogoException(ErroresEnum.ERROR_EXTENSION_ZIP);
		}
	}

	private Archivo obtenerArchivo(SoporteDocumentalModel modelo, String nombreArchivo, Long idDictamen) {
		ArchivoPlantillaDictamenModel archivo= archivoRepo
				.findByNombreContainingAndCarpetaPlantillaModelIdDictamen("Entregables", modelo.getIdDictamen())
				.orElse( null);
		
		if (archivo==null) {
			
			ArchivoOtroDocumentoFaseDictamenModel archivo2 = new ArchivoOtroDocumentoFaseDictamenModel();
			archivo2.setDescripcion("Entregables del servicio");
			archivo2.setNombre(nombreArchivo);
			archivo2.setEstatus(true);
			archivo2.setNivel(1);
			archivo2.setObligatorio(false);
			archivo2.setOrden(1);
			archivo2.setTipo("Archivo");
			archivo2.setIdDictamen(idDictamen);
			return archivo2;
		    }else {
		    	return archivo;
		    }
		
	}

	private void actualizarArchivo(Archivo archivo, String pathZip, MultipartFile entregables) {

		archivo.setRuta(pathZip + "/" + archivo.getNombre() + ".zip");
		archivo.setCargado(true);
		archivo.setTamanoMb((double) entregables.getSize() / (1024 * 1024));
		
		if (archivo instanceof ArchivoPlantillaDictamenModel archivoEspecifico) {
			archivoRepo.save(archivoEspecifico);
		}else if (archivo instanceof ArchivoOtroDocumentoFaseDictamenModel archivoEspecifico) {
			archivoOtroFase.save(archivoEspecifico);
			}
		
	}

	private void cargarArchivo(MultipartFile entregables, String pathZip, String nombreArchivo) {
		try {
			cargarArchivoConInformacionRenombrado(entregables, pathZip, nombreArchivo);
		} catch (Exception e) {

			if (e.getMessage() != null && e.getMessage().contains("Connection is closed")) {
				throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
			}
			throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
		}
	}

	private void actualizarModeloConEntregables(SoporteDocumentalModel modelo, Archivo archivo,
			String pathZip, IdSoporteDocumentalDto idSoporteDocumental) {
		if (archivo instanceof ArchivoPlantillaDictamenModel archivoEspecifico) {
			modelo.setArchivoZip(archivoEspecifico);
		}
		if (archivo.isCargado()) {
			modelo.setPathEntregables(pathZip + "/" + archivo.getNombre() + ".zip");
		}
		modelo.setNombreEntregables(idSoporteDocumental.getNombreDocumento());
	}

	private void actualizarUltimaModificacion(SoporteDocumentalModel modelo) {
		Optional<Dictamen> dictamenUltimo = Optional.ofNullable(modelo.getDictamen());

		if (dictamenUltimo.isEmpty()) {
			dictamenUltimo = dictamenRepository.findByIdDictamen(modelo.getIdDictamen());
		}

		if (dictamenUltimo.isPresent()) {
			Dictamen dictamen = dictamenUltimo.get();

			// Forzar la inicialización si es un proxy
			if (dictamen instanceof HibernateProxy) {
				dictamen = (Dictamen) ((HibernateProxy) dictamen).getHibernateLazyInitializer().getImplementation();
			}

			dictamen.setUltimaModificacion(dictamenService.ultimaModificacionGeneral());
			dictamenRepository.save(dictamen);
		}
	}

	// basarse para solictud para factura
	@Override
	public SoporteDocumentalModel actualizarOficio(IdSoporteDocumentalDto idSoporteDocumental, MultipartFile oficio) {
		SoporteDocumentalModel modelo = obtenerSoporteDocumental(idSoporteDocumental);
		Dictamen dictamen = obtenerDictamen(modelo);

		String pathPdf = dictamenService.generarRuta(dictamen.getIdDictamenVisual(), dictamen.getContratoModel());
		
		ArchivoPlantillaDictamenModel archivo = obtenerArchivoOficio(modelo);
		
		 Boolean estatusResponsabilidad = validarResponsabilidad(dictamen.getIdContrato());
	        if (Boolean.TRUE.equals(estatusResponsabilidad)) {
	        	comprobarObligatorio(archivo, (validarOficio(oficio)));
	        }
		
		String extension;
        if (oficio!=null && !oficio.isEmpty()) {
        	extension = obtenerExtension(oficio);
        	validarExtensionPdf(extension);
        	cargarArchivo(oficio, pathPdf, archivo.getNombre() + ".pdf");
        	actualizarArchivoOficio(archivo, pathPdf, oficio);
        }

       
		

		actualizarModeloConOficio(modelo, archivo, pathPdf, idSoporteDocumental);

		actualizarUltimaModificacion(modelo);
		return soporteDocumentalRepository.save(modelo);
	}

	private boolean validarOficio(MultipartFile oficio) {
	    return oficio != null && !oficio.isEmpty();
	}

	private String obtenerExtension(MultipartFile archivo) {
		String nombreOriginal = archivo.getOriginalFilename();
		if (nombreOriginal == null || !nombreOriginal.contains(".")) {
			throw new CatalogoException(ErroresEnum.ERROR_EXTENSION_PDF);
		}
		return nombreOriginal.substring(nombreOriginal.lastIndexOf(".") + 1);
	}

	private void validarExtensionPdf(String extension) {
		if (!"pdf".equalsIgnoreCase(extension)) {
			throw new CatalogoException(ErroresEnum.ERROR_EXTENSION_PDF);
		}
	}

	private ArchivoPlantillaDictamenModel obtenerArchivoOficio(SoporteDocumentalModel modelo) {
		return archivoRepo
				.findByNombreContainingAndCarpetaPlantillaModelIdDictamen("OfDicAcepSer", modelo.getIdDictamen())
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_ARCHIVO_NO_ENCONTRADO));
	}

	private void actualizarArchivoOficio(ArchivoPlantillaDictamenModel archivo, String pathPdf, MultipartFile oficio) {
		archivo.setRuta(pathPdf + "/" + archivo.getNombre() + ".pdf");
		archivo.setCargado(true);
		archivo.setTamanoMb((double) oficio.getSize() / (1024 * 1024));
		archivoRepo.save(archivo);
	}

	private void actualizarModeloConOficio(SoporteDocumentalModel modelo, ArchivoPlantillaDictamenModel archivo,
			String pathPdf, IdSoporteDocumentalDto idSoporteDocumental) {
		modelo.setArchivoPdf(archivo);
		if (archivo.isCargado()) {
			modelo.setPathOficio(pathPdf + "/" + archivo.getNombre() + ".pdf");
			modelo.setNombreOficio(idSoporteDocumental.getNombreDocumento());
		}
		
	}

	@Override
	public SoporteDocumentalModel obtenerSoporteDocumental(Long dictamenId) {
		try {

			return soporteDocumentalRepository.findByIdDictamen(dictamenId);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}
	}

	@Override
	public Dictamen dictaminado(Long dictamenId) {
		log.info("dictamenId {}", dictamenId);

		SoporteDocumentalModel soporte = obtenerSoporteDocumental(dictamenId);

		
		validarExtensiones(soporte);

		List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.ESTATUS_DICTAMEN.getIdCatalogo(), Constantes.ESTATUS_DICTAMINADO);

		soporte.getDictamen().setIdEstatusDictamen(lista.get(0).getPrimaryKey());

		actualizarUltimaModificacion(soporte);

		Dictamen dictamen = dictamenRepositoy.findByIdDictamen(dictamenId)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.MSJ_NOT_FOUND_DICTAMEN));

		if (dictamen instanceof HibernateProxy) {
			dictamen = (Dictamen) ((HibernateProxy) dictamen).getHibernateLazyInitializer().getImplementation();
		}



		// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),


		// TipoSeccionPista.DICTAMEN_DATOS_GENERALES.getIdSeccionPista(),


		// Constantes.getAtributosDictaminado()[0] + dictamen.getIdDictamen() + "|"


		// + Constantes.getAtributosDictaminado()[1] + dictamen.getCatEstatusDictamen().getNombre(),


		// Optional.of(dictamen));

		return dictamen;
	}

	private void validarArchivos(SoporteDocumentalModel soporte) {
		Boolean validar = validarSiExistenPenasDeducciones(soporte.getIdDictamen());
		log.info("Estatus de validación (penas/deducciones): {}", validar);
		Boolean estatusResponsabilidad = validarResponsabilidad(soporte.getDictamen().getIdContrato());
		String archivoExcel = soporte.getNombrePenasDeducciones();
		String archivoZip = soporte.getNombreEntregables();
		String archivoPdf = soporte.getNombreOficio();

		if (Boolean.TRUE.equals(validar)) {
			if (archivoExcel == null || archivoExcel.isEmpty() || archivoZip == null || archivoZip.isEmpty()) {
				log.error("Falta información en los archivos (Excel, ZIP) cuando validar es true");
				throw new CatalogoException(ErroresEnum.FALTA_INFORMACION_SOPORTE_DOCUEMNTAL);
			}
			if (Boolean.TRUE.equals(estatusResponsabilidad) && (archivoPdf == null || archivoPdf.isEmpty())) {
				log.error("Falta el archivo PDF cuando validar es true y estatusResponsabilidad es false");
				throw new CatalogoException(ErroresEnum.FALTA_INFORMACION_SOPORTE_DOCUEMNTAL);
			}
		} else {
			if (Boolean.TRUE.equals(estatusResponsabilidad) && (archivoPdf == null || archivoPdf.isEmpty())) {
				log.error("Falta el archivo PDF cuando validar es false y estatusResponsabilidad es false");
				throw new CatalogoException(ErroresEnum.FALTA_INFORMACION_SOPORTE_DOCUEMNTAL);
			}
		}
	}

	private void validarExtensiones(SoporteDocumentalModel soporte) {
		List<String> extensionesValidas = Arrays.asList("pdf", "zip", "xlsx");

		validarExtensionArchivo(soporte.getNombrePenasDeducciones(), extensionesValidas,
				ErroresEnum.ERROR_EXTENSION_EXCEL, "Excel");
		validarExtensionArchivo(soporte.getNombreEntregables(), extensionesValidas, ErroresEnum.ERROR_EXTENSION_EXCEL,
				"ZIP");
		validarExtensionArchivo(soporte.getNombreOficio(), extensionesValidas, ErroresEnum.ERROR_EXTENSION_EXCEL,
				"PDF");
	}

	private void validarExtensionArchivo(String archivo, List<String> extensionesValidas, ErroresEnum error,
			String tipoArchivo) {
		if (archivo != null && !archivo.isEmpty()) {
			String extension = archivo.substring(archivo.lastIndexOf(".") + 1).toLowerCase();
			if (!extensionesValidas.contains(extension)) {
				log.error("La extensión del archivo {} no es válida: {}", tipoArchivo, extension);
				throw new CatalogoException(error);
			}
		}
	}

	public Optional<Usuario> obtenerUsuario() {
		return session.retornarUsuario();
	}

	@Override
	public Boolean validarResponsabilidad(Long idContrato) {
		log.info("Iniciando validación de responsabilidad para el contrato con ID: {}", idContrato);

		List<ParticipantesAdministracionModel> listaParticipantes = participantesContratoRepository
				.findByIdContratoAndEstatusTrueAndVigenteTrue(idContrato);
		log.info("Participantes encontrados: {}", listaParticipantes.size());

		Integer administradorId = null;
		Integer verificadorId = null;
		Integer nivelAdmin = null;
		Integer nivelVerif = null;

		Optional<Usuario> usuarioLogueado = obtenerUsuario();
		Long userIdLogueado = usuarioLogueado.map(Usuario::getIdUser).orElse(null);

		log.info("Usuario logueado: {}", userIdLogueado);

		if (userIdLogueado == null) {
			log.error(ERROR_USER);
			return false;
		}

		for (ParticipantesAdministracionModel participante : listaParticipantes) {
			String responsabilidad = participante.getCatResponsabilidad().getNombre();
			Integer userId = participante.getIdReferencia();
			Integer nivel = participante.getNivel();

			log.info("Analizando participante: userId={}, responsabilidad={}", userId, responsabilidad);

			if (responsabilidad.trim().equals("Administrador del contrato")) {
				administradorId = userId;
				nivelAdmin = nivel;
				log.info("Se identificó al administrador del contrato: {}", administradorId);
			} else if (responsabilidad.trim().equals(VERIFICADOR)) {
				verificadorId = userId;
				nivelVerif = nivel;
				log.info("Se identificó al verificador general: {}", verificadorId);
			}

			if (administradorId != null && verificadorId != null && administradorId.equals(verificadorId) && nivelAdmin.equals(nivelVerif)) {
				log.warn("El usuario logueado tiene ambas responsabilidades: administrador y verificador");
				return false;
			}
		}

		log.info("Validación completada exitosamente. Usuario no tiene ambas responsabilidades.");
		return true;
	}

	@Override
	public Boolean validarResponsabilidadFechaRecepcion(Long dictamenId) {
		Dictamen dictamen = dictamenRepositoy.findByIdDictamen(dictamenId)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));

		List<ParticipantesAdministracionModel> listaParticipantes = participantesContratoRepository
				.findByIdContratoAndEstatusTrueAndVigenteTrue(dictamen.getIdContrato());

		Optional<Usuario> usuarioLogueado = obtenerUsuario();
		Long userIdLogueado = usuarioLogueado.map(Usuario::getIdUser).orElse(null);
		Long administradorId = null;
		Long verificadorId = null;

		if (userIdLogueado == null) {
			log.error(ERROR_USER);
			return false;
		}

		if (isAdmin(usuarioLogueado) || isVerificadorGeneral(usuarioLogueado)) {
			return true;
		}

		for (ParticipantesAdministracionModel participante : listaParticipantes) {
			String responsabilidad = participante.getCatResponsabilidad().getNombre();
			Long userId = participante.getIdUser();

			log.info("Analizando participante: userId={}, responsabilidad={}", userId, responsabilidad);

			if (responsabilidad.trim().equals("Participantes en la administración de la verificación")) {
				administradorId = userId;
				log.info("Se identificó al administrador del contrato: {}", administradorId);
			} else if (responsabilidad.trim().equals(VERIFICADOR)) {
				verificadorId = userId;
				log.info("Se identificó al verificador general: {}", verificadorId);
			}

			if (administradorId != null && verificadorId != null && administradorId.equals(verificadorId)) {
				log.warn("El usuario logueado tiene ambas responsabilidades: administrador y verificador");
				return false;
			} else if (Objects.equals(administradorId, userIdLogueado)
					|| Objects.equals(verificadorId, userIdLogueado)) {
				log.warn("El usuario logueado cumple con una de las dos responsabilidades o ambas");
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean validarResponsabilidadSoporteDictaminado(Long dictamenId) {
		Dictamen dictamen = dictamenRepositoy.findByIdDictamen(dictamenId)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));

		List<ParticipantesAdministracionModel> listaParticipantes = participantesContratoRepository
				.findByIdContratoAndEstatusTrueAndVigenteTrue(dictamen.getIdContrato());

		Optional<Usuario> usuarioLogueado = obtenerUsuario();
		Long userIdLogueado = usuarioLogueado.map(Usuario::getIdUser).orElse(null);

		if (isAdmin(usuarioLogueado)) {
			return true;
		}

		if (userIdLogueado == null) {
			log.error(ERROR_USER);
			return false;
		}

		String estatusDictamen = dictamen.getCatEstatusDictamen().getNombre();

		return validarAccesoPorEstatus(estatusDictamen, listaParticipantes, userIdLogueado, usuarioLogueado);
	}

	private boolean isAdmin(Optional<Usuario> usuarioLogueado) {
		if (usuarioLogueado.isPresent()) {
			List<RolModel> roles = usuarioLogueado.get().getRolModels();
			return roles.stream().anyMatch(rol -> "cn=SAT_SISECOFI_ADMIN_SIS".equals(rol.getNombre())
					|| "cn=SAT_SISECOFI_ADMIN_SIS_SEC".equals(rol.getNombre()));
		}
		return false;
	}

	private boolean isVerificadorGeneral(Optional<Usuario> usuarioLogueado) {
		if (usuarioLogueado.isPresent()) {
			List<RolModel> roles = usuarioLogueado.get().getRolModels();
			return roles.stream().anyMatch(rol -> "cn=SAT_SISECOFI_VERI_GEN".equals(rol.getNombre()));
		}
		return false;
	}

	private boolean validarAccesoPorEstatus(String estatusDictamen,
			List<ParticipantesAdministracionModel> listaParticipantes, Long userIdLogueado,
			Optional<Usuario> usuarioLogueado) {
		switch (estatusDictamen) {
		case "Inicial":
			return verificarAcceso(listaParticipantes, userIdLogueado,
					List.of("Participante en el dictamen", "Administrador del contrato"), usuarioLogueado);
		case "Dictaminado", "Proforma":
			return verificarAcceso(listaParticipantes, userIdLogueado,
					List.of("Participante en la administración de la verificación", VERIFICADOR),
					usuarioLogueado);
		default:
			return true;
		}
	}

	private boolean verificarAcceso(List<ParticipantesAdministracionModel> listaParticipantes, Long userIdLogueado,
			List<String> responsabilidadesPermitidas, Optional<Usuario> usuarioLogueado) {
		for (ParticipantesAdministracionModel participante : listaParticipantes) {
			String responsabilidad = participante.getCatResponsabilidad().getNombre();
			Long userId = participante.getIdUser();

			if (responsabilidadesPermitidas.contains(responsabilidad) && userId.equals(userIdLogueado)
					|| isVerificadorGeneral(usuarioLogueado)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean crearPista(Long idSoporteDocumental, Long idDictamen, Boolean estatusPeticiones) {
		logger.debug("Método crearPista iniciado con idSoporteDocumental={}, idDictamen={}, estatusPeticiones={}",
				idSoporteDocumental, idDictamen, estatusPeticiones);

		if (Boolean.TRUE.equals(estatusPeticiones)) {
			SoporteDocumentalModel soporte = soporteDocumentalRepository.findByIdDictamen(idDictamen);

			if (soporte != null) {
				boolean esCreacion = idSoporteDocumental == null;
				logger.debug("Es creación: {}", esCreacion);



				// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(),


				// esCreacion ? TipoMovPista.INSERTA_REGISTRO.getId() : TipoMovPista.ACTUALIZA_REGISTRO.getId(),


				// TipoSeccionPista.DICTAMEN_SOPORTE_DOC.getIdSeccionPista(),


				// Constantes.getAtributosSoporteDocumentalRegiActua()[0] + idDictamen, Optional.of(soporte));

				logger.debug("Pista guardada correctamente para el idDictamen={}", idDictamen);
				return true;
			} else {
				logger.warn("No se encontró SoporteDocumentalModel para el idDictamen={}", idDictamen);
			}
		} else {
			logger.debug("El valor de estatusPeticiones es falso, no se realiza ninguna operación.");
		}

		logger.debug("Método crearPista finalizado");
		return false;
	}

}
