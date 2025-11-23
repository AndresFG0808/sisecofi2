package com.sisecofi.admindevengados.repository.solicitudpago;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.admindevengados.dto.solicitudpago.SolicitudPagoInformacion;
import com.sisecofi.libreria.comunes.model.dictamenes.SolicitudPagoModel;

import java.util.List;

public interface SolicitudPagoRepository extends JpaRepository<SolicitudPagoModel, Long> {

    SolicitudPagoModel findByIdSolicitudPago(Long idSolicitudPago);

    List<SolicitudPagoModel> findByDictamenId(Long dictamenId);
    
    SolicitudPagoModel findByDictamenIdAndDictamenEstatusTrue(Long dictamenId);
    
    @Query("SELECT s.idSolicitudPago AS idSolicitudPago, " +
    	       "s.oficioSoliciturPago AS oficioSoliciturPago, " +
    	       "s.fechaSolicitud AS fechaSolicitud, " +
    	       "s.rutaSolicitudPago AS rutaSolicitudPago, " +
    	       "s.estatus AS estatus, " +
    	       "s.aplicaConvenioColaboracion AS aplicaConvenioColaboracion, " +
    	       "s.documentoGenerado AS documentoGenerado, " +
    	       "s.dictamen.idDictamen AS idDictamen, " +  
    	       "s.dictamen.idContrato AS idContrato, " +
    	       "(CASE WHEN s.dictamen.contratoModel.vigencia.catTipoMoneda.idTipoMoneda = 1 THEN false ELSE true END) AS moneda " +  
    	       "FROM SolicitudPagoModel s " +
    	       "WHERE s.idSolicitudPago = :idSolicitudPago")
    	SolicitudPagoInformacion findIdSolicitudPago(@Param("idSolicitudPago") Long idSolicitudPago);







}
