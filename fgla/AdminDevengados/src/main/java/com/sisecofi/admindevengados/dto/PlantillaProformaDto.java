package com.sisecofi.admindevengados.dto;

import com.sisecofi.libreria.comunes.dto.contrato.ContratoDto;
import com.sisecofi.libreria.comunes.dto.dictamen.dictamenDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PlantillaProformaDto {
    private dictamenDto dictamen;
    List<ResumenProformaDto> resumenProformaDtoList;
    List<DictaminadoDto> serviciosDictaminados;
    ContratoDto contrato;
    List<ObtenerPenaContractualDto> deduccionesDtoList;
}
