package com.sisecofi.proyectos.service.impl;
/*
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import com.sisecofi.proyectos.dto.EstructuraProyectoMetaDto;
import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoMetaDto;
import com.sisecofi.proyectos.service.PistaService;
import com.sisecofi.proyectos.util.consumer.ReporteProyectosConsumer;
import com.util.FactoryClass;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class ServicioReporteProyectosImplTest {

	@Mock
	private ReporteProyectosConsumer reporteProyectosConsumer;
	
	@Mock
	private ServicioProyectoImpl servicioProyectoImpl;
	
	@Mock
	private PistaService pistaService;
	
	@InjectMocks
	private ServicioReporteProyectosImpl servicioReporteProyectosImpl;

	@Test
	void obtenerReporteProyectosRegistradosTest() {
		log.info("Generando reporte");
		byte[] bytes = {};
		List<ProyectoMetaDto> lista = FactoryClass.regresarListaProyectosMeta();
		EstructuraProyectoMetaDto estructura= FactoryClass.obtenerEstructuraProyecto();
		Mockito.when(servicioProyectoImpl.buscarProyectosLista(estructura)).thenReturn(lista);
		Mockito.when(reporteProyectosConsumer.cerrarBytes()).thenReturn(bytes);
		byte[] archivo = servicioReporteProyectosImpl.obtenerReporteProyectosRegistrados(estructura);
		Assertions.assertEquals(archivo, bytes);
	}

} */
