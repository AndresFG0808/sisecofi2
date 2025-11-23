package com.sisecofi.admindevengados.dto.solicitudpago;

import lombok.Getter;
import lombok.Setter;
import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;
import java.time.LocalDateTime;

@Getter
@Setter
public class RequestPlantillaBaseDto {

    private String tipoDocumento;

    private DictamenId dictamenId;

    private Long idContrato;

    private Long idPlantillador;

    private String oficioSolicitudPago; //se agregan al doc

    private LocalDateTime fechaSolicitud;
    
}
