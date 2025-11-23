package com.sisecofi.admindevengados.util.consumer;

public class CfdiException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CfdiException(String message) {
        super(message);
    }

    public CfdiException(String message, Throwable cause) {
        super(message, cause);
    }

}
