package com.sisecofi.contratos.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EliminarParticipantesDto {
    Long idContrato;
    List<Long> idsParticipantes;
}
