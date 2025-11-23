package com.sisecofi.admindevengados.util.consumer;

import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.libreria.comunes.model.dictamenes.ReferenciaPagoModel;
import com.sisecofi.libreria.comunes.model.dictamenes.SolicitudPagoModel;
import org.springframework.stereotype.Component;

@Component
public class SolicitudPagoPistasConsumer {

    public String movimientoSolicitudPagol(SolicitudPagoModel solicitudPagoModel) {
        return Constantes.getAtributosSolicitudPago()[0] + (solicitudPagoModel.getIdSolicitudPago() == null ? "null" : solicitudPagoModel.getIdSolicitudPago()) + "|" +
                Constantes.getAtributosSolicitudPago()[1] + (solicitudPagoModel.getDictamenId() == null ? "null" : solicitudPagoModel.getDictamenId()) + "|" +
                Constantes.getAtributosSolicitudPago()[2] + (solicitudPagoModel.getOficioSoliciturPago() == null ? "null" : solicitudPagoModel.getOficioSoliciturPago()) + "|" +
                Constantes.getAtributosSolicitudPago()[3] + (solicitudPagoModel.getFechaSolicitud() == null ? "null" : solicitudPagoModel.getRutaSolicitudPago()) + "|" +
                Constantes.getAtributosSolicitudPago()[4] + (solicitudPagoModel.getRutaSolicitudPago() == null ? "null" : solicitudPagoModel.getRutaSolicitudPago()) + "|" +
                Constantes.getAtributosSolicitudPago()[5] + (solicitudPagoModel.getAplicaConvenioColaboracion() == null ? "null" : solicitudPagoModel.getRutaSolicitudPago());
    }

    public String movimientoReferenciaPago(ReferenciaPagoModel referenciaPagoModel) {
        return Constantes.getAtributosReferenciaPago()[0] + (referenciaPagoModel.getIdReferenciaPago() == null ? "null" : referenciaPagoModel.getIdReferenciaPago()) + "|" +
                Constantes.getAtributosReferenciaPago()[1] + (referenciaPagoModel.getIdTipoNotificacionPago() == null ? "null" : referenciaPagoModel.getIdTipoNotificacionPago()) + "|" +
                Constantes.getAtributosReferenciaPago()[2] + (referenciaPagoModel.getIdSolicitudPago() == null ? "null" : referenciaPagoModel.getIdSolicitudPago()) + "|" +
                Constantes.getAtributosReferenciaPago()[3] + (referenciaPagoModel.getOficioNotificacionPago() == null ? "null" : referenciaPagoModel.getOficioNotificacionPago()) + "|" +
                Constantes.getAtributosReferenciaPago()[4] + (referenciaPagoModel.getFechaNotificacion() == null ? "null" : referenciaPagoModel.getFechaNotificacion()) + "|" +
                Constantes.getAtributosReferenciaPago()[5] + (referenciaPagoModel.getFolioFichaPago() == null ? "null" : referenciaPagoModel.getFolioFichaPago()) + "|" +
                Constantes.getAtributosReferenciaPago()[6] + (referenciaPagoModel.getFechaPago() == null ? "null" : referenciaPagoModel.getFechaPago()) + "|" +
                Constantes.getAtributosReferenciaPago()[7] + (referenciaPagoModel.getTipoCambioPagado() == null ? "null" : referenciaPagoModel.getTipoCambioPagado()) + "|" +
                Constantes.getAtributosReferenciaPago()[8] + (referenciaPagoModel.getPagadoNAFIN() == null ? "null" : referenciaPagoModel.getPagadoNAFIN()) + "|" +
                Constantes.getAtributosReferenciaPago()[9] + (referenciaPagoModel.getRutaFichaNAFIN() == null ? "null" : referenciaPagoModel.getRutaFichaNAFIN()) + "|" +
                Constantes.getAtributosReferenciaPago()[10] + (referenciaPagoModel.getIdDesglose() == null ? "null" : referenciaPagoModel.getIdDesglose()) + "|" +
                Constantes.getAtributosReferenciaPago()[11] + (referenciaPagoModel.getIdFactura() == null ? "null" : referenciaPagoModel.getIdFactura()) + "|" +
                Constantes.getAtributosReferenciaPago()[12] + (referenciaPagoModel.getConvenioColaboracion() == null ? "null" : referenciaPagoModel.getConvenioColaboracion());
    }

}
