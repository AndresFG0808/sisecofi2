package com.sisecofi.proveedores.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.catalogo.CatTituloServicio;

public interface TituloServicioRepository extends JpaRepository<CatTituloServicio, Integer> {

    Optional<CatTituloServicio> findByIdTituloServicio(Integer idTituloServicio);


}
