package com.sisecofi.proyectos.dto;

import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoPlantillaComiteModel;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseComiteInfoReporte {
    private InformacionComiteDto informacionComite;
    private List<ArchivoPlantillaComiteModel> infomacionArchivos;
    private BaseCatalogoModel comite;
    private String sesionNumero;
    private String sesionClasificacion;
    private String contratoConvenio;
    private String afectacion;
    private String contrato;
    private String tipoMoneda;
}
