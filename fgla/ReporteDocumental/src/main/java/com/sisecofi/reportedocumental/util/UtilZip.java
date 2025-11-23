package com.sisecofi.reportedocumental.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class UtilZip {

	private UtilZip() {		
	}
	
	public static String getLastSegment(String path) {
		if (path == null || path.isEmpty()) {
			return ""; // Manejo de cadenas vacías o nulas
		}
		// Encontrar la última aparición de '/'
		int lastSlashIndex = path.lastIndexOf('/');
		// Retornar el segmento después del último '/'
		if (lastSlashIndex != -1 && lastSlashIndex < path.length() - 1) {
			return path.substring(lastSlashIndex + 1);
		} else {
			return path; // Si no hay '/', retorna toda la cadena
		}
	}

	public static byte[] convertInputStreamToByteArray(InputStream inputStream) throws IOException {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[1024]; // Tamaño del buffer
			int bytesRead;
			// Leer el InputStream en un buffer y escribir en ByteArrayOutputStream
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}
			return baos.toByteArray(); // Retornar el byte array
		}
	}

	public static byte[] createZip(List<String> fileNames, List<InputStream> inputStreams) throws IOException {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ZipOutputStream zos = new ZipOutputStream(baos)) {
			for (int i = 0; i < fileNames.size(); i++) {
				addFileToZip(fileNames.get(i), inputStreams.get(i), zos);
			}
			zos.finish(); // Finalizar la escritura del ZIP
			return baos.toByteArray(); // Retornar el ZIP como byte[]
		}
	}

	private static void addFileToZip(String fileName, InputStream inputStream, ZipOutputStream zos) throws IOException {
		ZipEntry zipEntry = new ZipEntry(fileName);
		zos.putNextEntry(zipEntry);
		byte[] buffer = new byte[1024];
		int bytesRead;
		// Leer del InputStream y escribir en el ZipOutputStream
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			zos.write(buffer, 0, bytesRead);
		}
		zos.closeEntry();
	}
}
