package com.sisecofi.contratos.service.carpetas;

import com.sisecofi.libreria.comunes.dto.CarpetaCompartidaDto;
import com.sisecofi.contratos.dto.ArchivoFaseDto;


public interface ServicioArchivo {
    
    void cargarArchivoFase(ArchivoFaseDto archivoDto);

    String descargarArchivo(String path, Long id);

    String descargarFolder(String path);

    CarpetaCompartidaDto descargarFolderSatCloud(String path);
    
    
    
    void eliminarArchivoSatCloud(String path, String nombre);

	void eliminarArchivo(String path);

	void eliminarArchivoSatCloud(String path);

	CarpetaCompartidaDto descargarFolderSatCloudGestion(String path, Long id, int modulo, int seccion);

	String descargarFolderGestion(String path, Long id, int modulo, int seccion);
    
}
