package com.sisecofi.admingeneral.service.plantillador.impl;

import com.sisecofi.admingeneral.dto.adminplantillas.CargaPlantillaDTO;
import com.sisecofi.admingeneral.dto.adminplantillas.ObtenerPlantillaDTO;
import com.sisecofi.libreria.comunes.model.plantillador.PlantilladorModel;
import com.sisecofi.admingeneral.repository.plantillador.PlantillaRespository;
import com.sisecofi.admingeneral.service.plantillador.CargaPlantilla;
import com.sisecofi.admingeneral.util.exception.PlantilladorException;
import com.sisecofi.libreria.comunes.service.NexusService;
import com.sisecofi.libreria.comunes.util.ConstantesParaRutasSATCloud;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CargaPlantillasExcelImpl implements CargaPlantilla {
	@Autowired
    private NexusService nexusService;
    @Autowired
    private PlantillaRespository plantilladorRepository;

    private static final String SEPARADOR= "/";

    @Override
    public CargaPlantillaDTO cargaPlantilla(MultipartFile file, String name, Long id) {
        Optional<PlantilladorModel> plantilla = Optional.empty();
        CargaPlantillaDTO plantillaDTO = new CargaPlantillaDTO();

        try{
            plantilla = plantilladorRepository.findById(id);
            if(!plantilla.isPresent()){
                plantillaDTO.setStatus("No se encuentra información");
                return plantillaDTO;
            }
        }catch (Exception e){
            throw new PlantilladorException(ErroresEnum.ERROR_AL_BUSCAR_RESULTADO);
        }

        PlantilladorModel plantillador = plantilla.get();
        plantillador.setFechaModificacion(LocalDateTime.now());
        String path = SEPARADOR+ConstantesParaRutasSATCloud.PATH_BASE_PLANTILLA+SEPARADOR+ name;

        plantillador.setPathFile(SEPARADOR+ConstantesParaRutasSATCloud.PATH_BASE_PLANTILLA+SEPARADOR+ name + SEPARADOR + file.getOriginalFilename());

        try {
            boolean archivo = nexusService.cargarArchivo(file.getInputStream(), path, file.getOriginalFilename());
            if (!archivo){
                plantillaDTO.setStatus("Error");
                return plantillaDTO;
            }
            plantilladorRepository.save(plantillador);
        }catch (Exception e){
            throw new PlantilladorException(ErroresEnum.ERROR_AL_GUARDAR);
        }
        plantillaDTO.setStatus("Cargado");

        return plantillaDTO;
    }

    @Override
    public ObtenerPlantillaDTO obtenerPlantilla(String path) {
        ObtenerPlantillaDTO plantillaExcel = new ObtenerPlantillaDTO();
        InputStream file = null;

        try{
            file = nexusService.descargarArchivo(path);
            if (file != null){
                plantillaExcel.setFile(file.readAllBytes());
                plantillaExcel.setNombrePlantilla(path);
            }
        }catch (Exception e){
            throw new PlantilladorException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
        }
        return plantillaExcel;
    }
}
