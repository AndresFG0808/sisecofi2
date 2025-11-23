package com.sisecofi.admindevengados.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sisecofi.admindevengados.model.DictaminadoModel;

@Repository
public interface BanderaPagadoFacturaRespository extends JpaRepository<DictaminadoModel, Long> {

    @Query("SELECT d FROM DictaminadoModel d WHERE d.idDictamen = :idDictamen")
    List<DictaminadoModel> findByIdDictamen(@Param("idDictamen") Long idDictamen);

    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END " +
            "FROM DictaminadoModel d " +
            "WHERE d.idDictamen = :idDictamen " +
            "AND (d.cantidadServiciosSat > 0 OR d.cantidadServiciosCc > 0)")
    Optional<Boolean> existeServiciosParaDictamen(@Param("idDictamen") Long idDictamen);

}
