package com.sisecofi.proveedores.service;

import org.springframework.stereotype.Service;

@Service
public interface ReporteGeneralProveedoresService {

    byte[] obtenerReporteGeneralProveedores (Integer idGiroEmpresarial, Integer idTituloServicio, Integer idCatResultadoDictamen);
}



