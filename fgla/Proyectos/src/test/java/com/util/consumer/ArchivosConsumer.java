package com.util.consumer;

import com.util.Constantes;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ArchivosConsumer {

    public static MultipartFile GenerarMultipartMock() throws IOException {
        String rutaArchivo = Constantes.ARCHIVO_PRUEBA_RUTA;

        Path pathArchivo = Paths.get(rutaArchivo);

        MultipartFile multipartFile = new MockMultipartFile(pathArchivo.getFileName()
                .toString(), pathArchivo.getFileName()
                .toString(),
                "application/json",Files.readAllBytes(pathArchivo));

        return multipartFile;
    }

    public  static  byte[] GenerarFile() throws IOException {
        
        return Files.readAllBytes(Paths.get(Constantes.ARCHIVO_PRUEBA_RUTA));
    }
}
