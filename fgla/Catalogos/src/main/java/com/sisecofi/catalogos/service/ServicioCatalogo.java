package com.sisecofi.catalogos.service;

import java.util.List;
import java.util.Map;

import com.sisecofi.catalogos.dto.CatalogoMetaDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface ServicioCatalogo {

	List<CatalogoMetaDto> obtenerCatalogos();

	List<CatalogoMetaDto> obtenerCatalogosComplementarios();

	Map<String, Object> metaCatalogo(int idCatalogo, Boolean id);

	BaseCatalogoModel guardarCatalogo(int idCatalogo, String json);

	<T extends BaseCatalogoModel> T actualizarCatalogo(int idCatalogo, String json);

	<T extends BaseCatalogoModel> List<T> obtenerInformacion(int idCatalogo, String filtro);

	<T extends BaseCatalogoModel> List<T> obtenerInformacionReporte(int idCatalogo);

	<T extends BaseCatalogoModel> List<T> obtenerInformacionCampo(int idCatalogo, String valor);

	<T extends BaseCatalogoModel> List<T> obtenerInformacionCampoCompleta(int idCatalogo, String valor);

	BaseCatalogoModel obtenerInformacionId(int idCatalogo, Integer id);

	<T extends BaseCatalogoModel> T obtenerInformacionIdInterno(int idCatalogo, Integer id);

	Map<String, Object> obtenerInformacionMeta(int idCatalogo, Integer id);

}
