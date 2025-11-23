package com.sisecofi.admindevengados.dto.solicitudpago;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;

@Getter
@Setter
public class ReferenciaPagoDto {

    private DictamenId dictamenId;

    private Long idSolicitudPago;

    private Boolean estatusPagado;

    private List<InfoReferenciaPagoDto> infoReferenciaPagoDto;
}
