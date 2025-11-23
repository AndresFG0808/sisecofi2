package com.sisecofi.proyectos.service.impl;
/*
import static org.mockito.ArgumentMatchers.anyLong;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import com.mock.FichaTecnicaMock;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.repository.UsuarioRepository;
import com.sisecofi.libreria.comunes.dto.proyecto.FichaTecnicaResponse;
import com.sisecofi.proyectos.microservicio.CatalogoMicroservicio;
import com.sisecofi.proyectos.repository.FichaAdmonCentralRepository;
import com.sisecofi.proyectos.repository.FichaAlineacionRepository;
import com.sisecofi.proyectos.repository.FichaTecnicaRepository;
import com.sisecofi.proyectos.repository.HistoricoRepository;
import com.sisecofi.proyectos.repository.ProyectoRepository;
import com.sisecofi.proyectos.service.PistaService;
import com.sisecofi.proyectos.service.ServicioAlineacion;
import com.sisecofi.proyectos.service.ServicioHistorico;
import com.util.FactoryClass;

@SpringBootTest
@AutoConfigureMockMvc
class ServicioFichaTecnicaTest {

	@Mock
	private FichaTecnicaRepository fichaRepository;

	@Mock
	private PistaService pistaService;

	@Mock
	private ProyectoRepository proyectoRepository;

	@Mock
	private CatalogoMicroservicio catalogoMicroservicio;

	@Mock
	private HistoricoRepository historicoRepository;

	@Mock
	private FichaAlineacionRepository fichaAlineacionRepository;

	@Mock
	private FichaAdmonCentralRepository fichaAdmonCentralRepository;
	
	@Mock
	private ServicioHistorico servicioHistorico;
	
	@Mock
	private UsuarioRepository usuarioRepository;
	
	@Mock
	private ServicioAlineacion servicioAlineacion;

	@InjectMocks
	private ServicioFichaTecnicaImpl servicioFichaTecnicaImpl;

	@BeforeEach
	void setFichaMock() throws IOException {
		FichaTecnicaMock.fichaMock(fichaRepository, pistaService, proyectoRepository,
				catalogoMicroservicio, historicoRepository, fichaAlineacionRepository, fichaAdmonCentralRepository, usuarioRepository);
	}
/*
	@Test
	void obtenerFichaTest() throws IOException {
		Mockito.when(proyectoRepository.findByIdProyectoAndEstatus(1L, true))
		.thenReturn(FactoryClass.optionalProyecto());
		Mockito.when(fichaRepository.findByIdFichaTecnica(anyLong())).thenReturn(FactoryClass.optionalFicha());
		FichaTecnicaResponse esperado = FactoryClass.obtenerFichaResponse();
		FichaTecnicaResponse obtenido = servicioFichaTecnicaImpl.obtenerFicha(1L);
		Assertions.assertEquals(esperado.getFicha().getObjetivoGeneral(), obtenido.getFicha().getObjetivoGeneral(),
				"La ficha tecnica obtenida es diferente a la esperada");
	}


	@Test
	void agregarLiderTest() throws IOException {
		Boolean obtenido = servicioFichaTecnicaImpl.agregarLider(1L);
		Assertions.assertEquals(true, obtenido, "El resultado no es el esperado");
	}

	
	@Test
	void guardarFichaTest() throws IOException {
		FichaTecnicaResponse esperado = FactoryClass.obtenerFichaResponse();
		FichaTecnicaResponse obtenido = servicioFichaTecnicaImpl.guardarFicha(FactoryClass.estructuraFicha(), 1L, true);
		Assertions.assertEquals(esperado.getFicha().getObjetivoGeneral(), obtenido.getFicha().getObjetivoGeneral(),
				"La ficha tecnica obtenida es diferente a la esperada");
	}

	
	@Test
	void enlistarUsuariosTest() throws IOException {
		List<Usuario> esperado = FactoryClass.listaUsuarios();
		List<Usuario> obtenido = servicioFichaTecnicaImpl.enlistarUsuarios();
		Assertions.assertEquals(esperado.get(0).getNombre(), obtenido.get(0).getNombre(), "El resultado no es el esperado");
	}

}*/
