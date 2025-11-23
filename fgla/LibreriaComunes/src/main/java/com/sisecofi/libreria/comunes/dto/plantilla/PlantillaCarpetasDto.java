package com.sisecofi.libreria.comunes.dto.plantilla;

import com.sisecofi.libreria.comunes.model.catalogo.CatFaseProyecto;
import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlantillaCarpetasDto<T>{

    private CatFaseProyecto catFaseProyecto;
    private Integer idFase;
    private PlantillaVigenteModel plantillaVigenteModel;
    private List<CarpetaPlantillaModel> carpetas;
}
