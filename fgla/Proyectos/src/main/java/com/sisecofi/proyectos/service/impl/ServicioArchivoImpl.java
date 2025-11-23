package com.sisecofi.proyectos.service.impl;

import com.sisecofi.libreria.comunes.dto.CarpetaCompartidaDto;
import com.sisecofi.libreria.comunes.dto.PapeleraDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.ArchivoOtroDocumentoFaseModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.ArchivoOtroDocumentoProyectoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.ArchivoPlantillaProyectoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoOtrosDocumentosComiteModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoPlantillaComiteModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.AsociasionComitePlantillaModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ComiteProyectoModel;
import com.sisecofi.libreria.comunes.util.ConstantesParaRutasSATCloud;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.proyectos.dto.*;
import com.sisecofi.proyectos.microservicio.CatalogoMicroservicio;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import com.sisecofi.libreria.comunes.repository.ArchivoOtroDocumentoFaseRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoOtroDocumentoProyectoRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoOtrosDocumentosComiteRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoPlantillaComiteRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoPlantillaProyectoRepository;
import com.sisecofi.proyectos.microservicio.PapeleraServico;
import com.sisecofi.proyectos.microservicio.PapeleraServicoControl;
import com.sisecofi.proyectos.repository.*;
import com.sisecofi.proyectos.service.PistaService;
import com.sisecofi.proyectos.util.Constantes;
import com.sisecofi.proyectos.util.consumer.Configuration;
import com.sisecofi.proyectos.util.consumer.PathGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.sisecofi.proyectos.service.ServicioArchivo;
import com.sisecofi.proyectos.service.ServicioProyecto;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.regex.*;
import org.springframework.web.multipart.MultipartFile;
import com.sisecofi.proyectos.util.exception.ProyectoException;
import com.sisecofi.proyectos.util.enums.ErroresEnum;

import java.io.*;
import java.util.Base64;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ServicioArchivoImpl implements ServicioArchivo {

	private final ComiteRepository comiteRepository;
	private final AsociacionComiteRepository asociacionComiteRepository;
	private final ArchivoPlantillaComiteRepository archivoPlantillaComiteRepository;
	private final ArchivoOtrosDocumentosComiteRepository archivoOtrosDocumentosComiteRepository;
	private final CatalogoMicroservicio catalogoMicroservicio;
	private final PistaService pistaService;
	private final ArchivoOtroDocumentoFaseRepository archivoOtroDocumentoFaseRepository;
	private final ArchivoOtroDocumentoProyectoRepository archivoOtroDocumentoProyectoRepository;
	private final ArchivoPlantillaProyectoRepository archivoPlantillaProyectoRepository;
	private final NexusImpl nexusImpl;
	private final PapeleraServico papeleraServicio;
	private final PapeleraServicoControl papeleraServicoControl;
	private static final String PEPELERA = "/PAPELERA";
	private static final String SEPARADOR = "/";
	private static final String CONEXION_CERRADO = "Connection is closed";
	private static final String ERROR_ELIMINAR_ARCHIVO=  "No se pudo eliminar el archivo temporal: {}";
	private static final String ARCHIVO_ELIMINADO = "Archivo temporal eliminado: {}";
	private final ServicioProyecto servicioProyecto;

	@Override
	public String cargarArchivo(Integer idComiteProyecto, MultipartFile file) {

		ComiteProyectoModel comiteProyecto = comiteRepository.findByIdComiteProyecto(idComiteProyecto)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.COMITE_PROYECTO_NO_ENCONTRADO));
		try {
			log.info("Cuerpo de la peticion: {}", idComiteProyecto);

			Integer idComite = comiteProyecto.getIdComite();
			ProyectoModel proyecto = comiteProyecto.getProyectoModel();
			Long idProyecto = proyecto.getIdProyecto();

			Configuration config = new Configuration();
			config.setProperty(Constantes.BASE_FOLDER, ConstantesParaRutasSATCloud.PATH_BASE);
			config.setProperty(Constantes.COMITE_FOLDER, ConstantesParaRutasSATCloud.PATH_COMITE_FILES);

			PathGenerator pathGenerator = new PathGenerator();
			String path = pathGenerator.generarPath(idProyecto, idComite, config, idComiteProyecto, "");

			cargarArchivoConInformacion(file, path);

			pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),
					TipoSeccionPista.PROYECTO_DATOS_GENERALES.getIdSeccionPista(),
					Constantes.ID_PROYECTO + idProyecto.toString() + "|" + "id comite proyecto: " + idComite,
					Optional.empty());

			return file.getOriginalFilename();

		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.CONEXION_PERDIDA, e.getMessage());
		}
	}

	@Override
	@Transactional
	public String cargarArchivoFase(ArchivoFaseDto archivoDto) {
		Archivo archivo = archivoDto.getArchivo();
		Long idProyecto = archivoDto.getIdProyecto();
		String faseNombre = archivoDto.getNombreFase();
		String json = String.format(Constantes.FASE_NOMBRE, faseNombre);
		String nombreCompuesto = archivoDto.getNombreFile();
		int idMov = 3;
		Integer idFase = catalogoMicroservicio
				.obtenerInformacionCatalogoCampoEspecifico(CatalogosComunes.FASES_PROYECTOS.getIdCatalogo(), json)
				.stream().findFirst().map(BaseCatalogoModel::getPrimaryKey).orElse(1);

		Configuration config = new Configuration();
		config.setProperty(Constantes.BASE_FOLDER, ConstantesParaRutasSATCloud.PATH_BASE);
		config.setProperty(Constantes.FASE_FOLDER, ConstantesParaRutasSATCloud.PATH_FASES_FILES);

		if (archivoDto.getFile() != null && !archivoDto.getFile().isEmpty()) {
			if (archivo.getRuta() != null && !archivo.getRuta().isEmpty()) {
				eliminarArchivoCloud(archivo.getRuta(), false);
			}
			PathGenerator pathGenerator = new PathGenerator();
			String path = pathGenerator.generarPathFase(idProyecto, config);
			if (archivo instanceof ArchivoPlantillaProyectoModel archivoEspecifico) {
				path = SEPARADOR + archivoEspecifico.getCarpeta();
			}
			MultipartFile file = archivoDto.getFile();
			if (archivo instanceof ArchivoOtroDocumentoFaseModel) {
				path += SEPARADOR + faseNombre + SEPARADOR + ConstantesParaRutasSATCloud.PATH_OTROS_DOCUMENTOS;
				idMov = 1;
			}
			if (archivo instanceof ArchivoOtroDocumentoProyectoModel) {
				path += SEPARADOR + ConstantesParaRutasSATCloud.PATH_OTROS_DOCUMENTOS;
				idMov = 1;
			}

			String pathCompleto = path + SEPARADOR + nombreCompuesto;
			double sizeMb = (double) file.getSize() / (1024 * 1024);
			validarRutaArchivo(archivo.getRuta(), pathCompleto);
			archivo.setTamanoMb(sizeMb);
			archivo.setRuta(pathCompleto);
			archivo.setCargado(true);
			archivo.setEstatus(true);
			archivo.setFechaModificacion(LocalDateTime.now());
			cargaArchivoSatCloudFase(file, path, nombreCompuesto);

		}
		guardarArchivo(archivo, idProyecto, idFase);
		
		guardarPistaYModificacion(archivo, idProyecto, idMov, archivoDto);
		return null;

	}
	private void cargaArchivoSatCloudFase(MultipartFile file, String path, String nombreCompuesto) {
		try {
			cargarArchivoConInformacionRenombrado(file, path, nombreCompuesto);
		} catch (Exception e) {
			if (e.getMessage() != null && e.getMessage().contains(CONEXION_CERRADO)) {
				throw new ProyectoException(ErroresEnum.CONEXION_PERDIDA, e);
			}
			throw new ProyectoException(ErroresEnum.CONEXION_PERDIDA, e);
		}
	}
	
	private void guardarPistaYModificacion(Archivo archivo, Long idProyecto, int idMov, ArchivoFaseDto archivoDto) {
		String nombreArchivo = "";

		if (archivo.getRuta() != null) {
			String path = archivo.getRuta();
			int lastSlashIndex = path.lastIndexOf("/");

			nombreArchivo = "|" + path.substring(lastSlashIndex + 1);
		}

		if (idProyecto != null && idProyecto > 0) {
			servicioProyecto.actualizarUltimaModificacion(idProyecto);
		}

		pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), idMov,
				TipoSeccionPista.PROYECTO_DATOS_GESTION_DOCUMENTAL.getIdSeccionPista(),
				Constantes.getAtributosProyecto()[0] + archivoDto.getIdProyecto() + nombreArchivo, Optional.empty());
	}

	void guardarArchivo(Archivo archivo, Long idProyecto, Integer idFase) {
		if (archivo.getRuta() != null) {
			archivo.setCargado(true);
		}

		if (archivo instanceof ArchivoOtroDocumentoFaseModel archivoEspecifico) {
			archivoEspecifico.setIdProyecto(idProyecto);
			archivoEspecifico.setIdFaseProyecto(idFase);
			archivoOtroDocumentoFaseRepository.save(archivoEspecifico);
		} else if (archivo instanceof ArchivoOtroDocumentoProyectoModel archivoEspecifico) {
			archivoEspecifico.setIdProyecto(idProyecto);
			archivoOtroDocumentoProyectoRepository.save(archivoEspecifico);
		} else if (archivo instanceof ArchivoPlantillaProyectoModel archivoEspecifico
				&& archivoEspecifico.getId() != null) {
			archivoPlantillaProyectoRepository.save(archivoEspecifico);
		}
	}

	void validarRutaArchivo(String path, String pathNuevo) {
		if (path == null || !path.equals(pathNuevo)) {
			boolean rutaOcupada = Stream.of(archivoOtroDocumentoFaseRepository.findByRuta(pathNuevo),
					archivoOtroDocumentoProyectoRepository.findByRuta(pathNuevo),
					archivoPlantillaProyectoRepository.findByRuta(pathNuevo)).anyMatch(Optional::isPresent);
			if (rutaOcupada) {
				throw new ProyectoException(ErroresEnum.ERROR_RUTA_OCUPADA);
			}
		}
	}

	@Override
	public String cargarArchivosComite(List<ArchivosCargadosDto> archivosCargadosDtos) {

		try {

			for (ArchivosCargadosDto archivosCargadosDto : archivosCargadosDtos) {

				Integer idComiteProyecto = archivosCargadosDto.getIdComiteProyecto();
				Integer idPlantillaVigente = archivosCargadosDto.getIdPlantillaVigente();
				String nombreArchivo = "";

				ComiteProyectoModel comiteProyectoModel = comiteRepository.findByIdComiteProyecto(idComiteProyecto)
						.orElseThrow(() -> new ProyectoException(ErroresEnum.COMITE_PROYECTO_NO_ENCONTRADO));

				AsociasionComitePlantillaModel asociasionComitePlantilla = asociacionComiteRepository
						.findByIdComiteProyectoAndEstatusTrue(idComiteProyecto).orElse(null);

				Long idProyecto = comiteProyectoModel.getIdProyecto();
				Integer idComite = comiteProyectoModel.getIdComite();

				if (asociasionComitePlantilla == null) {
					AsociasionComitePlantillaModel asociasionComitePlantillaModel = new AsociasionComitePlantillaModel();
					asociasionComitePlantillaModel.setIdPlantillaVigente(idPlantillaVigente);
					asociasionComitePlantillaModel.setIdComiteProyecto(idComiteProyecto);
					asociasionComitePlantillaModel.setIdPlantillaVigente(idPlantillaVigente);
					asociasionComitePlantillaModel.setFechaAsignacion(LocalDateTime.now());
					asociasionComitePlantillaModel.setEstatus(true);

					asociacionComiteRepository.save(asociasionComitePlantillaModel);
				}

				if (!archivosCargadosDto.getArchivoPlantillaComiteDto().isEmpty()) {
					nombreArchivo = cargarArchivoAsociacion(archivosCargadosDto, asociasionComitePlantilla, idProyecto,
							idComite, idComiteProyecto);
				}

				if (!archivosCargadosDto.getArchivoOtrosDocumentosDto().isEmpty()) {
					nombreArchivo = cargarOtrosDocumentosCloud(archivosCargadosDto, idProyecto, idComite,
							idComiteProyecto);
				}

				pistaService.guardarPista(ModuloPista.INFORMACION_COMITES.getId(),
						TipoMovPista.INSERTA_REGISTRO.getId(),
						TipoSeccionPista.INFORMACION_COMITES_GESTION_DOCUMENTAL.getIdSeccionPista(),
						Constantes.getAtributosProyecto()[0] + idProyecto + nombreArchivo, Optional.empty());
			}
			return "Ok";

		} catch (ProyectoException e) {
			throw e;
		} catch (Exception e) {
			if (e.getMessage() != null && e.getMessage().contains(CONEXION_CERRADO)) {
				throw new ProyectoException(ErroresEnum.CONEXION_PERDIDA, e);
			}
			throw new ProyectoException(ErroresEnum.CONEXION_PERDIDA, e.getMessage());
		}
	}

	@Override
	public String actualizarArchivo(List<ArchivosActualizadosDto> archivosCargadosDtos) {

		for (ArchivosActualizadosDto archivosCargadosDto : archivosCargadosDtos) {

			Integer idComiteProyecto = archivosCargadosDto.getIdComiteProyecto();
			Integer idArchivoPlantillaComite = archivosCargadosDto.getIdArchivoPlantillaComite();
			String justificacion = archivosCargadosDto.getArchivoPlantillaComiteDto().getJustificacion();
			Boolean noAplica = archivosCargadosDto.getArchivoPlantillaComiteDto().isNoAplica();
			String carpeta = archivosCargadosDto.getDescripcionCarpeta();

			ComiteProyectoModel comiteProyectoModel = comiteRepository.findByIdComiteProyecto(idComiteProyecto)
					.orElseThrow(() -> new ProyectoException(ErroresEnum.COMITE_PROYECTO_NO_ENCONTRADO));
			Long idProyecto = comiteProyectoModel.getIdProyecto();

			ArchivoPlantillaComiteModel archivoPlantillaComiteModel = archivoPlantillaComiteRepository
					.findById(idArchivoPlantillaComite)
					.orElseThrow(() -> new ProyectoException(ErroresEnum.COMITE_PROYECTO_NO_ENCONTRADO));

			String archivoCargado = archivosCargadosDto.getArchivoCargado();
			if (archivoCargado != null) {
				PathGenerator pathGenerator = new PathGenerator();
				Integer idComite = comiteProyectoModel.getIdComite();
				String nombreArchivo = archivosCargadosDto.getArchivoPlantillaComiteDto().getNombre();
				Double tamanoMB = obtenerTamanoArchivo(archivosCargadosDto.getArchivoCargado());

				if (nombreArchivo == null) {
					return "Nombre de archivo vacío, no se pudo completar la operación";
				}

				Configuration config = new Configuration();
				config.setProperty(Constantes.BASE_FOLDER, ConstantesParaRutasSATCloud.PATH_BASE);
				config.setProperty(Constantes.COMITE_FOLDER, ConstantesParaRutasSATCloud.PATH_COMITE_FILES);
				config.setProperty(Constantes.COMITE_PROYECTO_FOLDER,
						ConstantesParaRutasSATCloud.PATH_COMITE_PROYECTO_FILES);
				log.info("nombre: {}", nombreArchivo);
				String path = pathGenerator.generarPath(idProyecto, idComite, config, idComiteProyecto, carpeta);
				String ruta = pathGenerator.generarRuta(path, nombreArchivo);
				log.info("path: {}", ruta);
				log.info("ruta: {}", ruta);

				cargaActualizacionArchivo(archivosCargadosDto, archivoPlantillaComiteModel, path, ruta, tamanoMB, idProyecto);

			} else {
				actualizarInfoArchivo(archivoPlantillaComiteModel, idArchivoPlantillaComite, justificacion, noAplica,
						null, null, null);
				pistaService.guardarPista(ModuloPista.INFORMACION_COMITES.getId(),
						TipoMovPista.ACTUALIZA_REGISTRO.getId(),
						TipoSeccionPista.PROYECTO_DATOS_INFORMACION_COMITE.getIdSeccionPista(),
						"Id proyecto: " + idProyecto + "|", Optional.empty());
			}
		}
		return "Ok";
	}
	
	private void actualizarArchivoComplemento(ArchivoPlantillaComiteModel archivoPlantillaComiteModel, File file, String path) {
		String pathAnterior = archivoPlantillaComiteModel.getRuta();
		if (pathAnterior != null) {
			try {
				eliminarArchivoCloud(pathAnterior, false);
				log.info("Archivo eliminado exitosamente.");
			} catch (ProyectoException e) {
				log.warn(
						"Error al eliminar el archivo en la nube, pero se procederá a limpiar la ruta en la base de datos.");
			} finally {
				archivoPlantillaComiteModel.setRuta(null);
				archivoPlantillaComiteRepository.save(archivoPlantillaComiteModel);
				log.info("Ruta eliminada de la base de datos y guardada correctamente.");
			}
		}

		try {
			cargarArchivosConInformacion(file, path);
		} catch (Exception e) {
			if (e.getMessage() != null && e.getMessage().contains(CONEXION_CERRADO)) {
				throw new ProyectoException(ErroresEnum.CONEXION_PERDIDA);
			}
			throw new ProyectoException(ErroresEnum.CONEXION_PERDIDA);
		}

	}
	
	private void cargaActualizacionArchivo(ArchivosActualizadosDto archivosCargadosDto, ArchivoPlantillaComiteModel archivoPlantillaComiteModel, String path, String ruta, Double tamanoMB, Long idProyecto) {
		File file=null;
		String archivoCargado = archivosCargadosDto.getArchivoCargado();
		String nombreArchivo = archivosCargadosDto.getArchivoPlantillaComiteDto().getNombre();
		Integer idArchivoPlantillaComite = archivosCargadosDto.getIdArchivoPlantillaComite();
		String justificacion = archivosCargadosDto.getArchivoPlantillaComiteDto().getJustificacion();
		Boolean noAplica = archivosCargadosDto.getArchivoPlantillaComiteDto().isNoAplica();
		try {
			file = base64ToFile(archivoCargado, nombreArchivo);

			actualizarArchivoComplemento(archivoPlantillaComiteModel, file, path);

			actualizarInfoArchivo(archivoPlantillaComiteModel, idArchivoPlantillaComite, justificacion,
					noAplica, nombreArchivo, ruta, tamanoMB);

			pistaService.guardarPista(ModuloPista.INFORMACION_COMITES.getId(),
					TipoMovPista.INSERTA_REGISTRO.getId(),
					TipoSeccionPista.INFORMACION_COMITES_GESTION_DOCUMENTAL.getIdSeccionPista(),
					Constantes.getAtributosProyecto()[0] + idProyecto + nombreArchivo, Optional.empty());

		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_GUARDAR, e);
		} finally {
		    if (file != null && file.exists()) {
		        Path pathFile = file.toPath();
		        try {
		            Files.delete(pathFile);
		            log.info(ARCHIVO_ELIMINADO, pathFile.toAbsolutePath());
		        } catch (IOException e) {
		            log.warn(ERROR_ELIMINAR_ARCHIVO, pathFile.toAbsolutePath(), e);
		        }
		    }
		}
	}

	@Override
	public String eliminarArchivos(List<ArchivosEliminadosDto> archivosEliminados) {

		try {
			List<Archivo> eliminadosact = new ArrayList<>();

			for (ArchivosEliminadosDto archivosEliminadosDto : archivosEliminados) {

				Integer idComiteProyecto = archivosEliminadosDto.getIdComiteProyecto();
				Integer idArchivo = archivosEliminadosDto.getIdArchivoPlantillaComite();
				String path = archivosEliminadosDto.getPath();

				AsociasionComitePlantillaModel asociasionComitePlantillaModel = asociacionComiteRepository
						.findByIdComiteProyectoAndEstatusTrue(idComiteProyecto).orElse(null);
				assert asociasionComitePlantillaModel != null;

				if (archivosEliminadosDto.getIdArchivoOtrosDocumentos() != null) {
					ArchivoOtrosDocumentosComiteModel archivoOtrosDocumentosComite = archivoOtrosDocumentosComiteRepository
							.findByIdAndEstatusTrue(archivosEliminadosDto.getIdArchivoOtrosDocumentos())
							.orElseThrow(() -> new ProyectoException(ErroresEnum.ERROR_AL_ELIMINAR));

					eliminadosact.add(archivoOtrosDocumentosComite);

				} else if (archivosEliminadosDto.getIdArchivoPlantillaComite() != null) {
					ArchivoPlantillaComiteModel archivoPlantillaComite = archivoPlantillaComiteRepository
							.findById(idArchivo)
							.orElseThrow(() -> new ProyectoException(ErroresEnum.COMITE_PROYECTO_NO_ENCONTRADO));

					eliminadosact.add(archivoPlantillaComite);

				} else {
					throw new ProyectoException(ErroresEnum.ERROR_AL_ELIMINAR_ARCHIVO);
				}
				List<ArchivoPlantillaComiteModel> archivoPlantillaComiteModel = archivoPlantillaComiteRepository
						.findByIdAsociacionComiteProyectoAndEstatusTrue(
								asociasionComitePlantillaModel.getIdAsociacionComitePlantilla());
				List<ArchivoOtrosDocumentosComiteModel> archivoOtrosDocumentosComite = archivoOtrosDocumentosComiteRepository
						.findByIdComiteProyectoAndEstatusTrue(idComiteProyecto);

				if (archivoPlantillaComiteModel.isEmpty() && archivoOtrosDocumentosComite.isEmpty()) {
					asociasionComitePlantillaModel.setEstatus(false);
					asociacionComiteRepository.save(asociasionComitePlantillaModel);
				}

				log.info("Archivo eliminado");
				int lastSlashIndex = path.lastIndexOf("/");
				String nombreArchivo = "|" + path.substring(lastSlashIndex + 1);

				pistaService.guardarPista(ModuloPista.INFORMACION_COMITES.getId(), TipoMovPista.BORRA_REGISTRO.getId(),
						TipoSeccionPista.PROYECTO_DATOS_GESTION_DOCUMENTAL.getIdSeccionPista(),
						Constantes.ID_COMITE_PROYECTO + idComiteProyecto + nombreArchivo, Optional.empty());

			}
			papeleraServicoControl.enviarPapelera(eliminadosact);

			return "OK";

		} catch (ProyectoException e) {
			throw e;
		} catch (Exception e) {
			if (e.getMessage() != null && e.getMessage().contains(CONEXION_CERRADO)) {
				throw new ProyectoException(ErroresEnum.CONEXION_PERDIDA, e);
			}
			throw new ProyectoException(ErroresEnum.ERROR_AL_DESCARGAR_FOLDER, e.getMessage());
		}
	}

	@Override
	public String eliminarArchivo(ArchivosEliminadosDto archivosEliminadosDto) {
		try {
			String path = archivosEliminadosDto.getPath();
			Integer idAsociacion = archivosEliminadosDto.getIdComiteProyecto();
			Integer idArchivo = archivosEliminadosDto.getIdArchivoPlantillaComite();

			eliminarArchivoCloud(path, true);

			AsociasionComitePlantillaModel asociasionComitePlantillaModel = asociacionComiteRepository
					.findByIdAsociacionComitePlantilla(idAsociacion).orElse(null);
			assert asociasionComitePlantillaModel != null;
			asociasionComitePlantillaModel.setEstatus(false);
			asociacionComiteRepository.save(asociasionComitePlantillaModel);

			ArchivoPlantillaComiteModel archivoPlantillaComite = archivoPlantillaComiteRepository.findById(idArchivo)
					.orElseThrow(() -> new ProyectoException(ErroresEnum.COMITE_PROYECTO_NO_ENCONTRADO));
			archivoPlantillaComite.setEstatus(false);
			archivoPlantillaComiteRepository.save(archivoPlantillaComite);

			return "Ok";

		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_ELIMINAR_ARCHIVO);
		}
	}

	@Override
	public String descargarArchivo(String path, Long idProyecto) {
		try {
			InputStream obj = nexusImpl.descargarArchivo(path);
			log.info("Descargado: {}", obj);
			byte[] bytes = obj.readAllBytes();
			obj.close();

			Long idProyectoFormato;
			if (idProyecto != null) {
				idProyectoFormato = idProyecto;
			} else {
				idProyectoFormato = idPoryectoExtraido(path);
			}

			int lastSlashIndex = path.lastIndexOf("/");

			String nombreArchivo = "|" + path.substring(lastSlashIndex + 1);

			pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),
					TipoSeccionPista.PROYECTO_DATOS_INFORMACION_COMITE.getIdSeccionPista(),
					"id de proyecto: " + idProyectoFormato + nombreArchivo, Optional.empty());

			return Base64.getEncoder().encodeToString(bytes);
		} catch (Exception e) {
			if (e.getMessage() != null && e.getMessage().contains(CONEXION_CERRADO)) {
				throw new ProyectoException(ErroresEnum.CONEXION_PERDIDA, e);
			}
			throw new ProyectoException(ErroresEnum.ERROR_AL_DESCARGAR_FOLDER, e.getMessage());
		}
	}

	private Long idPoryectoExtraido(String path) {
		String regex = "/PROYECTO/(\\d+)/";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(path);

		if (matcher.find()) {
			return Long.parseLong(matcher.group(1));
		}

		return null;
	}

	@Override
	public String descargarFolder(String path) {
		try {
			return descargarFolderComun(path, TipoSeccionPista.PROYECTO_DATOS_INFORMACION_COMITE.getIdSeccionPista());
		} catch (Exception e) {
			if (e.getMessage() != null && e.getMessage().contains(CONEXION_CERRADO)) {
				throw new ProyectoException(ErroresEnum.CONEXION_PERDIDA, e);
			}
			throw new ProyectoException(ErroresEnum.ERROR_AL_DESCARGAR_FOLDER, e.getMessage());
		}
	}

	@Override
	public String desgargarFolderComite(String path) {
		try {
			return descargarFolderComun(path, TipoSeccionPista.PROYECTO_DATOS_INFORMACION_COMITE.getIdSeccionPista());
		} catch (Exception e) {
			if (e.getMessage() != null && e.getMessage().contains(CONEXION_CERRADO)) {
				throw new ProyectoException(ErroresEnum.CONEXION_PERDIDA, e);
			}
			throw new ProyectoException(ErroresEnum.ERROR_AL_DESCARGAR_FOLDER, e.getMessage());
		}
	}

	@Override
	public CarpetaCompartidaDto descargarFolderSatCloud(String path) {
		try {
			return descargarFolderSatCloudComun(path,
					TipoSeccionPista.PROYECTO_DATOS_GESTION_DOCUMENTAL.getIdSeccionPista());

		} catch (Exception e) {
			if (e.getMessage() != null && e.getMessage().contains(CONEXION_CERRADO)) {
				throw new ProyectoException(ErroresEnum.CONEXION_PERDIDA, e);
			}
			throw new ProyectoException(ErroresEnum.ERROR_AL_DESCARGAR_FOLDER, e.getMessage());
		}
	}

	@Override
	public CarpetaCompartidaDto descargarFolderSatCloudComites(String path) {
		try {
			return descargarFolderSatCloudComun(path,
					TipoSeccionPista.PROYECTO_DATOS_INFORMACION_COMITE.getIdSeccionPista());

		} catch (Exception e) {
			if (e.getMessage() != null && e.getMessage().contains(CONEXION_CERRADO)) {
				throw new ProyectoException(ErroresEnum.CONEXION_PERDIDA, e);
			}
			throw new ProyectoException(ErroresEnum.ERROR_AL_DESCARGAR_FOLDER, e.getMessage());
		}
	}

	public void cargarArchivoConInformacion(MultipartFile file, String path) {
		try {
			nexusImpl.cargarArchivo(file.getInputStream(), path, file.getOriginalFilename());
			log.info("Archivo cargado: {},");

		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_GUARDAR, e);
		}
	}

	// renombrar archivo rna 38
	public void cargarArchivoConInformacionRenombrado(MultipartFile file, String path, String nombre) {
		try {
			boolean archivo = nexusImpl.cargarArchivo(file.getInputStream(), path, nombre);
			log.info("Archivo cargado: {},", archivo);

		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_GUARDAR, e);
		}
	}

	private String separarNombreArchivo(String path) {
		String[] archivos = path.split("/");
		return archivos[archivos.length - 1];
	}

	private static File base64ToFile(String base64String, String fileName) {
		try {

			String sanitizedFileName = sanitizeFileName(fileName);

			if (sanitizedFileName.contains("..") || sanitizedFileName.contains("/")
					|| sanitizedFileName.contains("\\")) {
				throw new IllegalArgumentException("El nombre del archivo contiene rutas no permitidas.");
			}

			byte[] decodedBytes = Base64.getDecoder().decode(base64String);

			Path tempDir = Files.createTempDirectory("secureTempDir");

			Path safePath = Paths.get(sanitizedFileName);
			String safeFileName = safePath.getFileName().toString();



			Path desiredFilePath = tempDir.resolve(safeFileName).normalize();

			if (!desiredFilePath.normalize().startsWith(tempDir.normalize())) {
				throw new IllegalArgumentException("Intento de Path Traversal detectado.");
			}

			if (Files.exists(desiredFilePath)) {
				throw new IllegalArgumentException("El archivo ya existe, evitando sobrescribir.");
			}

			Files.write(desiredFilePath, decodedBytes, StandardOpenOption.CREATE_NEW);

			return desiredFilePath.toFile();
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_ARCHIVO, e);
		}
	}
	private static String sanitizeFileName(String fileName) {
		return fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
	}

	private void cargarArchivosConInformacion(File file, String path) {
		try (FileInputStream fileInputStream = new FileInputStream(file)) {
			nexusImpl.cargarArchivo(fileInputStream, path, file.getName());
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_GUARDAR, e);
		}
	}

	private Double obtenerTamanoArchivo(String base64) {
		try {
			byte[] decodeBytes = Base64.getDecoder().decode(base64);

			int sizeInBytes = decodeBytes.length;

			return sizeInBytes / (1024.0 * 1024.0);

		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}
	}

	public void eliminarArchivoCloud(String path, Boolean isDelete) {
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

			if (Boolean.TRUE.equals(isDelete)) {
				pistaService.guardarPista(ModuloPista.INFORMACION_COMITES.getId(), TipoMovPista.BORRA_REGISTRO.getId(),
						TipoSeccionPista.INFORMACION_COMITES_GESTION_DOCUMENTAL.getIdSeccionPista(),
						TipoMovPista.BORRA_REGISTRO.getClave(), Optional.empty());

			}
		}

		catch (Exception e) {
			String mensajeError = obtenerMensajeError(e.getMessage());
			throw new ProyectoException(ErroresEnum.ERROR_AL_ELIMINAR_ARCHIVO_CLOUD, " " + mensajeError);
		}
	}

	private String obtenerMensajeError(String mensaje) {
		if (mensaje != null) {
			int startIndex = mensaje.lastIndexOf(':') + 1;
			return mensaje.substring(startIndex).trim();
		}
		return null;
	}

	public void procesarCargaArchivos(File file, String path) {
		try {
			cargarArchivosConInformacion(file, path);
		} catch (Exception e) {
			if (e.getMessage() != null && e.getMessage().contains(CONEXION_CERRADO)) {
				throw new ProyectoException(ErroresEnum.CONEXION_PERDIDA);
			}
			throw new ProyectoException(ErroresEnum.CONEXION_PERDIDA);
		}
	}

	public String cargarArchivoAsociacion(ArchivosCargadosDto archivosCargadosDto,
			AsociasionComitePlantillaModel asociasionComitePlantilla, Long idProyecto, Integer idComite,
			Integer idComiteProyecto) {
		AsociasionComitePlantillaModel asociacion = asociacionComiteRepository.findByIdComiteProyecto(idComiteProyecto)
				.orElse(null);

		assert asociacion != null;
		Integer idAsociacion = asociacion.getIdAsociacionComitePlantilla();
		String nombreArchivoPistas = "";

		for (ArchivoPlantillaComiteDto archivoCargado : archivosCargadosDto.getArchivoPlantillaComiteDto()) {

			PathGenerator pathGenerator = new PathGenerator();
			String nombreArchivo = archivoCargado.getNombre();
			String carpeta = archivoCargado.getDescripcionCarpeta();

			Configuration config = new Configuration();
			config.setProperty(Constantes.BASE_FOLDER, ConstantesParaRutasSATCloud.PATH_BASE);
			config.setProperty(Constantes.COMITE_FOLDER, ConstantesParaRutasSATCloud.PATH_COMITE_FILES);
			config.setProperty(Constantes.COMITE_PROYECTO_FOLDER,
					ConstantesParaRutasSATCloud.PATH_COMITE_PROYECTO_FILES);

			String path = pathGenerator.generarPath(idProyecto, idComite, config, idComiteProyecto, carpeta);
			String archivo = archivoCargado.getArchivoCargado();
			String ruta = pathGenerator.generarRuta(path, nombreArchivo);

			Integer idArchivoPlantilla = archivoCargado.getIdArchivoPlantilla();
			ArchivoPlantillaComiteModel archivoPlantillaComiteModel = archivoPlantillaComiteRepository
					.findByIdArchivoPlantillaAndIdAsociacionComiteProyectoAndEstatusTrue(idArchivoPlantilla,
							idAsociacion);

			if (archivoPlantillaComiteModel != null) {
				throw new ProyectoException(ErroresEnum.ARCHIVO_EXISTENTE);
			}

			boolean estatus = false;
			File file = null;
			try {
				file = base64ToFile(archivoCargado.getArchivoCargado(), nombreArchivo);
				estatus= comprobarCargado(asociasionComitePlantilla, archivo, path, file);

				ArchivoPlantillaComiteModel archivoPlantillaComite = new ArchivoPlantillaComiteModel();
				archivoPlantillaComite.setRuta(ruta);
				archivoPlantillaComite.setCarpeta(path);
				archivoPlantillaComite.setEstatus(true);
				archivoPlantillaComite.setFechaModificacion(LocalDateTime.now());
				archivoPlantillaComite.setJustificacion(archivoCargado.getJustificacion());
				archivoPlantillaComite.setNoAplica(archivoCargado.isNoAplica());
				archivoPlantillaComite.setNombre(archivoCargado.getNombre());
				archivoPlantillaComite.setDescripcion(archivoCargado.getDescripcion());
				archivoPlantillaComite.setObligatorio(false);
				archivoPlantillaComite.setCargado(estatus);
				archivoPlantillaComite.setNivel(1);
				archivoPlantillaComite.setOrden(1);

				if (archivo != null) {
					Double tamanoMB = obtenerTamanoArchivo(archivoCargado.getArchivoCargado());
					archivoPlantillaComite.setTamanoMb(tamanoMB);
				}

				archivoPlantillaComite.setIdAsociacionComiteProyecto(asociacion.getIdAsociacionComitePlantilla());
				archivoPlantillaComite.setIdArchivoPlantilla(archivoCargado.getIdArchivoPlantilla());
				archivoPlantillaComite.setCargado(true);
				archivoPlantillaComiteRepository.save(archivoPlantillaComite);
				nombreArchivoPistas = nombreArchivo;

			} catch (Exception e) {
				throw new ProyectoException(ErroresEnum.ERROR_AL_GUARDAR, e);
			} finally {
			    if (file != null && file.exists()) {
			        Path pathFile = file.toPath();
			        try {
			            Files.delete(pathFile);
			            log.info(ARCHIVO_ELIMINADO, pathFile.toAbsolutePath());
			        } catch (IOException e) {
			            log.warn(ERROR_ELIMINAR_ARCHIVO, pathFile.toAbsolutePath(), e);
			        }
			    }
			}
		}
		return nombreArchivoPistas;
	}
	
	private boolean comprobarCargado(AsociasionComitePlantillaModel asociasionComitePlantilla, String archivo, String path, File file) {
		if (asociasionComitePlantilla != null && archivo != null) {
			procesarCargaArchivos(file, path);
			return true;
		}
		return false;
	}

	public void procesarCargaDocumentos(ArchivoOtrosDocumentosDto archivoOtrosDocumentos, String ruta, Double tamanoMB,
			Integer idComiteProyecto, String pathOtrosDocumentosInterno, File fileOtrosDocumentos,
			Integer idArchivoPlantillaComite) {
		try {
			cargarOtrosDocumentos(archivoOtrosDocumentos, ruta, tamanoMB, idComiteProyecto, pathOtrosDocumentosInterno,
					idArchivoPlantillaComite);
			cargarArchivosConInformacion(fileOtrosDocumentos, pathOtrosDocumentosInterno);
		} catch (Exception e) {
			if (e.getMessage() != null && e.getMessage().contains(CONEXION_CERRADO)) {
				throw new ProyectoException(ErroresEnum.CONEXION_PERDIDA, e);
			}
			throw new ProyectoException(ErroresEnum.CONEXION_PERDIDA, e);
		}

	}

	public String cargarOtrosDocumentosCloud(ArchivosCargadosDto archivosCargadosDto, Long idProyecto, Integer idComite,
			Integer idComiteProyecto) {

		String nombreArchivoPistas = "";

		for (ArchivoOtrosDocumentosDto archivoOtrosDocumentos : archivosCargadosDto.getArchivoOtrosDocumentosDto()) {

			nombreArchivoPistas = archivoOtrosDocumentos.getNombre();
			String carpeta = archivoOtrosDocumentos.getDescripcionCarpeta();
			PathGenerator pathGenerator = new PathGenerator();
			Configuration config = new Configuration();
			config.setProperty(Constantes.BASE_FOLDER, ConstantesParaRutasSATCloud.PATH_BASE);
			config.setProperty(Constantes.COMITE_FOLDER, ConstantesParaRutasSATCloud.PATH_COMITE_FILES);
			config.setProperty(Constantes.COMITE_PROYECTO_FOLDER,
					ConstantesParaRutasSATCloud.PATH_COMITE_PROYECTO_FILES);

			Double tamanoMB = obtenerTamanoArchivo(archivoOtrosDocumentos.getArchivoCargadoOtrosDocumentos());

			File fileOtrosDocumentos = null;
			try {
				fileOtrosDocumentos = base64ToFile(archivoOtrosDocumentos.getArchivoCargadoOtrosDocumentos(),
						archivoOtrosDocumentos.getNombre());
				String pathOtrosDocumentosBase = pathGenerator.generarPath(idProyecto, idComite, config,
						idComiteProyecto, carpeta);

				if (carpeta == null || carpeta.isEmpty()) {
					pathOtrosDocumentosBase = pathGenerator.generarPathOtrosDocumentosExterno(idProyecto, idComite,
							config, idComiteProyecto);
				}

				cargarOtrosDocumentosCloudComplemento(archivoOtrosDocumentos,pathGenerator,pathOtrosDocumentosBase, tamanoMB, idComiteProyecto, fileOtrosDocumentos);
				
				

			} catch (Exception e) {
				throw new ProyectoException(ErroresEnum.ERROR_AL_GUARDAR, e);
			}finally {
			    if (fileOtrosDocumentos != null && fileOtrosDocumentos.exists()) {
			        Path path = fileOtrosDocumentos.toPath();
			        try {
			            Files.delete(path);
			            log.info(ARCHIVO_ELIMINADO, path.toAbsolutePath());
			        } catch (IOException e) {
			            log.warn(ERROR_ELIMINAR_ARCHIVO, path.toAbsolutePath(), e);
			        }
			    }
			}
		}
		return nombreArchivoPistas;
	}
	
	private void cargarOtrosDocumentosCloudComplemento(ArchivoOtrosDocumentosDto archivoOtrosDocumentos, PathGenerator pathGenerator, String pathOtrosDocumentosBase, Double tamanoMB, Integer idComiteProyecto, File fileOtrosDocumentos) {
		if (archivoOtrosDocumentos.getIdArchivoPlantillaComite() == null) {
			if (archivoOtrosDocumentos.getIdCarpetaPlantilla() == null
					&& archivoOtrosDocumentos.getArchivoCargadoOtrosDocumentos() != null) {

				String pathOtrosDocumentosInterno = pathGenerator.generarRutaOtrosDocumentos(
						pathOtrosDocumentosBase, ConstantesParaRutasSATCloud.PATH_OTROS_DOCUMENTOS);
				String ruta = pathGenerator.generarRuta(pathOtrosDocumentosInterno,
						archivoOtrosDocumentos.getNombre());

				archivoOtrosDocumentos.setOtrosDocumentosInterno(false);

				procesarCargaDocumentos(archivoOtrosDocumentos, ruta, tamanoMB, idComiteProyecto,
						pathOtrosDocumentosInterno, fileOtrosDocumentos, null);

			} else if (archivoOtrosDocumentos.getIdCarpetaPlantilla() != null
					&& archivoOtrosDocumentos.getArchivoCargadoOtrosDocumentos() != null) {

				String pathOtrosDocumentosExterno = pathGenerator.generarRuta(pathOtrosDocumentosBase,
						ConstantesParaRutasSATCloud.PATH_OTROS_DOCUMENTOS);

				String ruta = pathGenerator.generarRuta(pathOtrosDocumentosExterno,
						archivoOtrosDocumentos.getNombre());
				archivoOtrosDocumentos.setOtrosDocumentosInterno(true);
				procesarCargaDocumentos(archivoOtrosDocumentos, ruta, tamanoMB, idComiteProyecto,
						pathOtrosDocumentosExterno, fileOtrosDocumentos, null);
			} else {
				throw new ProyectoException(ErroresEnum.ERROR_PLANTILLA_ARCHIVO);
			}
		} else if (archivoOtrosDocumentos.getIdArchivoPlantillaComite() != null
				&& archivoOtrosDocumentos.getArchivoCargadoOtrosDocumentos() != null) {
			log.info("entra");
			log.info("id plantilla: {}", archivoOtrosDocumentos.getIdArchivoPlantillaComite());
			String pathOtrosDocumentosExterno = pathGenerator.generarRuta(pathOtrosDocumentosBase,
					ConstantesParaRutasSATCloud.PATH_OTROS_DOCUMENTOS);

			String ruta = pathGenerator.generarRuta(pathOtrosDocumentosExterno,
					archivoOtrosDocumentos.getNombre());
			archivoOtrosDocumentos.setOtrosDocumentosInterno(true);
			procesarCargaDocumentos(archivoOtrosDocumentos, ruta, tamanoMB, idComiteProyecto,
					pathOtrosDocumentosExterno, fileOtrosDocumentos,
					archivoOtrosDocumentos.getIdArchivoPlantillaComite());
		} else {
			throw new ProyectoException(ErroresEnum.ERROR_PLANTILLA_ARCHIVO);
		}
	}

	public void cargarOtrosDocumentos(ArchivoOtrosDocumentosDto otrosDocumentosDto, String rutaOtrosDocumentos,
			Double tamanoMb, Integer idComiteProyecto, String rutaCarpeta, Integer idArchivoPlantillaComite) {
		Boolean isOtrosDocumentosInterno = otrosDocumentosDto.isOtrosDocumentosInterno();

		ArchivoOtrosDocumentosComiteModel otrosDocumentosComiteModel;

		if (idArchivoPlantillaComite != null) {
			log.info("id de plantilla: {}", idArchivoPlantillaComite);
			otrosDocumentosComiteModel = archivoOtrosDocumentosComiteRepository.findById(idArchivoPlantillaComite)
					.orElseThrow(() -> new ProyectoException(ErroresEnum.ARCHIVO_EXISTENTE));
		} else {
			otrosDocumentosComiteModel = new ArchivoOtrosDocumentosComiteModel();
		}

		String nombre = otrosDocumentosDto.getNombre();
		String descripcion = (nombre != null && nombre.contains(".")) ? nombre.substring(0, nombre.lastIndexOf('.'))
				: nombre;

		otrosDocumentosComiteModel.setCarpeta(rutaCarpeta);
		otrosDocumentosComiteModel.setIdArchivoPlantilla(otrosDocumentosDto.getIdArchivoPlantillaComite());
		otrosDocumentosComiteModel.setEstatus(true);
		otrosDocumentosComiteModel.setCargado(true);
		otrosDocumentosComiteModel.setIdComiteProyecto(idComiteProyecto);
		otrosDocumentosComiteModel.setRuta(rutaOtrosDocumentos);
		otrosDocumentosComiteModel.setNombre(nombre);
		otrosDocumentosComiteModel.setDescripcion(descripcion);
		otrosDocumentosComiteModel.setJustificacion(otrosDocumentosDto.getJustificacion());
		otrosDocumentosComiteModel.setNoAplica(otrosDocumentosDto.isNoAplica());
		otrosDocumentosComiteModel.setFechaModificacion(LocalDateTime.now());
		otrosDocumentosComiteModel.setTamanoMb(tamanoMb);
		otrosDocumentosComiteModel.setObligatorio(false);
		otrosDocumentosComiteModel.setNivel(1);
		otrosDocumentosComiteModel.setOrden(1);
		otrosDocumentosComiteModel.setOtrosDocumentosInterno(isOtrosDocumentosInterno);
		otrosDocumentosComiteModel.setIdCarpetaPlantilla(otrosDocumentosDto.getIdCarpetaPlantilla());

		archivoOtrosDocumentosComiteRepository.save(otrosDocumentosComiteModel);
	}

	private void actualizarInfoArchivo(ArchivoPlantillaComiteModel archivoPlantillaComite,
			Integer idArchivoPlantillaComite, String justifdicacion, Boolean noAplica, String nombre, String ruta,
			Double tamanoMB) {

		archivoPlantillaComite.setEstatus(true);
		archivoPlantillaComite.setFechaModificacion(LocalDateTime.now());
		archivoPlantillaComite.setId(idArchivoPlantillaComite);
		archivoPlantillaComite.setJustificacion(justifdicacion);
		archivoPlantillaComite.setNoAplica(noAplica);

		if (nombre != null && ruta != null && tamanoMB != null) {
			archivoPlantillaComite.setNombre(nombre);
			archivoPlantillaComite.setRuta(ruta);
			archivoPlantillaComite.setTamanoMb(tamanoMB);
		}
		archivoPlantillaComiteRepository.save(archivoPlantillaComite);
	}

	@Override
	public void eliminarArchivoSatCloud(String path) {

		nexusImpl.generarCarpetasVacias(PEPELERA + path);
		nexusImpl.renameFile(path, PEPELERA + path);

		PapeleraDto dto = new PapeleraDto();
		dto.setFecha(horaActual());
		dto.setPathOriginal(path);
		dto.setPathNuevo(PEPELERA + path);

		papeleraServicio.generarId(dto);

	}

	private LocalDateTime horaActual() {
		ZoneId zoneId = ZoneId.of("America/Mexico_City");
		ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
		return zonedDateTime.toLocalDateTime();
	}

	private String descargarFolderComun(String path, Integer idSeccionPista) {
		try {
			InputStream obj = nexusImpl.descargarFolder(path);
			log.info("Descargado: {}", obj);
			byte[] bytes = obj.readAllBytes();
			obj.close();

			String contenido = nexusImpl.obtenerContenidoCarpeta(path);
			pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),
					idSeccionPista, contenido, Optional.empty());

			return Base64.getEncoder().encodeToString(bytes);

		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	private CarpetaCompartidaDto descargarFolderSatCloudComun(String path, Integer idSeccionPista) {
		try {
			String contenido = nexusImpl.obtenerContenidoCarpeta(path);

			pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),
					idSeccionPista, contenido, Optional.empty());

			return nexusImpl.crearCarpetaCompartida(path);

		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

}
