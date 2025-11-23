package com.sisecofi.admindevengados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sisecofi.libreria.comunes.model.catalogo.CatDesgloce;

@Repository
public interface CatDesgloceRepository extends JpaRepository<CatDesgloce, Long> {

}
