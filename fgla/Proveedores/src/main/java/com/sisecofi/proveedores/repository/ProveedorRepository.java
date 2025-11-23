package com.sisecofi.proveedores.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
import com.sisecofi.libreria.comunes.model.proveedores.TituloServicioProveedorModel;

import feign.Param;

import java.util.Optional;
import java.util.List;

/**
 * 
 * 
 * @author adtolentino
 * 
 * 
 *
 */

@Repository
public interface ProveedorRepository
                extends JpaRepository<ProveedorModel, Long>, JpaSpecificationExecutor<ProveedorModel> {

        Optional<ProveedorModel> findByIdProveedor(Long idProveedor);

        Optional<ProveedorModel> findByNombreProveedor(String nombreProveedor);

        Optional<ProveedorModel> findByRfc(String rfc);

        Optional<ProveedorModel> findByIdAgs(String idAgs);

        @Query("SELECT p FROM ProveedorModel p  ORDER BY p.idProveedor")
        Page<ProveedorModel> findAllOrderedByIdProveedor(Pageable pageable);

        @Query("SELECT d.idDirectorioContacto FROM DirectorioProveedorModel d WHERE d.proveedorModel.idProveedor = :id")
        List<Long> obtenerIdsDirectoriosProveedor(Long id);

        List<ProveedorModel> findAllByOrderByIdProveedorAsc();

        List<ProveedorModel> findByEstatusOrderByNombreProveedorAsc(boolean estatus);

        List<ProveedorModel> findAllByRfc(String rfc);

        @Query(value = "WITH ProveedorData AS ( " +
                "SELECT " +
                "sp.id_proveedor, " +
                "sp.nombre_proveedor, " +
                "sp.nombre_comercial, " +
                "sp.id_ags, " +
                "sge.nombre AS giro_empresarial, " +
                "sp.rfc, " +
                "COALESCE(STRING_AGG(DISTINCT CASE WHEN sdp.representante_legal THEN sdp.nombre_contacto ELSE NULL END, ', '), NULL) AS representante_legal, " +
                "sp.estatus " +
                "FROM sisecofi.sscft_proveedor sp " +
                "INNER JOIN sisecofi.sscfc_giro_empresarial sge ON sge.id_giro_empresarial = sp.id_giro_empresarial " +
                "LEFT JOIN sisecofi.sscft_directorio_proveedor sdp ON sdp.id_proveedor = sp.id_proveedor AND sdp.estatus = true " +
                "WHERE (:idGiroEmpresarial IS NULL OR sge.id_giro_empresarial = :idGiroEmpresarial) " +
                "GROUP BY sp.id_proveedor, sp.nombre_proveedor, sp.nombre_comercial, sge.nombre, sp.rfc, sp.estatus " +
                "), " +
                "TituloData AS ( " +
                "SELECT " +
                "stp.id_proveedor, " +
                "COUNT(stp.id_titulo_servicio_proveedor) AS total_titulos, " + 
                "STRING_AGG(DISTINCT sst.nombre_corto, ', ') AS nombres_titulos, " +  
                "MAX(stp.vigencia) AS vigencia, " +
                "MAX(stp.vencimiento_titulo) AS vencimiento_titulo, " +
                "MAX(stp.color_vigencia) AS color_vigencia " +
                "FROM sisecofi.sscfc_titulo_servicio_proveedor stp " +
                "LEFT JOIN sisecofi.sscfc_titulo_servicio sst ON sst.id_titulo_servicio = stp.id_titulo_servicio " +
                "WHERE ( " +
                "    :idTituloServicio IS NULL OR stp.id_titulo_servicio = :idTituloServicio " +  
                ") " +
                "AND stp.estatus_eliminacion_logica_titulo = true " +
                "GROUP BY stp.id_proveedor " +
                "), " +
                "DictamenData AS ( " +
                "SELECT " +
                "sdtp.id_proveedor, " +
                "SUM(CASE WHEN sdtp.id_resultado_dictamen_tecnico = 1 THEN 1 ELSE 0 END) AS cumple_count, " +
                "SUM(CASE WHEN sdtp.id_resultado_dictamen_tecnico = 2 THEN 1 ELSE 0 END) AS no_cumple_count, " +
                "COUNT(sdtp.id_dictamen_tecnico_proveedor) AS total_dictamenes " +
                "FROM sisecofi.sscfc_dictamen_tecnico_proveedor sdtp " +
                "WHERE ( " +
                "    :idCatResultadoDictamen IS NULL OR sdtp.id_resultado_dictamen_tecnico = :idCatResultadoDictamen " +
                ") " +
                "AND ( " +
                "    :idTituloServicio IS NULL OR sdtp.id_servicio_titulo IN ( " + 
                "        SELECT DISTINCT id_titulo_servicio FROM sisecofi.sscfc_titulo_servicio_proveedor " +
                "        WHERE id_proveedor = sdtp.id_proveedor " +
                "        AND (:idTituloServicio IS NULL OR id_titulo_servicio = :idTituloServicio) " +
                "        AND estatus_eliminacion_logica_titulo = true " +
                "    ) " +
                ") " +
                "AND sdtp.estatus_eliminacion_logica_dictamen = true " +
                "GROUP BY sdtp.id_proveedor " +
                ") " +
                "SELECT " +
                "pd.id_proveedor AS id, " +
                "pd.nombre_proveedor, " +
                "pd.nombre_comercial, " +
                "pd.id_ags, " +
                "pd.giro_empresarial AS giro_de_la_empresa, " +
                "pd.rfc, " +
                "pd.representante_legal, " +
                "CASE " +
                "WHEN td.total_titulos = 1 THEN td.nombres_titulos " +  
                "WHEN td.total_titulos > 1 THEN CAST(td.total_titulos AS VARCHAR) " +  
                "ELSE NULL " +  
                "END AS titulo_de_servicio, " +
                "CASE " +
                "WHEN td.total_titulos = 1 THEN td.vigencia " + 
                "ELSE NULL " +  
                "END AS vigencia, " +
                "td.color_vigencia, " +
                "td.vencimiento_titulo, " +
                "pd.estatus, " +
                "CASE " +
                "WHEN dd.total_dictamenes = 1 THEN " +
                "CASE " +
                "WHEN dd.cumple_count > 0 THEN 'Cumple' " +
                "WHEN dd.no_cumple_count > 0 THEN 'No Cumple' " +
                "ELSE NULL " +  
                "END " +
                "ELSE CAST(dd.total_dictamenes AS VARCHAR) " +
                "END AS cumple_dictamen " +
                "FROM ProveedorData pd " +
                "LEFT JOIN TituloData td ON td.id_proveedor = pd.id_proveedor " +
                "LEFT JOIN DictamenData dd ON dd.id_proveedor = pd.id_proveedor " +
                "WHERE (:idTituloServicio IS NULL OR td.id_proveedor IS NOT NULL) " +
                "AND (:idCatResultadoDictamen IS NULL OR dd.id_proveedor IS NOT NULL) " +
                "ORDER BY pd.id_proveedor ASC", 
        countQuery = "SELECT COUNT(*) FROM sisecofi.sscft_proveedor " +
                    "WHERE (:idGiroEmpresarial IS NULL OR id_giro_empresarial = :idGiroEmpresarial) " +
                    "AND (:idTituloServicio IS NULL OR id_proveedor IN ( " +
                    "    SELECT DISTINCT id_proveedor FROM sisecofi.sscfc_titulo_servicio_proveedor " +
                    "    WHERE id_titulo_servicio = :idTituloServicio AND estatus_eliminacion_logica_titulo = true)) " +
                    "AND (:idCatResultadoDictamen IS NULL OR id_proveedor IN ( " +
                    "    SELECT DISTINCT id_proveedor FROM sisecofi.sscfc_dictamen_tecnico_proveedor " +
                    "    WHERE id_resultado_dictamen_tecnico = :idCatResultadoDictamen AND estatus_eliminacion_logica_dictamen = true))",
        nativeQuery = true)

        Page<Object[]> obtenerProveedoresConTituloAgrupado(
            @Param("idGiroEmpresarial") Integer idGiroEmpresarial,
            @Param("idTituloServicio") Integer idTituloServicio,
            @Param("idCatResultadoDictamen") Integer idCatResultadoDictamen,
            Pageable pageable);



        @Query(value = "WITH ProveedorData AS ( " +
                "SELECT sp.id_proveedor " +
                "FROM sisecofi.sscft_proveedor sp " +
                "INNER JOIN sisecofi.sscfc_giro_empresarial sge ON sge.id_giro_empresarial = sp.id_giro_empresarial " +
                "WHERE (:idGiroEmpresarial IS NULL OR sge.id_giro_empresarial = :idGiroEmpresarial) " +
                "), " +
                "TituloData AS ( " +
                "SELECT DISTINCT stp.id_proveedor " +
                "FROM sisecofi.sscfc_titulo_servicio_proveedor stp " +
                "INNER JOIN sisecofi.sscfc_titulo_servicio sst ON sst.id_titulo_servicio = stp.id_titulo_servicio " +
                "WHERE (:idTituloServicio IS NULL OR sst.id_titulo_servicio = :idTituloServicio) " +
                "AND stp.estatus_eliminacion_logica_titulo = true " +
                "), " +
                "DictamenData AS ( " +
                "SELECT DISTINCT sdtp.id_proveedor " +
                "FROM sisecofi.sscfc_dictamen_tecnico_proveedor sdtp " +
                "LEFT JOIN sisecofi.sscfc_resultado_dictamen_tecnico scrdt ON scrdt.id_resultado_dictamen_tecnico = sdtp.id_resultado_dictamen_tecnico " +
                "WHERE sdtp.estatus_eliminacion_logica_dictamen = true " +
                "AND (:idCatResultadoDictamen IS NULL " +
                "    OR (:idCatResultadoDictamen = 1 AND scrdt.id_resultado_dictamen_tecnico = 1) " +
                "    OR (:idCatResultadoDictamen = 2 AND scrdt.id_resultado_dictamen_tecnico = 2)) " +
                ") " +
                "SELECT DISTINCT pd.id_proveedor " +
                "FROM ProveedorData pd " +
                "LEFT JOIN TituloData td ON td.id_proveedor = pd.id_proveedor " +
                "LEFT JOIN DictamenData dd ON dd.id_proveedor = pd.id_proveedor " +
                "WHERE (:idTituloServicio IS NULL OR td.id_proveedor IS NOT NULL) ",
        nativeQuery = true)
		List<Long> obtenerIdsProveedores(
		    @Param("idGiroEmpresarial") Integer idGiroEmpresarial,
		    @Param("idTituloServicio") Integer idTituloServicio,
		    @Param("idCatResultadoDictamen") Integer idCatResultadoDictamen);

        
        @Query("SELECT t FROM TituloServicioProveedorModel t WHERE t.proveedorModel.idProveedor = :idProveedor")
        List<TituloServicioProveedorModel> obtenerTitulosPorProveedor(@Param("idProveedor") Long idProveedor);
        
        @Query(value = "WITH TituloData AS ( " +
                "SELECT " +
                "stp.id_proveedor, " +
                "stp.id_titulo_servicio_proveedor, " +
                "sst.nombre_corto AS titulo_de_servicio, " +
                "stp.vigencia, " +
                "stp.vencimiento_titulo, " +
                "stp.color_vigencia " +
                "FROM sisecofi.sscfc_titulo_servicio_proveedor stp " +
                "INNER JOIN sisecofi.sscfc_titulo_servicio sst ON sst.id_titulo_servicio = stp.id_titulo_servicio " +
                "WHERE (:idTituloServicio IS NULL OR stp.id_titulo_servicio = :idTituloServicio) " +
                "AND (:idProveedor IS NULL OR stp.id_proveedor = :idProveedor) " +
                "AND stp.estatus_eliminacion_logica_titulo = true " +
                ") " +
                "SELECT " +
                "td.id_proveedor, " +
                "td.id_titulo_servicio_proveedor, " +
                "td.titulo_de_servicio, " +
                "td.vigencia, " +
                "td.vencimiento_titulo, " +
                "td.color_vigencia " +
                "FROM TituloData td " +
                "ORDER BY td.id_proveedor ASC", 
        countQuery = "SELECT COUNT(*) FROM sisecofi.sscfc_titulo_servicio_proveedor " +
                    "WHERE (:idTituloServicio IS NULL OR id_titulo_servicio = :idTituloServicio) " +
                    "AND (:idProveedor IS NULL OR id_proveedor = :idProveedor) " +
                    "AND estatus_eliminacion_logica_titulo = true",
        nativeQuery = true)
        Page<Object[]> obtenerTitulosFiltrados(
            @Param("idTituloServicio") Integer idTituloServicio,
            @Param("idProveedor") Integer idProveedor,
            Pageable pageable);
        
        @Query(value = "WITH DictamenData AS ( " +
                "SELECT " +
                "sdtp.id_proveedor, " +
                "sdtp.id_dictamen_tecnico_proveedor, " +
                "sdtp.id_resultado_dictamen_tecnico, " +
                "sdtp.anio, " +
                "sdtp.observacion, " +
                "sdtp.responsable, " +
                "stp.id_titulo_servicio, " +
                "sst.nombre_corto AS nombre_titulo_servicio, " +
                "sdtp.orden_dictamen_proveedor " +
                "FROM sisecofi.sscfc_dictamen_tecnico_proveedor sdtp " +
                "INNER JOIN sisecofi.sscfc_titulo_servicio_proveedor stp ON sdtp.id_servicio_titulo = stp.id_titulo_servicio_proveedor " +
                "INNER JOIN sisecofi.sscfc_titulo_servicio sst ON stp.id_titulo_servicio = sst.id_titulo_servicio " +
                "WHERE (:idCatResultadoDictamen IS NULL OR sdtp.id_resultado_dictamen_tecnico = :idCatResultadoDictamen) " +
                "AND (:idProveedor IS NULL OR sdtp.id_proveedor = :idProveedor) " +
                "AND (:idTituloServicio IS NULL OR stp.id_titulo_servicio = :idTituloServicio) " +
                "AND sdtp.estatus_eliminacion_logica_dictamen = true " +
                ") " +
                "SELECT " +
                "dd.id_proveedor, " +
                "dd.id_dictamen_tecnico_proveedor, " +
                "dd.id_resultado_dictamen_tecnico, " +
                "dd.anio, " +
                "dd.observacion, " +
                "dd.responsable, " +
                "dd.id_titulo_servicio, " +
                "dd.nombre_titulo_servicio, " +
                "dd.orden_dictamen_proveedor " +
                "FROM DictamenData dd " +
                "ORDER BY dd.id_proveedor ASC", 
        countQuery = "SELECT COUNT(*) FROM sisecofi.sscfc_dictamen_tecnico_proveedor sdtp " +
                    "INNER JOIN sisecofi.sscfc_titulo_servicio_proveedor stp ON sdtp.id_servicio_titulo = stp.id_titulo_servicio_proveedor " +
                    "INNER JOIN sisecofi.sscfc_titulo_servicio sst ON stp.id_titulo_servicio = sst.id_titulo_servicio " +
                    "WHERE (:idCatResultadoDictamen IS NULL OR sdtp.id_resultado_dictamen_tecnico = :idCatResultadoDictamen) " +
                    "AND (:idProveedor IS NULL OR sdtp.id_proveedor = :idProveedor) " +
                    "AND (:idTituloServicio IS NULL OR stp.id_titulo_servicio = :idTituloServicio) " +
                    "AND sdtp.estatus_eliminacion_logica_dictamen = true",
        nativeQuery = true)
        Page<Object[]> obtenerDictamenesFiltrados(
            @Param("idCatResultadoDictamen") Integer idCatResultadoDictamen,
            @Param("idProveedor") Integer idProveedor,
            @Param("idTituloServicio") Integer idTituloServicio,
            Pageable pageable);

}
