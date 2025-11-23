package com.sisecofi.proveedores.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sisecofi.proveedores.dto.DirectorioProveedorDto;
import com.sisecofi.libreria.comunes.model.proveedores.DirectorioProveedorModel;

/**
 *
 * @author adtolentino
 * 
 */

@Service
public interface DirectorioProveedorService {

    DirectorioProveedorModel crearDirectorioProveedor(DirectorioProveedorDto directorioDto);

    List<DirectorioProveedorDto> obtenerDirectorioProveedor();

    DirectorioProveedorDto obtenerDirectorioProveedorPorId(Long id);

    DirectorioProveedorModel actualizaDirectorioContacto (Long id, DirectorioProveedorDto directorioProveedorDto);

    void eliminarContactoDirectorio (Long id);

    
    

  


}
