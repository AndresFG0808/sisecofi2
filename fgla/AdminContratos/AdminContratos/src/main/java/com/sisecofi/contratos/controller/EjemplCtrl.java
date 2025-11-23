package com.sisecofi.contratos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.contratos.util.Constantes;


/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
public class EjemplCtrl {

	@GetMapping("/test")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<String> prueba1() {
		return new ResponseEntity<>("test", HttpStatus.OK);
	}

	@PutMapping("/test")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<String> prueba2() {
		return new ResponseEntity<>("test", HttpStatus.CREATED);
	}

	@PostMapping("/test")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<String> prueba3() {
		return new ResponseEntity<>("test", HttpStatus.OK);
	}

	@GetMapping("/test4")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<String> prueba4() {
		return new ResponseEntity<>("test", HttpStatus.OK);
	}

	@GetMapping("/test5")
	public ResponseEntity<String> prueba5() {
		return new ResponseEntity<>("test", HttpStatus.OK);
	}

}
