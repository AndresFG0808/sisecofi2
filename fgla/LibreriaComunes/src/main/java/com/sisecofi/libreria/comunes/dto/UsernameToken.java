package com.sisecofi.libreria.comunes.dto;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * Clase donde regresa el usuario en la sesión
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class UsernameToken extends AbstractAuthenticationToken implements Authentication {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final transient Object principal;
	private final transient Object credentials;
	private final List<String> scopes;

	public UsernameToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities,
			List<String> scopes) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		this.scopes = scopes;
		super.setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return this.credentials;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	public List<String> getScopes() {
		return scopes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(principal);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
	    if (obj == null) {
	        return false;
	    }
	    if (this == obj) {
	        return true;
	    }
	    if (!super.equals(obj) || getClass() != obj.getClass()) {
	        return false;
	    }
	    UsernameToken other = (UsernameToken) obj;
	    return Objects.equals(principal, other.principal);
	}
	
	
	@Override
	public String toString() {
		return "UsernameToken [principal=" + principal + ", credentials=" + credentials + ", scopes=" + scopes
				+ ", isAuthenticated()=" + isAuthenticated() + "]";
	}

}
