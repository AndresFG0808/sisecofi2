package com.sisecofi.admindevengados.dto.solicitudpago;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoReferenciaPagoResponseDto extends  InfoReferenciaPagoDto{

    //factura
    private String comprobanteFiscal;

    //factura
    private Integer idDesglose;

}
