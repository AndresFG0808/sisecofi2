package com.sisecofi.libreria.comunes.service;

import org.springframework.web.multipart.MultipartFile;


/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface PathService {

	boolean comprobarArchivo( MultipartFile file);
}
