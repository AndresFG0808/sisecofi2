package com.sisecofi.reportedocumental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.sisecofi.libreria.comunes.interceptors.SecurityFeignRequestInterceptor;
import com.sisecofi.libreria.comunes.interceptors.SecurityFeignRequestInterceptorDev;
import com.sisecofi.libreria.comunes.interceptors.TokenUtil;

import feign.RequestInterceptor;

@SpringBootApplication
@EnableFeignClients
public class SisecofiReporteApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SisecofiReporteApplication.class, args);
	}

	@Profile("produccion")
	@Bean
	public RequestInterceptor securityFeignRequestInterceptor() {
		return new SecurityFeignRequestInterceptor();
	}

	@Profile("dev")
	@Bean
	public RequestInterceptor securityFeignRequestInterceptorDev(@Autowired TokenUtil tokenUtil) {
		return new SecurityFeignRequestInterceptorDev(tokenUtil);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	private static Class<SisecofiReporteApplication> applicationClass = SisecofiReporteApplication.class;
}
