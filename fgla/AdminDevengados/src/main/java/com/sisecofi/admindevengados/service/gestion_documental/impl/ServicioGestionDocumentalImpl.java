package com.sisecofi.admindevengados.service.gestion_documental.impl;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sisecofi.admindevengados.dto.ArchivoFaseDto;
import com.sisecofi.admindevengados.dto.DescargaSatCloudRequest;
import com.sisecofi.admindevengados.microservicio.PapeleraServicoControl;
import com.sisecofi.admindevengados.repository.DictamenRepository;
import com.sisecofi.admindevengados.repository.facturas.FacturasRepository;
import com.sisecofi.admindevengados.repository.facturas.NotasCreditoRepository;
import com.sisecofi.admindevengados.repository.gestion_documental.CarpetaPlantillaDictamenRepository;
import com.sisecofi.admindevengados.service.DictamenService;
import com.sisecofi.admindevengados.service.PistaService;
import com.sisecofi.admindevengados.service.gestion_documental.ServicioGestionDocumental;
import com.sisecofi.admindevengados.service.impl.NexusImpl;
import com.sisecofi.admindevengados.util.exception.CatalogoException;
import com.sisecofi.libreria.comunes.dto.CarpetaCompartidaDto;
import com.sisecofi.libreria.comunes.dto.carpeta.ArchivoCargadoFaseDto;
import com.sisecofi.libreria.comunes.dto.carpeta.CarpetaDtoResponse;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.model.dictamenes.ReferenciaPagoModel;
import com.sisecofi.libreria.comunes.model.dictamenes.factura.FacturaModel;
import com.sisecofi.libreria.comunes.model.dictamenes.factura.NotaCreditoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoOtroDocumentoDictamenModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoOtroDocumentoFaseDictamenModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoPlantillaDictamenModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.CarpetaPlantillaDictamenModel;
import com.sisecofi.libreria.comunes.util.ConstantesParaRutasSATCloud;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.libreria.comunes.repository.ArchivoOtroDocumentoDictamenRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoOtroDocumentoFaseDictamenRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoPlantillaDictamenRepository;
import com.sisecofi.admindevengados.util.Constantes;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("rawtypes")
public class ServicioGestionDocumentalImpl implements ServicioGestionDocumental {

	private final CarpetaPlantillaDictamenRepository carpetaPlantillaDictamenRepository;
	private final ArchivoOtroDocumentoFaseDictamenRepository archivoOtroDocumentoFaseDictamenRepository;
	private final ArchivoOtroDocumentoDictamenRepository archivoOtroDocumentoDictamenRepository;
	private final ArchivoPlantillaDictamenRepository archivoPlantillaDictamenRepository;
	private final DictamenService dictamenService;
	private final NexusImpl nexusImpl;
	private final FacturasRepository facturasRepository;
	private final NotasCreditoRepository notasCreditoRepository;
	private final DictamenRepository dictamenRepositoy;
	private final PistaService pistaService;
	private final PapeleraServicoControl papeleraServicoControl;
	private static final String CONEXION_CERRADO = "Connection is closed";

	@SuppressWarnings("unchecked")
	@Override
	public List<CarpetaDtoResponse> obtenerEstructuraDocumental(Long idDictamen) {
		List<Archivo> archivos = new ArrayList<>();

		
		Dictamen dictamen = dictamenRepositoy.findByIdDictamen(idDictamen)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));

		List<CarpetaPlantillaDictamenModel> listaCarpetas = carpetaPlantillaDictamenRepository
				.findByNivelAndIdDictamenAndCarpetaBaseEstatusTrue(1, idDictamen);

		List<FacturaModel> listaFacturas = facturasRepository.facturasNoCanceladas(idDictamen);

		List<NotaCreditoModel> listaNotas = notasCreditoRepository.notasNoCanceladas(idDictamen);

		List<ArchivoPlantillaDictamenModel> arch = new ArrayList<>(listaCarpetas.get(0).getArchivosActivos());

		for (FacturaModel factura : listaFacturas) {
			for (ReferenciaPagoModel ref : factura.getReferenciaPago()) {
				if (ref.getArchivoPdf() != null) {
					arch.add(ref.getArchivoPdf());
				}
			}
			if (factura.getArchivoPdf()!=null) {
				arch.add(factura.getArchivoPdf());
				arch.add(factura.getArchivoXml());
			}
			
		}
		for (NotaCreditoModel nota : listaNotas) {
			if (nota.getArchivoPdf()!=null) {
				arch.add(nota.getArchivoPdf());
				arch.add(nota.getArchivoXml());
			}
		}

		archivos.addAll(arch);

		List<Archivo> listaDocsFase = archivoOtroDocumentoFaseDictamenRepository
				.findByIdDictamenAndEstatusTrueOrderById(idDictamen);

		archivos.addAll(listaDocsFase);

		List<CarpetaDtoResponse> estructura = new ArrayList<>();

		arch.sort(Comparator.comparing(ArchivoPlantillaDictamenModel::getNombre));
		if (!listaCarpetas.isEmpty()) {
			CarpetaDtoResponse carpetaDto = new CarpetaDtoResponse();
			carpetaDto.setNombre(dictamen.getIdDictamenVisual() );
			carpetaDto.setDescripcion(dictamen.getIdDictamenVisual());
			carpetaDto.setRuta(listaCarpetas.get(0).getRuta());
			List<Object> subRows = new ArrayList<>();
			for (ArchivoPlantillaDictamenModel elemento : arch) {
				subRows.add(elemento);
			}
			CarpetaDtoResponse carpetaOtro = new CarpetaDtoResponse();
			carpetaOtro.setDescripcion(Constantes.CARPETA_OTROS);
			carpetaOtro.setNombre(Constantes.CARPETA_OTROS);
			carpetaOtro.setRuta(listaCarpetas.get(0).getRuta() +"/"+ ConstantesParaRutasSATCloud.PATH_OTROS_DOCUMENTOS);
			carpetaOtro.setSubRows(listaDocsFase);
			subRows.add(carpetaOtro);
			carpetaDto.setSubRows(subRows);
			estructura.add(carpetaDto);
		}
		CarpetaDtoResponse carpetaDto = new CarpetaDtoResponse();
		carpetaDto.setDescripcion(Constantes.CARPETA_OTROS);
		carpetaDto.setNombre(Constantes.CARPETA_OTROS);
		List<Archivo> listaOtros = archivoOtroDocumentoDictamenRepository
				.findByIdDictamenAndEstatusTrue(idDictamen);
		archivos.addAll(listaOtros);
		carpetaDto.setSubRows(listaOtros);

		carpetaDto.setRuta(dictamenService.generarRutaDictamen(dictamen.getIdDictamenVisual(), dictamen.getContratoModel()));
		estructura.add(carpetaDto);

		String resultado = archivos.stream()
				.map(archivo -> "|id: " + archivo.getId() + "|descripcion: " + archivo.getDescripcion())
				.collect(Collectors.joining(" "));



		// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


		// TipoSeccionPista.DICTAMEN_GESTION_DOCUMENTAL.getIdSeccionPista(),


		// Constantes.getAtributosGenerales()[11] + dictamen.getIdDictamenVisual() + resultado, Optional.empty());

		return estructura;
	}

	@Override
	public CarpetaCompartidaDto descargaSatCloud(DescargaSatCloudRequest descargaSatCloudRequest) {
		String encodedUrl = obtenerEncodedUrl(descargaSatCloudRequest);



		// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


		// TipoSeccionPista.DICTAMEN_GESTION_DOCUMENTAL.getIdSeccionPista(),


		// Constantes.getAtributosGenerales()[11] + descargaSatCloudRequest.getIdDictamen(), Optional.empty());

		return descargarFolderSatCloud(encodedUrl);
	}

	private CarpetaCompartidaDto descargarFolderSatCloud(String path) {
		try {
			String contenido = nexusImpl.obtenerContenidoCarpeta(path);



			// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),


			// TipoSeccionPista.DICTAMEN_GESTION_DOCUMENTAL.getIdSeccionPista(), contenido, Optional.empty());
			return nexusImpl.crearCarpetaCompartida(path);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_DESCARGAR_ARCHIVO);
		}
	}

	private String descargarFolder(String path) {
		try {
			InputStream obj = nexusImpl.descargarFolder(path);
			byte[] bytes = obj.readAllBytes();
			obj.close();

			String contenido = nexusImpl.obtenerContenidoCarpeta(path);



			// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),


			// TipoSeccionPista.DICTAMEN_GESTION_DOCUMENTAL.getIdSeccionPista(), contenido, Optional.empty());

			return Base64.getEncoder().encodeToString(bytes);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_DESCARGAR_ARCHIVO);
		}
	}

	@Override
	public String descargaMasiva(DescargaSatCloudRequest descargaSatCloudRequest) {
		String encodedUrl = obtenerEncodedUrl(descargaSatCloudRequest);



		// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


		// TipoSeccionPista.DICTAMEN_GESTION_DOCUMENTAL.getIdSeccionPista(),


		// Constantes.getAtributosGenerales()[11] + descargaSatCloudRequest.getIdDictamen(), Optional.empty());

		return descargarFolder(encodedUrl);
	}

	private String obtenerEncodedUrl(DescargaSatCloudRequest descargaSatCloudRequest) {
		if (descargaSatCloudRequest.getPath() != null && !descargaSatCloudRequest.getPath().isEmpty()) {
			return descargaSatCloudRequest.getPath();
		}

		Long idDic = descargaSatCloudRequest.getIdDictamen();
		Dictamen dictamen = dictamenRepositoy.findByIdDictamen(idDic)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));
		String rutaGeneral = dictamenService.generarRuta(dictamen.getIdDictamenVisual()  ,
				dictamen.getContratoModel());
		return rutaGeneral.replace("|", "%7C");
	}

	@Override
	public boolean eliminarArchivos(List<Archivo> eliminados) {
		Long id = 0L;
		for (Archivo eliminado : eliminados) {
			if (eliminado instanceof ArchivoOtroDocumentoFaseDictamenModel archivoEspecifico) {
				id = archivoEspecifico.getIdDictamen();
			} else if (eliminado instanceof ArchivoOtroDocumentoDictamenModel archivoEspecifico) {
				id = archivoEspecifico.getIdDictamen();
			} else if (eliminado instanceof ArchivoPlantillaDictamenModel archivoEspecifico) {
				archivoEspecifico = archivoPlantillaDictamenRepository.findById(eliminado.getId())
						.orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_AL_ELIMINAR_ARCHIVO));
				id = archivoEspecifico.getCarpetaPlantillaModel().getIdDictamen();
			}

			String path = eliminado.getRuta();
			int lastSlashIndex = path.lastIndexOf("/");

			String nombreArchivo = "|" + path.substring(lastSlashIndex + 1);

			papeleraServicoControl.enviarPapelera(eliminados);



			// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.BORRA_REGISTRO.getId(),


			// TipoSeccionPista.DICTAMEN_GESTION_DOCUMENTAL.getIdSeccionPista(),


			// Constantes.getAtributosGenerales()[11] + id + nombreArchivo, Optional.empty());
		}
		return true;
	}

	@Override
	@Transactional
	public Boolean cargarArchivoFaseIndividual(ArchivoCargadoFaseDto dto) {
		Integer idArch = null;
		try {
		    idArch = Integer.parseInt(dto.getIdArchivo());
		} catch (NumberFormatException e) {
		    log.error("El valor proporcionado no es un número válido.");
		    idArch = null;
		}

		try {
			ArchivoFaseDto archivoDto = new ArchivoFaseDto();

			Archivo archivoEspecifico = obtenerArchivoPorTipo(dto.getType(), idArch, archivoDto, dto.getIdDictamen(), dto.isNoAplica());
			actualizarArchivoConDatos(archivoEspecifico, dto.isNoAplica(), dto.getJustificacion(),
					dto.getCarpeta(), dto.getDescripcion());

			String nombreCompuesto = generarNombreCompuesto(archivoEspecifico, dto.getFile(), dto.getType());

			archivoDto.setArchivo(archivoEspecifico);
			archivoDto.setNombreFile(nombreCompuesto);
			archivoDto.setIdDictamen(dto.getIdDictamen());
			archivoDto.setNombreFase(dto.getNombreFase());
			archivoDto.setFile(dto.getFile());

			cargarArchivo(archivoDto);

			return true;
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA);
		}
	}

	private Archivo obtenerArchivoPorTipo(String type, Integer idArchivo, ArchivoFaseDto dto, Long idDictamen, boolean aplica) {
		dto.setTipoMov(3);
		Long idDic = idDictamen;
		Dictamen dictamen = dictamenRepositoy.findByIdDictamen(idDic)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));
		switch (type) {
		case Constantes.TIPO_PLANTILLA:
			dto.setRuta(dictamenService.generarRuta(dictamen.getIdDictamenVisual() , dictamen.getContratoModel()));
			dto.setTipoMov(1);
			ArchivoPlantillaDictamenModel ad= archivoPlantillaDictamenRepository.findById(idArchivo)
					.orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_ARCHIVO));
			ad.actualizaObligatorio(aplica, ad.getArchivoBase().isObligatorio());
			return ad;
		case Constantes.TIPO_FASE:
			dto.setRuta(dictamenService.generarRutaFase(dictamen.getIdDictamenVisual(), dictamen.getContratoModel()));
			return idArchivo == null ? new ArchivoOtroDocumentoFaseDictamenModel()
					: archivoOtroDocumentoFaseDictamenRepository.findById(idArchivo)
							.orElseGet(ArchivoOtroDocumentoFaseDictamenModel::new);
		case Constantes.TIPO_DICTAMEN:
			dto.setRuta(dictamenService.generarRutaDictamen(dictamen.getIdDictamenVisual(), dictamen.getContratoModel()));
			return idArchivo == null ? new ArchivoOtroDocumentoDictamenModel()
					: archivoOtroDocumentoDictamenRepository.findById(idArchivo)
							.orElseGet(ArchivoOtroDocumentoDictamenModel::new);
		default:
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_ARCHIVO);
		}
	}

	private void actualizarArchivoConDatos(Archivo archivo, boolean noAplica, String justificacion,
			String carpeta, String descripcion) {
		archivo.setNoAplica(noAplica);
		archivo.setJustificacion(justificacion);
		archivo.setFechaModificacion(horaActual());

		if (archivo instanceof ArchivoPlantillaDictamenModel archivoEspecifico) {
			archivoEspecifico.setCarpeta(carpeta);
		} else {
			archivo.setDescripcion(descripcion);
			archivo.setNombre(descripcion);
		}
	}

	private String generarNombreCompuesto(Archivo archivo, MultipartFile file, String type) {
		if (file != null && !file.isEmpty()) {
			String originalFilename = file.getOriginalFilename();
			if (originalFilename != null) {
				String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

				if (type.equals(Constantes.TIPO_PLANTILLA)) {
					return archivo.getNombre() + extension;
				} else {
					return originalFilename;
				}
			}
		}
		return "";
	}

	private LocalDateTime horaActual() {
		ZoneId zoneId = ZoneId.of("America/Mexico_City");
		ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
		return zonedDateTime.toLocalDateTime();
	}

	@Override
	public String descargarArchivo(String path) {
		try {
			Long idDictamen = 0L;
			InputStream obj = nexusImpl.descargarArchivo(path);
			byte[] bytes = obj.readAllBytes();
			obj.close();

			Optional<Archivo> arc;
			arc = archivoOtroDocumentoFaseDictamenRepository.findByRuta(path);
			if (arc.isPresent()) {
				Archivo ar = arc.get();
				ArchivoOtroDocumentoFaseDictamenModel otro = (ArchivoOtroDocumentoFaseDictamenModel) ar;
				idDictamen = otro.getIdDictamen();
			} else {
				Optional<Archivo> arcPlantilla = archivoPlantillaDictamenRepository
						.findByPathAndFacturaStatusNotCancelled(path);
				if (arcPlantilla.isPresent()) {
					Archivo ar = arcPlantilla.get();
					ArchivoPlantillaDictamenModel plantilla = (ArchivoPlantillaDictamenModel) ar;
					idDictamen = plantilla.getCarpetaPlantillaModel().getIdDictamen();

				} else {
					Optional<Archivo> arcOtro = archivoOtroDocumentoDictamenRepository.findByRuta(path);
					if (arcOtro.isPresent()) {
						Archivo ar = arcOtro.get();
						ArchivoOtroDocumentoDictamenModel otro = (ArchivoOtroDocumentoDictamenModel) ar;
						idDictamen = otro.getIdDictamen();
					}

				}
			}
			int lastSlashIndex = path.lastIndexOf("/");

			String nombreArchivo = "|" + path.substring(lastSlashIndex + 1);

			// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),

			// TipoSeccionPista.DICTAMEN_GESTION_DOCUMENTAL.getIdSeccionPista(),

			// Constantes.getAtributosGenerales()[11] + idDictamen + nombreArchivo, Optional.empty());
			return Base64.getEncoder().encodeToString(bytes);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_DESCARGAR_ARCHIVO, e);
		}
	}

	@Transactional
	private String cargarArchivo(ArchivoFaseDto archivoDto) {
		try {
			Archivo archivo = archivoDto.getArchivo();
			if (archivoDto.getFile() != null && !archivoDto.getFile().isEmpty()) {
				String nombreCompuesto = archivoDto.getNombreFile();
				String path = archivoDto.getRuta();
				MultipartFile file = archivoDto.getFile();

				double sizeMb = (double) file.getSize() / (1024 * 1024);
				archivo.setTamanoMb(sizeMb);
				archivo.setRuta(path + "/" + nombreCompuesto);
				archivo.setCargado(true);
				archivo.setEstatus(true);
				archivo.setFechaModificacion(LocalDateTime.now());

				
				cargarArchivo(file, path, nombreCompuesto);
			}

			guardarArchivo(archivo, archivoDto.getIdDictamen());

			String nombreArchivo = "";

			if (archivo.getRuta() != null) {
				String path = archivo.getRuta();
				int lastSlashIndex = path.lastIndexOf("/");

				nombreArchivo = "|" + path.substring(lastSlashIndex + 1);
			}



			// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), archivoDto.getTipoMov(),


			// TipoSeccionPista.DICTAMEN_GESTION_DOCUMENTAL.getIdSeccionPista(),


			// Constantes.getAtributosGenerales()[10] + archivoDto.getIdDictamen() + nombreArchivo,


			// Optional.empty());

			return null;
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_GUARDAR, e.getMessage());
		}
	}

	private void cargarArchivo(MultipartFile file, String path, String nombreCompuesto) {
	    try {
	        cargarArchivoConInformacionRenombrado(file, path, nombreCompuesto);
	    } catch (Exception e) {
	        if (e.getMessage() != null && e.getMessage().contains(CONEXION_CERRADO)) {
	            throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
	        }
	        throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
	    }
	}

	
	private void cargarArchivoConInformacionRenombrado(MultipartFile file, String path, String nombre) {
		try {
			nexusImpl.cargarArchivo(file.getInputStream(), path, nombre);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_GUARDAR, e);
		}
	}

	boolean guardarArchivo(Archivo archivo, Long idDictamen) {
		if (archivo.getRuta() != null) {
			archivo.setCargado(true);
		}

		if (archivo instanceof ArchivoOtroDocumentoFaseDictamenModel archivoEspecifico) {
			archivoEspecifico.setIdDictamen(idDictamen);
			archivoOtroDocumentoFaseDictamenRepository.save(archivoEspecifico);
		} else if (archivo instanceof ArchivoOtroDocumentoDictamenModel archivoEspecifico) {
			archivoEspecifico.setIdDictamen(idDictamen);
			archivoOtroDocumentoDictamenRepository.save(archivoEspecifico);
		} else if (archivo instanceof ArchivoPlantillaDictamenModel archivoEspecifico
				&& archivoEspecifico.getId() != null) {
			archivoPlantillaDictamenRepository.save(archivoEspecifico);
		}

		return true;
	}

}
