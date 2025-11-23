package com.directorio.activo.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.directorio.activo.dto.BusquedaDirectorioDto;
import com.directorio.activo.dto.PersonaDTO;
import com.directorio.activo.dto.UsuarioDirectorioDto;
import com.directorio.activo.service.DirectorioActivoService;
import com.directorio.activo.util.enums.ErroresDirectorioActivoEnum;
import com.directorio.activo.util.exception.DirectorioActivoException;
import com.google.gson.Gson;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Service
public class DirectorioActivoServiceImpl implements DirectorioActivoService {

	private static final Logger LOG = LoggerFactory.getLogger(DirectorioActivoServiceImpl.class);
	private final RestTemplate restTemplate;

	@Value("${url.directorio.activo}")
	private String url;

	public DirectorioActivoServiceImpl(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	@Override
	public List<UsuarioDirectorioDto> buscar(BusquedaDirectorioDto dto) throws DirectorioActivoException {
		try {
			List<PersonaDTO> lista = llamarServicio(dto, url);
			LOG.info("Servicio regreso: {}", lista);
			if (lista != null && !lista.isEmpty()) {
				return lista.stream().map(u -> {
					UsuarioDirectorioDto directorioDto = new UsuarioDirectorioDto();
					directorioDto.setAdministracion(u.getDescUnAdmin());
					directorioDto.setNombreCompleto(u.getNombreCompleto());
					directorioDto.setPuesto(u.getUnAdmin());
					directorioDto.setRfcCorto(u.getRfcCorto());
					directorioDto.setUnAdmin(u.getUnAdmin());
					directorioDto.setNombres(u.getNombre());
					directorioDto.setApellidoPaterno(u.getApellidoPaterno());
					directorioDto.setApellidoMaterno(u.getApellidoMaterno());
					return directorioDto;
				}).collect(Collectors.toList());
			}
			return Collections.emptyList();
		} catch (Exception e) {
			throw new DirectorioActivoException(ErroresDirectorioActivoEnum.ERROR_DIRECTORIO_ACTIVO, e);
		}
	}

	private List<PersonaDTO> llamarServicio(BusquedaDirectorioDto body, String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		Gson gson = new Gson();
		String jsonBody = gson.toJson(body);
		HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
		ResponseEntity<List<PersonaDTO>> response = restTemplate.exchange(url, HttpMethod.POST, entity,
				new ParameterizedTypeReference<List<PersonaDTO>>() {
				});
		return response.getBody();
	}

}
