package com.sisecofi.admingeneral.dto.adminplantillas;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObtenerPlantillaDTO {
    private String nombrePlantilla;
    private byte[] file;
}
