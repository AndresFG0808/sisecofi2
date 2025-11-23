package com.sisecofi.libreria.comunes.security;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.sisecofi.libreria.comunes.dto.UsernameToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Slf4j
public class CustomFilterDev extends GenericFilterBean {

	private final UsernameToken usernameToken;

	public CustomFilterDev(UsernameToken usernameToken) {
		super();
		this.usernameToken = usernameToken;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		setUpSpringAuthentication(usernameToken);
		chain.doFilter(request, response);
	}

	private void setUpSpringAuthentication(UsernameToken usernameToken) {
		log.info("Usuario seteado: {}", usernameToken);
		SecurityContextHolder.getContext().setAuthentication(usernameToken);
	}
}
