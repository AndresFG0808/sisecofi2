package com.sisecofi.admindevengados.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sisecofi.libreria.comunes.model.dictamenes.factura.FacturaModel;

import feign.Param;

@Repository
public interface MontoSATYCCRepository  extends JpaRepository<FacturaModel, Long>{

    @Query("SELECT d FROM FacturaModel d WHERE d.idDictamen=:idDictamen")
    List<FacturaModel> findByIdDictamen (@Param("idDictamen") Long idDictamen);


    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END " +
    "FROM FacturaModel d " +
    "WHERE d.idDictamen = :idDictamen " +
    "AND (d.montoPesosSat > 0 OR d.montoPesosCC > 0)")
    Optional<Boolean> existeMontoParaDictamen(@Param("idDictamen") Long idDictamen);
    

}           
