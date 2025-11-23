package com.sisecofi.contratos.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Configuration
@EnableMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
@Profile("produccion")
public class SecurityConfiguration {

}
