package com.sisecofi.libreria.comunes.dto;

import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;

import lombok.Builder;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Builder
public class ResponseGeneric<T> {

	private T data;
	private DefinitionMessage msj;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMsj() {
		return msj.getMessage();
	}

	public void setMsj(DefinitionMessage msj) {
		this.msj = msj;
	} 

}
