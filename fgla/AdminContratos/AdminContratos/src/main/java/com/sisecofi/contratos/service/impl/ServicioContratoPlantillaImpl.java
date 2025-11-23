package com.sisecofi.contratos.service.impl;

import com.sisecofi.contratos.microservicios.CatalogoMicroservicio;
import com.sisecofi.contratos.repository.carpetas.CarpetaPlantillaContratoRepository;
import com.sisecofi.contratos.repository.carpetas.CarpetaPlantillaRepository;
import com.sisecofi.contratos.repository.contrato.ContratoPlantillaRepository;
import com.sisecofi.contratos.repository.contrato.ContratoRepository;
import com.sisecofi.contratos.service.ServicioContratoPlantilla;
import com.sisecofi.contratos.service.ServicioPlantilla;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.contratos.util.enums.ErroresEnum;
import com.sisecofi.contratos.util.exception.ContratoException;
import com.sisecofi.libreria.comunes.dto.plantilla.PlantillaDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoPlantillaContratoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.CarpetaPlantillaContratoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.contratos.ContratoPlantilla;
import com.sisecofi.libreria.comunes.model.plantilla.ArchivoPlantillaModel;
import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.util.ConstantesParaRutasSATCloud;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.exception.NexusException;
import com.sisecofi.libreria.comunes.util.sesion.Session;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ServicioContratoPlantillaImpl implements ServicioContratoPlantilla {

	private final CatalogoMicroservicio catalogoMicroservicio;
	private final ServicioPlantilla servicioPlantilla;
	private final ContratoPlantillaRepository contratoPlantillaRepository;
	private final Session session;
	private final ContratoRepository contratoRepository;
	private final CarpetaPlantillaRepository carpetaPlantillaRepository;
	private final NexusImpl nexusImpl;
	private final CarpetaPlantillaContratoRepository carpetaPlantillaContratoRepository;

	@Override
	public List<PlantillaVigenteModel> obtenerPlantillas() {
		List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.FASES_PROYECTOS.getIdCatalogo(), Constantes.ESTATUS_CONTRATO_EJECUCION);
		if (!lista.isEmpty()) {
			return servicioPlantilla.plantillasPorFase(lista.get(0).getPrimaryKey());
		}
		return new ArrayList<>();
	}

	@Override
	@Transactional
	public Boolean asociarPlantillas(ContratoPlantilla asociacion, Long idContrato) {
		Optional<ContratoPlantilla> opcional = contratoPlantillaRepository
				.findByIdContratoAndIdPlantillaVigenteAndEstatusTrue(idContrato, asociacion.getIdPlantillaVigente());
        if(opcional.isPresent()) {
        	return false;
        }	
		asociacion.setEstatus(true);
		asociacion.setIdContrato(idContrato);
		guardarAsociacion(asociacion);
		return true;
	}

	private void crearCarpetas(ContratoPlantilla asociacion, String rutaBase) {
		List<CarpetaPlantillaModel> lista = carpetaPlantillaRepository
				.findByNivelAndPlantillaVigenteModelIdPlantillaVigente(1, asociacion.getIdPlantillaVigente());
		for (CarpetaPlantillaModel car : lista) {
			carpetaPlantillaContratoRepository.save(crearCarpeta(car, asociacion, rutaBase));
		}
	}

	public CarpetaPlantillaContratoModel crearCarpeta(CarpetaPlantillaModel car, ContratoPlantilla asociacion, String rutaBase) {
	    CarpetaPlantillaContratoModel carpeta = new CarpetaPlantillaContratoModel();
	    configurarCarpeta(carpeta, car, rutaBase);
	    carpeta.setContratoPlantilla(asociacion);

	    for (ArchivoPlantillaModel arc : car.getArchivos()) {
	        carpeta.addArchivo(crearArchivo(arc, carpeta.getRuta()));
	    }

	    for (CarpetaPlantillaModel subCarpeta : car.getSubCarpetas()) {
	        carpeta.addSubCarpeta(crearCarpeta(subCarpeta, asociacion, carpeta.getRuta()));
	    }

	    return carpeta;
	}

	public ArchivoPlantillaContratoModel crearArchivo(ArchivoPlantillaModel arc, String rutaActual) {
	    ArchivoPlantillaContratoModel archivo = new ArchivoPlantillaContratoModel();
	    configurarArchivo(archivo, arc, rutaActual);
	    return archivo;
	}

	private void configurarCarpeta(CarpetaPlantillaContratoModel carpeta, CarpetaPlantillaModel car, String rutaBase) {
	    carpeta.setDescripcion(car.getDescripcion());
	    carpeta.setNombre(car.getNombre());
	    carpeta.setObligatorio(car.isObligatorio());
	    carpeta.setEstatus(car.isEstatus());
	    carpeta.setNivel(car.getNivel());
	    carpeta.setOrden(car.getOrden());
	    carpeta.setTipo(car.getTipo());
	    carpeta.setCarpetaBase(car);
	    carpeta.setRuta(rutaBase + "/" + car.getNombre());
	}

	private void configurarArchivo(ArchivoPlantillaContratoModel archivo, ArchivoPlantillaModel arc, String rutaActual) {
	    archivo.setDescripcion(arc.getDescripcion());
	    archivo.setEstatus(arc.isEstatus());
	    archivo.setArchivoBase(arc);
	    archivo.setNombre(arc.getNombre());
	    archivo.setNivel(arc.getNivel());
	    archivo.setNoAplica(false);
	    archivo.setTipo(arc.getTipo());
	    archivo.setObligatorio(arc.isObligatorio());
	    archivo.setCarpeta(rutaActual);
	}


	@Override
	@Transactional
	public Boolean editarAsociacionPlantillas(ContratoPlantilla asociacion) {
			ContratoPlantilla original = contratoPlantillaRepository.findById(asociacion.getIdContratoPlantilla())
					.orElseThrow(() -> new ContratoException(ErroresEnum.ASOCIACION_NO_ENCONTRADA));
			if (!asociacion.getIdPlantillaVigente().equals(original.getIdPlantillaVigente())) {
				deshabilitarCarpetas(original);
			}
			guardarAsociacion(asociacion);
		return true;
	}

	@Override
	@Transactional
	public Boolean eliminarAsociacionPlantillas(List<Long> ids) {
		for (Long id : ids) {
			ContratoPlantilla asociacion = contratoPlantillaRepository.findById(id)
					.orElseThrow(() -> new ContratoException(ErroresEnum.ASOCIACION_NO_ENCONTRADA));
			asociacion.setEstatus(false);
			actualizarUltimaMod(asociacion.getIdContrato());
			contratoPlantillaRepository.save(asociacion);
			deshabilitarCarpetas(asociacion);
		}
		return true;
	}

	private void deshabilitarCarpetas(ContratoPlantilla asociacion) {
	    List<CarpetaPlantillaContratoModel> lista = carpetaPlantillaContratoRepository
	            .findByNivelAndContratoPlantilla(1, asociacion);
	    
	    
	    for (CarpetaPlantillaContratoModel car : lista) {
	    	deshabilitarRecursivo(car);
	        try {
	            nexusImpl.borrarFolder(car.getRuta());
	        } catch (NexusException e) {
	            log.error("Error al borrar carpeta en Nexus:"); 
	        }
	        carpetaPlantillaContratoRepository.save(car);
	    }
	}
	
	private void deshabilitarRecursivo(CarpetaPlantillaContratoModel carpeta) {
	    carpeta.setEstatusCarpeta(false);

	    for (ArchivoPlantillaContratoModel archivo : carpeta.getArchivosActivos()) {
	        archivo.setEstatus(false);

	    }

	    for (CarpetaPlantillaContratoModel hijo : carpeta.getSubCarpetas()) {
	        deshabilitarRecursivo(hijo);
	    }
	}


	@SuppressWarnings("rawtypes")
	private void guardarAsociacion(ContratoPlantilla asociacion) {
		ContratoModel contrato= actualizarUltimaMod(asociacion.getIdContrato());
		PlantillaDto dto= servicioPlantilla.obtenerPlantillaPorId(asociacion.getIdPlantillaVigente());
		String nombreCorto= contrato.getNombreCorto();
		Long idProyecto= contrato.getProyecto().getIdProyecto();
		contratoPlantillaRepository.save(asociacion);
		String rutaBase = ConstantesParaRutasSATCloud.PATH_BASE + "/"+ idProyecto +"/"+ ConstantesParaRutasSATCloud.PATH_BASE_CONTRATOS +"/"
				+ nombreCorto + "/FASES/" + "EJECUCION"+ "/"+dto.getPlantillaVigenteModel().getNombre();
		crearCarpetas(asociacion, rutaBase);
	}

	private Usuario obtenerUsuario() {
	    return session.retornarUsuario()
	                  .orElseThrow(() -> new ContratoException(ErroresEnum.USUARIO_NO_ENCONTRADO));
	}


	private LocalDateTime horaActual() {
		ZoneId zoneId = ZoneId.of("America/Mexico_City");
		ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
		return zonedDateTime.toLocalDateTime();
	}

	private ContratoModel actualizarUltimaMod(Long idContrato) {
		ContratoModel contrato = contratoRepository.findByIdContrato(idContrato)
				.orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));
		contrato.setFechaUltimaModificacion(horaActual());
		contrato.setUltimoModificador(obtenerUsuario().getNombre());
		return contratoRepository.save(contrato);
		
	}

	@Override
	public List<ContratoPlantilla> obtenerAsociaciones(Long idContrato) {
		return contratoPlantillaRepository.findByIdContratoAndEstatusTrue(idContrato);
	}

}