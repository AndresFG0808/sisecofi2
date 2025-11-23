package com.sisecofi.proyectos.configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.sisecofi.libreria.comunes.dto.UsernameToken;
import com.sisecofi.libreria.comunes.model.usuario.RolModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.repository.UsuarioRepository;
import com.sisecofi.libreria.comunes.util.exception.UserNotFoundException;
import com.sisecofi.proyectos.util.enums.ErroresEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Configuration
@Profile("dev")
@Slf4j
public class UserDevConfiguration {

	@Bean
	public UsernameToken token(@Value("${user.solo.dev}") String user, @Value("${user.solo.dev.scopes}") String scopes,
			@Autowired UsuarioRepository repository) {
		Optional<Usuario> optional = repository.findByUserName(user);
		if (optional.isPresent()) {
			String[] sc = scopes.split(",");
			List<String> authorities = optional.get().getRolModels().stream().map(RolModel::getNombre)
					.toList();
			UsernameToken auth = new UsernameToken(optional.get().getUserName(), null,
					authorities.stream().map(SimpleGrantedAuthority::new).toList(),
					Arrays.asList(sc));
			log.info("Usuario seteado: {}", auth);
			return auth;
		}
		throw new UserNotFoundException(ErroresEnum.ERROR_MSJ_SIN_USUARIO);
	}

}
