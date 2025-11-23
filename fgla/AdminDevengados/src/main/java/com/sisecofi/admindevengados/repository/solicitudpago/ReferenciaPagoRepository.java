package com.sisecofi.admindevengados.repository.solicitudpago;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sisecofi.admindevengados.dto.solicitudpago.ReferenciaPagoConvenioInformacion;
import com.sisecofi.admindevengados.dto.solicitudpago.ReferenciaPagoMontos;
import com.sisecofi.libreria.comunes.model.dictamenes.ReferenciaPagoModel;

import feign.Param;

import java.util.List;

public interface ReferenciaPagoRepository extends JpaRepository<ReferenciaPagoModel, Long> {

    Integer countByIdFacturaAndIdSolicitudPagoAndEstatusTrue(Long idFactura, Long idSolicitudPago);

    ReferenciaPagoModel findByIdReferenciaPagoAndEstatusTrue(Long idReferenciaPago);

    @Query("SELECT r FROM ReferenciaPagoModel r WHERE r.idSolicitudPago = :idSolicitudPago AND r.facturaModel.catEstatusFactura.nombre <> 'Cancelado'")
    List<ReferenciaPagoModel> findByIdSolicitudPagoAndFacturaModelCatEstatusFacturaNombreNotCancelado(@Param("idSolicitudPago") Long idSolicitudPago);
    
    List<ReferenciaPagoModel> findByIdFacturaAndEstatusTrue(Long idFactura);
    
    @Query("SELECT COALESCE(r.tipoCambioPagado, 0) AS tipoCambioPagado, COALESCE(r.pagadoNAFIN, 0) AS pagadoNAFIN " +
    	       "FROM ReferenciaPagoModel r " +
    	       "WHERE r.idFactura = :idFactura AND r.estatus = true AND r.convenioColaboracion = :convenio")
    	ReferenciaPagoMontos findReferenciaByFacturaAndConvenio(@Param("idFactura") Long idFactura, @Param("convenio") boolean convenio);



   List<ReferenciaPagoModel> findByIdFacturaAndEstatus(Long idFactura,Boolean estatus);

   List<ReferenciaPagoModel> findByIdFacturaAndConvenioColaboracionTrue(Long idFactura);
   
   @Query("SELECT r.idReferenciaPago AS idReferenciaPago, " +
	       "r.idTipoNotificacionPago AS idTipoNotificacionPago, " +
	       "r.idSolicitudPago AS idSolicitudPago, " +
	       "r.oficioNotificacionPago AS oficioNotificacionPago, " +
	       "r.folioFichaPago AS folioFichaPago, " +
	       "r.fechaPago AS fechaPago, " +
	       "r.fechaNotificacion AS fechaNotificacion, " +
	       "r.tipoCambioPagado AS tipoCambioPagado, " +
	       "r.pagadoNAFIN AS pagadoNAFIN, " +
	       "r.rutaFichaNAFIN AS rutaFichaNAFIN, " +
	       "r.idDesglose AS idDesglose, " +
	       "r.estatusPagado AS estatusPagado, " +
	       "r.convenioColaboracion AS convenioColaboracion " +
	       "FROM ReferenciaPagoModel r " +
	       "WHERE r.idFactura = :idFactura AND r.estatus = :estatus")
	List<ReferenciaPagoConvenioInformacion> findConveniosByIdFacturaAndEstatus(@Param("idFactura") Long idFactura, 
	                                                                          @Param("estatus") Boolean estatus);


}
