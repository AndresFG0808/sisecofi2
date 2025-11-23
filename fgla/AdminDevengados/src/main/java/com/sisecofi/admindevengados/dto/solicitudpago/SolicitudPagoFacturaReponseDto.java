package com.sisecofi.admindevengados.dto.solicitudpago;

import com.sisecofi.libreria.comunes.model.dictamenes.factura.FacturaModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolicitudPagoFacturaReponseDto extends FacturaModel{
    private  Long idReferenciaPago;

}
