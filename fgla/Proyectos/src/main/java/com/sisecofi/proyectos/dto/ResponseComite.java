package com.sisecofi.proyectos.dto;

import com.sisecofi.libreria.comunes.dto.contrato.ContratoDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoOtrosDocumentosComiteModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoPlantillaComiteModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.AsociasionComitePlantillaModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ComiteProyectoModel;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseComite {

    private ComiteProyectoModel informacionComiteProyecto;
    private BaseCatalogoModel comite;
    private ContratoDto contrato;
    private BaseCatalogoModel tipoMoneda;
    private BaseCatalogoModel contratoConvenio;
    private AsociasionComitePlantillaModel asociasiones;
    private List<Integer> idsAfectacion;
    private List<ArchivoPlantillaComiteModel> informacionArchivos;
    private List<ArchivoOtrosDocumentosComiteModel> informacionArchivosOtrosDocumentos;
}
