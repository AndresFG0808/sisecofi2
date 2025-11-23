package com.sisecofi.proyectos.dto;

import com.sisecofi.libreria.comunes.model.catalogo.CatFaseProyecto;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoOtrosDocumentosComiteModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoPlantillaComiteModel;
import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class EstructuraPlaantillaDto {
    private CatFaseProyecto catFaseProyecto;
    private Integer idFase;
    private PlantillaVigenteModel plantillaVigenteModel;
    private List<CarpetaPlantillaModel> carpetas;
    private List<ArchivoPlantillaComiteModel> infomacionArchivos;
    private List<ArchivoOtrosDocumentosComiteModel> informacionArchivosOtrosDocumentos;
}
