package com.sisecofi.contratos.service.carpetas.impl;

import com.sisecofi.libreria.comunes.dto.CarpetaCompartidaDto;
import com.sisecofi.libreria.comunes.dto.PapeleraDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
import com.sisecofi.libreria.comunes.util.ConstantesParaRutasSATCloud;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.libreria.comunes.util.exception.NexusException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import com.sisecofi.contratos.dto.ArchivoFaseDto;
import com.sisecofi.contratos.microservicios.CatalogoMicroservicio;
import com.sisecofi.contratos.microservicios.PapeleraServico;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.*;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.ArchivoOtroDocumentoFaseReintegroModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.ArchivoOtroDocumentoReintegroModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.ArchivoPlantillaReintegroModel;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoOtroDocumentoFaseConvenioRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoOtroDocumentoFaseReintegroRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoOtroDocumentoContratoRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoOtroDocumentoConvenioRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoOtroDocumentoReintegroRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoPlantillaContratoRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoPlantillaConvenioRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoOtroDocumentoFaseContratoRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoPlantillaReintegroRepository;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.contratos.repository.contrato.ContratoRepository;
import com.sisecofi.contratos.repository.contrato.ConvenioModificatorioRepository;
import com.sisecofi.contratos.service.PistaService;
import com.sisecofi.contratos.service.carpetas.ServicioArchivo;
import com.sisecofi.contratos.service.impl.NexusImpl;
import com.sisecofi.contratos.util.consumer.PathGenerator;
import com.sisecofi.contratos.util.enums.ErroresEnum;
import com.sisecofi.contratos.util.exception.ContratoException;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.contratos.util.consumer.Configuration;

import java.io.*;
import java.util.Base64;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ServicioArchivoImpl implements ServicioArchivo {

	private final NexusImpl nexusImpl;
	private static final String ERROR = "Error:{}";
	private static final String LOG_DOWNLOAD = "Descargado: {}";
	private final PapeleraServico papeleraServicio;
	private final ArchivoOtroDocumentoContratoRepository archivoOtroDocumentoContratoRepository;
	private final ArchivoOtroDocumentoFaseContratoRepository archivoOtroDocumentoFaseRepository;
	private final ArchivoPlantillaContratoRepository archivoPlantillaContratoRepository;
	private final CatalogoMicroservicio catalogoMicroservicio;
	private final ContratoRepository contratoRepository;
	private final ConvenioModificatorioRepository convenioRepository;
	private final ArchivoOtroDocumentoConvenioRepository archivoOtroDocumentoConvenioRepository;
	private final ArchivoOtroDocumentoFaseConvenioRepository archivoOtroDocumentoFaseConvenioRepository;
	private final ArchivoPlantillaConvenioRepository archivoPlantillaConvenioRepository;
	private final ArchivoOtroDocumentoFaseReintegroRepository archivoOtroDocumentoFaseReintegroRepository;
	private final ArchivoOtroDocumentoReintegroRepository archivoOtroDocumentoReintegroRepository;
	private final ArchivoPlantillaReintegroRepository archivoPlantillaReintegroRepository;
	private static final String PEPELERA = "/PAPELERA";
	private static final String DELIMITADOR= "/";
	private final PistaService pistaService;

	@Override
	public void cargarArchivoFase(ArchivoFaseDto archivoDto) {
		try {
		if (archivoDto.getTipoRegistro() == 1) {
			this.cargarArchivo(archivoDto, true);
		} else if (archivoDto.getTipoRegistro() == 2) {
			this.cargarArchivo(archivoDto, false);
		} else {
			this.cargarArchivoReintegro(archivoDto);
		}
		}catch (Exception e) {
			throw new ContratoException(ErroresEnum.CONEXION_PERDIDA);
		}
	}

	private void cargarArchivo(ArchivoFaseDto archivoDto, boolean esContrato) {
	    int idMov = 3;
	    Archivo archivo = archivoDto.getArchivo();
	    Long idContrato = archivoDto.getIdContrato();
	    String faseNombre = archivoDto.getNombreFase();
	    String nombreCompuesto = archivoDto.getNombreFile();

	    // Obtener datos comunes
	    DatosProyecto datosProyecto = obtenerDatosProyecto(idContrato, faseNombre, esContrato);
	    Integer idFase = obtenerIdFase(datosProyecto.json);

	    try {
	        Configuration config = crearConfiguracion(esContrato, datosProyecto.nombreCorto, datosProyecto.idProyecto);

	        if (archivoDto.getFile() != null && !archivoDto.getFile().isEmpty()) {
	            procesarArchivo(archivoDto, archivo, config, esContrato, nombreCompuesto, datosProyecto.nombreCorto, datosProyecto.numeroCm);
	        }

	        guardarArchivo(archivo, idContrato, idFase);
	        registrarPista(archivo, idMov, esContrato, idContrato);
	    } catch (Exception e) {
	        throw new ContratoException(ErroresEnum.ERROR_ARCHIVO_NO_CARGADO, e);
	    }
	}

	private DatosProyecto obtenerDatosProyecto(Long idContrato, String faseNombre, boolean esContrato) {
	    if (esContrato) {
	        ContratoModel contrato = contratoRepository.findByIdContrato(idContrato)
	                .orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));
	        return new DatosProyecto(
	                contrato.getProyecto().getIdProyecto(),
	                contrato.getNombreCorto(),
	                null,
	                String.format(Constantes.FASE_NOMBRE, faseNombre)
	        );
	    } else {
	        ConvenioModificatorioModel convenio = convenioRepository.findByIdConvenioModificatorioAndEstatusTrue(idContrato)
	                .orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));
	        return new DatosProyecto(
	                convenio.getContratoModel().getProyecto().getIdProyecto(),
	                convenio.getContratoModel().getNombreCorto(),
	                convenio.getNumeroConvenio(),
	                String.format(Constantes.FASE_NOMBRE, faseNombre)
	        );
	    }
	}

	private Integer obtenerIdFase(String json) {
	    return catalogoMicroservicio
	            .obtenerInformacionCatalogoCampoEspecifico(CatalogosComunes.FASES_PROYECTOS.getIdCatalogo(), json)
	            .stream().findFirst().map(BaseCatalogoModel::getPrimaryKey).orElse(0);
	}

	private void procesarArchivo(ArchivoFaseDto archivoDto, Archivo archivo, Configuration config, boolean esContrato,
	                             String nombreCompuesto, String nombreCorto, String numeroCm) {
	    PathGenerator pathGenerator = new PathGenerator();
	    String path = esContrato
	            ? pathGenerator.generarPathFase(nombreCorto, config)
	            : pathGenerator.generarPathFaseConvenio(numeroCm, config);

	    path = ajustarPathPorTipoArchivo(archivo, path);

	    MultipartFile file = archivoDto.getFile();
	    String pathCompleto = path + DELIMITADOR + nombreCompuesto;
	    double sizeMb = (double) file.getSize() / (1024 * 1024);

	    archivo.setTamanoMb(sizeMb);
	    archivo.setRuta(pathCompleto);
	    archivo.setCargado(true);
	    archivo.setEstatus(true);
	    archivo.setFechaModificacion(LocalDateTime.now());
	    cargarArchivoConInformacionRenombrado(file, path, nombreCompuesto);
	}

	private String ajustarPathPorTipoArchivo(Archivo archivo, String path) {
	    if (archivo instanceof ArchivoPlantillaContratoModel || archivo instanceof ArchivoPlantillaConvenioModel) {
	        path = DELIMITADOR + archivo.getCarpeta();
	    }
	    if (archivo instanceof ArchivoOtroDocumentoFaseContratoModel || archivo instanceof ArchivoOtroDocumentoFaseConvenioModel) {
	        path += DELIMITADOR + "EJECUCION" + DELIMITADOR+ ConstantesParaRutasSATCloud.PATH_OTROS_DOCUMENTOS;
	    }
	    if (archivo instanceof ArchivoOtroDocumentoContratoModel || archivo instanceof ArchivoOtroDocumentoConvenioModel) {
	        path += DELIMITADOR+ ConstantesParaRutasSATCloud.PATH_OTROS_DOCUMENTOS;
	    }
	    return path;
	}

	private void registrarPista(Archivo archivo, int idMov, boolean esContrato, Long idContrato) {
	    String nombreArchivo = archivo.getRuta() != null
	            ? "|" + archivo.getRuta().substring(archivo.getRuta().lastIndexOf(DELIMITADOR) + 1)
	            : "";

	    // pistaService.guardarPista(

	    // ModuloPista.ADMIN_CONTRATOS.getId(),

	    // idMov,

	    // TipoSeccionPista.CONTRATOS_GESTION_DOCUMENTAL.getIdSeccionPista(),

	    // Constantes.getAtributosGenerales()[esContrato ? 0 : 1] + idContrato + nombreArchivo,

	    // Optional.empty()

	    // );
	}

	private static class DatosProyecto {
	    Long idProyecto;
	    String nombreCorto;
	    String numeroCm;
	    String json;

	    DatosProyecto(Long idProyecto, String nombreCorto,String numeroCm, String json) {
	        this.idProyecto = idProyecto;
	        this.nombreCorto = nombreCorto;
	        this.numeroCm =numeroCm;
	        this.json = json;
	    }
	}

	
	
	private Configuration crearConfiguracion(boolean contrato, String nombreCorto, Long idProyecto) {
		Configuration config = new Configuration();
		if(contrato) {
			config.setProperty(Constantes.BASE_FOLDER_COTRATOS, ConstantesParaRutasSATCloud.PATH_BASE + DELIMITADOR + idProyecto
					+ DELIMITADOR + ConstantesParaRutasSATCloud.PATH_BASE_CONTRATOS);
			config.setProperty(Constantes.FASE_FOLDER, ConstantesParaRutasSATCloud.PATH_FASES_FILES);
		}else {
			config.setProperty(Constantes.BASE_FOLDER_COTRATOS,
					ConstantesParaRutasSATCloud.PATH_BASE + DELIMITADOR + idProyecto + DELIMITADOR
							+ ConstantesParaRutasSATCloud.PATH_BASE_CONTRATOS + DELIMITADOR + nombreCorto + DELIMITADOR
							+ ConstantesParaRutasSATCloud.PATH_BASE_CONVENIOS);
			config.setProperty(Constantes.FASE_FOLDER, ConstantesParaRutasSATCloud.PATH_FASES_FILES);
		}
		return config;
	}
	

	// --------------------reintegro-------------------------
	private void cargarArchivoReintegro(ArchivoFaseDto archivoDto) {
		int idMov = archivoDto.getIdMov();
		try {
			Archivo archivo = archivoDto.getArchivo();

			if (archivoDto.getFile() != null && !archivoDto.getFile().isEmpty()) {

				String nombreCompuesto = archivoDto.getNombreFile();
				String path = archivoDto.getRuta();
				MultipartFile file = archivoDto.getFile();

				double sizeMb = (double) file.getSize() / (1024 * 1024);
				archivo.setTamanoMb(sizeMb);
				archivo.setRuta(path + DELIMITADOR + nombreCompuesto);
				archivo.setCargado(true);
				archivo.setEstatus(true);
				archivo.setFechaModificacion(LocalDateTime.now());

				cargarArchivoConInformacionRenombrado(file, path, nombreCompuesto);

			}

			guardarArchivo(archivo, archivoDto.getIdContrato(), 1);

			String nombreArchivo = "";

			if (archivo.getRuta() != null) {
				String path = archivo.getRuta();
				int lastSlashIndex = path.lastIndexOf(DELIMITADOR);

				nombreArchivo = "|" + path.substring(lastSlashIndex + 1);
			}



			// pistaService.guardarPista(ModuloPista.REINTEGRO.getId(), idMov,


			// TipoSeccionPista.REINTEGRO_GESTION_DOCUMENTAL.getIdSeccionPista(),


			// Constantes.getAtributosGenerales()[2] + archivoDto.getIdReintegro() + nombreArchivo,


			// Optional.empty());
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_ARCHIVO_NO_CARGADO, e);
		}
	}

	boolean guardarArchivo(Archivo archivo, Long idContrato, Integer idFase) {
		if (archivo.getRuta() != null) {
			archivo.setCargado(true);
		}
		if (archivo instanceof ArchivoOtroDocumentoFaseContratoModel archivoEspecifico) {
			archivoEspecifico.setIdContrato(idContrato);
			archivoEspecifico.setIdFaseProyecto(idFase);
			archivoOtroDocumentoFaseRepository.save(archivoEspecifico);
		} else if (archivo instanceof ArchivoOtroDocumentoContratoModel archivoEspecifico) {
			archivoEspecifico.setIdContrato(idContrato);
			archivoOtroDocumentoContratoRepository.save(archivoEspecifico);
		} else if (archivo instanceof ArchivoPlantillaContratoModel archivoEspecifico && archivo.getId() != null) {
			archivoPlantillaContratoRepository.save(archivoEspecifico);
		}
		// ---------------para convenio-------
		else if (archivo instanceof ArchivoPlantillaConvenioModel archivoEspecifico&& archivo.getId() != null) {
			archivoPlantillaConvenioRepository.save(archivoEspecifico);
		} else if (archivo instanceof ArchivoOtroDocumentoConvenioModel archivoEspecifico) {
			archivoEspecifico.setIdConvenioModificatorio(idContrato);
			archivoOtroDocumentoConvenioRepository.save(archivoEspecifico);
		} else if (archivo instanceof ArchivoOtroDocumentoFaseConvenioModel archivoEspecifico) {
			archivoEspecifico.setIdConvenioModificatorio(idContrato);
			archivoEspecifico.setIdFaseProyecto(idFase);
			archivoOtroDocumentoFaseConvenioRepository.save(archivoEspecifico);
		}
		// ---------------para reintegros-------
		else if (archivo instanceof ArchivoPlantillaReintegroModel archivoEspecifico && archivo.getId() != null) {
			archivoPlantillaReintegroRepository.save(archivoEspecifico);
		} else if (archivo instanceof ArchivoOtroDocumentoReintegroModel archivoEspecifico) {
			archivoEspecifico.setIdContrato(idContrato);
			archivoOtroDocumentoReintegroRepository.save(archivoEspecifico);
		} else if (archivo instanceof ArchivoOtroDocumentoFaseReintegroModel archivoEspecifico) {
			archivoEspecifico.setIdReintegrosAsociados(idContrato);
			archivoOtroDocumentoFaseReintegroRepository.save(archivoEspecifico);
		}

		return true;
	}

	public void cargarArchivoConInformacionRenombrado(MultipartFile file, String path, String nombre) {
		try {
			boolean archivo = nexusImpl.cargarArchivo(file.getInputStream(), path, nombre);
			log.info("Archivo cargado: {},", archivo);

		} catch (Exception e) {
			log.error(ERROR);
			throw new ContratoException(ErroresEnum.ERROR_AL_GUARDAR, e);
		}
	}



	@Override
	public String descargarArchivo(String path, Long id) {
		try {
			InputStream obj = nexusImpl.descargarArchivo(path);
			log.info(LOG_DOWNLOAD, obj);
			byte[] bytes = obj.readAllBytes();
			obj.close();

			int lastSlashIndex = path.lastIndexOf(DELIMITADOR);

			String nombreArchivo = "|" + path.substring(lastSlashIndex + 1);



			// pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),


			// TipoSeccionPista.CONTRATOS_GESTION_DOCUMENTAL.getIdSeccionPista(), "id: " + id + nombreArchivo,


			// Optional.empty());

			return Base64.getEncoder().encodeToString(bytes);
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_DESCARGAR_ARCHIVO);
		}
	}

	@Override
	public String descargarFolder(String path) {
		try {
			InputStream obj = nexusImpl.descargarFolder(path);
			log.info(LOG_DOWNLOAD, obj);
			byte[] bytes = obj.readAllBytes();
			obj.close();
			return Base64.getEncoder().encodeToString(bytes);
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_DESCARGAR_ARCHIVO);
		}
	}

	@Override
	public String descargarFolderGestion(String path, Long id, int modulo, int seccion) {
		try {
			InputStream obj = nexusImpl.descargarFolder(path);
			log.info(LOG_DOWNLOAD, obj);
			byte[] bytes = obj.readAllBytes();
			obj.close();

			String contenido = nexusImpl.obtenerContenidoCarpeta(path);



			// pistaService.guardarPista(modulo, TipoMovPista.IMPRIME_REGISTRO.getId(), seccion, contenido,


			// Optional.empty());

			return Base64.getEncoder().encodeToString(bytes);
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_DESCARGAR_ARCHIVO);
		}
	}

	@Override
	public CarpetaCompartidaDto descargarFolderSatCloudGestion(String path, Long id, int modulo, int seccion) {
		try {

			String contenido = nexusImpl.obtenerContenidoCarpeta(path);



			// pistaService.guardarPista(modulo, TipoMovPista.IMPRIME_REGISTRO.getId(), seccion, contenido,


			// Optional.empty());

			return nexusImpl.crearCarpetaCompartida(path);
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_DESCARGAR_ARCHIVO);
		}
	}

	@Override
	public CarpetaCompartidaDto descargarFolderSatCloud(String path) {
		try {
			return nexusImpl.crearCarpetaCompartida(path);
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_DESCARGAR_ARCHIVO);
		}
	}

	@Override
	public void eliminarArchivoSatCloud(String path, String nombre) {
		PapeleraDto dto = new PapeleraDto();
		dto.setFecha(LocalDateTime.now());
		dto.setPathOriginal(path);
		dto = papeleraServicio.generarId(dto);
		PathGenerator pathGenerator = new PathGenerator();
		String pathNuevo = pathGenerator.generarPathPapeleta(dto.getIdPapelera(), nombre);

		dto.setPathNuevo(pathNuevo);
		dto = papeleraServicio.generarId(dto);
		try {
			nexusImpl.eliminarArchivo(path, dto.getIdPapelera());
		} catch (NexusException e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_ELIMINAR_ARCHIVO);
		}

	}

	@Override
	public void eliminarArchivo(String path) {
		try {
			nexusImpl.eliminarArchivoSat(path);
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_ELIMINAR_ARCHIVO);
		}
	}

	@Override
	public void eliminarArchivoSatCloud(String path) {
		try {

			nexusImpl.generarCarpetasVacias(PEPELERA + path);
			nexusImpl.renameFile(path, PEPELERA + path);

			PapeleraDto dto = new PapeleraDto();
			dto.setFecha(horaActual());
			dto.setPathOriginal(path);
			dto.setPathNuevo(PEPELERA + path);

			papeleraServicio.generarId(dto);

		} catch (Exception e) {
			log.error(ERROR);
		}
	}

	private LocalDateTime horaActual() {
		ZoneId zoneId = ZoneId.of("America/Mexico_City");
		ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
		return zonedDateTime.toLocalDateTime();
	}

}
