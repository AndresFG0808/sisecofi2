package com.sisecofi.admindevengados.microservicio;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
public abstract class CatalogoMicroservicio {

	private static final String PATH_INFORMACION = "catalogos-interno/catalogos/consumo-interno/informacion/";
	private final RestTemplate restTemplate;
	private final String url;

	protected CatalogoMicroservicio(RestTemplate restTemplate, String url) {
		super();
		this.restTemplate = restTemplate;
		this.url = url;
	}

	public abstract HttpEntity<Object> generarHeaders(Optional<HttpEntity<String>> obj);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T extends BaseCatalogoModel> List<T> obtenerInformacionCatalogo(int idCatalogo) {
		ResponseEntity<List> responseEntity = restTemplate.exchange(url + PATH_INFORMACION + idCatalogo, HttpMethod.GET,
				generarHeaders(Optional.empty()), List.class);
		return responseEntity.getBody();
	}

	public <T extends BaseCatalogoModel> T obtenerInformacionCatalogoId(int idCatalogo, Integer id) {
		ResponseEntity<T> responseEntity = restTemplate.exchange(url + PATH_INFORMACION + idCatalogo + "/" + id,
				HttpMethod.GET, generarHeaders(Optional.empty()), new ParameterizedTypeReference<T>() {
				});
		return responseEntity.getBody();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T extends BaseCatalogoModel> T obtenerInformacionCatalogoIdReference(int idCatalogo, Integer id,
			Object cat) {
		ResponseEntity responseEntity = restTemplate.exchange(url + PATH_INFORMACION + idCatalogo + "/" + id,
				HttpMethod.GET, generarHeaders(Optional.empty()), new ParameterizedTypeReference<>() {
				});
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		Object t = responseEntity.getBody();
		Map<String, Object> map = mapper.convertValue(t, new TypeReference<Map<String, Object>>() {
		});
		return (T) mapper.convertValue(map, cat.getClass());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T extends BaseCatalogoModel> List<T> obtenerInformacionCatalogoCampo(int idCatalogo, String json) {
		HttpEntity<String> request = new HttpEntity<>(json);
		HttpEntity<Object> obj = generarHeaders(Optional.of(request));
		ResponseEntity<List> responseEntity = restTemplate.exchange(url + PATH_INFORMACION + idCatalogo,
				HttpMethod.POST, obj, List.class);
		return responseEntity.getBody();
	}
	
	public <T extends BaseCatalogoModel> List<T> obtenerInformacionCatalogoCampoEspecifico(int idCatalogo, String json) {
		HttpEntity<String> request = new HttpEntity<>(json);
		HttpEntity<Object> obj = generarHeaders(Optional.of(request));
		ParameterizedTypeReference<List<T>> typeRef = new ParameterizedTypeReference<>() {
		};
		ResponseEntity<List<T>> responseEntity = restTemplate.exchange(url + PATH_INFORMACION + idCatalogo,
				HttpMethod.POST, obj, typeRef);
		return responseEntity.getBody();
	}
	
	public <T extends BaseCatalogoModel> T obtenerInformacionCatalogoIdEspecifico(int idCatalogo, Integer id, Class<T> responseType) {
	    ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
	            url + PATH_INFORMACION + idCatalogo + "/" + id,
	            HttpMethod.GET, generarHeaders(Optional.empty()),
	            new ParameterizedTypeReference<Map<String, Object>>() {
	            });
	    Map<String, Object> map = responseEntity.getBody();
	    if (map == null || map.isEmpty()) {
	        throw new IllegalStateException("La respuesta del catálogo está vacía o nula");
	    }
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.registerModule(new JavaTimeModule());
	    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	    return mapper.convertValue(map, responseType);
	}

	@SuppressWarnings({ "rawtypes"})
	public <T extends BaseCatalogoModel> List<T> obtenerInformacionCatalogoCampoReference(int idCatalogo, String json, Object cat) {
		HttpEntity<String> request = new HttpEntity<>(json);
		HttpEntity<Object> obj = generarHeaders(Optional.of(request));
		ResponseEntity<List> responseEntity = restTemplate.exchange(url + PATH_INFORMACION + idCatalogo,
				HttpMethod.POST, obj, List.class);
		
		 ObjectMapper mapper = new ObjectMapper()
		            .registerModule(new JavaTimeModule())
		            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		 
		 Object body = responseEntity.getBody();
		 
		 JavaType listType = mapper.getTypeFactory()
		            .constructCollectionType(List.class, (Class<?>) cat.getClass());
		 
		 return mapper.convertValue(body, listType);
	}

}
