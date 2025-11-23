package com.sisecofi.reportedocumental.repository.controldoc;

import org.springframework.stereotype.Service;

import com.sisecofi.reportedocumental.util.enums.ConsultaControlDocumentalEnum;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ControlDocumentalRepository {

	private final EntityManager entityManager;

	public String obtenerRuta(Integer id, int identificador) {
		String consulta = ConsultaControlDocumentalEnum.SIN_ARCHIVO.obtenerConsulta(identificador);
		log.info("Consulta obtenida: {} id:{}-identificador:{}", consulta, id, identificador);
		String ruta = (String) entityManager.createNativeQuery(consulta.replace(":id", String.valueOf(id))).getSingleResult();
		log.info("Ruta: {} id:{}-identificador:{}", ruta, id, identificador);
		return ruta;
	}

}
