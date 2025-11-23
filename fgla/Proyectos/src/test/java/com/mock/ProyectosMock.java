package com.mock;

import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonGeneral;
import com.sisecofi.libreria.comunes.util.sesion.Session;
import com.sisecofi.proyectos.microservicio.CatalogoMicroservicio;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import com.sisecofi.proyectos.repository.ProyectoRepository;
import com.sisecofi.proyectos.service.PistaService;
import com.sisecofi.proyectos.service.ServicioFichaTecnica;
import com.util.FactoryClass;
import org.mockito.Mockito;
import org.springframework.data.jpa.domain.Specification;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public class ProyectosMock {

	@SuppressWarnings("unchecked")
	public static void proyectosMock(ProyectoRepository proyectoRepository, PistaService pistaService,
			CatalogoMicroservicio catalogoMicroservicio, Session session, ServicioFichaTecnica fichaServicio) {

		List<ProyectoModel> mockList = FactoryClass.regresarListaProyectos();
		Mockito.when(pistaService.guardarPista(anyInt(), anyInt(), anyInt(), anyString(),Optional.empty())).thenReturn(true);
		Mockito.when(proyectoRepository.findAll(any(Specification.class))).thenReturn(mockList);
		Mockito.when(proyectoRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(FactoryClass.obtenerPaginacion());
		List<BaseCatalogoModel> lista = FactoryClass.regresarListaEstatus();
		Mockito.when(proyectoRepository.save(FactoryClass.regresarProyecto()))
				.thenReturn(FactoryClass.regresarProyecto());
		Mockito.when(catalogoMicroservicio.obtenerInformacionCatalogoCampo(anyInt(), anyString())).thenReturn(lista);

		Mockito.when(catalogoMicroservicio.obtenerInformacionCatalogoId(anyInt(), anyInt()))
				.thenReturn(FactoryClass.obtenerEstatus());

		Mockito.when(proyectoRepository.findTopByOrderByIdProyectoDesc()).thenReturn(FactoryClass.regresarProyecto());
		Mockito.when(proyectoRepository.findAll()).thenReturn(mockList);
		Mockito.when(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(anyInt(), anyInt(),
				eq(CatAdmonCentral.class))).thenReturn(FactoryClass.regresarAdmonCentral());
		Mockito.when(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(anyInt(), anyInt(),
				eq(CatAdmonGeneral.class))).thenReturn(FactoryClass.regresarAdmonGeneral());
		Mockito.when(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(anyInt(), anyInt(),
				eq(CatAdministracion.class))).thenReturn(FactoryClass.regresarAdministracion());
		Mockito.when(session.retornarUsuario()).thenReturn(FactoryClass.obtenerUsuario());

		Mockito.when(proyectoRepository.findByIdProyectoAndEstatus(anyLong(), eq(true)))
				.thenReturn(FactoryClass.optionalProyecto());
		Mockito.when(proyectoRepository.findByIdAgp(anyString())).thenReturn(Optional.empty());

	}
}
