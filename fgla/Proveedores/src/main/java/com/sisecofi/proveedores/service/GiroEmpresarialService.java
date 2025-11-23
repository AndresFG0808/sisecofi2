package com.sisecofi.proveedores.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.model.catalogo.CatGiroEmpresarial;




@Service
public interface GiroEmpresarialService {
	
    CatGiroEmpresarial obtenerGiroPorId(Long id);

    List<CatGiroEmpresarial> obtenerTodosLosGiros();
	
}
