package com.sisecofi.admindevengados.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.catalogo.CatTipoMoneda;

public interface CatTipoMonedaRepository extends JpaRepository <CatTipoMoneda, Integer>{

    
    Optional<CatTipoMoneda> findByIdTipoMoneda(Integer idTipoMoneda);

    Optional<CatTipoMoneda> findByNombre(String nombre);

}
