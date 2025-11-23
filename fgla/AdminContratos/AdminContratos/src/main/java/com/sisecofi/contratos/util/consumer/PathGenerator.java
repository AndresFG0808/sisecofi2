package com.sisecofi.contratos.util.consumer;


import com.sisecofi.contratos.util.Constantes;

import jakarta.validation.constraints.NotNull;

public class PathGenerator {
    private static final String PAPELERA = "/papelera/";

    public String generarPath(Long idContrato, Integer idSeccionLaout,Configuration config) {
        String baseFolder = config.getProperty(Constantes.BASE_FOLDER);
        String layoutFilesFolder = config.getProperty(Constantes.LAYOUT_FOLDER);

        return "/" + baseFolder + "/" + idContrato.toString() + "/" + layoutFilesFolder + "/" + idSeccionLaout;
    }

    public String generarRuta(String pathBase, String nombre) {

        return pathBase + "/" + nombre ;
    }

    public String generarPathPapeleta(Long idPalelera, String nombreArchivo){

        return PAPELERA + idPalelera.toString() + "/" + nombreArchivo;
    }
    
    public String generarPathFase(@NotNull String nombreCorto, @NotNull Configuration config) {
        String baseFolder = config.getProperty(Constantes.BASE_FOLDER_COTRATOS);
        return "/" + baseFolder + "/" + nombreCorto+"/FASES";
    }
    
    public String generarPathFaseConvenio(@NotNull String numeroCm, @NotNull Configuration config) {
        String baseFolder = config.getProperty(Constantes.BASE_FOLDER_COTRATOS);
        return "/" + baseFolder + "/" + numeroCm;
    }

    public String generarPathCierre(Long idContrato, Configuration config) {
        String baseFolder = config.getProperty(Constantes.BASE_FOLDER);
        String contratoFolder = config.getProperty(Constantes.CONTRATO_FOLDER);

        return "/" + baseFolder + "/" + contratoFolder + "/" +  idContrato.toString() ;
    }
    
    

}
