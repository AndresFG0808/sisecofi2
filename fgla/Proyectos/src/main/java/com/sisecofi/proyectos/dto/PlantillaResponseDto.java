package com.sisecofi.proyectos.dto;

import com.sisecofi.libreria.comunes.dto.plantilla.PlantillaDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class PlantillaResponseDto {
    private Integer idFase;
    @SuppressWarnings("rawtypes")
	private PlantillaDto plantillaVigenteModel;
    private List<CarpetaPlantillaComiteDto> carpetas;
}
