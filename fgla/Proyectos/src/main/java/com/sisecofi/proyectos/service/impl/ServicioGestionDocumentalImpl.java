package com.sisecofi.proyectos.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.transaction.Transactional;

import com.sisecofi.libreria.comunes.dto.CarpetaCompartidaDto;
import com.sisecofi.libreria.comunes.model.catalogo.CatFaseProyecto;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.ArchivoOtroDocumentoFaseModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.ArchivoOtroDocumentoProyectoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.ArchivoPlantillaProyectoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.AsociacionesModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.carpetas.CarpetaPlantillaProyectoModel;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;
import com.sisecofi.libreria.comunes.repository.ArchivoOtroDocumentoFaseRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoOtroDocumentoProyectoRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoPlantillaProyectoRepository;
import com.sisecofi.libreria.comunes.util.ConstantesParaRutasSATCloud;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.libreria.comunes.dto.carpeta.ArchivoCargadoFaseDto;
import com.sisecofi.libreria.comunes.dto.carpeta.CarpetaDtoResponse;
import com.sisecofi.proyectos.dto.ArchivoFaseDto;
import com.sisecofi.proyectos.dto.DescargaSatCloudRequest;
import com.sisecofi.proyectos.dto.GestionDocumentalRequest;
import com.sisecofi.proyectos.microservicio.PapeleraServicoControl;
import com.sisecofi.proyectos.repository.AsociacionRepository;
import com.sisecofi.proyectos.repository.CarpetaPlantillaProyectoRepository;
import com.sisecofi.proyectos.service.PistaService;
import com.sisecofi.proyectos.service.ServicioArchivo;
import com.sisecofi.proyectos.service.ServicioGestionDocumental;
import com.sisecofi.proyectos.service.ServicioProyecto;
import com.sisecofi.proyectos.util.Constantes;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.exception.ProyectoException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("rawtypes")
public class ServicioGestionDocumentalImpl implements ServicioGestionDocumental {

	private final AsociacionRepository asociacionRepository;
	private final ArchivoPlantillaProyectoRepository archivoPlantillaProyectoRepository;
	private final ArchivoOtroDocumentoProyectoRepository archivoOtroDocumentoProyectoRepository;
	private final ArchivoOtroDocumentoFaseRepository archivoOtroDocumentoFaseRepository;
	private final ServicioArchivo servicioArchivo;
	private final CarpetaPlantillaProyectoRepository carpetaPlantillaProyectoRepository;
	private final PapeleraServicoControl papeleraServicoControl;
	private final PistaService pistaService;
	private static final String OTROS_DOC= "Otros Documentos";
	private final ServicioProyecto servicioProyecto;

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<CarpetaDtoResponse> obtenerEstructuraDocumental(Long idProyecto) {
	    try {
		List<AsociacionesModel> asociaciones = obtenerAsociaciones(idProyecto);

	    List<CarpetaDtoResponse> estructura = new ArrayList<>();
	    List<Archivo> archivos = new ArrayList<>();

	    if (!asociaciones.isEmpty()) {
	    	LinkedHashMap<CatFaseProyecto, List<AsociacionesModel>> asociacionesPorFase = asociaciones.stream()
	                .collect(Collectors.groupingBy(
	                    asociacion -> asociacion.getPlantillaVigenteModel().getCatFaseProyecto(),
	                    LinkedHashMap::new, 
	                    Collectors.toList()
	                ));
	    	
	        for (Map.Entry<CatFaseProyecto, List<AsociacionesModel>> entry : asociacionesPorFase.entrySet()) {
	            CatFaseProyecto fase = entry.getKey();
	            List<AsociacionesModel> asociacionesFase = entry.getValue();

	            CarpetaDtoResponse carpetaFaseDto = new CarpetaDtoResponse();
	            carpetaFaseDto.setNombre(fase.getNombre());
	            carpetaFaseDto.setRuta(ConstantesParaRutasSATCloud.PATH_BASE + "/" + idProyecto + Constantes.FASES + fase.getNombre());
	            carpetaFaseDto.setDescripcion(fase.getNombre());

	            List<CarpetaDtoResponse> subRowsFase = new ArrayList<>();

	            Map<PlantillaVigenteModel, List<CarpetaPlantillaProyectoModel>> carpetasPorPlantilla = carpetaPlantillaProyectoRepository
	            	    .findByAsociacionesModelPlantillaVigenteModelCatFaseProyectoAndNivelAndCarpetaBaseEstatusAndAsociacionesModelInAndEstatusCarpetaTrueOrderByIdCarpetaPlantillaProyectoAsc(
	            	            fase, 1, true, asociacionesFase)
	            	    .stream()
	            	    .collect(Collectors.groupingBy(
	            	        carpeta -> carpeta.getAsociacionesModel().getPlantillaVigenteModel(),
	            	        LinkedHashMap::new,
	            	        Collectors.toList()
	            	    ));

	            for (Map.Entry<PlantillaVigenteModel, List<CarpetaPlantillaProyectoModel>> plantillaEntry : carpetasPorPlantilla.entrySet()) {
	                PlantillaVigenteModel plantilla = plantillaEntry.getKey();
	                List<CarpetaPlantillaProyectoModel> carpetas = plantillaEntry.getValue();

	                CarpetaDtoResponse carpetaPlantillaDto = new CarpetaDtoResponse();
	                carpetaPlantillaDto.setNombre(plantilla.getNombre());
	                carpetaPlantillaDto.setRuta(ConstantesParaRutasSATCloud.PATH_BASE + "/" + idProyecto + Constantes.FASES
	                        + fase.getNombre() + "/" + plantilla.getNombre());
	                carpetaPlantillaDto.setDescripcion(plantilla.getNombre());

	                List<CarpetaDtoResponse> subRowsPlantilla = carpetas.stream()
	                        .map(carpeta -> convertirACarpetaDto(carpeta, archivos))
	                        .toList();

	                carpetaPlantillaDto.setSubRows(subRowsPlantilla);
	                subRowsFase.add(carpetaPlantillaDto);
	            }

	            CarpetaDtoResponse otrosDocumentosFase = new CarpetaDtoResponse();
	            otrosDocumentosFase.setNombre(OTROS_DOC);
	            otrosDocumentosFase.setRuta(ConstantesParaRutasSATCloud.PATH_BASE + "/" + idProyecto + Constantes.FASES
	                    + fase.getNombre() + "/" + ConstantesParaRutasSATCloud.PATH_OTROS_DOCUMENTOS_GENERAL);
	            otrosDocumentosFase.setDescripcion(OTROS_DOC);

	            List<Archivo> otrosArchivoFase = archivoOtroDocumentoFaseRepository
	                    .findByIdFaseProyectoAndIdProyectoAndEstatus(fase.getIdFaseProyecto(), idProyecto, true);
	            otrosDocumentosFase.setSubRows(otrosArchivoFase);
	            otrosDocumentosFase.setType("tipoFase");
	            subRowsFase.add(otrosDocumentosFase);

	            carpetaFaseDto.setSubRows(subRowsFase);
	            estructura.add(carpetaFaseDto);
	        }
	    }

	    List<Archivo> otrosArchivosProyecto = archivoOtroDocumentoProyectoRepository
	            .findByIdProyectoAndEstatus(idProyecto, true);
	    CarpetaDtoResponse carpetaOtrosDocumentosProyecto = new CarpetaDtoResponse();
	    carpetaOtrosDocumentosProyecto.setNombre(OTROS_DOC);
	    carpetaOtrosDocumentosProyecto.setType("tipoProyecto");
	    carpetaOtrosDocumentosProyecto.setRuta(ConstantesParaRutasSATCloud.PATH_BASE + "/" + idProyecto + "/FASES/"
	            + ConstantesParaRutasSATCloud.PATH_OTROS_DOCUMENTOS_GENERAL);
	    carpetaOtrosDocumentosProyecto.setSubRows(otrosArchivosProyecto);
	    carpetaOtrosDocumentosProyecto.setDescripcion(OTROS_DOC);
	    estructura.add(carpetaOtrosDocumentosProyecto);

	    String resultado = archivos.stream()
	            .map(archivo -> "|id: " + archivo.getId() + "|descripcion: " + archivo.getDescripcion())
	            .collect(Collectors.joining(" "));



	    // pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


	    // TipoSeccionPista.PROYECTO_DATOS_GESTION_DOCUMENTAL.getIdSeccionPista(),


	    // Constantes.getAtributosProyecto()[0] + idProyecto + resultado, Optional.empty());

	    return estructura;
	    }catch (Exception e) {
	    	throw new ProyectoException(ErroresEnum.CONEXION_PERDIDA, e.getMessage());
		} 
	}
 
	@SuppressWarnings({ "unchecked" })
	private CarpetaDtoResponse convertirACarpetaDto(CarpetaPlantillaProyectoModel carpeta, List<Archivo> archivosList) {
		CarpetaDtoResponse carpetaDto = new CarpetaDtoResponse();
		carpetaDto.setIdCarpetaPlantillaProyecto(carpeta.getIdCarpetaPlantillaProyecto());
		carpetaDto.setNombre(carpeta.getNombre());
		carpetaDto.setRuta(carpeta.getRuta());
		carpetaDto.setDescripcion(carpeta.getNombre());
		carpetaDto.setType("tipoPlantilla");
		List<CarpetaPlantillaProyectoModel> subCarpetas = carpeta.getASubCarpetasActivas();
		List<Object> subCarpetaDtos = new ArrayList<>();


	    if (subCarpetas != null) {
	        subCarpetaDtos = subCarpetas.stream()
	            .map(subCarpeta -> convertirACarpetaDto(subCarpeta, archivosList))
	            .collect(Collectors.toList());
	    }

		List<ArchivoPlantillaProyectoModel> archivos = carpeta.getArchivosActivos();
		if (archivos != null) {
			for (ArchivoPlantillaProyectoModel archivo : archivos) {
				subCarpetaDtos.add(archivo);
				archivosList.add(archivo);
			}
		}

		carpetaDto.setSubRows(subCarpetaDtos);
		return carpetaDto;
	}

	private List<AsociacionesModel> obtenerAsociaciones(Long idProyecto) {
		List<AsociacionesModel> lista= asociacionRepository.findByIdProyectoAndEstatusAsociacionTrueOrderByIdAsociacionAsc(idProyecto);
		lista.sort(Comparator.comparing(
	            a -> a.getPlantillaVigenteModel() != null && a.getPlantillaVigenteModel().getNombre() != null
	                 ? a.getPlantillaVigenteModel().getNombre().toLowerCase()
	                 : "", 
	            String::compareToIgnoreCase
	        ));
		return lista;
	}

	@Override
	@Transactional
	public Boolean guardarTabla(GestionDocumentalRequest archivos) {
		for (ArchivoFaseDto archivo : archivos.getArchivos()) {
			servicioArchivo.cargarArchivoFase(archivo);
		}
		return true;
	}

	@Override
	public CarpetaCompartidaDto descargaSatCloud(DescargaSatCloudRequest descargaSatCloudRequest) {
		String rutaGeneral;
		if (descargaSatCloudRequest.getPath() != null && !descargaSatCloudRequest.getPath().isEmpty()) {
			rutaGeneral = descargaSatCloudRequest.getPath();
		} else {
			rutaGeneral = ConstantesParaRutasSATCloud.PATH_BASE + "/" + descargaSatCloudRequest.getIdProyecto()
					+ "/FASES";
			
		}

		// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),

		// TipoSeccionPista.PROYECTO_DATOS_GESTION_DOCUMENTAL.getIdSeccionPista(),

		// Constantes.getAtributosProyecto()[0] + descargaSatCloudRequest.getIdProyecto(),Optional.empty());
		
		return servicioArchivo.descargarFolderSatCloud(rutaGeneral);	

	}

	@Override
	public String descargaMasiva(DescargaSatCloudRequest descargaSatCloudRequest) {
		String rutaGeneral;
		if (descargaSatCloudRequest.getPath() != null && !descargaSatCloudRequest.getPath().isEmpty()) {
			rutaGeneral = descargaSatCloudRequest.getPath();
		} else {
			rutaGeneral = ConstantesParaRutasSATCloud.PATH_BASE + "/" + descargaSatCloudRequest.getIdProyecto()
					+ "/FASES";
		}

		return servicioArchivo.descargarFolder(rutaGeneral);
	}

	@Override
	@Transactional
	public boolean eliminarArchivos(List<Archivo> eliminados) {
		
		List<Archivo> eliminadosact = new ArrayList<>();
        Long id=0L;
	    Archivo eliminado = eliminados.get(0);
	    
	    if (eliminado.getType().equals("tipoPlantilla")) {
	    	ArchivoPlantillaProyectoModel archivoEspecifico = archivoPlantillaProyectoRepository
                    .findById(eliminado.getId())
                    .orElseThrow(() -> new ProyectoException(ErroresEnum.ERROR_AL_ELIMINAR));
	    	id= archivoEspecifico.getCarpetaPlantillaModel().getAsociacionesModel().getIdProyecto();
	    	eliminadosact.add(archivoEspecifico);
	    }else if (eliminado.getType().equals("tipoFase")) {
	    	ArchivoOtroDocumentoFaseModel archivoEspecifico = (ArchivoOtroDocumentoFaseModel) eliminado;
	    	id= archivoEspecifico.getIdProyecto();
	    	eliminadosact.add(archivoEspecifico);
	    }else if (eliminado.getType().equals("tipoProyecto")) {
	    	ArchivoOtroDocumentoProyectoModel archivoEspecifico = (ArchivoOtroDocumentoProyectoModel) eliminado;
	    	id= archivoEspecifico.getIdProyectoAux();
	    	eliminadosact.add(archivoEspecifico);
	    }

		papeleraServicoControl.enviarPapelera(eliminadosact);
		
		String path = eliminados.get(0).getRuta();
		int lastSlashIndex = path.lastIndexOf("/");
		
		String nombreArchivo= "|"+path.substring(lastSlashIndex + 1);
		
		if (id>0) {
			servicioProyecto.actualizarUltimaModificacion(id);
		}
		

		
		// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.BORRA_REGISTRO.getId(),

		
		// TipoSeccionPista.PROYECTO_DATOS_GESTION_DOCUMENTAL.getIdSeccionPista(),

		
		// Constantes.getAtributosProyecto()[0] + id + nombreArchivo,Optional.empty());
		return true;
	}

	@Override
	@Transactional
	public Boolean cargarArchivoFaseIndividual(ArchivoCargadoFaseDto dto) {
		Integer idArch = null;
		try {
		    idArch = Integer.parseInt(dto.getIdArchivo());
		} catch (NumberFormatException e) {
		    log.warn("El valor de idArchivo no es un número válido: {}", dto.getIdArchivo(), e);
		    idArch = null; 
		}
		
		Archivo archivoEspecifico = obtenerArchivoPorTipo(dto.getType(), idArch, dto.isNoAplica());
		actualizarArchivoConDatos(archivoEspecifico, dto.isNoAplica(), dto.getJustificacion(),
				dto.getCarpeta(), dto.getDescripcion());

		String nombreCompuesto = generarNombreCompuesto(archivoEspecifico, dto.getFile(), dto.getType());

		ArchivoFaseDto archivoDto = new ArchivoFaseDto();
		archivoDto.setArchivo(archivoEspecifico);
		archivoDto.setNombreFile(nombreCompuesto);
		archivoDto.setIdProyecto(dto.getIdProyecto());
		archivoDto.setNombreFase(dto.getNombreFase());
		archivoDto.setFile(dto.getFile());

		servicioArchivo.cargarArchivoFase(archivoDto);
		return true;
	}

	private Archivo obtenerArchivoPorTipo(String type, Integer idArchivo, boolean aplica) {
		switch (type) {
		case Constantes.TIPO_PLANTILLA:
			ArchivoPlantillaProyectoModel arc= archivoPlantillaProyectoRepository.findById(idArchivo)
					.orElseThrow(() -> new ProyectoException(ErroresEnum.ARCHIVO_NO_ENCONTRADO));
			arc.actualizaObligatorio(aplica, arc.getArchivoBase().isObligatorio());
			return arc;
		case Constantes.TIPO_FASE:
			return idArchivo == null ? new ArchivoOtroDocumentoFaseModel()
					: archivoOtroDocumentoFaseRepository.findById(idArchivo)
							.orElseGet(ArchivoOtroDocumentoFaseModel::new);
		case Constantes.TIPO_PROYECTO:
			return idArchivo == null ? new ArchivoOtroDocumentoProyectoModel()
					: archivoOtroDocumentoProyectoRepository.findById(idArchivo)
							.orElseGet(ArchivoOtroDocumentoProyectoModel::new);
		default:
			throw new ProyectoException(ErroresEnum.ARCHIVO_NO_ENCONTRADO);
		}
	}

	private void actualizarArchivoConDatos(Archivo archivo, boolean noAplica, String justificacion,
			String carpeta, String descripcion) {
		
		archivo.setNoAplica(noAplica);
		archivo.setJustificacion(justificacion);
		archivo.setFechaModificacion(horaActual());

		if (archivo instanceof ArchivoPlantillaProyectoModel archivoEspecifico) {
			archivoEspecifico.setCarpeta(carpeta);
		}

		if (archivo instanceof ArchivoOtroDocumentoFaseModel || archivo instanceof ArchivoOtroDocumentoProyectoModel) {
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
					ArchivoPlantillaProyectoModel archivoPlantilla = (ArchivoPlantillaProyectoModel) archivo;
					return archivoPlantilla.getNombre() + "_" + archivoPlantilla.getCarpetaPlantillaModel()
							.getAsociacionesModel().getProyectoModel().getNombreCorto() + extension;
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

}
