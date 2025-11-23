package com.sisecofi.libreria.comunes.service;

import java.util.Optional;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface CrudSevice<S> {

	S save(S entity);

	Optional<S> findById(Long id);

	Iterable<S> findAll();
}
