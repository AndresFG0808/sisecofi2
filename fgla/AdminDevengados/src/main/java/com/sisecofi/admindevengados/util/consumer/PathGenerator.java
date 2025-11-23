package com.sisecofi.admindevengados.util.consumer;

import com.sisecofi.admindevengados.util.Constantes;
import jakarta.validation.constraints.NotNull;

public class PathGenerator {
	
	private static final String BASE= "baseFolder";
	private static final String PAPELERA = "/papelera/";

	public String generarPathSoporte(@NotNull String dictamenId, @NotNull Configuration config) {
		String baseFolder = config.getProperty(BASE);
		String soporteFilesFolder = config.getProperty("SoporteFilesFolder");
		return "/" + baseFolder + "/" + dictamenId + "/" + soporteFilesFolder;
	}
	
	public String generarPathFactura(@NotNull String dictamenId, @NotNull Configuration config) {
		String baseFolder = config.getProperty(BASE);
		String soporteFilesFolder = config.getProperty("FacturaFilesFolder");
		return "/" + baseFolder + "/" + dictamenId + "/" + soporteFilesFolder;
	}
	
	public String generarPathNotaCredito(@NotNull String dictamenId, @NotNull Configuration config) {
		String baseFolder = config.getProperty(BASE);
		String soporteFilesFolder = config.getProperty("NotaCreditoFilesFolder");
		return "/" + baseFolder + "/" + dictamenId + "/" + soporteFilesFolder;
	}


	public String generarPathSolicitudPago(Long idSolicitudPago,Configuration configuration){
		String baseFolder = configuration.getProperty(Constantes.BASE_FOLDER);

		String solocitudFilesFolder = configuration.getProperty(Constantes.SOLICITUD_FOLDER);

		return "/"+ baseFolder+"/"+ solocitudFilesFolder + "/" + idSolicitudPago;
	}

	public String generarRuta(@NotNull String pathBase, String nombre) {

		return pathBase + "/" + nombre ;
	}

	public String generarPathSolicitudPago(String carpeta, String nombre){

		return carpeta + "/" + nombre + ".pdf";
	}

	public String generarPathPapeleta(Long idPalelera, String nombreArchivo){

		return PAPELERA + idPalelera.toString() + "/" + nombreArchivo;
	}
}
