package com.sisecofi.proveedores.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.sisecofi.libreria.comunes.model.proveedores.TituloServicioProveedorModel;

import feign.Param;

public interface TituloServicioProveedorServiceRepository extends JpaRepository<TituloServicioProveedorModel, Long> {

	Optional<TituloServicioProveedorModel> findByIdTituloServicioProveedor(Long idTituloServicioProveedor);

	Optional<TituloServicioProveedorModel> findByNumeroTitulo(String numeroTitulo);

	Optional<TituloServicioProveedorModel> findByIdTituloServicioProveedorAndEstatusEliminacionLogicaTituloTrue(
			Long idTituloServicioProveedor);

	@Query("SELECT ts FROM TituloServicioProveedorModel ts WHERE ts.estatusEliminacionLogicaTitulo = true ORDER BY ts.ordenTitulo ASC")
	List<TituloServicioProveedorModel> findByIdTituloServicioProveedorAndEstatusEliminacionLogicaTrue();

	@Query("SELECT COALESCE(MAX(ra.ordenTitulo), 0) FROM TituloServicioProveedorModel ra WHERE ra.proveedorModel.idProveedor = :idProveedor")
	Integer findMaxOrdenTituloByIdProveedor(@Param("idProveedor") Long idProveedor);

	@Query("SELECT ra FROM TituloServicioProveedorModel ra WHERE ra.proveedorModel.idProveedor = :idProveedor AND ra.estatusEliminacionLogicaTitulo = true ORDER BY ra.ordenTitulo ASC")
	List<TituloServicioProveedorModel> findProveedorOrdenTituloAsc(@Param("idProveedor") Long idProveedor);

	@Query("SELECT tsp FROM TituloServicioProveedorModel tsp " +
		       "WHERE tsp.estatusEliminacionLogicaTitulo = true " +
		       "AND tsp.proveedorModel.idProveedor = :idProveedor " +
		       "AND tsp.tituloServicioModel.idTituloServicio = :idTituloServicio " +
		       "ORDER BY tsp.ordenTitulo ASC")
		List<TituloServicioProveedorModel> findByProveedorYTitulo(
		    @Param("idProveedor") Integer idProveedor,
		    @Param("idTituloServicio") Integer idTituloServicio
		);



}
