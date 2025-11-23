package com.sisecofi.catalogos;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.sisecofi.catalogos.service.ServicioCatalogo;
import com.sisecofi.catalogos.service.impl.ReporteServiceImpl;
import com.sisecofi.catalogos.util.consumer.ReporteCatConsumer;
import com.sisecofi.catalogos.utilerias.FactoryClass;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
class CatalogoReporteTest {

	@Mock
	private ReporteCatConsumer catConsumer;

	@Mock
	private ServicioCatalogo servicioCatalogo;

	@InjectMocks
	private ReporteServiceImpl reporteServiceImpl;

	@Test
	void catalogos() {
		log.info("Generando reporte");
		byte[] bytes = {};
		List<BaseCatalogoModel> lista = FactoryClass.regresarListaCatalogos();
		Mockito.when(servicioCatalogo.obtenerInformacionReporte(1)).thenReturn(lista);
		Mockito.when(catConsumer.cerrarBytes()).thenReturn(bytes);
		byte[] archivo = reporteServiceImpl.obtenerReporte(1);
		Assertions.assertNotNull(archivo);
	}

}
