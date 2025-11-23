package com.mock;

import com.sisecofi.libreria.comunes.dto.plantilla.PlantillaDto;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;
import com.sisecofi.proyectos.dto.PlantillaVigenteModelDto;
import com.sisecofi.proyectos.service.ServicioPlantilla;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;

public class PlantillaMock {
	@SuppressWarnings("rawtypes")
	public static void plantillaMock(ServicioPlantilla servicioPlantilla, Integer idPlantilla){

        PlantillaVigenteModel plantillaVigenteModel = new PlantillaVigenteModel();
        plantillaVigenteModel.setIdPlantillaVigente(idPlantilla);
        
		PlantillaDto plantillaDto = new PlantillaDto();
        plantillaDto.setIdFase(1);
        plantillaDto.setPlantillaVigenteModel(plantillaVigenteModel);


        Mockito.when(servicioPlantilla.obtenerPlantillaPorId(idPlantilla)).thenReturn(plantillaDto);
    }

    public static void listaPlantillaMock(ServicioPlantilla servicioPlantilla){
        Integer idPlantilla = 2;

        PlantillaVigenteModel plantillaVigenteModel = new PlantillaVigenteModel();
        plantillaVigenteModel.setIdPlantillaVigente(idPlantilla);
        plantillaVigenteModel.setNombre("plantilla");

        List<PlantillaVigenteModel> plantillaVigenteModelList =  new ArrayList<PlantillaVigenteModel>();
        plantillaVigenteModelList.add(plantillaVigenteModel);

        Mockito.when(servicioPlantilla.obtenerPlantillas()).thenReturn(plantillaVigenteModelList);
    }

    public static void  estructuraMock(ServicioPlantilla servicioPlantilla){
        PlantillaVigenteModelDto plantillaVigenteModelDto = new PlantillaVigenteModelDto();
        plantillaVigenteModelDto.setIdPlantillaVigente(1);

        Mockito.when(servicioPlantilla.obtenerEstructura(1)).thenReturn(plantillaVigenteModelDto);
    }
}
