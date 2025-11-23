package com.sisecofi.proyectos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.proyectos.model.ProyectoProveedorModel;

public interface ProyectoProveedorRepository extends JpaRepository<ProyectoProveedorModel, Long> {

	List<ProyectoProveedorModel> findByProyectoModelIdProyectoAndEstatusOrderByIdProyectoProveedorAsc(Long idProyecto, boolean estatus);
	
	List<ProyectoProveedorModel> findByProyectoModelIdProyectoAndEstatusTrue(Long idProyecto);

	@Modifying
	@Query("update ProyectoProveedorModel set estatus=:estatus where idProyectoProveedor=:idProyectoProveedor")
	void actualizarRegistro(@Param("idProyectoProveedor") Long idProyectoProveedor, @Param("estatus") boolean estatus);
	
	ProyectoProveedorModel findByIdProyectoProveedor(Long idProyectoProveedor);

}
