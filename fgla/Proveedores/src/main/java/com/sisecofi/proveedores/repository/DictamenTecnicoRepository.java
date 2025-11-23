package com.sisecofi.proveedores.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.sisecofi.libreria.comunes.model.proveedores.DictamenTecnicoModel;
import com.sisecofi.proveedores.dto.DictamenTecnicoResponseDto;

import feign.Param;

public interface DictamenTecnicoRepository extends JpaRepository<DictamenTecnicoModel, Long> {

	Optional<DictamenTecnicoModel> findByIdDictamenTecnicoProveedor(Long idDictamenTecnicoProveedor);

	Optional<DictamenTecnicoModel> findByIdDictamenTecnicoProveedorAndEstatusEliminacionLogicaDictamenTrue(
			Long idDictamenTecnicoProveedor);

	@Query("SELECT dt FROM DictamenTecnicoModel dt WHERE dt.estatusEliminacionLogicaDictamen = true ORDER BY dt.ordenDictamenProveedor ASC")
	List<DictamenTecnicoModel> findByIdDictamenTecnicoProveedorAndEstatusEliminacionLogicaDictamenTrue();

	@Query("SELECT MAX(ra.ordenDictamenProveedor) FROM DictamenTecnicoModel ra WHERE ra.proveedorModel.idProveedor = :idProveedor and ra.estatusEliminacionLogicaDictamen = true")
	Integer findMaxOrdenDictamenProveedorByIdProveedor(@Param("idProveedor") Long idProveedor);
	
	@Query("SELECT ra FROM DictamenTecnicoModel ra WHERE ra.proveedorModel.idProveedor = :idProveedor AND ra.estatusEliminacionLogicaDictamen = true ORDER BY ra.ordenDictamenProveedor ASC")
	List<DictamenTecnicoModel> findActiveDictamenesByProveedor(@Param("idProveedor") Long idProveedor);

	@Query("SELECT new com.sisecofi.proveedores.dto.DictamenTecnicoResponseDto( " + "ra.idDictamenTecnicoProveedor, "
			+ "ra.anio, " + "ra.responsable, " + "ra.catResultadoDictamenTecnicoModel.nombre, " + "ra.observacion, "
			+ "ra.catTituloServicioModel.idTituloServicio, " + "ra.catTituloServicioModel.nombre, "
			+ "ra.catResultadoDictamenTecnicoModel.idResultadoDictamenTecnico, " + "ra.proveedorModel.idProveedor, "
			+ "ra.ordenDictamenProveedor ) " + "FROM DictamenTecnicoModel ra "
			+ "WHERE ra.proveedorModel.idProveedor = :idProveedor "
			+ "AND ra.catResultadoDictamenTecnicoModel.idResultadoDictamenTecnico = :idResultadoDictamen "
			+ "AND ra.estatusEliminacionLogicaDictamen = true " + "ORDER BY ra.ordenDictamenProveedor ASC")
	List<DictamenTecnicoResponseDto> findActiveDictamenesByProveedorAndResultadoDictamen(
			@Param("idProveedor") Long idProveedor, @Param("idResultadoDictamen") Integer idResultadoDictamen);
	
	    @Query("SELECT dtp FROM DictamenTecnicoModel dtp " +
	           "JOIN FETCH dtp.catTituloServicioModel ts " +
	           "JOIN FETCH dtp.catResultadoDictamenTecnicoModel rd " +
	           "LEFT JOIN FETCH dtp.proveedorModel p " +
	           "WHERE dtp.estatusEliminacionLogicaDictamen = true " +
	           "AND p.idProveedor = :idProveedor " +
	           "AND ts.idTituloServicio = :idTituloServicio " +
	           "AND rd.idResultadoDictamenTecnico = :idCatResultadoDictamen")
	    List<DictamenTecnicoModel> findByProveedorTituloYResultado(
	        @Param("idProveedor") Integer idProveedor,
	        @Param("idTituloServicio") Integer idTituloServicio,
	        @Param("idCatResultadoDictamen") Integer idCatResultadoDictamen
	    );
	    
	    @Query("SELECT dtp FROM DictamenTecnicoModel dtp " +
	    	       "JOIN FETCH dtp.catTituloServicioModel ts " +
	    	       "LEFT JOIN FETCH dtp.proveedorModel p " +
	    	       "WHERE dtp.estatusEliminacionLogicaDictamen = true " +
	    	       "AND p.idProveedor = :idProveedor " +
	    	       "AND ts.idTituloServicio = :idTituloServicio")
	    	List<DictamenTecnicoModel> findByProveedorAndTituloServicio(
	    	    @Param("idProveedor") Integer idProveedor,
	    	    @Param("idTituloServicio") Integer idTituloServicio
	    	);


	    @Query("SELECT dtp FROM DictamenTecnicoModel dtp " +
	           "JOIN FETCH dtp.catResultadoDictamenTecnicoModel rd " +
	           "LEFT JOIN FETCH dtp.proveedorModel p " +
	           "LEFT JOIN FETCH dtp.catTituloServicioModel ts " +
	           "WHERE dtp.estatusEliminacionLogicaDictamen = true " +
	           "AND p.idProveedor = :idProveedor " +
	           "AND rd.idResultadoDictamenTecnico = :idCatResultadoDictamen")
	    List<DictamenTecnicoModel> findByProveedorYResultado(
	        @Param("idProveedor") Integer idProveedor,
	        @Param("idCatResultadoDictamen") Integer idCatResultadoDictamen
	    );

	    @Query("SELECT dtp FROM DictamenTecnicoModel dtp " +
	           "LEFT JOIN FETCH dtp.catTituloServicioModel ts " +
	           "LEFT JOIN FETCH dtp.catResultadoDictamenTecnicoModel rd " +
	           "LEFT JOIN FETCH dtp.proveedorModel p " +
	           "WHERE dtp.estatusEliminacionLogicaDictamen = true " +
	           "AND p.idProveedor = :idProveedor")
	    List<DictamenTecnicoModel> findByProveedor(
	        @Param("idProveedor") Integer idProveedor
	    );


}
