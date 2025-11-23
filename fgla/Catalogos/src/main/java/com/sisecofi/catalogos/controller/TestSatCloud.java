package com.sisecofi.catalogos.controller;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.sisecofi.catalogos.microservicios.PapeleraServicio;
import com.sisecofi.catalogos.service.impl.NexusImpl;
import com.sisecofi.catalogos.util.Constantes;
import com.sisecofi.libreria.comunes.dto.CompartidoCloudModel;
import com.sisecofi.libreria.comunes.dto.PapeleraDto;
import com.sisecofi.libreria.comunes.service.PathService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
@Slf4j
public class TestSatCloud {

	private final NexusImpl impl;
	private final PapeleraServicio papeleraServicio;
	private static final String ERROR = "Error:{}";
	private final PathService pathService;

	@PutMapping("/cargar")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<String> cargar(@RequestParam("file") MultipartFile file, @RequestParam("path") String path) {
		try {

			if (!pathService.comprobarArchivo(file)) {
	            return ResponseEntity.badRequest().body("Nombre de archivo o extensión no permitida.");
	        }
			boolean archivo = impl.cargarArchivo(file.getInputStream(), path, file.getOriginalFilename());
			log.info("Archivo cargado: {},", archivo);
		} catch (Exception e) {
			log.error(ERROR);
		}
		return new ResponseEntity<>("", HttpStatus.CREATED);
	}

	@PutMapping("/compartir")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<CompartidoCloudModel> compartirArchivo(@RequestParam("path") String path) {
	    CompartidoCloudModel url = null;
	    try {
	        if (!validarRutaPermitida(path)) {
	            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	        }

	        url = impl.compartirSoloLectura(path);
 
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    return new ResponseEntity<>(url, HttpStatus.CREATED);
	}
	
	private boolean validarRutaPermitida(String inputPath) {
	    try {
	        // Directorio base permitido
	        Path baseDir = Paths.get("/ruta/compartible/permitida").toRealPath();

	        // Resolver la ruta solicitada de forma segura
	        Path requestedPath = baseDir.resolve(inputPath).normalize();

	        // Verificar que la ruta final esté dentro del directorio base
	        return requestedPath.startsWith(baseDir);
	    } catch (IOException | InvalidPathException e) {
	        return false;
	    }
	}


	@PutMapping("/eliminar")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<String> eliminar(@RequestParam("path") String path) {
		try {
			PapeleraDto dto = new PapeleraDto();
			dto.setFecha(LocalDateTime.now());
			dto.setPathNuevo(path);
			dto.setPathOriginal(path);
			dto = papeleraServicio.generarId(dto);
			impl.eliminarArchivo(path, dto.getIdPapelera());
			log.info("Archivo eliminado");
		} catch (Exception e) {
			log.error(ERROR);
		}
		return new ResponseEntity<>("", HttpStatus.CREATED);
	}

	@PostMapping("/descargar")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<byte[]> descargar(@RequestParam("path") String path) {
		try {
			InputStream obj = impl.descargarArchivo(path);
			log.info("Descargado: {}", obj);
			byte[] bytes = obj.readAllBytes();
			obj.close();
			return new ResponseEntity<>(bytes, HttpStatus.OK);
		} catch (Exception e) {
			log.error(ERROR);
		}
		return ResponseEntity.ofNullable(null);
	}

}
