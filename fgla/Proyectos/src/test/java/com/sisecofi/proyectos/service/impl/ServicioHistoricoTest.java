package com.sisecofi.proyectos.service.impl;
/*
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyBoolean;
import java.io.IOException;
import java.util.Base64;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.sisecofi.proyectos.repository.FichaTecnicaRepository;
import com.sisecofi.proyectos.repository.HistoricoRepository;
import com.sisecofi.proyectos.util.consumer.ReporteHistoricoConsumer;
import com.util.FactoryClass;
import com.sisecofi.libreria.comunes.model.proyectos.HistoricoModel;

@SpringBootTest
@AutoConfigureMockMvc
class ServicioHistoricoTest {

	@Mock
	private HistoricoRepository historicoRepository;
	
	@Mock
	private FichaTecnicaRepository fichaRepository;
	
	@Mock
	private ReporteHistoricoConsumer consumer;

	@InjectMocks
	private ServicioHistoricoImpl servicioHistoricoImpl;

	@Test
	void eliminarHistoricoTest() throws IOException {
		Mockito.when(fichaRepository.existsById(anyLong())).thenReturn(true);
		Mockito.when(fichaRepository.findByIdFichaTecnica(anyLong())).thenReturn(FactoryClass.optionalFicha());
		Mockito.when(historicoRepository.findByIdHistoricoAndEstatusHistorico(anyLong(), anyBoolean())).thenReturn(FactoryClass.historicoOptional());
		boolean obtenido = servicioHistoricoImpl.eliminarHistorico(1L);
		Assertions.assertEquals(true, obtenido, "El resultado no es el esperado al eliminar Historico");
	}
	
	@Test
	void agregarHistoricosTest() throws IOException {
		Mockito.when(fichaRepository.existsById(anyLong())).thenReturn(true);
		Mockito.when(fichaRepository.findByIdFichaTecnica(anyLong())).thenReturn(FactoryClass.optionalFicha());
		Set<HistoricoModel> historicos = new HashSet<>(FactoryClass.obtenerHistoricoLista());
		Mockito.when(historicoRepository
				.findTopByIdFichaTecnicaAndEstatusHistoricoAndEstatusOrderByIdHistoricoDesc(1L, true, false)).thenReturn(FactoryClass.historicoOptional());
		Mockito.when(historicoRepository
				.findTopByIdFichaTecnicaAndEstatusHistoricoAndEstatusOrderByIdHistoricoDesc(1L, true, true)).thenReturn(FactoryClass.historicoOptionalNuevo());
		boolean obtenido = servicioHistoricoImpl.agregarHistoricos(historicos, 1L);
		Assertions.assertEquals(true, obtenido, "Ocurrio un error al guardar");
	}
	
	@Test
	void agregarHistoricosNuevaFichaTest() throws IOException {
		Mockito.when(fichaRepository.existsById(anyLong())).thenReturn(true);
		Mockito.when(fichaRepository.findByIdFichaTecnica(anyLong())).thenReturn(FactoryClass.optionalFicha());
		Set<HistoricoModel> historicos = new HashSet<>(FactoryClass.obtenerHistoricoLista());
		Mockito.when(historicoRepository
				.findTopByIdFichaTecnicaAndEstatusHistoricoAndEstatusOrderByIdHistoricoDesc(1L, true, false)).thenReturn(Optional.empty());
		Mockito.when(historicoRepository
				.findTopByIdFichaTecnicaAndEstatusHistoricoAndEstatusOrderByIdHistoricoDesc(1L, true, true)).thenReturn(FactoryClass.historicoOptionalNuevo());
		boolean obtenido = servicioHistoricoImpl.agregarHistoricos(historicos, 1L);
		Assertions.assertEquals(true, obtenido, "Ocurrio un error al guardar cuando la ficha tecnica es nueva");
	}
	
	
	 @Test
	void generarReporteHistoricoTest() {
		 Mockito.when(fichaRepository.existsById(anyLong())).thenReturn(true);
		Mockito.when(fichaRepository.findByIdFichaTecnica(anyLong())).thenReturn(FactoryClass.optionalFicha());
		byte[] bytes = {};
		Mockito.when(fichaRepository.findByIdProyecto(anyLong())).thenReturn(FactoryClass.optionalFicha());
		Set<HistoricoModel> historicos= new HashSet<>();
		Mockito.when(historicoRepository.findByIdFichaTecnicaAndEstatusHistorico(1L, true)).thenReturn(historicos);
		Mockito.when(consumer.cerrarBytes()).thenReturn(bytes);
		String obtenido = servicioHistoricoImpl.generarReporteHistorico(1L);
		String esperado = Base64.getEncoder().encodeToString(bytes);
		Assertions.assertEquals(esperado, obtenido);
	}
}*/
