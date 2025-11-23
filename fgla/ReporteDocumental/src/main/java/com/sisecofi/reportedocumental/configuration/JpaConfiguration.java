package com.sisecofi.reportedocumental.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Configuration
@ComponentScans(value = { @ComponentScan(basePackages = "com.sisecofi.libreria.comunes.*"),
		@ComponentScan(basePackages = "com.sisecofi.libreria.comunes.*") })
@EnableJpaRepositories(basePackages = { "com.sisecofi.libreria.comunes.*", "com.sisecofi.reportedocumental.*" })
@EntityScan(basePackages = { "com.sisecofi.libreria.comunes.*", "com.sisecofi.reportedocumental.*" })
public class JpaConfiguration {

}
