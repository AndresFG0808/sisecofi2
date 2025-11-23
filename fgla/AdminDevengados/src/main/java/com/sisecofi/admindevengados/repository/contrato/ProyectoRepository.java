package com.sisecofi.admindevengados.repository.contrato;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProyectoRepository
		extends JpaRepository<ProyectoModel, Long>, JpaSpecificationExecutor<ProyectoModel> {

	@Query("SELECT c.proyecto.idProyecto FROM ContratoModel c " +
		       "WHERE c.idContrato = :idContrato AND c.estatus = TRUE")
		Optional<Long> findIdProyectoByIdContrato(@Param("idContrato") Long idContrato);


}
