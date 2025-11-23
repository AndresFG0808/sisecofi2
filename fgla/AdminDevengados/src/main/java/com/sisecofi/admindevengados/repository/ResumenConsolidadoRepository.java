package com.sisecofi.admindevengados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.admindevengados.model.ResumenConsolidadoModel;
import java.util.List;


public interface ResumenConsolidadoRepository extends JpaRepository<ResumenConsolidadoModel, Long> {
	
	List<ResumenConsolidadoModel> findByIdDictamenOrderByIdResumenConsolidadoAsc(Long idDictamen);
	
	
	@Query("SELECT r FROM ResumenConsolidadoModel r " +
		       "WHERE r.dictamen.idContrato = :idContrato " +
		       "AND r.dictamen.idProveedor = :idProveedor " +
		       "AND r.dictamen.idPeriodoControlAnio = :idPeriodoControlAnio " +
		       "AND r.dictamen.idPeriodoControlMes = :idPeriodoControlMes AND r.catFaseDictamen.nombre <> 'Facturado'" +
		       "ORDER BY r.idResumenConsolidado ASC")
		List<ResumenConsolidadoModel> findByDictamenIdContratoDictamenIdProveedorDictamenIdPeriodoControlAnioDictamenIdPeriodoControlMesOrderByIdResumenConsolidadoAsc(
		       @Param("idContrato") Long idContrato,
		       @Param("idProveedor") Long idProveedor,
		       @Param("idPeriodoControlAnio") Integer idPeriodoControlAnio,
		       @Param("idPeriodoControlMes") Integer idPeriodoControlMes);

	ResumenConsolidadoModel findByIdDictamenAndIdFaseDictamen(Long idDictamen, Integer idFaseDictamen);
	
	@Query("SELECT r.idDictamen, r.total, r.totalPesos " +
		       "FROM ResumenConsolidadoModel r " +
		       "WHERE r.idDictamen IN :idDictamenes " +
		       "ORDER BY r.idResumenConsolidado ASC")
		List<Object[]> findResumenByIdDictamenIn(@Param("idDictamenes") List<Long> idDictamenes);
		
		@Query("SELECT r.dictamen.idDictamen, r.total, r.totalPesos " +
			       "FROM ResumenConsolidadoModel r " +
			       "WHERE r.dictamen.idContrato = :idContrato " +
			       "AND r.dictamen.idProveedor = :idProveedor " +
			       "AND r.dictamen.idPeriodoControlAnio = :idPeriodoControlAnio " +
			       "AND r.dictamen.idPeriodoControlMes = :idPeriodoControlMes " +
			       "ORDER BY r.idResumenConsolidado ASC") // ✅ Ordena ascendente (Fase 1 será el primero)
			List<Object[]> findResumenByContratoProveedorPeriodo(
			        @Param("idContrato") Long idContrato,
			        @Param("idProveedor") Long idProveedor,
			        @Param("idPeriodoControlAnio") Integer idPeriodoControlAnio,
			        @Param("idPeriodoControlMes") Integer idPeriodoControlMes);








}
