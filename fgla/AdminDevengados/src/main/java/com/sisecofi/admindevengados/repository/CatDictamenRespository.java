package com.sisecofi.admindevengados.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;


public interface CatDictamenRespository extends JpaRepository<Dictamen, Long> {

    Optional<Dictamen> findByIdDictamen(Long dictamenId);

}
