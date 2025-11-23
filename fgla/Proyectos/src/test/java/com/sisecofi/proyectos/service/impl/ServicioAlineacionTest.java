package com.sisecofi.proyectos.service.impl;
/*
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import java.io.IOException;
import java.util.Base64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import com.sisecofi.proyectos.repository.FichaAlineacionRepository;
import com.sisecofi.proyectos.repository.FichaTecnicaRepository;
import com.sisecofi.proyectos.util.consumer.ReporteAlineacionConsumer;
import com.util.FactoryClass;
import com.sisecofi.proyectos.microservicio.CatalogoMicroservicio;

@SpringBootTest
@AutoConfigureMockMvc
class ServicioAlineacionTest {

	@Mock
	private FichaAlineacionRepository fichaAlineacionRepository;
	
	@Mock
	private FichaTecnicaRepository fichaRepository;
	
	@Mock
	private ReporteAlineacionConsumer consumer;
	
	@Mock
	private CatalogoMicroservicio catalogoMicroservicio;

	@InjectMocks
	private ServicioAlineacionImpl servicioAlineacionImpl;

	@Test
	void eliminarAlineacionTest() throws IOException {
		Mockito.when(fichaRepository.existsById(anyLong())).thenReturn(true);
		Mockito.when(fichaRepository.findByIdFichaTecnica(anyLong())).thenReturn(FactoryClass.optionalFicha());
		Mockito.when(fichaAlineacionRepository.findById(anyLong())).thenReturn(FactoryClass.getAlineacion());
		boolean obtenido = servicioAlineacionImpl.eliminarAlineacion(1L);
		Assertions.assertEquals(true, obtenido, "El resultado no es el esperado al eliminar Historico");
	}

	@Test
	void agregarHistoricosTest() throws IOException {
		Mockito.when(fichaRepository.existsById(anyLong())).thenReturn(true);
		Mockito.when(fichaRepository.findByIdFichaTecnica(anyLong())).thenReturn(FactoryClass.optionalFicha());
		Mockito.when(catalogoMicroservicio.obtenerInformacionCatalogoId(anyInt(), anyInt()))
		.thenReturn(FactoryClass.obtenerEstatus());
		boolean obtenido = servicioAlineacionImpl.agregarAlineaciones(FactoryClass.obtenerAlineacionesRequest(), 1L);
		Assertions.assertEquals(true, obtenido, "Ocurrio un error al guardar");
	}

	@Test
	void generarReporteAlineacionTest() {
		Mockito.when(fichaRepository.existsById(anyLong())).thenReturn(true);
		byte[] bytes = {};
		Mockito.when(fichaRepository.findByIdFichaTecnica(anyLong())).thenReturn(FactoryClass.optionalFicha());
		Mockito.when(fichaRepository.findByIdProyecto(anyLong())).thenReturn(FactoryClass.optionalFicha());
		Mockito.when(consumer.cerrarBytes()).thenReturn(bytes);
		String obtenido = servicioAlineacionImpl.generarReporteAlineacion(1L);
		String esperado = Base64.getEncoder().encodeToString(bytes);
		Assertions.assertEquals(esperado, obtenido);
	}

}*/
