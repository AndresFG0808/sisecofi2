package com.mock;

import com.sisecofi.proyectos.dto.ArchivosActualizadosDto;
import com.sisecofi.proyectos.service.ServicioArchivo;
import org.springframework.web.multipart.MultipartFile;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArchivoMock {

    public static void cargarArchivoMock(ServicioArchivo servicioArchivo, Integer id, MultipartFile file){

        String fileName = file.getOriginalFilename();

        Mockito.when(servicioArchivo.cargarArchivo(id,file)).thenReturn(fileName);
    }

    public static void  descargarArchivoMock(ServicioArchivo servicioArchivo, String path) throws IOException {
        String fileStr = "file";

        Mockito.when(servicioArchivo.descargarArchivo(path, null)).thenReturn(fileStr);
        Mockito.when(servicioArchivo.descargarFolder(path)).thenReturn(fileStr);
    }

    public static void cargarArchivosMock(ServicioArchivo servicioArchivo, Integer id, MultipartFile file){

        String fileName = file.getOriginalFilename();

        Mockito.when(servicioArchivo.cargarArchivo(id,file)).thenReturn(fileName);
    }

    public static void actualizarArchivosMock(ServicioArchivo servicioArchivo, String fileName){

        List<ArchivosActualizadosDto> archivosCargadosDtos = new ArrayList<>();

        Mockito.when(servicioArchivo.actualizarArchivo(archivosCargadosDtos)).thenReturn(fileName);
    }
}
