package com.sisecofi.admindevengados.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sisecofi.admindevengados.microservicio.FeignClientInterceptor;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor feignClientInterceptor(HttpServletRequest request) {
        return new FeignClientInterceptor(request);
    }
}
