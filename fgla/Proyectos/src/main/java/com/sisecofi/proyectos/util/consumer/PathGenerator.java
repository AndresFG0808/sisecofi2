package com.sisecofi.proyectos.util.consumer;
import com.sisecofi.proyectos.dto.CarpetasDto;
import com.sisecofi.proyectos.util.Constantes;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class PathGenerator {
    private static final String PAPELERA = "/papelera/";


    public String generarPath(@NotNull Long idProyecto, @NotNull Integer idComite, @NotNull Configuration config, Integer idComiteProyecto, String carpeta) {
        String baseFolder = config.getProperty(Constantes.BASE_FOLDER);
        String comiteFilesFolder = config.getProperty(Constantes.COMITE_FOLDER);
        String comiteProyectoFolder = config.getProperty(Constantes.COMITE_PROYECTO_FOLDER);

        return "/" + baseFolder + "/" + idProyecto.toString() + "/" + comiteFilesFolder + "/" + idComite + "/" + comiteProyectoFolder + "/" + idComiteProyecto  + "/" + "Información de comités" + "/" + carpeta;
    }

    public String generarPathOtrosDocumentosExterno(@NotNull Long idProyecto, @NotNull Integer idComite, @NotNull Configuration config, Integer idComiteProyecto) {
        String baseFolder = config.getProperty(Constantes.BASE_FOLDER);
        String comiteFilesFolder = config.getProperty(Constantes.COMITE_FOLDER);
        String comiteProyectoFolder = config.getProperty(Constantes.COMITE_PROYECTO_FOLDER);

        return "/" + baseFolder + "/" + idProyecto.toString() + "/" + comiteFilesFolder + "/" + idComite + "/" + comiteProyectoFolder + "/" + idComiteProyecto + "/" + "Información de comités";
    }
    
    public String generarPathFase(@NotNull Long idProyecto, @NotNull Configuration config) {
        String baseFolder = config.getProperty(Constantes.BASE_FOLDER);
        return "/" + baseFolder + "/" + idProyecto.toString()+"/FASES";
    }
    
    public String generarRutaOtrosDocumentos(@NotNull String pathBase, String nombreOtrosDocs) {

        return "/" +pathBase + "/" + nombreOtrosDocs ;
    }

    public String generarRuta(@NotNull String pathBase, String nombre) {

        return pathBase + "/" + nombre ;
    }



    public String generarPathCarpetas(List<CarpetasDto> nombreCarpetas) {
        StringBuilder path = new StringBuilder("/");

        for (CarpetasDto carpeta : nombreCarpetas) {
            path.append(carpeta.getNombreCarpeta()).append("/").append(carpeta.getIdCarpetaPlantillaComite()).append("/");
        }

        return path.toString();
    }

    public String generarPathPapeleta(Long idPalelera, String nombreArchivo){

        return PAPELERA + idPalelera.toString() + "/" + nombreArchivo;
    }

    public String actualizarPath(String ruta, String nuevoNombreArchivo){

        if (ruta == null || ruta.isEmpty()) {
            throw new IllegalArgumentException("La ruta no puede ser nula ni vacía");
        }

        int indiceUltimoSeparador = ruta.lastIndexOf('/');

        if (indiceUltimoSeparador == -1) {
            throw new IllegalArgumentException("La ruta no contiene un separador de directorio");
        }

        String rutaDirectorio = ruta.substring(0, indiceUltimoSeparador + 1);

        return rutaDirectorio + nuevoNombreArchivo;
    }
}
