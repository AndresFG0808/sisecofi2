package com.sisecofi.proveedores.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.model.catalogo.CatTituloServicio;

@Service
public interface CatTituloServicioService {

    CatTituloServicio obtenerTituloServicioPorId(Integer idServicioTitulo);

    List<CatTituloServicio> obtenerTodosTitulosServicios();

}
