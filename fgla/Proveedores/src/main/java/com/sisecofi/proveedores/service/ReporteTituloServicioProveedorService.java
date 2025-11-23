package com.sisecofi.proveedores.service;

import org.springframework.stereotype.Service;

@Service
public interface ReporteTituloServicioProveedorService {

    byte[] obtenerReporteTituloServicioProveedor(Long idProveedor);
}
