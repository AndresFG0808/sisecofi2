package com.sisecofi.libreria.comunes.util.enums;

import jakarta.validation.constraints.AssertFalse.List;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public enum TypeObject {

	TYPE_LIST(List.class),
	TYPE_STRING(String.class);

	private Object o;

	TypeObject(Object o) {
		this.o = o;
	}

	public Object getO() {
		return o;
	}

}
