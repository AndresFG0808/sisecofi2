package com.sisecofi.contratos.util.consumer;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;

@Converter
public class BigDecimalMatrixToJsonConverter implements AttributeConverter<BigDecimal[][], String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(BigDecimal[][] attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting matrix to JSON", e);
        }
    }

    @Override
    public BigDecimal[][] convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, BigDecimal[][].class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to matrix", e);
        }
    }
}
