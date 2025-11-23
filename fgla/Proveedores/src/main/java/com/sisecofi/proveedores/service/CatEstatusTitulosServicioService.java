package com.sisecofi.proveedores.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusTituloServicio;

@Service
public interface CatEstatusTitulosServicioService {

	CatEstatusTituloServicio obtenerEstatusPorId(Integer idCatEstatusTituloServicio);

    List<CatEstatusTituloServicio> obtenerTodosEstatus();

}
