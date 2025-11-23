package com.sisecofi.catalogos.utilerias;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class Util {

	private Util() {
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
