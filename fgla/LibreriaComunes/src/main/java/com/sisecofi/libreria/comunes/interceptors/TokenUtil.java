package com.sisecofi.libreria.comunes.interceptors;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.util.enums.ErroresSistema;
import com.sisecofi.libreria.comunes.util.exception.DescargaArchivoException;
import com.sisecofi.libreria.comunes.util.exception.ErrorSistemaException;
import com.sisecofi.libreria.comunes.util.sesion.Session;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Component
@RequiredArgsConstructor
public class TokenUtil {

    private static final String BASE_SECRET = "ClaveSegura";

    private final Session session;

    private static String generateDerivedSecret() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(BASE_SECRET.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new DescargaArchivoException("Error al generar el secreto derivado", e);
        }
    }

    public String generarToken() {
        Optional<Usuario> usuario = session.retornarUsuario();
        if (usuario.isPresent()) {
            String roles = usuario.get().getRolModels().stream()
                    .map(e -> e.getNombre())
                    .collect(Collectors.joining(","));
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(roles);

            String derivedSecret = generateDerivedSecret();

            String token = Jwts.builder()
                    .setId("tableroapp")
                    .setSubject(usuario.get().getUserName())
                    .claim("user_name", usuario.get().getUserName())
                    .claim("authorities",
                            grantedAuthorities.stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .toList())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 600000)) // 10 minutos
                    .signWith(SignatureAlgorithm.HS512, derivedSecret.getBytes())
                    .compact();
            return "Bearer " + token;
        }
        throw new ErrorSistemaException(ErroresSistema.ERROR_AL_GENERAR_TOKEN_DEV);
    }
}