package com.sisecofi.contratos.dto;

import com.sisecofi.libreria.comunes.dto.contrato.ContratoDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContratosResponseDto {
    List<ContratoDto> contratoDtos;
    String mensaje;
}
