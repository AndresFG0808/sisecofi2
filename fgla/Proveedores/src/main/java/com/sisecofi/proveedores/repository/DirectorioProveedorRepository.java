package com.sisecofi.proveedores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sisecofi.libreria.comunes.model.proveedores.DirectorioProveedorModel;
import feign.Param;
import java.util.Optional;
import java.util.List;



@Repository
public interface DirectorioProveedorRepository extends JpaRepository<DirectorioProveedorModel, Long> {

    Optional<DirectorioProveedorModel> findByidDirectorioContacto(Long idDirectorioContacto);

    Optional<DirectorioProveedorModel> findByNombreContacto(String nombreContacto);

    Optional<DirectorioProveedorModel> findBytelefonoOficina(String telefonoOficina);

    @Query("SELECT dp FROM DirectorioProveedorModel dp WHERE dp.estatus = true ORDER BY dp.ordenDirectorio ASC")
    List<DirectorioProveedorModel> findByEstatusTrue();
    
    @Query("SELECT dp FROM DirectorioProveedorModel dp WHERE dp.estatus = true AND dp.proveedorModel.idProveedor = :idProveedor ORDER BY dp.idDirectorioContacto")
    List<DirectorioProveedorModel> findByEstatusTrueAndIdProveedor(@Param("idProveedor") Long idProveedor);



    Optional <DirectorioProveedorModel> findByIdDirectorioContactoAndEstatusTrue(Long id);

    @Query("SELECT COALESCE(MAX(ra.ordenDirectorio), 0) FROM DirectorioProveedorModel ra WHERE ra.proveedorModel.idProveedor = :idProveedor")
    Integer findMaxOrdenDirectorioByIdProveedor(@Param("idProveedor")Long idProveedor);

    @Query("SELECT ra FROM DirectorioProveedorModel ra WHERE ra.proveedorModel.idProveedor = :idProveedor AND ra.estatus = true ORDER BY ra.ordenDirectorio ASC")
    List<DirectorioProveedorModel> findActiveProveedorDirectorioAsc(@Param("idProveedor") Long idProveedor);
    






}
 