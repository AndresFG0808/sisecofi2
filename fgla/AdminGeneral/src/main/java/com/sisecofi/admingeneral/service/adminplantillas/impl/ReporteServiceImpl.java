package com.sisecofi.admingeneral.service.adminplantillas.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sisecofi.admingeneral.repository.adminplantillas.CarpetaPlantillaRepository;
import com.sisecofi.admingeneral.repository.adminplantillas.PlantillaVigenteRopository;
import com.sisecofi.admingeneral.service.adminpistas.PistaService;
import com.sisecofi.admingeneral.service.adminplantillas.ReporteService;
import com.sisecofi.admingeneral.util.Constantes;
import com.sisecofi.admingeneral.util.consumers.CarpetaPlantillaConsumer;
import com.sisecofi.admingeneral.util.enums.ErroresAdminPlantillaEnum;
import com.sisecofi.admingeneral.util.exception.PlantillaException;
import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

	private final PlantillaVigenteRopository plantillaVigenteRopository;
	private final CarpetaPlantillaRepository carpetaPlantillaRepository;
	private final CarpetaPlantillaConsumer carpetaPlantillaConsumer;
	private final PistaService pistaService;

	@Override
	public byte[] generarReporte(Integer idPlantilla) {
		Optional<PlantillaVigenteModel> plan = plantillaVigenteRopository.findById(idPlantilla);
		log.info("Plantilla: {}", plan);

		if (plan.isPresent()) {
			List<CarpetaPlantillaModel> carpetas = carpetaPlantillaRepository
					.findByPlantillaVigenteModelIdPlantillaVigenteAndNivel(plan.get().getIdPlantillaVigente(), 1);

			Integer nivelMaximo = carpetaPlantillaRepository
					.findMaxNivelByPlantillaId(plan.get().getIdPlantillaVigente());

			String cabeceras = generarCabeceras(nivelMaximo);

			carpetaPlantillaConsumer.inializar("reporte");
			carpetaPlantillaConsumer.agregarCabeceras(cabeceras);
			carpetaPlantillaConsumer.accept(carpetas);

			pistaService.guardarPista(ModuloPista.MATRIZ_DOCUMENTAL.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),
					TipoSeccionPista.MATRIZ_DOCUMENTAL.getIdSeccionPista(),
					Constantes.getDescargarPlantillaFase()[0] + plan.get().getIdPlantillaVigente() + "|"
							+ Constantes.getDescargarPlantillaFase()[1] + plan.get().getNombre() + ".xlsx" + "|"
							+ Constantes.getDescargarPlantillaFase()[3] + plan.get().getCatFaseProyecto().getNombre(),
					Optional.empty());
			return carpetaPlantillaConsumer.cerrarBytes();
		}

		throw new PlantillaException(ErroresAdminPlantillaEnum.ERROR_PLANTILLA_NO_ENCONTRADA);
	}

	private String generarCabeceras(Integer max) {

		StringBuilder cabeceras = new StringBuilder();

		for (int i = 1; i <= max; i++) {
			if (i > 1) {
				cabeceras.append(",");
			}
			cabeceras.append("Carpeta ").append(i).append(",Archivos");
		}

		cabeceras.append(",Descripción de los documentos");

		return cabeceras.toString();
	}

	@Override
	public byte[] generarReporteBase() {
	    try (InputStream inputStream = ReporteServiceImpl.class.getResourceAsStream("/Nomenclatura de documentos.xlsx")) {
	        if (inputStream == null) {
	            throw new PlantillaException(ErroresAdminPlantillaEnum.ERROR_AL_GENERAR_REPORTE);
	        }
	        pistaService.guardarPista(
	            ModuloPista.MATRIZ_DOCUMENTAL.getId(),
	            TipoMovPista.IMPRIME_REGISTRO.getId(),
	            TipoSeccionPista.MATRIZ_DOCUMENTAL.getIdSeccionPista(),
	            Constantes.getDescargarPlantillaFase()[2],
	            Optional.empty()
	        );
	        return inputStream.readAllBytes();
	    } catch (IOException e) {
	        throw new PlantillaException(ErroresAdminPlantillaEnum.ERROR_AL_GENERAR_REPORTE);
	    }
	}


}
