package com.sisecofi.admindevengados.dto.solicitudpago;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReferenciaPagoResponseDto {

    private ReferenciaPagoConvenioDto referenciaPagoModel;
    private ReferenciaPagoConvenioDto referenciaConvenioColaboracion;
}
