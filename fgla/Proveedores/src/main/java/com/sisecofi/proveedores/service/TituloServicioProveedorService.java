package com.sisecofi.proveedores.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sisecofi.proveedores.dto.ConsultaTituloServicioProveedorDto;
import com.sisecofi.proveedores.dto.TituloServicioProveedorDto;
import com.sisecofi.proveedores.dto.TituloServicioResponseDto;

@Service
public interface TituloServicioProveedorService {

    ConsultaTituloServicioProveedorDto obtenerTituloServicioProveedorPorId (Long idTituloServicioProveedor);

    List<ConsultaTituloServicioProveedorDto> obtenerTodosLosTitulos();

    TituloServicioResponseDto crearTituloServicioProveedor(TituloServicioProveedorDto tituloServicioProveedorDto);

    TituloServicioResponseDto actualizarTituloServicioProveedor(Long idTituloServicioProveedor, TituloServicioProveedorDto tituloServicioProveedorDto);

    void eliminacionLogicaTituloServicioProveedor(Long idTituloServicioProveedor);


}
