package com.mock;

import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonGeneral;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusProyecto;
import com.sisecofi.proyectos.microservicio.CatalogoMicroservicio;
import com.sisecofi.proyectos.service.impl.ServicioCatalogosImpl;
import com.util.FactoryClass;

import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BaseCatalogoMock {

	public static void baseCatalogoMock(ServicioCatalogosImpl servicioCatalogosImpl,
			CatalogoMicroservicio catalogoMicroservicio) {
		List<BaseCatalogoModel> baseCatalogoModel = new ArrayList<>();

		BaseCatalogoModel comite = new BaseCatalogoModel();
		comite.setNombre("NombreCatalogo");
		comite.setFechaCreacion(LocalDateTime.now());
		comite.setFechaModificacion(LocalDateTime.now());
		comite.setEstatus(true);

		// baseCatalogoModel.add(comite);
		baseCatalogoModel.add(FactoryClass.obtenerEstatus());

		Mockito.when(servicioCatalogosImpl.obtenerAfectacionInfo()).thenReturn(baseCatalogoModel);
		Mockito.when(servicioCatalogosImpl.obtenerContratoConvenioInfo()).thenReturn(baseCatalogoModel);
	//	Mockito.when(servicioCatalogosImpl.obtenerContratosInfo()).thenReturn(baseCatalogoModel);
		Mockito.when(servicioCatalogosImpl.obtenerSesionInfo()).thenReturn(baseCatalogoModel);
		Mockito.when(servicioCatalogosImpl.obtenerPlantillaInfo()).thenReturn(baseCatalogoModel);
		Mockito.when(servicioCatalogosImpl.obtenerTipoDeMonedaInfo()).thenReturn(baseCatalogoModel);
		Mockito.when(catalogoMicroservicio.obtenerInformacionCatalogoCampo(anyInt(), anyString()))
				.thenReturn(baseCatalogoModel);
		Mockito.when(catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(anyInt(), anyString()))
		.thenReturn(baseCatalogoModel);
		Mockito.when(catalogoMicroservicio.obtenerInformacionCatalogoId(anyInt(), anyInt()))
				.thenReturn(FactoryClass.obtenerEstatus());
		Mockito.when(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(anyInt(), anyInt(),
				eq(CatAdmonCentral.class))).thenReturn(FactoryClass.regresarAdmonCentral());
		Mockito.when(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(anyInt(), anyInt(),
				eq(CatEstatusProyecto.class))).thenReturn(FactoryClass.regresarEstatusInicial());
		Mockito.when(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(anyInt(), anyInt(),
				eq(CatAdmonGeneral.class))).thenReturn(FactoryClass.regresarAdmonGeneral());
		Mockito.when(catalogoMicroservicio.obtenerInformacionCatalogo(anyInt())).thenReturn(baseCatalogoModel);
	}
}
