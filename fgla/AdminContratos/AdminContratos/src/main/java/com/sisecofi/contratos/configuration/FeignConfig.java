package com.sisecofi.contratos.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import com.sisecofi.contratos.util.enums.ErroresEnum;
import com.sisecofi.contratos.util.exception.ContratoException;

import feign.Response;
import feign.codec.ErrorDecoder;

import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class FeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    public static class CustomErrorDecoder implements ErrorDecoder {

        @Override
        public Exception decode(String methodKey, Response response) {
            String jsonResponse = null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().asInputStream()))) {
                jsonResponse = reader.lines().collect(Collectors.joining("\n"));
            } catch (IOException e) {
                log.error("Error reading response body");
                throw new ContratoException(ErroresEnum.ERROR_BODY);
            }

            if (log.isDebugEnabled()) {
                log.debug("Response JSON: {}", jsonResponse);
            }


            return new RuntimeException("Error deserializing response: " + Optional.ofNullable(jsonResponse).orElse("No response body"));
        }
    }
}