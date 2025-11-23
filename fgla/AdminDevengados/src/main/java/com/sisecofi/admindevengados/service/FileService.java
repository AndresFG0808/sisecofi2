package com.sisecofi.admindevengados.service;

import com.sisecofi.admindevengados.service.impl.NexusImpl;
import com.sisecofi.admindevengados.util.enums.ErroresEnum;
import com.sisecofi.admindevengados.util.exception.CatalogoException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Base64;

@Service
@Slf4j
@AllArgsConstructor
public class FileService {

    private final NexusImpl nexusImpl;

    public void subirArchivoANexus(MultipartFile file, String path, String nombre) {
        try {
            boolean archivo = this.nexusImpl.cargarArchivo(file.getInputStream(), path, nombre);
            log.info("Archivo cargado: {},", archivo);
        } catch (Exception e) {
            throw new CatalogoException(ErroresEnum.ERROR_AL_GUARDAR_ARCHIVO, e);
        }
    }

    public String descargarArchivo(String path) {
        try {
            InputStream obj = this.nexusImpl.descargarArchivo(path);
            log.info("Descargado: {}", obj);
            byte[] bytes = obj.readAllBytes();
            obj.close();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            throw new CatalogoException(ErroresEnum.ERROR_AL_DESCARGAR_ARCHIVO);
        }
    }
}
