package com.sisecofi.catalogos.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.catalogos.dto.CatalogoMetaDto;
import com.sisecofi.catalogos.dto.ReponseCatalogo;
import com.sisecofi.catalogos.service.ServicioCatalogo;
import com.sisecofi.catalogos.util.Constantes;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.util.sesion.Session;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@Slf4j
@RequiredArgsConstructor
public class CatalogoCtrl {

	private final ServicioCatalogo catalogo;
	protected final Session session;

	@GetMapping("/catalogos")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario")
	public ResponseEntity<List<CatalogoMetaDto>> catalogos() {
		List<CatalogoMetaDto> lista = catalogo.obtenerCatalogos();
		lista.stream().forEach(response -> {
			response.add(Link.of(WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder.methodOn(CatalogoCtrl.class)
							.metadataCatalogoAlta(response.getIdCatalogo()))
					.withRel(Constantes.META_NEW).toUri().getPath()).withName(Constantes.META_NEW));
			response.add(Link.of(WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder.methodOn(CatalogoCtrl.class)
							.obtenerInformacionCatalogo(response.getIdCatalogo(), ""))
					.withRel(Constantes.ALL_DATA).toUri().getPath()).withName(Constantes.ALL_DATA));
		});
		log.info("Consultando: {}", session.retornarNombreUsuario());
		log.info("Consultando: {}", session.retornarUsuario());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@GetMapping("/catalogos-complementarios")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<List<CatalogoMetaDto>> catalogosComplementarios() {
		List<CatalogoMetaDto> lista = catalogo.obtenerCatalogosComplementarios();
		lista.stream().forEach(response -> {
			response.add(Link.of(WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder.methodOn(CatalogoCtrl.class)
							.metadataCatalogoAlta(response.getIdCatalogo()))
					.withRel(Constantes.META_NEW).toUri().getPath()).withName(Constantes.META_NEW));
			response.add(Link.of(WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder.methodOn(CatalogoCtrl.class)
							.obtenerInformacionCatalogo(response.getIdCatalogo(), ""))
					.withRel(Constantes.ALL_DATA).toUri().getPath()).withName(Constantes.ALL_DATA));
		});
		log.info("Consultando complementarios: {}", session.retornarNombreUsuario());
		log.info("Consultando complementarios: {}", session.retornarUsuario());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/catalogos/alta-meta/{idCatalogo}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<ReponseCatalogo> metadataCatalogoAlta(@PathVariable("idCatalogo") int idCatalogo) {
		ReponseCatalogo response = new ReponseCatalogo();
		Map<String, Object> data = catalogo.metaCatalogo(idCatalogo, false);
		CatalogoMetaDto cat = (CatalogoMetaDto) data.get(Constantes.METADATA);
		data.remove(Constantes.METADATA);
		cat.add(Link.of(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(CatalogoCtrl.class).guardarCatalogo(cat.getIdCatalogo(), ""))
				.withSelfRel().toUri().getPath()).withName(Constantes.SAVE));
		Map<Integer, Object> hijos = ((Map<Integer, Object>) data.get(Constantes.HIJO));
		int idCatalogoComplementario = 0;
		for (Entry<Integer, Object> entry : hijos.entrySet()) {
			Integer llave = entry.getKey();
			response.add(Link.of(WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder.methodOn(CatalogoCtrl.class).obtenerInformacionCatalogo(llave, ""))
					.withRel(Constantes.ID + llave).toUri().getPath()).withName(Constantes.ALL_DATA));
			idCatalogoComplementario = llave;
		}
		response.setHijos(hijos.size());
		if (hijos.size() == 1) {
			response.setIdCatalogoComplementario(idCatalogoComplementario);
		}
		data.remove(Constantes.HIJO);
		response.setCatalogoMetaDto(cat);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/catalogos/guardar/{idCatalogo}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<BaseCatalogoModel> guardarCatalogo(@PathVariable("idCatalogo") int idCatalogo,
			@RequestBody String json) {
		BaseCatalogoModel obj = catalogo.guardarCatalogo(idCatalogo, json);
		obj.add(Link.of(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(CatalogoCtrl.class).obtenerInformacionCatalogo(idCatalogo, ""))
				.withRel(Constantes.ALL_DATA).toUri().getPath()).withName(Constantes.ALL_DATA));
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@PostMapping("/catalogos/actualizar/{idCatalogo}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<BaseCatalogoModel> actualizarCatalogo(@PathVariable("idCatalogo") int idCatalogo,
			@RequestBody String json) {
		BaseCatalogoModel obj = catalogo.actualizarCatalogo(idCatalogo, json);
		obj.add(Link.of(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(CatalogoCtrl.class).obtenerInformacionCatalogo(idCatalogo, ""))
				.withRel(Constantes.ALL_DATA).toUri().getPath()).withName(Constantes.ALL_DATA));
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@GetMapping("/catalogos/informacion/{idCatalogo}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public <T extends BaseCatalogoModel> ResponseEntity<List<T>> obtenerInformacionCatalogo(
			@PathVariable("idCatalogo") int idCatalogo,
			@RequestParam(value = "filtro", required = false, defaultValue = "id") String filtro) {
		List<T> informacion = catalogo.obtenerInformacion(idCatalogo, filtro);
		informacion.forEach(data -> {
			data.add(Link
					.of(WebMvcLinkBuilder
							.linkTo(WebMvcLinkBuilder.methodOn(CatalogoCtrl.class).actualizarCatalogo(idCatalogo, ""))
							.withRel(String.valueOf(Constantes.ID + idCatalogo)).toUri().getPath())
					.withName(Constantes.UPDATE));

			data.add(Link
					.of(WebMvcLinkBuilder
							.linkTo(WebMvcLinkBuilder.methodOn(CatalogoCtrl.class).metadataCatalogoAlta(idCatalogo))
							.withRel(String.valueOf(Constantes.ID + idCatalogo)).toUri().getPath())
					.withName(Constantes.META_NEW));
		});
		return new ResponseEntity<>(informacion, HttpStatus.OK);
	}

	@GetMapping("/catalogos/informacion/{idCatalogo}/{id}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<BaseCatalogoModel> obtenerInformacionCatalogoId(@PathVariable("idCatalogo") int idCatalogo,
			@PathVariable("id") Integer id) {
		return new ResponseEntity<>(catalogo.obtenerInformacionId(idCatalogo, id), HttpStatus.OK);
	}

	@GetMapping("/catalogos/informacion/meta/{idCatalogo}/{id}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<Map<String, Object>> obtenerInformacionCatalogoMeta(
			@PathVariable("idCatalogo") int idCatalogo, @PathVariable("id") Integer id) {
		return new ResponseEntity<>(catalogo.obtenerInformacionMeta(idCatalogo, id), HttpStatus.OK);
	}

}
