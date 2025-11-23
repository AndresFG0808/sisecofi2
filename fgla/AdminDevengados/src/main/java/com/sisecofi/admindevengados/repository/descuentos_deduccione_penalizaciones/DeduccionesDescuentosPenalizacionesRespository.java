package com.sisecofi.admindevengados.repository.descuentos_deduccione_penalizaciones;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sisecofi.admindevengados.model.deducciones_descuentos_penalizaciones.DeduccionesDescuentosPenalizacionesModel;

import feign.Param;

@Repository
public interface DeduccionesDescuentosPenalizacionesRespository
        extends JpaRepository<DeduccionesDescuentosPenalizacionesModel, Long> {

    @Query("SELECT COALESCE(MAX(ra.ordenDescuento), 0) FROM DeduccionesDescuentosPenalizacionesModel ra WHERE ra.dictamen.idDictamen = :dictamenId")
    Integer findMaxOrdenDescuentoByIdDictamen(@Param("dictamenId") Long dictamenId);

    Optional<DeduccionesDescuentosPenalizacionesModel> findByIdDdpAndEstatusDeduccionTrue(Long idDdp);


    List<DeduccionesDescuentosPenalizacionesModel> findByIdDictamenAndEstatusDeduccionTrueOrderByIdDdpAsc(
            Long idDictamen);

     List<DeduccionesDescuentosPenalizacionesModel> findByIdDictamen(Long idDictamen);

    @Query("SELECT SUM(d.monto) FROM DeduccionesDescuentosPenalizacionesModel d WHERE d.idDictamen = :idDictamen")
    Double sumarMontosPorIdDictamen(@Param("idDictamen") String idDictamen);
}
