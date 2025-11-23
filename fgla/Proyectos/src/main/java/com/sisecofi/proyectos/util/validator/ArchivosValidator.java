package com.sisecofi.proyectos.util.validator;

import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoPlantillaComiteModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.AsociasionComitePlantillaModel;
import com.sisecofi.libreria.comunes.repository.ArchivoPlantillaComiteRepository;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.exception.ProyectoException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Transactional
public class ArchivosValidator {

    private ArchivoPlantillaComiteRepository archivoPlantillaComiteRepository;


    public void idsCarpetasValidacion(List<AsociasionComitePlantillaModel> asociasiones, Integer idCarpetaPlantilaVigente){

        int idFijo = 1;

        boolean tieneAsociaciones = !asociasiones.isEmpty();

        Set<Integer> idsCarpetaPlantillaComite = asociasiones.stream()
                .map(AsociasionComitePlantillaModel::getIdAsociacionComitePlantilla)
                .collect(Collectors.toSet());

        boolean esDiferente = tieneAsociaciones && !idsCarpetaPlantillaComite.contains(idCarpetaPlantilaVigente) && idCarpetaPlantilaVigente != idFijo;

        if (esDiferente) {
            throw new ProyectoException(ErroresEnum.ERROR_ID_CARPETA);
        }
    }


    public void  validarRutas(List<String> rutas, String ruta){
        if (rutas.contains(ruta)){
            String nombreArchivoValidado = separarNombre(ruta,"/");
            log.error("el nombre de archivo ya existe",nombreArchivoValidado);

            throw new ProyectoException(ErroresEnum.ERROR_NOMBRE_ARCHIVO_DUPLICADO, nombreArchivoValidado);
        }
    }

    private String separarNombre(String path, String patron) {
        String[] archivos = path.split(patron);
        return archivos[archivos.length - 1];
    }

    public void validarNombre(List<String> listRutas, String rutaValidacion){

        if (listRutas.contains(rutaValidacion)){
            String nombreArchivoValidado = separarNombreArchivo(rutaValidacion);
            log.error("el nombre de archivo ya existe",nombreArchivoValidado);

            throw new ProyectoException(ErroresEnum.ERROR_NOMBRE_ARCHIVO_DUPLICADO);
        }
    }

    public static String separarNombreArchivo(String rutaValidacion) {
        if (rutaValidacion == null || rutaValidacion.isEmpty()) {
            throw new IllegalArgumentException("El filePath no puede ser nulo o vacío");
        }
        String[] parts = rutaValidacion.split("/");
        return parts[parts.length - 1];
    }

    public void validarArchivoGuardado(Integer idArchivoPlantila, Integer idAsociacion){

        ArchivoPlantillaComiteModel archivoPlantillaComiteModel = archivoPlantillaComiteRepository.findByIdArchivoPlantillaAndIdAsociacionComiteProyectoAndEstatusTrue(idArchivoPlantila,idAsociacion);

        if (archivoPlantillaComiteModel!= null){
            throw new ProyectoException(ErroresEnum.ARCHIVO_EXISTENTE);
        }

    }
}
