package com.sisecofi.reportedocumental.configuration;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@PropertySource("file:/sisecofi/configuracion-reportes.properties")
@ConfigurationPropertiesScan
@Configuration
public class PropertiesConfiguration {

}
