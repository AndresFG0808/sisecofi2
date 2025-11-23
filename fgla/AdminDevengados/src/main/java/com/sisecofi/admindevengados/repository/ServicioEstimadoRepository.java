package com.sisecofi.admindevengados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.admindevengados.dto.estimacion.EstimacionProyeccionDto;
import com.sisecofi.admindevengados.model.ServicioEstimadoModel;
import com.sisecofi.libreria.comunes.model.estimacion.EstimacionModel;

import java.util.List;
import java.util.Optional;

public interface ServicioEstimadoRepository extends JpaRepository<ServicioEstimadoModel, Long> {

	List<ServicioEstimadoModel> findByIdEstimacionAndServicioBaseEstatusTrue(Long idEstimacion);
	
	List<ServicioEstimadoModel> findByIdEstimacionAndServicioBaseEstatusTrueOrderByServicioBaseIdServicioContrato(Long idEstimacion);
	
	List<ServicioEstimadoModel> findByEstimacionModelInAndAndServicioBaseEstatusTrue(List<EstimacionModel> lista);

	Optional<ServicioEstimadoModel> findByIdEstimacionAndServicioBaseIdServicioContrato(Long idEstimacion, Long idServicio);
	
	boolean existsByIdEstimacionAndServicioBaseIdServicioContrato(Long idEstimacion, Long idServicioContrato);
	
	@Query("SELECT new com.sisecofi.admindevengados.dto.estimacion.EstimacionProyeccionDto(e.cantidadServiciosEstimados, e.serviciosAcumulados) " +
		       "FROM ServicioEstimadoModel e WHERE e.idServicioEstimado = :id")
		EstimacionProyeccionDto findCantidadYAcumuladosById(@Param("id") Long id);


}
