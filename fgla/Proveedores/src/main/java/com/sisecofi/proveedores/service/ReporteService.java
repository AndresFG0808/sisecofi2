package com.sisecofi.proveedores.service;

import org.springframework.stereotype.Service;

@Service
public interface ReporteService {

    byte[] obtenerReporteDirectorioContacto(Long idProveedor);

}
