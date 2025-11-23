package com.sisecofi.admindevengados.repository;

import com.sisecofi.admindevengados.model.SolicitudFacturaModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudFacturaRepository extends JpaRepository<SolicitudFacturaModel, Long> {

	SolicitudFacturaModel findByDictamenIdAndDictamenEstatusTrue(Long dictamenId);

	Optional<SolicitudFacturaModel> findByDictamenId(Long dictamenId);
	
}
