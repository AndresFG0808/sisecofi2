package com.sisecofi.proyectos.dto;

import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoOtrosDocumentosComiteModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoPlantillaComiteModel;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ResponseComiteInfo {

    private InformacionComiteDto informacionComite;
    private List<ArchivoPlantillaComiteModel> infomacionArchivos;
    private List<ArchivoOtrosDocumentosComiteModel> informacionArchivosOtrosDocumentos;
    private BaseCatalogoModel comite;
}


