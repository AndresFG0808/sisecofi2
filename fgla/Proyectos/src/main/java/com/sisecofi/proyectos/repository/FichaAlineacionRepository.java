package com.sisecofi.proyectos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sisecofi.proyectos.model.FichaAlineacion;

public interface FichaAlineacionRepository extends JpaRepository<FichaAlineacion, Long> {
	
	List<FichaAlineacion> findByIdFichaTecnicaAndEstatusAlineacion(Long idFichaTecnica, boolean estatus);
	
}
