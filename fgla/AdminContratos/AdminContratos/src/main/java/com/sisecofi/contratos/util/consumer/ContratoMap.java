package com.sisecofi.contratos.util.consumer;

import com.sisecofi.libreria.comunes.dto.contrato.ContratoDto;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class ContratoMap implements BiFunction<ContratoModel,Integer, ContratoDto> {

    @Override
    public ContratoDto apply(ContratoModel contratoModel, Integer integer) {
        return null;
    }
}
