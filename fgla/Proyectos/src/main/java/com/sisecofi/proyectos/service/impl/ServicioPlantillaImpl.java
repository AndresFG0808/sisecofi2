package com.sisecofi.proyectos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sisecofi.libreria.comunes.dto.plantilla.PlantillaCarpetasDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoOtrosDocumentosComiteModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoPlantillaComiteModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.AsociasionComitePlantillaModel;
import com.sisecofi.libreria.comunes.model.plantilla.ArchivoPlantillaModel;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.proyectos.microservicio.CatalogoMicroservicio;

import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.dto.plantilla.PlantillaDto;
import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;
import com.sisecofi.libreria.comunes.repository.ArchivoOtrosDocumentosComiteRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoPlantillaComiteRepository;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.proyectos.dto.PlantillaVigenteModelDto;
import com.sisecofi.proyectos.microservicio.PlantillaDocMicroservicio;
import com.sisecofi.proyectos.repository.AsociacionComiteRepository;
import com.sisecofi.proyectos.service.PistaService;
import com.sisecofi.proyectos.service.ServicioPlantilla;
import com.sisecofi.proyectos.util.Constantes;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.exception.ProyectoException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ServicioPlantillaImpl implements ServicioPlantilla {

	private final PlantillaDocMicroservicio plantillaDocMicroservicio;
	private final PistaService pistaService;
	private final ArchivoPlantillaComiteRepository archivoPlantillaComiteRepository;
	private final AsociacionComiteRepository asociacionComiteRepository;
	private final CatalogoMicroservicio catalogoMicroservicio;
	private final ArchivoOtrosDocumentosComiteRepository archivoOtrosDocumentosComiteRepository;

	@SuppressWarnings("rawtypes")
	@Override
	public PlantillaDto obtenerPlantillaPorId(Integer idPlantilla) {

		try {
			pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),
					TipoSeccionPista.PROYECTO_DATOS_GENERALES.getIdSeccionPista(),
					TipoMovPista.CONSULTA_REGISTRO.getClave(),Optional.empty());

			return plantillaDocMicroservicio.obtenerPlantilla(idPlantilla);

		} catch (Exception e) {
			log.info(Constantes.ERROR_AL_CONSULTAR_EXEPCION);
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public List<PlantillaVigenteModel> obtenerPlantillas() {

		List<PlantillaVigenteModel> plantillaVigenteComite = obtenerPlantillasComite();

		if (plantillaVigenteComite.isEmpty()) {
			log.info(Constantes.ERROR_AL_CONSULTAR_EXEPCION, plantillaVigenteComite);
			throw new ProyectoException(ErroresEnum.PLANTILLAS_NO_ENCONTRADAS);
		}

		try {
			pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),
					TipoSeccionPista.PROYECTO_DATOS_GENERALES.getIdSeccionPista(),
					TipoMovPista.CONSULTA_REGISTRO.getClave(),Optional.empty());

			return plantillaVigenteComite;

		} catch (Exception e) {
			log.info(Constantes.ERROR_AL_CONSULTAR_EXEPCION);
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	public List<PlantillaVigenteModel> obtenerPlantillasComite() {
		try {
			List<BaseCatalogoModel> fasesComite = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(CatalogosComunes.FASES_PROYECTOS.getIdCatalogo(), Constantes.VALIDACION_COMITE);
			BaseCatalogoModel catalogoComite = fasesComite.get(0);
			Integer idPlantilla = catalogoComite.getPrimaryKey();

			return plantillaDocMicroservicio.plantillasFase(idPlantilla);

		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}

	@Override
	public List<PlantillaVigenteModel> plantillasPorFase(Integer idFaseProyecto) {
		try {
			return plantillaDocMicroservicio.plantillasFase(idFaseProyecto);
		} catch (Exception e) {
			log.info(Constantes.ERROR_AL_CONSULTAR_EXEPCION);
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PlantillaVigenteModelDto obtenerEstructura(Integer idComiteProyecto) {

		AsociasionComitePlantillaModel asociasionComitePlantilla = asociacionComiteRepository
				.findByIdComiteProyectoAndEstatusTrue(idComiteProyecto).orElse(null);

		if (asociasionComitePlantilla == null){
			throw new ProyectoException(ErroresEnum.COMITE_PROYECTO_NO_ENCONTRADO);
		}

		try {
			PlantillaVigenteModelDto plantillaVigenteModelDto = new PlantillaVigenteModelDto();

			Integer idPlantillaVigente = asociasionComitePlantilla.getIdPlantillaVigente();
			Integer idAsociacion = asociasionComitePlantilla.getIdAsociacionComitePlantilla();

			PlantillaCarpetasDto plantilla = plantillaDocMicroservicio.obtenerPlantillaCarpeta(idPlantillaVigente);

			List<CarpetaPlantillaModel> carpetas = plantilla.getCarpetas();

			List<ArchivoPlantillaComiteModel> archivios = new ArrayList<>();
			List<ArchivoOtrosDocumentosComiteModel> archivoOtrosDocumentos = new ArrayList<>();

			List<ArchivoOtrosDocumentosComiteModel> otrosDocumentosExterno = archivoOtrosDocumentosComiteRepository.findByIdComiteProyectoAndEstatusTrueAndIdCarpetaPlantillaNull(idComiteProyecto);

			for (CarpetaPlantillaModel carpeta: carpetas){
				Integer idCarpetaPlantilla = carpeta.getIdCarpetaPlantilla();
				List<ArchivoPlantillaModel> archivoPlantilla = carpeta.getArchivos();
				List<ArchivoOtrosDocumentosComiteModel> archivoOtrosDocumentosComite =  archivoOtrosDocumentosComiteRepository.findByIdCarpetaPlantillaAndIdComiteProyectoAndEstatusTrue(idCarpetaPlantilla, idComiteProyecto);
				archivoOtrosDocumentos.addAll(archivoOtrosDocumentosComite);



				for (ArchivoPlantillaModel archivo : archivoPlantilla){
					Integer idArchivoPlantilla = archivo.getIdArchivoPlantilla();

					ArchivoPlantillaComiteModel archivoPlantillaComite = archivoPlantillaComiteRepository.findByIdArchivoPlantillaAndIdAsociacionComiteProyectoAndEstatusTrue(idArchivoPlantilla, idAsociacion);
					log.info("info request -------->", archivoPlantillaComite);
					if (archivoPlantillaComite!= null){
						archivios.add(archivoPlantillaComite);
					}
				}
			}
			log.info("Plantilla: {}", plantilla);

			plantillaVigenteModelDto.setIdPlantillaVigente(idPlantillaVigente);
			plantillaVigenteModelDto.setIdFaseProyecto(plantilla.getPlantillaVigenteModel().getIdFaseProyecto());

			plantillaVigenteModelDto.setCatFaseProyecto(plantilla.getCatFaseProyecto());
			plantillaVigenteModelDto.setPlantillaVigenteModel(plantilla.getPlantillaVigenteModel());
			plantillaVigenteModelDto.setIdFase(plantilla.getIdFase());
			plantillaVigenteModelDto.setCarpetasPlantilla(plantilla.getCarpetas());

			plantillaVigenteModelDto.setArchivosPlantillaComite(archivios);
			plantillaVigenteModelDto.setArchivoOtrosDocumentos(archivoOtrosDocumentos);

			if (!otrosDocumentosExterno.isEmpty()){
				plantillaVigenteModelDto.setArhivoOtrosDocumentosExterno(otrosDocumentosExterno);
			}

			return plantillaVigenteModelDto;

		} catch (Exception e) {
			log.info(Constantes.ERROR_AL_CONSULTAR_EXEPCION);
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PlantillaVigenteModelDto obtenerEstructuraPorIdPlantilla(Integer idPlantillaVigente) {

		try {
			PlantillaVigenteModelDto plantillaVigenteModelDto = new PlantillaVigenteModelDto();

			PlantillaCarpetasDto plantilla = plantillaDocMicroservicio.obtenerPlantillaCarpeta(idPlantillaVigente);

			log.info("Plantilla: {}", plantilla);

			plantillaVigenteModelDto.setIdPlantillaVigente(idPlantillaVigente);
			plantillaVigenteModelDto.setIdFaseProyecto(plantilla.getPlantillaVigenteModel().getIdFaseProyecto());

			plantillaVigenteModelDto.setCatFaseProyecto(plantilla.getCatFaseProyecto());
			plantillaVigenteModelDto.setPlantillaVigenteModel(plantilla.getPlantillaVigenteModel());
			plantillaVigenteModelDto.setIdFase(plantilla.getIdFase());
			plantillaVigenteModelDto.setCarpetasPlantilla(plantilla.getCarpetas());


			return plantillaVigenteModelDto;

		} catch (Exception e) {
			log.info(Constantes.ERROR_AL_CONSULTAR_EXEPCION);
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}
}
