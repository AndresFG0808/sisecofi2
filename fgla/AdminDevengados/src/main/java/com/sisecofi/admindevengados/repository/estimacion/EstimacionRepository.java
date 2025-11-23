package com.sisecofi.admindevengados.repository.estimacion;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.model.estimacion.EstimacionModel;

public interface EstimacionRepository extends JpaRepository<EstimacionModel, Long> {

	List<String> findIdEstimacionByIdEstimacionContaining(String nombre);

	List<EstimacionModel> findByIdEstimacionContaining(String nombre);

	@Query("SELECT e FROM EstimacionModel e " +
		       "WHERE e.estatus = true " +
		       "AND e.contratoModel.idContrato = :idContrato " +
		       "AND e.idProveedor = :idProveedor " +
		       "AND e.catEstatusEstimacion.nombre = 'Estimado' " +
		       "ORDER BY e.catPeriodoControlAnio.nombre DESC, e.catPeriodoControlMes.nombre DESC")
		List<EstimacionModel> findByEstatusTrueAndContratoModelIdContratoAndIdProveedorAndCatEstatusEstimacionNombreOrderByCatPeriodoControlAnioNombreDescCatPeriodoControlMesNombreDesc(
		    @Param("idContrato") Long idContrato,
		    @Param("idProveedor") Long idProveedor);

	List<EstimacionModel> findByContratoModelIdContratoAndIdEstatusEstimacionAndIdProveedorAndEstatusOrderByIdEstimacionAsc(Long idContrato,
			Integer idEstatus, Long idProveedor, boolean estatus);
	
	List<EstimacionModel> findByContratoModelIdContratoAndIdProveedorAndEstatusOrderByIdEstimacionAsc(Long idContrato,
			 Long idProveedor, boolean estatus);
	
	List<EstimacionModel> findByIdContratoAndIdProveedorAndIdPeriodoControlAnioAndIdPeriodoControlMesAndEstatusTrue(
			Long idContrato, Long idProveedor, Integer idPeriodoControlAnio, Integer idPeriodoControlMes);
	
	Long countByIdContratoAndIdProveedorAndIdPeriodoControlAnioAndIdPeriodoControlMesAndEstatusTrueAndIdEstatusEstimacionNotAndIdEstimacionNot(
			Long idContrato, Long idProveedor, Integer idPeriodoControlAnio, Integer idPeriodoControlMes, Integer idEstatusEstimacion, Long idEstimacion);
	
	Long countByIdContratoAndIdProveedorAndIdPeriodoControlAnioAndIdPeriodoControlMesAndEstatusTrueAndIdEstatusEstimacionNot(
			Long idContrato, Long idProveedor, Integer idPeriodoControlAnio, Integer idPeriodoControlMes, Integer idEstatusEstimacion);
	
	@Query("SELECT e FROM EstimacionModel e WHERE e.idContrato = :idContrato AND e.idProveedor = :idProveedor AND e.estatus = true AND e.catEstatusEstimacion.nombre = :nombre AND e.idEstimacion <> :idEstimacion")
	List<EstimacionModel> findByIdContratoAndIdProveedorAndEstatusTrueAndEstadoNotAndIdEstimacionNot(
	        @Param("idContrato") Long idContrato, 
	        @Param("idProveedor") Long idProveedor, 
	        @Param("nombre") String nombre,
	        @Param("idEstimacion") Long idEstimacion);
	
	Optional<EstimacionModel> findByIdEstimacionAndEstatusTrue(Long idEstimacion);
	
	Optional <EstimacionModel> findByContratoModelNombreCortoAndIdProveedorAndConsecutivoAndEstatusTrue(String nombreCorto, Long idProveedor, Integer consecutivo);
	

	@Query("SELECT e.idEstimacion " +
		       "FROM EstimacionModel e " +
		       "WHERE e.contratoModel.nombreCorto = :nombreCorto " +
		       "AND e.idProveedor = :idProveedor " +
		       "AND e.consecutivo = :consecutivo " +
		       "AND e.estatus = true")
		Optional<Long> findIdEstimacionByContratoAndProveedorAndConsecutivoAndEstatusTrue(
		       @Param("nombreCorto") String nombreCorto,
		       @Param("idProveedor") Long idProveedor,
		       @Param("consecutivo") Integer consecutivo);


	List<EstimacionModel> findByContratoModelIdContratoAndEstatusTrue(Long idContrato);
	
	@Query("""
		    SELECT 
		        e.contratoModel.nombreCorto,
		        e.consecutivo,
		        e.catEstatusEstimacion.nombre,
		        e.idContrato,
		        e.montoEstimado,
		        e.montoEstimadoPesos,
		        e.idProveedor,
		        e.idPeriodoControlAnio,
		        e.idPeriodoControlMes,
		        e.catPeriodoControlMes.nombre,
		        e.catPeriodoControlAnio.nombre,
		        e.proveedorModel.nombreProveedor,
		        e.periodoInicio,
		        e.periodoFin,
		        e.tipoCambioReferencial,
		        e.idEstimacion
		    FROM EstimacionModel e
		    WHERE e.idContrato = :idContrato
		      AND (:idEstatusEstimacion IS NULL OR e.idEstatusEstimacion = :idEstatusEstimacion)
		      AND e.idProveedor = :idProveedor
		      AND e.estatus = TRUE
		    ORDER BY e.idEstimacion ASC
		""")
		List<Object[]> findEstimacionesOptimizado(
		    @Param("idContrato") Long idContrato,
		    @Param("idEstatusEstimacion") Integer idEstatusEstimacion,
		    @Param("idProveedor") Long idProveedor);



	boolean existsByIdEstimacionAndEstatusTrue(Long idEstimacion);
	
	boolean existsByIdContratoAndEstatusTrue(Long idContrato);
	
	boolean existsByIdContratoAndIdProveedorAndEstatusTrueAndConsecutivo(Long idContrato, Long idProveedor, Integer consecutivo);
	
	@Query("SELECT COALESCE(MAX(e.consecutivo), 0) + 1 " +
		       "FROM EstimacionModel e " +
		       "WHERE e.idContrato = :idContrato " +
		       "AND e.idProveedor = :idProveedor " +
		       "AND e.estatus = true")
		Integer obtenerSiguienteConsecutivo(@Param("idContrato") Long idContrato,
		                                    @Param("idProveedor") Long idProveedor);


}
