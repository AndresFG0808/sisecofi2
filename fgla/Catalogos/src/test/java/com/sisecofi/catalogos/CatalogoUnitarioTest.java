package com.sisecofi.catalogos;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.sisecofi.catalogos.dto.CatalogoMetaDto;
import com.sisecofi.catalogos.service.impl.ServicioCatalogoImpl;
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
class CatalogoUnitarioTest {

	@Mock
	private ServicioCatalogoImpl servicioCatalogoImpl;

	@Test
	void catalogos() throws Exception {
		log.info("Probando mock catalogos");
		List<CatalogoMetaDto> mockList = FactoryClass.regresarMockMeta();
		Mockito.when(servicioCatalogoImpl.obtenerCatalogos()).thenReturn(mockList);
		List<CatalogoMetaDto> resultado = servicioCatalogoImpl.obtenerCatalogos();
		Assertions.assertEquals(mockList.get(0).getCatalogo(), resultado.get(0).getCatalogo());
	}

	@Test
	void metadataCatalogoAlta() throws Exception {
		Map<String, Object> map = FactoryClass.regresarMap();
		Mockito.when(servicioCatalogoImpl.metaCatalogo(1, true)).thenReturn(map);
		Map<String, Object> resultado = servicioCatalogoImpl.metaCatalogo(1, true);
		Assertions.assertEquals(map.get("Mock"), resultado.get("Mock"));
	}

	@Test
	void guardarCatalogo() throws Exception {
		BaseCatalogoModel esperado = FactoryClass.regresarCatalogoBase();
		String json = "{\"nombre\": \"prueba modificacion para servicio\",\"fechaCreacion\": 1707264000000,\"descripcion\": \"prueba modificacion para servicio\",\"fechaModificacion\":1707264000000,\"estatus\": true,\"idTipoMapa\": 19}";
		Mockito.when(servicioCatalogoImpl.guardarCatalogo(1, json)).thenReturn(esperado);
		BaseCatalogoModel resultado = servicioCatalogoImpl.guardarCatalogo(1, json);
		Assertions.assertEquals(esperado.getNombre(), resultado.getNombre());
	}

	@Test
	void actualizarCatalogo() throws Exception {
		BaseCatalogoModel esperado = FactoryClass.regresarCatalogoBase();
		String json = "{\"nombre\": \"prueba modificacion para servicio\",\"fechaCreacion\": 1707264000000,\"descripcion\": \"prueba modificacion para servicio\",\"fechaModificacion\":1707264000000,\"estatus\": true,\"idTipoMapa\": 19}";
		Mockito.when(servicioCatalogoImpl.actualizarCatalogo(1, json)).thenReturn(esperado);
		BaseCatalogoModel resultado = servicioCatalogoImpl.actualizarCatalogo(1, json);
		Assertions.assertEquals(esperado.getNombre(), resultado.getNombre());
	}

	@Test
	void obtenerInformacion() throws Exception {
		List<BaseCatalogoModel> mockList = FactoryClass.regresarListaCatalogos();
		Mockito.when(servicioCatalogoImpl.obtenerInformacion(1)).thenReturn(mockList);
		List<BaseCatalogoModel> resultado = servicioCatalogoImpl.obtenerInformacion(1);
		Assertions.assertEquals(mockList.get(0).getNombre(), resultado.get(0).getNombre());
	}

	@Test
	void obtenerInformacionReporte() throws Exception {
		List<BaseCatalogoModel> mockList = FactoryClass.regresarListaCatalogos();
		Mockito.when(servicioCatalogoImpl.obtenerInformacionReporte(1)).thenReturn(mockList);
		List<BaseCatalogoModel> resultado = servicioCatalogoImpl.obtenerInformacionReporte(1);
		Assertions.assertEquals(mockList.get(0).getNombre(), resultado.get(0).getNombre());
	}

	@Test
	void obtenerInformacionCampo() throws Exception {
		List<BaseCatalogoModel> mockList = FactoryClass.regresarListaCatalogos();
		String json = "{\"nombre\": \"Catalogo Mock\"}";
		Mockito.when(servicioCatalogoImpl.obtenerInformacionCampo(1, json)).thenReturn(mockList);
		List<BaseCatalogoModel> resultado = servicioCatalogoImpl.obtenerInformacionCampo(1, json);
		Assertions.assertEquals(mockList.get(0).getNombre(), resultado.get(0).getNombre());
	}

	@Test
	void obtenerInformacionId() throws Exception {
		BaseCatalogoModel esperado = FactoryClass.regresarCatalogoBase();
		Mockito.when(servicioCatalogoImpl.obtenerInformacionId(1, 1)).thenReturn(esperado);
		BaseCatalogoModel resultado = servicioCatalogoImpl.obtenerInformacionId(1, 1);
		Assertions.assertEquals(esperado.getPrimaryKey(), resultado.getPrimaryKey());
	}

	@Test
	void obtenerInformacionMeta() throws Exception {
		Map<String, Object> map = FactoryClass.regresarMap();
		Mockito.when(servicioCatalogoImpl.obtenerInformacionMeta(1, 1)).thenReturn(map);
		Map<String, Object> resultado = servicioCatalogoImpl.obtenerInformacionMeta(1, 1);
		Assertions.assertEquals(map.get("Mock"), resultado.get("Mock"));
	}
}
