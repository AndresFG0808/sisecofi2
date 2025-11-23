package com.sisecofi.proyectos.service.adminplantrabajo.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.model.plantillador.PlantilladorModel;
import com.sisecofi.libreria.comunes.service.NexusService;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.proyectos.dto.adminplantrabajo.ObtenerPlantillaDto;
import com.sisecofi.proyectos.repository.adminplantrabajo.CargaPlantilladorRepository;
import com.sisecofi.proyectos.service.PistaService;
import com.sisecofi.proyectos.service.adminplantrabajo.CargaPlantilladorService;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.exception.PlantillaException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CargaPlantilladroServiceImpl implements CargaPlantilladorService {

    private final CargaPlantilladorRepository cargaPlantilladorRepository;
    private final PistaService pistaService;

    @Autowired
    private NexusService nexusService;

    @Override
    public List<Map<String, Object>> obtenerPlantillas(String nombrePlantilla) {
        List<PlantilladorModel> plantillas;
    
        // Si no se proporciona nombre, obtener todas las plantillas
        if (nombrePlantilla == null || nombrePlantilla.isEmpty()) {
            plantillas = cargaPlantilladorRepository.findAll(); // Obtener todas las plantillas
        } else {
            // Consultar las plantillas que coincidan con el nombre
            plantillas = cargaPlantilladorRepository.findByNombreContainingIgnoreCase(nombrePlantilla);
        }
    
        return plantillas.stream()
                .filter(plantilla -> plantilla.getPathFile() != null && !plantilla.getPathFile().isEmpty())
                .filter(plantilla -> plantilla.getNombre().toLowerCase().contains("plan"))
                .map(plantilla -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("idPlantillador", plantilla.getIdPlantillador());
                    result.put("nombre", plantilla.getNombre());
                    return result;
                }).toList();
    }
   

    @Override
    public ObtenerPlantillaDto obtenerPlantilla(Long idPlantillador) {
        ObtenerPlantillaDto plantillaExcel = new ObtenerPlantillaDto();
        InputStream file = null;

        try {

            PlantilladorModel platilaldor = cargaPlantilladorRepository.findByIdPlantillador(idPlantillador)
                    .orElseThrow(() -> new PlantillaException(ErroresEnum.ERROR_PLANTILLA_NO_ENCONTRADA));

            String path = platilaldor.getPathFile();

            plantillaExcel.setNombrePlantilla(path);

            file = nexusService.descargarArchivo(path);
            if (file != null) {
                plantillaExcel.setFile(file.readAllBytes());
                plantillaExcel.setNombrePlantilla(path);
            }

        } catch (Exception e) {
            throw new PlantillaException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);

        }

        StringBuilder builder = new StringBuilder();
        builder.append("Plan Tipo: ").append(plantillaExcel.getNombrePlantilla());

        pistaService.guardarPista(ModuloPista.PROYECTOS.getId(),
                TipoMovPista.IMPRIME_REGISTRO.getId(),
                TipoSeccionPista.PROYECTO_DATOS_PLAN_TRABAJO.getIdSeccionPista(),
                builder.toString(),
                Optional.empty());

        return plantillaExcel;

    }

}
