package com.sisecofi.proyectos.service.impl;
/*
import com.mock.ArchivoMock;
import com.sisecofi.proyectos.dto.ArchivosActualizadosDto;
import com.sisecofi.proyectos.repository.ComiteRepository;
import com.sisecofi.proyectos.service.ServicioArchivo;
import com.util.Constantes;
import com.util.consumer.ArchivosConsumer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ServicioArchivoTest {
    @MockBean
    private ServicioArchivo servicioArchivo;


    @Test
    void cargarArchivo() throws IOException {
        String rutaArchivo = Constantes.ARCHIVO_PRUEBA_RUTA;

        Path pathArchivo = Paths.get(rutaArchivo);

        String noombreArchivo =  pathArchivo.getFileName()
                        .toString();

        byte[] contenidoArchivo = Files.readAllBytes(pathArchivo);

        MultipartFile multipartFile = new MockMultipartFile(noombreArchivo, noombreArchivo,
                "application/json",contenidoArchivo);

        Assertions.assertNotNull(multipartFile);

        ArchivoMock.cargarArchivoMock(servicioArchivo, 1, multipartFile);

        String responseCargarArchivo =  servicioArchivo.cargarArchivo(1,multipartFile);

        Assertions.assertNotNull(responseCargarArchivo);
        Assertions.assertEquals(noombreArchivo,responseCargarArchivo);
        Assertions.assertEquals(String.class, responseCargarArchivo.getClass());
    }

    @Test
    void actualizarArchivos() throws IOException {

        String contenidoArchivo = "nombre-archivo";

        ArchivoMock.actualizarArchivosMock(servicioArchivo, contenidoArchivo);
        List<ArchivosActualizadosDto> archivosCargadosDtos = new ArrayList<>();
        String response = servicioArchivo.actualizarArchivo(archivosCargadosDtos);

        Assertions.assertEquals(contenidoArchivo, response);
    }

    @Test
    void descargarArchivo() throws IOException {
        ArchivoMock.descargarArchivoMock(servicioArchivo, Constantes.PATH_DE_PRUEBA);

        String file = servicioArchivo.descargarArchivo(Constantes.PATH_DE_PRUEBA);

        Assertions.assertNotNull(file);
        Assertions.assertEquals(String.class, file.getClass());
    }

    @Test
    void descargarFolder() throws IOException {
        ArchivoMock.descargarArchivoMock(servicioArchivo, Constantes.PATH_DE_PRUEBA);

        String fileStr = servicioArchivo.descargarFolder(Constantes.PATH_DE_PRUEBA);
        Assertions.assertNotNull(fileStr);
        Assertions.assertEquals(String.class, fileStr.getClass());
    }
}*/