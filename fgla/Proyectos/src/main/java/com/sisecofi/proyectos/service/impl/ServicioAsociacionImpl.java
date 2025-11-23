package com.sisecofi.proyectos.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.sisecofi.libreria.comunes.util.ConstantesParaRutasSATCloud;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.proyectos.dto.AsociacionGuardarRequest;
import com.sisecofi.proyectos.dto.AsociacionResponse;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import com.sisecofi.libreria.comunes.repository.ArchivoPlantillaProyectoRepository;
import com.sisecofi.proyectos.repository.AsociacionRepository;
import com.sisecofi.proyectos.repository.CarpetaPlantillaProyectoRepository;
import com.sisecofi.proyectos.repository.CarpetaPlantillaRepository;
import com.sisecofi.proyectos.repository.PlantillaVigenteRopository;
import com.sisecofi.proyectos.repository.ProyectoRepository;
import com.sisecofi.proyectos.service.PistaService;
import com.sisecofi.proyectos.service.ServicioAsociacion;
import com.sisecofi.proyectos.service.ServicioPlantilla;
import com.sisecofi.proyectos.service.ServicioProyecto;
import com.sisecofi.proyectos.util.Constantes;
import com.sisecofi.proyectos.util.consumer.ReporteAsociacionConsumer;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.exception.ProyectoException;
import com.sisecofi.libreria.comunes.dto.plantilla.PlantillaDto;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.ArchivoPlantillaProyectoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.AsociacionesModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.carpetas.CarpetaPlantillaProyectoModel;
import com.sisecofi.libreria.comunes.model.plantilla.ArchivoPlantillaModel;
import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("rawtypes")
public class ServicioAsociacionImpl implements ServicioAsociacion {

	private final AsociacionRepository asociacionRepository;
	private final ProyectoRepository proyectoRepository;
	private final ServicioPlantilla servicioPlantilla;
	private final PistaService pistaService;
	private final ReporteAsociacionConsumer consumer;
	private final ArchivoPlantillaProyectoRepository archivoPlantillaProyectoRepository;
	private final CarpetaPlantillaRepository carpetaPlantillaRepository;
	private final CarpetaPlantillaProyectoRepository carpetaPlantillaProyectoRepository;
	private final ServicioProyecto servicioProyecto;
	private final PlantillaVigenteRopository plantillaVigenteRopository;


	@Transactional
	@Override
	public boolean crearAsociacion(AsociacionesModel asociacion, Long idProyecto) {
	    PlantillaVigenteModel plantillaDto = plantillaVigenteRopository
	            .findByIdPlantillaVigente(asociacion.getIdPlantillaVigente());

	    asociacion.setEstatusAsociacion(true);
	    asociacion.setIdProyecto(idProyecto);
	    asociacionRepository.save(asociacion);

	    // Generar ruta base para carpetas
	    String rutaBase = String.format("%s/%d/FASES/%s/%s",
	            ConstantesParaRutasSATCloud.PATH_BASE, idProyecto,
	            plantillaDto.getCatFaseProyecto().getNombre(), plantillaDto.getNombre());

	    // Crear carpetas asociadas
	    crearCarpetas(asociacion.getIdPlantillaVigente(), asociacion, rutaBase);

	    // Actualizar última modificación del proyecto y registrar en pista
	    servicioProyecto.actualizarUltimaModificacion(idProyecto);
	    
	    
	    long existe = asociacionRepository.countByIdProyectoAndIdPlantillaVigenteAndEstatusAsociacion(
	            idProyecto, asociacion.getIdPlantillaVigente(), true);
	    
	    long existeCerrado = asociacionRepository.countByIdProyectoAndEstatusAsociacionAndPlantillaVigenteModelCatFaseProyectoNombre(
	            idProyecto, true, "Cerrado");
	    
	    if (existe>1 || (existeCerrado>1 && plantillaDto.getCatFaseProyecto().getNombre().equals("Cerrado"))) {
	        throw new ProyectoException(ErroresEnum.PLANTILLA_YA_ASOCIADA);
	    }
	   
	    pistaService.guardarPista(
	            ModuloPista.PROYECTOS.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),
	            TipoSeccionPista.PROYECTO_ASOCIAR_FASE.getIdSeccionPista(),
	            String.format("%s%s|%s%d|%s%s",
	                    Constantes.getAtributosAsociacion()[0], asociacion.getIdAsociacion(),
	                    Constantes.getAtributosAsociacion()[1], idProyecto,
	                    Constantes.getAtributosAsociacion()[2], plantillaDto.getNombre()),
	            Optional.empty());

	    return true;
	}


	public void crearCarpetas(Integer idPlantilla, AsociacionesModel asociacion, String rutaBase) {
		List<CarpetaPlantillaModel> lista = carpetaPlantillaRepository
				.findByNivelAndPlantillaVigenteModelIdPlantillaVigente(1, idPlantilla);
		for (CarpetaPlantillaModel car : lista) {
			carpetaPlantillaProyectoRepository.save(crearCarpeta(car, asociacion, rutaBase));
		}
	}
	

	public CarpetaPlantillaProyectoModel crearCarpeta(CarpetaPlantillaModel car, AsociacionesModel asociacion,
			String rutaBase) {
		CarpetaPlantillaProyectoModel carpeta = new CarpetaPlantillaProyectoModel();
		carpeta.setAsociacionesModel(asociacion);
		carpeta.setDescripcion(car.getDescripcion());
		carpeta.setNombre(car.getNombre());
		carpeta.setObligatorio(car.isObligatorio());
		carpeta.setEstatus(car.isEstatus());
		carpeta.setNivel(car.getNivel());
		carpeta.setOrden(car.getOrden());
		carpeta.setTipo(car.getTipo());
		carpeta.setCarpetaBase(car);
		carpeta.setEstatusCarpeta(true);
		String rutaActual = rutaBase + "/" + car.getNombre();
		carpeta.setRuta(rutaActual);
		for (ArchivoPlantillaModel arc : car.getArchivos()) {
			carpeta.addArchivo(crearArchivo(arc, rutaActual));
		}

		for (CarpetaPlantillaModel carp : car.getSubCarpetas()) {
			carpeta.addSubCarpeta(crearCarpeta(carp, asociacion, rutaActual));
		}

		return carpeta;
	}

	public ArchivoPlantillaProyectoModel crearArchivo(ArchivoPlantillaModel arc, String rutaActual) {
		ArchivoPlantillaProyectoModel archivo = new ArchivoPlantillaProyectoModel();
		archivo.setDescripcion(arc.getDescripcion());
		archivo.setEstatus(arc.isEstatus());
		archivo.setArchivoBase(arc);
		archivo.setNombre(arc.getNombre());
		archivo.setNivel(arc.getNivel());
		archivo.setNoAplica(false);
		archivo.setTipo(arc.getTipo());
		archivo.setObligatorio(arc.isObligatorio());
		archivo.setCarpeta(rutaActual);
		return archivo;
	}

	@Override
	public List<AsociacionResponse> obtenerAsociaciones(Long idProyecto) {
		ProyectoModel proyecto = proyectoRepository.findByIdProyectoAndEstatus(idProyecto, true)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.PROYECTO_NO_ENCONTRADO));
		List<AsociacionesModel> listaAsociaciones = asociacionRepository
				.findByIdProyectoAndEstatusAsociacionOrderByOrdenAsc(proyecto.getIdProyecto(), true);
		listaAsociaciones.sort(Comparator.comparing(
	            a -> a.getPlantillaVigenteModel() != null && a.getPlantillaVigenteModel().getNombre() != null
	                 ? a.getPlantillaVigenteModel().getNombre().toLowerCase()
	                 : "", 
	            String::compareToIgnoreCase
	        ));
		List<AsociacionResponse> listaResponse = new ArrayList<>();
		for (AsociacionesModel asociacion : listaAsociaciones) {
			actualizarCargado(asociacion.getIdAsociacion());
			listaResponse.add(agruparRespuesta(asociacion));
		}
		return listaResponse;
	}

	private AsociacionResponse agruparRespuesta(AsociacionesModel asociacion) {
		AsociacionResponse response = new AsociacionResponse();
		if (asociacion != null) {
			PlantillaVigenteModel plantillaDto = plantillaVigenteRopository.findByIdPlantillaVigente(asociacion.getIdPlantillaVigente());
			response.setIdAsociacion(asociacion.getIdAsociacion());
			response.setPlantilla(plantillaDto);
			response.setFase(plantillaDto.getCatFaseProyecto());
			response.setFechaAsignacion(asociacion.getFechaAsignacion());
			response.setPlantillasOpciones(plantillaVigenteRopository.findByIdFaseProyectoAndEstado(plantillaDto.getIdFaseProyecto(), true));
			response.setCargado(asociacion.isCargado());
			response.setOrden(asociacion.getOrden());
			}
		return response;
	}

	private boolean validarFecha(LocalDate fecha) {
		if (fecha.isAfter(LocalDate.now())) {
			throw new ProyectoException(ErroresEnum.ERROR_FECHA_ASIGNACION);
		}
		return true;
	}

	private boolean validarCargado(AsociacionesModel asociacion) {
		if (asociacion.isCargado()) {
			throw new ProyectoException(ErroresEnum.ERROR_ARCHIVOS_CARGADOS);
		}
		return true;
	}

	@Override
	@Transactional
	public boolean modificarAsociacion(AsociacionesModel asociacion) {
		AsociacionesModel asociacionOptional = asociacionRepository
				.findByIdAsociacionAndEstatusAsociacion(asociacion.getIdAsociacion(), true)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.ASOCIACION_NO_ENCONTRADA));
		validarFecha(asociacion.getFechaAsignacion());
		validarCargado(asociacionOptional);
		asociacion.setIdAsociacion(asociacionOptional.getIdAsociacion());
		asociacion.setIdProyecto(asociacionOptional.getIdProyecto());
		asociacion.setEstatusAsociacion(asociacionOptional.isEstatusAsociacion());
		Optional<AsociacionesModel> opcional = asociacionRepository
				.findByIdProyectoAndIdPlantillaVigenteAndEstatusAsociacion(asociacion.getIdProyecto(),
						asociacion.getIdPlantillaVigente(), true);
		if (opcional.isPresent() && !opcional.get().getIdAsociacion().equals(asociacion.getIdAsociacion())) {
			throw new ProyectoException(ErroresEnum.PLANTILLA_YA_ASOCIADA);
		}
		
		asociacionRepository.save(asociacion);
		deshabilitarCarpetas(asociacion);

		PlantillaDto plantillaDto = servicioPlantilla.obtenerPlantillaPorId(asociacion.getIdPlantillaVigente());

		String rutaBase = ConstantesParaRutasSATCloud.PATH_BASE + "/" + asociacion.getIdProyecto() + "/"
				+ plantillaDto.getPlantillaVigenteModel().getCatFaseProyecto().getNombre()+"/"+plantillaDto.getPlantillaVigenteModel().getNombre();
		crearCarpetas(asociacion.getIdPlantillaVigente(), asociacion, rutaBase);

		servicioProyecto.actualizarUltimaModificacion(asociacion.getIdProyecto());
		
		pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),
				TipoSeccionPista.PROYECTO_ASOCIAR_FASE.getIdSeccionPista(),
				Constantes.getAtributosAsociacion()[0] + asociacion.getIdAsociacion() + "|"
						+ Constantes.getAtributosAsociacion()[1] + asociacion.getIdProyecto() + "|"
						+ Constantes.getAtributosAsociacion()[2] + plantillaDto.getPlantillaVigenteModel().getNombre(),
				Optional.empty());
		return true;
	}

	private void deshabilitarCarpetas(AsociacionesModel asociacion) {
	    List<CarpetaPlantillaProyectoModel> raices =
	            carpetaPlantillaProyectoRepository.findByNivelAndAsociacionesModel(1, asociacion);

	    for (CarpetaPlantillaProyectoModel raiz : raices) {
	        deshabilitarRecursivo(raiz);
	    }
	}
	
	private void deshabilitarRecursivo(CarpetaPlantillaProyectoModel carpeta) {
	    carpeta.setEstatusCarpeta(false);

	    for (ArchivoPlantillaProyectoModel archivo : carpeta.getArchivos()) {
	        archivo.setEstatus(false);

	    }

	    for (CarpetaPlantillaProyectoModel hijo : carpeta.getSubCarpetas()) {
	        deshabilitarRecursivo(hijo);
	    }
	}

	@Override
	@Transactional
	public boolean eliminarAsociacion(Long idAsociacion) {
		AsociacionesModel asociacionOptional = asociacionRepository
				.findByIdAsociacionAndEstatusAsociacion(idAsociacion, true)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.ASOCIACION_NO_ENCONTRADA));
		validarCargado(asociacionOptional);
		asociacionOptional.setEstatusAsociacion(false);
		deshabilitarCarpetas(asociacionOptional);
		asociacionRepository.save(asociacionOptional);
		PlantillaDto plantillaDto = servicioPlantilla.obtenerPlantillaPorId(asociacionOptional.getIdPlantillaVigente());
		servicioProyecto.actualizarUltimaModificacion(asociacionOptional.getIdProyecto());
		
		pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.BORRA_REGISTRO.getId(),
				TipoSeccionPista.PROYECTO_ASOCIAR_FASE.getIdSeccionPista(),
				Constantes.getAtributosAsociacion()[0] + asociacionOptional.getIdAsociacion() + "|"
						+ Constantes.getAtributosAsociacion()[1] + asociacionOptional.getIdProyecto() + "|"
						+ Constantes.getAtributosAsociacion()[2] + plantillaDto.getPlantillaVigenteModel().getNombre(),
				Optional.empty());
		return true;
	}

	@Override
	@Transactional
	public List<AsociacionResponse> guardarAsociaciones(AsociacionGuardarRequest request, Long idProyecto) {
		if (request.getAsociacionesEliminadas() != null && !request.getAsociacionesEliminadas().isEmpty()) {
			for (Long id : request.getAsociacionesEliminadas()) {
				eliminarAsociacion(id);
			}
		}
		if (request.getAsociacionesNuevas() != null && !request.getAsociacionesNuevas().isEmpty()) {
			for (AsociacionesModel asociacion : request.getAsociacionesNuevas()) {
				crearAsociacion(asociacion, idProyecto);
			}
		}
		if (request.getAsociacionesModificadas() != null && !request.getAsociacionesModificadas().isEmpty()) {
			for (AsociacionesModel asociacion : request.getAsociacionesModificadas()) {
				modificarAsociacion(asociacion);
			}
		}
		servicioProyecto.actualizarUltimaModificacion(idProyecto);
		return obtenerAsociaciones(idProyecto);
	}

	@Override
	public String generarReporteAsociacion(Long idProyecto) {
		List<AsociacionResponse> lista = obtenerAsociaciones(idProyecto);
		try {
			StringBuilder nombresPlantilla = new StringBuilder();

			for (AsociacionResponse asociacion : lista) {
			    if (!nombresPlantilla.isEmpty()) {
			        nombresPlantilla.append("|");
			    }
			    nombresPlantilla.append(asociacion.getPlantilla().getNombre());
			}

			String resultado = nombresPlantilla.toString();
			
			consumer.inializar("Asociaciones del proyecto con id: " + idProyecto);
			consumer.agregarCabeceras(Constantes.TITULOS_REPORTE_ASOCIACION);
			lista.stream().forEach(consumer);
			byte[] reporte = consumer.cerrarBytes();
			
			pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),
					TipoSeccionPista.PROYECTO_ASOCIAR_FASE.getIdSeccionPista(),
					Constantes.getAtributosAsociacion()[1] + idProyecto + "|"
							+ Constantes.getAtributosAsociacion()[2] + resultado,
					Optional.empty());
			
			return Base64.getEncoder().encodeToString(reporte);
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}
	}

	private void actualizarCargado(Long idAsociacion) {
		long count = archivoPlantillaProyectoRepository.countByCargadoAndIdAsociacion(true, idAsociacion);
		AsociacionesModel asociacionOptional = asociacionRepository
				.findByIdAsociacionAndEstatusAsociacion(idAsociacion, true)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.ASOCIACION_NO_ENCONTRADA));
		asociacionOptional.setCargado(count > 0);
		asociacionRepository.save(asociacionOptional);
	}

	@Override
	public Set<Integer> plantillasOcupadas() {
		List<AsociacionesModel> lista = asociacionRepository.findByEstatusAsociacionTrue();
		Set<Integer> listaIds = new LinkedHashSet<>();
		for (AsociacionesModel asociacion : lista) {
			listaIds.add(asociacion.getIdPlantillaVigente());
		}
		return listaIds;
	}

}
