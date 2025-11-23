package com.sisecofi.admindevengados.dto.deducciones_descuentos_penalizaciones_dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Data
@AllArgsConstructor
public class ValidarDictamenResponseDto {
    private  String mensaje;
    private LocalDateTime ultimaModificacion;
}
