package com.sisecofi.proyectos.service.adminplantrabajo;

import java.util.List;
import java.util.Map;

import com.sisecofi.proyectos.dto.adminplantrabajo.ObtenerPlantillaDto;

public interface CargaPlantilladorService {

    List<Map<String, Object>> obtenerPlantillas(String nombrePlantilla);

    ObtenerPlantillaDto obtenerPlantilla(Long idPlantillador);

}
