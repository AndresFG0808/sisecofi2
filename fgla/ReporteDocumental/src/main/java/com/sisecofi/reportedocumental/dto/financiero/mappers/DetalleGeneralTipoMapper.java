package com.sisecofi.reportedocumental.dto.financiero.mappers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleGeneralTipoMapper {
    private String nombreCortoProyecto;
    private String nombreCortoContrato;
    private String numeroContrato;
    private String proveedor;
    private String periodoInicio;
    private String periodoFin;
    private String periodoControl;
    private String descripcion;
}
