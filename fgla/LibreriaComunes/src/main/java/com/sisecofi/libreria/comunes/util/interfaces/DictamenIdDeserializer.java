package com.sisecofi.libreria.comunes.util.interfaces;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;

import java.io.IOException;

public class DictamenIdDeserializer extends JsonDeserializer<DictamenId> {

    @Override
    public DictamenId deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        return null;
    }
}