package com.sisecofi.admindevengados.dto.deducciones_descuentos_penalizaciones_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArchivoDto {
    private String path;
    private String nombre;
    private boolean obligatorio;
}
