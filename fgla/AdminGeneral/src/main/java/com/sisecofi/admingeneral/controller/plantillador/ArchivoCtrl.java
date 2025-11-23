package com.sisecofi.admingeneral.controller.plantillador;

import java.util.HashMap;
import java.util.Map;

import com.sisecofi.admingeneral.dto.plantillador.HtmlExcelListDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sisecofi.admingeneral.dto.plantillador.RequestPlantilla;
import com.sisecofi.admingeneral.service.plantillador.HtmlWordService;
import com.sisecofi.admingeneral.util.Constantes;
import com.sisecofi.admingeneral.util.ConstantesPlantillador;
import com.sisecofi.libreria.comunes.service.PathService;
import com.sisecofi.libreria.comunes.util.anotaciones.ConsumoInterno;

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
public class ArchivoCtrl {

	private final HtmlWordService htmlWordService;
	@Autowired
	private final PathService pathService;

	@SuppressWarnings("rawtypes")
	@PostMapping(path = ConstantesPlantillador.PATH_BASE
			+ "/guardar-imagen", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity generarId(@RequestParam("upload") MultipartFile model) {
		if (!pathService.comprobarArchivo(model)) {
            return ResponseEntity.badRequest().body(null);
        }
		log.info("Imagene: {}", model.getOriginalFilename());
		Map<String, Object> map = new HashMap<>();
		map.put("key1", "value1");
		map.put("results", "");
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@PostMapping("/plantillador/cargar")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<byte[]> cargarDocx(@RequestBody RequestPlantilla plantilla) {
		return new ResponseEntity<>(htmlWordService.convertirHmltWord(plantilla), HttpStatus.OK);
	}
	
	@PostMapping("/plantillador/cargar-pdf")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<byte[]> cargarPdf(@RequestBody RequestPlantilla plantilla) {
		return new ResponseEntity<>(htmlWordService.convertirHmltPdf(plantilla), HttpStatus.OK);
	}

	@PostMapping("/plantillador/generar-pdf")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<byte[]> generarPdf(@RequestBody RequestPlantilla plantilla) {
		return new ResponseEntity<>(htmlWordService.convertirHmltPdf(plantilla), HttpStatus.OK);
	}
	@PostMapping("/plantillador/cierre-rcp/html/pdf")
	@ConsumoInterno
	public ResponseEntity<ByteArrayResource> cierreProyecto(@RequestBody HtmlExcelListDto inputs) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "pdf"));
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=HtmlToPdf.pdf");
		return new ResponseEntity<>(new ByteArrayResource(htmlWordService.cierreProyectoPdf(inputs)), headers, HttpStatus.OK);
	}

}
