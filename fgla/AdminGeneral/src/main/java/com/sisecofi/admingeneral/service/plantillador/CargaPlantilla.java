package com.sisecofi.admingeneral.service.plantillador;

import com.sisecofi.admingeneral.dto.adminplantillas.CargaPlantillaDTO;
import com.sisecofi.admingeneral.dto.adminplantillas.ObtenerPlantillaDTO;
import org.springframework.web.multipart.MultipartFile;

public interface CargaPlantilla {
    CargaPlantillaDTO cargaPlantilla(MultipartFile file, String name, Long id);
    ObtenerPlantillaDTO obtenerPlantilla(String path);
}
