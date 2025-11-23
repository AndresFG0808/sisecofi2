package com.mock;


import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonGeneral;
import com.sisecofi.libreria.comunes.repository.UsuarioRepository;
import com.sisecofi.proyectos.microservicio.CatalogoMicroservicio;
import com.sisecofi.proyectos.repository.FichaAdmonCentralRepository;
import com.sisecofi.proyectos.repository.FichaAlineacionRepository;
import com.sisecofi.proyectos.repository.FichaTecnicaRepository;
import com.sisecofi.proyectos.repository.HistoricoRepository;
import com.sisecofi.proyectos.repository.ProyectoRepository;
import com.sisecofi.proyectos.service.PistaService;
import com.util.FactoryClass;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import java.io.IOException;
import java.util.Optional;

public class FichaTecnicaMock {

	public static void fichaMock(FichaTecnicaRepository fichaRepository, PistaService pistaService,
			 ProyectoRepository proyectoRepository,
			CatalogoMicroservicio catalogoMicroservicio, HistoricoRepository historicoRepository,
			FichaAlineacionRepository fichaAlineacionRepository,
			FichaAdmonCentralRepository fichaAdmonCentralRepository, UsuarioRepository usuarioRepository)
			throws IOException {
		Mockito.when(fichaRepository.findByIdProyecto(anyLong())).thenReturn(FactoryClass.optionalFicha());
		Mockito.when(pistaService.guardarPista(anyInt(), anyInt(), anyInt(), anyString(),Optional.empty())).thenReturn(true);
		Mockito.when(catalogoMicroservicio.obtenerInformacionCatalogoId(anyInt(), anyInt()))
		.thenReturn(FactoryClass.obtenerEstatus());
		Mockito.when(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(anyInt(), anyInt(), eq(CatAdmonCentral.class)))
				.thenReturn(FactoryClass.regresarAdmonCentral());
		Mockito.when(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(anyInt(), anyInt(), eq(CatAdmonGeneral.class)))
		.thenReturn(FactoryClass.regresarAdmonGeneral());
		Mockito.when(historicoRepository.findByIdFichaTecnicaAndEstatusHistorico(anyLong(), anyBoolean()
				)).thenReturn(FactoryClass.obtenerHistoricoSet());
		Mockito.when(fichaAlineacionRepository.findByIdFichaTecnicaAndEstatusAlineacion(anyLong(), anyBoolean()))
				.thenReturn(FactoryClass.obtenerAlineaciones());
		
		Mockito.when(historicoRepository.findByIdFichaTecnicaAndEstatusAndEstatusHistorico(any(), anyBoolean(),
				anyBoolean())).thenReturn(Optional.empty());
		Mockito.when(proyectoRepository.findByIdProyectoAndEstatus(anyLong(), anyBoolean()))
				.thenReturn(FactoryClass.optionalProyecto());
		Mockito.when(usuarioRepository.findByEstatus(anyBoolean()))
		.thenReturn(FactoryClass.listaUsuarios());
	}
}
