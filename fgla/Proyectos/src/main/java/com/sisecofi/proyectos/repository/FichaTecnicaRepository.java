package com.sisecofi.proyectos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.sisecofi.libreria.comunes.model.proyectos.FichaTecnicaModel;

@Repository
public interface FichaTecnicaRepository extends JpaRepository<FichaTecnicaModel, Long> {

	Optional<FichaTecnicaModel> findByIdFichaTecnica(Long idFichaTecnica);

	Optional<FichaTecnicaModel> findByIdProyecto(Long idProyecto);
	
}
