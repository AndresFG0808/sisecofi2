package com.sisecofi.admindevengados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;

import java.util.Optional;


@Repository
public interface ProveedorRepository
                extends JpaRepository<ProveedorModel, Long>, JpaSpecificationExecutor<ProveedorModel> {

        Optional<ProveedorModel> findByIdProveedor(Long idProveedor);
        
        @Query("SELECT p.nombreProveedor FROM ProveedorModel p WHERE p.idProveedor = :idProveedor")
        Optional<String> findNombreProveedorByIdProveedor(@Param("idProveedor") Long idProveedor);
}
