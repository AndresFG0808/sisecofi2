package com.sisecofi.admindevengados.dto.deducciones_descuentos_penalizaciones_dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProformaArchivoRequestDto {
    private String tipoArchivo;
    private Integer idSubPlantillador;
    private Long idDictamen;
    private Boolean plantilla;
}
