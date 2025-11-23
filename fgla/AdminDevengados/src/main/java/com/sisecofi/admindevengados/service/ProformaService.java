package com.sisecofi.admindevengados.service;

import com.sisecofi.admindevengados.dto.*;
import com.sisecofi.admindevengados.model.SolicitudFacturaModel;

import java.util.List;

public interface ProformaService {
    List<ResumenProformaDto> obtenerResumenProforma(Long idDictamen);
    PlantillaProformaDto obtenerPlantillaProforma(Long dictamenId);
    List<ResumenProformaDto> guardarProforma(List<DescuentoDto> listDescuento);
    SolicitudFacturaResponseDto solicitudFacturaProformaGuardar(SolicitudFacturaCreateDto request);
    SolicitudFacturaResponseDto solicitudFacturaProformaActualizar(SolicitudFacturaUpdateDto request);
    List<DesgloceProformaDto> obtenerCatalogoDesglose();
    SolicitudFacturaBanderaDto buscarPorIdDictamen(Long idDictamen);
    String solicitudFaturaDescargarOficio(String path);
	SolicitudFacturaModel validarSolicitudFacrura(Long idDictamen);
}
