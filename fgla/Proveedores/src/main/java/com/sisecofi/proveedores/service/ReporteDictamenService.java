package com.sisecofi.proveedores.service;

import org.springframework.stereotype.Service;

@Service
public interface ReporteDictamenService {

    byte[] obtenerReporteDictamenTecnico(Long idProveedor);

}
