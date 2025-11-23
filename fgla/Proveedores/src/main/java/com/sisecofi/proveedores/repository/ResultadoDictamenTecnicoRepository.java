package com.sisecofi.proveedores.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.catalogo.CatResultadoDictamenTecnicoModel;

public interface ResultadoDictamenTecnicoRepository extends JpaRepository<CatResultadoDictamenTecnicoModel, Integer>{

    Optional<CatResultadoDictamenTecnicoModel>findById(Integer id);

}
