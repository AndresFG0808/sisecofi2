package com.sisecofi.proyectos.service.impl;
/*
import com.mock.PlantillaMock;
import com.sisecofi.libreria.comunes.dto.plantilla.PlantillaDto;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;
import com.sisecofi.proyectos.dto.PlantillaVigenteModelDto;
import com.sisecofi.proyectos.service.ServicioPlantilla;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

@SpringBootTest
class ServicioPlantillaTest {
    @Autowired
    ServicioPlantilla servicioPlantilla;

    @MockBean
    private ServicioPlantillaImpl servicioPlantillaImpl;

    @Test
    void obtenerPlantillaPorId() {
        Integer idPlantilla = 2;
        PlantillaMock.plantillaMock(servicioPlantillaImpl, idPlantilla);

        PlantillaDto plantillaDto = servicioPlantilla.obtenerPlantillaPorId(idPlantilla);

        Assertions.assertNotNull(plantillaDto);
        Assertions.assertNotNull(plantillaDto.getPlantillaVigenteModel());
        Assertions.assertEquals(idPlantilla,plantillaDto.getPlantillaVigenteModel().getIdPlantillaVigente());
    }

    @Test
    void obtenePlantillas() {
        PlantillaMock.listaPlantillaMock(servicioPlantillaImpl);

        List<PlantillaVigenteModel> plantillaVigenteModelList = servicioPlantillaImpl.obtenerPlantillas();

        for(PlantillaVigenteModel plantillaModel: plantillaVigenteModelList){
            Assertions.assertNotNull(plantillaModel.getIdPlantillaVigente());
            Assertions.assertNotNull(plantillaModel.getNombre());
        }
    }

    @Test
    void obtenerEstructura(){
        PlantillaMock.estructuraMock(servicioPlantillaImpl);

        PlantillaVigenteModelDto estructuea =  servicioPlantillaImpl.obtenerEstructura(1);

        Assertions.assertNotNull(estructuea);
        Assertions.assertEquals(1,estructuea.getIdPlantillaVigente());
    }
}*/