package com.sisecofi.contratos.service.convenio_modificatorio.impl;

import com.sisecofi.contratos.repository.carpetas.CarpetaPlantillaConvenioRepository;
import com.sisecofi.contratos.repository.carpetas.CarpetaPlantillaRepository;
import com.sisecofi.contratos.repository.contrato.ConvenioModificatorioRepository;
import com.sisecofi.contratos.repository.convenio_modificatorio.ConvenioPlantillaRepository;
import com.sisecofi.contratos.service.PistaService;
import com.sisecofi.contratos.service.ServicioPlantilla;
import com.sisecofi.contratos.service.convenio_modificatorio.ServicioConvenioPlantilla;
import com.sisecofi.contratos.service.impl.NexusImpl;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.contratos.util.enums.ErroresEnum;
import com.sisecofi.contratos.util.exception.ContratoException;
import com.sisecofi.libreria.comunes.dto.plantilla.PlantillaDto;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoPlantillaConvenioModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.CarpetaPlantillaConvenioModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.contratos.ConvenioPlantilla;
import com.sisecofi.libreria.comunes.model.plantilla.ArchivoPlantillaModel;
import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;
import com.sisecofi.libreria.comunes.util.ConstantesParaRutasSATCloud;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.libreria.comunes.util.exception.NexusException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ServicioConvenioPlantillaImpl implements ServicioConvenioPlantilla {

	private final ConvenioPlantillaRepository convenioPlantillaRepository;
	private final CarpetaPlantillaRepository carpetaPlantillaRepository;
	private final NexusImpl nexusImpl;
	private final ConvenioModificatorioRepository convenioModificatorioRepository;
	private final CarpetaPlantillaConvenioRepository carpetaPlantillaConvenioRepository;
	private final PistaService pistaService;
	private final ServicioPlantilla servicioPlantilla;

	@Override
	@Transactional
	public Boolean asociarPlantillas(ConvenioPlantilla asociacion, Long idConvenio) {
		Optional<ConvenioPlantilla> opcional = convenioPlantillaRepository
				.findByIdConvenioAndIdPlantillaVigenteAndEstatusTrue(idConvenio, asociacion.getIdPlantillaVigente());

		if (opcional.isPresent()) {
			return true;
		}

		try {
			asociacion.setEstatus(true);
			asociacion.setIdConvenio(idConvenio);
			guardarAsociacion(asociacion);

			@SuppressWarnings("rawtypes")
			PlantillaDto dto = servicioPlantilla.obtenerPlantillaPorId(asociacion.getIdPlantillaVigente());

			pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),
					TipoSeccionPista.CONVENIO_MODIFICATORIO_ASIGNAR_PLANTILLA.getIdSeccionPista(),
					Constantes.getAtributosPlantillaConvenioModificatorio()[0] + idConvenio + "|"
							+ Constantes.getAtributosPlantillaConvenioModificatorio()[1]
							+ dto.getPlantillaVigenteModel().getNombre() + "|Id asociacion: "
							+ asociacion.getIdConvenioPlantilla() + "|Estatus: true",
					Optional.empty());
			return true;

		} catch (DataAccessException e) {
			log.error("Error de acceso a datos al asociar la plantilla");
			return false;
		} catch (IllegalArgumentException e) {
			log.warn("Argumento inválido proporcionado al asociar la plantilla");
			return false;
		}
	}

	private void crearCarpetas(ConvenioPlantilla asociacion, String rutaBase) {
		List<CarpetaPlantillaModel> lista = carpetaPlantillaRepository
				.findByNivelAndPlantillaVigenteModelIdPlantillaVigente(1, asociacion.getIdPlantillaVigente());

		List<CarpetaPlantillaConvenioModel> carpetasAInsertar = new ArrayList<>();
		for (CarpetaPlantillaModel car : lista) {
			carpetasAInsertar.add(crearCarpeta(car, asociacion, rutaBase));
		}

		carpetaPlantillaConvenioRepository.saveAll(carpetasAInsertar);
	}

	public CarpetaPlantillaConvenioModel crearCarpeta(CarpetaPlantillaModel car, ConvenioPlantilla asociacion,
			String rutaBase) {
		CarpetaPlantillaConvenioModel carpeta = new CarpetaPlantillaConvenioModel();
		carpeta.setConvenioPlantilla(asociacion);
		carpeta.setDescripcion(car.getDescripcion());
		carpeta.setNombre(car.getNombre());
		carpeta.setObligatorio(car.isObligatorio());
		carpeta.setEstatus(car.isEstatus());
		carpeta.setNivel(car.getNivel());
		carpeta.setOrden(car.getOrden());
		carpeta.setTipo(car.getTipo());
		carpeta.setCarpetaBase(car);
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

	public ArchivoPlantillaConvenioModel crearArchivo(ArchivoPlantillaModel arc, String rutaActual) {
		ArchivoPlantillaConvenioModel archivo = new ArchivoPlantillaConvenioModel();
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
	@Transactional
	public Boolean editarAsociacionPlantillas(ConvenioPlantilla asociacion) {
		ConvenioPlantilla original = convenioPlantillaRepository.findById(asociacion.getIdConvenioPlantilla())
				.orElseThrow(() -> new ContratoException(ErroresEnum.ASOCIACION_NO_ENCONTRADA));

		if (!asociacion.getIdPlantillaVigente().equals(original.getIdPlantillaVigente())) {
			deshabilitarCarpetas(original);
		}
		@SuppressWarnings("rawtypes")
		PlantillaDto dto = servicioPlantilla.obtenerPlantillaPorId(asociacion.getIdPlantillaVigente());
		guardarAsociacion(asociacion);

		pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),
				TipoSeccionPista.CONVENIO_MODIFICATORIO_ASIGNAR_PLANTILLA.getIdSeccionPista(),
				Constantes.getAtributosPlantillaConvenioModificatorio()[0] + original.getIdConvenio() + "|"
						+ Constantes.getAtributosPlantillaConvenioModificatorio()[1]
						+ dto.getPlantillaVigenteModel().getNombre() + "|Estatus: true",
				Optional.empty());
		return true;
	}

	@Override
	@Transactional
	public Boolean eliminarAsociacionPlantillas(List<Long> ids) {
		for (Long id : ids) {
			ConvenioPlantilla asociacion = convenioPlantillaRepository.findById(id)
					.orElseThrow(() -> new ContratoException(ErroresEnum.ASOCIACION_NO_ENCONTRADA));
			asociacion.setEstatus(false);
			convenioPlantillaRepository.save(asociacion);
			@SuppressWarnings("rawtypes")
			PlantillaDto dto = servicioPlantilla.obtenerPlantillaPorId(asociacion.getIdPlantillaVigente());
			deshabilitarCarpetas(asociacion);
			pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.BORRA_REGISTRO.getId(),
					TipoSeccionPista.CONVENIO_MODIFICATORIO_ASIGNAR_PLANTILLA.getIdSeccionPista(),
					Constantes.getAtributosPlantillaConvenioModificatorio()[0] + asociacion.getIdConvenio() + "|"
							+ Constantes.getAtributosPlantillaConvenioModificatorio()[1]
							+ dto.getPlantillaVigenteModel().getNombre() + "|Estatus: false",
					Optional.empty());
		}
		return true;
	}

	private void deshabilitarCarpetas(ConvenioPlantilla asociacion) {
		List<CarpetaPlantillaConvenioModel> lista = carpetaPlantillaConvenioRepository
				.findByNivelAndConvenioPlantilla(1, asociacion);
		for (CarpetaPlantillaConvenioModel car : lista) {
			deshabilitarRecursivo(car);
			try {
				nexusImpl.borrarFolder(car.getRuta());
			} catch (NexusException e) {
				throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
			}
			carpetaPlantillaConvenioRepository.save(car);
		}
	}
	
	private void deshabilitarRecursivo(CarpetaPlantillaConvenioModel carpeta) {
	    carpeta.setEstatusCarpeta(false);

	    for (ArchivoPlantillaConvenioModel archivo : carpeta.getArchivosActivos()) {
	        archivo.setEstatus(false);

	    }

	    for (CarpetaPlantillaConvenioModel hijo : carpeta.getSubCarpetas()) {
	        deshabilitarRecursivo(hijo);
	    }
	}

	@SuppressWarnings("rawtypes")
	private void guardarAsociacion(ConvenioPlantilla asociacion) {
		convenioPlantillaRepository.save(asociacion);
		PlantillaDto dto = servicioPlantilla.obtenerPlantillaPorId(asociacion.getIdPlantillaVigente());
		ConvenioModificatorioModel convenio = convenioModificatorioRepository
				.findByIdConvenioModificatorioAndEstatusTrue(asociacion.getIdConvenio())
				.orElseThrow(() -> new ContratoException(ErroresEnum.ASOCIACION_NO_ENCONTRADA));
		String nombreCorto = convenio.getContratoModel().getNombreCorto();
		Long idProyecto = convenio.getContratoModel().getProyecto().getIdProyecto();
		String rutaBase = ConstantesParaRutasSATCloud.PATH_BASE + "/" + idProyecto + "/"
				+ ConstantesParaRutasSATCloud.PATH_BASE_CONTRATOS + "/" + nombreCorto + "/"
				+ ConstantesParaRutasSATCloud.PATH_BASE_CONVENIOS + "/" + convenio.getNumeroConvenio() + "/"
				+ "EJECUCION" + "/" + dto.getPlantillaVigenteModel().getNombre();
		crearCarpetas(asociacion, rutaBase);
	}

	@Override
	public List<ConvenioPlantilla> obtenerAsociaciones(Long idConvenio) {
		return convenioPlantillaRepository.findByidConvenioAndEstatusTrue(idConvenio);
	}

}