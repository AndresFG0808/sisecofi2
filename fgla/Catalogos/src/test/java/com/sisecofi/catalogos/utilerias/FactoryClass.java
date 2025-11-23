package com.sisecofi.catalogos.utilerias;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sisecofi.catalogos.dto.CatalogoMetaDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class FactoryClass {

	private FactoryClass() {
	}

	public static BaseCatalogoModel retornarCatalogoBase() {
		BaseCatalogoModel base = new BaseCatalogoModel();
		base.setPrimaryKey(1);
		base.setNombre("prueba");
		return base;
	}

	public static List<CatalogoMetaDto> regresarMockMeta() {
		List<CatalogoMetaDto> mockList = new ArrayList<>();
		CatalogoMetaDto obj = new CatalogoMetaDto();
		obj.setIdCatalogo(1);
		obj.setCatalogo("Catalogo Mock");
		obj.setClase("Mock");
		mockList.add(obj);
		return mockList;
	}

	public static Map<String, Object> regresarMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("Mock", "mock");
		return map;
	}

	public static BaseCatalogoModel regresarCatalogoBase() {
		BaseCatalogoModel esperado = new BaseCatalogoModel();
		esperado.setNombre("prueba modificacion para servicio");
		return esperado;
	}

	public static List<BaseCatalogoModel> regresarListaCatalogos() {
		List<BaseCatalogoModel> mockList = new ArrayList<>();
		BaseCatalogoModel obj = new BaseCatalogoModel();
		obj.setPrimaryKey(1);
		obj.setNombre("Catalogo Mock");
		mockList.add(obj);
		return mockList;
	}

}
