package com.sisecofi.admindevengados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.model.contratos.ServicioContratoModel;
import java.util.List;


public interface ServicioContratoRepository extends JpaRepository<ServicioContratoModel, Long> {

	
	@Query("SELECT s FROM ServicioContratoModel s WHERE s.idContrato = :idContrato AND s.estatus = true ORDER BY COALESCE(s.orden, s.idServicioContrato)")
    List<ServicioContratoModel> obtenerServiciosOrdenados(@Param("idContrato") Long idContrato);
	
	@Query("SELECT COALESCE(MIN(s.idServicioContrato), 0) FROM ServicioContratoModel s WHERE s.idContrato = :idContrato AND s.estatus = true")
	   Long obtenerPrimerIdServicioContrato(@Param("idContrato") Long idContrato);
	
     
}
