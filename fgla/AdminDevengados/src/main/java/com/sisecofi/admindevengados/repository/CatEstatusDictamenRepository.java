package com.sisecofi.admindevengados.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusDictamen;


public interface CatEstatusDictamenRepository extends JpaRepository<CatEstatusDictamen, Integer> {
	
	CatEstatusDictamen findByNombre(String nombre);

}
