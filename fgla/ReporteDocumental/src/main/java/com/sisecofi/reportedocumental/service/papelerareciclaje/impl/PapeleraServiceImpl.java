package com.sisecofi.reportedocumental.service.papelerareciclaje.impl;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.sisecofi.libreria.comunes.dto.CompartidoCloudModel;
import com.sisecofi.libreria.comunes.dto.PapeleraDto;
import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.ArchivoOtroDocumentoFaseModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.ArchivoOtroDocumentoProyectoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.ArchivoPlantillaProyectoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoOtroDocumentoContratoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoOtroDocumentoConvenioModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoOtroDocumentoFaseContratoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoOtroDocumentoFaseConvenioModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoPlantillaContratoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoPlantillaConvenioModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoOtroDocumentoDictamenModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoOtroDocumentoFaseDictamenModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoPlantillaDictamenModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.ArchivoOtroDocumentoFaseReintegroModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.ArchivoOtroDocumentoReintegroModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.ArchivoPlantillaReintegroModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoOtrosDocumentosComiteModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoPlantillaComiteModel;
import com.sisecofi.libreria.comunes.model.papelera.PapeleraModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.repository.ArchivoOtroDocumentoContratoRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoOtroDocumentoConvenioRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoOtroDocumentoDictamenRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoOtroDocumentoFaseRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoOtroDocumentoProyectoRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoOtroDocumentoReintegroRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoOtrosDocumentosComiteRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoPlantillaComiteRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoPlantillaContratoRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoPlantillaConvenioRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoPlantillaProyectoRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoOtroDocumentoFaseContratoRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoOtroDocumentoFaseConvenioRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoOtroDocumentoFaseDictamenRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoOtroDocumentoFaseReintegroRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoPlantillaDictamenRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoPlantillaReintegroRepository;
import com.sisecofi.libreria.comunes.repository.dinamico.DinamicoRepository;
import com.sisecofi.libreria.comunes.repository.papelera.PapeleraRepository;
import com.sisecofi.libreria.comunes.service.NexusService;
import com.sisecofi.libreria.comunes.util.PistaUtil;
import com.sisecofi.libreria.comunes.util.anotaciones.reportes.FilterField;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.libreria.comunes.util.enums.TypeObject;
import com.sisecofi.libreria.comunes.util.exception.NexusException;
import com.sisecofi.libreria.comunes.util.sesion.Session;
import com.sisecofi.reportedocumental.dto.controldocumental.PageDocumentalDto;
import com.sisecofi.reportedocumental.dto.papelera.BusquedaPapelera;
import com.sisecofi.reportedocumental.dto.papelera.BusquedaPapeleraData;
import com.sisecofi.reportedocumental.dto.papelera.BusquedaPapeleraDto;
import com.sisecofi.reportedocumental.dto.papelera.PagePapelera;
import com.sisecofi.reportedocumental.service.PistaService;
import com.sisecofi.reportedocumental.service.ReportExportGenericService;
import com.sisecofi.reportedocumental.service.ServicioArchivo;
import com.sisecofi.reportedocumental.service.impl.MapperArchivoPapelera;
import com.sisecofi.reportedocumental.service.papelerareciclaje.PapeleraService;
import com.sisecofi.reportedocumental.util.UtilZip;
import com.sisecofi.reportedocumental.util.enums.ErroresEnum;
import com.sisecofi.reportedocumental.util.enums.ErroresPapeleraEnum;
import com.sisecofi.reportedocumental.util.exception.ControlDocumentalException;
import com.sisecofi.reportedocumental.util.exception.PapeleraException;
import com.sisecofi.reportedocumental.util.exception.PistaException;
import com.sisecofi.reportedocumental.util.exception.ReporteException;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PapeleraServiceImpl implements PapeleraService {
	
	private final ServicioArchivo servicioArchivo;
	
	private final ArchivoPlantillaProyectoRepository archivoPlantillaProyectoRepository;
	
	private final ArchivoOtroDocumentoProyectoRepository archivoOtroDocumentoProyectoRepository;
	
	private final ArchivoOtroDocumentoFaseRepository archivoOtroDocumentoFaseRepository;
	
	private final ArchivoPlantillaContratoRepository archivoPlantillaContratoRepository;
	
	private final ArchivoPlantillaComiteRepository archivoPlantillaComiteRepository;
	
	private final ArchivoOtroDocumentoFaseContratoRepository archivoOtroDocumentoFaseContratoRepository;
	
	private final ArchivoOtroDocumentoContratoRepository archivoOtroDocumentoContratoRepository;
	
	private final ArchivoOtroDocumentoConvenioRepository archivoOtroDocumentoConvenioRepository;
	
	private final ArchivoOtroDocumentoFaseConvenioRepository archivoOtroDocumentoFaseConvenioRepository;
	
	private final ArchivoPlantillaConvenioRepository archivoPlantillaConvenioRepository;
	
	private final ArchivoPlantillaDictamenRepository archivoPlantillaDictamenRepository;
	
	private final ArchivoOtroDocumentoFaseDictamenRepository archivoOtroDocumentoFaseDictamenRepository;
	
	private final ArchivoOtroDocumentoDictamenRepository archivoOtroDocumentoDictamenRepository;
	
	private final ArchivoOtroDocumentoReintegroRepository archivoOtroDocumentoReintegroRepository;
	
	private final ArchivoOtroDocumentoFaseReintegroRepository archivoOtroDocumentoFaseReintegroRepository;
	
	private final ArchivoPlantillaReintegroRepository archivoPlantillaReintegroRepository;
	
	private final ArchivoOtrosDocumentosComiteRepository archivoOtrosDocumentosComiteRepository;
	
	private final Session session;
	
	private final PistaService pistaService;
	
	private final PapeleraRepository papeleraRepository;
	
	private final ReportExportGenericService exportGenericService;
	
	private final DinamicoRepository dinamicoRepository;
	
	private final NexusService nexusService;

	@Qualifier("mapperArchivoPapelera")
	private final MapperArchivoPapelera archivoPapelera;
	private final Map<Class<?>, JpaRepository<?, ?>> repoMap = new HashMap<>();
	private static final String IN = " IN(VALORES)";
	private static final String AND = " AND ";
	private static final String TIMESTAMP = "TO_TIMESTAMP('";
	private static final String FECHA = "','YYYY/MM/DD HH24:MI:SS')";
	
	
	@PostConstruct
	  private void init() {
		repoMap.put(ArchivoPlantillaProyectoModel.class, archivoPlantillaProyectoRepository);
	    repoMap.put(ArchivoOtroDocumentoFaseModel.class, archivoOtroDocumentoFaseRepository);
	    repoMap.put(ArchivoOtroDocumentoProyectoModel.class, archivoOtroDocumentoProyectoRepository);
	    repoMap.put(ArchivoPlantillaContratoModel.class, archivoPlantillaContratoRepository);
	    repoMap.put(ArchivoPlantillaComiteModel.class, archivoPlantillaComiteRepository);
	    repoMap.put(ArchivoOtroDocumentoFaseContratoModel.class, archivoOtroDocumentoFaseContratoRepository);
	    repoMap.put(ArchivoOtroDocumentoContratoModel.class, archivoOtroDocumentoContratoRepository);
	    repoMap.put(ArchivoOtroDocumentoConvenioModel.class, archivoOtroDocumentoConvenioRepository);
	    repoMap.put(ArchivoOtroDocumentoFaseConvenioModel.class, archivoOtroDocumentoFaseConvenioRepository);
	    repoMap.put(ArchivoPlantillaConvenioModel.class, archivoPlantillaConvenioRepository);
	    repoMap.put(ArchivoPlantillaDictamenModel.class, archivoPlantillaDictamenRepository);
	    repoMap.put(ArchivoOtroDocumentoFaseDictamenModel.class, archivoOtroDocumentoFaseDictamenRepository);
	    repoMap.put(ArchivoOtroDocumentoDictamenModel.class, archivoOtroDocumentoDictamenRepository);
	    repoMap.put(ArchivoOtroDocumentoReintegroModel.class, archivoOtroDocumentoReintegroRepository);
	    repoMap.put(ArchivoOtroDocumentoFaseReintegroModel.class, archivoOtroDocumentoFaseReintegroRepository);
	    repoMap.put(ArchivoPlantillaReintegroModel.class, archivoPlantillaReintegroRepository);
	    repoMap.put(ArchivoOtrosDocumentosComiteModel.class, archivoOtrosDocumentosComiteRepository);
	  }
	
	@Override
	public PagePapelera obtenerReporte(BusquedaPapeleraDto busquedaPapeleraDto) {
		try {
			busquedaPapeleraDto.setDataReporteDto(new BusquedaPapelera(new BusquedaPapeleraData()));
			PageGeneric page = dinamicoRepository.generarData(busquedaPapeleraDto, generarWhere(busquedaPapeleraDto), true);
			
			if (page.getContent() != null && page.getContent().isEmpty()) {
				throw new ReporteException(ErroresEnum.ERROR_SIN_RESULTADOS);
			}
			

			
			// pistaService.guardarPista(ModuloPista.SISTEMA.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(), TipoSeccionPista.PAPELERA_RECICLAJE.getIdSeccionPista(), busquedaPapeleraDto.criterios());
			return new PagePapelera(page);
		} catch (ReporteException e) {
			throw e;
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERROR_GENERAL_PISTAS, e);
		}
	}
	
	@Override
	public boolean eliminarArchivos(List<Archivo> eliminados) {
		for (Archivo eliminado : eliminados) {
			String ruta = eliminado.getRuta();
			
			PapeleraDto papeleraDto = new PapeleraDto();
			papeleraDto.setPathOriginal(ruta);
			papeleraDto.setIdArchivo(eliminado.getId());
			papeleraDto.setTipoArchivo(eliminado.getType());
			
			identificaTipoArchivoEliminado(eliminado, papeleraDto);
			papeleraDto.setNombreDocumento(eliminado.getNombre());
			papeleraDto.setDescripcion(eliminado.getDescripcion());
			Optional<Usuario> usuarioOpt = session.retornarUsuario();
			if(usuarioOpt.isPresent()) {
				Usuario usuario = usuarioOpt.get();
				papeleraDto.setIdUsuario(usuario.getIdUser());
				papeleraDto.setUsuarioElimina(usuario.getNombre());
			}
			if (ruta != null) {
				servicioArchivo.enviarArchivoSatCloudPapelera(papeleraDto);
			}
		}
		return true;
	}
	
	private PapeleraDto identificaTipoArchivoEliminado(Archivo eliminado, PapeleraDto papeleraDto) {
		
		Set<String> tiposConEstatusTrue = Set.of(
				  "ArchivoPlantillaProyectoModel",
				  "ArchivoPlantillaContratoModel",
				  "ArchivoPlantillaComiteModel",
				  "ArchivoPlantillaConvenioModel",
				  "ArchivoPlantillaDictamenModel",
				  "ArchivoPlantillaReintegroModel"
				);
	
		
		boolean estatus = tiposConEstatusTrue.contains(eliminado.getClass().getSimpleName());
		
		guardarArchivo(eliminado, papeleraDto, estatus);
		
		return papeleraDto;
	}
	
	public void guardarArchivo(Archivo archivo, PapeleraDto papeleraDto, boolean estatus) {
		@SuppressWarnings("unchecked")
		JpaRepository<Archivo, ?> repo = (JpaRepository<Archivo, ?>) repoMap.get(archivo.getClass());
	    
	    if (repo == null) {
	        throw new IllegalArgumentException(
	            "No se encontró repositorio para la clase: " + archivo.getClass().getSimpleName()
	        );
	    }
	    
	    papeleraDto.setIdProyecto(archivo.getIdProyecto());
		papeleraDto.setNombreCorto(archivo.getNombreCorto());
		papeleraDto.setFase(archivo.getFase());
		papeleraDto.setPlantilla(archivo.getPlantilla());
		papeleraDto.setTamano(archivo.getTamanoMb() == null ? null : archivo.getTamanoMb().toString());
		
		archivo.setRuta(null);
		archivo.setCargado(false);
		archivo.setTamanoMb(null);
		archivo.setEstatus(estatus);

	    repo.save(archivo);
	}

	
	@Override
	public boolean eliminarArchivosPapelera(List<PapeleraDto> archivosPapelera) {
		for (PapeleraDto papelera : archivosPapelera) {

			// pistaService.guardarPista(ModuloPista.SISTEMA.getId(), TipoMovPista.BORRA_REGISTRO.getId(), TipoSeccionPista.PAPELERA_RECICLAJE.getIdSeccionPista(), "Id Proyecto: " + papelera.getIdProyecto() + "|" + "Nombre corto: " + papelera.getNombreCorto() + "|" + "Nombre del documento: " + papelera.getPlantilla());
			servicioArchivo.eliminarArchivoSatCloud(papelera);
		}
		return true;
	}
	
	@Override
	public boolean restaurarArchivoPapelera(List<PapeleraDto> archivosPapelera) {
		for (PapeleraDto archivoPapeleraR : archivosPapelera) {
			
			PapeleraModel papeleraModel = papeleraRepository.findById(archivoPapeleraR.getIdPapelera())
					.orElseThrow(() -> new PapeleraException(ErroresPapeleraEnum.ERROR_AL_CONSULTAR));
			archivoPapeleraR.setTamano(papeleraModel.getTamano());
			

			
			// pistaService.guardarPista(ModuloPista.SISTEMA.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(), TipoSeccionPista.PAPELERA_RECICLAJE.getIdSeccionPista(), "Id Proyecto: " + archivoPapeleraR.getIdProyecto() + "|" + "Nombre corto: " + archivoPapeleraR.getNombreCorto() + "|" + "Nombre del documento: " + archivoPapeleraR.getPlantilla());
			servicioArchivo.restaurarArchivoPapelera(archivoPapeleraR);
			
			
			switch (archivoPapeleraR.getTipoArchivo()) {
		    case "tipoFase":
		        procesarArchivo(archivoPapeleraR, archivoOtroDocumentoFaseRepository, true);
		        break;
		    case "tipoProyecto":
		        procesarArchivo(archivoPapeleraR, archivoOtroDocumentoProyectoRepository, true);
		        break;
		    case "tipoPlantilla":
		        procesarArchivo(archivoPapeleraR, archivoPlantillaProyectoRepository, false);
		        break;
		    case "tipoPlantillaContrato":
		        procesarArchivo(archivoPapeleraR, archivoPlantillaContratoRepository, false);
		        break;
		    case "tipoPlantillaComite":
		        procesarArchivo(archivoPapeleraR, archivoPlantillaComiteRepository, false);
		        break;
		    case "tipoFaseContrato":
		        procesarArchivo(archivoPapeleraR, archivoOtroDocumentoFaseContratoRepository, true);
		        break;
		    case "tipoContrato":
		        procesarArchivo(archivoPapeleraR, archivoOtroDocumentoContratoRepository, true);
		        break;
		    case "tipoConvenio":
		        procesarArchivo(archivoPapeleraR, archivoOtroDocumentoConvenioRepository, true);
		        break;
		    case "tipoFaseConvenio":
		        procesarArchivo(archivoPapeleraR, archivoOtroDocumentoFaseConvenioRepository, true);
		        break;
		    case "tipoPlantillaConvenio":
		        procesarArchivo(archivoPapeleraR, archivoPlantillaConvenioRepository, false);
		        break;
		    case "tipoPlantillaDictamen":
		        procesarArchivo(archivoPapeleraR, archivoPlantillaDictamenRepository, false);
		        break;
		    case "tipoFaseDictamen":
		        procesarArchivo(archivoPapeleraR, archivoOtroDocumentoFaseDictamenRepository, true);
		        break;
		    case "tipoDictamen":
		        procesarArchivo(archivoPapeleraR, archivoOtroDocumentoDictamenRepository, true);
		        break;
		    case "tipoReintegro":
		        procesarArchivo(archivoPapeleraR, archivoOtroDocumentoReintegroRepository, true);
		        break;
		    case "tipoFaseReintegro":
		        procesarArchivo(archivoPapeleraR, archivoOtroDocumentoFaseReintegroRepository, true);
		        break;
		    case "tipoPlantillaReintegro":
		        procesarArchivo(archivoPapeleraR, archivoPlantillaReintegroRepository, false);
		        break;
		    case "tipoComite":
		        procesarArchivo(archivoPapeleraR, archivoOtrosDocumentosComiteRepository, true);
		        break;
		    default:
		        throw new ControlDocumentalException(ErroresEnum.ERROR_TIPO_ARCHIVO_NO_SOPORTADO);
		}

		}
		return true;
	}
	
	private <T extends Archivo> void procesarArchivo(
			PapeleraDto archivoPapeleraR,
		    JpaRepository<T, Integer> repository,
		    boolean actualizarEstatus
		) {
		    T archivo = repository.findById(archivoPapeleraR.getIdArchivo())
		        .orElseThrow(() -> new ControlDocumentalException(ErroresEnum.ERROR_OBTENER_ARCHIVO_PLANTILLA_PROYECTO));

		    archivo.setCargado(true);
		    archivo.setRuta(archivoPapeleraR.getPathOriginal());
		    archivo.setTamanoMb(
		        StringUtils.isBlank(archivoPapeleraR.getTamano())
		            ? 0
		            : Double.parseDouble(archivoPapeleraR.getTamano())
		    );

		    if (actualizarEstatus) {
		        archivo.setEstatus(true);
		    }

		    repository.save(archivo);
		}


	@Override
	public byte[] exportarExcel(BusquedaPapeleraDto busquedaPapeleraDto) {
		try {
			PageDocumentalDto<Object[]> pageMerge = generaPage(busquedaPapeleraDto);
			List<String> etiquetas = pageMerge.getEtiquetas();
			log.info("Etiquetess: {}", etiquetas);
			//Size debe quedar igual a 9 para no pintar las cabeceras siguientes
			if(etiquetas.size() > 9) {
				while(etiquetas.size() > 9) {
					etiquetas.remove(etiquetas.size()-1);
				}
			}
			
			byte[] archivo = exportGenericService.exportarReporte(new PageGeneric(pageMerge.getPage(), etiquetas),
					"Control documental", archivoPapelera);

			String mov = PistaUtil.cadenaPistasObjecto(busquedaPapeleraDto);

			// pistaService.guardarPista(ModuloPista.SISTEMA.getId(),

			// TipoMovPista.IMPRIME_REGISTRO.getId(),

			// TipoSeccionPista.PAPELERA_RECICLAJE.getIdSeccionPista(), mov);

			return archivo;
		} catch (PistaException e) {
			throw e;
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERROR_GENERAL_PISTAS, e);
		}
	}
	
	@Override
	public byte[] descargarZip(BusquedaPapeleraDto busquedaPapeleraDto) {
		try {
			PageDocumentalDto<Object[]> pageMerge = generaPage(busquedaPapeleraDto);

			Set<String> archivos = pageMerge.getPage().getContent().stream().filter(d -> d[9] != null)
					.map(d -> String.valueOf(d[9])).collect(Collectors.toSet());
			log.info("archivos: {}", archivos);

			List<String> nombre = new ArrayList<>();
			List<InputStream> archivosInputStream = new ArrayList<>();

			archivos.stream().forEach(d -> {
				try {
					InputStream archivo = nexusService.descargarArchivo(d);
					nombre.add(UtilZip.getLastSegment(d));
					archivosInputStream.add(archivo);
				} catch (NexusException e) {
					log.error("Error archivo no se puedo generar al zip:");
				}
			});
			byte[] bytes = UtilZip.createZip(nombre, archivosInputStream);

			String s = PistaUtil.cadenaPistasObjecto(busquedaPapeleraDto);

			// pistaService.guardarPista(ModuloPista.SISTEMA.getId(),

			// TipoMovPista.IMPRIME_REGISTRO.getId(),

			// TipoSeccionPista.PAPELERA_RECICLAJE.getIdSeccionPista(), s);

			return bytes;
		} catch (PistaException | ReporteException e) {
			throw e;
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERROR_AL_GENERAR_CONTRASENNIA, e);
		}
	}
	
	@Override
	public byte[] descargarArchivo(Long idPapelera) {
		try {
			
			PapeleraModel papeleraModel = papeleraRepository.getReferenceById(idPapelera);
			
			log.info("descargando archivo : {}", papeleraModel.getPathNuevo());
			InputStream archivo = nexusService.descargarArchivo(papeleraModel.getPathNuevo());
			
			byte[] bytes = UtilZip.convertInputStreamToByteArray(archivo);
			
			String movimiento = PistaUtil.cadenaPistasObjecto(papeleraModel);

			// pistaService.guardarPista(ModuloPista.SISTEMA.getId(),

			// TipoMovPista.IMPRIME_REGISTRO.getId(),

			// TipoSeccionPista.PAPELERA_RECICLAJE.getIdSeccionPista(), movimiento);

			return bytes;
		} catch (PistaException | ReporteException e) {
			throw e;
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERROR_AL_GENERAR_CONTRASENNIA, e);
		}
	}

	@Override
	public List<CompartidoCloudModel> descargarCloud(BusquedaPapeleraDto busquedaPapeleraDto) {
		try {
			PageDocumentalDto<Object[]> pageMerge = generaPage(busquedaPapeleraDto);

			Set<String> archivos = pageMerge.getPage().getContent().stream().filter(d -> d[9] != null)
					.map(d -> String.valueOf(d[9])).collect(Collectors.toSet());

			log.info("archivos: {}", archivos);
			
			List<String> rutaArchivos = new ArrayList<>();
			for(String elemento : archivos) {
				rutaArchivos.add(elemento.substring(0, elemento.lastIndexOf("/")+1));
			}
			
			String s = PistaUtil.cadenaPistasObjecto(busquedaPapeleraDto);

			// pistaService.guardarPista(ModuloPista.SISTEMA.getId(),

			// TipoMovPista.IMPRIME_REGISTRO.getId(),

			// TipoSeccionPista.PAPELERA_RECICLAJE.getIdSeccionPista(), s);

			return nexusService.compartirSoloLectura(rutaArchivos);
		} catch (PistaException e) {
			throw e;
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERROR_AL_GENERAR_CONTRASENNIA, e);
		}
	}
	
	private PageDocumentalDto<Object[]> generaPage(BusquedaPapeleraDto busquedaPapeleraDto)
			throws IllegalAccessException {
		busquedaPapeleraDto.setDataReporteDto(new BusquedaPapelera(new BusquedaPapeleraData()));
		PageGeneric page = dinamicoRepository.generarData(busquedaPapeleraDto, generarWhere1(busquedaPapeleraDto),
				false);
		
		PageDocumentalDto<Object[]> dto = new PageDocumentalDto<>();
		dto.setPage(page);
		dto.setEtiquetas(page.getEtiquetas());
		return dto;
	}
	
	public static <T> Page<T> mergePages(Page<T> page1, Page<T> page2) {
		List<T> combinedContent = new ArrayList<>();
		combinedContent.addAll(page1.getContent());
		combinedContent.addAll(page2.getContent());
		long totalElements = page1.getTotalElements() + page2.getTotalElements();//FALSO POSITIVO DE FORTIFY
		return new PageImpl<>(combinedContent, page1.getPageable(), totalElements);
	}
	
	private String generarWhere(BusquedaPapeleraDto busquedaDto) throws IllegalAccessException {
		StringBuilder where = new StringBuilder();
		
		if(busquedaDto.getIdUsuario() != null && (busquedaDto.getFecha() == null && busquedaDto.getFechaFinal() == null)) {
			where.append(" AND s1.estatus = true and s1.id_user = " + busquedaDto.getIdUsuario());
		}else if(busquedaDto.getIdUsuario() == null && (busquedaDto.getFecha() != null && busquedaDto.getFechaFinal() != null)) {
			where.append("AND s1.estatus = true ");
			rangoFechas(where, busquedaDto);
			
		}else {
			where.append("AND s1.estatus = true AND s1.id_user = " + busquedaDto.getIdUsuario());
			rangoFechas(where, busquedaDto);
		}
		
		log.info("Where generado: {}", where.toString());
		return where.toString();
	}
	
	private void rangoFechas(StringBuilder where, BusquedaPapeleraDto busquedaDto) {
	    where.append(AND);
	    where.append(" DATE_TRUNC('day',s1.fecha) BETWEEN ");
	    where.append(TIMESTAMP);
	    where.append(busquedaDto.getFecha().toString().split("T")[0]);
	    where.append(" 00:00:00");
	    where.append(FECHA);
	    where.append(" and ");
	    where.append(TIMESTAMP);
	    where.append(busquedaDto.getFechaFinal().toString().split("T")[0]);
	    where.append(" 23:59:59");
	    where.append(FECHA);
	}
	
	private String generarWhere1(BusquedaPapeleraDto busquedaPapeleraDto) throws IllegalAccessException {
	    StringBuilder where = new StringBuilder();

	    for (Field field : busquedaPapeleraDto.getClass().getDeclaredFields()) {
	        ReflectionUtils.makeAccessible(field);
	        Object fieldValue = field.get(busquedaPapeleraDto);

	        if (fieldValue != null) {
	            FilterField filter = AnnotationUtils.findAnnotation(field, FilterField.class);
	            if (filter != null) {
	                agregarCondicionFiltro(where, fieldValue, filter);
	            }
	        }
	    }

	    log.info("Where generado: {}", where.toString());
	    return where.toString();
	}

	@SuppressWarnings("unchecked")
	private void agregarCondicionFiltro(StringBuilder where, Object fieldValue, FilterField filter) {
	    
	    if (filter.type().equals(TypeObject.TYPE_LIST)) {
	        List<Integer> lista = (List<Integer>) fieldValue;
	        if (!lista.isEmpty()) {
	            where.append(AND)
	                 .append(filter.filter())
	                 .append(IN.replace("VALORES", lista.toString())
	                           .replace("[", "").replace("]", ""));
	        }
	    } else if (!fieldValue.toString().isEmpty()) {
	        where.append(AND)
	             .append(filter.filter())
	             .append("=")
	             .append("'")
	             .append(fieldValue.toString())
	             .append("'");
	    }
	}


}
