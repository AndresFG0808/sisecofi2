package com.sisecofi.admindevengados.dto.solicitudpago;

import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolicitudPagoPlantillaDto {
    private Long idContrato;
    private DictamenId idDictamen;
    private String header;
    private String footer;
    private String contenido;
}
