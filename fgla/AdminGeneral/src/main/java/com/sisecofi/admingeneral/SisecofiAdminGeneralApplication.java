package com.sisecofi.admingeneral;

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
//@ComponentScan(basePackages = {"com.webservice.cfdi","com.sisecofi"})
@EnableFeignClients
public class SisecofiAdminGeneralApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SisecofiAdminGeneralApplication.class, args);
	}

	@Profile("produccion")
	@Bean
	public RequestInterceptor securityFeignRequestInterceptor() {
		return new SecurityFeignRequestInterceptor();
	}

	
	@Profile("dev")
	@Bean
	public RequestInterceptor securityFeignRequestInterceptorDev(@Autowired(required = true) TokenUtil tokenUtil) {
		return new SecurityFeignRequestInterceptorDev(tokenUtil);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	private static Class<SisecofiAdminGeneralApplication> applicationClass = SisecofiAdminGeneralApplication.class;

}
