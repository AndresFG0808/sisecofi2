package com.sisecofi.admindevengados.service.solicitudpago;

import com.sisecofi.admindevengados.dto.solicitudpago.*;
import com.sisecofi.libreria.comunes.dto.plantillador.ContenidoPlantilladorPdfReponseDto;
import com.sisecofi.libreria.comunes.model.plantillador.PlantilladorModel;
import java.util.List;


public interface SolicitudPagoService {

    SolicitudPagoResponseDto guardarSolicitudPago(SolicitudPagoDto solicitudPagoDto);

    String editarEstatusSolicitudPago(EstatusSolicitudPagoDto solicitudPagoDto);

    String guardarReferenciaPago(ReferenciaPagoDto referenciaPagoDto);

    String editarSolicitudPago(EditarSolicitudPagoDto referenciaPagoDto);

    String editarReferenciaPago(ReferenciaPagoDto referenciaPagoDto);

    ContenidoPlantilladorPdfReponseDto obtenerPlantillaBase(RequestPlantillaBaseDto request);

    List<PlantilladorModel> obtenerPlantillas();

    Boolean validarArchivoReferenciaPago(Long idReferenciaPago);

    SolicitudPagoResponseDto obtenerReferenciaPago(Long idSolicitudPago);

    List<SolicitudPagoReponseDto> obtenerSolicitudPago(ObtenerSolicitudPagoRequest request);


}
