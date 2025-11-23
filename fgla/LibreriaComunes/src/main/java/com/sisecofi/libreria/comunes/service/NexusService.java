package com.sisecofi.libreria.comunes.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.aarboard.nextcloud.api.NextcloudConnector;

import com.sisecofi.libreria.comunes.dto.CarpetaCompartidaDto;
import com.sisecofi.libreria.comunes.dto.CompartidoCloudModel;
import com.sisecofi.libreria.comunes.util.exception.NexusException;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface NexusService {

	NextcloudConnector conectar() throws NexusException;

	boolean cargarArchivos(InputStream inputStream, String remotePath, String nombreArchivo) throws NexusException;

	boolean crearFolder(InputStream inputStream, String path, String nombreArchivo) throws NexusException;

	InputStream descargarFolder(String remotepath) throws NexusException;

	boolean borrarFolder(String path) throws NexusException;

	boolean cargarArchivo(InputStream inputStream, String remotePath, String nombreArchivo) throws NexusException;

	CompartidoCloudModel compartirSoloLectura(String path) throws NexusException;

	InputStream descargarArchivo(String remotepath) throws NexusException;

	void eliminarArchivo(String path, Long idPapelera) throws NexusException;

	void restautarArchivo(String path, String pathOriginal) throws NexusException;

	File[] informacionFolder(String remotepath) throws NexusException;

	CarpetaCompartidaDto crearCarpetaCompartida(String remotepath) throws NexusException;

	void generarCarpetasVacias(String path) throws NexusException;

	void eliminarArchivoSat(String path);

	List<CompartidoCloudModel> compartirSoloLectura(List<String> path) throws NexusException;

	void renameFile(String pathOriginal, String pathPapelera);
}
