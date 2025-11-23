package com.sisecofi.libreria.comunes.util.archivo;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import java.io.*;

public class ConvertirArchivoZip {
	
	private ConvertirArchivoZip() {
		super();
	}

	public static void zipDir(File directory, File zipFile) throws IOException {
        try (OutputStream fileDeSalida = new FileOutputStream(zipFile);
             ZipArchiveOutputStream zipDeSalida = new ZipArchiveOutputStream(fileDeSalida)) {
            addDirToZip(directory, "", zipDeSalida);
        }
    }

    private static void addDirToZip(File directorio, String path, ZipArchiveOutputStream zos) throws IOException {
        File[] files = directorio.listFiles();
        byte[] buffer = new byte[1024];
        int bytesRead;

        for (File file : files) {
            if (file.isDirectory()) {
                addDirToZip(file, path + file.getName() + File.separator, zos);
                continue;
            }

            try (InputStream fis = new FileInputStream(file)) {
                ArchiveEntry entry = new ZipArchiveEntry(path + file.getName());
                zos.putArchiveEntry(entry);

                while ((bytesRead = fis.read(buffer)) != -1) {
                    zos.write(buffer, 0, bytesRead);
                }
                zos.closeArchiveEntry();
            }
        }
    }
}
