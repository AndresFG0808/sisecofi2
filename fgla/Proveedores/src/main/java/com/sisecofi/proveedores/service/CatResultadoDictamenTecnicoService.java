package com.sisecofi.proveedores.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.model.catalogo.CatResultadoDictamenTecnicoModel;

@Service
public interface CatResultadoDictamenTecnicoService {

    List<CatResultadoDictamenTecnicoModel> obtenerTodosResultados();

}
