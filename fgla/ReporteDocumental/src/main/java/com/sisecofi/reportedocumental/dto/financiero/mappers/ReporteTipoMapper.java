package com.sisecofi.reportedocumental.dto.financiero.mappers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReporteTipoMapper {
    private DetalleGeneralTipoMapper detalleGeneral;
    private DetalleFacturaMapper detalleFactura;
    private DetalleDeduccionMapper detalleDeduccion;
    private DetallePenalizacionMapper detallePenalizacion;
    private DetalleReintegroMapper detalleReintegro;
}
