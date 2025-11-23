package com.sisecofi.proyectos.service;

import com.sisecofi.libreria.comunes.dto.CarpetaCompartidaDto;
import com.sisecofi.proyectos.dto.ArchivosActualizadosDto;
import com.sisecofi.proyectos.dto.ArchivosCargadosDto;
import com.sisecofi.proyectos.dto.ArchivoFaseDto;
import com.sisecofi.proyectos.dto.ArchivosEliminadosDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ServicioArchivo {

    String cargarArchivo(Integer idComiteProyecto, MultipartFile file);
    
    String cargarArchivoFase(ArchivoFaseDto archivoDto);

    String cargarArchivosComite(List<ArchivosCargadosDto> archivosCargadosDtos);

    String actualizarArchivo(List<ArchivosActualizadosDto> archivosCargadosDtos);

    String eliminarArchivos(List<ArchivosEliminadosDto> archivosCargados);

    String eliminarArchivo(ArchivosEliminadosDto path);

    String descargarArchivo(String path, Long idProyecto);

    String descargarFolder(String path);

    String desgargarFolderComite(String path);

    CarpetaCompartidaDto descargarFolderSatCloud(String path);

    CarpetaCompartidaDto descargarFolderSatCloudComites(String path);

    void eliminarArchivoSatCloud(String path);
    
}
