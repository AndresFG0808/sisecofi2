package com.sisecofi.catalogos.service;

import java.util.List;

import com.sisecofi.catalogos.dto.AdminGeneric;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorGeneral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonGeneral;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface ServicioCatalogoComp {

	<T extends BaseCatalogoModel> List<T> obtenerAdministracion(int id);

	<T extends BaseCatalogoModel> List<T> obtenerCentral(int id);

	<T extends BaseCatalogoModel> List<T> obtenerMapas(int idCentral);

	AdminGeneric<CatAdmonGeneral, CatAdministradorGeneral> guardarAdministracionesGenerales(
			AdminGeneric<CatAdmonGeneral, CatAdministradorGeneral> adminGralDto);

	AdminGeneric<CatAdmonCentral, CatAdministradorCentral> guardarAdministracionesCentral(
			AdminGeneric<CatAdmonCentral, CatAdministradorCentral> adminGralDto);

	AdminGeneric<CatAdministracion, CatAdministradorAdministracion> guardarAdministraciones(
			AdminGeneric<CatAdministracion, CatAdministradorAdministracion> adminGralDto);

}
