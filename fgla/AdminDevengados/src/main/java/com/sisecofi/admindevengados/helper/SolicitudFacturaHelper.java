package com.sisecofi.admindevengados.helper;

import com.sisecofi.admindevengados.dto.SolicitudFacturaCreateDto;
import com.sisecofi.admindevengados.dto.SolicitudFacturaResponseDto;
import com.sisecofi.admindevengados.model.SolicitudFacturaModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoPlantillaDictamenModel;
import org.springframework.stereotype.Component;

@Component
public class SolicitudFacturaHelper {

    public SolicitudFacturaModel mapToModelToSave(SolicitudFacturaCreateDto dto, ArchivoPlantillaDictamenModel archivo) {
        SolicitudFacturaModel model = new SolicitudFacturaModel();
        model.setDictamenId(dto.idDictamen());
        model.setNoOficioSolicitud(dto.noOficioSolicitud());
        model.setFechaSolicitud(dto.fechaSolicitud().atStartOfDay());
        model.setArchivoPdf(archivo);
        model.setBanderaFactura(Boolean.FALSE);
        return model;
    }

    public SolicitudFacturaResponseDto mapToResponse(SolicitudFacturaModel model) {
        return SolicitudFacturaResponseDto.builder()
                .idSolicitudFactura(model.getIdSolicitudFactura())
                .dictamenId(model.getDictamenId())
                .noOficioSolicitud(model.getNoOficioSolicitud())
                .fechaSolicitud(model.getFechaSolicitud().toLocalDate())
                .fechaRecepcionFactura(model.getFechaRecepcionFactura() != null ? model.getFechaRecepcionFactura().toLocalDate() : null)
                .banderaFactura(model.getBanderaFactura())
                .ruta(model.getRuta())
                .build();
    }
}
