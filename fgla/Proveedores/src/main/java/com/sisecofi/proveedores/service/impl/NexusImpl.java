package com.sisecofi.proveedores.service.impl;

import org.springframework.beans.factory.annotation.Value;

import com.sisecofi.libreria.comunes.service.impl.NexusServiceImpl;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class NexusImpl extends NexusServiceImpl {

	public NexusImpl(@Value("${sat.cloud.server.name}") String server, @Value("${sat.cloud.server.port}") int port,
			@Value("${sat.cloud.server.user}") String user, @Value("${sat.cloud.server.pass}") String pass) {
		super();
	}

}
